package com.cavetale.mytems.item.captain;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.session.Session;
import com.cavetale.mytems.util.Text;
import com.cavetale.worldmarker.util.Tags;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

@RequiredArgsConstructor @Getter
public final class Blunderbuss implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private final String description = ""
        + "Use the mighty blunderbuss t' hit yer enemies from afar.";
    private final TextColor titleColor = TextColor.color(0xddd417);
    private final TextColor loreColor = TextColor.color(0x760a0a);
    @Getter private static NamespacedKey singleUseKey;

    @Override
    public void enable() {
        displayName = Component.text("Blunderbuss", titleColor);
        prototype = new ItemStack(key.material);
        ItemMeta meta = prototype.getItemMeta();
        meta.displayName(displayName.decoration(TextDecoration.ITALIC, false));
        meta.lore(Text.wrapLore(description, c -> {
                    return c.color(loreColor).decoration(TextDecoration.ITALIC, false);
                }));
        meta.addItemFlags(ItemFlag.values());
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
        singleUseKey = new NamespacedKey(MytemsPlugin.getInstance(), "single_use");
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
        boolean result = pullTheTrigger(player);
        if (result && Tags.getInt(item.getItemMeta().getPersistentDataContainer(), singleUseKey) != null) {
            item.subtract(1);
        }
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, Player player, ItemStack item) {
        if (event.getRightClicked().getType() == EntityType.IRON_GOLEM) {
            event.setCancelled(true);
        }
    }

    public boolean pullTheTrigger(Player player) {
        Session session = MytemsPlugin.getInstance().getSessions().of(player);
        long cooldown = session.getCooldownInTicks(key.id);
        if (cooldown > 0) {
            long seconds = (cooldown - 1L) / 20L + 1;
            player.sendActionBar(Component.text("Cooldown " + seconds + "s", NamedTextColor.DARK_RED));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.5f, 2.0f);
            return false;
        }
        session.setCooldown(key.id, 60);
        Location playerLocation = player.getLocation();
        Location eyeLocation = player.getEyeLocation();
        RayTraceResult rayTraceResult;
        Vector gunDirection = playerLocation.getDirection();
        rayTraceResult = player.getWorld().rayTrace(eyeLocation, gunDirection,
                                                    64.0, // maxDistance
                                                    FluidCollisionMode.NEVER,
                                                    true, // ignorePassableBlocks
                                                    0.0, // raySize
                                                    e -> {
                                                        if (e.equals(player)) return false;
                                                        if (!(e instanceof Player)) return false;
                                                        Player p = (Player) e;
                                                        if (p.getGameMode() == GameMode.SPECTATOR) return false;
                                                        if (p.getGameMode() == GameMode.CREATIVE) return false;
                                                        return true;
                                                    });
        playerLocation.getWorld().playSound(playerLocation,
                                            Sound.ENTITY_GENERIC_EXPLODE,
                                            SoundCategory.PLAYERS,
                                            1.0f, 2.0f);
        Location smokeLocation = eyeLocation.add(gunDirection.normalize());
        smokeLocation.getWorld().spawnParticle(Particle.SMOKE_NORMAL,
                                               smokeLocation,
                                               16, // count
                                               0.25, 0.25, 0.25, // offset
                                               0.01); // speed
        if (rayTraceResult == null) return true;
        Entity entity = rayTraceResult.getHitEntity();
        if (entity instanceof Player) { // null check
            boostHitEntity(entity, gunDirection);
        }
        return true;
    }

    public boolean boostHitEntity(Entity target, Vector direction) {
        Entity vehicle = target.getVehicle();
        if (vehicle != null) {
            if (vehicle.getType() == EntityType.ARMOR_STAND) return false;
            vehicle.removePassenger(target);
        }
        target.setVelocity(target.getVelocity().add(direction.normalize().multiply(3.0)));
        target.getWorld().spawnParticle(Particle.BLOCK_DUST, target.getLocation(), 24, .25, .25, .25, 0,
                                        Material.OAK_PLANKS.createBlockData());
        return true;
    }
}
