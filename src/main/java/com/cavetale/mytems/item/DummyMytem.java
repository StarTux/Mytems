package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.util.CamelCase.toCamelCase;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.text;

@Getter
@RequiredArgsConstructor
public final class DummyMytem implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        displayName = text(toCamelCase(" ", key));
        prototype = new ItemStack(key.material);
        tooltip(prototype, List.of(displayName));
        key.markItemStack(prototype);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setUseItemInHand(Event.Result.DENY);
    }
}
