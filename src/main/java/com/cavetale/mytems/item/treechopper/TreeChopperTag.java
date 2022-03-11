package com.cavetale.mytems.item.treechopper;

import com.cavetale.mytems.MytemTag;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.worldmarker.util.Tags;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

@Data @EqualsAndHashCode(callSuper = true)
public final class TreeChopperTag extends MytemTag {
    protected Map<String, Integer> stats = new HashMap<>();

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && stats.isEmpty();
    }

    @Override
    public boolean isDismissable() {
        return isEmpty();
    }

    @Override
    public void load(ItemStack itemStack) {
        super.load(itemStack);
        PersistentDataContainer tag = itemStack.getItemMeta().getPersistentDataContainer();
        for (TreeChopperStat stat : TreeChopperStat.values()) {
            Integer value = Tags.getInt(tag, MytemsPlugin.namespacedKey(stat.key));
            if (value == null) continue;
            stats.put(stat.key, value);
        }
    }

    @Override
    public void store(ItemStack itemStack) {
        super.store(itemStack);
        itemStack.editMeta(meta -> {
                PersistentDataContainer tag = meta.getPersistentDataContainer();
                for (TreeChopperStat stat : TreeChopperStat.values()) {
                    Integer value = stats.get(stat.key);
                    if (value == null) continue;
                    Tags.set(tag, MytemsPlugin.namespacedKey(stat.key), value);
                }
            });
    }

    public int getStat(TreeChopperStat stat) {
        return Math.min(stat.maxLevel, Math.max(0, stats.getOrDefault(stat.key, 0)));
    }

    public void setStat(TreeChopperStat stat, int value) {
        stats.put(stat.key, Math.min(stat.maxLevel, Math.max(0, value)));
    }

    public static int getMaxLogBlocks(int level) {
        return 5 + 6 * (1 << level);
    }

    public static int getMaxLeafBlocks(int level) {
        return level > 0
            ? 100 * (1 << (level - 1))
            : 0;
    }

    public int getMaxLogBlocks() {
        return getMaxLogBlocks(getStat(TreeChopperStat.CHOP));
    }

    public int getMaxLeafBlocks() {
        return getMaxLeafBlocks(getStat(TreeChopperStat.LEAF));
    }

    public static int getChoppingSpeed(int speedLevel) {
        return speedLevel > 0
            ? speedLevel * 2
            : 1;
    }

    public int getChoppingSpeed() {
        return getChoppingSpeed(getStat(TreeChopperStat.SPEED));
    }

    public int getLevel() {
        int result = 0;
        for (TreeChopperStat stat : TreeChopperStat.values()) {
            if (stat.type != TreeChopperStat.Type.UPGRADE) continue;
            result += getStat(stat);
        }
        return result;
    }

    public int getUpgradeCost() {
        return 100 * (1 + getLevel());
    }

    public boolean upgradeIsPossible() {
        for (TreeChopperStat stat : TreeChopperStat.values()) {
            if (stat.type != TreeChopperStat.Type.UPGRADE) continue;
            if (getStat(stat) >= stat.maxLevel) continue;
            if (stat.conflictsWith(this)) continue;
            if (!stat.doesMeetRequirements(this)) continue;
            return true;
        }
        return false;
    }
}
