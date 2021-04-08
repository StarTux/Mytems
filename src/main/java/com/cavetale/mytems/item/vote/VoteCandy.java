package com.cavetale.mytems.item.vote;

import com.cavetale.mytems.Mytems;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class VoteCandy extends VoteItem {
    public static final int TIME = 3 * 20 * 60;
    private final PotionEffect[] effects = {
        PotionEffectType.ABSORPTION.createEffect(TIME, 1),
        PotionEffectType.CONDUIT_POWER.createEffect(TIME, 0),
        PotionEffectType.DAMAGE_RESISTANCE.createEffect(TIME, 0),
        PotionEffectType.DOLPHINS_GRACE.createEffect(TIME, 0),
        PotionEffectType.FAST_DIGGING.createEffect(TIME, 0),
        PotionEffectType.FIRE_RESISTANCE.createEffect(TIME, 0),
        PotionEffectType.GLOWING.createEffect(TIME, 0),
        PotionEffectType.HEALTH_BOOST.createEffect(TIME, 2),
        PotionEffectType.HERO_OF_THE_VILLAGE.createEffect(TIME, 0),
        PotionEffectType.INCREASE_DAMAGE.createEffect(TIME, 0),
        PotionEffectType.INVISIBILITY.createEffect(TIME, 0),
        PotionEffectType.JUMP.createEffect(TIME, 0),
        PotionEffectType.JUMP.createEffect(TIME, 1),
        PotionEffectType.LUCK.createEffect(TIME, 0),
        PotionEffectType.REGENERATION.createEffect(TIME, 0),
        PotionEffectType.SATURATION.createEffect(TIME, 0),
        PotionEffectType.SLOW_FALLING.createEffect(TIME, 0),
        PotionEffectType.SPEED.createEffect(TIME, 0),
        PotionEffectType.SPEED.createEffect(TIME, 1),
        PotionEffectType.WATER_BREATHING.createEffect(TIME, 0)
    };

    public VoteCandy(final Mytems mytems) {
        super(mytems);
    }

    @Override
    public void enable() {
        displayName = Component.text("Voting Candy").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false);
        prototype = new ItemStack(key.material);
        ItemMeta meta = prototype.getItemMeta();
        meta.displayName(displayName);
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Gives unobtainable potion").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("effects when eaten.").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.empty());
        meta.lore(lore);
        prototype.setItemMeta(meta);
        meta.addItemFlags(ItemFlag.values());
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
    }

    @Override
    public void onConsume(PlayerItemConsumeEvent event, Player player, ItemStack item) {
        int effectIndex = random.nextInt(effects.length);
        PotionEffect effect = effects[random.nextInt(effects.length)];
        player.addPotionEffect(effect);
        player.playSound(player.getEyeLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.MASTER, 0.25f, 2.0f);
    }
}
