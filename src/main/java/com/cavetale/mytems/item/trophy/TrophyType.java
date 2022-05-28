package com.cavetale.mytems.item.trophy;

import com.cavetale.mytems.Mytems;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TrophyType {
    // CUP
    GOLDEN_CUP(Mytems.GOLDEN_CUP, TrophyCategory.CUP, TrophyQuality.GOLD),
    SILVER_CUP(Mytems.SILVER_CUP, TrophyCategory.CUP, TrophyQuality.SILVER),
    BRONZE_CUP(Mytems.BRONZE_CUP, TrophyCategory.CUP, TrophyQuality.BRONZE),
    PARTICIPATION_CUP(Mytems.PARTICIPATION_CUP, TrophyCategory.CUP, TrophyQuality.PARTICIPATION),
    // MEDAL
    GOLD_MEDAL(Mytems.GOLD_MEDAL, TrophyCategory.MEDAL, TrophyQuality.GOLD),
    SILVER_MEDAL(Mytems.SILVER_MEDAL, TrophyCategory.MEDAL, TrophyQuality.SILVER),
    BRONZE_MEDAL(Mytems.BRONZE_MEDAL, TrophyCategory.MEDAL, TrophyQuality.BRONZE),
    PARTICIPATION_MEDAL(Mytems.PARTICIPATION_MEDAL, TrophyCategory.MEDAL, TrophyQuality.PARTICIPATION),
    // TETRIS
    TETRIS_1(Mytems.TETRIS_I, TrophyCategory.TETRIS, TrophyQuality.GOLD),
    TETRIS_2(Mytems.TETRIS_Z, TrophyCategory.TETRIS, TrophyQuality.SILVER),
    TETRIS_3(Mytems.TETRIS_T, TrophyCategory.TETRIS, TrophyQuality.BRONZE),
    TETRIS_4(Mytems.TETRIS_L, TrophyCategory.TETRIS, TrophyQuality.PARTICIPATION),
    // EASTER
    GOLD_EASTER_TROPHY(Mytems.GOLD_EASTER_TROPHY, TrophyCategory.EASTER, TrophyQuality.GOLD),
    SILVER_EASTER_TROPHY(Mytems.SILVER_EASTER_TROPHY, TrophyCategory.EASTER, TrophyQuality.SILVER),
    BRONZE_EASTER_TROPHY(Mytems.BRONZE_EASTER_TROPHY, TrophyCategory.EASTER, TrophyQuality.BRONZE),
    PARTICIPATION_EASTER_TROPHY(Mytems.PARTICIPATION_EASTER_TROPHY, TrophyCategory.EASTER, TrophyQuality.PARTICIPATION),
    // VERTIGO
    GOLD_VERTIGO_TROPHY(Mytems.GOLD_VERTIGO_TROPHY, TrophyCategory.VERTIGO, TrophyQuality.GOLD),
    SILVER_VERTIGO_TROPHY(Mytems.SILVER_VERTIGO_TROPHY, TrophyCategory.VERTIGO, TrophyQuality.SILVER),
    BRONZE_VERTIGO_TROPHY(Mytems.BRONZE_VERTIGO_TROPHY, TrophyCategory.VERTIGO, TrophyQuality.BRONZE),
    PARTICIPATION_VERTIGO_TROPHY(Mytems.PARTICIPATION_VERTIGO_TROPHY, TrophyCategory.VERTIGO, TrophyQuality.PARTICIPATION),
    // King of the Ladder
    GOLD_LADDER_TROPHY(Mytems.GOLD_LADDER_TROPHY, TrophyCategory.LADDER, TrophyQuality.GOLD),
    SILVER_LADDER_TROPHY(Mytems.SILVER_LADDER_TROPHY, TrophyCategory.LADDER, TrophyQuality.SILVER),
    BRONZE_LADDER_TROPHY(Mytems.BRONZE_LADDER_TROPHY, TrophyCategory.LADDER, TrophyQuality.BRONZE),
    PARTICIPATION_LADDER_TROPHY(Mytems.PARTICIPATION_LADDER_TROPHY, TrophyCategory.LADDER, TrophyQuality.PARTICIPATION),
    // Cavepaint
    GOLD_CAVEPAINT_TROPHY(Mytems.GOLD_CAVEPAINT_TROPHY, TrophyCategory.CAVEPAINT, TrophyQuality.GOLD),
    SILVER_CAVEPAINT_TROPHY(Mytems.SILVER_CAVEPAINT_TROPHY, TrophyCategory.CAVEPAINT, TrophyQuality.SILVER),
    BRONZE_CAVEPAINT_TROPHY(Mytems.BRONZE_CAVEPAINT_TROPHY, TrophyCategory.CAVEPAINT, TrophyQuality.BRONZE),
    PARTICIPATION_CAVEPAINT_TROPHY(Mytems.PARTICIPATION_CAVEPAINT_TROPHY, TrophyCategory.CAVEPAINT, TrophyQuality.PARTICIPATION),
    // Maypole
    GOLD_MAYPOLE_TROPHY(Mytems.FROZEN_AMBER, TrophyCategory.MAYPOLE, TrophyQuality.GOLD),
    SILVER_MAYPOLE_TROPHY(Mytems.FROST_FLOWER, TrophyCategory.MAYPOLE, TrophyQuality.SILVER),
    BRONZE_MAYPOLE_TROPHY(Mytems.FIRE_AMANITA, TrophyCategory.MAYPOLE, TrophyQuality.BRONZE),
    PARTICIPATION_MAYPOLE_TROPHY(Mytems.CLAMSHELL, TrophyCategory.MAYPOLE, TrophyQuality.PARTICIPATION),
    // Red Light Green Light
    GOLD_RED_GREEN_LIGHT_TROPHY(Mytems.GOLD_RED_GREEN_LIGHT_TROPHY, TrophyCategory.RED_GREEN_LIGHT, TrophyQuality.GOLD),
    SILVER_RED_GREEN_LIGHT_TROPHY(Mytems.SILVER_RED_GREEN_LIGHT_TROPHY, TrophyCategory.RED_GREEN_LIGHT, TrophyQuality.SILVER),
    BRONZE_RED_GREEN_LIGHT_TROPHY(Mytems.BRONZE_RED_GREEN_LIGHT_TROPHY, TrophyCategory.RED_GREEN_LIGHT, TrophyQuality.BRONZE),
    PART_RED_GREEN_LIGHT_TROPHY(Mytems.PART_RED_GREEN_LIGHT_TROPHY, TrophyCategory.RED_GREEN_LIGHT, TrophyQuality.PARTICIPATION),
    ;

    public final Mytems mytems;
    public final TrophyCategory category;
    public final TrophyQuality quality;

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
