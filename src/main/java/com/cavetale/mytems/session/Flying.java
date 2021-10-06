package com.cavetale.mytems.session;

import com.cavetale.mytems.Mytems;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public final  class Flying {
    private final Session session;
    @Getter private int flyTime;
    private Consumer<Player> flyEndTask;
    private Consumer<Player> flyTickTask;
    @Getter private String flightCauseId;
    @Getter private boolean flying;
    private float flyingSpeed;

    public void disable(Player player) {
        if (flying) {
            stopFlying(player);
        }
    }

    public boolean isFlying(Mytems mytems) {
        return flying && mytems.id.equals(flightCauseId);
    }

    public void tick(Player player) {
        if (!flying) return;
        if (flyTickTask != null) {
            flyTickTask.accept(player);
        }
        flyTime += 1;
    }

    public void setFlying(Player player, Mytems mytems, float speed, Consumer<Player> tickTask, Consumer<Player> endTask) {
        this.flying = true;
        this.flightCauseId = mytems.id;
        this.flyTickTask = tickTask;
        this.flyEndTask = endTask;
        this.flyingSpeed = speed;
        startFlying(player);
    }

    private void startFlying(Player player) {
        if (!player.getAllowFlight()) {
            player.setAllowFlight(true);
        }
        if (!player.isFlying()) {
            player.setFlying(true);
            player.setFlySpeed(flyingSpeed);
        }
    }

    public void stopFlying(Player player) {
        this.flying = false;
        this.flyEndTask = null;
        this.flyTickTask = null;
        this.flyTime = 0;
        player.setFlySpeed(0.1f);
        player.setFlying(false);
        player.setAllowFlight(false);
    }

    public void onToggleOff(Player player) {
        if (!flying) return;
        if (flyEndTask != null) flyEndTask.accept(player);
        stopFlying(player);
    }
}
