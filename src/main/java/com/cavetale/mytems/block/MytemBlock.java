package com.cavetale.mytems.block;

import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface MytemBlock {
    String getKey();

    default void register() {
        BlockRegistry.blockRegistry().register(this);
    }

    default void onPlayerInteract(PlayerInteractEvent event, Block block) { }
    default void onBlockBreak(BlockBreakEvent event, Block block) { }
    default void onBlockDamage(BlockDamageEvent event, Block block) { }
    default void onBlockPlace(BlockPlaceEvent event, Block block) { }
    default void onBlockExplode(BlockExplodeEvent event, Block block) { }
    default void onEntityExplode(EntityExplodeEvent event, Block block) { }
}
