package com.cavetale.mytems.item.tree;

import com.cavetale.mytems.Mytems;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;

@Getter @RequiredArgsConstructor
public enum CustomTreeType {
    OAK(Mytems.OAK_SEED, Material.OAK_SAPLING),
    BIRCH(Mytems.BIRCH_SEED, Material.BIRCH_SAPLING),
    SPRUCE(Mytems.SPRUCE_SEED, Material.SPRUCE_SAPLING),
    JUNGLE(Mytems.JUNGLE_SEED, Material.JUNGLE_SAPLING),
    ACACIA(Mytems.ACACIA_SEED, Material.ACACIA_SAPLING),
    DARK_OAK(Mytems.DARK_OAK_SEED, Material.DARK_OAK_SAPLING);

    public final Mytems seedMytems;
    public final Material saplingMaterial;

    public static CustomTreeType ofSeed(Mytems mytems) {
        for (CustomTreeType it : CustomTreeType.values()) {
            if (it.seedMytems == mytems) return it;
        }
        return null;
    }
}
