package com.cavetale.mytems;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;
import static com.cavetale.mytems.MytemsPlugin.plugin;

/**
 * Deny trading of Mytems using merchant recipes that ask for the
 * underlying vanilla item.
 */
public final class TradeListener implements Listener {
    public void enable() {
        Bukkit.getPluginManager().registerEvents(this, plugin());
    }

    @EventHandler(ignoreCancelled = true)
    private void onInventoryClick(InventoryClickEvent event) {
        if (event.getSlotType() != SlotType.RESULT) return;
        if (!(event.getInventory() instanceof MerchantInventory inventory)) return;
        final MerchantRecipe recipe = inventory.getSelectedRecipe();
        if (recipe == null) return;
        final List<ItemStack> ingredients = recipe.getIngredients();
        for (int i = 0; i < ingredients.size(); i += 1) {
            final ItemStack ingredient = ingredients.get(i);
            if (ingredient == null) continue;
            final ItemStack input = inventory.getItem(i);
            if (input == null) continue;
            if (Mytems.forItem(input) != Mytems.forItem(ingredient)) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
