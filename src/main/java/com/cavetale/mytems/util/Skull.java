package com.cavetale.mytems.util;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public final class Skull {
    private Skull() { }

    public static ItemStack create(String name, UUID uuid, String texture) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.displayName(Component.text(name));
        PlayerProfile profile = Bukkit.createProfile(uuid);
        ProfileProperty property = new ProfileProperty("textures", texture, (String) null);
        profile.setProperty(property);
        meta.setPlayerProfile(profile);
        item.setItemMeta(meta);
        return item;
    }
}
