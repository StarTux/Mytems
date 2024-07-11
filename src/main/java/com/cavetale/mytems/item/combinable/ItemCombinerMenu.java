package com.cavetale.mytems.item.combinable;

import com.cavetale.core.event.item.PlayerReceiveItemsEvent;
import com.cavetale.core.font.GuiOverlay;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Gui;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@Data
public final class ItemCombinerMenu {
    private static final int MENU_SIZE = 6 * 9;
    private static final int OUTPUT_SLOT = 4 + 4 * 9;
    private final Player player;
    private Gui gui;
    private final InputSlot input1 = new InputSlot(2, 1);
    private final InputSlot input2 = new InputSlot(6, 1);
    private ItemCombinerRecipe selectedRecipe;

    public ItemCombinerMenu(final Player player) {
        this.player = player;
    }

    public void open() {
        gui = new Gui()
            .size(MENU_SIZE)
            .title(text("  Item Combiner", GOLD));
        gui.layer(GuiOverlay.COMBINER, WHITE);
        updateMenuSlots();
        gui.setItem(8, 0, Mytems.INFO_BUTTON.createIcon(List.of(text("View all recipes", GRAY))), this::onClickRecipes);
        gui.onClick(this::onClickBottomInventory);
        gui.open(player);
    }

    public List<InputSlot> getInputSlots() {
        return List.of(input1, input2);
    }

    public void updateMenuSlots() {
        for (InputSlot inputSlot : getInputSlots()) {
            inputSlot.updateMenu();
        }
        if (selectedRecipe != null
            && input1.hasItem() && input2.hasItem()
            && selectedRecipe.doesAcceptInputs(input1.getItem(), input2.getItem())) {
            gui.setItem(OUTPUT_SLOT, selectedRecipe.combine(input1.getItem(), input2.getItem()), this::onClickOutputSlot);
        } else {
            gui.setItem(OUTPUT_SLOT, null);
        }
    }

