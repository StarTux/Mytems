package com.cavetale.mytems.item.scarlet;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.event.combat.DamageCalculationEvent;
import com.cavetale.mytems.event.combat.DamageFactor;
import com.cavetale.mytems.gear.Equipment;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.util.Attr;
import com.cavetale.mytems.util.Skull;
import com.cavetale.mytems.util.Text;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import static com.cavetale.mytems.util.Items.tooltip;

@RequiredArgsConstructor @Getter
public abstract class ScarletItem implements GearItem {
    protected static final TextColor TEXT_COLOR = TextColor.color(0xFF2400);
    protected static final Color LEATHER_COLOR = Color.fromRGB(0x8c2924);
    protected static final Operation MOVEMENT_OP = Operation.ADD_SCALAR;
    protected static final double MOVEMENT_MOD = -0.035;
    protected static final double KNOCKBACK_MOD = 0.1;
    protected static final double TOUGHNESS_MOD = 3.0;
    protected final Mytems key;
    protected List<Component> baseLore;
    // Set by ctor of subclasses:
    protected Component displayName;
    protected String description;
    protected ItemStack prototype;

    @Override
    public final void enable() {
        baseLore = Text.wrapLore(description, c -> c.color(NamedTextColor.GRAY));
        prototype.editMeta(meta -> {
                tooltip(meta, createTooltip());
                if (meta instanceof Repairable) {
                    ((Repairable) meta).setRepairCost(9999);
                    meta.setUnbreakable(true);
                    meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                }
                key.markItemMeta(meta);
            });
    }

    @Override
    public final ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public final ItemSet getItemSet() {
        return ScarletItemSet.getInstance();
    }

    public static final class Helmet extends ScarletItem {
        @SuppressWarnings("LineLength")
        static final Skull SKULL = new
            Skull("ScarletHelmet",
                  UUID.fromString("d460ea5d-bb08-4796-8e11-bd3e8fcda0a1"),
                  "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2Q0Njc5M2JkYmNlNWNhZDVmMzdiMTI0ZWFmMWUzNjg5YmJhMThkNTlhODA2ODU2N2M3NGY0ZmYxYTE4In19fQ==",
                  null);

        public Helmet(final Mytems key) {
            super(key);
            displayName = Component.text("Scarlet Helmet", TEXT_COLOR);
            description = "A sturdy helmet that a noble warrior can trust with their life.";
            prototype = new ItemStack(Material.PLAYER_HEAD);
            prototype.editMeta(meta -> {
                    SKULL.apply((SkullMeta) meta);
                    final EquipmentSlotGroup slot = EquipmentSlotGroup.HEAD;
                    Attr.add(meta, Attribute.ARMOR, "scarlet_helmet_armor", 3.0, Operation.ADD_NUMBER, slot);
                    Attr.add(meta, Attribute.ARMOR_TOUGHNESS, "scarlet_helmet_armor_toughness", TOUGHNESS_MOD, Operation.ADD_NUMBER, slot);
                    Attr.add(meta, Attribute.KNOCKBACK_RESISTANCE, "scarlet_helmet_knockback_resistance", KNOCKBACK_MOD, Operation.ADD_NUMBER, slot);
                    Attr.add(meta, Attribute.MOVEMENT_SPEED, "scarlet_helmet_movement_speed", MOVEMENT_MOD, MOVEMENT_OP, slot);
                });
        }
    }

    public static final class Chestplate extends ScarletItem {
        public Chestplate(final Mytems key) {
            super(key);
            displayName = Component.text("Scarlet Chestplate", TEXT_COLOR);
            description = "Clad with the color of scarlet, your body represents the strength of the kingdom.";
            prototype = new ItemStack(Material.LEATHER_CHESTPLATE);
            prototype.editMeta(meta -> {
                    ((LeatherArmorMeta) meta).setColor(LEATHER_COLOR);
                    meta.addItemFlags(ItemFlag.HIDE_DYE);
                    final EquipmentSlotGroup slot = EquipmentSlotGroup.CHEST;
                    Attr.add(meta, Attribute.ARMOR, "scarlet_chestplate_armor", 8.0, Operation.ADD_NUMBER, slot);
                    Attr.add(meta, Attribute.ARMOR_TOUGHNESS, "scarlet_chestplate_armor_toughness", TOUGHNESS_MOD, Operation.ADD_NUMBER, slot);
                    Attr.add(meta, Attribute.KNOCKBACK_RESISTANCE, "scarlet_chestplate_knockback_resistance", KNOCKBACK_MOD, Operation.ADD_NUMBER, slot);
                    Attr.add(meta, Attribute.MOVEMENT_SPEED, "scarlet_chestplate_movement_speed", MOVEMENT_MOD, MOVEMENT_OP, slot);
                });
        }
    }

