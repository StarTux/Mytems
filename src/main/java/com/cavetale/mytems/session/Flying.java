package com.cavetale.mytems.session;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final  class Flying {
    private final Session session;
    private int flyingTicks = 0; // remaining
    @Getter private int flyTime;
    private Runnable flyEndTask;
    private Runnable flyTickTask;
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
            Runnable run = flyEndTask;
            if (run != null) {
                flyEndTask = null;
                run.run();
            }
            if (flyingTicks == 0) {
                stopFlying();
            }
        } else {
            if (flyTickTask != null) {
                flyTickTask.run();
            }
        }
        flyTime += 1;
    }

    public void setFlying(float speed, int ticks, Runnable tickTask, Runnable endTask) {
        flyTickTask = tickTask;
        flyEndTask = endTask;
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
        flyEndTask = null;
        flyTickTask = null;
        flyTime = 0;
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
        if (flyEndTask != null) flyEndTask.run();
        flyTickTask = null;
        flyEndTask = null;
        flyingTicks = 0;
        flyTime = 0;
        if (allowed) resetAllow();
    }
}
