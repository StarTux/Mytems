package com.cavetale.mytems.loot;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsCategory;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.MytemsTag;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
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
    private Random random = new Random();

    @EventHandler(ignoreCancelled = true)
    private void onLootGenerate(LootGenerateEvent event) {
        if (event.getLootTable().getKey().getKey().startsWith("chests/")) {
            if (event.getLootTable().getKey().equals(LootTables.JUNGLE_TEMPLE_DISPENSER.getKey())) return;
            onChestGenerate(event.getLootTable(), event.getLoot());
        }
    }

    private void onChestGenerate(LootTable lootTable, List<ItemStack> loot) {
        List<Mytems> list = MytemsTag.of(MytemsCategory.TREE_SEED).getMytems();
        Mytems mytems = list.get(random.nextInt(list.size()));
        int amount = random.nextInt(16) + 1;
        loot.add(mytems.createItemStack(amount));
    }

    @EventHandler(ignoreCancelled = true)
    private void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == SpawnReason.NATURAL && event.getEntity() instanceof WanderingTrader trader) {
            onWanderingTraderSpawn(trader);
        }
    }

    private void onWanderingTraderSpawn(WanderingTrader trader) {
        List<Mytems> list = MytemsTag.of(MytemsCategory.TREE_SEED).getMytems();
        Mytems mytems = list.get(random.nextInt(list.size()));
        MerchantRecipe recipe = new MerchantRecipe(mytems.createItemStack(1), 128);
        recipe.setIngredients(List.of(new ItemStack(Material.EMERALD, 2)));
        List<MerchantRecipe> recipes = new ArrayList<>(trader.getRecipes());
        recipes.add(recipe);
        trader.setRecipes(recipes);
    }
}
