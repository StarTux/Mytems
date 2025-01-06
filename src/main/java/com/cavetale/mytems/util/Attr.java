package com.cavetale.mytems.util;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.meta.ItemMeta;
import static com.cavetale.mytems.MytemsPlugin.namespacedKey;

public final class Attr {
    private Attr() { }

    public static void add(ItemMeta meta, Attribute attribute, String name, double value, Operation operation, EquipmentSlotGroup slot) {
        meta.addAttributeModifier(attribute, new AttributeModifier(namespacedKey(name), value, operation, slot));
    }

    public static void addNumber(ItemMeta meta, Attribute attribute, String name, double value, EquipmentSlotGroup slot) {
        add(meta, attribute, name, value, Operation.ADD_NUMBER, slot);
    }

    public static void addScalar(ItemMeta meta, Attribute attribute, String name, double value, EquipmentSlotGroup slot) {
        add(meta, attribute, name, value, Operation.ADD_SCALAR, slot);
    }

    public static void multiplyScalar1(ItemMeta meta, Attribute attribute, String name, double value, EquipmentSlotGroup slot) {
        add(meta, attribute, name, value, Operation.MULTIPLY_SCALAR_1, slot);
    }

    public static AttributeModifier of(final String name, final double value, final Operation operation, final EquipmentSlotGroup slot) {
        return new AttributeModifier(namespacedKey(name), value, operation, slot);
    }
}
