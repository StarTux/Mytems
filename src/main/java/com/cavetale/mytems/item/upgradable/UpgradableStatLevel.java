package com.cavetale.mytems.item.upgradable;

import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Represent one upgrade level of a stat.  Each stat must have at
 * least one level.
 */
public interface UpgradableStatLevel {
    /**
     * Get the level number, starting at 1.
     */
    int getLevel();

    /**
     * Get the icon which is displayed in the GUI once this level
     * has been unlocked.
     */
    ItemStack getIcon();

    /**
     * Get the description of this level.
     */
    List<Component> getDescription();

    /**
     * Get the required tier.
     */
    default UpgradableItemTier getRequiredTier() {
        return null;
    }

    default void applyToItem(ItemMeta meta) { }
}
