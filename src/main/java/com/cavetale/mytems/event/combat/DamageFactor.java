package com.cavetale.mytems.event.combat;

import java.util.function.BiConsumer;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;

/**
 * This enum wraps the (deprecated) DamageModifier.  Selected are only
 * the ones which represent a factor which modifies the damage of a
 * combat event.
 */
@RequiredArgsConstructor @SuppressWarnings("deprecation")
public enum DamageFactor {
    HELMET(DamageModifier.HARD_HAT,
           DamageCalculation::setHelmetFactor,
           DamageCalculation::getHelmetFactor),
    SHIELD(DamageModifier.BLOCKING,
           DamageCalculation::setShieldFactor,
           DamageCalculation::getShieldFactor),
    ARMOR(DamageModifier.ARMOR,
          DamageCalculation::setArmorFactor,
          DamageCalculation::getArmorFactor),
    RESISTANCE(DamageModifier.RESISTANCE,
               DamageCalculation::setResistanceFactor,
               DamageCalculation::getResistanceFactor),
    PROTECTION(DamageModifier.MAGIC,
               DamageCalculation::setProtectionFactor,
               DamageCalculation::getProtectionFactor),
    FREEZING(DamageModifier.FREEZING,
             DamageCalculation::setFreezingFactor,
             DamageCalculation::getFreezingFactor),
    INVULNERABLE(DamageModifier.INVULNERABILITY_REDUCTION,
             DamageCalculation::setInvulnerableFactor,
             DamageCalculation::getInvulnerableFactor),
    ;

    public final DamageModifier damageModifier;
    public final BiConsumer<DamageCalculation, Double> setter;
    public final Function<DamageCalculation, Double> getter;
}
