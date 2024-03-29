package com.cavetale.mytems.item.upgradable;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Text;

public interface UpgradableItemTier {
    /**
     * Get the tier number, starting at 1.
     */
    int getTier();

    /**
     * Get the Mytems instance corresponding to this stat set.
     */
    Mytems getMytems();

    default String getRomanTier() {
        return Text.roman(getTier());
    }
}
