package com.cavetale.mytems.item.acula;

import com.cavetale.mytems.MytemPersistenceFlag;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.gear.Equipment;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.gear.Slot;
import com.cavetale.mytems.util.Text;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Implementors must set displayName, baseLore, and prototype within
 * the enable() method.
 * The prototype is the base item, before its lore is updated.
 */
@RequiredArgsConstructor
abstract class AculaItem implements GearItem {
    @Getter protected final Mytems key;
    @Getter protected Component displayName;
    protected List<Component> baseLore;
    protected ItemStack prototype;

    @Override
    public final void updateItemLore(ItemMeta meta, Player player, Equipment equipment, Slot slot) {
        meta.displayName(displayName);
        List<Component> lore = new ArrayList<>(baseLore);
        ItemSet itemSet = getItemSet();
        List<SetBonus> setBonuses = itemSet.getSetBonuses();
        if (!setBonuses.isEmpty()) {
            int count = equipment == null ? 0 : equipment.countSetItems(itemSet);
            lore.add(Component.empty());
            lore.add(creepify("Set Bonus [" + count + "]", slot != null));
            for (SetBonus setBonus : itemSet.getSetBonuses()) {
                int need = setBonus.getRequiredItemCount();
                String description = "(" + need + ") " + setBonus.getDescription();
                TextColor color = count >= need ? NamedTextColor.RED : NamedTextColor.DARK_GRAY;
                lore.addAll(Text.wrapLore(description, c -> c.color(color)));
            }
        }
        meta.lore(lore);
    }

    @Override
    public ItemSet getItemSet() {
        return AculaItemSet.getInstance();
    }

    protected Component creepify(String in, boolean bold) {
        int len = in.length();
        int iter = 255 / len * 3 / 4;
        Component component = bold ? Component.empty().decorate(TextDecoration.BOLD) : Component.empty();
        for (int i = 0; i < len; i += 1) {
            component = component.append(Component.text(in.substring(i, i + 1)).color(TextColor.color(255 - iter * i, 0, 0)));
        }
        return component;
    }

    @Override
    public ItemStack createItemStack() {
        ItemStack itemStack = prototype.clone();
        ItemMeta meta = itemStack.getItemMeta();
        updateItemLore(meta);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public Set<MytemPersistenceFlag> getMytemPersistenceFlags() {
        return EnumSet.of(MytemPersistenceFlag.DURABILITY);
    }
}