    private void onClickBottomInventory(InventoryClickEvent event) {
        final int slot = event.getSlot();
        if (slot < 0) {
            return;
        }
        if (!event.isLeftClick()) {
            return;
        }
        if (!event.getClickedInventory().equals(player.getInventory())) {
            return;
        }
        // Definitely bottom
        event.setCancelled(true);
        final ItemStack item = player.getInventory().getItem(slot);
        if (item == null || item.isEmpty()) {
            return;
        }
        // Item already selected, same effect as clicking
        // corresponding input slot.
        for (InputSlot inputSlot : getInputSlots()) {
            if (inputSlot.getPlayerInventorySlot() == slot) {
                inputSlot.resetItem();
                selectedRecipe = null;
                updateMenuSlots();
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
                return;
            }
        }
        final List<? extends ItemCombinerRecipe> recipes = getRecipes();
        if (!input1.hasItem()) {
            final ItemStack otherItem = input2.hasItem()
                ? input2.getItem()
                : null;
            for (ItemCombinerRecipe recipe : recipes) {
                if (!recipe.doesAcceptInput1(item)) {
                    continue;
                }
                if (otherItem != null && !recipe.doesAcceptInput2(otherItem)) {
                    continue;
                }
                input1.setItem(slot);
                selectedRecipe = recipe;
                updateMenuSlots();
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
                return;
            }
        } else if (!input2.hasItem()) {
            final ItemStack otherItem = input1.hasItem()
                ? input1.getItem()
                : null;
            for (ItemCombinerRecipe recipe : recipes) {
                if (!recipe.doesAcceptInput2(item)) {
                    continue;
                }
                if (otherItem != null && !recipe.doesAcceptInput1(otherItem)) {
                    continue;
                }
                input2.setItem(slot);
                selectedRecipe = recipe;
                updateMenuSlots();
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
                return;
            }
        }
        // Nothing found
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 0.5f);
    }

    private List<? extends ItemCombinerRecipe> getRecipes() {
        return UpgradableItemCombinerRecipe.getAll();
    }

    private void onClickOutputSlot(InventoryClickEvent event) {
        if (selectedRecipe == null
            || !input1.hasItem() || !input2.hasItem()
            || !selectedRecipe.doesAcceptInputs(input1.getItem(), input2.getItem())) {
            return;
        }
        final ItemStack result = selectedRecipe.combine(input1.getItem(), input2.getItem());
        if (result == null || result.isEmpty()) return;
        player.getInventory().setItem(input1.getPlayerInventorySlot(), null);
        player.getInventory().setItem(input2.getPlayerInventorySlot(), null);
        gui = new Gui();
        gui.size(MENU_SIZE)
            .layer(GuiOverlay.COMBINER, WHITE)
            .onClose(close -> PlayerReceiveItemsEvent.receiveInventory(player, gui.getInventory()))
            .onClick(this::onClickResultInventory)
            .onDrag(drag -> drag.setCancelled(true));
        gui.setItem(OUTPUT_SLOT, result);
        gui.setEditable(true);
        gui.open(player);
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1f, 0.85f);
        player.playSound(player.getLocation(), Sound.BLOCK_SMITHING_TABLE_USE, 1f, 0.65f);
    }

    private void onClickResultInventory(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) {
            return;
        } else if (event.getClickedInventory().equals(gui.getInventory())) {
            final int slot = event.getSlot();
            if (slot != OUTPUT_SLOT) {
                event.setCancelled(true);
                return;
            }
            final ItemStack item = event.getCurrentItem();
            if (item == null || item.isEmpty()) {
                event.setCancelled(true);
                return;
            }
            switch (event.getAction()) {
            case COLLECT_TO_CURSOR:
            case MOVE_TO_OTHER_INVENTORY:
            case PICKUP_ALL:
            case PICKUP_HALF:
            case PICKUP_ONE:
            case PICKUP_SOME:
                return;
            default:
                event.setCancelled(true);
            }
        } else if (event.getClickedInventory().equals(player.getInventory())) {
            switch (event.getAction()) {
            case MOVE_TO_OTHER_INVENTORY:
                event.setCancelled(true);
            default:
                break;
            }
        }
    }

    private void onClickRecipes(InventoryClickEvent event) {
        if (!event.isLeftClick()) {
            return;
        }
        final Merchant merchant = Bukkit.createMerchant(text("Item Combiner Recipes", color(0xaa6c39)));
        final List<MerchantRecipe> recipes = new ArrayList<>();
        for (ItemCombinerRecipe recipe : getRecipes()) {
            final List<ItemStack> preview = recipe.getItemPreview();
            MerchantRecipe mrecipe = new MerchantRecipe(preview.get(2), 1);
            mrecipe.setIngredients(List.of(preview.get(0), preview.get(1)));
            recipes.add(mrecipe);
        }
        merchant.setRecipes(recipes);
        final Gui listGui = new Gui();
        listGui.setItem(Gui.OUTSIDE, null, click -> {
                open();
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
            });
        listGui.setEditable(true);
        listGui.map(player, player.openMerchant(merchant, true).getTopInventory());
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
    }

    @Data
    private final class InputSlot {
        private final int menuSlotIndex;
        private int playerInventorySlot = -1;

        private InputSlot(final int x, final int y) {
            this.menuSlotIndex = x + y * 9;
        }

        public boolean hasItem() {
            return playerInventorySlot >= 0;
        }

        public ItemStack getItem() {
            return player.getInventory().getItem(playerInventorySlot);
        }

        public void setItem(int value) {
            playerInventorySlot = value;
        }

        public void resetItem() {
            playerInventorySlot = -1;
        }

        public void updateMenu() {
            if (!hasItem()) {
                gui.setItem(menuSlotIndex, null, event -> onClickInput(event));
            } else {
                gui.setItem(menuSlotIndex, getItem(), event -> onClickInput(event));
            }
        }

        private void onClickInput(InventoryClickEvent event) {
            if (!event.isLeftClick()) return;
            if (!hasItem()) return;
            resetItem();
            selectedRecipe = null;
            updateMenuSlots();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
        }
    }
}
