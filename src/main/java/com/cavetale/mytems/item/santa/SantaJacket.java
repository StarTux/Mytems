package com.cavetale.mytems.item.santa;

import com.cavetale.mytems.Mytems;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public final class SantaJacket extends SantaItem {
    public static final Mytems KEY = Mytems.SANTA_JACKET;
    @Getter private final String description = ""
        + "Jingle all the way"
        + "\n\n"
        + "Soaring through the air without a windshield gets cold. This jacket will help with that.";

    public SantaJacket(final Mytems key) {
        super(key);
    }

    @Override
    public Mytems getKey() {
        return KEY;
    }

    @Override
    public ItemStack getBaseItemStack() {
        return makeJacket();
    }

    @Override
    public String getRawDisplayName() {
        return "Santa's Jacket";
    }
}
