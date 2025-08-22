package com.cavetale.mytems.farming;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.bukkit.block.Biome;
import static com.cavetale.core.util.CamelCase.toCamelCase;

@Getter
public enum Climate {
    COLD,
    TEMPERATE,
    HOT,
    NETHER,
    END,
    ;

    private static final Map<Biome, Climate> CACHE = new HashMap<>();
    private final String displayName = toCamelCase(" ", this);

    public static Climate of(Biome biome) {
        final Climate cached = CACHE.get(biome);
        if (cached != null) return cached;
        final Climate computed = compute(biome);
        CACHE.put(biome, computed);
        return computed;
    }

    private static Climate compute(Biome biome) {
        switch (biome.getKey().getKey()) {
        case "badlands":
        case "bamboo_jungle":
        case "deep_lukewarm_ocean":
        case "desert":
        case "eroded_badlands":
        case "jungle":
        case "mangrove_swamp":
        case "savanna":
        case "savanna_plateau":
        case "sparse_jungle":
        case "warm_ocean":
        case "windswept_savanna":
        case "wooded_badlands":
            return HOT;
        case "beach":
        case "birch_forest":
        case "cherry_grove":
        case "dark_forest":
        case "deep_dark":
        case "deep_ocean":
        case "dripstone_caves":
        case "flower_forest":
        case "forest":
        case "lukewarm_ocean":
        case "lush_caves":
        case "meadow":
        case "mushroom_fields":
        case "ocean":
        case "old_growth_birch_forest":
        case "pale_garden":
        case "plains":
        case "river":
        case "sunflower_plains":
        case "swamp":
            return TEMPERATE;
        case "cold_ocean":
        case "deep_cold_ocean":
        case "deep_frozen_ocean":
        case "frozen_ocean":
        case "frozen_peaks":
        case "frozen_river":
        case "grove":
        case "ice_spikes":
        case "jagged_peaks":
        case "old_growth_pine_taiga":
        case "old_growth_spruce_taiga":
        case "snowy_beach":
        case "snowy_plains":
        case "snowy_slopes":
        case "snowy_taiga":
        case "stony_shore":
        case "taiga":
        case "windswept_forest":
        case "windswept_gravelly_hills":
        case "windswept_hills":
            return COLD;
        case "basalt_deltas":
        case "crimson_forest":
        case "nether_wastes":
        case "soul_sand_valley":
        case "stony_peaks":
        case "warped_forest":
            return NETHER;
        case "end_barrens":
        case "end_highlands":
        case "end_midlands":
        case "small_end_islands":
        case "the_end":
        case "the_void":
            return END;
        default:
            return TEMPERATE;
        }
    }
}
