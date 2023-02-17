package com.cavetale.mytems.item.dividers;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.util.Items;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.MytemsPlugin.sessionOf;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@Getter
public final class Dividers implements Mytem {
    private final Mytems key;
    private final Component displayName;
    private final ItemStack prototype;

    public Dividers(final Mytems key) {
        this.key = key;
        this.displayName = text("Dividers", color(0xE1C16E));
        this.prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                Items.text(meta, List.of(displayName,
                                         text(tiny("Plan out circles on the"), GRAY),
                                         text(tiny("ground and build them"), GRAY),
                                         text(tiny("accurately with ease"), GRAY),
                                         empty(),
                                         textOfChildren(Mytems.MOUSE_LEFT, text(" Set Center", GRAY)),
                                         textOfChildren(Mytems.MOUSE_RIGHT, text(" Draw with Radius", GRAY))));
                meta.setUnbreakable(true);
                meta.addItemFlags(ItemFlag.values());
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
    public void onPlayerLeftClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (!event.hasBlock()) return;
        event.setCancelled(true);
        click(player, event.getClickedBlock(), false);
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (!event.hasBlock()) return;
        event.setCancelled(true);
        click(player, event.getClickedBlock(), true);
    }

    private void click(Player player, Block block, boolean right) {
        DividersSession session = sessionOf(player).getFavorites().getOrSet(DividersSession.class, DividersSession::new);
        if (session.drawing) return;
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) {
            soundFail(player);
            return;
        }
        if (!block.getWorld().getName().equals(session.world)) {
            session.reset();
            session.world = block.getWorld().getName();
        }
        if (!right) {
            session.point1 = Vec3i.of(block);
            player.sendActionBar(text("Center " + session.point1, AQUA));
        } else {
            session.point2 = Vec3i.of(block);
        }
        if (right && session.point1 != null && session.point2 != null) {
            drawCircle(player, session, session.point1, session.point2);
            soundDraw(player);
        } else {
            soundUse(player);
        }
    }

    private static int distanceSquared(Vec3i a, Vec3i b) {
        int dx = b.x - a.x;
        int dz = b.z - a.z;
        return dx * dx + dz * dz;
    }

    private static boolean isFullBlock(Block block) {
        var voxelShape = block.getCollisionShape();
        if (voxelShape == null) return false;
        var boundingBoxes = voxelShape.getBoundingBoxes();
        if (boundingBoxes.size() != 1) return false;
        var bb = boundingBoxes.iterator().next();
        return bb.getWidthX() == 1.0
            && bb.getHeight() == 1.0
            && bb.getWidthZ() == 1.0;
    }

    private boolean drawCircle(Player player, DividersSession session, Vec3i a, Vec3i b) {
        World world = player.getWorld();
        Vec3i c = new Vec3i(a.x, b.y, a.z);
        Location center = c.toLocation(world).add(0.5, 0.5, 0.5);
        if (!center.isChunkLoaded()) {
            player.sendActionBar(text("Center not loaded: " + a, RED));
            soundFail(player);
            return false;
        }
        final int radSq = distanceSquared(a, b);
        final double rad = Math.sqrt((double) radSq);
        if (rad > 64.0) {
            player.sendActionBar(text("Radius too big: " + (int) Math.round(rad), RED));
            soundFail(player);
            return false;
        }
        final double tau = Math.PI * 2.0;
        final double step = 0.01 / rad;
        if (session.blocks != null) {
            for (Vec3i vec : session.blocks) {
                if (!world.isChunkLoaded(vec.x >> 4, vec.z >> 4)) continue;
                Block block = vec.toBlock(world);
                player.sendBlockChange(block.getLocation(), block.getBlockData());
            }
            session.blocks = null;
        }
        List<Vec3i> blocks = new ArrayList<>();
        blocks.add(c);
        LOOP:
        for (double angle = 0.0; angle < tau; angle += step) {
            double dx = Math.cos(angle) * rad;
            double dz = Math.sin(angle) * rad;
            Location outline = center.clone().add(dx, 0.0, dz);
            if (!outline.isChunkLoaded()) continue;
            Block block = outline.getBlock();
            while (!isFullBlock(block)) {
                if (block.getY() <= world.getMinHeight()) continue LOOP;
                block = block.getRelative(0, -1, 0);
            }
            Vec3i vec = Vec3i.of(block);
            if (blocks.contains(vec)) continue;
            blocks.add(vec);
        }
        session.drawing = true;
        Bukkit.getScheduler().runTaskLater(MytemsPlugin.getInstance(), () -> {
                session.drawing = false;
                if (!player.isOnline() || !player.getWorld().equals(world)) {
                    return;
                }
                for (Vec3i vec : blocks) {
                    if (!world.isChunkLoaded(vec.x >> 4, vec.z >> 4)) continue;
                    Block block = vec.toBlock(world);
                    Material mat = (block.getX() & 1) == (block.getZ() & 1)
                        ? Material.BLACK_CONCRETE
                        : Material.WHITE_CONCRETE;
                    player.sendBlockChange(block.getLocation(), mat.createBlockData());
                }
                player.sendActionBar(textOfChildren(text("Drawn circle at "),
                                                    text(c.x + " " + c.y + " " + c.z, GOLD),
                                                    text(" radius "),
                                                    text((int) Math.round(rad), GOLD),
                                                    (blocks.size() > 1
                                                     ? text(" (" + blocks.size() + " blocks)", GRAY)
                                                     : empty())));
            }, 2L);
        session.blocks = blocks;
        return true;
    }

    @Override
    public void onDamageEntity(EntityDamageByEntityEvent event, Player player, ItemStack item) {
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
