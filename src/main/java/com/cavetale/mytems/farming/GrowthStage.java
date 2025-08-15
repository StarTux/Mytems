package com.cavetale.mytems.farming;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.mytems.block.BlockRegistryEntry;
import com.cavetale.worldmarker.block.BlockMarker;
import io.papermc.paper.datacomponent.DataComponentTypes;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import static com.cavetale.mytems.MytemsPlugin.namespacedKey;

/**
 * Represent one growth stage of a plant, with everything we need to
 * know to place and remove said stage, and keep it updated.
 *
 * These method will be used from the corresponding FarmingSeeds
 * object.
 */
@Getter
@RequiredArgsConstructor
public enum GrowthStage {
    KING_RICHARD_LEEK_0(FarmingPlantType.KING_RICHARD_LEEK, Model.X),
    KING_RICHARD_LEEK_1(FarmingPlantType.KING_RICHARD_LEEK, Model.X),
    KING_RICHARD_LEEK_2(FarmingPlantType.KING_RICHARD_LEEK, Model.X),
    KING_RICHARD_LEEK_3(FarmingPlantType.KING_RICHARD_LEEK, Model.X),

    BLUE_POPCORN_0(FarmingPlantType.BLUE_POPCORN, Model.X),
    BLUE_POPCORN_1(FarmingPlantType.BLUE_POPCORN, Model.X),
    BLUE_POPCORN_2(FarmingPlantType.BLUE_POPCORN, Model.XTALL),
    BLUE_POPCORN_3(FarmingPlantType.BLUE_POPCORN, Model.XTALL),
    BLUE_POPCORN_4(FarmingPlantType.BLUE_POPCORN, Model.X3TALL),
    BLUE_POPCORN_5(FarmingPlantType.BLUE_POPCORN, Model.X3TALL),

    ASHEN_NETHER_WART_0(FarmingPlantType.ASHEN_NETHER_WART, Model.X),
    ASHEN_NETHER_WART_1(FarmingPlantType.ASHEN_NETHER_WART, Model.X),
    ASHEN_NETHER_WART_2(FarmingPlantType.ASHEN_NETHER_WART, Model.X),
    ASHEN_NETHER_WART_3(FarmingPlantType.ASHEN_NETHER_WART, Model.X),

    GLOOMROT_NETHER_WART_0(FarmingPlantType.GLOOMROT_NETHER_WART, Model.X),
    GLOOMROT_NETHER_WART_1(FarmingPlantType.GLOOMROT_NETHER_WART, Model.X),
    GLOOMROT_NETHER_WART_2(FarmingPlantType.GLOOMROT_NETHER_WART, Model.X),
    GLOOMROT_NETHER_WART_3(FarmingPlantType.GLOOMROT_NETHER_WART, Model.X),

    INFERNAL_NETHER_WART_0(FarmingPlantType.INFERNAL_NETHER_WART, Model.X),
    INFERNAL_NETHER_WART_1(FarmingPlantType.INFERNAL_NETHER_WART, Model.X),
    INFERNAL_NETHER_WART_2(FarmingPlantType.INFERNAL_NETHER_WART, Model.X),
    INFERNAL_NETHER_WART_3(FarmingPlantType.INFERNAL_NETHER_WART, Model.X),

    GREEN_CABBAGE_0(FarmingPlantType.GREEN_CABBAGE, Model.X),
    GREEN_CABBAGE_1(FarmingPlantType.GREEN_CABBAGE, Model.X),
    GREEN_CABBAGE_2(FarmingPlantType.GREEN_CABBAGE, Model.X),
    GREEN_CABBAGE_3(FarmingPlantType.GREEN_CABBAGE, Model.X),
    GREEN_CABBAGE_4(FarmingPlantType.GREEN_CABBAGE, Model.X),
    GREEN_CABBAGE_5(FarmingPlantType.GREEN_CABBAGE, Model.X),

    YELLOW_PITAYA_0(FarmingPlantType.YELLOW_PITAYA, Model.X),
    YELLOW_PITAYA_1(FarmingPlantType.YELLOW_PITAYA, Model.X),
    YELLOW_PITAYA_2(FarmingPlantType.YELLOW_PITAYA, Model.XTALL),
    YELLOW_PITAYA_3(FarmingPlantType.YELLOW_PITAYA, Model.XTALL),
    YELLOW_PITAYA_4(FarmingPlantType.YELLOW_PITAYA, Model.XTALL),
    YELLOW_PITAYA_5(FarmingPlantType.YELLOW_PITAYA, Model.XTALL),
    YELLOW_PITAYA_6(FarmingPlantType.YELLOW_PITAYA, Model.XTALL),
    YELLOW_PITAYA_7(FarmingPlantType.YELLOW_PITAYA, Model.XTALL),

    CAMELIA_TEA_0(FarmingPlantType.CAMELIA_TEA, Model.X),
    CAMELIA_TEA_1(FarmingPlantType.CAMELIA_TEA, Model.X),
    CAMELIA_TEA_2(FarmingPlantType.CAMELIA_TEA, Model.X),
    CAMELIA_TEA_3(FarmingPlantType.CAMELIA_TEA, Model.X),

