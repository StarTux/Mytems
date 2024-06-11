package com.cavetale.mytems.item.upgradable;

import java.util.List;

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

    default int getMenuSize() {
        return 6 * 9;
    }
}
