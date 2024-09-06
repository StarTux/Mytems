package com.cavetale.mytems.item.yardstick;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.struct.Cuboid;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.core.text.LineWrap;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.axis.CuboidOutline;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@Getter
public final class Yardstick implements Mytem {
    private final Mytems key;
    private Component displayName;
    private ItemStack prototype;
    private static final int MAX_LENGTH = 512;

    public Yardstick(final Mytems key) {
        this.key = key;
    }

    private static final String TOOLTIP = "Draw lines and measure distances.";

    @Override
    public void enable() {
        this.displayName = text("Yardstick", color(0xE1C16E));
        this.prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                List<Component> text = new ArrayList<>();
                text.add(displayName);
                text.addAll(new LineWrap().emoji(true).componentMaker(str -> text(str, WHITE)).wrap(TOOLTIP));
                text.add(empty());
                text.add(textOfChildren(Mytems.MOUSE_LEFT, text(" Set point A", GRAY)));
                text.add(textOfChildren(Mytems.MOUSE_RIGHT, text(" Set point B", GRAY)));
                text.add(textOfChildren(Mytems.MOUSE_CURSOR, Mytems.MOUSE_RIGHT, text(" Clear selection", GRAY)));
                tooltip(meta, text);
                meta.setUnbreakable(true);
                meta.addItemFlags(ItemFlag.values());
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerLeftClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (!event.hasBlock()) return;
        event.setCancelled(true);
        click(player, event.getClickedBlock(), 1);
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (!event.hasBlock()) return;
        event.setCancelled(true);
        click(player, event.getClickedBlock(), 2);
    }

    private void click(Player player, Block block, int num) {
        YardstickSession session = YardstickSession.of(player);
        if (session.drawing) return;
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) {
            soundFail(player);
            return;
        }
        if (!block.getWorld().getName().equals(session.world)) {
            session.reset();
            session.world = block.getWorld().getName();
        }
        if (num == 1) {
            session.point1 = Vec3i.of(block);
            player.sendActionBar(textOfChildren(key, text("Point A ", GRAY),
                                                text(session.point1.x + " " + session.point1.y + " " + session.point1.z, GOLD)));
        } else if (num == 2) {
            session.point2 = Vec3i.of(block);
            player.sendActionBar(textOfChildren(key, text("Point B ", GRAY),
                                                text(session.point2.x + " " + session.point2.y + " " + session.point2.z, GOLD)));
        }
        if (session.point1 != null && session.point2 != null) {
            if (drawLine(player, session, session.point1, session.point2)) {
                soundDraw(player);
            } else {
                soundFail(player);
            }
        } else {
            soundUse(player);
        }
    }

    private boolean drawLine(Player player, YardstickSession session, Vec3i a, Vec3i b) {
        session.clearBlocks();
        session.blocks = new HashMap<>();
        final World world = player.getWorld();
        final int length;
        if (a.equals(b)) {
            drawBlock(player, session, world, a);
            length = 1;
        } else {
            final Vector mid = new Vector(0.5, 0.5, 0.5);
            final Vector start = a.toVector().add(mid);
            final Vector stop = b.toVector().add(mid);
            final Vector direction = stop.clone().subtract(start);
            length = (int) Math.round(direction.length());
            if (length > MAX_LENGTH) {
                player.sendActionBar(text("Line span too long: " + length, RED));
                soundFail(player);
                return false;
            }
            final BlockIterator blockIterator = new BlockIterator(world, start, direction, 0.0, length);
            while (blockIterator.hasNext()) {
                drawBlock(player, session, world, Vec3i.of(blockIterator.next()));
            }
        }
        player.sendMessage(textOfChildren(key,
                                          text(tiny(" distance"), GRAY), text(length, GOLD),
                                          text(tiny(" blocks"), GRAY), text(session.blocks.size(), GOLD)));
        return true;
    }

    private void drawBlock(Player player, YardstickSession session, World world, Vec3i vector) {
        final Cuboid cuboid = new Cuboid(vector.x, vector.y, vector.z,
                                         vector.x, vector.y, vector.z);
        final CuboidOutline outline = new CuboidOutline(world, cuboid);
        outline.showOnlyTo(player);
        outline.spawn();
        outline.glow(Color.fromRGB(0xE1C16E));
        session.blocks.put(vector, outline);
    }

    @Override
    public void onDamageEntity(EntityDamageByEntityEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerInventoryClick(InventoryClickEvent event, Player player, ItemStack item) {
        if (!event.isRightClick()) return;
        final YardstickSession session = YardstickSession.of(player);
        session.clearBlocks();
        session.reset();
        soundUse(player);
        event.setCancelled(true);
    }

    private void soundUse(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, SoundCategory.MASTER, 1.0f, 2.0f);
    }

    private void soundDraw(Player player) {
        player.playSound(player.getLocation(), Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, SoundCategory.MASTER, 1.0f, 1.25f);
    }

    private void soundFail(Player player) {
        player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, SoundCategory.MASTER, 0.5f, 0.5f);
    }
}
