package com.cavetale.mytems;

import com.cavetale.mytems.util.Json;
import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import java.util.Set;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
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
    ItemStack createItemStack();

    /**
     * Create a fresh copy for the player.
     */
    default ItemStack createItemStack(Player player) {
        return createItemStack();
    }

    Component getDisplayName();

    default void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) { }

    default void onPlayerInteractEntity(PlayerInteractEntityEvent event, Player player, ItemStack item) { }

    default void onPlayerFallDamage(EntityDamageEvent event, Player player, ItemStack item) { }

    default void onPlayerShootBow(EntityShootBowEvent event, Player player, ItemStack item) { }

    default void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) { }

    default void onBlockBreak(BlockBreakEvent event, Player player, ItemStack item) { }

    default void onToggleGlide(EntityToggleGlideEvent event, Player player, ItemStack item) { }

    default void onConsume(PlayerItemConsumeEvent event, Player player, ItemStack item) { }

    default void onPlayerDrop(PlayerDropItemEvent event, Player player, ItemStack item) { }

    default void onEntityPickup(EntityPickupItemEvent event, ItemStack item) { }

    default void onInventoryPickup(InventoryPickupItemEvent event, ItemStack item) { }

    default void onDamageEntity(EntityDamageByEntityEvent event, Player player, ItemStack item) { }

    default void onPlayerArmorRemove(PlayerArmorChangeEvent event, Player player, ItemStack item) { }

    default void onPlayerArmorEquip(PlayerArmorChangeEvent event, Player player, ItemStack item) { }

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
        return tag.isEmpty() ? null : Json.serialize(tag);
    }

    /**
     * Convenience function without a Player argument. It is
     * recommended to override the other one, or both.
     */
    default ItemStack deserializeTag(String serialized) {
        return deserializeTag(serialized, (Player) null);
    }

    /**
     * Deserialize an item tag with a new owner.
     */
    default ItemStack deserializeTag(String serialized, Player player) {
        ItemStack itemStack = createItemStack();
        MytemTag tag = Json.deserialize(serialized, MytemTag.class);
        Set<MytemPersistenceFlag> flags = getMytemPersistenceFlags();
        if (player != null && flags.contains(MytemPersistenceFlag.OWNER)) {
            tag.setOwner(MytemOwner.ofPlayer(player));
        }
        tag.store(itemStack, flags);
        return itemStack;
    }
}
