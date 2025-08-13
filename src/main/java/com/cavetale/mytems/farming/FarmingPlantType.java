package com.cavetale.mytems.farming;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.block.BlockRegistryEntry;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import static com.cavetale.mytems.block.BlockRegistry.blockRegistry;

@Getter
@RequiredArgsConstructor
public enum FarmingPlantType {
    ARABICA_COFFEE(CropGroup.COFFEE, GrowthType.BUSH, Mytems.ARABICA_COFFEE_BEAN, Mytems.ARABICA_COFFEE_SEEDS),
    ASHEN_NETHER_WART(CropGroup.NETHER_WART, GrowthType.NETHER_WART, Mytems.ASHEN_NETHER_WART, Mytems.ASHEN_NETHER_WART_SEEDS),
    BLACKBERRY(CropGroup.BLACKBERRY, GrowthType.BUSH, Mytems.BLACKBERRY, Mytems.BLACKBERRY_SEEDS),
    BLACK_CHERRY(CropGroup.CHERRY, GrowthType.TREE, Mytems.BLACK_CHERRIES, Mytems.BLACK_CHERRY_SEEDS),
    BLUESHELL_BANANA(CropGroup.BANANA, GrowthType.TREE, Mytems.BLUESHELL_BANANA, Mytems.BLUESHELL_BANANA_SEEDS),
    BLUE_POPCORN(CropGroup.CORN, GrowthType.HERBACEOUS, Mytems.BLUE_POPCORN, Mytems.BLUE_POPCORN_SEEDS),
    BRIGHT_GLOW_BERRY(CropGroup.BERRY, GrowthType.VINE, Mytems.BRIGHT_GLOW_BERRIES, Mytems.BRIGHT_GLOW_BERRY_SEEDS),
    CAMAROSA_STRAWBERRY(CropGroup.STRAWBERRY, GrowthType.HERBACEOUS, Mytems.CAMAROSA_STRAWBERRY, Mytems.CAMAROSA_STRAWBERRY_SEEDS),
    CAMELIA_TEA(CropGroup.TEA, GrowthType.BUSH, Mytems.CAMELIA_TEA_LEAVES, Mytems.CAMELIA_TEA_SEEDS),
    CANDY_GRAPE(CropGroup.GRAPE, GrowthType.VINE, Mytems.CANDY_GRAPES, Mytems.CANDY_GRAPE_SEEDS),
    CANTALOUPE(CropGroup.MELON, GrowthType.GOURD, Mytems.CANTALOUPE, Mytems.CANTALOUPE_SEEDS),
    CAVENDISH_BANANA(CropGroup.BANANA, GrowthType.TREE, Mytems.CAVENDISH_BANANA, Mytems.CAVENDISH_BANANA_SEEDS),
    CHERRY_BELLE_RADISH(CropGroup.RADISH, GrowthType.ROOT, Mytems.CHERRY_BELLE_RADISH, Mytems.CHERRY_BELLE_RADISH_SEEDS),
    COMMON_OAT(CropGroup.OAT, GrowthType.GRAIN, Mytems.COMMON_OAT, Mytems.COMMON_OAT_SEEDS),
    CONCORD_GRAPE(CropGroup.GRAPE, GrowthType.VINE, Mytems.CONCORD_GRAPES, Mytems.CONCORD_GRAPE_SEEDS),
    CRIOLLO_COCOA(CropGroup.COCOA, GrowthType.COCOA, Mytems.CRIOLLO_COCOA, Mytems.CRIOLLO_COCOA_SEEDS),
    DENT_CORN(CropGroup.CORN, GrowthType.HERBACEOUS, Mytems.DENT_CORN, Mytems.DENT_CORN_SEEDS),
    DWARF_COCONUT(CropGroup.COCONUT, GrowthType.TREE, Mytems.DWARF_COCONUT, Mytems.DWARF_COCONUT_SEEDS),
    ETHEREAL_CHORUS_FRUIT(CropGroup.FRUIT, GrowthType.CHORUS, Mytems.ETHEREAL_CHORUS_FRUIT, Mytems.ETHEREAL_CHORUS_FRUIT_SEEDS),
    EUREKA_LEMON(CropGroup.LEMON, GrowthType.TREE, Mytems.EUREKA_LEMON, Mytems.EUREKA_LEMON_SEEDS),
    FALSE_PICKLE(CropGroup.PICKLE, GrowthType.GOURD, Mytems.FALSE_PICKLE, Mytems.FALSE_PICKLE_SEEDS),
    FLY_AGARIC_MUSHROOM(CropGroup.MUSHROOM, GrowthType.MUSHROOM, Mytems.FLY_AGARIC_MUSHROOM, Mytems.FLY_AGARIC_MUSHROOM_SPORES),
    FORASTERO_COCOA(CropGroup.COCOA, GrowthType.COCOA, Mytems.FORASTERO_COCOA, Mytems.FORASTERO_COCOA_SEEDS),
    GARDEN_CUCUMBER(CropGroup.CUCUMBER, GrowthType.GOURD, Mytems.GARDEN_CUCUMBER, Mytems.GARDEN_CUCUMBER_SEEDS),
    GIANT_COCONUT(CropGroup.COCONUT, GrowthType.TREE, Mytems.GIANT_COCONUT, Mytems.GIANT_COCONUT_SEEDS),
    GLOBE_EGGPLANT(CropGroup.EGGPLANT, GrowthType.HERBACEOUS, Mytems.GLOBE_EGGPLANT, Mytems.GLOBE_EGGPLANT_SEEDS),
    GLOOMROT_NETHER_WART(CropGroup.NETHER_WART, GrowthType.NETHER_WART, Mytems.GLOOMROT_NETHER_WART, Mytems.GLOOMROT_NETHER_WART_SEEDS),
    GLOWROOT(CropGroup.GLOWROOT, GrowthType.ROOT, Mytems.GLOWROOT, Mytems.GLOWROOT_SEEDS),
    GOLD_KIWI(CropGroup.KIWI, GrowthType.VINE, Mytems.GOLD_KIWI, Mytems.GOLD_KIWI_SEEDS),
    GOOSEBERRY(CropGroup.GOOSEBERRY, GrowthType.BUSH, Mytems.GOOSEBERRY, Mytems.GOOSEBERRY_SEEDS),
    GRANNY_SMITH_APPLE(CropGroup.APPLE, GrowthType.TREE, Mytems.GRANNY_SMITH_APPLE, Mytems.GRANNY_SMITH_APPLE_SEEDS),
    GREEN_BELL_PEPPER(CropGroup.PEPPER, GrowthType.BUSH, Mytems.GREEN_BELL_PEPPER, Mytems.GREEN_BELL_PEPPER_SEEDS),
    GREEN_CABBAGE(CropGroup.CABBAGE, GrowthType.LEAFY, Mytems.GREEN_CABBAGE, Mytems.GREEN_CABBAGE_SEEDS),
    GREEN_SQUASH(CropGroup.SQUASH, GrowthType.GOURD, Mytems.GREEN_SQUASH, Mytems.GREEN_SQUASH_SEEDS),
    HARD_WHEAT(CropGroup.WHEAT, GrowthType.GRAIN, Mytems.HARD_WHEAT, Mytems.HARD_WHEAT_SEEDS),
    HASS_AVOCADO(CropGroup.AVOCADO, GrowthType.TREE, Mytems.HASS_AVOCADO, Mytems.HASS_AVOCADO_SEEDS),
    HEIRLOOM_TOMATO(CropGroup.TOMATO, GrowthType.HERBACEOUS, Mytems.HEIRLOOM_TOMATO, Mytems.HEIRLOOM_TOMATO_SEEDS),
    HERBAL_TEA(CropGroup.TEA, GrowthType.BUSH, Mytems.HERBAL_TEA_LEAVES, Mytems.HERBAL_TEA_SEEDS),
    HERITAGE_RASPBERRY(CropGroup.RASPBERRY, GrowthType.BUSH, Mytems.HERITAGE_RASPBERRY, Mytems.HERITAGE_RASPBERRY_SEEDS),
    HOLLOW_CROWN_PARSNIP(CropGroup.PARSNIP, GrowthType.ROOT, Mytems.HOLLOW_CROWN_PARSNIP, Mytems.HOLLOW_CROWN_PARSNIP_SEEDS),
    HONEYCRISP_APPLE(CropGroup.APPLE, GrowthType.TREE, Mytems.HONEYCRISP_APPLE, Mytems.HONEYCRISP_APPLE_SEEDS),
    HONEYDEW(CropGroup.HONEYDEW, GrowthType.GOURD, Mytems.HONEYDEW, Mytems.HONEYDEW_SEEDS),
    IMPERATOR_CARROT(CropGroup.CARROT, GrowthType.ROOT, Mytems.IMPERATOR_CARROT, Mytems.IMPERATOR_CARROT_SEEDS),
    INFERNAL_NETHER_WART(CropGroup.NETHER_WART, GrowthType.NETHER_WART, Mytems.INFERNAL_NETHER_WART, Mytems.INFERNAL_NETHER_WART_SEEDS),
    JASMINE_RICE(CropGroup.RICE, GrowthType.TREE, Mytems.JASMINE_RICE, Mytems.JASMINE_RICE_SEEDS),
    KING_RICHARD_LEEK(CropGroup.LEEK, GrowthType.ROOT, Mytems.KING_RICHARD_LEEK, Mytems.KING_RICHARD_LEEK_SEEDS),
    MIDNIGHT_DRAGONBLOOM(CropGroup.DRAGONBLOOM, GrowthType.CACTUS, Mytems.MIDNIGHT_DRAGONBLOOM, Mytems.MIDNIGHT_DRAGONBLOOM_SEEDS),
    MORELLO_CHERRY(CropGroup.CHERRY, GrowthType.TREE, Mytems.MORELLO_CHERRIES, Mytems.MORELLO_CHERRY_SEEDS),
    MOREL_MUSHROOM(CropGroup.MUSHROOM, GrowthType.MUSHROOM, Mytems.MOREL_MUSHROOM, Mytems.MOREL_MUSHROOM_SPORES),
    NAVEL_ORANGE(CropGroup.ORANGE, GrowthType.TREE, Mytems.NAVEL_ORANGE, Mytems.NAVEL_ORANGE_SEEDS),
    NORI(CropGroup.NORI, GrowthType.KELP, Mytems.NORI, Mytems.NORI_SEEDS),
    NORTHERN_BLUEBERRY(CropGroup.BLUEBERRY, GrowthType.BUSH, Mytems.NORTHERN_BLUEBERRIES, Mytems.NORTHERN_BLUEBERRY_SEEDS),
    PICUAL_OLIVE(CropGroup.OLIVE, GrowthType.TREE, Mytems.PICUAL_OLIVE, Mytems.PICUAL_OLIVE_SEEDS),
    PLANTAIN(CropGroup.PLANTAIN, GrowthType.TREE, Mytems.PLANTAIN, Mytems.PLANTAIN_SEEDS),
    PORTOBELLO_MUSHROOM(CropGroup.MUSHROOM, GrowthType.MUSHROOM, Mytems.PORTOBELLO_MUSHROOM, Mytems.PORTOBELLO_MUSHROOM_SPORES),
    PURPLE_CARROT(CropGroup.CARROT, GrowthType.ROOT, Mytems.PURPLE_CARROT, Mytems.PURPLE_CARROT_SEEDS),
    QUEEN_ANNE_CHERRY(CropGroup.CHERRY, GrowthType.TREE, Mytems.QUEEN_ANNE_CHERRIES, Mytems.QUEEN_ANNE_CHERRY_SEEDS),
    QUEEN_PINEAPPLE(CropGroup.PINEAPPLE, GrowthType.HERBACEOUS, Mytems.QUEEN_PINEAPPLE, Mytems.QUEEN_PINEAPPLE_SEEDS),
    RED_DELICIOUS_APPLE(CropGroup.APPLE, GrowthType.TREE, Mytems.RED_DELICIOUS_APPLE, Mytems.RED_DELICIOUS_APPLE_SEEDS),
    RED_GARDEN_BEET(CropGroup.BEET, GrowthType.ROOT, Mytems.RED_GARDEN_BEET, Mytems.RED_GARDEN_BEET_SEEDS),
    RED_PITAYA(CropGroup.PITAYA, GrowthType.CACTUS, Mytems.RED_PITAYA, Mytems.RED_PITAYA_SEEDS),
    RED_POTATO(CropGroup.POTATO, GrowthType.ROOT, Mytems.RED_POTATO, Mytems.RED_POTATO_SEEDS),
    ROMAINE_LETTUCE(CropGroup.LETTUCE, GrowthType.LEAFY, Mytems.ROMAINE_LETTUCE, Mytems.ROMAINE_LETTUCE_SEEDS),
    RUSSET_POTATO(CropGroup.POTATO, GrowthType.ROOT, Mytems.RUSSET_POTATO, Mytems.RUSSET_POTATO_SEEDS),
    SILVERSKIN_GARLIC(CropGroup.GARLIC, GrowthType.ROOT, Mytems.SILVERSKIN_GARLIC, Mytems.SILVERSKIN_GARLIC_SEEDS),
    SIMCA_PLUM(CropGroup.PLUM, GrowthType.TREE, Mytems.SIMCA_PLUM, Mytems.SIMCA_PLUM_SEEDS),
    SPICY_FIG(CropGroup.FIG, GrowthType.VINE, Mytems.SPICY_FIG, Mytems.SPICY_FIG_SEEDS),
    SUGAR_PIE_PUMPKIN(CropGroup.PUMPKIN, GrowthType.GOURD, Mytems.SUGAR_PIE_PUMPKIN, Mytems.SUGAR_PIE_PUMPKIN_SEEDS),
    SUNSET_TEA(CropGroup.TEA, GrowthType.BUSH, Mytems.SUNSET_TEA_LEAVES, Mytems.SUNSET_TEA_SEEDS),
    SWEET_CORN(CropGroup.CORN, GrowthType.HERBACEOUS, Mytems.SWEET_CORN, Mytems.SWEET_CORN_SEEDS),
    SWEET_POTATO(CropGroup.POTATO, GrowthType.ROOT, Mytems.SWEET_POTATO, Mytems.SWEET_POTATO_SEEDS),
    TURBO_COFFEE(CropGroup.COFFEE, GrowthType.BUSH, Mytems.TURBO_COFFEE_BEAN, Mytems.TURBO_COFFEE_SEEDS),
    WAKAME(CropGroup.WAKAME, GrowthType.KELP, Mytems.WAKAME, Mytems.WAKAME_SEEDS),
    WARPAL_BERRY(CropGroup.BERRY, GrowthType.BUSH, Mytems.WARPAL_BERRIES, Mytems.WARPAL_BERRY_SEEDS),
    WATERMELON(CropGroup.WATERMELON, GrowthType.GOURD, Mytems.WATERMELON, Mytems.WATERMELON_SEEDS),
    WHITE_GRAPE(CropGroup.GRAPE, GrowthType.VINE, Mytems.WHITE_GRAPES, Mytems.WHITE_GRAPE_SEEDS),
    YELLOW_ONION(CropGroup.ONION, GrowthType.ROOT, Mytems.YELLOW_ONION, Mytems.YELLOW_ONION_SEEDS),
    YELLOW_PITAYA(CropGroup.PITAYA, GrowthType.CACTUS, Mytems.YELLOW_PITAYA, Mytems.YELLOW_PITAYA_SEEDS),
    YELLOW_SQUASH(CropGroup.SQUASH, GrowthType.GOURD, Mytems.YELLOW_SQUASH, Mytems.YELLOW_SQUASH_SEEDS),
    ;

