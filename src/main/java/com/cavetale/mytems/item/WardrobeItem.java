package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsCategory;
import com.cavetale.mytems.util.Text;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@RequiredArgsConstructor @Getter
public final class WardrobeItem implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        displayName = Component.text(Text.toCamelCase(key, " ")).decoration(TextDecoration.ITALIC, false);
        prototype = new ItemStack(key.material);
        ItemMeta meta = prototype.getItemMeta();
        meta.displayName(displayName);
        meta.addItemFlags(ItemFlag.values());
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (player.isOp()) return;
        event.setCancelled(true);
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, Player player, ItemStack item) {
        if (player.isOp()) return;
        event.setCancelled(true);
    }

    @Override
    public void onConsume(PlayerItemConsumeEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerDrop(PlayerDropItemEvent event, Player player, ItemStack item) {
        if (player.isOp()) return;
        event.getItemDrop().remove();
    }

    @Override
    public void onEntityPickup(EntityPickupItemEvent event, ItemStack item) {
        event.setCancelled(true);
        event.getItem().remove();
    }

    @Override
    public void onInventoryPickup(InventoryPickupItemEvent event, ItemStack item) {
        event.setCancelled(true);
        event.getItem().remove();
    }

    @Override
    public void onSwapHandItems(PlayerSwapHandItemsEvent event, Player player, ItemStack item, EquipmentSlot slot) {
        if (key.category == MytemsCategory.WARDROBE_OFFHAND) {
            event.setCancelled(true);
        }
    }

    @Override
    public boolean isMassStorable() {
        return false;
    }
}
