package com.cavetale.mytems.util;

import com.cavetale.mytems.MytemsPlugin;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
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

public final class Gui implements InventoryHolder {
    public static final int OUTSIDE = -999;
    public static final int OFF_HAND = 40;
    private Inventory inventory;
    private Map<Integer, Slot> slots = new HashMap<>();
    private Consumer<InventoryCloseEvent> onClose = null;
    private Consumer<InventoryOpenEvent> onOpen = null;
    @Getter @Setter private boolean editable = false;
    @Getter private int size = 3 * 9;
    @Getter private Component title = Component.empty();
    @Getter @Setter private InventoryType inventoryType = null;
    protected boolean locked = false;
    @Getter @Setter protected int lockedSlot = -1;

    @RequiredArgsConstructor @AllArgsConstructor
    private static final class Slot {
        final int index;
        ItemStack item;
        Consumer<InventoryClickEvent> onClick;
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
                ? Bukkit.getServer().createInventory(this, size, title)
                : Bukkit.getServer().createInventory(this, inventoryType, title);
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

    public Gui clear() {
        if (inventory != null) inventory.clear();
        slots.clear();
        onOpen = null;
        onClose = null;
        return this;
    }

    void onInventoryOpen(InventoryOpenEvent event) {
        if (onOpen != null) {
            Bukkit.getScheduler().runTask(MytemsPlugin.getInstance(), () -> onOpen.accept(event));
        }
    }

    void onInventoryClose(InventoryCloseEvent event) {
        if (onClose != null) {
            onClose.accept(event);
        }
    }

    void onInventoryClick(InventoryClickEvent event) {
        if (!editable) {
            event.setCancelled(true);
        } else if (lockedSlot >= 0) {
            if (event.getView().getBottomInventory().equals(event.getView().getInventory(event.getRawSlot()))) {
                if (event.getSlot() == lockedSlot) {
                    event.setCancelled(true);
                }
            }
            if (event.getClick() == ClickType.NUMBER_KEY) {
                if (lockedSlot == event.getHotbarButton()) {
                    event.setCancelled(true);
                }
            } else if (event.getClick() == ClickType.SWAP_OFFHAND) {
                Bukkit.getScheduler().runTaskLater(MytemsPlugin.getInstance(), () -> ((Player) event.getWhoClicked()).updateInventory(), 10L);
                if (lockedSlot == OFF_HAND) {
                    event.setCancelled(true);
                }
            }
            return;
        }
        if (locked) return;
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() != null && !inventory.equals(event.getClickedInventory())) {
            return;
        }
        Slot slot = slots.get(event.getSlot());
        if (slot != null && slot.onClick != null) {
            locked = true;
            Bukkit.getScheduler().runTask(MytemsPlugin.getInstance(), () -> {
                    locked = false;
                    slot.onClick.accept(event);
                });
        }
    }

    void onInventoryDrag(InventoryDragEvent event) {
        if (!editable) {
            event.setCancelled(true);
        } else if (lockedSlot >= 0) {
            for (int raw : event.getRawSlots()) {

                if (event.getView().getBottomInventory().equals(event.getView().getInventory(raw))) {
                    if (lockedSlot == event.getView().convertSlot(raw)) {
                        event.setCancelled(true);
                    }
                }
            }
            return;
        }
    }

    @RequiredArgsConstructor
    public static final class EventListener implements Listener {
        @EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
        void onInventoryOpen(final InventoryOpenEvent event) {
            if (event.getInventory().getHolder() instanceof Gui) {
                ((Gui) event.getInventory().getHolder()).onInventoryOpen(event);
            }
        }

        @EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
        void onInventoryClose(final InventoryCloseEvent event) {
            if (event.getInventory().getHolder() instanceof Gui) {
                ((Gui) event.getInventory().getHolder()).onInventoryClose(event);
            }
        }

        @EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
        void onInventoryClick(final InventoryClickEvent event) {
            if (event.getInventory().getHolder() instanceof Gui) {
                ((Gui) event.getInventory().getHolder()).onInventoryClick(event);
            }
        }

        @EventHandler(ignoreCancelled = false, priority = EventPriority.LOWEST)
        void onInventoryDrag(final InventoryDragEvent event) {
            if (event.getInventory().getHolder() instanceof Gui) {
                ((Gui) event.getInventory().getHolder()).onInventoryDrag(event);
            }
        }

        @EventHandler
        void onPluginDisable(PluginDisableEvent event) {
            if (event.getPlugin() == MytemsPlugin.getInstance()) {
                Gui.disable();
            }
        }
    }

    public static Gui of(Player player) {
        InventoryView view = player.getOpenInventory();
        if (view == null) return null;
        Inventory topInventory = view.getTopInventory();
        if (topInventory == null) return null;
        InventoryHolder holder = topInventory.getHolder();
        if (!(holder instanceof Gui)) return null;
        Gui gui = (Gui) holder;
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
}
