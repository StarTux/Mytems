package com.cavetale.mytems.item.halloween;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor @Getter
public final class HalloweenToken2 implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private static final TextColor COLOR = TextColor.color(0xFF8000);

    @Override
    public void enable() {
        this.displayName = Component.text("Fancy Halloween Token", COLOR);
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                Items.text(meta, List.of(displayName,
                                         Component.text("Exchange for the", NamedTextColor.GRAY),
                                         Component.text("most valuable", NamedTextColor.GRAY),
                                         Component.text("Halloween goodies!", NamedTextColor.GRAY)));
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }
}
