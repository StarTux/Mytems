package com.cavetale.mytems.session;

import com.cavetale.mytems.gear.EntityAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

/**
 * This manages the plugin-wide attributes mechanism for one player.
 *
 * Item sets (possibly items) can emit a list of EntityAttribute which
 * the player should have.  The Equipment keeps a list of them, which
 * is updated every tick in the Session. This manager reads the list
 * and adds the attributes that are missing from the player and
 * removes the ones that they should no longer have.
 */
public final class Attributes {
    public static final String TMP_PREFIX = "tmp_";
    private final Session session;
    private final Set<NamespacedKey> hasAttributes = new HashSet<>();
    private final Set<NamespacedKey> shouldHaveAttributeKeys = new HashSet<>();
    private final Map<Attribute, List<EntityAttribute>> shouldHaveAttributes = new EnumMap<>(Attribute.class);

    public Attributes(final Session session) {
        this.session = session;
        for (Attribute attr : Attribute.values()) {
            shouldHaveAttributes.put(attr, new ArrayList<>());
        }
    }

    public void enable() { }

    public void disable(Player player) {
        clear(player);
    }

    private void clear(Player player) {
        for (Attribute attribute : Attribute.values()) {
            AttributeInstance attributeInstance = player.getAttribute(attribute);
            if (attributeInstance == null) continue;
            for (AttributeModifier attributeModifier : attributeInstance.getModifiers()) {
                if (isTemporaryAttributeKey(attributeModifier.getKey())) {
                    attributeInstance.removeModifier(attributeModifier);
                }
            }
        }
    }

    public static boolean isTemporaryAttributeKey(NamespacedKey key) {
        return "mytems".equals(key.getNamespace()) && key.getKey().startsWith(TMP_PREFIX);
    }

    protected void update(Player player) {
        hasAttributes.clear();
        shouldHaveAttributeKeys.clear();
        shouldHaveAttributes.values().forEach(List::clear);
        // Compile all the attributes that should be there
        for (EntityAttribute entityAttribute : session.equipment.getEntityAttributes()) {
            shouldHaveAttributes.get(entityAttribute.getAttribute()).add(entityAttribute);
            shouldHaveAttributeKeys.add(entityAttribute.getKey());
        }
        for (Attribute attribute : Attribute.values()) {
            AttributeInstance attributeInstance = player.getAttribute(attribute);
            if (attributeInstance == null) continue;
            // See which attributes are there, by namespaced key.
            Collection<AttributeModifier> modifiers = attributeInstance.getModifiers();
            for (AttributeModifier attributeModifier : modifiers) {
                final NamespacedKey key = attributeModifier.getKey();
                if (!isTemporaryAttributeKey(key)) {
                    continue;
                }
                // Remove if they should not be there or mark as already had
                if (!shouldHaveAttributeKeys.contains(key)) {
                    attributeInstance.removeModifier(attributeModifier);
                } else {
                    hasAttributes.add(key);
                }
            }
            for (EntityAttribute entityAttribute : shouldHaveAttributes.get(attribute)) {
                if (hasAttributes.contains(entityAttribute.getKey())) {
                    continue;
                }
                AttributeModifier modifier = entityAttribute.toAttributeModifier();
                attributeInstance.addModifier(modifier);
            }
        }
    }
}
