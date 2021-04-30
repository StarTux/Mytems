package com.cavetale.mytems;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.Data;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * An intermediate data structure to hold (de)serialized items stored
 * in an ItemStack. It's serialized to String via Json.
 *
 * A Mytem object uses this internally.
 *
 * The store method performs additive operations only and never
 * removes something that's stored in the item, be it enchantments,
 * durability, amount, or owner.
 *
 * @see MytemPersistenceFlag
 */
@Data
public class MytemTag {
    protected Map<String, Integer> enchantments;
    protected Integer durability;
    protected Integer amount;
    protected MytemOwner owner;
    protected String displayNameJson;

    /**
     * Determine if this tag is empty and needs not be saved.
     */
    public boolean isEmpty() {
        return (enchantments == null || enchantments.isEmpty())
            && (durability == null || durability == 0)
            && (amount == null || amount == 1)
            && owner == null;
    }

    /**
     * Load this empty tag from an item.
     */
    public void load(ItemStack itemStack, Set<MytemPersistenceFlag> flags) {
        int itemAmount = itemStack.getAmount();
        if (itemAmount == 1 && (flags == null || flags.isEmpty())) return;
        MytemTag tag = new MytemTag();
        if (itemAmount != 1) amount = itemAmount;
        for (MytemPersistenceFlag flag : flags) {
            switch (flag) {
            case ENCHANTMENTS:
                for (Map.Entry<Enchantment, Integer> entry : itemStack.getEnchantments().entrySet()) {
                    Enchantment enchantment = entry.getKey();
                    NamespacedKey namespacedKey = enchantment.getKey();
                    String mapKey = "minecraft".equals(namespacedKey.getNamespace())
                        ? namespacedKey.getKey()
                        : namespacedKey.toString();
                    Integer level = entry.getValue();
                    if (enchantments == null) {
                        enchantments = new HashMap<>();
                    }
                    enchantments.put(mapKey, level);
                }
                break;
            case DURABILITY: {
                if (!itemStack.hasItemMeta()) continue;
                ItemMeta meta = itemStack.getItemMeta();
                if (meta instanceof Damageable) {
                    int damage = ((Damageable) meta).getDamage();
                    if (damage != 0) durability = damage;
                }
                break;
            }
            case OWNER: {
                if (!itemStack.hasItemMeta()) continue;
                ItemMeta meta = itemStack.getItemMeta();
                MytemOwner itemOwner = MytemOwner.ofItemMeta(meta);
                if (itemOwner != null && itemOwner.isValid()) {
                    owner = itemOwner;
                }
                break;
            }
            case DISPLAY_NAME: {
                if (!itemStack.hasItemMeta()) continue;
                ItemMeta meta = itemStack.getItemMeta();
                Component displayName = meta.displayName();
                if (displayName == null) continue;
                displayNameJson = GsonComponentSerializer.gson().serialize(displayName);
                break;
            }
            default:
                break;
            }
        }
    }

    /**
     * Store the contents of this tag in a new item.
     */
    public void store(ItemStack itemStack, Set<MytemPersistenceFlag> flags) {
        if (amount != null) {
            itemStack.setAmount(amount);
        }
        if (flags == null || flags.isEmpty()) return;
        for (MytemPersistenceFlag flag : flags) {
            switch (flag) {
            case ENCHANTMENTS: {
                if (enchantments == null) continue;
                for (Map.Entry<String, Integer> entry : enchantments.entrySet()) {
                    String keyString = entry.getKey();
                    Integer level = entry.getValue();
                    NamespacedKey key;
                    try {
                        key = keyString.contains(":")
                            ? NamespacedKey.fromString(keyString)
                            : NamespacedKey.minecraft(keyString);
                    } catch (IllegalArgumentException iae) {
                        MytemsPlugin.getInstance().getLogger().warning("Invalid NamespacedKey: " + keyString);
                        iae.printStackTrace();
                        continue;
                    }
                    Enchantment enchantment = Enchantment.getByKey(key);
                    if (enchantment == null) {
                        MytemsPlugin.getInstance().getLogger().warning("Enchantment not found: " + key);
                        continue;
                    }
                    itemStack.addUnsafeEnchantment(enchantment, level);
                }
                break;
            }
            case DURABILITY: {
                if (durability == null) continue;
                ItemMeta meta = itemStack.getItemMeta();
                if (meta instanceof Damageable) {
                    ((Damageable) meta).setDamage(durability);
                    itemStack.setItemMeta(meta);
                }
                break;
            }
            case OWNER: {
                if (owner == null || !owner.isValid()) continue;
                owner.setItemStack(itemStack);
                break;
            }
            case DISPLAY_NAME: {
                if (displayNameJson == null) continue;
                Component displayName;
                try {
                    displayName = GsonComponentSerializer.gson().deserialize(displayNameJson);
                } catch (Exception e) {
                    MytemsPlugin.getInstance().getLogger().warning("Invalid display name JSON: " + displayNameJson);
                    continue;
                }
                ItemMeta meta = itemStack.getItemMeta();
                meta.displayName(displayName);
                itemStack.setItemMeta(meta);
                break;
            }
            default:
                break;
            }
        }
    }

    public final Component getDisplayName() {
        return displayNameJson == null
            ? Component.empty()
            : GsonComponentSerializer.gson().deserialize(displayNameJson);
    }

    public final void setDisplayName(Component component) {
        displayNameJson = component == null
            ? null
            : GsonComponentSerializer.gson().serialize(component);
    }
}
