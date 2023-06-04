package com.cavetale.mytems.item.captain;

import com.cavetale.core.connect.NetworkServer;
import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.entity.PlayerEntityAbilityQuery;
import com.cavetale.core.event.player.PluginPlayerEvent;
import com.cavetale.core.text.LineWrap;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.session.Session;
import com.cavetale.mytems.util.Items;
import com.cavetale.worldmarker.util.Tags;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import static com.cavetale.core.font.Unicode.tiny;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@RequiredArgsConstructor @Getter
public final class Blunderbuss implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private final String description = "Use the mighty watergun t' hit yer enemies from afar.";
    private static final Duration COOLDOWN = Duration.ofSeconds(5);
    @Getter private static NamespacedKey singleUseKey;
    private static final int COLOR = 0x6060FF;

    @Override
    public void enable() {
        displayName = text("Pirate Watergun", color(COLOR));
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                List<Component> txt = new ArrayList<>();
                txt.add(displayName);
                txt.addAll(new LineWrap()
                           .componentMaker(str -> text(str, color(COLOR)))
                           .wrap(tiny(description)));
                txt.add(empty());
                txt.add(textOfChildren(Mytems.MOUSE_LEFT, text(" Pull Trigger", GRAY)));
                Items.text(meta, txt);
                meta.addItemFlags(ItemFlag.values());
                key.markItemMeta(meta);
            });
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
    public void onPlayerLeftClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
        boolean result = pullTheTrigger(player);
        if (result && Tags.getInt(item.getItemMeta().getPersistentDataContainer(), singleUseKey) != null) {
            item.subtract(1);
        }
    }

    @Override
    public void onPrePlayerAttackEntity(PrePlayerAttackEntityEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
        boolean result = pullTheTrigger(player);
        if (result && Tags.getInt(item.getItemMeta().getPersistentDataContainer(), singleUseKey) != null) {
            item.subtract(1);
        }
    }

    /**
     * Pull the trigger on the gun in hand.
     * @return true if the blunderbuss was fired, false otherwise.
     */
    public boolean pullTheTrigger(Player player) {
        Session session = MytemsPlugin.getInstance().getSessions().of(player);
        long cooldown = session.getCooldown(key).toSeconds();
        if (cooldown > 0) {
            player.sendActionBar(text("Cooldown " + cooldown + "s", DARK_RED));
            player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1.0f, 0.5f);
            return false;
        }
        session.cooldown(key).duration(COOLDOWN);
        final Location playerLocation = player.getLocation();
        final Location eyeLocation = player.getEyeLocation();
        final RayTraceResult rayTraceResult;
        final Vector gunDirection = playerLocation.getDirection();
        final World world = player.getWorld();
        final double maxDistance = 64.0;
        final boolean ignorePassableBlocks = true;
        final double raySize = 0.125;
        rayTraceResult = world.rayTrace(eyeLocation, gunDirection, maxDistance, FluidCollisionMode.NEVER, ignorePassableBlocks, raySize, e -> {
                if (e.equals(player)) return false;
                if (e instanceof Player p) {
                    if (p.getGameMode() == GameMode.SPECTATOR) return false;
                    if (p.getGameMode() == GameMode.CREATIVE) return false;
                }
                return true;
            });
        playerLocation.getWorld().playSound(playerLocation, Sound.BLOCK_BREWING_STAND_BREW, SoundCategory.PLAYERS, 1.0f, 2.0f);
        playerLocation.getWorld().playSound(playerLocation, Sound.ITEM_BUCKET_FILL, SoundCategory.PLAYERS, 1.0f, 2.0f);
        final Vector smokeStart = eyeLocation.toVector().add(gunDirection.clone().multiply(0.5));
        final Vector smokeEnd = rayTraceResult != null
            ? rayTraceResult.getHitPosition()
            : eyeLocation.toVector().add(gunDirection.clone().multiply(10));
        final double smokeLength = smokeEnd.clone().subtract(smokeStart).length();
        for (double i = 0; i < smokeLength; i += 0.25) {
            Vector smokeVector = smokeStart.clone().multiply(i)
                .add(smokeEnd.clone().multiply(smokeLength - i))
                .multiply(1.0 / smokeLength);
            world.spawnParticle(Particle.WATER_DROP, smokeVector.toLocation(world), 1, 0.0, 0.0, 0.0, 1.0);
        }
        world.spawnParticle(Particle.WATER_WAKE, smokeStart.toLocation(world), 16, 0.0, 0.0, 0.0, 0.15);
        if (rayTraceResult == null) return true;
        Entity targetEntity = rayTraceResult.getHitEntity();
        if (targetEntity == null) return false;
        return boostHitEntity(player, targetEntity, gunDirection);
    }

    /**
     * Attempt to boost the target entity.
     * @return true if entity was boosted, false otherwise.
     */
    public boolean boostHitEntity(Player shooter, Entity target, Vector direction) {
        if (target instanceof Player player) {
            if (!NetworkServer.OVERBOARD.isThisServer()) {
                ItemStack hand = player.getEquipment().getItemInMainHand();
                if (hand == null || !key.isItem(hand)) return false;
            }
            // Temporary solution
            if (!PlayerBlockAbilityQuery.Action.FLY.query(player, player.getLocation().getBlock())) {
                return false;
            }
            PluginPlayerEvent.Name.START_FLYING.call(MytemsPlugin.getInstance(), player);
        } else if (!PlayerEntityAbilityQuery.Action.MOVE.query(shooter, target)) {
            return false;
        }
        Entity vehicle = target.getVehicle();
        if (vehicle != null) {
            if (vehicle.getType() == EntityType.ARMOR_STAND) return false;
            vehicle.removePassenger(target);
        }
        target.setVelocity(target.getVelocity().add(direction.normalize().multiply(3.0)));
        target.getWorld().spawnParticle(Particle.WATER_WAKE, target.getLocation(), 32, 0.1, 0.1, 0.1, 0.15);
        target.getWorld().playSound(target.getLocation(), Sound.ENTITY_GENERIC_SPLASH, SoundCategory.PLAYERS, 1.0f, 2.0f);
        return true;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
