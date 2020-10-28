package com.cavetale.mytems;

import com.cavetale.worldmarker.ItemMarker;
import com.destroystokyo.paper.event.inventory.PrepareResultEvent;
import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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
        Mytems key = Mytems.forId(id);
        if (key == null) return;
        plugin.getMytem(key).onPlayerRightClick(event, event.getPlayer(), item);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onPlayerJoin(PlayerJoinEvent event) {
        plugin.enter(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onPlayerQuit(PlayerQuitEvent event) {
        plugin.exit(event.getPlayer());
    }

    /**
     * Mytems cannot be used on a grindstone or enchanted.
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    void onPrepareResult(PrepareResultEvent event) {
        ItemStack item = event.getResult();
        if (item == null) return;
        String id = ItemMarker.getId(item);
        if (id == null) return;
        Mytems key = Mytems.forId(id);
        if (key == null) return;
        switch (event.getView().getType()) {
        case GRINDSTONE:
            event.setResult(null);
            break;
        default:
            event.setResult(plugin.getMytem(key).getItem());
        }
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
    void onPlayerFallDamage(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        ItemStack item = player.getInventory().getBoots();
        if (item == null) return;
        String id = ItemMarker.getId(item);
        if (id == null) return;
        Mytems mytems = Mytems.forId(id);
        if (mytems == null) return;
        plugin.getMytem(mytems).onPlayerFallDamage(event, player, item);
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOW)
    void onEntityShootBow(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        ItemStack item = event.getBow();
        if (item == null) return;
        String id = ItemMarker.getId(item);
        if (id == null) return;
        Mytems mytems = Mytems.forId(id);
        if (mytems == null) return;
        plugin.getMytem(mytems).onPlayerShootBow(event, player, item);
    }

    @EventHandler
    void onPlayerItemHeld(PlayerItemHeldEvent event) {
        plugin.sessions.of(event.getPlayer()).equipmentDidChange();
    }

    @EventHandler
    void onPlayerArmorChange(PlayerArmorChangeEvent event) {
        plugin.sessions.of(event.getPlayer()).equipmentDidChange();
    }

    @EventHandler
    void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() instanceof PlayerInventory && event.getWhoClicked() instanceof Player) {
            plugin.sessions.of((Player) event.getWhoClicked()).equipmentDidChange();
        }
    }

    @EventHandler
    void onPlayerPickupItem(PlayerPickupItemEvent event) {
        plugin.sessions.of(event.getPlayer()).equipmentDidChange();
    }

    @EventHandler
    void onPlayerDropItem(PlayerDropItemEvent event) {
        plugin.sessions.of(event.getPlayer()).equipmentDidChange();
    }

    @EventHandler
    void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        plugin.sessions.of(event.getPlayer()).equipmentDidChange();
    }
}
