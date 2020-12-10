package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.session.Session;
import com.cavetale.mytems.util.Text;
import com.cavetale.worldmarker.ItemMarker;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@RequiredArgsConstructor
public final class MagicCape implements Mytem {
    private final MytemsPlugin plugin;
    public static final Mytems KEY = Mytems.MAGIC_CAPE;
    public static final int XP_PER_SECOND = 4;
    public static final float FLY_SPEED = 0.025f;
    public static final String FOOD_COOLDOWN_KEY = "magic_cape:hunger";
    public static final int SECONDS_PER_FOOD = 5;
    @Getter private BaseComponent[] displayName;
    private final String description = ""
        + ChatColor.AQUA + "Woven with the rare membrane dropped by a phantom boss."
        + "\n\n"
        + ChatColor.AQUA + "USE"
        + ChatColor.GRAY + " Float like a butterfly. Start gliding with this cape equipped and you will fly instead."
        + "\n\n"
        + ChatColor.AQUA + ChatColor.BOLD + "Cost"
        + "\n"
        + " " + ChatColor.AQUA + XP_PER_SECOND + " exp lvl" + ChatColor.GRAY + " per second"
        + "\n"
        + " " + ChatColor.AQUA + "1 food" + ChatColor.GRAY + " every " + SECONDS_PER_FOOD + " seconds";
    private ItemStack prototype;

    @Override
    public Mytems getKey() {
        return KEY;
    }

    @Override
    public String getId() {
        return KEY.id;
    }

    @Override
    public void enable() {
        ComponentBuilder cb = new ComponentBuilder();
        UnicornHorn.rainbowify(cb, "Magic Cape");
        displayName = cb.create();
        prototype = new ItemStack(Material.ELYTRA).ensureServerConversions();
        ItemMeta meta = prototype.getItemMeta();
        meta.setDisplayNameComponent(displayName);
        meta.setLore(Text.wrapMultiline(description, Text.ITEM_LORE_WIDTH));
        prototype.setItemMeta(meta);
        ItemMarker.setId(prototype, KEY.id);
    }

    @Override
    public ItemStack getItem() {
        return prototype.clone();
    }

    @Override
    public void onToggleGlide(EntityToggleGlideEvent event, Player player, ItemStack item) {
        if (event.isCancelled()) return;
        if (!event.isGliding()) return;
        if (player.getLevel() < XP_PER_SECOND) return;
        event.setCancelled(true);
        player.setLevel(player.getLevel() - XP_PER_SECOND);
        Session session = plugin.getSessions().of(player);
        session.getFlying().setFlying(FLY_SPEED, 20, () -> refresh(player));
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PHANTOM_FLAP, SoundCategory.PLAYERS, 2.0f, 2.0f);
    }

    public void refresh(Player player) {
        if (!player.isFlying() || player.getLevel() < XP_PER_SECOND || Mytems.forItem(player.getInventory().getChestplate()) != KEY) {
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PHANTOM_FLAP, SoundCategory.PLAYERS, 2.0f, 1.0f);
            return;
        }
        Session session = plugin.getSessions().of(player);
        player.setLevel(player.getLevel() - XP_PER_SECOND);
        if (!session.isOnCooldown(FOOD_COOLDOWN_KEY)) {
            session.setCooldown(FOOD_COOLDOWN_KEY, 20 * SECONDS_PER_FOOD);
            player.sendMessage("Hunger!");
            if (player.getSaturation() > 0f) {
                player.setSaturation(Math.max(0f, player.getSaturation() - 1f));
            } else if (player.getFoodLevel() > 0) {
                player.setFoodLevel(player.getFoodLevel() - 1);
            }
        }
        session.getFlying().setFlying(FLY_SPEED, 20, () -> refresh(player));
    }

    @Override
    public boolean shouldAutoFix() {
        return true;
    }
}
