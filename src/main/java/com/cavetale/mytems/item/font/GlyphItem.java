package com.cavetale.mytems.item.font;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Skull;
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
            ? Skull.create(glyph.string.toUpperCase(), glyph.uuid, glyph.texture, null)
            : new ItemStack(key.material);
        key.markItemStack(item);
        prototype = item;
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }
}
