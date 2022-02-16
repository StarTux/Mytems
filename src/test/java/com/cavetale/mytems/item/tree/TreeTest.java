package com.cavetale.mytems.item.tree;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsCategory;
import org.junit.Test;

public final class TreeTest {
    @Test
    public void test() {
        for (Mytems it : Mytems.values()) {
            if (it.category == MytemsCategory.TREE_SEED) {
                CustomTreeType.ofSeed(it);
            }
        }
    }
}
