package com.cavetale.mytems.item.wateringcan;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Json;
import com.cavetale.mytems.util.Text;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
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
public final class WateringCan implements Mytem {
    protected final Mytems key;
    protected final WateringCanType type;
    protected final ItemStack prototype;
    protected final Component displayName;
    protected final List<Component> tooltip;

    public WateringCan(final Mytems key) {
        this.key = key;
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
        tag.wateringCan = this;
        tag.store(prototype);
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
        WateringCanTag tag = new WateringCanTag();
        tag.wateringCan = this;
        tag.load(item);
        if (block.getType() == Material.WATER_CAULDRON) {
            if (tag.water == 0) return;
            if (!(block.getBlockData() instanceof Levelled levelled)) return;
            tag.water = 0;
            tag.store(item);
            int level = levelled.getLevel();
            if (level == levelled.getMinimumLevel()) {
                block.setType(Material.CAULDRON);
            } else {
                levelled.setLevel(level - 1);
                block.setBlockData(levelled);
            }
            Location location = block.getLocation().add(0.5, 1.0, 0.5);
            block.getWorld().spawnParticle(SPLASH, location, 38, 0.2, 0.2, 0.2, 0.0);
            block.getWorld().playSound(location, ITEM_BUCKET_FILL, BLOCKS, 0.5f, 2.0f);
        } else {
            if (tag.water >= type.maxWater) {
                player.sendMessage(text("Watering can is empty", RED));
                return;
            }
            for (int z = -type.radius; z <= type.radius && tag.water < type.maxWater; z += 1) {
                for (int x = -type.radius; x <= type.radius && tag.water < type.maxWater; x += 1) {
                    if (water(player, block.getRelative(x, 0, z))) {
                        waterCount += 1;
                        tag.water += 1;
                    }
                }
            }
            if (waterCount == 0) return;
            if (tag.water < type.maxWater) {
                tag.store(item);
            } else {
                type.emptyMytems.setItem(item);
            }
            Location location = block.getLocation().add(0.5, 0.5, 0.5);
            block.getWorld().playSound(location, BLOCK_BREWING_STAND_BREW, BLOCKS, 0.6f, 1.7f);
            block.getWorld().playSound(location, WEATHER_RAIN, BLOCKS, 0.3f, 2.0f);
        }
    }

    private boolean water(Player player, Block block) {
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return false;
        if (!(block.getBlockData() instanceof Ageable ageable)) return false;
        if (ageable.getAge() >= ageable.getMaximumAge()) return false;
        ageable.setAge(ageable.getAge() + 1);
        block.setBlockData(ageable);
        Location location = block.getLocation().add(0.5, 0.5, 0.5);
        block.getWorld().spawnParticle(SPLASH, location, 38, 0.2, 0.2, 0.2, 0.0);
        return true;
    }

    @Override
    public WateringCanTag serializeTag(ItemStack itemStack) {
        WateringCanTag tag = new WateringCanTag();
        tag.wateringCan = this;
        tag.load(itemStack);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        ItemStack itemStack = createItemStack();
        WateringCanTag tag = Json.deserialize(serialized, WateringCanTag.class);
        if (tag != null && !tag.isEmpty()) {
            tag.wateringCan = this;
            tag.store(itemStack);
        }
        return itemStack;
    }
}
