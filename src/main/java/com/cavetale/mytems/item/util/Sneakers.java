package com.cavetale.mytems.item.util;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Attr;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static org.bukkit.Color.fromRGB;

@Getter
public final class Sneakers implements Mytem {
    private final Mytems key;
    private final ItemStack prototype;
    private final Component displayName;

    public Sneakers(final Mytems mytems) {
        this.key = mytems;
        this.displayName = text("Sneakers", RED);
        List<Component> tooltip = List.of(displayName,
                                          text(tiny("Previously worn by"), BLUE),
                                          text(tiny("Sanic the Hedgehag"), BLUE));
        this.prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                Attr.add(meta, Attribute.MOVEMENT_SPEED, "sneakers_speed", 0.1, Operation.ADD_NUMBER, EquipmentSlotGroup.FEET);
                if (meta instanceof LeatherArmorMeta meta2) {
                    meta2.setColor(fromRGB(0xffffff));
                }
                tooltip(meta, tooltip);
                meta.addItemFlags(ItemFlag.HIDE_DYE);
                key.markItemMeta(meta);
            });
    }

    @Override
    public void enable() { }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }
}
