package com.cavetale.mytems.item.vote;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.MytemOwner;
import com.cavetale.mytems.MytemPersistenceFlag;
import com.cavetale.mytems.MytemTag;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Json;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@RequiredArgsConstructor @Getter
abstract class VoteItem implements Mytem {
    protected final Mytems key;
    protected ItemStack prototype;
    protected Component displayName;
    protected final Random random = new Random();

    @Override
    public ItemStack getItem() {
        return prototype.clone();
    }

    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public ItemStack createItemStack(Player player) {
        ItemStack itemStack = prototype.clone();
        ItemMeta meta = itemStack.getItemMeta();
        List<Component> lore = meta.lore();
        lore.addAll(getThanksLore(player.getName()));
        meta.lore(lore);
        MytemOwner.setItemMeta(player, meta);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public Set<MytemPersistenceFlag> getMytemPersistenceFlags() {
        return MytemPersistenceFlag.OWNER.toSet();
    }

    @Override
    public final ItemStack deserializeTag(String serialized) {
        MytemTag tag = Json.deserialize(serialized, MytemTag.class, MytemTag::new);
        ItemStack itemStack = prototype.clone();
        tag.store(itemStack, getMytemPersistenceFlags());
        if (tag.getOwner() != null) {
            ItemMeta meta = itemStack.getItemMeta();
            List<Component> lore = meta.lore();
            lore.addAll(getThanksLore(tag.getOwner().getName()));
            meta.lore(lore);
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    protected static List<Component> getThanksLore(String playerName) {
        Component[] components = {
            Component.text("Thank you for voting, ").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)
            .append(Component.text(playerName).color(NamedTextColor.GOLD))
            .append(Component.text("!").color(NamedTextColor.WHITE)),
            Component.text("Your vote helps us get more").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
            Component.text("players on the server.").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false),
            Component.text(""),
            Component.text("Sincerely, ").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)
            .append(Component.text("cavetale.com").color(NamedTextColor.GRAY).decorate(TextDecoration.ITALIC))
        };
        return Arrays.asList(components);
    }
}
