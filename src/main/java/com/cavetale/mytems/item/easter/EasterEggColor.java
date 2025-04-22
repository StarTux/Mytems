package com.cavetale.mytems.item.easter;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Skull;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.inventory.ItemStack;
import static net.kyori.adventure.text.format.TextColor.color;

@RequiredArgsConstructor
public enum EasterEggColor {
    BLUE(color(0x8A2BE2), Mytems.BLUE_EASTER_EGG, Mytems.BLUE_EASTER_BASKET,
         UUID.fromString("2c87257c-c058-4a28-990e-eeeeae44f8d7"),
         "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWViMzM1MTgyZGI1ZjNiZTgwZmNjZjZlYWJlNTk5ZjQxMDdkNGZmMGU5ZjQ0ZjM0MTc0Y2VmYTZlMmI1NzY4In19fQ=="),
    GREEN(color(0x00FF00), Mytems.GREEN_EASTER_EGG, Mytems.GREEN_EASTER_BASKET,
          UUID.fromString("259631f6-804d-4ce5-9e67-d47e236728d2"),
          "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjJjZDVkZjlkN2YxZmE4MzQxZmNjZTJmM2MxMThlMmY1MTdlNGQyZDk5ZGYyYzUxZDYxZDkzZWQ3ZjgzZTEzIn19fQ=="),
    ORANGE(color(0xFFA500), Mytems.ORANGE_EASTER_EGG, Mytems.ORANGE_EASTER_BASKET,
           UUID.fromString("e28bd1f7-e7d3-4f18-b745-e2a53723b22d"),
           "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmI4YjJiZGZjNWQxM2I5ZjQ1OTkwZWUyZWFhODJlNDRhZmIyYTY5YjU2YWM5Mjc2YTEzMjkyYjI0YzNlMWRlIn19fQ=="),
    PINK(color(0xFFB6C1), Mytems.PINK_EASTER_EGG, Mytems.PINK_EASTER_BASKET,
         UUID.fromString("07117dcf-6869-4f7e-a99c-0b7ce3fd93c9"),
         "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTViOGRjYmVhMjdmNDJmNWFlOTEwNDQ1ZTA1ZGFjODlkMzEwYWFmMjM2YTZjMjEyM2I4NTI4MTIwIn19fQ=="),
    PURPLE(color(0x800080), Mytems.PURPLE_EASTER_EGG, Mytems.PURPLE_EASTER_BASKET,
           UUID.fromString("31de2b5f-47f1-4910-accd-6747c3e77059"),
           "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQ2YjQ2OWY2NmE1NzQ1OTZmMWViNTFmYWNkY2FhMTUyMmE0ODYwMTE3MDA4NTRjZWRjNDc5NmU0NGYyM2I5MCJ9fX0="),
    YELLOW(color(0xFFFF00), Mytems.YELLOW_EASTER_EGG, Mytems.YELLOW_EASTER_BASKET,
           UUID.fromString("0e0e78c9-3756-4204-b4c4-06d9c5bc9d03"),
           "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTJkMzE3ZTFhMTI4M2FiMmY2NDc0ZjNhN2UxODI1OWU2MGE0NzkxYjYxMzMxOWVmZWRhN2ViZGFiODk5MzQifX19"),
    ;

    public final TextColor textColor;
    public final Mytems eggMytems;
    public final Mytems basketMytems;
    private final UUID skullUuid;
    private final String skullTexture;

    public static EasterEggColor of(Mytems mytems) {
        for (EasterEggColor it : EasterEggColor.values()) {
            if (it.eggMytems == mytems || it.basketMytems == mytems) return it;
        }
        return null;
    }

    protected ItemStack getBaseItemStack() {
        return Skull.create(null, skullUuid, skullTexture);
    }
}
