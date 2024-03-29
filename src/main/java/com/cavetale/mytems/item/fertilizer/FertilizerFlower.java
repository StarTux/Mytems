package com.cavetale.mytems.item.fertilizer;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerChangeBlockEvent;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class FertilizerFlower implements FertilizerGrowth {
    public final Material material;

    @Override
    public ItemStack getIcon() {
        return new ItemStack(material);
    }

    @Override
    public boolean canGrow(Player player, Block block) {
        return Fertilizer.flowerGrowables.isTagged(block.getType())
            && block.getRelative(0, 1, 0).isEmpty()
            && PlayerBlockAbilityQuery.Action.BUILD.query(player, block.getRelative(0, 1, 0));
    }

    @Override
    public void grow(Player player, Block block, ItemStack itemStack) {
        Block above = block.getRelative(0, 1, 0);
        new PlayerChangeBlockEvent(player, above, material.createBlockData(), itemStack).callEvent();
        above.setBlockData(material.createBlockData());
    }
}
