package com.cavetale.mytems.event.combat;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Text;
import java.util.logging.Logger;
import lombok.Data;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Explosive;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;

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
    private Block damagerBlock;
    private Explosive explosive;
    private double baseDamage;
    // All factors are the amount that passes through!
    private double helmetFactor;
    private double shieldFactor;
    private double armorFactor;
    private double resistanceFactor;
    private double protectionFactor;
    private double freezingFactor;
    private double invulnerableFactor;
    /**
     * The absorption value is special because it is derived after all
     * other damage calculations are done, based on the absorption
     * amount of the target entity.
     */
    private double absorption; // flat
    // Custom
    private BaseDamageModifier baseDamageModifier;
    private FinalDamageModifier finalDamageModifier;

    public DamageCalculation(final EntityDamageEvent event) {
        this.event = event;
        this.target = event.getEntity() instanceof LivingEntity living ? living : null;
        if (event instanceof EntityDamageByEntityEvent event2) {
            if (event2.getDamager() instanceof Explosive it) {
                this.explosive = it;
            }
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
        } else if (event instanceof EntityDamageByBlockEvent blockEvent) {
            this.damagerBlock = blockEvent.getDamager();
            this.projectile = null;
            this.attacker = null;
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

    /**
     * We must here manually rule out several damage causes because
     * the definition of EntityDamageEvent::isApplicable is rather
     * generous.  A handful of damage causes can be generally ruled
     * out.
     */
    public boolean isApplicable(DamageFactor factor) {
        if (!event.isApplicable(factor.damageModifier)) return false;
        DamageCause cause = event.getCause();
        switch (cause) {
        case CUSTOM:
        case STARVATION:
        case SUFFOCATION:
        case SUICIDE:
        case VOID:
            return false;
        default: break;
        }
        switch (factor) {
        case HELMET:
            return cause == DamageCause.FALLING_BLOCK;
        case SHIELD:
            if (cause == DamageCause.ENTITY_ATTACK) return true;
            if (cause == DamageCause.ENTITY_SWEEP_ATTACK) return true;
            if (cause == DamageCause.PROJECTILE) {
                if (projectile instanceof AbstractArrow arrow && arrow.getPierceLevel() > 0) {
                    return false;
                }
                return true;
            }
            if (cause == DamageCause.ENTITY_EXPLOSION) {
                if (explosive instanceof TNTPrimed tnt
                    && (tnt.getSource() == null || tnt.getSource() == target)) {
                    return false;
                }
                return true;
            }
            return false;
        case ARMOR:
            switch (cause) {
            case BLOCK_EXPLOSION:
            case CONTACT:
            case ENTITY_ATTACK:
            case ENTITY_EXPLOSION:
            case ENTITY_SWEEP_ATTACK:
            case FALLING_BLOCK:
            case LAVA:
            case LIGHTNING:
            case PROJECTILE:
            case THORNS:
                return true;
            case CRAMMING:
            case DRAGON_BREATH:
            case FALL:
            case FLY_INTO_WALL:
            case MAGIC:
            case POISON:
            case SUFFOCATION:
            case VOID:
            case WITHER:
            default:
                return false;
            }
        case RESISTANCE:
            return true;
        case PROTECTION:
            return true;
        default: return true;
        }
    }

    public double getTotalFactor() {
        return helmetFactor
            * shieldFactor
            * armorFactor
            * resistanceFactor
            * protectionFactor;
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

    /**
     * Apply the results to the event if possible.
     */
    public void apply() {
        // Calculate base damage
        if (baseDamageModifier != null) {
            baseDamageModifier.setBase(baseDamage);
            baseDamage = baseDamageModifier.computeResult();
        }
        event.setDamage(DamageModifier.BASE, baseDamage);
        // Apply all modifiers except absorption
        double currentDamage = baseDamage;
        for (DamageFactor it : DamageFactor.values()) {
            if (!event.isApplicable(it.damageModifier)) continue;
            double factor = get(it);
            double reduction = (1.0 - factor) * currentDamage;
            currentDamage *= factor;
            event.setDamage(it.damageModifier, -reduction);
        }
        // Absorption is special
        absorption = target == null
            ? 0.0
            : Math.min(currentDamage, target.getAbsorptionAmount());
        // Final damage
        if (finalDamageModifier != null) {
            finalDamageModifier.setBase(-absorption);
            absorption = -finalDamageModifier.computeResult();
        }
        event.setDamage(DamageModifier.ABSORPTION, -absorption);
        //
        if (event.getFinalDamage() < 0.0) {
            if (event.getFinalDamage() <= -0.01) {
                errorPrint();
                debugPrint();
            }
            for (DamageModifier mod : DamageModifier.values()) {
                if (event.isApplicable(mod)) {
                    event.setDamage(mod, 0.0);
                }
            }
        }
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

    public boolean isEvP() {
        return isCombat() && !attackerIsPlayer() && targetIsPlayer();
    }

    public boolean hasPlayer() {
        return attackerIsPlayer() || targetIsPlayer();
    }

    public boolean hasExactlyOnePlayer() {
        return isCombat() && (attackerIsPlayer() ^ targetIsPlayer());
    }

    public boolean isBlocking() {
        return shieldFactor == 0.0;
    }

    public boolean isArrowAttack() {
        if (!isProjectileAttack() || projectile == null) return false;
        return projectile instanceof Arrow // TippedArrow is subinterface
            || projectile instanceof SpectralArrow;
    }

    public AbstractArrow getArrow() {
        return isArrowAttack()
            ? (AbstractArrow) projectile
            : null;
    }

    public DamageCause getDamageCause() {
        return event.getCause();
    }

    public boolean isProjectileAttack() {
        switch (event.getCause()) {
        case PROJECTILE: return true;
        default: return false;
        }
    }

    public boolean isMeleeAttack() {
        switch (event.getCause()) {
        case ENTITY_ATTACK:
        case ENTITY_SWEEP_ATTACK:
            return true;
        default: return false;
        }
    }

    public boolean setIfApplicable(DamageFactor factor, double value) {
        if (!isApplicable(factor)) return false;
        set(factor, value);
        return true;
    }

    private static String fmt(double in) {
        return String.format("%.2f", in);
    }

    private static String fmt2(double in) {
        return String.format("%.4f", in);
    }

    public void debugPrint() {
        Logger logger = MytemsPlugin.getInstance().getLogger();
        logger.info("DMG DEBUG " + (attacker != null ? Text.toCamelCase(attacker.getType(), "") : "?")
                    + (hasProjectile() ? " -> " : " v ")
                    + (target != null ? Text.toCamelCase(target.getType(), "") : "?")
                    + " " + Text.toCamelCase(event.getCause(), ""));
        if (projectile instanceof AbstractArrow arrow) {
            logger.info("Arrow Damage " + fmt(arrow.getDamage()));
            logger.info("Arrow Crit " + arrow.isCritical());
            if (arrow.getWeapon() != null) {
                logger.info("Arrow Weapon " + arrow.getWeapon().getType());
            }
        }
        logger.info("Base " + fmt(baseDamage));
        if (baseDamageModifier != null) {
            logger.info("Base Mod " + baseDamageModifier.toString());
        }
        for (DamageFactor it : DamageFactor.values()) {
            if (!event.isApplicable(it.damageModifier)) continue;
            // if (get(it) == 1.0 && event.getDamage(it.damageModifier) == 0.0) continue;
            double my = getFlatDamageReduction(it);
            double its = event.getDamage(it.damageModifier);
            double value = get(it);
            logger.info((Math.abs(my + its) < 0.001 ? "OK" : "WRONG")
                        + " " + Text.toCamelCase(it, "")
                        + " " + fmt(Math.round(value * 100.0)) + "%"
                        + " => " + fmt(getFlatDamage(it))
                        + " (" + fmt(my) + "|" + fmt(its) + ")");
        }
        logger.info((Math.abs(absorption + event.getDamage(DamageModifier.ABSORPTION)) < 0.001 ? "OK" : "WRONG")
                    + " Absorption"
                    + " " + fmt(absorption) + "|" + fmt(event.getDamage(DamageModifier.ABSORPTION)));
        if (finalDamageModifier != null) {
            logger.info("Final Mod " + finalDamageModifier.toString());
        }
        logger.info((Math.abs(getTotalDamage() - event.getFinalDamage()) < 0.001 ? "OK" : "WRONG")
                    + " Final"
                    + " => " + fmt2(getTotalDamage()) + "|" + fmt2(event.getFinalDamage()));
        logger.info("========================================");
    }

    private static String printItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return "";
        }
        Mytems mytems = Mytems.forItem(item);
        if (mytems == null) {
            return item.getType().name().toLowerCase();
        }
        return mytems.name().toLowerCase();
    }

    private static String printLivingEntity(LivingEntity living) {
        return (living instanceof Player player
                ? "player[" + player.getName() + "]"
                : living.getType().name().toLowerCase())
            + "(" + printItem(living.getEquipment().getItemInMainHand())
            + "," + printItem(living.getEquipment().getItemInOffHand())
            + "," + printItem(living.getEquipment().getHelmet())
            + "," + printItem(living.getEquipment().getChestplate())
            + "," + printItem(living.getEquipment().getLeggings())
            + "," + printItem(living.getEquipment().getBoots())
            + ")";
    }

    public void errorPrint() {
        Logger logger = MytemsPlugin.getInstance().getLogger();
        logger.warning("DMG ERROR"
                       + " " + fmt2(baseDamage) + " => " + fmt2(getTotalDamage())
                       + ", " + fmt2(event.getDamage()) + " => " + fmt2(event.getFinalDamage()));
        if (attacker != null) {
            logger.warning("ATK " + printLivingEntity(attacker));
        }
        if (target != null) {
            logger.warning("DEF " + printLivingEntity(target));
        }
        if (projectile != null) {
            logger.warning("PROJ " + projectile.getType().name().toLowerCase());
        }
        if (explosive != null) {
            logger.warning("EXPL " + explosive.getType().name().toLowerCase());
        }
        if (damagerBlock != null) {
            logger.warning("BLOCK " + damagerBlock.getType().name().toLowerCase());
        }
        logger.warning("////////////////////////////////////////");
    }

    public BaseDamageModifier getOrCreateBaseDamageModifier() {
        if (baseDamageModifier == null) {
            baseDamageModifier = new BaseDamageModifier();
        }
        return baseDamageModifier;
    }

    public FinalDamageModifier getOrCreateFinalDamageModifier() {
        if (finalDamageModifier == null) {
            finalDamageModifier = new FinalDamageModifier();
        }
        return finalDamageModifier;
    }

    public boolean hasBaseDamageModifier() {
        return baseDamageModifier != null;
    }
}
