package com.cavetale.mytems.item.finder;

import com.cavetale.mytems.Mytems;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor
public enum FinderType {
    NORMAL(80, Mytems.STRUCTURE_FINDER, text("Structure Finder", GRAY), GRAY,
           "Find common structures known to most birds, sailors, and adventurors"),
    SECRET(150, Mytems.SECRET_FINDER, text("Secret Finder", AQUA), AQUA,
           "Locate faraway secret relics hidden from contemporary knowledge"),
    MASTER(250, Mytems.MASTER_FINDER, text("Master Finder", GOLD), GOLD,
           "Find all structures with an extended range and wisdom from the elders"),
    ;

    public final int range;
    public final Mytems mytems;
    public final Component displayName;
    public final TextColor color;
    public final String description;

    public static FinderType of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }
}
