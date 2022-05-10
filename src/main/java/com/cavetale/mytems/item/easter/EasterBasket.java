package com.cavetale.mytems.item.easter;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import static net.kyori.adventure.text.Component.text;

@Getter
public final class EasterBasket implements Mytem {
    private final Mytems key;
    private final EasterEggColor color;
    private Component displayName;
    private ItemStack prototype;

    public EasterBasket(final Mytems key) {
        this.key = key;
        this.color = Objects.requireNonNull(EasterEggColor.of(key));
    }

    @Override
    public void enable() {
        String name = Text.toCamelCase(key);
        displayName = text(name, color.textColor);
        prototype = color.getBaseItemStack();
        prototype.editMeta(meta -> {
                Items.text(meta, List.of(displayName));
                key.markItemMeta(meta);
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
}
