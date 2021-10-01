package com.cavetale.mytems.item.medieval;

import com.cavetale.core.event.player.PluginPlayerEvent;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Items;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

@RequiredArgsConstructor @Getter
public final class WitchBroom implements Mytem {
    public static final long MAX_LIFETIME = 1000L * 60L * 5L;
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        displayName = Component.text("Witch Broom", NamedTextColor.LIGHT_PURPLE);
        List<Component> text = List.of(displayName,
                                       Component.text("Right-click", NamedTextColor.GREEN)
                                       .append(Component.text(" to lift off!", NamedTextColor.GRAY)));
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                Items.unbreakable(meta);
                Items.text(meta, text);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, Player player, ItemStack itemStack) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack itemStack) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack itemStack) {
        event.setUseInteractedBlock(Event.Result.DENY);
        event.setUseItemInHand(Event.Result.DENY);
        if (player.getVehicle() != null) return;
        if (player.isGliding() || player.isFlying() || !player.isOnGround()) return;
        final ArmorStand armorStand = player.getWorld().spawn(player.getLocation(), ArmorStand.class, e -> {
                e.setPersistent(false);
                e.setVisible(false);
                e.setSmall(true);
            });
        if (!PluginPlayerEvent.Name.START_FLYING.cancellable(MytemsPlugin.getInstance(), player).call()) {
            armorStand.remove();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, 1.0f, 0.5f);
            return;
        }
        armorStand.addPassenger(player);
        final long then = System.currentTimeMillis();
        new BukkitRunnable() {
            private int noMoveTicks = 0;
            private int x;
            private int y;
            private int z;
            @Override public void run() {
                long now = System.currentTimeMillis();
                Location location = player.getLocation();
                if (x != location.getBlockX() || y != location.getBlockY() || z != location.getBlockZ()) {
                    x = location.getBlockX();
                    y = location.getBlockY();
                    z = location.getBlockZ();
                    noMoveTicks = 0;
                } else {
                    noMoveTicks += 1;
                }
                Vector lookAt = location.getDirection();
                boolean shouldRemove =
                    then + MAX_LIFETIME < now
                    || !player.isOnline()
                    || armorStand.isDead()
                    || player.getVehicle() != armorStand
                    || lookAt.length() < 0.01
                    || noMoveTicks >= 60
                    || y > 400;
                if (shouldRemove) {
                    armorStand.remove();
                    cancel();
                    player.setFallDistance(0);
                    player.sendMessage(Component.text("You exit the Witch Broom", NamedTextColor.GRAY));
                    return;
                }
                armorStand.setVelocity(lookAt.normalize().multiply(0.8));
                player.getWorld().spawnParticle(Particle.WAX_ON, location, 1, 0.0, 0.0, 0.0, 0.0);
            }
        }.runTaskTimer(MytemsPlugin.getInstance(), 0L, 0L);
        player.sendMessage(Component.text("You mount the Witch Broom!", NamedTextColor.GRAY));
        player.playSound(player.getLocation(), Sound.ENTITY_PIG_SADDLE, SoundCategory.MASTER, 1.0f, 1.0f);
    }
}
