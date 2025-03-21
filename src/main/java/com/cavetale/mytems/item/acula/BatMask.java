package com.cavetale.mytems.item.acula;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Attr;
import com.cavetale.mytems.util.Skull;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;

@Getter
public final class BatMask extends AculaItem {
    private final String rawDisplayName = "Bat Mask";
    private final String description = ""
        + "It is unknown who made this mask or for what purpose it was worn,"
        + " but a team of specialists on the occult and vampirism has determined that it resembles a bat.";

    public BatMask(final Mytems key) {
        super(key);
    }

    @Override @SuppressWarnings("LineLength")
    protected ItemStack getRawItemStack() {
        ItemStack item = Skull.create("BatMask",
                                      UUID.fromString("62e67882-f5a4-411b-816f-2f704b3feb51"),
                                      "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjc2NjE5NjUyZmFmZWM5MGNlOThkZjUwMTNjNjNkYzZhNzc3NzZhYjI3ODczYjczZGFmYjJiNmJkZWIxODUifX19",
                                      null);
        item.editMeta(meta -> {
                Attr.add(meta, Attribute.ARMOR, "bat_mask_armor", 3.0, Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD);
                Attr.add(meta, Attribute.ARMOR_TOUGHNESS, "bat_mask_armor_toughness", 3.0, Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD);
            });
        return item;
    }
}
