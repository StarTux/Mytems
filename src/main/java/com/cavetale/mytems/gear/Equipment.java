package com.cavetale.mytems.gear;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

@Getter
public final class Equipment {
    private final Map<Slot, Equipped> equipped = new EnumMap<>(Slot.class);
    private final Map<ItemSet, Integer> itemSets = new IdentityHashMap<>();
    private final List<SetBonus> setBonuses = new ArrayList<>();
    private final List<EntityAttribute> entityAttributes = new ArrayList<>();

    /**
     * Fetch a cached version if possible.  If not, create a new
     * temporary instance.
     */
    public static Equipment of(LivingEntity living) {
        if (living instanceof Player player) {
            return MytemsPlugin.getInstance().getSessions().of(player).getEquipment();
        }
        Equipment result = new Equipment();
        result.loadLivingEntity(living);
        return result;
    }

    public void clear() {
        equipped.clear();
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
            if (item == null) continue;
            if (!slot.guess(item)) continue;
            Mytems mytems = Mytems.forItem(item); // may yield null
            if (mytems == null) continue;
            Mytem mytem = mytems.getMytem();
            if (!(mytem instanceof GearItem)) continue;
            GearItem gearItem = (GearItem) mytem;
            equipped.put(slot, new Equipped(slot, item, gearItem));
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
        entityAttributes.clear();
        for (SetBonus setBonus : setBonuses) {
            int has = countSetItems(setBonus.getItemSet());
            List<EntityAttribute> list = setBonus.getEntityAttributes(has);
            if (list == null) continue;
            entityAttributes.addAll(list);
        }
    }

    public int countSetItems(ItemSet itemSet) {
        return itemSets.getOrDefault(itemSet, 0);
    }

    public boolean hasSetBonus(SetBonus setBonus) {
        return setBonuses.contains(setBonus);
    }

    public boolean isEmpty() {
        return equipped.isEmpty();
    }

    public List<Equipped> getItems() {
        return new ArrayList<>(equipped.values());
    }

    public boolean containsSlot(Slot slot) {
        return equipped.containsKey(slot);
    }
}
