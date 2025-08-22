package com.cavetale.mytems.farming;

import lombok.Getter;
import static com.cavetale.core.util.CamelCase.toCamelCase;

@Getter
public enum PlantLocation {
    GARDEN_BED,   // Small plots, rows
    FIELD,        // Large plots, tilled
    BUSH,         // Perennial or semi-woody bushes
    TRELLIS,      // Requires vertical support
    TREE,         // Excluded from your core group, but included for completeness
    AQUATIC,      // Excluded from your core group
    OTHER,        // For special cases (e.g., mushrooms, nether wart)
    ;

    private final boolean onFarmland;
    private final String displayName;

    PlantLocation() {
        this.onFarmland = switch (this) {
        case GARDEN_BED, FIELD, BUSH -> true;
        default -> false;
        };
        this.displayName = toCamelCase(" ", this);
    }
}
