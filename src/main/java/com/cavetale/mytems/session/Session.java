package com.cavetale.mytems.session;

import com.cavetale.mytems.MytemsPlugin;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class Session {
    private final MytemsPlugin plugin;
    @Getter private final UUID uuid;
    private Map<String, Long> cooldowns = new HashMap<>();
    static final long NANOS_PER_TICK = 50L * 1000L * 1000L;

    public Session(final MytemsPlugin plugin, final Player player) {
        this.plugin = plugin;
        this.uuid = player.getUniqueId();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public void setCooldown(String key, int ticks) {
        long now = System.nanoTime();
        cooldowns.put(key, now + (long) ticks * NANOS_PER_TICK);
    }

    public boolean isOnCooldown(String key) {
        Long cd = cooldowns.get(key);
        if (cd == null) return false;
        long now = System.nanoTime();
        return cd < now;
    }

    public long getCooldownInTicks(String key) {
        Long cd = cooldowns.get(key);
        if (cd == null) return 0L;
        long now = System.nanoTime();
        if (now > cd) return 0L;
        return (cd - now) / NANOS_PER_TICK;
    }

    public void tick() {
        // ?
    }
}
