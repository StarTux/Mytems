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
    LEAF(Vec2i.of(3, 2), text("Leaf Blower"), () -> new ItemStack(Material.OAK_LEAVES),
         List.of(new LeafLevel(1, () -> new ItemStack(Material.OAK_LEAVES),
                               TreeChopperTier.IRON),
                 new LeafLevel(2, () -> new ItemStack(Material.OAK_LEAVES),
                               TreeChopperTier.IRON),
                 new LeafLevel(3, () -> new ItemStack(Material.OAK_LEAVES),
                               TreeChopperTier.IRON),
                 new LeafLevel(4, () -> new ItemStack(Material.OAK_LEAVES),
                               TreeChopperTier.IRON)),
         List.of(), List.of()),
    FORTUNE(Vec2i.of(3, 1), text("Fortune"), () -> Mytems.DICE.createIcon(),
            List.of(new FortuneLevel(1, Mytems.DICE::createIcon,
                                     TreeChopperTier.IRON),
                    new FortuneLevel(2, Mytems.DICE::createIcon,
                                     TreeChopperTier.IRON),
                    new FortuneLevel(3, Mytems.DICE::createIcon,
                                     TreeChopperTier.IRON)),
            List.of(TreeChopperStat.LEAF), List.of()) {

        @Override public List<TreeChopperStat> getConflicts() {
            return List.of(SILK);
        }
    },
    SILK(Vec2i.of(3, 3), text("Shears"), () -> new ItemStack(Material.SHEARS),
         List.of(new TreeChopperStatLevel(1, () -> new ItemStack(Material.SHEARS, 1),
                                          List.of(text("Chopped leaves drop"),
                                                  text("as blocks")),
                                          TreeChopperTier.IRON),
                 new TreeChopperStatLevel(2, () -> new ItemStack(Material.SHEARS, 2),
                                          List.of(text("Chopped leaves and"),
                                                  text("vines drop as blocks")),
                                          TreeChopperTier.IRON)),
         List.of(TreeChopperStat.LEAF), List.of()) {

        @Override public List<TreeChopperStat> getConflicts() {
            return List.of(FORTUNE);
        }
    },
    REPLANT(Vec2i.of(2, 2), text("Replant"), () -> new ItemStack(Material.OAK_SAPLING),
            List.of(new TreeChopperStatLevel(1, () -> new ItemStack(Material.OAK_SAPLING),
                                             List.of(text("Chopped trees are"),
                                                     text("instantly replanted")),
                                             TreeChopperTier.IRON)),
            List.of(TreeChopperStat.LEAF), List.of()),
    PICKUP(Vec2i.of(4, 1), text("Magnet"), Mytems.MAGNET::createIcon,
           List.of(new TreeChopperStatLevel(1, Mytems.MAGNET::createIcon,
                                            List.of(text("Pick up broken blocks")),
                                            TreeChopperTier.IRON)),
           List.of(), List.of()),
    SPEED(Vec2i.of(5, 2), text("Speed"), () -> new ItemStack(Material.SUGAR),
          List.of(new SpeedLevel(1, () -> new ItemStack(Material.SUGAR),
                                 TreeChopperTier.IRON),
                  new SpeedLevel(2, () -> new ItemStack(Material.SUGAR),
                                 TreeChopperTier.IRON),
                  new SpeedLevel(3, () -> new ItemStack(Material.SUGAR),
                                 TreeChopperTier.IRON),
                  new SpeedLevel(4, () -> new ItemStack(Material.SUGAR),
                                 TreeChopperTier.IRON),
                  new SpeedLevel(5, () -> new ItemStack(Material.SUGAR),
                                 TreeChopperTier.IRON)),
          List.of(TreeChopperStat.CHOP), List.of()) {

        @Override public void removeFromItem(ItemMeta meta) {
            meta.removeEnchant(Enchantment.EFFICIENCY);
        }

        @Override public void applyToItem(ItemMeta meta, int upgradeLevel) {
            meta.addEnchant(Enchantment.EFFICIENCY, upgradeLevel, true);
        }
    },
    PUNCH(Vec2i.of(6, 2), text("Punching"), () -> new ItemStack(Material.GUNPOWDER),
          List.of(new TreeChopperStatLevel(1, () -> new ItemStack(Material.GUNPOWDER),
                                           List.of(text("Insta chop the root"),
                                                   text("block")),
                                           TreeChopperTier.IRON)),
          List.of(), List.of(TreeChopperStat.SPEED)),
    ENCH(Vec2i.of(0, 0), text("Enchanter"), () -> new ItemStack(Material.EXPERIENCE_BOTTLE),
         List.of(new EnchantLevel(1, () -> new ItemStack(Material.EXPERIENCE_BOTTLE),
                                  TreeChopperTier.IRON),
                 new EnchantLevel(2, () -> new ItemStack(Material.EXPERIENCE_BOTTLE),
                                  TreeChopperTier.IRON),
                 new EnchantLevel(3, () -> new ItemStack(Material.EXPERIENCE_BOTTLE),
                                  TreeChopperTier.IRON),
                 new EnchantLevel(4, () -> new ItemStack(Material.EXPERIENCE_BOTTLE),
                                  TreeChopperTier.IRON),
                 new EnchantLevel(5, () -> new ItemStack(Material.EXPERIENCE_BOTTLE),
                                  TreeChopperTier.IRON),
                 new EnchantLevel(6, () -> new ItemStack(Material.EXPERIENCE_BOTTLE),
                                  TreeChopperTier.IRON),
                 new EnchantLevel(7, () -> new ItemStack(Material.EXPERIENCE_BOTTLE),
                                  TreeChopperTier.IRON),
                 new EnchantLevel(8, () -> new ItemStack(Material.EXPERIENCE_BOTTLE),
                                  TreeChopperTier.IRON),
                 new EnchantLevel(9, () -> new ItemStack(Material.EXPERIENCE_BOTTLE),
                                  TreeChopperTier.IRON),
                 new EnchantLevel(10, () -> new ItemStack(Material.EXPERIENCE_BOTTLE),
                                  TreeChopperTier.IRON)),
         List.of(), List.of()),
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
