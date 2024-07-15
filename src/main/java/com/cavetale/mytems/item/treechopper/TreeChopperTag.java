package com.cavetale.mytems.item.treechopper;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.upgradable.UpgradableItemMenu;
import com.cavetale.mytems.item.upgradable.UpgradableItemTag;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.tooltip;

@Data @EqualsAndHashCode(callSuper = true)
public abstract class TreeChopperTag extends UpgradableItemTag {
    // Legacy, season 5
    private Map<String, Integer> stats;

    public static final class Iron extends TreeChopperTag {
        @Override
        public TreeChopperTier getUpgradableItemTier() {
            return TreeChopperTier.IRON;
        }
    }

    public static final class Gold extends TreeChopperTag {
        @Override
        public TreeChopperTier getUpgradableItemTier() {
            return TreeChopperTier.GOLD;
        }
    }

    @Override
    public final boolean isEmpty() {
        return super.isEmpty() && stats == null;
    }

    @Override
    public final void load(ItemStack itemStack) {
        super.load(itemStack);
        if (getLevel() == 0 && getUpgrades() != null) {
            // Legacy items did not store the level
            int nextLevel = 0;
            for (TreeChopperStat stat : TreeChopperStat.values()) {
                nextLevel += getUpgradeLevel(stat);
            }
            setLevel(nextLevel);
        }
    }

    @Override
    public final void store(ItemStack itemStack) {
        if (stats != null) {
            legacyConversion();
        }
        super.store(itemStack);
        itemStack.editMeta(meta -> {
                tooltip(meta, getDefaultTooltip());
            });
    }

    private void legacyConversion() {
        // Legacy conversion
        int nextLevel = 0;
        for (TreeChopperStat stat : TreeChopperStat.values()) {
            final int level = stats.getOrDefault(stat.getKey(), 0);
            if (level <= 0) {
                continue;
            }
            setUpgradeLevel(stat, level);
            nextLevel += level;
        }
        setXp(stats.getOrDefault("xp", 0));
        setLevel(nextLevel);
        stats = null;
    }

    @Override
    public final TreeChopperItem getUpgradableItem() {
        return TreeChopperItem.treeChopperItem();
    }

    @Override
    public final void onMenuCreated(UpgradableItemMenu menu) {
        // 4,2
        menu.getGui().setItem(3, 2, Mytems.ARROW_LEFT.createIcon(), null);
        menu.getGui().setItem(5, 2, Mytems.ARROW_RIGHT.createIcon(), null);
        menu.getGui().setItem(4, 1, Mytems.ARROW_UP.createIcon(), null);
    }

    public static int getMaxLogBlocks(int level) {
        return 5 + 6 * (1 << level);
    }

    public static int getMaxLeafBlocks(int level) {
        return level > 0
            ? 100 * (1 << (level - 1))
            : 0;
    }

    public final int getMaxLogBlocks() {
        return getMaxLogBlocks(getUpgradeLevel(TreeChopperStat.CHOP));
    }

    public final int getMaxLeafBlocks() {
        return getMaxLeafBlocks(getUpgradeLevel(TreeChopperStat.LEAF));
    }

    public static int getChoppingSpeed(int speedLevel) {
        return speedLevel > 0
            ? speedLevel * 2
            : 1;
    }

    public final int getChoppingSpeed() {
        return getChoppingSpeed(getUpgradeLevel(TreeChopperStat.SPEED));
    }
}
