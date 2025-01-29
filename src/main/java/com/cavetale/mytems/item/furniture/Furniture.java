package com.cavetale.mytems.item.furniture;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerChangeBlockEvent;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.block.BlockEventHandler;
import com.cavetale.worldmarker.block.BlockMarker;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import static com.cavetale.mytems.MytemsPlugin.mytemsPlugin;
import static com.cavetale.mytems.block.BlockRegistry.blockRegistry;
import static com.cavetale.mytems.util.Items.tooltip;
import static java.util.Objects.requireNonNull;

/**
 * This class represents a furniture, both the item and the placed block.
 *
 * Furniture will turn into one or more blocks (usually barriers) when
 * placed, with one or more Item Displays.
 * This is remembered via BlockMarker which stores the Mytems id in
 * each block, together with the origin block, provided more than one
 * block are placed.
 *
 * It can be broken with a single left click which results in the
 * removal of all furniture blocks, which are determined via flood
 * fill.  It also returns a single furniture item.
 */
@Getter
public final class Furniture implements Mytem, BlockEventHandler {
    private final Mytems key;
    private final FurnitureType furnitureType;
    private ItemStack prototype;

    public Furniture(final Mytems key) {
        this.key = key;
        this.furnitureType = requireNonNull(FurnitureType.of(key), "mytems:" + key);
    }

    @Override
    public void enable() {
        this.prototype = new ItemStack(key.material);
        furnitureType.enable();
        prototype.editMeta(meta -> {
                key.markItemMeta(meta);
                tooltip(meta, List.of(getDisplayName()));
            });
        blockRegistry().register(key)
            .addEventHandler(this);
    }

    @Override
    public Component getDisplayName() {
        return furnitureType.getDisplayName();
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (event.useItemInHand() == Event.Result.DENY) {
            return;
        }
        if (!event.hasBlock()) return;
        event.setCancelled(true);
        final Block block = event.getClickedBlock().getRelative(event.getBlockFace());
        Bukkit.getScheduler().runTask(mytemsPlugin(), () -> {
                final ItemStack item2 = player.getInventory().getItem(event.getHand());
                if (!item.equals(item2)) return;
                if (placeFurniture(player, block, item2)) {
                    block.getWorld().playSound(block.getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_WOOD_PLACE, 1.0f, 0.5f);
                }
            });
    }

