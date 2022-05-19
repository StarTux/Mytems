package com.cavetale.mytems.item.photo;

import com.cavetale.mytems.MytemTag;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Items;
import com.cavetale.worldmarker.util.Tags;
import com.winthier.playercache.PlayerCache;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.bukkit.persistence.PersistentDataContainer;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.item.photo.Photo.SEPIA;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;
import static net.kyori.adventure.text.format.TextDecoration.*;

public final class PhotoTag extends MytemTag {
    public static final String PHOTO_ID = "photo_id";
    protected Integer photoId; // != mapId

    @Override
    public boolean isEmpty() {
        return super.isEmpty()
            && photoId == null;
    }

    @Override
    public void load(ItemStack itemStack) {
        super.load(itemStack);
        if (!itemStack.hasItemMeta()) return;
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return;
        PersistentDataContainer tag = meta.getPersistentDataContainer();
        this.photoId = Tags.getInt(tag, MytemsPlugin.namespacedKey(PHOTO_ID));
    }

    @Override
    public void store(ItemStack itemStack) {
        super.store(itemStack);
        itemStack.editMeta(m -> {
                if (photoId != null) {
                    PersistentDataContainer tag = m.getPersistentDataContainer();
                    Tags.set(tag, MytemsPlugin.namespacedKey(PHOTO_ID), photoId);
                }
                if (!(m instanceof MapMeta meta)) return;
                PhotoData data = photoId != null
                    ? Photo.getPhotoData(photoId)
                    : Photo.getPhotoData(0);
                @SuppressWarnings("deprecation") MapView mapView = Bukkit.getMap(data.mapId());
                meta.setMapView(mapView);
                int hexColor = data.color() & 0xFFFFFF;
                meta.setColor(Color.fromRGB(hexColor));
                meta.setScaling(false);
                meta.setColor(Color.fromRGB(data.color()));
                meta.setLocationName(data.name());
                List<Component> tooltip = new ArrayList<>();
                tooltip.add(text((data.name() != null ? data.name() : "Photo"), color(hexColor)));
                if (data.name() != null) {
                    tooltip.add(text("Photo", DARK_GRAY, ITALIC));
                }
                if (data.owner() != null) {
                    String ownerName = PlayerCache.nameForUuid(data.owner());
                    tooltip.add(join(noSeparators(), text(tiny("owner "), GRAY), text(ownerName, color(SEPIA))));
                }
                tooltip.add(join(noSeparators(), text(tiny("see "), GRAY), text("/photo", color(SEPIA))));
                Items.text(meta, tooltip);
            });
    }

    @Override
    public boolean isDismissable() {
        return isEmpty();
    }
}
