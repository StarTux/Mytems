package com.cavetale.mytems.item.treechopper;

import com.cavetale.core.command.CommandArgCompleter;
import com.cavetale.core.command.CommandNode;
import com.cavetale.core.command.CommandWarn;
import com.cavetale.core.font.GuiOverlay;
import com.cavetale.core.util.Json;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Gui;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.exploits.PlayerPlacedBlocks.isPlayerPlaced;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

@RequiredArgsConstructor @Getter
public final class TreeChopper implements Mytem {
    private final Mytems key;
    private static final TextColor ALLGREEN = TextColor.color(0x00FF00);
    private static final TextColor ALLRED = TextColor.color(0xFF0000);
    private ItemStack prototype;
    private Component displayName;
    private CommandNode commandNode;

    @Override
    public void enable() {
        this.displayName = text("Tree Chopper", ALLGREEN);
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                Items.text(meta, makeItemTooltip(new TreeChopperTag()));
                meta.setUnbreakable(true);
                meta.addItemFlags(ItemFlag.values());
                key.markItemMeta(meta);
            });
        commandNode = MytemsPlugin.getInstance().getMytemsCommand().registerItemCommand(key)
            .description("Tree Chopper commands");
        CommandNode statNode = commandNode.addChild("stat")
            .description("Change a stat");
        for (TreeChopperStat stat : TreeChopperStat.values()) {
            statNode.addChild(stat.key)
                .description("Change the " + stat.displayName + " stat")
                .completers(CommandArgCompleter.integer(i -> true))
                .playerCaller((player, args) -> statCommand(stat, player, args));
        }
        commandNode.addChild("max").denyTabCompletion()
            .description("Max out your chopper")
            .playerCaller(this::maxCommand);
        commandNode.addChild("debug").denyTabCompletion()
            .description("Dump debug information")
            .senderCaller(this::debug);
        Bukkit.getPluginManager().registerEvents(new TreeChopListener(), MytemsPlugin.getInstance());
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, Player player, ItemStack itemStack) {
        tryToChop((Cancellable) event, player, itemStack, event.getBlock());
    }

    @Override
    public void onBlockDamage(BlockDamageEvent event, Player player, ItemStack itemStack) {
        tryToChop((Cancellable) event, player, itemStack, event.getBlock());
    }

    private void tryToChop(Cancellable event, Player player, ItemStack itemStack, Block block) {
        if (event.isCancelled()) return;
        if (!Tag.LOGS.isTagged(block.getType())) return;
        if (isPlayerPlaced(block)) return;
        TreeChopperTag tag = serializeTag(itemStack);
        switch (player.getGameMode()) {
        case SURVIVAL:
        case ADVENTURE:
            if (event instanceof BlockDamageEvent && tag.getStat(TreeChopperStat.PUNCH) == 0) {
                return;
            }
            if (event instanceof BlockBreakEvent && tag.getStat(TreeChopperStat.PUNCH) != 0) {
                return;
            }
            break;
        case CREATIVE: // always allow
            break;
        case SPECTATOR: // always deny
        default:
            return;
        }
        if (player.getFoodLevel() < 2) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 0.5f, 0.75f);
            player.sendActionBar(text("You're low on food!", GRAY));
            return;
        }
        if (player.getVehicle() != null || player.isGliding() || player.isFlying() || player.isSwimming()) {
            player.sendActionBar(text("Cannot chop unless you're on your feet", GRAY));
            return;
        }
        TreeChopperSession session = MytemsPlugin.getInstance().getSessions().of(player)
            .getFavorites().getOrSet(TreeChopperSession.class, TreeChopperSession::new);
        if (session.x == block.getX() && session.y == block.getY() && session.z == block.getZ()) {
            return;
        }
        TreeChop chop = new TreeChop(tag);
        TreeChopStatus status = chop.fill(player, block);
        if (false) {
            // DEBUG
            player.sendMessage("Chop!"
                               + " " + status
                               + " logs=" + chop.logBlocks.size() + "/" + tag.getMaxLogBlocks()
                               + " leaves=" + chop.leafBlocks.size() + "/" + tag.getMaxLeafBlocks());
        }
        new TreeChopEvent(player, chop, status).callEvent();
        switch (status) {
        case SUCCESS: break;
        case TOO_MANY_LOGS:
            player.sendActionBar(join(noSeparators(),
                                      text("Tree is too big! Level up your "),
                                      displayName,
                                      text(" first!"))
                                 .color(GRAY));
            return;
        case NOTHING_FOUND:
        case NO_SAPLING:
        default: return;
        }
        event.setCancelled(true);
        chop.chop(player);
        session.x = block.getX();
        session.y = block.getY();
        session.z = block.getZ();
        if (!tag.upgradeIsPossible()) return;
        int xp = tag.getStat(TreeChopperStat.XP);
        int upgradeCost = tag.getUpgradeCost();
        int xpBonus = 0;
        if (xp < upgradeCost && !chop.saplingBlocks.isEmpty()) {
            // Need blocks on the ground to get xp: Otherwise, cutting
            // top-to-bottom would yield way too much!
            xpBonus = 1;
            for (int i = 1; i <= chop.logBlocks.size(); i *= 2) {
                xpBonus += 1;
            }
            xpBonus = Math.max(1, (xpBonus - 1) / 2 + 1);
            xp = Math.min(upgradeCost, xp + xpBonus);
            tag.setStat(TreeChopperStat.XP, xp);
            tag.store(itemStack);
            Items.text(itemStack, makeItemTooltip(tag));
        }
        if (xp >= upgradeCost) {
            Component msg = join(noSeparators(),
                                 text("Your "),
                                 displayName,
                                 text(" has enough Experience to be upgraded!"))
                .color(WHITE);
            player.sendMessage(msg);
            player.sendActionBar(msg);
        } else if (xpBonus > 0) {
            Component msg = join(noSeparators(),
                                 displayName,
                                 text(" " + xp),
                                 text("/", WHITE),
                                 text(upgradeCost))
                .color(ALLGREEN);
            player.sendActionBar(msg);
        }
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack itemStack) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!player.isSneaking() && event.hasBlock()) {
            Block block = event.getClickedBlock();
            Material mat = block.getType();
            if (mat.isInteractable()) return;
            if (Tag.LOGS.isTagged(mat) && !mat.name().startsWith("STRIPPED")) return;
        }
        event.setUseInteractedBlock(Event.Result.DENY);
        TreeChopperTag tag = serializeTag(itemStack);
        int xp = tag.getStat(TreeChopperStat.XP);
        openUpgradeGui(player, itemStack, tag);
    }

    protected List<Component> makeItemTooltip(TreeChopperTag tag) {
        List<Component> result = new ArrayList<>();
        int level = tag.getLevel();
        if (level <= 0) {
            result.add(displayName);
        } else {
            result.add(join(noSeparators(),
                            displayName,
                            text(" Level " + level, WHITE)));
        }
        result.add(join(noSeparators(),
                        text("Experience "),
                        text(tag.getStat(TreeChopperStat.XP), ALLGREEN),
                        text("/"),
                        text(tag.getUpgradeCost(), ALLGREEN))
                   .color(WHITE));
        if (level <= 0) {
            result.add(text("Break the base of a tree", GRAY));
            result.add(text("to chop it down entirely.", GRAY));
            result.add(text("Chopped trees grant ex-", GRAY));
            result.add(text("perience which unlock", GRAY));
            result.add(text("powerful upgrades!", GRAY));
            result.add(empty());
            result.add(join(noSeparators(), Mytems.MOUSE_RIGHT, text(" Open menu", GRAY)));
        }
        for (TreeChopperStat stat : TreeChopperStat.values()) {
            if (stat.type != TreeChopperStat.Type.UPGRADE) continue;
            int value = tag.getStat(stat);
            if (value > 0) {
                result.add(text(stat.displayName + " " + Text.roman(value), GRAY));
            }
            if (value > 0 || stat == TreeChopperStat.CHOP) {
                result.addAll(getStatDescription(stat, value));
            }
        }
        return result;
    }

    public List<Component> getStatDescription(TreeChopperStat stat, int value) {
        switch (stat) {
        case CHOP: {
            int n = TreeChopperTag.getMaxLogBlocks(value);
            return List.of(text("Chop trees with up", DARK_GRAY),
                           text("to " + n + " logs", DARK_GRAY));
        }
        case LEAF: {
            int n = TreeChopperTag.getMaxLeafBlocks(value);
            return List.of(text("Chop up to " + n, DARK_GRAY),
                           text("attached leaves", DARK_GRAY));
        }
        case FORTUNE: {
            String n = Text.roman(value);
            return List.of(text("Bonus drops from", DARK_GRAY),
                           text("chopped leaves", DARK_GRAY));
        }
        case SILK:
            if (value < 2) {
                return List.of(text("Chopped leaves drop", DARK_GRAY),
                               text("as blocks", DARK_GRAY));
            } else {
                return List.of(text("Chopped leaves and", DARK_GRAY),
                               text("vines drop as blocks", DARK_GRAY));
            }
        case REPLANT:
            return List.of(text("Chopped trees are", DARK_GRAY),
                           text("instantly replanted", DARK_GRAY));
        case PICKUP:
            return List.of(text("Pick up broken blocks", DARK_GRAY));
        case SPEED: {
            int n = TreeChopperTag.getChoppingSpeed(value);
            return List.of(text("Chop trees " + n + " times", DARK_GRAY),
                           text("as fast", DARK_GRAY));
        }
        case PUNCH: {
            return List.of(text("Hit the root block", DARK_GRAY),
                           text("to start chopping", DARK_GRAY));
        }
        case ENCH: {
            return List.of(text("Chopped logs and", DARK_GRAY),
                           text("leaves drop exp", DARK_GRAY));
        }
        default: return List.of();
        }
    }

    @Override
    public TreeChopperTag serializeTag(ItemStack itemStack) {
        TreeChopperTag tag = new TreeChopperTag();
        tag.load(itemStack);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        ItemStack itemStack = createItemStack();
        TreeChopperTag tag = Json.deserialize(serialized, TreeChopperTag.class, TreeChopperTag::new);
        tag.store(itemStack);
        Items.text(itemStack, makeItemTooltip(tag));
        return itemStack;
    }

    protected boolean statCommand(TreeChopperStat stat, Player player, String[] args) {
        if (args.length != 1) return false;
        int value;
        try {
            value = Integer.parseInt(args[0]);
        } catch (IllegalArgumentException iae) {
            throw new CommandWarn("Bad integer: " + args[0]);
        }
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (!key.isItem(itemStack)) {
            throw new CommandWarn("Hold the Tree Chopper in your main hand!");
        }
        TreeChopperTag tag = serializeTag(itemStack);
        int oldValue = tag.getStat(stat);
        tag.setStat(stat, value);
        tag.store(itemStack);
        Items.text(itemStack, makeItemTooltip(tag));
        int newValue = tag.getStat(stat);
        player.sendMessage(text(stat.displayName + " changed from " + oldValue + " to " + newValue, YELLOW));
        return true;
    }

    protected boolean maxCommand(Player player, String[] args) {
        if (args.length != 0) return false;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (!key.isItem(itemStack)) {
            throw new CommandWarn("Hold the Tree Chopper in your main hand!");
        }
        TreeChopperTag tag = serializeTag(itemStack);
        for (TreeChopperStat stat : TreeChopperStat.values()) {
            if (stat.type != TreeChopperStat.Type.UPGRADE) continue;
            tag.setStat(stat, stat.maxLevel);
        }
        tag.store(itemStack);
        Items.text(itemStack, makeItemTooltip(tag));
        player.sendMessage(text("Your Tree Chopper was maxed out", YELLOW));
        return true;
    }

    protected boolean openUpgradeGui(Player player, ItemStack itemStack, TreeChopperTag tag) {
        List<TreeChopperStat> upgrades = new ArrayList<>();
        for (TreeChopperStat stat : TreeChopperStat.values()) {
            if (stat.type != TreeChopperStat.Type.UPGRADE) continue;
            if (tag.getStat(stat) <= 0 && stat.conflictsWith(tag)) continue;
            upgrades.add(stat);
        }
        final int size = 4 * 9;
        Gui gui = new Gui()
            .size(size)
            .title(GuiOverlay.BLANK.builder(size, DARK_GREEN)
                   .title(join(noSeparators(), key.component, displayName, text(" Upgrades", WHITE)))
                   .layer(GuiOverlay.TOP_BAR, TextColor.color(0x115511))
                   .build());
        gui.setItem(4, itemStack.clone());
        int i = 18 + (9 - upgrades.size()) / 2;
        for (TreeChopperStat stat : upgrades) {
            boolean doesMeetRequirements = stat.doesMeetRequirements(tag);
            boolean maxLevelExceeded = tag.getStat(stat) >= stat.maxLevel;
            final boolean locked = !doesMeetRequirements
                || tag.getStat(TreeChopperStat.XP) < tag.getUpgradeCost()
                || maxLevelExceeded;
            final boolean conflicts = stat.conflictsWith(tag); // happens with maxed axes
            List<Component> tooltip = new ArrayList<>();
            int level = Math.min(stat.maxLevel, tag.getStat(stat) + 1);
            tooltip.add(text(stat.displayName + " " + Text.roman(level), locked ? ALLRED : ALLGREEN));
            tooltip.add(text("Max Level " + Text.roman(stat.maxLevel),
                             maxLevelExceeded ? GOLD : GRAY, ITALIC));
            for (Map.Entry<TreeChopperStat, Integer> requirement : stat.requirements.entrySet()) {
                tooltip.add(text("Requires " + requirement.getKey().displayName
                                 + " " + Text.roman(requirement.getValue()),
                                 doesMeetRequirements ? GRAY : ALLRED, ITALIC));
            }
            for (TreeChopperStat conflict : stat.conflicts) {
                tooltip.add(text("Conflicts with " + conflict.displayName, GRAY, ITALIC));
            }
            tooltip.addAll(getStatDescription(stat, level));
            ItemStack icon = maxLevelExceeded
                ? Mytems.STAR.createIcon()
                : locked ? Mytems.COPPER_KEYHOLE.createIcon() : stat.icon.get();
            Items.text(icon, tooltip);
            int itemIndex = i++;
            gui.setItem(itemIndex, icon, click -> {
                    if (!click.isLeftClick()) return;
                    if (locked || conflicts) {
                        player.playSound(player.getLocation(),
                                         Sound.UI_BUTTON_CLICK, SoundCategory.MASTER,
                                         0.5f, 0.5f);
                        return;
                    }
                    player.playSound(player.getLocation(),
                                     Sound.UI_BUTTON_CLICK, SoundCategory.MASTER,
                                     0.5f, 1.25f);
                    if (upgrade(player, itemStack, tag, stat)) {
                        player.closeInventory();
                    }
                });
        }
        gui.open(player);
        return true;
    }

    protected boolean upgrade(Player player, ItemStack itemStack, TreeChopperTag tag, TreeChopperStat stat) {
        final int xp = tag.getStat(TreeChopperStat.XP);
        final int cost = tag.getUpgradeCost();
        if (xp < cost) return false;
        int level = tag.getStat(stat);
        if (level >= stat.maxLevel) return false;
        if (stat.conflictsWith(tag)) return false;
        if (!stat.doesMeetRequirements(tag)) return false;
        tag.setStat(TreeChopperStat.XP, xp - cost);
        level += 1;
        tag.setStat(stat, level);
        tag.store(itemStack);
        Items.text(itemStack, makeItemTooltip(tag));
        player.showTitle(Title.title(text(stat.displayName, ALLGREEN),
                                     text("Level " + level, WHITE)));
        player.playSound(player.getLocation(),
                         Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER,
                         0.5f, 1.0f);
        return true;
    }

    protected boolean debug(CommandSender sender, String[] args) {
        if (args.length != 0) return false;
        sender.sendMessage("Chopping " + TreeChop.CHOPPING.size() + " blocks:");
        int i = 0;
        for (Block block : TreeChop.CHOPPING) {
            if (i++ >= 9) break;
            sender.sendMessage("- " + block.getWorld().getName()
                               + " " + block.getX()
                               + " " + block.getY()
                               + " " + block.getZ());
        }
        return true;
    }

    @Override
    public boolean isMassStorable() {
        return false;
    }
}
