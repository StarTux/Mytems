package com.cavetale.mytems.item.treechopper;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerBreakBlockEvent;
import com.cavetale.mytems.MytemsPlugin;
import com.destroystokyo.paper.MaterialSetTag;
import com.winthier.exploits.Exploits;
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
import org.bukkit.block.data.type.Leaves;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
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
            }));
    private static final List<Face> FACES = Face.all();
    protected final TreeChopperTag tag;
    protected final List<Block> logBlocks = new ArrayList<>();
    protected final List<Block> leafBlocks = new ArrayList<>();
    protected final Map<ChoppedType, Integer> leafCount = new EnumMap<>(ChoppedType.class);
    protected int minHeight;
    // Used for chopping
    protected ChoppedType primaryType = null;
    protected boolean doVines;
    protected int enchanter;
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
        minHeight = brokenBlock.getY();
        doVines = tag.getStat(TreeChopperStat.SILK) >= 2;
        enchanter = tag.getStat(TreeChopperStat.ENCH);
        // Log blocks is initialized with the original block!
        logBlocks.add(brokenBlock);
        // Blocks already searched, good or not
        Set<Block> done = new HashSet<>();
        done.add(brokenBlock);
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
                if (nbor.getY() < minHeight) continue;
                if (Exploits.isPlayerPlaced(nbor)) continue;
                if (Tag.LOGS.isTagged(nbor.getType())) {
                    if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, nbor)) {
                        continue;
                    }
                    if (logBlocks.size() >= tag.getMaxLogBlocks()) {
                        return TOO_MANY_LOGS;
                    }
                    logBlocks.add(nbor);
                    int x = nbor.getX();
                    int z = nbor.getZ();
                } else if (Tag.LEAVES.isTagged(nbor.getType())) {
                    if (nbor.getBlockData() instanceof Leaves leaves && leaves.isPersistent()) continue;
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
            for (Face face : faces()) {
                Block nbor = leafBlock.getRelative(face.x, face.y, face.z);
                if (done.contains(nbor)) continue;
                done.add(nbor);
                if (nbor.getY() < minHeight) continue;
                // Do your voodoo to figure out if the leaf block is
                // too far way.
                if (Exploits.isPlayerPlaced(nbor)) continue;
                if (Tag.LEAVES.isTagged(nbor.getType())) {
                    if (nbor.getBlockData() instanceof Leaves leaves && leaves.isPersistent()) continue;
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
        if (tag.getStat(TreeChopperStat.REPLANT) > 0 && leafBlocks.size() >= 4) {
            for (Block leafBlock : leafBlocks) {
                ChoppedType type = ChoppedType.of(leafBlock);
                if (type == null) continue;
                leafCount.compute(type, (t, i) -> i != null ? i + 1 : 1);
            }
            int highest = 0;
            for (ChoppedType type : ChoppedType.values()) {
                int count = leafCount.getOrDefault(type, 0);
                if (count > highest) {
                    primaryType = type;
                    highest = count;
                }
            }
        }
        final int speed = tag.getStat(TreeChopperStat.SPEED);
        new BukkitRunnable() {
            protected int logBlockIndex;
            protected int leafBlockIndex;
            protected int replanted;

            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel();
                    return;
                }
                if (doVines) {
                    ItemStack shears = new ItemStack(Material.SHEARS);
                    for (Block vineBlock : leafBlocks) {
                        if (vineBlock.getType() != Material.VINE) continue;
                        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, vineBlock)) continue;
                        PlayerBreakBlockEvent.call(player, vineBlock);
                        vineBlock.breakNaturally(shears, true);
                    }
                }
                for (int i = 0; i <= speed; i += 1) {
                    if (!iter()) break;
                }
            }

            private boolean iter() {
                if (logBlockIndex < logBlocks.size()) {
                    Block logBlock = logBlocks.get(logBlockIndex++);
                    if (!Tag.LOGS.isTagged(logBlock.getType())) return true;
                    if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, logBlock)) return true;
                    PlayerBreakBlockEvent.call(player, logBlock);
                    logBlock.breakNaturally(axeItem, true);
                    brokenBlocks += 1;
                    if (primaryType != null
                        && replanted < leafBlocks.size()
                        && logBlock.getY() == minHeight
                        && SAPLING_PLACEABLE.isTagged(logBlock.getRelative(BlockFace.DOWN).getType())) {
                        List<Material> saplingTypes = List.copyOf(primaryType.saplings.getValues());
                        Material saplingType = saplingTypes.size() > 1
                            ? saplingTypes.get(ThreadLocalRandom.current().nextInt(saplingTypes.size()))
                            : saplingTypes.get(0);
                        logBlock.setType(saplingType);
                        replanted += 1;
                    }
                    if (player.getSaturation() >= 0.01f) {
                        // Buff saturation over food level
                        player.setSaturation(Math.max(0.0f, player.getSaturation() - 0.025f));
                    }
                    if (ThreadLocalRandom.current().nextInt(20) == 0) {
                        player.setFoodLevel(Math.max(0, player.getFoodLevel() - 1));
                    }
                    if (enchanter > 0 && ThreadLocalRandom.current().nextInt(100) < enchanter) {
                        player.giveExp(1, true);
                    }
                    return true;
                }
                if (leafBlockIndex < leafBlocks.size()) {
                    Block leafBlock = leafBlocks.get(leafBlockIndex++);
                    if (!Tag.LEAVES.isTagged(leafBlock.getType())) return true;
                    if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, leafBlock)) return true;
                    PlayerBreakBlockEvent.call(player, leafBlock);
                    leafBlock.breakNaturally(axeItem, true);
                    brokenBlocks += 1;
                    if (enchanter > 0 && ThreadLocalRandom.current().nextInt(200) < enchanter) {
                        player.giveExp(1, true);
                    }
                    return true;
                }
                cancel();
                return false;
            }
        }.runTaskTimer(MytemsPlugin.getInstance(), 0L, 1L);
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
