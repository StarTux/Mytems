package com.cavetale.mytems.item.scuba;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Exp;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Skull;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.core.font.VanillaItems.EXPERIENCE_BOTTLE;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@Data
@RequiredArgsConstructor
public final class ScubaHelmet implements Mytem {
    private final Mytems key;
    private Component displayName;

    private ItemStack prototype;

    @Override
    public void enable() {
        displayName = text("Scuba Helmet", GOLD);
        prototype = Skull.create("",
                                 UUID.fromString("c93392d2-fb71-4c15-ac0b-b88430454a1a"),
                                 "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDhiOWQyOTljNjE4NjhiMGUyZTk4YTM2YjE3MzhkMDgxNjE2Y2U3N2ViMWU0MzFjYWJiOWNiOTYzOWNkZTk3YiJ9fX0=");
        prototype.editMeta(meta -> {
                Items.text(meta,
                           List.of(displayName,
                                   text(tiny("Never run out of oxygen"), GRAY),
                                   text(tiny("while wearing this helmet."), GRAY),
                                   empty(),
                                   textOfChildren(EXPERIENCE_BOTTLE, text(" Slowly drains exp", color(0xD8E45A)))));
                key.markItemMeta(meta);
            });
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
    public void onPlayerAirChange(EntityAirChangeEvent event, Player player, ItemStack item, EquipmentSlot slot) {
        if (event.isCancelled()) return;
        if (slot != EquipmentSlot.HEAD) return;
        if (event.getAmount() >= player.getRemainingAir()) return;
        if (!Exp.tryToTake(player, 0.00025f)) return;
        event.setCancelled(true);
    }
}
