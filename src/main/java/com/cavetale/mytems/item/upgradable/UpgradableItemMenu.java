package com.cavetale.mytems.item.upgradable;

import com.cavetale.core.font.GuiOverlay;
import com.cavetale.core.item.ItemKinds;
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
import static com.cavetale.mytems.util.Text.ITEM_LORE_WIDTH;
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

    private static final Component DIVIDER = text(" ".repeat(ITEM_LORE_WIDTH), color(0x28055E), STRIKETHROUGH);

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
            final UpgradableStatStatus status = new UpgradableStatStatus(tag, stat);
            final ItemStack icon = status.hasCurrentLevel()
                ? status.getCurrentLevel().getIcon()
                : stat.getIcon();
            final List<Component> tooltip = new ArrayList<>();
            tooltip.add(textOfChildren(ItemKinds.icon(stat.getIcon()), stat.getTitle()));
            if (status.hasCurrentLevel()) {
                tooltip.add(DIVIDER);
                tooltip.add(textOfChildren(Mytems.CHECKED_CHECKBOX,
                                           text(tiny(" level ") + status.getCurrentLevel().getLevel() + "/" + stat.getMaxLevel().getLevel(), LIGHT_PURPLE, ITALIC)));
                for (Component line : status.getCurrentLevel().getDescription()) {
                    tooltip.add(textOfChildren(text("  "), line));
                }
                tooltip.add(DIVIDER);
            }
            if (status.hasNextLevel() && !status.isPermanentlyLocked()) {
                if (!status.hasCurrentLevel()) {
                    tooltip.add(DIVIDER);
                }
                tooltip.add(textOfChildren((status.isUpgradable() ? Mytems.ARROW_RIGHT : Mytems.CROSSED_CHECKBOX),
                                           text(tiny("level ") + status.getNextLevel().getLevel() + "/" + stat.getMaxLevel().getLevel(), LIGHT_PURPLE, ITALIC)));
                for (Component line : status.getNextLevel().getDescription()) {
                    tooltip.add(textOfChildren(text("  "), line));
                }
                if (status.isUpgradable()) {
                    if (status.getCurrentLevel() == null) {
                        for (UpgradableStat conflict : stat.getConflicts()) {
                            tooltip.add(text(tiny("conflicts with upgrade"), DARK_GRAY));
                            tooltip.add(textOfChildren(text("  "),
                                                       ItemKinds.icon(conflict.getIcon()),
                                                       conflict.getTitle())
                                        .color(DARK_GRAY));
                        }
                    }
                    if (status.getCurrentLevel() == null) {
                        tooltip.add(textOfChildren(Mytems.MOUSE_RIGHT, text(tiny(" to unlock"), GREEN)));
                    } else {
                        tooltip.add(textOfChildren(Mytems.MOUSE_RIGHT, text(tiny(" to upgrade"), GREEN)));
                    }
                }
                if (status.isTierTooLow()) {
                    tooltip.add(textOfChildren(textOfChildren(Mytems.CROSSED_CHECKBOX,
                                                              text(tiny("requires tier " + status.getRequiredItemTier().getRomanTier()), DARK_RED))));
                    tooltip.add(textOfChildren(text("  "), status.getRequiredItemTier().getMytems().getMytem().getDisplayName())
                                .color(DARK_RED));
                }
                if (status.hasMissingDependencies()) {
                    for (UpgradableStat dependency : status.getMissingDependencies()) {
                        tooltip.add(textOfChildren(Mytems.CROSSED_CHECKBOX, text(tiny("requires upgrade"), DARK_RED)));
                        tooltip.add(textOfChildren(text("  "),
                                                   ItemKinds.icon(dependency.getIcon()),
                                                   dependency.getTitle())
                                    .color(DARK_RED));
                    }
                    for (UpgradableStat completeDependency : status.getMissingCompleteDependencies()) {
                        tooltip.add(textOfChildren(Mytems.CROSSED_CHECKBOX, text(tiny("requires upgrade"), DARK_RED)));
                        tooltip.add(textOfChildren(text("  "),
                                                   ItemKinds.icon(completeDependency.getIcon()),
                                                   completeDependency.getTitle(),
                                                   text(roman(completeDependency.getMaxLevel().getLevel())))
                                    .color(DARK_RED));
                    }
                }
                if (status.isItemLevelTooLow()) {
                    tooltip.add(textOfChildren(Mytems.CROSSED_CHECKBOX, text(tiny("requires more xp"), DARK_RED)));
                }
                if (status.hasStatConflict()) {
                    for (UpgradableStat conflict : status.getConflictingStats()) {
                        tooltip.add(textOfChildren(Mytems.CROSSED_CHECKBOX, text(tiny("conflicts with upgrade"), DARK_RED)));
                        tooltip.add(textOfChildren(text("  "),
                                                   ItemKinds.icon(conflict.getIcon()),
                                                   textOfChildren(conflict.getTitle()))
                                    .color(DARK_RED));
                    }
                }
            }
            final TextColor highlightColor;
            if (status.isUpgradable()) {
                highlightColor = BLUE;
            } else if (status.getCurrentLevel() == null) {
                if (status.isPermanentlyLocked()) {
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
