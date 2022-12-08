package com.cavetale.mytems.shulker;

import com.cavetale.core.event.item.PlayerReceiveItemsEvent;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Gui;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Tag;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.RED;

@RequiredArgsConstructor
public final class ShulkerBoxListener implements Listener {
    private final MytemsPlugin plugin;

    public void enable() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    /**
     * A shulker box in hand is opened when right clicking under the
     * following conditions.
     * - Player not in spectator mode
     * - Not sneaking
     * - Not facing interactable block
     * - Uses main hand
     */
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
    protected void onPlayerClickShulkerBox(PlayerInteractEvent event) {
        switch (event.getAction()) {
        case RIGHT_CLICK_BLOCK:
        case RIGHT_CLICK_AIR: break;
        default: return;
        }
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!event.hasItem()) return;
        Player player = event.getPlayer();
        if (player.isSneaking()) return;
        if (player.getGameMode() == GameMode.SPECTATOR) return;
        if (event.hasBlock() && event.getClickedBlock().getType().isInteractable()) return;
        final int heldItemSlot = player.getInventory().getHeldItemSlot();
        final ItemStack itemStack = player.getInventory().getItem(heldItemSlot);
        if (itemStack == null) return;
        final Material itemType = itemStack.getType();
        if (!Tag.SHULKER_BOXES.isTagged(itemType)) return;
        if (!(itemStack.getItemMeta() instanceof BlockStateMeta shulkerMeta)) return;
        if (!(shulkerMeta.getBlockState() instanceof ShulkerBox shulkerBox)) return;
        event.setCancelled(true);
        if (player.getOpenInventory().getType() != InventoryType.CRAFTING) return;
        player.closeInventory();
        final Inventory shulkerInventory = shulkerBox.getInventory();
        final Gui gui = new Gui();
        player.getInventory().setItem(heldItemSlot, null);
        gui.onClose(closeEvent -> {
                player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_CLOSE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                shulkerMeta.setBlockState(shulkerBox);
                itemStack.setItemMeta(shulkerMeta);
                ItemStack slotItem = player.getInventory().getItem(heldItemSlot);
                if (slotItem == null || slotItem.getType().isAir()) {
                    player.getInventory().setItem(heldItemSlot, itemStack);
                    return;
                } else {
                    PlayerReceiveItemsEvent evt = new PlayerReceiveItemsEvent(player, List.of(itemStack));
                    evt.giveItems();
                    boolean drop = false;
                    if (!evt.isEmpty()) {
                        evt.callEvent();
                        if (!evt.isEmpty()) {
                            evt.dropItems();
                            drop = true;
                        }
                    }
                    if (drop) {
                        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, SoundCategory.MASTER, 1.0f, 0.5f);
                        player.sendMessage(text("Your inventory was full so your shulker box was dropped!", RED));
                    } else {
                        player.sendMessage(text("Your shulker slot was blocked so the box moved to a different slot!", RED));
                        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, SoundCategory.MASTER, 0.2f, 0.9f);
                    }
                }
            });
        gui.setEditable(true);
        gui.open(player, shulkerInventory);
        player.sendActionBar(text("Sneak to Place Shulker Box"));
        player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }
}
