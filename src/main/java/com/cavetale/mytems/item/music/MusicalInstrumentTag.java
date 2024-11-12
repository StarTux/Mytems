package com.cavetale.mytems.item.music;

import com.cavetale.mytems.MytemTag;
import com.cavetale.mytems.Mytems;
import com.cavetale.worldmarker.util.Tags;
import java.util.Objects;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;

public final class MusicalInstrumentTag extends MytemTag {
    protected String flat;
    protected String sharp;

    @Override
    public boolean isEmpty() {
        return super.isEmpty() && musicalInstrumentIsEmpty();
    }

    public boolean musicalInstrumentIsEmpty() {
        return flat == null && sharp == null;
    }

    @Override
    public void load(Mytems mytems, ItemStack itemStack) {
        super.load(mytems, itemStack);
        PersistentDataContainer tag = itemStack.getItemMeta().getPersistentDataContainer();
        sharp = Tags.getString(tag, MusicalInstrument.SHARP_KEY);
        flat = Tags.getString(tag, MusicalInstrument.FLAT_KEY);
    }

    @Override
    public void store(Mytems mytems, ItemStack itemStack) {
        super.store(mytems, itemStack);
        if (!musicalInstrumentIsEmpty()) {
            itemStack.editMeta(meta -> {
                    PersistentDataContainer tag = meta.getPersistentDataContainer();
                    if (sharp != null) {
                        Tags.set(tag, MusicalInstrument.SHARP_KEY, sharp);
                    }
                    if (flat != null) {
                        Tags.set(tag, MusicalInstrument.FLAT_KEY, flat);
                    }
                    final MusicalInstrument musicalInstrument = (MusicalInstrument) mytems.getMytem();
                    musicalInstrument.updateLore(meta, sharp, flat);
                });
        }
    }

    @Override
    public boolean isDismissable() {
        return super.isEmpty();
    }

    @Override
    public boolean isSimilar(MytemTag other) {
        return super.isSimilar(other)
            && other instanceof MusicalInstrumentTag that
            && Objects.equals(this.flat, that.flat)
            && Objects.equals(this.sharp, that.sharp);
    }
}
