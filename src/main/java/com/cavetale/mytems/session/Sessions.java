package com.cavetale.mytems.session;

import com.cavetale.mytems.MytemsPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public final class Sessions implements Listener {
    private final MytemsPlugin plugin;
    private final Map<UUID, Session> sessions = new HashMap<>();

    public void enable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            of(player);
        }
        Bukkit.getPluginManager().registerEvents(this, plugin);
        Bukkit.getScheduler().runTaskTimer(plugin, this::tick, 1L, 1L);
    }

    public void disable() {
        for (Session session : sessions.values()) {
            session.disable();
        }
        sessions.clear();
    }

    public List<Session> all() {
        return new ArrayList<>(sessions.values());
    }

    public Session of(Player player) {
        return sessions.computeIfAbsent(player.getUniqueId(), u -> new Session(plugin, player).enable());
    }

    protected Session remove(UUID uuid) {
        Session session = sessions.remove(uuid);
        if (session != null) session.disable();
        return  session;
    }

    protected Session remove(Player player) {
        return remove(player.getUniqueId());
    }

    protected void remove(Session session) {
        remove(session.getUuid());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onPlayerJoin(PlayerJoinEvent event) {
        of(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onPlayerQuit(PlayerQuitEvent event) {
        remove(event.getPlayer());
    }

    private void tick() {
        for (Session session : all()) {
            try {
                session.tick();
            } catch (Exception e) {
                remove(session);
                e.printStackTrace();
            }
        }
    }
}
