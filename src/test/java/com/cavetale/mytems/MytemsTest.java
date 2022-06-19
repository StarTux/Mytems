package com.cavetale.mytems;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public final class MytemsTest {
    @Test
    public void test() {
        final Set<Integer> customModelDataSet = new HashSet<>();
        final Set<Character> characterSet = new HashSet<>();
        int min = Integer.MAX_VALUE;;
        for (Mytems mytems : Mytems.values()) {
            if (mytems.material == null) {
                throw new IllegalStateException(mytems + ": material is null!");
            }
            if (mytems.customModelData != null) {
                if (mytems.category != MytemsCategory.POCKET_MOB) {
                    if (customModelDataSet.contains(mytems.customModelData)) {
                        throw new IllegalStateException(mytems + ": duplicate custom model data: " + mytems.customModelData);
                    }
                    customModelDataSet.add(mytems.customModelData);
                }
                if (min > mytems.customModelData) min = mytems.customModelData;
            } else {
                System.out.println("No custom model data: " + mytems);
            }
            if (mytems.character != (char) 0) {
                if (characterSet.contains(mytems.character)) {
                    throw new IllegalStateException(mytems + ": duplicate character: " + Integer.toHexString((int) mytems.character));
                }
                characterSet.add(mytems.character);
            }
        }
        int max;
        for (max = min;; max += 1) {
            if (!customModelDataSet.contains(max)) {
                if (!customModelDataSet.contains(max + 1)) break;
                System.out.println("Mytems Model Gap: " + max);
            }
        }
        System.out.println("Mytems Models: " + min + "..." + max);
    }
}
