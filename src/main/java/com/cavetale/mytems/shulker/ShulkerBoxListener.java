package com.cavetale.mytems.shulker;

import com.cavetale.core.event.item.PlayerReceiveItemsEvent;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Gui;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Tag;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
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
     * A shulker box in hand is opened when right clicking in your
     * inventory.
     *
     * Opening a GUI after right clicking your inventory must happen
     * on the next tick, or else a ghost item will appear on the
     * cursor next time they open the inventory.
     */
    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOW)
    protected void onInventoryClick(InventoryClickEvent event) {
        if (!event.isRightClick()) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        if (!player.hasPermission("mytems.openshulker.click")) {
            return;
        }
        // Player inventory
        // In creative mode, this will not work because inventory
        // click events are not fired inside the creative inventory.
        if (event.getView().getType() != InventoryType.CRAFTING) {
            return;
        }
        // Empty cursor
        if (event.getCursor() != null && !event.getCursor().isEmpty()) {
            return;
        }
        // Disallow in spectator mode.  This would otherwise work!
        if (player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        final int clickedSlot = event.getSlot();
        if (clickedSlot < 0) {
            return;
        }
        final ItemStack clickedItem = player.getInventory().getItem(clickedSlot);
        if (clickedItem == null || !Tag.SHULKER_BOXES.isTagged(clickedItem.getType())) {
            return;
        }
        // No return
        event.setCancelled(true);
        player.closeInventory();
        openShulkerBox(player, clickedSlot, clickedItem);
    }

    protected void openShulkerBox(Player player, final int shulkerBoxSlot, ItemStack shulkerBoxItem) {
        final Gui gui = new Gui();
        player.getInventory().setItem(shulkerBoxSlot, null);
        final BlockStateMeta shulkerMeta = (BlockStateMeta) shulkerBoxItem.getItemMeta();
        final ShulkerBox shulkerBox = (ShulkerBox) shulkerMeta.getBlockState();
        final Inventory shulkerInventory = shulkerBox.getInventory();
        gui.onClose(closeEvent -> {
                player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_CLOSE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                shulkerMeta.setBlockState(shulkerBox);
                shulkerBoxItem.setItemMeta(shulkerMeta);
                ItemStack slotItem = player.getInventory().getItem(shulkerBoxSlot);
                if (slotItem == null || slotItem.getType().isAir()) {
                    player.getInventory().setItem(shulkerBoxSlot, shulkerBoxItem);
                    return;
                } else {
                    PlayerReceiveItemsEvent evt = new PlayerReceiveItemsEvent(player, List.of(shulkerBoxItem));
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
                player.sendActionBar(text("Shulker Box Closed"));
            });
        gui.setEditable(true);
        Bukkit.getScheduler().runTask(plugin, () -> gui.open(player, shulkerInventory));
        player.sendActionBar(text("Shulker Box Opened"));
        player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }
}
