package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Skull;
import com.cavetale.mytems.util.Text;
import java.awt.Color;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@RequiredArgsConstructor
public final class ChristmasToken implements Mytem {
    public static final Mytems ID = Mytems.CHRISTMAS_TOKEN;
    private static final String SKULL_NAME = "Christmas Token";
    private static final UUID SKULL_ID = UUID.fromString("6d46f5a1-a833-414c-ba0d-9842cb59316e");
    private static final String SKULL_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjU2MTJkYzdiODZkNzFhZmMxMTk3MzAxYzE1ZmQ5NzllOWYzOWU3YjFmNDFkOGYxZWJkZjgxMTU1NzZlMmUifX19";
    private final MytemsPlugin plugin;
    private ItemStack prototype;
    private String description = ""
        + ChatColor.DARK_GRAY + "Christmas Event 2020"
        + "\n\n" + ChatColor.BLUE + "Ho ho ho! Find out how to exchange this token for actual goodies."
        + "\n\n" + ChatColor.BLUE + "You will when Santa comes to town.";
    private BaseComponent[] displayName;

    @Override
    public String getId() {
        return ID.key;
    }

    private BaseComponent[] xmasify(String in) {
        int len = in.length();
        int iter = 255 / len;
        ComponentBuilder cb = new ComponentBuilder();
        for (int i = 0; i < len; i += 1) {
            int white = 255 - Math.abs(i - (len / 2)) * 2 * iter;
            cb.append(in.substring(i, i + 1)).color(ChatColor.of(new Color(white, white, 255)));
        }
        return cb.create();
    }

    @Override
    public void enable() {
        displayName = xmasify("Christmas Token");
        prototype = Skull.create(SKULL_NAME, SKULL_ID, SKULL_TEXTURE);
        ItemMeta meta = prototype.getItemMeta();
        meta.setLoreComponents(Text.toBaseComponents(Text.wrapMultiline(description, Text.ITEM_LORE_WIDTH)));
        meta.setDisplayNameComponent(displayName);
        prototype.setItemMeta(meta);
    }

    @Override
    public ItemStack getItem() {
        return prototype.clone();
    }

    @Override
    public BaseComponent[] getDisplayName() {
        return displayName;
    }
}
