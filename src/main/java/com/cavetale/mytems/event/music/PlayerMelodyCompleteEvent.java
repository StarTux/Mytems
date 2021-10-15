package com.cavetale.mytems.event.music;

import com.cavetale.mytems.item.music.InstrumentType;
import com.cavetale.mytems.item.music.Melody;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter @RequiredArgsConstructor
public final class PlayerMelodyCompleteEvent extends Event {
    @Getter protected static HandlerList handlerList = new HandlerList();
    protected final Player player;
    protected final InstrumentType instrumentType;
    protected final Melody melody;
    protected final int score;

    public HandlerList getHandlers() {
        return handlerList;
    }
}
