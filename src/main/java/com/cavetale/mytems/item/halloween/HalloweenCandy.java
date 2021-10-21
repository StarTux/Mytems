package com.cavetale.mytems.item.halloween;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor @Getter
public final class HalloweenCandy implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private static final TextColor COLOR = TextColor.color(0xAA40AA);

    @Override
    public void enable() {
        this.displayName = Component.text(Text.toCamelCase(key, " "), COLOR);
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                Items.text(meta, List.of(displayName,
                                         Component.text("Trade in for", NamedTextColor.GRAY),
                                         Component.text("Halloween Tokens!", NamedTextColor.GRAY)));
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }
}
