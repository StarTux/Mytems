package com.cavetale.mytems.item.acula;

import com.cavetale.mytems.event.combat.DamageCalculationEvent;
import com.cavetale.mytems.event.combat.DamageFactor;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import java.util.List;
import lombok.Getter;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffectType;

@Getter
public final class AculaItemSet implements ItemSet {
    private final String name = "Acula Set";
    private static AculaItemSet instance = null;

    public final SetBonus fullArmor = new SetBonus() {
            @Getter public final AculaItemSet itemSet = AculaItemSet.this;
            @Getter public final int requiredItemCount = 4;
            @Getter public final String name = "Bat Shield";
            @Getter public final String description = "Max Enchantment Protection";

            @Override
            public void onDefendingDamageCalculation(DamageCalculationEvent event) {
                event.setIfApplicable(DamageFactor.PROTECTION, val -> Math.min(val, 0.2));
            }
        };

    public final SetBonus invisibleDamage = new SetBonus() {
            public final int bonusPercentage = 45;
            public final double damageFactor = 1.0 + ((double) bonusPercentage * 0.01);
            @Getter public final AculaItemSet itemSet = AculaItemSet.this;
            @Getter public final int requiredItemCount = 6;
            @Getter public final String name = "Vampiric Mirror";
            @Getter public final String description = "Deal extra " + bonusPercentage + "% damage while invisible";

            @Override
            public void onAttackingDamageCalculation(DamageCalculationEvent event) {
                if (!event.getAttacker().hasPotionEffect(PotionEffectType.INVISIBILITY)) return;
                if (event.getCalculation().getEvent().getCause() != DamageCause.ENTITY_ATTACK) return;
                final double damage = event.getCalculation().getBaseDamage();
                event.getCalculation().setBaseDamage(damage * damageFactor);
                event.setHandled(true);
            }
        };

    @Override
    public List<SetBonus> getSetBonuses() {
        return List.of(fullArmor, invisibleDamage);
    }

    public static AculaItemSet getInstance() {
        if (instance == null) instance = new AculaItemSet();
        return instance;
    }
}
