package com.cavetale.mytems;

import com.cavetale.mytems.gear.Equipment;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.session.Sessions;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class MytemsPlugin extends JavaPlugin {
    @Getter private static MytemsPlugin instance;
    final MytemsCommand mytemsCommand = new MytemsCommand(this);
    final EventListener eventListener = new EventListener(this);
    final Sessions sessions = new Sessions(this);
    private Map<Mytems, Mytem> mytems = new EnumMap<>(Mytems.class);
    private List<CustomMytemSlot> customMytemSlots = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
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
            Mytem mytem = it.ctor.apply(it);
            mytems.put(it, mytem);
            mytem.enable();
        }
    }

    public void enter(Player player) {
        sessions.of(player).enable();
        fixPlayerInventory(player);
    }

    public void exit(Player player) {
        sessions.of(player).disable();
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

    public Equipment getEquipment(Entity entity) {
        if (entity instanceof Player) {
            return sessions.of((Player) entity).getEquipment();
        } else if (entity instanceof LivingEntity) {
            Equipment equipment = new Equipment(this);
            equipment.loadLivingEntity((LivingEntity) entity);
            return equipment;
        } else {
            return null;
        }
    }

    public int fixPlayerInventory(Player player) {
        return fixInventory(player.getInventory())
            + fixInventory(player.getEnderChest());
    }

    public int fixInventory(Inventory inventory) {
        int count = 0;
        for (int i = 0; i < inventory.getSize(); i += 1) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack == null || itemStack.getAmount() == 0) continue;
            ItemStack newItemStack = fixItemStack(itemStack);
            if (newItemStack == null) continue;
            inventory.setItem(i, newItemStack);
            count += 1;
        }
        return count;
    }

    public ItemStack fixItemStack(ItemStack oldItemStack) {
        Mytem mytem = getMytem(oldItemStack);
        if (mytem == null) return null;
        ItemStack newItemStack = mytem.getItem();
        newItemStack.setAmount(oldItemStack.getAmount());
        Set<ItemFixFlag> itemFixFlags = mytem.getItemFixFlags();
        if (itemFixFlags.contains(ItemFixFlag.COPY_ENCHANTMENTS)) {
            newItemStack.addUnsafeEnchantments(oldItemStack.getEnchantments());
        }
        if (itemFixFlags.contains(ItemFixFlag.COPY_DURABILITY)) {
            ItemMeta oldItemMeta = oldItemStack.getItemMeta();
            ItemMeta newItemMeta = newItemStack.getItemMeta();
            if (oldItemMeta instanceof Damageable && newItemMeta instanceof Damageable) {
                ((Damageable) newItemMeta).setDamage(((Damageable) oldItemMeta).getDamage());
            }
            newItemStack.setItemMeta(newItemMeta);
        }
        if (oldItemStack.equals(newItemStack)) return null;
        return newItemStack;
    }

    public static void registerMytem(JavaPlugin plugin, Mytems mytems, Mytem mytem) {
        Mytem old = instance.mytems.get(mytems);
        instance.mytems.put(mytems, mytem);
        mytem.enable();
        instance.customMytemSlots.removeIf(slot -> slot.mytems == mytems);
        instance.customMytemSlots.add(new CustomMytemSlot(plugin, mytems, mytem));
        if (old != null) {
            try {
                old.disable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void onDisablePlugin(JavaPlugin plugin) {
        for (CustomMytemSlot customMytemSlot : customMytemSlots) {
            if (plugin.equals(customMytemSlot.plugin)) {
                try {
                    customMytemSlot.mytem.disable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Mytem mytem = customMytemSlot.mytems.ctor.apply(customMytemSlot.mytems);
                mytems.put(customMytemSlot.mytems, mytem);
                mytem.enable();
            }
        }
        customMytemSlots.removeIf(slot -> plugin.equals(slot.plugin));
    }
}
