package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * This will be replaced by the MagicMap plugin's own implementation,
 * provided it is installed.
 */
@Getter @RequiredArgsConstructor
public final class MagicMap implements Mytem {
    private final Mytems key;
    private Component displayName;

    @Override
    public void enable() {
        displayName = Component.text("Magic Map").color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false);
    }

    @Override
    public ItemStack createItemStack() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(displayName);
        key.markItemMeta(meta);
        item.setItemMeta(meta);
        return item;
    }
}
