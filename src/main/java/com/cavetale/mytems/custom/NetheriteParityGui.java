package com.cavetale.mytems.custom;

import com.cavetale.core.font.GuiOverlay;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Gui;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.MytemsPlugin.plugin;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor
public final class NetheriteParityGui {
    private final Player player;
    private final Gui gui = new Gui(plugin()).rows(4);
    private CraftingSlot templateSlot = new CraftingSlot(19);
    private CraftingSlot armorSlot = new CraftingSlot(20);
    private CraftingSlot kittyCoinSlot = new CraftingSlot(21);
    private final int resultSlot = 25;

    public void open() {
        gui.title(GuiOverlay.CRAFTING_PARITY.builder(gui.getSize(), WHITE)
                  .title(text("Netherite Parity"))
                  .build());
        gui.onClick(this::onClick);
        gui.setItem(resultSlot, null, this::onClickResultSlot);
        gui.open(player);
    }

    private void onClick(InventoryClickEvent event) {
        if (!event.getClick().isLeftClick()) return;
        System.out.println(event.getEventName() + " " + event.getClickedInventory().equals(player.getInventory()));
        if (!event.getClickedInventory().equals(player.getInventory())) return;
        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType().isAir()) return;
        CraftingSlot slot;
        if (item.getType() == Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE) {
            slot = templateSlot;
        } else if (Mytems.KITTY_COIN.isItem(item)) {
            slot = kittyCoinSlot;
        } else if (checkInputItem(item)) {
            slot = armorSlot;
        } else {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 0.5f);
            return;
        }
        slot.set(event.getSlot(), item);
        slot.update(gui);
        updateResultSlot();
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
    }

    private boolean checkInputItem(ItemStack item) {
        if (item == null || item.getType().isAir() || item.getAmount() != 1) return false;
        if (Mytems.forItem(item) != null) return false;
        if (!NetheriteParity.getUpgradableArmor().contains(item.getType())) return false;
        if (NetheriteParity.isUpgraded(item)) return false;
        return true;
    }

    private boolean checkRecipe() {
        return templateSlot.checkConsistency(player)
            && templateSlot.getItemCopy().getType() == Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE
            && armorSlot.checkConsistency(player)
            && kittyCoinSlot.checkConsistency(player)
            && Mytems.KITTY_COIN.isItem(kittyCoinSlot.getItemCopy())
            && checkInputItem(armorSlot.getItemCopy());
    }

    private void updateResultSlot() {
        if (!checkRecipe()) {
            gui.setItem(resultSlot, null, this::onClickResultSlot);
        } else {
            gui.setItem(resultSlot, craftResultItem(), this::onClickResultSlot);
        }
    }

    private ItemStack craftResultItem() {
        ItemStack item = armorSlot.getItemCopy().clone();
        if (!NetheriteParity.upgradeItem(item)) return null;
        return item;
    }

    private void onClickResultSlot(InventoryClickEvent event) {
        if (!checkRecipe()) {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 0.5f);
            return;
        }
        armorSlot.change(player, craftResultItem());
        for (CraftingSlot slot : List.of(templateSlot, kittyCoinSlot)) {
            slot.subtract(player);
            slot.unset();
            slot.update(gui);
        }
        armorSlot.unset();
        armorSlot.update(gui);
        gui.setItem(resultSlot, null, this::onClickResultSlot);
        player.playSound(player.getLocation(), Sound.BLOCK_SMITHING_TABLE_USE, 1f, 0.5f);
    }
}
