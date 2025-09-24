package com.cavetale.mytems.item.halloween;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.session.Session;
import io.papermc.paper.datacomponent.DataComponentTypes;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Items.tooltip;
import static com.cavetale.mytems.util.Text.wrapLore2;
import static io.papermc.paper.datacomponent.item.ItemEnchantments.itemEnchantments;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor @Getter
public final class EtherealBlade implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private final String lore = "This blade can pierce through the thin veil which separates the world of the living from the realm of the dead.";

    @Override
    public void enable() {
        displayName = text("Ethereal Blade", LIGHT_PURPLE);
        prototype = new ItemStack(key.material);
        prototype.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false);
        prototype.setData(DataComponentTypes.ENCHANTMENTS, itemEnchantments()
                          .add(Enchantment.SHARPNESS, 6)
                          .add(Enchantment.FIRE_ASPECT, 2)
                          .add(Enchantment.KNOCKBACK, 2)
                          .add(Enchantment.LOOTING, 3));
        final List<Component> tooltip = new ArrayList<>();
        tooltip.add(displayName);
        tooltip.addAll(wrapLore2(lore, txt -> text(tiny(txt), GOLD)));
        tooltip.add(empty());
        tooltip.add(textOfChildren(Mytems.MOUSE_RIGHT, text(" Phase out", GRAY)));
        tooltip(prototype, tooltip);
        key.markItemStack(prototype);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        final Session session = Session.of(player);
        if (session.isOnCooldown(key)) return;
        final int duration = 20 * 5;
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, duration, 0, true, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, duration, 0, true, false, true));
        session.cooldown(key).duration(Duration.ofSeconds(30));
        player.getWorld().spawnParticle(Particle.REVERSE_PORTAL, player.getEyeLocation(), 32, 0.125, 0.125, 0.125, 10.0);
        player.playSound(player, Sound.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.MASTER, 1f, 1.5f);
    }

    @Override
    public void onDamageEntity(EntityDamageByEntityEvent event, Player player, ItemStack item) {
        if (event.isCancelled()) return;
        if (event.getCause() != DamageCause.ENTITY_ATTACK) return;
        final Location location = player.getEyeLocation();
        location.add(location.getDirection());
        location.getWorld().spawnParticle(Particle.REVERSE_PORTAL, location, 32, 0.05, 0.05, 0.05, 5.0);
    }
}
