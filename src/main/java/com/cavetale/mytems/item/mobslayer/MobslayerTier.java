package com.cavetale.mytems.item.mobslayer;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.upgradable.UpgradableItemTier;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

@Getter
@RequiredArgsConstructor
public enum MobslayerTier implements UpgradableItemTier {
    TIER_1(1, Mytems.MOBSLAYER, "Mobslayer", MobslayerTag.Tier1.class, MobslayerTag.Tier1::new),
    TIER_2(2, Mytems.MOBSLAYER2, "Mobslayer II", MobslayerTag.Tier2.class, MobslayerTag.Tier2::new),
    TIER_3(3, Mytems.MOBSLAYER3, "Mobslayer III", MobslayerTag.Tier3.class, MobslayerTag.Tier3::new),
    ;

    private final int tier;
    private final Mytems mytems;
    private final String displayName;
    private final Class<? extends MobslayerTag> tagClass;
    private final Supplier<? extends MobslayerTag> tagSupplier;

    @Override
    public MobslayerItem getUpgradableItem() {
        return MobslayerItem.mobslayerItem();
    }

    @Override
    public TextColor getMenuColor() {
        return NamedTextColor.RED;
    }

    public MobslayerTag createTag() {
        return tagSupplier.get();
    }

    public static MobslayerTier of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }
}
