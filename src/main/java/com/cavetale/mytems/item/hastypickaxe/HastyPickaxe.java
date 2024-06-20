package com.cavetale.mytems.item.hastypickaxe;

import com.cavetale.core.event.block.PlayerBreakBlockEvent;
import com.cavetale.core.exploits.PlayerPlacedBlocks;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.upgradable.UpgradableItemMenu;
import com.cavetale.mytems.util.Json;
import com.google.common.collect.ImmutableListMultimap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
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
        prototype.editMeta(meta -> {
                meta.setAttributeModifiers(ImmutableListMultimap.of());
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    public Component getDisplayName() {
        return tier.getDisplayName();
    }

    @Override
    public void onPlayerInventoryClick(InventoryClickEvent event, Player player, ItemStack item) {
        if (!event.isRightClick()) return;
        event.setCancelled(true);
        final var tag = tier.createTag();
        tag.load(item);
        final var menu = new UpgradableItemMenu(player, item, tag, HastyPickaxeItem.hastyPickaxeItem());
        Bukkit.getScheduler().runTask(plugin(), menu::open);
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
                    tag.store(item);
                }
                if (tag.hasAvailableUnlocks()) {
                    player.sendMessage(textOfChildren(text("Your ", GREEN), key, text(" has leveled up.", GREEN)));
                    player.sendMessage(textOfChildren(Mytems.MOUSE_CURSOR, Mytems.MOUSE_RIGHT,
                                                      text(" Right click it in your inventory to choose a perk.", GREEN)));
                }
            });
    }

    private int getXp(Block block) {
        if (PlayerPlacedBlocks.isPlayerPlaced(block)) return 0;
        final var mat = block.getType();
        return switch (mat) {
        case STONE -> 1;
        case ANDESITE -> 1;
        case DIORITE -> 1;
        case GRANITE -> 1;
        case DEEPSLATE -> 2;
        case COAL_ORE -> 3;
        case DEEPSLATE_COAL_ORE -> 3;
        case IRON_ORE -> 3;
        case DEEPSLATE_IRON_ORE -> 3;
        case COPPER_ORE -> 3;
        case DEEPSLATE_COPPER_ORE -> 3;
        case GOLD_ORE -> 3;
        case DEEPSLATE_GOLD_ORE -> 3;
        case NETHER_GOLD_ORE -> 3;
        case LAPIS_ORE -> 5;
        case DEEPSLATE_LAPIS_ORE -> 5;
        case DIAMOND_ORE -> 10;
        case DEEPSLATE_DIAMOND_ORE -> 10;
        case EMERALD_ORE -> 10;
        case DEEPSLATE_EMERALD_ORE -> 10;
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
