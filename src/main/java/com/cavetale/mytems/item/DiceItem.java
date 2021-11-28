package com.cavetale.mytems.item;

import com.cavetale.core.font.Unicode;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Skull;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor @Getter
public final class DiceItem implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        String texture = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6"
            + "Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv"
            + "NWNmYWRhZWM3NzZlZWRlNDlhNDMzZTA5OTdmZTA0NDJm"
            + "NzY1OTk4OTI0MTliYWFiODNkMjhlOGJhN2YxZjMifX19";
        this.prototype = Skull.create("Dice",
                                      UUID.fromString("eb405652-f501-44b3-86f7-9fa2eebd5303"),
                                      texture,
                                      null);
        this.displayName = Component.text("Dice");
        List<Component> text = List.of(displayName);
        prototype.editMeta(meta -> {
                Items.text(meta, text);
                key.markItemMeta(meta);
            });
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

    /**
     * Putting the item in an item frame will cause a roll.
     *
     * Apparently this event is called instead of PlayerInteractEvent,
     * or the latter is called right after, with an empty hand.
     */
    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, Player player, ItemStack item) {
        if (!(event.getRightClicked() instanceof ItemFrame)) return;
        final int roll = ThreadLocalRandom.current().nextInt(6) + 1;
        final String unicode = unicode(roll);
        item.editMeta(meta -> {
                Items.text(meta, List.of(Component.text(unicode + " Dice")));
            });
        player.sendActionBar(Component.text("The dice rolls " + unicode + roll, NamedTextColor.GREEN));
        Location loc = event.getRightClicked().getLocation();
        loc.getWorld().playSound(loc, Sound.UI_BUTTON_CLICK, SoundCategory.PLAYERS, 1.0f, 2.0f);
    }

    /**
     * Just clicking the item will still trigger a roll, albeit in
     * private.
     */
    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        final int roll = ThreadLocalRandom.current().nextInt(6) + 1;
        final String unicode = unicode(roll);
        item.editMeta(meta -> {
                Items.text(meta, List.of(Component.text(unicode + " Dice")));
            });
        player.sendActionBar(Component.text("The dice rolls " + unicode + roll, NamedTextColor.GREEN));
        player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, SoundCategory.MASTER, 0.5f, 2.0f);
    }
}
