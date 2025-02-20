package com.cavetale.mytems.item.treechopper;

import com.cavetale.core.struct.Vec2i;
import com.cavetale.mytems.Mytems;
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

@Getter
@RequiredArgsConstructor
public enum TreeChopperStat implements UpgradableStat {
    CHOP(Vec2i.of(4, 2), text("Chopping"), () -> new ItemStack(Material.OAK_LOG),
         List.of(new ChopLevel(1, () -> new ItemStack(Material.OAK_LOG),
                               TreeChopperTier.IRON),
                 new ChopLevel(2, () -> new ItemStack(Material.OAK_LOG),
                               TreeChopperTier.IRON),
                 new ChopLevel(3, () -> new ItemStack(Material.OAK_LOG),
                               TreeChopperTier.IRON),
                 new ChopLevel(4, () -> new ItemStack(Material.OAK_LOG),
                               TreeChopperTier.IRON),
                 new ChopLevel(5, () -> new ItemStack(Material.OAK_LOG),
                               TreeChopperTier.IRON),
                 new ChopLevel(6, () -> new ItemStack(Material.OAK_LOG),
                               TreeChopperTier.IRON)),
         List.of(), List.of()),
    // Leaves, Left
    LEAF(Vec2i.of(2, 2), text("Leaf Blower"), () -> new ItemStack(Material.OAK_LEAVES),
         List.of(new LeafLevel(1, () -> new ItemStack(Material.OAK_LEAVES),
                               TreeChopperTier.IRON),
                 new LeafLevel(2, () -> new ItemStack(Material.OAK_LEAVES),
                               TreeChopperTier.IRON),
                 new LeafLevel(3, () -> new ItemStack(Material.OAK_LEAVES),
                               TreeChopperTier.IRON),
                 new LeafLevel(4, () -> new ItemStack(Material.OAK_LEAVES),
                               TreeChopperTier.IRON)),
         List.of(CHOP), List.of()),
    FORTUNE(Vec2i.of(2, 0), text("Fortune"), () -> Mytems.DICE.createIcon(),
            List.of(new FortuneLevel(1, Mytems.DICE::createIcon,
                                     TreeChopperTier.IRON),
                    new FortuneLevel(2, Mytems.DICE::createIcon,
                                     TreeChopperTier.IRON),
                    new FortuneLevel(3, Mytems.DICE::createIcon,
                                     TreeChopperTier.IRON)),
            List.of(TreeChopperStat.LEAF), List.of()) {
        @Override public void removeFromItem(ItemMeta meta) {
            meta.removeEnchant(Enchantment.FORTUNE);
        }

        @Override public void applyToItem(ItemMeta meta, int upgradeLevel) {
            meta.addEnchant(Enchantment.FORTUNE, upgradeLevel, true);
        }
    },
    SILK(Vec2i.of(2, 4), text("Shears"), () -> new ItemStack(Material.SHEARS),
         List.of(new TreeChopperStatLevel(1, () -> new ItemStack(Material.SHEARS, 1),
                                          List.of(text("Chopped leaves drop"),
                                                  text("as blocks")),
                                          TreeChopperTier.IRON),
                 new TreeChopperStatLevel(2, () -> new ItemStack(Material.SHEARS, 2),
                                          List.of(text("Chopped leaves and"),
                                                  text("vines drop as blocks")),
                                          TreeChopperTier.IRON)),
         List.of(TreeChopperStat.LEAF), List.of()) {
        @Override public void removeFromItem(ItemMeta meta) {
            meta.removeEnchant(Enchantment.SILK_TOUCH);
        }

        @Override public void applyToItem(ItemMeta meta, int upgradeLevel) {
            meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        }
    },
    REPLANT(Vec2i.of(0, 2), text("Replant"), () -> new ItemStack(Material.OAK_SAPLING),
            List.of(new TreeChopperStatLevel(1, () -> new ItemStack(Material.OAK_SAPLING),
                                             List.of(text("Chopped trees are"),
                                                     text("instantly replanted")),
                                             TreeChopperTier.GOLD)),
            List.of(TreeChopperStat.LEAF), List.of()),
    PICKUP(Vec2i.of(4, 0), text("Magnet"), Mytems.MAGNET::createIcon,
           List.of(new TreeChopperStatLevel(1, Mytems.MAGNET::createIcon,
                                            List.of(text("Pick up broken blocks")),
                                            TreeChopperTier.IRON)),
           List.of(CHOP), List.of()),
    // Speed, Right
    SPEED(Vec2i.of(6, 2), text("Speed"), () -> new ItemStack(Material.SUGAR),
          List.of(new SpeedLevel(1, () -> new ItemStack(Material.SUGAR),
                                 TreeChopperTier.IRON),
                  new SpeedLevel(2, () -> new ItemStack(Material.SUGAR, 2),
                                 TreeChopperTier.IRON),
                  new SpeedLevel(3, () -> new ItemStack(Material.SUGAR, 3),
                                 TreeChopperTier.IRON),
                  new SpeedLevel(4, () -> new ItemStack(Material.SUGAR, 4),
                                 TreeChopperTier.IRON),
                  new SpeedLevel(5, () -> new ItemStack(Material.SUGAR, 5),
                                 TreeChopperTier.IRON)),
          List.of(TreeChopperStat.CHOP), List.of()),
    EFFICIENCY(Vec2i.of(7, 2), text("Efficiency"), () -> new ItemStack(Material.GOLDEN_AXE),
               List.of(new EfficiencyLevel(1, TreeChopperTier.IRON),
                       new EfficiencyLevel(2, TreeChopperTier.IRON),
                       new EfficiencyLevel(3, TreeChopperTier.IRON),
                       new EfficiencyLevel(4, TreeChopperTier.IRON),
                       new EfficiencyLevel(5, TreeChopperTier.IRON)),
               List.of(), List.of(TreeChopperStat.SPEED)) {
        @Override public void removeFromItem(ItemMeta meta) {
            meta.removeEnchant(Enchantment.EFFICIENCY);
        }

        @Override public void applyToItem(ItemMeta meta, int upgradeLevel) {
            meta.addEnchant(Enchantment.EFFICIENCY, upgradeLevel, true);
        }
    },
    PUNCH(Vec2i.of(8, 2), text("Punching"), () -> new ItemStack(Material.GUNPOWDER),
          List.of(new TreeChopperStatLevel(1, () -> new ItemStack(Material.GUNPOWDER),
                                           List.of(text("Insta chop the root"),
                                                   text("block")),
                                           TreeChopperTier.GOLD)),
          List.of(), List.of(TreeChopperStat.EFFICIENCY)),
    MUSHROOM(Vec2i.of(6, 0), text("Mushrooms"), () -> new ItemStack(Material.RED_MUSHROOM),
             List.of(new TreeChopperStatLevel(1, () -> new ItemStack(Material.RED_MUSHROOM),
                                              List.of(text("Chop Huge Mushrooms")),
                                              TreeChopperTier.GOLD)),
             List.of(), List.of(SPEED)),
    FUNGI(Vec2i.of(7, 0), text("Fungi"), () -> new ItemStack(Material.CRIMSON_FUNGUS),
          List.of(new TreeChopperStatLevel(1, () -> new ItemStack(Material.CRIMSON_FUNGUS),
                                           List.of(text("Chop Crimson Fungus"),
                                                   text("and Warped Fungus")),
                                           TreeChopperTier.GOLD)),
          List.of(MUSHROOM), List.of()),
    SHROOMLIGHT(Vec2i.of(8, 0), text("Shroomlights"), () -> new ItemStack(Material.SHROOMLIGHT),
                List.of(new TreeChopperStatLevel(1, () -> new ItemStack(Material.SHROOMLIGHT),
                                                 List.of(text("Pick up Shroomlights"),
                                                         text("when chopping Fungi")),
                                                 TreeChopperTier.GOLD)),
                List.of(FUNGI), List.of()),
    // Mangrove, Down
    MANGROVE(Vec2i.of(4, 4), text("Mangroves"), () -> new ItemStack(Material.MANGROVE_PROPAGULE),
             List.of(new TreeChopperStatLevel(1, () -> new ItemStack(Material.MANGROVE_PROPAGULE),
                                              List.of(text("Chop Mangrove Trees")),
                                              TreeChopperTier.IRON)),
             List.of(CHOP), List.of()),
    ;

