package com.cavetale.mytems.item.hastypickaxe;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerBreakBlockEvent;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.core.util.Json;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.session.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import static com.cavetale.core.exploits.PlayerPlacedBlocks.isPlayerPlaced;
import static com.cavetale.core.font.Unicode.subscript;
import static com.cavetale.core.font.Unicode.superscript;
import static com.cavetale.mytems.MytemsPlugin.plugin;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

/**
 * This is the HastyPickaxe Mytem, whereas the UpgradableItem
 * implementation resides in HastyPickaxeItem.
 */
@Getter
@RequiredArgsConstructor
public final class HastyPickaxe implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private HastyPickaxeTier tier;

    @Override
    public void enable() {
        tier = HastyPickaxeTier.of(key);
        if (tier == null) {
            throw new IllegalArgumentException("HastyPickaxe.key = " + key);
        }
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> key.markItemMeta(meta));
        tier.createTag().store(prototype);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    public Component getDisplayName() {
        return tier.getDisplayName();
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, Player player, ItemStack item) {
        if (event.isCancelled()) return;
        final Block block = event.getBlock();
        onBreak(player, block, item);
        final HastyPickaxeTag tag = serializeTag(item);
        final int radius = tag.getEffectiveUpgradeLevel(HastyPickaxeStat.RADIUS);
        if (radius > 0 && !player.isSneaking() && STONE_TYPES.contains(block.getType())) {
            Bukkit.getScheduler().runTask(plugin(), () -> {
                    final int count = breakRadius(player, block, item,  2 + radius - 1);
                });
        }
        final int haste = tag.getEffectiveUpgradeLevel(HastyPickaxeStat.HASTE);
        if (haste > 0 && block.getType().name().endsWith("_ORE")) {
            final int hasteTime = tag.getEffectiveUpgradeLevel(HastyPickaxeStat.HASTE_TIME);
            final int duration = 20 * 10 * (hasteTime + 1);
            final int amplifier = haste - 1;
            player.addPotionEffect(PotionEffectType.HASTE.createEffect(duration, amplifier)
                                   .withAmbient(true)
                                   .withIcon(true)
                                   .withParticles(false));
        }
    }

    @Override
    public void onPlayerBreakBlock(PlayerBreakBlockEvent event, Player player, ItemStack item) {
        if (event.isCancelled()) return;
        onBreak(player, event.getBlock(), item);
    }

    private static final Set<Material> STONE_TYPES = Set.of(Material.STONE,
                                                            Material.DEEPSLATE,
                                                            Material.ANDESITE,
                                                            Material.DIORITE,
                                                            Material.GRANITE,
                                                            Material.TUFF,
                                                            Material.NETHERRACK,
                                                            Material.BLACKSTONE,
                                                            Material.BASALT,
                                                            Material.END_STONE);

    private void onBreak(Player player, Block block, ItemStack item) {
        if (isPlayerPlaced(block)) {
            return;
        }
        final Material material = block.getType();
        final int xp = getXp(material);
        if (xp <= 0) return;
        Bukkit.getScheduler().runTask(plugin(), () -> {
                final HastyPickaxeTag tag = serializeTag(item);
                if (xp > 0) {
                    if (tag.addXpAndNotify(player, xp)) {
                        tag.store(item);
                    }
                }
            });
    }

    private int breakRadius(Player player, Block pivot, ItemStack item, int radius) {
        final List<Block> blocks = new ArrayList<>();
        final int radius2 = radius * radius - 1;
        final int cx = pivot.getX();
        final int cy = pivot.getY();
        final int cz = pivot.getZ();
        blocks.add(pivot);
        final List<BlockFace> faces = List.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN);
        for (int blockIndex = 0; blockIndex < blocks.size() && blockIndex < 1000; blockIndex += 1) {
            final Block block = blocks.get(blockIndex);
            final List<BlockFace> faceList = new ArrayList<>(faces);
            Collections.shuffle(faceList);
            for (BlockFace face : faceList) {
                final Block nbor = block.getRelative(face);
                if (nbor.getY() < cy) continue;
                if (blocks.contains(nbor)) continue;
                if (!STONE_TYPES.contains(nbor.getType())) continue;
                if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, nbor)) continue;
                final int dy = nbor.getY() - cy;
                final int dx = nbor.getX() - cx;
                final int dz = nbor.getZ() - cz;
                final int distance2 = (dx * dx) + (dy * dy) + (dz * dz);
                if (distance2 > radius2) continue;
                blocks.add(nbor);
            }
        }
        if (blocks.size() <= 1) return 0;
        for (int i = 1; i < blocks.size(); i += 1) {
            final Block block = blocks.get(i);
            if (!new PlayerBreakBlockEvent(player, block, item).callEvent()) continue;
            if (player.getSaturation() >= 0.01f) {
                player.setSaturation(Math.max(0.0f, player.getSaturation() - 0.025f));
            } else if (player.getFoodLevel() > 0) {
                if (ThreadLocalRandom.current().nextInt(20) == 0) {
                    player.setFoodLevel(player.getFoodLevel() - 1);
                }
            } else {
                break;
            }
            block.breakNaturally(item, true, true);
        }
        return blocks.size();
    }

    private int getXp(Material material) {
        return switch (material) {
        // case COAL_ORE -> 1;
        // case DEEPSLATE_COAL_ORE -> 1;

        case REDSTONE_ORE -> 1;
        case DEEPSLATE_REDSTONE_ORE -> 1;

        case IRON_ORE -> 1;
        case DEEPSLATE_IRON_ORE -> 1;

        case COPPER_ORE -> 1;
        case DEEPSLATE_COPPER_ORE -> 1;

        case NETHER_QUARTZ_ORE -> 1;
        case GLOWSTONE -> 1;

        case GOLD_ORE -> 2;
        case DEEPSLATE_GOLD_ORE -> 2;
        case NETHER_GOLD_ORE -> 2;

        case LAPIS_ORE -> 3;
        case DEEPSLATE_LAPIS_ORE -> 5;

        case EMERALD_ORE -> 4;
        case DEEPSLATE_EMERALD_ORE -> 4;

        case DIAMOND_ORE -> 5;
        case DEEPSLATE_DIAMOND_ORE -> 5;

        case ANCIENT_DEBRIS -> 10;
        default -> 0;
        };
    }

    @Override
    public void onBlockDamage(BlockDamageEvent event, Player player, ItemStack item) {
        final Block block = event.getBlock();
        final Material material = block.getType();
        if (material == Material.END_PORTAL_FRAME) {
            tryToBreak(player, block, item, HastyPickaxeStat.END_PORTAL, 50);
            event.setCancelled(true);
        } else if (material == Material.BEDROCK) {
            // The bedrock ability requires bedrock to come from the
            // end or be player placed.
            if (!isEndPillarBlock(block) && !isPlayerPlaced(block)) {
                return;
            }
            tryToBreak(player, block, item, HastyPickaxeStat.BEDROCK, 300);
            event.setCancelled(true);
        }
    }

    private boolean isEndPillarBlock(Block block) {
        if (block.getWorld().getEnvironment() != World.Environment.THE_END) return false;
        if (Math.abs(block.getX()) > 64) return false;
        if (Math.abs(block.getZ()) > 64) return false;
        // We assume that this is an end pillar block if it is a lone
        // bedrock block, with no other bedrock around.  This should
        // reasonably confirm that it is neither the End Portal nor an
        // End Gateway.
        for (int y = -1; y <= 1; y += 1) {
            for (int z = -1; z <= 1; z += 1) {
                for (int x = -1; x <= 1; x += 1) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    if (block.getRelative(x, y, z).getType() == Material.BEDROCK) return false;
                }
            }
        }
        return true;
    }

    private void tryToBreak(Player player, Block block, ItemStack item, HastyPickaxeStat stat, int max) {
        final HastyPickaxeTag tag = serializeTag(item);
        final int upgradeLevel = tag.getUpgradeLevel(stat);
        if (upgradeLevel < 1) {
            return;
        }
        max /= upgradeLevel;
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) {
            return;
        }
        final Vec3i vector = Vec3i.of(block);
        final BlockBreakProgress blockBreakProgress = Session.of(player).getFavorites().getOrSet(BlockBreakProgress.class, BlockBreakProgress::new);
        final World world = block.getWorld();
        if (!world.getName().equals(blockBreakProgress.world) || !vector.equals(blockBreakProgress.where)) {
            blockBreakProgress.reset();
            blockBreakProgress.world = world.getName();
            blockBreakProgress.where = vector;
            blockBreakProgress.progress = 0;
        }
        blockBreakProgress.progress += 1;
        if (blockBreakProgress.progress >= max) {
            blockBreakProgress.reset();
            if (!new PlayerBreakBlockEvent(player, block, item).callEvent()) {
                return;
            }
            final BlockData blockData = block.getBlockData();
            final Material material = blockData.getMaterial();
            final Location location = block.getLocation().add(0.5, 0.5, 0.5);
            block.setType(Material.AIR);
            world.dropItem(location, new ItemStack(material));
            if (blockData instanceof EndPortalFrame endPortalFrame && endPortalFrame.hasEye()) {
                if (tag.getUpgradeLevel(HastyPickaxeStat.SILK_TOUCH) > 0) {
                    world.dropItem(location, new ItemStack(Material.ENDER_EYE));
                }
                breakAdjacentEndPortals(block);
            }
            player.sendActionBar(empty());
            world.spawnParticle(Particle.BLOCK, location, 32, 0.0, 0.0, 0.0, 1.0, material.createBlockData());
            world.spawnParticle(Particle.SMOKE, location, 32, 0.0, 0.0, 0.0, 0.25);
            world.spawnParticle(Particle.WHITE_SMOKE, location, 32, 0.0, 0.0, 0.0, 0.25);
            if (material == Material.BEDROCK) {
                world.playSound(location, Sound.BLOCK_STONE_BREAK, 2f, 0.75f);
            } else if (material == Material.END_PORTAL_FRAME) {
                world.playSound(location, Sound.BLOCK_AMETHYST_BLOCK_BREAK, 2f, 0.5f);
            }
        } else {
            final int bars = 20;
            final int full = (blockBreakProgress.progress * bars) / max;
            final int empty = bars - full;
            player.sendActionBar(textOfChildren(key,
                                                text(superscript(String.format("%02d", blockBreakProgress.progress)) + "/" + subscript(max), tier.getMenuColor()),
                                                text("|".repeat(full), tier.getMenuColor()), text("|".repeat(empty), color(0x303030))));
            player.sendBlockDamage(block.getLocation(), (float) blockBreakProgress.progress / (float) max);
        }
    }

    private void breakAdjacentEndPortals(Block pivot) {
        List<Block> blocks = new ArrayList<>();
        blocks.add(pivot);
        for (int blockIndex = 0; blockIndex < blocks.size() && blockIndex < 36; blockIndex += 1) {
            final Block block = blocks.get(blockIndex);
            for (BlockFace face : List.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST)) {
                final Block nbor = block.getRelative(face);
                if (blocks.contains(nbor)) continue;
                if (nbor.getType() != Material.END_PORTAL) continue;
                blocks.add(nbor);
                nbor.setType(Material.AIR);
                final Location location = nbor.getLocation().add(0.5, 0.5, 0.5);
                nbor.getWorld().spawnParticle(Particle.BLOCK, location, 16, 0.0, 0.0, 0.0, 1.0, Material.END_PORTAL.createBlockData());
                nbor.getWorld().playSound(location, Sound.BLOCK_GLASS_BREAK, 1f, 0.5f);
            }
        }
    }

    private static final class BlockBreakProgress {
        private String world;
        private Vec3i where;
        private int progress;

        private void reset() {
            world = null;
            where = null;
            progress = 0;
        }
    }

    @Override
    public HastyPickaxeTag serializeTag(ItemStack itemStack) {
        HastyPickaxeTag tag = tier.createTag();
        tag.load(itemStack);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        ItemStack itemStack = createItemStack();
        HastyPickaxeTag tag = Json.deserialize(serialized, tier.getTagClass());
        if (tag != null && !tag.isEmpty()) {
            tag.store(itemStack);
        }
        return itemStack;
    }

    @Override
    public boolean isMassStorable() {
        return false;
    }
}
