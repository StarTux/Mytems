package com.cavetale.mytems.item.music;

import com.cavetale.mytems.Mytems;
import lombok.RequiredArgsConstructor;
import org.bukkit.Note.Tone;
import org.bukkit.Note;

@RequiredArgsConstructor
public enum Semitone {
    NATURAL("\u266E", Mytems.INVISIBLE_ITEM) {
        @Override public Note bukkitNote(int octave, Tone tone) {
            return Note.natural(octave, tone);
        }
    },
    SHARP("\u266F", Mytems.MUSICAL_SHARP) {
        @Override public Note bukkitNote(int octave, Tone tone) {
            if (tone == Tone.F) octave += 1;
            return Note.sharp(octave, tone);
        }
    },
    FLAT("\u266D", Mytems.MUSICAL_FLAT) {
        @Override public Note bukkitNote(int octave, Tone tone) {
            return Note.flat(octave, tone);
        }
    };

    public final String symbol;
    public final Mytems mytems;
    public abstract Note bukkitNote(int octave, Tone tone);

    public String serialize() {
        return symbol;
    }

    public static Semitone deserialize(String in) {
        if (in.isEmpty()) return NATURAL;
        if (in.equals(SHARP.symbol)) return SHARP;
        if (in.equals(FLAT.symbol)) return FLAT;
        throw new IllegalArgumentException(in);
    }
}
