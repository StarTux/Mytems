package com.cavetale.mytems;

import com.cavetale.core.command.CommandArgCompleter;
import com.cavetale.core.command.CommandNode;
import com.cavetale.core.command.CommandWarn;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.mytems.block.BlockRegistryEntry;
import com.cavetale.mytems.farming.FarmingKeys;
import com.cavetale.mytems.farming.FarmlandPlantBlock;
import com.cavetale.worldmarker.block.BlockMarker;
import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor
public final class MytemsBlockCommand {
    private final CommandNode blockNode;

    public void enable() {
        blockNode.addChild("tick").denyTabCompletion()
            .description("Trigger random block tick")
            .playerCaller(this::tick);
        blockNode.addChild("cheatgrowth").arguments("[days]")
            .description("Speed up growth")
            .playerCaller(this::cheatGrowth);
        blockNode.addChild("cheatWater").denyTabCompletion()
            .description("Speed up plant drinking")
            .playerCaller(this::cheatWater);
    }

    private void tick(Player player) {
        final Block block = player.getTargetBlockExact((int) player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE).getValue());
        if (block == null) {
            throw new CommandWarn("No target block");
        }
        final BlockRegistryEntry entry = BlockRegistryEntry.at(block);
        if (entry == null) {
            throw new CommandWarn("No block entry: " + Vec3i.of(block));
        }
        entry.randomTick(block, Instant.now());
        player.sendMessage(textOfChildren(text("Block ticked: ", YELLOW),
                                          text("" + Vec3i.of(block), WHITE)));
    }

    private boolean cheatGrowth(Player player, String[] args) {
        if (args.length != 1 && args.length != 0) return false;
        final int days = args.length >= 1
            ? CommandArgCompleter.requireInt(args[0], i -> i > 0)
            : 1;
        final Block block = player.getTargetBlockExact((int) player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE).getValue());
        if (block == null) {
            throw new CommandWarn("No target block");
        }
        final BlockRegistryEntry entry = BlockRegistryEntry.at(block);
        if (entry == null) {
            throw new CommandWarn("No block entry at " + Vec3i.of(block));
        }
        if (!(entry.getImplementation() instanceof FarmlandPlantBlock farmPlantBlock)) {
            throw new CommandWarn("No farmland plant at " + Vec3i.of(block) + ": " + entry.getId());
        }
        for (int i = 0; i < days; i += 1) {
            final Instant now = Instant.now();
            final Instant then = now.minus(Duration.ofHours(1));
            final var it = BlockMarker.getTag(block, false, tag -> {
                    FarmingKeys.GROWTH_START.setInstant(tag, then);
                    return true;
                });
            if (it == null) {
                throw new CommandWarn("Block has no tag: " + Vec3i.of(block));
            }
            entry.randomTick(block, now);
        }
        player.sendMessage(textOfChildren(text(farmPlantBlock.getType().getDisplayName(), WHITE),
                                          text(" attempted to grow ", YELLOW),
                                          text(days, WHITE),
                                          text(" day" + (days != 1 ? "s" : "") + ": ", YELLOW),
                                          text("" + Vec3i.of(block), WHITE)));
        return true;
    }

    private void cheatWater(Player player) {
        final Block block = player.getTargetBlockExact((int) player.getAttribute(Attribute.BLOCK_INTERACTION_RANGE).getValue());
        if (block == null) {
            throw new CommandWarn("No target block");
        }
        final BlockRegistryEntry entry = BlockRegistryEntry.at(block);
        if (entry == null) {
            throw new CommandWarn("No block entry at " + Vec3i.of(block));
        }
        if (!(entry.getImplementation() instanceof FarmlandPlantBlock farmlandPlantBlock)) {
            throw new CommandWarn("No farmland plant at " + Vec3i.of(block) + ": " + entry.getId());
        }
        final var it = BlockMarker.getTag(block, false, tag -> {
                final Instant inst = Instant.now().minus(farmlandPlantBlock.getType().getWateringNeed().getDrinkInterval());
                FarmingKeys.LAST_DRINK.setInstant(tag, inst);
                player.sendMessage(textOfChildren(text("Growth start was set to ", YELLOW),
                                                  text("" + inst, WHITE),
                                                  text(": ", YELLOW),
                                                  text("" + Vec3i.of(block), WHITE)));
                return true;
            });
        if (it == null) {
            throw new CommandWarn("Block has no tag: " + Vec3i.of(block));
        }
    }
}
