package com.cavetale.mytems.gear;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.kyori.adventure.text.Component;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

public interface ItemSet {
    String getName();
    List<SetBonus> getSetBonuses();

    default List<Component> createTooltip() {
        return createTooltip(0);
    }

    default List<Component> createTooltip(@Nullable Equipment equipment) {
        return createTooltip(equipment != null ? equipment.countSetItems(this) : 0);
    }

    default List<Component> createTooltip(int setItemCount) {
        if (getSetBonuses().isEmpty()) return List.of();
        List<Component> tooltip = new ArrayList<>();
        tooltip.add(join(noSeparators(), new Component[] {
                    text(getName()),
                    text(" [" + setItemCount + "]"),
                }).color(GRAY));
        for (SetBonus setBonus : getSetBonuses()) {
            if (setBonus.isHidden()) continue;
            tooltip.addAll(setBonus.createTooltip(setItemCount));
        }
        return tooltip;
    }
}
