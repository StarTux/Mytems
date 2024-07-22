package com.cavetale.mytems.item.upgradable;

import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Text;
import net.kyori.adventure.text.format.TextColor;

public interface UpgradableItemTier {
    /**
     * Get the tier number, starting at 1.
     */
    int getTier();

    /**
     * Get the Mytems instance corresponding to this stat set.
     */
    Mytems getMytems();

    UpgradableItem getUpgradableItem();

    default String getRomanTier() {
        return Text.roman(getTier());
    }

    TextColor getMenuColor();
}
