package com.cavetale.mytems.item;

import com.cavetale.core.font.VanillaItems;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.tooltip;
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
                     Mytems.MOUSE_RIGHT.component,
                     text(" open menu", GRAY)),
                join(noSeparators(),
                     Mytems.MOUSE_RIGHT.component,
                     VanillaItems.ARMOR_STAND.component,
                     text(" edit", GRAY)),
            });
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                tooltip(meta, text);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
