package com.cavetale.mytems.item.drink;

import com.cavetale.core.font.Unicode;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor @Getter
public final class FizzyBrew implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        displayName = text("Fizzy Brew" + Unicode.TRADEMARK.getString(), GOLD);
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                tooltip(meta, List.of(displayName));
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onConsume(PlayerItemConsumeEvent event, Player player, ItemStack item) {
        final int duration = 60 * 20;
        player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, duration, 0, true, true, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, duration, 0, true, true, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, duration, 0, true, true, true));
        event.setReplacement(Mytems.EMPTY_STEIN.createItemStack());
    }
}
