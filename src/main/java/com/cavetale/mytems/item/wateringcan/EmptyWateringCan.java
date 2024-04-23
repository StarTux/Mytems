package com.cavetale.mytems.item.wateringcan;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Text;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static org.bukkit.Particle.*;
import static org.bukkit.Sound.*;
import static org.bukkit.SoundCategory.*;
import static org.bukkit.inventory.ItemFlag.*;

@Getter
public final class EmptyWateringCan implements Mytem {
    private final Mytems key;
    private final WateringCanType type;
    private final ItemStack prototype;
    private final Component displayName;
    protected final List<Component> tooltip;

    public EmptyWateringCan(final Mytems key) {
        this.key = key;
        this.type = WateringCanType.of(key);
        this.displayName = text(Text.toCamelCase(key, " "), type.textColor);
        this.prototype = new ItemStack(key.material);
        tooltip = List.of(displayName,
                          text(tiny("Fill with water"), GRAY),
                          text(tiny("at a cauldron."), GRAY));
        prototype.editMeta(meta -> {
                meta.setUnbreakable(true);
                meta.addItemFlags(HIDE_UNBREAKABLE, HIDE_ATTRIBUTES);
                tooltip(meta, tooltip);
                key.markItemMeta(meta);
            });
    }

    @Override
    public void enable() {
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setUseItemInHand(Event.Result.DENY);
        if (!event.hasBlock()) return;
        int waterCount = 0;
        Block block = event.getClickedBlock();
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return;
        if (block.getType() == Material.WATER_CAULDRON) {
            if (!(block.getBlockData() instanceof Levelled levelled)) return;
            type.filledMytems.setItem(item);
            int level = levelled.getLevel();
            if (level == levelled.getMinimumLevel()) {
                block.setType(Material.CAULDRON);
            } else {
                levelled.setLevel(level - 1);
                block.setBlockData(levelled);
            }
            Location location = block.getLocation().add(0.5, 1.0, 0.5);
            block.getWorld().spawnParticle(WATER_SPLASH, location, 38, 0.2, 0.2, 0.2, 0.0);
            block.getWorld().playSound(location, ITEM_BUCKET_FILL, BLOCKS, 0.5f, 2.0f);
        }
    }
}
