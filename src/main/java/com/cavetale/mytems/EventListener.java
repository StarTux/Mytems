package com.cavetale.mytems;

import com.cavetale.core.event.block.PlayerBreakBlockEvent;
import com.cavetale.core.event.block.PlayerChangeBlockEvent;
import com.cavetale.core.event.entity.PlayerEntityAbilityQuery;
import com.cavetale.mytems.event.combat.DamageCalculation;
import com.cavetale.mytems.event.combat.DamageCalculationEvent;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.item.music.PlayerPlayInstrumentEvent;
import com.cavetale.mytems.util.Entities;
import com.cavetale.worldmarker.entity.EntityMarker;
import com.cavetale.worldmarker.util.Tags;
import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.destroystokyo.paper.event.inventory.PrepareResultEvent;
import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import io.papermc.paper.event.player.PrePlayerAttackEntityEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.entity.ZombieHorse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityEnterBlockEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;

@RequiredArgsConstructor
public final class EventListener implements Listener {
    private final MytemsPlugin plugin;
    private NamespacedKey fixBlocksKey;
    private long fixBlocksValue;
    @Getter private Set<UUID> damageCalculationDebugPlayers = new HashSet<>();

    public void enable() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        fixBlocksKey = new NamespacedKey(plugin, "fixblocks");
        fixBlocksValue = ThreadLocalRandom.current().nextLong();
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        for (SetBonus setBonus : plugin.sessions.of(player).getEquipment().getSetBonuses()) {
            setBonus.onPlayerInteract(event, player);
        }
        switch (event.getAction()) {
        case RIGHT_CLICK_BLOCK:
        case RIGHT_CLICK_AIR: {
            ItemStack item = event.getItem();
            if (item != null && item.getType() == Material.WRITTEN_BOOK) {
                // Close inventories before opening any books because
                // InventoryCloseEvent is never called!
                player.closeInventory();
            }
            Mytems mytems = Mytems.forItem(item);
            if (mytems == null) return;
            mytems.getMytem().onPlayerRightClick(event, player, item);
            break;
        }
        case LEFT_CLICK_BLOCK:
        case LEFT_CLICK_AIR: {
            ItemStack item = event.getItem();
            Mytems mytems = Mytems.forItem(item);
            if (mytems == null) return;
            mytems.getMytem().onPlayerLeftClick(event, player, item);
            break;
        }
        default: break;
        }
    }

    @EventHandler
    private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItem(event.getHand());
        Mytems mytems = Mytems.forItem(item);
        if (mytems != null) {
            mytems.getMytem().onPlayerInteractEntity(event, player, item);
        }
        for (SetBonus setBonus : plugin.sessions.of(player).getEquipment().getSetBonuses()) {
            setBonus.onPlayerInteractEntity(event, player);
        }
        if (event.isCancelled()) return;
        if (event.getHand() == EquipmentSlot.HAND && event.getRightClicked() instanceof ZombieHorse zhorse && !zhorse.isTamed()
            && zhorse.getPassengers().isEmpty() && PlayerEntityAbilityQuery.Action.MOUNT.query(player, zhorse)) {
            if (item != null && !item.getType().isAir()) {
                zhorse.getWorld().playSound(zhorse.getEyeLocation(), Sound.ENTITY_HORSE_ANGRY, 1.0f, 1.0f);
            } else {
                if (!player.isInsideVehicle() || player.leaveVehicle()) {
                    zhorse.addPassenger(player);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        plugin.fixPlayerInventory(player);
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.equals(player)) continue;
            if (plugin.sessionOf(online).isHidingPlayers()) {
                online.hidePlayer(plugin, event.getPlayer());
            }
        }
    }

    /**
     * Mytems cannot be crafted on any workstation.
     * Should handle:
     * - PrepareAnvilEvent
     * - PrepareGrindstoneEvent
     * - PrepareSmithingEvent
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    private void onPrepareResult(PrepareResultEvent event) {
        ItemStack item = event.getResult();
        Mytems mytems = Mytems.forItem(item);
        if (mytems == null) return;
        event.setResult(null);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    private void onPrepareItemEnchant(PrepareItemEnchantEvent event) {
        ItemStack item = event.getItem();
        Mytems mytems = Mytems.forItem(item);
        if (mytems == null) return;
        for (EnchantmentOffer offer : event.getOffers()) {
            offer.setCost(9999);
            offer.setEnchantment(Enchantment.VANISHING_CURSE);
            offer.setEnchantmentLevel(1);
        }
    }

    /**
     * Mytems should not be wasted in regular recipes.
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    private void onPrepareItemCraft(PrepareItemCraftEvent event) {
        for (ItemStack item : event.getInventory().getMatrix()) {
            Mytems mytems = Mytems.forItem(item);
            if (mytems != null) {
                event.getInventory().setResult(null);
                return;
            }
        }
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
    private void onPlayerDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        for (SetBonus setBonus : plugin.sessions.of(player).getEquipment().getSetBonuses()) {
            setBonus.onPlayerDamage(event, player);
        }
        for (EquipmentSlot slot : List.of(EquipmentSlot.HAND, EquipmentSlot.OFF_HAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)) {
            ItemStack item = player.getInventory().getItem(slot);
            if (item == null) continue;
            Mytems mytems = Mytems.forItem(item);
            if (mytems == null) continue;
            plugin.getMytem(mytems).onPlayerDamage(event, player, item, slot);
        }
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.HIGH)
    private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            if (event.getCause() == DamageCause.ENTITY_ATTACK) {
                Player player = (Player) event.getDamager();
                ItemStack hand = player.getInventory().getItemInMainHand();
                Mytems mytems = Mytems.forItem(hand);
                if (mytems != null) {
                    mytems.getMytem().onDamageEntity(event, player, hand);
                }
            }
        }
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            for (SetBonus setBonus : plugin.sessions.of(player).getEquipment().getSetBonuses()) {
                setBonus.onPlayerDamageByEntity(event, player, event.getDamager());
            }
        }
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOW)
    private void onPrePlayerAttackEntity(PrePlayerAttackEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        Mytems mytems = Mytems.forItem(item);
        if (mytems != null) mytems.getMytem().onPrePlayerAttackEntity(event, player, item);
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOW)
    private void onEntityShootBow(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ItemStack item = event.getBow();
        if (item == null) return;
        Mytems mytems = Mytems.forItem(item);
        if (mytems == null) return;
        plugin.getMytem(mytems).onPlayerShootBow(event, player, item);
    }

    @EventHandler
    private void onPlayerItemHeld(PlayerItemHeldEvent event) {
        plugin.sessions.of(event.getPlayer()).equipmentDidChange();
    }

    @EventHandler
    private void onPlayerArmorChange(PlayerArmorChangeEvent event) {
        Mytems oldMytems = Mytems.forItem(event.getOldItem());
        if (oldMytems != null) {
            oldMytems.getMytem().onPlayerArmorRemove(event, event.getPlayer(), event.getOldItem());
        }
        Mytems newMytems = Mytems.forItem(event.getNewItem());
        if (newMytems != null) {
            newMytems.getMytem().onPlayerArmorEquip(event, event.getPlayer(), event.getNewItem());
        }
        plugin.sessions.of(event.getPlayer()).equipmentDidChange();
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getClickedInventory() instanceof PlayerInventory) {
            plugin.sessions.of(player).equipmentDidChange();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onEntityAddToWorld(EntityAddToWorldEvent event) {
        if (event.getEntity() instanceof Item) {
            Item item = (Item) event.getEntity();
            ItemStack itemStack = item.getItemStack();
            if (itemStack == null) return;
            ItemStack newItemStack = plugin.fixItemStack(itemStack);
            if (newItemStack == null) return;
            item.setItemStack(newItemStack);
        }
    }

    @EventHandler
    private void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItemDrop().getItemStack();
        Mytems mytems = Mytems.forItem(item);
        if (mytems != null) {
            mytems.getMytem().onPlayerDrop(event, player, item);
            if (event.isCancelled()) return;
        }
        plugin.sessions.of(player).equipmentDidChange();
    }

    @EventHandler
    private void onEntityPickupItem(EntityPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        Mytems mytems = Mytems.forItem(item);
        if (mytems == null) return;
        mytems.getMytem().onEntityPickup(event, item);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onInventoryPickupItem(InventoryPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        Mytems mytems = Mytems.forItem(item);
        if (mytems == null) return;
        mytems.getMytem().onInventoryPickup(event, item);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getMainHandItem();
        Mytems mytems = Mytems.forItem(item);
        if (mytems != null) {
            mytems.getMytem().onSwapHandItems(event, player, item, EquipmentSlot.HAND);
        }
        item = event.getMainHandItem();
        mytems = Mytems.forItem(item);
        if (mytems != null) {
            mytems.getMytem().onSwapHandItems(event, player, item, EquipmentSlot.OFF_HAND);
        }
        if (event.isCancelled()) return;
        plugin.sessions.of(player).equipmentDidChange();
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onBlockPlace(BlockPlaceEvent event) {
        ItemStack itemStack = event.getItemInHand();
        if (itemStack != null) {
            Mytems mytems = Mytems.forItem(itemStack);
            if (mytems != null) {
                mytems.getMytem().onBlockPlace(event, event.getPlayer(), itemStack);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onBlockBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (itemInHand != null) {
            final Mytems mytems = Mytems.forItem(itemInHand);
            if (mytems != null) {
                mytems.getMytem().onBlockBreak(event, player, itemInHand);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onBlockDamage(BlockDamageEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack != null) {
            Mytems mytems = Mytems.forItem(itemStack);
            if (mytems != null) {
                mytems.getMytem().onBlockDamage(event, player, itemStack);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onPlayerBucketFill(PlayerBucketFillEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack == null) return;
        Mytems mytems = Mytems.forItem(itemStack);
        if (mytems == null) return;
        mytems.getMytem().onBucketFill(event, event.getPlayer(), itemStack);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onEntityToggleGlide(EntityToggleGlideEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        ItemStack itemStack = player.getInventory().getChestplate();
        if (itemStack == null) return;
        Mytems mytems = Mytems.forItem(itemStack);
        if (mytems == null) return;
        mytems.getMytem().onToggleGlide(event, player, itemStack);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Mytems mytems = Mytems.forItem(item);
        if (mytems != null) {
            mytems.getMytem().onConsume(event, player, item);
        }
        for (SetBonus setBonus : plugin.sessions.of(player).getEquipment().getSetBonuses()) {
            setBonus.onPlayerItemConsume(event, player, item);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onEntityPotionEffect(EntityPotionEffectEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            for (SetBonus setBonus : plugin.sessions.of(player).getEquipment().getSetBonuses()) {
                setBonus.onPlayerPotionEffect(event, player);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onPlayerJump(PlayerJumpEvent event) {
        Player player = event.getPlayer();
        for (SetBonus setBonus : plugin.sessions.of(player).getEquipment().getSetBonuses()) {
            setBonus.onPlayerJump(event, player);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onPlayerMove(PlayerMoveEvent event) {
        if (!event.hasChangedPosition()) return;
        Player player = event.getPlayer();
        for (SetBonus setBonus : plugin.sessions.of(player).getEquipment().getSetBonuses()) {
            setBonus.onPlayerMove(event, player);
        }
    }

    @EventHandler
    private void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin() == plugin) return;
        if (event.getPlugin() instanceof JavaPlugin) {
            plugin.onDisablePlugin((JavaPlugin) event.getPlugin());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onChunkLoad(ChunkLoadEvent event) {
        final Chunk chunk = event.getChunk();
        Bukkit.getScheduler().runTask(plugin, () -> {
                PersistentDataContainer tag = chunk.getPersistentDataContainer();
                Long value = Tags.getLong(tag, fixBlocksKey);
                if (value != null && value == fixBlocksValue) return;
                Tags.set(tag, fixBlocksKey, fixBlocksValue);
                plugin.fixChunkBlocks(chunk);
            });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onEntitiesLoad(EntitiesLoadEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> {
                for (Entity entity : event.getEntities()) {
                    plugin.fixEntity(entity);
                }
            });
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = false)
    private void onPlayerAttemptPickupItem(PlayerAttemptPickupItemEvent event) {
        Mytems mytems = Mytems.forItem(event.getItem().getItemStack());
        if (mytems == null) return;
        mytems.getMytem().onPlayerAttemptPickupItem(event);
    }

    private boolean onPlayerLaunchTridentRecursiveLock;

    /**
     * Return elytra with loytalty instantly.  We do this by
     * cancelling the event, launching another elytra, and putting the
     * item on a longer cooldown.
     *
     * Sadly, removing the enchant from the projectile, which would be
     * the ideal solution, is not possible.
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onPlayerLaunchTrident(ProjectileLaunchEvent event) {
        if (onPlayerLaunchTridentRecursiveLock) return;
        if (!(event.getEntity() instanceof Trident trident)) return;
        ItemStack itemStack = trident.getItemStack();
        final int loy = itemStack.getEnchantmentLevel(Enchantment.LOYALTY);
        if (loy <= 0) return;
        if (!(trident.getShooter() instanceof Player player)) return;
        onPlayerLaunchTridentRecursiveLock = true;
        Trident trident2 = player.launchProjectile(Trident.class, trident.getVelocity());
        onPlayerLaunchTridentRecursiveLock = false;
        if (trident2 == null) return;
        event.setCancelled(true);
        trident2.setPersistent(false);
        trident2.setItem(itemStack.clone());
        trident2.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
        trident2.setCritical(trident.isCritical());
        trident2.setDamage(trident.getDamage());
        trident2.setKnockbackStrength(trident.getKnockbackStrength());
        trident2.setPierceLevel(trident.getPierceLevel());
        trident2.setLoyaltyLevel(0);
        EntityMarker.setId(trident2, "mytems:trident");
        final int chan = itemStack.getEnchantmentLevel(Enchantment.CHANNELING);
        if (chan > 0) {
            trident2.setGlint(true);
        }
        player.setCooldown(itemStack.getType(), Math.max(0, (4 - loy) * 20));
        player.playSound(player.getLocation(), Sound.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0f, 1.0f);
        if (player.getGameMode() != GameMode.CREATIVE) {
            final int unbreaking = itemStack.getEnchantmentLevel(Enchantment.UNBREAKING);
            if (unbreaking > 0) {
                float chance = 1.0f / (float) (unbreaking + 1);
                float roll = (float) ThreadLocalRandom.current().nextDouble();
                if (roll >= chance) return;
            }
            ItemStack hand = player.getInventory().getItemInMainHand();
            if (hand == null || hand.getType() != Material.TRIDENT) {
                hand = player.getInventory().getItemInOffHand();
            }
            if (hand != null && hand.getType() == Material.TRIDENT) {
                hand.editMeta(meta -> {
                        if (!meta.isUnbreakable() && meta instanceof Damageable dmg) {
                            dmg.setDamage(dmg.getDamage() + 1);
                        }
                    });
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onTridentHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Trident trident && EntityMarker.hasId(trident, "mytems:trident")) {
            trident.remove();
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onProjectileHitPlayer(ProjectileHitEvent event) {
        if (event.getHitEntity() instanceof Player player) {
            for (SetBonus setBonus : plugin.sessions.of(player).getEquipment().getSetBonuses()) {
                setBonus.onProjectileHitPlayer(event, player);
            }
            ItemStack hand = player.getInventory().getItemInMainHand();
            if (hand != null) {
                Mytems mytems = Mytems.forItem(hand);
                if (mytems != null) {
                    mytems.getMytem().onProjectileHitPlayer(event, player, hand, EquipmentSlot.HAND);
                }
            }
            ItemStack offHand = player.getInventory().getItemInOffHand();
            if (offHand != null) {
                Mytems mytems = Mytems.forItem(offHand);
                if (mytems != null) {
                    mytems.getMytem().onProjectileHitPlayer(event, player, offHand, EquipmentSlot.OFF_HAND);
                }
            }
        }
    }

    private DamageCalculationEvent damageCalculationEvent;

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    private void onEntityDamageCalculateLow(EntityDamageEvent event) {
        DamageCalculation calc = new DamageCalculation(event);
        if (!calc.isValid()) return;
        damageCalculationEvent = new DamageCalculationEvent(calc);
        damageCalculationEvent.callEvent();
        if (calc.attackerIsPlayer() && damageCalculationDebugPlayers.contains(calc.getAttackerPlayer().getUniqueId())) {
            damageCalculationEvent.setShouldPrintDebug(true);
        }
        if (calc.targetIsPlayer() && damageCalculationDebugPlayers.contains(calc.getTargetPlayer().getUniqueId())) {
            damageCalculationEvent.setShouldPrintDebug(true);
        }
        if (!damageCalculationEvent.isHandled() && !damageCalculationEvent.isShouldPrintDebug()) {
            damageCalculationEvent = null;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    private void onEntityDamageCalculateHighest(EntityDamageEvent event) {
        if (damageCalculationEvent == null) return;
        if (damageCalculationEvent.getEntityDamageEvent() != event) {
            damageCalculationEvent = null;
        }
        if (damageCalculationEvent.isHandled() && !event.isCancelled()) {
            damageCalculationEvent.getCalculation().apply();
        }
        if (damageCalculationEvent.isShouldPrintDebug()) {
            damageCalculationEvent.getCalculation().debugPrint();
        }
        damageCalculationEvent.schedulePostDamageActions();
        damageCalculationEvent = null;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    private void onDamageCalculation(DamageCalculationEvent event) {
        // Defender
        if (event.targetIsPlayer()) {
            Player target = event.getTargetPlayer();
            for (SetBonus setBonus : plugin.sessions.of(target).getEquipment().getSetBonuses()) {
                setBonus.onDefendingDamageCalculation(event);
            }
        }
        if (event.getTarget() != null) {
            for (EquipmentSlot slot : List.of(EquipmentSlot.HAND, EquipmentSlot.OFF_HAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)) {
                ItemStack itemStack = event.getTarget().getEquipment().getItem(slot);
                Mytems mytems = Mytems.forItem(itemStack);
                if (mytems == null) continue;
                mytems.getMytem().onDefendingDamageCalculation(event, itemStack, slot);
            }
        }
        // Attacker
        if (event.attackerIsPlayer()) {
            Player attacker = event.getAttackerPlayer();
            for (SetBonus setBonus : plugin.sessions.of(attacker).getEquipment().getSetBonuses()) {
                setBonus.onAttackingDamageCalculation(event);
            }
        }
        if (event.getAttacker() != null) {
            for (EquipmentSlot slot : List.of(EquipmentSlot.HAND, EquipmentSlot.OFF_HAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)) {
                ItemStack itemStack = event.getAttacker().getEquipment().getItem(slot);
                Mytems mytems = Mytems.forItem(itemStack);
                if (mytems == null) continue;
                mytems.getMytem().onAttackingDamageCalculation(event, itemStack, slot);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onPlayerItemDamage(PlayerItemDamageEvent event) {
        ItemStack item = event.getItem();
        Mytems mytems = Mytems.forItem(item);
        if (mytems != null) mytems.getMytem().onPlayerItemDamage(event);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onEntityTarget(EntityTargetEvent event) {
        if (event.getTarget() instanceof Player player) {
            for (SetBonus setBonus : plugin.sessions.of(player).getEquipment().getSetBonuses()) {
                setBonus.onEntityTargetPlayer(event, player);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player player) {
            for (SetBonus setBonus : plugin.sessions.of(player).getEquipment().getSetBonuses()) {
                setBonus.onFoodLevelChange(event, player);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onPlayerShearEntity(PlayerShearEntityEvent event) {
        Player player = event.getPlayer();
        for (SetBonus setBonus : plugin.sessions.of(player).getEquipment().getSetBonuses()) {
            setBonus.onPlayerShearEntity(event, player);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onPlayerPlayInstrument(PlayerPlayInstrumentEvent event) {
        Player player = event.getPlayer();
        for (SetBonus setBonus : plugin.sessions.of(player).getEquipment().getSetBonuses()) {
            setBonus.onPlayerPlayInstrument(event, player);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (event.isSneaking()) {
            player.eject();
        }
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack != null) {
            Mytems mytems = Mytems.forItem(itemStack);
            if (mytems != null) {
                mytems.getMytem().onPlayerToggleSneak(event, player, itemStack, EquipmentSlot.HAND);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onEntityRemoveFromWorld(EntityRemoveFromWorldEvent event) {
        Entity entity = event.getEntity();
        if (Entities.isTransient(entity)) {
            Bukkit.getScheduler().runTask(plugin, () -> entity.remove());
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onEntityEnterBlock(EntityEnterBlockEvent event) {
        Entity entity = event.getEntity();
        if (Entities.isTransient(entity)) {
            event.setCancelled(true);
            entity.remove();
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onFurnaceBurn(FurnaceBurnEvent event) {
        ItemStack fuel = event.getFuel();
        Mytems mytems = Mytems.forItem(fuel);
        if (mytems != null) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onEntityAirChange(EntityAirChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        for (EquipmentSlot slot : List.of(EquipmentSlot.HAND, EquipmentSlot.OFF_HAND, EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)) {
            final ItemStack item = player.getInventory().getItem(slot);
            if (item == null) continue;
            final Mytems mytems = Mytems.forItem(item);
            if (mytems == null) continue;
            plugin.getMytem(mytems).onPlayerAirChange(event, player, item, slot);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onPlayerBreakBlock(PlayerBreakBlockEvent event) {
        if (!event.hasItem()) return;
        final Mytems mytems = Mytems.forItem(event.getItemStack());
        if (mytems == null) return;
        mytems.getMytem().onPlayerBreakBlock(event, event.getPlayer(), event.getItemStack());
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onPlayerChangeBlock(PlayerChangeBlockEvent event) {
        if (!event.hasItem()) return;
        final Mytems mytems = Mytems.forItem(event.getItemStack());
        if (mytems == null) return;
        mytems.getMytem().onPlayerChangeBlock(event, event.getPlayer(), event.getItemStack());
    }
}
