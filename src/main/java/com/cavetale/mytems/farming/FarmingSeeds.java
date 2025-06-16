package com.cavetale.mytems.farming;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.block.BlockRegistryEntry;
import com.cavetale.mytems.util.Text;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor @Getter
public final class FarmingSeeds implements Mytem {
    private final Mytems key;
    private final FarmingPlantType farmingPlantType;
    private ItemStack prototype;
    private Component displayName;

    public FarmingSeeds(final Mytems key) {
        this.key = key;
        this.farmingPlantType = FarmingPlantType.ofSeedItem(key);
    }

    @Override
    public void enable() {
        displayName = text(Text.toCamelCase(key, " "), GREEN);
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                tooltip(meta, List.of(displayName));
                key.markItemMeta(meta);
            });
        farmingPlantType.enable();
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setUseItemInHand(Event.Result.DENY);
        if (!event.hasBlock()) return;
        final Block farmland = event.getClickedBlock();
        if (farmland.getType() != Material.FARMLAND) return;
        if (event.getBlockFace() != BlockFace.UP) return;
        if (BlockRegistryEntry.at(farmland) != null) return;
        final GrowthStage growthStage = farmingPlantType.getGrowthStages().get(0);
        if (!growthStage.checkAdditionalBlocks(farmland)) return;
        // No return
        item.subtract(1);
        farmingPlantType.getBlockRegistryEntry().setBlockId(farmland);
        growthStage.place(farmland);
        farmland.getWorld().playSound(farmland.getLocation().add(0.5, 1.5, 0.5), Sound.ITEM_CROP_PLANT, 1f, 1f);
    }
}
