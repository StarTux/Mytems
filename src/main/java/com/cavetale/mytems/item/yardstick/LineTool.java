package com.cavetale.mytems.item.yardstick;

import com.cavetale.core.struct.Vec2i;
import com.cavetale.core.struct.Vec3i;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.bukkit.Axis;

public final class LineTool {
    /**
     * Get the 2nd coordinate of a line which is wider than tall.  The
     * first coordinate is obvious as changes by 1 each step.
     * @param a the first coordinate
     * @param b the second coordinate
     * @param width the width of the line
     * @param height the height of the line, or abs(b - a)
     */
    public static int[] helper2d(int a, int b, int width) {
        assert width >= 0;
        final int height = Math.abs(b - a);
        final int[] result = new int[width + 1];
        final int step = a < b ? 1 : -1;
        int v = a;
        int part = width / 2;
        for (int i = 0; i <= width; i += 1) {
            result[i] = v;
            part += height;
            if (part >= width) {
                v += step;
                part -= width;
            }
        }
        return result;
    }

    private static List<Vec3i> helper3d(Vec3i a, Vec3i b, Vec3i sizes, Axis[] axes) {
        final int[] line1 = helper2d(a.getAxis(axes[1]), b.getAxis(axes[1]), sizes.getAxis(axes[0]));
        final int[] line2 = helper2d(a.getAxis(axes[2]), b.getAxis(axes[2]), sizes.getAxis(axes[0]));
        final int width = sizes.getAxis(axes[0]);
        final List<Vec3i> result = new ArrayList<>(width);
        int value = a.getAxis(axes[0]);
        final int step = value < b.getAxis(axes[0]) ? 1 : -1;
        for (int i = 0; i <= width; i += 1) {
            final int[] values = new int[3];
            values[axes[0].ordinal()] = value;
            values[axes[1].ordinal()] = line1[i];
            values[axes[2].ordinal()] = line2[i];
            result.add(Vec3i.of(values));
            value += step;
        }
        return result;
    }

    public static List<Vec2i> line2d(Vec2i a, Vec2i b) {
        final int width = Math.abs(b.x - a.x);
        final int height = Math.abs(b.z - a.z);
        final List<Vec2i> result = new ArrayList<>();
        if (width >= height) {
            final int[] zs = helper2d(a.z, b.z, width);
            final int step = a.x < b.x ? 1 : -1;
            int x = a.x;
            for (int i = 0; i <= width; i += 1) {
                result.add(Vec2i.of(x, zs[i]));
                x += step;
            }
        } else {
            final int[] xs = helper2d(a.x, b.x, height);
            final int step = a.z < b.z ? 1 : -1;
            int z = a.z;
            for (int i = 0; i <= height; i += 1) {
                result.add(Vec2i.of(xs[i], z));
                z += step;
            }
        }
        return result;
    }

    public static List<Vec3i> line3d(Vec3i a, Vec3i b) {
        final Vec3i sizes = Vec3i.of(Math.abs(b.x - a.x),
                                     Math.abs(b.y - a.y),
                                     Math.abs(b.z - a.z));
        Axis[] axes = Axis.values();
        Arrays.sort(axes, Comparator.comparing(sizes::getAxis).reversed());
        return helper3d(a, b, sizes, axes);
    }

    private LineTool() { }
}
