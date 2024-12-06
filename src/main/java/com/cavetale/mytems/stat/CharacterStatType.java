package com.cavetale.mytems.stat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * This enum uses implementations of the CharacterStat interface for
 * polymorphism.
 */
@Getter
@RequiredArgsConstructor
public enum CharacterStatType {
    // Record
    STRENGTH(CharacterStatCategory.RECORD),
    DEFENSE(CharacterStatCategory.RECORD),
    DEXTERITY(CharacterStatCategory.RECORD),
    EVASION(CharacterStatCategory.RECORD),
    // Element
    BLAZE(CharacterStatCategory.ELEMENT),
    EARTH(CharacterStatCategory.ELEMENT),
    WATER(CharacterStatCategory.ELEMENT),
    BREEZE(CharacterStatCategory.ELEMENT),

    LOVE(CharacterStatCategory.ELEMENT),

    ENDER(CharacterStatCategory.ELEMENT),
    SCULK(CharacterStatCategory.ELEMENT),
    PLANT(CharacterStatCategory.ELEMENT),
    ICE(CharacterStatCategory.ELEMENT),
    LIGHTNING(CharacterStatCategory.ELEMENT),
    ;

    private final CharacterStatCategory category;
    private CharacterStat instance;

    public CharacterStat getInstance() {
        if (instance == null) {
            instance = category.getConstructor().apply(this);
        }
        return instance;
    }
}
