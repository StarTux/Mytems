package com.cavetale.mytems;

import java.util.HashMap;
import java.util.Map;

public enum Mytems {
    DR_ACULA_STAFF,
    FLAME_SHIELD,
    STOMPERS,
    GHAST_BOW;

    private static final Map<String, Mytems> ID_MAP = new HashMap<>();
    public final String key;
    public final String id;

    static {
        for (Mytems it : Mytems.values()) {
            ID_MAP.put(it.id, it);
            ID_MAP.put(it.id.split(":")[0], it);
        }
    }

    Mytems() {
        this.key = name().toLowerCase();
        this.id = "mytems:" + key;
    }

    public static Mytems forId(String in) {
        return ID_MAP.get(in);
    }
}
