package com.cavetale.mytems.item.garden;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Text;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.tooltip;
import static com.cavetale.mytems.util.Items.unbreakable;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextColor.color;

@RequiredArgsConstructor @Getter
public final class Scissors implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        displayName = Text.gradient("Pair of Scissors", color(0x008000), color(0x00ff00));
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                List<Component> text = new ArrayList<>();
                text.add(displayName);
                text.addAll(Text.wrapLore("These scissors will last you forever.", c -> c.color(color(0x808060))));
                tooltip(meta, text);
                unbreakable(meta);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }
}
