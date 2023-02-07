package com.cavetale.mytems.item.arcade;

import com.cavetale.core.font.GuiOverlay;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsCategory;
import com.cavetale.mytems.MytemsTag;
import com.cavetale.mytems.util.Gui;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.util.CamelCase.toCamelCase;
import static com.cavetale.mytems.MytemsPlugin.plugin;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static org.bukkit.SoundCategory.MASTER;

public final class ArcadeMemory {
    private final int size = 6 * 9;
    private final List<Mytems> cards = new ArrayList<>();
    private final List<Boolean> revealed = new ArrayList<>();
    private int score = 0;
    private int opened1 = -1;
    private int opened2 = -1;
    private int turns;
    private boolean closed;

    public static void start(Player player) {
        new ArcadeMemory().setup().turn(player);
    }

    private ArcadeMemory setup() {
        List<Mytems> deck = new ArrayList<>();
        deck.addAll(MytemsTag.of(MytemsCategory.MOB_FACE).getValues());
        Collections.shuffle(deck);
        for (Mytems mytems : deck) {
            if (cards.size() >= size) break;
            cards.add(mytems);
            cards.add(mytems);
            revealed.add(false);
            revealed.add(false);
        }
        Collections.shuffle(cards);
        return this;
    }

    private void turn(Player player) {
        Gui gui = new Gui().size(size);
        GuiOverlay.Builder builder = score < size / 2
            ? (GuiOverlay.BLANK.builder(size, WHITE)
               .layer(GuiOverlay.TITLE_BAR, DARK_GRAY)
               .title(textOfChildren(text("Memory ", GRAY),
                                     text(score + "/" + (size / 2), YELLOW))))
            : (GuiOverlay.BLANK.builder(size, GOLD)
               .layer(GuiOverlay.TITLE_BAR, BLUE)
               .title(textOfChildren(text("Memory Complete in " + turns + " Turns", GOLD))));
        for (int i = 0; i < size; i += 1) {
            Mytems mytems = cards.get(i);
            boolean selected = opened1 == i || opened2 == i;
            boolean opened = revealed.get(i) || selected;
            ItemStack icon = opened
                ? mytems.createIcon(List.of(text(toCamelCase(" ", mytems), WHITE)))
                : Mytems.CHECKBOX.createIcon(List.of(text("???", DARK_GRAY),
                                                     textOfChildren(Mytems.MOUSE_LEFT, text(" Reveal", GRAY))));
            final int index = i;
            gui.setItem(i, icon, click -> {
                    if (!click.isLeftClick()) return;
                    if (opened) {
                        // fail, already opened
                        player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, MASTER, 1.0f, 0.5f);
                    } else if (opened1 < 0) {
                        opened1 = index;
                        turn(player);
                        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, MASTER, 1.0f, 2.0f);
                    } else if (opened2 < 0) {
                        opened2 = index;
                        turn(player);
                        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, MASTER, 1.0f, 2.0f);
                    } else {
                        // fail, waiting for next turn
                        player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, MASTER, 1.0f, 0.5f);
                    }
                });
            if (selected) builder.highlightSlot(i, GOLD);
        }
        if (opened1 >= 0 && opened2 >= 0) {
            Bukkit.getScheduler().runTaskLater(plugin(), () -> {
                    if (closed) return;
                    if (cards.get(opened1) == cards.get(opened2)) {
                        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, MASTER, 1.0f, 1.0f);
                        score += 1;
                        revealed.set(opened1, true);
                        revealed.set(opened2, true);
                    } else {
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, MASTER, 1.0f, 1.0f);
                    }
                    opened1 = -1;
                    opened2 = -1;
                    turns += 1;
                    turn(player);
                }, 20L);
        }
        gui.title(builder.build());
        gui.onOpen(evt -> closed = false);
        gui.onClose(evt -> closed = true);
        gui.open(player);
    }
}
