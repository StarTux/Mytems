package com.cavetale.mytems.item.furniture;

import com.cavetale.core.struct.Vec3i;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;

@Getter
@RequiredArgsConstructor
public final class FurnitureTypeSofa extends FurnitureTypeAbstractChair {
    private final FurnitureType type;

    @Override
    public FurnitureOrientation getOrientation() {
        return FurnitureOrientation.HORIZONTAL_AXIS_ALIGNED;
    }

    @Override
    public Map<Vec3i, BlockData> createBlockData(BlockFace facing) {
        return switch (facing) {
        case SOUTH -> Map.of(Vec3i.ZERO, Material.BARRIER.createBlockData(),
                             Vec3i.of(-1, 0, 0), Material.BARRIER.createBlockData());
        case NORTH -> Map.of(Vec3i.ZERO, Material.BARRIER.createBlockData(),
                             Vec3i.of(1, 0, 0), Material.BARRIER.createBlockData());
        case EAST -> Map.of(Vec3i.ZERO, Material.BARRIER.createBlockData(),
                            Vec3i.of(0, 0, 1), Material.BARRIER.createBlockData());
        case WEST -> Map.of(Vec3i.ZERO, Material.BARRIER.createBlockData(),
                            Vec3i.of(0, 0, -1), Material.BARRIER.createBlockData());
        default -> Map.of(Vec3i.ZERO, Material.BARRIER.createBlockData(),
                          Vec3i.of(-1, 0, 0), Material.BARRIER.createBlockData());
        };
    }
}
