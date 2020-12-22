package com.cavetale.mytems;

import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.item.ChristmasToken;
import com.cavetale.worldmarker.ItemMarker;
import com.destroystokyo.paper.event.inventory.PrepareResultEvent;
import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
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
            break;
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
    void onEntityPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            plugin.sessions.of(player).equipmentDidChange();
            Bukkit.getScheduler().runTask(plugin, () -> plugin.fixPlayerInventory(player));
        }
    }

    @EventHandler
    void onPlayerDropItem(PlayerDropItemEvent event) {
        plugin.sessions.of(event.getPlayer()).equipmentDidChange();
    }

    @EventHandler
    void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        plugin.sessions.of(event.getPlayer()).equipmentDidChange();
    }

    /**
     * Restore the Christmas Token and fix the missing id bug.
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onBlockDropItem(BlockDropItemEvent event) {
        BlockState state = event.getBlockState();
        if (event.getItems().size() != 1) return;
        if (!(state instanceof Skull)) return;
        Skull skull = (Skull) state;
        PlayerProfile profile = skull.getPlayerProfile();
        if (profile == null) return;
        UUID id = profile.getId();
        if (!Objects.equals(id, ChristmasToken.SKULL_ID)) return;
        if (!profile.hasProperty("textures")) return;
        for (ProfileProperty property : profile.getProperties()) {
            if (Objects.equals(property.getName(), "textures")
                && Objects.equals(property.getValue(), ChristmasToken.SKULL_TEXTURE)) {
                // Success!
                Location location = event.getItems().get(0).getLocation();
                event.setCancelled(true);
                location.getWorld().dropItem(location, Mytems.CHRISTMAS_TOKEN.getMytem().getItem());
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    void onBlockPlace(BlockPlaceEvent event) {
        ItemStack itemStack = event.getItemInHand();
        if (itemStack != null) {
            Mytems mytems = Mytems.forItem(itemStack);
            if (mytems != null) {
                mytems.getMytem().onBlockPlace(event, event.getPlayer(), itemStack);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    void onEntityToggleGlide(EntityToggleGlideEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        ItemStack itemStack = player.getInventory().getChestplate();
        if (itemStack == null) return;
        Mytems mytems = Mytems.forItem(itemStack);
        if (mytems == null) return;
        mytems.getMytem().onToggleGlide(event, player, itemStack);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        if (event.isFlying()) return;
        plugin.getSessions().of(event.getPlayer()).getFlying().onToggleOff();
    }

    @EventHandler(priority = EventPriority.HIGH)
    void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        for (SetBonus setBonus : plugin.sessions.of(player).getEquipment().getSetBonuses()) {
            setBonus.onPlayerItemConsume(event, player, item);
        }
    }
}
