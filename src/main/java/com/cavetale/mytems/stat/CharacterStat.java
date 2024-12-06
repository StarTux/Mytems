package com.cavetale.mytems.stat;

/**
 * Implementations provide polymorphism for the CharacterStatType
 * enum.
 */
public interface CharacterStat {
    CharacterStatType getType();

    default CharacterStatCategory getCategory() {
        return getType().getCategory();
    }
}
