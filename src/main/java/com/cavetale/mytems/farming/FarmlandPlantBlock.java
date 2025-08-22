package com.cavetale.mytems.farming;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.worldmarker.block.BlockMarker;
import java.time.Instant;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

/**
 * This represents each block belonging to a plant, from the farmland
 * to the growth above.
 */
public final class FarmlandPlantBlock extends AbstractPlantBlock {
    public FarmlandPlantBlock(final FarmingPlantType type) {
        super(type);
    }

    @Override
    public void randomTick(Block block, Instant now) {
        if (type.getBlockRegistryEntry().getRelativeParentVector(block) != null) {
            // This is not the main block
            return;
        }
        if (!(block.getBlockData() instanceof Farmland farmland)) {
            warn(block, "Is not farmland");
            removeBlock(block);
            return;
        }
        final int growthStageIndex = FarmingKeys.GROWTH_STAGE.getInt(BlockMarker.getTag(block), 0);
        final GrowthStage growthStage = type.getGrowthStages().get(growthStageIndex);
        if (!growthStage.checkAdditionalBlocksConsistency(block)) {
            warn(block, "Additional block check failed");
            removeBlock(block);
            return;
        }
        new FarmlandTick(type, block, farmland, now).run();
    }

    public void removeBlock(Block block) {
        final int growthStageIndex = FarmingKeys.GROWTH_STAGE.getInt(BlockMarker.getTag(block), 0);
        final GrowthStage growthStage = type.getGrowthStages().get(growthStageIndex);
        growthStage.remove(block);
        final boolean ripe = FarmingKeys.RIPE.getBoolean(BlockMarker.getTag(block));
        type.getBlockRegistryEntry().clearBlock(block);
    }

    /**
     * If the ripe farmland is broken, we drop the crop.
     *
     * The event gets cancelled by the BlockRegistryEntry, so here we
     * uncancel it.
     */
    @Override
    public void onBlockBreak(BlockBreakEvent event, Block block) {
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(event.getPlayer(), block)) {
            return;
        }
        final boolean ripe = FarmingKeys.RIPE.getBoolean(BlockMarker.getTag(block));
        removeBlock(block);
        final Location location = block.getLocation().add(0.5, 1.0, 0.5);
        block.getWorld().playSound(location, Sound.BLOCK_ROOTED_DIRT_BREAK, SoundCategory.MASTER, 2f, 0.8f);
        final int amount;
        if (ripe) {
            amount = type.rollRandomYield();
            block.getWorld().dropItem(location, type.getCropItem().createItemStack(amount));
        } else {
            amount = 0;
        }
        log(block, event.getPlayer().getName() + " break yield: " + amount);
        event.setCancelled(false);
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event, Block block) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(event.getPlayer(), block)) {
            return;
        }
        final boolean ripe = FarmingKeys.RIPE.getBoolean(BlockMarker.getTag(block));
        if (!ripe) {
            return;
        }
        if (type.doesRegrow() && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            harvest(event.getPlayer(), block);
        } else if (!type.doesRegrow() && event.getAction() == Action.LEFT_CLICK_BLOCK) {
            harvest(event.getPlayer(), block);
        }
    }

    public void harvest(Player player, Block block) {
        final String playerName = player != null
            ? player.getName()
            : "N/A";
        final int amount = type.rollRandomYield();
        if (type.doesRegrow()) {
            var readonly = BlockMarker.getTag(block, false, tag -> {
                    FarmingKeys.RIPE.setBoolean(tag, false);
                    final int oldAge = FarmingKeys.AGE.getInt(tag, 0);
                    final int newAge = type.getGrowthTime() - type.getRegrowthTime();
                    FarmingKeys.AGE.setInt(tag, newAge);
                    final int maxIndex = type.getGrowthStages().size() - 1;
                    final int oldGrowthStageIndex = FarmingKeys.GROWTH_STAGE.getInt(tag, 0);
                    final int newGrowthStageIndex = Math.min(maxIndex, (newAge * maxIndex) / type.getCropGroup().getGrowthTime());
                    final GrowthStage oldGrowthStage = type.getGrowthStages().get(oldGrowthStageIndex);
                    oldGrowthStage.remove(block);
                    FarmingKeys.GROWTH_STAGE.setInt(tag, newGrowthStageIndex);
                    final GrowthStage newGrowthStage = type.getGrowthStages().get(newGrowthStageIndex);
                    newGrowthStage.place(block);
                    log(block, playerName + " harvest regrow yield: " + amount
                        + ", age: " + oldAge + " => " + newAge + " / " + type.getGrowthTime()
                        + ", stage: " + oldGrowthStageIndex + " => " + newGrowthStageIndex + " / " + maxIndex);
                    return true;
                });
        } else {
            removeBlock(block);
            log(block, playerName + " harvest not regrow yield: " + amount);
        }
        final Location location = block.getLocation().add(0.5, 1.0, 0.5);
        block.getWorld().dropItem(location, type.getCropItem().createItemStack(amount));
        block.getWorld().playSound(location, Sound.BLOCK_ROOTED_DIRT_BREAK, SoundCategory.MASTER, 2f, 0.8f);
        block.getWorld().playSound(location, Sound.ENTITY_SNIFFER_DROP_SEED, SoundCategory.MASTER, 0.5f, 0.5f);
        block.getWorld().spawnParticle(Particle.BLOCK, location, 16, 0.125, 0.125, 0.125, 0.0, Material.FARMLAND.createBlockData());
    }
}
