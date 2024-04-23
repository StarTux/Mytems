package com.cavetale.mytems.item.dividers;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.struct.Vec2i;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import static com.cavetale.mytems.MytemsPlugin.sessionOf;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@Getter
public final class Dividers implements Mytem {
    private final Mytems key;
    private final Component displayName;
    private ItemStack prototype;
    private static final List<List<Vec2i>> BASE_CIRCLES = new ArrayList<>();
    private static final int MAX_RADIUS = 256;

    public Dividers(final Mytems key) {
        this.key = key;
        this.displayName = text("Dividers", color(0xE1C16E));
    }

    @Override
    public void enable() {
        this.prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                tooltip(meta, List.of(displayName,
                                      text(tiny("Plan out circles on the"), GRAY),
                                      text(tiny("ground and build them"), GRAY),
                                      text(tiny("accurately with ease"), GRAY),
                                      empty(),
                                      textOfChildren(Mytems.MOUSE_LEFT, text(" Set Center", GRAY)),
                                      textOfChildren(Mytems.MOUSE_RIGHT, text(" Draw Circle", GRAY))));
                meta.setUnbreakable(true);
                meta.addItemFlags(ItemFlag.values());
                key.markItemMeta(meta);
            });
        BASE_CIRCLES.add(List.of(Vec2i.of(0, 0)));
        for (int radius = 1; radius < MAX_RADIUS; radius += 1) {
            BASE_CIRCLES.add(null);
        }
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerLeftClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (!event.hasBlock()) return;
        event.setCancelled(true);
        click(player, event.getClickedBlock(), false);
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (!event.hasBlock()) return;
        event.setCancelled(true);
        click(player, event.getClickedBlock(), true);
    }

    private void click(Player player, Block block, boolean right) {
        DividersSession session = sessionOf(player).getFavorites().getOrSet(DividersSession.class, DividersSession::new);
        if (session.drawing) return;
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) {
            soundFail(player);
            return;
        }
        if (!block.getWorld().getName().equals(session.world)) {
            session.reset();
            session.world = block.getWorld().getName();
        }
        if (!right) {
            session.point1 = Vec3i.of(block);
            draw(player, List.of(session.point1));
            player.sendActionBar(text("Center " + session.point1, AQUA));
        } else {
            if (session.point1 == null) {
                session.point1 = Vec3i.of(block);
                draw(player, List.of(session.point1));
            } else {
                session.point2 = Vec3i.of(block);
            }
        }
        if (right && session.point1 != null && session.point2 != null) {
            drawCircle(player, session, session.point1, session.point2);
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

    /**
     * Mid-Point Circle Drawing Algorithm.
     * See https://www.geeksforgeeks.org/mid-point-circle-drawing-algorithm/
     */
    private static List<Vec2i> getBaseCircle(final int radius) {
        if (radius < 0 || radius > MAX_RADIUS) return List.of();
        List<Vec2i> baseCircle = BASE_CIRCLES.get(radius);
        if (baseCircle != null) return baseCircle;
        baseCircle = new ArrayList<>();
        BASE_CIRCLES.set(radius, baseCircle);
        final double rr = (double) (radius * radius);
        int x = radius;
        for (int y = 0; y <= x; y += 1) {
            baseCircle.add(Vec2i.of(x, y));
            double fx = ((double) x) - 0.5;
            double fy = ((double) y) + 1.0;
            if (fx * fx + fy * fy > rr) x -= 1;
        }
        return baseCircle;
    }

    private boolean drawCircle(Player player, DividersSession session, Vec3i a, Vec3i b) {
        World world = player.getWorld();
        Location center = a.toLocation(world).add(0.5, 0.5, 0.5);
        if (!center.isChunkLoaded()) {
            player.sendActionBar(text("Center not loaded: " + a, RED));
            soundFail(player);
            return false;
        }
        final double dx = (double) a.x - (double) b.x;
        final double dz = (double) a.z - (double) b.z;
        final int radius = (int) Math.round(Math.sqrt(dx * dx + dz * dz));
        if (radius > MAX_RADIUS) {
            player.sendActionBar(text("Radius too big: " + radius, RED));
            soundFail(player);
            return false;
        }
        session.radius = radius;
        List<Vec2i> baseCircle = getBaseCircle(radius);
        List<Vec3i> baseBlocks = new ArrayList<>();
        for (Vec2i base : baseCircle) {
            baseBlocks.add(Vec3i.of(a.x + base.x, a.y, a.z + base.z));
            baseBlocks.add(Vec3i.of(a.x + base.x, a.y, a.z - base.z));
            baseBlocks.add(Vec3i.of(a.x - base.x, a.y, a.z + base.z));
            baseBlocks.add(Vec3i.of(a.x - base.x, a.y, a.z - base.z));
            baseBlocks.add(Vec3i.of(a.x + base.z, a.y, a.z + base.x));
            baseBlocks.add(Vec3i.of(a.x + base.z, a.y, a.z - base.x));
            baseBlocks.add(Vec3i.of(a.x - base.z, a.y, a.z + base.x));
            baseBlocks.add(Vec3i.of(a.x - base.z, a.y, a.z - base.x));
        }
        List<Vec3i> blocks = new ArrayList<>();
        blocks.add(a);
        LOOP:
        for (Vec3i base : baseBlocks) {
            if (!world.isChunkLoaded(base.x >> 4, base.z >> 4)) continue;
            Block block = base.toBlock(world);
            while (isFullBlock(block) && block.getY() < world.getMaxHeight()) {
                Block above = block.getRelative(0, 1, 0);
                if (!isFullBlock(above)) break;
                block = above;
            }
            while (!isFullBlock(block)) {
                if (block.getY() <= world.getMinHeight()) continue LOOP;
                block = block.getRelative(0, -1, 0);
            }
            Vec3i vec = Vec3i.of(block);
            if (blocks.contains(vec)) continue;
            blocks.add(vec);
        }
        draw(player, blocks);
        return true;
    }

    private void draw(Player player, List<Vec3i> blocks) {
        DividersSession session = sessionOf(player).getFavorites().getOrSet(DividersSession.class, DividersSession::new);
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
                player.sendActionBar(textOfChildren(key, text(" Drawn circle at "),
                                                    text(session.point1.x + " " + session.point1.z, GOLD),
                                                    text(" radius "), text(session.radius, GOLD)));
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
