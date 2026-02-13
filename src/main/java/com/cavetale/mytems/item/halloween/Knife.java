package com.cavetale.mytems.item.halloween;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Items.tooltip;
import static com.cavetale.mytems.util.Text.wrapLore2;
import static io.papermc.paper.datacomponent.item.ItemEnchantments.itemEnchantments;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
@RequiredArgsConstructor
public final class Knife implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private final String lore = "Unearthed from the dumpster behind an old movie set, yet it is still quite sharp.";

    @Override
    public void enable() {
        displayName = text("Kitchen Knife", DARK_RED);
        prototype = new ItemStack(key.material);
        prototype.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false);
        prototype.setData(DataComponentTypes.ENCHANTMENTS, itemEnchantments()
                          .add(Enchantment.SHARPNESS, 4)
                          .add(Enchantment.SMITE, 5)
                          .add(Enchantment.LOOTING, 3));
        final ItemAttributeModifiers.Builder attributes = ItemAttributeModifiers.itemAttributes();
        for (ItemAttributeModifiers.Entry entry : Material.NETHERITE_SWORD.getDefaultData(DataComponentTypes.ATTRIBUTE_MODIFIERS).modifiers()) {
            if (entry.attribute() == Attribute.ATTACK_SPEED) {
                attributes.addModifier(entry.attribute(),
                                       new AttributeModifier(entry.modifier().getKey(), -1, AttributeModifier.Operation.ADD_NUMBER, entry.getGroup()),
                                       entry.getGroup(), entry.display());
            } else {
                attributes.addModifier(entry.attribute(), entry.modifier(), entry.getGroup(), entry.display());
            }
        }
        prototype.setData(DataComponentTypes.ATTRIBUTE_MODIFIERS, attributes);
        final List<Component> tooltip = new ArrayList<>();
        tooltip.add(displayName);
        tooltip.addAll(wrapLore2(lore, txt -> text(tiny(txt), GOLD)));
        tooltip(prototype, tooltip);
        key.markItemStack(prototype);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onDamageEntity(EntityDamageByEntityEvent event, Player player, ItemStack item) {
        if (event.isCancelled()) return;
        if (event.getCause() != DamageCause.ENTITY_ATTACK) return;
        player.getWorld().playSound(player, Sound.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.MASTER, 1f, 2f);
    }
}
