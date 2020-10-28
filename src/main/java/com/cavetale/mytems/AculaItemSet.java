package com.cavetale.mytems.item;

import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

public final class AculaItemSet implements ItemSet {
    private static AculaItemSet instance = null;
    private final MytemsPlugin plugin;
    @Getter List<SetBonus> setBonuses;
    @Getter public final SetBonus vampiricBonusDamage = new SetBonus() {
            @Getter public final int requiredItemCount = 2;
            @Getter public final String description = "Deal extra 50% damage to vampiric enemies";
        };
    @Getter public final SetBonus vampirismResistance = new SetBonus() {
            @Getter public final int requiredItemCount = 4;
            @Getter public final String description = "Resistance to Vampirism";
        };

    public static AculaItemSet getInstance(MytemsPlugin plugin) {
        if (instance == null || instance.plugin != plugin) {
            instance = new AculaItemSet(plugin);
        }
        return instance;
    }

    private AculaItemSet(final MytemsPlugin plugin) {
        this.plugin = plugin;
        setBonuses = Arrays.asList(vampiricBonusDamage, vampirismResistance);
    }
}
