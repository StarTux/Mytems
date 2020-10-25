package com.cavetale.mytems;

import com.cavetale.mytems.item.DrAculaStaff;
import com.cavetale.mytems.item.FlameShield;
import com.cavetale.mytems.item.GhastBow;
import com.cavetale.mytems.item.Stompers;
import com.cavetale.mytems.session.Sessions;
import java.util.EnumMap;
import java.util.Map;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class MytemsPlugin extends JavaPlugin {
    final MytemsCommand mytemsCommand = new MytemsCommand(this);
    final EventListener eventListener = new EventListener(this);
    final Sessions sessions = new Sessions(this);
    private Map<Mytems, Mytem> mytems = new EnumMap<>(Mytems.class);

    @Override
    public void onEnable() {
        mytemsCommand.enable();
        eventListener.enable();
        sessions.enable();
        enableItems();
        for (Player player : Bukkit.getOnlinePlayers()) {
            enter(player);
        }
        Bukkit.getScheduler().runTaskTimer(this, this::tick, 1L, 1L);
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            exit(player);
        }
    }

    public void enableItems() {
        mytems.put(Mytems.DR_ACULA_STAFF, new DrAculaStaff(this));
        mytems.put(Mytems.FLAME_SHIELD, new FlameShield(this));
        mytems.put(Mytems.STOMPERS, new Stompers(this));
        mytems.put(Mytems.GHAST_BOW, new GhastBow(this));
        for (Mytems it : Mytems.values()) {
            Mytem mytem = mytems.get(it);
            if (mytem == null) throw new IllegalStateException(it + "=null");
            mytem.enable();
        }
    }

    public void enter(Player player) {
        sessions.of(player);
    }

    public void exit(Player player) {
        sessions.remove(player);
    }

    void tick() {
        sessions.tick();
    }

    public Mytem getMytem(Mytems it) {
        return mytems.get(it);
    }
}
