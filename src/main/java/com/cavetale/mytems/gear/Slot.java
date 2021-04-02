package com.cavetale.mytems.gear;

import com.destroystokyo.paper.MaterialTags;
import org.bukkit.Material;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public enum Slot {
    MAIN_HAND(EquipmentSlot.HAND),
    OFF_HAND(EquipmentSlot.OFF_HAND),
    HELMET(EquipmentSlot.HEAD),
    CHESTPLATE(EquipmentSlot.CHEST),
    LEGGINGS(EquipmentSlot.LEGS),
    BOOTS(EquipmentSlot.FEET);

    public final EquipmentSlot bukkitEquipmentSlot;

    Slot(final EquipmentSlot bukkitEquipmentSlot) {
        this.bukkitEquipmentSlot = bukkitEquipmentSlot;
    }

    public boolean guess(ItemStack item) {
        if (item == null) return false;
        Material mat = item.getType();
        if (MaterialTags.HEAD_EQUIPPABLE.isTagged(mat)) return this == HELMET;
        if (MaterialTags.CHEST_EQUIPPABLE.isTagged(mat)) return this == CHESTPLATE;
        if (MaterialTags.LEGGINGS.isTagged(mat)) return this == LEGGINGS;
        if (MaterialTags.BOOTS.isTagged(mat)) return this == BOOTS;
        return this == MAIN_HAND || this == OFF_HAND;
    }
}
