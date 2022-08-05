package com.cavetale.mytems.item.coin;

import com.cavetale.mytems.Mytems;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.format.TextColor;
import static net.kyori.adventure.text.format.TextColor.color;

@RequiredArgsConstructor
public enum Denomination {
    COPPER(10, Mytems.COPPER_COIN, color(0xE77C56)),
    SILVER(100, Mytems.SILVER_COIN, color(0xD8D8D8)),
    GOLD(1000, Mytems.GOLDEN_COIN, color(0xFDF55F)),
    DIAMOND(10_000, Mytems.DIAMOND_COIN, color(0xA1FBE8)),
    RUBY(100_000, Mytems.RUBY_COIN, color(0xDA4358)),
    ;

    public final int value;
    public final Mytems mytems;
    public final TextColor color;

    public static Denomination of(Mytems mytems) {
        for (var it : Denomination.values()) {
            if (it.mytems == mytems) return it;
        }
        throw new IllegalArgumentException("mytems=" + mytems);
    }
}
