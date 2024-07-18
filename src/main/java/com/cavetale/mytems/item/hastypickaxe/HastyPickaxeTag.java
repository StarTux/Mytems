package com.cavetale.mytems.item.hastypickaxe;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.upgradable.UpgradableItem;
import com.cavetale.mytems.item.upgradable.UpgradableItemMenu;
import com.cavetale.mytems.item.upgradable.UpgradableItemTag;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.tooltip;

public abstract class HastyPickaxeTag extends UpgradableItemTag {
    public static final class Copper extends HastyPickaxeTag {
        @Override
        public HastyPickaxeTier getUpgradableItemTier() {
            return HastyPickaxeTier.COPPER;
        }
    }

    public static final class Gold extends HastyPickaxeTag {
        @Override
        public HastyPickaxeTier getUpgradableItemTier() {
            return HastyPickaxeTier.GOLD;
        }
    }

    public static final class Diamond extends HastyPickaxeTag {
        @Override
        public HastyPickaxeTier getUpgradableItemTier() {
            return HastyPickaxeTier.DIAMOND;
        }
    }

    public static final class Ruby extends HastyPickaxeTag {
        @Override
        public HastyPickaxeTier getUpgradableItemTier() {
            return HastyPickaxeTier.RUBY;
        }
    }

    @Override
    public abstract HastyPickaxeTier getUpgradableItemTier();

    @Override
    public final UpgradableItem getUpgradableItem() {
        return HastyPickaxeItem.hastyPickaxeItem();
    }

    @Override
    public final void store(ItemStack itemStack) {
        super.store(itemStack);
        itemStack.editMeta(meta -> {
                tooltip(meta, getDefaultTooltip());
            });
    }

    @Override
    public final void onMenuCreated(UpgradableItemMenu menu) {
        menu.getGui().setItem(2, 2, Mytems.ARROW_LEFT.createIcon(), null);
        menu.getGui().setItem(4, 2, Mytems.ARROW_RIGHT.createIcon(), null);
        menu.getGui().setItem(3, 1, Mytems.ARROW_UP.createIcon(), null);
        menu.getGui().setItem(3, 3, Mytems.ARROW_DOWN.createIcon(), null);
        menu.getGui().setItem(6, 2, Mytems.ARROW_RIGHT.createIcon(), null);
    }
}
