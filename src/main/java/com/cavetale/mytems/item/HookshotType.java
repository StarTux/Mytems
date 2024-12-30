package com.cavetale.mytems.item.hookshot;

import com.cavetale.mytems.Mytems;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
@RequiredArgsConstructor
public enum HookshotType {
    PUMPKIN(Mytems.PUMPKIN_HOOKSHOT, text("Pumpkin Hookshot", GOLD), Set.of(Material.PUMPKIN, Material.CARVED_PUMPKIN, Material.JACK_O_LANTERN)),
    ;

    private final Mytems mytems;
    private final Component displayName;
    private final Set<Material> attachedMaterials;

    public static HookshotType of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }
}
