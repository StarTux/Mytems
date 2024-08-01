package com.cavetale.mytems.item.treechopper;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerBreakBlockEvent;
import com.cavetale.core.event.block.PlayerChangeBlockEvent;
import com.cavetale.mytems.MytemsPlugin;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
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
    private static final int MAX_SHROOMLIGHT_BLOCKS = 9;
    protected static final Set<Block> CHOPPING = new HashSet<>();
    private static final List<Face> FACES = Face.all();
    protected final TreeChopperTag tag;
    protected final List<Block> logBlocks = new ArrayList<>();
    protected final List<Block> leafBlocks = new ArrayList<>();
    protected final List<Block> vineBlocks = new ArrayList<>();
    protected final List<Block> shroomlightBlocks = new ArrayList<>();
    protected final List<Block> saplingBlocks = new ArrayList<>(); // for placement
    protected int minHeight;
    // Used for chopping
    private ChoppedType choppedType = null;
    protected boolean doVines;
    protected boolean didVines;
    protected boolean doReplant;
    protected boolean doShroomlights;
    protected boolean didShroomlights;
    protected int pickup;
    protected int brokenBlocks;

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
        choppedType = ChoppedType.of(brokenBlock.getType());
        if (choppedType == null) {
            return TreeChopStatus.NOTHING_FOUND;
        }
        if (choppedType == ChoppedType.RED_MUSHROOM || choppedType == ChoppedType.BROWN_MUSHROOM) {
            if (tag.getEffectiveUpgradeLevel(TreeChopperStat.MUSHROOM) < 1) {
                return TreeChopStatus.STAT_REQUIRED;
            }
        }
        if (choppedType == ChoppedType.WARPED || choppedType == ChoppedType.CRIMSON) {
            if (tag.getEffectiveUpgradeLevel(TreeChopperStat.FUNGI) < 1) {
                return TreeChopStatus.STAT_REQUIRED;
            }
            doShroomlights = tag.getEffectiveUpgradeLevel(TreeChopperStat.SHROOMLIGHT) > 0;
        }
        if (choppedType == ChoppedType.MANGROVE) {
            if (tag.getEffectiveUpgradeLevel(TreeChopperStat.MANGROVE) < 1) {
                return TreeChopStatus.STAT_REQUIRED;
            }
        }
        minHeight = brokenBlock.getY();
        doVines = tag.getEffectiveUpgradeLevel(TreeChopperStat.SILK) >= 2;
        doReplant = tag.getEffectiveUpgradeLevel(TreeChopperStat.REPLANT) >= 1;
        pickup = tag.getEffectiveUpgradeLevel(TreeChopperStat.PICKUP);
        // Log blocks is initialized with the original block!
        logBlocks.add(brokenBlock);
        // Blocks already searched, good or not
        Set<Block> done = new HashSet<>();
        done.add(brokenBlock);
        if (choppedType.placeableOn.isTagged(brokenBlock.getRelative(BlockFace.DOWN).getType())) {
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
                if (choppedType.logs.isTagged(nbor.getType())) {
                    if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, nbor)) {
                        continue;
                    }
                    if (logBlocks.size() >= tag.getMaxLogBlocks()) {
                        return TOO_MANY_LOGS;
                    }
                    logBlocks.add(nbor);
                    if (logBlock.getY() <= minHeight + 1 && choppedType.placeableOn.isTagged(logBlock.getRelative(BlockFace.DOWN).getType())) {
                        // Need this to replant AND to determine if xp are given!
                        saplingBlocks.add(nbor);
                    }
                } else if (choppedType == ChoppedType.OAK && ChoppedType.AZALEA.isAzaleaLeaf(nbor.getType())) {
                    // Special case: Azalea tree is like a subtype of
                    // Oak
                    if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, nbor)) continue;
                    leafBlocks.add(nbor);
                    choppedType = ChoppedType.AZALEA;
                } else if (leafBlocks.isEmpty() && choppedType == ChoppedType.RED_MUSHROOM && ChoppedType.BROWN_MUSHROOM.leaves.isTagged(nbor.getType())) {
                    // Special case: Red -> brown mushroom
                    if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, nbor)) continue;
                    leafBlocks.add(nbor);
                    choppedType = ChoppedType.BROWN_MUSHROOM;
                } else if (choppedType.leaves.isTagged(nbor.getType())) {
                    if (leafBlocks.size() >= tag.getMaxLeafBlocks()) continue;
                    if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, nbor)) continue;
                    leafBlocks.add(nbor);
                } else if (doVines && nbor.getType() == Material.VINE) {
                    if (vineBlocks.size() >= tag.getMaxLeafBlocks()) continue;
                    if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, nbor)) continue;
                    vineBlocks.add(nbor);
                } else if (doShroomlights && nbor.getType() == Material.SHROOMLIGHT) {
                    if (shroomlightBlocks.size() >= MAX_SHROOMLIGHT_BLOCKS) continue;
                    if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, nbor)) continue;
                    shroomlightBlocks.add(nbor);
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
                if (choppedType.leaves.isTagged(nbor.getType())) {
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
                    vineBlocks.add(nbor);
                    if (vineBlocks.size() >= tag.getMaxLeafBlocks()) break LEAVES;
                }
            }
        }
        if (saplingBlocks.isEmpty()) return NO_SAPLING;
        if (logBlocks.size() < 4) return NOTHING_FOUND;
        return SUCCESS;
    }

    public void chop(Player player, ItemStack itemStack) {
        final ItemStack axeItem = new ItemStack(Material.NETHERITE_AXE);
        final int fortune = tag.getEffectiveUpgradeLevel(TreeChopperStat.FORTUNE);
        final int silk = tag.getEffectiveUpgradeLevel(TreeChopperStat.SILK);
        if (fortune > 0) {
            axeItem.addUnsafeEnchantments(Map.of(Enchantment.FORTUNE, fortune));
        }
        if (silk > 0) {
            axeItem.addUnsafeEnchantments(Map.of(Enchantment.SILK_TOUCH, silk));
        }
        final int speed = tag.getChoppingSpeed();
        CHOPPING.addAll(logBlocks);
        CHOPPING.addAll(leafBlocks);
        CHOPPING.addAll(vineBlocks);
        new BukkitRunnable() {
            protected int logBlockIndex;
            protected int leafBlockIndex;
            protected int saplingBlocksPlaced;

            @Override
            public void run() {
                if (doVines && !didVines) {
                    didVines = true;
                    ItemStack shears = new ItemStack(Material.SHEARS);
                    for (Block vineBlock : vineBlocks) {
                        if (vineBlock.getType() != Material.VINE) continue;
                        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, vineBlock)) continue;
                        if (!new PlayerBreakBlockEvent(player, vineBlock, itemStack).callEvent()) continue;
                        if (pickup > 0) TreeChopListener.target = player.getLocation();
                        vineBlock.breakNaturally(shears, true);
                        TreeChopListener.target = null;
                    }
                }
                int blocksBrokenNow = 0;
                for (int i = 0; i <= speed; i += 1) {
                    if (logBlockIndex < logBlocks.size()) {
                        Block logBlock = logBlocks.get(logBlockIndex++);
                        if (!choppedType.logs.isTagged(logBlock.getType())) continue;
                        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, logBlock)) continue;
                        if (!new PlayerBreakBlockEvent(player, logBlock, itemStack).callEvent()) continue;
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
                        }
                        blocksBrokenNow += 1;
                    }
                }
                if (blocksBrokenNow > 0) return;
                // Break leaves
                for (int i = 0; i <= 2 * speed; i += 1) {
                    if (leafBlockIndex < leafBlocks.size()) {
                        Block leafBlock = leafBlocks.get(leafBlockIndex++);
                        if (!choppedType.leaves.isTagged(leafBlock.getType())) continue;
                        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, leafBlock)) continue;
                        if (!new PlayerBreakBlockEvent(player, leafBlock, itemStack).callEvent()) continue;
                        if (pickup > 0) TreeChopListener.target = player.getLocation();
                        leafBlock.breakNaturally(axeItem, true);
                        TreeChopListener.target = null;
                        brokenBlocks += 1;
                        blocksBrokenNow += 1;
                    }
                }
                if (blocksBrokenNow > 0) return;
                // Break shroomlights
                if (doShroomlights && !didShroomlights) {
                    didShroomlights = true;
                    for (Block shroomlightBlock : shroomlightBlocks) {
                        if (shroomlightBlock.getType() != Material.SHROOMLIGHT) continue;
                        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, shroomlightBlock)) continue;
                        if (!new PlayerBreakBlockEvent(player, shroomlightBlock, itemStack).callEvent()) continue;
                        if (pickup > 0) TreeChopListener.target = player.getLocation();
                        shroomlightBlock.breakNaturally(axeItem, true);
                        TreeChopListener.target = null;
                        brokenBlocks += 1;
                        blocksBrokenNow += 1;
                    }
                }
                if (blocksBrokenNow > 0) return;
                // Replant
                if (doReplant) {
                    for (Block saplingBlock : saplingBlocks) {
                        if (!saplingBlock.isEmpty()) continue;
                        if (!choppedType.placeableOn.isTagged(saplingBlock.getRelative(BlockFace.DOWN).getType())) continue;
                        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, saplingBlock)) continue;
                        final List<Material> saplingTypes = List.copyOf(choppedType.saplings.getValues());
                        final Material saplingType = saplingTypes.size() > 1
                            ? saplingTypes.get(ThreadLocalRandom.current().nextInt(saplingTypes.size()))
                            : saplingTypes.get(0);
                        if (!new PlayerChangeBlockEvent(player, saplingBlock, saplingType.createBlockData(), itemStack).callEvent()) continue;
                        saplingBlock.setType(saplingType);
                        saplingBlocksPlaced += 1;
                        final Location effectLocation = saplingBlock.getLocation().add(0.5, 0.25, 0.5);
                        saplingBlock.getWorld().playSound(effectLocation, Sound.BLOCK_NYLIUM_PLACE, 1f, 1f);
                        saplingBlock.getWorld().spawnParticle(Particle.BLOCK, effectLocation, 32, 0f, 0f, 0f, 0f, saplingType.createBlockData());
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

    /**
     * Get the chopped type.
     *
     * @deprecation Use getChoppedType() instead.
     */
    @Deprecated
    public ChoppedType guessChoppedType() {
        return choppedType;
    }
}
