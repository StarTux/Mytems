package com.cavetale.mytems.item.acula;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.session.Session;
import com.cavetale.worldmarker.entity.EntityMarker;
import java.util.UUID;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import static org.bukkit.attribute.AttributeModifier.Operation.*;
import static org.bukkit.inventory.EquipmentSlot.*;

@Getter
public final class DrAculaStaff extends AculaItem {
    private final int durationSeconds = 30;
    private final int cooldownSeconds = 60;
    private final String rawDisplayName = "Dr. Acula's Staff";
    private final String description = ""
        + ChatColor.RED + "This staff was found among the mysterious doctor's belongings in the inn he stayed at, long after he had fled town.";
    private final String usage = ""
        + ChatColor.RED + "Right click"
        + ChatColor.DARK_GRAY + "\u2013"
        + ChatColor.GRAY + "Disappear for " + durationSeconds + "s";

    public DrAculaStaff(final Mytems key) {
        super(key);
    }

    @Override
    protected ItemStack getRawItemStack() {
        ItemStack item = new ItemStack(Material.NETHERITE_SWORD);
        item.editMeta(meta -> {
                AttributeModifier attr;
                attr = new AttributeModifier(UUID.randomUUID(), key.id, 12.0, ADD_NUMBER, HAND);
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attr);
                attr = new AttributeModifier(UUID.randomUUID(), key.id, 1.6, ADD_NUMBER, HAND);
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attr);
            });
        return item;
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (event.hasBlock()) {
            Block block = event.getClickedBlock();
            if (block.getType().isInteractable()) return;
        }
        event.setUseItemInHand(Event.Result.DENY);
        use(player, item);
    }

    protected void use(Player player, ItemStack item) {
        Session session = MytemsPlugin.getInstance().getSessions().of(player);
        long cooldown = session.getCooldownInTicks(key.id);
        if (cooldown > 0) {
            long seconds = (cooldown - 1L) / 20L + 1;
            player.sendActionBar(Component.text("Cooldown " + seconds + "s", NamedTextColor.DARK_RED));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.5f, 2.0f);
            return;
        }
        PotionEffect effect = player.getPotionEffect(PotionEffectType.INVISIBILITY);
        if (effect != null && effect.getDuration() >= durationSeconds * 20) {
            player.sendActionBar(Component.text("Already invisible!", NamedTextColor.DARK_RED));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, SoundCategory.MASTER, 0.5f, 2.0f);
            return;
        }
        effect = new PotionEffect(PotionEffectType.INVISIBILITY, durationSeconds * 20, 0, false, false, true);
        player.addPotionEffect(effect);
        session.setCooldown(key.id, cooldownSeconds * 20);
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
            if (bat == null) break;
            EntityMarker.setId(bat, key.id);
            Bukkit.getScheduler().runTaskLater(MytemsPlugin.getInstance(), bat::remove, 40L + (long) i);
        }
        base.getWorld().playSound(base, Sound.ENTITY_BAT_LOOP, SoundCategory.MASTER, 0.5f, 2.0f);
        base.getWorld().spawnParticle(Particle.SMOKE_LARGE, base, 16, 0.5, 0.7, 0.5, 0.05);
    }
}
