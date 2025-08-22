package com.cavetale.mytems.farming;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum LightRequirement {
    FULL_SUN(15, 15),
    PARTIAL_SHADE(8, 14),
    SHADE(0, 7),
    NONE(0, 0),
    ;

    private final int minimumLightLevel;
    private final int maximumLightLevel;
}
