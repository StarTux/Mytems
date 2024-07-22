package com.cavetale.mytems.item.acula;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Attr;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;

@Getter
public final class BatCape extends AculaItem {
    private final String rawDisplayName = "Bat Cape";
    private final String description = "The wrinkles can be ironed out.";

    public BatCape(final Mytems key) {
        super(key);
    }

    @Override
    protected ItemStack getRawItemStack() {
        var item = new ItemStack(Material.ELYTRA);
        item.editMeta(meta -> {
                Attr.add(meta, Attribute.GENERIC_ARMOR, "bat_cape_armor", 8.0, Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST);
                Attr.add(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, "bat_cape_armor_toughness", 3.0, Operation.ADD_NUMBER, EquipmentSlotGroup.CHEST);
            });
        return item;
    }
}
