package com.cavetale.mytems.item.finder;

import com.cavetale.mytems.Mytems;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextColor.color;

@RequiredArgsConstructor
public enum FinderType {
    STRUCTURE(100, Mytems.STRUCTURE_FINDER, "Structure Finder", color(0xC4A484),
              "Find common structures known to most sailors, adventurers, and birds"),
    SECRET(150, Mytems.SECRET_FINDER, "Secret Finder", color(0x00CDCD),
           "Locate secret relics hidden from contemporary knowledge"),
    MYSTIC(200, Mytems.MYSTIC_FINDER, "Mystic Finder", color(0xD5A000),
           "Pinpoint all ancient heritage sites with the wisdom of the elders"),
    ;

    public final int range;
    public final Mytems mytems;
    public final String displayName;
    public final TextColor color;
    public final String description;

    public static FinderType of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }

    public Component getDisplayComponent() {
        return text(displayName, color);
    }
}
