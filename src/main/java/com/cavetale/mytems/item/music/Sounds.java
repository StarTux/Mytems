package com.cavetale.mytems.item.music;

import com.cavetale.mytems.MytemsPlugin;
import java.util.EnumMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.bukkit.Instrument;
import org.bukkit.Sound;

/**
 * Map Instrument to Sound.
 */
@RequiredArgsConstructor
public enum Sounds {
    BANJO(Instrument.BANJO, Sound.BLOCK_NOTE_BLOCK_BANJO),
    BASS_DRUM(Instrument.BASS_DRUM, Sound.BLOCK_NOTE_BLOCK_BASEDRUM),
    BASS_GUITAR(Instrument.BASS_GUITAR, Sound.BLOCK_NOTE_BLOCK_BASS),
    BELL(Instrument.BELL, Sound.BLOCK_NOTE_BLOCK_BELL),
    BIT(Instrument.BIT, Sound.BLOCK_NOTE_BLOCK_BIT),
    CHIME(Instrument.CHIME, Sound.BLOCK_NOTE_BLOCK_CHIME),
    COW_BELL(Instrument.COW_BELL, Sound.BLOCK_NOTE_BLOCK_COW_BELL),
    DIDGERIDOO(Instrument.DIDGERIDOO, Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO),
    FLUTE(Instrument.FLUTE, Sound.BLOCK_NOTE_BLOCK_FLUTE),
    GUITAR(Instrument.GUITAR, Sound.BLOCK_NOTE_BLOCK_BASS),
    IRON_XYLOPHONE(Instrument.IRON_XYLOPHONE, Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE),
    PIANO(Instrument.PIANO, Sound.BLOCK_NOTE_BLOCK_HARP),
    PLING(Instrument.PLING, Sound.BLOCK_NOTE_BLOCK_PLING),
    SNARE_DRUM(Instrument.SNARE_DRUM, Sound.BLOCK_NOTE_BLOCK_SNARE),
    STICKS(Instrument.STICKS, Sound.BLOCK_NOTE_BLOCK_HAT),
    XYLOPHONE(Instrument.XYLOPHONE, Sound.BLOCK_NOTE_BLOCK_XYLOPHONE);

    protected static final Map<Instrument, Sounds> INSTRUMENT_MAP = new EnumMap<>(Instrument.class);

    public final Instrument instrument;
    public final Sound sound;

    static {
        for (Sounds sounds : Sounds.values()) {
            INSTRUMENT_MAP.put(sounds.instrument, sounds);
        }
        for (Instrument instrument : Instrument.values()) {
            if (!INSTRUMENT_MAP.containsKey(instrument)) {
                MytemsPlugin.getInstance().getLogger().warning("Sounds: Missing instrument: " + instrument);
            }
        }
    }

    public static Sounds of(Instrument instrument) {
        return INSTRUMENT_MAP.get(instrument);
    }
}
