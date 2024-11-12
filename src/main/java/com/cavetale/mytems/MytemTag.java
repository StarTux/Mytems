package com.cavetale.mytems;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

/**
 * An intermediate data structure to hold (de)serialized items stored
 * in an ItemStack. It's serialized to String via Json.
 *
 * Superclass of other tags.  The Mytem implementation works in tandem
 * with subclass of MytemTag to serialize and deserialize all
 * ItemStacks.
 */
@Data
public class MytemTag {
    protected Integer amount;

    /**
     * Determine if this tag is empty and needs not be saved.
     */
    public boolean isEmpty() {
        return amount == null || amount == 1;
    }

    /**
     * Load this empty tag from an item.
     */
    public void load(Mytems mytems, ItemStack itemStack) {
        if (itemStack.getAmount() != 1) {
            this.amount = itemStack.getAmount();
        }
    }

    /**
     * Store the contents of this tag in a new item.
     */
    public void store(Mytems mytems, ItemStack itemStack) {
        if (amount != null) {
            itemStack.setAmount(amount);
        }
    }

    /**
     * Determine if the information in this tag may be discarded in
     * favor of efficient storage.  This is intended for Mass Storage,
     * which cannot store non-empty, non-dismissable item tags.
     */
    public boolean isDismissable() {
        return isEmpty();
    }

    /**
     * Check if this MytemTag can be merged with another.  This will
     * disregard isDismissable() as well as getMaxItemCount().
     * Essentially, compare everything besides the item amount.
     * Subclasses should alway call super!
     */
    public boolean isSimilar(MytemTag other) {
        return other != null;
    }
}
