package com.cavetale.mytems.item.treechopper;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerBreakBlockEvent;
import com.cavetale.core.event.block.PlayerChangeBlockEvent;
import com.cavetale.mytems.MytemsPlugin;
import com.destroystokyo.paper.MaterialSetTag;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import static com.cavetale.core.exploits.PlayerPlacedBlocks.isPlayerPlaced;
import static com.cavetale.mytems.item.treechopper.TreeChopStatus.*;

/**
 * Represents one tree chopping action.  It provides the flood fill
 * and block breaking mechanics.
 */
@Data @RequiredArgsConstructor
public final class TreeChop {
    protected static final Tag<Material> SAPLING_PLACEABLE = new MaterialSetTag(null, List.of(new Material[] {
                Material.DIRT,
                Material.COARSE_DIRT,
                Material.ROOTED_DIRT,
                Material.MOSS_BLOCK,
                Material.GRASS_BLOCK,
                Material.PODZOL,
                Material.FARMLAND,
                // World Gen
                Material.GRAVEL,
                Material.SAND,
            }));
    protected static final Set<Block> CHOPPING = new HashSet<>();
    private static final List<Face> FACES = Face.all();
    protected final TreeChopperTag tag;
    protected final List<Block> logBlocks = new ArrayList<>();
    protected final List<Block> leafBlocks = new ArrayList<>();
    protected final List<Block> saplingBlocks = new ArrayList<>(); // for placement
    protected final Map<ChoppedType, Integer> leafCount = new EnumMap<>(ChoppedType.class);
    protected int minHeight;
    // Used for chopping
    protected ChoppedType replantType = null;
    protected boolean doVines;
    protected boolean doReplant;
    protected int enchanter;
    protected int pickup;
    protected int brokenBlocks;
    protected static final BlockFace[] FACE6 = {
        BlockFace.UP,
        BlockFace.DOWN,
        BlockFace.NORTH,
        BlockFace.EAST,
        BlockFace.SOUTH,
        BlockFace.WEST,
    };

    private List<Face> faces() {
        Collections.shuffle(FACES);
        return FACES;
    }

