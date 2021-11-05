package com.cavetale.mytems.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

@Data
public final class Skull {
    protected String name;
    protected UUID uuid;
    protected String texture;
    protected String signature;
    protected List<DummyProperty> properties;

    public ItemStack create() {
        return create(this.name, this.uuid, this.texture, this.signature);
    }

    public static Skull of(ItemStack itemStack) {
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        PlayerProfile profile = meta.getPlayerProfile();
        Skull skull = new Skull();
        skull.name = profile.getName();
        skull.uuid = profile.getId();
        for (ProfileProperty property : profile.getProperties()) {
            if ("textures".equals(property.getName())) {
                skull.texture = property.getValue();
                skull.signature = property.getSignature();
            } else {
                DummyProperty dummyProperty = new DummyProperty();
                if (skull.properties == null) {
                    skull.properties = new ArrayList<>();
                }
                skull.properties.add(dummyProperty);
                dummyProperty.name = property.getName();
                dummyProperty.value = property.getValue();
                dummyProperty.signature = property.getSignature();
            }
        }
        return skull;
    }

    public static ItemStack create(String name,
                                   UUID uuid,
                                   @NonNull String texture,
                                   String signature) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        PlayerProfile profile = Bukkit.createProfile(uuid, name);
        ProfileProperty property = new ProfileProperty("textures", texture, signature);
        profile.setProperty(property);
        meta.setPlayerProfile(profile);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack create(String name, UUID uuid, String texture) {
        return create(name, uuid, texture, null);
    }

    public static final class DummyProperty {
        protected String name;
        protected String value;
        protected String signature;
    }
}
