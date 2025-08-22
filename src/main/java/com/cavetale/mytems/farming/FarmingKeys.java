package com.cavetale.mytems.farming;

import java.time.Instant;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import static com.cavetale.mytems.MytemsPlugin.namespacedKey;

public enum FarmingKeys {
    AGE,
    GROWTH_STAGE,
    GROWTH_START,
    HIBERNATING,
    HYDRATED,
    LAST_DRINK,
    RIPE,
    TOTAL_AGE,
    ;

    private NamespacedKey namespacedKey;

    public NamespacedKey getNamespacedKey() {
        if (namespacedKey == null) {
            namespacedKey = namespacedKey(name().toLowerCase());
        }
        return namespacedKey;
    }

    public boolean getBoolean(PersistentDataContainer tag) {
        return tag.getOrDefault(getNamespacedKey(), PersistentDataType.BOOLEAN, false);
    }

    public void setBoolean(PersistentDataContainer tag, boolean value) {
        tag.set(getNamespacedKey(), PersistentDataType.BOOLEAN, value);
    }

    public long getLong(PersistentDataContainer tag, long dfl) {
        return tag.getOrDefault(getNamespacedKey(), PersistentDataType.LONG, dfl);
    }

    public void setLong(PersistentDataContainer tag, long value) {
        tag.set(getNamespacedKey(), PersistentDataType.LONG, value);
    }

    public int getInt(PersistentDataContainer tag, int dfl) {
        return tag.getOrDefault(getNamespacedKey(), PersistentDataType.INTEGER, dfl);
    }

    public void setInt(PersistentDataContainer tag, int value) {
        tag.set(getNamespacedKey(), PersistentDataType.INTEGER, value);
    }

    public Instant getInstant(PersistentDataContainer tag) {
        return Instant.ofEpochMilli(getLong(tag, 0L));
    }

    public void setInstant(PersistentDataContainer tag, Instant instant) {
        setLong(tag, instant.toEpochMilli());
    }

    public boolean has(PersistentDataContainer tag) {
        return tag.has(getNamespacedKey());
    }

    public void remove(PersistentDataContainer tag) {
        tag.remove(getNamespacedKey());
    }
}
