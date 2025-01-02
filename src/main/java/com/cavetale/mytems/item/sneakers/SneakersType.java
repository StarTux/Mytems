package com.cavetale.mytems.item.sneakers;

import com.cavetale.mytems.Mytems;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SneakersType {
    WHITE(1, Mytems.SNEAKERS, 0xffffff, 0.5, 0.0, 0.0),
    RED(2, Mytems.RED_SNEAKERS, 0xff0000, 0.75, 0.0, 0.0),
    BLUE(3, Mytems.BLUE_SNEAKERS, 0x8080ff, 1.0, 0.1, 0.0),
    PURPLE(4, Mytems.PURPLE_SNEAKERS, 0xff00ff, 1.0, 0.25, 0.0),
    GOLDEN(5, Mytems.GOLDEN_SNEAKERS, 0xffaa00, 1.0, 0.5, 1.0),
    ;

    private final int tier;
    private final Mytems mytems;
    private final int hexColor;
    private final double speedBonus;
    private final double jumpBonus;
    private final double stepBonus;

    public static SneakersType of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }
}
