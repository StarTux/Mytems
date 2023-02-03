package com.cavetale.mytems.item.music;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter @RequiredArgsConstructor
public final class PlayerPlayInstrumentEvent extends Event {
    private final Player player;
    private final InstrumentType instrumentType;
    private final Touch touch;

    public Note getNote() {
        return touch.bukkitNote;
    }

    public Note.Tone getTone() {
        return touch.tone;
    }

    public Semitone getSemitone() {
        return touch.semitone;
    }

    /** Required by event. */
    @Getter protected static HandlerList handlerList = new HandlerList();

    /** Required by event. */
    public HandlerList getHandlers() {
        return handlerList;
    }
}