    CANTALOUPE_0(FarmingPlantType.CANTALOUPE, Model.X),
    CANTALOUPE_1(FarmingPlantType.CANTALOUPE, Model.X),
    CANTALOUPE_2(FarmingPlantType.CANTALOUPE, Model.X),
    CANTALOUPE_3(FarmingPlantType.CANTALOUPE, Model.X),

    RUSSET_POTATO_0(FarmingPlantType.RUSSET_POTATO, Model.X),
    RUSSET_POTATO_1(FarmingPlantType.RUSSET_POTATO, Model.X),
    RUSSET_POTATO_2(FarmingPlantType.RUSSET_POTATO, Model.X),
    RUSSET_POTATO_3(FarmingPlantType.RUSSET_POTATO, Model.X),

    SWEET_POTATO_0(FarmingPlantType.SWEET_POTATO, Model.X),
    SWEET_POTATO_1(FarmingPlantType.SWEET_POTATO, Model.X),
    SWEET_POTATO_2(FarmingPlantType.SWEET_POTATO, Model.X),
    SWEET_POTATO_3(FarmingPlantType.SWEET_POTATO, Model.X),

    RED_POTATO_0(FarmingPlantType.RED_POTATO, Model.X),
    RED_POTATO_1(FarmingPlantType.RED_POTATO, Model.X),
    RED_POTATO_2(FarmingPlantType.RED_POTATO, Model.X),
    RED_POTATO_3(FarmingPlantType.RED_POTATO, Model.X),

    RED_GARDEN_BEET_0(FarmingPlantType.RED_GARDEN_BEET, Model.X),
    RED_GARDEN_BEET_1(FarmingPlantType.RED_GARDEN_BEET, Model.X),
    RED_GARDEN_BEET_2(FarmingPlantType.RED_GARDEN_BEET, Model.X),
    RED_GARDEN_BEET_3(FarmingPlantType.RED_GARDEN_BEET, Model.X),

    COMMON_OAT_0(FarmingPlantType.COMMON_OAT, Model.X),
    COMMON_OAT_1(FarmingPlantType.COMMON_OAT, Model.X),
    COMMON_OAT_2(FarmingPlantType.COMMON_OAT, Model.X),
    COMMON_OAT_3(FarmingPlantType.COMMON_OAT, Model.X),
    COMMON_OAT_4(FarmingPlantType.COMMON_OAT, Model.X),
    COMMON_OAT_5(FarmingPlantType.COMMON_OAT, Model.X),
    COMMON_OAT_6(FarmingPlantType.COMMON_OAT, Model.X),
    COMMON_OAT_7(FarmingPlantType.COMMON_OAT, Model.X),

    HARD_WHEAT_0(FarmingPlantType.HARD_WHEAT, Model.X),
    HARD_WHEAT_1(FarmingPlantType.HARD_WHEAT, Model.X),
    HARD_WHEAT_2(FarmingPlantType.HARD_WHEAT, Model.X),
    HARD_WHEAT_3(FarmingPlantType.HARD_WHEAT, Model.X),
    HARD_WHEAT_4(FarmingPlantType.HARD_WHEAT, Model.X),
    HARD_WHEAT_5(FarmingPlantType.HARD_WHEAT, Model.X),
    HARD_WHEAT_6(FarmingPlantType.HARD_WHEAT, Model.X),
    HARD_WHEAT_7(FarmingPlantType.HARD_WHEAT, Model.X),

    SILVERSKIN_GARLIC_0(FarmingPlantType.SILVERSKIN_GARLIC, Model.X),
    SILVERSKIN_GARLIC_1(FarmingPlantType.SILVERSKIN_GARLIC, Model.X),
    SILVERSKIN_GARLIC_2(FarmingPlantType.SILVERSKIN_GARLIC, Model.X),
    SILVERSKIN_GARLIC_3(FarmingPlantType.SILVERSKIN_GARLIC, Model.X),

    DENT_CORN_0(FarmingPlantType.DENT_CORN, Model.X),
    DENT_CORN_1(FarmingPlantType.DENT_CORN, Model.X),
    DENT_CORN_2(FarmingPlantType.DENT_CORN, Model.XTALL),
    DENT_CORN_3(FarmingPlantType.DENT_CORN, Model.XTALL),
    DENT_CORN_4(FarmingPlantType.DENT_CORN, Model.X3TALL),
    DENT_CORN_5(FarmingPlantType.DENT_CORN, Model.X3TALL),

    SWEET_CORN_0(FarmingPlantType.SWEET_CORN, Model.X),
    SWEET_CORN_1(FarmingPlantType.SWEET_CORN, Model.X),
    SWEET_CORN_2(FarmingPlantType.SWEET_CORN, Model.XTALL),
    SWEET_CORN_3(FarmingPlantType.SWEET_CORN, Model.XTALL),
    SWEET_CORN_4(FarmingPlantType.SWEET_CORN, Model.X3TALL),
    SWEET_CORN_5(FarmingPlantType.SWEET_CORN, Model.X3TALL),

