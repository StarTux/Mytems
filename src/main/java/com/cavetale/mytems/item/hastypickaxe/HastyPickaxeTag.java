package com.cavetale.mytems.item.hastypickaxe;

import com.cavetale.mytems.item.upgradable.UpgradableItem;
import com.cavetale.mytems.item.upgradable.UpgradableItemTag;
import com.cavetale.mytems.util.Items;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

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
    public final void store(ItemStack itemStack) {
        super.store(itemStack);
        itemStack.editMeta(meta -> {
                Items.clearAttributes(meta);
                meta.setUnbreakable(true);
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
                meta.setEnchantmentGlintOverride(false);
            });
    }

    @Override
    public abstract HastyPickaxeTier getUpgradableItemTier();

    @Override
    public final UpgradableItem getUpgradableItem() {
        return HastyPickaxeItem.hastyPickaxeItem();
    }

    @Override
    public final boolean shouldAutoPlaceArrows() {
        return true;
    }

    @Override
    public final int getRequiredXpFromLevel(int theCurrentLevel) {
        return 150 * (1 + theCurrentLevel);
    }
}
