package com.cavetale.mytems.item.medieval;

import com.cavetale.core.event.block.PlayerBreakBlockEvent;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import com.destroystokyo.paper.block.BlockSoundGroup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@RequiredArgsConstructor @Getter
public final class GoldenScythe implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private final String description = "Harvest your crops in a 3x3 area!";
    private TextColor goldLight = TextColor.color(0xE7C065);
    private TextColor goldDark = TextColor.color(0xD4AF37);

    @Override
    public void enable() {
        displayName = Text.gradient("Golden Scythe", goldLight, goldDark);
        prototype = new ItemStack(key.material);
        ItemMeta meta = prototype.getItemMeta();
        meta.displayName(displayName.decoration(TextDecoration.ITALIC, false));
        meta.addItemFlags(ItemFlag.values());
        meta.lore(Text.wrapLore(description, c -> {
                    return c.color(goldLight).decoration(TextDecoration.ITALIC, false);
                }));
        Items.unbreakable(meta);
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, Player player, ItemStack itemStack) {
        Block block = event.getBlock();
        if (!Tag.CROPS.isTagged(block.getType())) return;
        final int radius = 1;
        for (int z = -radius; z <= radius; z += 1) {
            for (int x = -radius; x <= radius; x += 1) {
                if (x == 0 && z == 0) continue;
                harvestBlock(player, block.getRelative(x, 0, z));
            }
        }
    }

    private void harvestBlock(Player player, Block block) {
        if (!Tag.CROPS.isTagged(block.getType())) return;
        BlockData data = block.getBlockData();
        if (!(data instanceof Ageable)) return;
        Ageable ageable = (Ageable) data;
        if (ageable.getAge() != ageable.getMaximumAge()) return;
        PlayerBreakBlockEvent pbeEvent = new PlayerBreakBlockEvent(player, block);
        Bukkit.getPluginManager().callEvent(pbeEvent);
        if (pbeEvent.isCancelled()) return;
        BlockSoundGroup soundGroup = block.getSoundGroup();
        block.breakNaturally();
        if (soundGroup != null) {
            Sound sound = soundGroup.getBreakSound();
            if (sound != null) {
                block.getWorld().playSound(block.getLocation(), sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
        }
        block.getWorld().spawnParticle(Particle.SWEEP_ATTACK, block.getLocation().add(0.5, 0.5, 0.5), 1, 0.0, 0.0, 0.0, 0.0);
    }
}
