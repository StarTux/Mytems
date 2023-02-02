package com.cavetale.mytems.item.beestick;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.entity.PlayerEntityAbilityQuery;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Entities;
import com.cavetale.mytems.util.Items;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Bee;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Text.wrapLore;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static org.bukkit.Sound.*;
import static org.bukkit.SoundCategory.*;
import static org.bukkit.attribute.Attribute.*;

@RequiredArgsConstructor @Getter
public final class Beestick implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private String description = "Spawn angry bees at your opponents";
    private int hunger = 1;

    @Override
    public void enable() {
        this.displayName = text("Beestick", GOLD);
        prototype = new ItemStack(key.material);
        List<Component> lore = new ArrayList<>();
        lore.add(displayName);
        lore.addAll(wrapLore(tiny(description.toLowerCase()), c -> c.color(GOLD)));
        lore.add(empty());
        lore.add(join(noSeparators(), text(tiny("cost "), GRAY), text(hunger + " hunger", GOLD)));
        prototype.editMeta(meta -> {
                Items.text(meta, lore);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setUseItemInHand(Event.Result.DENY);
        spawnBee(player);
    }

    private void spawnBee(Player player) {
        Location location = player.getEyeLocation();
        Block block = location.getBlock();
        if (!PlayerBlockAbilityQuery.Action.SPAWN_MOB.query(player, block)) {
            player.sendActionBar(text("Cannot spawn bees here", RED));
            player.playSound(player.getLocation(), ENTITY_BEE_DEATH, MASTER, 1.0f, 0.8f);
            return;
        }
        if (player.getFoodLevel() < hunger) {
            player.sendActionBar(text("Not enough food", RED));
            player.playSound(player.getLocation(), ENTITY_BEE_DEATH, MASTER, 1.0f, 0.8f);
            return;
        }
        location = location.add(location.getDirection());
        Bee bee = location.getWorld().spawn(location, Bee.class, e -> {
                e.setPersistent(false);
                Entities.setTransient(e);
                e.setRemoveWhenFarAway(true);
                e.setAdult();
                e.getAttribute(GENERIC_MAX_HEALTH).setBaseValue(0.1);
                e.setHealth(0.1);
                e.setAnger(9999);
            });
        if (bee == null || bee.isDead()) {
            player.sendActionBar(text("Cannot spawn bees here", RED));
            player.playSound(player.getLocation(), ENTITY_BEE_DEATH, MASTER, 1.0f, 0.8f);
            return;
        }
        player.setFoodLevel(player.getFoodLevel() - hunger);
        Collection<LivingEntity> targets = location.getNearbyEntitiesByType(LivingEntity.class, 16.0, 16.0, 16.0, e -> {
                return !player.equals(e) && !bee.equals(e)
                    && PlayerEntityAbilityQuery.Action.DAMAGE.query(player, e)
                    && !(e instanceof Tameable tameable && tameable.isTamed())
                    && bee.hasLineOfSight(e);
            });
        List<LivingEntity> monsters = new ArrayList<>();
        List<LivingEntity> players = new ArrayList<>();
        List<LivingEntity> mobs = new ArrayList<>();
        for (LivingEntity target : targets) {
            if (target instanceof Player) {
                players.add(target);
            } else if (target instanceof Monster) {
                monsters.add(target);
            } else if (target instanceof Mob) {
                mobs.add(target);
            }
        }
        List<LivingEntity> enemies = !monsters.isEmpty()
            ? monsters
            : (!players.isEmpty()
               ? players
               : mobs);
        if (enemies.isEmpty()) {
            bee.setTarget(player);
        } else {
            LivingEntity enemy = enemies.get(ThreadLocalRandom.current().nextInt(enemies.size()));
            bee.setTarget(enemy);
        }
    }
}
