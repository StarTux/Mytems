package com.cavetale.mytems.item.upgradable;

import com.cavetale.mytems.MytemTag;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.session.Session;
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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import static com.cavetale.core.font.Unicode.subscript;
import static com.cavetale.core.font.Unicode.superscript;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.MytemsPlugin.namespacedKey;
import static com.cavetale.mytems.util.Items.tooltip;
import static com.cavetale.mytems.util.Text.roman;
import static net.kyori.adventure.text.Component.space;
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
            && level == 0
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
                tooltip(meta, getDefaultTooltip());
            });
    }

    /**
     * Check if the other tag also is an object of this class and has
     * equal members.
     */
    public boolean isSimilar(MytemTag other) {
        return super.isSimilar(other)
            && other instanceof UpgradableItemTag that
            && this.xp == that.xp
            && this.level == that.level
            && Objects.equals(this.upgrades, that.upgrades)
            && Objects.equals(this.disabled, that.disabled);
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
     * Count how many stats the player can unlock with the current
     * tier and configuration of unlocked upgrades.
     *
     * This is slightly inaccurate because mutually conflicting but
     * not yet unlocked stats will be counted twice, but for the
     * purpose of determining if more XP shall be earned, this will be
     * correct.
     */
    public final int countAvailableUpgradeLevels() {
        int result = 0;
        final UpgradableItemTier currentTier = getUpgradableItemTier();
        STATS:
        for (UpgradableStat stat : getUpgradableItem().getStats()) {
            final int hasLevel = getUpgradeLevel(stat);
            for (UpgradableStat conflict : stat.getConflicts()) {
                if (getUpgradeLevel(conflict) > 0) {
                    result += hasLevel;
                    continue STATS;
                }
            }
            for (UpgradableStatLevel statLevel : stat.getLevels()) {
                if (hasLevel >= statLevel.getLevel()) {
                    result += 1;
                    continue;
                }
                final UpgradableItemTier requiredTier = statLevel.getRequiredTier();
                if (requiredTier != null && requiredTier.getTier() > currentTier.getTier()) {
                    continue;
                }
                result += 1;
            }
        }
        return result;
    }

    public final void resetUpgrades() {
        upgrades = null;
        disabled = null;
    }

    /**
     * Get the required exp for the next level.
     */
    public final int getRequiredXp() {
        return getRequiredXpFromLevel(level);
    }

    /**
     * Get the required xp for any level.  Override this function to
     * change leveling.
     *
     * @param theCurrentLevel the current level
     * @return the required amount of xp to get from the current level
     *     to the next.
     */
    public int getRequiredXpFromLevel(int theCurrentLevel) {
        return 100 * (1 + theCurrentLevel);
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
     *
     * @param amount the xp amount to be added
     * @return true if xp were added, false otherwise.
     */
    public final boolean addXp(int amount) {
        if (countTotalUpgrades() < level) {
            return false;
        }
        if (countAvailableUpgradeLevels() <= level) {
            return false;
        }
        xp += amount;
        if (xp >= getRequiredXp()) {
            xp = 0;
            level += 1;
        }
        return true;
    }

    private static final class Favorite {
        long fullTime;
    }

    /**
     * Add xp and inform the user.  Calls addXp and returns its
     * result, with extra messaging.
     *
     * @param amount the xp amount to be added
     * @return true if xp were added, false otherwise.
     */
    public final boolean addXpAndNotify(Player player, int amount) {
        final boolean result = addXp(amount);
        if (hasAvailableUnlocks()) {
            final Favorite favorite = Session.of(player).getFavorites().getOrSet(Favorite.class, Favorite::new);
            final long fullTime = player.getWorld().getFullTime();
            if (favorite.fullTime != fullTime) {
                favorite.fullTime = fullTime;
                final Component message = textOfChildren(text("Your ", GREEN),
                                                         getUpgradableItemTier().getMytems(),
                                                         text(" has leveled up.", GREEN))
                    .color(getUpgradableItemTier().getMenuColor());
                player.sendMessage(message);
                player.sendMessage(textOfChildren(Mytems.MOUSE_CURSOR, Mytems.MOUSE_RIGHT,
                                                  text(" Right click it in your inventory to choose a perk.", GREEN))
                                   .color(getUpgradableItemTier().getMenuColor()));
                player.sendActionBar(message);
                return result;
            }
        }
        if (result) {
            Component message = textOfChildren(getUpgradableItemTier().getMytems(),
                                               text(level),
                                               space(),
                                               text(superscript(xp)),
                                               text("/"),
                                               text(subscript(getRequiredXp())))
                .color(getUpgradableItemTier().getMenuColor());
            player.sendActionBar(message);
        }
        return result;
    }

    public final int getTotalXp() {
        int result = xp;
        for (int i = 0; i < level; i += 1) {
            result += getRequiredXpFromLevel(i);
        }
        return result;
    }

    public final void setTotalXp(final int value) {
        int remainder = value;
        level = 0;
        while (remainder >= getRequiredXp()) {
            remainder -= getRequiredXp();
            level += 1;
        }
        xp = remainder;
    }

    public final List<Component> getDefaultTooltip() {
        final List<Component> tooltip = new ArrayList<>();
        tooltip.add(getUpgradableItemTier().getMytems().getMytem().getDisplayName());
        tooltip.add(text(tiny("tier " + getUpgradableItemTier().getRomanTier().toLowerCase()), getUpgradableItemTier().getMenuColor()));
        tooltip.addAll(getTooltipDescription());
        for (UpgradableStat stat : getUpgradableItem().getStats()) {
            final int upgradeLevel = getEffectiveUpgradeLevel(stat);
            if (upgradeLevel < 1) continue;
            if (stat.getLevels().size() > 1) {
                tooltip.add(textOfChildren(stat.getChatIcon(), stat.getTitle(), text(" " + roman(upgradeLevel))).color(GRAY));
            } else {
                tooltip.add(textOfChildren(stat.getChatIcon(), stat.getTitle()).color(GRAY));
            }
        }
        tooltip.add(textOfChildren(text(tiny("lv "), GRAY), text(getLevel(), WHITE)));
        tooltip.add(textOfChildren(text(tiny("xp "), GRAY), text(superscript(getXp()), WHITE), text("/", GRAY), text(subscript(getRequiredXp()), WHITE)));
        tooltip.add(textOfChildren(Mytems.MOUSE_CURSOR, Mytems.MOUSE_RIGHT, text(" Open menu", GRAY)));
        return tooltip;
    }

    /**
     * Override this to edit the menu.
     */
    public void onMenuCreated(UpgradableItemMenu menu) { }

    /**
     * Override to add text to the tooltip.
     */
    public List<Component> getTooltipDescription() {
        return List.of();
    }

    /**
     * Should the menu have automatic arrows between dependent stats
     * that have one gap inbetween?
     * See UpgradableItemMenu#placeArrow.
     */
    public boolean shouldAutoPlaceArrows() {
        return true;
    }
}
