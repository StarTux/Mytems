package com.cavetale.mytems.item.arcade;

import com.cavetale.core.font.GuiOverlay;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Gui;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

public final class ArcadeTicTacToe {
    private final Random random = new Random();
    private static final int[][] ROWS = {
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8},
        {0, 3, 6},
        {1, 4, 7},
        {2, 5, 8},
        {0, 4, 8},
        {2, 4, 6},
    };
    private int[] cells = new int[9];
    private int winner;

    public static void start(Player player) {
        new ArcadeTicTacToe().turn(player);
    }

    private void turn(Player player) {
        winner = getWinner();
        boolean full = isFull();
        if (winner == 0 && !full) {
            cells[getNextAiMove()] = 2;
            player.playSound(player.getLocation(), Sound.BLOCK_STONE_PLACE, SoundCategory.MASTER, 1.0f, 1.0f);
            winner = getWinner();
            full = isFull();
        }
        final Component title;
        final TextColor bg;
        if (winner == 2) {
            title = text("You lose", RED);
            bg = DARK_GRAY;
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1.0f, 1.0f);
        } else if (winner == 1) {
            bg = GREEN;
            title = text("You win!", BLACK);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 0.5f, 2.0f);
        } else if (full) {
            title = text("It's a draw", WHITE);
            bg = DARK_GRAY;
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1.0f, 1.0f);
        } else {
            title = text("Tic Tac Toe", WHITE);
            bg = color(0xFBFCDD);
            player.playSound(player.getLocation(), Sound.BLOCK_STONE_PLACE, SoundCategory.MASTER, 1.0f, 1.0f);
        }
        final int size = 5 * 9;
        Gui gui = new Gui()
            .size(size);
        GuiOverlay.Builder builder = GuiOverlay.BLANK.builder(size, bg).title(title);
        for (int dx = 0; dx < 3; dx += 1) {
            for (int dy = 0; dy < 3; dy += 1) {
                final int x = dx;
                final int y = dy;
                int cell = cells[x + y * 3];
                switch (cell) {
                case 0:
                    gui.setItem(x + 3, y + 1, Mytems.CHECKBOX
                                .createIcon(List.of(text("Empty Cell", DARK_GRAY),
                                                    empty(),
                                                    textOfChildren(Mytems.MOUSE_LEFT, text(" Place your ", GRAY),
                                                                   Mytems.CHECKED_CHECKBOX))),
                                click -> {
                                    if (!click.isLeftClick()) return;
                                    if (winner != 0) {
                                        player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, SoundCategory.MASTER, 1.0f, 0.5f);
                                        return;
                                    }
                                    cells[x + y * 3] = 1;
                                    turn(player);
                                });
                    break;
                case 1:
                    gui.setItem(x + 3, y + 1, Mytems.CHECKED_CHECKBOX .createIcon(List.of(text("Your Cell", GREEN))),
                                click -> {
                                    if (!click.isLeftClick()) return;
                                    player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, SoundCategory.MASTER, 1.0f, 0.5f);
                                });
                    if (winner == 1) {
                        builder.highlightSlot(x + 3, y + 1, DARK_GREEN);
                    }
                    break;
                case 2:
                    gui.setItem(x + 3, y + 1, Mytems.CROSSED_CHECKBOX .createIcon(List.of(text("Enemy Cell", RED))),
                                click -> {
                                    if (!click.isLeftClick()) return;
                                    player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, SoundCategory.MASTER, 1.0f, 0.5f);
                                });
                    if (winner == 2) {
                        builder.highlightSlot(x + 3, y + 1, DARK_RED);
                    }
                    break;
                default: break;
                }
            }
        }
        gui.title(builder.build());
        gui.open(player);
    }

    public int getWinner() {
        OUTER: for (int[] row : ROWS) {
            int green = 0;
            int red = 0;
            for (int i : row) {
                switch (cells[i]) {
                case 1:
                    if (red > 0) continue OUTER;
                    green += 1;
                    break;
                case 2:
                    if (green > 0) continue OUTER;
                    red += 1;
                    break;
                default: continue OUTER;
                }
            }
            if (green == 3) return 1;
            if (red == 3) return 2;
        }
        return 0;
    }

    public int getNextAiMove() {
        int rescue = -1;
        for (int[] row : ROWS) {
            int green = 0;
            int red = 0;
            int lastEmpty = 0;
            for (int i : row) {
                switch (cells[i]) {
                case 1: green += 1; break;
                case 2: red += 1; break;
                default: lastEmpty = i;
                }
            }
            if (red == 2 && green == 0) {
                return lastEmpty;
            } else if (green == 2 && red == 0) {
                rescue = lastEmpty;
            }
        }
        if (rescue >= 0) return rescue;
        int result = -1;
        int chance = 1;
        for (int i = 0; i < 9; i += 1) {
            if (cells[i] == 0) {
                if (random.nextInt(chance) == 0) {
                    result = i;
                }
                chance += 1;
            }
        }
        return result;
    }

    public boolean isFull() {
        for (int i : cells) {
            if (i == 0) return false;
        }
        return true;
    }

    @Override public String toString() {
        List<Integer> ls = new ArrayList<>();
        for (int i : cells) ls.add(i);
        return "" + ls;
    }
}
