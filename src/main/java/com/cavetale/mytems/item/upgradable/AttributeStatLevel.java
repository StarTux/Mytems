package com.cavetale.mytems.item.upgradable;

import com.google.common.collect.Multimap;
import java.util.List;
import java.util.function.Supplier;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.MytemsPlugin.namespacedKey;
import static com.cavetale.mytems.util.Text.formatDouble;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.GRAY;

/**
 * Add an attribute to an item.
 *
 * NOTE This will only work if
 * UpgradableItemTag::shouldHandleAttributes yields true.
 */
@Getter
public final class AttributeStatLevel implements UpgradableStatLevel {
    private final int level;
    private final UpgradableItemTier requiredTier;
    private final Supplier<ItemStack> iconSupplier;
    private final Attribute attribute;
    private final AttributeModifier attributeModifier;

    public AttributeStatLevel(final int level, final UpgradableItemTier requiredTier, final Supplier<ItemStack> iconSupplier, final Attribute attribute, final AttributeModifier attributeModifier) {
        this.level = level;
        this.requiredTier = requiredTier;
        this.iconSupplier = iconSupplier;
        this.attribute = attribute;
        this.attributeModifier = attributeModifier;
    }

    public AttributeStatLevel(final int level, final UpgradableItemTier requiredTier, final Supplier<ItemStack> iconSupplier,
                              final Attribute attribute, final String name, final double value, final Operation operation, final EquipmentSlotGroup slot) {
        this(level, requiredTier, iconSupplier, attribute, new AttributeModifier(namespacedKey(name), value, operation, slot));
    }

    @Override
    public ItemStack getIcon() {
        return iconSupplier.get();
    }

    @Override
    public List<Component> getDescription() {
        final double amount = attributeModifier.getAmount();
        final String prefix = amount >= 0.0 ? "+" : "";
        final String suffix = attributeModifier.getOperation() == Operation.ADD_NUMBER ? "" : "%";
        return List.of(textOfChildren(text(prefix + formatDouble(amount) + suffix + " "),
                                      Component.translatable(attribute.translationKey()))
                       .color(GRAY));
    }

    @Override
    public void applyAttributes(Multimap<Attribute, AttributeModifier> attributes) {
        attributes.put(attribute, attributeModifier);
    }
}
