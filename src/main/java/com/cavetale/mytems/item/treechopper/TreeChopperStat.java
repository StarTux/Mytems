package com.cavetale.mytems.item.treechopper;

import com.cavetale.mytems.Mytems;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum TreeChopperStat {
    XP(Type.BASE, Integer.MAX_VALUE, "Experience", () -> null),
    CHOP(Type.UPGRADE, 6, "Chopping", () -> new ItemStack(Material.OAK_LOG)),
    LEAF(Type.UPGRADE, 4, "Leaf Blower", () -> new ItemStack(Material.OAK_LEAVES)),
    FORTUNE(Type.UPGRADE, 3, "Fortune", () -> Mytems.DICE.createIcon()),
    SILK(Type.UPGRADE, 2, "Shears", () -> new ItemStack(Material.SHEARS)),
    REPLANT(Type.UPGRADE, 1, "Replant", () -> new ItemStack(Material.OAK_SAPLING)),
    PICKUP(Type.UPGRADE, 1, "Magnet", () -> new ItemStack(Material.HOPPER_MINECART)),
    SPEED(Type.UPGRADE, 5, "Speed", () -> new ItemStack(Material.SUGAR)),
    PUNCH(Type.UPGRADE, 1, "Punching", () -> new ItemStack(Material.GUNPOWDER)),
    ENCH(Type.UPGRADE, 10, "Enchanter", () -> new ItemStack(Material.EXPERIENCE_BOTTLE));

    public enum Type {
        BASE,
        UPGRADE;
    }

    public final TreeChopperStat.Type type;
    public final String key;
    public final int maxLevel;
    public final String displayName;
    public final Supplier<ItemStack> icon;
    protected Set<TreeChopperStat> conflicts;
    protected Map<TreeChopperStat, Integer> requirements;

    TreeChopperStat(final Type type, final int maxLevel, final String displayName, final Supplier<ItemStack> icon) {
        this.type = type;
        this.key = name().toLowerCase();
        this.maxLevel = maxLevel;
        this.displayName = displayName;
        this.icon = icon;
    }

    static {
        for (TreeChopperStat stat : TreeChopperStat.values()) {
            stat.conflicts = EnumSet.noneOf(TreeChopperStat.class);
            stat.requirements = new EnumMap<>(TreeChopperStat.class);
        }
        addConflict(SILK, FORTUNE);
        addConflict(SILK, REPLANT);
        SILK.requirements.put(LEAF, 1);
        FORTUNE.requirements.put(LEAF, 1);
        REPLANT.requirements.put(LEAF, 1);
        SPEED.requirements.put(CHOP, 1);
        PUNCH.requirements.put(SPEED, 2);
    }

    private static void addConflict(TreeChopperStat a, TreeChopperStat b) {
        a.conflicts.add(b);
        b.conflicts.add(a);
    }

    public static TreeChopperStat ofKey(String key) {
        for (TreeChopperStat upgrade : TreeChopperStat.values()) {
            if (key.equals(upgrade.key)) return upgrade;
        }
        return null;
    }

    public boolean conflictsWith(TreeChopperStat other) {
        return conflicts.contains(other);
    }

    public boolean conflictsWith(TreeChopperTag tag) {
        for (TreeChopperStat other : TreeChopperStat.values()) {
            if (this == other) continue;
            if (conflictsWith(other) && tag.getStat(other) > 0) return true;
        }
        return false;
    }

    public boolean doesMeetRequirements(TreeChopperTag tag) {
        if (requirements.isEmpty()) return true;
        for (Map.Entry<TreeChopperStat, Integer> requirement : requirements.entrySet()) {
            for (TreeChopperStat other : TreeChopperStat.values()) {
                if (tag.getStat(requirement.getKey()) < requirement.getValue()) {
                    return false;
                }
            }
        }
        return true;
    }
}
