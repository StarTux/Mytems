package com.cavetale.mytems.item;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerChangeBlockEvent;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.MytemsPlugin;
import com.cavetale.mytems.session.Session;
import com.cavetale.mytems.util.BlockColor;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import com.cavetale.worldmarker.block.BlockMarker;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;

/**
 * Paintbrush in 16 colors.  It can change the color of any colored
 * block.
 *
 * The paintbrush works where the player has the ability to "USE" the
 * block.  If the block is marked via WrldMarker as
 * "paintbrush_canvas", the block will actually change.  Otherwise, a
 * block update packet is sent to all nearby players.
 */
@RequiredArgsConstructor @Getter
public final class Paintbrush implements Mytem {
    public static final double RANGE = 48.0;
    private final Mytems key;
    private ItemStack prototype;
    private List<Component> text;
    private Component displayName;
    private Brush brush;

    @RequiredArgsConstructor
    public enum Brush {
        BLACK(Mytems.BLACK_PAINTBRUSH, BlockColor.BLACK),
        RED(Mytems.RED_PAINTBRUSH, BlockColor.RED),
        GREEN(Mytems.GREEN_PAINTBRUSH, BlockColor.GREEN),
        BROWN(Mytems.BROWN_PAINTBRUSH, BlockColor.BROWN),
        BLUE(Mytems.BLUE_PAINTBRUSH, BlockColor.BLUE),
        PURPLE(Mytems.PURPLE_PAINTBRUSH, BlockColor.PURPLE),
        CYAN(Mytems.CYAN_PAINTBRUSH, BlockColor.CYAN),
        LIGHT_GRAY(Mytems.LIGHT_GRAY_PAINTBRUSH, BlockColor.LIGHT_GRAY),
        GRAY(Mytems.GRAY_PAINTBRUSH, BlockColor.GRAY),
        PINK(Mytems.PINK_PAINTBRUSH, BlockColor.PINK),
        LIME(Mytems.LIME_PAINTBRUSH, BlockColor.LIME),
        YELLOW(Mytems.YELLOW_PAINTBRUSH, BlockColor.YELLOW),
        LIGHT_BLUE(Mytems.LIGHT_BLUE_PAINTBRUSH, BlockColor.LIGHT_BLUE),
        MAGENTA(Mytems.MAGENTA_PAINTBRUSH, BlockColor.MAGENTA),
        ORANGE(Mytems.ORANGE_PAINTBRUSH, BlockColor.ORANGE),
        WHITE(Mytems.WHITE_PAINTBRUSH, BlockColor.WHITE);

        protected final Mytems mytems;
        protected final BlockColor blockColor;

        public static Brush of(Mytems mytems) {
            for (Brush brush : Brush.values()) {
                if (brush.mytems == mytems) return brush;
            }
            throw new IllegalArgumentException(mytems.name());
        }

        public static Brush of(BlockColor blockColor) {
            for (Brush brush : Brush.values()) {
                if (brush.blockColor == blockColor) return brush;
            }
            throw new IllegalArgumentException(blockColor.name());
        }
    }

