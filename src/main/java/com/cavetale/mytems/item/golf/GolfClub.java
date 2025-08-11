package com.cavetale.mytems.item.golf;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerChangeBlockEvent;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.worldmarker.block.BlockMarker;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Items.tooltip;
import static com.cavetale.mytems.util.Text.roman;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor @Getter
public final class GolfClub implements Mytem {
    private final Mytems key;
    private final GolfClubQuality quality;
    private Component displayName;
    private ItemStack prototype;

    public GolfClub(final Mytems key) {
        this.key = key;
        this.quality = GolfClubQuality.of(key);
    }

    @Override
    public void enable() {
        displayName = quality.getDisplayComponent();
        prototype = new ItemStack(key.material);
        tooltip(prototype, List.of(displayName,
                                   text(tiny("tier " + roman(quality.ordinal() + 1)), quality.getTextColor()),
                                   textOfChildren(text(tiny("strength "), GRAY), text(quality.getIntegerStrength(), WHITE)),
                                   textOfChildren(Mytems.MOUSE_RIGHT, text(" Swing", GRAY))));
        key.markItemStack(prototype);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setUseItemInHand(Event.Result.DENY);
        if (!event.hasBlock()) {
            return;
        }
        final Block block = event.getClickedBlock();
        if (block.getType() != Material.DRAGON_EGG) {
            return;
        }
        event.setUseInteractedBlock(Event.Result.DENY);
        if (BlockMarker.hasId(block)) {
            return;
        }
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) {
            return;
        }
        final RayTraceResult rayTrace = player.rayTraceBlocks(4.0);
        if (rayTrace == null || rayTrace.getHitBlock() == null || !block.equals(rayTrace.getHitBlock())) {
            return;
        }
        // No return!
        new PlayerChangeBlockEvent(player, block, Material.AIR.createBlockData(), item).callEvent();
        final Vector ballCenter = block.getLocation().add(0.5, 1.125, 0.5).toVector();
        final Vector hitPoint = rayTrace.getHitPosition();
        final Vector velocity = ballCenter.subtract(hitPoint)
            .normalize()
            .multiply(quality.getStrength());
        block.setType(Material.AIR);
        final Location ballLocation = block.getLocation().add(0.5, 0.0, 0.5);
        final FallingBlock ball = block.getWorld().spawn(ballLocation, FallingBlock.class, e -> {
                e.setBlockData(Material.DRAGON_EGG.createBlockData());
                e.setVelocity(velocity);
            });
        ballLocation.getWorld().playSound(ballLocation, Sound.BLOCK_DISPENSER_LAUNCH, SoundCategory.MASTER, 1f, 0.5f);
        ballLocation.getWorld().playSound(ballLocation, Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, SoundCategory.MASTER, 1f, 0.5f);
        ballLocation.getWorld().spawnParticle(Particle.BLOCK, ballLocation.clone().add(0, 0.5, 0), 32, 0.4, 0.125, 0.4, 0.0, Material.DRAGON_EGG.createBlockData());
    }
}
