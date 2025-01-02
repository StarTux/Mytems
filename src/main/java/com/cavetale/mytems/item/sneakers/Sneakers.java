package com.cavetale.mytems.item.sneakers;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Attr;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.core.util.CamelCase.toCamelCase;
import static com.cavetale.mytems.util.Items.tooltip;
import static com.cavetale.mytems.util.Text.roman;
import static java.util.Objects.requireNonNull;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static org.bukkit.Color.fromRGB;

@Getter
public final class Sneakers implements Mytem {
    private final Mytems key;
    private final SneakersType sneakersType;
    private ItemStack prototype;
    private Component displayName;

    public Sneakers(final Mytems mytems) {
        this.key = mytems;
        this.sneakersType = requireNonNull(SneakersType.of(key));
    }

    @Override
    public void enable() {
        final TextColor color = TextColor.color(sneakersType.getHexColor());
        displayName = text(toCamelCase(" ", key), color);
        List<Component> tooltip = List.of(displayName,
                                          text("Tier " + roman(sneakersType.getTier()), GRAY),
                                          text(tiny("Previously worn by"), color),
                                          text(tiny("Sanic the Hedgehag"), color));
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                key.markItemMeta(meta);
                Attr.add(meta, Attribute.MOVEMENT_SPEED, "sneakers_speed", sneakersType.getSpeedBonus(), Operation.ADD_SCALAR, EquipmentSlotGroup.FEET);
                if (sneakersType.getJumpBonus() > 0.0) {
                    Attr.add(meta, Attribute.JUMP_STRENGTH, "sneakers_jump", sneakersType.getJumpBonus(), Operation.ADD_SCALAR, EquipmentSlotGroup.FEET);
                }
                if (sneakersType.getStepBonus() > 0.0) {
                    Attr.add(meta, Attribute.STEP_HEIGHT, "sneakers_step", sneakersType.getStepBonus(), Operation.ADD_SCALAR, EquipmentSlotGroup.FEET);
                }
                if (meta instanceof LeatherArmorMeta meta2) {
                    meta2.setColor(fromRGB(sneakersType.getHexColor()));
                }
                tooltip(meta, tooltip);
                meta.addItemFlags(ItemFlag.HIDE_DYE);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }
}
