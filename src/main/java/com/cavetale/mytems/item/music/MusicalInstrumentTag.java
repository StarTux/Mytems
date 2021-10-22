package com.cavetale.mytems.item.music;

import com.cavetale.mytems.MytemTag;
import com.cavetale.worldmarker.util.Tags;
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

    public void load(ItemStack itemStack, MusicalInstrument instance) {
        super.load(itemStack, instance.getMytemPersistenceFlags());
        PersistentDataContainer tag = itemStack.getItemMeta().getPersistentDataContainer();
        sharp = Tags.getString(tag, MusicalInstrument.SHARP_KEY);
        flat = Tags.getString(tag, MusicalInstrument.FLAT_KEY);
    }

    public void store(ItemStack itemStack, MusicalInstrument instance) {
        super.store(itemStack, instance.getMytemPersistenceFlags());
        if (!musicalInstrumentIsEmpty()) {
            itemStack.editMeta(meta -> {
                    PersistentDataContainer tag = meta.getPersistentDataContainer();
                    if (sharp != null) {
                        Tags.set(tag, MusicalInstrument.SHARP_KEY, sharp);
                    }
                    if (flat != null) {
                        Tags.set(tag, MusicalInstrument.FLAT_KEY, flat);
                    }
                    instance.updateLore(meta, sharp, flat);
                });
        }
    }
}
