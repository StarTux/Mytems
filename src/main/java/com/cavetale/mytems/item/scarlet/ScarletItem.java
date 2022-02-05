package com.cavetale.mytems.item.scarlet;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Skull;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.SkullMeta;

@RequiredArgsConstructor @Getter
public abstract class ScarletItem implements GearItem {
    protected static final TextColor TEXT_COLOR = TextColor.color(0xFF2400);
    protected static final Color LEATHER_COLOR = Color.fromRGB(0x8c2924);
    protected final Mytems key;
    // Set by ctor of subclasses:
    protected Component displayName;
    protected List<Component> baseLore;
    protected ItemStack prototype;

    @Override
    public final void enable() {
        prototype.editMeta(meta -> {
                Items.text(meta, createTooltip());
                if (meta instanceof Repairable) {
                    ((Repairable) meta).setRepairCost(9999);
                    meta.setUnbreakable(true);
                }
                key.markItemMeta(meta);
            });
    }

    @Override
    public final ItemStack createItemStack() {
        return prototype.clone();
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
            baseLore = List.of(new Component[] {
                    displayName,
                    Component.empty(),
                    Component.text("A sturdy helmet that", NamedTextColor.GRAY),
                    Component.text("a noble warrior can", NamedTextColor.GRAY),
                    Component.text("trust with their life.", NamedTextColor.GRAY),
                });
            prototype = new ItemStack(Material.PLAYER_HEAD);
            prototype.editMeta(meta -> {
                    SKULL.apply((SkullMeta) meta);
                    meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
                    meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
                    meta.addEnchant(Enchantment.THORNS, 3, true);
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,
                                              new AttributeModifier(UUID.fromString("fffe2558-0000-6d6c-0002-d63fffff2528"),
                                                                    "generic.armor_toughness",
                                                                    3.0,
                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                    EquipmentSlot.HEAD));
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR,
                                              new AttributeModifier(UUID.fromString("fffe2558-0000-6dd0-0002-d63fffff2460"),
                                                                    "generic.armor",
                                                                    3.0,
                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                    EquipmentSlot.HEAD));
                    meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE,
                                              new AttributeModifier(UUID.fromString("fffe2558-0000-6e34-0002-d63fffff2398"),
                                                                    "generic.knockback_resistance",
                                                                    0.1,
                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                    EquipmentSlot.HEAD));
                    meta.setUnbreakable(true);
                });
        }
    }

    public static final class Chestplate extends ScarletItem {
        public Chestplate(final Mytems key) {
            super(key);
            displayName = Component.text("Scarlet Chestplate", TEXT_COLOR);
            baseLore = List.of(new Component[] {
                    displayName,
                    Component.empty(),
                    Component.text("Clad with the color", NamedTextColor.GRAY),
                    Component.text("of scarlet, your body", NamedTextColor.GRAY),
                    Component.text("represents the strength", NamedTextColor.GRAY),
                    Component.text("of the kingdom.", NamedTextColor.GRAY),
                });
            prototype = new ItemStack(Material.LEATHER_CHESTPLATE);
            prototype.editMeta(meta -> {
                    ((LeatherArmorMeta) meta).setColor(LEATHER_COLOR);
                    meta.addItemFlags(ItemFlag.HIDE_DYE);
                    meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 3, true);
                    meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
                    meta.addEnchant(Enchantment.THORNS, 3, true);
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,
                                              new AttributeModifier(UUID.fromString("fffe2558-0000-83b0-0002-d63ffffef8a0"),
                                                                    "generic.armor_toughness",
                                                                    3.0,
                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                    EquipmentSlot.CHEST));
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR,
                                              new AttributeModifier(UUID.fromString("fffe2558-0000-8414-0002-d63ffffef7d8"),
                                                                    "generic.armor",
                                                                    8.0,
                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                    EquipmentSlot.CHEST));
                    meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE,
                                              new AttributeModifier(UUID.fromString("fffe2558-0000-8478-0002-d63ffffef710"),
                                                                    "generic.knockback_resistance",
                                                                    0.1,
                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                    EquipmentSlot.CHEST));
                    meta.setUnbreakable(true);
                });
        }
    }

    public static final class Leggings extends ScarletItem {
        public Leggings(final Mytems key) {
            super(key);
            displayName = Component.text("Scarlet Leggings", TEXT_COLOR);
            baseLore = List.of(new Component[] {
                    displayName,
                    Component.empty(),
                    Component.text("Overlapping metal plates", NamedTextColor.GRAY),
                    Component.text("cover the flexible leather", NamedTextColor.GRAY),
                    Component.text("interior.", NamedTextColor.GRAY),
                });
            prototype = new ItemStack(Material.LEATHER_LEGGINGS);
            prototype.editMeta(meta -> {
                    ((LeatherArmorMeta) meta).setColor(LEATHER_COLOR);
                    meta.addItemFlags(ItemFlag.HIDE_DYE);
                    meta.addEnchant(Enchantment.PROTECTION_FIRE, 3, true);
                    meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
                    meta.addEnchant(Enchantment.THORNS, 3, true);
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,
                                              new AttributeModifier(UUID.fromString("fffe2558-0000-cfdc-0002-d63ffffe6048"),
                                                                    "generic.armor_toughness",
                                                                    3.0,
                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                    EquipmentSlot.LEGS));
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR,
                                              new AttributeModifier(UUID.fromString("fffe2558-0000-d040-0002-d63ffffe5f80"),
                                                                    "generic.armor",
                                                                    6.0,
                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                    EquipmentSlot.LEGS));
                    meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE,
                                              new AttributeModifier(UUID.fromString("fffe2558-0000-d0a4-0002-d63ffffe5eb8"),
                                                                    "generic.knockback_resistance",
                                                                    0.1,
                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                    EquipmentSlot.LEGS));
                    meta.setUnbreakable(true);
                });
        }
    }

    public static final class Boots extends ScarletItem {
        public Boots(final Mytems key) {
            super(key);
            displayName = Component.text("Scarlet Boots", TEXT_COLOR);
            baseLore = List.of(new Component[] {
                    displayName,
                    Component.empty(),
                    Component.text("A pair of armored boots", NamedTextColor.GRAY),
                    Component.text("that will support you", NamedTextColor.GRAY),
                    Component.text("through any battle.", NamedTextColor.GRAY),
                });
            prototype = new ItemStack(Material.LEATHER_BOOTS);
            prototype.editMeta(meta -> {
                    ((LeatherArmorMeta) meta).setColor(LEATHER_COLOR);
                    meta.addItemFlags(ItemFlag.HIDE_DYE);
                    meta.addEnchant(Enchantment.PROTECTION_FALL, 3, true);
                    meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
                    meta.addEnchant(Enchantment.THORNS, 3, true);
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,
                                              new AttributeModifier(UUID.fromString("fffe2558-0000-b4e8-0002-d63ffffe9630"),
                                                                    "generic.armor_toughness",
                                                                    3.0,
                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                    EquipmentSlot.FEET));
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR,
                                              new AttributeModifier(UUID.fromString("fffe2558-0000-b54c-0002-d63ffffe9568"),
                                                                    "generic.armor",
                                                                    3.0,
                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                    EquipmentSlot.FEET));
                    meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE,
                                              new AttributeModifier(UUID.fromString("fffe2558-0000-b5b0-0002-d63ffffe94a0"),
                                                                    "generic.knockback_resistance",
                                                                    0.1,
                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                    EquipmentSlot.FEET));
                    meta.setUnbreakable(true);
                });
        }
    }

    public static final class Sword extends ScarletItem {
        public Sword(final Mytems key) {
            super(key);
            displayName = Component.text("Scarlet Broadsword", TEXT_COLOR);
            baseLore = List.of(new Component[] {
                    displayName,
                    Component.empty(),
                    Component.text("A heavy and unwieldy", NamedTextColor.GRAY),
                    Component.text("sword that swings slowly,", NamedTextColor.GRAY),
                    Component.text("but packs a large punch.", NamedTextColor.GRAY),
                });
            prototype = new ItemStack(Material.NETHERITE_SWORD);
            prototype.editMeta(meta -> {
                    meta.addEnchant(Enchantment.KNOCKBACK, 3, true);
                    meta.addEnchant(Enchantment.DAMAGE_ALL, 6, true);
                    meta.addEnchant(Enchantment.SWEEPING_EDGE, 4, true);
                    meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,
                                              new AttributeModifier(UUID.fromString("fffe2549-0000-85b7-0001-6ab4fffef492"),
                                                                    "generic.attack_damage",
                                                                    20.0,
                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                    EquipmentSlot.HAND));
                    meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED,
                                              new AttributeModifier(UUID.fromString("fffe2549-0000-861b-0001-6ab4fffef3ca"),
                                                                    "generic.movement_speed",
                                                                    -0.1,
                                                                    AttributeModifier.Operation.ADD_SCALAR,
                                                                    EquipmentSlot.HAND));
                    meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,
                                              new AttributeModifier(UUID.fromString("fffe2549-0000-867f-0001-6ab4fffef302"),
                                                                    "generic.attack_speed",
                                                                    -3.5,
                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                    EquipmentSlot.HAND));
                    meta.setUnbreakable(true);
                });
        }
    }

    public static final class Shield extends ScarletItem {
        public Shield(final Mytems key) {
            super(key);
            displayName = Component.text("Scarlet Shield", TEXT_COLOR);
            baseLore = List.of(new Component[] {
                    displayName,
                    Component.empty(),
                    Component.text("An ornate shield", NamedTextColor.GRAY),
                    Component.text("with a fancy red", NamedTextColor.GRAY),
                    Component.text("shatter pattern.", NamedTextColor.GRAY),
                });
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
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,
                                              new AttributeModifier(UUID.fromString("fffe2558-0000-df25-0000-48c4fffe41b6"),
                                                                    "generic.armor_toughness",
                                                                    3.0,
                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                    EquipmentSlot.HAND));
                    meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE,
                                              new AttributeModifier(UUID.fromString("fffe2558-0000-df89-0000-48c4fffe40ee"),
                                                                    "generic.knockback_resistance",
                                                                    0.1,
                                                                    AttributeModifier.Operation.ADD_NUMBER,
                                                                    EquipmentSlot.HAND));
                    meta.setUnbreakable(true);
                });
        }
    }

    @Getter
    public static final class ScarletItemSet implements ItemSet {
        protected static ScarletItemSet instance;
        protected final String name = "Scarlet";
        protected final List<SetBonus> setBonuses = List.of();

        static ItemSet getInstance() {
            if (instance == null) {
                instance = new ScarletItemSet();
            }
            return instance;
        }
    }
}
