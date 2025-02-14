package com.cavetale.mytems.block;

import com.cavetale.worldmarker.block.BlockMarker;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import lombok.Getter;
import org.bukkit.block.Block;

@Getter
public final class BlockRegistryEntry {
    private final String id;
    private final List<BlockEventHandler> eventHandlers = new ArrayList<>();
    private boolean cancelBlockEdits;

    public BlockRegistryEntry(final String id) {
        this.id = id;
    }

    public static BlockRegistryEntry at(Block block) {
        return BlockRegistry.blockRegistry().getEntryAt(block);
    }

    public void set(Block block) {
        BlockMarker.setId(block, id);
    }

    public void unset(Block block) {
        BlockMarker.resetId(block);
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
}
