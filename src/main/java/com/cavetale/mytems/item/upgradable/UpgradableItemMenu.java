package com.cavetale.mytems.item.upgradable;

import com.cavetale.core.struct.Vec2i;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Gui;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.JoinConfiguration.separator;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

@Data
@RequiredArgsConstructor
public final class UpgradableItemMenu {
    private final Player player;
    private final ItemStack item;
    private final UpgradableItemTag tag;
    private final UpgradableItem upgradableItem;
    private Gui gui;

    public void open() {
        gui = new Gui()
            .size(upgradableItem.getMenuSize())
            .title(upgradableItem.getMenuTitle());
        final UpgradableItemTier itemTier = tag.getTier();
        for (UpgradableStat stat : upgradableItem.getStats()) {
            final UpgradableStatLevel level = stat.getLevel(tag.getUpgradeLevel(stat));
            final ItemStack icon;
            if (level == null && itemTier.getTier() < stat.getLevel(1).getRequiredTier()) {
                // Cannot have because it requires higher tier
                final UpgradableItemTier requiredTier = upgradableItem.getTier(stat.getLevel(1).getRequiredTier());
                final Mytems mytems = requiredTier.getMytems();
                icon = tooltip(Mytems.QUESTION_MARK.createIcon(),
                               List.of(textOfChildren(text("Requires ", DARK_GRAY), mytems.component, mytems.getMytem().getDisplayName())));
            } else if (level == null && tag.hasConflicts(stat)) {
                // Conflicts with unlocked stat
                final List<UpgradableStat> conflicts = tag.getUnlockedConflicts(stat);
                final List<Component> names = new ArrayList<>(conflicts.size());
                for (UpgradableStat conflict : conflicts) {
                    names.add(conflict.getTitle());
                }
                icon = tooltip(Mytems.NO.createIcon(),
                               List.of(text("Conflicts with ", DARK_GRAY),
                                       join(separator(text(", ", DARK_GRAY)), names)));
            } else if (level == null) {
                // Not unlocked but unlockable
                List<Component> tooltip = new ArrayList<>();
                tooltip.add(stat.getTitle());
                tooltip.addAll(stat.getLevel(1).getDescription());
                tooltip.add(textOfChildren(Mytems.MOUSE_RIGHT,
                                           text(" Unlock Upgrade")));
                icon = tooltip(stat.getIcon(), tooltip);
            } else {
                // Level != null
                List<Component> tooltip = new ArrayList<>();
                tooltip.add(stat.getTitle());
                tooltip.add(text("Level " + level.getLevel() + "/" + stat.getMaxLevel(), LIGHT_PURPLE, ITALIC));
                tooltip.addAll(level.getDescription());
                if (level.getLevel() < stat.getMaxLevel()) {
                    // Unlocked but higher level available
                    final UpgradableStatLevel nextLevel = stat.getLevel(level.getLevel() + 1);
                    if (itemTier.getTier() < nextLevel.getRequiredTier()) {
                        // Level requires higher tier
                        final UpgradableItemTier requiredTier = upgradableItem.getTier(stat.getLevel(nextLevel.getLevel()).getRequiredTier());
                        final Mytems mytems = requiredTier.getMytems();
                        tooltip.add(textOfChildren(text("Requires ", DARK_GRAY), mytems.component, mytems.getMytem().getDisplayName()));
                    } else {
                        // Level available
                        tooltip.add(text("Next Level", LIGHT_PURPLE, ITALIC));
                        tooltip.addAll(nextLevel.getDescription());
                        tooltip.add(empty());
                        tooltip.add(textOfChildren(Mytems.MOUSE_RIGHT,
                                                   text(" Unlock Level " + nextLevel.getLevel(), GRAY)));
                    }
                }
                icon = tooltip(stat.getIcon(), tooltip);
            }
            final Vec2i slot = stat.getGuiSlot();
            gui.setItem(slot.x, slot.z, icon, click -> {
                    if (click.isRightClick()) {
                        onClickUnlockStat(stat);
                        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, 0.5f, 1.0f);
                    } else if (click.isLeftClick()) {
                        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, 0.5f, 0.5f);
                    }
                });
        }
        gui.open(player);
    }

    private void onClickUnlockStat(UpgradableStat stat) {
        if (!tag.canUpgradeStat(stat).isSuccess()) return;
    }
}
