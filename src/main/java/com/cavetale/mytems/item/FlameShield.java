package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.worldmarker.ItemMarker;
import java.awt.Color;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

@RequiredArgsConstructor
public final class FlameShield implements Mytem {
    private final MytemsPlugin plugin;
    public static final String ID = "mytems:flame_shield";
    ItemStack baseItem;

    public FlameShield enable() {
        byte[] bytes = Base64.getDecoder().decode("H4sIAAAAAAAAAHXLsQ6CMBSF4QNFxZqAg0/jiLg7GPcrVKjSNmkvg29vMQ5dPNufL0cCAruWmG7KB+0sIA8lct1jb7RVnacHH8Oo1dRLCKZBYN2SoUEhTqJqJte9zpY1v6+LFg2FxeotygsxK2+DjF2U2PwawrinwOrkJucjZUgsv/NfMj6hOiUx+Dmx6nuNOVvO8AESRyVa5QAAAA==");
        baseItem = ItemStack.deserializeBytes(bytes);
        return this;
    }

    @Override
    public String getId() {
        return ID;
    }

    public ItemStack create() {
        ItemStack item = baseItem.clone();
        ItemMeta meta = item.getItemMeta();
        ComponentBuilder cb = new ComponentBuilder();
        String name = "Flame Shield";
        int len = name.length();
        int iter = 255 / name.length() * 3 / 4;
        for (int i = 0; i < name.length(); i += 1) {
            cb.append(name.substring(i, i + 1)).color(ChatColor.of(new Color(255 - iter * i, 0, 0)));
        }
        meta.setDisplayNameComponent(cb.create());
        List<String> lore = Arrays
            .asList("",
                    ChatColor.GRAY + "After the castle was",
                    ChatColor.GRAY + "completely burned down",
                    ChatColor.GRAY + "to the ground, this",
                    ChatColor.GRAY + "item remained intact.",
                    "",
                    ChatColor.GRAY + "For years it was kept",
                    ChatColor.GRAY + "in a secret vault,",
                    ChatColor.GRAY + "until one day, when",
                    ChatColor.GRAY + "it vanished.");
        meta.setLore(lore);
        Repairable repairable = (Repairable) meta;
        repairable.setRepairCost(9999);
        meta.addEnchant(Enchantment.DURABILITY, 4, true);
        meta.addEnchant(Enchantment.MENDING, 1, true);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR,
                                  new AttributeModifier(UUID.randomUUID(), ID, 4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,
                                  new AttributeModifier(UUID.randomUUID(), ID, 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE,
                                  new AttributeModifier(UUID.randomUUID(), ID, 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        item.setItemMeta(meta);
        ItemMarker.setId(item, ID);
        return item;
    }
}