    GLOWROOT_0(FarmingPlantType.GLOWROOT, Model.X),
    GLOWROOT_1(FarmingPlantType.GLOWROOT, Model.X),
    GLOWROOT_2(FarmingPlantType.GLOWROOT, Model.X),
    GLOWROOT_3(FarmingPlantType.GLOWROOT, Model.X),
    GLOWROOT_4(FarmingPlantType.GLOWROOT, Model.X),
    GLOWROOT_5(FarmingPlantType.GLOWROOT, Model.X),

    SUNSET_TEA_0(FarmingPlantType.SUNSET_TEA, Model.X),
    SUNSET_TEA_1(FarmingPlantType.SUNSET_TEA, Model.X),
    SUNSET_TEA_2(FarmingPlantType.SUNSET_TEA, Model.X),
    SUNSET_TEA_3(FarmingPlantType.SUNSET_TEA, Model.X),

    CRIOLLO_COCOA_0(FarmingPlantType.CRIOLLO_COCOA, Model.X),
    CRIOLLO_COCOA_1(FarmingPlantType.CRIOLLO_COCOA, Model.X),
    CRIOLLO_COCOA_2(FarmingPlantType.CRIOLLO_COCOA, Model.X),

    HERITAGE_RASPBERRY_0(FarmingPlantType.HERITAGE_RASPBERRY, Model.XTALL),
    HERITAGE_RASPBERRY_1(FarmingPlantType.HERITAGE_RASPBERRY, Model.XTALL),
    HERITAGE_RASPBERRY_2(FarmingPlantType.HERITAGE_RASPBERRY, Model.XTALL),
    HERITAGE_RASPBERRY_3(FarmingPlantType.HERITAGE_RASPBERRY, Model.XTALL),

    WATERMELON_0(FarmingPlantType.WATERMELON, Model.X),
    WATERMELON_1(FarmingPlantType.WATERMELON, Model.X),
    WATERMELON_2(FarmingPlantType.WATERMELON, Model.X),
    WATERMELON_3(FarmingPlantType.WATERMELON, Model.X),

    NORTHERN_BLUEBERRIES_0(FarmingPlantType.NORTHERN_BLUEBERRY, Model.X),
    NORTHERN_BLUEBERRIES_1(FarmingPlantType.NORTHERN_BLUEBERRY, Model.X),
    NORTHERN_BLUEBERRIES_2(FarmingPlantType.NORTHERN_BLUEBERRY, Model.X),
    NORTHERN_BLUEBERRIES_3(FarmingPlantType.NORTHERN_BLUEBERRY, Model.X),

    WARPAL_BERRIES_0(FarmingPlantType.WARPAL_BERRY, Model.X),
    WARPAL_BERRIES_1(FarmingPlantType.WARPAL_BERRY, Model.X),
    WARPAL_BERRIES_2(FarmingPlantType.WARPAL_BERRY, Model.X),
    WARPAL_BERRIES_3(FarmingPlantType.WARPAL_BERRY, Model.X),

    JASMINE_RICE_0(FarmingPlantType.JASMINE_RICE, Model.X),
    JASMINE_RICE_1(FarmingPlantType.JASMINE_RICE, Model.X),
    JASMINE_RICE_2(FarmingPlantType.JASMINE_RICE, Model.X),
    JASMINE_RICE_3(FarmingPlantType.JASMINE_RICE, Model.X),
    JASMINE_RICE_4(FarmingPlantType.JASMINE_RICE, Model.X),
    JASMINE_RICE_5(FarmingPlantType.JASMINE_RICE, Model.X),
    JASMINE_RICE_6(FarmingPlantType.JASMINE_RICE, Model.X),
    JASMINE_RICE_7(FarmingPlantType.JASMINE_RICE, Model.X),

    WAKAME_BOTTOM_0(FarmingPlantType.WAKAME, Model.X),
    WAKAME_BOTTOM_1(FarmingPlantType.WAKAME, Model.X),
    WAKAME_MIDDLE(FarmingPlantType.WAKAME, Model.X),
    WAKAME_TOP(FarmingPlantType.WAKAME, Model.X),

    HEIRLOOM_TOMATO_0(FarmingPlantType.HEIRLOOM_TOMATO, Model.X),
    HEIRLOOM_TOMATO_1(FarmingPlantType.HEIRLOOM_TOMATO, Model.X),
    HEIRLOOM_TOMATO_2(FarmingPlantType.HEIRLOOM_TOMATO, Model.X),
    HEIRLOOM_TOMATO_3(FarmingPlantType.HEIRLOOM_TOMATO, Model.X),
    HEIRLOOM_TOMATO_4(FarmingPlantType.HEIRLOOM_TOMATO, Model.X),
    HEIRLOOM_TOMATO_5(FarmingPlantType.HEIRLOOM_TOMATO, Model.X),
    HEIRLOOM_TOMATO_6(FarmingPlantType.HEIRLOOM_TOMATO, Model.X),
    HEIRLOOM_TOMATO_7(FarmingPlantType.HEIRLOOM_TOMATO, Model.X),

    GARDEN_CUCUMBER_0(FarmingPlantType.GARDEN_CUCUMBER, Model.X),
    GARDEN_CUCUMBER_1(FarmingPlantType.GARDEN_CUCUMBER, Model.X),
    GARDEN_CUCUMBER_2(FarmingPlantType.GARDEN_CUCUMBER, Model.X),
    GARDEN_CUCUMBER_3(FarmingPlantType.GARDEN_CUCUMBER, Model.X),

