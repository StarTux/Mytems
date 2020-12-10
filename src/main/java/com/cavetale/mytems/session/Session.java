package com.cavetale.mytems.session;

import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.gear.Equipment;
import com.cavetale.mytems.gear.GearItem;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public final class Session {
    protected final MytemsPlugin plugin;
    protected final Player player;
    protected Map<String, Long> cooldowns = new HashMap<>();
    public static final long NANOS_PER_TICK = 50L * 1000L * 1000L;
    private int equipmentUpdateTicks = 0;
    @Getter private Equipment equipment; // Updated every tick
    @Getter private Flying flying = new Flying(this);

    public Session(final MytemsPlugin plugin, final Player player) {
        this.plugin = plugin;
        this.player = player;
        equipment = new Equipment(plugin);
    }

    public void disable() {
        flying.disable();
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

    void tick() {
        equipment.clear();
        equipment.loadPlayer(player);
        if (equipmentUpdateTicks > 0) {
            equipmentUpdateTicks -= 1;
            if (equipmentUpdateTicks == 0) {
                updateEquipment();
            }
        }
        flying.tick();
    }

    /**
     * Update all GearItems in the player's inventory and apply or
     * remove effects as needed.
     */
    private void updateEquipment() {
        if (!equipment.isEmpty()) {
            for (Equipment.Equipped equipped : equipment.getItems()) {
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
        for (Equipment.Slot slot : Equipment.Slot.values()) {
            if (equipment.containsSlot(slot)) continue;
            ItemStack itemStack = inventory.getItem(slot.bukkitEquipmentSlot);
            if (itemStack != null) updateLooseItem(itemStack);
        }
    }

    private void updateLooseItem(ItemStack itemStack) {
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
