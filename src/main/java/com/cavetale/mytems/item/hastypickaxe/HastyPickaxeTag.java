package com.cavetale.mytems.item.hastypickaxe;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.upgradable.UpgradableItem;
import com.cavetale.mytems.item.upgradable.UpgradableItemTag;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public abstract class HastyPickaxeTag extends UpgradableItemTag {
    public static final class Copper extends HastyPickaxeTag {
        @Override
        public HastyPickaxeTier getTier() {
            return HastyPickaxeTier.COPPER;
        }
    }

    public static final class Gold extends HastyPickaxeTag {
        @Override
        public HastyPickaxeTier getTier() {
            return HastyPickaxeTier.GOLD;
        }
    }

    public static final class Diamond extends HastyPickaxeTag {
        @Override
        public HastyPickaxeTier getTier() {
            return HastyPickaxeTier.DIAMOND;
        }
    }

    @Override
    public abstract HastyPickaxeTier getTier();

    @Override
    public final UpgradableItem getUpgradableItem() {
        return HastyPickaxeItem.hastyPickaxeItem();
    }

    @Override
    public final void store(ItemStack itemStack) {
        super.store(itemStack);
        final var tier = getTier();
        itemStack.editMeta(meta -> {
                tooltip(meta,
                        List.of(tier.getDisplayName(),
                                text(tiny("tier " + tier.getRomanTier().toLowerCase()), LIGHT_PURPLE),
                                textOfChildren(text(tiny("level "), GRAY), text(getLevel(), WHITE)),
                                textOfChildren(text(tiny("xp "), GRAY), text(getXp(), WHITE)),
                                empty(),
                                textOfChildren(Mytems.MOUSE_RIGHT, text(" Open menu", GRAY))));
            });
    }
}