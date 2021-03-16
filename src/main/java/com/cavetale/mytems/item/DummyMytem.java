package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Text;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor @Getter
public final class DummyMytem implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private BaseComponent[] displayName;

    @Override
    public void enable() {
        displayName = Text.builder(key.id).create();
        prototype = new ItemStack(key.material);
        key.markItemStack(prototype);
    }

    @Override
    public ItemStack getItem() {
        return prototype.clone();
    }
}
