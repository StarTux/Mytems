package com.cavetale.mytems.item.acula;

import com.cavetale.mytems.gear.ItemSet;
import com.cavetale.mytems.gear.SetBonus;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public final class AculaItemSet implements ItemSet {
    private final String name = "Acula";
    private static AculaItemSet instance = null;
    private final List<SetBonus> setBonuses;

    public final SetBonus vampiricBonusDamage = new SetBonus() {
            @Getter public final int requiredItemCount = 2;
            @Getter public final String name = "Garlic Breath";
            @Getter public final String description = "Deal extra 50% damage to vampiric enemies";
        };

    public final SetBonus vampirismResistance = new SetBonus() {
            @Getter public final int requiredItemCount = 4;
            @Getter public final String name = "Silver Shield";
            @Getter public final String description = "Resistance to Vampirism";
        };

    public static AculaItemSet getInstance() {
        if (instance == null) instance = new AculaItemSet();
        return instance;
    }

    private AculaItemSet() {
        setBonuses = Arrays.asList(vampiricBonusDamage, vampirismResistance);
    }
}
