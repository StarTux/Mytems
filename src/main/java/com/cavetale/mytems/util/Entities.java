package com.cavetale.mytems.util;

import com.cavetale.worldmarker.entity.EntityMarker;
import org.bukkit.entity.Entity;

public final class Entities {
    private Entities() { }

    public static void setTransient(Entity entity) {
        EntityMarker.setId(entity, "transient");
        entity.setPersistent(false);
    }

    public static boolean isTransient(Entity entity) {
        return EntityMarker.hasId(entity, "transient");
    }
}
