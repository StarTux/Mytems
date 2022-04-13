package com.cavetale.mytems.item.trophy;

import com.cavetale.mytems.Mytems;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TrophyType {
    GOLDEN_CUP(Mytems.GOLDEN_CUP, TrophyCategory.CUP, TrophyQuality.GOLD),
    SILVER_CUP(Mytems.SILVER_CUP, TrophyCategory.CUP, TrophyQuality.SILVER),
    BRONZE_CUP(Mytems.BRONZE_CUP, TrophyCategory.CUP, TrophyQuality.BRONZE),
    GOLD_MEDAL(Mytems.GOLD_MEDAL, TrophyCategory.MEDAL, TrophyQuality.GOLD),
    SILVER_MEDAL(Mytems.SILVER_MEDAL, TrophyCategory.MEDAL, TrophyQuality.SILVER),
    BRONZE_MEDAL(Mytems.BRONZE_MEDAL, TrophyCategory.MEDAL, TrophyQuality.BRONZE);

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
}
