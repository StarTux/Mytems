package com.cavetale.mytems.item.gem;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.format.TextColor;

@Getter
@RequiredArgsConstructor
public enum GemColor {
    SAPPHIRE(0x7070FF), // Rich Blue
    OPAL(0xFF69B4), // Pink, iridescent
    TOPAZ(0xE0E000), // Yellow
    JADE(0xA36C), // Soft green with a gray-blue undertone
    RUBY(0xFF0040), // Red
    ;

    private final int hexColor;

    public TextColor getTextColor() {
        return TextColor.color(hexColor);
    }
}
