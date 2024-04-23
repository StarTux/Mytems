package com.cavetale.mytems.item.equipment;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import static com.cavetale.mytems.util.Items.tooltip;
import static java.util.Objects.requireNonNull;
import static net.kyori.adventure.text.Component.text;

@RequiredArgsConstructor @Getter
public final class EquipmentItem implements Mytem {
    private final Mytems key;
    private final EquipmentType type;
    private final Component displayName;
    private ItemStack prototype;

    public EquipmentItem(final Mytems key) {
        this.key = key;
        this.type = requireNonNull(EquipmentType.of(key));
        this.displayName = text(type.displayName, type.getTextColor());
    }

    @Override
    public void enable() {
        List<Component> tooltip = new ArrayList<>();
        tooltip.add(displayName);
        if (type.element != null) {
            tooltip.add(type.element.getIconDisplayName());
        }
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                if (meta instanceof LeatherArmorMeta leather) {
                    TextColor color = type.getTextColor();
                    leather.setColor(Color.fromRGB(color.red(), color.green(), color.blue()));
                }
                meta.setUnbreakable(true);
                meta.addItemFlags(ItemFlag.values());
                tooltip(meta, tooltip);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event, Player player, ItemStack item) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerRightClick(PlayerInteractEvent event, Player player, ItemStack item) {
        event.setUseItemInHand(Event.Result.DENY);
    }


    @Override
    public boolean isMassStorable() {
        return false;
    }

    @Override
    public boolean isAvailableToPlayers() {
        return true;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
