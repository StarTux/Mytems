package com.cavetale.mytems.session;

import com.cavetale.mytems.MytemsPlugin;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public final class Sessions {
    private final MytemsPlugin plugin;
    private final Map<UUID, Session> sessions = new HashMap<>();

    public Sessions enable() {
        Bukkit.getScheduler().runTaskTimer(plugin, this::tick, 1L, 1L);
        return this;
    }

    public Session of(Player player) {
        return sessions.computeIfAbsent(player.getUniqueId(), u -> new Session(plugin, player));
    }

    public Session of(UUID uuid) {
        return sessions.get(uuid);
    }

    public Session remove(UUID uuid) {
        return sessions.remove(uuid);
    }

    public Session remove(Player player) {
        return remove(player.getUniqueId());
    }

    public void tick() {
        for (Session session : sessions.values()) {
            session.tick();
        }
    }
}
