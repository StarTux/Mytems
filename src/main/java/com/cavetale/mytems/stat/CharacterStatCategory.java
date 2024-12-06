package com.cavetale.mytems.stat;

import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Typically one per class implementing CharacterStat.
 */
@Getter
@RequiredArgsConstructor
public enum CharacterStatCategory {
    RECORD(RecordCharacterStat::new),
    ELEMENT(ElementCharacterStat::new),
    ;

    private final Function<CharacterStatType, CharacterStat> constructor;
}
