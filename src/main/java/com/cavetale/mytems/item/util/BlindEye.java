package com.cavetale.mytems.item.util;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.session.Session;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.MytemsPlugin.sessionOf;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public final class BlindEye implements Mytem {
    private final Mytems key;
    private final Component displayName;
    private final ItemStack prototype;

    public BlindEye(final Mytems key) {
        this.key = key;
        this.displayName = text("Blind Eye", GOLD);
        this.prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                tooltip(meta, List.of(displayName,
                                      text("Hide or show all", GRAY),
                                      text("other players.", GRAY),
                                      empty(),
                                      join(noSeparators(), Mytems.MOUSE_RIGHT, text(" Toggle", GRAY))));
                key.markItemMeta(meta);
            });
    }

    @Override
    public void enable() { }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
        if (player.getGameMode() == GameMode.SPECTATOR) return;
        Session session = sessionOf(player);
        boolean newValue = !session.isHidingPlayers();
        session.setHidingPlayers(newValue);
        if (newValue) {
            player.sendActionBar(join(noSeparators(), key, text(" Hiding other Players", GOLD)));
        } else {
            player.sendActionBar(join(noSeparators(), key, text(" No longer hiding other Players", AQUA)));
        }
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, 0.5f, 1.5f);
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, Player player, ItemStack item) {
        if (event.getRightClicked() instanceof ItemFrame) return;
        event.setCancelled(true);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
