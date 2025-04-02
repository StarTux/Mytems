package com.cavetale.mytems.item.food;

import com.cavetale.core.font.Unicode;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import io.papermc.paper.datacomponent.DataComponentTypes;
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
import static io.papermc.paper.datacomponent.item.Consumable.consumable;
import static io.papermc.paper.datacomponent.item.FoodProperties.food;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor @Getter
public final class CreeperBooger implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        displayName = text("Creeper Booger", DARK_GREEN);
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                tooltip(meta, List.of(displayName,
                                      text(Unicode.tiny("do not eat!"), GRAY)));
                key.markItemMeta(meta);
            });
        prototype.setData(DataComponentTypes.CONSUMABLE, consumable().consumeSeconds(2));
        prototype.setData(DataComponentTypes.FOOD, food().canAlwaysEat(true).nutrition(10).saturation(10));
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onConsume(PlayerItemConsumeEvent event, Player player, ItemStack item) {
        final int duration = 20 * 10;
        player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, duration, 0, true, true, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_DAMAGE, 1, 1, true, true, true));
    }
}
