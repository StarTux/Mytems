package com.cavetale.mytems.item.craft;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerChangeBlockEvent;
import com.cavetale.core.event.item.PlayerReceiveItemsEvent;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.block.BlockEventHandler;
import com.cavetale.mytems.custom.NetheriteParityGui;
import com.cavetale.worldmarker.block.BlockMarker;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import static com.cavetale.mytems.MytemsPlugin.plugin;

@RequiredArgsConstructor
public final class NetheriteParityBlockEventHandler implements BlockEventHandler {
    @Override
    public void onBlockDamage(BlockDamageEvent event, Block block) {
        if (event.isCancelled()) return;
        event.setCancelled(true);
        Bukkit.getScheduler().runTask(plugin(), () -> breakBlock(event.getPlayer(), block));
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, Block block) {
        if (event.isCancelled()) return;
        event.setCancelled(true);
        Bukkit.getScheduler().runTask(plugin(), () -> breakBlock(event.getPlayer(), block));
    }

    private void breakBlock(Player player, Block block) {
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) {
            return;
        }
        if (!new PlayerChangeBlockEvent(player, block, Material.AIR.createBlockData()).callEvent()) {
            return;
        }
        BlockMarker.resetId(block);
        block.setBlockData(Material.AIR.createBlockData());
        if (player.getGameMode() != GameMode.CREATIVE) {
            PlayerReceiveItemsEvent prie = new PlayerReceiveItemsEvent(player, List.of(Mytems.NETHERITE_PARITY_TABLE.createItemStack()));
            prie.giveItems();
            prie.callEvent();
            prie.dropItems();
        }
        for (ItemDisplay id : block.getLocation().add(0.5, 0.5, 0.5).getNearbyEntitiesByType(ItemDisplay.class, 0.5)) {
            id.remove();
        }
        block.getWorld().playSound(block.getLocation(), Sound.BLOCK_NETHERITE_BLOCK_BREAK, 1.0f, 0.5f);
        block.getWorld().spawnParticle(Particle.BLOCK, block.getLocation().add(0.5, 0.5, 0.5), 32, 0.25, 0.25, 0.25, 0.0,
                                       Material.NETHERITE_BLOCK.createBlockData());
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event, Block block) {
        if (event.useInteractedBlock() == Event.Result.DENY) {
            return;
        }
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getPlayer().isSneaking()) return;
        new NetheriteParityGui(event.getPlayer()).open();
        event.setCancelled(true);
        block.getWorld().playSound(block.getLocation(), Sound.BLOCK_NETHERITE_BLOCK_STEP, 1.0f, 0.5f);
    }
}
