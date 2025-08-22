package com.cavetale.mytems.farming;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.mytems.block.BlockEventHandler;
import com.cavetale.mytems.block.BlockImplementation;
import java.time.Instant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import static com.cavetale.mytems.MytemsPlugin.mytemsPlugin;

@Getter
@RequiredArgsConstructor
public abstract class AbstractPlantBlock implements BlockImplementation, BlockEventHandler {
    protected final FarmingPlantType type;

    /**
     * Default implementation just cancels.
     */
    @Override
    public void onBlockBreak(BlockBreakEvent event, Block block) {
        event.setCancelled(true);
    }

    /**
     * Default implementation does nothing.
     */
    @Override
    public void randomTick(Block block, Instant now) { }

    public final void log(Block block, String msg) {
        mytemsPlugin().getLogger().info("[FarmingPlantBlock] [" + type.getDisplayName() + "] [" + Vec3i.of(block) + "] " + msg);
    }

    public final void warn(Block block, String msg) {
        mytemsPlugin().getLogger().severe("[FarmingPlantBlock] [" + type.getDisplayName() + "] [" + Vec3i.of(block) + "] " + msg);
    }
}
