package com.cavetale.mytems.item.font;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor @Getter
public final class GlyphItem implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        Glyph glyph = Objects.requireNonNull(Glyph.MYTEMS_MAP.get(key));
        displayName = Component.text(glyph.string.toUpperCase(), NamedTextColor.WHITE);
        ItemStack item = key.material == Material.PLAYER_HEAD
            ? Items.skull(glyph.uuid, glyph.string, glyph.texture, null)
            : new ItemStack(key.material);
        key.markItemStack(item);
        prototype = Items.text(item, List.of(displayName));
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }
}
