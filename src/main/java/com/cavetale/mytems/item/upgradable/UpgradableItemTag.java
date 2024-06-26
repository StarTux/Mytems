package com.cavetale.mytems.item.upgradable;

import com.cavetale.mytems.MytemTag;
import com.cavetale.mytems.Mytems;
import com.cavetale.worldmarker.util.Tags;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import static com.cavetale.core.font.Unicode.subscript;
import static com.cavetale.core.font.Unicode.superscript;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.MytemsPlugin.namespacedKey;
import static com.cavetale.mytems.util.Text.roman;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class UpgradableItemTag extends MytemTag {
    private static final String XP = "xp";
    private static final String LEVEL = "level";
    private static final String DISABLED = "disabled";
    private int xp;
    private int level;
    private Map<String, Integer> upgrades;
    private Set<String> disabled;

    /**
     * Check if all stats are empty and there are no xp, after
     * checking the superclass method.
     */
    @Override
    public boolean isEmpty() {
        return super.isEmpty()
            && xp == 0
            && (upgrades == null || upgrades.isEmpty())
            && (disabled == null || disabled.isEmpty());
    }

    /**
     * Get the upgradable stat set associated with this item.
     */
    public abstract UpgradableItem getUpgradableItem();

    /**
     * Get the tier of this tag, starting at 1.
     */
    public abstract UpgradableItemTier getUpgradableItemTier();

    /**
     * Load the xp and all stats from the item.
     */
    @Override
    public void load(ItemStack itemStack) {
        super.load(itemStack);
        if (!itemStack.hasItemMeta()) {
            return;
        }
        final var pdc = itemStack.getItemMeta().getPersistentDataContainer();
        this.xp = Tags.getInt(pdc, namespacedKey(XP), 0);
        this.level = Tags.getInt(pdc, namespacedKey(LEVEL), 0);
        this.upgrades = new HashMap<>();
        for (var stat : getUpgradableItem().getStats()) {
            final int upgradeLevel = Tags.getInt(pdc, stat.getNamespacedKey(), 0);
            if (upgradeLevel == 0) continue;
            upgrades.put(stat.getKey(), upgradeLevel);
        }
        final var disabledKey = namespacedKey(DISABLED);
        if (pdc.has(disabledKey, PersistentDataType.LIST.strings())) {
            for (String key : pdc.get(disabledKey, PersistentDataType.LIST.strings())) {
                if (getUpgradableItem().statForKey(key) == null) continue;
                if (disabled == null) {
                    disabled = new HashSet<>();
                }
                disabled.add(key);
            }
        }
    }

    /**
     * Store the exp and stats.
     */
    @Override
    public void store(ItemStack itemStack) {
        super.store(itemStack);
        itemStack.editMeta(meta -> {
                final var pdc = meta.getPersistentDataContainer();
                Tags.set(pdc, namespacedKey(XP), xp);
                Tags.set(pdc, namespacedKey(LEVEL), level);
                for (UpgradableStat stat : getUpgradableItem().getStats()) {
                    final int upgradeLevel = upgrades != null
                        ? upgrades.getOrDefault(stat.getKey(), 0)
                        : 0;
                    if (upgradeLevel == 0) {
                        pdc.remove(stat.getNamespacedKey());
                        stat.removeFromItem(meta);
                    } else {
                        Tags.set(pdc, stat.getNamespacedKey(), upgradeLevel);
                        stat.applyToItem(meta, upgradeLevel);
                    }
                }
                if (disabled != null && !disabled.isEmpty()) {
                    pdc.set(namespacedKey(DISABLED), PersistentDataType.LIST.strings(), List.copyOf(disabled));
                } else {
                    pdc.remove(namespacedKey(DISABLED));
                }
            });
    }

    /**
     * Check if the other tag also is an object of this class and has
     * equal members.
     */
    public boolean isSimilar(MytemTag other) {
        if (!(other instanceof UpgradableItemTag tag)) return false;
        return super.isSimilar(other)
            && this.xp == tag.xp
            && Objects.equals(this.upgrades, tag.upgrades);
    }

    public final int getUpgradeLevel(UpgradableStat stat) {
        if (upgrades == null) return 0;
        return upgrades.getOrDefault(stat.getKey(), 0);
    }

    /**
     * Get the upgrade level, provided the stat is not disabled.
     *
     * @param stat the stat
     * @return the upgrade level if the stat is not disabled,
     *     otherwise always 0.
     */
    public final int getEffectiveUpgradeLevel(UpgradableStat stat) {
        return !isStatDisabled(stat)
            ? getUpgradeLevel(stat)
            : 0;
    }

    public final void setUpgradeLevel(UpgradableStat stat, int upgradeLevel) {
        if (upgradeLevel <= 0) {
            throw new IllegalArgumentException("stat=" + stat + " level=" + upgradeLevel);
        }
        upgrades = upgrades == null
            ? new HashMap<>()
            : new HashMap<>(upgrades);
        upgrades.put(stat.getKey(), upgradeLevel);
    }

    public final boolean isStatDisabled(UpgradableStat stat) {
        return disabled != null && disabled.contains(stat.getKey());
    }

    public final void setStatDisabled(UpgradableStat stat, boolean value) {
        if (value) {
            if (disabled == null) {
                disabled = new HashSet<>();
            }
            disabled.add(stat.getKey());
        } else {
            if (disabled == null) {
                return;
            }
            disabled.remove(stat.getKey());
            if (disabled.isEmpty()) {
                disabled = null;
            }
        }
    }

    public final boolean hasUnlockedConflictsWith(UpgradableStat stat) {
        for (UpgradableStat conflict : stat.getConflicts()) {
            if (getUpgradeLevel(conflict) > 0) return true;
        }
        return false;
    }

    public final List<UpgradableStat> getUnlockedConflictsWith(UpgradableStat stat) {
        final List<? extends UpgradableStat> conflicts = stat.getConflicts();
        if (conflicts.isEmpty()) return List.of();
        final List<UpgradableStat> result = new ArrayList<>(conflicts.size());
        for (UpgradableStat conflict : conflicts) {
            if (getUpgradeLevel(conflict) > 0) {
                result.add(conflict);
            }
        }
        return result;
    }

    public final List<UpgradableStat> getMissingDependenciesFor(UpgradableStat stat) {
        final List<UpgradableStat> result = new ArrayList<>();
        for (UpgradableStat dependency : stat.getDependencies()) {
            if (getUpgradeLevel(dependency) == 0) {
                result.add(dependency);
            }
        }
        return result;
    }

    public final List<UpgradableStat> getMissingCompleteDependenciesFor(UpgradableStat stat) {
        final List<UpgradableStat> result = new ArrayList<>();
        for (UpgradableStat completeDependency : stat.getCompleteDependencies()) {
            if (getUpgradeLevel(completeDependency) < completeDependency.getMaxLevel().getLevel()) {
                result.add(completeDependency);
            }
        }
        return result;
    }

    public final int countTotalUpgrades() {
        if (upgrades == null) return 0;
        int result = 0;
        for (var theLevel : upgrades.values()) {
            result += theLevel;
        }
        return result;
    }

    /**
     * Get the required exp for the next level.
     */
    public int getRequiredXp() {
        return 100 * (1 + getLevel());
    }

    public final boolean hasAvailableUnlocks() {
        if (countTotalUpgrades() >= level) {
            return false;
        }
        for (UpgradableStat stat : getUpgradableItem().getStats()) {
            if (new UpgradableStatStatus(this, stat).isUpgradable()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Try to add some xp which may or may not cause a level up.
     * @param amount the xp amount to be added
     * @return true if xp were added, false otherwise.
     */
    public final boolean addXp(int amount) {
        if (countTotalUpgrades() < level) {
            return false;
        }
        xp += amount;
        if (xp >= getRequiredXp()) {
            xp = 0;
            level += 1;
        }
        return true;
    }

    public final List<Component> getDefaultTooltip() {
        final List<Component> tooltip = new ArrayList<>();
        final UpgradableItemTier tier = getUpgradableItemTier();
        tooltip.add(tier.getMytems().getMytem().getDisplayName());
        tooltip.add(text(tiny("tier " + tier.getRomanTier().toLowerCase()), LIGHT_PURPLE));
        for (UpgradableStat stat : getUpgradableItem().getStats()) {
            final int upgradeLevel = getEffectiveUpgradeLevel(stat);
            if (upgradeLevel < 1) continue;
            if (stat.getLevels().size() > 1) {
                tooltip.add(textOfChildren(stat.getChatIcon(), stat.getTitle(), text(" " + roman(upgradeLevel))).color(GRAY));
            } else {
                tooltip.add(textOfChildren(stat.getChatIcon(), stat.getTitle()));
            }
        }
        tooltip.add(textOfChildren(text(tiny("level "), GRAY), text(getLevel(), WHITE)));
        tooltip.add(textOfChildren(text(tiny("xp "), GRAY), text(superscript(getXp()), WHITE), text("/", GRAY), text(subscript(getRequiredXp()), WHITE)));
        tooltip.add(textOfChildren(Mytems.MOUSE_CURSOR, Mytems.MOUSE_RIGHT, text(" Open menu", GRAY)));
        return tooltip;
    }
}
