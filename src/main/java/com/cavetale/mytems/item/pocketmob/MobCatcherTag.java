package com.cavetale.mytems.item.pocketmob;

import com.cavetale.mytems.MytemPersistenceFlag;
import com.cavetale.mytems.MytemTag;
import java.util.Set;
import org.bukkit.inventory.ItemStack;

/**
 * The data stored in a PocketMob item.
 */
public final class MobCatcherTag extends MytemTag {
    protected String tagJson;

    public boolean isEmpty() {
        return tagJson == null && super.isEmpty();
    }

    public void load(ItemStack itemStack, Set<MytemPersistenceFlag> flags) {
        super.load(itemStack, flags);
    }

    public void store(ItemStack itemStack, Set<MytemPersistenceFlag> flags) {
        super.store(itemStack, flags);
    }
}
