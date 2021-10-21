package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor @Getter
public final class DummyMytem implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        this.displayName = Component.text(Text.toCamelCase(key, " "));
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                Items.text(meta, List.of(displayName));
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }
}
