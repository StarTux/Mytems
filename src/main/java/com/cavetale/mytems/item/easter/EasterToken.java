package com.cavetale.mytems.item.easter;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter @RequiredArgsConstructor
public final class EasterToken implements Mytem {
    private final Mytems key;
    private BaseComponent[] displayName;
    private ItemStack prototype;

    @Override
    public void enable() {
        ChatColor chatColor = ChatColor.of("#FFB6C1");
        displayName = Text.builder("Easter Token").color(chatColor).italic(false).bold(true).create();
        prototype = getBaseItemStack();
        ItemMeta meta = prototype.getItemMeta();
        meta.setDisplayNameComponent(displayName);
        List<BaseComponent[]> lore = new ArrayList<>();
        lore.add(Text.builder("Easter Event").color(chatColor).italic(false).create());
        lore.add(Text.builder("Trade this for cool").color(ChatColor.GRAY).italic(false).create());
        lore.add(Text.builder("prizes at spawn.").color(ChatColor.GRAY).italic(false).create());
        lore.add(Text.builder("Only until Easter!").color(ChatColor.GRAY).italic(false).create());
        meta.setLoreComponents(lore);
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
    }

    @Override
    public ItemStack getItem() {
        return prototype.clone();
    }

    @SuppressWarnings("LineLength")
    protected ItemStack getBaseItemStack() {
        return Items.deserialize("H4sIAAAAAAAAAE2PS07CQByH/7wiljUuXBniAQoNGEhMVCAwDZ1SHi1DTMxAB2mZDqQPoTWewRu48wbGa3gEE8/gASw7l9/vsfgkgByUOjSkJvMDZysApPMiZB0byp4j2NKnq7C14zRm/sOaUVuCXEgfJTixneAYFyGPqceg/FwJ2SGstCpDR2wu7iIh4soLSCCNNxHn+l4wP4Whv90xP3RYcArF4yHyWSABQKYIBZPyiMEHi1V5PlvL9kzlyxg1Up6MZa4jd3eFhBkv2qiBvLTv3zYGcfPfth5Sq86Joq7nwogWnikPlBFn/VF16U2fNAslZGKvyYTUSW3k4E43xjWjiq3ugbiGgr2RhxOjqvXIXktUh1iGQpJuVbewq09slyScY9fkWgclmmvUsTWV5221uZrJ1wAlyCI7Fcl/37yTrx4KXj9/3s7ufy9TNyi0t5EIM/AH+4LNoW8BAAA=");
    }
}
