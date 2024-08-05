package com.cavetale.mytems.item.spleef;

import com.cavetale.mytems.item.upgradable.UpgradableItem;
import com.cavetale.mytems.item.upgradable.UpgradableStat;
import java.util.List;

public final class SpleefShovelItem implements UpgradableItem {
    private static SpleefShovelItem instance;

    public static SpleefShovelItem spleefShovelItem() {
        if (instance == null) {
            instance = new SpleefShovelItem();
        }
        return instance;
    }

    @Override
    public List<SpleefShovelTier> getTiers() {
        return List.of(SpleefShovelTier.values());
    }

    @Override
    public List<? extends UpgradableStat> getStats() {
        return List.of(SpleefShovelStat.values());
    }

    @Override
    public UpgradableStat statForKey(String key) {
        return null;
    }

    @Override
    public int getMenuSize() {
        return 5 * 9;
    }
}
