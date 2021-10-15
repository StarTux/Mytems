package com.cavetale.mytems.item.music;

import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note.Tone;
import org.bukkit.Note;
import org.bukkit.entity.Player;

/**
 * A Beat is a key touch dictated by a plugin. As such, it as more
 * information and is part of a Melody.
 */
public final class Beat {
    public final int ticks; // Ticks to pause after
    public final Instrument instrument;
    public final Tone tone;
    public final Semitone semitone;
    public final Note bukkitNote;
    public final String displayString;

    private Beat(final int ticks, final Instrument instrument,
                 final Tone tone, final Semitone semitone, final Note bukkitNote) {
        this.ticks = ticks;
        this.instrument = instrument;
        this.tone = tone;
        this.semitone = semitone;
        this.bukkitNote = bukkitNote;
        this.displayString = tone.name() + semitone.symbol;
    }

    public static Beat of(int ticks, Instrument instrument, Tone tone, Semitone semitone, int octave) {
        Note bukkitNote = semitone.apply(Note.natural(octave, tone));
        return bukkitNote.getTone() == tone || bukkitNote.isSharped()
            ? new Beat(ticks, instrument, tone, semitone, bukkitNote)
            : new Beat(ticks, instrument, bukkitNote.getTone(), Semitone.NATURAL, bukkitNote);
    }

    public void play(Player player, Location location) {
        player.playNote(location, instrument, bukkitNote);
    }

    public void play(Location location, double range) {
        for (Player player : location.getWorld().getNearbyEntitiesByType(Player.class, location, range, range, range)) {
            play(player, location);
        }
    }

    public boolean countsAs(Touch touch) {
        return bukkitNote.getTone() == touch.bukkitNote.getTone()
            && bukkitNote.isSharped() == touch.bukkitNote.isSharped();
    }

    public Beat withTicks(int newTicks) {
        return new Beat(newTicks, instrument, tone, semitone, bukkitNote);
    }

    public String serialize() {
        return instrument.name().toLowerCase()
            + "," + ticks
            + "," + tone + semitone.serialize() + bukkitNote.getOctave();
    }

    public static Beat deserialize(String in) {
        String[] toks = in.split(",");
        if (toks.length != 3) {
            throw new IllegalArgumentException("toks.length != 3");
        }
        final Instrument instrument = Instrument.valueOf(toks[0].toUpperCase());
        final int ticks = Integer.parseInt(toks[1]);
        final Tone tone = Tone.valueOf(toks[2].substring(0, 1));
        final Semitone semitone = toks[2].length() == 3
            ? Semitone.deserialize(toks[2].substring(1, 2))
            : Semitone.NATURAL;
        final int octave = Integer.parseInt(toks[2].substring(toks[2].length() - 1, toks[2].length()));
        return Beat.of(ticks, instrument, tone, semitone, octave);
    }
}
