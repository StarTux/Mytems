package com.cavetale.mytems.item.hookshot;

import com.cavetale.core.font.VanillaItems;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.session.Session;
import com.cavetale.mytems.util.Attr;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Items.tooltip;
import static java.util.Objects.requireNonNull;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public final class Hookshot implements Mytem {
    private final Mytems key;
    private final HookshotType hookshotType;
    private Component displayName;
    private ItemStack prototype;

    public Hookshot(final Mytems key) {
        this.key = key;
        this.hookshotType = requireNonNull(HookshotType.of(key));
    }

    @Override
    public void enable() {
        displayName = hookshotType.getDisplayName();
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                key.markItemMeta(meta);
                final List<Component> tooltip = new ArrayList<>();
                tooltip.add(displayName);
                final List<Component> blocks = new ArrayList<>();
                for (Material mat : hookshotType.getAttachedMaterials()) {
                    final VanillaItems vi = VanillaItems.of(mat);
                    if (vi != null) blocks.add(vi.asComponent());
                }
                tooltip.add(textOfChildren(text(tiny("range "), GRAY), text(16, WHITE), text(tiny("b"), GRAY)));
                tooltip.add(textOfChildren(text(tiny("speed "), GRAY), text("0.75", WHITE), text(tiny("bps"), GRAY)));
                tooltip.add(textOfChildren(text(tiny("hooks "), GRAY), join(noSeparators(), blocks)));
                tooltip.add(textOfChildren(Mytems.MOUSE_RIGHT, text(" Shoot", GRAY)));
                tooltip(meta, tooltip);
                Attr.addNumber(meta, Attribute.BLOCK_INTERACTION_RANGE, "hookshot_range", 5.5, EquipmentSlotGroup.OFFHAND);
            });
    }

    public boolean shoot(Player player) {
        final Session session = Session.of(player);
        final HookshotShot oldShot = session.getFavorites().get(HookshotShot.class);
        if (oldShot != null) {
            oldShot.stop();
            return false;
        }
        final Location playerLocation = player.getLocation();
        final HookshotShot shot = new HookshotShot(key, hookshotType, player, session);
        shot.start();
        return true;
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
        shoot(player);
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    @Override
    public void onBlockDamage(BlockDamageEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }
}
