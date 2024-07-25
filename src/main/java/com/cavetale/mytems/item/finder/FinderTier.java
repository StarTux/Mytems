package com.cavetale.mytems.item.finder;

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
public enum FinderTier implements UpgradableItemTier {
    STRUCTURE(1, 150, Mytems.STRUCTURE_FINDER, "Structure Finder", color(0xad8053),
              FinderTag.Structure.class, FinderTag.Structure::new,
              "Find common structures known to most sailors, adventurers, and birds"),
    SECRET(2, 200, Mytems.SECRET_FINDER, "Secret Finder", color(0xffc103),
           FinderTag.Secret.class, FinderTag.Secret::new,
           "Locate secret relics hidden from contemporary knowledge"),
    MYSTIC(3, 250, Mytems.MYSTIC_FINDER, "Mystic Finder", color(0x00caca),
           FinderTag.Mystic.class, FinderTag.Mystic::new,
           "Pinpoint all ancient heritage sites with the wisdom of the elders"),
    MASTER(4, 300, Mytems.MASTER_FINDER, "Master Finder", color(0xe46868),
           FinderTag.Master.class, FinderTag.Master::new,
           "Pinpoint all ancient heritage sites with the wisdom of the elders"),
    ;

    public static final FinderTier TIER_1 = STRUCTURE;
    public static final FinderTier TIER_2 = SECRET;
    public static final FinderTier TIER_3 = MYSTIC;
    public static final FinderTier TIER_4 = MASTER;

    private final int tier;
    private final int range;
    private final Mytems mytems;
    private final String displayName;
    private final TextColor color;
    private final Class<? extends FinderTag> tagClass;
    private final Supplier<FinderTag> tagSupplier;
    private final String description;

    @Override
    public FinderItem getUpgradableItem() {
        return FinderItem.finderItem();
    }

    @Override
    public TextColor getMenuColor() {
        return color;
    }

    public static FinderTier of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }

    public static FinderTier forKey(String key) {
        try {
            return valueOf(key.toUpperCase());
        } catch (IllegalArgumentException iae) {
            return null;
        }
    }

    public FinderTag createTag() {
        return tagSupplier.get();
    }

    public Component getTitle() {
        return text(displayName, color);
    }
}
