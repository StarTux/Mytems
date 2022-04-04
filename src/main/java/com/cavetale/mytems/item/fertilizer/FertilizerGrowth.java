package com.cavetale.mytems.item.fertilizer;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface FertilizerGrowth {
    ItemStack getIcon();

    boolean canGrow(Player player, Block block);

    void grow(Player player, Block block);
}
