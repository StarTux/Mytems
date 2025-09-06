package com.cavetale.mytems.session;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.block.BlockRegistryEntry;
import com.cavetale.mytems.event.block.PlayerDamageBlockTickEvent;
import java.time.Instant;
import lombok.Data;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Data
public final class BlockDamageSession {
    private final Session session;
    private final Block block;
    private final Instant startTime = Instant.now();
    private int ticks = 0;

    /**
     * On every tick we check for validity and call the event.
     * Validity means that the player is still looking at the block
     * and holding down the mining button.  The is controlled by
     * BlockDamageListener.
     *
     * Should the block damage be invalid, we call the event once more
     * in a cancelled state.
     */
    public void tick(Player player) {
        ticks += 1;
        if (!checkOrRemove(player)) {
            return;
        }
        final PlayerDamageBlockTickEvent event = new PlayerDamageBlockTickEvent(player, block, ticks, startTime);
        if (!event.callEvent()) {
            session.setBlockDamage(null);
            return;
        }
        final ItemStack item = player.getInventory().getItemInMainHand();
        final Mytems handMytems = Mytems.forItem(item);
        if (handMytems != null) {
            handMytems.getMytem().onCustomBlockDamage(player, block, item, ticks);
        }
        final BlockRegistryEntry entry = BlockRegistryEntry.at(block);
        if (entry != null) {
            entry.applyEventHandlers(handler -> handler.onCustomBlockDamage(player, block, item, ticks));
        }
    }

    /**
     * Call once more in cancelled state, then remove yourself from
     * the session.
     */
    public void cancel(Player player) {
        session.setBlockDamage(null);
        final PlayerDamageBlockTickEvent event = new PlayerDamageBlockTickEvent(player, block, ticks, startTime);
        event.setCancelled(true);
        event.callEvent();
    }

    private boolean checkOrRemove(Player player) {
        final int maxDistance = (int) Math.ceil(player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE).getValue());
        final Block target = player.getTargetBlockExact(maxDistance);
        if (!block.equals(target)) {
            cancel(player);
            return false;
        } else {
            return true;
        }
    }
}
