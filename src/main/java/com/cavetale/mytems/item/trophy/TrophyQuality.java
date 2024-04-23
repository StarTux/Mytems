package com.cavetale.mytems.item.trophy;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.format.TextColor;
import static net.kyori.adventure.text.format.TextColor.color;

@RequiredArgsConstructor
public enum TrophyQuality {
    GOLD(color(0xFFD700)),
    SILVER(color(0xC0C0C0)),
    BRONZE(color(0xCD7F32)),
    PARTICIPATION(color(0x6e6eff));

    public final TextColor textColor;
}
