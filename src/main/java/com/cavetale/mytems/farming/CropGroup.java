package com.cavetale.mytems.farming;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import static com.cavetale.mytems.farming.CropTag.*;

/**
 * This puts crops into groups for recipes.
 * This categorization could change a lot.
 *
 * @see FarmingPlantType
 */
@Getter
public enum CropGroup {
    // Garden Beds
    BEET(PlantLocation.GARDEN_BED, GrowthType.ROOT, WateringNeed.THIRSTY,
         Set.of(Season.SPRING, Season.FALL), Set.of(Climate.TEMPERATE, Climate.COLD),
         LightRequirement.FULL_SUN, cropYield(3, 5), growthTime(21)),
    CABBAGE(PlantLocation.GARDEN_BED, GrowthType.LEAFY, WateringNeed.THIRSTY,
            Set.of(Season.SPRING, Season.FALL), Set.of(Climate.TEMPERATE, Climate.COLD),
            LightRequirement.FULL_SUN, cropYield(1), growthTime(28)),
    CARROT(PlantLocation.GARDEN_BED, GrowthType.ROOT, WateringNeed.THIRSTY,
           Set.of(Season.SPRING, Season.FALL), Set.of(Climate.TEMPERATE, Climate.COLD),
           LightRequirement.FULL_SUN, cropYield(3, 6), growthTime(21)),
    GARLIC(PlantLocation.GARDEN_BED, GrowthType.ROOT, WateringNeed.THIRSTY,
           Set.of(Season.SPRING, Season.FALL), Set.of(Climate.TEMPERATE),
           LightRequirement.FULL_SUN, cropYield(4, 8), growthTime(30)),
    LEEK(PlantLocation.GARDEN_BED, GrowthType.ROOT, WateringNeed.THIRSTY,
         Set.of(Season.SPRING, Season.FALL), Set.of(Climate.TEMPERATE),
         LightRequirement.FULL_SUN, cropYield(1), growthTime(35)),
    LETTUCE(PlantLocation.GARDEN_BED, GrowthType.LEAFY, WateringNeed.THIRSTY,
            Set.of(Season.SPRING, Season.FALL), Set.of(Climate.TEMPERATE, Climate.COLD),
            LightRequirement.PARTIAL_SHADE, cropYield(1), growthTime(14), regrowthTime(7)),
    ONION(PlantLocation.GARDEN_BED, GrowthType.ROOT, WateringNeed.THIRSTY,
          Set.of(Season.SPRING, Season.FALL), Set.of(Climate.TEMPERATE),
          LightRequirement.FULL_SUN, cropYield(3, 5), growthTime(28)),
    PARSNIP(PlantLocation.GARDEN_BED, GrowthType.ROOT, WateringNeed.THIRSTY,
            Set.of(Season.SPRING, Season.FALL), Set.of(Climate.TEMPERATE, Climate.COLD),
            LightRequirement.FULL_SUN, cropYield(2, 4), growthTime(30)),
    PINEAPPLE(PlantLocation.GARDEN_BED, GrowthType.HERBACEOUS, WateringNeed.MODERATE,
              Set.of(Season.SUMMER), Set.of(Climate.HOT),
              LightRequirement.FULL_SUN, cropYield(1), growthTime(90), PERENNIAL),
    POTATO(PlantLocation.GARDEN_BED, GrowthType.ROOT, WateringNeed.MODERATE,
           Set.of(Season.SPRING), Set.of(Climate.TEMPERATE),
           LightRequirement.FULL_SUN, cropYield(3, 6), growthTime(28)),
    RADISH(PlantLocation.GARDEN_BED, GrowthType.ROOT, WateringNeed.THIRSTY,
           Set.of(Season.SPRING, Season.FALL), Set.of(Climate.TEMPERATE, Climate.COLD),
           LightRequirement.FULL_SUN, cropYield(3, 5), growthTime(14)),

