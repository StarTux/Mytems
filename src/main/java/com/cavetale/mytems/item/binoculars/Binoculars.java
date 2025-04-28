package com.cavetale.mytems.item.binoculars;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.session.Session;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.MytemsPlugin.mytemsPlugin;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
@RequiredArgsConstructor
public final class Binoculars implements Mytem, Listener {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private static final int MAX_DISTANCE = 64;

    @Override
    public void enable() {
        displayName = text("Binoculars", LIGHT_PURPLE);
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                tooltip(meta, List.of(displayName,
                                      textOfChildren(Mytems.MOUSE_RIGHT, text("Enhance view distance", GRAY))));
                key.markItemMeta(meta);
            });
        Bukkit.getPluginManager().registerEvents(this, mytemsPlugin());
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (player.getViewDistance() == 32) return;
        player.setViewDistance(32);
        final Session session = Session.of(player);
        final BinocularsFavorite fav = session.getFavorites().getOrSet(BinocularsFavorite.class, BinocularsFavorite::new);
        fav.setWorldName(player.getWorld().getName());
        final Location location = player.getLocation();
        fav.setX(location.getBlockX());
        fav.setZ(location.getBlockZ());
        player.sendActionBar(textOfChildren(key, text("View distance maxed out", LIGHT_PURPLE)));
        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, SoundCategory.MASTER, 1f, 1.5f);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final Session session = Session.of(player);
        final BinocularsFavorite fav = session.getFavorites().get(BinocularsFavorite.class);
        if (fav == null) return;
        if (!player.getWorld().getName().equals(fav.getWorldName())) {
            session.getFavorites().removeInstance(fav);
            return;
        }
        final Location location = event.getTo();
        if (Math.abs(location.getBlockX() - fav.getX()) > MAX_DISTANCE || Math.abs(location.getBlockZ() - fav.getZ()) > MAX_DISTANCE) {
            session.getFavorites().removeInstance(fav);
            player.setViewDistance(player.getWorld().getViewDistance());
            player.sendActionBar(textOfChildren(key, text("View distance reset", DARK_PURPLE)));
            player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, SoundCategory.MASTER, 1f, 1.5f);
        }
    }
}
