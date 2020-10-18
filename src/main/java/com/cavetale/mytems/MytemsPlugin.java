package com.cavetale.mytems;

import com.cavetale.mytems.item.DrAculaStaff;
import com.cavetale.mytems.item.FlameShield;
import com.cavetale.mytems.session.Sessions;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class MytemsPlugin extends JavaPlugin {
    final MytemsCommand mytemsCommand = new MytemsCommand(this);
    final EventListener eventListener = new EventListener(this);
    final Sessions sessions = new Sessions(this);
    DrAculaStaff drAculaStaff;
    FlameShield flameShield;

    @Override
    public void onEnable() {
        mytemsCommand.enable();
        eventListener.enable();
        sessions.enable();
        drAculaStaff = new DrAculaStaff(this).enable();
        flameShield = new FlameShield(this).enable();
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

    public void enter(Player player) {
        sessions.of(player);
    }

    public void exit(Player player) {
        sessions.remove(player);
    }

    void tick() {
        sessions.tick();
    }
}
