package com.cavetale.mytems.item.photo;

import com.cavetale.core.util.Json;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.List;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextColor.color;

@RequiredArgsConstructor @Getter
public final class Photo implements Mytem {
    public static final int SEPIA = 0xEA8B57;
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    /**
     * Let the Photos plugin set this.
     */
    @Setter private static Function<Integer, PhotoData> photoDataGetter = null;
    @Setter private static Function<Integer, Integer> photoIdGetter = null;

    @Override
    public void enable() {
        displayName = text("Photo", color(SEPIA));
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                tooltip(meta, List.of(displayName));
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public PhotoTag serializeTag(ItemStack itemStack) {
        PhotoTag tag = new PhotoTag();
        tag.load(key, itemStack);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        ItemStack result = prototype.clone();
        PhotoTag tag = Json.deserialize(serialized, PhotoTag.class);
        if (tag != null) tag.store(key, result);
        return result;
    }

    @Override
    public boolean isMassStorable() {
        return false;
    }

    protected static PhotoData getPhotoData(int photoId) {
        if (photoDataGetter == null) return PhotoData.ZERO;
        PhotoData result = photoDataGetter.apply(photoId);
        return result != null
            ? photoDataGetter.apply(photoId)
            : PhotoData.ZERO;
    }

    public static ItemStack createItemStack(int photoId) {
        PhotoTag tag = new PhotoTag();
        tag.photoId = photoId;
        ItemStack itemStack = Mytems.PHOTO.createItemStack();
        tag.store(Mytems.PHOTO, itemStack);
        return itemStack;
    }

    public static int getPhotoId(ItemStack itemStack) {
        PhotoTag tag = new PhotoTag();
        tag.load(Mytems.PHOTO, itemStack);
        if (tag.photoId == null) {
            return 0;
        }
        return tag.photoId;
    }

    public static ItemStack fixLegacyPhoto(ItemStack itemStack) {
        if (photoIdGetter == null) return null;
        if (!itemStack.hasItemMeta()) return null;
        MapMeta meta = (MapMeta) itemStack.getItemMeta();
        if (!meta.hasMapView()) return null;
        MapView mapView = meta.getMapView();
        if (mapView == null) return null;
        int mapId = mapView.getId();
        int photoId = photoIdGetter.apply(mapId);
        if (photoId <= 0) return null;
        ItemStack result = createItemStack(photoId);
        result.setAmount(itemStack.getAmount());
        return result;
    }
}
