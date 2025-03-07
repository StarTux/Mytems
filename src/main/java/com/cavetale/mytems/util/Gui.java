package com.cavetale.mytems.util;

import com.cavetale.core.font.GuiOverlay;
import com.cavetale.mytems.MytemsPlugin;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor
public final class Gui implements InventoryHolder {
    public static final int OUTSIDE = -999;
    public static final int OFF_HAND = 40;
    @Getter private final Plugin plugin;
    @Getter @Setter private Object privateData;
    private Inventory inventory;
    private Map<Integer, Slot> slots = new HashMap<>();
    private Consumer<InventoryCloseEvent> onClose = null;
    private Consumer<InventoryOpenEvent> onOpen = null;
    private Consumer<InventoryClickEvent> onClick = null;
    private Consumer<InventoryClickEvent> onClickBottom = null;
    private Consumer<InventoryDragEvent> onDrag = null;
    @Getter @Setter private boolean editable = false;
    @Getter private int size = 3 * 9;
    @Getter private Component title = Component.empty();
    @Getter @Setter private InventoryType inventoryType = null;
    @Getter @Setter protected boolean locked = false;
    private static final Map<UUID, Gui> GUI_MAP = new HashMap<>();
    private GuiOverlay.Builder overlayBuilder;

    @RequiredArgsConstructor @AllArgsConstructor
    private static final class Slot {
        final int index;
        ItemStack item;
        Consumer<InventoryClickEvent> onClick;
    }

    public Gui() {
        this(MytemsPlugin.getInstance());
    }

    public Gui title(Component newTitle) {
        title = newTitle;
        return this;
    }

    public Gui size(int newSize) {
        if (newSize <= 0 || newSize % 9 != 0) {
            throw new IllegalArgumentException("newSize=" + newSize);
        }
        if (inventoryType != null && newSize != inventoryType.getDefaultSize()) {
            throw new IllegalArgumentException("newSize: " + newSize + "!=" + inventoryType.getDefaultSize());
        }
        size = newSize;
        return this;
    }

    public Gui type(InventoryType newType) {
        if (!newType.isCreatable()) {
            throw new IllegalArgumentException("newType=" + newType);
        }
        this.inventoryType = newType;
        this.size = inventoryType.getDefaultSize();
        return this;
    }

    public Gui rows(int rowCount) {
        if (rowCount <= 0) throw new IllegalArgumentException("rowCount=" + rowCount);
        size = rowCount * 9;
        return this;
    }

    public Inventory getInventory() {
        if (inventory == null) {
            inventory = inventoryType == null
                ? Bukkit.getServer().createInventory(this, size, buildTitle())
                : Bukkit.getServer().createInventory(this, inventoryType, buildTitle());
            for (int i = 0; i < size; i += 1) {
                Slot slot = slots.get(i);
                if (slot != null) {
                    inventory.setItem(i, slot.item);
                }
            }
        }
        return inventory;
    }

    public ItemStack getItem(int index) {
        if (index < 0) index = OUTSIDE;
        Slot slot = slots.get(index);
        return slot != null
            ? slot.item
            : null;
    }

    public void setItem(int index, ItemStack item) {
        setItem(index, item, null);
    }

    public void setItem(int index, ItemStack item, Consumer<InventoryClickEvent> responder) {
        if (inventory != null && index >= 0 && inventory.getSize() > index) {
            inventory.setItem(index, item);
        }
        if (index < 0) index = OUTSIDE;
        Slot slot = new Slot(index, item, responder);
        slots.put(index, slot);
    }

    public void setItem(int column, int row, ItemStack item, Consumer<InventoryClickEvent> responder) {
        if (column < 0 || column > 8) {
            throw new IllegalArgumentException("column=" + column);
        }
        if (row < 0) throw new IllegalArgumentException("row=" + row);
        setItem(column + row * 9, item, responder);
    }

    public void onClickOutside(Consumer<InventoryClickEvent> responder) {
        setItem(OUTSIDE, null, responder);
    }

    public Gui open(Player player) {
        player.openInventory(getInventory());
        return this;
    }

    public Gui reopen(Player player) {
        player.closeInventory();
        inventory = null;
        player.openInventory(getInventory());
        return this;
    }

    public Gui onClose(Consumer<InventoryCloseEvent> responder) {
        onClose = responder;
        return this;
    }

    public Gui onOpen(Consumer<InventoryOpenEvent> responder) {
        onOpen = responder;
        return this;
    }

    public Gui onClick(Consumer<InventoryClickEvent> responder) {
        onClick = responder;
        return this;
    }

    public Gui onClickBottom(Consumer<InventoryClickEvent> responder) {
        onClickBottom = responder;
        return this;
    }

    public Gui onDrag(Consumer<InventoryDragEvent> responder) {
        onDrag = responder;
        return this;
    }

    public Gui clear() {
        if (inventory != null) inventory.clear();
        slots.clear();
        onOpen = null;
        onClose = null;
        return this;
    }

    private void onInventoryOpen(InventoryOpenEvent event) {
        if (onOpen != null) {
            Bukkit.getScheduler().runTask(plugin, () -> onOpen.accept(event));
        }
    }

