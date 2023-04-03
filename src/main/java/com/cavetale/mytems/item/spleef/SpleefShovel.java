package com.cavetale.mytems.item.spleef;

import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.MytemsPlugin.blockBreakListener;
import static java.util.Objects.requireNonNull;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public final class SpleefShovel implements Mytem {
    private final Mytems key;
    private final SpleefShovelType type;
    private ItemStack prototype;
    private Component displayName;
    private ItemStack silkTouch;

    public SpleefShovel(final Mytems mytems) {
        this.key = mytems;
        this.type = requireNonNull(SpleefShovelType.of(mytems));
    }

    @Override
    public void enable() {
        this.displayName = text(type.displayName, type.color);
        this.prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                List<Component> text = List.of(displayName,
                                               text(tiny("Excavate a whole area"), GRAY),
                                               text(tiny("all at once with Silk"), GRAY),
                                               text(tiny("Touch at the cost of"), GRAY),
                                               text(tiny("a little extra hunger"), GRAY),
                                               empty(),
                                               textOfChildren(text(tiny("range "), GRAY), text(type.range, type.color)));
                Items.unbreakable(meta);
                meta.addItemFlags(ItemFlag.values());
                Items.text(meta, text);
                key.markItemMeta(meta);
            });
        this.silkTouch = new ItemStack(Material.NETHERITE_PICKAXE);
        silkTouch.editMeta(meta -> meta.addEnchant(Enchantment.SILK_TOUCH, 1, true));
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onBlockDamage(BlockDamageEvent event, Player player, ItemStack item) {
        if (event.isCancelled()) return;
        if (!event.getBlock().isPreferredTool(item)) return;
        if (player.getGameMode() != GameMode.CREATIVE && player.getFoodLevel() == 0) {
            player.sendActionBar(text("You are exhausted", RED));
            return;
        }
        if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, event.getBlock())) return;
        event.setCancelled(true);
        int count = 0;
        final int r = type.range;
        for (int dy = -r; dy <= r; dy += 1) {
            for (int dz = -r; dz <= r; dz += 1) {
                for (int dx = -r; dx <= r; dx += 1) {
                    Block block = event.getBlock().getRelative(dx, dy, dz);
                    if (dx * dx + dy * dy + dz * dz > r * r) continue;
                    if (block.isEmpty() || block.isLiquid()) continue;
                    if (!block.isPreferredTool(item)) continue;
                    if (!PlayerBlockAbilityQuery.Action.BUILD.query(player, block)) continue;
                    BlockData blockData = block.getBlockData();
                    if (!blockBreakListener().breakBlock(player, silkTouch, block, e -> {
                                e.getEntity().teleport(player.getLocation());
                                e.getEntity().setPickupDelay(0);
                                e.getEntity().setOwner(player.getUniqueId());
                            })) {
                        continue;
                    }
                    block.getWorld().spawnParticle(Particle.BLOCK_DUST, block.getLocation().add(0.5, 0.5, 0.5),
                                                   8, 0.0, 0.25, 0.25, 0.25, blockData);
                    count += 1;
                }
            }
        }
        if (count > 0 && player.getGameMode() != GameMode.CREATIVE) {
            for (int i = 0; i < count; i += 1) {
                if (player.getSaturation() >= 0.01f) {
                    player.setSaturation(Math.max(0.0f, player.getSaturation() - 0.025f));
                } else if (ThreadLocalRandom.current().nextInt(32) == 0) {
                    player.setFoodLevel(Math.max(0, player.getFoodLevel() - 1));
                }
            }
        }
    }
}
