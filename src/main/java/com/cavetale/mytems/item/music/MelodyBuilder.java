package com.cavetale.mytems.item.music;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.bukkit.Instrument;
import org.bukkit.Note.Tone;

public final class MelodyBuilder {
    private final List<Beat> beats = new ArrayList<>();
    private Instrument defaultInstrument;
    private Map<Tone, Semitone> keyMap = new EnumMap<>(Tone.class);
    private long speed = 50L;
    private List<MelodyBuilder> extra;
    private MelodyBuilder parent;

    public Melody build() {
        if (beats.isEmpty()) throw new IllegalStateException("beats is empty");
        List<Melody> extraList;
        if (extra != null && !extra.isEmpty()) {
            extraList = new ArrayList<>(extra.size());
            for (MelodyBuilder it : extra) {
                extraList.add(it.build());
            }
        } else {
            extraList = null;
        }
        return new Melody(defaultInstrument, Map.copyOf(keyMap), List.copyOf(beats), speed, extraList);
    }

    public MelodyBuilder speed(long newSpeed) {
        if (newSpeed < 1) {
            throw new IllegalArgumentException("speed cannot be negative");
        }
        this.speed = newSpeed;
        return this;
    }

    public MelodyBuilder key(Tone tone, Semitone semitone) {
        keyMap.put(tone, semitone);
        return this;
    }

    public MelodyBuilder keys(Map<Tone, Semitone> map) {
        keyMap.clear();
        keyMap.putAll(map);
        return this;
    }

    public MelodyBuilder instrument(Instrument newInstrument) {
        this.defaultInstrument = newInstrument;
        return this;
    }

    public MelodyBuilder pause(int ticks) {
        beats.add(Beat.pause(ticks));
        return this;
    }

    public MelodyBuilder beat(int ticks, Instrument instrument, Tone tone, Semitone semitone, int octave) {
        beats.add(Beat.of(ticks, instrument, tone, semitone, octave));
        return this;
    }

    public MelodyBuilder beat(int ticks, Tone tone, Semitone semitone, int octave) {
        beats.add(Beat.of(ticks, null, tone, semitone, octave));
        return this;
    }

    public MelodyBuilder beat(int ticks, Instrument instrument, Tone tone, int octave) {
        beats.add(Beat.of(ticks, instrument, tone, null, octave));
        return this;
    }

    public MelodyBuilder beat(int ticks, Tone tone, int octave) {
        beats.add(Beat.of(ticks, null, tone, null, octave));
        return this;
    }

    public MelodyBuilder beat(Instrument instrument, Tone tone, Semitone semitone, int octave) {
        beats.add(Beat.of(0, instrument, tone, semitone, octave));
        return this;
    }

    public MelodyBuilder beat(Tone tone, Semitone semitone, int octave) {
        beats.add(Beat.of(0, null, tone, semitone, octave));
        return this;
    }

    public MelodyBuilder beat(Instrument instrument, Tone tone, int octave) {
        beats.add(Beat.of(0, instrument, tone, null, octave));
        return this;
    }

    public MelodyBuilder beat(Tone tone, int octave) {
        beats.add(Beat.of(0, null, tone, null, octave));
        return this;
    }

    public MelodyBuilder extra(Instrument instrument) {
        if (extra == null) extra = new ArrayList<>();
        MelodyBuilder result = new MelodyBuilder().instrument(instrument).keys(keyMap).speed(speed);
        result.parent = this;
        extra.add(result);
        return result;
    }

    public MelodyBuilder extra(Consumer<MelodyBuilder> consumer) {
        if (extra == null) extra = new ArrayList<>();
        MelodyBuilder result = new MelodyBuilder().instrument(defaultInstrument).keys(keyMap).speed(speed);
        result.parent = this;
        extra.add(result);
        consumer.accept(result);
        return this;
    }

    public MelodyBuilder parent() {
        return this.parent;
    }
}
