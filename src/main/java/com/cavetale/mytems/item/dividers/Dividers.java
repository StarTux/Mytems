package com.cavetale.mytems.item.dividers;

import com.cavetale.core.struct.Cuboid;
import com.cavetale.core.struct.Vec2i;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.axis.CuboidOutline;
import com.cavetale.mytems.session.Session;
import com.cavetale.mytems.util.Attr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Axis;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@Getter
public final class Dividers implements Mytem {
    private static final int MAX_RADIUS = 256;
    private static final int COLOR_HEX = 0x95b8d1;
    private final Mytems key;
    private final Component displayName;
    private ItemStack prototype;
    private static final List<List<Vec2i>> BASE_CIRCLES = new ArrayList<>();

    public Dividers(final Mytems key) {
        this.key = key;
        this.displayName = text("Dividers", color(COLOR_HEX));
    }

    @Override
    public void enable() {
        this.prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                key.markItemMeta(meta);
                tooltip(meta, List.of(displayName,
                                      text(tiny("Plan out circles and build"), GRAY),
                                      text(tiny("them accurately with ease"), GRAY),
                                      empty(),
                                      textOfChildren(Mytems.MOUSE_LEFT, text(" Set Center", GRAY)),
                                      textOfChildren(Mytems.MOUSE_RIGHT, text(" Draw Circle", GRAY)),
                                      textOfChildren(Mytems.MOUSE_CURSOR, Mytems.MOUSE_RIGHT, text(" Clear selection", GRAY))));
                meta.setUnbreakable(true);
                Attr.addNumber(meta, Attribute.PLAYER_BLOCK_INTERACTION_RANGE, "dividers_range", 5.5, EquipmentSlotGroup.HAND);
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            });
        BASE_CIRCLES.add(List.of(Vec2i.of(0, 0)));
        for (int radius = 1; radius <= MAX_RADIUS; radius += 1) {
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
        click(player, event.getClickedBlock(), false, faceToAxis(event.getBlockFace()));
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (!event.hasBlock()) return;
        event.setCancelled(true);
        click(player, event.getClickedBlock(), true, faceToAxis(event.getBlockFace()));
    }

    private static Axis faceToAxis(BlockFace face) {
        return switch (face) {
        case EAST -> Axis.X;
        case WEST -> Axis.X;
        case NORTH -> Axis.Z;
        case SOUTH -> Axis.Z;
        case UP -> Axis.Y;
        case DOWN -> Axis.Y;
        default -> Axis.Y;
        };
    }

    private void click(Player player, Block block, boolean right, Axis axis) {
        DividersSession session = Session.of(player).getFavorites().getOrSet(DividersSession.class, DividersSession::new);
        if (!block.getWorld().getName().equals(session.world)) {
            session.clearBlocks();
            session.reset();
            session.world = block.getWorld().getName();
        }
        if (!right) {
            session.point1 = Vec3i.of(block);
            session.axis = axis;
            draw(player, session, List.of(session.point1));
            player.sendActionBar(textOfChildren(key,
                                                text("Center ", GRAY), text("" + session.point1, AQUA),
                                                text(" " + axis.name(), color(COLOR_HEX)), text(tiny("axis"), GRAY)));
        } else {
            if (session.point1 == null) {
                session.point1 = Vec3i.of(block);
                session.axis = axis;
                draw(player, session, List.of(session.point1));
            } else {
                session.point2 = Vec3i.of(block);
            }
        }
        if (right && session.point1 != null && session.point2 != null) {
            drawCircle(player, session, session.point1, session.point2, session.axis);
            player.sendMessage(textOfChildren(key,
                                              text("(" + session.point1 + ")", color(COLOR_HEX)),
                                              text(" " + session.axis.name(), color(COLOR_HEX)), text(tiny("axis"), GRAY),
                                              text(" " + session.radius, color(COLOR_HEX)), text(tiny("radius"), GRAY),
                                              text(" " + (session.blocks.size() - 1), color(COLOR_HEX)), text(tiny("blocks"), GRAY))
                               .insertion("(" + session.point1 + ") (" + session.point2 + ")"));
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

    private boolean drawCircle(Player player, DividersSession session, Vec3i a, Vec3i b, Axis axis) {
        World world = player.getWorld();
        Location center = a.toLocation(world).add(0.5, 0.5, 0.5);
        if (!center.isChunkLoaded()) {
            player.sendActionBar(text("Center not loaded: " + a, RED));
            soundFail(player);
            return false;
        }
        final int radius = a.maxDistance(b);
        if (radius > MAX_RADIUS) {
            player.sendActionBar(text("Radius too big: " + radius, RED));
            soundFail(player);
            return false;
        }
        session.radius = radius;
        List<Vec2i> baseCircle = getBaseCircle(radius);
        final Vec3i r;
        final Vec3i u;
        switch (axis) {
        case X:
            r = Vec3i.of(0, 0, 1);
            u = Vec3i.of(0, 1, 0);
            break;
        case Y:
            r = Vec3i.of(1, 0, 0);
            u = Vec3i.of(0, 0, 1);
            break;
        case Z:
            r = Vec3i.of(1, 0, 0);
            u = Vec3i.of(0, 1, 0);
            break;
        default:
            throw new IllegalStateException("axis=" + axis);
        }
        final Set<Vec3i> blocks = new HashSet<>();
        for (Vec2i base : baseCircle) {
            blocks.add(a.add(r.multiply(+base.x)).add(u.multiply(+base.z)));
            blocks.add(a.add(r.multiply(+base.x)).add(u.multiply(-base.z)));
            blocks.add(a.add(r.multiply(-base.x)).add(u.multiply(+base.z)));
            blocks.add(a.add(r.multiply(-base.x)).add(u.multiply(-base.z)));
            blocks.add(a.add(r.multiply(+base.z)).add(u.multiply(+base.x)));
            blocks.add(a.add(r.multiply(+base.z)).add(u.multiply(-base.x)));
            blocks.add(a.add(r.multiply(-base.z)).add(u.multiply(+base.x)));
            blocks.add(a.add(r.multiply(-base.z)).add(u.multiply(-base.x)));
        }
        blocks.add(a);
        draw(player, session, List.copyOf(blocks));
        return true;
    }

    private void draw(Player player, DividersSession session, List<Vec3i> blocks) {
        final World world = player.getWorld();
        session.clearBlocks();
        session.blocks = new HashMap<>();
        for (Vec3i vector : blocks) {
            if (!world.isChunkLoaded(vector.x >> 4, vector.z >> 4)) {
                continue;
            }
            final Cuboid cuboid = new Cuboid(vector.x, vector.y, vector.z,
                                             vector.x, vector.y, vector.z);
            final CuboidOutline outline = new CuboidOutline(world, cuboid);
            outline.showOnlyTo(player);
            outline.spawn();
            outline.glow(Color.fromRGB(COLOR_HEX));
            session.blocks.put(vector, outline);
        }
    }

    @Override
    public void onPlayerInventoryClick(InventoryClickEvent event, Player player, ItemStack item) {
        if (!event.isRightClick()) return;
        final DividersSession session = DividersSession.of(player);
        session.clearBlocks();
        session.reset();
        soundUse(player);
        event.setCancelled(true);
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
