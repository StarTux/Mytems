package com.cavetale.mytems.item.wateringcan;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerChangeBlockEvent;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.BlockColor;
import com.cavetale.mytems.util.Json;
import com.cavetale.mytems.util.Text;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.type.CaveVinesPlant;
import org.bukkit.block.data.type.Farmland;
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
@RequiredArgsConstructor
public final class WateringCan implements Mytem {
    protected final Mytems key;
    protected WateringCanType type;
    protected ItemStack prototype;
    protected Component displayName;
    protected List<Component> tooltip;

    @Override
    public void enable() {
        this.type = WateringCanType.of(key);
        this.displayName = text(Text.toCamelCase(key, " "), type.textColor);
        this.prototype = new ItemStack(key.material);
        tooltip = List.of(displayName,
                          text(tiny("Use on crops to"), GRAY),
                          text(tiny("make them grow"), GRAY),
                          text(tiny("super fast."), GRAY));
        prototype.editMeta(meta -> {
                meta.addItemFlags(HIDE_ATTRIBUTES);
                key.markItemMeta(meta);
            });
        WateringCanTag tag = new WateringCanTag();
        tag.store(key, prototype);
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
        WateringCanTag tag = new WateringCanTag();
        tag.load(key, item);
        final boolean creative = player.getGameMode() == GameMode.CREATIVE;
        if (block.getType() == Material.WATER_CAULDRON) {
            if (tag.water == 0) return;
            if (!(block.getBlockData() instanceof Levelled levelled)) return;
            tag.water = 0;
            tag.store(key, item);
            int level = levelled.getLevel();
            if (!creative) {
                if (level == levelled.getMinimumLevel()) {
                    block.setType(Material.CAULDRON);
                } else {
                    levelled.setLevel(level - 1);
                    block.setBlockData(levelled);
                }
            }
            Location location = block.getLocation().add(0.5, 1.0, 0.5);
            block.getWorld().spawnParticle(SPLASH, location, 38, 0.2, 0.2, 0.2, 0.0);
            block.getWorld().playSound(location, ITEM_BUCKET_FILL, BLOCKS, 0.5f, 2.0f);
        } else {
            if (!creative && tag.water >= type.maxWater) {
                player.sendMessage(text("Watering can is empty", RED));
                return;
            }
            for (int z = -type.radius; z <= type.radius && tag.water < type.maxWater; z += 1) {
                for (int x = -type.radius; x <= type.radius && tag.water < type.maxWater; x += 1) {
                    if (water(player, block.getRelative(x, 0, z), item, x == 0 && z == 0)) {
                        waterCount += 1;
                        if (!creative) {
                            tag.water += 1;
                        }
                    }
                }
            }
            if (waterCount == 0) return;
            if (!creative) {
                if (tag.water < type.maxWater) {
                    tag.store(key, item);
                } else {
                    player.getInventory().setItem(event.getHand(), type.emptyMytems.createItemStack());
                }
            }
            Location location = block.getLocation().add(0.5, 0.5, 0.5);
            block.getWorld().playSound(location, BLOCK_BREWING_STAND_BREW, BLOCKS, 0.6f, 1.7f);
            block.getWorld().playSound(location, WEATHER_RAIN, BLOCKS, 0.3f, 2.0f);
        }
    }

    private boolean water(Player player, Block block, ItemStack item, boolean direct) {
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return false;
        if (block.getBlockData() instanceof CaveVinesPlant caveVines) {
            if (caveVines.hasBerries()) return false;
            caveVines.setBerries(true);
            new PlayerChangeBlockEvent(player, block, caveVines, item).callEvent();
            block.setBlockData(caveVines);
            Location location = block.getLocation().add(0.5, 0.5, 0.5);
            block.getWorld().spawnParticle(SPLASH, location, 38, 0.2, 0.2, 0.2, 0.0);
            return true;
        } else if (block.getBlockData() instanceof Ageable ageable) {
            if (ageable.getAge() >= ageable.getMaximumAge()) return false;
            ageable.setAge(ageable.getAge() + 1);
            new PlayerChangeBlockEvent(player, block, ageable, item).callEvent();
            block.setBlockData(ageable);
            Location location = block.getLocation().add(0.5, 0.5, 0.5);
            block.getWorld().spawnParticle(SPLASH, location, 38, 0.2, 0.2, 0.2, 0.0);
            return true;
        } else if (block.getBlockData() instanceof Farmland farmland) {
            if (farmland.getMoisture() >= farmland.getMaximumMoisture()) return false;
            farmland.setMoisture(farmland.getMaximumMoisture());
            new PlayerChangeBlockEvent(player, block, farmland, item).callEvent();
            block.setBlockData(farmland);
            final Location location = block.getLocation().add(0.5, 1.0, 0.5);
            block.getWorld().spawnParticle(SPLASH, location, 38, 0.2, 0.2, 0.2, 0.0);
            return true;
        } else if (direct && Tag.CONCRETE_POWDER.isTagged(block.getType())) {
            final BlockColor color = BlockColor.of(block.getType());
            if (color == null) return false;
            final Material newMaterial = color.getMaterial(BlockColor.Suffix.CONCRETE);
            new PlayerChangeBlockEvent(player, block, newMaterial.createBlockData(), item).callEvent();
            block.setBlockData(newMaterial.createBlockData());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public WateringCanTag serializeTag(ItemStack itemStack) {
        WateringCanTag tag = new WateringCanTag();
        tag.load(key, itemStack);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        ItemStack itemStack = createItemStack();
        WateringCanTag tag = Json.deserialize(serialized, WateringCanTag.class);
        if (tag != null && !tag.isEmpty()) {
            tag.store(key, itemStack);
        }
        return itemStack;
    }
}
