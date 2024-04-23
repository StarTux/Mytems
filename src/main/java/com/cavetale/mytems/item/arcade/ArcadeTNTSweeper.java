package com.cavetale.mytems.item.arcade;

import com.cavetale.core.font.GuiOverlay;
import com.cavetale.core.font.VanillaItems;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.font.Glyph;
import com.cavetale.mytems.util.Gui;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.subscript;
import static com.cavetale.core.font.Unicode.superscript;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

public final class ArcadeTNTSweeper {
    private static final int EMPTY = -1;
    private static final int TNT = -2;
    private static final int BOOM = -3;
    private static final int MARKED_EMPTY = -4;
    private static final int MARKED_TNT = -5;
    private final Random random = new Random();
    private int score;
    private int total;
    private List<Integer> board = new ArrayList<>();
    protected int width = 9;
    protected int height = 6;
    protected int size = width * height;
    protected int bombs = 6;
    private State state = State.PLAY;

    public static void start(Player player) {
        var inst = new ArcadeTNTSweeper();
        inst.makeBoard();
        inst.turn(player);
        player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, SoundCategory.MASTER, 1.0f, 1.0f);
    }

    private void turn(Player player) {
        Gui gui = new Gui().size(size);
        final TextColor bg;
        final Component title;
        if (state == State.LOSE) {
            bg = RED;
            title = textOfChildren(Mytems.SURPRISED, text(" You Lost", DARK_RED, BOLD));
        } else if (state == State.WIN) {
            bg = GREEN;
            title = textOfChildren(Mytems.COOL, text(" You Win ", GOLD, BOLD));
        } else {
            bg = GRAY;
            title = textOfChildren(Mytems.SMILE,
                                   text(" TNT Sweep ", RED),
                                   text(superscript(score) + "/" + subscript(total)));
        }
        GuiOverlay.Builder overlay = GuiOverlay.BLANK.builder(size, bg).title(title);
        for (int cy = 0; cy < height; cy += 1) {
            for (int cx = 0; cx < width; cx += 1) {
                final int cell = board.get(cx + cy * width);
                final ItemStack icon;
                if (cell == 0) {
                    icon = Mytems.INVISIBLE_ITEM.createIcon(List.of(text("Empty", GRAY)));
                } else if (cell > 0) {
                    icon = Glyph.toGlyph((char) ('0' + cell)).mytems.createIcon(List.of(textOfChildren(text(cell, BLUE),
                                                                                                       VanillaItems.TNT,
                                                                                                       text(" adjacent", BLUE))));
                } else if (cell == EMPTY) {
                    icon = Mytems.CHECKBOX.createIcon(List.of(textOfChildren(Mytems.MOUSE_LEFT, text(" Reveal", GRAY)),
                                                              textOfChildren(Mytems.MOUSE_RIGHT, text(" Mark", GRAY))));
                } else if (cell == TNT) {
                    if (state == State.LOSE) {
                        icon = tooltip(new ItemStack(Material.TNT),
                                       List.of(text("Boom! You lost.", RED)));
                    } else {
                        icon = Mytems.CHECKBOX.createIcon(List.of(textOfChildren(Mytems.MOUSE_LEFT, text(" Reveal", GRAY)),
                                                                  textOfChildren(Mytems.MOUSE_RIGHT, text(" Mark", GRAY))));
                    }
                } else if (cell == BOOM) {
                    icon = tooltip(new ItemStack(Material.TNT),
                                   List.of(text("Boom! You lost.", RED)));
                } else if (cell == MARKED_EMPTY || cell == MARKED_TNT) {
                    icon = Mytems.CROSSED_CHECKBOX.createIcon(List.of(textOfChildren(Mytems.MOUSE_RIGHT, text(" Unmark", GRAY))));
                } else {
                    icon = Mytems.QUESTION_MARK.createIcon(List.of(text(cell, DARK_RED)));
                }
                final int x = cx;
                final int y = cy;
                gui.setItem(x, y, icon, click -> {
                        if (state == State.LOSE) return;
                        if (state == State.WIN) {
                            var game = new ArcadeTNTSweeper();
                            game.bombs = this.bombs + 1;
                            game.makeBoard();
                            game.turn(player);
                            player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, SoundCategory.MASTER, 1.0f, 1.0f);
                            return;
                        }
                        if (click.isLeftClick()) {
                            if (reveal(player, x, y)) {
                                player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, SoundCategory.MASTER, 1.0f, 1.0f);
                                if (state == State.PLAY && score == total) {
                                    state = State.WIN;
                                    turn(player);
                                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 0.5f, 2.0f);
                                }
                            }
                        } else if (click.isRightClick()) {
                            int newCell = cell;
                            if (cell == EMPTY) {
                                newCell = MARKED_EMPTY;
                            } else if (cell == TNT) {
                                newCell = MARKED_TNT;
                            } else if (cell == MARKED_EMPTY) {
                                newCell = EMPTY;
                            } else if (cell == MARKED_TNT) {
                                newCell = TNT;
                            } else {
                                return;
                            }
                            if (cell != newCell) {
                                board.set(x + y * width, newCell);
                                player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, SoundCategory.MASTER, 1.0f, 1.0f);
                                turn(player);
                            }
                        }
                    });
            }
        }
        gui.title(overlay.build());
        gui.open(player);
    }

    private void makeBoard() {
        board.clear();
        total = size - bombs;
        score = 0;
        for (int i = 0; i < width * height; i += 1) {
            board.add(i < bombs ? TNT : EMPTY);
        }
        Collections.shuffle(board, random);
    }


    private boolean reveal(Player player, int x, int y) {
        int cell = board.get(x + y * width);
        if (cell == TNT && score == 0) {
            cell = -1;
            do {
                Collections.shuffle(board, random);
            } while (board.get(x + y * width) != cell);
        }
        if (cell == EMPTY) {
            revealRec(x, y);
            turn(player);
            return true;
        } else if (cell == TNT) {
            board.set(x + y * width, BOOM);
            state = State.LOSE;
            turn(player);
            player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 1.0f, 1.0f);
            return false;
        } else {
            return false;
        }
    }

    private void revealRec(int x, int y) {
        if (x < 0 || x >= width) return;
        if (y < 0 || y >= height) return;
        int cell = board.get(x + y * width);
        if (cell != EMPTY) return;
        cell = 0;
        final int ax = Math.max(0, x - 1);
        final int bx = Math.min(width - 1, x + 1);
        final int ay = Math.max(0, y - 1);
        final int by = Math.min(height - 1, y + 1);
        for (int cy = ay; cy <= by; cy += 1) {
            for (int cx = ax; cx <= bx; cx += 1) {
                int cell2 = board.get(cx + cy * width);
                if (cell2 == TNT || cell2 == MARKED_TNT) {
                    cell += 1;
                }
            }
        }
        board.set(x + y * width, cell);
        score += 1;
        if (cell != 0) return;
        revealRec(x - 1, y);
        revealRec(x + 1, y);
        revealRec(x, y - 1);
        revealRec(x, y + 1);
    }

    private enum State {
        PLAY,
        LOSE,
        WIN;
    }
}
