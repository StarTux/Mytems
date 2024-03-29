package com.cavetale.mytems.item.fertilizer;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerChangeBlockEvent;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class FertilizerBisectedFlower implements FertilizerGrowth {
    public final Material material;

    @Override
    public ItemStack getIcon() {
        return new ItemStack(material);
    }

    @Override
    public boolean canGrow(Player player, Block block) {
        return Fertilizer.flowerGrowables.isTagged(block.getType())
            && block.getRelative(0, 1, 0).isEmpty()
            && block.getRelative(0, 2, 0).isEmpty()
            && PlayerBlockAbilityQuery.Action.BUILD.query(player, block.getRelative(0, 1, 0))
            && PlayerBlockAbilityQuery.Action.BUILD.query(player, block.getRelative(0, 2, 0));
    }

    @Override
    public void grow(Player player, Block block, ItemStack itemStack) {
        Block above = block.getRelative(0, 1, 0);
        Block abov2 = block.getRelative(0, 2, 0);
        Bisected bdata = (Bisected) material.createBlockData();
        Bisected bdat2 = (Bisected) material.createBlockData();
        bdata.setHalf(Bisected.Half.BOTTOM);
        bdat2.setHalf(Bisected.Half.TOP);
        new PlayerChangeBlockEvent(player, above, bdata, itemStack).callEvent();
        new PlayerChangeBlockEvent(player, abov2, bdat2, itemStack).callEvent();
        above.setBlockData(bdata, false);
        abov2.setBlockData(bdat2, false);
    }
}
