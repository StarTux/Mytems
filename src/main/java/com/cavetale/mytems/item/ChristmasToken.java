package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Skull;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.inventory.ItemStack;

@Getter @RequiredArgsConstructor
public final class ChristmasToken implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        displayName = Component.join(JoinConfiguration.noSeparators(), new Component[] {
                Component.text("C", TextColor.color(0x1111ff)),
                Component.text("h", TextColor.color(0x3333ff)),
                Component.text("r", NamedTextColor.BLUE),
                Component.text("i", TextColor.color(0x7777ff)),
                Component.text("s", TextColor.color(0x9999ff)),
                Component.text("t", TextColor.color(0xbbbbff)),
                Component.text("m", TextColor.color(0xddddff)),
                Component.text("a", NamedTextColor.WHITE),
                Component.text("s", TextColor.color(0xddddff)),
                Component.text(" ", TextColor.color(0xbbbbff)),
                Component.text("T", TextColor.color(0x9999ff)),
                Component.text("o", TextColor.color(0x7777ff)),
                Component.text("k", NamedTextColor.BLUE),
                Component.text("e", TextColor.color(0x3333ff)),
                Component.text("n", TextColor.color(0x1111ff)),
            });
        List<Component> text = List.of(new Component[] {
                displayName,
                Component.text("Christmas Event 2020", NamedTextColor.DARK_GRAY),
                Component.empty(),
                Component.text("Ho ho ho! Find out how to", NamedTextColor.BLUE),
                Component.text("exchange this token for actual", NamedTextColor.BLUE),
                Component.text("goodies.", NamedTextColor.BLUE),
                Component.empty(),
                Component.text("You will when Santa comes to", NamedTextColor.BLUE),
                Component.text("town.", NamedTextColor.BLUE),
            });
        String texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6"
            + "Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv"
            + "ZjU2MTJkYzdiODZkNzFhZmMxMTk3MzAxYzE1ZmQ5Nzll"
            + "OWYzOWU3YjFmNDFkOGYxZWJkZjgxMTU1NzZlMmUifX19";
        prototype = Skull.create("Christmas Token",
                                 UUID.fromString("6d46f5a1-a833-414c-ba0d-9842cb59316e"),
                                 texture,
                                 null);
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
