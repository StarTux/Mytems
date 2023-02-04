package com.cavetale.mytems.item.hourglass;

import com.cavetale.core.font.Unicode;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.BlockColor;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import static com.cavetale.mytems.util.Text.gradient;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor
public enum HourglassType {
    COLORFALL(Mytems.COLORFALL_HOURGLASS,
              textOfChildren(text("C", BlockColor.ORANGE.textColor),
                             text("o", BlockColor.MAGENTA.textColor),
                             text("l", BlockColor.LIGHT_BLUE.textColor),
                             text("o", BlockColor.YELLOW.textColor),
                             text("r", BlockColor.LIME.textColor),
                             text("f", BlockColor.PINK.textColor),
                             text("a", BlockColor.BLUE.textColor),
                             text("l", BlockColor.GREEN.textColor),
                             text("l", BlockColor.RED.textColor),
                             space(),
                             text("H", BlockColor.ORANGE.textColor),
                             text("o", BlockColor.MAGENTA.textColor),
                             text("u", BlockColor.LIGHT_BLUE.textColor),
                             text("r", BlockColor.YELLOW.textColor),
                             text("g", BlockColor.LIME.textColor),
                             text("l", BlockColor.PINK.textColor),
                             text("a", BlockColor.BLUE.textColor),
                             text("s", BlockColor.GREEN.textColor),
                             text("s", BlockColor.RED.textColor)),
              "Always know the precise time in your world. Pre or post meridiem.",
              "View the time"),
    MOONLIGHT(Mytems.MOONLIGHT_HOURGLASS,
              textOfChildren(Mytems.GREEN_MOON, gradient("Moonlight Hourglass", GREEN, DARK_GREEN)),
              "Get the current phase of the moon, visible or not.",
              "View time and moon phase"),
    ATMOSPHERE(Mytems.ATMOSPHERE_HOURGLASS,
               gradient(Unicode.CLOUD.string + "Atmospheric Hourglass", BLUE, AQUA),
               "With ancient magic, this clock can predict the weather.",
               "View weather forecast"),
    CLIMATE(Mytems.CLIMATE_HOURGLASS,
            gradient("Climate Hourglass", RED, YELLOW, BLUE),
            "A delicate implement which is capable of reporting the temperature in celsius or fahrenheit.",
            "View temperature"),
    ;

    public final Mytems mytems;
    public final Component displayName;
    public final String description;
    public final String rightClickLine;

    public static HourglassType of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }
}
