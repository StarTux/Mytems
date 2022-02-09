package com.cavetale.mytems.event.combat;

import com.cavetale.mytems.MytemsPlugin;
import java.util.ArrayList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
    private ArrayList<PostDamageAction> postDamageActions;
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

    public boolean isCombat() {
        return calc.isCombat();
    }

    public boolean isApplicable(DamageFactor factor) {
        return calc.isApplicable(factor);
    }

    public boolean setIfApplicable(DamageFactor factor, double value) {
        if (!calc.isApplicable(factor)) return false;
        getCalculation().set(factor, value);
        handled = true;
        return true;
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

    public Player getTargetPlayer() {
        return calc.getTargetPlayer();
    }

    public Player getAttackerPlayer() {
        return calc.getAttackerPlayer();
    }

    public LivingEntity getTarget() {
        return calc.getTarget();
    }

    public LivingEntity getAttacker() {
        return calc.getAttacker();
    }

    public void addPostDamageAction(boolean ignoreCancelled, Runnable callback) {
        if (postDamageActions == null) postDamageActions = new ArrayList<>();
        postDamageActions.add(new PostDamageAction(ignoreCancelled, callback));
    }

    /**
     * Internal use only!
     */
    public void schedulePostDamageActions() {
        if (postDamageActions == null) return;
        Bukkit.getScheduler().runTask(MytemsPlugin.getInstance(), () -> {
                for (PostDamageAction it : postDamageActions) {
                    if (it.ignoreCancelled() && calc.getEvent().isCancelled()) continue;
                    it.callback().run();
                }
            });
    }
}
