package com.cavetale.mytems.item.acula;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Repairable;
import static com.cavetale.core.font.Unicode.tiny;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

/**
 * Implementors must set displayName, baseLore, and prototype within
 * the enable() method.
 * The prototype is the base item, before its lore is updated.
 */
@RequiredArgsConstructor
abstract class AculaItem implements GearItem {
    @Getter protected final Mytems key;
    @Getter protected Component displayName;
    @Getter protected final List<Component> baseLore = new ArrayList<>();
    protected ItemStack prototype;

    @Override
    public final void enable() {
        displayName = creepify(getRawDisplayName());
        prototype = getRawItemStack();
        baseLore.addAll(Text.wrapLore(tiny(getDescription().toLowerCase()), t -> t.color(RED)));
        if (getUsage() != null) {
            baseLore.add(empty());
            baseLore.addAll(getUsage());
        }
        prototype.editMeta(meta -> {
                Items.text(meta, createTooltip());
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                if (meta instanceof Repairable repairable) {
                    repairable.setRepairCost(9999);
                    meta.setUnbreakable(true);
                }
                key.markItemMeta(meta);
            });
    }

    protected abstract String getDescription();

    protected List<Component> getUsage() {
        return null;
    }

    protected abstract String getRawDisplayName();

    protected abstract ItemStack getRawItemStack();

    @Override
    public ItemSet getItemSet() {
        return AculaItemSet.getInstance();
    }

    protected Component creepify(String in) {
        int len = in.length();
        int iter = 255 / len * 3 / 4;
        TextComponent.Builder cb = text();
        for (int i = 0; i < len; i += 1) {
            cb.append(text(in.substring(i, i + 1), TextColor.color(255 - iter * i, 0, 0)));
        }
        return cb.build();
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }
}
