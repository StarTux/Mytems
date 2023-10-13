package com.cavetale.mytems.item.acula;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.event.combat.DamageCalculationEvent;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static org.bukkit.attribute.Attribute.*;
import static org.bukkit.attribute.AttributeModifier.Operation.*;
import static org.bukkit.block.banner.PatternType.*;
import static org.bukkit.inventory.EquipmentSlot.*;

@Getter
public final class FlameShield extends AculaItem {
    private final String rawDisplayName = "Flame Shield";
    private final String description = ""
        + "After the castle was completely burned down to the ground, this item remained intact."
        + " For years it was kept in a secret vault, until one day, when it vanished.";
    private final List<Component> usage = List.of(textOfChildren(key, text(" Attacking enemies are set on fire", GRAY)));

    public FlameShield(final Mytems key) {
        super(key);
    }

    @Override @SuppressWarnings("LineLength")
    protected ItemStack getRawItemStack() {
        ItemStack itemStack = new ItemStack(Material.SHIELD);
        itemStack.editMeta(meta -> {
                BlockStateMeta blockStateMeta = (BlockStateMeta) meta;
                Banner banner = (Banner) blockStateMeta.getBlockState();
                banner.setBaseColor(DyeColor.BLACK);
                banner.setPatterns(List.of(new Pattern(DyeColor.ORANGE, MOJANG),
                                           new Pattern(DyeColor.ORANGE, TRIANGLE_BOTTOM),
                                           new Pattern(DyeColor.BLACK, RHOMBUS_MIDDLE),
                                           new Pattern(DyeColor.RED, GRADIENT_UP)));
                blockStateMeta.setBlockState(banner);
                meta.addAttributeModifier(GENERIC_ARMOR,
                                          new AttributeModifier(UUID.randomUUID(), key.id, 4, ADD_NUMBER, OFF_HAND));
                meta.addAttributeModifier(GENERIC_ARMOR_TOUGHNESS,
                                          new AttributeModifier(UUID.randomUUID(), key.id, 1, ADD_NUMBER, OFF_HAND));
                meta.addAttributeModifier(GENERIC_KNOCKBACK_RESISTANCE,
                                          new AttributeModifier(UUID.randomUUID(),
                                                                key.id, 0.1, ADD_NUMBER, OFF_HAND));
                meta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS); // hides banner patterns
            });
        return itemStack;
    }

    @Override
    public void onDefendingDamageCalculation(DamageCalculationEvent event, ItemStack item, EquipmentSlot slot) {
        switch (event.getEntityDamageEvent().getCause()) {
        case ENTITY_ATTACK:
        case PROJECTILE:
            break;
        default:
            return;
        }
        if (slot != EquipmentSlot.HAND && slot != EquipmentSlot.OFF_HAND) return;
        if (event.getCalculation().getShieldFactor() > 0.99) return;
        if (!(event.getTarget() instanceof Player player)) return;
        if (!player.isBlocking()) return;
        if (slot != player.getHandRaised()) return;
        if (!(event.getEntityDamageEvent() instanceof EntityDamageByEntityEvent event2)) return;
        final Entity attacker = event2.getDamager();
        event.addPostDamageAction(true, () -> {
                attacker.setFireTicks(60);
            });
    }
}
