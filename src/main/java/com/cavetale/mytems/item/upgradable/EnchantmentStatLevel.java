package com.cavetale.mytems.item.upgradable;

import java.util.List;
import java.util.function.Supplier;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter
public final class EnchantmentStatLevel implements UpgradableStatLevel {
    private final int level;
    private final UpgradableItemTier requiredTier;
    private final Supplier<ItemStack> iconSupplier;
    private final Enchantment enchantment;
    private final int enchantmentLevel;

    public EnchantmentStatLevel(final int level, final UpgradableItemTier requiredTier, final Supplier<ItemStack> iconSupplier, final Enchantment enchantment, final int enchantmentLevel) {
        this.level = level;
        this.requiredTier = requiredTier;
        this.iconSupplier = iconSupplier;
        this.enchantment = enchantment;
        this.enchantmentLevel = enchantmentLevel;
    }

    public EnchantmentStatLevel(final int level, final UpgradableItemTier requiredTier, final Supplier<ItemStack> iconSupplier, final Enchantment enchantment) {
        this(level, requiredTier, iconSupplier, enchantment, level);
    }

    @Override
    public ItemStack getIcon() {
        return iconSupplier.get();
    }

    @Override
    public List<Component> getDescription() {
        return List.of(enchantment.displayName(enchantmentLevel));
    }

    @Override
    public void applyToItem(ItemMeta meta) {
        meta.addEnchant(enchantment, enchantmentLevel, true);
    }
}
