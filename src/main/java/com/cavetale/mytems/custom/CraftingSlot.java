package com.cavetale.mytems.custom;

import com.cavetale.mytems.util.Gui;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter @RequiredArgsConstructor
public final class CraftingSlot {
    private final int guiIndex;
    private boolean set;
    private int playerIndex;
    private ItemStack itemCopy;

    public void set(final int index, final ItemStack item) {
        this.set = true;
        this.playerIndex = index;
        this.itemCopy = item.clone();
    }

    public void unset() {
        this.set = false;
        this.playerIndex = -1;
        this.itemCopy = null;
    }

    public boolean checkConsistency(Player player) {
        if (!set) return false;
        ItemStack playerItem = player.getInventory().getItem(playerIndex);
        return itemCopy.equals(playerItem);
    }

    public void update(Gui gui) {
        gui.setItem(guiIndex, itemCopy != null ? itemCopy.clone() : null);
    }

    public void change(Player player, ItemStack item) {
        player.getInventory().setItem(playerIndex, item);
    }

    public void subtract(Player player, int amount) {
        player.getInventory().getItem(playerIndex).subtract(amount);
    }

    public void subtract(Player player) {
        player.getInventory().getItem(playerIndex).subtract(1);
    }
}
