package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Skull;
import com.cavetale.mytems.util.Text;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter @RequiredArgsConstructor
public final class ChristmasToken implements Mytem {
    private final Mytems key;
    private static final String SKULL_NAME = "Christmas Token";
    public static final UUID SKULL_ID = UUID.fromString("6d46f5a1-a833-414c-ba0d-9842cb59316e");
    public static final String SKULL_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjU2MTJkYzdiODZkNzFhZmMxMTk3MzAxYzE1ZmQ5NzllOWYzOWU3YjFmNDFkOGYxZWJkZjgxMTU1NzZlMmUifX19";
    private ItemStack prototype;
    private String description = ""
        + ChatColor.DARK_GRAY + "Christmas Event 2020"
        + "\n\n" + ChatColor.BLUE + "Ho ho ho! Find out how to exchange this token for actual goodies."
        + "\n\n" + ChatColor.BLUE + "You will when Santa comes to town.";
    private Component displayName;

    private Component xmasify(String in) {
        int len = in.length();
        int iter = 255 / len;
        Component component = Component.empty();
        for (int i = 0; i < len; i += 1) {
            int white = 255 - Math.abs(i - (len / 2)) * 2 * iter;
            component = component.append(Component.text(in.substring(i, i + 1)).color(TextColor.color(white, white, 255)));
        }
        return component;
    }

    @Override
    public void enable() {
        displayName = xmasify("Christmas Token");
        prototype = Skull.create(SKULL_NAME, SKULL_ID, SKULL_TEXTURE);
        ItemMeta meta = prototype.getItemMeta();
        meta.lore(Text.wrapLore(description));
        meta.displayName(displayName);
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }
}
