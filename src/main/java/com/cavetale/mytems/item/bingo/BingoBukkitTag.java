package com.cavetale.mytems.item.bingo;

import com.cavetale.mytems.MytemTag;
import com.cavetale.worldmarker.util.Tags;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.MytemsPlugin.namespacedKey;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public final class BingoBukkitTag extends MytemTag {
    protected transient BingoBukkitType type = BingoBukkitType.DEFAULT;
    protected int water = 0;

    public BingoBukkitTag() { }

    public BingoBukkitTag(final BingoBukkitType type) {
        this.type = type;
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty()
            && water == 0;
    }

    @Override
    public void load(ItemStack itemStack) {
        super.load(itemStack);
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer tag = meta.getPersistentDataContainer();
        Integer w = Tags.getInt(tag, namespacedKey("water"));
        if (w != null) water = w;
    }

    @Override
    public void store(ItemStack itemStack) {
        super.store(itemStack);
        itemStack.editMeta(meta -> {
                PersistentDataContainer tag = meta.getPersistentDataContainer();
                if (water == 0) {
                    tag.remove(namespacedKey("water"));
                } else {
                    Tags.set(tag, namespacedKey("water"), water);
                }
                if (meta instanceof Damageable damageable) {
                    if (water == 0) {
                        damageable.setDamage(0);
                    } else {
                        int max = type.mytems.material.getMaxDurability();
                        int filled = (water * max) / type.capacity;
                        damageable.setDamage(Math.max(1, Math.min(max - 1, max - filled)));
                    }
                }
                List<Component> text = new ArrayList<>(type.instance.baseText);
                text.add(textOfChildren(text(tiny("water "), GRAY), text(water + "/" + type.capacity, BLUE)));
                tooltip(meta, text);
            });
    }

    @Override
    public boolean isDismissable() {
        return isEmpty();
    }

    @Override
    public boolean isSimilar(MytemTag other) {
        return super.isSimilar(other)
            && other instanceof BingoBukkitTag tag
            && tag.water == water;
    }
}
