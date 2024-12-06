package com.cavetale.mytems.item.gem;

import com.cavetale.mytems.Mytems;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GemShape {
    SQUARE(Mytems.SQUARE_SHAPED_SLOT),
    TRIANGLE(Mytems.TRIANGLE_SHAPED_SLOT),
    STAR(Mytems.STAR_SHAPED_SLOT),
    MOON(Mytems.MOON_SHAPED_SLOT),
    CIRCLE(Mytems.CIRCLE_SHAPED_SLOT),
    LIGHTNING(Mytems.LIGHTNING_SHAPED_SLOT),
    HEART(Mytems.HEART_SHAPED_SLOT),
    TEAR(Mytems.TEAR_SHAPED_SLOT),
    ;

    private final Mytems slotMytems;

    public static GemShape ofSlotMytems(Mytems mytems) {
        for (var it : values()) {
            if (it.slotMytems == mytems) return it;
        }
        return null;
    }
}