    GOLD_KIWI_0(FarmingPlantType.GOLD_KIWI, Model.X),
    GOLD_KIWI_1(FarmingPlantType.GOLD_KIWI, Model.X),
    GOLD_KIWI_2(FarmingPlantType.GOLD_KIWI, Model.X),
    GOLD_KIWI_3(FarmingPlantType.GOLD_KIWI, Model.X),
    GOLD_KIWI_4(FarmingPlantType.GOLD_KIWI, Model.X),

    HOSS_AVOCADO_0(FarmingPlantType.HASS_AVOCADO, Model.X),

    ETHEREAL_CHORUS_FRUIT_0(FarmingPlantType.ETHEREAL_CHORUS_FRUIT, Model.X),
    ETHEREAL_CHORUS_FRUIT_1(FarmingPlantType.ETHEREAL_CHORUS_FRUIT, Model.X),
    ETHEREAL_CHORUS_FRUIT_2(FarmingPlantType.ETHEREAL_CHORUS_FRUIT, Model.X),
    ETHEREAL_CHORUS_FRUIT_3(FarmingPlantType.ETHEREAL_CHORUS_FRUIT, Model.X),
    ETHEREAL_CHORUS_FRUIT_4(FarmingPlantType.ETHEREAL_CHORUS_FRUIT, Model.X),
    ETHEREAL_CHORUS_FRUIT_5(FarmingPlantType.ETHEREAL_CHORUS_FRUIT, Model.X),
    ETHEREAL_CHORUS_FRUIT_6(FarmingPlantType.ETHEREAL_CHORUS_FRUIT, Model.X),
    ETHEREAL_CHORUS_FRUIT_7(FarmingPlantType.ETHEREAL_CHORUS_FRUIT, Model.X),

    PORTOBELLO_MUSHROOM_0(FarmingPlantType.PORTOBELLO_MUSHROOM, Model.X),
    PORTOBELLO_MUSHROOM_1(FarmingPlantType.PORTOBELLO_MUSHROOM, Model.X),
    PORTOBELLO_MUSHROOM_2(FarmingPlantType.PORTOBELLO_MUSHROOM, Model.X),
    PORTOBELLO_MUSHROOM_3(FarmingPlantType.PORTOBELLO_MUSHROOM, Model.X),

    FALSE_PICKLE_0(FarmingPlantType.FALSE_PICKLE, Model.X),
    FALSE_PICKLE_1(FarmingPlantType.FALSE_PICKLE, Model.X),
    FALSE_PICKLE_2(FarmingPlantType.FALSE_PICKLE, Model.X),
    FALSE_PICKLE_3(FarmingPlantType.FALSE_PICKLE, Model.X),

    GREEN_BELL_PEPPER_0(FarmingPlantType.GREEN_BELL_PEPPER, Model.X),
    GREEN_BELL_PEPPER_1(FarmingPlantType.GREEN_BELL_PEPPER, Model.X),
    GREEN_BELL_PEPPER_2(FarmingPlantType.GREEN_BELL_PEPPER, Model.X),
    GREEN_BELL_PEPPER_3(FarmingPlantType.GREEN_BELL_PEPPER, Model.X),
    GREEN_BELL_PEPPER_4(FarmingPlantType.GREEN_BELL_PEPPER, Model.X),
    GREEN_BELL_PEPPER_5(FarmingPlantType.GREEN_BELL_PEPPER, Model.X),
    GREEN_BELL_PEPPER_6(FarmingPlantType.GREEN_BELL_PEPPER, Model.X),
    GREEN_BELL_PEPPER_7(FarmingPlantType.GREEN_BELL_PEPPER, Model.X),

    RED_PITAYA_0(FarmingPlantType.RED_PITAYA, Model.X),
    RED_PITAYA_1(FarmingPlantType.RED_PITAYA, Model.X),
    RED_PITAYA_2(FarmingPlantType.RED_PITAYA, Model.XTALL),
    RED_PITAYA_3(FarmingPlantType.RED_PITAYA, Model.XTALL),
    RED_PITAYA_4(FarmingPlantType.RED_PITAYA, Model.XTALL),
    RED_PITAYA_5(FarmingPlantType.RED_PITAYA, Model.XTALL),
    RED_PITAYA_6(FarmingPlantType.RED_PITAYA, Model.XTALL),
    RED_PITAYA_7(FarmingPlantType.RED_PITAYA, Model.XTALL),

    HOLLOW_CROWN_PARSNIP_0(FarmingPlantType.HOLLOW_CROWN_PARSNIP, Model.X),
    HOLLOW_CROWN_PARSNIP_1(FarmingPlantType.HOLLOW_CROWN_PARSNIP, Model.X),
    HOLLOW_CROWN_PARSNIP_2(FarmingPlantType.HOLLOW_CROWN_PARSNIP, Model.X),
    HOLLOW_CROWN_PARSNIP_3(FarmingPlantType.HOLLOW_CROWN_PARSNIP, Model.X),

