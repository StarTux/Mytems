package com.cavetale.mytems.item.golf;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.tooltip;

@RequiredArgsConstructor @Getter
public final class GolfClub implements Mytem {
    private final Mytems key;
    private final GolfClubQuality quality;
    private Component displayName;
    private ItemStack prototype;

    public GolfClub(final Mytems key) {
        this.key = key;
        this.quality = GolfClubQuality.of(key);
    }

    @Override
    public void enable() {
        displayName = quality.getDisplayComponent();
        prototype = new ItemStack(key.material);
        tooltip(prototype, List.of(displayName));
        key.markItemStack(prototype);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setUseItemInHand(Event.Result.DENY);
        if (!event.hasBlock() || event.getClickedBlock().getType() != Material.DRAGON_EGG) return;
    }
}
