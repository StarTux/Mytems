package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.session.Session;
import com.cavetale.worldmarker.ItemMarker;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@RequiredArgsConstructor
public final class DrAculaStaff implements Mytem {
    public static final Mytems KEY = Mytems.DR_ACULA_STAFF;
    private final MytemsPlugin plugin;
    final int durationSeconds = 30;
    final int cooldownSeconds = 60;
    ItemStack prototype;
    @Getter private BaseComponent[] displayName;

    @Override
    public String getId() {
        return KEY.id;
    }

    @Override
    public void enable() {
        ComponentBuilder cb = new ComponentBuilder();
        String name = "Dr. Acula's Staff";
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

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (event.hasBlock()) {
            Block block = event.getClickedBlock();
            if (block.getType().isInteractable()) return;
        }
        event.setCancelled(true);
        use(player, item);
    }

    public void use(Player player, ItemStack item) {
        Session session = plugin.getSessions().of(player);
        long cooldown = session.getCooldownInTicks(KEY.id);
        if (cooldown > 0) {
            long seconds = (cooldown - 1L) / 20L + 1;
            player.sendActionBar(ChatColor.DARK_RED + "Cooldown " + seconds + "s");
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.5f, 2.0f);
            return;
        }
        PotionEffect effect = player.getPotionEffect(PotionEffectType.INVISIBILITY);
        if (effect != null && effect.getDuration() >= durationSeconds * 20) {
            player.sendActionBar(ChatColor.DARK_RED + "Already invisible!");
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, SoundCategory.MASTER, 0.5f, 2.0f);
            return;
        }
        effect = new PotionEffect(PotionEffectType.INVISIBILITY, durationSeconds * 20, 0, false, false, true);
        player.addPotionEffect(effect, false);
        session.setCooldown(KEY.id, cooldownSeconds * 20);
        Location base = player.getLocation().add(0, 1, 0);
        for (int i = 0; i < 16; i += 1) {
            Location loc = base.clone().add((Math.random() - Math.random()) * 0.5,
                                            Math.random() - Math.random(),
                                            (Math.random() - Math.random()) * 0.5);
            Bat bat = loc.getWorld().spawn(loc, Bat.class, b -> {
                    b.setPersistent(false);
                    b.setSilent(true);
                    b.setRemoveWhenFarAway(true);
                    b.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(2048.0);
                    b.setHealth(2048.0);
                });
            Bukkit.getScheduler().runTaskLater(plugin, bat::remove, 40L + (long) i);
        }
        base.getWorld().playSound(base, Sound.ENTITY_BAT_LOOP, SoundCategory.MASTER, 0.5f, 2.0f);
        base.getWorld().spawnParticle(Particle.SMOKE_LARGE, base, 16, 0.5, 0.7, 0.5, 0.05);
    }

    public ItemStack create() {
        ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayNameComponent(displayName);
        List<String> lore = Arrays
            .asList(ChatColor.GRAY + "This staff was found",
                    ChatColor.GRAY + "among the mysterious",
                    ChatColor.GRAY + "doctor's belongings in",
                    ChatColor.GRAY + "the inn he stayed at,",
                    ChatColor.GRAY + "long after he had fled",
                    ChatColor.GRAY + "town.",
                    "",
                    ChatColor.GRAY + "He was never seen again,",
                    ChatColor.GRAY + "but his legend never",
                    ChatColor.GRAY + "ceased.",
                    "",
                    ChatColor.RED + "Use " + ChatColor.GRAY + "Right click to disappear",
                    ChatColor.RED + "Duration " + ChatColor.GRAY + durationSeconds + "s",
                    ChatColor.RED + "Cooldown " + ChatColor.GRAY + cooldownSeconds + "s");
        meta.setLore(lore);
        Repairable repairable = (Repairable) meta;
        repairable.setRepairCost(9999);
        meta.addEnchant(Enchantment.DURABILITY, 4, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE,
                                  new AttributeModifier(UUID.randomUUID(), KEY.id, 12.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED,
                                  new AttributeModifier(UUID.randomUUID(), KEY.id, 1.6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        item.setItemMeta(meta);
        ItemMarker.setId(item, KEY.id);
        return item;
    }
}
