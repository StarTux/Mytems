package com.cavetale.mytems.item.halloween;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.event.combat.DamageCalculationEvent;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemAttributeModifiers;
import io.papermc.paper.world.MoonPhase;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.MytemsPlugin.namespacedKey;
import static com.cavetale.mytems.util.Items.tooltip;
import static com.cavetale.mytems.util.Text.wrapLore2;
import static io.papermc.paper.datacomponent.item.ItemEnchantments.itemEnchantments;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
@RequiredArgsConstructor
public final class CrescentMoon implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private final String lore = "This scythe channels the power of the moon.";

    @Override
    public void enable() {
        displayName = text("Crescent Moon", GRAY);
        prototype = new ItemStack(key.material);
        prototype.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false);
        prototype.setData(DataComponentTypes.ENCHANTMENTS, itemEnchantments()
                          .add(Enchantment.SHARPNESS, 5)
                          .add(Enchantment.BANE_OF_ARTHROPODS, 3)
                          .add(Enchantment.KNOCKBACK, 2)
                          .add(Enchantment.LOOTING, 3));
        final ItemAttributeModifiers.Builder attributes = ItemAttributeModifiers.itemAttributes();
        for (ItemAttributeModifiers.Entry entry : Material.NETHERITE_SWORD.getDefaultData(DataComponentTypes.ATTRIBUTE_MODIFIERS).modifiers()) {
            attributes.addModifier(entry.attribute(), entry.modifier(), entry.getGroup(), entry.display());
        }
        attributes.addModifier(Attribute.SWEEPING_DAMAGE_RATIO, new AttributeModifier(namespacedKey("crescent_moon_sweep"), 1.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND));
        attributes.addModifier(Attribute.ENTITY_INTERACTION_RANGE, new AttributeModifier(namespacedKey("crescent_moon_range"), 1.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND));
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
        final Location location = player.getEyeLocation();
        location.add(location.getDirection());
        location.getWorld().spawnParticle(Particle.DUST, location, 12, 0.25, 0.25, 0.25, 0.0, new Particle.DustOptions(Color.WHITE, 0.5f));
        location.getWorld().spawnParticle(Particle.DUST, location, 12, 0.25, 0.25, 0.25, 0.0, new Particle.DustOptions(Color.GRAY, 0.5f));
    }

    @Override
    public void onAttackingDamageCalculation(DamageCalculationEvent event, ItemStack item, EquipmentSlot slot) {
        if (event.getAttacker().getWorld().isDayTime()) return;
        final MoonPhase moon = event.getAttacker().getWorld().getMoonPhase();
        if (moon == MoonPhase.NEW_MOON) return;
        final double bonus = switch (moon) {
        case FULL_MOON -> 1.0;
        case WAXING_GIBBOUS, WANING_GIBBOUS -> 0.75;
        case FIRST_QUARTER, LAST_QUARTER -> 0.5;
        case WANING_CRESCENT, WAXING_CRESCENT -> 0.25;
        case NEW_MOON -> 0.0;
        };
        if (bonus == 0.0) return;
        event.getCalculation().getOrCreateBaseDamageModifier().addFlatBonus(bonus * 4.0, "crescent_moon");
        if (!event.attackerIsPlayer() || event.getCalculation().getDamageCause() != DamageCause.ENTITY_ATTACK) return;
        event.addPostDamageAction(false, () -> event.getAttacker().getWorld().playSound(event.getAttackerPlayer(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.MASTER, 1f, 1f + (float) bonus));
    }
}