    PICUAL_OLIVE_0(FarmingPlantType.PICUAL_OLIVE, Model.XTALL),
    PICUAL_OLIVE_1(FarmingPlantType.PICUAL_OLIVE, Model.XTALL),
    PICUAL_OLIVE_2(FarmingPlantType.PICUAL_OLIVE, Model.XTALL),

    ARABICA_COFFEE_0(FarmingPlantType.ARABICA_COFFEE, Model.X),
    ARABICA_COFFEE_1(FarmingPlantType.ARABICA_COFFEE, Model.X),
    ARABICA_COFFEE_2(FarmingPlantType.ARABICA_COFFEE, Model.X),
    ARABICA_COFFEE_3(FarmingPlantType.ARABICA_COFFEE, Model.X),

    DWARF_COCONUT_0(FarmingPlantType.DWARF_COCONUT, Model.X),
    DWARF_COCONUT_1(FarmingPlantType.DWARF_COCONUT, Model.X),
    DWARF_COCONUT_2(FarmingPlantType.DWARF_COCONUT, Model.X),
    DWARF_COCONUT_3(FarmingPlantType.DWARF_COCONUT, Model.X),
    DWARF_COCONUT_4(FarmingPlantType.DWARF_COCONUT, Model.X),
    DWARF_COCONUT_5(FarmingPlantType.DWARF_COCONUT, Model.X),
    DWARF_COCONUT_6(FarmingPlantType.DWARF_COCONUT, Model.X),
    DWARF_COCONUT_7(FarmingPlantType.DWARF_COCONUT, Model.X),

    RADISH_0(FarmingPlantType.CHERRY_BELLE_RADISH, Model.X),
    RADISH_1(FarmingPlantType.CHERRY_BELLE_RADISH, Model.X),
    RADISH_2(FarmingPlantType.CHERRY_BELLE_RADISH, Model.X),
    RADISH_3(FarmingPlantType.CHERRY_BELLE_RADISH, Model.X),

    HONEYDEW_0(FarmingPlantType.HONEYDEW, Model.X),
    HONEYDEW_1(FarmingPlantType.HONEYDEW, Model.X),
    HONEYDEW_2(FarmingPlantType.HONEYDEW, Model.X),
    HONEYDEW_3(FarmingPlantType.HONEYDEW, Model.X),

    GOOSEBERRY_0(FarmingPlantType.GOOSEBERRY, Model.XTALL),
    GOOSEBERRY_1(FarmingPlantType.GOOSEBERRY, Model.XTALL),
    GOOSEBERRY_3(FarmingPlantType.GOOSEBERRY, Model.XTALL),
    GOOSEBERRY_4(FarmingPlantType.GOOSEBERRY, Model.XTALL),

    SUGAR_PIE_PUMPKIN_0(FarmingPlantType.SUGAR_PIE_PUMPKIN, Model.X),
    SUGAR_PIE_PUMPKIN_1(FarmingPlantType.SUGAR_PIE_PUMPKIN, Model.X),
    SUGAR_PIE_PUMPKIN_2(FarmingPlantType.SUGAR_PIE_PUMPKIN, Model.X),
    SUGAR_PIE_PUMPKIN_3(FarmingPlantType.SUGAR_PIE_PUMPKIN, Model.X),
    SUGAR_PIE_PUMPKIN_4(FarmingPlantType.SUGAR_PIE_PUMPKIN, Model.X),
    SUGAR_PIE_PUMPKIN_5(FarmingPlantType.SUGAR_PIE_PUMPKIN, Model.X),
    SUGAR_PIE_PUMPKIN_6(FarmingPlantType.SUGAR_PIE_PUMPKIN, Model.X),

    CAMAROSA_STRAWBERRY_0(FarmingPlantType.CAMAROSA_STRAWBERRY, Model.X),
    CAMAROSA_STRAWBERRY_1(FarmingPlantType.CAMAROSA_STRAWBERRY, Model.X),
    CAMAROSA_STRAWBERRY_2(FarmingPlantType.CAMAROSA_STRAWBERRY, Model.X),
    CAMAROSA_STRAWBERRY_3(FarmingPlantType.CAMAROSA_STRAWBERRY, Model.X),

    SPICY_FIG_0(FarmingPlantType.SPICY_FIG, Model.X),
    SPICY_FIG_1(FarmingPlantType.SPICY_FIG, Model.X),
    SPICY_FIG_2(FarmingPlantType.SPICY_FIG, Model.X),
    SPICY_FIG_3(FarmingPlantType.SPICY_FIG, Model.X),

    MOREL_MUSHROOM_0(FarmingPlantType.MOREL_MUSHROOM, Model.X),
    MOREL_MUSHROOM_1(FarmingPlantType.MOREL_MUSHROOM, Model.X),
    MOREL_MUSHROOM_2(FarmingPlantType.MOREL_MUSHROOM, Model.X),
    MOREL_MUSHROOM_3(FarmingPlantType.MOREL_MUSHROOM, Model.X),

    NAVEL_ORANGE_0(FarmingPlantType.NAVEL_ORANGE, Model.X),

