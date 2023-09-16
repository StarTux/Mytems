package com.cavetale.mytems.util;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

public final class Items {
    private Items() { }

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

    public static ItemStack text(ItemStack item, List<Component> text) {
        item.editMeta(meta -> text(meta, text));
        return item;
    }

    public static void text(ItemMeta meta, List<Component> text) {
        meta.displayName(text.isEmpty() ? Component.empty() : nonItalic(text.get(0)));
        meta.lore(text.isEmpty() ? List.of() : text.subList(1, text.size())
                  .stream().map(Items::nonItalic).collect(Collectors.toList()));
    }

    public static void text(ItemMeta meta, Component displayName, List<Component> lore) {
        meta.displayName(displayName);
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
}
