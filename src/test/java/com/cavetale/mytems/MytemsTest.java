package com.cavetale.mytems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;

public final class MytemsTest {
    @Test
    public void test() {
        final Set<Integer> customModelDataSet = new HashSet<>();
        final Set<Character> characterSet = new HashSet<>();
        int min = Integer.MAX_VALUE;
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
                    int num = (int) mytems.character;
                    throw new IllegalStateException(mytems + ": duplicate character: " + num + " 0x" + Integer.toHexString(num));
                }
                characterSet.add(mytems.character);
            }
            for (Character it : mytems.characters) {
                if (it == null || it == (char) 0 || it == mytems.character) continue;
                if (characterSet.contains(it)) {
                    int num = (int) it;
                    throw new IllegalStateException(mytems + ": duplicate character: " + num + " 0x" + Integer.toHexString(num));
                }
                characterSet.add(it);
                customModelDataSet.add((int) it);
            }
        }
        int max;
        List<Integer> gaps = new ArrayList<>();
        for (max = min;; max += 1) {
            if (!customModelDataSet.contains(max)) {
                if (!customModelDataSet.contains(max + 1)) {
                    max -= 1;
                    break;
                }
                gaps.add(max);
            }
        }
        System.out.println("// CustomModelData Range: " + min + "..." + max);
        System.out.println("// CustomModelData Gaps: " + gaps);
        System.out.println("// CustomModelData Next: " + (max + 1));
    }
}
