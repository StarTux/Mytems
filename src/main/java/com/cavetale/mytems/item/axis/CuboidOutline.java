package com.cavetale.mytems.item.axis;

import com.cavetale.core.struct.Cuboid;
import com.cavetale.core.struct.Vec3d;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.mytems.Mytems;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Value;
import org.bukkit.Axis;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import static com.cavetale.mytems.MytemsPlugin.plugin;

public final class CuboidOutline {
    private static final float RIGHT_ANGLE = (float) (Math.PI * 0.5);
    private static final Vector3f VECTOR_ZERO = new Vector3f(0f, 0f, 0f);
    private static final AxisAngle4f ROTATION_ZERO = new AxisAngle4f(0f, 0f, 0f, 0f);

    @Getter private final World world;
    @Getter private Cuboid cuboid;
    private Player showOnlyTo;
    private Vec3d center;
    private Vec3i size;
    @Getter private final List<Entry> entries = new ArrayList<>();

    @Value
    private static final class Entry {
        private final Axis axis;
        private final boolean max1;
        private final boolean max2;
        private final ItemDisplay itemDisplay;
    }

    public CuboidOutline(final World world, final Cuboid cuboid) {
        this.world = world;
        setCuboid(cuboid);
    }

    public void showOnlyTo(Player player) {
        showOnlyTo = player;
    }

    public void spawn() {
        for (Axis axis : Axis.values()) {
            final Vector3f scale = getScale(axis);
            final AxisAngle4f rotation = getRotation(axis);
            for (boolean max1 : List.of(false, true)) {
                for (boolean max2 : List.of(false, true)) {
                    final ItemDisplay itemDisplay = world.spawn(getLocation(axis, max1, max2), ItemDisplay.class, e -> {
                            e.setPersistent(false);
                            e.setItemStack(Mytems.AXIS_MODEL.createItemStack());
                            e.setTransformation(new Transformation(VECTOR_ZERO,
                                                                   rotation,
                                                                   scale,
                                                                   ROTATION_ZERO));
                            e.setBrightness(new ItemDisplay.Brightness(15, 15));
                            e.setViewRange(1024);
                            if (showOnlyTo != null) {
                                e.setVisibleByDefault(false);
                            }
                        });
                    if (itemDisplay == null) continue;
                    if (showOnlyTo != null) {
                        showOnlyTo.showEntity(plugin(), itemDisplay);
                    }
                    entries.add(new Entry(axis, max1, max2, itemDisplay));
                }
            }
        }
    }

    public void glow(Color glowColor) {
        for (Entry entry : entries) {
            entry.itemDisplay.setGlowColorOverride(glowColor);
            entry.itemDisplay.setGlowing(true);
        }
    }

    public void unglow() {
        for (Entry entry : entries) {
            entry.itemDisplay.setGlowing(false);
        }
    }

    public void update(Cuboid newCuboid) {
        setCuboid(newCuboid);
        for (Entry entry : entries) {
            entry.itemDisplay.teleport(getLocation(entry.axis, entry.max1, entry.max2));
            entry.itemDisplay.setTransformation(new Transformation(VECTOR_ZERO,
                                                                   getRotation(entry.axis),
                                                                   getScale(entry.axis),
                                                                   ROTATION_ZERO));
        }
    }

    public void remove() {
        for (Entry entry : entries) {
            entry.itemDisplay.remove();
        }
        entries.clear();
    }

    public void removeLater(final long delay) {
        if (delay < 1) {
            throw new IllegalArgumentException("delay=" + delay);
        }
        Bukkit.getScheduler().runTaskLater(plugin(), this::remove, delay);
    }

    @Override
    public String toString() {
        return "CuboidOutline(" + world.getName() + ", " + cuboid + ")";
    }

    private void setCuboid(Cuboid cuboid) {
        this.cuboid = cuboid;
        this.center = cuboid.getCenterExact();
        this.size = cuboid.getSize();
    }

    private Location getLocation(Axis axis, boolean max1, boolean max2) {
        return switch (axis) {
        case X -> new Location(world, center.x, (!max1 ? cuboid.ay : cuboid.by + 1), (!max2 ? cuboid.az : cuboid.bz + 1));
        case Y -> new Location(world, (!max1 ? cuboid.ax : cuboid.bx + 1), center.y, (!max2 ? cuboid.az : cuboid.bz + 1));
        case Z -> new Location(world, (!max1 ? cuboid.ax : cuboid.bx + 1), (!max2 ? cuboid.ay : cuboid.by + 1), center.z);
        };
    }

    private Vector3f getScale(Axis axis) {
        return switch (axis) {
        case X -> new Vector3f(1f, size.x, 1f);
        case Y -> new Vector3f(1f, size.y, 1f);
        case Z -> new Vector3f(1f, size.z, 1f);
        };
    }

    private AxisAngle4f getRotation(Axis axis) {
        return switch (axis) {
        case X -> new AxisAngle4f(RIGHT_ANGLE, 0f, 0f, 1f);
        case Y -> ROTATION_ZERO;
        case Z -> new AxisAngle4f(RIGHT_ANGLE, 1f, 0f, 0f);
        };
    }
}
