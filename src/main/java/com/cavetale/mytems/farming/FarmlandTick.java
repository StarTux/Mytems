package com.cavetale.mytems.farming;

import com.cavetale.worldmarker.block.BlockMarker;
import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.bukkit.HeightMap;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.persistence.PersistentDataContainer;

@RequiredArgsConstructor
public final class FarmlandTick {
    private final FarmingPlantType type;
    private final Block block;
    private final Farmland farmland;
    private final Instant now;
    private PersistentDataContainer tag;
    private boolean rainedOn;
    private boolean hibernating;
    private boolean shouldSaveTag;
    private boolean wasHydrated;
    private boolean hydrated;

    public void run() {
        if (type.getWateringNeed() == WateringNeed.NONE) {
            warn("No watering need");
            return;
        }
        BlockMarker.getTag(block, false, theTag -> {
                this.tag = theTag;
                rain();
                season();
                if (hibernating) return shouldSaveTag;
                drink();
                grow();
                return shouldSaveTag;
            });
        if (tag == null) {
            warn("No tag");
        }
    }

    private void rain() {
        if (!block.getWorld().hasStorm()) {
            return;
        }
        if (block.getWorld().getHighestBlockYAt(block.getX(), block.getZ(), HeightMap.WORLD_SURFACE) > block.getY()) {
            return;
        }
        rainedOn = true;
        farmland.setMoisture(farmland.getMaximumMoisture());
        block.setBlockData(farmland, false);
    }

    private void season() {
        final boolean wasHibernating = FarmingKeys.HIBERNATING.getBoolean(tag);
        hibernating = wasHibernating;
        final Season season = Season.getCurrentSeason(now);
        if (wasHibernating && type.getSeasons().contains(season)) {
            hibernating = false;
            FarmingKeys.HIBERNATING.setBoolean(tag, hibernating);
            shouldSaveTag = true;
            log("Wake up from hibernation: " + season);
        } else if (!wasHibernating && !type.getSeasons().contains(season)) {
            hibernating = true;
            FarmingKeys.HIBERNATING.setBoolean(tag, hibernating);
            FarmingKeys.AGE.setInt(tag, 0);
            updateGrowthStage(0);
            shouldSaveTag = true;
            log("Hibernate: " + season);
        }
    }

    private void drink() {
        wasHydrated = FarmingKeys.HYDRATED.getBoolean(tag);
        hydrated = wasHydrated;
        final Instant lastDrink = FarmingKeys.LAST_DRINK.getInstant(tag);
        final Duration timeSinceDrink = Duration.between(lastDrink, now);
        log("Time since drink "
            + timeSinceDrink.toDays() + "d "
            + (timeSinceDrink.toHours() % 24) + "h "
            + (timeSinceDrink.toMinutes() % 60) + "m "
            + (timeSinceDrink.toSeconds() % 60) + "s");
        if (timeSinceDrink.compareTo(type.getWateringNeed().getDrinkInterval()) < 0) {
            return;
        }
        // Try to drink
        if (farmland.getMoisture() < farmland.getMaximumMoisture()) {
            log("Thirsty");
            hydrated = false;
        } else {
            log("Drink");
            hydrated = true;
            FarmingKeys.LAST_DRINK.setInstant(tag, now);
            shouldSaveTag = true;
            if (!rainedOn) {
                farmland.setMoisture(0);
                block.setBlockData(farmland, false);
            }
        }
        if (wasHydrated != hydrated) {
            FarmingKeys.HYDRATED.setBoolean(tag, hydrated);
            shouldSaveTag = true;
        }
    }

    private void grow() {
        final int light = block.getRelative(0, 1, 0).getLightFromSky();
        if (light < type.getLightRequirement().getMinimumLightLevel() || light > type.getLightRequirement().getMaximumLightLevel()) {
            FarmingKeys.GROWTH_START.setInstant(tag, now);
            shouldSaveTag = true;
            return;
        }
        if (!wasHydrated || !hydrated || !FarmingKeys.GROWTH_START.has(tag)) {
            FarmingKeys.GROWTH_START.setInstant(tag, now);
            shouldSaveTag = true;
            return;
        }
        final Instant growthStart = FarmingKeys.GROWTH_START.getInstant(tag);
        final Duration timeSinceGrowth = Duration.between(growthStart, now);
        log("Time since growth "
            + timeSinceGrowth.toDays() + "d "
            + (timeSinceGrowth.toHours() % 24) + "h "
            + (timeSinceGrowth.toMinutes() % 60) + "m "
            + (timeSinceGrowth.toSeconds() % 60) + "s");
        if (timeSinceGrowth.toHours() < 1) return;
        final int oldAge = FarmingKeys.AGE.getInt(tag, 0);
        final int newAge = oldAge + 1;
        log("Grow " + oldAge + " => " + newAge + " / " + type.getGrowthTime());
        FarmingKeys.AGE.setInt(tag, newAge);
        FarmingKeys.TOTAL_AGE.setInt(tag, 1 + FarmingKeys.TOTAL_AGE.getInt(tag, 0));
        FarmingKeys.GROWTH_START.setInstant(tag, now);
        FarmingKeys.RIPE.setBoolean(tag, newAge >= type.getGrowthTime());
        shouldSaveTag = true;
        final int maxIndex = type.getGrowthStages().size() - 1;
        final int newGrowthStageIndex = Math.min(maxIndex, (newAge * maxIndex) / type.getCropGroup().getGrowthTime());
        updateGrowthStage(newGrowthStageIndex);
    }

    public void updateGrowthStage(final int newGrowthStageIndex) {
        final int oldGrowthStageIndex = FarmingKeys.GROWTH_STAGE.getInt(tag, 0);
        if (oldGrowthStageIndex == newGrowthStageIndex) return;
        log("GrowthStage " + oldGrowthStageIndex + " => " + newGrowthStageIndex + " / " + type.getGrowthStages().size());
        final GrowthStage oldGrowthStage = type.getGrowthStages().get(oldGrowthStageIndex);
        oldGrowthStage.remove(block);
        FarmingKeys.GROWTH_STAGE.setInt(tag, newGrowthStageIndex);
        final GrowthStage newGrowthStage = type.getGrowthStages().get(newGrowthStageIndex);
        newGrowthStage.place(block);
        shouldSaveTag = true;
    }

    public void log(String msg) {
        type.getPlantBlock().log(block, msg);
    }

    public void warn(String msg) {
        type.getPlantBlock().warn(block, msg);
    }
}
