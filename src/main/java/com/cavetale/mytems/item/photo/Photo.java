package com.cavetale.mytems.item.photo;

import com.cavetale.core.util.Json;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import java.util.List;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextColor.color;

@RequiredArgsConstructor @Getter
public final class Photo implements Mytem {
    public static final int SEPIA = 0x9E5B40;
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    /**
     * Let the Photos plugin set this.
     */
    @Setter private static Function<Integer, PhotoData> photoDataGetter = null;

    @Override
    public void enable() {
        displayName = text("Photo", color(SEPIA));
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                Items.text(meta, List.of(displayName));
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
        tag.load(itemStack);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        ItemStack result = prototype.clone();
        PhotoTag tag = Json.deserialize(serialized, PhotoTag.class);
        if (tag != null) tag.store(result);
        return result;
    }

    @Override
    public boolean isMassStorable() {
        return false;
    }

    protected static PhotoData getPhotoData(int photoId) {
        return photoDataGetter != null
            ? photoDataGetter.apply(photoId)
            : new PhotoData(null, 0, SEPIA, "Photo");
    }
}
