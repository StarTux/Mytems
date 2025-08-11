package com.cavetale.mytems.loot;

import com.cavetale.mytems.Mytems;
import java.util.List;
import org.bukkit.inventory.ItemStack;

public final class Loot {
    private static Loot instance;

    public static Loot loot() {
        if (instance == null) {
            instance = new Loot();
        }
        return instance;
    }

    public void populatePrimeLoot(List<ItemStack> result, int minItemsToAdd) {
        final int goalSize = result.size() + minItemsToAdd;
        while (result.size() < goalSize) {
            result.add(Mytems.RUBY_COIN.createItemStack());
            result.add(Mytems.MAGIC_CAPE.createItemStack());
            result.add(Mytems.MOBSLAYER.createItemStack());
            result.add(Mytems.BINGO_BUKKIT.createItemStack());
            result.add(Mytems.WITCH_BROOM.createItemStack());
            result.add(Mytems.SNEAKERS.createItemStack());
            result.add(Mytems.UNICORN_HORN.createItemStack());
            result.add(Mytems.SEALED_CAVEBOY.createItemStack());
            result.add(Mytems.SCISSORS.createItemStack());
            result.add(Mytems.STRUCTURE_FINDER.createItemStack());
            result.add(Mytems.DEFLECTOR_SHIELD.createItemStack());
            result.add(Mytems.COPPER_SPLEEF_SHOVEL.createItemStack());
            result.add(Mytems.DIVIDERS.createItemStack());
            result.add(Mytems.YARDSTICK.createItemStack());
            result.add(Mytems.LUMINATOR.createItemStack());
            result.add(Mytems.SCUBA_HELMET.createItemStack());
            result.add(Mytems.MINER_HELMET.createItemStack());
            result.add(Mytems.EMPTY_WATERING_CAN.createItemStack());
            result.add(Mytems.IRON_SCYTHE.createItemStack());
            result.add(Mytems.TREE_CHOPPER.createItemStack());
            result.add(Mytems.HASTY_PICKAXE.createItemStack());
            result.add(Mytems.BINOCULARS.createItemStack());
            result.add(Mytems.LOVE_POTION.createItemStack());
            result.add(Mytems.WOODEN_GOLF_CLUB.createItemStack());
        }
    }

    public void populateRegularLoot(List<ItemStack> result, int minItemsToAdd) {
        final int goalSize = result.size() + minItemsToAdd;
        while (result.size() < goalSize) {
            result.add(Mytems.GOLDEN_COIN.createItemStack());
            result.add(Mytems.DIAMOND_COIN.createItemStack());
            // result.add(Mytems.BLUNDERBUSS.createItemStack());
            // result.add(Mytems.CAPTAINS_CUTLASS.createItemStack());
            // result.add(Mytems.ENDERBALL.createItemStack());
            // result.add(Mytems.MAGNIFYING_GLASS.createItemStack());
            // result.add(Mytems.FERTILIZER.createItemStack(64));
            // result.add(Mytems.SNOW_SHOVEL.createItemStack());
            // result.add(Mytems.COLORFALL_HOURGLASS.createItemStack());
        }
    }
}
