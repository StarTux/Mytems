package com.cavetale.mytems.farming;

public enum PlantLocation {
    GARDEN_BED,   // Small plots, rows
    FIELD,        // Large plots, tilled
    BUSH,         // Perennial or semi-woody bushes
    TRELLIS,      // Requires vertical support
    TREE,         // Excluded from your core group, but included for completeness
    AQUATIC,      // Excluded from your core group
    OTHER,        // For special cases (e.g., mushrooms, nether wart)
    ;
}
