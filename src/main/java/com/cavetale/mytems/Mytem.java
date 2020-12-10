package com.cavetale.mytems;

import java.util.Collections;
import java.util.Set;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface Mytem {
    Mytems getKey();

    String getId();

    void enable();

    /**
     * Create a fresh copy.
     */
    ItemStack getItem();

    BaseComponent[] getDisplayName();

    default void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) { }

    default void onPlayerFallDamage(EntityDamageEvent event, Player player, ItemStack item) { }

    default void onPlayerShootBow(EntityShootBowEvent event, Player player, ItemStack item) { }

    default void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) { }

    default void onToggleGlide(EntityToggleGlideEvent event, Player player, ItemStack item) { }

    default boolean shouldAutoFix() {
        return false;
    }

    default Set<ItemFixFlag> getItemFixFlags() {
        return Collections.emptySet();
    }
}
