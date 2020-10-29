package com.cavetale.mytems.gear;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.destroystokyo.paper.MaterialTags;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public final class Equipment {
    private final MytemsPlugin plugin;
    private final Map<Slot, Equipped> items = new EnumMap<>(Slot.class);
    private final Map<ItemSet, Integer> itemSets = new IdentityHashMap<>();
    @Getter private final List<SetBonus> setBonuses = new ArrayList<>();

    public Equipment(final MytemsPlugin plugin) {
        this.plugin = plugin;
    }

    public enum Slot {
        MAIN_HAND(EquipmentSlot.HAND),
        OFF_HAND(EquipmentSlot.OFF_HAND),
        HELMET(EquipmentSlot.HEAD),
        CHESTPLATE(EquipmentSlot.CHEST),
        LEGGINGS(EquipmentSlot.LEGS),
        BOOTS(EquipmentSlot.FEET);

        public final EquipmentSlot bukkitEquipmentSlot;

        Slot(final EquipmentSlot bukkitEquipmentSlot) {
            this.bukkitEquipmentSlot = bukkitEquipmentSlot;
        }

        public boolean guess(ItemStack item) {
            if (item == null) return false;
            Material mat = item.getType();
            if (MaterialTags.HEAD_EQUIPPABLE.isTagged(mat)) return this == HELMET;
            if (MaterialTags.CHEST_EQUIPPABLE.isTagged(mat)) return this == CHESTPLATE;
            if (MaterialTags.LEGGINGS.isTagged(mat)) return this == LEGGINGS;
            if (MaterialTags.BOOTS.isTagged(mat)) return this == BOOTS;
            return this == MAIN_HAND || this == OFF_HAND;
        }
    }

    @RequiredArgsConstructor
    public static final class Equipped {
        public final Slot slot;
        public final ItemStack itemStack;
        public final GearItem gearItem;
    }

    public void clear() {
        items.clear();
        itemSets.clear();
        setBonuses.clear();
    }

    public void loadPlayer(Player player) {
        loadLivingEntity(player);
    }

    public void loadLivingEntity(LivingEntity livingEntity) {
        EntityEquipment entityEquipment = livingEntity.getEquipment();
        for (Slot slot : Slot.values()) {
            ItemStack item = entityEquipment.getItem(slot.bukkitEquipmentSlot);
            if (!slot.guess(item)) continue;
            Mytems mytems = Mytems.forItem(item); // may yield null
            if (mytems == null) continue;
            Mytem mytem = plugin.getMytem(mytems);
            if (!(mytem instanceof GearItem)) continue;
            GearItem gearItem = (GearItem) mytem;
            items.put(slot, new Equipped(slot, item, gearItem));
            ItemSet itemSet = gearItem.getItemSet();
            if (itemSet != null) {
                int count = itemSets.compute(itemSet, (is, num) -> num == null ? 1 : num + 1);
            }
        }
        for (Map.Entry<ItemSet, Integer> entry : itemSets.entrySet()) {
            ItemSet itemSet = entry.getKey();
            int level = entry.getValue();
            for (SetBonus setBonus : itemSet.getSetBonuses()) {
                if (level >= setBonus.getRequiredItemCount()) {
                    setBonuses.add(setBonus);
                }
            }
        }
    }

    public int countSetItems(ItemSet itemSet) {
        Integer val = itemSets.get(itemSet);
        return val == null ? 0 : val;
    }

    public boolean hasSetBonus(SetBonus setBonus) {
        return setBonuses.contains(setBonus);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public List<Equipped> getItems() {
        return items.values().stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public boolean containsSlot(Slot slot) {
        return items.containsKey(slot);
    }
}
