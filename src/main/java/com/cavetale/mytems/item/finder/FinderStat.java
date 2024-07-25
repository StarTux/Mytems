package com.cavetale.mytems.item.finder;

import com.cavetale.core.struct.Vec2i;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.upgradable.UpgradableStat;
import com.cavetale.mytems.item.upgradable.UpgradableStatLevel;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.iconize;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
@RequiredArgsConstructor
public enum FinderStat implements UpgradableStat {
    RANGE(Vec2i.of(4, 0), text("Range", GOLD), Mytems.YARDSTICK::createIcon,
          List.of(new RangeLevel(1, 200, FinderTier.STRUCTURE),
                  new RangeLevel(2, 300, FinderTier.SECRET),
                  new RangeLevel(3, 400, FinderTier.MYSTIC),
                  new RangeLevel(4, 500, FinderTier.MASTER)),
          List.of(), List.of()),
    // Start
    TREASURE(Vec2i.of(4, 2), FinderTier.STRUCTURE, "Treasure", () -> new ItemStack(Material.CHEST_MINECART), List.of()),
    // Left
    PYRAMID(Vec2i.of(2, 2), FinderTier.STRUCTURE, "Pyramids", () -> new ItemStack(Material.SANDSTONE_STAIRS), List.of(TREASURE)),
    ARCHAEOLOGY(Vec2i.of(2, 4), FinderTier.SECRET, "Archaeology", () -> new ItemStack(Material.BRUSH), List.of(PYRAMID)),
    // Right
    VILLAIN(Vec2i.of(6, 2), FinderTier.STRUCTURE, "Villains", Mytems.PILLAGER_FACE::createIcon, List.of(TREASURE)),
    CASTLE(Vec2i.of(6, 3), FinderTier.SECRET, "Castles", () -> new ItemStack(Material.ENDER_EYE), List.of(VILLAIN)),
    HIDDEN(Vec2i.of(6, 4), FinderTier.MYSTIC, "Hidden", Mytems.CAVETALE_DUNGEON::createIcon, List.of(CASTLE)),
    // Final
    TRIAL_CHAMBER(Vec2i.of(4, 4), FinderTier.MASTER, "Bosses", () -> new ItemStack(Material.TRIAL_KEY), List.of(HIDDEN, ARCHAEOLOGY)),
    ;

    public static final FinderStat NONE = null;

    private final Vec2i guiSlot;
    private final Component title;
    private final Supplier<ItemStack> iconSupplier;
    private final List<? extends UpgradableStatLevel> levels;
    private final List<FinderStat> dependencies;
    private final List<FinderStat> completeDependencies;

    /**
     * The constructor for FoundType unlocks.  It constructs a single level of FoundLevel.
     */
    FinderStat(final Vec2i guiSlot,
               final FinderTier requiredTier,
               final String displayName,
               final Supplier<ItemStack> iconSupplier,
               final List<FinderStat> dependencies) {
        this.guiSlot = guiSlot;
        this.title = text(displayName, requiredTier.getColor());
        this.iconSupplier = iconSupplier;
        this.levels = List.of(new FoundLevel(this, requiredTier, iconSupplier));
        this.dependencies = dependencies;
        this.completeDependencies = List.of();
    }

    @Value
    public static final class FoundLevel implements UpgradableStatLevel {
        private final FinderStat stat;
        private final FinderTier requiredTier;
        private final Supplier<ItemStack> iconSupplier;

        @Override
        public int getLevel() {
            return 1;
        }

        @Override
        public ItemStack getIcon() {
            return iconize(iconSupplier.get());
        }

        @Override
        public List<Component> getDescription() {
            final List<Component> result = new ArrayList<>();
            for (FoundType type : FoundType.values()) {
                if (type.isDisabled()) {
                    continue;
                }
                if (type.getRequiredStat() != stat) {
                    continue;
                }
                result.add(text(type.getDisplayName(), requiredTier.getColor()));
            }
            return result;
        }
    }

    @Value
    public static final class RangeLevel implements UpgradableStatLevel {
        private final int level;
        private final int range;
        private final FinderTier requiredTier;

        @Override
        public ItemStack getIcon() {
            return Mytems.YARDSTICK.createIcon(level);
        }

        @Override
        public List<Component> getDescription() {
            return List.of(text("Increase range to " + range));
        }
    }

    @Override
    public String getKey() {
        return name().toLowerCase();
    }

    @Override
    public ItemStack getIcon() {
        return iconize(iconSupplier.get());
    }

    @Override
    public List<FinderStat> getConflicts() {
        return List.of();
    }

    public static FinderStat forKey(String key) {
        try {
            return valueOf(key.toUpperCase());
        } catch (IllegalArgumentException iae) {
            return null;
        }
    }
}
