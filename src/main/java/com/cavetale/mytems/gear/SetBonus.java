package com.cavetale.mytems.gear;

import com.cavetale.mytems.event.combat.DamageCalculationEvent;
import com.cavetale.mytems.util.Text;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

public interface SetBonus {
    TextColor DARK_DARK_GRAY = TextColor.color(0x333333);

    ItemSet getItemSet();

    int getRequiredItemCount();

    String getName();

    default String getName(int has) {
        return getName();
    }

    String getDescription();

    default String getDescription(int has) {
        return getDescription();
    }

    default boolean isHidden() {
        return false;
    }

    default List<EntityAttribute> getEntityAttributes(int has) {
        return getEntityAttributes();
    }

    default List<EntityAttribute> getEntityAttributes() {
        return List.of();
    }

    default List<PotionEffect> getPotionEffects(int has) {
        return getPotionEffects();
    }

    default List<PotionEffect> getPotionEffects() {
        return List.of();
    }

    default List<Component> createTooltip(int has) {
        List<Component> tooltip = new ArrayList<>();
        int req = getRequiredItemCount();
        boolean active = has >= req;
        tooltip.add(text((req > 1 ? "(" + getRequiredItemCount() + ") " : "") + getName(has), active ? GRAY : DARK_DARK_GRAY));
        String description = getDescription(has);
        if (!description.isEmpty()) {
            for (String line : Text.wrapLine(getDescription(has), Text.ITEM_LORE_WIDTH)) {
                tooltip.add(text(line, active ? DARK_GRAY : DARK_DARK_GRAY));
            }
        }
        return tooltip;
    }

    default void onPlayerItemConsume(PlayerItemConsumeEvent event, Player player, ItemStack item) { }

    default void onPlayerDamageByEntity(EntityDamageByEntityEvent event, Player player, Entity damager) { }

    default void onPlayerDamage(EntityDamageEvent event, Player player) { }

    default void onPlayerPotionEffect(EntityPotionEffectEvent event, Player player) { }

    default void onPlayerJump(PlayerJumpEvent event, Player player) { }

    default void onDefendingDamageCalculation(DamageCalculationEvent event) { }

    default void onAttackingDamageCalculation(DamageCalculationEvent event) { }

    default void tick(LivingEntity living, int has) { }
}
