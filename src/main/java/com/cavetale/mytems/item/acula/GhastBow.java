package com.cavetale.mytems.item.acula;

import com.cavetale.mytems.Mytems;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

@Getter
public final class GhastBow extends AculaItem {
    private final String rawDisplayName = "Ghast Bow";
    private final String description = ""
        + ChatColor.RED + "Legend has it the bowstring of this unique item was soaked in ghast tears."
        + " They say it's the only weapon which may lower a vampire's guard..."
        + "\n\n"
        + ChatColor.RED + "shoot " + ChatColor.GRAY + "Launch fireball for 1 exp level";

    public GhastBow(final Mytems key) {
        super(key);
    }

    @Override
    protected ItemStack getRawItemStack() {
        return new ItemStack(Material.BOW);
    }

    @Override
    public void onPlayerShootBow(EntityShootBowEvent event, Player player, ItemStack item) {
        if (event.getConsumable() != null && event.getConsumable().getType() == Material.ARROW) {
            event.setConsumeItem(false);
        }
        if (!(event.getProjectile() instanceof Arrow arrow)) return;
        if (!arrow.hasCustomEffects() && arrow.getBasePotionData().getType() == PotionType.UNCRAFTABLE) {
            // Normal arrow, not tipped. The interface TippedArrow is deprecated.
            arrow.setFireTicks(200);
        }
        if (player.getLevel() < 1) return;
        Projectile projectile;
        float force = event.getForce();
        if (force < 1.0f) {
            projectile = player.launchProjectile(SmallFireball.class);
        } else {
            projectile = player.launchProjectile(LargeFireball.class);
        }
        if (projectile == null) return;
        event.setConsumeItem(false);
        event.setProjectile(projectile);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, SoundCategory.PLAYERS, 0.5f, 1.25f);
        player.getWorld().spawnParticle(Particle.FLAME, player.getEyeLocation(), 8, 0.25, 0.25, 0.25, 0.0);
        player.setLevel(player.getLevel() - 1);
    }
}
