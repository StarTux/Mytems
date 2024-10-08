package com.cavetale.mytems.item.upgradable;

import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Level;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.MytemsPlugin.plugin;
import static com.cavetale.mytems.util.Text.formatDouble;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.GRAY;

/**
 * This level modifies the base stats of an item.  It expects to be
 * handed an attribute multimap where the first entry with a
 * 'minecraft:' namespace is a base stat, which it will replace.
 *
 * NOTE This will only work if
 * UpgradableItemTag::shouldHandleAttributes yields true, and
 * UpgradableItemTag::createAttributeMultimap yields the default
 * attribute modifiers, which is in the default implementation.
 */
@Getter
public final class BaseAttributeStatLevel implements UpgradableStatLevel {
    private final int level;
    private final UpgradableItemTier requiredTier;
    private final Supplier<ItemStack> iconSupplier;
    private final Attribute attribute;
    private final double amount;
    private boolean warned;

    public BaseAttributeStatLevel(final int level, final UpgradableItemTier requiredTier, final Supplier<ItemStack> iconSupplier, final Attribute attribute, final double amount) {
        this.level = level;
        this.requiredTier = requiredTier;
        this.iconSupplier = iconSupplier;
        this.attribute = attribute;
        this.amount = amount;
    }

    @Override
    public ItemStack getIcon() {
        return iconSupplier.get();
    }

    @Override
    public List<Component> getDescription() {
        final String prefix = amount >= 0.0 ? "+" : "";
        return List.of(textOfChildren(text(prefix + formatDouble(amount) + " "),
                                      Component.translatable(attribute.translationKey()))
                       .color(GRAY));
    }

    @Override
    public void applyAttributes(Multimap<Attribute, AttributeModifier> attributeMap) {
        Collection<AttributeModifier> mods = attributeMap.get(attribute);
        AttributeModifier mod = null;
        for (AttributeModifier it : mods) {
            if (NamespacedKey.MINECRAFT.equals(it.getKey().getNamespace())) {
                mod = it;
                break;
            }
        }
        if (mod == null) {
            if (!warned) {
                warned = true;
                plugin().getLogger().log(Level.SEVERE, attribute + " not found in " + attributeMap, new RuntimeException());
            }
            return;
        }
        mods.remove(mod);
        final AttributeModifier mod2 = new AttributeModifier(mod.getKey(),
                                                             mod.getAmount() + amount,
                                                             mod.getOperation(),
                                                             mod.getSlotGroup());
        mods.add(mod2);
    }
}
