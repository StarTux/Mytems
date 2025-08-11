package com.cavetale.mytems.item.golf;

import com.cavetale.mytems.Mytems;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import static com.cavetale.core.util.CamelCase.toCamelCase;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextColor.color;

@Getter
@RequiredArgsConstructor
public enum GolfClubQuality {
    WOOD(0.5, Mytems.WOODEN_GOLF_CLUB, color(0xa47449)),
    COPPER(1.0, Mytems.COPPER_GOLF_CLUB, color(0xb87333)),
    IRON(1.5, Mytems.IRON_GOLF_CLUB, NamedTextColor.GRAY),
    GOLD(2.0, Mytems.GOLDEN_GOLF_CLUB, NamedTextColor.GOLD),
    DIAMOND(2.5, Mytems.DIAMOND_GOLF_CLUB, NamedTextColor.AQUA),
    ;

    private final double strength;
    private final Mytems mytems;
    private final TextColor textColor;

    public String getDisplayName() {
        return toCamelCase(" ", mytems);
    }

    public Component getDisplayComponent() {
        return text(getDisplayName(), textColor);
    }

    public int getIntegerStrength() {
        return (int) Math.round(strength * 20.0);
    }

    public static GolfClubQuality of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }
}
