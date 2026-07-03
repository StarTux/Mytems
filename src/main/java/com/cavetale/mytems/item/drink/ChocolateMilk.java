package com.cavetale.mytems.item.drink;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import static com.cavetale.mytems.util.Items.tooltip;
import static io.papermc.paper.datacomponent.item.Consumable.consumable;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@Getter
@RequiredArgsConstructor
public final class ChocolateMilk implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        displayName = text("Chocolate Milk", color(0xcfcfff));
        prototype = new ItemStack(key.material);
        key.markItemStack(prototype);
        tooltip(
            prototype,
            List.of(
                displayName,
                textOfChildren(
                    text("Happy birthday, Y", GRAY),
                    Mytems.HEART
                )
            )
        );
        prototype.setData(
            DataComponentTypes.CONSUMABLE, consumable()
            .consumeSeconds(10)
            .animation(ItemUseAnimation.DRINK)
            .sound(Sound.ENTITY_GENERIC_DRINK.getKey())
            .hasConsumeParticles(false)
        );
        prototype.setData(DataComponentTypes.MAX_STACK_SIZE, 1);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onConsume(PlayerItemConsumeEvent event, Player player, ItemStack item) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, 0, true, true, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 60, 0, true, true, true));
        player.playSound(player, Sound.BLOCK_BEACON_ACTIVATE, SoundCategory.MASTER, 1f, 2f);
        player.playSound(player, Sound.ENTITY_PLAYER_BURP, SoundCategory.MASTER, .25f, 1f);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
