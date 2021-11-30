package com.cavetale.mytems.item.music;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Value;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Note.Tone;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * A melody which can be replayed and serialized.
 */
@Value
public final class Melody {
    protected final Instrument instrument;
    protected final Map<Tone, Semitone> keys;
    protected final List<Beat> beats;
    protected final long speed; // millis per tick
    protected final List<Melody> extra;

    public Melody withSpeed(long newSpeed) {
        return new Melody(instrument, keys, beats, newSpeed, extra.stream()
                          .map(m -> m.withSpeed(newSpeed))
                          .collect(Collectors.toList()));
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

    public static MelodyBuilder builder(Instrument instrument, long speed) {
        return new MelodyBuilder().instrument(instrument).speed(speed);
    }

    public static MelodyBuilder builder(Instrument instrument) {
        return new MelodyBuilder().instrument(instrument);
    }
}
