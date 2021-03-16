package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Text;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import java.util.UUID;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public final class BatMask extends AculaItem {
    private String description = ""
        + ChatColor.RED + "It is unknown who made this mask or for what purpose it was worn,"
        + " but a team of specialists on the occult and vampirism has determined that it resembles a bat.";
    private final UUID skullId = UUID.fromString("62e67882-f5a4-411b-816f-2f704b3feb51");
    @SuppressWarnings("LineLength")
    private final String skullTexture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjc2NjE5NjUyZmFmZWM5MGNlOThkZjUwMTNjNjNkYzZhNzc3NzZhYjI3ODczYjczZGFmYjJiNmJkZWIxODUifX19";

    public BatMask(final Mytems key) {
        super(key);
    }

    @Override
    public void enable() {
        displayName = creepify("Bat Mask", false);
        baseLore = Text.toBaseComponents(Text.wrapMultiline(description, Text.ITEM_LORE_WIDTH));
        prototype = create();
    }

    private ItemStack create() {
        @SuppressWarnings("LineLength")
        ItemStack item = makeSkull(skullId, "BatMask", skullTexture, null);
        ItemMeta meta = item.getItemMeta();
        AttributeModifier attr;
        attr = new AttributeModifier(UUID.randomUUID(), key.id, 10.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        meta.addAttributeModifier(Attribute.GENERIC_MAX_HEALTH, attr);
        attr = new AttributeModifier(UUID.randomUUID(), key.id, 2.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, attr);
        attr = new AttributeModifier(UUID.randomUUID(), key.id, 3.0, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD);
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, attr);
        key.markItemMeta(meta);
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack makeSkull(UUID uuid, String name, String texture, String signature) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        PlayerProfile profile = Bukkit.getServer().createProfile(uuid, name);
        ProfileProperty prop = new ProfileProperty("textures", texture, signature);
        profile.setProperty(prop);
        meta.setPlayerProfile(profile);
        item.setItemMeta(meta);
        return item;
    }
}
