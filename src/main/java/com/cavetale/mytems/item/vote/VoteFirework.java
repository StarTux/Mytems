package com.cavetale.mytems.item.vote;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public final class VoteFirework extends VoteItem implements Listener {
    private UUID fireworkUser;
    private boolean fireworkDispensed;

    public VoteFirework(final Mytems mytems) {
        super(mytems);
    }

    @Override
    public void enable() {
        displayName = Component.text("Voting Firework").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false);
        prototype = new ItemStack(key.material);
        FireworkMeta meta = (FireworkMeta) prototype.getItemMeta();
        meta.displayName(displayName);
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Firework with incredible").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("magical effects!").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text(""));
        meta.lore(lore);
        meta.addEffect(FireworkEffect.builder().withColor(Color.RED).build());
        meta.addItemFlags(ItemFlag.values());
        meta.addItemFlags(ItemFlag.values());
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
        Bukkit.getPluginManager().registerEvents(this, MytemsPlugin.getInstance());
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack itemStack) {
        if (event.useItemInHand() == Event.Result.DENY) return;
        fireworkUser = event.getPlayer().getUniqueId();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockDispense(BlockDispenseEvent event) {
        ItemStack item = event.getItem();
        if (Mytems.forItem(item) != key) return;
        fireworkDispensed = true;
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof Firework)) return;
        final Firework firework = (Firework) event.getEntity();
        if (fireworkUser != null) {
            UUID uuid = fireworkUser;
            fireworkUser = null;
            fireworkDispensed = false;
            if (!uuid.equals(firework.getSpawningEntity())) return;
            firework.setFireworkMeta(randomFireworkMeta());
        } else if (fireworkDispensed) {
            fireworkUser = null;
            fireworkDispensed = false;
            firework.setFireworkMeta(randomFireworkMeta());
        }
    }

    public FireworkEffect.Type randomFireworkEffectType() {
        switch (random.nextInt(10)) {
        case 0: return FireworkEffect.Type.CREEPER;
        case 1: return FireworkEffect.Type.STAR;
        case 2: case 3: return FireworkEffect.Type.BALL_LARGE;
        case 4: case 5: case 6: return FireworkEffect.Type.BURST;
        case 7: case 8: case 9: return FireworkEffect.Type.BALL;
        default: return FireworkEffect.Type.BALL;
        }
    }

    FireworkMeta randomFireworkMeta(FireworkEffect.Type type) {
        FireworkMeta meta = (FireworkMeta) Bukkit.getItemFactory().getItemMeta(Material.FIREWORK_ROCKET);
        FireworkEffect.Builder builder = FireworkEffect.builder().with(type);
        int amount = type == FireworkEffect.Type.CREEPER || type == FireworkEffect.Type.STAR
            ? 1 : 3 + random.nextInt(3);
        for (int i = 0; i < amount; i += 1) {
            int rgb = 0xFFFFFF & java.awt.Color.HSBtoRGB(random.nextFloat(), 1.0f, 1.0f);
            builder.withColor(Color.fromRGB(rgb));
            meta.addEffect(builder.build());
        }
        meta.setPower(1 + random.nextInt(2));
        return meta;
    }

    FireworkMeta randomFireworkMeta() {
        return randomFireworkMeta(randomFireworkEffectType());
    }
}
