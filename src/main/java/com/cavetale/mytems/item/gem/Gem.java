package com.cavetale.mytems.item.gem;

import com.cavetale.core.util.Json;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Text;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.tooltip;
import static java.util.Objects.requireNonNull;
import static net.kyori.adventure.text.Component.text;

@Getter
public final class Gem implements Mytem {
    private final Mytems key;
    private final GemType gemType;
    private ItemStack prototype;
    private Component displayName;

    public Gem(final Mytems mytems) {
        this.key = mytems;
        this.gemType = requireNonNull(GemType.of(key));
    }

    @Override
    public void enable() {
        displayName = text(Text.toCamelCase(key, " "), gemType.getColor().getTextColor());
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                key.markItemMeta(meta);
                tooltip(meta, List.of(displayName));
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public boolean isMassStorable() {
        return false;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public GemTag serializeTag(ItemStack itemStack) {
        final GemTag tag = new GemTag();
        tag.load(key, itemStack);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        ItemStack itemStack = createItemStack();
        final GemTag tag = Json.deserialize(serialized, GemTag.class);
        if (tag != null && !tag.isEmpty()) {
            tag.store(key, itemStack);
        }
        return itemStack;
    }
}
