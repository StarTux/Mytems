package com.cavetale.mytems.farming;

import lombok.Getter;
import static com.cavetale.core.util.CamelCase.toCamelCase;

@Getter
public enum GrowthType {
    BUSH,
    CACTUS,
    CHORUS,
    COCOA,
    GOURD,
    GRAIN,
    HERBACEOUS,
    KELP,
    LEAFY,
    MUSHROOM,
    NETHER_WART,
    ROOT,
    TREE,
    VINE,
    ;

    private final String displayName = toCamelCase(" ", this);
}
