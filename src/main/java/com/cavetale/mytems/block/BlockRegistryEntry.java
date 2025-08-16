package com.cavetale.mytems.block;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.worldmarker.block.BlockMarker;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import static com.cavetale.mytems.MytemsPlugin.mytemsPlugin;

@Getter
@RequiredArgsConstructor
public final class BlockRegistryEntry {
    private final String id;
    private final BlockImplementation implementation;
    private final List<BlockEventHandler> eventHandlers = new ArrayList<>();
    private boolean cancelBlockEdits;

    public static BlockRegistryEntry at(Block block) {
        return BlockRegistry.blockRegistry().getEntryAt(block);
    }

    public void setBlockId(Block block) {
        BlockMarker.setId(block, id);
    }

    public void resetBlockId(Block block) {
        BlockMarker.resetId(block);
    }

    public void clearBlock(Block block) {
        BlockMarker.clearTag(block);
    }

    public boolean isBlock(Block block) {
        return BlockMarker.hasId(block, id);
    }

    public void setParentBlock(Block theBlock, Block parentBlock) {
        final Vec3i parentVector = Vec3i.of(parentBlock).subtract(Vec3i.of(theBlock));
        BlockMarker.getTag(theBlock, true, tag -> {
                tag.set(mytemsPlugin().namespacedKey("parent"), PersistentDataType.INTEGER_ARRAY, parentVector.toArray());
                return true;
            });
    }

    /**
     * Get the relative parent vector.
     */
    public static Vec3i getRelativeParentVector(Block theBlock) {
        final PersistentDataContainer tag = BlockMarker.getTag(theBlock);
        // This should not happen if we previously got a valid id from this block.
        if (tag == null) return null;
        final int[] parentVectorArray = tag.get(mytemsPlugin().namespacedKey("parent"), PersistentDataType.INTEGER_ARRAY);
        if (parentVectorArray == null) return null;
        if (parentVectorArray.length != 3) return null;
        return Vec3i.of(parentVectorArray);
    }

    /**
     * Get the direct parent block of this block.
     *
     * @return null if there is no parent block vector or the
     *   designated parent does not have the correct id.
     */
    public Block getParentBlock(Block theBlock) {
        final Vec3i relativeVector = getRelativeParentVector(theBlock);
        if (relativeVector == null) return null;
        final Vec3i theBlockVector = Vec3i.of(theBlock);
        final Vec3i parentVector = relativeVector.add(theBlockVector);
        final Block parentBlock = parentVector.toBlock(theBlock.getWorld());
        final String parentBlockId = BlockMarker.getId(parentBlock);
        if (!id.equals(parentBlockId)) {
            mytemsPlugin().getLogger().severe("[BlockRegistry] Bad parent block"
                                              + " world:" + theBlock.getWorld().getName()
                                              + " block: " + theBlockVector
                                              + " rel: " + relativeVector
                                              + " parent:" + parentVector
                                              + " id:" + id
                                              + " parent.id: " + parentBlockId);
            return null;
        }
        return parentBlock;
    }

    public Block getRecursiveParentBlock(final Block theBlock) {
        Block currentBlock = theBlock;
        Block result = null;
        final List<Block> blocks = new ArrayList<>();
        blocks.add(currentBlock);
        do {
            currentBlock = getParentBlock(currentBlock);
            if (currentBlock == null) break;
            result = currentBlock;
            if (blocks.contains(currentBlock)) break;
            blocks.add(currentBlock);
        } while (true);
        return result;
    }

    public BlockRegistryEntry addEventHandler(BlockEventHandler handler) {
        eventHandlers.add(handler);
        return this;
    }

    public BlockRegistryEntry setCancelBlockEdits(boolean value) {
        this.cancelBlockEdits = value;
        return this;
    }

    public void removeEventHandler(BlockEventHandler handler) {
        eventHandlers.remove(handler);
    }

    public void applyEventHandlers(Consumer<BlockEventHandler> callback) {
        for (BlockEventHandler handler : eventHandlers) {
            callback.accept(handler);
        }
    }

    public void tick(Block block) {
        if (implementation == null) return;
        implementation.tick(block);
    }
}
