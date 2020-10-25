package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.worldmarker.ItemMarker;
import java.awt.Color;
import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

@RequiredArgsConstructor
public final class GhastBow implements Mytem {
    public static final Mytems KEY = Mytems.GHAST_BOW;
    private final MytemsPlugin plugin;
    @Getter BaseComponent[] displayName;
    private ItemStack prototype;

    @Override
    public String getId() {
        return KEY.id;
    }

    @Override
    public void enable() {
        String name = "Ghast Bow";
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
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayNameComponent(displayName);
        String[] lore = {
            "",
            ChatColor.GRAY + "Legend has it the",
            ChatColor.GRAY + "bowstring of this unique",
            ChatColor.GRAY + "item was soaked in ghast",
            ChatColor.GRAY + "tears.",
            "",
            ChatColor.RED + "USE " + ChatColor.GRAY + "Shooting a regular",
            ChatColor.GRAY + "arrow will launch a",
            ChatColor.GRAY + "fireball instead.",
            ChatColor.RED + "COST " + ChatColor.GRAY + "1 XP Level",
        };
        meta.setLore(Arrays.asList(lore));
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        ((Repairable) meta).setRepairCost(9999);
        item.setItemMeta(meta);
        ItemMarker.setId(item, KEY.id);
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
        event.setConsumeArrow(false);
        event.setProjectile(projectile);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GHAST_SHOOT, SoundCategory.PLAYERS, 0.5f, 1.25f);
        player.getWorld().spawnParticle(Particle.FLAME, player.getEyeLocation(), 8, 0.25, 0.25, 0.25, 0.0);
        player.setLevel(player.getLevel() - 1);
    }
}
