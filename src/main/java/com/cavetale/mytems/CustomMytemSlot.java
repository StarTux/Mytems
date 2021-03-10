package com.cavetale.mytems;

import lombok.Value;
import org.bukkit.plugin.java.JavaPlugin;

@Value
class CustomMytemSlot {
    protected final JavaPlugin plugin;
    protected final Mytems mytems;
    protected final Mytem mytem;
}
