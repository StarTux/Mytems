package com.cavetale.mytems.item.furniture;

import com.cavetale.mytems.block.BlockEventHandler;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * One of these exists for every instance of Furniture.
 */
@RequiredArgsConstructor
public final class FurnitureBlockEventHandler implements BlockEventHandler {
    private final Furniture furniture;

    @Override
    public void onCustomBlockDamage(final Player player, final Block block, final ItemStack item, final int ticks) {
        if (ticks < 40) return;
        if (player.getGameMode() == GameMode.SPECTATOR) return;
        final Block originBlock = furniture.getOriginBlock(block, true);
        if (originBlock == null) return;
        if (furniture.breakFurniture(player, originBlock)) {
            originBlock.getWorld().playSound(originBlock.getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_WOOD_BREAK, 1.0f, 0.5f);
        }
    }

    /**
     * This exists for creative mode.
     */
    @Override
    public void onBlockBreak(BlockBreakEvent event, Block block) {
        event.setCancelled(true);
        final Player player = event.getPlayer();
        final Block originBlock = furniture.getOriginBlock(block, true);
        if (originBlock == null) return;
        if (furniture.breakFurniture(player, originBlock)) {
            originBlock.getWorld().playSound(originBlock.getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_WOOD_BREAK, 1.0f, 0.5f);
        }
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event, Block clickedBlock) {
        final boolean left;
        final boolean right;
        switch (event.getAction()) {
        case LEFT_CLICK_BLOCK: return;
        case RIGHT_CLICK_BLOCK: break; // OK
        default: return;
        }
        if (event.getHand() != EquipmentSlot.HAND) return;
        final Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.SPECTATOR) return;
        final Block originBlock = furniture.getOriginBlock(clickedBlock, true);
        if (originBlock == null) {
            return;
        }
        event.setCancelled(true);
        furniture.getFurnitureType().getImplementation().onPlayerRightClick(player, clickedBlock, originBlock);
    }
}
