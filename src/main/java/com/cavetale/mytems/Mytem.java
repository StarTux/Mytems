package com.cavetale.mytems;

import com.cavetale.mytems.util.Json;
import java.util.Set;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public interface Mytem {
    Mytems getKey();

    void enable();

    default void disable() { }

    /**
     * Create a fresh copy.
     */
    ItemStack getItem();

    default ItemStack createItemStack() {
        return getItem();
    }

    default ItemStack createItemStack(Player player) {
        return getItem();
    }

    BaseComponent[] getDisplayName();

    default void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) { }

    default void onPlayerFallDamage(EntityDamageEvent event, Player player, ItemStack item) { }

    default void onPlayerShootBow(EntityShootBowEvent event, Player player, ItemStack item) { }

    default void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) { }

    default void onToggleGlide(EntityToggleGlideEvent event, Player player, ItemStack item) { }

    default void onConsume(PlayerItemConsumeEvent event, Player player, ItemStack item) { }

    default Set<MytemPersistenceFlag> getMytemPersistenceFlags() {
        return MytemPersistenceFlag.NONE;
    }

    /**
     * These can be overridden entirely. The default version attempts
     * to respect the MytemPersistenceFlags.
     */
    default String serializeTag(ItemStack itemStack) {
        MytemTag tag = new MytemTag();
        tag.load(itemStack, getMytemPersistenceFlags());
        return tag.isEmpty() ? null : Json.simplified(tag);
    }

    /**
     * These can be overridden entirely. The default version attempts
     * to respect the MytemPersistenceFlags.
     */
    default ItemStack deserializeTag(String serialized) {
        ItemStack itemStack = getItem();
        MytemTag tag = Json.deserialize(serialized, MytemTag.class);
        tag.store(itemStack, getMytemPersistenceFlags());
        return itemStack;
    }
}