    // Fields
    CORN(PlantLocation.FIELD, GrowthType.HERBACEOUS, WateringNeed.MODERATE,
         Set.of(Season.SUMMER), Set.of(Climate.HOT, Climate.TEMPERATE),
         LightRequirement.FULL_SUN, cropYield(2, 4), growthTime(30)),
    OAT(PlantLocation.FIELD, GrowthType.GRAIN, WateringNeed.MODERATE,
        Set.of(Season.SPRING, Season.FALL), Set.of(Climate.TEMPERATE, Climate.COLD),
        LightRequirement.FULL_SUN, cropYield(5, 10), growthTime(28)),
    WHEAT(PlantLocation.FIELD, GrowthType.GRAIN, WateringNeed.MODERATE,
          Set.of(Season.SPRING, Season.FALL), Set.of(Climate.TEMPERATE),
          LightRequirement.FULL_SUN, cropYield(5, 10), growthTime(28)),
    PUMPKIN(PlantLocation.FIELD, GrowthType.GOURD, WateringNeed.HARDY,
            Set.of(Season.SUMMER), Set.of(Climate.TEMPERATE, Climate.HOT),
            LightRequirement.FULL_SUN, cropYield(1), growthTime(45)),
    SQUASH(PlantLocation.FIELD, GrowthType.GOURD, WateringNeed.MODERATE,
           Set.of(Season.SUMMER), Set.of(Climate.TEMPERATE, Climate.HOT),
           LightRequirement.FULL_SUN, cropYield(2, 4), growthTime(35)),
    WATERMELON(PlantLocation.FIELD, GrowthType.GOURD, WateringNeed.MODERATE,
               Set.of(Season.SUMMER), Set.of(Climate.HOT),
               LightRequirement.FULL_SUN, cropYield(1), growthTime(40)),
    MELON(PlantLocation.FIELD, GrowthType.GOURD, WateringNeed.MODERATE,
          Set.of(Season.SUMMER), Set.of(Climate.HOT, Climate.TEMPERATE),
          LightRequirement.FULL_SUN, cropYield(1), growthTime(35)),
    HONEYDEW(PlantLocation.FIELD, GrowthType.GOURD, WateringNeed.MODERATE,
             Set.of(Season.SUMMER), Set.of(Climate.HOT, Climate.TEMPERATE),
             LightRequirement.FULL_SUN, cropYield(1), growthTime(35)),
    RICE(PlantLocation.FIELD, GrowthType.GRAIN, WateringNeed.THIRSTY,
         Set.of(Season.SUMMER), Set.of(Climate.HOT, Climate.TEMPERATE),
         LightRequirement.FULL_SUN, cropYield(10, 15), growthTime(30)),

    // Bushes
    BERRY(PlantLocation.BUSH, GrowthType.BUSH, WateringNeed.MODERATE,
          Set.of(Season.SUMMER), Set.of(Climate.TEMPERATE),
          LightRequirement.FULL_SUN, cropYield(5, 10), growthTime(30), regrowthTime(14), PERENNIAL),
    BLACKBERRY(PlantLocation.BUSH, GrowthType.BUSH, WateringNeed.MODERATE,
               Set.of(Season.SUMMER), Set.of(Climate.TEMPERATE),
               LightRequirement.FULL_SUN, cropYield(5, 10), growthTime(30), regrowthTime(14), PERENNIAL),
    BLUEBERRY(PlantLocation.BUSH, GrowthType.BUSH, WateringNeed.MODERATE,
              Set.of(Season.SUMMER), Set.of(Climate.TEMPERATE, Climate.COLD),
              LightRequirement.FULL_SUN, cropYield(5, 10), growthTime(90), regrowthTime(14), PERENNIAL),
    COFFEE(PlantLocation.BUSH, GrowthType.BUSH, WateringNeed.HARDY,
           Set.of(Season.SUMMER), Set.of(Climate.HOT),
           LightRequirement.PARTIAL_SHADE, cropYield(10, 15), growthTime(180), regrowthTime(30), PERENNIAL),
    EGGPLANT(PlantLocation.BUSH, GrowthType.HERBACEOUS, WateringNeed.MODERATE,
             Set.of(Season.SUMMER), Set.of(Climate.HOT, Climate.TEMPERATE),
             LightRequirement.FULL_SUN, cropYield(2, 4), growthTime(35)),
    GOOSEBERRY(PlantLocation.BUSH, GrowthType.BUSH, WateringNeed.MODERATE,
               Set.of(Season.SUMMER), Set.of(Climate.TEMPERATE, Climate.COLD),
               LightRequirement.FULL_SUN, cropYield(5, 10), growthTime(30), regrowthTime(14), PERENNIAL),
    PEPPER(PlantLocation.BUSH, GrowthType.BUSH, WateringNeed.MODERATE,
           Set.of(Season.SUMMER), Set.of(Climate.HOT, Climate.TEMPERATE),
           LightRequirement.FULL_SUN, cropYield(3, 6), growthTime(30)),
    PLANTAIN(PlantLocation.BUSH, GrowthType.TREE, WateringNeed.MODERATE,
             Set.of(Season.SUMMER), Set.of(Climate.HOT),
             LightRequirement.FULL_SUN, cropYield(5, 10), growthTime(90), regrowthTime(30), PERENNIAL),
    RASPBERRY(PlantLocation.BUSH, GrowthType.BUSH, WateringNeed.MODERATE,
              Set.of(Season.SUMMER), Set.of(Climate.TEMPERATE),
              LightRequirement.FULL_SUN, cropYield(5, 10), growthTime(30), regrowthTime(14), PERENNIAL),
    STRAWBERRY(PlantLocation.BUSH, GrowthType.HERBACEOUS, WateringNeed.MODERATE,
               Set.of(Season.SPRING, Season.SUMMER), Set.of(Climate.TEMPERATE),
               LightRequirement.FULL_SUN, cropYield(3, 8), growthTime(30), regrowthTime(7), PERENNIAL),
    TEA(PlantLocation.BUSH, GrowthType.BUSH, WateringNeed.HARDY,
        Set.of(Season.SPRING, Season.SUMMER), Set.of(Climate.TEMPERATE),
        LightRequirement.PARTIAL_SHADE, cropYield(10), growthTime(180), regrowthTime(30), PERENNIAL),
    TOMATO(PlantLocation.BUSH, GrowthType.HERBACEOUS, WateringNeed.MODERATE,
           Set.of(Season.SUMMER), Set.of(Climate.HOT, Climate.TEMPERATE),
           LightRequirement.FULL_SUN, cropYield(3, 6), growthTime(30), regrowthTime(14), PERENNIAL),

