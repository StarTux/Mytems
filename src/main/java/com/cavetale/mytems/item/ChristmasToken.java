package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Skull;
import com.cavetale.mytems.util.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@Getter @RequiredArgsConstructor
public final class ChristmasToken implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        displayName = textOfChildren(text("C", color(0x1111ff)),
                                     text("h", color(0x3333ff)),
                                     text("r", BLUE),
                                     text("i", color(0x7777ff)),
                                     text("s", color(0x9999ff)),
                                     text("t", color(0xbbbbff)),
                                     text("m", color(0xddddff)),
                                     text("a", WHITE),
                                     text("s", color(0xddddff)),
                                     text(" ", color(0xbbbbff)),
                                     text("T", color(0x9999ff)),
                                     text("o", color(0x7777ff)),
                                     text("k", BLUE),
                                     text("e", color(0x3333ff)),
                                     text("n", color(0x1111ff)));
        String rawText = ""
            + "Ho ho ho! Find out how to exchange this token for actual goodies."
            + "\n\n"
            + "You will when Santa comes to town.";
        List<Component> text = new ArrayList<>();
        text.add(displayName);
        text.addAll(Text.wrapLore(rawText, c -> c.color(BLUE)));
        String texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6"
            + "Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv"
            + "ZjU2MTJkYzdiODZkNzFhZmMxMTk3MzAxYzE1ZmQ5Nzll"
            + "OWYzOWU3YjFmNDFkOGYxZWJkZjgxMTU1NzZlMmUifX19";
        prototype = Skull.create("ChristmasToken",
                                 UUID.fromString("6d46f5a1-a833-414c-ba0d-9842cb59316e"),
                                 texture,
                                 null);
        prototype.editMeta(meta -> {
                tooltip(meta, text);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }
}
