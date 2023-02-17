package com.cavetale.mytems.session;

import com.cavetale.mytems.Mytems;
import java.time.Duration;
import net.kyori.adventure.text.ComponentLike;

public final class Cooldown {
    protected Mytems mytems;
    protected Mytems icon;
    protected long start;
    protected long end;

    public Cooldown(final Mytems mytems) {
        this.mytems = mytems;
        this.icon = null;
        this.start = System.currentTimeMillis();
        this.end = 0L;
    }

    public Duration getDuration() {
        long timeLeft = end - System.currentTimeMillis();
        return Duration.ofSeconds((timeLeft - 1L) / 1000L + 1L);
    }

    public Cooldown duration(Duration newDuration) {
        this.end = System.currentTimeMillis() + newDuration.toMillis();
        return this;
    }

    public Cooldown icon(final Mytems newIcon) {
        this.icon = newIcon;
        return this;
    }

    public ComponentLike getIcon() {
        if (icon != null) return icon.getCurrentAnimationFrame();
        return mytems.getCurrentAnimationFrame();
    }
}
