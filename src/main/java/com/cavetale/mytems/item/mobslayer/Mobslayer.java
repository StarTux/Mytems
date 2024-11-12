package com.cavetale.mytems.item.mobslayer;

import com.cavetale.core.font.Unicode;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Json;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Items.tooltip;
import static java.util.Objects.requireNonNull;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextColor.color;

@RequiredArgsConstructor @Getter
public final class Mobslayer implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private MobslayerTier tier;

    @Override
    public void enable() {
        tier = requireNonNull(MobslayerTier.of(key));
        final TextColor red = color(0xFF0000 - (0x330000 * tier.getTier()));
        this.displayName = text(Unicode.SKULL.string + tier.getDisplayName(), red);
        this.prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                tooltip(meta, List.of(displayName,
                                      text(tiny("Neither living nor dead"), red),
                                      text(tiny("may escape the wrath"), red),
                                      text(tiny("of this blade."), red)));
                meta.setUnbreakable(true);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public MobslayerTag serializeTag(ItemStack item) {
        final MobslayerTag tag = tier.createTag();
        tag.load(key, item);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        final ItemStack itemStack = createItemStack();
        final MobslayerTag tag = Json.deserialize(serialized, tier.getTagClass());
        if (tag != null) tag.store(key, itemStack);
        return itemStack;
    }
}
