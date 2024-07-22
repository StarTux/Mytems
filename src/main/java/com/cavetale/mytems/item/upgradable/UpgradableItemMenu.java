package com.cavetale.mytems.item.upgradable;

import com.cavetale.core.font.GuiOverlay;
import com.cavetale.core.struct.Vec2i;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Gui;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.subscript;
import static com.cavetale.core.font.Unicode.superscript;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.MytemsPlugin.plugin;
import static com.cavetale.mytems.util.Items.tooltip;
import static com.cavetale.mytems.util.Text.ITEM_LORE_WIDTH;
import static com.cavetale.mytems.util.Text.roman;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;
import static net.kyori.adventure.text.format.TextColor.lerp;
import static net.kyori.adventure.text.format.TextDecoration.BOLD;
import static net.kyori.adventure.text.format.TextDecoration.STRIKETHROUGH;

@Data
@RequiredArgsConstructor
public final class UpgradableItemMenu {
    private final Player player;
    private final ItemStack itemStack;
    private final UpgradableItemTag tag;
    private Gui gui;

    private static final Component DIVIDER = text(" ".repeat(ITEM_LORE_WIDTH + 2), color(0x28055E), STRIKETHROUGH);

    public void open() {
        final HashSet<Vec2i> usedSlots = new HashSet<>();
        final UpgradableItemTier itemTier = tag.getUpgradableItemTier();
        final TextColor menuColor = itemTier.getMenuColor();
        final UpgradableItem upgradableItem = tag.getUpgradableItem();
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
        gui.layer(GuiOverlay.BLANK, menuColor);
        gui.layer(GuiOverlay.TITLE_BAR, menuColor);
        for (UpgradableStat stat : upgradableItem.getStats()) {
            final UpgradableStatStatus status = new UpgradableStatStatus(tag, stat);
            final ItemStack icon;
            if (status.isDisabled()) {
                icon = Mytems.NO.createIcon();
            } else if (status.hasCurrentLevel()) {
                icon = status.getCurrentLevel().getIcon();
            } else if (status.isLocked()) {
                icon = Mytems.SILVER_KEYHOLE.createIcon();
            } else {
                icon = stat.getIcon();
            }
            final List<Component> tooltip = new ArrayList<>();
            tooltip.add(textOfChildren(stat.getChatIcon(), stat.getTitle()));
            if (status.hasCurrentLevel()) {
                tooltip.add(DIVIDER);
                tooltip.add(textOfChildren(Mytems.CHECKED_CHECKBOX,
                                           text(tiny("level ") + status.getCurrentLevel().getLevel() + "/" + stat.getMaxLevel().getLevel(), LIGHT_PURPLE)));
                for (Component line : status.getCurrentLevel().getDescription()) {
                    tooltip.add(textOfChildren(text("  "), line));
                }
            }
            if (status.hasNextLevel()) {
                tooltip.add(DIVIDER);
                tooltip.add(textOfChildren((status.isUpgradable() ? Mytems.ARROW_RIGHT : Mytems.CROSSED_CHECKBOX),
                                           text(tiny("level ") + status.getNextLevel().getLevel() + "/" + stat.getMaxLevel().getLevel(), LIGHT_PURPLE)));
                for (Component line : status.getNextLevel().getDescription()) {
                    tooltip.add(textOfChildren(text("  "), line));
                }
                if (status.isUpgradable()) {
                    if (status.getCurrentLevel() == null) {
                        for (UpgradableStat conflict : stat.getConflicts()) {
                            tooltip.add(text(tiny("conflicts with upgrade"), DARK_GRAY));
                            tooltip.add(textOfChildren(text("  "),
                                                       conflict.getChatIcon(),
                                                       conflict.getTitle())
                                        .color(DARK_GRAY));
                        }
                    }
                    if (status.getCurrentLevel() == null) {
                        tooltip.add(textOfChildren(Mytems.MOUSE_RIGHT, text(tiny(" unlock"), GREEN)));
                    } else {
                        tooltip.add(textOfChildren(Mytems.MOUSE_RIGHT, text(tiny(" upgrade"), GREEN)));
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
                                                   dependency.getChatIcon(),
                                                   dependency.getTitle())
                                    .color(DARK_RED));
                    }
                    for (UpgradableStat completeDependency : status.getMissingCompleteDependencies()) {
                        tooltip.add(textOfChildren(Mytems.CROSSED_CHECKBOX, text(tiny("requires upgrade"), DARK_RED)));
                        tooltip.add(textOfChildren(text("  "),
                                                   completeDependency.getChatIcon(),
                                                   completeDependency.getTitle(),
                                                   space(),
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
                                                   conflict.getChatIcon(),
                                                   textOfChildren(conflict.getTitle()))
                                    .color(DARK_RED));
                    }
                }
            }
            if (status.hasCurrentLevel()) {
                if (status.isDisabled()) {
                    tooltip.add(textOfChildren(Mytems.NO, text(tiny("disabled"), DARK_RED)));
                    tooltip.add(textOfChildren(text("DROP", GREEN), text(tiny(" to enable"), GRAY)));
                } else if (status.hasCurrentLevel()) {
                    tooltip.add(textOfChildren(text("DROP", GREEN), text(tiny(" to disable"), GRAY)));
                }
            }
            final TextColor highlightColor;
            if (status.isDisabled()) {
                // Disabled, show X
                highlightColor = null;
            } else if (status.isLocked()) {
                // Locked, show keyhole
                highlightColor = null;
            } else if (status.isUpgradable()) {
                highlightColor = BLUE;
            } else if (!status.hasCurrentLevel()) {
                if (status.isPermanentlyLocked()) {
                    highlightColor = color(0x202020);
                } else {
                    highlightColor = null;
                }
            } else if (status.getCurrentLevel() != null) {
                final float percentage = (float) status.getCurrentLevel().getLevel() / (float) stat.getMaxLevel().getLevel();
                final float value = (0.35f + percentage) / 1.35f;
                highlightColor = lerp(value, BLACK, menuColor);
            } else {
                highlightColor = lerp(0.35f, BLACK, menuColor);
            }
            final Vec2i slot = stat.getGuiSlot();
            if (usedSlots.contains(slot)) {
                plugin().getLogger().warning("[UpgradableItemMenu] Slot overlap: " + upgradableItem.getClass().getName() + " " + slot);
            }
            usedSlots.add(slot);
            gui.setItem(slot.x, slot.z, tooltip(icon, tooltip), click -> {
                    if (click.isRightClick()) {
                        onClickUnlockStat(stat, status);
                    } else if (click.isLeftClick()) {
                        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, 0.5f, 0.5f);
                    } else if (click.getClick() == ClickType.DROP) {
                        onDrop(stat, status);
                    }
                });
            if (highlightColor != null) {
                gui.highlight(slot.x, slot.z, highlightColor);
            }
        }
        if (tag.countTotalUpgrades() > 0) {
            final List<Component> resetTooltip = List.of(text("Reset Upgrades", DARK_RED),
                                                         text("Reset all upgrades, so", GRAY),
                                                         text("you can reassign them.", GRAY),
                                                         text("This will consume one", GRAY),
                                                         textOfChildren(Mytems.KITTY_COIN, text("Kitty Coin from your", GRAY)),
                                                         text("inventory. You keep", GRAY),
                                                         text("item xp and levels.", GRAY),
                                                         empty(),
                                                         textOfChildren(Mytems.MOUSE_RIGHT, text(tiny(" reset"), DARK_RED)));
            final ItemStack resetButton = Mytems.REDO.createIcon(resetTooltip);
            gui.highlight(8, gui.getSize() / 9 - 1, color(0x202020));
            gui.setItem(gui.getSize() - 1, resetButton, click -> {
                    if (!click.isRightClick()) return;
                    onClickReset();
                });
        }
        tag.onMenuCreated(this);
        gui.open(player);
    }

    private void onClickUnlockStat(UpgradableStat stat, UpgradableStatStatus status) {
        if (!status.isUpgradable()) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, 0.5f, 0.5f);
            return;
        }
        tag.setUpgradeLevel(stat, Math.min(stat.getMaxLevel().getLevel(), tag.getUpgradeLevel(stat) + 1));
        tag.store(itemStack);
        open();
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 0.5f, 0.75f);
    }

    private void onDrop(UpgradableStat stat, UpgradableStatStatus status) {
        if (!status.hasCurrentLevel()) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, 0.5f, 0.5f);
            return;
        }
        tag.setStatDisabled(stat, !tag.isStatDisabled(stat));
        tag.store(itemStack);
        open();
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, 1f, 1f);
    }

    private void onClickReset() {
        if (tag.countTotalUpgrades() <= 0) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, 0.5f, 0.5f);
            return;
        }
        boolean kittyCoinTaken = false;
        for (int i = 0; i < player.getInventory().getSize(); i += 1) {
            final ItemStack kittyCoin = player.getInventory().getItem(i);
            if (!Mytems.KITTY_COIN.isItem(kittyCoin)) {
                continue;
            }
            kittyCoinTaken = true;
            kittyCoin.subtract(1);
            break;
        }
        if (!kittyCoinTaken) {
            player.sendMessage(textOfChildren(text("You do not have a "), Mytems.KITTY_COIN, text("Kitty Coin in your inventory"))
                               .color(RED));
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, 0.5f, 0.5f);
            return;
        }
        tag.resetUpgrades();
        tag.store(itemStack);
        open();
        player.sendMessage(textOfChildren(Mytems.KITTY_COIN, text("Kitty Coin consumed, upgrades reset. You can now reassign them.", GREEN)));
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 0.5f, 0.5f);
    }
}
