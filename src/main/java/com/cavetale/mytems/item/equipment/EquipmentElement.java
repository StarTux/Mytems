package com.cavetale.mytems.item.equipment;

import com.cavetale.core.font.VanillaItems;
import com.cavetale.mytems.Mytems;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import static com.cavetale.core.util.CamelCase.toCamelCase;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor
public enum EquipmentElement {
    BLAZE(RED),
    EARTH(GRAY),
    ENDER(LIGHT_PURPLE),
    FREEZE(BLUE),
    LIGHTNING(AQUA),
    FRIENDSHIP(RED),
    GUARDIAN(DARK_AQUA),
    REDSTONE(DARK_RED),
    ;

    public final TextColor textColor;

    public Component getIcon() {
        switch (this) {
        case BLAZE: return VanillaItems.BLAZE_POWDER.component;
        case EARTH: return VanillaItems.MUD.component;
        case ENDER: return VanillaItems.ENDER_EYE.component;
        case FREEZE: return VanillaItems.ICE.component;
        case LIGHTNING: return Mytems.LIGHTNING.component;
        case FRIENDSHIP: return Mytems.HEART.component;
        case GUARDIAN: return VanillaItems.PRISMARINE.component;
        case REDSTONE: return VanillaItems.REDSTONE.component;
        default: throw new IllegalStateException(this.name());
        }
    }

    public Component getIconDisplayName() {
        return join(noSeparators(), getIcon(), text(toCamelCase(" ", this), textColor));
    }
}
