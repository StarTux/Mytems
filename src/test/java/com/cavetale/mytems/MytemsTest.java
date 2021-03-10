package com.cavetale.mytems;

import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public final class MytemsTest {
    @Test
    public void test() {
        final Set<Integer> customModelDataSet = new HashSet<>();
        for (Mytems mytems : Mytems.values()) {
            if (mytems.customModelData != null) {
                if (mytems.material == null) {
                    throw new IllegalStateException(mytems + ": material is null!");
                }
                if (customModelDataSet.contains(mytems.customModelData)) {
                    throw new IllegalStateException(mytems + ": duplicate custom model data: " + mytems.customModelData);
                }
                customModelDataSet.add(mytems.customModelData);
            }
        }
    }
}
