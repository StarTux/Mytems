package com.cavetale.mytems.item.easter;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter @RequiredArgsConstructor
public final class EasterEgg implements Mytem {
    private final Mytems key;
    private BaseComponent[] displayName;
    private ItemStack prototype;

    @Override
    public void enable() {
        String name = Stream.of(key.name().split("_"))
            .map(s -> s.substring(0, 1) + s.substring(1).toLowerCase())
            .collect(Collectors.joining(" "));
        ChatColor chatColor = getChatColor();
        displayName = Text.builder(name).color(chatColor).italic(false).create();
        prototype = getBaseItemStack();
        ItemMeta meta = prototype.getItemMeta();
        meta.setDisplayNameComponent(displayName);
        List<BaseComponent[]> lore = new ArrayList<>();
        lore.add(Text.builder("Easter Event").color(chatColor).italic(false).create());
        lore.add(Text.builder("Collected in the Easter").color(ChatColor.GRAY).italic(false).create());
        lore.add(Text.builder("Area. The portal at").color(ChatColor.GRAY).italic(false).create());
        lore.add(Text.builder("spawn can take you there.").color(ChatColor.GRAY).italic(false).create());
        meta.setLoreComponents(lore);
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
    }

    @Override
    public ItemStack getItem() {
        return prototype.clone();
    }

    public ChatColor getChatColor() {
        switch (key) {
        case BLUE_EASTER_EGG:
            return ChatColor.of("#8A2BE2");
        case GREEN_EASTER_EGG:
            return ChatColor.of("#00FF00");
        case PINK_EASTER_EGG:
            return ChatColor.of("#FFB6C1");
        case YELLOW_EASTER_EGG: default:
            return ChatColor.of("#FFFF00");
        }
    }

    @SuppressWarnings("LineLength")
    protected ItemStack getBaseItemStack() {
        switch (key) {
        case BLUE_EASTER_EGG:
            return Items.deserialize("H4sIAAAAAAAAAE2Pv07CUByFf/In1pq4uRgH0sQEE4dWBQMJgwGCt7FVoBZ6F3NpL6Xl3kLaW6Gos8/gSzj5AL6Fb+HsaNkcv3PONxwZoAj7HSKITeMkWEQA8pEEhcCDQx5E1I3JVDSXjGQ0fpxR4slQFMSXYdcLkm0sQckknMLxsyLoWihNpUsSQeNK1/cr1QlL6anyCjLIw3nK2N0qonEO9/FiSWMR0GQPpK2XxjSRAWBHgrJNcgs+aaareDxTvbHO3AzVc7aGKrtD4fIKRXY2aaM64nl/c12/zRr/tjVBRjXmXOgzHPXTCbfV24sBozcDzeUPT3hkB8bG0AzLz3APaTg0A2z5K8zNEIeYOSOdmda8hsP+2uh4c7OHudF72LKKQ0M1LFd1zm3uWJgZHGnmxrlEkdaY9lstgH0oIC8/Ujp7O3n5GuvV94Ofn4/O73f+DcrtRRqJHfgDrqRv/HYBAAA=");
        case GREEN_EASTER_EGG:
            return Items.deserialize("H4sIAAAAAAAAAE2Py07CQBiFf24Ry9qNiQmpMcEdF8FAwsJQAm0oWCiFzsYMdChtp4VMp0prND6FS5/CJzG+gq/g2rJzdXIu3+IIADkoSZhjg7DQ2QUAwnkRso4FZ74TkDXDG97ZUxwT9rAl2BIgx7EtwInlhMe4CPkx9glcPIucHLjYEfs45ISV+7ZdrtiMkOBafAEBhJkXUTp5CghLzT3b7QnjDglPoXgEI0ZCAQAyRSgYmEYEPkmsVNFyW7WWCl3Hciv1+qxKJ7K7v5UDI1715Jbsp/3wrjWK2/+2TY4XTWo2lC0KtGjlG9VRY0rJcFpb+/NH01VcJBkecqk3rpsH5Pdv1ERLdewiXfHVunpQ9S1VfbOm6hYdD7QYSV4TDczYTOYHJKWM5CVooTWQaydI7ydyUGtvtG4XoARZ2UqP5K/ea79v6ujnw/5+vbQrX+k3KPR2UcAz8Ac6ftiVdwEAAA==");
        case PINK_EASTER_EGG:
            return Items.deserialize("H4sIAAAAAAAAAE2Pu07CUByH/9xiLZODi3EgTLpxN5AwEChwGtoK1EK7mAM90JZzCmlPxar4Eq6+gIkv4uRD+Bwmls3x+12GTwTIQL6HOTZIELpbH0C8ECDt2nDOXJ8sA7zirR3FMQnuHYJtETIcr0U4sd3wGAuQVTEjcPlc5OSRF1tFCYecBAVpvS5c7Vx/c108gAjidBNRqu19EiRwG2x3JOAuCU9BOP6igIQiAKQEyBmYRgQ+SSyXrLlTsucyXcaokbA+LVENebsb5BvxoosaiCX9sNMYxc1/2zrHszo1q7Jj+eNowYzSqDqhZDgpL9ndg6IbrjaYeCYzHMWzmdqTmTrrU02X9mpvXLb0Ttka9D2tRzfKk7Q3Z32meErF1C1P8aRYqaCaqqOaoqM98svN1bjdBshDGtmJQPbk7PDtuNrrx3v+5ef37Stxglx3G/k8BX/qY8ZibgEAAA==");
        case YELLOW_EASTER_EGG: default:
            return Items.deserialize("H4sIAAAAAAAAAE2Pu1LCQBiFf26KYYbOxsJhUmkXQGCgU67JkERuCUnjLMlCFjaBSTaSxPFRfAcLS0stfATfxtBZnnO+U3wcQA5KPcSQhv2A7D0A7qoIWWLDpUs8bPlozToHimLsPzkY2RzkGNpwcG6T4FQXIa8gF8P1C89wxPgO30cBw36lv9lUbmJM6f54y78CB9xsF1KqHj3sp+HR3x+wzwgOLqB4eoY+DjgAyBShoCEaYnjHsSSYS0ewlxK1YrGZ5vlMoKq4PbRET4tXXbEpuuk+um+O4/Y/tsGQ3qBGXXJMbxKuXE0Y16cUj6ZVy108m3NpJyf9ujkfOPJcvJNrAyK7Rk3pWYK5VRyltojUnlhV9UVNHvYFJdlFxtaI5ESOVF1zTX2aMhoxhwOi9nYNOZmQ9bLaBihBVrRTiXy5HP20tIf8x9fZ7/fnWy71gkJ3H3osA3/gulAhdAEAAA==");
        }
    }
}
