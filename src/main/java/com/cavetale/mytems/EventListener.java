package com.cavetale.mytems;

import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.item.ChristmasToken;
import com.cavetale.worldmarker.item.ItemMarker;
import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import com.destroystokyo.paper.event.inventory.PrepareResultEvent;
import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

@RequiredArgsConstructor
public final class EventListener implements Listener {
    private final MytemsPlugin plugin;

    public void enable() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    void onPlayerInteract(PlayerInteractEvent event) {
        switch (event.getAction()) {
        case RIGHT_CLICK_BLOCK:
        case RIGHT_CLICK_AIR: {
            ItemStack item = event.getItem();
            Mytems mytems = Mytems.forItem(item);
            if (mytems == null) return;
            mytems.getMytem().onPlayerRightClick(event, event.getPlayer(), item);
            break;
        }
        case LEFT_CLICK_BLOCK:
        case LEFT_CLICK_AIR: {
            ItemStack item = event.getItem();
            Mytems mytems = Mytems.forItem(item);
            if (mytems == null) return;
            mytems.getMytem().onPlayerLeftClick(event, event.getPlayer(), item);
            break;
        }
        default: break;
        }
    }

    @EventHandler
    void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItem(event.getHand());
        Mytems mytems = Mytems.forItem(item);
        if (mytems == null) return;
        mytems.getMytem().onPlayerInteractEntity(event, player, item);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    void onPlayerJoin(PlayerJoinEvent event) {
        plugin.fixPlayerInventory(event.getPlayer());
    }

