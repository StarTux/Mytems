package com.cavetale.mytems.farming;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.mytems.block.BlockEventHandler;
import com.cavetale.mytems.block.BlockImplementation;
import com.cavetale.worldmarker.block.BlockMarker;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import static com.cavetale.mytems.MytemsPlugin.mytemsPlugin;
import static com.cavetale.mytems.MytemsPlugin.namespacedKey;

/**
 * This represents each block belonging to a plant, from the farmland
 * to the growth above.
 */
@Getter
@RequiredArgsConstructor
public final class FarmingPlantBlock implements BlockImplementation, BlockEventHandler {
    public static final String GROWTH_STAGE = "growth_stage";
    private final FarmingPlantType type;

    public boolean water(Player player, Block block) {
        if (type.getBlockRegistryEntry().getRelativeParentVector(block) != null) return false;
        final int growthStage = getGrowthStage(block);
        if (growthStage >= type.getGrowthStages().size() - 1) return false;
        final GrowthStage oldGrowthStage = type.getGrowthStages().get(growthStage);
        oldGrowthStage.remove(block);
        final GrowthStage newGrowthStage = type.getGrowthStages().get(growthStage + 1);
        setGrowthStage(block, growthStage + 1);
        newGrowthStage.place(block);
        return true;
    }

    public int getGrowthStage(Block block) {
        final PersistentDataContainer tag = BlockMarker.getTag(block);
        if (tag == null) return 0;
        return tag.getOrDefault(namespacedKey(GROWTH_STAGE), PersistentDataType.INTEGER, 0);
    }

    public void setGrowthStage(Block block, int value) {
        BlockMarker.getTag(block, true, tag -> {
                tag.set(namespacedKey(GROWTH_STAGE), PersistentDataType.INTEGER, value);
                return true;
            });
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, Block block) {
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(event.getPlayer(), block)) return;
        final int growthStage = getGrowthStage(block);
        final GrowthStage oldGrowthStage = type.getGrowthStages().get(growthStage);
        oldGrowthStage.remove(block);
        type.getBlockRegistryEntry().clearBlock(block);
        final Location location = block.getLocation().add(0.5, 1.5, 0.5);
        block.getWorld().playSound(location, Sound.BLOCK_ROOTED_DIRT_BREAK, 2f, 0.8f);
        block.getWorld().spawnParticle(Particle.BLOCK, location, 16, 0.125, 0.125, 0.125, 0.0, Material.FARMLAND.createBlockData());
        if (growthStage == type.getGrowthStages().size() - 1) {
            block.getWorld().dropItem(location, type.getCropItem().createItemStack());
        }
    }

    @Override
    public void tick(Block block) {
        mytemsPlugin().getLogger().info("[" + type + "] ticking at " + Vec3i.of(block));
    }
}
