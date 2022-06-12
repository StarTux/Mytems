package com.cavetale.mytems.item.trophy;

import com.cavetale.mytems.Mytems;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.item.trophy.TrophyCategory.*;
import static com.cavetale.mytems.item.trophy.TrophyQuality.*;

@RequiredArgsConstructor
public enum TrophyType {
    // CUP
    GOLDEN_CUP(Mytems.GOLDEN_CUP, CUP, GOLD),
    SILVER_CUP(Mytems.SILVER_CUP, CUP, SILVER),
    BRONZE_CUP(Mytems.BRONZE_CUP, CUP, BRONZE),
    PARTICIPATION_CUP(Mytems.PARTICIPATION_CUP, CUP, PARTICIPATION),
    // MEDAL
    GOLD_MEDAL(Mytems.GOLD_MEDAL, MEDAL, GOLD),
    SILVER_MEDAL(Mytems.SILVER_MEDAL, MEDAL, SILVER),
    BRONZE_MEDAL(Mytems.BRONZE_MEDAL, MEDAL, BRONZE),
    PARTICIPATION_MEDAL(Mytems.PARTICIPATION_MEDAL, MEDAL, PARTICIPATION),
    // TETRIS
    TETRIS_1(Mytems.TETRIS_I, TETRIS, GOLD),
    TETRIS_2(Mytems.TETRIS_Z, TETRIS, SILVER),
    TETRIS_3(Mytems.TETRIS_T, TETRIS, BRONZE),
    TETRIS_4(Mytems.TETRIS_L, TETRIS, PARTICIPATION),
    // EASTER
    GOLD_EASTER_TROPHY(Mytems.GOLD_EASTER_TROPHY, EASTER, GOLD),
    SILVER_EASTER_TROPHY(Mytems.SILVER_EASTER_TROPHY, EASTER, SILVER),
    BRONZE_EASTER_TROPHY(Mytems.BRONZE_EASTER_TROPHY, EASTER, BRONZE),
    PARTICIPATION_EASTER_TROPHY(Mytems.PARTICIPATION_EASTER_TROPHY, EASTER, PARTICIPATION),
    // VERTIGO
    GOLD_VERTIGO_TROPHY(Mytems.GOLD_VERTIGO_TROPHY, VERTIGO, GOLD),
    SILVER_VERTIGO_TROPHY(Mytems.SILVER_VERTIGO_TROPHY, VERTIGO, SILVER),
    BRONZE_VERTIGO_TROPHY(Mytems.BRONZE_VERTIGO_TROPHY, VERTIGO, BRONZE),
    PARTICIPATION_VERTIGO_TROPHY(Mytems.PARTICIPATION_VERTIGO_TROPHY, VERTIGO, PARTICIPATION),
    // King of the Ladder
    GOLD_LADDER_TROPHY(Mytems.GOLD_LADDER_TROPHY, LADDER, GOLD),
    SILVER_LADDER_TROPHY(Mytems.SILVER_LADDER_TROPHY, LADDER, SILVER),
    BRONZE_LADDER_TROPHY(Mytems.BRONZE_LADDER_TROPHY, LADDER, BRONZE),
    PARTICIPATION_LADDER_TROPHY(Mytems.PARTICIPATION_LADDER_TROPHY, LADDER, PARTICIPATION),
    // Cavepaint
    GOLD_CAVEPAINT_TROPHY(Mytems.GOLD_CAVEPAINT_TROPHY, CAVEPAINT, GOLD),
    SILVER_CAVEPAINT_TROPHY(Mytems.SILVER_CAVEPAINT_TROPHY, CAVEPAINT, SILVER),
    BRONZE_CAVEPAINT_TROPHY(Mytems.BRONZE_CAVEPAINT_TROPHY, CAVEPAINT, BRONZE),
    PARTICIPATION_CAVEPAINT_TROPHY(Mytems.PARTICIPATION_CAVEPAINT_TROPHY, CAVEPAINT, PARTICIPATION),
    // Maypole
    GOLD_MAYPOLE_TROPHY(Mytems.FROZEN_AMBER, MAYPOLE, GOLD),
    SILVER_MAYPOLE_TROPHY(Mytems.FROST_FLOWER, MAYPOLE, SILVER),
    BRONZE_MAYPOLE_TROPHY(Mytems.FIRE_AMANITA, MAYPOLE, BRONZE),
    PARTICIPATION_MAYPOLE_TROPHY(Mytems.CLAMSHELL, MAYPOLE, PARTICIPATION),
    // Red Light Green Light
    GOLD_RED_GREEN_LIGHT_TROPHY(Mytems.GOLD_RED_GREEN_LIGHT_TROPHY, RED_GREEN_LIGHT, GOLD),
    SILVER_RED_GREEN_LIGHT_TROPHY(Mytems.SILVER_RED_GREEN_LIGHT_TROPHY, RED_GREEN_LIGHT, SILVER),
    BRONZE_RED_GREEN_LIGHT_TROPHY(Mytems.BRONZE_RED_GREEN_LIGHT_TROPHY, RED_GREEN_LIGHT, BRONZE),
    PART_RED_GREEN_LIGHT_TROPHY(Mytems.PART_RED_GREEN_LIGHT_TROPHY, RED_GREEN_LIGHT, PARTICIPATION),
    // Hide and Seek
    GOLD_HIDE_AND_SEEK_TROPHY(Mytems.GOLD_HIDE_AND_SEEK_TROPHY, HIDE_AND_SEEK, GOLD),
    SILVER_HIDE_AND_SEEK_TROPHY(Mytems.SILVER_HIDE_AND_SEEK_TROPHY, HIDE_AND_SEEK, SILVER),
    BRONZE_HIDE_AND_SEEK_TROPHY(Mytems.BRONZE_HIDE_AND_SEEK_TROPHY, HIDE_AND_SEEK, BRONZE),
    PART_HIDE_AND_SEEK_TROPHY(Mytems.PART_HIDE_AND_SEEK_TROPHY, HIDE_AND_SEEK, PARTICIPATION),
    // Vote
    GOLD_VOTE_TROPHY(Mytems.GOLD_VOTE_TROPHY, VOTE, GOLD),
    SILVER_VOTE_TROPHY(Mytems.SILVER_VOTE_TROPHY, VOTE, SILVER),
    BRONZE_VOTE_TROPHY(Mytems.BRONZE_VOTE_TROPHY, VOTE, BRONZE),
    PART_VOTE_TROPHY(Mytems.PART_VOTE_TROPHY, VOTE, PARTICIPATION),
    // Shovel
    GOLDEN_SHOVEL(Material.GOLDEN_SHOVEL, SHOVEL, GOLD),
    SILVER_SHOVEL(Material.IRON_SHOVEL, SHOVEL, SILVER),
    BRONZE_SHOVEL(Material.STONE_SHOVEL, SHOVEL, BRONZE),
    PART_SHOVEL(Material.WOODEN_SHOVEL, SHOVEL, PARTICIPATION),
    // Sword
    GOLDEN_SWORD(Material.GOLDEN_SWORD, SWORD, GOLD),
    SILVER_SWORD(Material.IRON_SWORD, SWORD, SILVER),
    BRONZE_SWORD(Material.STONE_SWORD, SWORD, BRONZE),
    PART_SWORD(Material.WOODEN_SWORD, SWORD, PARTICIPATION),
    // Pickaxe
    GOLDEN_PICKAXE(Material.GOLDEN_PICKAXE, PICKAXE, GOLD),
    SILVER_PICKAXE(Material.IRON_PICKAXE, PICKAXE, SILVER),
    BRONZE_PICKAXE(Material.STONE_PICKAXE, PICKAXE, BRONZE),
    PART_PICKAXE(Material.WOODEN_PICKAXE, PICKAXE, PARTICIPATION),
    // Pickaxe
    GOLDEN_AXE(Material.GOLDEN_AXE, AXE, GOLD),
    SILVER_AXE(Material.IRON_AXE, AXE, SILVER),
    BRONZE_AXE(Material.STONE_AXE, AXE, BRONZE),
    PART_AXE(Material.WOODEN_AXE, AXE, PARTICIPATION),
    // Hoe
    GOLDEN_HOE(Material.GOLDEN_HOE, HOE, GOLD),
    SILVER_HOE(Material.IRON_HOE, HOE, SILVER),
    BRONZE_HOE(Material.STONE_HOE, HOE, BRONZE),
    PART_HOE(Material.WOODEN_HOE, HOE, PARTICIPATION),
    ;

    private final Mytems mytems;
    private final Material material;
    public final TrophyCategory category;
    public final TrophyQuality quality;

    TrophyType(final Mytems mytems, final TrophyCategory category, final TrophyQuality quality) {
        this(mytems, null, category, quality);
    }

    TrophyType(final Material material, final TrophyCategory category, final TrophyQuality quality) {
        this(null, material, category, quality);
    }

    public ItemStack createItemStack() {
        if (mytems != null) {
            return mytems.createItemStack();
        }
        ItemStack item = new ItemStack(material);
        item.editMeta(m -> m.addItemFlags(ItemFlag.values()));
        return item;
    }

    public static TrophyType of(Mytems mytems) {
        for (TrophyType it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }

    public static TrophyType of(TrophyCategory category, TrophyQuality quality) {
        for (TrophyType it : values()) {
            if (it.category == category && it.quality == quality) return it;
        }
        return null;
    }

    public static List<TrophyType> of(TrophyCategory category) {
        List<TrophyType> result = new ArrayList<>();
        for (TrophyType it : values()) {
            if (it.category == category) result.add(it);
        }
        return result;
    }
}
