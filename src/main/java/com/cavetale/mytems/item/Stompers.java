package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.worldmarker.ItemMarker;
import com.winthier.generic_events.GenericEvents;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
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

@RequiredArgsConstructor
public final class Stompers implements Mytem {
    public static final Mytems KEY = Mytems.STOMPERS;
    private final MytemsPlugin plugin;
    @Getter BaseComponent[] displayName;
    ItemStack prototype;
    double damageFactor = 2.0;
    String damageFactorStr = "2";
    double radius = 3.0;
    String radiusStr = "3";

    @Override
    public String getId() {
        return KEY.id;
    }

    @Override
    public void enable() {
        String name = "Stompers";
        ComponentBuilder cb = new ComponentBuilder();
        int len = name.length();
        int iter = 255 / name.length() * 3 / 4;
        for (int i = 0; i < name.length(); i += 1) {
            cb.append(name.substring(i, i + 1)).color(ChatColor.of(new Color(255 - iter * i, 0, 0)));
        }
        displayName = cb.create();
        prototype = create();
    }

    @Override
    public ItemStack getItem() {
        return prototype.clone();
    }

    public ItemStack create() {
        ItemStack item = new ItemStack(Material.NETHERITE_BOOTS);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayNameComponent(displayName);
        String[] lore = {
            "",
            ChatColor.GRAY + "When first discovered,",
            ChatColor.GRAY + "my clumsy assistant",
            ChatColor.GRAY + "managed to drop these",
            ChatColor.GRAY + "shoes to the ground.",
            "",
            ChatColor.GRAY + "The resulting damage",
            ChatColor.GRAY + "sustained by my prizeless",
            ChatColor.GRAY + "collection of glassware",
            ChatColor.GRAY + "has yet to be evaluated.",
            "",
            ChatColor.GRAY + "May their new owner have",
            ChatColor.GRAY + "more luck.",
            "" + ChatColor.DARK_GRAY + ChatColor.ITALIC + "                 Unknown",
            "",
            ChatColor.RED + "USE " + ChatColor.GRAY + "Taking fall damage",
            ChatColor.GRAY + "deals " + damageFactorStr + "x base damage",
            ChatColor.GRAY + "to enemies within " + radiusStr + " blocks."
        };
        meta.setLore(Arrays.asList(lore));
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        meta.addEnchant(Enchantment.PROTECTION_FALL, 4, true);
        ((Repairable) meta).setRepairCost(9999);
        item.setItemMeta(meta);
        ItemMarker.setId(item, KEY.id);
        return item;
    }

    @Override
    public void onPlayerFallDamage(EntityDamageEvent event, Player player, ItemStack item) {
        double dmg = event.getDamage(EntityDamageEvent.DamageModifier.BASE);
        List<Damageable> targets = new ArrayList<>();
        Location center = player.getLocation();
        double rads = radius * radius;
        for (Damageable entity : player.getWorld().getNearbyEntitiesByType(Damageable.class, center, radius, radius, radius)) {
            if (entity.equals(player)) continue;
            if (entity instanceof Tameable) {
                if (((Tameable) entity).getOwner() instanceof Player) continue;
            }
            if (entity instanceof Player && !entity.getWorld().getPVP()) continue;
            if (!GenericEvents.playerCanDamageEntity(player, entity)) continue;
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
