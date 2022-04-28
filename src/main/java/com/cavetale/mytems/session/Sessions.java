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
import org.bukkit.event.player.PlayerToggleFlightEvent;

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
            Player player = session.getPlayer();
            if (player != null) session.disable(player);
        }
        sessions.clear();
    }

    public List<Session> all() {
        return new ArrayList<>(sessions.values());
    }

    public Session of(Player player) {
        Session session = sessions.get(player.getUniqueId());
        if (session == null) {
            session = new Session(plugin, player);
            sessions.put(player.getUniqueId(), session);
            session.enable(player);
        }
        return session;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onPlayerJoin(PlayerJoinEvent event) {
        of(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Session session = sessions.remove(player.getUniqueId());
        if (session != null) session.disable(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        if (event.isFlying()) return;
        Player player = event.getPlayer();
        of(player).getFlying().onToggleOff(player);
    }

    private void tick() {
        for (Session session : all()) {
            Player player = session.getPlayer();
            if (player == null) {
                sessions.remove(session.getUuid());
                continue;
            }
            try {
                session.tick(player);
            } catch (Exception e) {
                e.printStackTrace();
                session.disable(player);
                sessions.remove(player.getUniqueId());
            }
        }
    }
}
