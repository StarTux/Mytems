package com.cavetale.mytems;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public final class MytemsTest {
    @Test
    public void test() {
        final Set<Integer> customModelDataSet = new HashSet<>();
        final Set<Character> characterSet = new HashSet<>();
        int lowest = 0xE201;
        for (Mytems mytems : Mytems.values()) {
            if (mytems.customModelData != null) {
                if (mytems.material == null) {
                    throw new IllegalStateException(mytems + ": material is null!");
                }
                if (!MytemsTag.POCKET_MOB.isTagged(mytems)) {
                    if (customModelDataSet.contains(mytems.customModelData)) {
                        throw new IllegalStateException(mytems + ": duplicate custom model data: " + mytems.customModelData);
                    }
                    customModelDataSet.add(mytems.customModelData);
                }
                if (mytems.character != (char) 0) {
                    if ((int) mytems.character < lowest) {
                        lowest = (int) mytems.character;
                    }
                    if (characterSet.contains(mytems.character)) {
                        throw new IllegalStateException(mytems + ": duplicate character: " + Integer.toHexString((int) mytems.character));
                    }
                    characterSet.add(mytems.character);
                }
            } else {
                System.out.println("No custom model data: " + mytems);
            }
        }
        System.out.println("Lowest char: \\u" + Integer.toHexString(lowest).toUpperCase());
    }
}
