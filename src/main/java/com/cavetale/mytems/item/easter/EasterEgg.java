package com.cavetale.mytems.item.easter;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter @RequiredArgsConstructor
public final class EasterEgg implements Mytem {
    private final Mytems key;
    private Component displayName;
    private ItemStack prototype;

    @Override
    public void enable() {
        String name = Stream.of(key.name().split("_"))
            .map(s -> s.substring(0, 1) + s.substring(1).toLowerCase())
            .collect(Collectors.joining(" "));
        TextColor chatColor = getChatColor();
        displayName = Component.text(name).color(chatColor).decoration(TextDecoration.ITALIC, false);
        prototype = getBaseItemStack();
        ItemMeta meta = prototype.getItemMeta();
        meta.displayName(displayName);
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Easter Event").color(chatColor).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Collected in the Easter").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("Area. The portal at").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text("spawn can take you there.").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
    }

    @Override
    public ItemStack getItem() {
        return prototype.clone();
    }

    public TextColor getChatColor() {
        switch (key) {
        case BLUE_EASTER_EGG:
            return TextColor.color(0x8A2BE2);
        case GREEN_EASTER_EGG:
            return TextColor.color(0x00FF00);
        case ORANGE_EASTER_EGG:
            return TextColor.color(0xFFA500);
        case PINK_EASTER_EGG:
            return TextColor.color(0xFFB6C1);
        case PURPLE_EASTER_EGG:
            return TextColor.color(0x800080);
        case YELLOW_EASTER_EGG: default:
            return TextColor.color(0xFFFF00);
        }
    }

    @SuppressWarnings("LineLength")
    protected ItemStack getBaseItemStack() {
        switch (key) {
        case BLUE_EASTER_EGG:
            return Items.deserialize("H4sIAAAAAAAAAE2Pv07CUByFf/In1pq4uRgH0sQEE4dWBQMJgwGCt7FVoBZ6F3NpL6Xl3kLaW6Gos8/gSzj5AL6Fb+HsaNkcv3PONxwZoAj7HSKITeMkWEQA8pEEhcCDQx5E1I3JVDSXjGQ0fpxR4slQFMSXYdcLkm0sQckknMLxsyLoWihNpUsSQeNK1/cr1QlL6anyCjLIw3nK2N0qonEO9/FiSWMR0GQPpK2XxjSRAWBHgrJNcgs+aaareDxTvbHO3AzVc7aGKrtD4fIKRXY2aaM64nl/c12/zRr/tjVBRjXmXOgzHPXTCbfV24sBozcDzeUPT3hkB8bG0AzLz3APaTg0A2z5K8zNEIeYOSOdmda8hsP+2uh4c7OHudF72LKKQ0M1LFd1zm3uWJgZHGnmxrlEkdaY9lstgH0oIC8/Ujp7O3n5GuvV94Ofn4/O73f+DcrtRRqJHfgDrqRv/HYBAAA=");
        case GREEN_EASTER_EGG:
            return Items.deserialize("H4sIAAAAAAAAAE2Py07CQBiFf24Ry9qNiQmpMcEdF8FAwsJQAm0oWCiFzsYMdChtp4VMp0prND6FS5/CJzG+gq/g2rJzdXIu3+IIADkoSZhjg7DQ2QUAwnkRso4FZ74TkDXDG97ZUxwT9rAl2BIgx7EtwInlhMe4CPkx9glcPIucHLjYEfs45ISV+7ZdrtiMkOBafAEBhJkXUTp5CghLzT3b7QnjDglPoXgEI0ZCAQAyRSgYmEYEPkmsVNFyW7WWCl3Hciv1+qxKJ7K7v5UDI1715Jbsp/3wrjWK2/+2TY4XTWo2lC0KtGjlG9VRY0rJcFpb+/NH01VcJBkecqk3rpsH5Pdv1ERLdewiXfHVunpQ9S1VfbOm6hYdD7QYSV4TDczYTOYHJKWM5CVooTWQaydI7ydyUGtvtG4XoARZ2UqP5K/ea79v6ujnw/5+vbQrX+k3KPR2UcAz8Ac6ftiVdwEAAA==");
        case ORANGE_EASTER_EGG:
            return Items.deserialize("H4sIAAAAAAAAAE2PT06DQByFf7UaK12bGBfG4EYXJrSVGk1cGFt1iIClFBw2ZiwjfzpAMwxWNJ7Ac3gBY+I5jDfoxiO4le5cvve+t/gkgDo0e0QQh/I8ylIAabMBS5EP60mU0jEn9+J4ykhJ+W1IiS9BXZBAglU/yhd1A5YNklDYepYFfRTysdwnuaB8ux8E27sZJ2lA9+QXkEAaTgrGzFlKeRWueTalXEQ0X4PG4llwmksAUGvAikNYQeGDlpri3YSKf6OxcYm6VbaHCjNRPD1EqVPenaEuSqr98rR7VR79Y1VBXJXhjhZ66aC4SxzlqmMxemm1xsnowUvQAY61yLvwYsMdPOptpHrxoGXak5nnjkrPPQ/NnsaMnhVWbIltrOJ41MaururxuI3t/pMeT0ocIwU/GUx3LYbS1tH94OQEoAlLyK9EluevX78/3+bGZ3/+drjzvl+5wcpZVqSiBn+jQa1neAEAAA==");
        case PINK_EASTER_EGG:
            return Items.deserialize("H4sIAAAAAAAAAE2Pu07CUByH/9xiLZODi3EgTLpxN5AwEChwGtoK1EK7mAM90JZzCmlPxar4Eq6+gIkv4uRD+Bwmls3x+12GTwTIQL6HOTZIELpbH0C8ECDt2nDOXJ8sA7zirR3FMQnuHYJtETIcr0U4sd3wGAuQVTEjcPlc5OSRF1tFCYecBAVpvS5c7Vx/c108gAjidBNRqu19EiRwG2x3JOAuCU9BOP6igIQiAKQEyBmYRgQ+SSyXrLlTsucyXcaokbA+LVENebsb5BvxoosaiCX9sNMYxc1/2zrHszo1q7Jj+eNowYzSqDqhZDgpL9ndg6IbrjaYeCYzHMWzmdqTmTrrU02X9mpvXLb0Ttka9D2tRzfKk7Q3Z32meErF1C1P8aRYqaCaqqOaoqM98svN1bjdBshDGtmJQPbk7PDtuNrrx3v+5ef37Stxglx3G/k8BX/qY8ZibgEAAA==");
        case PURPLE_EASTER_EGG:
            return Items.deserialize("H4sIAAAAAAAAAE2Pv07CQBzHfwhELIuLi4MhTBqXUgQDiQOBAiX0KlBaykIOetDCXWmuV7Ea38jRN3D2FVydfQPL5vj5/hk+EkAWih0ssEV45O8DAOmyACe+CxfMD8iK47VohhQnhC88gl0JsgJvJDh1/egYFyCHMCNw9VoW5FmUm2UVR4LwkrrZlK7DmIeU3JTfQAJpsospNQ4B4Sk88n1IuPBJdAaF4zPmJJIAIFOAvIVpTOCDJAN5PvNkdzagq0Srp2xOZGpo2/BeC6xk2dbqGkv7fqs+TBr/tjWB7Rp1qgNvHoziJbPkYXVMSX9cWbHpk85GirMdKYbtKIipFfQyqhjmnOm25SOzyxwb7Ryl6+nmNNGZKhsd56CbalXvtO6QOd7O7fEWdVY1xKYy6jmJrmg1vT1orGfyA0ARTjQ3FclVvm8XvV/t/P1r0/v8CZ3UDfLtfRyIDPwBpqR9JXgBAAA=");
        case YELLOW_EASTER_EGG: default:
            return Items.deserialize("H4sIAAAAAAAAAE2Pu1LCQBiFf26KYYbOxsJhUmkXQGCgU67JkERuCUnjLMlCFjaBSTaSxPFRfAcLS0stfATfxtBZnnO+U3wcQA5KPcSQhv2A7D0A7qoIWWLDpUs8bPlozToHimLsPzkY2RzkGNpwcG6T4FQXIa8gF8P1C89wxPgO30cBw36lv9lUbmJM6f54y78CB9xsF1KqHj3sp+HR3x+wzwgOLqB4eoY+DjgAyBShoCEaYnjHsSSYS0ewlxK1YrGZ5vlMoKq4PbRET4tXXbEpuuk+um+O4/Y/tsGQ3qBGXXJMbxKuXE0Y16cUj6ZVy108m3NpJyf9ujkfOPJcvJNrAyK7Rk3pWYK5VRyltojUnlhV9UVNHvYFJdlFxtaI5ESOVF1zTX2aMhoxhwOi9nYNOZmQ9bLaBihBVrRTiXy5HP20tIf8x9fZ7/fnWy71gkJ3H3osA3/gulAhdAEAAA==");
        }
    }
}
