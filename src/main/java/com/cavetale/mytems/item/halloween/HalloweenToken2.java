package com.cavetale.mytems.item.halloween;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.GRAY;

@RequiredArgsConstructor @Getter
public final class HalloweenToken2 implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private static final TextColor COLOR = TextColor.color(0xFF8000);

    @Override
    public void enable() {
        this.displayName = text("Fancy Halloween Token", COLOR);
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                tooltip(meta, List.of(displayName,
                                      text("Exchange for the", GRAY),
                                      text("most valuable", GRAY),
                                      text("Halloween goodies!", GRAY),
                                      empty(),
                                      text("There is a limited", GRAY),
                                      text("quantity of these,", GRAY),
                                      text("so choose wisely!", GRAY)));
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
