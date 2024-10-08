package com.cavetale.mytems.item.mobslayer;

import com.cavetale.mytems.item.upgradable.UpgradableItem;
import java.util.List;

public final class MobslayerItem implements UpgradableItem {
    private static MobslayerItem instance;

    public static MobslayerItem mobslayerItem() {
        if (instance == null) {
            instance = new MobslayerItem();
        }
        return instance;
    }

    @Override
    public List<MobslayerTier> getTiers() {
        return List.of(MobslayerTier.values());
    }

    @Override
    public List<MobslayerStat> getStats() {
        return List.of(MobslayerStat.values());
    }
}
