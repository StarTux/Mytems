package com.cavetale.mytems;

import com.cavetale.mytems.gear.Equipment;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.session.Sessions;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Tag;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class MytemsPlugin extends JavaPlugin {
    @Getter private static MytemsPlugin instance;
    final MytemsCommand mytemsCommand = new MytemsCommand(this);
    final EventListener eventListener = new EventListener(this);
    final Sessions sessions = new Sessions(this);
    private Map<Mytems, Mytem> mytems = new EnumMap<>(Mytems.class);
    private List<CustomMytemSlot> customMytemSlots = new ArrayList<>();
    private boolean fixAllPlayerInventoriesScheduled = false;

    @Override
    public void onEnable() {
        instance = this;
        mytemsCommand.enable();
        eventListener.enable();
        enableItems();
        fixAllPlayerInventoriesLater();
        sessions.enable();
    }

    @Override
    public void onDisable() {
        sessions.disable();
        for (Mytem mytem : mytems.values()) {
            try {
                mytem.disable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mytems.clear();
    }

    public void enableItems() {
        for (Mytems it : Mytems.values()) {
            Mytem mytem = it.ctor.apply(it);
            mytems.put(it, mytem);
            mytem.enable();
        }
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

    public void fixAllPlayerInventoriesLater() {
        if (fixAllPlayerInventoriesScheduled) return;
        fixAllPlayerInventoriesScheduled = true;
        Bukkit.getScheduler().runTaskLater(this, () -> {
                fixAllPlayerInventoriesScheduled = false;
                fixAllPlayerInventories();
            }, 0L);
    }

    public void fixAllPlayerInventories() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            fixPlayerInventory(player);
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

    /**
     * Attempt to update an item. This means generating a new Mytem
     * with the same serialized data if it's mytem. Or a container
     * with all updated contained items.
     *
     * @return the ItemStack if it was fixed and needs updating in its
     *   context, null otherwise
     */
    public ItemStack fixItemStack(ItemStack oldItemStack) {
        if (oldItemStack == null || oldItemStack.getAmount() == 0) return null;
        Mytems key = Mytems.forItem(oldItemStack);
        if (key != null) {
            String serialized = key.serializeItem(oldItemStack);
            ItemStack newItemStack = Mytems.deserializeItem(serialized);
            if (newItemStack == null) return null;
            return newItemStack.equals(oldItemStack) ? null : newItemStack;
        }
        if (Tag.SHULKER_BOXES.isTagged(oldItemStack.getType())) {
            if (!oldItemStack.hasItemMeta()) return null;
            BlockStateMeta meta = (BlockStateMeta) oldItemStack.getItemMeta();
            ShulkerBox shulkerBox = (ShulkerBox) meta.getBlockState();
            int count = fixInventory(shulkerBox.getInventory());
            if (count == 0) return null;
            meta.setBlockState(shulkerBox);
            oldItemStack.setItemMeta(meta);
            return oldItemStack;
        }
        return null;
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
        instance.fixAllPlayerInventoriesLater();
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
