package com.cavetale.mytems.item.potion;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import static java.awt.Color.HSBtoRGB;
import static org.bukkit.Color.fromRGB;

@RequiredArgsConstructor @Getter
public final class PotionFlask implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        this.displayName = Component.text(Text.toCamelCase(key, " "));
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                Items.text(meta, List.of(displayName));
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        ItemStack result = prototype.clone();
        result.editMeta(m -> {
                if (m instanceof PotionMeta meta) {
                    meta.setColor(fromRGB(0xFFFFFF & HSBtoRGB(ThreadLocalRandom.current().nextFloat(), 1.0f, 1.0f)));
                }
            });
        return result;
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    @Override
    public void onConsume(PlayerItemConsumeEvent event, Player player, ItemStack item) {
        event.setReplacement(Mytems.EMPTY_FLASK.createItemStack());
    }
}
