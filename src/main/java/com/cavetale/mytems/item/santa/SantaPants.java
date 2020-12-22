package com.cavetale.mytems.item.santa;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public final class SantaPants extends SantaItem {
    public static final Mytems KEY = Mytems.SANTA_PANTS;
    @Getter private final String description = ""
        + "&c&oOh what fun it is to ride"
        + "\n\n"
        + "&cSince a sled does not come with seat pads,"
        + " these pants are extra thick for more comfort while sitting for hours on end.";

    public SantaPants(final MytemsPlugin plugin) {
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
        return makePants();
    }

    @Override
    public String getRawDisplayName() {
        return "Santa's Pants";
    }
}
