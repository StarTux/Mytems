package com.cavetale.mytems.item.bingo;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerChangeBlockEvent;
import com.cavetale.core.util.Json;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World.Environment;
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
import static com.cavetale.mytems.util.Items.tooltip;
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
                tooltip(meta, baseText);
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
        BingoBukkitTag tag = new BingoBukkitTag();
        tag.store(key, result);
        return result;
    }

    /**
     * Sneaking indicates that the player wants to interact with the
     * block that is adjacent to the clicked block, in direction of
     * the clicked face.
     * So the many !sneak checks are all there to interact with the
     * target block directly.
     */
    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (player.getGameMode() == GameMode.SPECTATOR) return;
        event.setUseInteractedBlock(Event.Result.DENY);
        if (player.getWorld().getEnvironment() == Environment.NETHER) return;
        final RayTraceResult trace = player.rayTraceBlocks(5.0, FluidCollisionMode.SOURCE_ONLY);
        if (trace == null) return;
        final Block block = trace.getHitBlock();
        if (block == null) return;
        final BlockData blockData = block.getBlockData();
        final boolean sneak = player.isSneaking();
        final BingoBukkitTag tag = new BingoBukkitTag();
        tag.load(key, item);
        if (blockData.getMaterial() == Material.WATER && blockData instanceof Levelled levelled && levelled.getLevel() == 0) {
            // Clicking water withouer sneaking
            // Fill from water source
            if (tag.water >= type.capacity) return;
            if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return;
            final BlockData newBlockData = Material.AIR.createBlockData();
            if (!new PlayerChangeBlockEvent(player, block, newBlockData).callEvent()) return;
            block.setBlockData(newBlockData);
            tag.addWater();
            tag.store(key, item);
            fillEffect(block);
        } else if (!sneak && blockData instanceof Waterlogged waterlogged) {
            if (waterlogged.isWaterlogged()) {
                // Fill from waterlogged
                if (tag.water >= type.capacity) return;
                if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return;
                waterlogged.setWaterlogged(false);
                if (!new PlayerChangeBlockEvent(player, block, waterlogged).callEvent()) return;
                block.setBlockData(blockData);
                tag.addWater();
                tag.store(key, item);
                fillEffect(block);
            } else {
                // Place on waterlogged
                if (tag.water <= 0) return;
                if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return;
                waterlogged.setWaterlogged(true);
                if (!new PlayerChangeBlockEvent(player, block, waterlogged).callEvent()) return;
                block.setBlockData(blockData);
                tag.subtractWater();
                tag.store(key, item);
                placeEffect(block);
            }
        } else if (!sneak && blockData.getMaterial() == Material.CAULDRON) {
            // Fill cauldron
            if (tag.water <= 0) return;
            if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return;
            final Levelled cauldron = (Levelled) Material.WATER_CAULDRON.createBlockData();
            cauldron.setLevel(cauldron.getMaximumLevel());
            if (!new PlayerChangeBlockEvent(player, block, cauldron).callEvent()) return;
            block.setBlockData(cauldron);
            tag.subtractWater();
            tag.store(key, item);
            placeEffect(block);
        } else if (!sneak && blockData.getMaterial() == Material.WATER_CAULDRON && blockData instanceof Levelled cauldron) {
            if (cauldron.getLevel() == cauldron.getMaximumLevel()) {
                // Cauldron is full. Empty it.
                if (tag.water >= type.capacity) return;
                if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return;
                final BlockData emptyCauldron = Material.CAULDRON.createBlockData();
                if (!new PlayerChangeBlockEvent(player, block, emptyCauldron).callEvent()) return;
                block.setBlockData(emptyCauldron);
                tag.addWater();
                tag.store(key, item);
                fillEffect(block);
            } else {
                // Cauldron not quite full. Fill it.
                if (tag.water <= 0) return;
                if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return;
                cauldron.setLevel(cauldron.getMaximumLevel());
                if (!new PlayerChangeBlockEvent(player, block, cauldron).callEvent()) return;
                block.setBlockData(cauldron);
                tag.subtractWater();
                tag.store(key, item);
                placeEffect(block);
            }
        } else if (trace.getHitBlockFace() != null) {
            // Possibly sneaking.
            // We place water in the adjacent block.
            final Block nbor = block.getRelative(trace.getHitBlockFace());
            final BlockData nborData = nbor.getBlockData();
            if (nbor.isEmpty() || (nborData.getMaterial() == Material.WATER && nborData instanceof Levelled levelled && levelled.getLevel() != 0)) {
                // Place
                tag.load(key, item);
                if (tag.water <= 0) return;
                if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, nbor)) return;
                BlockData newBlockData = Material.WATER.createBlockData();
                if (!new PlayerChangeBlockEvent(player, nbor, newBlockData).callEvent()) return;
                nbor.setBlockData(newBlockData);
                tag.subtractWater();
                tag.store(key, item);
                placeEffect(nbor);
            }
        }
    }

    private void fillEffect(Block block) {
        Location location = block.getLocation().add(0.5, 0.5, 0.5);
        location.getWorld().playSound(location, Sound.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
        location.getWorld().spawnParticle(Particle.SPLASH, location, 32, 0.25, 0.25, 0.25, 1.0);
    }

    private void placeEffect(Block block) {
        Location location = block.getLocation().add(0.5, 0.5, 0.5);
        location.getWorld().playSound(location, Sound.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0f, 1.0f);
        location.getWorld().spawnParticle(Particle.SPLASH, location, 32, 0.25, 0.25, 0.25, 1.0);
    }

    @Override
    public BingoBukkitTag serializeTag(ItemStack itemStack) {
        BingoBukkitTag tag = new BingoBukkitTag();
        tag.load(key, itemStack);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        ItemStack result = prototype.clone();
        BingoBukkitTag tag = Json.deserialize(serialized, BingoBukkitTag.class);
        if (tag != null) {
            tag.store(key, result);
        }
        return result;
    }
}
