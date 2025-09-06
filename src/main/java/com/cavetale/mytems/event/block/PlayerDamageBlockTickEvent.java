package com.cavetale.mytems.event.block;

import java.time.Instant;
import lombok.Data;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called once per tick by BlockDamageSession as long as the player is
 * holding down the mining button on a block.
 *
 * Cancelling this event will cancel any future ticks on the same
 * block until the player actually damages any block again, see
 * BlockDamageSession.
 */
@Data
public final class PlayerDamageBlockTickEvent extends Event implements Cancellable {
    @Getter private static HandlerList handlerList = new HandlerList();
    private final Player player;
    private final Block block;
    private final int damageTicks;
    private final Instant startTime;
    private boolean cancelled;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
