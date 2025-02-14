package com.cavetale.mytems.session;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.block.BlockRegistryEntry;
import lombok.Data;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Data
public final class BlockDamageSession {
    private final Block block;
    private int ticks = 0;

    public void tick(Session session, Player player) {
        ticks += 1;
        final ItemStack item = player.getInventory().getItemInMainHand();
        final Mytems handMytems = Mytems.forItem(item);
        if (handMytems != null) {
            if (!checkOrRemove(session, player)) return;
            handMytems.getMytem().onCustomBlockDamage(player, block, item, ticks);
        }
        final BlockRegistryEntry entry = BlockRegistryEntry.at(block);
        if (entry != null) {
            if (!checkOrRemove(session, player)) return;
            entry.applyEventHandlers(handler -> handler.onCustomBlockDamage(player, block, item, ticks));
        }
    }

    private boolean checkOrRemove(Session session, Player player) {
        final int maxDistance = (int) Math.ceil(player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE).getValue());
        final Block target = player.getTargetBlockExact(maxDistance);
        if (!block.equals(target)) {
            session.setBlockDamage(null);
            return false;
        } else {
            return true;
        }
    }
}
