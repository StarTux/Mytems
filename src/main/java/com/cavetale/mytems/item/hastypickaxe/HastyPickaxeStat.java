package com.cavetale.mytems.item.hastypickaxe;

import com.cavetale.core.struct.Vec2i;
import com.cavetale.mytems.Mytems;
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
import static com.cavetale.mytems.util.Items.iconize;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
@RequiredArgsConstructor
public enum HastyPickaxeStat implements UpgradableStat {
    EFFICIENCY(Vec2i.of(4, 2), text("Efficiency", GRAY), () -> Mytems.HASTY_PICKAXE.createIcon(),
               List.of(new Level(1, HastyPickaxeTier.COPPER, () -> Mytems.HASTY_PICKAXE.createIcon(1), List.of(text("Add Efficiency I"))),
                       new Level(2, HastyPickaxeTier.COPPER, () -> Mytems.HASTY_PICKAXE.createIcon(2), List.of(text("Add Efficiency II"))),
                       new Level(3, HastyPickaxeTier.COPPER, () -> Mytems.HASTY_PICKAXE.createIcon(3), List.of(text("Add Efficiency III"))),
                       new Level(4, HastyPickaxeTier.COPPER, () -> Mytems.HASTY_PICKAXE.createIcon(4), List.of(text("Add Efficiency IV"))),
                       new Level(5, HastyPickaxeTier.COPPER, () -> Mytems.HASTY_PICKAXE.createIcon(5), List.of(text("Add Efficiency V")))),
               List.of(), List.of()) {
        @Override public void removeFromItem(ItemMeta meta) {
            meta.removeEnchant(Enchantment.EFFICIENCY);
        }

        @Override public void applyToItem(ItemMeta meta, int upgradeLevel) {
            meta.addEnchant(Enchantment.EFFICIENCY, upgradeLevel, true);
        }
    },

    FORTUNE(Vec2i.of(4, 1), text("Fortune", GOLD), () -> new ItemStack(Material.DIAMOND),
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

        @Override public List<HastyPickaxeStat> getConflicts() {
            return List.of(SILK_TOUCH);
        }
    },

    SILK_TOUCH(Vec2i.of(4, 3), text("Silk Touch", AQUA), () -> new ItemStack(Material.FEATHER),
               List.of(new Level(1, HastyPickaxeTier.COPPER, () -> new ItemStack(Material.FEATHER), List.of(text("Add Silk Touch")))),
               List.of(HastyPickaxeStat.EFFICIENCY), List.of()) {
        @Override public void removeFromItem(ItemMeta meta) {
            meta.removeEnchant(Enchantment.SILK_TOUCH);
        }

        @Override public void applyToItem(ItemMeta meta, int upgradeLevel) {
            meta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
        }

        @Override public List<HastyPickaxeStat> getConflicts() {
            return List.of(FORTUNE);
        }
    },

    HASTE(Vec2i.of(5, 2), text("Haste", GRAY), () -> new ItemStack(Material.GOLDEN_PICKAXE),
          List.of(new Level(1, HastyPickaxeTier.GOLD, () -> new ItemStack(Material.GOLDEN_PICKAXE, 1), List.of(text("Get Haste when you"),
                                                                                                               text("break an ore block"))),
                  new Level(2, HastyPickaxeTier.DIAMOND, () -> new ItemStack(Material.GOLDEN_PICKAXE, 2), List.of(text("Get Haste II when you"),
                                                                                                                  text("break an ore block")))),
          List.of(), List.of(EFFICIENCY)),

    END_PORTAL(Vec2i.of(6, 2), text("End Portal Breaker", LIGHT_PURPLE), () -> new ItemStack(Material.END_PORTAL_FRAME),
               List.of(new Level(1, HastyPickaxeTier.DIAMOND, () -> new ItemStack(Material.END_PORTAL_FRAME), List.of(text("Mine End Portals"),
                                                                                                                      text("The Eye of Ender", DARK_PURPLE),
                                                                                                                      text("will be lost, unless", DARK_PURPLE),
                                                                                                                      text("you have Silk", DARK_PURPLE),
                                                                                                                      text("Touch", DARK_PURPLE)))),
               List.of(HASTE), List.of()),

    BEDROCK(Vec2i.of(7, 2), text("Bedrock Breaker", DARK_GRAY), () -> new ItemStack(Material.BEDROCK),
            List.of(new Level(1, HastyPickaxeTier.DIAMOND, () -> new ItemStack(Material.BEDROCK), List.of(text("Break Bedrock,"),
                                                                                                          text("provided it is either"),
                                                                                                          text("player placed or"),
                                                                                                          text("spawned in the End")))),
            List.of(END_PORTAL), List.of()),
    ;

    @Value
    public static final class Level implements UpgradableStatLevel {
        private final int level;
        private final UpgradableItemTier requiredTier;
        private final Supplier<ItemStack> iconSupplier;
        private final List<Component> description;

        @Override
        public ItemStack getIcon() {
            return iconize(iconSupplier.get());
        }
    }

    private final Vec2i guiSlot;
    private final Component title;
    private final Supplier<ItemStack> iconSupplier;
    private final List<Level> levels;
    private final List<HastyPickaxeStat> dependencies;
    private final List<HastyPickaxeStat> completeDependencies;

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
        return iconize(iconSupplier.get());
    }

    public List<HastyPickaxeStat> getConflicts() {
        return List.of();
    }
}
