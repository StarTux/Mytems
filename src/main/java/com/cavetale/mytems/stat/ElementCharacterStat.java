package com.cavetale.mytems.stat;

import lombok.Getter;
import static java.util.Objects.requireNonNull;

@Getter
public final class ElementCharacterStat implements CharacterStat {
    private final CharacterStatType type;
    private final CharacterElement element;

    ElementCharacterStat(final CharacterStatType type) {
        this.type = type;
        this.element = requireNonNull(CharacterElement.of(type), "" + type);
    }

    @Override
    public CharacterStatCategory getCategory() {
        return CharacterStatCategory.ELEMENT;
    }
}
