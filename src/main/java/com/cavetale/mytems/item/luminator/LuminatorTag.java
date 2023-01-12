package com.cavetale.mytems.item.luminator;

import com.cavetale.mytems.MytemTag;
import org.bukkit.inventory.ItemStack;

public final class LuminatorTag extends MytemTag {
    protected int light = 0;

    @Override
    public boolean isEmpty() {
        return super.isEmpty()
            && light == 0;
    }

    @Override
    public void load(ItemStack itemStack) {
        super.load(itemStack);
    }

    @Override
    public void store(ItemStack itemStack) {
        super.store(itemStack);
    }

    @Override
    public boolean isDismissable() {
        return isEmpty();
    }

    @Override
    public boolean isSimilar(MytemTag other) {
        return super.isSimilar(other)
            && other instanceof LuminatorTag tag
            && tag.light == light;
    }
}
