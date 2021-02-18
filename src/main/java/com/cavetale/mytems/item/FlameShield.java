package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Text;
import com.cavetale.worldmarker.item.ItemMarker;
import java.util.Base64;
import java.util.UUID;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

public final class FlameShield extends AculaItem {
    public static final Mytems KEY = Mytems.FLAME_SHIELD;
    private ItemStack baseItem;
    private String description = "\n\n"
        + ChatColor.GRAY + "After the castle was completely burned down to the ground, this item remained intact."
        + "\n\n"
        + ChatColor.GRAY + "For years it was kept in a secret vault, until one day, when it vanished.";

    public FlameShield(final MytemsPlugin plugin) {
        super(plugin);
    }

    @Override
    public Mytems getKey() {
        return KEY;
    }

    @Override
    public String getId() {
        return KEY.id;
    }

    @Override
    public void enable() {
        displayName = creepify("Flame Shield", false);
        @SuppressWarnings("LineLength")
        final byte[] bytes = Base64.getDecoder().decode("H4sIAAAAAAAAAHXLsQ6CMBSF4QNFxZqAg0/jiLg7GPcrVKjSNmkvg29vMQ5dPNufL0cCAruWmG7KB+0sIA8lct1jb7RVnacHH8Oo1dRLCKZBYN2SoUEhTqJqJte9zpY1v6+LFg2FxeotygsxK2+DjF2U2PwawrinwOrkJucjZUgsv/NfMj6hOiUx+Dmx6nuNOVvO8AESRyVa5QAAAA==");
        baseItem = ItemStack.deserializeBytes(bytes);
        baseLore = Text.toBaseComponents(Text.wrapMultiline(description, Text.ITEM_LORE_WIDTH));
        prototype = create();
    }

    public ItemStack create() {
        ItemStack item = baseItem.clone();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayNameComponent(displayName);
        Repairable repairable = (Repairable) meta;
        repairable.setRepairCost(9999);
        meta.addEnchant(Enchantment.DURABILITY, 4, true);
        meta.addEnchant(Enchantment.MENDING, 1, true);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR,
                                  new AttributeModifier(UUID.randomUUID(), KEY.id, 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,
                                  new AttributeModifier(UUID.randomUUID(), KEY.id, 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE,
                                  new AttributeModifier(UUID.randomUUID(), KEY.id, 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        item.setItemMeta(meta);
        ItemMarker.setId(item, KEY.id);
        return item;
    }
}
