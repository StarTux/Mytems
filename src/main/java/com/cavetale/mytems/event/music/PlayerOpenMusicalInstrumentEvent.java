package com.cavetale.mytems.event.music;

import com.cavetale.mytems.item.music.InstrumentType;
import com.cavetale.mytems.item.music.Melody;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter @RequiredArgsConstructor
public final class PlayerOpenMusicalInstrumentEvent extends Event {
    @Getter protected static HandlerList handlerList = new HandlerList();
    protected final Player player;
    protected final InstrumentType instrumentType;
    @Setter protected Melody heroMelody;

    public boolean isHeroMode() {
        return heroMelody != null;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }
}
