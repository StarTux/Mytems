package com.cavetale.mytems;

import com.cavetale.mytems.util.Json;
import java.util.Set;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public interface Mytem {
    Mytems getKey();

    void enable();

    default void disable() { }

    /**
     * Create a fresh copy.
     */
    ItemStack createItemStack();

    /**
     * Create a fresh copy for the player.
     */
    default ItemStack createItemStack(Player player) {
        return createItemStack();
    }

    Component getDisplayName();

    default void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) { }

    default void onPlayerFallDamage(EntityDamageEvent event, Player player, ItemStack item) { }

    default void onPlayerShootBow(EntityShootBowEvent event, Player player, ItemStack item) { }

    default void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) { }

    default void onToggleGlide(EntityToggleGlideEvent event, Player player, ItemStack item) { }

    default void onConsume(PlayerItemConsumeEvent event, Player player, ItemStack item) { }

    default Set<MytemPersistenceFlag> getMytemPersistenceFlags() {
        return MytemPersistenceFlag.NONE;
    }

    /**
     * These can be overridden entirely. The default version attempts
     * to respect the MytemPersistenceFlags.
     */
    default String serializeTag(ItemStack itemStack) {
        MytemTag tag = new MytemTag();
        tag.load(itemStack, getMytemPersistenceFlags());
        return tag.isEmpty() ? null : Json.simplified(tag);
    }

    /**
     * These can be overridden entirely. The default version attempts
     * to respect the MytemPersistenceFlags.
     */
    default ItemStack deserializeTag(String serialized) {
        ItemStack itemStack = createItemStack();
        MytemTag tag = Json.deserialize(serialized, MytemTag.class);
        tag.store(itemStack, getMytemPersistenceFlags());
        return itemStack;
    }

    /**
     * Deserialize an item tag with a new owner.
     *
     * Overriders may accept a null for the player argument in order
     * to call it from the overload above, but the default
     * implementation will not.
     */
    default ItemStack deserializeTag(String serialized, Player player) {
        ItemStack itemStack = createItemStack();
        MytemTag tag = Json.deserialize(serialized, MytemTag.class);
        Set<MytemPersistenceFlag> flags = getMytemPersistenceFlags();
        if (flags.contains(MytemPersistenceFlag.OWNER)) {
            tag.setOwner(MytemOwner.ofPlayer(player));
        }
        tag.store(itemStack, flags);
        return itemStack;
    }
}
