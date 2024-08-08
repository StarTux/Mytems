package com.cavetale.mytems.item.spleef;

import com.cavetale.mytems.item.upgradable.UpgradableItem;
import com.cavetale.mytems.item.upgradable.UpgradableItemTag;
import com.cavetale.mytems.util.Items;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public abstract class SpleefShovelTag extends UpgradableItemTag {
    public static final class Copper extends SpleefShovelTag {
        @Override
        public SpleefShovelTier getUpgradableItemTier() {
            return SpleefShovelTier.COPPER;
        }
    }

    public static final class Iron extends SpleefShovelTag {
        @Override
        public SpleefShovelTier getUpgradableItemTier() {
            return SpleefShovelTier.IRON;
        }
    }

    public static final class Gold extends SpleefShovelTag {
        @Override
        public SpleefShovelTier getUpgradableItemTier() {
            return SpleefShovelTier.GOLD;
        }
    }

    public static final class Diamond extends SpleefShovelTag {
        @Override
        public SpleefShovelTier getUpgradableItemTier() {
            return SpleefShovelTier.DIAMOND;
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
    public final UpgradableItem getUpgradableItem() {
        return SpleefShovelItem.spleefShovelItem();
    }

    @Override
    public final List<Component> getTooltipDescription() {
        return List.of(text("Excavate a whole area", GRAY),
                       text("all at once at the", GRAY),
                       text("cost of a little", GRAY),
                       text("extra hunger", GRAY));
    }

    @Override
    public final int getRequiredXpFromLevel(int theCurrentLevel) {
        return 300 * (1 + theCurrentLevel);
    }

    public final int getRange() {
        return getEffectiveUpgradeLevel(SpleefShovelStat.RANGE);
    }
}
