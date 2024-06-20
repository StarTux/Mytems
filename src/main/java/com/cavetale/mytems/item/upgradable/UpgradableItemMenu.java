package com.cavetale.mytems.item.upgradable;

import com.cavetale.core.font.GuiOverlay;
import com.cavetale.core.struct.Vec2i;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Gui;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.subscript;
import static com.cavetale.core.font.Unicode.superscript;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Items.tooltip;
import static com.cavetale.mytems.util.Text.roman;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;
import static net.kyori.adventure.text.format.TextDecoration.*;

@Data
@RequiredArgsConstructor
public final class UpgradableItemMenu {
    private final Player player;
    private final ItemStack itemStack;
    private final UpgradableItemTag tag;
    private final UpgradableItem upgradableItem;
    private Gui gui;

    public void open() {
        final UpgradableItemTier itemTier = tag.getUpgradableItemTier();
        gui = new Gui()
            .size(upgradableItem.getMenuSize())
            .title(textOfChildren(itemTier.getMytems(),
                                  space(),
                                  text(tiny("level"), GRAY),
                                  text(tag.getLevel(), WHITE, BOLD),
                                  space(),
                                  text(tiny("xp"), GRAY),
                                  text(superscript(tag.getXp()), WHITE),
                                  text("/", GRAY),
                                  text(subscript(tag.getRequiredXp()), WHITE)));
        gui.layer(GuiOverlay.BLANK, itemTier.getMenuColor());
        gui.layer(GuiOverlay.TITLE_BAR, itemTier.getMenuColor());
        for (UpgradableStat stat : upgradableItem.getStats()) {
            final UpgradableStatStatus status = tag.getUpgradeStatus(stat);
            final ItemStack icon = status.hasCurrentLevel()
                ? status.getCurrentLevel().getIcon()
                : stat.getIcon();
            final List<Component> tooltip = new ArrayList<>();
            tooltip.add(stat.getTitle());
            if (status.hasCurrentLevel()) {
                // Display the current level
                tooltip.add(textOfChildren(Mytems.CHECKED_CHECKBOX,
                                           text(tiny(" level ") + status.getCurrentLevel().getLevel() + "/" + stat.getMaxLevel().getLevel(), LIGHT_PURPLE, ITALIC)));
                tooltip.addAll(status.getCurrentLevel().getDescription());
            }
            if (status.hasNextLevel() && (status.isUpgradable() || !status.getType().isPermanentlyLocked())) {
                if (status.hasCurrentLevel()) {
                    // Divider line
                    tooltip.add(textOfChildren(text("  "), text("                        ", LIGHT_PURPLE, STRIKETHROUGH), text("  ")));
                }
                // Display the next level
                tooltip.add(textOfChildren((status.isUpgradable() ? Mytems.ARROW_RIGHT : Mytems.CROSSED_CHECKBOX),
                                           text(tiny(" level ") + status.getNextLevel().getLevel() + "/" + stat.getMaxLevel().getLevel(), LIGHT_PURPLE, ITALIC)));
                tooltip.addAll(status.getNextLevel().getDescription());
            }
            final TextColor highlightColor;
            if (status.isUpgradable()) {
                highlightColor = BLUE;
            } else if (status.getCurrentLevel() == null) {
                if (status.getType().isPermanentlyLocked()) {
                    highlightColor = color(0x600000);
                } else {
                    highlightColor = null;
                }
            } else if (status.getCurrentLevel() != null) {
                final float percentage = (float) status.getCurrentLevel().getLevel() / (float) stat.getMaxLevel().getLevel();
                final float value = (0.5f + percentage) / 1.5f;
                highlightColor = color(value, value, value);
            } else {
                final float value = 0.5f;
                highlightColor = color(value, value, value);
            }
            if (status instanceof UpgradableStatStatus.Upgradable) {
                if (status.getCurrentLevel() == null) {
                    tooltip.add(textOfChildren(Mytems.MOUSE_RIGHT, text(tiny(" to unlock"), GREEN)));
                } else {
                    tooltip.add(textOfChildren(Mytems.MOUSE_RIGHT, text(tiny(" to upgrade"), GREEN)));
                }
                if (status.getCurrentLevel() == null) {
                    for (UpgradableStat conflict : stat.getConflicts()) {
                        tooltip.add(textOfChildren(text(tiny("conflicts with ")), conflict.getTitle())
                                    .color(GRAY));
                    }
                }
            } else if (status instanceof UpgradableStatStatus.TierTooLow tierTooLow) {
                tooltip.add(textOfChildren(text(tiny("requires item tier ") + tierTooLow.getRequiredTier().getRomanTier(), DARK_RED)));
                tooltip.add(textOfChildren(text("  "), tierTooLow.getRequiredTier().getMytems().getMytem().getDisplayName())
                            .color(DARK_RED));
            } else if (status instanceof UpgradableStatStatus.UnmetDependencies unmetDependencies) {
                for (UpgradableStat dependency : unmetDependencies.getMissingDependencies()) {
                    tooltip.add(textOfChildren(text(tiny("requires ")), dependency.getTitle())
                                .color(DARK_RED));
                }
                for (UpgradableStat completeDependency : unmetDependencies.getMissingCompleteDependencies()) {
                    tooltip.add(textOfChildren(text(tiny("requires ")),
                                               completeDependency.getTitle(),
                                               text(" " + roman(completeDependency.getMaxLevel().getLevel())))
                                .color(DARK_RED));
                }
            } else if (status instanceof UpgradableStatStatus.ItemLevelTooLow itemLevelTooLow) {
                tooltip.add(text(tiny("you must earn enough"), DARK_RED));
                tooltip.add(text(tiny("xp for level ") + itemLevelTooLow.getRequiredItemLevel(), DARK_RED));
            } else if (status instanceof UpgradableStatStatus.StatConflict statConflict) {
                for (UpgradableStat conflict : statConflict.getConflictingStats()) {
                    tooltip.add(textOfChildren(text(tiny("conflicts with ")), conflict.getTitle())
                                .color(DARK_RED));
                }
            }
            final Vec2i slot = stat.getGuiSlot();
            gui.setItem(slot.x, slot.z, tooltip(icon, tooltip), click -> {
                    if (click.isRightClick()) {
                        onClickUnlockStat(stat, status);
                    } else if (click.isLeftClick()) {
                        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, 0.5f, 0.5f);
                    }
                });
            if (highlightColor != null) {
                gui.highlight(slot.x, slot.z, highlightColor);
            }
        }
        gui.open(player);
    }

    private void onClickUnlockStat(UpgradableStat stat, UpgradableStatStatus status) {
        if (!status.isUpgradable()) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, 0.5f, 5.0f);
            return;
        }
        tag.setUpgradeLevel(stat, tag.getUpgradeLevel(stat) + 1);
        tag.store(itemStack);
        open();
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 0.5f, 1.0f);
    }
}
