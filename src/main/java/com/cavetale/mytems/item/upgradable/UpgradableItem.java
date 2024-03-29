package com.cavetale.mytems.item.upgradable;

import com.cavetale.worldmarker.util.Tags;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import static com.cavetale.mytems.MytemsPlugin.namespacedKey;

/**
 * This describes an upgradable item.  An implementing class
 * is expected to provide a single(ton) instance to the
 * UpgradableItemTag implementation in order to provide static
 * information about a collection of stats belonging to an upgradable
 * item.
 *
 * This is essentially a collection of utility functions which other
 * components of an upgradable item are going to take advantage of,
 * especially UpgradableItemTag and diverse listeners.
 *
 * The getStats method needs to be overriden.  All other defaults are
 * expected to work on any item.
 */
public interface UpgradableItem {
    /**
     * Get all tiers.
     */
    List<? extends UpgradableItemTier> getTiers();

    /**
     * Return all stats in a list.
     */
    List<? extends UpgradableStat> getStats();

    default UpgradableItemTier getTier(int tier) {
        return getTiers().get(tier - 1);
    }

    /**
     * Load all upgrade levels from an item.
     */
    default Map<String, Integer> getUpgradeLevels(ItemStack item) {
        if (!item.hasItemMeta()) {
            return Map.of();
        }
        return getUpgradeLevels(item.getItemMeta().getPersistentDataContainer());
    }

    /**
     * Get all upgrade levels of an item.
     */
    default Map<String, Integer> getUpgradeLevels(PersistentDataContainer tag) {
        final var result = new HashMap<String, Integer>();
        for (var stat : getStats()) {
            final var key = stat.getKey();
            result.put(key, stat.getUpgradeLevel(tag));
        }
        return result;
    }

    /**
     * Set the upgrade levels on an item.
     *
     * @param item the item
     * @param levels the level map
     */
    default void setUpgradeLevels(ItemStack item, Map<String, Integer> levels) {
        item.editMeta(meta -> {
                setUpgradeLevels(meta.getPersistentDataContainer(), levels);
            });
    }

    /**
     * Set the upgrade levels on an item tag.
     *
     * @param tag the item tag
     * @param levels the level map
     */
    default void setUpgradeLevels(PersistentDataContainer tag, Map<String, Integer> levels) {
        for (var entry : levels.entrySet()) {
            Tags.set(tag, namespacedKey(entry.getKey()), entry.getValue());
        }
    }

    default int getMenuSize() {
        return 3 * 9;
    }

    Component getMenuTitle();
}
