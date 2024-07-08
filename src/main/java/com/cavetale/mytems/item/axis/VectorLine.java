package com.cavetale.mytems.item.axis;

import com.cavetale.core.struct.Vec3d;
import com.cavetale.mytems.Mytems;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;
import static com.cavetale.mytems.MytemsPlugin.plugin;

public final class VectorLine {
    private static final Vector3f VECTOR_ZERO = new Vector3f(0f, 0f, 0f);
    private static final AxisAngle4f ROTATION_ZERO = new AxisAngle4f(0f, 0f, 0f, 0f);

    @Getter private final World world;
    private final Vec3d pointA;
    private final Vec3d pointB;
    private Player showOnlyTo;
    private ItemDisplay itemDisplay;

    public VectorLine(final World world, final Vec3d pointA, final Vec3d pointB) {
        this.world = world;
        this.pointA = pointA;
        this.pointB = pointB;
    }

    public VectorLine(final Location a, final Location b) {
        if (!a.getWorld().equals(b.getWorld())) {
            throw new IllegalArgumentException("Locations not in same world");
        }
        this.world = a.getWorld();
        this.pointA = Vec3d.of(a);
        this.pointB = Vec3d.of(b);
    }

    public void showOnlyTo(Player player) {
        showOnlyTo = player;
    }

    public void spawn() {
        final double cx = 0.5 * pointA.x + 0.5 * pointB.x;
        final double cy = 0.5 * pointA.y + 0.5 * pointB.y;
        final double cz = 0.5 * pointA.z + 0.5 * pointB.z;
        final Location c = new Location(world, cx, cy, cz);
        final double vx = pointB.x - pointA.x;
        final double vy = pointB.y - pointA.y;
        final double vz = pointB.z - pointA.z;
        c.setDirection(new Vector(vx, vy, vz));
        c.setPitch(c.getPitch() + 90f);
        itemDisplay = world.spawn(c, ItemDisplay.class, e -> {
                e.setPersistent(false);
                e.setItemStack(Mytems.AXIS_MODEL.createItemStack());
                final float length = (float) Math.sqrt(vx * vx + vy * vy + vz * vz);
                final Vector3f scale = new Vector3f(1f, length, 1f);
                e.setTransformation(new Transformation(VECTOR_ZERO,
                                                       ROTATION_ZERO,
                                                       scale,
                                                       ROTATION_ZERO));
                e.setBrightness(new ItemDisplay.Brightness(15, 15));
                e.setViewRange(1024);
                if (showOnlyTo != null) {
                    e.setVisibleByDefault(false);
                }
            });
        if (itemDisplay == null) return;
        if (showOnlyTo != null) {
            showOnlyTo.showEntity(plugin(), itemDisplay);
        }
    }

    public void glow(Color glowColor) {
        itemDisplay.setGlowColorOverride(glowColor);
        itemDisplay.setGlowing(true);
    }

    public void unglow() {
        itemDisplay.setGlowing(false);
    }

    public void remove() {
        itemDisplay.remove();
        itemDisplay = null;
    }

    public void removeLater(final long delay) {
        if (delay < 1) {
            throw new IllegalArgumentException("delay=" + delay);
        }
        Bukkit.getScheduler().runTaskLater(plugin(), this::remove, delay);
    }
}
