package com.cavetale.mytems.item.bingo;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerChangeBlockEvent;
import com.cavetale.core.util.Json;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import static java.util.Objects.requireNonNull;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public final class BingoBukkit implements Mytem {
    private final Mytems key;
    private final BingoBukkitType type;
    private ItemStack prototype;
    protected List<Component> baseText;

    public BingoBukkit(final Mytems key) {
        this.key = key;
        this.type = requireNonNull(BingoBukkitType.of(key));
        type.instance = this;
    }

    @Override
    public void enable() {
        prototype = new ItemStack(key.material);
        baseText = List.of(type.displayName,
                           text("This bucket can hold", GRAY),
                           text("anything, but mostly", GRAY),
                           text("water.", GRAY),
                           textOfChildren(Mytems.MOUSE_RIGHT, text(" Fill or place", GRAY)),
                           textOfChildren(Mytems.SHIFT_KEY, Mytems.MOUSE_RIGHT, text(" Place", GRAY)));
        prototype.editMeta(meta -> {
                Items.text(meta, baseText);
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                key.markItemMeta(meta);
            });
    }

    @Override
    public Component getDisplayName() {
        return type.displayName;
    }

    @Override
    public ItemStack createItemStack() {
        ItemStack result = prototype.clone();
        BingoBukkitTag tag = new BingoBukkitTag(type);
        tag.store(result);
        return result;
    }

    @Override
    public void onPlayerLeftClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setUseInteractedBlock(Event.Result.DENY);
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setUseInteractedBlock(Event.Result.DENY);
        RayTraceResult trace = player.rayTraceBlocks(4.0, FluidCollisionMode.SOURCE_ONLY);
        Block block = trace.getHitBlock();
        if (block == null) return;
        BlockData blockData = block.getBlockData();
        boolean sneak = player.isSneaking();
        if (!sneak && blockData.getMaterial() == Material.WATER && blockData instanceof Levelled levelled && levelled.getLevel() == 0) {
            // Fill
            BingoBukkitTag tag = new BingoBukkitTag(type);
            tag.load(item);
            if (tag.water >= type.capacity) return;
            if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return;
            BlockData newBlockData = Material.AIR.createBlockData();
            if (!new PlayerChangeBlockEvent(player, block, newBlockData).callEvent()) return;
            block.setBlockData(newBlockData);
            tag.water += 1;
            tag.store(item);
            fillEffect(block);
        } else if (!sneak && blockData instanceof Waterlogged waterlogged) {
            if (waterlogged.isWaterlogged()) {
                // Fill
                BingoBukkitTag tag = new BingoBukkitTag(type);
                tag.load(item);
                if (tag.water >= type.capacity) return;
                if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return;
                waterlogged.setWaterlogged(false);
                if (!new PlayerChangeBlockEvent(player, block, waterlogged).callEvent()) return;
                block.setBlockData(blockData);
                tag.water += 1;
                tag.store(item);
                fillEffect(block);
            } else {
                // Place
                BingoBukkitTag tag = new BingoBukkitTag(type);
                tag.load(item);
                if (tag.water <= 0) return;
                if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return;
                waterlogged.setWaterlogged(true);
                if (!new PlayerChangeBlockEvent(player, block, waterlogged).callEvent()) return;
                block.setBlockData(blockData);
                tag.water -= 1;
                tag.store(item);
                placeEffect(block);
            }
        } else if (trace.getHitBlockFace() != null) {
            block = block.getRelative(trace.getHitBlockFace());
            blockData = block.getBlockData();
            if (block.isEmpty() || (blockData.getMaterial() == Material.WATER && blockData instanceof Levelled levelled && levelled.getLevel() != 0)) {
                // Place
                BingoBukkitTag tag = new BingoBukkitTag(type);
                tag.load(item);
                if (tag.water <= 0) return;
                if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return;
                BlockData newBlockData = Material.WATER.createBlockData();
                if (!new PlayerChangeBlockEvent(player, block, newBlockData).callEvent()) return;
                block.setBlockData(newBlockData);
                tag.water -= 1;
                tag.store(item);
                placeEffect(block);
            }
        }
    }

    private void fillEffect(Block block) {
        Location location = block.getLocation().add(0.5, 0.5, 0.5);
        location.getWorld().playSound(location, Sound.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
        location.getWorld().spawnParticle(Particle.WATER_SPLASH, location, 32, 0.25, 0.25, 0.25, 1.0);
    }

    private void placeEffect(Block block) {
        Location location = block.getLocation().add(0.5, 0.5, 0.5);
        location.getWorld().playSound(location, Sound.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
        location.getWorld().spawnParticle(Particle.WATER_SPLASH, location, 32, 0.25, 0.25, 0.25, 1.0);
    }

    @Override
    public BingoBukkitTag serializeTag(ItemStack itemStack) {
        BingoBukkitTag tag = new BingoBukkitTag();
        tag.type = this.type;
        tag.load(itemStack);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        ItemStack result = prototype.clone();
        BingoBukkitTag tag = Json.deserialize(serialized, BingoBukkitTag.class);
        if (tag != null) {
            tag.type = this.type;
            tag.store(result);
        }
        return result;
    }
}
