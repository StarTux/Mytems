package com.cavetale.mytems.item.hastypickaxe;

import com.cavetale.core.event.block.PlayerBreakBlockEvent;
import com.cavetale.core.exploits.PlayerPlacedBlocks;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.upgradable.UpgradableItemMenu;
import com.cavetale.mytems.util.Json;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.mytems.MytemsPlugin.plugin;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
@RequiredArgsConstructor
public final class HastyPickaxe implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private HastyPickaxeTier tier;

    @Override
    public void enable() {
        tier = HastyPickaxeTier.of(key);
        if (tier == null) {
            throw new IllegalArgumentException("HastyPickaxe.key = " + key);
        }
        prototype = new ItemStack(key.material);
        tier.createTag().store(prototype);
        prototype.editMeta(meta -> key.markItemMeta(meta));
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    public Component getDisplayName() {
        return tier.getDisplayName();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        final var tag = tier.createTag();
        tag.load(item);
        new UpgradableItemMenu(player, item, tag, HastyPickaxeItem.hastyPickaxeItem()).open();
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, Player player, ItemStack item) {
        if (event.isCancelled()) return;
        onBreak(player, event.getBlock(), item);
    }

    @Override
    public void onPlayerBreakBlock(PlayerBreakBlockEvent event, Player player, ItemStack item) {
        if (event.isCancelled()) return;
        onBreak(player, event.getBlock(), item);
    }

    private void onBreak(Player player, Block block, ItemStack item) {
        final int xp = getXp(block);
        if (xp <= 0) return;
        Bukkit.getScheduler().runTask(plugin(), () -> {
                final HastyPickaxeTag tag = serializeTag(item);
                if (tag.addXp(xp)) {
                    player.sendMessage(textOfChildren(text("Your ", GREEN),
                                                      key,
                                                      text(" has leveled up.", GREEN)));
                }
                tag.store(item);
            });
    }

    private int getXp(Block block) {
        if (PlayerPlacedBlocks.isPlayerPlaced(block)) return 0;
        final var mat = block.getType();
        if (mat.name().endsWith("_ORE")) return 2;
        return switch (mat) {
        case STONE -> 1;
        case ANDESITE -> 1;
        case DIORITE -> 1;
        case GRANITE -> 1;
        case DEEPSLATE -> 1;
        default -> 0;
        };
    }

    @Override
    public void onBlockDamage(BlockDamageEvent event, Player player, ItemStack item) {
    }

    @Override
    public HastyPickaxeTag serializeTag(ItemStack itemStack) {
        HastyPickaxeTag tag = tier.createTag();
        tag.load(itemStack);
        return tag;
    }

    @Override
    public ItemStack deserializeTag(String serialized) {
        ItemStack itemStack = createItemStack();
        HastyPickaxeTag tag = Json.deserialize(serialized, tier.getTagClass());
        if (tag != null && !tag.isEmpty()) {
            tag.store(itemStack);
        }
        return itemStack;
    }

    @Override
    public boolean isMassStorable() {
        return false;
    }
}
