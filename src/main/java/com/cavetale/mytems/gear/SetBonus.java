package com.cavetale.mytems.gear;

import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public interface SetBonus {
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

    // TODO: Make an EventCallback interface or similar, and have Mytem and SetBonus implement them!
    default void onPlayerItemConsume(PlayerItemConsumeEvent event, Player player, ItemStack item) { }

    default void onPlayerDamageByEntity(EntityDamageByEntityEvent event, Player player, Entity damager) { }

    default void onPlayerPotionEffect(EntityPotionEffectEvent event, Player player) { }

    default void tick(LivingEntity living) { }
}
