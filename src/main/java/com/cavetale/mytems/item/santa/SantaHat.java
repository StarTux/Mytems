package com.cavetale.mytems.item.santa;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public final class SantaHat extends SantaItem {
    public static final Mytems KEY = Mytems.SANTA_HAT;
    @Getter private final String description = ""
        + "&c&oJingle bells, jingle bells"
        + "\n\n"
        + "&cThis mask carries the likeness of Santa, white beard included.";

    public SantaHat(final MytemsPlugin plugin) {
        super(plugin);
    }

    @Override
    public Mytems getKey() {
        return KEY;
    }

    @Override
    public ItemStack getBaseItemStack() {
        return makeHat();
    }

    @Override
    public String getRawDisplayName() {
        return "Santa's Hat";
    }
}