    // Trellis
    CUCUMBER(PlantLocation.TRELLIS, GrowthType.GOURD, WateringNeed.THIRSTY,
             Set.of(Season.SUMMER), Set.of(Climate.HOT, Climate.TEMPERATE),
             LightRequirement.FULL_SUN, cropYield(2, 4), growthTime(25)),
    GRAPE(PlantLocation.TRELLIS, GrowthType.VINE, WateringNeed.MODERATE,
          Set.of(Season.SUMMER, Season.FALL), Set.of(Climate.TEMPERATE),
          LightRequirement.FULL_SUN, cropYield(10, 15), growthTime(60), regrowthTime(30), PERENNIAL),
    KIWI(PlantLocation.TRELLIS, GrowthType.VINE, WateringNeed.MODERATE,
         Set.of(Season.SUMMER), Set.of(Climate.TEMPERATE),
         LightRequirement.FULL_SUN, cropYield(10, 15), growthTime(120), regrowthTime(30), PERENNIAL),
    PITAYA(PlantLocation.TRELLIS, GrowthType.CACTUS, WateringNeed.MODERATE,
           Set.of(Season.SUMMER), Set.of(Climate.HOT),
           LightRequirement.FULL_SUN, cropYield(3, 5), growthTime(90), regrowthTime(30), PERENNIAL),

    // Trees
    APPLE(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE,
          Set.of(Season.SPRING, Season.FALL), Set.of(Climate.TEMPERATE, Climate.COLD),
          LightRequirement.FULL_SUN, cropYield(10, 15), growthTime(180), regrowthTime(30), PERENNIAL),
    AVOCADO(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE,
            Set.of(Season.SUMMER), Set.of(Climate.HOT),
            LightRequirement.FULL_SUN, cropYield(5, 10), growthTime(270), regrowthTime(30), PERENNIAL),
    BANANA(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE,
           Set.of(Season.SUMMER), Set.of(Climate.HOT),
           LightRequirement.FULL_SUN, cropYield(10, 15), growthTime(120), regrowthTime(30), PERENNIAL),
    CHERRY(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE,
           Set.of(Season.SPRING, Season.SUMMER), Set.of(Climate.TEMPERATE),
           LightRequirement.FULL_SUN, cropYield(15, 20), growthTime(180), regrowthTime(30), PERENNIAL),
    COCONUT(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE,
            Set.of(Season.SUMMER), Set.of(Climate.HOT),
            LightRequirement.FULL_SUN, cropYield(3, 5), growthTime(365), regrowthTime(30), PERENNIAL),
    FIG(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE,
        Set.of(Season.SUMMER, Season.FALL), Set.of(Climate.HOT, Climate.TEMPERATE),
        LightRequirement.FULL_SUN, cropYield(10, 15), growthTime(120), regrowthTime(30), PERENNIAL),
    LEMON(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE,
          Set.of(Season.SUMMER), Set.of(Climate.HOT, Climate.TEMPERATE),
          LightRequirement.FULL_SUN, cropYield(5, 10), growthTime(180), regrowthTime(30), PERENNIAL),
    OLIVE(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE,
          Set.of(Season.SUMMER, Season.FALL), Set.of(Climate.HOT, Climate.TEMPERATE),
          LightRequirement.FULL_SUN, cropYield(10, 15), growthTime(270), regrowthTime(30), PERENNIAL),
    ORANGE(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE,
           Set.of(Season.SUMMER), Set.of(Climate.HOT, Climate.TEMPERATE),
           LightRequirement.FULL_SUN, cropYield(10, 15), growthTime(180), regrowthTime(30), PERENNIAL),
    PLUM(PlantLocation.TREE, GrowthType.TREE, WateringNeed.NONE,
         Set.of(Season.SUMMER), Set.of(Climate.TEMPERATE),
         LightRequirement.FULL_SUN, cropYield(10, 15), growthTime(180), regrowthTime(30), PERENNIAL),
    COCOA(PlantLocation.TREE, GrowthType.COCOA, WateringNeed.NONE,
          Set.of(Season.SUMMER), Set.of(Climate.HOT),
          LightRequirement.PARTIAL_SHADE, cropYield(5, 10), growthTime(365), regrowthTime(30), PERENNIAL),

