package com.cavetale.mytems.item.music;

import com.cavetale.mytems.Mytems;
import lombok.RequiredArgsConstructor;
import org.bukkit.Instrument;

@RequiredArgsConstructor
public enum InstrumentType {
    ANGELIC_HARP(Mytems.ANGELIC_HARP, Instrument.PIANO),
    BANJO(Mytems.BANJO, Instrument.BANJO),
    BIT_BOY(Mytems.BIT_BOY, Instrument.BIT),
    CLICKS_AND_STICKS(Mytems.CLICKS_AND_STICKS, Instrument.STICKS),
    COW_BELL(Mytems.COW_BELL, Instrument.COW_BELL),
    ELECTRIC_GUITAR(Mytems.ELECTRIC_GUITAR, Instrument.BASS_GUITAR),
    ELECTRIC_PIANO(Mytems.ELECTRIC_PIANO, Instrument.PLING),
    GUITAR(Mytems.GUITAR, Instrument.GUITAR),
    IRON_XYLOPHONE(Mytems.IRON_XYLOPHONE, Instrument.IRON_XYLOPHONE),
    MUSICAL_BELL(Mytems.MUSICAL_BELL, Instrument.BELL),
    PAN_FLUTE(Mytems.PAN_FLUTE, Instrument.FLUTE),
    POCKET_PIANO(Mytems.POCKET_PIANO, Instrument.PIANO),
    RAINBOW_XYLOPHONE(Mytems.RAINBOW_XYLOPHONE, Instrument.XYLOPHONE),
    SNARE_DRUM(Mytems.SNARE_DRUM, Instrument.SNARE_DRUM),
    TRIANGLE(Mytems.TRIANGLE, Instrument.CHIME),
    WOODEN_DRUM(Mytems.WOODEN_DRUM, Instrument.BASS_DRUM),
    WOODEN_HORN(Mytems.WOODEN_HORN, Instrument.DIDGERIDOO),
    WOODEN_LUTE(Mytems.WOODEN_LUTE, Instrument.GUITAR),
    WOODEN_OCARINA(Mytems.WOODEN_OCARINA, Instrument.FLUTE);

    public final Mytems mytems;
    public final Instrument instrument;
}
