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
import static com.cavetale.mytems.MytemsPlugin.sessionOf;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public final class Dividers implements Mytem {
    private final Mytems key;
    private final Component displayName;
    private final ItemStack prototype;

    public Dividers(final Mytems key) {
        this.key = key;
        this.displayName = text("Dividers", AQUA);
        this.prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                Items.text(meta, List.of(displayName,
                                         text("Draw a circle to", GRAY),
                                         text("help you build.", GRAY),
                                         empty(),
                                         join(noSeparators(), Mytems.MOUSE_LEFT, text(" Center", GRAY)),
                                         join(noSeparators(), Mytems.MOUSE_RIGHT, text(" Radius", GRAY))));
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
        for (double angle = 0.0; angle < tau; angle += step) {
            double dx = Math.cos(angle) * rad;
            double dz = Math.sin(angle) * rad;
            Location outline = center.clone().add(dx, 0.0, dz);
            if (!outline.isChunkLoaded()) continue;
            Vec3i block = Vec3i.of(outline);
            if (!blocks.contains(block)) {
                blocks.add(block);
            }
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
                    if (block.getType().isAir() || block.isLiquid() || block.getCollisionShape().getBoundingBoxes().isEmpty()) {
                        player.sendBlockChange(block.getLocation(), Material.GRAY_STAINED_GLASS.createBlockData());
                    } else {
                        player.sendBlockChange(block.getLocation(), Material.GRAY_CONCRETE.createBlockData());
                    }
                }
                player.sendMessage(join(noSeparators(),
                                        text("Drawn circle at "),
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
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, SoundCategory.MASTER, 1.0f, 2.0f);
    }

    private void soundDraw(Player player) {
        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, SoundCategory.MASTER, 1.0f, 2.0f);
    }

    private void soundFail(Player player) {
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, SoundCategory.MASTER, 0.5f, 0.5f);
    }
}
