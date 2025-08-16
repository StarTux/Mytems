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
    BEET(PlantLocation.GARDEN_BED, WateringNeed.THIRSTY),
    CABBAGE(PlantLocation.GARDEN_BED, WateringNeed.THIRSTY),
    CARROT(PlantLocation.GARDEN_BED, WateringNeed.THIRSTY),
    GARLIC(PlantLocation.GARDEN_BED, WateringNeed.THIRSTY),
    LEEK(PlantLocation.GARDEN_BED, WateringNeed.THIRSTY),
    LETTUCE(PlantLocation.GARDEN_BED, WateringNeed.THIRSTY),
    ONION(PlantLocation.GARDEN_BED, WateringNeed.THIRSTY),
    PARSNIP(PlantLocation.GARDEN_BED, WateringNeed.THIRSTY),
    PINEAPPLE(PlantLocation.GARDEN_BED, WateringNeed.MODERATE), // Grows as a terrestrial herbaceous plant
    POTATO(PlantLocation.GARDEN_BED, WateringNeed.MODERATE),
    RADISH(PlantLocation.GARDEN_BED, WateringNeed.THIRSTY),

    // Fields
    CORN(PlantLocation.FIELD, WateringNeed.MODERATE),
    OAT(PlantLocation.FIELD, WateringNeed.MODERATE),
    WHEAT(PlantLocation.FIELD, WateringNeed.MODERATE),
    PUMPKIN(PlantLocation.FIELD, WateringNeed.HARDY),
    SQUASH(PlantLocation.FIELD, WateringNeed.MODERATE),
    WATERMELON(PlantLocation.FIELD, WateringNeed.MODERATE),
    MELON(PlantLocation.FIELD, WateringNeed.MODERATE),
    HONEYDEW(PlantLocation.FIELD, WateringNeed.MODERATE),
    RICE(PlantLocation.FIELD, WateringNeed.THIRSTY), // Rice needs flooded fields

    // Bushes
    BERRY(PlantLocation.BUSH, WateringNeed.MODERATE),
    BLACKBERRY(PlantLocation.BUSH, WateringNeed.MODERATE),
    BLUEBERRY(PlantLocation.BUSH, WateringNeed.MODERATE),
    COFFEE(PlantLocation.BUSH, WateringNeed.HARDY),
    EGGPLANT(PlantLocation.BUSH, WateringNeed.MODERATE),
    GOOSEBERRY(PlantLocation.BUSH, WateringNeed.MODERATE),
    PEPPER(PlantLocation.BUSH, WateringNeed.MODERATE),
    PLANTAIN(PlantLocation.BUSH, WateringNeed.MODERATE), // Large herbaceous plant, similar to banana
    RASPBERRY(PlantLocation.BUSH, WateringNeed.MODERATE),
    STRAWBERRY(PlantLocation.BUSH, WateringNeed.MODERATE),
    TEA(PlantLocation.BUSH, WateringNeed.HARDY),
    TOMATO(PlantLocation.BUSH, WateringNeed.MODERATE),

    // Trellis
    CUCUMBER(PlantLocation.TRELLIS, WateringNeed.THIRSTY),
    GRAPE(PlantLocation.TRELLIS, WateringNeed.MODERATE),
    KIWI(PlantLocation.TRELLIS, WateringNeed.MODERATE),
    PITAYA(PlantLocation.TRELLIS, WateringNeed.MODERATE), // Climbing cactus vine

    // Trees
    APPLE(PlantLocation.TREE, WateringNeed.NONE),
    AVOCADO(PlantLocation.TREE, WateringNeed.NONE),
    BANANA(PlantLocation.TREE, WateringNeed.NONE),
    CHERRY(PlantLocation.TREE, WateringNeed.NONE),
    COCONUT(PlantLocation.TREE, WateringNeed.NONE),
    FIG(PlantLocation.TREE, WateringNeed.NONE),
    LEMON(PlantLocation.TREE, WateringNeed.NONE),
    OLIVE(PlantLocation.TREE, WateringNeed.NONE),
    ORANGE(PlantLocation.TREE, WateringNeed.NONE),
    PLUM(PlantLocation.TREE, WateringNeed.NONE),
    COCOA(PlantLocation.TREE, WateringNeed.NONE),

    MUSHROOM(PlantLocation.OTHER, WateringNeed.NONE),

    // Aquatic
    NORI(PlantLocation.AQUATIC, WateringNeed.NONE),
    WAKAME(PlantLocation.AQUATIC, WateringNeed.NONE),

    // Fantasy
    NETHER_WART(PlantLocation.OTHER, WateringNeed.NONE),
    DRAGONBLOOM(PlantLocation.OTHER, WateringNeed.NONE),
    GLOWROOT(PlantLocation.OTHER, WateringNeed.NONE),
    CHORUS(PlantLocation.OTHER, WateringNeed.NONE),
    GLOW_BERRY(PlantLocation.OTHER, WateringNeed.NONE),
    ;

    private final PlantLocation plantLocation;
    private final WateringNeed wateringNeed;
}
