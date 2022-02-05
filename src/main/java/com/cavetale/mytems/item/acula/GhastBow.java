package com.cavetale.mytems.item.acula;

import com.cavetale.mytems.Mytems;
import lombok.Getter;
import org.bukkit.ChatColor;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Repairable;

@Getter
public final class GhastBow extends AculaItem {
    private final String rawDisplayName = "Ghast Bow";
    private final String description = "\n\n"
        + ChatColor.RED + "Legend has it the bowstring of this unique item was soaked in ghast tears."
        + " They say it's the only weapon which may lower a vampire's guard..."
        + "\n\n"
        + ChatColor.RED + "USE " + ChatColor.GRAY + "Shooting a regular arrow will launch a fireball instead"
        + "\n"
        + ChatColor.RED + "Cost " + ChatColor.GRAY + "1 XP Level";

    public GhastBow(final Mytems key) {
        super(key);
    }

    @Override
    protected ItemStack getRawItemStack() {
        ItemStack item = new ItemStack(Material.BOW);
        item.editMeta(meta -> {
                meta.setUnbreakable(true);
                meta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
                meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
                ((Repairable) meta).setRepairCost(9999);
            });
        return item;
    }

    @Override
    public void onPlayerShootBow(EntityShootBowEvent event, Player player, ItemStack item) {
        if (!(event.getProjectile() instanceof Arrow)) return;
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
