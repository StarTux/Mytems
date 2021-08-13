package com.cavetale.mytems.item.acula;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Text;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.util.Vector;

public final class Stompers extends AculaItem {
    double damageFactor = 2.0;
    String damageFactorStr = "2";
    double radius = 3.0;
    String radiusStr = "3";
    String description = "\n\n"
        + ChatColor.RED + "When first discovered, my clumsy assistant managed to drop these shoes to the ground."
        + "\n\n"
        + ChatColor.RED + "The resulting damage sustained by my prizeless collection of glassware has yet to be evaluated."
        + " May their new owner have more luck."
        + " " + ChatColor.DARK_GRAY + ChatColor.UNDERLINE + "Unknown"
        + "\n\n"
        + ChatColor.RED + "USE " + ChatColor.GRAY + "Taking fall damage"
        + "\n"
        + ChatColor.GRAY + "deals " + damageFactorStr + "x base damage"
        + "\n"
        + ChatColor.GRAY + "to enemies within " + radiusStr + " blocks.";

    public Stompers(final Mytems key) {
        super(key);
    }

    @Override
    public void enable() {
        displayName = creepify("Stompers", false);
        baseLore = Text.wrapLore(description);
        prototype = create();
    }

    public ItemStack create() {
        ItemStack item = new ItemStack(Material.NETHERITE_BOOTS);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(displayName);
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, true);
        ((Repairable) meta).setRepairCost(9999);
        key.markItemMeta(meta);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void onPlayerFallDamage(EntityDamageEvent event, Player player, ItemStack item) {
        double dmg = event.getDamage();
        List<Damageable> targets = new ArrayList<>();
        Location center = player.getLocation();
        double rads = radius * radius;
        for (Damageable entity : player.getWorld().getNearbyEntitiesByType(Damageable.class, center, radius, radius, radius)) {
            if (entity.equals(player)) continue;
            if (entity instanceof Tameable) {
                if (((Tameable) entity).getOwner() instanceof Player) continue;
            }
            if (entity instanceof Player && !entity.getWorld().getPVP()) continue;
            if (center.distanceSquared(entity.getLocation()) > rads) continue;
            targets.add(entity);
        }
        int particles = (int) (radius * Math.PI * 2.0 / 3.0); // circumference / 3
        for (Damageable target : targets) {
            target.damage(dmg * damageFactor, player);
            Vector vec = target.getLocation().subtract(center).toVector().normalize();
            target.setVelocity(target.getVelocity().add(vec));
        }
        boolean small = dmg <= 2.5;
        for (int i = 0; i < particles; i += 1) {
            double angle = (double) i / (double) particles;
            double dx = Math.cos(Math.PI * 2.0 * angle);
            double dz = Math.sin(Math.PI * 2.0 * angle);
            Location loc = center.clone().add(dx * radius, 0.5, dz * radius);
            loc.getWorld().spawnParticle((small ? Particle.EXPLOSION_NORMAL : Particle.EXPLOSION_LARGE), loc, 1, 0.0, 0.0, 0.0, 0.0);
        }
        center.getWorld().playSound(center, Sound.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, (small ? 0.25f : 0.5f), 0.5f);
    }
}
