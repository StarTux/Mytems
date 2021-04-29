package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Text;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@RequiredArgsConstructor @Getter
public final class Ingredient implements Mytem {
    private static TextColor color = TextColor.color(0xafa259);
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        displayName = Component.text()
            .append(key.component)
            .append(Component.text(Text.toCamelCase(key, " "), color))
            .decoration(TextDecoration.ITALIC, false)
            .build();
        prototype = new ItemStack(key.material);
        ItemMeta meta = prototype.getItemMeta();
        meta.displayName(displayName);
        meta.addItemFlags(ItemFlag.values());
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
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
    public void onConsume(PlayerItemConsumeEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }
}
