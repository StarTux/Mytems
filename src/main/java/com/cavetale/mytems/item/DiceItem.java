package com.cavetale.mytems.item;

import com.cavetale.core.font.Unicode;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Skull;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

@Getter
public final class DiceItem implements Mytem {
    private final Mytems key;
    private final Type type;
    private ItemStack prototype;
    private Component displayName;
    private List<Component> text;

    public DiceItem(final Mytems mytems) {
        this.key = mytems;
        this.type = Objects.requireNonNull(Type.of(key));
    }

    @Override
    public void enable() {
        if (type == Type.DICE) {
            String texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6"
                + "Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv"
                + "NWNmYWRhZWM3NzZlZWRlNDlhNDMzZTA5OTdmZTA0NDJm"
                + "NzY1OTk4OTI0MTliYWFiODNkMjhlOGJhN2YxZjMifX19";
            this.prototype = Skull.create("Die",
                                          UUID.fromString("eb405652-f501-44b3-86f7-9fa2eebd5303"),
                                          texture,
                                          null);
            this.displayName = text("Die");
        } else {
            this.displayName = text(type == Type.DICE100 ? "d%" : "d" + type.sides, AQUA);
            this.prototype = new ItemStack(key.material);
        }
        text = List.of(displayName,
                       text("Rolls any number", GRAY),
                       text("between 1 and " + type.sides + ".", GRAY),
                       empty(),
                       join(noSeparators(),
                            Mytems.MOUSE_RIGHT,
                            text(" Roll", GRAY)),
                       text("Put in item frame", DARK_GRAY),
                       text("to announce the", DARK_GRAY),
                       text("result locally", DARK_GRAY));
        prototype.editMeta(meta -> {
                Items.text(meta, text);
                key.markItemMeta(meta);
            });
    }

    @RequiredArgsConstructor
    public enum Type {
        DICE(Mytems.DICE, 6),
        DICE4(Mytems.DICE4, 4),
        DICE8(Mytems.DICE8, 8),
        DICE10(Mytems.DICE10, 10),
        DICE12(Mytems.DICE12, 12),
        DICE20(Mytems.DICE20, 20),
        DICE100(Mytems.DICE100, 100);

        public final Mytems mytems;
        public final int sides;

        public static Type of(Mytems mytems) {
            for (Type type : Type.values()) {
                if (type.mytems == mytems) return type;
            }
            return null;
        }
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    private static String unicode(int in) {
        switch (in) {
        case 1: return Unicode.DICE_1.string;
        case 2: return Unicode.DICE_2.string;
        case 3: return Unicode.DICE_3.string;
        case 4: return Unicode.DICE_4.string;
        case 5: return Unicode.DICE_5.string;
        case 6: return Unicode.DICE_6.string;
        default: return "" + in;
        }
    }

    private String numberString(int in) {
        return type == Type.DICE
            ? unicode(in) + in
            : "" + in;
    }

    /**
     * Putting the item in an item frame will cause a roll.
     *
     * Apparently this event is called instead of PlayerInteractEvent,
     * or the latter is called right after, with an empty hand.
     */
    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, Player player, ItemStack item) {
        if (!(event.getRightClicked() instanceof ItemFrame)) return;
        final int roll = ThreadLocalRandom.current().nextInt(type.sides) + 1;
        final String numberString = numberString(roll);
        item.editMeta(meta -> meta.displayName(join(noSeparators(),
                                                    displayName,
                                                    text(" [" + numberString + "]", WHITE))
                                               .decoration(ITALIC, false)));
        for (Player nearby : player.getLocation().getNearbyEntitiesByType(Player.class, 32.0, 32.0, 32.0)) {
            Component message = join(noSeparators(),
                                     player.displayName(),
                                     text("'s die rolls ", AQUA),
                                     text(numberString, WHITE));
            nearby.sendActionBar(message);
            nearby.sendMessage(message);
        }
        Location loc = event.getRightClicked().getLocation();
        loc.getWorld().playSound(loc, Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 1.0f, 2.0f);
    }

    /**
     * Just clicking the item will still trigger a roll, albeit in
     * private.
     */
    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        final int roll = ThreadLocalRandom.current().nextInt(type.sides) + 1;
        final String numberString = numberString(roll);
        item.editMeta(meta -> meta.displayName(join(noSeparators(),
                                                    displayName,
                                                    text(" [" + numberString + "]", WHITE))
                                               .decoration(ITALIC, false)));
        player.sendActionBar(join(noSeparators(),
                                  text("Your die rolls ", AQUA),
                                  text(numberString, WHITE)));
        player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, SoundCategory.MASTER, 0.5f, 2.0f);
    }
}
