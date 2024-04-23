package com.cavetale.mytems.item.luminator;

import com.cavetale.mytems.MytemTag;
import com.cavetale.mytems.Mytems;
import com.cavetale.worldmarker.util.Tags;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataContainer;
import static com.cavetale.mytems.MytemsPlugin.namespacedKey;
import static com.cavetale.mytems.util.Items.tooltip;

@Getter
public final class LuminatorTag extends MytemTag {
    protected int light = 0;

    @Override
    public boolean isEmpty() {
        return super.isEmpty()
            && light == 0;
    }

    @Override
    public void load(ItemStack itemStack) {
        super.load(itemStack);
        if (!itemStack.hasItemMeta()) return;
        PersistentDataContainer tag = itemStack.getItemMeta().getPersistentDataContainer();
        Integer l = Tags.getInt(tag, namespacedKey("light"));
        this.light = l != null ? l : 0;
    }

    @Override
    public void store(ItemStack itemStack) {
        super.store(itemStack);
        itemStack.editMeta(meta -> {
                PersistentDataContainer tag = meta.getPersistentDataContainer();
                if (light == 0) {
                    tag.remove(namespacedKey("light"));
                } else {
                    Tags.set(tag, namespacedKey("light"), light);
                }
                if (meta instanceof Damageable damageable) {
                    if (light == 0) {
                        damageable.setDamage(0);
                    } else {
                        final int max = itemStack.getType().getMaxDurability();
                        final int filled = (light * max) / Luminator.MAX_LIGHT;
                        damageable.setDamage(Math.max(1, Math.min(max - 1, max - filled)));
                    }
                }
                tooltip(meta, ((Luminator) Mytems.LUMINATOR.getMytem()).baseTextWithLight(light));
            });
    }

    @Override
    public boolean isDismissable() {
        return super.isDismissable() && light == 0;
    }

    @Override
    public boolean isSimilar(MytemTag other) {
        return super.isSimilar(other)
            && other instanceof LuminatorTag tag
            && this.light == tag.light;
    }
}
