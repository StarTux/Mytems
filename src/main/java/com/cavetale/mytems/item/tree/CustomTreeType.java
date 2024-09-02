package com.cavetale.mytems.item.tree;

import com.cavetale.mytems.Mytems;
import java.util.Random;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@RequiredArgsConstructor
public enum CustomTreeType {
    OAK(Mytems.OAKNUT, Material.OAK_SAPLING, TreeSeedCategory.REGULAR),
    BIRCH(Mytems.BIRCH_SEED, Material.BIRCH_SAPLING, TreeSeedCategory.REGULAR),
    SPRUCE(Mytems.SPRUCE_CONE, Material.SPRUCE_SAPLING, TreeSeedCategory.REGULAR),
    JUNGLE(Mytems.JUNGLE_SEED, Material.JUNGLE_SAPLING, TreeSeedCategory.REGULAR),
    ACACIA(Mytems.ACACIA_SEED, Material.ACACIA_SAPLING, TreeSeedCategory.REGULAR),
    DARK_OAK(Mytems.DARK_OAK_SEED, Material.DARK_OAK_SAPLING, TreeSeedCategory.REGULAR),
    AZALEA(Mytems.AZALEA_SEED, Material.AZALEA, TreeSeedCategory.REGULAR),
    SCOTCH_PINE(Mytems.SCOTCH_PINE_CONE, Material.SPRUCE_SAPLING, TreeSeedCategory.REGULAR),
    FIR(Mytems.FIR_CONE, Material.SPRUCE_SAPLING, TreeSeedCategory.REGULAR),
    FANCY_OAK(Mytems.FANCY_OAK_SEED, Material.OAK_SAPLING, TreeSeedCategory.FANCY),
    FANCY_BIRCH(Mytems.FANCY_BIRCH_SEED, Material.BIRCH_SAPLING, TreeSeedCategory.FANCY),
    FANCY_SPRUCE(Mytems.FANCY_SPRUCE_CONE, Material.SPRUCE_SAPLING, TreeSeedCategory.FANCY),
    BROWN_MUSHROOM_SPAWN(Mytems.BROWN_MUSHROOM_SPAWN, Material.BROWN_MUSHROOM, TreeSeedCategory.MUSHROOM),
    CACTUS_SEED(Mytems.CACTUS_SEED, Material.CACTUS, TreeSeedCategory.REGULAR),
    CHERRY_PIT(Mytems.CHERRY_PIT, Material.CHERRY_SAPLING, TreeSeedCategory.REGULAR),
    CRIMSON_SPORE(Mytems.CRIMSON_SPORE, Material.CRIMSON_FUNGUS, TreeSeedCategory.MUSHROOM),
    MYSTERY_SEED(Mytems.MYSTERY_SEED, Material.SMALL_DRIPLEAF, TreeSeedCategory.FANCY),
    RED_MUSHROOM_SPAWN(Mytems.RED_MUSHROOM_SPAWN, Material.RED_MUSHROOM, TreeSeedCategory.MUSHROOM),
    WARPED_SPORE(Mytems.WARPED_SPORE, Material.WARPED_FUNGUS, TreeSeedCategory.MUSHROOM),
    ;

    public final Mytems seedMytems;
    public final Material saplingMaterial;
    private final TreeSeedCategory treeSeedCategory;
    @Setter
    private int treeModelCount;

    @Getter
    @RequiredArgsConstructor
    public enum TreeSeedCategory {
        REGULAR(10, 4, 16),
        FANCY(1, 1, 4),
        MUSHROOM(5, 1, 8),
        ;

        private final int randomWeight;
        private final int minLootAmount;
        private final int maxLootAmount;
    }

    public static CustomTreeType ofSeed(Mytems mytems) {
        for (CustomTreeType it : CustomTreeType.values()) {
            if (it.seedMytems == mytems) return it;
        }
        return null;
    }

    public int getRandomWeight() {
        return treeSeedCategory.randomWeight;
    }

    public int randomLootAmount(Random random) {
        final int min = treeSeedCategory.minLootAmount;
        final int max = treeSeedCategory.maxLootAmount;
        if (min == max) {
            return min;
        } else {
            return min + random.nextInt(max - min + 1);
        }
    }

    public ItemStack randomLootItemStack(Random random) {
        return seedMytems.createItemStack(randomLootAmount(random));
    }
}
