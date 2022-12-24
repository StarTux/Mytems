package com.cavetale.mytems.item.santa;

import com.cavetale.mytems.event.combat.DamageCalculationEvent;
import com.cavetale.mytems.event.combat.DamageFactor;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Getter
public final class SantaItemSet implements ItemSet {
    private static SantaItemSet instance = null;
    private final String name = "Santa Set";
    List<SetBonus> setBonuses;

    @Getter @RequiredArgsConstructor
    public static final class CookieBonus implements SetBonus {
        protected final SantaItemSet itemSet;
        protected final int requiredItemCount;
        protected final String name = "Cookie Crunch";
        protected final String description = "Eating cookies boosts health of players around you";

        @Override
        public void onPlayerItemConsume(PlayerItemConsumeEvent event, Player player, ItemStack item) {
            if (item.getType() == Material.COOKIE) {
                final double r = 12;
                for (Player nearby : player.getLocation().getNearbyEntitiesByType(Player.class, r, r, r)) {
                    final PotionEffect oldHealthBoost = nearby.getPotionEffect(PotionEffectType.HEALTH_BOOST);
                    final int healthBoostAmp = oldHealthBoost != null
                        ? Math.min(oldHealthBoost.getAmplifier() + 1, 4)
                        : 0;
                    nearby.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 20 * 45, healthBoostAmp, true, false, true));
                    nearby.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 5, healthBoostAmp, true, false, true));
                    nearby.getWorld().playSound(nearby.getLocation(), Sound.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 1.0f, 1.0f);
                }
            }
        }
    }

    @Getter @RequiredArgsConstructor
    public static final class FullDefense implements SetBonus {
        protected final SantaItemSet itemSet;
        protected final int requiredItemCount;
        protected final String name = "Santa Skin";
        protected final String description = "Maximum enchantment protection";

        @Override
        public void onDefendingDamageCalculation(DamageCalculationEvent event) {
            event.setIfApplicable(DamageFactor.PROTECTION, 0.2f);
        }
    }

    public static SantaItemSet getInstance() {
        if (instance == null) instance = new SantaItemSet();
        return instance;
    }

    private SantaItemSet() {
        setBonuses = List.of(new FullDefense(this, 2),
                             new CookieBonus(this, 4));
    }
}
