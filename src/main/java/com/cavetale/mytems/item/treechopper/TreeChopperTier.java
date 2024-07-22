package com.cavetale.mytems.item.treechopper;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.upgradable.UpgradableItemTier;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import static com.cavetale.mytems.item.treechopper.TreeChopperItem.treeChopperItem;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@Getter
@RequiredArgsConstructor
public enum TreeChopperTier implements UpgradableItemTier {
    IRON(1, Mytems.TREE_CHOPPER, TreeChopperTag.Iron.class, TreeChopperTag.Iron::new,
         text("Steel Tree Chopper", color(0xa0a0a0)), color(0xa0a0a0)),
    GOLD(2, Mytems.GOLDEN_TREE_CHOPPER, TreeChopperTag.Gold.class, TreeChopperTag.Gold::new,
         text("Golden Tree Chopper", color(0xeabb3a)), color(0xeabb3a)),
    ;

    private final int tier;
    private final Mytems mytems;
    private final Class<? extends TreeChopperTag> tagClass;
    private final Supplier<TreeChopperTag> tagSupplier;
    private final Component displayName;
    private final TextColor menuColor;

    public static TreeChopperTier of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) {
                return it;
            }
        }
        throw new IllegalArgumentException("mytems=" + mytems);
    }

    public TreeChopperTag createTag() {
        return tagSupplier.get();
    }

    @Override
    public TreeChopperItem getUpgradableItem() {
        return treeChopperItem();
    }
}
