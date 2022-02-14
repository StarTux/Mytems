package com.cavetale.mytems.util;

import java.util.UUID;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;

public final class Attr {
    private Attr() { }

    public static void add(ItemMeta meta, Attribute attribute, UUID uuid, String name, double value, Operation operation, EquipmentSlot slot) {
        meta.addAttributeModifier(attribute, new AttributeModifier(uuid, name, value, operation, slot));
    }

    public static void addNumber(ItemMeta meta, Attribute attribute, UUID uuid, String name, double value, EquipmentSlot slot) {
        add(meta, attribute, uuid, name, value, Operation.ADD_NUMBER, slot);
    }

    public static AttributeModifier of(final UUID uuid, final String name, final double value, final Operation operation, final EquipmentSlot slot) {
        return new AttributeModifier(uuid, name, value, operation, slot);
    }
}
