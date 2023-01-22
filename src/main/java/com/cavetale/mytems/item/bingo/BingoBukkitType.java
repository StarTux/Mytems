package com.cavetale.mytems.item.bingo;

import com.cavetale.mytems.Mytems;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import static net.kyori.adventure.text.Component.text;
import net.kyori.adventure.text.format.NamedTextColor;

@RequiredArgsConstructor
public enum BingoBukkitType {
    DEFAULT(16, Mytems.BINGO_BUKKIT, text("Bingo Bukkit")),
    GOLD(32, Mytems.GOLD_BINGO_BUKKIT, text("Golden Bingo Bukkit", NamedTextColor.GOLD)),
    DIAMOND(128, Mytems.DIAMOND_BINGO_BUKKIT, text("Diamond Bingo Bukkit", NamedTextColor.AQUA)),
    ;

    public final int capacity;
    public final Mytems mytems;
    public final Component displayName;
    protected BingoBukkit instance;

    public static BingoBukkitType of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }
}
