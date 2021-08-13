package com.cavetale.mytems.item.pocketmob;

import com.cavetale.mytems.MytemTag;
import com.cavetale.mytems.util.Json;
import com.cavetale.worldmarker.util.Tags;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

/**
 * The data stored in a PocketMob item.
 */
@Data @EqualsAndHashCode(callSuper = true)
public final class PocketMobTag extends MytemTag {
    public static final NamespacedKey KEY_MOB_TAG = NamespacedKey.fromString("pocketmob:mob_tag");
    protected String mobTag;
    public static final TextColor COLOR_FG = TextColor.color(0xffa600); // gold
    public static final TextColor COLOR_BG = TextColor.color(0x3f274d); // pale purle

    @Override
    public boolean isEmpty() {
        return mobTag == null && super.isEmpty();
    }

    public void load(ItemStack itemStack, PocketMob pocketMob) {
        super.load(itemStack, pocketMob.getMytemPersistenceFlags());
        if (!itemStack.hasItemMeta()) return;
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer tag = meta.getPersistentDataContainer();
        mobTag = Tags.getString(tag, KEY_MOB_TAG);
    }

    /**
     * This method stores this tag in the item and updates its lore
     * accordingly.
     */
    public void store(ItemStack itemStack, PocketMob pocketMob) {
        super.store(itemStack, pocketMob.getMytemPersistenceFlags());
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer tag = meta.getPersistentDataContainer();
        if (mobTag != null) {
            Tags.set(tag, KEY_MOB_TAG, mobTag);
        }
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text().append(Component.text("Pocket Mob: ", COLOR_BG))
                 .append(Component.text(pocketMob.getEntityTypeName(), COLOR_FG))
                 .decoration(TextDecoration.ITALIC, false).build());
        Map<String, Object> map = parseMobTag();
        Object o;
        o = map.get("Health");
        if (o instanceof Number) {
            lore.add(Component.text().append(Component.text("Health: ", COLOR_BG))
                     .append(Component.text(((Number) o).intValue(), COLOR_FG))
                     .decoration(TextDecoration.ITALIC, false).build());
        }
        meta.lore(lore);
        itemStack.setItemMeta(meta);
    }

    public Map<String, Object> parseMobTag() {
        if (mobTag == null) return new HashMap<>();
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> result = (Map<String, Object>) Json.deserialize(mobTag, Map.class);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
