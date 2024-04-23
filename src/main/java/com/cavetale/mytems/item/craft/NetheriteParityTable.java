package com.cavetale.mytems.item.craft;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerChangeBlockEvent;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Blocks;
import com.cavetale.worldmarker.block.BlockMarker;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.MytemsPlugin.plugin;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.text;

@RequiredArgsConstructor @Getter
public final class NetheriteParityTable implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        displayName = text("Netherite Parity Table");
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                tooltip(meta, List.of(displayName));
                key.markItemMeta(meta);
            });
        new NetheriteParityTableBlock(key).register();
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (event.isCancelled()) return;
        if (!event.hasBlock()) return;
        event.setCancelled(true);
        final Block block = event.getClickedBlock().getRelative(event.getBlockFace());
        Bukkit.getScheduler().runTask(plugin(), () -> {
                if (!item.equals(player.getInventory().getItem(event.getHand()))) return;
                if (!block.getType().isAir()) return;
                if (BlockMarker.getId(block) != null) return;
                if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) {
                    return;
                }
                if (!new PlayerChangeBlockEvent(player, block, Material.BARRIER.createBlockData(), item).callEvent()) {
                    return;
                }
                block.setBlockData(Material.BARRIER.createBlockData());
                BlockMarker.setId(block, key.id);
                Blocks.place(key, block, e -> { });
                block.getWorld().playSound(block.getLocation(), Sound.BLOCK_NETHERITE_BLOCK_PLACE, 1.0f, 0.5f);
                if (player.getGameMode() != GameMode.CREATIVE) {
                    item.subtract(1);
                }
            });
    }
}
