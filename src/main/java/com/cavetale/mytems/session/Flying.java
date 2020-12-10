package com.cavetale.mytems.session;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final  class Flying {
    private final Session session;
    private int flyingTicks = 0;
    private Runnable endFlightCallback;
    boolean allowed = false;

    public void disable() {
        if (flyingTicks > 0) {
            stopFlying();
        }
    }

    public void tick() {
        if (flyingTicks == 0) return;
        flyingTicks -= 1;
        if (flyingTicks == 0) {
            Runnable run = endFlightCallback;
            if (run != null) {
                endFlightCallback = null;
                run.run();
            }
            if (flyingTicks == 0) {
                stopFlying();
            }
        }
    }

    public void setFlying(float speed, int ticks, Runnable callback) {
        endFlightCallback = callback;
        flyingTicks = ticks;
        if (!session.player.getAllowFlight()) {
            session.player.setAllowFlight(true);
            allowed = true;
        }
        session.player.setFlying(true);
        session.player.setFlySpeed(speed);
    }

    public void stopFlying() {
        flyingTicks = 0;
        session.player.setFlySpeed(0.1f);
        session.player.setFlying(false);
        if (allowed) resetAllow();
    }

    void resetAllow() {
        allowed = false;
        session.player.setAllowFlight(false);
    }

    public void onToggleOff() {
        if (flyingTicks == 0) return;
        flyingTicks = 0;
        if (allowed) resetAllow();
    }
}
