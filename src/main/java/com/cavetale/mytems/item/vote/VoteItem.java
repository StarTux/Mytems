package com.cavetale.mytems.item.vote;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.MytemOwner;
import com.cavetale.mytems.MytemPersistenceFlag;
import com.cavetale.mytems.MytemTag;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Json;
import com.cavetale.mytems.util.Text;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@RequiredArgsConstructor @Getter
abstract class VoteItem implements Mytem {
    protected final Mytems key;
    protected ItemStack prototype;
    protected BaseComponent[] displayName;
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
        List<BaseComponent[]> lore = meta.getLoreComponents();
        lore.addAll(VoteItem.getThanksLore(player.getName()));
        meta.setLoreComponents(lore);
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
            List<BaseComponent[]> lore = meta.getLoreComponents();
            lore.addAll(getThanksLore(tag.getOwner().getName()));
            meta.setLoreComponents(lore);
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    protected static List<BaseComponent[]> getThanksLore(String playerName) {
        BaseComponent[][] components = {
            Text.builder("Thank you for voting, ").color(ChatColor.WHITE).italic(false)
            .append(playerName).color(ChatColor.GOLD).append("!").color(ChatColor.WHITE).create(),
            Text.builder("Your vote helps us get more").color(ChatColor.WHITE).italic(false).create(),
            Text.builder("players on the server.").color(ChatColor.WHITE).italic(false).create(),
            Text.builder("").create(),
            Text.builder("Sincerely, ").color(ChatColor.WHITE).italic(false)
            .append("cavetale.com").color(ChatColor.GRAY).italic(true).create()
        };
        return Arrays.asList(components);
    }
}
