package com.cavetale.mytems.item.spleef;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.struct.Vec3i;
import com.cavetale.core.util.Json;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.MytemsPlugin.blockBreakListener;
import static com.cavetale.mytems.MytemsPlugin.plugin;
import static java.util.Objects.requireNonNull;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public final class SpleefShovel implements Mytem {
    private final Mytems key;
    private final SpleefShovelTier tier;
    private ItemStack prototype;
    private Component displayName;
    private ItemStack silkTouch;
    private List<Vec3i> faces = new ArrayList<>();

    public SpleefShovel(final Mytems mytems) {
        this.key = mytems;
        this.tier = requireNonNull(SpleefShovelTier.of(mytems));
    }

    @Override
    public void enable() {
        this.displayName = text(tier.getDisplayName(), tier.getColor());
        this.prototype = new ItemStack(key.material);
        tier.createTag().store(prototype);
        prototype.editMeta(meta -> key.markItemMeta(meta));
        this.silkTouch = new ItemStack(Material.NETHERITE_SHOVEL);
        silkTouch.editMeta(meta -> meta.addEnchant(Enchantment.SILK_TOUCH, 1, true));
        for (int y = -1; y <= 1; y += 1) {
            for (int z = -1; z <= 1; z += 1) {
                for (int x = -1; x <= 1; x += 1) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    if (x != 0 && y != 0 && z != 0) continue;
                    faces.add(Vec3i.of(x, y, z));
                }
            }
        }
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, Player player, ItemStack item) {
        if (event.isCancelled()) return;
        if (!Tag.MINEABLE_SHOVEL.isTagged(event.getBlock().getType())) return;
        if (!event.getBlock().isPreferredTool(item)) return;
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, event.getBlock())) return;
        final SpleefShovelTag tag = serializeTag(item);
        final int breakRangeCount = breakRange(player, event.getBlock(), item, tag);
        final int bonusXp = (int) Math.sqrt((double) breakRangeCount);
        Bukkit.getScheduler().runTask(plugin(), () -> {
                if (tag.addXpAndNotify(player, 1 + bonusXp)) {
                    tag.store(item);
                }
            });
    }

    private int breakRange(Player player, Block center, ItemStack item, SpleefShovelTag tag) {
        if (player.isSneaking()) return 0;
        // Check range
        final int range = tag.getRange();
        final boolean brush = tag.getEffectiveUpgradeLevel(SpleefShovelStat.BRUSH) > 0;
        final boolean floating = tag.getEffectiveUpgradeLevel(SpleefShovelStat.FLOAT) > 0;
        if (range <= 0) return 0;
        // Check food
        if (player.getGameMode() != GameMode.CREATIVE && player.getFoodLevel() == 0) {
            return 0;
        }
        // Flood fill
        final Vec3i centerVec = Vec3i.of(center);
        final int rangeSquared = range * range;
        final List<Block> blockBreakList = new ArrayList<>();
        blockBreakList.add(center);
        final Set<Vec3i> done = new HashSet<>();
        for (int i = 0; i < blockBreakList.size(); i += 1) {
            final Block block = blockBreakList.get(i);
            Collections.shuffle(faces);
            for (Vec3i face : faces) {
                final Block nbor = block.getRelative(face.x, face.y, face.z);
                final Vec3i nborVec = Vec3i.of(nbor);
                if (done.contains(nborVec)) continue;
                done.add(nborVec);
                if (nborVec.subtract(centerVec).lengthSquared() > rangeSquared) continue;
                if (brush && isSuspicious(nbor.getType())) continue;
                if (!Tag.MINEABLE_SHOVEL.isTagged(nbor.getType())) continue;
                if (!nbor.isPreferredTool(item)) continue;
                if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, nbor)) continue;
                blockBreakList.add(nbor);
            }
        }
        // Break and take food
        int count = 0;
        for (int i = 1; i < blockBreakList.size(); i += 1) {
            final Block breakBlock = blockBreakList.get(i);
            if (player.getGameMode() != GameMode.CREATIVE) {
                if (player.getSaturation() >= 0.01f) {
                    player.setSaturation(Math.max(0.0f, player.getSaturation() - 0.025f));
                } else if (player.getFoodLevel() > 0) {
                    if (ThreadLocalRandom.current().nextInt(32) == 0) {
                        player.setFoodLevel(Math.max(0, player.getFoodLevel() - 1));
                    }
                } else {
                    break;
                }
            }
            final BlockData blockData = breakBlock.getBlockData();
            final Sound breakSound = breakBlock.getBlockSoundGroup().getBreakSound();
            final boolean breakResult = !floating
                ? blockBreakListener().breakBlockAndPickup(player, silkTouch, breakBlock)
                : blockBreakListener().breakBlockNoPhysicsAndPickup(player, silkTouch, breakBlock);
            if (!breakResult) {
                continue;
            }
            // Effects
            final Location location = breakBlock.getLocation().add(0.5, 0.5, 0.5);
            location.getWorld().spawnParticle(Particle.BLOCK, location, 8, 0.0, 0.25, 0.25, 0.25, blockData);
            location.getWorld().playSound(location, breakSound, 0.5f, 1f);
            count += 1;
        }
        return count;
    }

    private static boolean isSuspicious(Material material) {
        return material == Material.SUSPICIOUS_GRAVEL
            || material == Material.SUSPICIOUS_SAND;
    }

    @Override
    public SpleefShovelTag serializeTag(ItemStack itemStack) {
        final SpleefShovelTag tag = tier.createTag();
        tag.load(itemStack);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        final ItemStack itemStack = createItemStack();
        final SpleefShovelTag tag = Json.deserialize(serialized, tier.getTagClass());
        if (tag != null && !tag.isEmpty()) {
            tag.store(itemStack);
        }
        return itemStack;
    }
}
