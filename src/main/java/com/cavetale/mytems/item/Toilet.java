package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Text;
import com.cavetale.worldmarker.block.BlockMarker;
import com.cavetale.worldmarker.entity.EntityMarker;
import com.winthier.generic_events.GenericEvents;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

@RequiredArgsConstructor
public final class Toilet implements Mytem, Listener {
    @Getter private final Mytems key;
    @Getter private BaseComponent[] displayName;
    private ItemStack emptyPrototype;
    private ItemStack prototype;
    Map<Block, Seat> blockMap = new HashMap<>();
    Map<UUID, Seat> uuidMap = new HashMap<>();
    Random random = new Random();

    @RequiredArgsConstructor
    static final class Seat {
        final Block block;
        final ArmorStand armorStand;
    }

    @Override
    public void enable() {
        displayName = Text.builder("Toilet").color(ChatColor.of("#A0A0A0")).italic(false).create();
        prototype = new ItemStack(key.material);
        ItemMeta meta = prototype.getItemMeta();
        meta.setDisplayNameComponent(displayName);
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
        emptyPrototype = new ItemStack(key.material);
        key.markItemStack(emptyPrototype);
        Bukkit.getPluginManager().registerEvents(this, MytemsPlugin.getInstance());
    }

    @Override
    public void disable() {
        for (Seat seat : new ArrayList<>(blockMap.values())) {
            disableSeat(seat);
        }
    }

    @Override
    public ItemStack getItem() {
        return prototype.clone();
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack itemStack) {
        if (event.isCancelled()) return;
        event.setCancelled(true);
        final BlockFace facing;
        Block block = event.getBlock();
        if (!GenericEvents.playerCanBuild(player, block)) return;
        Block against = event.getBlockAgainst();
        int dx = block.getX() - against.getX();
        int dz = block.getZ() - against.getZ();
        if (dx != 0) {
            facing = dx > 0 ? BlockFace.EAST : BlockFace.WEST;
        } else if (dz != 0) {
            facing = dz > 0 ? BlockFace.SOUTH : BlockFace.NORTH;
        } else {
            Vector dir = player.getLocation().getDirection();
            if (Math.abs(dir.getX()) > Math.abs(dir.getZ())) {
                facing = dir.getX() > 0 ? BlockFace.WEST : BlockFace.EAST;
            } else {
                facing = dir.getZ() > 0 ? BlockFace.NORTH : BlockFace.SOUTH;
            }
        }
        ItemFrame itemFrame;
        Location location = block.getLocation().add(0.5, 0.5, 0.5);
        try {
            itemFrame = location.getWorld().spawn(location, ItemFrame.class, e -> {
                    e.setFixed(true);
                    e.setFacingDirection(facing);
                    e.setPersistent(true);
                    e.setVisible(false);
                    e.setItem(emptyPrototype.clone());
                    e.setItemDropChance(0.0f);
                    e.setFixed(true);
                });
        } catch (IllegalArgumentException iae) {
            return;
        }
        EntityMarker.setId(itemFrame, key.id);
        Bukkit.getScheduler().runTask(MytemsPlugin.getInstance(), () -> {
                block.setType(Material.BARRIER);
                BlockMarker.setId(block, key.id);
            });
        itemStack.subtract();
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        switch (event.getAction()) {
        case RIGHT_CLICK_BLOCK: break;
        default: return;
        }
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!event.hasBlock()) return;
        Block block = event.getClickedBlock();
        if (!block.getRelative(0, 1, 0).isPassable()) return;
        Player player = event.getPlayer();
        if (player.isSneaking()) return;
        if (player.getGameMode() == GameMode.SPECTATOR) return;
        if (player.getVehicle() != null) return;
        if (!player.isOnGround()) return;
        if (isOccupied(block)) return;
        if (!BlockMarker.hasId(block, key.id)) return;
        Location loc = block.getLocation().add(0.5, 0.5, 0.5);
        BlockFace face = null;
        for (ItemFrame itemFrame : loc.getWorld().getNearbyEntitiesByType(ItemFrame.class, loc, 0.5)) {
            face = itemFrame.getFacing();
            break;
        }
        if (face == null) return;
        event.setCancelled(true);
        Vector dir = face.getDirection();
        loc = loc.setDirection(face.getDirection());
        loc = loc.add(dir.normalize().multiply(0.1));
        loc = loc.add(0, -0.3, 0);
        ArmorStand armorStand = loc.getWorld().spawn(loc, ArmorStand.class, as -> {
                as.setPersistent(false);
                as.setVisible(false);
                as.setMarker(true);
            });
        if (armorStand == null) return;
        player.teleport(loc);
        armorStand.addPassenger(player);
        Seat seat = new Seat(block, armorStand);
        enableSeat(seat);
        if (random.nextBoolean()) {
            loc.getWorld().playSound(loc, Sound.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 1.0f, 0.5f);
        } else {
            loc.getWorld().playSound(loc, Sound.ENTITY_FISH_SWIM, SoundCategory.PLAYERS, 1.0f, 0.9f);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockDamage(BlockDamageEvent event) {
        Block block = event.getBlock();
        if (!BlockMarker.hasId(block, key.id)) return;
        Player player = event.getPlayer();
        if (!GenericEvents.playerCanBuild(player, block)) return;
        event.setCancelled(true);
        disableSeat(block);
        BlockMarker.resetId(block);
        Location loc = block.getLocation().add(0.5, 0.5, 0.5);
        ItemFrame itemFrame = null;
        for (ItemFrame it : loc.getWorld().getNearbyEntitiesByType(ItemFrame.class, loc, 0.5)) {
            itemFrame = it;
            it.remove();
        }
        if (block.getType() == Material.BARRIER) {
            block.setType(Material.AIR);
        }
        if (itemFrame != null) {
            loc.getWorld().dropItem(loc, getItem());
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (!BlockMarker.hasId(block, key.id)) return;
        BlockMarker.resetId(block);
        disableSeat(block);
        Player player = event.getPlayer();
        Location loc = block.getLocation().add(0.5, 0.5, 0.5);
        for (ItemFrame it : loc.getWorld().getNearbyEntitiesByType(ItemFrame.class, loc, 0.5)) {
            it.remove();
        }
    }

    boolean isOccupied(Block block) {
        Seat seat = blockMap.get(block);
        if (seat == null) return false;
        if (seat.armorStand.isValid() && !seat.armorStand.getPassengers().isEmpty()) {
            return true;
        }
        disableSeat(seat);
        return false;
    }

    void disableSeat(Seat seat) {
        blockMap.remove(seat.block);
        uuidMap.remove(seat.armorStand.getUniqueId());
        seat.armorStand.remove();
    }

    void enableSeat(Seat seat) {
        blockMap.put(seat.block, seat);
        uuidMap.put(seat.armorStand.getUniqueId(), seat);
    }

    void disableSeat(Block block) {
        Seat seat = blockMap.get(block);
        if (seat != null) disableSeat(seat);
    }
}
