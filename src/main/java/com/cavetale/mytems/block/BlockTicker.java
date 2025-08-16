package com.cavetale.mytems.block;

import com.cavetale.mytems.MytemsPlugin;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.block.Block;

@RequiredArgsConstructor
public final class BlockTicker {
    private final MytemsPlugin plugin;
    private final Random random = new Random();

    public void enable() {
        Bukkit.getScheduler().runTaskTimer(plugin, this::tick, 1L, 1L);
    }

    private void tick() {
        for (World world : Bukkit.getWorlds()) {
            final int randomTickSpeed = world.getGameRuleValue(GameRule.RANDOM_TICK_SPEED);
            if (randomTickSpeed < 1) continue;
            final int minHeight = world.getMinHeight();
            final int maxHeight = world.getMaxHeight();
            for (Chunk chunk : world.getLoadedChunks()) {
                if (chunk.getLoadLevel() != Chunk.LoadLevel.ENTITY_TICKING) {
                    continue;
                }
                for (int i = 0; i < randomTickSpeed; i += 1) {
                    final int y = minHeight + random.nextInt(maxHeight - minHeight);
                    final int x = random.nextInt(16);
                    final int z = random.nextInt(16);
                    final Block block = chunk.getBlock(x, y, z);
                    final BlockRegistryEntry entry = BlockRegistryEntry.at(block);
                    if (entry == null) continue;
                    entry.tick(block);
                }
            }
        }
    }
}
