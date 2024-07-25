package com.cavetale.mytems.item.finder;

import com.cavetale.mytems.item.upgradable.UpgradableItem;
import java.util.List;

public final class FinderItem implements UpgradableItem {
    private static final FinderItem INSTANCE = new FinderItem();

    public static FinderItem finderItem() {
        return INSTANCE;
    }

    @Override
    public List<FinderTier> getTiers() {
        return List.of(FinderTier.values());
    }

    @Override
    public List<FinderStat> getStats() {
        return List.of(FinderStat.values());
    }

    @Override
    public FinderStat statForKey(String key) {
        return FinderStat.forKey(key);
    }

    @Override
    public int getMenuSize() {
        return 6 * 9;
    }
}