    /**
     * Mytems cannot be used on a grindstone, enchanted (with books)
     * or renamed, depending on their mytem persistence flags.
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    void onPrepareResult(PrepareResultEvent event) {
        ItemStack item = event.getResult();
        Mytems mytems = Mytems.forItem(item);
        if (mytems == null) return;
        switch (event.getView().getType()) {
        case GRINDSTONE:
            if (!mytems.getMytem().getMytemPersistenceFlags().contains(MytemPersistenceFlag.ENCHANTMENTS)) {
                event.setResult(null);
            }
            break;
        case ANVIL: {
            AnvilInventory inv = (AnvilInventory) event.getInventory();
            if (inv.getRenameText() != null) {
                if (!mytems.getMytem().getMytemPersistenceFlags().contains(MytemPersistenceFlag.DISPLAY_NAME)) {
                    event.setResult(null);
                }
            } else {
                if (!mytems.getMytem().getMytemPersistenceFlags().contains(MytemPersistenceFlag.ENCHANTMENTS)) {
                    event.setResult(null);
                }
            }
            break;
        }
        default:
            break;
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    void onPrepareItemEnchant(PrepareItemEnchantEvent event) {
        ItemStack item = event.getItem();
        Mytems mytems = Mytems.forItem(item);
        if (mytems == null) return;
        if (!mytems.getMytem().getMytemPersistenceFlags().contains(MytemPersistenceFlag.ENCHANTMENTS)) {
            for (EnchantmentOffer offer : event.getOffers()) {
                offer.setCost(9999);
                offer.setEnchantment(Enchantment.VANISHING_CURSE);
                offer.setEnchantmentLevel(1);
            }
        }
    }

    /**
     * Mytems should not be wasted in regular recipes.
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    void onPrepareItemCraft(PrepareItemCraftEvent event) {
        for (ItemStack item : event.getInventory().getMatrix()) {
            Mytems mytems = Mytems.forItem(item);
            if (mytems != null) {
                event.getInventory().setResult(null);
                return;
            }
        }
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.MONITOR)
    void onPlayerFallDamage(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        for (SetBonus setBonus : plugin.sessions.of(player).getEquipment().getSetBonuses()) {
            setBonus.onPlayerDamage(event, player);
        }
        ItemStack item = player.getInventory().getBoots();
        if (item == null) return;
        String id = ItemMarker.getId(item);
        if (id == null) return;
        Mytems mytems = Mytems.forId(id);
        if (mytems == null) return;
        plugin.getMytem(mytems).onPlayerFallDamage(event, player, item);
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.HIGH)
    void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
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
    void onEntityShootBow(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        ItemStack item = event.getBow();
        if (item == null) return;
        String id = ItemMarker.getId(item);
        if (id == null) return;
        Mytems mytems = Mytems.forId(id);
        if (mytems == null) return;
        plugin.getMytem(mytems).onPlayerShootBow(event, player, item);
    }

    @EventHandler
    void onPlayerItemHeld(PlayerItemHeldEvent event) {
        plugin.sessions.of(event.getPlayer()).equipmentDidChange();
    }

    @EventHandler
    void onPlayerArmorChange(PlayerArmorChangeEvent event) {
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
    void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() instanceof PlayerInventory && event.getWhoClicked() instanceof Player) {
            plugin.sessions.of((Player) event.getWhoClicked()).equipmentDidChange();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    void onEntityAddToWorld(EntityAddToWorldEvent event) {
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
    void onPlayerDropItem(PlayerDropItemEvent event) {
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
    void onEntityPickupItem(EntityPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        Mytems mytems = Mytems.forItem(item);
        if (mytems == null) return;
        mytems.getMytem().onEntityPickup(event, item);
    }

    @EventHandler(priority = EventPriority.LOW)
    void onInventoryPickupItem(InventoryPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        Mytems mytems = Mytems.forItem(item);
        if (mytems == null) return;
        mytems.getMytem().onInventoryPickup(event, item);
    }

    @EventHandler
    void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        plugin.sessions.of(event.getPlayer()).equipmentDidChange();
    }

    /**
     * Restore the Christmas Token and fix the missing id bug.
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onBlockDropItem(BlockDropItemEvent event) {
        BlockState state = event.getBlockState();
        if (event.getItems().size() != 1) return;
        if (!(state instanceof Skull)) return;
        Skull skull = (Skull) state;
        PlayerProfile profile = skull.getPlayerProfile();
        if (profile == null) return;
        UUID id = profile.getId();
        if (!Objects.equals(id, ChristmasToken.SKULL_ID)) return;
        if (!profile.hasProperty("textures")) return;
        for (ProfileProperty property : profile.getProperties()) {
            if (Objects.equals(property.getName(), "textures")
                && Objects.equals(property.getValue(), ChristmasToken.SKULL_TEXTURE)) {
                // Success!
                Location location = event.getItems().get(0).getLocation();
                event.setCancelled(true);
                ItemStack drop = Mytems.CHRISTMAS_TOKEN.createItemStack(event.getPlayer());
                location.getWorld().dropItem(location, drop);
                return;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    void onBlockPlace(BlockPlaceEvent event) {
        ItemStack itemStack = event.getItemInHand();
        if (itemStack != null) {
            Mytems mytems = Mytems.forItem(itemStack);
            if (mytems != null) {
                mytems.getMytem().onBlockPlace(event, event.getPlayer(), itemStack);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack != null) {
            Mytems mytems = Mytems.forItem(itemStack);
            if (mytems != null) {
                mytems.getMytem().onBlockBreak(event, player, itemStack);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    void onBlockDamage(BlockDamageEvent event) {
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
    void onPlayerBucketFill(PlayerBucketFillEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack == null) return;
        Mytems mytems = Mytems.forItem(itemStack);
        if (mytems == null) return;
        mytems.getMytem().onBucketFill(event, event.getPlayer(), itemStack);
    }

    @EventHandler(priority = EventPriority.HIGH)
    void onEntityToggleGlide(EntityToggleGlideEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        ItemStack itemStack = player.getInventory().getChestplate();
        if (itemStack == null) return;
        Mytems mytems = Mytems.forItem(itemStack);
        if (mytems == null) return;
        mytems.getMytem().onToggleGlide(event, player, itemStack);
    }

    @EventHandler(priority = EventPriority.HIGH)
    void onPlayerItemConsume(PlayerItemConsumeEvent event) {
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
    void onEntityPotionEffect(EntityPotionEffectEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            for (SetBonus setBonus : plugin.sessions.of(player).getEquipment().getSetBonuses()) {
                setBonus.onPlayerPotionEffect(event, player);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    void onPlayerJump(PlayerJumpEvent event) {
        Player player = event.getPlayer();
        for (SetBonus setBonus : plugin.sessions.of(player).getEquipment().getSetBonuses()) {
            setBonus.onPlayerJump(event, player);
        }
    }

    @EventHandler
    void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin() == plugin) return;
        if (event.getPlugin() instanceof JavaPlugin) {
            plugin.onDisablePlugin((JavaPlugin) event.getPlugin());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    protected void onChunkLoad(ChunkLoadEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> {
                plugin.fixChunkBlocks(event.getChunk());
            });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    protected void onEntitiesLoad(EntitiesLoadEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> {
                for (Entity entity : event.getEntities()) {
                    plugin.fixEntity(entity);
                }
            });
    }
}
