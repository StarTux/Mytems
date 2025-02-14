package com.cavetale.mytems.block;

import com.cavetale.mytems.Mytems;
import com.cavetale.worldmarker.block.BlockMarker;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockMultiPlaceEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import static com.cavetale.mytems.MytemsPlugin.plugin;

/**
 * Register block types in the world.
 */
public final class BlockRegistry implements Listener {
    private final Map<String, BlockRegistryEntry> blockMap = new HashMap<>();

    public void enable() {
        Bukkit.getPluginManager().registerEvents(this, plugin());
    }

    public static BlockRegistry blockRegistry() {
        return plugin().getBlockRegistry();
    }

    public BlockRegistryEntry register(final Mytems mytems) {
        return register(mytems.getId());
    }

    public BlockRegistryEntry register(final String id) {
        final BlockRegistryEntry entry = new BlockRegistryEntry(id);
        blockMap.put(id, entry);
        return entry;
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.hasBlock()) return;
        final Block block = event.getClickedBlock();
        if (block == null) return;
        handle(block, null, eh -> eh.onPlayerInteract(event, block));
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onBlockBreak(BlockBreakEvent event) {
        final Block block = event.getBlock();
        handle(block, en -> onBlockEdit(event, en), eh -> eh.onBlockBreak(event, block));
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onBlockDamage(BlockDamageEvent event) {
        final Block block = event.getBlock();
        handle(block, null, eh -> eh.onBlockDamage(event, block));
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onBlockPlace(BlockPlaceEvent event) {
        final Block block = event.getBlock();
        handle(block, en -> onBlockEdit(event, en), eh -> eh.onBlockPlace(event, block));
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onBlockMultiPlace(BlockMultiPlaceEvent event) {
        for (BlockState blockState : event.getReplacedBlockStates()) {
            final Block block = blockState.getBlock();
            handle(block, en -> onBlockEdit(event, en), null);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onBlockExplode(BlockExplodeEvent event) {
        for (Block block : event.blockList()) {
            handle(block, en -> onBlockEdit(event, en), eh -> eh.onBlockExplode(event, block));
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onEntityExplode(EntityExplodeEvent event) {
        for (Block block : event.blockList()) {
            handle(block, en -> onBlockEdit(event, en), eh -> eh.onEntityExplode(event, block));
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onBlockPistonExtend(BlockPistonExtendEvent event) {
        final Set<Block> blocks = new LinkedHashSet<>();
        for (Block block : event.getBlocks()) {
            blocks.add(block);
            blocks.add(block.getRelative(event.getDirection()));
        }
        for (Block block : blocks) {
            handle(block, en -> onBlockEdit(event, en), null);
            if (event.isCancelled()) return;
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onBlockPistonRetract(BlockPistonRetractEvent event) {
        final Set<Block> blocks = new LinkedHashSet<>();
        for (Block block : event.getBlocks()) {
            blocks.add(block);
            blocks.add(block.getRelative(event.getDirection()));
        }
        for (Block block : blocks) {
            handle(block, en -> onBlockEdit(event, en), null);
            if (event.isCancelled()) return;
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onEntityChangeBlock(EntityChangeBlockEvent event) {
        handle(event.getBlock(), en -> onBlockEdit(event, en), null);
    }

    public BlockRegistryEntry getEntryAt(Block block) {
        final String id = BlockMarker.getId(block);
        if (id == null) return null;
        return blockMap.get(id);
    }

    private void handle(Block block, Consumer<BlockRegistryEntry> entryCallback, Consumer<BlockEventHandler> eventHandlerCallback) {
        final String id = BlockMarker.getId(block);
        if (id == null) return;
        final BlockRegistryEntry entry = blockMap.get(id);
        if (entry == null) return;
        if (entryCallback != null) {
            entryCallback.accept(entry);
        }
        if (eventHandlerCallback != null) {
            for (BlockEventHandler handler : entry.getEventHandlers()) {
                eventHandlerCallback.accept(handler);
            }
        }
    }

    private static void onBlockEdit(Cancellable event, BlockRegistryEntry entry) {
        if (entry.isCancelBlockEdits()) event.setCancelled(true);
    }
}
