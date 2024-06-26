package com.cavetale.mytems.item.tree;

import com.cavetale.mytems.Mytems;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter @RequiredArgsConstructor
public enum CustomTreeType {
    OAK(Mytems.OAKNUT, Material.OAK_SAPLING),
    BIRCH(Mytems.BIRCH_SEED, Material.BIRCH_SAPLING),
    SPRUCE(Mytems.SPRUCE_CONE, Material.SPRUCE_SAPLING),
    JUNGLE(Mytems.JUNGLE_SEED, Material.JUNGLE_SAPLING),
    ACACIA(Mytems.ACACIA_SEED, Material.ACACIA_SAPLING),
    DARK_OAK(Mytems.DARK_OAK_SEED, Material.DARK_OAK_SAPLING),
    AZALEA(Mytems.AZALEA_SEED, Material.AZALEA),
    SCOTCH_PINE(Mytems.SCOTCH_PINE_CONE, Material.SPRUCE_SAPLING),
    FIR(Mytems.FIR_CONE, Material.SPRUCE_SAPLING),
    FANCY_OAK(Mytems.FANCY_OAK_SEED, Material.OAK_SAPLING),
    FANCY_BIRCH(Mytems.FANCY_BIRCH_SEED, Material.BIRCH_SAPLING),
    FANCY_SPRUCE(Mytems.FANCY_SPRUCE_CONE, Material.SPRUCE_SAPLING),
    BROWN_MUSHROOM_SPAWN(Mytems.BROWN_MUSHROOM_SPAWN, Material.BROWN_MUSHROOM),
    CACTUS_SEED(Mytems.CACTUS_SEED, Material.CACTUS),
    CHERRY_PIT(Mytems.CHERRY_PIT, Material.CHERRY_SAPLING),
    CRIMSON_SPORE(Mytems.CRIMSON_SPORE, Material.CRIMSON_FUNGUS),
    MYSTERY_SEED(Mytems.MYSTERY_SEED, Material.SMALL_DRIPLEAF),
    RED_MUSHROOM_SPAWN(Mytems.RED_MUSHROOM_SPAWN, Material.RED_MUSHROOM),
    WARPED_SPORE(Mytems.WARPED_SPORE, Material.WARPED_FUNGUS),
    ;

    public final Mytems seedMytems;
    public final Material saplingMaterial;

    public static CustomTreeType ofSeed(Mytems mytems) {
        for (CustomTreeType it : CustomTreeType.values()) {
            if (it.seedMytems == mytems) return it;
        }
        return null;
    }
}
