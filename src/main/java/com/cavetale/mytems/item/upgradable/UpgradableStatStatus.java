package com.cavetale.mytems.item.upgradable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * An enum to tell if a stat is upgradable.
 */
@Getter
@RequiredArgsConstructor
public enum UpgradableStatStatus {
    TIER_TOO_LOW(false),
    STAT_CONFLICT(false),
    MAX_LEVEL(false),
    UPGRADABLE(true),
    ;

    private final boolean success;
}
