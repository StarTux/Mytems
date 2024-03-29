package com.cavetale.mytems.item.hastypickaxe;

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
import org.bukkit.inventory.ItemStack;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
@RequiredArgsConstructor
public enum HastyPickaxeStat implements UpgradableStat {
    NONE(Vec2i.of(0, 0), text("None", WHITE), () -> Mytems.OK.createItemStack(),
         List.of(new Level(1, 1, () -> Mytems.OK.createItemStack(), List.of(text("Description")))),
         List.of(), List.of());
    ;

    @Value
    public static final class Level implements UpgradableStatLevel {
        private final int level;
        private final int requiredTier;
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
