package com.cavetale.mytems.item.upgradable;

import com.cavetale.core.struct.Vec2i;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static com.cavetale.mytems.MytemsPlugin.namespacedKey;

public interface UpgradableStat {
    /**
     * Get the uniquely identifying key of this item.  It must not
     * coincide with any other stat in the containing set, or any
     * other custom item property starting with 'mytems:'.
     */
    String getKey();

    default NamespacedKey getNamespacedKey() {
        return namespacedKey(getKey());
    }

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

    default UpgradableStatLevel getFirstLevel() {
        return getLevel(1);
    }

    default UpgradableStatLevel getMaxLevel() {
        final var levels = getLevels();
        return levels.get(levels.size() - 1);
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
     * Each dependency must have one level unlocked.
     */
    List<? extends UpgradableStat> getDependencies();

    /**
     * List all complete dependencies of this stat.
     * Complete dependencies must have all levels unlocked.
     */
    List<? extends UpgradableStat> getCompleteDependencies();

    /**
     * Lis all stats which this stat conflicts with.  This stat cannot
     * be unlocked if any of the returned stats have been unlocked.
     * This rule is not commutative.
     */
    List<? extends UpgradableStat> getConflicts();

    default void removeFromItem(ItemMeta meta) { }

    /**
     * Override either this method or the corresponding method in the
     * stat level in order to implement upgrade behavior such as
     * enchantments.
     */
    default void applyToItem(ItemMeta meta, int upgradeLevel) {
        getLevel(upgradeLevel).applyToItem(meta);
    }
}
