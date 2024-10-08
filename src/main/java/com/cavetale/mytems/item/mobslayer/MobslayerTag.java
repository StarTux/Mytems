package com.cavetale.mytems.item.mobslayer;

import com.cavetale.mytems.item.upgradable.UpgradableItemTag;

public abstract class MobslayerTag extends UpgradableItemTag {
    @Override
    public final MobslayerItem getUpgradableItem() {
        return MobslayerItem.mobslayerItem();
    }

    @Override
    public final boolean shouldHandleAttributes() {
        return true;
    }

    public static final class Tier1 extends MobslayerTag {
        @Override
        public MobslayerTier getUpgradableItemTier() {
            return MobslayerTier.TIER_1;
        }
    };

    public static final class Tier2 extends MobslayerTag {
        @Override
        public MobslayerTier getUpgradableItemTier() {
            return MobslayerTier.TIER_2;
        }
    };

    public static final class Tier3 extends MobslayerTag {
        @Override
        public MobslayerTier getUpgradableItemTier() {
            return MobslayerTier.TIER_3;
        }
    };
}
