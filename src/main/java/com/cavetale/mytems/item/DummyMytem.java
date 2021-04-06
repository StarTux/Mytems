package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Text;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@RequiredArgsConstructor @Getter
public final class DummyMytem implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private BaseComponent[] displayName;

    @Override
    public void enable() {
        displayName = Text.builder(Text.toCamelCase(key, " ")).italic(false).create();
        prototype = new ItemStack(key.material);
        ItemMeta meta = prototype.getItemMeta();
        meta.setDisplayNameComponent(displayName);
        meta.addItemFlags(ItemFlag.values());
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
    }

    @Override
    public ItemStack getItem() {
        return prototype.clone();
    }
}
