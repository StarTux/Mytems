package com.cavetale.mytems.util;

import com.winthier.exploits.Exploits;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;

public final class PlayerPlacedBlocks {
    private PlayerPlacedBlocks() { }

    public static boolean isPlayerPlaced(Block block) {
        if (!Bukkit.getPluginManager().isPluginEnabled("Exploits")) return false;
        return Exploits.isPlayerPlaced(block);
    }
}
