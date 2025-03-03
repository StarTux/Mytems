package com.cavetale.mytems.item.garden;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerBreakBlockEvent;
import com.cavetale.core.event.block.PlayerChangeBlockEvent;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Text;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundGroup;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.MytemsPlugin.plugin;
import static com.cavetale.mytems.util.Items.tooltip;
import static com.cavetale.mytems.util.Items.unbreakable;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;
import static org.bukkit.Particle.*;

@RequiredArgsConstructor @Getter
public final class Scythe implements Mytem, Listener {
    private final Mytems key;
    private final Quality quality;
    private final Component displayName;
    private final ItemStack prototype;
    private final boolean leftClickAllowed = false; // Flip switch if whining ensues
    private CropType brokenCropType;
    private boolean dropReduced;
    private Player breakingPlayer;

    public Scythe(final Mytems mytems) {
        this.key = mytems;
        this.quality = Quality.of(mytems);
        this.displayName = Text.gradient(quality.displayName, quality.dark, quality.light);
        this.prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                List<Component> text = new ArrayList<>();
                text.add(displayName);
                text.addAll(Text.wrapLore(quality.description, c -> c.color(quality.light)));
                text.add(empty());
                if (leftClickAllowed) {
                    text.add(textOfChildren(Mytems.MOUSE_LEFT, text("/", GRAY), Mytems.MOUSE_RIGHT, text(" Harvest crop", quality.light)));
                } else {
                    text.add(textOfChildren(Mytems.MOUSE_RIGHT, text(" Harvest crop", quality.light)));
                }
                tooltip(meta, text);
                unbreakable(meta);
                meta.addItemFlags(ItemFlag.values());
                key.markItemMeta(meta);
            });
    }

    @RequiredArgsConstructor
    private enum Quality {
        IRON(Mytems.IRON_SCYTHE, 0,
             color(0x777777), color(0xd8d8d8),
             "Iron Scythe",
             "Harvest crops and replant instantly."),
        GOLD(Mytems.GOLDEN_SCYTHE, 1,
             color(0xD4AF37), color(0xE7C065),
             "Golden Scythe",
             "Harvest your crops in a 3x3 area and replant instantly."),
        ;

        public final Mytems mytems;
        public final int radius;
        private final TextColor dark;
        private final TextColor light;
        public final String displayName;
        public final String description;

        public static Quality of(Mytems mytems) {
            for (Quality it : values()) {
                if (it.mytems == mytems) return it;
            }
            return null;
        }
    }

    @Override
    public void enable() {
        Bukkit.getPluginManager().registerEvents(this, plugin());
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (player.getGameMode() == GameMode.SPECTATOR) return;
        if (!event.hasBlock()) return;
        event.setUseInteractedBlock(Event.Result.DENY);
        click(player, event.getClickedBlock(), item);
    }

    @Override
    public void onPlayerLeftClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (player.getGameMode() == GameMode.SPECTATOR) return;
        if (!event.hasBlock()) return;
        event.setUseInteractedBlock(Event.Result.DENY);
        if (!leftClickAllowed) return;
        click(player, event.getClickedBlock(), item);
    }

    private void click(Player player, Block block, ItemStack itemStack) {
        if (player.getGameMode() != GameMode.CREATIVE && player.getFoodLevel() == 0) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 0.5f, 0.75f);
            player.sendActionBar(text("You are exhausted", RED));
            return;
        }
        final int radius = quality.radius;
        Bukkit.getScheduler().runTask(plugin(), () -> {
                int count = 0;
                for (int z = -radius; z <= radius; z += 1) {
                    for (int x = -radius; x <= radius; x += 1) {
                        for (int y = -radius; y <= radius; y += 1) {
                            if (harvestBlock(player, block.getRelative(x, 0, z), itemStack)) {
                                count += 1;
                            }
                        }
                    }
                }
                if (count > 0) {
                    player.swingMainHand();
                    if (player.getGameMode() != GameMode.CREATIVE) {
                        player.setExhaustion(player.getExhaustion() + 0.01f);
                    }
                }
            });
    }

    @RequiredArgsConstructor
    private enum CropType {
        WHEAT(Material.WHEAT, Material.WHEAT_SEEDS),
        BEETROOT(Material.BEETROOTS, Material.BEETROOT_SEEDS),
        POTATO(Material.POTATOES, Material.POTATO),
        CARROT(Material.CARROTS, Material.CARROT),
        NETHER_WART(Material.NETHER_WART, Material.NETHER_WART),
        ;

        public final Material crop;
        public final Material seed;

        public static CropType of(Block block) {
            Material mat = block.getType();
            for (CropType it : values()) {
                if (mat == it.crop) return it;
            }
            return null;
        }

        public static CropType of(ItemStack item) {
            Material mat = item.getType();
            for (CropType it : values()) {
                if (mat == it.seed) return it;
            }
            return null;
        }
    }

    private boolean harvestBlock(Player player, Block block, ItemStack itemStack) {
        final CropType cropType = CropType.of(block);
        if (cropType == null) return false;
        BlockData data = block.getBlockData();
        if (!(data instanceof Ageable)) return false;
        Ageable ageable = (Ageable) data;
        if (ageable.getAge() != ageable.getMaximumAge()) return false;
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return false;
        if (!new PlayerBreakBlockEvent(player, block, itemStack).callEvent()) return false;
        SoundGroup snd = block.getBlockSoundGroup();
        block.getWorld().playSound(block.getLocation(), snd.getBreakSound(), snd.getVolume(), snd.getPitch());
        if (player.getGameMode() != GameMode.CREATIVE) {
            this.brokenCropType = cropType;
            this.dropReduced = false;
            this.breakingPlayer = player;
            block.breakNaturally(true); // triggerEffect
            this.brokenCropType = null;
            this.breakingPlayer = null;
        }
        block.getWorld().spawnParticle(SWEEP_ATTACK, block.getLocation().add(0.5, 0.5, 0.5), 1, 0.0, 0.0, 0.0, 0.0);
        ageable.setAge(0);
        new PlayerChangeBlockEvent(player, block, ageable, itemStack).callEvent();
        block.setBlockData(ageable, true);
        return true;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    private void onItemSpawn(ItemSpawnEvent event) {
        if (brokenCropType == null) return;
        Item item = event.getEntity();
        ItemStack itemStack = item.getItemStack();
        if (!dropReduced && CropType.of(itemStack) == brokenCropType) {
            dropReduced = true;
            final int amount = itemStack.getAmount();
            itemStack.subtract(1);
            if (amount <= 1) {
                event.setCancelled(true);
                return;
            }
        }
        item.teleport(breakingPlayer);
        item.setPickupDelay(0);
        item.setOwner(breakingPlayer.getUniqueId());
    }
}
