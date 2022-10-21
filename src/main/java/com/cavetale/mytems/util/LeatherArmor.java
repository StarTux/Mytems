package com.cavetale.mytems.util;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public final class LeatherArmor {
    private LeatherArmor() { }

    public static ItemStack leatherArmor(Material material, int r, int g, int b) {
        ItemStack result = new ItemStack(material);
        result.editMeta(m -> {
                if (m instanceof LeatherArmorMeta meta) {
                    meta.setColor(Color.fromRGB(r, g, b));
                    meta.addItemFlags(ItemFlag.HIDE_DYE);
                }
            });
        return result;
    }

    public static ItemStack leatherArmor(Material material, TextColor color) {
        return leatherArmor(material, color.red(), color.green(), color.blue());
    }
}
