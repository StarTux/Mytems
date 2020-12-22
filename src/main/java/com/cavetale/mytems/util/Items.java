package com.cavetale.mytems.util;

import java.util.Base64;
import org.bukkit.inventory.ItemStack;

public final class Items {
    private Items() { }

    public static ItemStack deserialize(String base64) {
        final byte[] bytes = Base64.getDecoder().decode(base64);
        ItemStack item = ItemStack.deserializeBytes(bytes);
        return item;
    }
}