    /**
     * Flood fill starting at (hopefully) the base of the tree,
     * attempting to find all connected log and leaf blocks, up to the
     * limits specified in the TreeChopperTag.
     */
    public TreeChopStatus fill(Player player, Block brokenBlock) {
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, brokenBlock)) {
            return NOTHING_FOUND;
        }
        minHeight = brokenBlock.getY();
        doVines = tag.getStat(TreeChopperStat.SILK) >= 2;
        doReplant = tag.getStat(TreeChopperStat.REPLANT) >= 1;
        enchanter = tag.getStat(TreeChopperStat.ENCH);
        pickup = tag.getStat(TreeChopperStat.PICKUP);
        // Log blocks is initialized with the original block!
        logBlocks.add(brokenBlock);
        // Blocks already searched, good or not
        Set<Block> done = new HashSet<>();
        done.add(brokenBlock);
        if (SAPLING_PLACEABLE.isTagged(brokenBlock.getRelative(BlockFace.DOWN).getType())) {
            saplingBlocks.add(brokenBlock);
        }
        // Crawl over logs.  Neighboring logs will be added to the
        // list.  Leaves go into their own list and will be searched
        // after.  Search is abandoned with corresponding return value
        // if there are too many logs.
        for (int logBlockIndex = 0; logBlockIndex < logBlocks.size(); logBlockIndex += 1) {
            Block logBlock = logBlocks.get(logBlockIndex);
            for (Face face : faces()) {
                Block nbor = logBlock.getRelative(face.x, face.y, face.z);
                if (done.contains(nbor)) continue;
                done.add(nbor);
                if (CHOPPING.contains(nbor)) continue;
                if (nbor.getY() < minHeight) continue;
                if (isPlayerPlaced(nbor)) continue;
                if (Tag.LOGS.isTagged(nbor.getType())) {
                    if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, nbor)) {
                        continue;
                    }
                    if (logBlocks.size() >= tag.getMaxLogBlocks()) {
                        return TOO_MANY_LOGS;
                    }
                    logBlocks.add(nbor);
                    if (logBlock.getY() <= minHeight + 1 && SAPLING_PLACEABLE.isTagged(logBlock.getRelative(BlockFace.DOWN).getType())) {
                        // Need this to replant AND to determine if xp are given!
                        saplingBlocks.add(nbor);
                    }
                } else if (Tag.LEAVES.isTagged(nbor.getType())) {
                    if (leafBlocks.size() >= tag.getMaxLeafBlocks()) continue;
                    if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, nbor)) continue;
                    leafBlocks.add(nbor);
                } else if (doVines && nbor.getType() == Material.VINE) {
                    if (leafBlocks.size() >= tag.getMaxLeafBlocks()) continue;
                    if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, nbor)) continue;
                    leafBlocks.add(nbor);
                }
            }
        }
        // Crawl over leaves.  Neighboring leaves will be added to the
        // leaf list.  Quit gracefully when the maximum number of
        // leaves is exceeded.
        LEAVES:
        for (int leafBlockIndex = 0; leafBlockIndex < leafBlocks.size(); leafBlockIndex += 1) {
            Block leafBlock = leafBlocks.get(leafBlockIndex);
            NBORS:
            for (Face face : faces()) {
                Block nbor = leafBlock.getRelative(face.x, face.y, face.z);
                if (done.contains(nbor)) continue;
                done.add(nbor);
                if (CHOPPING.contains(nbor)) continue;
                if (nbor.getY() < minHeight) continue;
                if (isPlayerPlaced(nbor)) continue;
                if (Tag.LEAVES.isTagged(nbor.getType())) {
                    // If the leaf is attached to a log from a
                    // different tree, we skip it.
                    for (BlockFace blockFace : FACE6) {
                        Block adj = nbor.getRelative(blockFace);
                        if (Tag.LOGS.isTagged(adj.getType()) && !logBlocks.contains(adj)) {
                            continue NBORS;
                        }
                    }
                    // Approximate if the leaf is connected with one
                    // of our logs.
                    boolean isConnected = false;
                    for (Block logBlock : logBlocks) {
                        int dx = Math.abs(logBlock.getX() - nbor.getX());
                        int dz = Math.abs(logBlock.getZ() - nbor.getZ());
                        if (dx + dz <= 6) {
                            isConnected = true;
                            break;
                        }
                    }
                    if (!isConnected) continue;
                    if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, nbor)) continue;
                    leafBlocks.add(nbor);
                    if (leafBlocks.size() >= tag.getMaxLeafBlocks()) break LEAVES;
                } else if (doVines && nbor.getType() == Material.VINE) {
                    if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, nbor)) continue;
                    leafBlocks.add(nbor);
                    if (leafBlocks.size() >= tag.getMaxLeafBlocks()) break LEAVES;
                }
            }
        }
        return logBlocks.size() > 2 ? SUCCESS : NOTHING_FOUND;
    }

    public void chop(Player player) {
        ItemStack axeItem = new ItemStack(Material.NETHERITE_AXE);
        int fortune = tag.getStat(TreeChopperStat.FORTUNE);
        int silk = tag.getStat(TreeChopperStat.SILK);
        if (fortune > 0) axeItem.addUnsafeEnchantments(Map.of(Enchantment.LOOT_BONUS_BLOCKS, fortune));
        if (silk > 0) axeItem.addUnsafeEnchantments(Map.of(Enchantment.SILK_TOUCH, silk));
        // Determine the primary tree type.
        // If replant is not unlocked, we skip this!
        if (doReplant && !leafBlocks.isEmpty()) {
            for (Block leafBlock : leafBlocks) {
                ChoppedType type = ChoppedType.of(leafBlock);
                if (type == null) continue;
                leafCount.compute(type, (t, i) -> i != null ? i + 1 : 1);
            }
            int highest = 0;
            for (ChoppedType type : ChoppedType.values()) {
                int count = leafCount.getOrDefault(type, 0);
                if (count > highest) {
                    replantType = type;
                    highest = count;
                }
            }
        }
        final int speed = tag.getChoppingSpeed();
        CHOPPING.addAll(logBlocks);
        CHOPPING.addAll(leafBlocks);
        new BukkitRunnable() {
            protected int logBlockIndex;
            protected int leafBlockIndex;
            protected int saplingBlocksPlaced;

            @Override
            public void run() {
                if (doVines) {
                    ItemStack shears = new ItemStack(Material.SHEARS);
                    for (Block vineBlock : leafBlocks) {
                        if (vineBlock.getType() != Material.VINE) continue;
                        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, vineBlock)) continue;
                        PlayerBreakBlockEvent.call(player, vineBlock);
                        if (pickup > 0) TreeChopListener.target = player.getLocation();
                        vineBlock.breakNaturally(shears, true);
                        TreeChopListener.target = null;
                    }
                }
                int blocksBrokenNow = 0;
                for (int i = 0; i <= speed; i += 1) {
                    if (logBlockIndex < logBlocks.size()) {
                        Block logBlock = logBlocks.get(logBlockIndex++);
                        if (!Tag.LOGS.isTagged(logBlock.getType())) continue;
                        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, logBlock)) continue;
                        PlayerBreakBlockEvent.call(player, logBlock);
                        if (pickup > 0) TreeChopListener.target = player.getLocation();
                        logBlock.breakNaturally(axeItem, true);
                        TreeChopListener.target = null;
                        brokenBlocks += 1;
                        if (player.isOnline()) {
                            if (player.getSaturation() >= 0.01f) {
                                // Buff saturation over food level
                                player.setSaturation(Math.max(0.0f, player.getSaturation() - 0.025f));
                            } else if (ThreadLocalRandom.current().nextInt(20) == 0) {
                                player.setFoodLevel(Math.max(0, player.getFoodLevel() - 1));
                            }
                            if (enchanter > 0 && ThreadLocalRandom.current().nextInt(20) < enchanter) {
                                player.giveExp(1, true);
                            }
                        }
                        blocksBrokenNow += 1;
                    }
                }
                if (blocksBrokenNow > 0) return;
                for (int i = 0; i <= 2 * speed; i += 1) {
                    if (leafBlockIndex < leafBlocks.size()) {
                        Block leafBlock = leafBlocks.get(leafBlockIndex++);
                        if (!Tag.LEAVES.isTagged(leafBlock.getType())) continue;
                        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, leafBlock)) continue;
                        PlayerBreakBlockEvent.call(player, leafBlock);
                        if (pickup > 0) TreeChopListener.target = player.getLocation();
                        leafBlock.breakNaturally(axeItem, true);
                        TreeChopListener.target = null;
                        brokenBlocks += 1;
                        if (enchanter > 0 && ThreadLocalRandom.current().nextInt(200) < enchanter && player.isOnline()) {
                            player.giveExp(1, true);
                        }
                        blocksBrokenNow += 1;
                    }
                }
                if (blocksBrokenNow > 0) return;
                if (doReplant && replantType != null) {
                    for (Block saplingBlock : saplingBlocks) {
                        if (!saplingBlock.isEmpty()) continue;
                        if (!SAPLING_PLACEABLE.isTagged(saplingBlock.getRelative(BlockFace.DOWN).getType())) continue;
                        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, saplingBlock)) continue;
                        List<Material> saplingTypes = List.copyOf(replantType.saplings.getValues());
                        Material saplingType = saplingTypes.size() > 1
                            ? saplingTypes.get(ThreadLocalRandom.current().nextInt(saplingTypes.size()))
                            : saplingTypes.get(0);
                        new PlayerChangeBlockEvent(player, saplingBlock, saplingType.createBlockData()).callEvent();
                        saplingBlock.setType(saplingType);
                        saplingBlocksPlaced += 1;
                        if (saplingBlocksPlaced >= leafBlocks.size()) break;
                    }
                }
                cancel();
                CHOPPING.removeAll(logBlocks);
                CHOPPING.removeAll(leafBlocks);
            }
        }.runTaskTimer(MytemsPlugin.getInstance(), 0L, 3L);
    }

    private record Face(int x, int y, int z) {
        /**
         * We need diagonal faces because of Acacia.
         */
        public static List<Face> all() {
            List<Face> result = new ArrayList<>();
            for (int y = -1; y < 2; y += 1) {
                for (int z = -1; z < 2; z += 1) {
                    for (int x = -1; x < 2; x += 1) {
                        if (x == 0 && y == 0 && z == 0) continue;
                        result.add(new Face(x, y, z));
                    }
                }
            }
            return result;
        }
    }
}
