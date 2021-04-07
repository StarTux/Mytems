package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Text;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
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
    private BaseComponent[] displayName;

    @Override
    public void enable() {
        displayName = Text.builder("Magic Map").color(ChatColor.LIGHT_PURPLE).italic(false).create();
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayNameComponent(displayName);
        key.markItemMeta(meta);
        item.setItemMeta(meta);
        return item;
    }
}