    @Override
    public void enable() {
        this.brush = Brush.of(key);
        this.displayName = Component.text(Text.toCamelCase(key, " "), brush.blockColor.textColor);
        this.text = List.of(displayName,
                            Component.text("Turn the block you're", NamedTextColor.GRAY),
                            (Component.text("looking at ", NamedTextColor.GRAY)
                             .append(Component.text(brush.blockColor.niceName, brush.blockColor.textColor))
                             .append(Component.text(".", NamedTextColor.GRAY))),
                            Component.text("The effect is not", NamedTextColor.GRAY),
                            Component.text("permanent!", NamedTextColor.GRAY),
                            Component.empty(),
                            Component.join(JoinConfiguration.noSeparators(),
                                           Component.text("Left ", NamedTextColor.GREEN),
                                           Component.text("Pick Color", NamedTextColor.GRAY)),
                            Component.join(JoinConfiguration.noSeparators(),
                                           Component.text("Right ", NamedTextColor.GREEN),
                                           Component.text("Paint", NamedTextColor.GRAY)));
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                Items.text(meta, text);
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
        event.setUseInteractedBlock(Event.Result.DENY);
        event.setUseItemInHand(Event.Result.DENY);
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.hasBlock()) {
            player.sendActionBar(Component.text("Too close! Get some distance.", NamedTextColor.RED));
            return;
        }
        Session session = MytemsPlugin.getInstance().getSessions().of(player);
        long cooldown = session.getCooldownInTicks(key.id);
        if (cooldown > 0) return;
        RayTraceResult rayTraceResult = player.rayTraceBlocks(RANGE);
        if (rayTraceResult == null) {
            session.setCooldown(key.id, 20);
            return;
        }
        Block block = rayTraceResult.getHitBlock();
        paintBlock(player, block, rayTraceResult.getHitBlockFace());
    }

    /**
     * Paint a target block.  The color is implied by this instance.
     *
     * @param targetBlock the target block
     * @param face the BlockFace that will display particle effects
     * @return true if the block was updated in any way, false otherwise
     */
    private boolean paintBlock(Player player, Block targetBlock, BlockFace face) {
        if (!PlayerBlockAbilityQuery.Action.USE.query(player, targetBlock)) return false;
        BlockColor targetBlockColor = BlockColor.of(targetBlock.getType());
        if (targetBlockColor == null) return false;
        BlockColor.Suffix suffix = targetBlockColor.suffixOf(targetBlock.getType());
        if (suffix == null) return false;
        switch (suffix) {
        case BANNER:
        case WALL_BANNER:
            return false;
        default: break;
        }
        Material newMaterial = brush.blockColor.getMaterial(suffix);
        String oldBlockData = targetBlock.getBlockData().getAsString();
        int brackIndex = oldBlockData.indexOf("[");
        if (brackIndex > 0) {
            System.out.println(oldBlockData.substring(brackIndex));
        }
        BlockData newBlockData = brackIndex > 0
            ? Bukkit.createBlockData(newMaterial, oldBlockData.substring(brackIndex))
            : newMaterial.createBlockData();
        if (BlockMarker.hasId(targetBlock, "paintbrush_canvas")) {
            if (targetBlock.getType() == newBlockData.getMaterial()) return false;
            new PlayerChangeBlockEvent(player, targetBlock, newBlockData).callEvent();
            targetBlock.setBlockData(newBlockData);
        } else {
            final int maxDist = targetBlock.getWorld().getNoTickViewDistance();
            final int chunkX = targetBlock.getX() >> 4;
            final int chunkZ = targetBlock.getZ() >> 4;
            for (Player nearby : targetBlock.getWorld().getPlayers()) {
                Location loc = player.getLocation();
                if (Math.abs((loc.getBlockX() >> 4) - chunkX) > maxDist) continue;
                if (Math.abs((loc.getBlockZ() >> 4) - chunkZ) > maxDist) continue;
                nearby.sendBlockChange(targetBlock.getLocation(), newBlockData);
            }
        }
        targetBlock.getWorld().playSound(targetBlock.getLocation(),
                                         Sound.ITEM_BUCKET_FILL, SoundCategory.MASTER, 0.5f, 2.0f);
        targetBlock.getWorld().spawnParticle(Particle.REDSTONE,
                                             targetBlock.getRelative(face).getLocation().add(0.5, 0.5, 0.5),
                                             16,
                                             0.25f, 0.25f, 0.25f, 0.0f,
                                             new Particle.DustOptions(brush.blockColor.bukkitColor, 1.0f));
        return true;
    }

    @Override
    public void onPlayerLeftClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setUseInteractedBlock(Event.Result.DENY);
        event.setUseItemInHand(Event.Result.DENY);
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (item.getAmount() > 1) return;
        final Block block;
        final BlockFace face;
        if (event.hasBlock()) {
            block = event.getClickedBlock();
            face = event.getBlockFace();
        } else {
            Session session = MytemsPlugin.getInstance().getSessions().of(player);
            long cooldown = session.getCooldownInTicks(key.id);
            if (cooldown > 0) return;
            RayTraceResult rayTraceResult = player.rayTraceBlocks(RANGE);
            if (rayTraceResult == null) {
                session.setCooldown(key.id, 20);
                return;
            }
            block = rayTraceResult.getHitBlock();
            face = rayTraceResult.getHitBlockFace();
        }
        BlockColor blockColor = BlockColor.of(block.getType());
        if (blockColor == null || blockColor == brush.blockColor) return;
        Brush newBrush = Brush.of(blockColor);
        player.getEquipment().setItem(event.getHand(), newBrush.mytems.createItemStack());
        player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_DRINK, SoundCategory.MASTER, 0.5f, 1.5f);
        player.spawnParticle(Particle.REDSTONE,
                             block.getRelative(face).getLocation().add(0.5, 0.5, 0.5),
                             16,
                             0.25f, 0.25f, 0.25f, 0.0f,
                             new Particle.DustOptions(newBrush.blockColor.bukkitColor, 1.0f));
    }
}
