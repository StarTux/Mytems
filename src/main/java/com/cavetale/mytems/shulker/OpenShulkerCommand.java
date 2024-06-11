package com.cavetale.mytems.shulker;

import com.cavetale.core.command.AbstractCommand;
import com.cavetale.core.command.CommandWarn;
import com.cavetale.mytems.MytemsPlugin;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class OpenShulkerCommand extends AbstractCommand<MytemsPlugin> {
    public OpenShulkerCommand(final MytemsPlugin plugin) {
        super(plugin, "openshulker");
    }

    @Override
    protected void onEnable() {
        rootNode.denyTabCompletion()
            .description("Open shulker box in hand")
            .playerCaller(this::openShulker);
    }

    private void openShulker(Player player) {
        final int heldItemSlot = player.getInventory().getHeldItemSlot();
        final ItemStack heldItemStack = player.getInventory().getItem(heldItemSlot);
        if (heldItemStack == null || !Tag.SHULKER_BOXES.isTagged(heldItemStack.getType())) {
            throw new CommandWarn("There is no shulker box in your hand");
        }
        plugin.getShulkerBoxListener().openShulkerBox(player, heldItemSlot, heldItemStack);
    }
}
