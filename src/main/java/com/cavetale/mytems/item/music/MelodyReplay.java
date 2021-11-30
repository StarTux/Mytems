package com.cavetale.mytems.item.music;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public final class MelodyReplay {
    protected final Plugin plugin;
    protected final Melody melody;
    protected final BiFunction<Melody, Beat, Boolean> beatCallback;
    protected final List<Long> timings;
    protected BukkitTask task;
    protected int beatIndex = 0;
    private final List<MelodyReplay> extra;

    public MelodyReplay(final Plugin plugin, final Melody melody, final BiFunction<Melody, Beat, Boolean> beatCallback) {
        if (melody.beats.isEmpty()) throw new IllegalArgumentException("beats is empty!");
        this.plugin = plugin;
        this.melody = melody;
        this.beatCallback = beatCallback;
        this.timings = new ArrayList<>(melody.beats.size());
        if (melody.getExtra() != null) {
            extra = new ArrayList<>(melody.getExtra().size());
            for (Melody extraMelody : melody.getExtra()) {
                extra.add(new MelodyReplay(plugin, extraMelody, beatCallback));
            }
        } else {
            extra = null;
        }
    }

    public static MelodyReplay play(Plugin plugin, Melody melody, Player player) {
        MelodyReplay result = new MelodyReplay(plugin, melody, (m, beat) -> {
                if (!player.isOnline()) return false;
                beat.play(m, player, player.getLocation());
                return true;
        });
        result.start();
        return result;
    }

    public static MelodyReplay play(Plugin plugin, Melody melody, Location location) {
        MelodyReplay result = new MelodyReplay(plugin, melody, (m, beat) -> {
                if (!location.isChunkLoaded()) return false;
                beat.play(m, location);
                return true;
        });
        result.start();
        return result;
    }

    public void start() {
        if (task != null) throw new IllegalStateException("already running!");
        long currentTime = System.currentTimeMillis();
        for (Beat beat : melody.beats) {
            timings.add(currentTime);
            currentTime += melody.speed * (long) beat.ticks;
        }
        task = Bukkit.getScheduler().runTaskTimer(plugin, this::run, 0L, 1L);
        if (extra != null) {
            for (MelodyReplay it : extra) {
                it.start();
            }
        }
    }

    private void halt() {
        if (task == null) return;
        task.cancel();
        task = null;
    }

    public void stop() {
        halt();
        if (extra != null) {
            for (MelodyReplay it : extra) {
                it.halt();
            }
        }
    }

    public boolean didStop() {
        return task == null;
    }

    private void run() {
        while (task != null) {
            long now = System.currentTimeMillis();
            long timing = timings.get(beatIndex);
            if (now < timing) return;
            Beat beat = melody.beats.get(beatIndex);
            boolean value = beatCallback.apply(melody, beat);
            beatIndex += 1;
            if (!value || beatIndex >= melody.beats.size()) {
                halt();
                return;
            }
        }
    }
}
