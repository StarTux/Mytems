package com.cavetale.mytems.item.music;

import com.cavetale.core.util.Json;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public final class MusicTest {
    @Test
    public void test() {
        Melody melody = Melody.builder(Instrument.PIANO, 50L)
            .key(Note.Tone.A, Semitone.SHARP)
            .beat(20, Note.Tone.A, 1)
            .extra(extra -> {
                    extra.instrument(Instrument.PLING)
                        .beat(20, Note.Tone.B, 1);
                })
            .build();
        String serial = Json.serialize(melody);
        Melody melody2 = Json.deserialize(serial, Melody.class);
        String serial2 = Json.serialize(melody2);
        assertEquals(melody, melody2);
        assertEquals(serial, serial2);
    }
}
