package com.cavetale.mytems;

import java.util.EnumSet;
import java.util.Set;

public enum ItemFixFlag {
    COPY_ENCHANTMENTS,
    COPY_DURABILITY;

    public static final Set<ItemFixFlag> COPY_VANILLA = EnumSet.of(COPY_ENCHANTMENTS, COPY_DURABILITY);
}
