package com.cavetale.mytems.item.upgradable;

import com.cavetale.mytems.MytemTag;
import com.cavetale.worldmarker.util.Tags;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.MytemsPlugin.namespacedKey;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class UpgradableItemTag extends MytemTag {
    private static final String XP = "xp";
    private static final String LEVEL = "level";
    private int xp;
    private int level;
    private Map<String, Integer> upgrades;

    /**
     * Check if all stats are empty and there are no xp, after
     * checking the superclass method.
     */
    @Override
    public boolean isEmpty() {
        return super.isEmpty()
            && xp == 0
            && (upgrades == null || upgrades.isEmpty());
    }

    /**
     * Get the upgradable stat set associated with this item.
     */
    public abstract UpgradableItem getUpgradableItem();

    /**
     * Get the tier of this tag, starting at 1.
     */
    public abstract UpgradableItemTier getUpgradableItemTier();

    /**
     * Load the xp and all stats from the item.
     */
    @Override
    public void load(ItemStack itemStack) {
        super.load(itemStack);
        if (!itemStack.hasItemMeta()) {
            return;
        }
        final var pdc = itemStack.getItemMeta().getPersistentDataContainer();
        this.xp = Tags.getInt(pdc, namespacedKey(XP), 0);
        this.level = Tags.getInt(pdc, namespacedKey(LEVEL), 0);
        this.upgrades = new HashMap<>();
        for (var stat : getUpgradableItem().getStats()) {
            final int upgradeLevel = Tags.getInt(pdc, stat.getNamespacedKey(), 0);
            if (upgradeLevel == 0) continue;
            upgrades.put(stat.getKey(), upgradeLevel);
        }
    }

    /**
     * Store the exp and stats.
     */
    @Override
    public void store(ItemStack itemStack) {
        super.store(itemStack);
        itemStack.editMeta(meta -> {
                final var pdc = meta.getPersistentDataContainer();
                Tags.set(pdc, namespacedKey(XP), xp);
                Tags.set(pdc, namespacedKey(LEVEL), level);
                for (UpgradableStat stat : getUpgradableItem().getStats()) {
                    final int upgradeLevel = upgrades != null
                        ? upgrades.getOrDefault(stat.getKey(), 0)
                        : 0;
                    if (upgradeLevel == 0) {
                        pdc.remove(stat.getNamespacedKey());
                        stat.removeFromItem(meta);
                    } else {
                        Tags.set(pdc, stat.getNamespacedKey(), 1);
                        stat.applyToItem(meta, upgradeLevel);
                    }
                }
            });
    }

    /**
     * Check if the other tag also is an object of this class and has
     * equal members.
     */
    public boolean isSimilar(MytemTag other) {
        if (!(other instanceof UpgradableItemTag tag)) return false;
        return super.isSimilar(other)
            && this.xp == tag.xp
            && Objects.equals(this.upgrades, tag.upgrades);
    }

    public final int getUpgradeLevel(UpgradableStat stat) {
        if (upgrades == null) return 0;
        return upgrades.getOrDefault(stat.getKey(), 0);
    }

    public final void setUpgradeLevel(UpgradableStat stat, int upgradeLevel) {
        if (upgradeLevel <= 0) {
            throw new IllegalArgumentException("stat=" + stat + " level=" + upgradeLevel);
        }
        upgrades = upgrades == null
            ? new HashMap<>()
            : new HashMap<>(upgrades);
        upgrades.put(stat.getKey(), upgradeLevel);
    }

    public final boolean hasUnlockedConflictsWith(UpgradableStat stat) {
        for (UpgradableStat conflict : stat.getConflicts()) {
            if (getUpgradeLevel(conflict) > 0) return true;
        }
        return false;
    }

    public final List<UpgradableStat> getUnlockedConflictsWith(UpgradableStat stat) {
        final List<? extends UpgradableStat> conflicts = stat.getConflicts();
        if (conflicts.isEmpty()) return List.of();
        final List<UpgradableStat> result = new ArrayList<>(conflicts.size());
        for (UpgradableStat conflict : conflicts) {
            if (getUpgradeLevel(conflict) > 0) {
                result.add(conflict);
            }
        }
        return result;
    }

    public final List<UpgradableStat> getMissingDependenciesFor(UpgradableStat stat) {
        final List<UpgradableStat> result = new ArrayList<>();
        for (UpgradableStat dependency : stat.getDependencies()) {
            if (getUpgradeLevel(dependency) == 0) {
                result.add(dependency);
            }
        }
        return result;
    }

    public final int countTotalUpgrades() {
        if (upgrades == null) return 0;
        int result = 0;
        for (var theLevel : upgrades.values()) {
            result += theLevel;
        }
        return result;
    }

    public final UpgradableStatStatus getUpgradeStatus(UpgradableStat stat) {
        final UpgradableItemTier itemTier = getUpgradableItemTier();
        final List<UpgradableStat> missingDependencies = getMissingDependenciesFor(stat);
        if (!missingDependencies.isEmpty()) {
            return new UpgradableStatStatus.UnmetDependencies(stat.getFirstLevel(), missingDependencies);
        }
        final UpgradableStatLevel currentLevel = stat.getLevel(getUpgradeLevel(stat));
        if (currentLevel == null) {
            final UpgradableStatLevel firstLevel = stat.getLevel(1);
            final UpgradableItemTier requiredTier = firstLevel.getRequiredTier();
            if (requiredTier != null && itemTier.getTier() < requiredTier.getTier()) {
                return new UpgradableStatStatus.TierTooLow(null, firstLevel, itemTier, requiredTier);
            }
            final List<UpgradableStat> conflicts = getUnlockedConflictsWith(stat);
            if (conflicts != null && !conflicts.isEmpty()) {
                return new UpgradableStatStatus.StatConflict(firstLevel, conflicts);
            }
            final int totalUpgradeCount = countTotalUpgrades();
            if (level <= totalUpgradeCount) {
                return new UpgradableStatStatus.ItemLevelTooLow(null, firstLevel, level, totalUpgradeCount + 1);
            }
            return new UpgradableStatStatus.Upgradable(null, firstLevel);
        } else {
            final UpgradableStatLevel maxLevel = stat.getMaxLevel();
            if (currentLevel.getLevel() >= maxLevel.getLevel()) {
                return new UpgradableStatStatus.MaxLevel(maxLevel);
            }
            final UpgradableStatLevel nextLevel = stat.getLevel(currentLevel.getLevel() + 1);
            final UpgradableItemTier requiredTier = nextLevel.getRequiredTier();
            if (itemTier.getTier() < requiredTier.getTier()) {
                return new UpgradableStatStatus.TierTooLow(currentLevel, nextLevel, itemTier, requiredTier);
            }
            final int totalUpgradeCount = countTotalUpgrades();
            if (level <= totalUpgradeCount) {
                return new UpgradableStatStatus.ItemLevelTooLow(currentLevel, nextLevel, level, totalUpgradeCount + 1);
            }
            return new UpgradableStatStatus.Upgradable(currentLevel, nextLevel);
        }
    }

    /**
     * Get the required exp for the next level.
     */
    public int getRequiredXp() {
        return 100 * (1 + getLevel());
    }

    /**
     * Add some xp which may or may not cause a level up.
     * @param amount the xp amount to be added
     * @return true if there was a level up, false otherwise.
     */
    public final boolean addXp(int amount) {
        xp += amount;
        int required = getRequiredXp();
        boolean result = false;
        while (xp >= required) {
            xp -= required;
            level += 1;
            required = getRequiredXp();
            result = true;
        }
        return result;
    }
}
