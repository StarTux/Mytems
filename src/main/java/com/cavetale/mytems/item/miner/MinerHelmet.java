package com.cavetale.mytems.item.miner;

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
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.core.font.VanillaItems.EXPERIENCE_BOTTLE;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@Data
@RequiredArgsConstructor
public final class MinerHelmet implements Mytem {
    private final Mytems key;
    private Component displayName;
    private ItemStack prototype;

    @Override
    public void enable() {
        displayName = text("Miner Helmet", RED);
        prototype = Skull.create("",
                                 UUID.fromString("63946565-a03a-14c6-5a3b-50c790d07ca1"),
                                 "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU1NWY5YTQ4YWNjNDJhMDFiODgxYWY3MjZmZmMxYWMxNzg3ODllMzI3NGNiMmMxY2RiMTJmNTdjIn19fQ==");
        prototype.editMeta(meta -> {
                Items.text(meta,
                           List.of(displayName,
                                   text(tiny("This lamp cap lets you"), GRAY),
                                   text(tiny("see even in the darkest"), GRAY),
                                   text(tiny("caves."), GRAY),
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
    public void onTick(Player player, ItemStack item, EquipmentSlot slot) {
        if (slot != EquipmentSlot.HEAD) return;
        switch (player.getGameMode()) {
        case CREATIVE:
        case SPECTATOR:
            break;
        case SURVIVAL:
        case ADVENTURE:
        default:
            if (!Exp.tryToTake(player, 0.00025f)) return;
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 399, 0, true, false, true));
    }
}
