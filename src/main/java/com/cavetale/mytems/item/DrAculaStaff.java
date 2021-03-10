package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.session.Session;
import com.cavetale.mytems.util.Text;
import java.util.UUID;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
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
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class DrAculaStaff extends AculaItem {
    public static final Mytems KEY = Mytems.DR_ACULA_STAFF;
    final int durationSeconds = 30;
    final int cooldownSeconds = 60;
    private String description = ""
        + ChatColor.RED + "This staff was found among the mysterious doctor's belongings in the inn he stayed at, long after he had fled town."
        + "\n\n"
        + ChatColor.RED + "He was never seen again, but his legend never ceased."
        + "\n\n"
        + ChatColor.RED + "USE " + ChatColor.GRAY + "Right click to disappear"
        + "\n"
        + ChatColor.RED + "Duration " + ChatColor.GRAY + durationSeconds + "s"
        + "\n"
        + ChatColor.RED + "Cooldown " + ChatColor.GRAY + cooldownSeconds + "s";

    public DrAculaStaff(final MytemsPlugin plugin) {
        super(plugin);
    }

    @Override
    public Mytems getKey() {
        return KEY;
    }

    @Override
    public void enable() {
        displayName = creepify("Dr. Acula's Staff", false);
        baseLore = Text.toBaseComponents(Text.wrapMultiline(description, Text.ITEM_LORE_WIDTH));
        prototype = create();
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
        player.addPotionEffect(effect);
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
        Repairable repairable = (Repairable) meta;
        repairable.setRepairCost(9999);
        AttributeModifier attr;
        attr = new AttributeModifier(UUID.randomUUID(), KEY.id, 12.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attr);
        attr = new AttributeModifier(UUID.randomUUID(), KEY.id, 1.6, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attr);
        KEY.markItemMeta(meta);
        item.setItemMeta(meta);
        return item;
    }
}
