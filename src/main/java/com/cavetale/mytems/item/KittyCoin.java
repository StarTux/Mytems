package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Skull;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextColor.color;
import static net.kyori.adventure.text.format.TextDecoration.BOLD;

public final class KittyCoin implements Mytem {
    @Getter private final Mytems key;
    @Getter private final Component displayName;
    private final ItemStack prototype;
    @SuppressWarnings("LineLength")
    private static final Skull SKULL = new
        Skull("Cavetale",
              UUID.fromString("38b7fcf5-8cd8-4654-98f8-64a98c286f1e"),
              "ewogICJ0aW1lc3RhbXAiIDogMTYwNzQ2MzI5MDg0NCwKICAicHJvZmlsZUlkIiA6ICIzOGI3ZmNmNThjZDg0NjU0OThmODY0YTk4YzI4NmYxZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJDYXZldGFsZSIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9iMmU1ZWZiMDM2ZGFkYmJkODY5NzI4YzE0NzZhODMzZTRmMjkzYmU4MGJhYjA5NTYyYzZmZWE0ZjgwMDk4ZDU5IgogICAgfQogIH0KfQ==",
              "lKwExnC/9al/Koi8QgQbzaXyEIwkK/Vjvc/ovEelZv6WMSUSICuE+Fmqd3GR6jQGwxskW4uMD2w3AO1Nf7qlG3op0voWjbs0w4PzfehuNF9mx4we6Mm7TZ0qA6QN6XIxxtQebAD/GXARvzWQk4LtHoiJ9l3BIxGb/dDwbav1COGe+zl+hOrxpi5lSMthH4EcVHX41bA+XIAC6vrFs83wy4VRi0MR9prFKOBW61LrMKxVgOLPJy0W1hIaf8v25Wtsa8jzuiPBE8WBxJwffqyGneI0LRR80i8MCX8G4pUuQ5scS9cYphJwsvhu7Dg+DUofxCXOw4GEd47ocv2aPTqdW7HTKlgaDOVTAMcp3/9yv2U8+AiKqX6yuzpjUpwQfzagund0wtlvZSRne6lDGt+RfYV8Wh6cS9VnH8TKzFHTvF19CdCdyU1+tess8q2g5tvz3V8y/TB7OBr4ZfuTYkHcDYUo0jDkP7CoEGGJT12RQI7aUwgEWa6mFI4PeN01YalA4YRVgcHGLFFxA9gzr7eGg5gn0I571/cSXUYbYP8OYesnB4gKtl0Uf9sv6yX+xUpkFkPMXtfSYNb8m3Uw20x+VlPQLnT1SO0q1+62ghx+b0JZAekri1knHGhXOHNH0CE4V2kovTtl9dR/P4H7X+zp1bdviHGIsGgnLpJckvzyKLY=");

    public KittyCoin(final Mytems key) {
        this.key = key;
        this.displayName = text("Kitty Coin", color(0xFFD700), BOLD);
        this.prototype = SKULL.create();
        prototype.editMeta(meta -> {
                tooltip(meta, List.of(displayName));
                key.markItemMeta(meta);
            });
    }

    @Override
    public void enable() {
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }
}
