package com.cavetale.mytems.item.yardstick;

import com.cavetale.core.struct.Cuboid;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.core.text.LineWrap;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.axis.CuboidOutline;
import com.cavetale.mytems.util.Attr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@Getter
public final class Yardstick implements Mytem {
    private static final int COLOR_HEX = 0xE1C16E;
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
        this.displayName = text("Yardstick", color(COLOR_HEX));
        this.prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                key.markItemMeta(meta);
                List<Component> text = new ArrayList<>();
                text.add(displayName);
                text.addAll(new LineWrap().emoji(true).componentMaker(str -> text(tiny(str), GRAY)).wrap(TOOLTIP));
                text.add(empty());
                text.add(textOfChildren(Mytems.MOUSE_LEFT, text(" Set point A", GRAY)));
                text.add(textOfChildren(Mytems.MOUSE_RIGHT, text(" Set point B", GRAY)));
                text.add(textOfChildren(Mytems.MOUSE_CURSOR, Mytems.MOUSE_RIGHT, text(" Clear selection", GRAY)));
                tooltip(meta, text);
                meta.setUnbreakable(true);
                Attr.addNumber(meta, Attribute.PLAYER_BLOCK_INTERACTION_RANGE, "yardstick_range", 5.5, EquipmentSlotGroup.HAND);
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
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
        if (!block.getWorld().getName().equals(session.world)) {
            session.clearBlocks();
            session.reset();
            session.world = block.getWorld().getName();
        }
        if (num == 1) {
            session.point1 = Vec3i.of(block);
            player.sendActionBar(textOfChildren(key, text("Point A ", GRAY),
                                                text(session.point1.x + " " + session.point1.y + " " + session.point1.z, color(COLOR_HEX))));
        } else if (num == 2) {
            session.point2 = Vec3i.of(block);
            player.sendActionBar(textOfChildren(key, text("Point B ", GRAY),
                                                text(session.point2.x + " " + session.point2.y + " " + session.point2.z, color(COLOR_HEX))));
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
        final int length = a.roundedDistance(b);
        if (length > MAX_LENGTH) {
            player.sendActionBar(text("Line span too long: " + length, RED));
            soundFail(player);
            return false;
        }
        for (Vec3i vector : LineTool.line3d(a, b)) {
            if (!world.isChunkLoaded(vector.x >> 4, vector.z >> 4)) {
                continue;
            }
            final Cuboid cuboid = new Cuboid(vector.x, vector.y, vector.z,
                                             vector.x, vector.y, vector.z);
            final CuboidOutline outline = new CuboidOutline(world, cuboid);
            outline.showOnlyTo(player);
            outline.spawn();
            outline.glow(Color.fromRGB(COLOR_HEX));
            session.blocks.put(vector, outline);
        }
        player.sendMessage(textOfChildren(key,
                                          text("(" + a + ")", color(COLOR_HEX)),
                                          text(" - ", GRAY),
                                          text("(" + b + ")", color(COLOR_HEX)),
                                          text(" " + session.blocks.size(), color(COLOR_HEX)),
                                          text(tiny("blocks"), GRAY))
                           .insertion("(" + a + ") (" + b + ")"));
        return true;
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
