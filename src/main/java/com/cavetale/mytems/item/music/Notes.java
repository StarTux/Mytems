package com.cavetale.mytems.item.music;

import org.bukkit.Note;

/**
 * Map Notes to pitches.
 * There are exactly 25 entries, covering all notes in range [0,24].
 * The names of this enum don not actually matter. What matters is the
 * correct order and accurate pitches.
 */
public enum Notes {
    F_SHARP_0(0.5f),
    G_NATUR_0(0.529732f),
    G_SHARP_0(0.561231f),
    A_NATUR_0(0.594604f),
    A_SHARP_0(0.629961f),
    B_NATUR_0(0.667420f),
    C_NATUR_0(0.707107f),
    C_SHARP_0(0.749154f),
    D_NATUR_0(0.793701f),
    D_SHARP_0(0.840896f),
    E_NATUR_0(0.890899f),
    F_NATUR_0(0.943874f),
    F_SHARP_1(1.0f),
    G_NATUR_1(1.059463f),
    G_SHARP_1(1.122462f),
    A_NATUR_1(1.189207f),
    A_SHARP_1(1.259921f),
    B_NATUR_1(1.334840f),
    C_NATUR_1(1.414214f),
    C_SHARP_1(1.498307f),
    D_NATUR_1(1.587401f),
    D_SHARP_1(1.681793f),
    E_NATUR_1(1.781797f),
    F_NATUR_1(1.887749f),
    F_SHARP_2(2.0f);

    public final Note note;
    public final float pitch;

    Notes(final float pitch) {
        this.note = new Note(ordinal());
        this.pitch = pitch;
    }

    @SuppressWarnings("deprecation")
    public static Notes of(Note note) {
        return Notes.values()[(int) note.getId()];
    }
}
