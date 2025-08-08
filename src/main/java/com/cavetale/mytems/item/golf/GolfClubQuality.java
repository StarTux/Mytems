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
    WOOD(Mytems.WOODEN_GOLF_CLUB, color(0xa47449)),
    COPPER(Mytems.COPPER_GOLF_CLUB, color(0xb87333)),
    IRON(Mytems.IRON_GOLF_CLUB, NamedTextColor.GRAY),
    GOLD(Mytems.GOLDEN_GOLF_CLUB, NamedTextColor.GOLD),
    DIAMOND(Mytems.DIAMOND_GOLF_CLUB, NamedTextColor.AQUA),
    ;

    private final Mytems mytems;
    private final TextColor textColor;

    public String getDisplayName() {
        return toCamelCase(" ", mytems);
    }

    public Component getDisplayComponent() {
        return text(getDisplayName(), textColor);
    }

    public static GolfClubQuality of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }
}
