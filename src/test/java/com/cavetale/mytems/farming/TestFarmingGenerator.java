package com.cavetale.mytems.farming;

import com.cavetale.mytems.Mytems;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import org.junit.Test;

/**
 * Run this test to scan the Cavetale Resource Pack for new image
 * files and create the necessary enums, which can then be pasted in
 * the appropriate files.
 */
public final class TestFarmingGenerator {
    private int nextChar = 0xf3d2;

    @Test
    public void test() throws IOException {
        // Uncomment to run test
        // Console command: mvn test -Dtest="TestFarmingGenerator"
        // generate();
    }

    private void generate() throws IOException {
        final File folder = new File("/home/startux/src/bukkit/CavetaleResourcePack/src/farming/");
        final File itemFolder = new File("/home/startux/src/bukkit/CavetaleResourcePack/src/resourcepack/assets/mytems/textures/item/farming/");
        final List<String> farmingPlantTypeLines = new ArrayList<>();
        final List<String> growthStageLines = new ArrayList<>();
        final List<String> mytemsLines = new ArrayList<>();
        final Set<File> cropFiles = new HashSet<>();
        final Set<File> seedFiles = new HashSet<>();
        final Set<String> cropGroups = new HashSet<>();
        for (CropGroup it : CropGroup.values()) cropGroups.add(it + "");
        for (File plantFolder : folder.listFiles()) {
            if (FarmingPlantType.find(plantFolder.getName()) != null) continue;
            final String plantTypeSmall = plantFolder.getName();
            final String plantType = plantTypeSmall.toUpperCase();
            final String[] tmp = plantType.split("_");
            final String cropGroup = tmp[tmp.length - 1];
            cropGroups.add(cropGroup);
            final String[] stageNames = plantFolder.list();
            Arrays.sort(stageNames);
            growthStageLines.add("");
            for (String stageName : stageNames) {
                if (!stageName.endsWith(".png")) continue;
                final File file = new File(plantFolder, stageName);
                final BufferedImage img = ImageIO.read(file);
                if (img == null) {
                    throw new IllegalStateException("Not found: " + file);
                }
                final int width = img.getWidth();
                final int height = img.getHeight();
                if (width != 16) throw new IllegalStateException(file + ": " + width + "x" + height);
                final String model = switch (height) {
                case 16 -> "X";
                case 32 -> "XTALL";
                case 48 -> "X3TALL";
                default -> throw new IllegalStateException(file + ": " + width + "x" + height);
                };
                stageName = stageName.toUpperCase();
                stageName = stageName.substring(0, stageName.length() - 4);
                growthStageLines.add(stageName + "(FarmingPlantType." + plantType + ", Model." + model + "),");
            }
            // Find the crops
            File cropFile = new File(itemFolder, plantTypeSmall + ".png");
            if (!cropFile.exists()) cropFile = new File(itemFolder, plantTypeSmall + "_bean.png");
            if (!cropFile.exists()) cropFile = new File(itemFolder, plantTypeSmall + "_leaves.png");
            if (!cropFile.exists()) cropFile = new File(itemFolder, plantTypeSmall.replace("berry", "berries") + ".png");
            if (!cropFile.exists()) cropFile = new File(itemFolder, plantTypeSmall.replace("_cherry", "_cherries") + ".png");
            if (!cropFile.exists()) cropFile = new File(itemFolder, plantTypeSmall.replace("_grape", "_grapes") + ".png");
            if (!cropFile.exists()) throw new IllegalStateException("File not found: " + cropFile);
            cropFiles.add(cropFile);
            String cropName = cropFile.getName();
            cropName = cropName.substring(0, cropName.length() - 4);
            mytemsLines.add(cropName.toUpperCase() + "(FarmingCrop.class, STICK, 0x" + Integer.toHexString(nextChar()) + ", MytemsCategory.FOOD),");
            // Find the seeds
            File seedFile = new File(itemFolder, plantTypeSmall + "_seeds.png");
            if (!seedFile.exists()) seedFile = new File(itemFolder, plantTypeSmall + "_spores.png");
            if (!seedFile.exists()) throw new IllegalStateException("File not found: " + seedFile);
            seedFiles.add(seedFile);
            String seedName = seedFile.getName();
            seedName = seedName.substring(0, seedName.length() - 4);
            mytemsLines.add(seedName.toUpperCase() + "(FarmingSeeds.class, STICK, 0x" + Integer.toHexString(nextChar()) + ", MytemsCategory.SEEDS),");
            // Finish plant
            farmingPlantTypeLines.add(plantType + "(CropGroup." + cropGroup + ", GrowthType.UNKNOWN, Mytems." + cropName.toUpperCase() + ", Mytems." + seedName.toUpperCase() + "),");
        }
        for (File file : itemFolder.listFiles()) {
            if (cropFiles.contains(file)) continue;
            if (seedFiles.contains(file)) continue;
            String name = file.getName();
            name = name.substring(0, name.length() - 4);
            if (Mytems.forId(name) != null) continue;
            throw new IllegalStateException("Unexpected file: " + file);
        }
        System.out.println("");
        System.out.println("// Farming Plant Type");
        System.out.println("");
        for (String it : farmingPlantTypeLines) System.out.println(it);
        System.out.println("");
        System.out.println("// Growth Stages");
        for (String it : growthStageLines) System.out.println(it);
        System.out.println("");
        System.out.println("// Mytems");
        System.out.println("");
        for (String it : mytemsLines) System.out.println(it);
        System.out.println("");
        System.out.println("// Crop Groups");
        System.out.println("");
        for (String it : cropGroups) System.out.println(it + ",");
        System.out.println("");
    }

    private int nextChar() {
        nextChar += 1;
        while (Mytems.forCharacter((char) nextChar) != null) {
            nextChar += 1;
        }
        return nextChar;
    }
}
