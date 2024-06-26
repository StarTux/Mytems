package com.cavetale.mytems.item.upgradable;

import java.util.List;
import lombok.Value;

/**
 * Report how a player can interact with a given stat.
 */
@Value
public final class UpgradableStatStatus {
    private final int currentItemLevel;
    private final int requiredItemLevel;
    private final UpgradableStatLevel currentLevel; // nullable
    private final UpgradableStatLevel nextLevel; // nullable
    private final UpgradableItemTier currentItemTier;
    private final UpgradableItemTier requiredItemTier; // nullable
    private final List<UpgradableStat> missingDependencies;
    private final List<UpgradableStat> missingCompleteDependencies;
    private final List<UpgradableStat> conflictingStats;
    private final boolean disabled;

    public UpgradableStatStatus(final UpgradableItemTag tag, final UpgradableStat stat) {
        this.currentItemLevel = tag.getLevel();
        this.requiredItemLevel = tag.countTotalUpgrades() + 1;
        this.currentLevel = stat.getLevel(tag.getUpgradeLevel(stat));
        if (currentLevel == null) {
            this.nextLevel = stat.getFirstLevel();
        } else if (currentLevel != stat.getMaxLevel()) {
            this.nextLevel = stat.getLevel(currentLevel.getLevel() + 1);
        } else {
            this.nextLevel = null;
        }
        this.currentItemTier = tag.getUpgradableItemTier();
        this.requiredItemTier = nextLevel != null
            ? nextLevel.getRequiredTier()
            : null;
        this.missingDependencies = tag.getMissingDependenciesFor(stat);
        this.missingCompleteDependencies = tag.getMissingCompleteDependenciesFor(stat);
        this.conflictingStats = tag.getUnlockedConflictsWith(stat);
        this.disabled = tag.isStatDisabled(stat);
    }

    public boolean isUpgradable() {
        return hasNextLevel()
            && !hasStatConflict()
            && !isTierTooLow()
            && !isItemLevelTooLow()
            && !hasMissingDependencies()
            && !disabled;
    }

    public boolean isPermanentlyLocked() {
        return !hasNextLevel()
            || hasStatConflict();
    }

    /**
     * Determine whether this upgrade is considered locked and to be
     * displayed with the lock icon.
     */
    public boolean isLocked() {
        return hasStatConflict()
            || isTierTooLow();
    }

    public boolean hasCurrentLevel() {
        return currentLevel != null;
    }

    public boolean hasNextLevel() {
        return nextLevel != null;
    }

    public boolean isTierTooLow() {
        return requiredItemTier == null
            || currentItemTier.getTier() < requiredItemTier.getTier();
    }

    public boolean hasMissingDependencies() {
        return !missingDependencies.isEmpty()
            || !missingCompleteDependencies.isEmpty();
    }

    public boolean hasStatConflict() {
        return !conflictingStats.isEmpty();
    }

    public boolean isItemLevelTooLow() {
        return currentItemLevel < requiredItemLevel;
    }
}
