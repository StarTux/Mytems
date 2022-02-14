package com.cavetale.mytems.item.santa;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.util.Attr;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Skull;
import com.cavetale.mytems.util.Text;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.Repairable;

@RequiredArgsConstructor
abstract class SantaItem implements GearItem {
    protected static final double BONUS_HEALTH = 2.0;
    protected static final Operation HEALTH_OP = Operation.ADD_NUMBER;
    protected static final UUID SKULL_ID = UUID.fromString("986f0d53-6462-43bc-827f-beba9afdd7f4");
    protected static final String SKULL_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTRlNDI0YjE2NzZmZWVjM2EzZjhlYmFkZTllN2Q2YTZmNzFmNzc1NmE4NjlmMzZmN2RmMGZjMTgyZDQzNmUifX19";
    @Getter protected final Mytems key;
    @Getter protected Component displayName;
    protected List<Component> baseLore;
    protected ItemStack prototype;

    @Override
    public void enable() {
        displayName = xmasify(getRawDisplayName(), false);
        prototype = getBaseItemStack();
        baseLore = Text.wrapLore(Text.colorize("\n\n" + getDescription()));
        prototype.editMeta(meta -> {
                Items.text(meta, createTooltip());
                getKey().markItemMeta(meta);
            });
    }

    abstract String getRawDisplayName();

    abstract String getDescription();

    abstract ItemStack getBaseItemStack();

    @Override
    public ItemSet getItemSet() {
        return SantaItemSet.getInstance();
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    protected Component xmasify(String in, boolean bold) {
        int len = in.length();
        int iter = 255 / len;
        Component component = Component.empty();
        if (bold) component = component.decorate(TextDecoration.BOLD);
        for (int i = 0; i < len; i += 1) {
            int white = 255 - (i * 255) / len;
            component = component.append(Component.text(in.substring(i, i + 1))
                                         .color(TextColor.color(255, white, white)));
        }
        return component;
    }

    protected static ItemStack makeColoredLeatherItem(Material material, Color color) {
        ItemStack itemStack = new ItemStack(material);
        LeatherArmorMeta meta = (LeatherArmorMeta) itemStack.getItemMeta();
        meta.setColor(color);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    protected ItemStack makeBoots() {
        ItemStack item = makeColoredLeatherItem(Material.LEATHER_BOOTS, Color.BLACK);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_FIRE, 4, false);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        EquipmentSlot slot = EquipmentSlot.FEET;
        Attr.add(meta, Attribute.GENERIC_MAX_HEALTH,
                 UUID.fromString("25582a55-5d0d-45cf-beda-4257bb5a12bd"),
                 getKey().id, BONUS_HEALTH, HEALTH_OP, slot);
        Attr.addNumber(meta, Attribute.GENERIC_ARMOR,
                       UUID.fromString("5c76a2ff-ce50-43c3-984f-20e6c70f5155"),
                       getKey().id, 3.0, slot);
        Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS,
                       UUID.fromString("cb36e422-2a10-447c-a5a7-0988e5adc773"),
                       getKey().id, 3.0, slot);
        ((Repairable) meta).setRepairCost(9999);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        item.setItemMeta(meta);
        return item;
    }

    protected ItemStack makePants() {
        ItemStack item = makeColoredLeatherItem(Material.LEATHER_LEGGINGS, Color.RED);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        EquipmentSlot slot = EquipmentSlot.LEGS;
        Attr.add(meta, Attribute.GENERIC_MAX_HEALTH,
                 UUID.fromString("5329e871-483e-4574-8555-c9ce4850f1d9"),
                 getKey().id, BONUS_HEALTH, HEALTH_OP, slot);
        Attr.addNumber(meta, Attribute.GENERIC_ARMOR,
                       UUID.fromString("44574584-59c7-45d5-86af-d2fb3e568754"),
                       getKey().id, 6.0, slot);
        Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS,
                       UUID.fromString("3f1b5d4d-ae14-47f0-a60e-ffd3f79a7e04"),
                       getKey().id, 3.0, slot);
        ((Repairable) meta).setRepairCost(9999);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        item.setItemMeta(meta);
        return item;
    }

    protected ItemStack makeJacket() {
        ItemStack item = makeColoredLeatherItem(Material.LEATHER_CHESTPLATE, Color.RED);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        EquipmentSlot slot = EquipmentSlot.CHEST;
        Attr.add(meta, Attribute.GENERIC_MAX_HEALTH,
                 UUID.fromString("9afaf033-baa1-4037-93c4-6ceaad54946e"),
                 getKey().id, BONUS_HEALTH, HEALTH_OP, slot);
        Attr.addNumber(meta, Attribute.GENERIC_ARMOR,
                       UUID.fromString("8bce96f8-2fdd-4235-bf3d-d578e9e7cf95"),
                       getKey().id, 8.0, slot);
        Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS,
                       UUID.fromString("cc160bc3-cf50-4702-b463-8d31eb5a2075"),
                       getKey().id, 3.0, slot);
        ((Repairable) meta).setRepairCost(9999);
        meta.addItemFlags(ItemFlag.HIDE_DYE);
        item.setItemMeta(meta);
        return item;
    }

    protected ItemStack makeHat() {
        ItemStack item = Skull.create("Santa", SKULL_ID, SKULL_TEXTURE);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, false);
        EquipmentSlot slot = EquipmentSlot.HEAD;
        Attr.add(meta, Attribute.GENERIC_MAX_HEALTH,
                 UUID.fromString("dc8d062e-bcaf-4fdd-b32e-0f6f9c3452a7"),
                 getKey().id, BONUS_HEALTH, HEALTH_OP, slot);
        Attr.addNumber(meta, Attribute.GENERIC_ARMOR,
                       UUID.fromString("2cf3df30-b900-4973-abf8-c5c0731b093c"),
                       getKey().id, 3.0, slot);
        Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS,
                       UUID.fromString("a41ab31a-5dda-445c-a8d7-931cd8a5e45a"),
                       getKey().id, 3.0, slot);
        item.setItemMeta(meta);
        return item;
    }
}
