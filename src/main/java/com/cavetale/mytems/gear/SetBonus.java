package com.cavetale.mytems.gear;

import java.util.List;
import javax.annotation.Nullable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public interface SetBonus {
    int getRequiredItemCount();

    String getName();

    String getDescription();

    @Nullable
    default List<EntityAttribute> getEntityAttributes(LivingEntity living) {
        return null;
    }

    // TODO: Make an EventCallback interface or similar, and have Mytem and SetBonus implement them!
    default void onPlayerItemConsume(PlayerItemConsumeEvent event, Player player, ItemStack item) { }
}