    /**
     * This method places the furniture one tick after the player
     * clicked.  This method must take into account the orientation of
     * the player as well as the shape of the furniture.
     */
    private boolean placeFurniture(Player player, Block originBlock, ItemStack item) {
        // Test origin block
        if (!originBlock.getType().isAir()) return false;
        if (BlockMarker.getId(originBlock) != null) return false;
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, originBlock)) {
            return false;
        }
        // Determine facing direction
        final BlockFace facing;
        final Vector dir = player.getLocation().getDirection();
        if (Math.abs(dir.getX()) > Math.abs(dir.getZ())) {
            facing = dir.getX() > 0 ? BlockFace.WEST : BlockFace.EAST;
        } else {
            facing = dir.getZ() > 0 ? BlockFace.NORTH : BlockFace.SOUTH;
        }
        // Create block data
        final Map<Vec3i, BlockData> blockDataMap = furnitureType.getImplementation().createBlockData(facing);
        for (Vec3i blockVector : blockDataMap.keySet()) {
            final Block block = originBlock.getRelative(blockVector.x, blockVector.y, blockVector.z);
            if (!block.isEmpty() && block.getType() != Material.LIGHT) return false;
            if (BlockMarker.getId(block) != null) return false;
            if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) {
                return false;
            }
        }
        final Vec3i originVector = Vec3i.of(originBlock);
        for (Map.Entry<Vec3i, BlockData> entry : blockDataMap.entrySet()) {
            final Vec3i blockVector = entry.getKey();
            final Block block = originBlock.getRelative(blockVector.x, blockVector.y, blockVector.z);
            final BlockData blockData = entry.getValue();
            new PlayerChangeBlockEvent(player, block, blockData, item).callEvent();
            block.setBlockData(blockData, false);
            BlockMarker.setId(block, key.id);
            if (!block.equals(originBlock)) {
                BlockMarker.getTag(block, true, tag -> {
                        tag.set(mytemsPlugin().namespacedKey("origin"), PersistentDataType.INTEGER_ARRAY, originVector.toArray());
                        return true;
                    });
            }
        }
        furnitureType.getImplementation().spawnItemDisplay(player, originBlock, facing);
        if (player.getGameMode() != GameMode.CREATIVE) {
            item.subtract(1);
        }
        return true;
    }

    /**
     * Find all blocks adjacend that belong to the furniture of this type and
     * have the given block as their origin.
     *
     * We use a simple flood fill.
     */
    private List<Block> findFurnitureBlocks(Block originBlock) {
        if (!originBlock.equals(getOriginBlock(originBlock))) return List.of();
        final Set<Vec3i> done = new HashSet<>();
        final List<Block> result = new ArrayList<>();
        result.add(originBlock);
        final Vec3i originVector = Vec3i.of(originBlock);
        done.add(originVector);
        for (int blockIndex = 0; blockIndex < result.size(); blockIndex += 1) {
            final Block currentBlock = result.get(blockIndex);
            for (int dy = -1; dy <= 1; dy += 1) {
                for (int dz = -1; dz <= 1; dz += 1) {
                    for (int dx = -1; dx <= 1; dx += 1) {
                        if (dx == 0 && dy == 0 && dz == 0) continue;
                        final Block nbor = currentBlock.getRelative(dx, dy, dz);
                        final Vec3i nborVec = Vec3i.of(nbor);
                        if (done.contains(nborVec)) continue;
                        done.add(nborVec);
                        if (!originBlock.equals(getOriginBlock(nbor))) continue;
                        result.add(nbor);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Break this furniture with origina at originBlock.
     *
     * We allow breaking if the player is allowed to break the origin
     * block.
     */
    private boolean breakFurniture(Player player, Block originBlock) {
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, originBlock)) {
            return false;
        }
        final List<Block> blocks = findFurnitureBlocks(originBlock);
        if (blocks.isEmpty()) return false;
        furnitureType.getImplementation().onBreakFurniture(player, originBlock, blocks);
        for (Block block : blocks) {
            BlockMarker.clearTag(block);
            block.setBlockData(Material.AIR.createBlockData());
        }
        final Location originCenter = originBlock.getLocation().add(0.5, 0.5, 0.5);
        for (ItemDisplay display : originCenter.getWorld().getNearbyEntitiesByType(ItemDisplay.class, originCenter, 0.5)) {
            display.remove();
        }
        if (player.getGameMode() != GameMode.CREATIVE) {
            originCenter.getWorld().dropItem(originCenter, createItemStack());
        }
        return true;
    }

    /**
     * Get the origin block of this furniture block.  May be the same
     * block if this block is the origin.
     *
     * The origin block must have this Mytem's id!
     *
     * @return The origin block or null if there is an inconsistency
     *   (see console)
     */
    private Block getOriginBlock(Block block) {
        final PersistentDataContainer tag = BlockMarker.getTag(block);
        // This should not happen if we previously got a valid id from this block.
        if (tag == null) return null;
        final int[] originVectorArray = tag.get(mytemsPlugin().namespacedKey("origin"), PersistentDataType.INTEGER_ARRAY);
        if (originVectorArray == null) return block;
        final Vec3i blockVector = Vec3i.of(block);
        final Vec3i originVector = originVectorArray != null && originVectorArray.length == 3
            ? Vec3i.of(originVectorArray)
            : blockVector;
        final Block originBlock = originVector.toBlock(block.getWorld());
        final String originBlockId = BlockMarker.getId(originBlock);
        // Check if origin is consistent
        if (!originBlock.equals(block) && !key.id.equals(originBlockId)) {
            mytemsPlugin().getLogger().severe("[" + key + "] Bad origin block"
                                              + " world:" + block.getWorld().getName()
                                              + " block: " + blockVector
                                              + " origin:" + originVector
                                              + " origin.id: " + originBlockId);
            return null;
        }
        return originBlock;
    }

    // Overrides BlockEventHandler from BlockRegistry
    @Override
    public void onPlayerInteract(PlayerInteractEvent event, Block clickedBlock) {
        final boolean left;
        final boolean right;
        switch (event.getAction()) {
        case LEFT_CLICK_BLOCK:
            left = true;
            right = false;
            break;
        case RIGHT_CLICK_BLOCK:
            left = false;
            right = true;
            break;
        default: return;
        }
        if (event.getHand() != EquipmentSlot.HAND) return;
        final Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.SPECTATOR) return;
        final Block originBlock = getOriginBlock(clickedBlock);
        if (originBlock == null) {
            return;
        } else if (left) {
            event.setCancelled(true);
            if (breakFurniture(player, originBlock)) {
                originBlock.getWorld().playSound(originBlock.getLocation().add(0.5, 0.5, 0.5), Sound.BLOCK_WOOD_BREAK, 1.0f, 0.5f);
            }
        } else if (right) {
            event.setCancelled(true);
            furnitureType.getImplementation().onPlayerRightClick(player, clickedBlock, originBlock);
        }
    }
}
