package com.cavetale.mytems.gear;

import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

public interface ItemSet {
    String getName();

    List<SetBonus> getSetBonuses();

    default int getMaxItemCount() {
        int max = 0;
        for (SetBonus setBonus : getSetBonuses()) {
            int req = setBonus.getRequiredItemCount();
            if (req > max) max = req;
        }
        return max;
    };

    default List<Component> createTooltip() {
        return createTooltip(0);
    }

    default List<Component> createTooltip(Equipment equipment) {
        return createTooltip(equipment != null ? equipment.countSetItems(this) : 0);
    }

    default List<Component> createTooltip(int setItemCount) {
        if (getSetBonuses().isEmpty()) return List.of();
        List<Component> tooltip = new ArrayList<>();
        tooltip.add(join(noSeparators(), new Component[] {
                    text(getName()),
                    text(" [" + setItemCount + "/" + getMaxItemCount() + "]"),
                }).color(GRAY));
        for (SetBonus setBonus : getSetBonuses()) {
            if (setBonus.isHidden()) continue;
            tooltip.addAll(setBonus.createTooltip(setItemCount));
        }
        return tooltip;
    }
}
