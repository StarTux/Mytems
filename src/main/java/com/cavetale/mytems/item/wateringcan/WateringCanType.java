package com.cavetale.mytems.item.wateringcan;

import com.cavetale.mytems.Mytems;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.format.TextColor;
import static net.kyori.adventure.text.format.TextColor.color;

@RequiredArgsConstructor
public enum WateringCanType {
    IRON(Mytems.WATERING_CAN,
         Mytems.EMPTY_WATERING_CAN,
         16, 0, color(0x4BAF98)),
    GOLD(Mytems.GOLDEN_WATERING_CAN,
         Mytems.EMPTY_GOLDEN_WATERING_CAN,
         128, 1, color(0xfff704)),
    ;

    public final Mytems filledMytems;
    public final Mytems emptyMytems;
    public final int maxWater;
    public final int radius;
    public final TextColor textColor;

    public static WateringCanType of(Mytems mytems) {
        for (WateringCanType it : values()) {
            if (it.filledMytems == mytems) return it;
            if (it.emptyMytems == mytems) return it;
        }
        return null;
    }

    public boolean isEmpty(Mytems mytems) {
        return mytems == emptyMytems;
    }
}
