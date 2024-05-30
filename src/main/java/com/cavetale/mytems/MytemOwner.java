package com.cavetale.mytems;

import com.cavetale.worldmarker.util.Tags;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

@Data @AllArgsConstructor @NoArgsConstructor
public final class MytemOwner {
    public static final NamespacedKey OWNER_KEY = NamespacedKey.fromString("mytems:owner");
    public static final NamespacedKey NAME_KEY = NamespacedKey.fromString("mytems:name");
    public static final NamespacedKey UUID_KEY = NamespacedKey.fromString("mytems:uuid");
    @NonNull protected UUID uuid;
    @NonNull protected String name;

    public static MytemOwner ofItemStack(ItemStack itemStack) {
        if (!itemStack.hasItemMeta()) return null;
        return ofItemMeta(itemStack.getItemMeta());
    }

    public static MytemOwner ofItemMeta(ItemMeta itemMeta) {
        return ofTag(itemMeta.getPersistentDataContainer());
    }

    public static MytemOwner ofTag(PersistentDataContainer tag) {
        PersistentDataContainer ownerTag = Tags.getTag(tag, OWNER_KEY);
        if (ownerTag == null) return null;
        String name = Tags.getString(ownerTag, NAME_KEY);
        if (name == null) return null;
        String uuidString = Tags.getString(ownerTag, UUID_KEY);
        if (uuidString == null) return null;
        UUID uuid;
        try {
            uuid = UUID.fromString(uuidString);
        } catch (IllegalArgumentException iae) {
            return null;
        }
        return new MytemOwner(uuid, name);
    }

    public void setItemStack(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        setItemMeta(itemMeta);
        itemStack.setItemMeta(itemMeta);
    }

    public void setItemMeta(ItemMeta itemMeta) {
        setTag(itemMeta.getPersistentDataContainer());
    }

    public void setTag(PersistentDataContainer tag) {
        Objects.requireNonNull(uuid, "uuid cannot be null");
        Objects.requireNonNull(name, "name cannot be null");
        PersistentDataContainer ownerTag = Tags.createTag(tag);
        Tags.set(ownerTag, UUID_KEY, uuid.toString());
        Tags.set(ownerTag, NAME_KEY, name);
        Tags.set(tag, OWNER_KEY, ownerTag);
    }

    public static MytemOwner ofPlayer(Player player) {
        return new MytemOwner(player.getUniqueId(), player.getName());
    }

    public boolean isValid() {
        return uuid != null && name != null;
    }

    public static void setItemStack(Player player, ItemStack itemStack) {
        MytemOwner.ofPlayer(player).setItemStack(itemStack);
    }

    public static void setItemMeta(Player player, ItemMeta itemMeta) {
        MytemOwner.ofPlayer(player).setItemMeta(itemMeta);
    }

    public static void setTag(Player player, PersistentDataContainer tag) {
        MytemOwner.ofPlayer(player).setTag(tag);
    }
}
