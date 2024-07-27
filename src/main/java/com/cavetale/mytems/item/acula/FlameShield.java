package com.cavetale.mytems.item.acula;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.event.combat.DamageCalculationEvent;
import com.cavetale.mytems.util.Attr;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

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
                banner.setPatterns(List.of(new Pattern(DyeColor.ORANGE, PatternType.MOJANG),
                                           new Pattern(DyeColor.ORANGE, PatternType.TRIANGLE_BOTTOM),
                                           new Pattern(DyeColor.BLACK, PatternType.RHOMBUS),
                                           new Pattern(DyeColor.RED, PatternType.GRADIENT_UP)));
                blockStateMeta.setBlockState(banner);
                Attr.add(meta, Attribute.GENERIC_ARMOR, "flame_shield_armor", 4, Operation.ADD_NUMBER, EquipmentSlotGroup.OFFHAND);
                Attr.add(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, "flame_shield_armor_toughness", 1, Operation.ADD_NUMBER, EquipmentSlotGroup.OFFHAND);
                Attr.add(meta, Attribute.GENERIC_KNOCKBACK_RESISTANCE, "flame_shield_knockback_resistance", 0.1, Operation.ADD_NUMBER, EquipmentSlotGroup.OFFHAND);
                meta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP); // hides banner patterns
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
        event.addPostDamageAction(() -> attacker.setFireTicks(60));
    }
}
