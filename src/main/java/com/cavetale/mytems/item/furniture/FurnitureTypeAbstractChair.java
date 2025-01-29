package com.cavetale.mytems.item.furniture;

import com.cavetale.mytems.block.chair.Chairs;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;

/**
 * Superclass of (hopefully) all chairs.
 * Want to buy entity component system.
 */
public abstract class FurnitureTypeAbstractChair implements FurnitureTypeImpl {
    /**
     * Attempt to sit on the chair.
     * This involves getting the item display orientation.
     */
    @Override
    public void onPlayerRightClick(Player player, Block clickedBlock, Block originBlock) {
        if (player.isSneaking()) return;
        if (player.getGameMode() == GameMode.SPECTATOR) return;
        if (player.isInsideVehicle()) return;
        final Location originCenter = originBlock.getLocation().add(0.5, 0.5, 0.5);
        final List<ItemDisplay> itemDisplays = new ArrayList<>();
        for (ItemDisplay display : originCenter.getWorld().getNearbyEntitiesByType(ItemDisplay.class, originCenter, 0.5)) {
            itemDisplays.add(display);
        }
        if (itemDisplays.size() != 1) return;
        final ItemDisplay itemDisplay = itemDisplays.get(0);
        final Location chairLocation = clickedBlock.getLocation().add(0.5, 0.5, 0.5);
        chairLocation.setYaw(itemDisplays.get(0).getLocation().getYaw());
        if (Chairs.chairs().sitDown(player, clickedBlock, chairLocation) != null) {
            chairLocation.getWorld().playSound(chairLocation, Sound.BLOCK_WOOD_PLACE, 1.0f, 1.0f);
        }
    }

    /**
     * When a chair is broken, we must dismount all its passengers.
     */
    @Override
    public void onBreakFurniture(Player player, Block originBlock, List<Block> brokenBlocks) {
        for (Block block : brokenBlocks) {
            Chairs.chairs().disableSeat(block);
        }
    }
}
