package com.cavetale.mytems.item.upgradable;

import com.cavetale.core.struct.Vec2i;
import com.cavetale.worldmarker.util.Tags;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import static com.cavetale.mytems.MytemsPlugin.namespacedKey;

public interface UpgradableStat {
    /**
     * Get the uniquely identifying key of this item.  It must not
     * coincide with any other stat in the containing set, or any
     * other custom item property starting with 'mytems:'.
     */
    String getKey();

    /**
     * Get the slot where this stat goes in the GUI.
     */
    Vec2i getGuiSlot();

    /**
     * Get all possible levels of this stat.  It is possible to
     * implement both UpgradableStat and Level, and then return a list
     * containing oneself.  Returning a single item list is expected
     * to be common.
     */
    List<? extends UpgradableStatLevel> getLevels();

    /**
     * Get the level corresponding to the level number.
     */
    default UpgradableStatLevel getLevel(int level) {
        if (level <= 0) return null;
        final List<? extends UpgradableStatLevel> levels = getLevels();
        return level < levels.size()
            ? levels.get(level - 1)
            : levels.get(levels.size() - 1);
    }

    default int getMaxLevel() {
        return getLevels().size();
    }

    /**
     * Get the title of this stat.
     */
    Component getTitle();

    /**
     * Get the base icon which is displayed in the GUI if no level has
     * been unlocked.
     */
    ItemStack getIcon();

    /**
     * List all dependencies of this stat.
     */
    List<? extends UpgradableStat> getDependencies();

    /**
     * Lis all stats which this stat conflicts with.  This stat cannot
     * be unlocked if any of the returned stats have been unlocked.
     * This rule is not commutative.
     */
    List<? extends UpgradableStat> getConflicts();

    /**
     * Get the upgrade level from an item.
     *
     * @param item the item
     * @return the level or 0
     */
    default int getUpgradeLevel(ItemStack item) {
        if (!item.hasItemMeta()) {
            return 0;
        }
        return getUpgradeLevel(item.getItemMeta().getPersistentDataContainer());
    }

    /**
     * Get the upgrade level from an item tag.
     *
     * @tag the item tag
     * @return the level or 0
     */
    default int getUpgradeLevel(PersistentDataContainer tag) {
        final Integer value = Tags.getInt(tag, namespacedKey(getKey()));
        return value != null
            ? value
            : 0;
    }

    /**
     * Set an upgrade level on an item tag.
     *
     * @param tag the item tag
     * @param level the level
     */
    default void setUpgradeLevel(PersistentDataContainer tag, int level) {
        if (level == 0) {
            tag.remove(namespacedKey(getKey()));
        } else {
            Tags.set(tag, namespacedKey(getKey()), 1);
        }
    }
}
