package com.cavetale.mytems.item.music;

import com.cavetale.core.util.Json;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note.Tone;
import org.bukkit.entity.Player;

@Getter @RequiredArgsConstructor
public final class Melody {
    protected final List<Beat> beats;
    protected final long speed; // millis per tick

    public Melody withSpeed(long newSpeed) {
        return new Melody(beats, newSpeed);
    }

    public int getMaxScore() {
        return beats.size() * 2;
    }

    public MelodyReplay play(Melody melody, Location location, double range) {
        return MelodyReplay.play(this, location, range);
    }

    public MelodyReplay play(Player player) {
        return MelodyReplay.play(this, player);
    }

    public static Builder builder(Instrument instrument, long speed) {
        return new Builder().instrument(instrument).speed(speed);
    }

    public static Builder builder(Instrument instrument) {
        return new Builder().instrument(instrument);
    }

    public static final class Builder {
        private final List<Beat> beats = new ArrayList<>();
        private Instrument defaultInstrument;
        private Map<Tone, Semitone> keyMap = new EnumMap<>(Tone.class);
        private long speed = 50L;

        public Melody build() {
            if (beats.isEmpty()) throw new IllegalStateException("beats is empty");
            return new Melody(List.copyOf(beats), speed);
        }

        public Builder speed(long newSpeed) {
            if (newSpeed < 1) {
                throw new IllegalArgumentException("speed cannot be negative");
            }
            this.speed = newSpeed;
            return this;
        }

        public Builder key(Tone tone, Semitone semitone) {
            keyMap.put(tone, semitone);
            return this;
        }

        public Builder keys(Map<Tone, Semitone> map) {
            keyMap.clear();
            keyMap.putAll(map);
            return this;
        }

        protected Semitone defaultSemitone(Tone tone) {
            return keyMap.getOrDefault(tone, Semitone.NATURAL);
        }

        public Builder instrument(Instrument newInstrument) {
            this.defaultInstrument = newInstrument;
            return this;
        }

        public Builder pause(int ticks) {
            if (beats.isEmpty()) throw new IllegalStateException("beats is empty");
            int index = beats.size() - 1;
            Beat beat = beats.get(index);
            beats.set(index, beat.withTicks(beat.ticks + ticks));
            return this;
        }

        public Builder beat(int ticks, Instrument instrument, Tone tone, Semitone semitone, int octave) {
            beats.add(Beat.of(ticks, instrument, tone, semitone, octave));
            return this;
        }

        public Builder beat(int ticks, Tone tone, Semitone semitone, int octave) {
            beats.add(Beat.of(ticks, defaultInstrument, tone, semitone, octave));
            return this;
        }

        public Builder beat(int ticks, Instrument instrument, Tone tone, int octave) {
            return beat(ticks, instrument, tone, defaultSemitone(tone), octave);
        }

        public Builder beat(int ticks, Tone tone, int octave) {
            return beat(ticks, defaultInstrument, tone, defaultSemitone(tone), octave);
        }

        public Builder beat(Instrument instrument, Tone tone, Semitone semitone, int octave) {
            return beat(0, instrument, tone, semitone, octave);
        }

        public Builder beat(Tone tone, Semitone semitone, int octave) {
            return beat(0, defaultInstrument, tone, semitone, octave);
        }

        public Builder beat(Instrument instrument, Tone tone, int octave) {
            return beat(0, instrument, tone, octave);
        }

        public Builder beat(Tone tone, int octave) {
            return beat(0, defaultInstrument, tone, octave);
        }
    }

    private static final class Tag {
        List<String> beats;
        long speed;
    }

    public String serialize() {
        Tag tag = new Tag();
        tag.beats = new ArrayList<>(beats.size());
        for (Beat beat : beats) {
            tag.beats.add(beat.serialize());
        }
        return Json.serialize(tag);
    }

    public static Melody deserialize(String in) {
        Tag tag = Json.deserialize(in, Tag.class);
        if (tag == null || tag.beats == null) return null;
        List<Beat> beatList = new ArrayList<>(tag.beats.size());
        for (String it : tag.beats) {
            beatList.add(Beat.deserialize(it));
        }
        return new Melody(beatList, tag.speed);
    }
}
