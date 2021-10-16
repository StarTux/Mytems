package com.cavetale.mytems.item.music;

import org.bukkit.Note;

/**
 * A Touch is a player generated musical note from a
 * MusicalInstrument.
 */
public final class Touch {
    public final Note.Tone tone;
    public final Semitone semitone;
    public final Note bukkitNote;

    private Touch(final Note.Tone tone, final Semitone semitone, final Note bukkitNote) {
        this.tone = tone;
        this.semitone = semitone;
        this.bukkitNote = bukkitNote;
    }

    public static Touch of(Note.Tone tone, Semitone semitone, int octave) {
        Note bukkitNote = semitone.apply(Note.natural(octave, tone));
        return bukkitNote.getTone() == tone || bukkitNote.isSharped()
            ? new Touch(tone, semitone, bukkitNote)
            : new Touch(bukkitNote.getTone(), Semitone.NATURAL, bukkitNote);
    }

    public boolean countsAs(Note other) {
        return bukkitNote.getTone() == other.getTone() && bukkitNote.isSharped() == other.isSharped();
    }

    @Override
    public String toString() {
        final int octave = bukkitNote.getOctave();
        return tone.name()
            + (semitone != Semitone.NATURAL ? semitone.symbol : "")
            + (octave == 0 ? "" : (octave == 2 ? "''" : "'"));
    }
}
