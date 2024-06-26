package com.cavetale.mytems.gear;

import com.cavetale.mytems.Mytem;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

/**
 * A special category of mytems which is capable of providing set
 * bonuses and based on that, produce a consistent tooltip.
 */
public interface GearItem extends Mytem {
    default ItemSet getItemSet() {
       return null;
    }

    default List<Component> getBaseLore() {
        return List.of();
    }

    /**
     * Create a tooltip for the item with the given equipment, which
     * may be null.
     */
    default List<Component> createTooltip(Equipment equipment, Equipped equipped) {
        List<Component> tooltip = new ArrayList<>();
        tooltip.add(getDisplayName());
        tooltip.addAll(getBaseLore());
        ItemSet itemSet = getItemSet();
        if (itemSet != null) {
            tooltip.add(Component.empty());
            tooltip.addAll(itemSet.createTooltip(equipment));
        }
        return tooltip;
    }

    default List<Component> createTooltip() {
        return createTooltip(null, null);
    }

    /**
     * Helmets of gear items are oftentimes player heads which can be
     * placed.
     * Let's cancel that.
     */
    @Override
    default void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) {
        if (item.getType().isBlock()) {
            event.setCancelled(true);
        }
    }

    @Override
    default int getMaxStackSize() {
        return 1;
    }
}
