package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Text;
import java.awt.Color;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
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
    @Getter private final Mytems key;
    @Getter private Component displayName;
    private final String description = ""
        + "Legend has it that this horn once belonged to a magical unicorn which lived over one thousand years ago."
        + " Scientists say it belongs to a narwhal."
        + "\n\n"
        + "Run like the wind. Wearing this horn gives you a speed boost. Put it in your helmet slot."
        + "\n\n";
    private ItemStack prototype;

    protected static Component rainbowify(String in) {
        Component component = Component.empty().decoration(TextDecoration.ITALIC, false);
        int len = in.length();
        for (int i = 0; i < len; i += 1) {
            float pos = (float) i / (float) len;
            int rgb = 0xFFFFFF & Color.HSBtoRGB(pos, 0.66f, 1.0f);
            component = component.append(Component.text(in.substring(i, i + 1)).color(TextColor.color(rgb)));
        }
        return component;
    }

    @Override
    public void enable() {
        displayName = rainbowify("Unicorn Horn");
        prototype = new ItemStack(Material.END_ROD).ensureServerConversions();
        ItemMeta meta = prototype.getItemMeta();
        meta.displayName(displayName);
        meta.lore(Text.wrapLore(description, c -> c.color(NamedTextColor.AQUA)));
        // Attr
        AttributeModifier attr;
        attr = new AttributeModifier(UUID.fromString("4ae9dba0-db6a-4843-9028-554ab26155d3"),
                                     key.id, 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, attr);
        attr = new AttributeModifier(UUID.fromString("f7febe45-8f8c-4cbf-b84a-0c6902670655"),
                                     key.id, 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, attr);
        attr = new AttributeModifier(UUID.fromString("f9e5bc8d-5532-4a6e-a0d9-fe8521f72c9a"),
                                     key.id, 0.15, AttributeModifier.Operation.ADD_SCALAR, EquipmentSlot.HEAD);
        meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, attr);
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
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
