package com.cavetale.mytems.block;

import com.cavetale.core.event.block.PlayerBreakBlockEvent;
import com.cavetale.mytems.MytemsPlugin;
import java.util.List;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class BlockBreakListener implements Listener {
    private final MytemsPlugin plugin;
    private Consumer<ItemSpawnEvent> storedDropCallback = null;

    public void enable() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Check the PlayerBreakBlockEvent, break the block, and apply the
     * callback to all drop events.
     */
    public boolean breakBlock(Player player, ItemStack tool, Block block, final Consumer<ItemSpawnEvent> dropCallback) {
        if (!new PlayerBreakBlockEvent(player, block, tool).callEvent()) return false;
        this.storedDropCallback = dropCallback;
        try {
            if (tool != null) {
                block.breakNaturally(tool);
            } else {
                block.breakNaturally();
            }
        } finally {
            this.storedDropCallback = null;
        }
        return true;
    }

    public boolean breakBlockAndPickup(Player player, ItemStack tool, Block block) {
        return breakBlock(player, tool, block, itemSpawnEvent -> {
                final Item item = itemSpawnEvent.getEntity();
                Bukkit.getScheduler().runTask(plugin, () -> {
                        item.teleport(player.getLocation());
                        item.setPickupDelay(0);
                    });
            });
    }

    public boolean breakBlockNoPhysics(Player player, ItemStack tool, Block block, final Consumer<List<ItemStack>> dropCallback) {
        if (!new PlayerBreakBlockEvent(player, block, tool).callEvent()) return false;
        final List<ItemStack> drops;
        if (player != null && tool != null) {
            drops = List.copyOf(block.getDrops(tool, player));
        } else if (tool != null) {
            drops = List.copyOf(block.getDrops(tool));
        } else {
            drops = List.copyOf(block.getDrops());
        }
        final boolean applyPhysics = false;
        block.setType(Material.AIR, applyPhysics);
        dropCallback.accept(drops);
        return true;
    }

    public boolean breakBlockNoPhysicsAndPickup(Player player, ItemStack tool, Block block) {
        return breakBlockNoPhysics(player, tool, block, drops -> {
                for (ItemStack drop : drops) {
                    final Item item = player.getWorld().dropItem(player.getLocation(), drop);
                    item.setPickupDelay(0);
                }
            });
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onItemSpawn(ItemSpawnEvent event) {
        if (storedDropCallback != null) {
            storedDropCallback.accept(event);
        }
    }

    public static BlockBreakListener blockBreakListener() {
        return MytemsPlugin.blockBreakListener();
    }
}
