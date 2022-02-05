package com.cavetale.mytems.gear;

import com.cavetale.mytems.util.Text;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
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
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

public interface SetBonus {
    TextColor DARK_DARK_GRAY = TextColor.color(0x333333);

    int getRequiredItemCount();

    String getName();

    String getDescription();

    @Nullable
    default List<EntityAttribute> getEntityAttributes() {
        return null;
    }

    @Nullable
    default List<EntityAttribute> getEntityAttributes(LivingEntity living) {
        return getEntityAttributes();
    }

    default List<Component> createTooltip(boolean active) {
        List<Component> tooltip = new ArrayList<>();
        tooltip.add(text("(" + getRequiredItemCount() + ") " + getName(), active ? GRAY : DARK_DARK_GRAY));
        for (String line : Text.wrapLine(getDescription(), Text.ITEM_LORE_WIDTH)) {
            tooltip.add(text(line, active ? DARK_GRAY : DARK_DARK_GRAY));
        }
        return tooltip;
    }

    default void onPlayerItemConsume(PlayerItemConsumeEvent event, Player player, ItemStack item) { }

    default void onPlayerDamageByEntity(EntityDamageByEntityEvent event, Player player, Entity damager) { }

    default void onPlayerDamage(EntityDamageEvent event, Player player) { }

    default void onPlayerPotionEffect(EntityPotionEffectEvent event, Player player) { }

    default void onPlayerJump(PlayerJumpEvent event, Player player) { }

    default void tick(LivingEntity living) { }
}