    PURPLE_CARROT0(FarmingPlantType.PURPLE_CARROT, Model.X),
    PURPLE_CARROT1(FarmingPlantType.PURPLE_CARROT, Model.X),
    PURPLE_CARROT2(FarmingPlantType.PURPLE_CARROT, Model.X),
    PURPLE_CARROT3(FarmingPlantType.PURPLE_CARROT, Model.X),

    MIDNIGHT_DRAGONBLOOM_0(FarmingPlantType.MIDNIGHT_DRAGONBLOOM, Model.X),
    MIDNIGHT_DRAGONBLOOM_1(FarmingPlantType.MIDNIGHT_DRAGONBLOOM, Model.X),
    MIDNIGHT_DRAGONBLOOM_2(FarmingPlantType.MIDNIGHT_DRAGONBLOOM, Model.XTALL),
    MIDNIGHT_DRAGONBLOOM_3(FarmingPlantType.MIDNIGHT_DRAGONBLOOM, Model.XTALL),
    MIDNIGHT_DRAGONBLOOM_4(FarmingPlantType.MIDNIGHT_DRAGONBLOOM, Model.XTALL),
    MIDNIGHT_DRAGONBLOOM_5(FarmingPlantType.MIDNIGHT_DRAGONBLOOM, Model.XTALL),
    MIDNIGHT_DRAGONBLOOM_6(FarmingPlantType.MIDNIGHT_DRAGONBLOOM, Model.XTALL),
    MIDNIGHT_DRAGONBLOOM_7(FarmingPlantType.MIDNIGHT_DRAGONBLOOM, Model.XTALL),

    HERBAL_TEA_0(FarmingPlantType.HERBAL_TEA, Model.X),
    HERBAL_TEA_1(FarmingPlantType.HERBAL_TEA, Model.X),
    HERBAL_TEA_2(FarmingPlantType.HERBAL_TEA, Model.X),
    HERBAL_TEA_3(FarmingPlantType.HERBAL_TEA, Model.X),

    CONCORD_GRAPE_1(FarmingPlantType.CONCORD_GRAPE, Model.XTALL),
    CONCORD_GRAPE_2(FarmingPlantType.CONCORD_GRAPE, Model.XTALL),
    CONCORD_GRAPE_3(FarmingPlantType.CONCORD_GRAPE, Model.XTALL),
    CONCORD_GRAPE_4(FarmingPlantType.CONCORD_GRAPE, Model.XTALL),
    CONCORD_GRAPE_5(FarmingPlantType.CONCORD_GRAPE, Model.XTALL),

    IMPERATOR_CARROT_0(FarmingPlantType.IMPERATOR_CARROT, Model.X),
    IMPERATOR_CARROT_1(FarmingPlantType.IMPERATOR_CARROT, Model.X),
    IMPERATOR_CARROT_2(FarmingPlantType.IMPERATOR_CARROT, Model.X),
    IMPERATOR_CARROT_3(FarmingPlantType.IMPERATOR_CARROT, Model.X),

    NORI_BOTTOM_0(FarmingPlantType.NORI, Model.X),
    NORI_BOTTOM_1(FarmingPlantType.NORI, Model.X),
    NORI_MIDDLE(FarmingPlantType.NORI, Model.X),
    NORI_TOP(FarmingPlantType.NORI, Model.X),

    BRIGHT_GLOW_BERRY_0(FarmingPlantType.BRIGHT_GLOW_BERRY, Model.X),
    BRIGHT_GLOW_BERRY_1(FarmingPlantType.BRIGHT_GLOW_BERRY, Model.X),
    BRIGHT_GLOW_BERRY_2(FarmingPlantType.BRIGHT_GLOW_BERRY, Model.X),

    GLOBE_EGGPLANT_0(FarmingPlantType.GLOBE_EGGPLANT, Model.X),
    GLOBE_EGGPLANT_1(FarmingPlantType.GLOBE_EGGPLANT, Model.X),
    GLOBE_EGGPLANT_2(FarmingPlantType.GLOBE_EGGPLANT, Model.X),
    GLOBE_EGGPLANT_3(FarmingPlantType.GLOBE_EGGPLANT, Model.X),
    GLOBE_EGGPLANT_4(FarmingPlantType.GLOBE_EGGPLANT, Model.X),
    GLOBE_EGGPLANT_5(FarmingPlantType.GLOBE_EGGPLANT, Model.X),
    GLOBE_EGGPLANT_6(FarmingPlantType.GLOBE_EGGPLANT, Model.X),
    GLOBE_EGGPLANT_7(FarmingPlantType.GLOBE_EGGPLANT, Model.X),

    FLY_AGARIC_MUSHROOM_0(FarmingPlantType.FLY_AGARIC_MUSHROOM, Model.X),
    FLY_AGARIC_MUSHROOM_1(FarmingPlantType.FLY_AGARIC_MUSHROOM, Model.X),
    FLY_AGARIC_MUSHROOM_2(FarmingPlantType.FLY_AGARIC_MUSHROOM, Model.X),
    FLY_AGARIC_MUSHROOM_3(FarmingPlantType.FLY_AGARIC_MUSHROOM, Model.X),