    @Value
    public static final class TreeChopperStatLevel implements UpgradableStatLevel {
        private final int level;
        private final Supplier<ItemStack> iconSupplier;
        private final List<Component> description;
        private final TreeChopperTier requiredTier;

        @Override
        public ItemStack getIcon() {
            return iconSupplier.get();
        }
    }

    @Value
    public static final class ChopLevel implements UpgradableStatLevel {
        private final int level;
        private final Supplier<ItemStack> iconSupplier;
        private final TreeChopperTier requiredTier;

        @Override
        public ItemStack getIcon() {
            return iconize(stack(iconSupplier.get(), level));
        }

        @Override
        public List<Component> getDescription() {
            final int logs = TreeChopperTag.getMaxLogBlocks(level);
            return List.of(text("Chop trees with up"),
                           text("to " + logs + " logs"));
        }
    }

    @Value
    public static final class LeafLevel implements UpgradableStatLevel {
        private final int level;
        private final Supplier<ItemStack> iconSupplier;
        private final TreeChopperTier requiredTier;

        @Override
        public ItemStack getIcon() {
            return iconize(iconSupplier.get());
        }

        @Override
        public List<Component> getDescription() {
            final int leaves = TreeChopperTag.getMaxLeafBlocks(level);
            return List.of(text("Chop up to " + leaves),
                           text("attached leaves"));
        }
    }

