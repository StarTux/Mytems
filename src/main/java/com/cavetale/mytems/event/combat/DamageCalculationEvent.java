package com.cavetale.mytems.event.combat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Access the DamageCalculation of an EntityDamageEvent.  In order to
 * have an affect, handled must be set to true!  See EventHandler.
 * DamageCalculation must be valid!
 */
@RequiredArgsConstructor
public final class DamageCalculationEvent extends Event {
    @Getter protected static HandlerList handlerList = new HandlerList();
    private final DamageCalculation calc;
    @Getter private boolean calculated;
    /**
     * Set to true in order to apply the calculation once the event is
     * through.
     */
    @Getter @Setter private boolean handled;
    /**
     * Set to true in order to print the debug report to the console
     * once this event is through.
     */
    @Getter @Setter private boolean shouldPrintDebug;

    public HandlerList getHandlers() {
        return handlerList;
    }

    public DamageCalculation getCalculation() {
        if (!calculated) {
            calc.calculate();
            calculated = true;
        }
        return calc;
    }

    public boolean hasPlayer() {
        return calc.hasPlayer();
    }

    public boolean targetIsPlayer() {
        return calc.targetIsPlayer();
    }

    public boolean attackerIsPlayer() {
        return calc.attackerIsPlayer();
    }

    public EntityDamageEvent getEntityDamageEvent() {
        return calc.getEvent();
    }
}
