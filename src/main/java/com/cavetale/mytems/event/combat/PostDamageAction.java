package com.cavetale.mytems.event.combat;

public final record PostDamageAction(boolean ignoreCancelled,
                                     Runnable callback) {
}
