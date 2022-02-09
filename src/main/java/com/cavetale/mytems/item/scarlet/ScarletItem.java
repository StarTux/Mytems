package com.cavetale.mytems.item.scarlet;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.event.combat.DamageCalculationEvent;
import com.cavetale.mytems.event.combat.DamageFactor;
import com.cavetale.mytems.gear.EntityAttribute;
import com.cavetale.mytems.gear.Equipment;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.util.Items;
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
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

@RequiredArgsConstructor @Getter
public abstract class ScarletItem implements GearItem {
    protected static final TextColor TEXT_COLOR = TextColor.color(0xFF2400);
    protected static final Color LEATHER_COLOR = Color.fromRGB(0x8c2924);
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
                Items.text(meta, createTooltip());
                if (meta instanceof Repairable) {
                    ((Repairable) meta).setRepairCost(9999);
                    meta.setUnbreakable(true);
                }
                key.markItemMeta(meta);
                meta.setUnbreakable(true);
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
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
        static final Skull SKULL = new Skull("Scarlet Helmet",
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
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
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
                    meta.addItemFlags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES);
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
                    meta.addItemFlags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES);
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
                    meta.addItemFlags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_ATTRIBUTES);
                });
        }
    }

    public static final class Sword extends ScarletItem {
        public Sword(final Mytems key) {
            super(key);
            displayName = Component.text("Scarlet Broadsword", TEXT_COLOR);
            description = "A heavy and unwieldy sword that swings slowly, but packs a large punch.";
            prototype = new ItemStack(Material.NETHERITE_SWORD);
            prototype.editMeta(meta -> {
                    meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,
                                              new AttributeModifier(UUID.fromString("fffe2549-0000-85b7-0001-6ab4fffef492"),
                                                                    "generic.attack_damage",
                                                                    20.0 + 3.5,
                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                    EquipmentSlot.HAND));
                    meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,
                                              new AttributeModifier(UUID.fromString("fffe2549-0000-867f-0001-6ab4fffef302"),
                                                                    "generic.attack_speed",
                                                                    -3.5,
                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                    EquipmentSlot.HAND));
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
                double dmg = event.getAttacker().getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue();
                event.getCalculation().setBaseDamage(dmg);
                event.setHandled(true);
            }
            boolean isEntityAttack = event.getEntityDamageEvent().getCause() == DamageCause.ENTITY_ATTACK;
            if (isEntityAttack && event.attackerIsPlayer()) {
                Player player = event.getAttackerPlayer();
                double value = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getValue();
                double speed = 20.0 / value;
                player.setCooldown(key.material, (int) Math.round(speed));
            }
            // Emulate Knockback
            LivingEntity target = event.getTarget();
            if (target != null && (isEntityAttack || isSweepAttack)) {
                event.addPostDamageAction(true, () -> {
                        if (target.isDead()) return;
                        double resist = target.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getValue();
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
                                               new Pattern(DyeColor.RED, PatternType.RHOMBUS_MIDDLE)));
                    blockStateMeta.setBlockState(banner);
                });
        }
    }

    @Getter
    public static final class ScarletItemSet implements ItemSet {
        protected static ScarletItemSet instance;
        protected final String name = "Scarlet";
        protected final List<SetBonus> setBonuses = List.of(new SetBonus[] {
                new Spikes(3),
                new HeavyArmor(4),
                new FullArmor(5),
                new FullProtection(6),
            });

        static ItemSet getInstance() {
            if (instance == null) {
                instance = new ScarletItemSet();
            }
            return instance;
        }

        @Getter @RequiredArgsConstructor
        public static final class Spikes implements SetBonus {
            protected final int requiredItemCount;
            protected final String name = "Spikes";
            protected final String description = "Return some Damage";

            @Override
            public String getName(int has) {
                int lvl = Math.max(0, has - 2);
                return lvl > 0 ? name + " " + Text.roman(lvl) : name;
            }

            @Override
            public String getDescription(int has) {
                int lvl = Math.max(0, has - 2);
                return "Return Damage as with " + lvl + "xThorns III";
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
                LivingEntity target = event.getTarget();
                LivingEntity attacker = event.getAttacker();
                if (target == null || attacker == null) return;
                int has = Equipment.of(target).countSetItems(ScarletItemSet.instance);
                if (has < requiredItemCount) return;
                int lvl = Math.max(0, has - 2);
                if (lvl <= 0) return;
                int totalDamage = 0;
                Random random = ThreadLocalRandom.current();
                for (int i = 0; i < lvl; i += 1) {
                    if (random.nextDouble() < 0.45) {
                        totalDamage += 1 + random.nextInt(4);
                    }
                }
                if (totalDamage <= 0) return;
                final double damage = Math.min(4.0, (double) totalDamage);
                event.addPostDamageAction(true, () -> {
                        if (attacker.isDead()) return;
                        attacker.damage(damage);
                        if (attacker instanceof Player attackerPlayer) {
                            attackerPlayer.playSound(attackerPlayer.getLocation(), Sound.ENCHANT_THORNS_HIT, 1.0f, 1.0f);
                        }
                        // Knockback
                        double resist = attacker.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getValue();
                        if (resist >= 1.0) return;
                        Vector v = attacker.getEyeLocation().subtract(target.getLocation()).toVector();
                        if (v.length() == 0.0) return;
                        v = v.normalize().multiply(0.25 * (1.0 - resist));
                        attacker.setVelocity(v);
                    });
            }
        }

        @Getter @RequiredArgsConstructor
        public static final class HeavyArmor implements SetBonus {
            protected final int requiredItemCount;
            protected final String name = "Heavy Armor";
            protected final String description = "Increased Knockback Resistance but Reduced Movement Speed";

            @Override
            public List<EntityAttribute> getEntityAttributes() {
                return List.of(new EntityAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE,
                                                   UUID.fromString("fffe2558-0000-6e34-0002-d63fffff2398"),
                                                   "scarlet_knockback_resist",
                                                   0.5,
                                                   AttributeModifier.Operation.ADD_NUMBER),
                               new EntityAttribute(Attribute.GENERIC_MOVEMENT_SPEED,
                                                   UUID.fromString("fffe2549-0000-861b-0001-6ab4fffef3ca"),
                                                   "scarlet_speed_reduction",
                                                   -0.1,
                                                   AttributeModifier.Operation.ADD_SCALAR));
            }
        }

        @Getter @RequiredArgsConstructor
        public static final class FullArmor implements SetBonus {
            protected final int requiredItemCount;
            protected final String name = "Armor \u221E";
            protected final String description = "Maximum Armor and Toughness";

            @Override
            public void onDefendingDamageCalculation(DamageCalculationEvent event) {
                event.setIfApplicable(DamageFactor.ARMOR, 0.2);
            }
        }

        @Getter @RequiredArgsConstructor
        public static final class FullProtection implements SetBonus {
            protected final int requiredItemCount;
            protected final String name = "Protection \u221E";
            protected final String description = "Maximum Protection Enchantment Effect";

            @Override
            public void onDefendingDamageCalculation(DamageCalculationEvent event) {
                event.setIfApplicable(DamageFactor.PROTECTION, 0.2);
            }
        }
    }
}
