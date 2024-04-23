package com.cavetale.mytems.item.arcade;

import com.cavetale.core.font.Unicode;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.MytemsPlugin.plugin;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;
import static org.bukkit.Sound.BLOCK_NOTE_BLOCK_BIT;
import static org.bukkit.SoundCategory.MASTER;

@Getter
public final class Caveboy implements Mytem {
    private final Mytems key;
    private final ArcadeGame game;
    private final Component displayName;
    private ItemStack prototype;

    public Caveboy(final Mytems key) {
        this.key = key;
        this.game = Objects.requireNonNull(ArcadeGame.of(key));
        this.displayName = textOfChildren(game.displayName, text(" Video Game", RED, ITALIC));
    }

    @Override
    public void enable() {
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                tooltip(meta, List.of(displayName,
                                      text("Caveboy" + Unicode.TRADEMARK.string, DARK_GRAY, ITALIC),
                                      text("this futuristic handheld", GRAY),
                                      text("fits into your pocket.", GRAY),
                                      text(tiny("AA Batteries sold"), GRAY),
                                      text(tiny("separately."), GRAY),
                                      empty(),
                                      textOfChildren(Mytems.MOUSE_LEFT, text(" Play ", GRAY), game.displayName)));
                meta.addItemFlags(ItemFlag.values());
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setUseItemInHand(Event.Result.DENY);
        game.startMethod.accept(player);
        Bukkit.getScheduler().runTaskLater(plugin(), () -> player.playSound(player.getLocation(), BLOCK_NOTE_BLOCK_BIT, MASTER, 1.0f, 1.1f), 0L);
        Bukkit.getScheduler().runTaskLater(plugin(), () -> player.playSound(player.getLocation(), BLOCK_NOTE_BLOCK_BIT, MASTER, 1.0f, 1.6f), 2L);
        Bukkit.getScheduler().runTaskLater(plugin(), () -> player.playSound(player.getLocation(), BLOCK_NOTE_BLOCK_BIT, MASTER, 1.0f, 2.0f), 4L);
        Bukkit.getScheduler().runTaskLater(plugin(), () -> player.playSound(player.getLocation(), BLOCK_NOTE_BLOCK_BIT, MASTER, 1.0f, 2.0f), 5L);
    }
}
