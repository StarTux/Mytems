package com.cavetale.mytems.item.spleef;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.upgradable.UpgradableItemTier;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextColor.color;

@Getter
@RequiredArgsConstructor
public enum SpleefShovelTier implements UpgradableItemTier {
    COPPER(1, Mytems.COPPER_SPLEEF_SHOVEL, "Copper Spleef Shovel", color(0xd67b5b),
           SpleefShovelTag.Copper.class, SpleefShovelTag.Copper::new),
    IRON(2, Mytems.IRON_SPLEEF_SHOVEL, "Iron Spleef Shovel", color(0xb9b9b9),
         SpleefShovelTag.Iron.class, SpleefShovelTag.Iron::new),
    GOLD(3, Mytems.GOLDEN_SPLEEF_SHOVEL, "Golden Spleef Shovel", color(0xffc107),
         SpleefShovelTag.Gold.class, SpleefShovelTag.Gold::new),
    DIAMOND(4, Mytems.DIAMOND_SPLEEF_SHOVEL, "Diamond Spleef Shovel", color(0xb3e0dc),
            SpleefShovelTag.Diamond.class, SpleefShovelTag.Diamond::new),
    ;

    private final int tier;
    private final Mytems mytems;
    private final String displayName;
    private final TextColor color;
    private final Class<? extends SpleefShovelTag> tagClass;
    private final Supplier<? extends SpleefShovelTag> tagSupplier;

    public static SpleefShovelTier of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }

    public Component getTitle() {
        return text(displayName, color);
    }

    public SpleefShovelTag createTag() {
        return tagSupplier.get();
    }

    @Override
    public SpleefShovelItem getUpgradableItem() {
        return SpleefShovelItem.spleefShovelItem();
    }

    @Override
    public TextColor getMenuColor() {
        return color;
    }
}
