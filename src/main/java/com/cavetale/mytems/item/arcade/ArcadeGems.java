package com.cavetale.mytems.item.arcade;

import com.cavetale.core.font.GuiOverlay;
import com.cavetale.core.struct.Vec2i;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Gui;
import com.cavetale.mytems.util.Items;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.core.util.CamelCase.toCamelCase;
import static com.cavetale.mytems.MytemsPlugin.plugin;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static org.bukkit.SoundCategory.MASTER;

public final class ArcadeGems {
    private final int width = 9;
    private final int height = 6;
    private final int size = width * height;
    private final List<Gem> gems = new ArrayList<>();
    private int score = 0;
    private int turns = 0;
    private Vec2i selected = null;
    private boolean closed = false;
    private final Random random = new Random();
    private Set<Vec2i> scored = new HashSet<>();
    private boolean gameOver = false;

    enum Gem {
        DIAMOND(Material.DIAMOND),
        EMERALD(Material.EMERALD),
        LAPIS(Material.LAPIS_LAZULI),
        REDSTONE(Material.REDSTONE),
        IRON(Material.RAW_IRON),
        COPPER(Material.RAW_COPPER),
        GOLD(Material.RAW_GOLD),
        QUARTZ(Material.QUARTZ),
        AMETHYST(Material.AMETHYST_SHARD),
        NETHER_STAR(Material.NETHER_STAR),
        ENDER_PEARL(Material.ENDER_PEARL),
        GAPPLE(Material.GOLDEN_APPLE),
        RUBY(Mytems.RUBY),
        ;

        protected final Mytems mytems;
        protected final Material material;

        Gem(final Mytems mytems) {
            this.mytems = mytems;
            this.material = null;
        }

        Gem(final Material material) {
            this.mytems = null;
            this.material = material;
        }

        protected ItemStack make() {
            return mytems != null
                ? mytems.createIcon()
                : new ItemStack(material);
        }
    }

    public static void start(Player player) {
        new ArcadeGems().setup().turn(player);
    }

    private ArcadeGems setup() {
        Gem[] values = Gem.values();
        for (int i = 0; i < size; i += 1) {
            gems.add(values[i % values.length]);
        }
        Collections.shuffle(gems, random);
        return this;
    }

    private void turn(Player player) {
        Gui gui = new Gui().size(size);
        GuiOverlay.Builder builder = GuiOverlay.BLANK.builder(size, WHITE)
            .layer(GuiOverlay.TITLE_BAR, DARK_GRAY)
            .title(textOfChildren(gameOver
                                  ? text("GAME OVER", RED)
                                  : text("Gems", GRAY),
                                  text(tiny(" score"), GRAY),
                                  text(score, YELLOW),
                                  text(tiny(" turns"), GRAY),
                                  text(turns, AQUA)));
        for (int y = 0; y < height; y += 1) {
            for (int x = 0; x < width; x += 1) {
                int index = x + y * width;
                Gem gem = gems.get(index);
                ItemStack icon = gem.make();
                final boolean swappable;
                if (gameOver || !scored.isEmpty()) {
                    swappable = false;
                } else if (selected != null) {
                    int dx = Math.abs(x - selected.x);
                    int dz = Math.abs(y - selected.z);
                    swappable = (dx == 1 && dz == 0) || (dx == 0 && dz == 1);
                } else {
                    swappable = true;
                }
                if (swappable) {
                    icon = Items.text(icon, List.of(text(toCamelCase(" ", gem), AQUA),
                                                    textOfChildren(Mytems.MOUSE_LEFT, text(" Swap", GRAY))));
                } else {
                    icon = Items.text(icon, List.of(text(toCamelCase(" ", gem), AQUA),
                                                    textOfChildren(Mytems.NO, text(" Cannot swap", RED))));
                }
                final Vec2i vec = new Vec2i(x, y);
                gui.setItem(x, y, icon, click -> {
                        if (closed) return;
                        if (!swappable) {
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, MASTER, 1.0f, 1.0f);
                        } else if (selected != null) {
                            turns += 1;
                            swap(selected, vec);
                            cross(selected);
                            cross(vec);
                            if (scored.isEmpty()) gameOver = true;
                            selected = null;
                            if (gameOver) {
                                player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, MASTER, 1.0f, 1.0f);
                            } else {
                                player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, MASTER, 1.0f, 1.0f);
                            }
                            turn(player);
                        } else {
                            selected = vec;
                            player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, MASTER, 1.0f, 1.0f);
                            turn(player);
                        }
                    });
                if (selected != null && selected.x == x && selected.z == y) {
                    builder.highlightSlot(x, y, AQUA);
                }
                for (Vec2i it : scored) {
                    builder.highlightSlot(it.x, it.z, GOLD);
                }
            }
        }
        gui.title(builder.build());
        gui.onOpen(evt -> closed = false);
        gui.onClose(evt -> closed = true);
        gui.open(player);
        if (!scored.isEmpty()) {
            Bukkit.getScheduler().runTaskLater(plugin(), () -> {
                    if (closed) return;
                    moveDown();
                    score += scored.size();
                    scored.clear();
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, MASTER, 1.0f, 1.0f);
                    turn(player);
                }, 20L);
        }
    }

    private void swap(Vec2i a, Vec2i b) {
        Gem tmp = getGem(a);
        setGem(a, getGem(b));
        setGem(b, tmp);
    }

    private Gem getGem(Vec2i vec) {
        return gems.get(vec.x + vec.z * width);
    }

    private void setGem(Vec2i vec, Gem gem) {
        gems.set(vec.x + vec.z * width, gem);
    }

    private void cross(Vec2i base) {
        Gem gem = gems.get(base.x + base.z * width);
        int total = 0;
        for (int x = base.x + 1; x < width; x += 1) {
            if (!crossIter(gem, new Vec2i(x, base.z))) break;
            total += 1;
        }
        for (int x = base.x - 1; x >= 0; x -= 1) {
            if (!crossIter(gem, new Vec2i(x, base.z))) break;
            total += 1;
        }
        for (int y = base.z + 1; y < height; y += 1) {
            if (!crossIter(gem, new Vec2i(base.x, y))) break;
            total += 1;
        }
        for (int y = base.z - 1; y >= 0; y -= 1) {
            if (!crossIter(gem, new Vec2i(base.x, y))) break;
            total += 1;
        }
        if (total > 0) scored.add(base);
    }

    private boolean crossIter(Gem gem, Vec2i vec) {
        if (scored.contains(vec)) return false;
        if (getGem(vec) != gem) return false;
        scored.add(vec);
        return true;
    }

    private void moveDown() {
        Gem[] values = Gem.values();
        List<Vec2i> list = new ArrayList<>(scored);
        Collections.sort(list, (a, b) -> Integer.compare(a.z, b.z));
        for (Vec2i it : list) {
            for (int y = it.z; y > 0; y -= 1) {
                setGem(new Vec2i(it.x, y), getGem(new Vec2i(it.x, y - 1)));
            }
            setGem(new Vec2i(it.x, 0), values[random.nextInt(values.length)]);
        }
    }
}
