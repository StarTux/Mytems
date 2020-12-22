package com.cavetale.mytems.session;

import com.cavetale.mytems.gear.EntityAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;

/**
 * This manages the plugin-wide attributes mechanism for one player.
 *
 * Item sets (possibly items) can emit a list of EntityAttribute which
 * the player should have. The Equipment keeps a list of them, which
 * is updated every tick in the Session. This manager reads the list
 * and adds the attributes that are missing from the player and
 * removes the ones that they should no longer have.
 */
public final class Attributes {
    public static final String PREFIX = "mytems:attr";
    private final Session session;
    private final Set<String> hasAttributes = new HashSet<>();
    private final Set<String> shouldHaveAttributeNames = new HashSet<>();
    private final Map<Attribute, List<EntityAttribute>> shouldHaveAttributes = new EnumMap<>(Attribute.class);

    public Attributes(final Session session) {
        this.session = session;
        for (Attribute attr : Attribute.values()) {
            shouldHaveAttributes.put(attr, new ArrayList<>());
        }
    }

    public void enable() { }

    public void disable() {
        clear();
    }

    private void clear() {
        for (Attribute attribute : Attribute.values()) {
            AttributeInstance attributeInstance = session.player.getAttribute(attribute);
            if (attributeInstance == null) continue;
            for (AttributeModifier attributeModifier : attributeInstance.getModifiers()) {
                if (!(attributeModifier.getName().startsWith(PREFIX))) continue;
                attributeInstance.removeModifier(attributeModifier);
            }
        }
    }

    protected void update() {
        hasAttributes.clear();
        shouldHaveAttributeNames.clear();
        shouldHaveAttributes.values().forEach(List::clear);
        // Compile all the attributes that should be there
        for (EntityAttribute entityAttribute : session.equipment.getEntityAttributes()) {
            shouldHaveAttributes.get(entityAttribute.getAttribute()).add(entityAttribute);
            shouldHaveAttributeNames.add(entityAttribute.getName());
        }
        for (Attribute attribute : Attribute.values()) {
            AttributeInstance attributeInstance = session.player.getAttribute(attribute);
            if (attributeInstance == null) continue;
            // See which attributes are there, by name.
            Collection<AttributeModifier> modifiers = attributeInstance.getModifiers();
            for (AttributeModifier attributeModifier : modifiers) {
                String name = attributeModifier.getName();
                if (!name.startsWith(PREFIX)) continue;
                name = name.substring(PREFIX.length());
                // Remove if they should not be there or mark as already had
                if (!shouldHaveAttributeNames.contains(name)) {
                    attributeInstance.removeModifier(attributeModifier);
                } else {
                    hasAttributes.add(name);
                }
            }
            for (EntityAttribute entityAttribute : shouldHaveAttributes.get(attribute)) {
                if (hasAttributes.contains(entityAttribute.getName())) continue;
                AttributeModifier modifier = entityAttribute.toAttributeModifier(PREFIX);
                attributeInstance.addModifier(modifier);
            }
        }
    }

    protected void tick() {
        update();
    }
}
