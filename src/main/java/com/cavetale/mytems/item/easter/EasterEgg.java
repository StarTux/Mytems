package com.cavetale.mytems.item.easter;

import com.cavetale.core.font.Unicode;
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
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public final class EasterEgg implements Mytem {
    private final Mytems key;
    private final EasterEggColor color;
    private Component displayName;
    private ItemStack prototype;

    public EasterEgg(final Mytems key) {
        this.key = key;
        this.color = Objects.requireNonNull(EasterEggColor.of(key));
    }

    @Override
    public void enable() {
        displayName = text(Text.toCamelCase(key), color.textColor);
        prototype = color.getBaseItemStack();
        prototype.editMeta(meta -> {
                Items.text(meta, List.of(new Component[] {
                            displayName,
                            text("Easter Event", DARK_GRAY),
                            text(Unicode.tiny("Collected in the".toLowerCase()), color.textColor),
                            text(Unicode.tiny("Easter World.".toLowerCase()), color.textColor),
                            text(Unicode.tiny("Enter the portal".toLowerCase()), color.textColor),
                            text(Unicode.tiny("at spawn to".toLowerCase()), color.textColor),
                            text(Unicode.tiny("get there.".toLowerCase()), color.textColor),
                        }));
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }
}
