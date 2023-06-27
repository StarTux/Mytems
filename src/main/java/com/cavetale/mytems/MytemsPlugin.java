package com.cavetale.mytems;

import com.cavetale.core.connect.ServerCategory;
import com.cavetale.core.font.Emoji;
import com.cavetale.core.font.GlyphPolicy;
import com.cavetale.core.item.ItemFinder;
import com.cavetale.mytems.block.BlockBreakListener;
import com.cavetale.mytems.block.BlockRegistry;
import com.cavetale.mytems.item.photo.Photo;
import com.cavetale.mytems.loot.LootTableListener;
import com.cavetale.mytems.session.Session;
import com.cavetale.mytems.session.Sessions;
import com.cavetale.mytems.shulker.ShulkerBoxListener;
import com.cavetale.mytems.util.Gui;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.loot.Lootable;
import org.bukkit.plugin.java.JavaPlugin;
import static com.cavetale.core.util.CamelCase.toCamelCase;

@Getter
public final class MytemsPlugin extends JavaPlugin implements ItemFinder {
    @Getter private static MytemsPlugin instance;
    final MytemsCommand mytemsCommand = new MytemsCommand(this);
    final EventListener eventListener = new EventListener(this);
    final ShulkerBoxListener shulkerBoxListener = new ShulkerBoxListener(this);
    final BlockBreakListener blockBreakListener = new BlockBreakListener(this);
    final Sessions sessions = new Sessions(this);
    private Map<Mytems, Mytem> mytems = new EnumMap<>(Mytems.class);
    private List<CustomMytemSlot> customMytemSlots = new ArrayList<>();
    private boolean fixAllPlayerInventoriesScheduled = false;
    private final BlockRegistry blockRegistry = new BlockRegistry();

    @Override
    public void onLoad() {
        ItemFinder.super.register();
    }

