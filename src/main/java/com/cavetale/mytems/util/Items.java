package com.cavetale.mytems.util;

import com.google.common.collect.ImmutableListMultimap;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

public final class Items {
    public static Component nonItalic(Component in) {
        if (in == null || Component.empty().equals(in)) return in;
        return Component.text().append(in).decoration(TextDecoration.ITALIC, false).build();
    }

    public static List<Component> nonItalic(List<Component> in) {
        List<Component> out = new ArrayList<>(in.size());
        for (Component i : in) {
            out.add(nonItalic(i));
        }
        return out;
    }

    @Deprecated
    public static ItemStack text(ItemStack item, List<Component> text) {
        return tooltip(item, text);
    }

    @Deprecated
    public static void text(ItemMeta meta, List<Component> text) {
        tooltip(meta, text);
    }

    @Deprecated
    public static void text(ItemMeta meta, Component displayName, List<Component> lore) {
        tooltip(meta, displayName, lore);
    }

    public static ItemStack tooltip(ItemStack item, List<Component> text) {
        item.editMeta(meta -> text(meta, text));
        return item;
    }

    public static void tooltip(ItemMeta meta, List<Component> text) {
        meta.displayName(text.isEmpty() ? Component.empty() : nonItalic(text.get(0)));
        meta.lore(text.isEmpty() ? List.of() : text.subList(1, text.size())
                  .stream().map(Items::nonItalic).collect(Collectors.toList()));
    }

    public static void tooltip(ItemMeta meta, Component displayName, List<Component> lore) {
        meta.displayName(nonItalic(displayName));
        meta.lore(nonItalic(lore));
    }

    public static ItemStack deserialize(String base64) {
        final byte[] bytes = Base64.getDecoder().decode(base64);
        ItemStack item = ItemStack.deserializeBytes(bytes);
        return item;
    }

    public static String serializeToBase64(ItemStack itemStack) {
        return Base64.getEncoder().encodeToString(itemStack.serializeAsBytes());
    }

    public static void unbreakable(ItemMeta meta) {
        meta.setUnbreakable(true);
        if (meta instanceof Repairable) {
            Repairable repairable = (Repairable) meta;
            repairable.setRepairCost(9999);
        }
    }

    public static void glow(ItemMeta meta) {
        meta.setEnchantmentGlintOverride(true);
    }

    public static ItemStack glow(ItemStack item) {
        item.editMeta(Items::glow);
        return item;
    }

    public static void clearAttributes(ItemMeta meta) {
        meta.setAttributeModifiers(ImmutableListMultimap.of());
    }

    public static void iconize(ItemMeta meta) {
        meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP,
                          ItemFlag.HIDE_ARMOR_TRIM,
                          ItemFlag.HIDE_ATTRIBUTES,
                          ItemFlag.HIDE_DESTROYS,
                          ItemFlag.HIDE_DYE,
                          ItemFlag.HIDE_ENCHANTS,
                          ItemFlag.HIDE_PLACED_ON,
                          ItemFlag.HIDE_STORED_ENCHANTS,
                          ItemFlag.HIDE_UNBREAKABLE);
        meta.setAttributeModifiers(ImmutableListMultimap.of());
    }

    public static ItemStack iconize(ItemStack item) {
        item.editMeta(Items::iconize);
        return item;
    }

    public static ItemStack stack(Material material, int size) {
        return stack(new ItemStack(material), size);
    }

    public static ItemStack stack(ItemStack item, int size) {
        if (size < 1) throw new IllegalArgumentException("size=" + size);
        item.editMeta(meta -> {
                if (meta instanceof Damageable damageable) {
                    damageable.setMaxDamage(null);
                }
                meta.setMaxStackSize(size);
            });
        item.setAmount(size);
        return item;
    }

    private Items() { }
}
