package com.cavetale.mytems.item.util;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Items.text;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static org.bukkit.Color.fromRGB;
import static org.bukkit.attribute.AttributeModifier.Operation.ADD_NUMBER;
import static org.bukkit.inventory.EquipmentSlot.FEET;
import static org.bukkit.inventory.ItemFlag.HIDE_DYE;

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
                final UUID uuid = UUID.fromString("4bfa07ac-502f-4903-ba7d-f01708640294");
                final String name = "generic.movement_speed";
                final double speed = 0.1;
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED,
                                          new AttributeModifier(uuid, name, speed, ADD_NUMBER, FEET));
                if (meta instanceof LeatherArmorMeta meta2) {
                    meta2.setColor(fromRGB(0xffffff));
                }
                text(meta, tooltip);
                meta.addItemFlags(HIDE_DYE);
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
