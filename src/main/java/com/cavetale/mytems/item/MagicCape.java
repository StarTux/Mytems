package com.cavetale.mytems.item;

import com.cavetale.core.event.player.PluginPlayerEvent;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.session.Session;
import com.cavetale.mytems.util.Text;
import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import java.awt.Color;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

@RequiredArgsConstructor
public final class MagicCape implements Mytem {
    @Getter private final Mytems key;
    public static final float FLY_SPEED = 0.025f;
    public static final int COOLDOWN_SECONDS = 20;
    public static final int MAX_DISTANCE = 32;
    @Getter private Component displayName;
    private final String description = ""
        + ChatColor.AQUA + "Woven with the rare membrane dropped by a phantom boss."
        + "\n\n"
        + ChatColor.AQUA + "USE"
        + ChatColor.GRAY + " Float like a butterfly. Start gliding with this cape equipped and you will fly instead."
        + "\n\n"
        + ChatColor.AQUA + "Distance " + ChatColor.GRAY + MAX_DISTANCE + " blocks";
    private ItemStack prototype;

    protected static Component rainbowify(String in) {
        Component component = Component.empty().decoration(TextDecoration.ITALIC, false);
        int len = in.length();
        for (int i = 0; i < len; i += 1) {
            float pos = (float) i / (float) len;
            int rgb = 0xFFFFFF & Color.HSBtoRGB(pos, 0.66f, 1.0f);
            component = component.append(Component.text(in.substring(i, i + 1)).color(TextColor.color(rgb)));
        }
        return component;
    }

    @Override
    public void enable() {
        displayName = rainbowify("Magic Cape");
        prototype = new ItemStack(Material.ELYTRA).ensureServerConversions();
        ItemMeta meta = prototype.getItemMeta();
        if (meta instanceof Repairable) {
            Repairable repairable = (Repairable) meta;
            repairable.setRepairCost(9999);
        }
        meta.setUnbreakable(true);
        meta.displayName(displayName);
        meta.lore(Text.wrapLore(description));
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    private static void fail(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.5f, 2.0f);
    }

    @Override
    public void onToggleGlide(EntityToggleGlideEvent event, Player player, ItemStack item) {
        switch (player.getGameMode()) {
        case CREATIVE: case SPECTATOR: return;
        default: break;
        }
        if (event.isCancelled()) return;
        if (!event.isGliding()) return;
        event.setCancelled(true);
        Session session = MytemsPlugin.getInstance().getSessions().of(player);
        if (session.getFlying().isFlying()) return;
        if (!PluginPlayerEvent.Name.START_FLYING.cancellable(MytemsPlugin.getInstance(), player).call()) {
            fail(player);
            return;
        }
        long cooldown = session.getCooldownInTicks(key.id);
        if (cooldown > 0) {
            long seconds = (cooldown - 1L) / 20L + 1;
            player.sendActionBar(rainbowify("Cooldown " + seconds + "s"));
            fail(player);
            return;
        }
        session.setCooldown(key.id, COOLDOWN_SECONDS * 20);
        session.getFlying().setFlying(player, key, FLY_SPEED, this::onTickFlight, this::onEndFlight);
        Location location = player.getLocation();
        session.getFavorites().set(new MagicCapeFlight(location.getWorld().getName(),
                                                       location.getBlockX(), location.getBlockY(), location.getBlockZ()));
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PHANTOM_FLAP, SoundCategory.PLAYERS, 2.0f, 2.0f);
        player.sendActionBar(rainbowify("The Magic Cape lifts you up"));
    }

    private void onTickFlight(Player player) {
        Session session = MytemsPlugin.getInstance().getSessions().of(player);
        MagicCapeFlight flight = session.getFavorites().get(MagicCapeFlight.class);
        if (flight == null) {
            session.getFlying().stopFlying(player);
            onEndFlight(player);
            fail(player);
            return;
        }
        Location location = player.getLocation();
        if (!location.getWorld().getName().equals(flight.world)
            || Math.abs(location.getBlockX() - flight.x) > MAX_DISTANCE
            || Math.abs(location.getBlockY() - flight.y) > MAX_DISTANCE
            || Math.abs(location.getBlockZ() - flight.z) > MAX_DISTANCE) {
            session.getFlying().stopFlying(player);
            onEndFlight(player);
            fail(player);
            player.sendActionBar(rainbowify("You flew too far!"));
            return;
        }
        if ((session.getFlying().getFlyTime() % 5) == 0) {
            player.getWorld().spawnParticle(Particle.END_ROD, player.getLocation(), 1, 0.0, 0.0, 0.0, 0.1);
        }
    }

    private void onEndFlight(Player player) {
        Session session = MytemsPlugin.getInstance().getSessions().of(player);
        session.setCooldown(key.id, COOLDOWN_SECONDS * 20);
        session.getFavorites().clear(MagicCapeFlight.class);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PHANTOM_FLAP, SoundCategory.PLAYERS, 2.0f, 1.0f);
        return;
    }

    @Override
    public void onPlayerArmorEquip(PlayerArmorChangeEvent event, Player player, ItemStack item) {
        if (player.isGliding()) player.setGliding(false);
    }

    @Override
    public void onPlayerArmorRemove(PlayerArmorChangeEvent event, Player player, ItemStack item) {
        Session session = MytemsPlugin.getInstance().getSessions().of(player);
        if (session.getFlying().isFlying(key)) {
            session.getFlying().stopFlying(player);
            onEndFlight(player);
            fail(player);
        }
    }

    @RequiredArgsConstructor
    private static final class MagicCapeFlight {
        protected final String world;
        protected final int x;
        protected final int y;
        protected final int z;
    }
}
