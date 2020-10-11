package com.cavetale.mytems;

import com.cavetale.mytems.item.DrAculaStaff;
import com.cavetale.worldmarker.ItemMarker;
import com.destroystokyo.paper.event.inventory.PrepareResultEvent;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class EventListener implements Listener {
    private final MytemsPlugin plugin;

    public void enable() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    void onPlayerInteract(PlayerInteractEvent event) {
        switch (event.getAction()) {
        case RIGHT_CLICK_BLOCK:
        case RIGHT_CLICK_AIR:
            onPlayerRightClick(event);
            break;
        default: break;
        }
    }

    void onPlayerRightClick(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null) return;
        String id = ItemMarker.getId(item);
        if (id == null) return;
        switch (id) {
        case DrAculaStaff.ID:
            event.setCancelled(true);
            plugin.drAculaStaff.onRightClick(event, item);
            return;
        default:
            break;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onPlayerJoin(PlayerJoinEvent event) {
        plugin.enter(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onPlayerQuit(PlayerQuitEvent event) {
        plugin.exit(event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    void onPrepareResult(PrepareResultEvent event) {
        ItemStack item = event.getResult();
        if (item == null) return;
        String id = ItemMarker.getId(item);
        if (id == null) return;
        switch (id) {
        case DrAculaStaff.ID:
            switch (event.getView().getType()) {
            case GRINDSTONE:
                event.setResult(null);
                break;
            default:
                event.setResult(plugin.drAculaStaff.create());
            }
            return;
        default:
            break;
        }
    }
}
