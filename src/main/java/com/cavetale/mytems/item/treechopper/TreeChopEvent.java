package com.cavetale.mytems.item.treechopper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called after the tree has been determined but before any actual
 * chopping happens.  Unless the status is SUCCESSFUL, no chopping
 * will happen.
 * To get the ChoppedType, use: event.getTreeChop().guessChoppedType().
 */
@Getter @RequiredArgsConstructor
public final class TreeChopEvent extends Event {
    @Getter protected static HandlerList handlerList = new HandlerList();
    protected final Player player;
    protected final TreeChop treeChop;
    protected final TreeChopStatus status;

    public boolean isSuccessful() {
        return status == TreeChopStatus.SUCCESS;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }
}
