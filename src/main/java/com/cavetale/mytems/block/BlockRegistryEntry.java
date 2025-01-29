package com.cavetale.mytems.block;

import com.cavetale.worldmarker.block.BlockMarker;
import java.util.ArrayList;
import java.util.List;
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

    public void set(Block block) {
        BlockMarker.setId(block, id);
    }

    public void unset(Block block) {
        BlockMarker.resetId(block);
    }

    public void addEventHandler(BlockEventHandler handler) {
        eventHandlers.add(handler);
    }

    public void removeEventHandler(BlockEventHandler handler) {
        eventHandlers.remove(handler);
    }
}
