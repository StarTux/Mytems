package com.cavetale.mytems;

import com.cavetale.worldmarker.block.BlockMarker;
import com.cavetale.worldmarker.block.BlockMarkerHook;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;

@RequiredArgsConstructor
public final class MytemsBlockMarkerHook implements BlockMarkerHook {
    private final MytemsPlugin plugin;

    public MytemsBlockMarkerHook enable() {
        BlockMarker.registerHook(plugin, this);
        return this;
    }

    static String toString(Block block) {
        return block.getWorld().getName() + " " + block.getX() + " " + block.getY() + " " + block.getZ();
    }

    @Override
    public void onBlockLoad(Block block, String id) {
        Mytems mytems = Mytems.forId(id);
        if (mytems == null) return;
        switch (mytems) {
        case ENDERBALL: {
            if (block.getType() != Material.DRAGON_EGG) {
                plugin.getLogger().info(mytems + " at " + toString(block) + " is not a " + Material.DRAGON_EGG);
                BlockMarker.resetId(block);
            }
            return;
        }
        case TOILET:
            if (block.getType() != Material.BARRIER) {
                plugin.getLogger().info(mytems + " at " + toString(block) + " is not a " + Material.BARRIER);
            }
            return;
        default: break;
        }
    }

    @Override
    public void onBlockUnload(Block block, String id) { }
}
