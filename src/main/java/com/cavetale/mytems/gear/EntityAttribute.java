package com.cavetale.mytems.gear;

import com.cavetale.mytems.session.Attributes;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import static com.cavetale.mytems.MytemsPlugin.namespacedKey;

/**
 * An attribute which should be added to an entity by their item (set).
 *
 * For proper function, it is expected that each attribute has a
 * unique name within the context of this plugin.
 *
 * A known weakness is that an attribute cannot change while retaining
 * the same name.
 */
@Data @AllArgsConstructor @NoArgsConstructor
public final class EntityAttribute {
    protected Attribute attribute;
    protected NamespacedKey key;
    protected double amount;
    protected Operation operation;

    public EntityAttribute(final Attribute attribute, final String name, final double amount, final Operation operation) {
        this(attribute, namespacedKey(Attributes.TMP_PREFIX + name), amount, operation);
    }

    public AttributeModifier toAttributeModifier() {
        return new AttributeModifier(key, amount, operation);
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

    public boolean add(Attributable target) {
        AttributeInstance attributeInstance = target.getAttribute(attribute);
        if (attributeInstance == null) return false;
        for (AttributeModifier attributeModifier : attributeInstance.getModifiers()) {
            if (key.equals(attributeModifier.getKey())) {
                return false;
            }
        }
        attributeInstance.addModifier(toAttributeModifier());
        return true;
    }

    public boolean remove(Attributable target) {
        AttributeInstance attributeInstance = target.getAttribute(attribute);
        if (attributeInstance == null) return false;
        for (AttributeModifier attributeModifier : List.copyOf(attributeInstance.getModifiers())) {
            if (!key.equals(attributeModifier.getKey())) {
                continue;
            }
            attributeInstance.removeModifier(attributeModifier);
            return true;
        }
        return false;
    }
}