    private final CropGroup cropGroup;
    private final GrowthType category;
    private final Mytems cropItem;
    private final Mytems seedItem;
    private List<GrowthStage> growthStages;
    private BlockRegistryEntry blockRegistryEntry;

    /**
     * Called by seedItem.
     */
    public void enable() {
        final FarmingPlantBlock impl = new FarmingPlantBlock(this);
        blockRegistryEntry = blockRegistry().register(cropItem, impl);
        blockRegistryEntry.setCancelBlockEdits(true);
        blockRegistryEntry.addEventHandler(impl);
        final List<GrowthStage> tmpGrowthStages = new ArrayList<>();
        for (GrowthStage it : GrowthStage.values()) {
            if (it.getFarmingPlantType() == this) {
                tmpGrowthStages.add(it);
            }
        }
        this.growthStages = List.copyOf(tmpGrowthStages);
    }

    public static FarmingPlantType find(String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (IllegalArgumentException iae) {
            return null;
        }
    }

    public static FarmingPlantType ofCropItem(Mytems cropItem) {
        for (var it : values()) {
            if (it.cropItem == cropItem) return it;
        }
        return null;
    }

    public static FarmingPlantType ofSeedItem(Mytems seedItem) {
        for (var it : values()) {
            if (it.seedItem == seedItem) return it;
        }
        return null;
    }
}
