package com.cavetale.mytems.item.arcade;

import com.cavetale.core.font.Unicode;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.MytemsPlugin.plugin;
import static com.cavetale.mytems.util.Items.clearAttributes;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

@Getter @RequiredArgsConstructor
public final class SealedCaveboy implements Mytem {
    private final Mytems key;
    private Component displayName;
    private ItemStack prototype;

    @Override
    public void enable() {
        displayName = text("Sealed Caveboy", GRAY);
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                tooltip(meta, List.of(displayName,
                                      text("Caveboy" + Unicode.TRADEMARK.string, DARK_GRAY, ITALIC),
                                      text("Mint in the box. This", GRAY),
                                      text("could be worth something", GRAY),
                                      text("some day. Or you could", GRAY),
                                      text("open and play it.", GRAY),
                                      text("What game could it be?", GRAY),
                                      empty(),
                                      textOfChildren(Mytems.MOUSE_CURSOR, Mytems.MOUSE_RIGHT, text(" Unwrap", GRAY))));
                meta.addItemFlags(ItemFlag.values());
                key.markItemMeta(meta);
                clearAttributes(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerInventoryClick(InventoryClickEvent event, Player player, ItemStack item) {
        if (!event.isRightClick()) {
            return;
        }
        if (event.getCursor() != null && !event.getCursor().isEmpty()) {
            return;
        }
        if (item.getAmount() > 1) {
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, SoundCategory.MASTER, 1.0f, 0.5f);
            return;
        }
        List<ArcadeGame> games = new ArrayList<>();
        for (var it : ArcadeGame.values()) games.add(it);
        games.remove(ArcadeGame.TIC_TAC_TOE);
        ArcadeGame game = games.get(ThreadLocalRandom.current().nextInt(games.size()));
        event.setCancelled(true);
        plugin().getLogger().info("[" + key + "] " + player.getName() + " unwrapped " + game);
        event.setCurrentItem(game.mytems.createItemStack());
        player.playSound(player.getLocation(), Sound.UI_LOOM_TAKE_RESULT, SoundCategory.MASTER, 1.0f, 2.0f);
        player.sendMessage(textOfChildren(text("The game inside is "), game.mytems, game.displayName));
    }
}
