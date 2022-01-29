package com.cavetale.mytems.item.farawaymap;

import com.cavetale.core.util.Json;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import com.winthier.connect.Connect;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.GRAY;

@RequiredArgsConstructor @Getter
public final class FarawayMap implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        this.displayName = text("Faraway Map", TextColor.color(0x398888));
        this.prototype = new ItemStack(key.material);
        List<Component> text = List.of(displayName,
                                       text("This is a map of", GRAY),
                                       text("lands far away.", GRAY),
                                       empty(),
                                       text("Its contents will", GRAY),
                                       text("be revealed when", GRAY),
                                       text("you return there.", GRAY));
        prototype.editMeta(meta -> {
                Items.text(meta, text);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public FarawayMapTag serializeTag(ItemStack itemStack) {
        FarawayMapTag tag = new FarawayMapTag();
        tag.load(itemStack);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        FarawayMapTag tag = Json.deserialize(serialized, FarawayMapTag.class);
        if (tag == null) return createItemStack();
        if (Connect.getInstance().getServerName().equals(tag.server)) {
            ItemStack itemStack = new ItemStack(Material.FILLED_MAP);
            tag.storeMap(itemStack);
            return itemStack;
        } else {
            ItemStack itemStack = createItemStack();
            tag.store(itemStack);
            return itemStack;
        }
    }
}