    YELLOW_ONION_0(FarmingPlantType.YELLOW_ONION, Model.X),
    YELLOW_ONION_1(FarmingPlantType.YELLOW_ONION, Model.X),
    YELLOW_ONION_2(FarmingPlantType.YELLOW_ONION, Model.X),
    YELLOW_ONION_3(FarmingPlantType.YELLOW_ONION, Model.X),

    BLACKBERRY_0(FarmingPlantType.BLACKBERRY, Model.XTALL),
    BLACKBERRY_1(FarmingPlantType.BLACKBERRY, Model.XTALL),
    BLACKBERRY_2(FarmingPlantType.BLACKBERRY, Model.XTALL),
    BLACKBERRY_3(FarmingPlantType.BLACKBERRY, Model.XTALL),

    GREEN_SQUASH_0(FarmingPlantType.GREEN_SQUASH, Model.X),
    GREEN_SQUASH_1(FarmingPlantType.GREEN_SQUASH, Model.X),
    GREEN_SQUASH_2(FarmingPlantType.GREEN_SQUASH, Model.X),
    GREEN_SQUASH_3(FarmingPlantType.GREEN_SQUASH, Model.X),
    GREEN_SQUASH_4(FarmingPlantType.GREEN_SQUASH, Model.X),
    GREEN_SQUASH_5(FarmingPlantType.GREEN_SQUASH, Model.X),
    GREEN_SQUASH_6(FarmingPlantType.GREEN_SQUASH, Model.X),

    CANDY_GRAPE_1(FarmingPlantType.CANDY_GRAPE, Model.XTALL),
    CANDY_GRAPE_2(FarmingPlantType.CANDY_GRAPE, Model.XTALL),
    CANDY_GRAPE_3(FarmingPlantType.CANDY_GRAPE, Model.XTALL),
    CANDY_GRAPE_4(FarmingPlantType.CANDY_GRAPE, Model.XTALL),
    CANDY_GRAPE_5(FarmingPlantType.CANDY_GRAPE, Model.XTALL),


    TURBO_COFFEE_0(FarmingPlantType.TURBO_COFFEE, Model.X),
    TURBO_COFFEE_1(FarmingPlantType.TURBO_COFFEE, Model.X),
    TURBO_COFFEE_2(FarmingPlantType.TURBO_COFFEE, Model.X),
    TURBO_COFFEE_3(FarmingPlantType.TURBO_COFFEE, Model.X),

    WHITE_GRAPE_1(FarmingPlantType.WHITE_GRAPE, Model.XTALL),
    WHITE_GRAPE_2(FarmingPlantType.WHITE_GRAPE, Model.XTALL),
    WHITE_GRAPE_3(FarmingPlantType.WHITE_GRAPE, Model.XTALL),
    WHITE_GRAPE_4(FarmingPlantType.WHITE_GRAPE, Model.XTALL),
    WHITE_GRAPE_5(FarmingPlantType.WHITE_GRAPE, Model.XTALL),

    PLAINTAIN_0(FarmingPlantType.PLANTAIN, Model.X),

    YELLOW_SQUASH_0(FarmingPlantType.YELLOW_SQUASH, Model.X),
    YELLOW_SQUASH_1(FarmingPlantType.YELLOW_SQUASH, Model.X),
    YELLOW_SQUASH_2(FarmingPlantType.YELLOW_SQUASH, Model.X),
    YELLOW_SQUASH_3(FarmingPlantType.YELLOW_SQUASH, Model.X),
    YELLOW_SQUASH_4(FarmingPlantType.YELLOW_SQUASH, Model.X),
    YELLOW_SQUASH_5(FarmingPlantType.YELLOW_SQUASH, Model.X),
    YELLOW_SQUASH_6(FarmingPlantType.YELLOW_SQUASH, Model.X),

    FORASTERO_COCOA_0(FarmingPlantType.FORASTERO_COCOA, Model.X),
    FORASTERO_COCOA_1(FarmingPlantType.FORASTERO_COCOA, Model.X),
    FORASTERO_COCOA_2(FarmingPlantType.FORASTERO_COCOA, Model.X),

    QUEEN_PINEAPPLE_0(FarmingPlantType.QUEEN_PINEAPPLE, Model.X),
    QUEEN_PINEAPPLE_1(FarmingPlantType.QUEEN_PINEAPPLE, Model.X),
    QUEEN_PINEAPPLE_2(FarmingPlantType.QUEEN_PINEAPPLE, Model.X),
    QUEEN_PINEAPPLE_3(FarmingPlantType.QUEEN_PINEAPPLE, Model.X),
    QUEEN_PINEAPPLE_4(FarmingPlantType.QUEEN_PINEAPPLE, Model.X),
    QUEEN_PINEAPPLE_5(FarmingPlantType.QUEEN_PINEAPPLE, Model.X),
    QUEEN_PINEAPPLE_6(FarmingPlantType.QUEEN_PINEAPPLE, Model.X),
    QUEEN_PINEAPPLE_7(FarmingPlantType.QUEEN_PINEAPPLE, Model.X),

