package com.cavetale.mytems.stat;

import lombok.Value;
import static java.util.Objects.requireNonNull;

@Value
public final class RecordCharacterStat implements CharacterStat {
    private final CharacterStatType type;
    private final CharacterRecord record;

    RecordCharacterStat(final CharacterStatType type) {
        this.type = type;
        this.record = requireNonNull(CharacterRecord.of(type), "" + type);
    }

    @Override
    public CharacterStatCategory getCategory() {
        return CharacterStatCategory.RECORD;
    }
}
