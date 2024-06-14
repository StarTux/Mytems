package com.cavetale.mytems.item.upgradable;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Report how a player can interact with a given stat.
 *
 * Returned by UpgradableItemTag#getUpgradeStatus(UpgradableStat).
 */
@Getter
@RequiredArgsConstructor
public abstract class UpgradableStatStatus {
    private final Type type;
    private final UpgradableStatLevel currentLevel;
    private final UpgradableStatLevel nextLevel;

    @Getter
    @RequiredArgsConstructor
    public enum Type {
        UPGRADABLE(true, false),
        TIER_TOO_LOW(false, false),
        UNMET_DEPENDENCIES(false, false),
        STAT_CONFLICT(false, true),
        MAX_LEVEL(false, true),
        ITEM_LEVEL_TOO_LOW(false, false),
        ;

        private final boolean upgradable;
        private final boolean permanentlyLocked;
    }

    public final boolean isUpgradable() {
        return type.isUpgradable();
    }

    public final boolean hasCurrentLevel() {
        return currentLevel != null;
    }

    public final boolean hasNextLevel() {
        return nextLevel != null;
    }

    /**
     * Stat can be upgraded from one level to the next.
     */
    @Value @EqualsAndHashCode(callSuper = true)
    public static final class Upgradable extends UpgradableStatStatus {
        public Upgradable(final UpgradableStatLevel currentLevel, final UpgradableStatLevel nextLevel) {
            super(Type.UPGRADABLE, currentLevel, nextLevel);
        }
    }

    /**
     * This renderes a level of the stat unobtainable until the item
     * has been upgraded.
     *
     * Since this stat may not have been unlocked at all,
     * getCurrentLevel() may yield null.  hasCurrentLevel() may be
     * checked first.
     */
    @Value @EqualsAndHashCode(callSuper = true)
    public static final class TierTooLow extends UpgradableStatStatus {
        private final UpgradableItemTier currentTier;
        private final UpgradableItemTier requiredTier;

        public TierTooLow(final UpgradableStatLevel currentLevel, final UpgradableStatLevel nextLevel, final UpgradableItemTier currentTier, final UpgradableItemTier requiredTier) {
            super(Type.TIER_TOO_LOW, currentLevel, nextLevel);
            this.currentTier = currentTier;
            this.requiredTier = requiredTier;
        }
    }

    /**
     * Missing dependencies render the entire stat unobtainable until
     * the dependencies are fulfilled.
     */
    @Value @EqualsAndHashCode(callSuper = true)
    public static final class UnmetDependencies extends UpgradableStatStatus {
        private final List<UpgradableStat> missingDependencies;

        public UnmetDependencies(final UpgradableStatLevel firstLevel, final List<UpgradableStat> missingDependencies) {
            super(Type.UNMET_DEPENDENCIES, null, firstLevel);
            this.missingDependencies = missingDependencies;
        }
    }

    /**
     * Conflicts render the entire stat permanently unobtainable.
     */
    @Value @EqualsAndHashCode(callSuper = true)
    public static final class StatConflict extends UpgradableStatStatus {
        private final List<UpgradableStat> conflictingStats;

        public StatConflict(final UpgradableStatLevel firstLevel, final List<UpgradableStat> conflictingStats) {
            super(Type.STAT_CONFLICT, null, firstLevel);
            this.conflictingStats = conflictingStats;
        }
    }

    @Value @EqualsAndHashCode(callSuper = true)
    public static final class MaxLevel extends UpgradableStatStatus {
        public MaxLevel(final UpgradableStatLevel maxLevel) {
            super(Type.MAX_LEVEL, maxLevel, null);
        }
    }

    /**
     * This should be checked last so we do not generate false hope
     * that any given stat could be unlocked if there were enough
     * points.
     */
    @Value @EqualsAndHashCode(callSuper = true)
    public static final class ItemLevelTooLow extends UpgradableStatStatus {
        private final int currentItemLevel;
        private final int requiredItemLevel;

        public ItemLevelTooLow(final UpgradableStatLevel currentLevel, final UpgradableStatLevel nextLevel, final int currentItemLevel, final int requiredItemLevel) {
            super(Type.ITEM_LEVEL_TOO_LOW, currentLevel, nextLevel);
            this.currentItemLevel = currentItemLevel;
            this.requiredItemLevel = requiredItemLevel;
        }

        public int getMissingItemLevels() {
            return requiredItemLevel - currentItemLevel;
        }
    }
}
