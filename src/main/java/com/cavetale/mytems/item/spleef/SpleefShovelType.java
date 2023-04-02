package com.cavetale.mytems.item.spleef;

import com.cavetale.mytems.Mytems;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.format.TextColor;
import static net.kyori.adventure.text.format.TextColor.color;

@RequiredArgsConstructor
public enum SpleefShovelType {
    COPPER(Mytems.COPPER_SPLEEF_SHOVEL, 1, "Copper Spleef Shovel", color(0xD67B5B)),
    IRON(Mytems.IRON_SPLEEF_SHOVEL, 2, "Iron Spleef Shovel", color(0xB9B9B9)),
    GOLD(Mytems.GOLDEN_SPLEEF_SHOVEL, 3, "Golden Spleef Shovel", color(0xFFC107)),
    ;

    public final Mytems mytems;
    public final int range;
    public final String displayName;
    public final TextColor color;

    public static SpleefShovelType of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }
}
