package com.cavetale.mytems.item.hastypickaxe;

import com.cavetale.mytems.item.upgradable.UpgradableItem;
import java.util.List;
import static net.kyori.adventure.text.format.NamedTextColor.*;

/**
 * This is the UpgradableItem implementation, whereas the Mytem
 * resides in HastyPickaxe.
 */
public final class HastyPickaxeItem implements UpgradableItem {
    private static HastyPickaxeItem instance;

    public static HastyPickaxeItem hastyPickaxeItem() {
        if (instance == null) {
            instance = new HastyPickaxeItem();
        }
        return instance;
    }

    @Override
    public List<HastyPickaxeTier> getTiers() {
        return List.of(HastyPickaxeTier.values());
    }

    @Override
    public List<HastyPickaxeStat> getStats() {
        return List.of(HastyPickaxeStat.values());
    }

    @Override
    public HastyPickaxeStat statForKey(String key) {
        return HastyPickaxeStat.forKey(key);
    }

    @Override
    public int getMenuSize() {
        return 5 * 9;
    }
}
