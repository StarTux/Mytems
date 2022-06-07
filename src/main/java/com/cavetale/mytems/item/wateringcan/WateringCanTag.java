package com.cavetale.mytems.item.wateringcan;

import com.cavetale.mytems.MytemTag;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.worldmarker.util.Tags;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Items.text;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

/**
 * The water level is reversed: 0 means full, while
 * WateringCanType.maxLevel is empty.  Thus, a full can may be stored
 * in MS.
 */
public final class WateringCanTag extends MytemTag {
    public static final String WATER = "water";
    protected int water;
    protected transient WateringCan wateringCan;

    @Override
    public boolean isEmpty() {
        return super.isEmpty()
            && water == 0;
    }

    @Override
    public void load(ItemStack itemStack) {
        super.load(itemStack);
        if (!itemStack.hasItemMeta()) return;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer tag = meta.getPersistentDataContainer();
        Integer value = Tags.getInt(tag, MytemsPlugin.namespacedKey(WATER));
        this.water = value != null ? value : 0;
    }

    @Override
    public void store(ItemStack itemStack) {
        super.store(itemStack);
        itemStack.editMeta(meta -> {
                PersistentDataContainer tag = meta.getPersistentDataContainer();
                Tags.set(tag, MytemsPlugin.namespacedKey(WATER), water);
                List<Component> tooltip = new ArrayList<>();
                tooltip.addAll(wateringCan.tooltip);
                int waterLeft = wateringCan.type.maxWater - water;
                if (waterLeft > 0) {
                    tooltip.add(join(noSeparators(), text(tiny("water "), GRAY), text(waterLeft, BLUE)));
                }
                text(meta, tooltip);
            });
    }

    @Override
    public boolean isDismissable() {
        return isEmpty();
    }
}