    private void onInventoryClose(InventoryCloseEvent event) {
        if (onClose != null) {
            onClose.accept(event);
        }
    }

    private void onInventoryClick(InventoryClickEvent event) {
        if (onClick != null) {
            onClick.accept(event);
            if (event.isCancelled()) return;
        }
        if (!editable) {
            event.setCancelled(true);
        }
        if (locked) return;
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (event.getClickedInventory() != null && !inventory.equals(event.getClickedInventory())) {
            if (onClickBottom != null) {
                onClickBottom.accept(event);
            }
            return;
        }
        Slot slot = slots.get(event.getSlot());
        if (slot != null && slot.onClick != null) {
            locked = true;
            Bukkit.getScheduler().runTask(plugin, () -> {
                    locked = false;
                    slot.onClick.accept(event);
                });
        }
    }

    private void onInventoryDrag(InventoryDragEvent event) {
        if (onDrag != null) {
            onDrag.accept(event);
            if (event.isCancelled()) return;
        }
        if (!editable) {
            event.setCancelled(true);
        }
    }

    @RequiredArgsConstructor
    public static final class EventListener implements Listener {
        @EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
        private void onInventoryOpen(final InventoryOpenEvent event) {
            if (event.getInventory().getHolder() instanceof Gui gui) {
                gui.onInventoryOpen(event);
            } else if (event.getPlayer() instanceof Player player) {
                Gui gui = Gui.of(player);
                if (gui != null) gui.onInventoryOpen(event);
            }
        }

        @EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
        private void onInventoryClose(final InventoryCloseEvent event) {
            if (event.getInventory().getHolder() instanceof Gui gui) {
                gui.onInventoryClose(event);
            } else if (event.getPlayer() instanceof Player player) {
                Gui gui = Gui.of(player);
                if (gui != null) {
                    GUI_MAP.remove(player.getUniqueId());
                    gui.onInventoryClose(event);
                }
            }
        }

        @EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
        private void onInventoryClick(final InventoryClickEvent event) {
            if (event.getInventory().getHolder() instanceof Gui gui) {
                gui.onInventoryClick(event);
            } else if (event.getWhoClicked() instanceof Player player) {
                Gui gui = Gui.of(player);
                if (gui != null) gui.onInventoryClick(event);
            }
        }

        @EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
        private void onInventoryDrag(final InventoryDragEvent event) {
            if (event.getInventory().getHolder() instanceof Gui gui) {
                gui.onInventoryDrag(event);
            } else if (event.getWhoClicked() instanceof Player player) {
                Gui gui = Gui.of(player);
                if (gui != null) gui.onInventoryDrag(event);
            }
        }

        @EventHandler
        private void onPluginDisable(PluginDisableEvent event) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                Gui gui = Gui.of(player);
                if (gui != null && gui.plugin == event.getPlugin()) {
                    player.closeInventory();
                }
            }
            if (event.getPlugin() == MytemsPlugin.getInstance()) {
                Gui.disable();
            }
        }
    }

    public void open(Player player, Inventory theInventory) {
        player.closeInventory();
        this.inventory = theInventory;
        GUI_MAP.put(player.getUniqueId(), this);
        player.openInventory(inventory);
    }

    public void map(Player player, Inventory theInventory) {
        this.inventory = theInventory;
        GUI_MAP.put(player.getUniqueId(), this);
    }

    public static Gui of(Player player) {
        Gui mapped = GUI_MAP.get(player.getUniqueId());
        if (mapped != null) return mapped;
        InventoryView view = player.getOpenInventory();
        if (view == null) return null;
        Inventory topInventory = view.getTopInventory();
        if (topInventory == null) return null;
        InventoryHolder holder = topInventory.getHolder();
        if (!(holder instanceof Gui gui)) return null;
        return gui;
    }

    public static void enable() {
        Bukkit.getPluginManager().registerEvents(new EventListener(), MytemsPlugin.getInstance());
    }

    public static void disable() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            Gui gui = Gui.of(player);
            if (gui != null) player.closeInventory();
        }
    }

    private GuiOverlay.Builder getOverlayBuilder() {
        if (overlayBuilder == null) {
            overlayBuilder = new GuiOverlay.Builder(size);
        }
        return overlayBuilder;
    }

    public Gui layer(GuiOverlay overlay, TextColor color) {
        getOverlayBuilder().layer(overlay, color);
        return this;
    }

    public Gui highlight(int slot, TextColor color) {
        getOverlayBuilder().highlightSlot(slot, color);
        return this;
    }

    public Gui highlight(int x, int y, TextColor color) {
        getOverlayBuilder().highlightSlot(x, y, color);
        return this;
    }

    public Gui tab(int tab, TextColor fg, TextColor bg) {
        layer(GuiOverlay.TAB_BG, bg);
        layer(GuiOverlay.tab(tab), fg);
        return this;
    }

    public Component buildTitle() {
        return overlayBuilder != null
            ? overlayBuilder.title(title).build()
            : title;
    }
}
