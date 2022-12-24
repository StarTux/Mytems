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
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.Repairable;

@RequiredArgsConstructor
abstract class SantaItem implements GearItem {
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
        itemStack.editMeta(m -> {
                LeatherArmorMeta meta = (LeatherArmorMeta) m;
                meta.setColor(color);
                meta.setUnbreakable(true);
                meta.addItemFlags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_UNBREAKABLE);
                ((Repairable) meta).setRepairCost(9999);
            });
        return itemStack;
    }

    protected ItemStack makeBoots() {
        ItemStack item = makeColoredLeatherItem(Material.LEATHER_BOOTS, Color.BLACK);
        item.editMeta(meta -> {
                EquipmentSlot slot = EquipmentSlot.FEET;
                Attr.addNumber(meta, Attribute.GENERIC_ARMOR,
                               UUID.fromString("5c76a2ff-ce50-43c3-984f-20e6c70f5155"),
                               getKey().id, 3.0, slot);
                Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS,
                               UUID.fromString("cb36e422-2a10-447c-a5a7-0988e5adc773"),
                               getKey().id, 3.0, slot);
            });
        return item;
    }

    protected ItemStack makePants() {
        ItemStack item = makeColoredLeatherItem(Material.LEATHER_LEGGINGS, Color.RED);
        item.editMeta(meta -> {
                EquipmentSlot slot = EquipmentSlot.LEGS;
                Attr.addNumber(meta, Attribute.GENERIC_ARMOR,
                               UUID.fromString("44574584-59c7-45d5-86af-d2fb3e568754"),
                               getKey().id, 6.0, slot);
                Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS,
                               UUID.fromString("3f1b5d4d-ae14-47f0-a60e-ffd3f79a7e04"),
                               getKey().id, 3.0, slot);
            });
        return item;
    }

    protected ItemStack makeJacket() {
        ItemStack item = makeColoredLeatherItem(Material.LEATHER_CHESTPLATE, Color.RED);
        item.editMeta(meta -> {
                EquipmentSlot slot = EquipmentSlot.CHEST;
                Attr.addNumber(meta, Attribute.GENERIC_ARMOR,
                               UUID.fromString("8bce96f8-2fdd-4235-bf3d-d578e9e7cf95"),
                               getKey().id, 8.0, slot);
                Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS,
                               UUID.fromString("cc160bc3-cf50-4702-b463-8d31eb5a2075"),
                               getKey().id, 3.0, slot);
            });
        return item;
    }

    protected ItemStack makeHat() {
        ItemStack item = Skull.create("Santa", SKULL_ID, SKULL_TEXTURE);
        item.editMeta(meta -> {
                EquipmentSlot slot = EquipmentSlot.HEAD;
                Attr.addNumber(meta, Attribute.GENERIC_ARMOR,
                               UUID.fromString("2cf3df30-b900-4973-abf8-c5c0731b093c"),
                               getKey().id, 3.0, slot);
                Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS,
                               UUID.fromString("a41ab31a-5dda-445c-a8d7-931cd8a5e45a"),
                               getKey().id, 3.0, slot);
            });
        return item;
    }
}
