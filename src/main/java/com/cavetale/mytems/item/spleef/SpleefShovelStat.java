package com.cavetale.mytems.item.spleef;

import com.cavetale.core.struct.Vec2i;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.upgradable.DefaultUpgradableStatLevel;
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
import static com.cavetale.mytems.util.Items.iconize;
import static com.cavetale.mytems.util.Items.stack;
import static com.cavetale.mytems.util.Text.roman;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
@RequiredArgsConstructor
public enum SpleefShovelStat implements UpgradableStat {
    EFFICIENCY(Vec2i.of(3, 1), "Efficiency", () -> iconize(Material.GOLDEN_SHOVEL),
               List.of(new EfficiencyLevel(1, SpleefShovelTier.COPPER),
                       new EfficiencyLevel(2, SpleefShovelTier.COPPER),
                       new EfficiencyLevel(3, SpleefShovelTier.COPPER),
                       new EfficiencyLevel(4, SpleefShovelTier.COPPER),
                       new EfficiencyLevel(5, SpleefShovelTier.IRON)),
               List.of(), List.of()) {
        @Override public void removeFromItem(ItemMeta meta) {
            meta.removeEnchant(Enchantment.EFFICIENCY);
        }

        @Override public void applyToItem(ItemMeta meta, int upgradeLevel) {
            meta.addEnchant(Enchantment.EFFICIENCY, upgradeLevel, true);
        }
    },
    // Range, Left
    RANGE(Vec2i.of(5, 1), "Range", Mytems.YARDSTICK::createIcon,
          List.of(new RangeLevel(1, SpleefShovelTier.COPPER),
                  new RangeLevel(2, SpleefShovelTier.IRON),
                  new RangeLevel(3, SpleefShovelTier.GOLD),
                  new RangeLevel(4, SpleefShovelTier.DIAMOND)),
          List.of(EFFICIENCY), List.of()),
    FLOAT(Vec2i.of(7, 1), "Floating", () -> iconize(Material.SCAFFOLDING),
          List.of(new DefaultUpgradableStatLevel(1, () -> iconize(Material.SCAFFOLDING),
                                                 List.of(text("Falling blocks will"),
                                                         text("not fall when broken"),
                                                         text("by range.")),
                                                 SpleefShovelTier.GOLD)),
          List.of(RANGE), List.of()),
    BRUSH(Vec2i.of(5, 3), "Brush", () -> iconize(Material.BRUSH),
          List.of(new DefaultUpgradableStatLevel(1, () -> iconize(Material.BRUSH),
                                                 List.of(text("Suspicious sand and"),
                                                         text("gravel are left"),
                                                         text("intact when broken"),
                                                         text("by range.")),
                                                 SpleefShovelTier.DIAMOND)),
          List.of(RANGE), List.of()),
    // Fortune and Silk, Left
    SILK_TOUCH(Vec2i.of(1, 1), "Silk Touch", () -> iconize(Material.FEATHER),
               List.of(new SilkTouchLevel(1, SpleefShovelTier.COPPER)),
               List.of(EFFICIENCY), List.of()) {
        @Override public void removeFromItem(ItemMeta meta) {
            meta.removeEnchant(Enchantment.SILK_TOUCH);
        }

        @Override public void applyToItem(ItemMeta meta, int upgradeLevel) {
            meta.addEnchant(Enchantment.SILK_TOUCH, upgradeLevel, true);
        }
    },
    FORTUNE(Vec2i.of(1, 3), "Fortune", () -> iconize(Material.DIAMOND),
            List.of(new FortuneLevel(1, SpleefShovelTier.IRON),
                    new FortuneLevel(2, SpleefShovelTier.IRON),
                    new FortuneLevel(3, SpleefShovelTier.GOLD)),
            List.of(SILK_TOUCH), List.of()) {
        @Override public void removeFromItem(ItemMeta meta) {
            meta.removeEnchant(Enchantment.FORTUNE);
        }

        @Override public void applyToItem(ItemMeta meta, int upgradeLevel) {
            meta.addEnchant(Enchantment.FORTUNE, upgradeLevel, true);
        }
    },
    ;

    private final String key = name().toLowerCase();
    private final Vec2i guiSlot;
    private final String displayName;
    private final Supplier<ItemStack> iconSupplier;
    private final List<? extends UpgradableStatLevel> levels;
    private final List<SpleefShovelStat> dependencies;
    private final List<SpleefShovelStat> completeDependencies;

    @Value
    public static final class EfficiencyLevel implements UpgradableStatLevel {
        private final int level;
        private final SpleefShovelTier requiredTier;

        @Override
        public ItemStack getIcon() {
            return stack(iconize(Material.GOLDEN_PICKAXE), level);
        }

        @Override
        public List<Component> getDescription() {
            return List.of(text("Add Efficiency " + roman(level)));
        }
    }

    @Value
    public static final class RangeLevel implements UpgradableStatLevel {
        private final int level;
        private final SpleefShovelTier requiredTier;

        @Override
        public ItemStack getIcon() {
            return Mytems.YARDSTICK.createIcon();
        }

        @Override
        public List<Component> getDescription() {
            return List.of(text("Increase range to " + level));
        }
    }

    @Value
    public static final class SilkTouchLevel implements UpgradableStatLevel {
        private final int level;
        private final SpleefShovelTier requiredTier;

        @Override
        public ItemStack getIcon() {
            return iconize(new ItemStack(Material.FEATHER, level));
        }

        @Override
        public List<Component> getDescription() {
            return List.of(text("Add Silk Touch " + roman(level)));
        }
    }

    @Value
    public static final class FortuneLevel implements UpgradableStatLevel {
        private final int level;
        private final SpleefShovelTier requiredTier;

        @Override
        public ItemStack getIcon() {
            return iconize(new ItemStack(Material.DIAMOND, level));
        }

        @Override
        public List<Component> getDescription() {
            return List.of(text("Add Fortune " + roman(level)));
        }
    }

    @Override
    public Component getTitle() {
        return text(displayName);
    }

    @Override
    public final ItemStack getIcon() {
        return iconSupplier.get();
    }

    @Override
    public final List<SpleefShovelStat> getConflicts() {
        return List.of();
    }
}
