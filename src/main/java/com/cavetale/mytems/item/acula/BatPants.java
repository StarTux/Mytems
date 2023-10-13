package com.cavetale.mytems.item.acula;

import com.cavetale.mytems.Mytems;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
public final class BatPants extends AculaItem {
    private final String rawDisplayName = "Bat Pants";
    private final String description = "Can be worn while suspended upside down.";

    public BatPants(final Mytems key) {
        super(key);
    }

    @Override
    protected ItemStack getRawItemStack() {
        return new ItemStack(Material.NETHERITE_LEGGINGS);
    }
}