    ROMAINE_LETTUCE_0(FarmingPlantType.ROMAINE_LETTUCE, Model.X),
    ROMAINE_LETTUCE_1(FarmingPlantType.ROMAINE_LETTUCE, Model.X),
    ROMAINE_LETTUCE_2(FarmingPlantType.ROMAINE_LETTUCE, Model.X),
    ROMAINE_LETTUCE_3(FarmingPlantType.ROMAINE_LETTUCE, Model.X),
    ROMAINE_LETTUCE_4(FarmingPlantType.ROMAINE_LETTUCE, Model.X),
    ROMAINE_LETTUCE_5(FarmingPlantType.ROMAINE_LETTUCE, Model.X),

    CAVENDISH_BANANA_SAPLING(FarmingPlantType.CAVENDISH_BANANA, Model.X),

    EUREKA_LEMON_SAPLING(FarmingPlantType.EUREKA_LEMON, Model.X),

    GIANT_COCONUT_SAPLING(FarmingPlantType.GIANT_COCONUT, Model.X),

    BLUESHELL_BANANA_SAPLING(FarmingPlantType.BLUESHELL_BANANA, Model.X),

    SIMCA_PLUM_SAPLING(FarmingPlantType.SIMCA_PLUM, Model.X),

    PIRI_PEPPER_1(FarmingPlantType.PIRI_PEPPER, Model.X),
    PIRI_PEPPER_2(FarmingPlantType.PIRI_PEPPER, Model.X),
    PIRI_PEPPER_3(FarmingPlantType.PIRI_PEPPER, Model.X),

    KIWI_0(FarmingPlantType.KIWI, Model.X),
    KIWI_1(FarmingPlantType.KIWI, Model.X),
    KIWI_2(FarmingPlantType.KIWI, Model.X),
    KIWI_3(FarmingPlantType.KIWI, Model.X),
    KIWI_4(FarmingPlantType.KIWI, Model.X),

    JALAPENO_PEPPER_1(FarmingPlantType.JALAPENO_PEPPER, Model.X),
    JALAPENO_PEPPER_2(FarmingPlantType.JALAPENO_PEPPER, Model.X),
    JALAPENO_PEPPER_3(FarmingPlantType.JALAPENO_PEPPER, Model.X),
    ;

    private final FarmingPlantType farmingPlantType;
    private final Model model;

    @Getter
    @RequiredArgsConstructor
    public enum Model {
        X(0.0, List.of(Vec3i.of(0, 1, 0))),
        XTALL(0.0, List.of(Vec3i.of(0, 1, 0),
                           Vec3i.of(0, 2, 0))),
        X3TALL(1.0, List.of(Vec3i.of(0, 1, 0),
                            Vec3i.of(0, 2, 0),
                            Vec3i.of(0, 3, 0)));
        ;

        private final double offsetY;
        private final List<Vec3i> additionalBlockVectors;
    }

    public NamespacedKey getNamespacedKey() {
        return namespacedKey(name().toLowerCase());
    }

    public boolean checkAdditionalBlocks(Block farmland) {
        for (Vec3i v : model.getAdditionalBlockVectors()) {
            final Block additionalBlock = farmland.getRelative(v.x, v.y, v.z);
            // If this block already belongs to us, we skip the check
            if (!farmland.equals(farmingPlantType.getBlockRegistryEntry().getRecursiveParentBlock(additionalBlock))) {
                continue;
            }
            // Check if block is empty and not marked
            if (!additionalBlock.isEmpty()) return false;
            if (BlockRegistryEntry.at(additionalBlock) != null) return false;
            if (BlockMarker.hasId(additionalBlock)) return false;
        }
        return true;
    }

    /**
     * Place this growth stage in this world, that is:
     *
     * - Mark all additional blocks with the crop id.
     * - Spawn the item display that represents this plant.
     *
     * The farmland block will be left unchanged.  It is assumed that
     * it was set up during the planting.
     */
    public void place(Block farmland) {
        for (Vec3i v : model.getAdditionalBlockVectors()) {
            final Block additionalBlock = farmland.getRelative(v.x, v.y, v.z);
            farmingPlantType.getBlockRegistryEntry().setBlockId(additionalBlock);
            farmingPlantType.getBlockRegistryEntry().setParentBlock(additionalBlock, farmland);
        }
        final ItemDisplay itemDisplay = farmland.getWorld().spawn(farmland.getLocation().add(0.5, 1.5 + model.getOffsetY() - 0.0625, 0.5), ItemDisplay.class, e -> {
                final ItemStack plantItem = new ItemStack(Material.SHORT_GRASS);
                plantItem.setData(DataComponentTypes.ITEM_MODEL, getNamespacedKey());
                e.setItemStack(plantItem);
                farmingPlantType.getCropItem().markEntity(e);
            });
    }

    /**
     * Remove this growth stage from the world, undoing everything
     * that was done in the setup method.
     */
    public void remove(Block farmland) {
        for (Vec3i v : model.getAdditionalBlockVectors()) {
            final Block additionalBlock = farmland.getRelative(v.x, v.y, v.z);
            farmingPlantType.getBlockRegistryEntry().clearBlock(additionalBlock);
            for (Entity entity : additionalBlock.getWorld().getNearbyEntities(BoundingBox.of(additionalBlock))) {
                if (!farmingPlantType.getCropItem().isEntity(entity)) continue;
                entity.remove();
            }
        }
    }
}
