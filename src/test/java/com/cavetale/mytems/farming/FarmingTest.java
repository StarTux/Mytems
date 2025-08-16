package com.cavetale.mytems.farming;

import java.util.EnumSet;
import org.junit.Test;
import static com.cavetale.core.util.CamelCase.toCamelCase;

public final class FarmingTest {
    @Test
    public void test() {
        PlantLocation oldPlantLocation = null;
        for (CropGroup cropGroup : CropGroup.values()) {
            int count = 0;
            final EnumSet<GrowthType> growthTypes = EnumSet.noneOf(GrowthType.class);
            for (FarmingPlantType farmingPlantType : FarmingPlantType.values()) {
                if (farmingPlantType.getCropGroup() != cropGroup) {
                    continue;
                }
                count += 1;
                growthTypes.add(farmingPlantType.getGrowthType());
            }
            if (count == 0) {
                System.err.println("Unused Crop Group: " + cropGroup);
            }
            if (growthTypes.size() != 1) {
                System.err.println("Multiple growth types: " + cropGroup + ": " + growthTypes);
            }
            if (oldPlantLocation != cropGroup.getPlantLocation()) {
                oldPlantLocation = cropGroup.getPlantLocation();
                System.out.println("");
                System.out.println("    // " + toCamelCase(" ", oldPlantLocation));
            }
            System.out.println("    " + cropGroup
                               + "(PlantLocation." + cropGroup.getPlantLocation()
                               + ", GrowthType." + growthTypes.iterator().next()
                               + ", WateringNeed." + cropGroup.getWateringNeed()
                               + "),");
        }
    }
}