    MUSHROOM(PlantLocation.OTHER, GrowthType.MUSHROOM, WateringNeed.NONE,
             Set.of(Season.FALL, Season.SPRING), Set.of(Climate.TEMPERATE, Climate.COLD),
             LightRequirement.SHADE, cropYield(2, 5), growthTime(10), regrowthTime(5)),

    // Aquatic
    NORI(PlantLocation.AQUATIC, GrowthType.KELP, WateringNeed.NONE,
         Set.of(Season.SPRING, Season.SUMMER, Season.FALL), Set.of(Climate.TEMPERATE, Climate.COLD),
         LightRequirement.PARTIAL_SHADE, cropYield(5, 10), growthTime(21)),
    WAKAME(PlantLocation.AQUATIC, GrowthType.KELP, WateringNeed.NONE,
           Set.of(Season.SPRING, Season.SUMMER, Season.FALL), Set.of(Climate.TEMPERATE, Climate.COLD),
           LightRequirement.PARTIAL_SHADE, cropYield(5, 10), growthTime(28)),

    // Fantasy
    NETHER_WART(PlantLocation.OTHER, GrowthType.NETHER_WART, WateringNeed.NONE,
                Set.of(Season.SUMMER), Set.of(Climate.HOT),
                LightRequirement.SHADE, cropYield(2, 4), growthTime(14)),
    DRAGONBLOOM(PlantLocation.OTHER, GrowthType.CACTUS, WateringNeed.NONE,
                Set.of(Season.SUMMER), Set.of(Climate.HOT),
                LightRequirement.FULL_SUN, cropYield(1), growthTime(21)),
    GLOWROOT(PlantLocation.OTHER, GrowthType.ROOT, WateringNeed.NONE,
             Set.of(Season.FALL, Season.WINTER), Set.of(Climate.COLD),
             LightRequirement.SHADE, cropYield(1, 3), growthTime(14)),
    CHORUS(PlantLocation.OTHER, GrowthType.CHORUS, WateringNeed.NONE,
           Set.of(Season.SPRING, Season.SUMMER), Set.of(Climate.TEMPERATE),
           LightRequirement.FULL_SUN, cropYield(1), growthTime(28)),
    GLOW_BERRY(PlantLocation.OTHER, GrowthType.VINE, WateringNeed.NONE,
               Set.of(Season.SUMMER), Set.of(Climate.TEMPERATE),
               LightRequirement.PARTIAL_SHADE, cropYield(3, 5), growthTime(21), regrowthTime(7), PERENNIAL),
    ;

    CropGroup(final PlantLocation plantLocation, final GrowthType growthType, final WateringNeed wateringNeed,
              final Set<Season> seasons, final Set<Climate> climates,
              final LightRequirement lightRequirement, final CropTag... tags) {
        this.plantLocation = plantLocation;
        this.growthType = growthType;
        this.wateringNeed = wateringNeed;
        this.seasons = seasons;
        this.climates = climates;
        this.lightRequirement = lightRequirement;
        for (CropTag tag : tags) {
            if (tag instanceof CropYield cropYieldTag) {
                this.minYield = cropYieldTag.min();
                this.maxYield = cropYieldTag.max();
            } else if (tag instanceof GrowthTime growthTimeTag) {
                this.growthTime = growthTimeTag.days();
            } else if (tag instanceof RegrowthTime regrowthTimeTag) {
                this.doesRegrow = true;
                this.regrowthTime = regrowthTimeTag.days();
            } else if (tag == PERENNIAL) {
                this.perennial = true;
            }
        }
    }

    private final PlantLocation plantLocation;
    private final GrowthType growthType;
    private final WateringNeed wateringNeed;
    private final Set<Season> seasons;
    private final Set<Climate> climates;
    private final LightRequirement lightRequirement;
    private int minYield = 1;
    private int maxYield = 1;
    private int growthTime = 30;
    private int regrowthTime = 0;
    private boolean doesRegrow = false;
    private boolean perennial = false;

    public boolean doesRegrow() {
        return doesRegrow;
    }

    public int rollRandomYield() {
        if (minYield == maxYield) return minYield;
        return minYield + ThreadLocalRandom.current().nextInt(maxYield - minYield + 1);
    }
}
