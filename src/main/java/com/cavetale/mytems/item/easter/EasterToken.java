package com.cavetale.mytems.item.easter;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static com.cavetale.mytems.util.Items.deserialize;
import static net.kyori.adventure.text.Component.text;

@Getter @RequiredArgsConstructor
public final class EasterToken implements Mytem {
    private final Mytems key;
    private Component displayName;
    private ItemStack prototype;

    @Override
    public void enable() {
        TextColor chatColor = TextColor.color(0xFFB6C1);
        displayName = text("Easter Token").color(chatColor).decoration(TextDecoration.ITALIC, false).decorate(TextDecoration.BOLD);
        prototype = getBaseItemStack();
        ItemMeta meta = prototype.getItemMeta();
        meta.displayName(displayName);
        List<Component> lore = new ArrayList<>();
        lore.add(text("Easter Event").color(chatColor).decoration(TextDecoration.ITALIC, false));
        lore.add(text("Trade this for cool").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(text("prizes at spawn.").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        lore.add(text("Only until Easter!").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @SuppressWarnings("LineLength")
    protected ItemStack getBaseItemStack() {
        return deserialize("H4sIAAAAAAAAAE2PS07CQByH/7wiljUuXBniAQoNGEhMVCAwDZ1SHi1DTMxAB2mZDqQPoTWewRu48wbGa3gEE8/gASw7l9/vsfgkgByUOjSkJvMDZysApPMiZB0byp4j2NKnq7C14zRm/sOaUVuCXEgfJTixneAYFyGPqceg/FwJ2SGstCpDR2wu7iIh4soLSCCNNxHn+l4wP4Whv90xP3RYcArF4yHyWSABQKYIBZPyiMEHi1V5PlvL9kzlyxg1Up6MZa4jd3eFhBkv2qiBvLTv3zYGcfPfth5Sq86Joq7nwogWnikPlBFn/VF16U2fNAslZGKvyYTUSW3k4E43xjWjiq3ugbiGgr2RhxOjqvXIXktUh1iGQpJuVbewq09slyScY9fkWgclmmvUsTWV5221uZrJ1wAlyCI7Fcl/37yTrx4KXj9/3s7ufy9TNyi0t5EIM/AH+4LNoW8BAAA=");
    }
}
