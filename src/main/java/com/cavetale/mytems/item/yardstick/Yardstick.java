package com.cavetale.mytems.item.yardstick;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.struct.Vec2i;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.core.text.LineWrap;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Items;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@Getter
public final class Yardstick implements Mytem {
    private final Mytems key;
    private Component displayName;
    private ItemStack prototype;
    private static final int MAX_LENGTH = 512;

    public Yardstick(final Mytems key) {
        this.key = key;
    }

    private static final String TOOLTIP = "Draw lines and measure distances.";

    @Override
    public void enable() {
        this.displayName = text("Yardstick", color(0xE1C16E));
        this.prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                List<Component> text = new ArrayList<>();
                text.add(displayName);
                text.addAll(new LineWrap().emoji(true).componentMaker(str -> text(str, WHITE)).wrap(TOOLTIP));
                text.add(empty());
                text.add(textOfChildren(Mytems.MOUSE_LEFT, text(" Set point A")));
                text.add(textOfChildren(Mytems.MOUSE_RIGHT, text(" Set point B")));
                Items.text(meta, text);
                meta.setUnbreakable(true);
                meta.addItemFlags(ItemFlag.values());
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerLeftClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (!event.hasBlock()) return;
        event.setCancelled(true);
        click(player, event.getClickedBlock(), 1);
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (!event.hasBlock()) return;
        event.setCancelled(true);
        click(player, event.getClickedBlock(), 2);
    }

