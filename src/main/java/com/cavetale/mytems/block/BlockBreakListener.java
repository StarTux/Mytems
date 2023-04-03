package com.cavetale.mytems.block;

import com.cavetale.core.event.block.PlayerBreakBlockEvent;
import com.cavetale.mytems.MytemsPlugin;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
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
        if (!new PlayerBreakBlockEvent(player, block).callEvent()) return false;
        this.storedDropCallback = dropCallback;
        try {
            block.breakNaturally(tool);
        } finally {
            this.storedDropCallback = null;
        }
        return true;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onItemSpawn(ItemSpawnEvent event) {
        if (storedDropCallback != null) {
            storedDropCallback.accept(event);
        }
    }
}
