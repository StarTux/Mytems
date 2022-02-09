package com.cavetale.mytems.event.combat;

import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Text;
import java.util.logging.Logger;
import lombok.Data;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Turn the cheeseburger that is Minecraft damage back into the cow
 * which is a series of percentage modifiers.
 */
@Data @SuppressWarnings("deprecation")
public final class DamageCalculation {
    @NonNull private final EntityDamageEvent event;
    private final LivingEntity attacker;
    private final LivingEntity target;
    private final Projectile projectile;
    private double baseDamage;
    // All factors are the amount that passes through!
    private double hardHatFactor;
    private double blockingFactor;
    private double armorFactor;
    private double resistanceFactor;
    private double magicFactor;
    /**
     * The absorption value is special because it is derived after all
     * other damage calculations are done, based on the absorption
     * amount of the target entity.
     */
    private double absorption; // flat

    public DamageCalculation(final EntityDamageEvent event) {
        this.event = event;
        this.target = event.getEntity() instanceof LivingEntity living ? living : null;
        if (event instanceof EntityDamageByEntityEvent event2) {
            if (event2.getDamager() instanceof LivingEntity living) {
                this.attacker = living;
                this.projectile = null;
            } else if (event2.getDamager() instanceof Projectile proj) {
                this.projectile = proj;
                this.attacker = proj.getShooter() instanceof LivingEntity living ? living : null;
            } else {
                this.attacker = null;
                this.projectile = null;
            }
        } else {
            this.projectile = null;
            this.attacker = null;
        }
    }

    public double get(DamageFactor factor) {
        return factor.getter.apply(this);
    }

    public double getReduction(DamageFactor factor) {
        return 1.0 - get(factor);
    }

    public void set(DamageFactor factor, double value) {
        factor.setter.accept(this, value);
    }

    public boolean isApplicable(DamageFactor factor) {
        return event.isApplicable(factor.damageModifier);
    }

    public double getTotalFactor() {
        return hardHatFactor
            * blockingFactor
            * armorFactor
            * resistanceFactor
            * magicFactor;
    }

    public double getTotalDamage() {
        return baseDamage * getTotalFactor() - absorption;
    }

    /**
     * Pull all the data from the given event.
     */
    public void calculate() {
        double currentDamage = event.getDamage(DamageModifier.BASE);
        this.baseDamage = currentDamage;
        for (DamageFactor it : DamageFactor.values()) {
            if (!event.isApplicable(it.damageModifier)) {
                set(it, 1.0);
                continue;
            }
            double reduction = event.getDamage(it.damageModifier);
            double factor = currentDamage > 0
                ? (currentDamage + reduction) / currentDamage
                : 0.0;
            currentDamage += reduction;
            set(it, factor);
        }
        this.absorption = -event.getDamage(DamageModifier.ABSORPTION);
    }

    public void apply() {
        event.setDamage(DamageModifier.BASE, baseDamage);
        double currentDamage = baseDamage;
        for (DamageFactor it : DamageFactor.values()) {
            if (!event.isApplicable(it.damageModifier)) continue;
            double factor = get(it);
            double reduction = (1.0 - factor) * currentDamage;
            currentDamage *= factor;
            event.setDamage(it.damageModifier, -reduction);
        }
        this.absorption = target == null ? 0.0 : Math.min(currentDamage, target.getAbsorptionAmount());
        event.setDamage(DamageModifier.ABSORPTION, -absorption);
    }

    public double getFlatDamage(DamageFactor until) {
        double currentDamage = baseDamage;
        for (DamageFactor it : DamageFactor.values()) {
            currentDamage *= get(it);
            if (it == until) break;
        }
        return currentDamage;
    }

    /**
     * This should equal EntityDamageEvent.get(by.damageModifier),
     * negative!
     */
    public double getFlatDamageReduction(DamageFactor by) {
        double currentDamage = baseDamage;
        for (DamageFactor it : DamageFactor.values()) {
            double factor = get(it);
            if (by == it) return (1.0 - factor) * currentDamage;
            currentDamage *= factor;
        }
        return 0.0;
    }

    public boolean isValid() {
        return target != null;
    }

    public boolean isCombat() {
        return attacker != null && target != null;
    }

    public boolean isPvp() {
        return attacker instanceof Player && target instanceof Player;
    }

    public boolean hasProjectile() {
        return projectile != null;
    }

    public boolean attackerIsPlayer() {
        return attacker instanceof Player;
    }

    public boolean targetIsPlayer() {
        return target instanceof Player;
    }

    public Player getTargetPlayer() {
        return target instanceof Player player ? player : null;
    }

    public Player getAttackerPlayer() {
        return attacker instanceof Player player ? player : null;
    }

    public boolean isPvE() {
        return isCombat() && attackerIsPlayer() && !targetIsPlayer();
    }

    public boolean isEvE() {
        return isCombat() && !attackerIsPlayer() && targetIsPlayer();
    }

    public boolean hasPlayer() {
        return attackerIsPlayer() || targetIsPlayer();
    }

    public boolean hasExactlyOnePlayer() {
        return isCombat() && (attackerIsPlayer() ^ targetIsPlayer());
    }

    public boolean isBlocking() {
        return blockingFactor == 0.0;
    }

    public boolean setIfApplicable(DamageFactor factor, double value) {
        if (!isApplicable(factor)) return false;
        set(factor, value);
        return true;
    }

    private static String fmt(double in) {
        return String.format("%.2f", in);
    }

    public void debugPrint() {
        Logger logger = MytemsPlugin.getInstance().getLogger();
        logger.info("DMG " + (attacker != null ? Text.toCamelCase(attacker.getType(), "") : "?")
                    + (hasProjectile() ? "->" : "v")
                    + (target != null ? Text.toCamelCase(target.getType(), "") : "?")
                    + " " + Text.toCamelCase(event.getCause(), ""));
        logger.info("Base " + fmt(baseDamage));
        for (DamageFactor it : DamageFactor.values()) {
            if (!event.isApplicable(it.damageModifier)) continue;
            // if (get(it) == 1.0 && event.getDamage(it.damageModifier) == 0.0) continue;
            double my = getFlatDamageReduction(it);
            double its = event.getDamage(it.damageModifier);
            double value = get(it);
            ChatColor color;
            if (value >= 1.0) {
                color = ChatColor.DARK_GRAY;
            } else if (value >= 0.75) {
                color = ChatColor.DARK_RED;
            } else if (value >= 0.5) {
                color = ChatColor.RED;
            } else if (value >= 0.25) {
                color = ChatColor.GOLD;
            } else {
                color = ChatColor.AQUA;
            }
            logger.info(color + (Math.abs(my + its) < 0.001 ? "OK" : "WRONG")
                        + " " + Text.toCamelCase(it, "")
                        + " " + fmt(Math.round(value * 100.0)) + "%"
                        + " => " + fmt(getFlatDamage(it))
                        + " (" + fmt(my) + "|" + fmt(its) + ")");
        }
        logger.info((Math.abs(absorption + event.getDamage(DamageModifier.ABSORPTION)) < 0.001 ? "OK" : "WRONG")
                    + " Absorption"
                    + " " + fmt(absorption) + "|" + fmt(event.getDamage(DamageModifier.ABSORPTION)));
        logger.info((Math.abs(getTotalDamage() - event.getFinalDamage()) < 0.001 ? "OK" : "WRONG")
                    + " Final"
                    + " => " + fmt(getTotalDamage()) + "|" + fmt(event.getFinalDamage()));
        logger.info("========================================");
    }
}
