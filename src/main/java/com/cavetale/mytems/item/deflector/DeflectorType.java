package com.cavetale.mytems.item.deflector;

import com.cavetale.mytems.Mytems;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor
public enum DeflectorType {
    DEFLECTOR(Mytems.DEFLECTOR_SHIELD, text("Deflector Shield", RED), Duration.ofSeconds(7),
              "Block to deflect projectiles away from you."),
    RETURN(Mytems.RETURN_SHIELD, text("Return Shield", GOLD), Duration.ofSeconds(4),
           "Return deflected projectiles back to sender."),
    VENGEANCE(Mytems.VENGEANCE_SHIELD, text("Vengeance Shield", BLUE), Duration.ofSeconds(2),
              "Blocked projectiles return with increased speed."),
    ;

    public final Mytems mytems;
    public final Component displayName;
    public final Duration cooldown;
    public final String description;

    public static DeflectorType of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }
}
