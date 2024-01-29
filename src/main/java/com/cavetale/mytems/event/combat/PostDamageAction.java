package com.cavetale.mytems.event.combat;

public record PostDamageAction(boolean ignoreCancelled,
                               Runnable callback) {
}
