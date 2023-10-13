package com.cavetale.mytems.item.acula;

import com.cavetale.mytems.Mytems;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import static java.util.UUID.randomUUID;
import static org.bukkit.attribute.AttributeModifier.Operation.*;

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
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(randomUUID(), key.id, 8.0, ADD_NUMBER, EquipmentSlot.CHEST));
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(randomUUID(), key.id, 3.0, ADD_NUMBER, EquipmentSlot.CHEST));
            });
        return item;
    }
}
