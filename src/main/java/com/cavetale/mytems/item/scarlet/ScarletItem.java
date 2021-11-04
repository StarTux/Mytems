package com.cavetale.mytems.item.scarlet;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.gear.Equipment;
import com.cavetale.mytems.gear.GearItem;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import com.cavetale.mytems.gear.Slot;
import com.cavetale.mytems.util.Items;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

@RequiredArgsConstructor @Getter
public abstract class ScarletItem implements GearItem {
    protected static final TextColor TEXT_COLOR = TextColor.color(0xFF2400);
    protected final Mytems key;
    // Set by ctor:
    protected Component displayName;
    protected List<Component> baseText;
    protected ItemStack prototype;

    @Override
    public final void enable() {
        prototype.editMeta(meta -> {
                if (meta instanceof Repairable) {
                    ((Repairable) meta).setRepairCost(9999);
                    meta.setUnbreakable(true);
                }
                updateItemLore(meta);
                key.markItemMeta(meta);
            });
    }

    @Override
    public final void updateItemLore(ItemMeta meta, Player player, Equipment equipment, Slot slot) {
        List<Component> text = new ArrayList<>(baseText);
        List<SetBonus> setBonuses = ScarletItemSet.getInstance().getSetBonuses();
        if (!setBonuses.isEmpty()) {
            int count = equipment == null ? 0 : equipment.countSetItems(ScarletItemSet.instance);
            text.add(Component.empty());
            text.add(Component.text("Set Bonus [" + count + "]", slot != null ? TEXT_COLOR : NamedTextColor.DARK_GRAY));
            for (SetBonus setBonus : ScarletItemSet.instance.getSetBonuses()) {
                final int need = setBonus.getRequiredItemCount();
                final boolean has = count >= need;
                text.add(Component.text("(" + need + ") " + setBonus.getDescription(),
                                        has ? TEXT_COLOR : NamedTextColor.DARK_GRAY));
            }
        }
        Items.text(meta, text);
    }

    @Override
    public final ItemStack createItemStack() {
        return prototype.clone();
    }

    public static final class Helmet extends ScarletItem {
        public Helmet(final Mytems key) {
            super(key);
            displayName = Component.text("Scarlet Helmet", TEXT_COLOR);
            baseText = List.of(displayName);
            prototype = new ItemStack(key.material);
        }
    }

    public static final class Chestplate extends ScarletItem {
        public Chestplate(final Mytems key) {
            super(key);
            displayName = Component.text("Scarlet Chestplate", TEXT_COLOR);
            baseText = List.of(displayName);
            prototype = new ItemStack(key.material);
        }
    }

    public static final class Leggings extends ScarletItem {
        public Leggings(final Mytems key) {
            super(key);
            displayName = Component.text("Scarlet Leggings", TEXT_COLOR);
            baseText = List.of(displayName);
            prototype = new ItemStack(key.material);
        }
    }

    public static final class Boots extends ScarletItem {
        public Boots(final Mytems key) {
            super(key);
            displayName = Component.text("Scarlet Boots", TEXT_COLOR);
            baseText = List.of(displayName);
            prototype = new ItemStack(key.material);
        }
    }

    public static final class Sword extends ScarletItem {
        public Sword(final Mytems key) {
            super(key);
            displayName = Component.text("Scarlet Sword", TEXT_COLOR);
            baseText = List.of(displayName);
            prototype = new ItemStack(key.material);
        }
    }

    @Getter
    public static final class ScarletItemSet implements ItemSet {
        protected static ScarletItemSet instance;
        protected final String name = "Scarlet";
        protected final List<SetBonus> setBonuses = List.of();

        static ItemSet getInstance() {
            if (instance == null) {
                instance = new ScarletItemSet();
            }
            return instance;
        }
    }
}
