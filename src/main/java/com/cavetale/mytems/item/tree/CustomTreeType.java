package com.cavetale.mytems.item.tree;

import com.cavetale.mytems.Mytems;

public enum CustomTreeType {
    OAK,
    BIRCH,
    SPRUCE,
    JUNGLE,
    ACACIA,
    DARK_OAK;

    public static CustomTreeType ofSeed(Mytems mytems) {
        String name = mytems.name();
        return valueOf(name.substring(0, name.length() - 5));
    }
}
