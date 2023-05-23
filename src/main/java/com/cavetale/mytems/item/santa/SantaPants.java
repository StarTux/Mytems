package com.cavetale.mytems.item.santa;

import com.cavetale.mytems.Mytems;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public final class SantaPants extends SantaItem {
    public static final Mytems KEY = Mytems.SANTA_PANTS;
    @Getter private final String description = ""
        + "Oh what fun it is to ride"
        + "\n\n"
        + "Since a sled does not come with seat pads,"
        + " these pants are extra thick for more comfort while sitting for hours on end.";

    public SantaPants(final Mytems key) {
        super(key);
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