    @Override
    public void onEnable() {
        instance = this;
        mytemsCommand.enable();
        eventListener.enable();
        shulkerBoxListener.enable();
        blockBreakListener.enable();
        enableItems();
        fixAllPlayerInventoriesLater();
        sessions.enable();
        Gui.enable();
        blockRegistry.enable();
        new MytemsBlockMarkerHook(this).enable();
        for (Mytems it : Mytems.values()) {
            it.preprocessAnimationFrames();
            if (it.component.equals(Component.empty())) continue;
            String name = it.name().toLowerCase();
            Emoji.addEmoji(name, it.component, it.getMytem().getDisplayName(),
                           GlyphPolicy.PUBLIC, it, toCamelCase(" ", it.category),
                           it.pixelWidth);
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            fixPlayerInventory(player);
        }
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                fixEntity(entity);
            }
            for (Chunk chunk : world.getLoadedChunks()) {
                fixChunkBlocks(chunk);
            }
        }
        if (ServerCategory.current().isSurvival()) {
            Bukkit.getPluginManager().registerEvents(new LootTableListener(this), this);
        }
    }

    @Override
    public void onDisable() {
        sessions.disable();
        for (Mytem mytem : mytems.values()) {
            try {
                mytem.disable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mytems.clear();
        ItemFinder.super.unregister();
        Gui.disable();
    }

    public void enableItems() {
        for (Mytems it : Mytems.values()) {
            final Mytem mytem;
            try {
                mytem = it.mytemClass.getConstructor(Mytems.class).newInstance(it);
            } catch (Exception t) {
                throw new RuntimeException(t);
            }
            mytems.put(it, mytem);
            mytem.enable();
        }
    }

    public Mytem getMytem(Mytems key) {
        return mytems.get(key);
    }

    public Mytem getMytem(ItemStack itemStack) {
        if (itemStack == null) return null;
        Mytems key = Mytems.forItem(itemStack);
        if (key == null) return null;
        return getMytem(key);
    }

    public void fixAllPlayerInventoriesLater() {
        if (fixAllPlayerInventoriesScheduled) return;
        fixAllPlayerInventoriesScheduled = true;
        Bukkit.getScheduler().runTaskLater(this, () -> {
                fixAllPlayerInventoriesScheduled = false;
                fixAllPlayerInventories();
            }, 0L);
    }

    public void fixAllPlayerInventories() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            fixPlayerInventory(player);
        }
    }

    public int fixPlayerInventory(Player player) {
        return fixInventory(player.getInventory())
            + fixInventory(player.getEnderChest());
    }

    public int fixInventory(Inventory inventory) {
        int count = 0;
        for (int i = 0; i < inventory.getSize(); i += 1) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack == null || itemStack.getType() == Material.AIR) continue;
            ItemStack newItemStack = fixItemStack(itemStack);
            if (newItemStack == null) continue;
            inventory.setItem(i, newItemStack);
            count += 1;
        }
        return count;
    }

    /**
     * Fix the blocks (and block entities) in a chunk.  Specifically
     * skip the entities in the chunk as they may not be loaded yet!
     */
    protected int fixChunkBlocks(Chunk chunk) {
        if (!chunk.isLoaded()) return 0;
        int result = 0;
        for (BlockState blockState : chunk.getTileEntities()) {
            if (blockState instanceof Container container) {
                if (container instanceof Lootable lootable && lootable.getLootTable() != null) {
                    continue; // Skip chest
                }
                result += fixInventory(container.getInventory());
            }
        }
        return result;
    }

    /**
     * Attempt to fix item on an entity which are visible to players.
     * Especially item frames and living entities with equipped items.
     */
    protected void fixEntity(Entity entity) {
        if (entity.isDead()) return;
        if (entity instanceof Player) return;
        if (entity instanceof ItemFrame itemFrame) {
            ItemStack itemStack = fixItemStack(itemFrame.getItem());
            if (itemStack != null) {
                itemFrame.setItem(itemStack, false); // playSound
            }
        }
        if (entity instanceof LivingEntity livingEntity) {
            EntityEquipment entityEquipment = livingEntity.getEquipment();
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack itemStack = fixItemStack(entityEquipment.getItem(slot));
                if (itemStack != null) {
                    entityEquipment.setItem(slot, itemStack);
                }
            }
        }
        if (entity instanceof InventoryHolder inventoryHolder) {
            fixInventory(inventoryHolder.getInventory());
        }
    }

    /**
     * Attempt to update an item. This means generating a new Mytem
     * with the same serialized data if it's mytem. Or a container
     * with all updated contained items.
     *
     * @return the ItemStack if it was fixed and needs updating in its
     *   context, null otherwise
     */
    public ItemStack fixItemStack(ItemStack oldItemStack) {
        if (oldItemStack == null || oldItemStack.getType() == Material.AIR) return null;
        Mytems key = Mytems.forItem(oldItemStack);
        if (key != null) {
            String serialized = key.serializeItem(oldItemStack);
            ItemStack newItemStack = Mytems.deserializeItem(serialized);
            if (newItemStack == null) return null;
            return newItemStack.equals(oldItemStack) ? null : newItemStack;
        }
        if (Tag.SHULKER_BOXES.isTagged(oldItemStack.getType())) {
            if (!oldItemStack.hasItemMeta()) return null;
            BlockStateMeta meta = (BlockStateMeta) oldItemStack.getItemMeta();
            ShulkerBox shulkerBox = (ShulkerBox) meta.getBlockState();
            int count = fixInventory(shulkerBox.getInventory());
            if (count == 0) return null;
            meta.setBlockState(shulkerBox);
            oldItemStack.setItemMeta(meta);
            return oldItemStack;
        }
        if (oldItemStack.getType() == Material.FILLED_MAP) {
            return Photo.fixLegacyPhoto(oldItemStack);
        }
        return null;
    }

    public static void registerMytem(JavaPlugin plugin, Mytems mytems, Mytem mytem) {
        if (!Objects.equals(mytems, mytem.getKey())) {
            throw new IllegalArgumentException(mytems + " != " + mytem.getKey());
        }
        Mytem old = instance.mytems.get(mytems);
        instance.mytems.put(mytems, mytem);
        mytem.enable();
        instance.customMytemSlots.removeIf(slot -> slot.mytems == mytems);
        instance.customMytemSlots.add(new CustomMytemSlot(plugin, mytems, mytem));
        if (old != null) {
            try {
                old.disable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        instance.fixAllPlayerInventoriesLater();
    }

    public static void registerMytem(JavaPlugin plugin, Mytem mytem) {
        registerMytem(plugin, mytem.getKey(), mytem);
    }

    public static void registerMytem(JavaPlugin plugin, Mytems mytems, Function<Mytems, Mytem> ctor) {
        registerMytem(plugin, mytems, ctor.apply(mytems));
    }

    protected void onDisablePlugin(JavaPlugin plugin) {
        for (CustomMytemSlot customMytemSlot : customMytemSlots) {
            if (plugin.equals(customMytemSlot.plugin)) {
                try {
                    customMytemSlot.mytem.disable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final Mytem mytem;
                try {
                    Mytems it = customMytemSlot.mytems;
                    mytem = it.mytemClass.getConstructor(Mytems.class).newInstance(it);
                } catch (Exception t) {
                    throw new RuntimeException(t);
                }
                mytems.put(customMytemSlot.mytems, mytem);
                mytem.enable();
            }
        }
        customMytemSlots.removeIf(slot -> plugin.equals(slot.plugin));
    }

    public static NamespacedKey namespacedKey(String value) {
        return new NamespacedKey(instance, value);
    }

    @Override
    public Mytems findItem(ItemStack item) {
        return Mytems.forItem(item);
    }

    @Override
    public Mytems findItem(NamespacedKey key) {
        if (!key.namespace().equals("mytems")) return null;
        return Mytems.forId(key.getKey());
    }

    public static Session sessionOf(Player player) {
        return instance.sessions.of(player);
    }

    public static MytemsPlugin plugin() {
        return instance;
    }

    public static BlockBreakListener blockBreakListener() {
        return instance.blockBreakListener;
    }
}
