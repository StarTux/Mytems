package com.cavetale.mytems.util;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public final class Collision {
    private Collision() { }

    public static boolean collidesWithBlock(World world, BoundingBox bb) {
        final Vector min = bb.getMin();
        final Vector max = bb.getMax();
        final int ax = min.getBlockX();
        final int ay = min.getBlockY() - 1; // be nice to fences
        final int az = min.getBlockZ();
        final int bx = max.getBlockX();
        final int by = max.getBlockY();
        final int bz = max.getBlockZ();
        int bs = 0;
        int bbs = 0;
        for (int y = ay; y <= by; y += 1) {
            for (int z = az; z <= bz; z += 1) {
                for (int x = ax; x <= bx; x += 1) {
                    Block block = world.getBlockAt(x, y, z);
                    bs += 1;
                    for (BoundingBox box : block.getCollisionShape().getBoundingBoxes()) {
                        bbs += 1;
                        if (bb.overlaps(box.shift((double) x, (double) y, (double) z))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
