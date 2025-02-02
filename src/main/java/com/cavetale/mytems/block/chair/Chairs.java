package com.cavetale.mytems.block.chair;

import com.cavetale.mytems.util.Entities;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import static com.cavetale.mytems.MytemsPlugin.mytemsPlugin;

public final class Chairs implements Listener {
    private static Chairs instance;
    private Map<Block, Seat> blockMap = new HashMap<>();
    private Map<UUID, Seat> uuidMap = new HashMap<>();

    public static Chairs chairs() {
        if (instance == null) {
            instance = new Chairs();
            Bukkit.getPluginManager().registerEvents(instance, mytemsPlugin());
        }
        return instance;
    }

    public static void disableAll() {
        if (instance == null) return;
        for (Seat seat : List.copyOf(instance.blockMap.values())) {
            instance.disableSeat(seat);
        }
    }

    @RequiredArgsConstructor
    public static final class Seat {
        final UUID uuid;
        final Block block;
        final ArmorStand armorStand;
    }

    public boolean isOccupied(Block block) {
        final Seat seat = blockMap.get(block);
        if (seat == null) return false;
        if (seat.armorStand.isValid() && !seat.armorStand.getPassengers().isEmpty()) {
            return true;
        }
        disableSeat(seat);
        return false;
    }

    public void disableSeat(Seat seat) {
        blockMap.remove(seat.block);
        uuidMap.remove(seat.armorStand.getUniqueId());
        seat.armorStand.remove();
    }

    public void enableSeat(Seat seat) {
        blockMap.put(seat.block, seat);
        uuidMap.put(seat.armorStand.getUniqueId(), seat);
    }

    public void disableSeat(Block block) {
        Seat seat = blockMap.get(block);
        if (seat != null) disableSeat(seat);
    }

    public Seat sitDown(Player player, Block block, Location location) {
        if (isOccupied(block)) return null;
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class, as -> {
                as.setPersistent(false);
                as.setVisible(false);
                as.setMarker(true);
                Entities.setTransient(as);
            });
        if (armorStand == null) return null;
        final Location ploc = player.getLocation();
        ploc.setDirection(location.getDirection());
        player.teleport(ploc);
        if (!armorStand.addPassenger(player)) {
            armorStand.remove();
            return null;
        }
        final Seat seat = new Seat(player.getUniqueId(), block, armorStand);
        enableSeat(seat);
        return seat;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!(player.getVehicle() instanceof ArmorStand)) return;
        ArmorStand armorStand = (ArmorStand) player.getVehicle();
        Seat seat = uuidMap.get(armorStand.getUniqueId());
        if (seat != null) disableSeat(seat);
    }

    @EventHandler
    public void onEntityDismount(EntityDismountEvent event) {
        if (!(event.getDismounted() instanceof ArmorStand armorStand)) return;
        if (!(event.getEntity() instanceof Player player)) return;
        final Seat seat = uuidMap.get(armorStand.getUniqueId());
        if (seat == null) return;
        disableSeat(seat);
        Bukkit.getScheduler().runTask(mytemsPlugin(), () -> {
                final Location ploc = player.getLocation();
                Location loc = seat.block.getLocation().add(0.5, 1.0, 0.5);
                if (!loc.getWorld().equals(ploc.getWorld())) return;
                if (ploc.distance(loc) > 2.0) return;
                loc.setDirection(ploc.getDirection());
                player.teleport(loc);
            });
    }
}
