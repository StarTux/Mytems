package com.cavetale.mytems.item.easter;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import io.papermc.paper.datacomponent.DataComponentTypes;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.inventory.ItemStack;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.GRAY;
import static net.kyori.adventure.text.format.TextDecoration.BOLD;

@Getter
@RequiredArgsConstructor
public final class EasterToken implements Mytem {
    private final Mytems key;
    private Component displayName;
    private ItemStack prototype;

    @Override
    public void enable() {
        TextColor chatColor = TextColor.color(0xffb6c1);
        displayName = text("Easter Token", chatColor, BOLD);
        prototype = new ItemStack(key.getMaterial());
        prototype.editMeta(meta -> {
                meta.itemName(displayName);
                meta.lore(List.of(text("Trade this for cool", GRAY),
                                  text("prizes with the", GRAY),
                                  text("Easter Bunny.", GRAY)));
                key.markItemMeta(meta);
            });
        prototype.unsetData(DataComponentTypes.CONSUMABLE);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }
}
