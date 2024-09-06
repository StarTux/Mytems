package com.cavetale.mytems.item.finder;

import com.cavetale.core.event.structure.PlayerDiscoverStructureEvent;
import com.cavetale.core.structure.Structure;
import com.cavetale.mytems.Mytems;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.MytemsPlugin.plugin;

public final class FinderListener implements Listener {
    private static FinderListener instance;

    protected static void enableOnce() {
        if (instance != null) return;
        instance = new FinderListener();
        instance.enable();
    }

    protected void enable() {
        Bukkit.getPluginManager().registerEvents(this, plugin());
    }

    /**
     * When a player discovers a structure. try to xp to any Finder
     * item in their inventory, preferrably in their hands.
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onPlayerDiscoverStructure(PlayerDiscoverStructureEvent event) {
        final Structure structure = event.getStructure();
        final FoundType foundType = FoundType.of(structure.getKey());
        if (foundType == null || foundType.isDisabled()) {
            return;
        }
        final Player player = event.getPlayer();
        if (giveFinderXp(player, structure, foundType, player.getInventory().getItemInMainHand())) {
            return;
        }
        if (giveFinderXp(player, structure, foundType, player.getInventory().getItemInOffHand())) {
            return;
        }
        for (int i = 0; i < player.getInventory().getSize(); i += 1) {
            if (giveFinderXp(player, structure, foundType, player.getInventory().getItem(i))) {
                return;
            }
        }
    }

    private boolean giveFinderXp(Player player, Structure structure, FoundType foundType, ItemStack item) {
        final Mytems mytems = Mytems.forItem(item);
        if (mytems == null) return false;
        if (!(mytems.getMytem() instanceof Finder finder)) return false;
        return finder.giveFinderXp(player, structure, foundType, item);
    }
}
