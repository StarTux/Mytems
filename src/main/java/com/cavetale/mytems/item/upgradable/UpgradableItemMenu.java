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
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
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
            .title(textOfChildren(text(tiny("level"), GRAY),
                                  text(tag.getLevel(), WHITE),
                                  text(tiny(" xp"), GRAY),
                                  text(tag.getXp(), WHITE),
                                  text("/", GRAY),
                                  text(tag.getRequiredXp(), WHITE)));
        gui.layer(GuiOverlay.BLANK, itemTier.getMenuColor());
        gui.layer(GuiOverlay.TOP_BAR, itemTier.getMenuColor());
        gui.setItem(0, itemStack);
        for (UpgradableStat stat : upgradableItem.getStats()) {
            final UpgradableStatStatus status = tag.getUpgradeStatus(stat);
            final ItemStack icon = status.hasCurrentLevel()
                ? status.getCurrentLevel().getIcon()
                : stat.getIcon();
            final List<Component> tooltip = new ArrayList<>();
            tooltip.add(stat.getTitle());
            if (status.hasCurrentLevel()) {
                // Display the current level
                tooltip.add(text(tiny("level ") + status.getCurrentLevel().getLevel() + "/" + stat.getMaxLevel().getLevel(), LIGHT_PURPLE, ITALIC));
                tooltip.addAll(status.getCurrentLevel().getDescription());
            }
            if (status.hasNextLevel() && (status.isUpgradable() || !status.getType().isPermanentlyLocked())) {
                if (status.hasCurrentLevel()) {
                    // Divider line
                    tooltip.add(textOfChildren(text("  "), text("                        ", LIGHT_PURPLE, STRIKETHROUGH), text("  ")));
                }
                // Display the next level
                tooltip.add(text(tiny("level ") + status.getNextLevel().getLevel() + "/" + stat.getMaxLevel().getLevel(), LIGHT_PURPLE, ITALIC));
                tooltip.addAll(status.getNextLevel().getDescription());
            }
            final TextColor highlightColor;
            if (status.isUpgradable()) {
                highlightColor = GREEN;
            } else {
                if (status.getType() == UpgradableStatStatus.Type.MAX_LEVEL) {
                    highlightColor = WHITE;
                } else if (status.getType() == UpgradableStatStatus.Type.ITEM_LEVEL_TOO_LOW) {
                    highlightColor = null;
                } else if (status.hasCurrentLevel()) {
                    highlightColor = GRAY;
                } else {
                    highlightColor = DARK_RED;
                }
            }
            if (status instanceof UpgradableStatStatus.Upgradable) {
                tooltip.add(textOfChildren(Mytems.MOUSE_RIGHT, text(tiny(" to unlock"), GREEN)));
            } else if (status instanceof UpgradableStatStatus.TierTooLow tierTooLow) {
                tooltip.add(textOfChildren(text(tiny("requires tier ") + tierTooLow.getRequiredTier().getRomanTier(), DARK_RED)));
                tooltip.add(textOfChildren(text("  "), tierTooLow.getRequiredTier().getMytems().getMytem().getDisplayName())
                            .color(DARK_RED));
            } else if (status instanceof UpgradableStatStatus.UnmetDependencies unmetDependencies) {
                for (UpgradableStat dependency : unmetDependencies.getMissingDependencies()) {
                    tooltip.add(textOfChildren(text(tiny("requires ")), dependency.getTitle())
                                .color(DARK_RED));
                }
            } else if (status instanceof UpgradableStatStatus.ItemLevelTooLow itemLevelTooLow) {
                tooltip.add(text(tiny("requires level ") + itemLevelTooLow.getRequiredItemLevel(), DARK_RED));
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