    @Value
    public static final class FortuneLevel implements UpgradableStatLevel {
        private final int level;
        private final Supplier<ItemStack> iconSupplier;
        private final TreeChopperTier requiredTier;

        @Override
        public ItemStack getIcon() {
            return iconize(iconSupplier.get());
        }

        @Override
        public List<Component> getDescription() {
            final String roman = roman(level);
            return List.of(text("Bonus drops from"),
                           text("chopped leaves"),
                           text("like Fortune " + roman));
        }
    }

    @Value
    public static final class SpeedLevel implements UpgradableStatLevel {
        private final int level;
        private final Supplier<ItemStack> iconSupplier;
        private final TreeChopperTier requiredTier;

        @Override
        public ItemStack getIcon() {
            return iconize(iconSupplier.get());
        }

        @Override
        public List<Component> getDescription() {
            final int speed = TreeChopperTag.getChoppingSpeed(level);
            return List.of(text("Chop trees " + speed + " times"),
                           text("as fast"));
        }
    }

    @Value
    public static final class EfficiencyLevel implements UpgradableStatLevel {
        private final int level;
        private final TreeChopperTier requiredTier;

        @Override
        public ItemStack getIcon() {
            return iconize(stack(Material.GOLDEN_AXE, level));
        }

        @Override
        public List<Component> getDescription() {
            return List.of(text("Add Efficiency " + roman(level)));
        }
    }

    @Value
    public static final class EnchantLevel implements UpgradableStatLevel {
        private final int level;
        private final Supplier<ItemStack> iconSupplier;
        private final TreeChopperTier requiredTier;

        @Override
        public ItemStack getIcon() {
            return iconize(iconSupplier.get());
        }

        @Override
        public List<Component> getDescription() {
            if (level == 0) {
                return List.of(text("Chopped logs and"),
                               text("leaves drop exp"));
            } else {
                return List.of(text("Chopped logs and"),
                               text("leaves " + level + " as"),
                               text("many exp"));
            }
        }
    }

    private final String key;
    private final Vec2i guiSlot;
    private final Component title;
    private final Supplier<ItemStack> iconSupplier;
    private final List<UpgradableStatLevel> levels;
    private final List<TreeChopperStat> dependencies;
    private final List<TreeChopperStat> completeDependencies;

    TreeChopperStat(final Vec2i guiSlot,
                    final Component title,
                    final Supplier<ItemStack> iconSupplier,
                    final List<UpgradableStatLevel> levels,
                    final List<TreeChopperStat> dependencies,
                    final List<TreeChopperStat> completeDependencies) {
        this.key = name().toLowerCase();
        this.guiSlot = guiSlot;
        this.title = title;
        this.iconSupplier = iconSupplier;
        this.levels = levels;
        this.dependencies = dependencies;
        this.completeDependencies = completeDependencies;
    }

    public static TreeChopperStat ofKey(String key) {
        for (TreeChopperStat upgrade : TreeChopperStat.values()) {
            if (key.equals(upgrade.key)) return upgrade;
        }
        return null;
    }

    @Override
    public ItemStack getIcon() {
        return iconSupplier.get();
    }

    @Override
    public List<TreeChopperStat> getConflicts() {
        return List.of();
    }
}
