package com.cavetale.mytems.item;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerBreakBlockEvent;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import com.destroystokyo.paper.block.BlockSoundGroup;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor @Getter
public final class SnowShovel implements Mytem {
    private static final int RADIUS = 4;
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        displayName = Text.gradient("Snow Shovel", NamedTextColor.BLUE, NamedTextColor.GRAY, NamedTextColor.WHITE);
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                meta.setUnbreakable(true);
                meta.displayName(displayName);
                meta.addItemFlags(ItemFlag.values());
                Items.text(meta,
                           List.of(displayName,
                                   Component.text("Plow snow in a", NamedTextColor.GRAY),
                                   Component.text(RADIUS + " block radius!", NamedTextColor.GRAY)));
                Items.unbreakable(meta);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, Player player, ItemStack itemStack) {
        if (event.isCancelled()) return;
        Block block = event.getBlock();
        if (block.getType() != Material.SNOW) return;
        for (int z = -RADIUS; z <= RADIUS; z += 1) {
            for (int x = -RADIUS; x <= RADIUS; x += 1) {
                if (x == 0 && z == 0) continue;
                if (x * x + z * z > RADIUS * RADIUS) continue;
                breakBlock(player, block.getRelative(x, 0, z), itemStack);
            }
        }
    }

    private void breakBlock(Player player, Block block, ItemStack itemStack) {
        if (block.getType() != Material.SNOW) return;
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return;
        PlayerBreakBlockEvent.call(player, block);
        BlockSoundGroup soundGroup = block.getSoundGroup();
        block.breakNaturally(itemStack, true);
        if (soundGroup != null) {
            Sound sound = soundGroup.getBreakSound();
            if (sound != null) {
                block.getWorld().playSound(block.getLocation(), sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
        }
        block.getWorld().spawnParticle(Particle.SWEEP_ATTACK, block.getLocation().add(0.5, 0.5, 0.5), 1, 0.0, 0.0, 0.0, 0.0);
        // Block#breakNaturally doesn't drop snowballs, so we do it!
        if (block.getWorld().getGameRuleValue(GameRule.DO_TILE_DROPS)
            && player.getGameMode() != GameMode.CREATIVE) {
            block.getWorld().dropItem(block.getLocation().add(0.5, 0.0, 0.5),
                                      new ItemStack(Material.SNOWBALL));
        }
    }
}
