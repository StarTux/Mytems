package com.cavetale.mytems.item.acula;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Repairable;
import static org.bukkit.attribute.Attribute.*;
import static org.bukkit.attribute.AttributeModifier.Operation.*;
import static org.bukkit.inventory.EquipmentSlot.*;

@Getter
public final class FlameShield extends AculaItem {
    private final String rawDisplayName = "Flame Shield";
    private final String description = "\n\n"
        + ChatColor.GRAY + "After the castle was completely burned down to the ground, this item remained intact."
        + "\n\n"
        + ChatColor.GRAY + "For years it was kept in a secret vault, until one day, when it vanished.";

    public FlameShield(final Mytems key) {
        super(key);
    }

    @Override @SuppressWarnings("LineLength")
    protected ItemStack getRawItemStack() {
        ItemStack item = Items.deserialize("H4sIAAAAAAAAAHXLsQ6CMBSF4QNFxZqAg0/jiLg7GPcrVKjSNmkvg29vMQ5dPNufL0cCAruWmG7KB+0sIA8lct1jb7RVnacHH8Oo1dRLCKZBYN2SoUEhTqJqJte9zpY1v6+LFg2FxeotygsxK2+DjF2U2PwawrinwOrkJucjZUgsv/NfMj6hOiUx+Dmx6nuNOVvO8AESRyVa5QAAAA==");
        item.editMeta(meta -> {
                meta.setUnbreakable(true);
                meta.displayName(displayName);
                Repairable repairable = (Repairable) meta;
                repairable.setRepairCost(9999);
                meta.addAttributeModifier(GENERIC_ARMOR,
                                          new AttributeModifier(UUID.randomUUID(), key.id, 4, ADD_NUMBER, HAND));
                meta.addAttributeModifier(GENERIC_ARMOR_TOUGHNESS,
                                          new AttributeModifier(UUID.randomUUID(), key.id, 1, ADD_NUMBER, HAND));
                meta.addAttributeModifier(GENERIC_KNOCKBACK_RESISTANCE,
                                          new AttributeModifier(UUID.randomUUID(),
                                                                key.id, 0.1, ADD_NUMBER, HAND));
            });
        return item;
    }
}
