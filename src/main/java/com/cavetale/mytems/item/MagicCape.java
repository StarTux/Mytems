package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.MytemPersistenceFlag;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.session.Session;
import com.cavetale.mytems.util.Text;
import java.awt.Color;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@RequiredArgsConstructor
public final class MagicCape implements Mytem {
    @Getter private final Mytems key;
    public static final int XP_PER_SECOND = 3;
    public static final float FLY_SPEED = 0.025f;
    public static final int SECONDS_PER_FOOD = 5;
    public static final int FLIGHT_SECONDS = 10;
    public static final int COOLDOWN_SECONDS = 20;
    @Getter private Component displayName;
    private final String description = ""
        + ChatColor.AQUA + "Woven with the rare membrane dropped by a phantom boss."
        + "\n\n"
        + ChatColor.AQUA + "USE"
        + ChatColor.GRAY + " Float like a butterfly. Start gliding with this cape equipped and you will fly instead, for up to "
        + ChatColor.AQUA + FLIGHT_SECONDS + ChatColor.GRAY + " seconds."
        + "\n\n"
        + ChatColor.AQUA + "Duration " + ChatColor.GRAY + FLIGHT_SECONDS + " seconds"
        + "\n"
        + ChatColor.AQUA + "Cooldown " + ChatColor.GRAY + COOLDOWN_SECONDS + " seconds"
        + "\n\n"
        + ChatColor.AQUA + "Cost " + ChatColor.GRAY + XP_PER_SECOND + " exp per second"
        + "\n"
        + ChatColor.AQUA + "Cost " + ChatColor.GRAY + "1 food per " + SECONDS_PER_FOOD + " seconds";
    private ItemStack prototype;

    protected static Component rainbowify(String in) {
        Component component = Component.empty().decoration(TextDecoration.ITALIC, false);
        int len = in.length();
        for (int i = 0; i < len; i += 1) {
            float pos = (float) i / (float) len;
            int rgb = 0xFFFFFF & Color.HSBtoRGB(pos, 0.66f, 1.0f);
            component = component.append(Component.text(in.substring(i, i + 1)).color(TextColor.color(rgb)));
        }
        return component;
    }

    @Override
    public void enable() {
        displayName = rainbowify("Magic Cape");
        prototype = new ItemStack(Material.ELYTRA).ensureServerConversions();
        ItemMeta meta = prototype.getItemMeta();
        meta.displayName(displayName);
        meta.lore(Text.wrapLore(description));
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onToggleGlide(EntityToggleGlideEvent event, Player player, ItemStack item) {
        if (event.isCancelled()) return;
        if (!event.isGliding()) return;
        if (player.getLevel() == 0 && player.getExp() <= 0f) {
            player.sendActionBar(ChatColor.DARK_RED + "Need exp!");
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.5f, 2.0f);
            return;
        }
        if (player.getFoodLevel() < 4) {
            player.sendActionBar(ChatColor.DARK_RED + "Need food!");
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.5f, 2.0f);
            return;
        }
        Session session = MytemsPlugin.getInstance().getSessions().of(player);
        long cooldown = session.getCooldownInTicks(key.id);
        if (cooldown > 0) {
            long seconds = (cooldown - 1L) / 20L + 1;
            player.sendActionBar(ChatColor.DARK_RED + "Cooldown " + seconds + "s");
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.5f, 2.0f);
            return;
        }
        session.setCooldown(key.id, COOLDOWN_SECONDS * 20);
        event.setCancelled(true);
        session.getFlying().setFlying(FLY_SPEED, FLIGHT_SECONDS * 20, () -> onFlyTick(player), () -> onEnd(player));
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PHANTOM_FLAP, SoundCategory.PLAYERS, 2.0f, 2.0f);
    }

    void onFlyTick(Player player) {
        Session session = MytemsPlugin.getInstance().getSessions().of(player);
        int flyTime = session.getFlying().getFlyTime();
        if (flyTime % 20 == 0) {
            // Take exp
            int expToLevel = player.getExpToLevel();
            float exp = player.getExp() * (float) expToLevel;
            exp -= (float) XP_PER_SECOND;
            if (exp < 0) {
                int level = player.getLevel() - 1;
                if (level >= 0) {
                    player.setLevel(level);
                    player.setExp(1f + exp / (float) player.getExpToLevel());
                } else {
                    player.setLevel(0);
                    player.setExp(0f);
                }
            } else {
                player.setExp(exp / (float) expToLevel);
            }
            // Take food
            int flySeconds = flyTime / 20;
            if (flySeconds % SECONDS_PER_FOOD == 0) {
                if (player.getSaturation() > 0f) {
                    player.setSaturation(Math.max(0f, player.getSaturation() - 1f));
                } else if (player.getFoodLevel() > 0) {
                    player.setFoodLevel(player.getFoodLevel() - 1);
                }
            }
        }
    }

    public void onEnd(Player player) {
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PHANTOM_FLAP, SoundCategory.PLAYERS, 2.0f, 1.0f);
        return;
    }

    @Override
    public Set<MytemPersistenceFlag> getMytemPersistenceFlags() {
        return MytemPersistenceFlag.VANILLA;
    }
}
