package com.cavetale.mytems.item.mobface;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsCategory;
import org.junit.Test;

public final class MobFaceTest {
    @Test
    public void test() {
        for (Mytems mytems : Mytems.values()) {
            if (mytems.category != MytemsCategory.MOB_FACE) continue;
            assert (MobFace.of(mytems) != null) : "No MobFace for Mytems." + mytems;
        }
    }
}
