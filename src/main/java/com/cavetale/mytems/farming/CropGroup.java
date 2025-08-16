package com.cavetale.mytems.farming;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * This puts crops into groups for recipes.
 * This categorization could change a lot.
 *
 * @see FarmingPlantType
 */
@Getter
@RequiredArgsConstructor
public enum CropGroup {
    // Garden Beds
    BEET(PlantLocation.GARDEN_BED, GrowthType.ROOT, WateringNeed.THIRSTY),
    CABBAGE(PlantLocation.GARDEN_BED, GrowthType.LEAFY, WateringNeed.THIRSTY),
    CARROT(PlantLocation.GARDEN_BED, GrowthType.ROOT, WateringNeed.THIRSTY),
    GARLIC(PlantLocation.GARDEN_BED, GrowthType.ROOT, WateringNeed.THIRSTY),
    LEEK(PlantLocation.GARDEN_BED, GrowthType.ROOT, WateringNeed.THIRSTY),
    LETTUCE(PlantLocation.GARDEN_BED, GrowthType.LEAFY, WateringNeed.THIRSTY),
    ONION(PlantLocation.GARDEN_BED, GrowthType.ROOT, WateringNeed.THIRSTY),
    PARSNIP(PlantLocation.GARDEN_BED, GrowthType.ROOT, WateringNeed.THIRSTY),
    PINEAPPLE(PlantLocation.GARDEN_BED, GrowthType.HERBACEOUS, WateringNeed.MODERATE), // Grows as a terrestrial herbaceous plant
    POTATO(PlantLocation.GARDEN_BED, GrowthType.ROOT, WateringNeed.MODERATE),
    RADISH(PlantLocation.GARDEN_BED, GrowthType.ROOT, WateringNeed.THIRSTY),

    // Fields
    CORN(PlantLocation.FIELD, GrowthType.HERBACEOUS, WateringNeed.MODERATE),
    OAT(PlantLocation.FIELD, GrowthType.GRAIN, WateringNeed.MODERATE),
    WHEAT(PlantLocation.FIELD, GrowthType.GRAIN, WateringNeed.MODERATE),
    PUMPKIN(PlantLocation.FIELD, GrowthType.GOURD, WateringNeed.HARDY),
    SQUASH(PlantLocation.FIELD, GrowthType.GOURD, WateringNeed.MODERATE),
    WATERMELON(PlantLocation.FIELD, GrowthType.GOURD, WateringNeed.MODERATE),
    MELON(PlantLocation.FIELD, GrowthType.GOURD, WateringNeed.MODERATE),
    HONEYDEW(PlantLocation.FIELD, GrowthType.GOURD, WateringNeed.MODERATE),
    RICE(PlantLocation.FIELD, GrowthType.GRAIN, WateringNeed.THIRSTY), // Rice needs flooded fields

    // Bushes
    BERRY(PlantLocation.BUSH, GrowthType.BUSH, WateringNeed.MODERATE),
    BLACKBERRY(PlantLocation.BUSH, GrowthType.BUSH, WateringNeed.MODERATE),
    BLUEBERRY(PlantLocation.BUSH, GrowthType.BUSH, WateringNeed.MODERATE),
    COFFEE(PlantLocation.BUSH, GrowthType.BUSH, WateringNeed.HARDY),
    EGGPLANT(PlantLocation.BUSH, GrowthType.HERBACEOUS, WateringNeed.MODERATE),
    GOOSEBERRY(PlantLocation.BUSH, GrowthType.BUSH, WateringNeed.MODERATE),
    PEPPER(PlantLocation.BUSH, GrowthType.BUSH, WateringNeed.MODERATE),
    PLANTAIN(PlantLocation.BUSH, GrowthType.TREE, WateringNeed.MODERATE), // Large herbaceous plant, similar to banana
    RASPBERRY(PlantLocation.BUSH, GrowthType.BUSH, WateringNeed.MODERATE),
    STRAWBERRY(PlantLocation.BUSH, GrowthType.HERBACEOUS, WateringNeed.MODERATE),
    TEA(PlantLocation.BUSH, GrowthType.BUSH, WateringNeed.HARDY),
    TOMATO(PlantLocation.BUSH, GrowthType.HERBACEOUS, WateringNeed.MODERATE),

    // Trellis
    CUCUMBER(PlantLocation.TRELLIS, GrowthType.GOURD, WateringNeed.THIRSTY),
    GRAPE(PlantLocation.TRELLIS, GrowthType.VINE, WateringNeed.MODERATE),
    KIWI(PlantLocation.TRELLIS, GrowthType.VINE, WateringNeed.MODERATE),
    PITAYA(PlantLocation.TRELLIS, GrowthType.CACTUS, WateringNeed.MODERATE), // Climbing cactus vine

    // Trees
    APPLE(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE),
    AVOCADO(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE),
    BANANA(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE),
    CHERRY(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE),
    COCONUT(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE),
    FIG(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE),
    LEMON(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE),
    OLIVE(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE),
    ORANGE(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE),
    PLUM(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE),
    COCOA(PlantLocation.TREE, GrowthType.COCOA, WateringNeed.NONE),

    MUSHROOM(PlantLocation.OTHER, GrowthType.MUSHROOM, WateringNeed.NONE),

    // Aquatic
    NORI(PlantLocation.AQUATIC, GrowthType.KELP, WateringNeed.NONE),
    WAKAME(PlantLocation.AQUATIC, GrowthType.KELP, WateringNeed.NONE),

    // Fantasy
    NETHER_WART(PlantLocation.OTHER, GrowthType.NETHER_WART, WateringNeed.NONE),
    DRAGONBLOOM(PlantLocation.OTHER, GrowthType.CACTUS, WateringNeed.NONE),
    GLOWROOT(PlantLocation.OTHER, GrowthType.ROOT, WateringNeed.NONE),
    CHORUS(PlantLocation.OTHER, GrowthType.CHORUS, WateringNeed.NONE),
    GLOW_BERRY(PlantLocation.OTHER, GrowthType.VINE, WateringNeed.NONE),
    ;

    private final PlantLocation plantLocation;
    private final GrowthType growthType;
    private final WateringNeed wateringNeed;
}
