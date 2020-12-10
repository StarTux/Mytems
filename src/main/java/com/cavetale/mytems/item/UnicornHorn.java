package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Text;
import com.cavetale.worldmarker.ItemMarker;
import java.awt.Color;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@RequiredArgsConstructor
public final class UnicornHorn implements Mytem {
    private final MytemsPlugin plugin;
    public static final Mytems KEY = Mytems.UNICORN_HORN;
    @Getter private BaseComponent[] displayName;
    private final String description = ""
        + ChatColor.AQUA + "Legend has it that this horn once belonged to a magical unicorn which lived over one thousand years ago."
        + " Scientists say it belongs to a narwhal."
        + "\n\n"
        + ChatColor.AQUA + "Run like the wind. Wearing this horn gives you a speed boost. Put it in your helmet slot."
        + "\n\n";
    private ItemStack prototype;

    protected static void rainbowify(ComponentBuilder cb, String in) {
        int len = in.length();
        for (int i = 0; i < len; i += 1) {
            float pos = (float) i / (float) len;
            Color color = new Color(Color.HSBtoRGB(pos, 1.0f, 1.0f));
            cb.append(in.substring(i, i + 1)).color(ChatColor.of(color));
        }
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
        ComponentBuilder cb = new ComponentBuilder();
        rainbowify(cb, "Unicorn Horn");
        displayName = cb.create();
        prototype = new ItemStack(Material.END_ROD).ensureServerConversions();
        ItemMeta meta = prototype.getItemMeta();
        meta.setDisplayNameComponent(displayName);
        meta.setLore(Text.wrapMultiline(description, Text.ITEM_LORE_WIDTH));
        // Attr
        AttributeModifier attr;
        attr = new AttributeModifier(UUID.fromString("4ae9dba0-db6a-4843-9028-554ab26155d3"),
                                     KEY.id, 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, attr);
        attr = new AttributeModifier(UUID.fromString("f7febe45-8f8c-4cbf-b84a-0c6902670655"),
                                     KEY.id, 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, attr);
        attr = new AttributeModifier(UUID.fromString("f9e5bc8d-5532-4a6e-a0d9-fe8521f72c9a"),
                                     KEY.id, 0.15, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HEAD);
        meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, attr);
        //
        prototype.setItemMeta(meta);
        ItemMarker.setId(prototype, KEY.id);
    }

    @Override
    public ItemStack getItem() {
        return prototype.clone();
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }
}
