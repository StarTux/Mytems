package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class TemplateItem implements Mytem {
    private final MytemsPlugin plugin;
    public static final Mytems KEY = null;

    @Override
    public Mytems getKey() {
        return KEY;
    }

    @Override
    public String getId() {
        return KEY.id;
    }

    @Override
    public void enable() {
    }

    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public BaseComponent[] getDisplayName() {
        return null;
    }
}