    public static final class Leggings extends ScarletItem {
        public Leggings(final Mytems key) {
            super(key);
            displayName = Component.text("Scarlet Leggings", TEXT_COLOR);
            description = "Overlapping metal plates cover the flexible leather interior.";
            prototype = new ItemStack(Material.LEATHER_LEGGINGS);
            prototype.editMeta(meta -> {
                    ((LeatherArmorMeta) meta).setColor(LEATHER_COLOR);
                    meta.addItemFlags(ItemFlag.HIDE_DYE);
                    final EquipmentSlotGroup slot = EquipmentSlotGroup.LEGS;
                    Attr.add(meta, Attribute.ARMOR, "scarlet_leggings_armor", 6.0, Operation.ADD_NUMBER, slot);
                    Attr.add(meta, Attribute.ARMOR_TOUGHNESS, "scarlet_leggings_armor_toughness", TOUGHNESS_MOD, Operation.ADD_NUMBER, slot);
                    Attr.add(meta, Attribute.KNOCKBACK_RESISTANCE, "scarlet_leggings_knockback_resistance", KNOCKBACK_MOD, Operation.ADD_NUMBER, slot);
                    Attr.add(meta, Attribute.MOVEMENT_SPEED, "scarlet_leggings_movement_speed", MOVEMENT_MOD, MOVEMENT_OP, slot);
                });
        }
    }

    public static final class Boots extends ScarletItem {
        public Boots(final Mytems key) {
            super(key);
            displayName = Component.text("Scarlet Boots", TEXT_COLOR);
            description = "A pair of armored boots that will support you through any battle.";
            prototype = new ItemStack(Material.LEATHER_BOOTS);
            prototype.editMeta(meta -> {
                    ((LeatherArmorMeta) meta).setColor(LEATHER_COLOR);
                    meta.addItemFlags(ItemFlag.HIDE_DYE);
                    final EquipmentSlotGroup slot = EquipmentSlotGroup.FEET;
                    Attr.add(meta, Attribute.ARMOR, "scarlet_boots_armor", 3.0, Operation.ADD_NUMBER, slot);
                    Attr.add(meta, Attribute.ARMOR_TOUGHNESS, "scarlet_boots_armor_toughness", TOUGHNESS_MOD, Operation.ADD_NUMBER, slot);
                    Attr.add(meta, Attribute.KNOCKBACK_RESISTANCE, "scarlet_boots_knockback_resistance", KNOCKBACK_MOD, Operation.ADD_NUMBER, slot);
                    Attr.add(meta, Attribute.MOVEMENT_SPEED, "scarlet_boots_movement_speed", MOVEMENT_MOD, MOVEMENT_OP, slot);
                });
        }
    }

