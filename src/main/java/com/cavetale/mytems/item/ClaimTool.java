package com.cavetale.mytems.item;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Items.clearAttributes;
import static com.cavetale.mytems.util.Items.tooltip;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@RequiredArgsConstructor @Getter
public final class ClaimTool implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;

    @Override
    public void enable() {
        final TextColor color = TextColor.color(0x8080ff);
        displayName = text("Claim Tool", color);
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                key.markItemMeta(meta);
                tooltip(meta, displayName,
                        text(tiny("Make changes your claim"), color),
                        text(tiny("with this mysterious"), color),
                        text(tiny("item in your hand."), color),
                        empty(),
                        textOfChildren(Mytems.MOUSE_LEFT, text(" Claim info", GRAY)),
                        textOfChildren(Mytems.MOUSE_RIGHT, text(" Edit claim size", GRAY)));
                clearAttributes(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setUseItemInHand(Event.Result.DENY);
    }

    @Override
    public void onBlockDamage(BlockDamageEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }
}
