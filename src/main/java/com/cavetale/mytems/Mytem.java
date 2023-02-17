package com.cavetale.mytems;

import com.cavetale.mytems.event.combat.DamageCalculationEvent;
import com.cavetale.mytems.util.Json;
import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
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
    @Deprecated
    default ItemStack createItemStack(Player player) {
        return createItemStack();
    }

    Component getDisplayName();

    default Component getDisplayName(ItemStack itemStack) {
        return getDisplayName();
    }

    default void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) { }

    default void onPlayerLeftClick(PlayerInteractEvent event, Player player, ItemStack item) { }

    default void onPlayerInteractEntity(PlayerInteractEntityEvent event, Player player, ItemStack item) { }

    default void onPlayerDamage(EntityDamageEvent event, Player player, ItemStack item, EquipmentSlot slot) { }

    default void onPlayerShootBow(EntityShootBowEvent event, Player player, ItemStack item) { }

    default void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) { }

    default void onBlockBreak(BlockBreakEvent event, Player player, ItemStack item) { }

    default void onBlockDamage(BlockDamageEvent event, Player player, ItemStack item) { }

    default void onBucketFill(PlayerBucketFillEvent event, Player player, ItemStack item) { }

    default void onToggleGlide(EntityToggleGlideEvent event, Player player, ItemStack item) { }

    default void onConsume(PlayerItemConsumeEvent event, Player player, ItemStack item) { }

    default void onPlayerDrop(PlayerDropItemEvent event, Player player, ItemStack item) { }

    default void onEntityPickup(EntityPickupItemEvent event, ItemStack item) { }

    default void onInventoryPickup(InventoryPickupItemEvent event, ItemStack item) { }

    default void onDamageEntity(EntityDamageByEntityEvent event, Player player, ItemStack item) { }

    default void onPrePlayerAttackEntity(PrePlayerAttackEntityEvent event, Player player, ItemStack item) { }

    default void onPlayerArmorRemove(PlayerArmorChangeEvent event, Player player, ItemStack item) { }

    default void onPlayerArmorEquip(PlayerArmorChangeEvent event, Player player, ItemStack item) { }

    default void onPlayerAttemptPickupItem(PlayerAttemptPickupItemEvent event) { }

    default void onDefendingDamageCalculation(DamageCalculationEvent event, ItemStack item, EquipmentSlot slot) { }

    default void onAttackingDamageCalculation(DamageCalculationEvent event, ItemStack item, EquipmentSlot slot) { }

    default void onPlayerItemDamage(PlayerItemDamageEvent event) {
        event.setCancelled(true);
    }

    default void onSwapHandItems(PlayerSwapHandItemsEvent event, Player player, ItemStack item, EquipmentSlot slot) { }

    /**
     * These can be overridden entirely.
     */
    default MytemTag serializeTag(ItemStack itemStack) {
        if (itemStack.getAmount() == 1) return null;
        MytemTag tag = new MytemTag();
        tag.load(itemStack);
        return tag;
    }

    /**
     * Convenience function without a Player argument. It is
     * recommended to override the other one, or both.
     */
    default ItemStack deserializeTag(String serialized) {
        ItemStack itemStack = createItemStack();
        MytemTag tag = Json.deserialize(serialized, MytemTag.class);
        if (tag != null && !tag.isEmpty()) {
            tag.store(itemStack);
        }
        return itemStack;
    }

    default int getMaxStackSize() {
        return getKey().material.getMaxStackSize();
    }

    /**
     * Return whether this item is expected to end up in player's hands.
     */
    default boolean isAvailableToPlayers() {
        return true;
    }

    /**
     * Return whether this item can be placed in Mass Storage.
     * Typically, an item does not belong in MS if they regularly come
     * with extra data which are not dismissable.
     */
    default boolean isMassStorable() {
        return true;
    }
}
