package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.LIGHT_PURPLE;

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
        displayName = text("Magic Map", LIGHT_PURPLE);
    }

    @Override
    public ItemStack createItemStack() {
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.itemName(displayName);
        key.markItemMeta(meta);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
