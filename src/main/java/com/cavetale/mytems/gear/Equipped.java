package com.cavetale.mytems.gear;

import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class Equipped {
    public final Slot slot;
    public final ItemStack itemStack;
    public final GearItem gearItem;

    @Override
    public String toString() {
        return slot.name().toLowerCase() + "=" + gearItem.getKey().serializeItem(itemStack);
    }
}
