package com.cavetale.mytems.item.luminator;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerBreakBlockEvent;
import com.cavetale.core.event.block.PlayerChangeBlockEvent;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Lightable;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.block.data.type.Candle;
import org.bukkit.block.data.type.Light;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a block which light can be drawn from.
 */
@Getter @RequiredArgsConstructor
public final class Luminance {
    private final int light;
    private final Supplier<Boolean> drawLightFunc;

    /**
     * Draw light from the block.  This will usually result in the
     * modification or breaking of the block.
     */
    public boolean drawLight() {
        return drawLightFunc.get();
    }

    public static Luminance of(Player player, Block block, ItemStack itemStack) {
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return null;
        return switch (block.getType()) {
        case FIRE -> new Luminance(15 / 5, () -> {
                if (!new PlayerBreakBlockEvent(player, block, itemStack).callEvent()) return false;
                block.setType(Material.AIR);
                return true;
        });
        case SOUL_FIRE -> new Luminance(10 / 5, () -> {
                if (!PlayerBreakBlockEvent.call(player, block)) return false;
                block.setType(Material.AIR);
                return true;
        });
        case CAMPFIRE -> {
            Campfire campfire = (Campfire) block.getBlockData();
            if (!campfire.isLit()) yield null;
            yield new Luminance(15, () -> {
                    campfire.setLit(false);
                    if (!new PlayerChangeBlockEvent(player, block, campfire, itemStack).callEvent()) return false;
                    block.setBlockData(campfire);
                    return true;
            });
        }
        case SOUL_CAMPFIRE -> {
            Campfire campfire = (Campfire) block.getBlockData();
            if (!campfire.isLit()) yield null;
            yield new Luminance(10, () -> {
                    campfire.setLit(false);
                    if (!new PlayerChangeBlockEvent(player, block, campfire, itemStack).callEvent()) return false;
                    block.setBlockData(campfire);
                    return true;
            });
        }
        case CANDLE,
            WHITE_CANDLE,
            ORANGE_CANDLE,
            MAGENTA_CANDLE,
            LIGHT_BLUE_CANDLE,
            YELLOW_CANDLE,
            LIME_CANDLE,
            PINK_CANDLE,
            GRAY_CANDLE,
            LIGHT_GRAY_CANDLE,
            CYAN_CANDLE,
            PURPLE_CANDLE,
            BLUE_CANDLE,
            BROWN_CANDLE,
            GREEN_CANDLE,
            RED_CANDLE,
            BLACK_CANDLE -> {
            Candle candle = (Candle) block.getBlockData();
            if (!candle.isLit()) yield null;
            yield new Luminance(3 * candle.getCandles(), () -> {
                    candle.setLit(false);
                    if (!new PlayerChangeBlockEvent(player, block, candle).callEvent()) return false;
                    block.setBlockData(candle);
                    return true;
            });
        }
        case FURNACE, BLAST_FURNACE, SMOKER -> {
            Furnace furnace = (Furnace) block.getState();
            if (furnace.getBurnTime() <= 0) yield null;
            yield new Luminance(13, () -> {
                    // No event is called here.
                    furnace.setBurnTime((short) 0);
                    furnace.update();
                    return true;
            });
        }
        case JACK_O_LANTERN -> {
            yield new Luminance(64, () -> {
                    Directional jack = (Directional) block.getBlockData();
                    Directional carv = (Directional) Material.CARVED_PUMPKIN.createBlockData();
                    carv.setFacing(jack.getFacing());
                    if (!new PlayerChangeBlockEvent(player, block, carv).callEvent()) return false;
                    block.setBlockData(carv);
                    return true;
            });
        }
        case WHITE_CANDLE_CAKE,
            ORANGE_CANDLE_CAKE,
            MAGENTA_CANDLE_CAKE,
            LIGHT_BLUE_CANDLE_CAKE,
            YELLOW_CANDLE_CAKE,
            LIME_CANDLE_CAKE,
            PINK_CANDLE_CAKE,
            GRAY_CANDLE_CAKE,
            LIGHT_GRAY_CANDLE_CAKE,
            CYAN_CANDLE_CAKE,
            PURPLE_CANDLE_CAKE,
            BLUE_CANDLE_CAKE,
            BROWN_CANDLE_CAKE,
            GREEN_CANDLE_CAKE,
            RED_CANDLE_CAKE,
            BLACK_CANDLE_CAKE -> {
            Lightable lit = (Lightable) block.getBlockData();
            if (!lit.isLit()) yield null;
            yield new Luminance(3, () -> {
                    lit.setLit(false);
                    if (!new PlayerChangeBlockEvent(player, block, lit).callEvent()) return false;
                    block.setBlockData(lit);
                    return true;
            });
        }
        case LIGHT -> {
            Light light = (Light) block.getBlockData();
            int level = light.getLevel();
            if (level <= 0) yield null;
            yield new Luminance(1, () -> {
                    BlockData newData;
                    if (level > 1) {
                        light.setLevel(level - 1);
                        newData = light;
                    } else {
                        newData = light.isWaterlogged()
                            ? Material.WATER.createBlockData()
                            : Material.AIR.createBlockData();
                    }
                    if (!new PlayerChangeBlockEvent(player, block, newData).callEvent()) return false;
                    block.setBlockData(newData);
                    if (level > 1) {
                        player.spawnParticle(Particle.BLOCK_MARKER, block.getLocation().add(0.5, 0.5, 0.5), 1, 0.0, 0.0, 0.0, 0.0, newData);
                    }
                    return true;
            });
        }
        default -> null;
        };
    }
}
