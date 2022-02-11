package com.cavetale.mytems;

import com.cavetale.core.util.Json;
import com.cavetale.mytems.item.music.Melody;
import com.cavetale.mytems.item.music.Semitone;
import java.util.HashSet;
import java.util.Set;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.junit.Test;

public final class MytemsTest {
    @Test
    public void test() {
        testDuplicates();
    }

    public void testDuplicates() {
        final Set<Integer> customModelDataSet = new HashSet<>();
        final Set<Character> characterSet = new HashSet<>();
        for (Mytems mytems : Mytems.values()) {
            if (mytems.material == null) {
                throw new IllegalStateException(mytems + ": material is null!");
            }
            if (mytems.customModelData != null) {
                if (mytems.category != MytemsCategory.POCKET_MOB) {
                    if (customModelDataSet.contains(mytems.customModelData)) {
                        throw new IllegalStateException(mytems + ": duplicate custom model data: " + mytems.customModelData);
                    }
                    customModelDataSet.add(mytems.customModelData);
                }
            } else {
                System.out.println("No custom model data: " + mytems);
            }
            if (mytems.character != (char) 0) {
                if (characterSet.contains(mytems.character)) {
                    throw new IllegalStateException(mytems + ": duplicate character: " + Integer.toHexString((int) mytems.character));
                }
                characterSet.add(mytems.character);
            }
        }
    }

    public void testMusic() {
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
        System.out.println(melody.equals(melody2));
        System.out.println(serial.equals(serial2));
        System.out.println(Json.prettyPrint(melody));
    }
}
