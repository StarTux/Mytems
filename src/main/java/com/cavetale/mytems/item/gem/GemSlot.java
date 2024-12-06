package com.cavetale.mytems.item.gem;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import static com.cavetale.mytems.util.Items.clearAttributes;
import static com.cavetale.mytems.util.Items.tooltip;
import static com.cavetale.mytems.util.Text.toCamelCase;
import static java.util.Objects.requireNonNull;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.WHITE;

@Getter
public final class GemSlot implements Mytem {
    private final Mytems key;
    private final GemShape shape;
    private ItemStack prototype;
    private Component displayName;

    public GemSlot(final Mytems mytems) {
        this.key = mytems;
        this.shape = requireNonNull(GemShape.ofSlotMytems(key));
    }

    @Override
    public void enable() {
        displayName = text(toCamelCase(key, " "), WHITE);
        prototype = new ItemStack(key.material);
        prototype.editMeta(LeatherArmorMeta.class, meta -> {
                key.markItemMeta(meta);
                meta.setColor(Color.WHITE);
                tooltip(meta, List.of(displayName));
                clearAttributes(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public boolean isAvailableToPlayers() {
        return false;
    }

    @Override
    public boolean isMassStorable() {
        return false;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
