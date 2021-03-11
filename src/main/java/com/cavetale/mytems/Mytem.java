package com.cavetale.mytems;

import com.cavetale.mytems.util.Json;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public interface Mytem {
    Mytems getKey();

    void enable();

    default void disable() { }

    /**
     * Create a fresh copy.
     */
    ItemStack getItem();

    BaseComponent[] getDisplayName();

    default void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) { }

    default void onPlayerFallDamage(EntityDamageEvent event, Player player, ItemStack item) { }

    default void onPlayerShootBow(EntityShootBowEvent event, Player player, ItemStack item) { }

    default void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) { }

    default void onToggleGlide(EntityToggleGlideEvent event, Player player, ItemStack item) { }

    default Set<ItemFixFlag> getItemFixFlags() {
        return Collections.emptySet();
    }

    final class Tag {
        Map<String, Integer> enchantments;
        Integer durability;
        Integer amount;
    }

    /**
     * These can be overridden entirely. The default version attempts
     * to respect the ItemFixFlags.
     */
    default String serializeTag(ItemStack itemStack) {
        Set<ItemFixFlag> flags = getItemFixFlags();
        int amount = itemStack.getAmount();
        if (amount == 1 && (flags == null || flags.isEmpty())) return null;
        Tag tag = new Tag();
        if (amount != 1) tag.amount = amount;
        if (flags.contains(ItemFixFlag.COPY_ENCHANTMENTS)) {
            tag.enchantments = new HashMap<>();
            for (Map.Entry<Enchantment, Integer> entry : itemStack.getEnchantments().entrySet()) {
                Enchantment enchantment = entry.getKey();
                NamespacedKey namespacedKey = enchantment.getKey();
                String mapKey = "minecraft".equals(namespacedKey.getNamespace())
                    ? namespacedKey.getKey()
                    : namespacedKey.toString();
                Integer level = entry.getValue();
                tag.enchantments.put(mapKey, level);
            }
        }
        if (flags.contains(ItemFixFlag.COPY_DURABILITY)) {
            ItemMeta meta = itemStack.getItemMeta();
            if (meta instanceof Damageable) {
                int damage = ((Damageable) meta).getDamage();
                if (damage != 0) tag.durability = damage;
            }
        }
        if (tag.enchantments == null && tag.durability == null && tag.amount == null) return null;
        return Json.simplified(tag);
    }

    /**
     * These can be overridden entirely. The default version attempts
     * to respect the ItemFixFlags.
     */
    default ItemStack deserializeTag(String serialized) {
        ItemStack itemStack = getItem();
        Tag tag = Json.deserialize(serialized, Tag.class);
        if (tag == null) {
            MytemsPlugin.getInstance().getLogger().warning("Invalid item tag: " + serialized);
            return itemStack;
        }
        if (tag.amount != null) {
            itemStack.setAmount(tag.amount);
        }
        for (ItemFixFlag flag : getItemFixFlags()) {
            switch (flag) {
            case COPY_ENCHANTMENTS:
                if (tag.enchantments == null) continue;
                for (Map.Entry<String, Integer> entry : tag.enchantments.entrySet()) {
                    String keyString = entry.getKey();
                    Integer level = entry.getValue();
                    NamespacedKey key;
                    try {
                        key = keyString.contains(":")
                            ? NamespacedKey.fromString(keyString)
                            : NamespacedKey.minecraft(keyString);
                    } catch (IllegalArgumentException iae) {
                        MytemsPlugin.getInstance().getLogger().warning(keyString + ": " + serialized);
                        iae.printStackTrace();
                        continue;
                    }
                    Enchantment enchantment = Enchantment.getByKey(key);
                    if (enchantment == null) {
                        MytemsPlugin.getInstance().getLogger().warning("Enchantment not found: " + key + ": " + serialized);
                        continue;
                    }
                    itemStack.addUnsafeEnchantment(enchantment, level);
                }
                break;
            case COPY_DURABILITY:
                if (tag.durability == null) continue;
                ItemMeta meta = itemStack.getItemMeta();
                if (meta instanceof Damageable) {
                    ((Damageable) meta).setDamage(tag.durability);
                    itemStack.setItemMeta(meta);
                }
                break;
            default:
                break;
            }
        }
        return itemStack;
    }
}
