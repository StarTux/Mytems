package com.cavetale.mytems.item.furniture;

import com.cavetale.core.struct.Vec3i;
import java.util.List;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;

public interface FurnitureTypeImpl {
    FurnitureType getType();

    FurnitureOrientation getOrientation();

    default Map<Vec3i, BlockData> createBlockData(BlockFace facing) {
        return Map.of(Vec3i.ZERO, Material.BARRIER.createBlockData());
    }

    default void spawnItemDisplay(Player player, Block block, BlockFace facing) {
        final Location location = block.getLocation().add(0.5, 0.5, 0.5);
        switch (getOrientation()) {
        case HORIZONTAL_FREE:
            location.setYaw(player.getYaw() + 180f);
            break;
        case HORIZONTAL_AXIS_ALIGNED:
            location.setDirection(facing.getDirection());
            break;
        case NONE: default: break;
        }
        location.getWorld().spawn(location, ItemDisplay.class, e -> {
                e.setItemStack(getType().getMytems().createItemStack());
            });
    }

    default void onPlayerRightClick(Player player, Block clickedBlock, Block originBlock) { }

    default void onBreakFurniture(Player player, Block originBlock, List<Block> brokenBlocks) { }
}
