package com.cavetale.mytems.item.vote;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor @Getter
abstract class VoteItem implements Mytem {
    protected final Mytems key;
    protected ItemStack prototype;
    protected Component displayName;
    protected final Random random = new Random();
    protected List<Component> baseLore;

    /**
     * Initialize the base lore and leave the rest to the
     * implementation.
     */
    @Override
    public void enable() {
        baseLore = List.of(new Component[] {
                Component.text("Thank you for voting!", NamedTextColor.GRAY),
                Component.text("Your vote helps us get more", NamedTextColor.GRAY),
                Component.text("players on the server.", NamedTextColor.GRAY),
                Component.text(""),
                Component.text("Sincerely, ", NamedTextColor.GRAY)
                .append(Component.text("cavetale.com", null, TextDecoration.ITALIC))
            });
    }

    @Override
    public final ItemStack createItemStack() {
        return prototype.clone();
    }
}
