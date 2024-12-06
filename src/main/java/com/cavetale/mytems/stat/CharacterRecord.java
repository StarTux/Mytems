package com.cavetale.mytems.stat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CharacterRecord {
    STRENGTH(CharacterStatType.STRENGTH),
    DEFENSE(CharacterStatType.DEFENSE),
    DEXTERITY(CharacterStatType.DEXTERITY),
    EVASION(CharacterStatType.EVASION),
    ;

    private final CharacterStatType characterStatType;

    public static CharacterRecord of(CharacterStatType that) {
        for (var it : values()) {
            if (it.characterStatType == that) return it;
        }
        return null;
    }
}
