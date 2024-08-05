package com.cavetale.mytems.item.upgradable;

import java.util.List;
import java.util.function.Supplier;
import lombok.Value;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

@Value
public class DefaultUpgradableStatLevel implements UpgradableStatLevel {
    private final int level;
    private final Supplier<ItemStack> iconSupplier;
    private final List<Component> description;
    private final UpgradableItemTier requiredTier;

    @Override
    public final ItemStack getIcon() {
        return iconSupplier.get();
    }
}
