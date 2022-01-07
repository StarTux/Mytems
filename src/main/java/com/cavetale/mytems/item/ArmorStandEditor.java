package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor @Getter
public final class ArmorStandEditor implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        this.displayName = text("Armor Stand Editor", DARK_GREEN);
        List<Component> text = List.of(new Component[] {
                displayName,
                text("Many options to move,", GRAY),
                text("rotate, pose, resize,", GRAY),
                text("hide and equip your", GRAY),
                text("armor stands!", GRAY),
                empty(),
                join(noSeparators(),
                     text("right-click", GREEN),
                     text(" open menu", GRAY)),
                join(noSeparators(),
                     text("click armor stand", GREEN),
                     text(" edit", GRAY)),
            });
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                Items.text(meta, text);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }
}
