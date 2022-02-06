package com.cavetale.mytems.item.coin;

import com.cavetale.mytems.Mytems;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.format.TextColor;
import static net.kyori.adventure.text.format.TextColor.color;

@RequiredArgsConstructor
public enum Denomination {
    COPPER(10, Mytems.COPPER_COIN, color(0xe77c56)),
    SILVER(100, Mytems.SILVER_COIN, color(0xd8d8d8)),
    GOLD(1000, Mytems.GOLDEN_COIN, color(0xfdf55f));

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
