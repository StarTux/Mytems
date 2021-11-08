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
import org.bukkit.plugin.Plugin;

@Getter @RequiredArgsConstructor
public final class Melody {
    protected final Map<Tone, Semitone> keys;
    protected final List<Beat> beats;
    protected final long speed; // millis per tick

    public Melody withSpeed(long newSpeed) {
        return new Melody(keys, beats, newSpeed);
    }

    public int getSize() {
        return beats.size();
    }

    public Beat getBeat(int index) {
        return beats.get(index);
    }

    public int getMaxScore() {
        return beats.size();
    }

    public int getAverageTicks() {
        int total = 0;
        int size = 0;
        for (Beat beat : beats) {
            if (beat.ticks > 0) {
                total += beat.ticks;
                size += 1;
            }
        }
        return total / size;
    }

    public MelodyReplay play(Plugin plugin, Location location) {
        return MelodyReplay.play(plugin, this, location);
    }

    public MelodyReplay play(Plugin plugin, Player player) {
        return MelodyReplay.play(plugin, this, player);
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
            return new Melody(Map.copyOf(keyMap), List.copyOf(beats), speed);
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
            beats.add(Beat.of(ticks, instrument, tone, semitone, true, octave));
            return this;
        }

        public Builder beat(int ticks, Tone tone, Semitone semitone, int octave) {
            beats.add(Beat.of(ticks, defaultInstrument, tone, semitone, true, octave));
            return this;
        }

        public Builder beat(int ticks, Instrument instrument, Tone tone, int octave) {
            beats.add(Beat.of(ticks, instrument, tone, defaultSemitone(tone), false, octave));
            return this;
        }

        public Builder beat(int ticks, Tone tone, int octave) {
            beats.add(Beat.of(ticks, defaultInstrument, tone, defaultSemitone(tone), false, octave));
            return this;
        }

        public Builder beat(Instrument instrument, Tone tone, Semitone semitone, int octave) {
            beats.add(Beat.of(0, instrument, tone, semitone, true, octave));
            return this;
        }

        public Builder beat(Tone tone, Semitone semitone, int octave) {
            beats.add(Beat.of(0, defaultInstrument, tone, semitone, true, octave));
            return this;
        }

        public Builder beat(Instrument instrument, Tone tone, int octave) {
            beats.add(Beat.of(0, instrument, tone, defaultSemitone(tone), false, octave));
            return this;
        }

        public Builder beat(Tone tone, int octave) {
            beats.add(Beat.of(0, defaultInstrument, tone, defaultSemitone(tone), false, octave));
            return this;
        }
    }

    private static final class Tag {
        Map<Tone, Semitone> keys;
        List<String> beats;
        long speed;
    }

    public String serialize() {
        Tag tag = new Tag();
        tag.keys = keys;
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
            beatList.add(Beat.deserialize(tag.keys, it));
        }
        return new Melody(tag.keys, beatList, tag.speed);
    }
}
