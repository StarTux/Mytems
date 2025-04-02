package com.cavetale.mytems.loot;

import com.cavetale.core.event.skills.SkillsMobKillRewardEvent;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.item.tree.CustomTreeType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;

@RequiredArgsConstructor
public final class LootTableListener implements Listener {
    private final MytemsPlugin plugin;

    @EventHandler(ignoreCancelled = true)
    private void onLootGenerate(LootGenerateEvent event) {
        if (event.getLootTable().getKey().getKey().startsWith("chests/")) {
            if (event.getLootTable().getKey().equals(LootTables.JUNGLE_TEMPLE_DISPENSER.getKey())) return;
            onChestGenerate(event.getLootTable(), event.getLoot());
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == SpawnReason.NATURAL && event.getEntity() instanceof WanderingTrader trader) {
            onWanderingTraderSpawn(trader);
        }
    }

    private void onChestGenerate(LootTable lootTable, List<ItemStack> loot) {
        final Random random = ThreadLocalRandom.current();
        final List<ItemStack> list = new ArrayList<>();
        addTreeSeeds(list, random);
        if (list.isEmpty()) return;
        final ItemStack item = list.get(random.nextInt(list.size()));
        loot.add(item);
    }

    private void onWanderingTraderSpawn(WanderingTrader trader) {
        final Random random = ThreadLocalRandom.current();
        // Loot list
        final List<ItemStack> list = new ArrayList<>();
        addTreeSeeds(list, random);
        if (list.isEmpty()) return;
        final ItemStack item = list.get(random.nextInt(list.size()));
        // Make recipe
        MerchantRecipe recipe = new MerchantRecipe(item, 128);
        recipe.setIngredients(List.of(new ItemStack(Material.EMERALD, 2)));
        List<MerchantRecipe> recipes = new ArrayList<>(trader.getRecipes());
        recipes.add(recipe);
        trader.setRecipes(recipes);
    }

    private int addTreeSeeds(List<ItemStack> list, Random random) {
        int total = 0;
        for (CustomTreeType customTreeType : CustomTreeType.values()) {
            if (customTreeType.getTreeModelCount() == 0) continue;
            for (int i = 0; i < customTreeType.getRandomWeight(); i += 1) {
                list.add(customTreeType.randomLootItemStack(random));
                total += 1;
            }
        }
        return total;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    private void onSkillsMobKillReward(SkillsMobKillRewardEvent event) {
        if (event.getMob().getType() != EntityType.CREEPER) return;
        if (event.getEntityDeathEvent().getDrops().isEmpty()) return;
        if (ThreadLocalRandom.current().nextInt(3) > 0) return;
        event.getEntityDeathEvent().getDrops().add(Mytems.CREEPER_BOOGER.createItemStack());
    }
}
