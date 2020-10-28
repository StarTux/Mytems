package com.cavetale.mytems.item;

import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.gear.Equipment;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.util.Text;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Implementors must set displayName, baseLore, and prototype within
 * the enable() method.
 * The prototype is the base item, before its lore is updated.
 */
abstract class AculaItem implements GearItem {
    protected final MytemsPlugin plugin;
    @Getter protected BaseComponent[] displayName;
    protected List<BaseComponent[]> baseLore;
    protected ItemStack prototype;

    AculaItem(final MytemsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public final void updateItemLore(ItemMeta meta, Player player, Equipment equipment, Equipment.Slot slot) {
        meta.setDisplayNameComponent(displayName);
        List<BaseComponent[]> lore = new ArrayList<>(baseLore);
        ItemSet itemSet = getItemSet();
        List<SetBonus> setBonuses = itemSet.getSetBonuses();
        if (!setBonuses.isEmpty()) {
            int count = equipment == null ? 0 : equipment.countSetItems(itemSet);
            lore.add(Text.toBaseComponents(""));
            lore.add(creepify("Set Bonus [" + count + "]", slot != null));
            for (SetBonus setBonus : itemSet.getSetBonuses()) {
                int need = setBonus.getRequiredItemCount();
                String description = count >= need
                    ? (ChatColor.DARK_RED + "(" + need + ") " + ChatColor.RED
                       + setBonus.getDescription().replace(ChatColor.RESET.toString(), ChatColor.RED.toString()))
                    : (ChatColor.DARK_GRAY + "(" + need + ") " + ChatColor.GRAY
                       + setBonus.getDescription().replace(ChatColor.RESET.toString(), ChatColor.GRAY.toString()));
                lore.addAll(Text.toBaseComponents(Text.wrapLines(description, Text.ITEM_LORE_WIDTH)));
            }
        }
        meta.setLoreComponents(lore);
    }

    @Override
    public ItemSet getItemSet() {
        return AculaItemSet.getInstance(plugin);
    }

    protected BaseComponent[] creepify(String in, boolean bold) {
        int len = in.length();
        int iter = 255 / len * 3 / 4;
        ComponentBuilder cb = new ComponentBuilder();
        for (int i = 0; i < len; i += 1) {
            cb.append(in.substring(i, i + 1)).color(ChatColor.of(new Color(255 - iter * i, 0, 0)));
            if (bold) cb.bold(true);
        }
        return cb.create();
    }

    @Override
    public ItemStack getItem() {
        ItemStack itemStack = prototype.clone();
        ItemMeta meta = itemStack.getItemMeta();
        updateItemLore(meta);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
