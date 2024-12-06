package com.cavetale.mytems.stat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CharacterElement {
    BLAZE(CharacterStatType.BLAZE),
    EARTH(CharacterStatType.EARTH),
    LIGHTNING(CharacterStatType.LIGHTNING),
    WATER(CharacterStatType.WATER),
    ICE(CharacterStatType.ICE),

    PLANT(CharacterStatType.PLANT),
    ENDER(CharacterStatType.ENDER),
    SCULK(CharacterStatType.SCULK),

    LOVE(CharacterStatType.LOVE),
    ;

    private final CharacterStatType characterStatType;

    private int[] efficiencies;

    public int getEfficiency(CharacterElement against) {
        return efficiencies[against.ordinal()];
    }

    public static CharacterElement of(CharacterStatType that) {
        for (var it : values()) {
            if (it.characterStatType == that) return it;
        }
        return null;
    }

    static {
        CharacterElement[] values = values();
        final int length = values.length;
        for (CharacterElement it : values) {
            it.efficiencies = new int[length];
        }

        strongAgainst(BLAZE, ICE, 2);
        strongAgainst(BLAZE, PLANT, 1);

        strongAgainst(WATER, BLAZE, 2);
        strongAgainst(WATER, EARTH, 1);

        strongAgainst(LIGHTNING, WATER, 2);

        strongAgainst(ENDER, EARTH, 2);
        strongAgainst(ENDER, WATER, 1);

        strongAgainst(ICE, WATER, 1);
        strongAgainst(ICE, EARTH, 1);

        strongAgainst(LOVE, ENDER, 2);

        strongAgainst(EARTH, LIGHTNING, 2);
        strongAgainst(EARTH, BLAZE, 1);
    }

    private void setEfficiency(CharacterElement against, int efficiency) {
        efficiencies[against.ordinal()] = efficiency;
    }

    private static void strongAgainst(CharacterElement strong, CharacterElement weak, int value) {
        if (strong.getEfficiency(weak) != 0) {
            throw new IllegalStateException("CharacterElement efficiency already set: " + strong + " vs " + weak);
        }
        if (weak.getEfficiency(strong) != 0) {
            throw new IllegalStateException("CharacterElement efficiency already set: " + weak + " vs " + strong);
        }
        strong.setEfficiency(weak, value);
        weak.setEfficiency(strong, -value);
    }
}
