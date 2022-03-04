package com.cavetale.mytems.item.treechopper;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

public final class TreeChopListener implements Listener {
    protected static Location target = null;

    @EventHandler(ignoreCancelled = true)
    protected void onItemSpawn(ItemSpawnEvent event) {
        if (target == null) return;
        event.getEntity().teleport(target);
        event.getEntity().setPickupDelay(0);
    }
}
