package com.cavetale.mytems;

import com.cavetale.mytems.event.combat.DamageCalculation;
import com.cavetale.mytems.event.combat.DamageCalculationEvent;
import com.cavetale.mytems.gear.SetBonus;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@Getter
@RequiredArgsConstructor
public final class DamageListener implements Listener {
    private final MytemsPlugin plugin;

    public void enable() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private UUID debugPlayerIn;
    private UUID debugPlayerOut;
    private DamageCalculationEvent damageCalculationEvent;

    public void clearDebugPlayer() {
        debugPlayerIn = null;
        debugPlayerOut = null;
    }

    public void setDebugPlayer(UUID in, UUID out) {
        debugPlayerIn = in;
        debugPlayerOut = out;
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    private void onEntityDamageCalculateLow(EntityDamageEvent event) {
        DamageCalculation calc = new DamageCalculation(event);
        if (!calc.isValid()) return;
        damageCalculationEvent = new DamageCalculationEvent(calc);
        damageCalculationEvent.callEvent();
        if (debugPlayerOut != null && calc.attackerIsPlayer() && debugPlayerOut.equals(calc.getAttackerPlayer().getUniqueId())) {
            damageCalculationEvent.setShouldPrintDebug(true);
        }
        if (debugPlayerIn != null && calc.targetIsPlayer() && debugPlayerIn.equals(calc.getTargetPlayer().getUniqueId())) {
            damageCalculationEvent.setShouldPrintDebug(true);
        }
        if (!damageCalculationEvent.isHandled() && !damageCalculationEvent.isShouldPrintDebug()) {
            damageCalculationEvent = null;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    private void onEntityDamageCalculateHighest(EntityDamageEvent event) {
        if (damageCalculationEvent == null) {
            return;
        }
        if (damageCalculationEvent.getEntityDamageEvent() != event) {
            damageCalculationEvent = null;
            return;
        }
        if (damageCalculationEvent.isHandled() && !event.isCancelled()) {
            damageCalculationEvent.getCalculation().apply();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    private void onEntityDamageCalculateMonitor(EntityDamageEvent event) {
        if (damageCalculationEvent == null) {
            return;
        }
        damageCalculationEvent.runPostDamageActions();
        if (damageCalculationEvent.isShouldPrintDebug()) {
            damageCalculationEvent.getCalculation().debugPrint();
        }
        damageCalculationEvent = null;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    private void onDamageCalculation(DamageCalculationEvent event) {
        // Defender
        if (event.targetIsPlayer()) {
            Player target = event.getTargetPlayer();
            for (SetBonus setBonus : plugin.sessions.of(target).getEquipment().getSetBonuses()) {
                setBonus.onDefendingDamageCalculation(event);
            }
        }
        if (event.getTarget() != null) {
            for (EquipmentSlot slot : List.of(EquipmentSlot.HAND, EquipmentSlot.OFF_HAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)) {
                ItemStack itemStack = event.getTarget().getEquipment().getItem(slot);
                Mytems mytems = Mytems.forItem(itemStack);
                if (mytems == null) continue;
                mytems.getMytem().onDefendingDamageCalculation(event, itemStack, slot);
            }
        }
        // Attacker
        if (event.attackerIsPlayer()) {
            Player attacker = event.getAttackerPlayer();
            for (SetBonus setBonus : plugin.sessions.of(attacker).getEquipment().getSetBonuses()) {
                setBonus.onAttackingDamageCalculation(event);
            }
        }
        if (event.getAttacker() != null) {
            for (EquipmentSlot slot : List.of(EquipmentSlot.HAND, EquipmentSlot.OFF_HAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)) {
                ItemStack itemStack = event.getAttacker().getEquipment().getItem(slot);
                Mytems mytems = Mytems.forItem(itemStack);
                if (mytems == null) continue;
                mytems.getMytem().onAttackingDamageCalculation(event, itemStack, slot);
            }
        }
    }
}
