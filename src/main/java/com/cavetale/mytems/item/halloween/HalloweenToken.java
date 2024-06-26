package com.cavetale.mytems.item.halloween;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.tooltip;

@RequiredArgsConstructor @Getter
public final class HalloweenToken implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private static final TextColor COLOR = TextColor.color(0xFF6000);

    @Override
    public void enable() {
        this.displayName = Component.text("Halloween Token", COLOR);
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                tooltip(meta, List.of(displayName,
                                      Component.text("Play Halloween", NamedTextColor.GRAY),
                                      Component.text("games to earn", NamedTextColor.GRAY),
                                      Component.text("more of these!", NamedTextColor.GRAY)));
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
