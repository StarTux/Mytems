package com.cavetale.mytems.block;

import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.session.BlockDamageSession;
import com.cavetale.mytems.session.Session;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageAbortEvent;
import org.bukkit.event.block.BlockDamageEvent;

@RequiredArgsConstructor
public final class BlockDamageListener implements Listener {
    private final MytemsPlugin plugin;

    public void enable() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
    private void onBlockDamage(BlockDamageEvent event) {
        final Session session = Session.of(event.getPlayer());
        session.setBlockDamage(null);
        if (event.isCancelled()) return;
        session.setBlockDamage(new BlockDamageSession(session, event.getBlock()));
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
    private void onBlockDamageAbort(BlockDamageAbortEvent event) {
        final Session session = Session.of(event.getPlayer());
        if (session.getBlockDamage() != null) {
            session.getBlockDamage().cancel(event.getPlayer());
        }
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
    private void onBlockBreak(BlockBreakEvent event) {
        final Session session = Session.of(event.getPlayer());
        if (session.getBlockDamage() != null) {
            session.getBlockDamage().cancel(event.getPlayer());
        }
    }
}
