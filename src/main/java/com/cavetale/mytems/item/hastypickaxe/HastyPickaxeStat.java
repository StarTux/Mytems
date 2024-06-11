package com.cavetale.mytems.item.hastypickaxe;

import com.cavetale.core.struct.Vec2i;
import com.cavetale.mytems.item.upgradable.UpgradableItemTier;
import com.cavetale.mytems.item.upgradable.UpgradableStat;
import com.cavetale.mytems.item.upgradable.UpgradableStatLevel;
import java.util.List;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
@RequiredArgsConstructor
public enum HastyPickaxeStat implements UpgradableStat {
    EFFICIENCY(Vec2i.of(4, 2), text("Efficiency", GRAY), () -> new ItemStack(Material.STONE),
               List.of(new Level(1, HastyPickaxeTier.COPPER, () -> new ItemStack(Material.FEATHER, 1), List.of(text("Add Efficiency I"))),
                       new Level(2, HastyPickaxeTier.COPPER, () -> new ItemStack(Material.FEATHER, 2), List.of(text("Add Efficiency II"))),
                       new Level(3, HastyPickaxeTier.COPPER, () -> new ItemStack(Material.FEATHER, 3), List.of(text("Add Efficiency III"))),
                       new Level(4, HastyPickaxeTier.COPPER, () -> new ItemStack(Material.FEATHER, 4), List.of(text("Add Efficiency IV"))),
                       new Level(5, HastyPickaxeTier.COPPER, () -> new ItemStack(Material.FEATHER, 5), List.of(text("Add Efficiency V")))),
               List.of(), List.of()) {
        @Override public void removeFromItem(ItemMeta meta) {
            meta.removeEnchant(Enchantment.EFFICIENCY);
        }

        @Override public void applyToItem(ItemMeta meta, int upgradeLevel) {
            meta.addEnchant(Enchantment.EFFICIENCY, upgradeLevel, true);
        }
    },
    FORTUNE(Vec2i.of(5, 2), text("Fortune", GOLD), () -> new ItemStack(Material.DIAMOND_ORE),
            List.of(new Level(1, HastyPickaxeTier.COPPER, () -> new ItemStack(Material.DIAMOND, 1), List.of(text("Add Fortune I"))),
                    new Level(2, HastyPickaxeTier.COPPER, () -> new ItemStack(Material.DIAMOND, 2), List.of(text("Add Fortune II"))),
                    new Level(3, HastyPickaxeTier.GOLD, () -> new ItemStack(Material.DIAMOND, 3), List.of(text("Add Fortune III"))),
                    new Level(4, HastyPickaxeTier.DIAMOND, () -> new ItemStack(Material.DIAMOND, 4), List.of(text("Add Fortune IV")))),
            List.of(HastyPickaxeStat.EFFICIENCY), List.of()) {

        @Override public void removeFromItem(ItemMeta meta) {
            meta.removeEnchant(Enchantment.FORTUNE);
        }

        @Override public void applyToItem(ItemMeta meta, int upgradeLevel) {
            meta.addEnchant(Enchantment.FORTUNE, upgradeLevel, true);
        }
    }
    ;

    @Value
    public static final class Level implements UpgradableStatLevel {
        private final int level;
        private final UpgradableItemTier requiredTier;
        private final Supplier<ItemStack> iconSupplier;
        private final List<Component> description;

        @Override
        public ItemStack getIcon() {
            return iconSupplier.get();
        }
    }

    private final Vec2i guiSlot;
    private final Component title;
    private final Supplier<ItemStack> iconSupplier;
    private final List<Level> levels;
    private final List<HastyPickaxeStat> dependencies;
    private final List<HastyPickaxeStat> conflicts;

    @Override
    public String getKey() {
        return name().toLowerCase();
    }

    /**
     * Get the base icon which is displayed in the GUI if no level has
     * been unlocked.
     */
    @Override
    public ItemStack getIcon() {
        return iconSupplier.get();
    }
}
