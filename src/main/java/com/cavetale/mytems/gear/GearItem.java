package com.cavetale.mytems.gear;

import com.cavetale.mytems.Mytem;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * A special category of mytems which provide buffs and set bonuses and
 * stuff.
 */
public interface GearItem extends Mytem {
    default ItemSet getItemSet() {
       return null;
    }

    /**
     * Update an item in an inventory.
     * @param meta the item meta
     * @param the player owning the item or null
     * @param the player equipment holding the item or null
     * @param the valid slot the item is in, or null
     */
    void updateItemLore(ItemMeta meta, @Nullable Player player, @Nullable Equipment equipment, @Nullable Equipment.Slot slot);

    default void updateItemLore(ItemMeta meta) {
        updateItemLore(meta, null, null, null);
    }
}
