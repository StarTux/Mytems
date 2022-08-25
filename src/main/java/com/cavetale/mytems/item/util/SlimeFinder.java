package com.cavetale.mytems.item.util;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.font.VanillaItems;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@Getter
public final class SlimeFinder implements Mytem {
    private final Mytems key;
    private final Component displayName;
    private final ItemStack prototype;
    private static final TextColor SLIME_GREEN = color(0x6FBF5C);
    private static final int MAX_HEIGHT = 40;

    public SlimeFinder(final Mytems key) {
        this.key = key;
        this.displayName = text("Slime Finder", SLIME_GREEN);
        assert key.material == Material.SLIME_BALL;
        this.prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                Items.text(meta, List.of(displayName,
                                         text("Find chunks which are", GRAY),
                                         text("able to spawn slimes.", GRAY),
                                         empty(),
                                         text("Slime chunks can spawn", GRAY),
                                         text("slimes below Y level " + MAX_HEIGHT + ".", GRAY),
                                         empty(),
                                         join(noSeparators(), Mytems.MOUSE_RIGHT, text(" Check chunk", GRAY))));
                key.markItemMeta(meta);
            });
    }

    @Override
    public void enable() { }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (player.getGameMode() == GameMode.SPECTATOR) return;
        if (!event.hasBlock()) return;
        event.setCancelled(true);
        Block block = event.getClickedBlock();
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) {
            player.sendActionBar(join(noSeparators(), VanillaItems.BARRIER,
                                      text(" You cannot build here", RED)));
            soundFail(player);
            return;
        }
        if (block.getY() > MAX_HEIGHT) {
            player.sendActionBar(join(noSeparators(), VanillaItems.BARRIER,
                                      text("Must be below slime level Y: " + MAX_HEIGHT, RED)));
            soundFail(player);
            return;
        }
        final int x = block.getChunk().getX();
        final int z = block.getChunk().getZ();
        if (!block.getChunk().isSlimeChunk()) {
            player.sendActionBar(join(noSeparators(), VanillaItems.BARRIER,
                                      text("Not a slime chunk: " + x + " " + z, RED)));
            return;
        }
        player.sendActionBar(join(noSeparators(), Mytems.SLIME_FINDER,
                                  text("This is a slime chunk: " + x + " " + z, SLIME_GREEN)));
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    private void soundUse(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_SLIME_JUMP, SoundCategory.MASTER, 1.0f, 2.0f);
    }

    private void soundFail(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_SLIME_DEATH, SoundCategory.MASTER, 1.0f, 0.5f);
    }
}
