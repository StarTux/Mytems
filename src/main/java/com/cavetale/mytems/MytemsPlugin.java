package com.cavetale.mytems;

import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.session.Sessions;
import java.util.EnumMap;
import java.util.Map;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
        for (Mytems it : Mytems.values()) {
            Mytem mytem = it.ctor.apply(this);
            mytems.put(it, mytem);
            mytem.enable();
        }
    }

    public void enter(Player player) {
        sessions.of(player).equipmentDidChange();
    }

    public void exit(Player player) {
        sessions.remove(player);
    }

    void tick() {
        sessions.tick();
    }

    public Mytem getMytem(Mytems key) {
        return mytems.get(key);
    }

    public Mytem getMytem(ItemStack itemStack) {
        if (itemStack == null) return null;
        Mytems key = Mytems.forItem(itemStack);
        if (key == null) return null;
        return getMytem(key);
    }

    public GearItem getGearItem(Mytems key) {
        Mytem mytem = mytems.get(key);
        return mytem instanceof GearItem ? (GearItem) mytem : null;
    }

    public GearItem getGearItem(ItemStack itemStack) {
        if (itemStack == null) return null;
        Mytems key = Mytems.forItem(itemStack);
        if (key == null) return null;
        return getGearItem(key);
    }
}
