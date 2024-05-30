package com.cavetale.mytems.item;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Text;
import com.cavetale.worldmarker.block.BlockMarker;
import com.cavetale.worldmarker.entity.EntityMarker;
import java.util.Iterator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

@RequiredArgsConstructor
public final class Enderball implements Mytem, Listener {
    @Getter private final Mytems key;
    private ItemStack prototype;
    @Getter private Component displayName;

    @Override
    public void enable() {
        String json = "[{\"text\":\"E\",\"color\":\"#4b0082\"}"
            + ",{\"text\":\"n\",\"color\":\"#620092\"}"
            + ",{\"text\":\"d\",\"color\":\"#7800a1\"}"
            + ",{\"text\":\"e\",\"color\":\"#8f00b1\"}"
            + ",{\"text\":\"r\",\"color\":\"#a500c1\"}"
            + ",{\"text\":\"b\",\"color\":\"#bc00d0\"}"
            + ",{\"text\":\"a\",\"color\":\"#d200e0\"}"
            + ",{\"text\":\"l\",\"color\":\"#e900ef\"}"
            + ",{\"text\":\"l\",\"color\":\"#ff00ff\"}]";
        displayName = Component.empty().decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD)
            .append(GsonComponentSerializer.gson().deserialize(json));
        prototype = new ItemStack(Material.DRAGON_EGG);
        ItemMeta meta = prototype.getItemMeta();
        meta.displayName(displayName);
        meta.lore(Text.wrapLore("Official Enderball\u2122 event ball."
                                + " Earned by event participation."
                                + " Signed by StarTux in black and purple ink."
                                + " Place it on the ground and kick it!"));
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        meta.addItemFlags(ItemFlag.values());
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
        Bukkit.getPluginManager().registerEvents(this, MytemsPlugin.getInstance());
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (!(event.getEntity() instanceof FallingBlock)) return;
        FallingBlock fallingBlock = (FallingBlock) event.getEntity();
        if (fallingBlock.getBlockData().getMaterial() != Material.DRAGON_EGG) return;
        Block block = event.getBlock();
        if (event.getTo().isEmpty()) {
            if (!BlockMarker.hasId(block, key.id)) return;
            BlockMarker.resetId(block);
            EntityMarker.setId(fallingBlock, key.id);
        } else {
            if (!EntityMarker.hasId(fallingBlock, key.id)) return;
            EntityMarker.resetId(fallingBlock);
            BlockMarker.setId(block, key.id);
        }
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
    void onEntityDropItem(EntityDropItemEvent event) {
        if (!(event.getEntity() instanceof FallingBlock)) return;
        FallingBlock fallingBlock = (FallingBlock) event.getEntity();
        if (fallingBlock.getBlockData().getMaterial() != Material.DRAGON_EGG) return;
        if (!EntityMarker.hasId(fallingBlock, key.id)) return;
        event.getItemDrop().setItemStack(createItemStack());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.hasBlock()) return;
        final Player player = event.getPlayer();
        final double height;
        switch (event.getAction()) {
        case LEFT_CLICK_BLOCK:
            height = 1.0;
            break;
        case RIGHT_CLICK_BLOCK:
            height = 0.5;
            break;
        default:
            return;
        }
        Block block = event.getClickedBlock();
        if (block.getType() != Material.DRAGON_EGG) return;
        if (!BlockMarker.hasId(block, key.id)) return;
        event.setCancelled(true);
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return;
        boolean strong = player.isSprinting();
        double strength = strong ? 1.0 : 0.7;
        Location ballLocation = block.getLocation().add(0.5, 0.0, 0.5);
        Vector vec = ballLocation.toVector().subtract(player.getLocation().toVector());
        vec.setY(0.0).normalize().setY(height).multiply(strength);
        final FallingBlock fallingBlock = block.getWorld().spawn(ballLocation, FallingBlock.class, fb -> {
                fb.setBlockData(Material.DRAGON_EGG.createBlockData());
            });
        if (fallingBlock == null) return;
        EntityMarker.setId(fallingBlock, key.id);
        block.setType(Material.AIR, false);
        BlockMarker.resetId(block);
        fallingBlock.setDropItem(true);
        fallingBlock.setVelocity(vec);
        if (strong) {
            ballLocation.getWorld().playSound(ballLocation, Sound.BLOCK_ENDER_CHEST_OPEN, SoundCategory.MASTER, 1.0f, 1.66f);
        } else {
            ballLocation.getWorld().playSound(ballLocation, Sound.BLOCK_ENDER_CHEST_OPEN, SoundCategory.MASTER, 1.0f, 2.0f);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        Block block = event.getBlock();
        if (block.getType() != Material.DRAGON_EGG) return;
        if (!BlockMarker.hasId(block, key.id)) return;
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onBlockDamage(BlockDamageEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        Block block = event.getBlock();
        if (block.getType() != Material.DRAGON_EGG) return;
        if (!BlockMarker.hasId(block, key.id)) return;
        event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onBlockFromTo(BlockFromToEvent event) {
        Block block = event.getBlock();
        if (block.getType() != Material.DRAGON_EGG) return;
        if (!BlockMarker.hasId(block, key.id)) return;
        event.setCancelled(true);
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack itemStack) {
        Block block = event.getBlock();
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return;
        BlockMarker.setId(block, key.id);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        for (Block block : event.getBlocks()) {
            if (BlockMarker.hasId(block, key.id)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        for (Block block : event.getBlocks()) {
            if (BlockMarker.hasId(block, key.id)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onBlockExplode(BlockExplodeEvent event) {
        for (Iterator<Block> iter = event.blockList().iterator(); iter.hasNext();) {
            if (BlockMarker.hasId(iter.next(), key.id)) {
                iter.remove();
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onEntityExplode(EntityExplodeEvent event) {
        for (Iterator<Block> iter = event.blockList().iterator(); iter.hasNext();) {
            if (BlockMarker.hasId(iter.next(), key.id)) {
                iter.remove();
            }
        }
    }
}
