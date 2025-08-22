package com.cavetale.mytems.block;

import java.time.Instant;
import org.bukkit.block.Block;

public interface BlockImplementation {
    void randomTick(Block block, Instant now);
}
