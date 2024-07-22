package com.cavetale.mytems.item.santa;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.util.Attr;
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
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.Repairable;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor
abstract class SantaItem implements GearItem {
    protected static final UUID SKULL_ID = UUID.fromString("986f0d53-6462-43bc-827f-beba9afdd7f4");
    protected static final String SKULL_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTRlNDI0YjE2NzZmZWVjM2EzZjhlYmFkZTllN2Q2YTZmNzFmNzc1NmE4NjlmMzZmN2RmMGZjMTgyZDQzNmUifX19";
    @Getter protected final Mytems key;
    @Getter protected Component displayName;
    @Getter protected List<Component> baseLore;
    protected ItemStack prototype;

    @Override
    public void enable() {
        displayName = xmasify(getRawDisplayName(), false);
        prototype = getBaseItemStack();
        baseLore = Text.wrapLore(getDescription(), t -> t.color(RED));
        prototype.editMeta(meta -> {
                tooltip(meta, createTooltip());
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
        Component component = empty();
        if (bold) component = component.decorate(TextDecoration.BOLD);
        for (int i = 0; i < len; i += 1) {
            int white = 255 - (i * 255) / len;
            component = component.append(text(in.substring(i, i + 1))
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
                EquipmentSlotGroup slot = EquipmentSlotGroup.FEET;
                Attr.addNumber(meta, Attribute.GENERIC_ARMOR, "santa_boots_armor", 3.0, slot);
                Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, "santa_boots_armor_toughness", 3.0, slot);
            });
        return item;
    }

    protected ItemStack makePants() {
        ItemStack item = makeColoredLeatherItem(Material.LEATHER_LEGGINGS, Color.RED);
        item.editMeta(meta -> {
                EquipmentSlotGroup slot = EquipmentSlotGroup.LEGS;
                Attr.addNumber(meta, Attribute.GENERIC_ARMOR, "santa_pants_armor", 6.0, slot);
                Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, "santa_pants_armor", 3.0, slot);
            });
        return item;
    }

    protected ItemStack makeJacket() {
        ItemStack item = makeColoredLeatherItem(Material.LEATHER_CHESTPLATE, Color.RED);
        item.editMeta(meta -> {
                EquipmentSlotGroup slot = EquipmentSlotGroup.CHEST;
                Attr.addNumber(meta, Attribute.GENERIC_ARMOR, "santa_jacket_armor", 8.0, slot);
                Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, "santa_pants_armor_toughness", 3.0, slot);
            });
        return item;
    }

    protected ItemStack makeHat() {
        ItemStack item = Skull.create("Santa", SKULL_ID, SKULL_TEXTURE);
        item.editMeta(meta -> {
                EquipmentSlotGroup slot = EquipmentSlotGroup.HEAD;
                Attr.addNumber(meta, Attribute.GENERIC_ARMOR, "santa_hat_armor", 3.0, slot);
                Attr.addNumber(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, "santa_hat_armor_toughness", 3.0, slot);
            });
        return item;
    }
}
