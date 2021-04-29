package com.cavetale.mytems.item.pocketmob;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.MytemPersistenceFlag;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Text;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Getter @RequiredArgsConstructor
public final class PocketMob implements Mytem {
    private final Mytems key;
    private final EntityType entityType;
    private Component displayName;
    private ItemStack prototype;

    @Override
    public void enable() {
        displayName = Component.text(Text.toCamelCase(key, " ")).decoration(TextDecoration.ITALIC, false);
        prototype = new ItemStack(key.material).ensureServerConversions();
        ItemMeta meta = prototype.getItemMeta();
        meta.displayName(displayName);
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public Set<MytemPersistenceFlag> getMytemPersistenceFlags() {
        return MytemPersistenceFlag.DISPLAY_NAME.toSet();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }
}
