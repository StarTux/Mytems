package com.cavetale.mytems.item.santa;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public final class SantaBoots extends SantaItem {
    public static final Mytems KEY = Mytems.SANTA_BOOTS;
    @Getter private final String description = ""
        + "&c&oIn a one-horse open sleigh, hey!"
        + "\n\n"
        + "&cJumping off a sleigh at maximum velocity, just to land in a searing hot fireplace is not easy. With these boots however, it is quite manageable.";

    public SantaBoots(final MytemsPlugin plugin) {
        super(plugin);
    }

    @Override
    public Mytems getKey() {
        return KEY;
    }

    @Override
    public String getId() {
        return KEY.id;
    }

    @Override
    public ItemStack getBaseItemStack() {
        return makeBoots();
    }

    @Override
    public String getRawDisplayName() {
        return "Santa's Boots";
    }
}
