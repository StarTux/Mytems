package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Text;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.tooltip;

@RequiredArgsConstructor @Getter
public final class ArmorPart implements Mytem {
    protected static final TextColor COPPER = TextColor.color(0xe77c56);
    protected static final TextColor RUST = TextColor.color(0x6b3017);
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private List<Component> text;

    @Override
    public void enable() {
        this.displayName = Component.text(Text.toCamelCase(key, " "), COPPER);
        this.text = List.of(displayName,
                            Component.text("Armor Scrap acquired", RUST),
                            Component.text("by completing Raids on", RUST),
                            Component.text("/raid.", RUST));
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                tooltip(meta, text);
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

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setUseItemInHand(Event.Result.DENY);
    }
}
