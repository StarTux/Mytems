package com.cavetale.mytems.session;

import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.gear.Equipment;
import com.cavetale.mytems.gear.Equipped;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.gear.Slot;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

@Getter
public final class Session {
    protected final MytemsPlugin plugin;
    protected final UUID uuid;
    protected final String name;
    protected Map<String, Long> cooldowns = new HashMap<>();
    public static final long MILLIS_PER_TICK = 50L;
    private int equipmentUpdateTicks = 0;
    protected Equipment equipment; // Updated every tick
    protected Flying flying = new Flying(this);
    protected Attributes attributes = new Attributes(this);
    protected final Favorites favorites = new Favorites();

    public Session(final MytemsPlugin plugin, final Player player) {
        this.plugin = plugin;
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        equipment = new Equipment(plugin);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public Session enable() {
        attributes.enable();
        loadEquipment();
        equipmentDidChange();
        return this;
    }

    public void disable(Player player) {
        flying.disable(player);
        attributes.disable(player);
    }

    public void setCooldown(String key, int ticks) {
        long now = System.currentTimeMillis();
        cooldowns.put(key, now + (long) ticks * MILLIS_PER_TICK);
    }

    public boolean isOnCooldown(String key) {
        Long cd = cooldowns.get(key);
        if (cd == null) return false;
        long now = System.currentTimeMillis();
        return cd < now;
    }

    public long getCooldownInTicks(String key) {
        Long cd = cooldowns.get(key);
        if (cd == null) return 0L;
        long now = System.currentTimeMillis();
        if (now > cd) return 0L;
        return (cd - now) / MILLIS_PER_TICK;
    }

    public void tick(Player player) {
        loadEquipment();
        if (equipmentUpdateTicks > 0) {
            equipmentUpdateTicks -= 1;
            if (equipmentUpdateTicks == 0) {
                updateEquipment();
            }
        }
        flying.tick(player);
        attributes.tick(player);
        for (SetBonus setBonus : equipment.getSetBonuses()) {
            setBonus.tick(player);
        }
    }

    public void loadEquipment() {
        Player player = getPlayer();
        equipment.clear();
        equipment.loadPlayer(player);
    }

    /**
     * Update all GearItems in the player's inventory and apply or
     * remove effects as needed.
     */
    private void updateEquipment() {
        Player player = getPlayer();
        if (!equipment.isEmpty()) {
            for (Equipped equipped : equipment.getItems()) {
                ItemMeta meta = equipped.itemStack.getItemMeta();
                equipped.gearItem.updateItemLore(meta, player, equipment, equipped.slot);
                equipped.itemStack.setItemMeta(meta);
            }
        }
        PlayerInventory inventory = player.getInventory();
        int held = inventory.getHeldItemSlot();
        for (int i = 0; i < 36; i += 1) {
            if (i == held) continue;
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack != null) updateLooseItem(itemStack);
        }
        // Update invalid equipment slots (this boots in hand)
        for (Slot slot : Slot.values()) {
            if (equipment.containsSlot(slot)) continue;
            ItemStack itemStack = inventory.getItem(slot.bukkitEquipmentSlot);
            if (itemStack != null) updateLooseItem(itemStack);
        }
    }

    private void updateLooseItem(ItemStack itemStack) {
        Player player = getPlayer();
        GearItem gearItem = plugin.getGearItem(itemStack);
        if (gearItem == null) return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        gearItem.updateItemLore(itemMeta, player, equipment, null);
        itemStack.setItemMeta(itemMeta);
    }

    public void equipmentDidChange() {
        equipmentUpdateTicks = 6;
    }
}
