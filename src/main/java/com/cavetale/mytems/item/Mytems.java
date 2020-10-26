package com.cavetale.mytems;

import com.cavetale.mytems.item.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public enum Mytems {
    DR_ACULA_STAFF(DrAculaStaff::new),
    FLAME_SHIELD(FlameShield::new),
    STOMPERS(Stompers::new),
    GHAST_BOW(GhastBow::new),
    KITTY_COIN(KittyCoin::new, "kitty_coin");

    private static final Map<String, Mytems> ID_MAP = new HashMap<>();
    public final String key; // unqualified, e.g. dr_acula_staff
    public final String id; // (optional) qualified, e.g. mytems:dr_acula_staff
    public final Function<MytemsPlugin, Mytem> ctor;

    static {
        for (Mytems it : Mytems.values()) {
            ID_MAP.put(it.id, it);
            ID_MAP.put(it.id.split(":")[0], it);
        }
    }

    Mytems(final Function<MytemsPlugin, Mytem> ctor) {
        this.ctor = ctor;
        this.key = name().toLowerCase();
        this.id = "mytems:" + key;
    }

    Mytems(final Function<MytemsPlugin, Mytem> ctor, final String id) {
        this.ctor = ctor;
        this.key = id;
        this.id = id;
    }

    public static Mytems forId(String in) {
        return ID_MAP.get(in);
    }
}
