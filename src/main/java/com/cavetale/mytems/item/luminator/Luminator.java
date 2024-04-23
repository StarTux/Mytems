package com.cavetale.mytems.item.luminator;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerChangeBlockEvent;
import com.cavetale.core.util.Json;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.block.data.type.Light;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public final class Luminator implements Mytem {
    private final Mytems key;
    private Component displayName;
    private ItemStack prototype;
    private final boolean empty;
    private final List<Component> baseText = new ArrayList<>();
    public static final int MAX_LIGHT = 64;

    public Luminator(final Mytems key) {
        this.key = key;
        this.empty = key == Mytems.EMPTY_LUMINATOR;
        this.displayName = text("Luminator", YELLOW);
    }

    @Override
    public void enable() {
        this.prototype = new ItemStack(key.material);
        baseText.addAll(List.of(displayName,
                                text("Draw luminance from", GRAY),
                                text("the world and place", GRAY),
                                text("invisible light sources", GRAY),
                                empty(),
                                textOfChildren(text(tiny("light"), GRAY), text(" 0", YELLOW)),
                                textOfChildren(Mytems.MOUSE_LEFT, text(" Place Light", GRAY)),
                                textOfChildren(Mytems.MOUSE_RIGHT, text(" Draw Light", GRAY)),
                                textOfChildren(Mytems.SHIFT_KEY, text(" View Light Blocks", GRAY))));
        prototype.editMeta(meta -> {
                tooltip(meta, baseText);
                meta.addItemFlags(ItemFlag.values());
                key.markItemMeta(meta);
            });
    }

    public List<Component> baseTextWithLight(int light) {
        List<Component> result = new ArrayList<>(baseText);
        result.set(5, textOfChildren(text(tiny("light"), GRAY), text(" " + light, YELLOW)));
        return result;
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public LuminatorTag serializeTag(ItemStack itemStack) {
        LuminatorTag tag = new LuminatorTag();
        tag.load(itemStack);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        ItemStack itemStack = createItemStack();
        LuminatorTag tag = Json.deserialize(serialized, LuminatorTag.class);
        if (tag != null && !tag.isEmpty()) {
            tag.store(itemStack);
        }
        return itemStack;
    }

    @Override
    public void onPlayerLeftClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (player.getGameMode() == GameMode.SPECTATOR) return;
        if (!event.hasBlock()) return;
        event.setCancelled(true);
        LuminatorTag tag = serializeTag(item);
        if (tag.light <= 0) return;
        Block block = event.getClickedBlock().getRelative(event.getBlockFace());
        BlockData blockData = block.getBlockData();
        boolean water = false;
        int oldLight = 0;
        if (!blockData.getMaterial().isAir()) {
            if (blockData.getMaterial() == Material.WATER && blockData instanceof Levelled levelled && levelled.getLevel() == 0) {
                water = true;
            } else if (blockData.getMaterial() == Material.LIGHT && blockData instanceof Levelled levelled) {
                oldLight = levelled.getLevel();
            } else {
                return;
            }
        }
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) return;
        final Light light = (Light) Material.LIGHT.createBlockData();
        final int lightAmount = Math.min(light.getMaximumLevel(), tag.light);
        light.setLevel(Math.max(light.getMinimumLevel(), Math.min(light.getMaximumLevel(), lightAmount + oldLight)));
        light.setWaterlogged(water);
        if (!new PlayerChangeBlockEvent(player, block, light, item).callEvent()) return;
        block.setBlockData(light);
        if (player.getGameMode() != GameMode.CREATIVE) {
            tag.light = Math.max(0, tag.light - lightAmount);
            if (tag.light <= 0) {
                Mytems.EMPTY_LUMINATOR.setItem(item);
            } else {
                tag.store(item);
            }
            if (tag.light <= 0) {
                player.sendActionBar(textOfChildren(Mytems.EMPTY_LUMINATOR, text(" Luminator Level ", GRAY), text(tag.light, YELLOW)));
            } else {
                player.sendActionBar(textOfChildren(Mytems.LUMINATOR, text(" Luminator Level ", GRAY), text(tag.light, YELLOW)));
            }
        }
        Location soundLocation = block.getLocation().add(0.5, 0.5, 0.5);
        block.getWorld().playSound(soundLocation, Sound.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 0.5f, 2.0f);
        Location particleLocation = soundLocation;
        block.getWorld().spawnParticle(Particle.SMALL_FLAME, particleLocation, lightAmount, .25, .25, .25, 0.025);
        player.spawnParticle(Particle.BLOCK_MARKER, block.getLocation().add(0.5, 0.5, 0.5), 1, 0.0, 0.0, 0.0, 0.0,
                             block.getBlockData());
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        if (player.getGameMode() == GameMode.SPECTATOR) return;
        if (!event.hasBlock()) return;
        event.setCancelled(true);
        Block block = event.getClickedBlock();
        LuminatorTag tag = serializeTag(item);
        if (tag.light >= MAX_LIGHT && player.getGameMode() != GameMode.CREATIVE) return;
        Luminance luminance = Luminance.of(player, block, item);
        if (luminance == null) {
            block = block.getRelative(event.getBlockFace());
            luminance = Luminance.of(player, block, item);
            if (luminance == null) return;
        }
        if (!luminance.drawLight()) return;
        if (empty) Mytems.LUMINATOR.setItem(item);
        int newLight = Math.min(MAX_LIGHT, tag.light + luminance.getLight());
        int drawnLight = newLight - tag.light;
        tag.light = newLight;
        tag.store(item);
        player.sendActionBar(textOfChildren(Mytems.LUMINATOR, text(" Luminator Level ", GRAY), text(tag.light, YELLOW)));
        Location soundLocation = block.getLocation().add(0.5, 0.5, 0.5);
        block.getWorld().playSound(soundLocation, Sound.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 0.5f, 2.0f);
        Location particleLocation = block.isEmpty() || block.isLiquid()
            ? soundLocation
            : block.getRelative(event.getBlockFace()).getLocation().add(0.5, 0.5, 0.5);
        block.getWorld().spawnParticle(Particle.SMALL_FLAME, particleLocation, drawnLight, .25, .25, .25, 0.025);
    }

    @Override
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event, Player player, ItemStack item, EquipmentSlot slot) {
        Block origin = player.getLocation().getBlock();
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, origin)) return;
        final int range = 16;
        int count = 0;
        Block lastBlock = null;
        int minDist = 0;
        for (int dy = -range; dy <= range; dy += 1) {
            for (int dz = -range; dz <= range; dz += 1) {
                for (int dx = -range; dx <= range; dx += 1) {
                    Block block = origin.getRelative(dx, dy, dz);
                    if (block.getType() != Material.LIGHT) continue;
                    player.spawnParticle(Particle.BLOCK_MARKER, block.getLocation().add(0.5, 0.5, 0.5), 1, 0.0, 0.0, 0.0, 0.0,
                                         block.getBlockData());
                    count += 1;
                    int dist = Math.abs(origin.getX() - block.getX())
                        + Math.abs(origin.getY() - block.getY())
                        + Math.abs(origin.getZ() - block.getZ());
                    if (lastBlock == null || dist < minDist) {
                        lastBlock = block;
                        minDist = dist;
                    }
                }
            }
        }
        if (count > 0) {
            player.playSound(lastBlock.getLocation().add(0.5, 0.5, 0.5),
                             Sound.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 0.5f, 2.0f);
        }
    }
}
