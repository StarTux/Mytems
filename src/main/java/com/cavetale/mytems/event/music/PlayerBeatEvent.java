package com.cavetale.mytems.event.music;

import com.cavetale.mytems.item.music.Beat;
import com.cavetale.mytems.item.music.InstrumentType;
import com.cavetale.mytems.item.music.Melody;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter @RequiredArgsConstructor
public final class PlayerBeatEvent extends Event {
    @Getter protected static HandlerList handlerList = new HandlerList();
    protected final Player player;
    protected final InstrumentType instrumentType;
    protected final Melody melody;
    protected final Beat beat; // nullable
    protected final Action action;

    public boolean hasBeat() {
        return beat != null;
    }

    @RequiredArgsConstructor
    public enum Action {
        HIT_BEAT(true, true),
        PLAY_BEAT_EARLY(false, true),
        PLAY_OUT_OF_TUNE(false, false),
        MISS_BEAT(false, true);

        public final boolean good;
        public final boolean hasBeat;

        public void call(Player thePlayer, InstrumentType theInstrumentType, Melody theMelody, Beat theBeat) {
            if (theBeat != null && !hasBeat) {
                throw new IllegalArgumentException(this + " does not allow a beat!");
            }
            if (theBeat == null && hasBeat) {
                throw new IllegalArgumentException(this + " requires a beat!");
            }
            new PlayerBeatEvent(thePlayer, theInstrumentType, theMelody, theBeat, this).callEvent();
        }

        public void call(Player thePlayer, InstrumentType theInstrumentType, Melody theMelody) {
            call(thePlayer, theInstrumentType, theMelody, null);
        }
    }

    public HandlerList getHandlers() {
        return handlerList;
    }
}
