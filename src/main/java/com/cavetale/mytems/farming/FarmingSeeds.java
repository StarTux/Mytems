package com.cavetale.mytems.farming;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.block.BlockRegistryEntry;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.util.CamelCase.toCamelCase;
import static com.cavetale.mytems.util.Items.tooltip;
import static com.cavetale.mytems.util.Text.wrapLore;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor @Getter
public final class FarmingSeeds implements Mytem {
    private final Mytems key;
    private final FarmingPlantType farmingPlantType;
    private ItemStack prototype;
    private Component displayName;

    public FarmingSeeds(final Mytems key) {
        this.key = key;
        this.farmingPlantType = FarmingPlantType.ofSeedItem(key);
    }

    @Override
    public void enable() {
        displayName = text(toCamelCase(" ", key), GREEN);
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                final List<Component> tooltip = new ArrayList<>();
                tooltip.add(displayName);
                final List<String> seasonNames = new ArrayList<>();
                for (Season season : Season.values()) {
                    if (farmingPlantType.getSeasons().contains(season)) {
                        seasonNames.add(season.getDisplayName().toLowerCase());
                    }
                }
                final List<String> climateNames = new ArrayList<>();
                for (Climate climate : Climate.values()) {
                    if (farmingPlantType.getClimates().contains(climate)) {
                        climateNames.add(climate.getDisplayName().toLowerCase());
                    }
                }
                final String firstSection = "This "
                    + farmingPlantType.getPlantLocation().getDisplayName().toLowerCase()
                    + " plant grows in the " + String.join(" and ", seasonNames)
                    + " in " + String.join(" or ", climateNames) + " climates.";
                tooltip.addAll(wrapLore(firstSection, c -> c.color(GRAY)));
                if (farmingPlantType.getWateringNeed() != WateringNeed.NONE) {
                    final String secondSection = "Water "
                        + (farmingPlantType.getWateringNeed() != WateringNeed.THIRSTY
                           ? "every day"
                           : farmingPlantType.getWateringNeed() != WateringNeed.MODERATE
                           ? "every other day"
                           : "every 4-5 days")
                        + " and you can harvest "
                        + (farmingPlantType.getMinYield() == farmingPlantType.getMaxYield()
                           ? (farmingPlantType.getMinYield() == 1
                              ? "1 crop"
                              : "" + farmingPlantType.getMinYield() + " crops")
                           : farmingPlantType.getMinYield() + "-" + farmingPlantType.getMaxYield() + " crops")
                        + " after " + farmingPlantType.getGrowthTime() + " days.";
                    tooltip.add(empty());
                    tooltip.addAll(wrapLore(secondSection, c -> c.color(GRAY)));
                }
                if (farmingPlantType.doesRegrow()) {
                    final String thirdSection = farmingPlantType.isPerennial()
                        ? "This plant bears fruit multiple times and can survive in the off-season."
                        : "This plant bears fruit multiple times while in season.";
                    tooltip.add(empty());
                    tooltip.addAll(wrapLore(thirdSection, c -> c.color(GRAY)));
                }
                tooltip(meta, tooltip);
                key.markItemMeta(meta);
            });
        farmingPlantType.enable();
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setUseItemInHand(Event.Result.DENY);
        if (!event.hasBlock()) return;
        if (farmingPlantType.getPlantLocation().isOnFarmland()) {
            final Block farmland = event.getClickedBlock();
            if (farmland.getType() != Material.FARMLAND) return;
            if (event.getBlockFace() != BlockFace.UP) return;
            if (BlockRegistryEntry.at(farmland) != null) return;
            final Season season = Season.getCurrentSeason(Instant.now());
            if (!farmingPlantType.getSeasons().contains(season)) {
                player.sendMessage(textOfChildren(text("Cannot plant ", RED),
                                                  key,
                                                  text(farmingPlantType.getDisplayName(), GOLD),
                                                  text(" during ", RED),
                                                  text(season.getDisplayName(), GOLD)));
                player.playSound(player, Sound.BLOCK_VAULT_CLOSE_SHUTTER, SoundCategory.MASTER, 1f, 0.5f);
                return;
            }
            final Climate climate = Climate.of(farmland.getBiome());
            if (!farmingPlantType.getClimates().contains(climate)) {
                player.sendMessage(textOfChildren(text("Cannot plant ", RED),
                                                  key,
                                                  text(farmingPlantType.getDisplayName(), GOLD),
                                                  text(" in a ", RED),
                                                  text(climate.getDisplayName(), GOLD),
                                                  text(" climate", RED)));
                player.playSound(player, Sound.BLOCK_VAULT_CLOSE_SHUTTER, SoundCategory.MASTER, 1f, 0.5f);
                return;
            }
            final GrowthStage growthStage = farmingPlantType.getGrowthStages().get(0);
            if (!growthStage.checkAdditionalBlocksAreEmpty(farmland)) return;
            // No return
            if (player.getGameMode() != GameMode.CREATIVE) {
                item.subtract(1);
            }
            farmingPlantType.getBlockRegistryEntry().setBlockId(farmland);
            growthStage.place(farmland);
            farmland.getWorld().playSound(farmland.getLocation().add(0.5, 1.5, 0.5), Sound.ITEM_CROP_PLANT, SoundCategory.MASTER, 1f, 1f);
        }
    }
}
