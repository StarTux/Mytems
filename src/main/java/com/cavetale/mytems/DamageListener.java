package com.cavetale.mytems;

import com.cavetale.mytems.event.combat.DamageCalculation;
import com.cavetale.mytems.event.combat.DamageCalculationEvent;
import com.cavetale.mytems.gear.SetBonus;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    
    private final Set<UUID> damageCalculationDebugPlayers = new HashSet<>();
    private DamageCalculationEvent damageCalculationEvent;

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    private void onEntityDamageCalculateLow(EntityDamageEvent event) {
        DamageCalculation calc = new DamageCalculation(event);
        if (!calc.isValid()) return;
        damageCalculationEvent = new DamageCalculationEvent(calc);
        damageCalculationEvent.callEvent();
        if (calc.attackerIsPlayer() && damageCalculationDebugPlayers.contains(calc.getAttackerPlayer().getUniqueId())) {
            damageCalculationEvent.setShouldPrintDebug(true);
        }
        if (calc.targetIsPlayer() && damageCalculationDebugPlayers.contains(calc.getTargetPlayer().getUniqueId())) {
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
        if (damageCalculationEvent.isShouldPrintDebug()) {
            damageCalculationEvent.getCalculation().debugPrint();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
    private void onEntityDamageCalculateMonitor(EntityDamageEvent event) {
        if (damageCalculationEvent == null) {
            return;
        }
        damageCalculationEvent.runPostDamageActions();
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
