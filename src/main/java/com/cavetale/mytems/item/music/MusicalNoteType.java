package com.cavetale.mytems.item.music;

import com.cavetale.mytems.Mytems;
import lombok.RequiredArgsConstructor;
import org.bukkit.Note.Tone;

@RequiredArgsConstructor
public enum MusicalNoteType {
    A_NOTE(Mytems.A_NOTE, Tone.A, null),
    A_FLAT_NOTE(Mytems.A_FLAT_NOTE, Tone.A, Semitone.FLAT),
    A_NATURAL_NOTE(Mytems.A_NATURAL_NOTE, Tone.A, Semitone.NATURAL),
    A_SHARP_NOTE(Mytems.A_SHARP_NOTE, Tone.A, Semitone.SHARP),
    B_NOTE(Mytems.B_NOTE, Tone.B, null),
    B_FLAT_NOTE(Mytems.B_FLAT_NOTE, Tone.B, Semitone.FLAT),
    B_NATURAL_NOTE(Mytems.B_NATURAL_NOTE, Tone.B, Semitone.NATURAL),
    B_SHARP_NOTE(Mytems.B_SHARP_NOTE, Tone.B, Semitone.SHARP),
    C_NOTE(Mytems.C_NOTE, Tone.C, null),
    C_FLAT_NOTE(Mytems.C_FLAT_NOTE, Tone.C, Semitone.FLAT),
    C_NATURAL_NOTE(Mytems.C_NATURAL_NOTE, Tone.C, Semitone.NATURAL),
    C_SHARP_NOTE(Mytems.C_SHARP_NOTE, Tone.C, Semitone.SHARP),
    D_NOTE(Mytems.D_NOTE, Tone.D, null),
    D_FLAT_NOTE(Mytems.D_FLAT_NOTE, Tone.D, Semitone.FLAT),
    D_NATURAL_NOTE(Mytems.D_NATURAL_NOTE, Tone.D, Semitone.NATURAL),
    D_SHARP_NOTE(Mytems.D_SHARP_NOTE, Tone.D, Semitone.SHARP),
    E_NOTE(Mytems.E_NOTE, Tone.E, null),
    E_FLAT_NOTE(Mytems.E_FLAT_NOTE, Tone.E, Semitone.FLAT),
    E_NATURAL_NOTE(Mytems.E_NATURAL_NOTE, Tone.E, Semitone.NATURAL),
    E_SHARP_NOTE(Mytems.E_SHARP_NOTE, Tone.E, Semitone.SHARP),
    F_NOTE(Mytems.F_NOTE, Tone.F, null),
    F_FLAT_NOTE(Mytems.F_FLAT_NOTE, Tone.F, Semitone.FLAT),
    F_NATURAL_NOTE(Mytems.F_NATURAL_NOTE, Tone.F, Semitone.NATURAL),
    F_SHARP_NOTE(Mytems.F_SHARP_NOTE, Tone.F, Semitone.SHARP),
    G_NOTE(Mytems.G_NOTE, Tone.G, null),
    G_FLAT_NOTE(Mytems.G_FLAT_NOTE, Tone.G, Semitone.FLAT),
    G_NATURAL_NOTE(Mytems.G_NATURAL_NOTE, Tone.G, Semitone.NATURAL),
    G_SHARP_NOTE(Mytems.G_SHARP_NOTE, Tone.G, Semitone.SHARP),
    ;

    private final Mytems mytems;
    private final Tone tone;
    private final Semitone semitone;

    public String getDisplayName() {
        if (semitone == null) {
            return tone.name();
        } else {
            return tone.name() + semitone.getSymbol();
        }
    }

    public static MusicalNoteType of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }

    public static MusicalNoteType of(Tone tone, Semitone semitone) {
        for (var it : values()) {
            if (it.tone == tone && it.semitone == semitone) return it;
        }
        return null;
    }
}