    private void click(Player player, Block block, int num) {
        YardstickSession session = YardstickSession.of(player);
        if (session.drawing) return;
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) {
            soundFail(player);
            return;
        }
        if (!block.getWorld().getName().equals(session.world)) {
            session.reset();
            session.world = block.getWorld().getName();
        }
        if (num == 1) {
            session.point1 = Vec3i.of(block);
            if (session.point2 == null) {
                draw(player, List.of(session.point1), false);
            }
            player.sendActionBar(textOfChildren(text("Point A ", GRAY),
                                                text(session.point1.x + " " + session.point1.y + " " + session.point1.z, GOLD)));
        } else if (num == 2) {
            session.point2 = Vec3i.of(block);
            if (session.point1 == null) {
                draw(player, List.of(session.point1), false);
            }
            player.sendActionBar(textOfChildren(text("Point B ", GRAY),
                                                text(session.point2.x + " " + session.point2.y + " " + session.point2.z, GOLD)));
        }
        if (session.point1 != null && session.point2 != null) {
            drawLine(player, session, session.point1, session.point2);
            soundDraw(player);
        } else {
            soundUse(player);
        }
    }

    private static boolean isFullBlock(Block block) {
        var voxelShape = block.getCollisionShape();
        if (voxelShape == null) return false;
        var boundingBoxes = voxelShape.getBoundingBoxes();
        if (boundingBoxes.size() != 1) return false;
        var bb = boundingBoxes.iterator().next();
        return bb.getWidthX() == 1.0
            && bb.getHeight() == 1.0
            && bb.getWidthZ() == 1.0;
    }

    private boolean drawLine(Player player, YardstickSession session, Vec3i a, Vec3i b) {
        World world = player.getWorld();
        final double dx = (double) a.x - (double) b.x;
        final double dy = (double) a.y - (double) b.y;
        final double dz = (double) a.z - (double) b.z;
        session.horizontalLength = (int) Math.round(Math.sqrt(dx * dx + dz * dz));
        if (session.horizontalLength > MAX_LENGTH) {
            player.sendActionBar(text("Line span too long: " + session.horizontalLength, RED));
            soundFail(player);
            return false;
        }
        session.length = (int) Math.round(Math.sqrt(dx * dx + dy * dy + dz * dz));
        List<Vec2i> horizontalLine = makeLine(Vec2i.of(a.x, a.z), Vec2i.of(b.x, b.z));
        List<Vec3i> blocks = new ArrayList<>();
        final int cy = (a.y + b.y) / 2;
        LOOP:
        for (Vec2i base : horizontalLine) {
            if (!world.isChunkLoaded(base.x >> 4, base.z >> 4)) continue;
            Block block = world.getBlockAt(base.x, cy, base.z);
            // Move up
            while (isFullBlock(block) && block.getY() < world.getMaxHeight()) {
                Block above = block.getRelative(0, 1, 0);
                if (!isFullBlock(above)) break;
                block = above;
            }
            // Move down
            while (!isFullBlock(block)) {
                if (block.getY() <= world.getMinHeight()) continue LOOP;
                block = block.getRelative(0, -1, 0);
            }
            Vec3i vec = Vec3i.of(block);
            if (blocks.contains(vec)) continue;
            blocks.add(vec);
        }
        draw(player, blocks, true);
        return true;
    }

    /**
     * We traverse the x-coordinates from left to right.  If the
     * z-axis is longer than the x-axis however, we swap them first.
     *
     * @return Each pixel of the line from point a to point b,
     * correctly swapped.
     */
    protected static List<Vec2i> makeLine(Vec2i a, Vec2i b) {
        final int hor = Math.abs(b.x - a.x);
        final int ver = Math.abs(b.z - a.z);
        final int dx;
        final int dz;
        final int ax;
        final int bx;
        final int az;
        final int bz;
        final boolean swapped;
        if (hor >= ver) {
            swapped = false;
            dx = hor;
            dz = ver;
            ax = a.x;
            bx = b.x;
            az = a.z;
            bz = b.z;
        } else {
            swapped = true;
            dx = ver;
            dz = hor;
            ax = a.z;
            bx = b.z;
            az = a.x;
            bz = b.x;
        }
        final List<Vec2i> line = new ArrayList<>();
        int z = az;
        int x = ax;
        int part = 0;
        final int sx = ax < bx ? 1 : -1;
        final int sz = az < bz ? 1 : -1;
        for (int i = 0; i <= dx; i += 1) {
            line.add(!swapped
                       ? Vec2i.of(x, z)
                       : Vec2i.of(z, x));
            part += dz;
            if (part >= dx) {
                z += sz;
                part -= dx;
            }
            x += sx;
        }
        return line;
    }

    private void draw(Player player, List<Vec3i> blocks, boolean feedback) {
        YardstickSession session = YardstickSession.of(player);
        if (session.drawing) return;
        session.drawing = true;
        Bukkit.getScheduler().runTaskLater(MytemsPlugin.getInstance(), () -> {
                session.drawing = false;
                final World world = player.getWorld();
                if (!player.isOnline() || !world.getName().equals(session.world)) {
                    return;
                }
                if (session.blocks != null) {
                    for (Vec3i vec : session.blocks) {
                        if (!world.isChunkLoaded(vec.x >> 4, vec.z >> 4)) continue;
                        Block block = vec.toBlock(world);
                        player.sendBlockChange(block.getLocation(), block.getBlockData());
                    }
                    session.blocks = null;
                }
                BlockData fake = Material.BUDDING_AMETHYST.createBlockData();
                for (Vec3i vec : blocks) {
                    if (!world.isChunkLoaded(vec.x >> 4, vec.z >> 4)) continue;
                    Block block = vec.toBlock(world);
                    player.sendBlockChange(block.getLocation(), fake);
                }
                if (feedback) {
                    player.sendMessage(textOfChildren(key, text(" Line drawn from "),
                                                      text(session.point1.x + " " + session.point1.y + " " + session.point2.z, GOLD),
                                                      text(" to "),
                                                      text(session.point2.x + " " + session.point2.y + " " + session.point2.z, GOLD))
                                       .color(GRAY));
                    if (session.length == session.horizontalLength) {
                        player.sendMessage(textOfChildren(key,
                                                          text(tiny(" Distance ")),
                                                          text(session.length, GOLD))
                                           .color(GRAY));
                    } else {
                        player.sendMessage(textOfChildren(key,
                                                          text(tiny(" Distance ")),
                                                          text(session.length, GOLD),
                                                          text(tiny(" Horizontal ")),
                                                          text(session.horizontalLength, GOLD))
                                           .color(GRAY));
                    }
                }
                session.blocks = blocks;
            }, 2L);
    }

    @Override
    public void onDamageEntity(EntityDamageByEntityEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    private void soundUse(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, SoundCategory.MASTER, 1.0f, 2.0f);
    }

    private void soundDraw(Player player) {
        player.playSound(player.getLocation(), Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, SoundCategory.MASTER, 1.0f, 1.25f);
    }

    private void soundFail(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, SoundCategory.MASTER, 0.5f, 0.5f);
    }
}
