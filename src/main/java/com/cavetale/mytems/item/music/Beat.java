package com.cavetale.mytems.item.music;

import lombok.Value;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note.Tone;
import org.bukkit.Note;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

/**
 * A beat is part of a melody.  All Object members can be null, in
 * which case they defer to the setting of the owning melody. A null
 * tone however refers to a pause.  When played, the beat will respect
 * the melody's key settings!
 */
@Value
public final class Beat {
    public final int ticks; // Ticks to pause after
    public final Instrument instrument;
    public final Tone tone;
    public final Semitone semitone;
    public final Integer octave;

    public static Beat of(int ticks, Instrument instrument, Tone tone, Semitone semitone, int octave) {
        return new Beat(ticks, instrument, tone, semitone, octave);
    }

    public static Beat pause(int ticks) {
        return new Beat(ticks, null, null, null, null);
    }

    public Note bukkitNote(Melody melody) {
        return semitone != null
            ? semitone.bukkitNote(octave, tone)
            : melody.keys.getOrDefault(tone, Semitone.NATURAL).bukkitNote(octave, tone);
    }

    public void play(Melody melody, Player player, Location location) {
        if (tone == null) return;
        player.playSound(location, Sounds.of(instrument != null ? instrument : melody.instrument).sound,
                         SoundCategory.MASTER, 1.0f, Notes.of(bukkitNote(melody)).pitch);
    }

    public void play(Melody melody, Location location) {
        if (tone == null) return;
        location.getWorld().playSound(location, Sounds.of(instrument != null ? instrument : melody.instrument).sound,
                                      SoundCategory.MASTER, 1.0f, Notes.of(bukkitNote(melody)).pitch);
    }

    public boolean countsAs(Melody melody, Touch touch) {
        Note note = bukkitNote(melody);
        return note.getTone() == touch.tone && note.isSharped() == touch.bukkitNote.isSharped();
    }

    public boolean countsAs(Melody melody, Note otherNote) {
        Note note = bukkitNote(melody);
        return note.getTone() == otherNote.getTone() && note.isSharped() == otherNote.isSharped();
    }

    public boolean isPause() {
        return tone == null;
    }

    public Beat withTicks(int newTicks) {
        return new Beat(newTicks, instrument, tone, semitone, octave);
    }

    public Beat cooked(Melody melody) {
        return new Beat(ticks,
                        instrument != null ? instrument : melody.instrument,
                        tone,
                        semitone != null ? semitone : melody.keys.getOrDefault(tone, Semitone.NATURAL),
                        octave);
    }

    @Override
    public String toString() {
        if (tone == null) return "pause(" + ticks + ")";
        return tone
            + (semitone != null ? semitone.toString() : "")
            + (octave != 0 ? octave : "");
    }
}
