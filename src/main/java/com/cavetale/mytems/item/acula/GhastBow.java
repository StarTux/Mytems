package com.cavetale.mytems.item.acula;

import com.cavetale.core.connect.NetworkServer;
import com.cavetale.mytems.Mytems;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public final class GhastBow extends AculaItem {
    private final String rawDisplayName = "Ghast Bow";
    private final String description = ""
        + "Legend has it the bowstring of this unique item was soaked in ghast tears."
        + " They say it's the only weapon which may lower a vampire's guard...";
    private final List<Component> usage = List.of(textOfChildren(Mytems.GHAST_BOW, text(" Launch fireball", GRAY)));

    public GhastBow(final Mytems key) {
        super(key);
    }

    @Override
    protected ItemStack getRawItemStack() {
        final ItemStack result = new ItemStack(Material.BOW);
        result.editMeta(meta -> {
                meta.addEnchant(Enchantment.INFINITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            });
        return result;
    }

    @Override
    public void onPlayerShootBow(EntityShootBowEvent event, Player player, ItemStack item) {
        if (event.isCancelled()) return;
        if (NetworkServer.current() == NetworkServer.FESTIVAL) {
            event.setCancelled(true);
            return;
        }
        if (!(event.getProjectile() instanceof Arrow arrow)) return;
        // TODO TEST THIS
        if (!arrow.hasCustomEffects() && arrow.getBasePotionType() == PotionType.AWKWARD) {
            // Normal arrow, not tipped. The interface TippedArrow is deprecated.
            arrow.setFireTicks(200);
        }
        Projectile projectile;
        float force = event.getForce();
        boolean fullForce = event.getForce() >= 1.0f;
        projectile = player.launchProjectile(fullForce ? LargeFireball.class : SmallFireball.class);
        if (projectile == null) return;
        event.setProjectile(projectile);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 0.5f, 1.25f);
        if (fullForce) {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_WARN, SoundCategory.PLAYERS, 0.5f, 1.125f);
        }
        player.getWorld().spawnParticle(Particle.FLAME, player.getEyeLocation(), 8, 0.25, 0.25, 0.25, 0.0);
    }
}
