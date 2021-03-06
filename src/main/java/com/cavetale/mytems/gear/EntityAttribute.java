package com.cavetale.mytems.gear;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.attribute.AttributeModifier;

/**
 * An attribute which should be added to an entity by their item (set).
 *
 * For proper function, it is expected that each attribute has a
 * unique name within the context of this plugin. It will be prefixed
 * with "mytems:" for quick identification.
 *
 * A known weakness is that an attribute cannot change while retaining
 * the same name.
 */
@Data @AllArgsConstructor @NoArgsConstructor
public final class EntityAttribute {
    protected Attribute attribute;
    protected UUID uuid;
    protected String name;
    protected double amount;
    protected Operation operation;

    public AttributeModifier toAttributeModifier(String prefix) {
        return new AttributeModifier(uuid, prefix + name, amount, operation);
    }

    @Override
    public String toString() {
        String op;
        switch (operation) {
        case ADD_NUMBER: op = "+"; break; // add
        case ADD_SCALAR: op = "*"; break; // multiply_base
        case MULTIPLY_SCALAR_1: op = "+*"; // multiply
        default: op = "?";
        }
        return attribute.getKey().getKey() + op + String.format("%.2f", amount);
    }
}
