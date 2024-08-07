package com.cavetale.mytems.item.combinable;

import java.util.List;
import org.bukkit.inventory.ItemStack;

public interface ItemCombinerRecipe {
    List<ItemStack> getItemPreview();

    boolean doesAcceptInput1(ItemStack input1);

    boolean doesAcceptInput2(ItemStack input2);

    default boolean doesAcceptInputs(ItemStack input1, ItemStack input2) {
        return doesAcceptInput1(input1) && doesAcceptInput2(input2);
    }

    ItemStack combine(ItemStack input1, ItemStack input2);
}
