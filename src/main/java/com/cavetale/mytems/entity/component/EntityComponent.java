package com.cavetale.mytems.entity.component;

import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

public interface EntityComponent {
    Plugin getPlugin();

    default void attach(Entity entity) {
        EntityComponentTag.of(entity).setComponent(this);
    }
}
