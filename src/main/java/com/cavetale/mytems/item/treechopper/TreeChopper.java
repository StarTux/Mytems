package com.cavetale.mytems.item.treechopper;

import com.cavetale.core.command.CommandNode;
import com.cavetale.core.util.Json;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.exploits.PlayerPlacedBlocks.isPlayerPlaced;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor @Getter
public final class TreeChopper implements Mytem {
    private final Mytems key;
    private final TreeChopperTier tier;
    private ItemStack prototype;
    private Component displayName;
    private CommandNode commandNode;

    public TreeChopper(final Mytems key) {
        this.key = key;
        this.tier = TreeChopperTier.of(key);
    }

    @Override
    public void enable() {
        displayName = tier.getDisplayName();
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> key.markItemMeta(meta));
        tier.createTag().store(key, prototype);
        // Commands
        commandNode = MytemsPlugin.getInstance().getMytemsCommand().registerItemCommand(key)
            .description("Tree Chopper commands");
        commandNode.addChild("debug").denyTabCompletion()
            .description("Dump debug information")
            .senderCaller(this::debug);
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
        if (!ChoppedType.ALL_LOGS.contains(block.getType())) return;
        if (isPlayerPlaced(block)) return;
        TreeChopperTag tag = serializeTag(itemStack);
        switch (player.getGameMode()) {
        case SURVIVAL:
        case ADVENTURE:
            if (event instanceof BlockDamageEvent && tag.getEffectiveUpgradeLevel(TreeChopperStat.PUNCH) == 0) {
                return;
            }
            if (event instanceof BlockBreakEvent && tag.getEffectiveUpgradeLevel(TreeChopperStat.PUNCH) != 0) {
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
        final TreeChop chop = new TreeChop(tag);
        final TreeChopStatus status = chop.fill(player, block);
        if (false) {
            // DEBUG
            player.sendMessage("Chop!"
                               + " " + status
                               + " type=" + chop.getChoppedType()
                               + " logs=" + chop.logBlocks.size() + "/" + tag.getMaxLogBlocks()
                               + " leaves=" + chop.leafBlocks.size() + "/" + tag.getMaxLeafBlocks());
        }
        new TreeChopEvent(player, chop, status).callEvent();
        switch (status) {
        case SUCCESS: break;
        case TOO_MANY_LOGS:
            player.sendActionBar(textOfChildren(text("Tree is too big! Level up your "),
                                                displayName,
                                                text(" first!"))
                                 .color(GRAY));
            return;
        case NOTHING_FOUND:
        case NO_SAPLING:
        case STAT_REQUIRED:
        default: return;
        }
        event.setCancelled(true);
        chop.chop(player, itemStack);
        session.x = block.getX();
        session.y = block.getY();
        session.z = block.getZ();
        // Give XP
        if (chop.saplingBlocks.isEmpty()) {
            return;
        }
        // Need blocks on the ground to get xp: Otherwise, cutting
        // top-to-bottom would yield way too much!
        int xpBonus = 1;
        for (int i = 1; i <= chop.logBlocks.size(); i *= 2) {
            xpBonus += 1;
        }
        xpBonus = Math.max(1, (xpBonus - 1) / 2 + 1);
        if (tag.addXpAndNotify(player, xpBonus)) {
            tag.store(key, itemStack);
        }
    }

    @Override
    public TreeChopperTag serializeTag(ItemStack itemStack) {
        TreeChopperTag tag = tier.createTag();
        tag.load(key, itemStack);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        ItemStack itemStack = createItemStack();
        TreeChopperTag tag = Json.deserialize(serialized, tier.getTagClass());
        if (tag != null && !tag.isEmpty()) {
            tag.store(key, itemStack);
        }
        return itemStack;
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
}
