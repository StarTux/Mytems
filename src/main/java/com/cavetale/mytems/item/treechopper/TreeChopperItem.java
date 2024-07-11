package com.cavetale.mytems.item.treechopper;

import com.cavetale.mytems.item.upgradable.UpgradableItem;
import java.util.List;

public final class TreeChopperItem implements UpgradableItem {
    private static TreeChopperItem instance;

    public static TreeChopperItem treeChopperItem() {
        if (instance == null) {
            instance = new TreeChopperItem();
        }
        return instance;
    }

    @Override
    public List<TreeChopperTier> getTiers() {
        return List.of(TreeChopperTier.values());
    }

    @Override
    public List<TreeChopperStat> getStats() {
        return List.of(TreeChopperStat.values());
    }

    @Override
    public TreeChopperStat statForKey(String key) {
        return TreeChopperStat.ofKey(key);
    }

    @Override
    public int getMenuSize() {
        return 5 * 9;
    }
}
