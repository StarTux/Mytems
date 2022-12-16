package com.cavetale.mytems.item.coin;

import com.cavetale.mytems.Mytems;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.format.TextColor;
import static net.kyori.adventure.text.format.TextColor.color;

@RequiredArgsConstructor
public enum Denomination {
    COPPER(10, Mytems.COPPER_COIN, color(0xE77C56), true),
    SILVER(100, Mytems.SILVER_COIN, color(0xD8D8D8), true),
    GOLD(1000, Mytems.GOLDEN_COIN, color(0xFDF55F), true),
    GOLDEN_HOOP(1000, Mytems.GOLDEN_HOOP, color(0xFFFF18), false),
    DIAMOND(10_000, Mytems.DIAMOND_COIN, color(0xA1FBE8), true),
    RUBY(100_000, Mytems.RUBY_COIN, color(0xDA4358), true),
    ;

    public final int value;
    public final Mytems mytems;
    public final TextColor color;
    public final boolean regularCurrency;

    public static Denomination of(Mytems mytems) {
        for (var it : Denomination.values()) {
            if (it.mytems == mytems) return it;
        }
        throw new IllegalArgumentException("mytems=" + mytems);
    }
}
