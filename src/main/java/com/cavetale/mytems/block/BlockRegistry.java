package com.cavetale.mytems.block;

import com.cavetale.worldmarker.block.BlockMarker;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import static com.cavetale.mytems.MytemsPlugin.plugin;

public final class BlockRegistry implements Listener {
    private final Map<String, MytemBlock> blockMap = new HashMap<>();

    public void enable() {
        Bukkit.getPluginManager().registerEvents(this, plugin());
    }

    public static BlockRegistry blockRegistry() {
        return plugin().getBlockRegistry();
    }

    public void register(MytemBlock mb) {
        blockMap.put(mb.getKey(), mb);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.hasBlock()) return;
        final Block block = event.getClickedBlock();
        if (block == null) return;
        handle(block, mb -> mb.onPlayerInteract(event, block));
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onBlockBreak(BlockBreakEvent event) {
        final Block block = event.getBlock();
        handle(block, mb -> mb.onBlockBreak(event, block));
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onBlockDamage(BlockDamageEvent event) {
        final Block block = event.getBlock();
        handle(block, mb -> mb.onBlockDamage(event, block));
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onBlockPlace(BlockPlaceEvent event) {
        final Block block = event.getBlock();
        handle(block, mb -> mb.onBlockPlace(event, block));
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onBlockExplode(BlockExplodeEvent event) {
        for (Block block : event.blockList()) {
            handle(block, mb -> mb.onBlockExplode(event, block));
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onEntityExplode(EntityExplodeEvent event) {
        for (Block block : event.blockList()) {
            handle(block, mb -> mb.onEntityExplode(event, block));
        }
    }

    private void handle(Block block, Consumer<MytemBlock> callback) {
        String id = BlockMarker.getId(block);
        if (id == null) return;
        MytemBlock mytemBlock = blockMap.get(id);
        if (mytemBlock == null) return;
        callback.accept(mytemBlock);
    }
}