    public static final class Shield extends ScarletItem {
        public Shield(final Mytems key) {
            super(key);
            displayName = Component.text("Scarlet Shield", TEXT_COLOR);
            description = "An ornate shield with a fancy red shatter pattern.";
            prototype = new ItemStack(Material.SHIELD);
            prototype.editMeta(meta -> {
                    BlockStateMeta blockStateMeta = (BlockStateMeta) meta;
                    Banner banner = (Banner) blockStateMeta.getBlockState();
                    banner.setBaseColor(DyeColor.RED);
                    banner.setPatterns(List.of(new Pattern(DyeColor.LIGHT_GRAY, PatternType.STRIPE_DOWNLEFT),
                                               new Pattern(DyeColor.LIGHT_GRAY, PatternType.STRIPE_DOWNRIGHT),
                                               new Pattern(DyeColor.LIGHT_GRAY, PatternType.STRAIGHT_CROSS),
                                               new Pattern(DyeColor.RED, PatternType.RHOMBUS)));
                    blockStateMeta.setBlockState(banner);
                    meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP); // hides banner patterns
                    final EquipmentSlotGroup slot = EquipmentSlotGroup.OFFHAND;
                    Attr.add(meta, Attribute.ARMOR, "scarlet_shield_armor", 6.0, Operation.ADD_NUMBER, slot);
                    Attr.add(meta, Attribute.ARMOR_TOUGHNESS, "scarlet_shield_armor_toughness", TOUGHNESS_MOD, Operation.ADD_NUMBER, slot);
                    Attr.add(meta, Attribute.KNOCKBACK_RESISTANCE, "scarlet_shield_knockback_resistance", KNOCKBACK_MOD, Operation.ADD_NUMBER, slot);
                    Attr.add(meta, Attribute.MOVEMENT_SPEED, "scarlet_shield_movement_speed", MOVEMENT_MOD, MOVEMENT_OP, slot);
                });
        }
    }

    public static final class Sword extends ScarletItem {
        public Sword(final Mytems key) {
            super(key);
            displayName = Component.text("Scarlet Sword", TEXT_COLOR);
            description = "A heavy and unwieldy sword that swings slowly, but packs a large punch."
                + "\n\nSweep Attacks deal full Attack Damage.";
            prototype = new ItemStack(Material.NETHERITE_SWORD);
            prototype.editMeta(meta -> {
                    final EquipmentSlotGroup slot = EquipmentSlotGroup.MAINHAND;
                    Attr.add(meta, Attribute.ATTACK_DAMAGE, "scarlet_sword_attack_damage", 20.0 + 4.0, Operation.ADD_NUMBER, slot);
                    Attr.add(meta, Attribute.ATTACK_SPEED, "scarlet_sword_attack_speed", -0.67, Operation.ADD_SCALAR, slot);
                    Attr.add(meta, Attribute.KNOCKBACK_RESISTANCE, "scarlet_sword_knockback_resistance", KNOCKBACK_MOD, Operation.ADD_NUMBER, slot);
                    Attr.add(meta, Attribute.MOVEMENT_SPEED, "scarlet_sword_movement_speed", MOVEMENT_MOD, MOVEMENT_OP, slot);
                });
        }

        /**
         * The regular attack damage formula for sweep attacks is:
         *
         * > 1 + AttackDamage * (SweepingEdge / (SweepingEdge + 1))
         *
         * So, attack damage for Sweeping Edge levels:
         * - lvl 0 => 1 dmg
         * - lvl 1 => 1 + AttackDamage * 1/2
         * - lvl 2 => 1 + AttackDamage * 2/3
         * - lvl 3 => 1 + AttackDamage * 4/5
         * - lvl 4 => 1 + AttackDamage * 4/5
         *
         * Damage is approaching 1 + AttackDamage, but is capped at AttackDamage.
         * Let's try setting it to the regular attack damage.
         */
        @Override
        public void onAttackingDamageCalculation(DamageCalculationEvent event, ItemStack itemStack, EquipmentSlot slot) {
            if (slot != EquipmentSlot.HAND) return;
            boolean isSweepAttack = event.getEntityDamageEvent().getCause() == DamageCause.ENTITY_SWEEP_ATTACK;
            if (isSweepAttack) {
                double dmg = event.getAttacker().getAttribute(Attribute.ATTACK_DAMAGE).getValue();
                event.getCalculation().setBaseDamage(dmg);
                event.setHandled(true);
            }
            boolean isEntityAttack = event.getEntityDamageEvent().getCause() == DamageCause.ENTITY_ATTACK;
            if (isEntityAttack && event.attackerIsPlayer()) {
                Player player = event.getAttackerPlayer();
                double value = player.getAttribute(Attribute.ATTACK_SPEED).getValue();
                double speed = 20.0 / value;
                player.setCooldown(key.material, (int) Math.round(speed));
            }
            // Emulate Knockback
            LivingEntity target = event.getTarget();
            if (target != null && (isEntityAttack || isSweepAttack)) {
                event.addPostDamageAction(() -> {
                        if (target.isDead()) return;
                        double resist = target.getAttribute(Attribute.KNOCKBACK_RESISTANCE).getValue();
                        if (resist >= 1.0) return;
                        Vector v = target.getEyeLocation().subtract(event.getAttacker().getLocation()).toVector();
                        if (v.length() == 0.0) return;
                        double str = event.getAttacker() instanceof Player player && player.isSprinting() ? 1.3 : 0.6;
                        v = v.normalize().multiply(str * (1.0 - resist));
                        target.setVelocity(v);
                    });
            }
        }
    }

    @Getter
    public static final class ScarletItemSet implements ItemSet {
        protected static ScarletItemSet instance;
        protected final String name = "Scarlet";
        protected final List<SetBonus> setBonuses = List.of(new SetBonus[] {
                new ScarletProtection(this, 1),
                new ScarletSpikes(this, 2),
                new ScarletResistance(this, 4),
                new ScarletStrength(this, 6),
            });
        protected final int maxItemCount = 6;

        static ItemSet getInstance() {
            if (instance == null) {
                instance = new ScarletItemSet();
            }
            return instance;
        }

        @Getter @RequiredArgsConstructor
        public static final class ScarletSpikes implements SetBonus {
            protected final ScarletItemSet itemSet;
            protected final int requiredItemCount;
            protected final String name = "Scarlet Spikes";
            protected final String description = "Return some Damage";

            @Override
            public String getName(int has) {
                return has > 0
                    ? name + " " + Text.roman(has)
                    : name;
            }

            @Override
            public String getDescription(int has) {
                return has > 1
                    ? "Return Damage \u00D7" + has
                    : description;
            }

            /**
             * Emulate thorns.
             * - Thorns is rolled for each item.
             * - Each thorns level increases the chance of thorns damage by 15%
             * - On a successful roll, damage is increased by 1, 2, 3, or 4, with equal propability.
             * - Total damage is capped at 4.
             * - An unspecified of knockback is applied.
             */
            @Override
            public void onDefendingDamageCalculation(DamageCalculationEvent event) {
                if (event.isBlocking()) return;
                LivingEntity target = event.getTarget();
                LivingEntity attacker = event.getAttacker();
                if (target == null || attacker == null) return;
                int has = Equipment.of(target).countSetItems(ScarletItemSet.instance);
                if (has < requiredItemCount) return;
                if (has <= 0) return;
                int totalDamage = 0;
                Random random = ThreadLocalRandom.current();
                for (int i = 0; i < has; i += 1) {
                    if (random.nextDouble() < 0.45) {
                        totalDamage += 1 + random.nextInt(4);
                    }
                }
                if (totalDamage <= 0) return;
                final double damage = Math.min(4.0, (double) totalDamage);
                event.addPostDamageAction(() -> {
                        if (attacker.isDead()) return;
                        attacker.damage(damage);
                        if (attacker instanceof Player attackerPlayer) {
                            attackerPlayer.playSound(attackerPlayer.getLocation(), Sound.ENCHANT_THORNS_HIT, 1.0f, 1.0f);
                        }
                        // Knockback
                        double resist = attacker.getAttribute(Attribute.KNOCKBACK_RESISTANCE).getValue();
                        if (resist >= 1.0) return;
                        Vector v = attacker.getEyeLocation().subtract(target.getLocation()).toVector();
                        if (v.length() == 0.0) return;
                        v = v.normalize().multiply(0.5 * (1.0 - resist));
                        attacker.setVelocity(v);
                    });
            }
        }

        @Getter @RequiredArgsConstructor
        public static final class ScarletProtection implements SetBonus {
            protected final ScarletItemSet itemSet;
            protected final int requiredItemCount;
            protected final String name = "Scarlet Protection";
            protected final String description = "Allround Protection";

            @Override
            public String getName(int has) {
                return has > 0
                    ? name + " " + Text.roman(has)
                    : name;
            }

            @Override
            public String getDescription(int has) {
                return has > 1
                    ? has + "\u00D7 " + description
                    : description;
            }

            @Override
            public void onDefendingDamageCalculation(DamageCalculationEvent event) {
                int has = Equipment.of(event.getTarget()).countSetItems(ScarletItemSet.instance);
                if (has <= 0) return;
                int epf = has * 5;
                double newValue = 1.0 - (((double) Math.min(20, epf)) / 25.0);
                event.setIfApplicable(DamageFactor.PROTECTION, val -> Math.min(val, newValue));
            }
        }

        @Getter @RequiredArgsConstructor
        public static final class ScarletResistance implements SetBonus {
            protected final ScarletItemSet itemSet;
            protected final int requiredItemCount;
            protected final String name = "Resistance I";
            protected final String description = "";
            protected final List<PotionEffect> potionEffects = List.of(new PotionEffect[] {
                    new PotionEffect(PotionEffectType.RESISTANCE, 39, 0, true, false, true),
                });
        }

        @Getter @RequiredArgsConstructor
        public static final class ScarletStrength implements SetBonus {
            protected final ScarletItemSet itemSet;
            protected final int requiredItemCount;
            protected final String name = "Strength I";
            protected final String description = "";
            protected final List<PotionEffect> potionEffects = List.of(new PotionEffect[] {
                    new PotionEffect(PotionEffectType.STRENGTH, 39, 0, true, false, true),
                });
        }
    }
}
