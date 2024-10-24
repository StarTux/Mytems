package com.cavetale.mytems.item.music;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import static com.cavetale.mytems.util.Items.clearAttributes;
import static com.cavetale.mytems.util.Items.tooltip;
import static java.util.Objects.requireNonNull;
import static net.kyori.adventure.text.Component.text;

public final class MusicalNote implements Mytem {
    @Getter private final Mytems key;
    @Getter private final MusicalNoteType musicalNoteType;
    @Getter private Component displayName;
    private ItemStack prototype;

    public MusicalNote(final Mytems key) {
        this.key = key;
        this.musicalNoteType = requireNonNull(MusicalNoteType.of(key));
    }

    @Override
    public void enable() {
        displayName = text(musicalNoteType.getDisplayName());
        prototype = new ItemStack(key.material);
        prototype.editMeta(LeatherArmorMeta.class, meta -> meta.setColor(Color.fromRGB(0x202020)));
        prototype.editMeta(meta -> {
                tooltip(meta, List.of(displayName));
                meta.addItemFlags(ItemFlag.values());
                clearAttributes(meta);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public boolean isAvailableToPlayers() {
        return false;
    }

    @Override
    public boolean isMassStorable() {
        return false;
    }
}
