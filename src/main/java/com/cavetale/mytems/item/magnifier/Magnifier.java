package com.cavetale.mytems.item.magnifier;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.subscript;
import static com.cavetale.mytems.MytemsPlugin.plugin;
import static com.cavetale.mytems.MytemsPlugin.sessionOf;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@RequiredArgsConstructor @Getter
public final class Magnifier implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private static final TextColor BLUE2 = color(0x8888ff);

    @Override
    public void enable() {
        displayName = text("Magnifying Glass", BLUE2);
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                List<Component> text = new ArrayList<>();
                text.add(displayName);
                String line = subscript("Scramble or unscramble text on signs.  Only you can see the result.");
                text.addAll(Text.wrapLore(line, c -> c.color(GRAY)));
                text.add(empty());
                text.add(textOfChildren(Mytems.MOUSE_RIGHT, text(" (Un)scramble sign", GRAY)));
                Items.text(meta, text);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    public static String rot13(String in) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < in.length(); i += 1) {
            char c = in.charAt(i);
            if (c >= 'a' && c <= 'z') {
                int cc = (int) (c - 'a');
                cc = cc >= 13
                    ? cc - 13
                    : cc + 13;
                c = (char) ('a' + cc);
            } else if (c >= 'A' && c <= 'Z') {
                int cc = (int) (c - 'A');
                cc = cc >= 13
                    ? cc - 13
                    : cc + 13;
                c = (char) ('A' + cc);
            }
            result.append(c);
        }
        return result.toString();
    }

    private static final class Favorite {
        Vec3i vec = null;
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (!event.hasBlock()) return;
        Block block = event.getClickedBlock();
        if (!(block.getState() instanceof Sign sign)) return;
        Vec3i vec = Vec3i.of(block);
        Favorite fav = sessionOf(player).getFavorites().getOrSet(Favorite.class, Favorite::new);
        List<Component> lines = new ArrayList<>(sign.lines());
        if (!vec.equals(fav.vec)) {
            fav.vec = vec;
            for (int i = 0; i < lines.size(); i += 1) {
                Component line = lines.get(i);
                if (!(line instanceof TextComponent textLine)) continue;
                textLine = textLine.content(rot13(textLine.content()));
                lines.set(i, textLine);
            }
            Bukkit.getScheduler().runTask(plugin(), () -> {
                    player.sendSignChange(block.getLocation(), lines, sign.getColor(), sign.isGlowingText());
                    player.sendActionBar(text("Sign scrambled", BLUE2));
                });
            Location location = block.getLocation().add(0.5, 0.5, 0.5);
            player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 0.5f, 2.0f);
            player.spawnParticle(Particle.ENCHANTMENT_TABLE, location, 32, .35f, .35f, .35f, 0.75);
        } else {
            fav.vec = null;
            Bukkit.getScheduler().runTask(plugin(), () -> {
                    player.sendSignChange(block.getLocation(), lines, sign.getColor(), sign.isGlowingText());
                    player.sendActionBar(text("Sign unscrambled", BLUE2));
                });
            Location location = block.getLocation().add(0.5, 0.5, 0.5);
            player.playSound(location, Sound.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0f, 0.5f);
            player.spawnParticle(Particle.ENCHANTMENT_TABLE, location, 32, .35f, .35f, .35f, 0.0);
        }
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
