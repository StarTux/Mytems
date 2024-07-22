package com.cavetale.mytems.item.hastypickaxe;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.upgradable.UpgradableItemTier;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import static com.cavetale.mytems.item.hastypickaxe.HastyPickaxeItem.hastyPickaxeItem;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextColor.color;

@Getter
@RequiredArgsConstructor
public enum HastyPickaxeTier implements UpgradableItemTier {
    COPPER(1, Mytems.HASTY_PICKAXE, HastyPickaxeTag.Copper.class, HastyPickaxeTag.Copper::new,
           text("Hasty Pickaxe", color(0xE77C56)), color(0xE77C56)),
    GOLD(2, Mytems.GOLDEN_HASTY_PICKAXE, HastyPickaxeTag.Gold.class, HastyPickaxeTag.Gold::new,
         text("Golden Hasty Pickaxe", color(0xF7C940)), color(0xB59410)),
    DIAMOND(3, Mytems.DIAMOND_HASTY_PICKAXE, HastyPickaxeTag.Diamond.class, HastyPickaxeTag.Diamond::new,
            text("Diamond Hasty Pickaxe", color(0x27B29A)), color(0x27B29A)),
    RUBY(4, Mytems.RUBY_HASTY_PICKAXE, HastyPickaxeTag.Ruby.class, HastyPickaxeTag.Ruby::new,
         text("Ruby Hasty Pickaxe", color(0xC7031F)), color(0xC7031F)),
    ;

    private final int tier;
    private final Mytems mytems;
    private final Class<? extends HastyPickaxeTag> tagClass;
    private final Supplier<HastyPickaxeTag> tagSupplier;
    private final Component displayName;
    private final TextColor menuColor;

    public static HastyPickaxeTier of(int tier) {
        return values()[tier - 1];
    }

    public static HastyPickaxeTier of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }

    public HastyPickaxeTag createTag() {
        return tagSupplier.get();
    }

    @Override
    public HastyPickaxeItem getUpgradableItem() {
        return hastyPickaxeItem();
    }
}
