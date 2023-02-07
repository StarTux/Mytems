package com.cavetale.mytems.item.hourglass;

import com.cavetale.core.font.Unicode;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.core.util.CamelCase.toCamelCase;
import static java.util.Objects.requireNonNull;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

@RequiredArgsConstructor @Getter
public final class Hourglass implements Mytem {
    private final Mytems key;
    private final HourglassType type;
    private ItemStack prototype;
    private Component displayName;

    public Hourglass(final Mytems key) {
        this.key = key;
        this.type = requireNonNull(HourglassType.of(key));
    }

    @Override
    public void enable() {
        displayName = type.displayName;
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                List<Component> txt = new ArrayList<>();
                txt.add(displayName);
                txt.addAll(Text.wrapLore(tiny(type.description), c -> c.color(GRAY)));
                txt.add(empty());
                txt.add(textOfChildren(Mytems.MOUSE_RIGHT, text(" " + type.rightClickLine, GRAY)));
                Items.text(meta, txt);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, SoundCategory.MASTER, 1.0f, 1.0f);
        World world = player.getWorld();
        Component time = getTimeComponent(world);
        var env = world.getEnvironment();
        switch (type) {
        case COLORFALL:
            player.sendActionBar(time);
            break;
        case MOONLIGHT:
            if (env == World.Environment.NETHER || env == World.Environment.THE_END) {
                player.sendActionBar(textOfChildren(time, text(" Nether", RED, ITALIC)));
                return;
            }
            player.sendActionBar(textOfChildren(time,
                                                text(" " + Unicode.MOON.string, DARK_GREEN),
                                                text(toCamelCase(" ", world.getMoonPhase()), GREEN)));
            break;
        case ATMOSPHERE: {
            if (env == World.Environment.NETHER || env == World.Environment.THE_END) {
                player.sendActionBar(textOfChildren(time, text(" Nether", RED, ITALIC)));
                return;
            }
            int duration = world.getWeatherDuration();
            player.sendActionBar(textOfChildren(time,
                                                space(),
                                                (world.hasStorm()
                                                 ? (world.isThundering()
                                                    ? textOfChildren(Mytems.LIGHTNING, text("Thunderstorm", AQUA))
                                                    : text("Rain", BLUE))
                                                 : text(Unicode.SUN.string + "Sunshine", YELLOW)),
                                                (duration > 0
                                                 ? text(" for " + (world.getWeatherDuration() / 20) + "s", GRAY)
                                                 : empty())));
            break;
        }
        case CLIMATE: {
            double c = (player.getLocation().getBlock().getTemperature() - 0.15) * 25.0;
            double f = c * 1.8 + 32.0;
            player.sendActionBar(textOfChildren(time,
                                                text(" " + ((int) c) + "\u00B0C", AQUA),
                                                text(" " + ((int) f) + "\u00B0F", AQUA)));
            break;
        }
        default: break;
        }
    }

    private static Component getTimeComponent(World world) {
        final long time = world.getTime();
        long hours = hours(time);
        String meridian = hours < 12 ? "am" : "pm";
        hours = hours % 12;
        if (hours == 0L) hours = 12;
        return textOfChildren(text(hours),
                              text(":", GRAY),
                              text(String.format("%02d", minutes(time))),
                              text(meridian, GRAY));
    }

    private static long hours(long raw) {
        long time = raw + 6000;
        long hours = time / 1000;
        if (hours >= 24) hours -= 24;
        return hours;
    }

    private static long minutes(long raw) {
        long time = raw + 6000;
        long minutes = ((time % 1000) * 60) / 1000;
        return minutes;
    }
}
