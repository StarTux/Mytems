package com.cavetale.mytems.item.mobslayer;

import com.cavetale.core.font.Unicode;
import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Text.roman;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextColor.color;

@RequiredArgsConstructor @Getter
public final class Mobslayer implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private int tier;

    @Override
    public void enable() {
        tier = switch (key) {
        case MOBSLAYER -> 0;
        case MOBSLAYER2 -> 1;
        case MOBSLAYER3 -> 2;
        default -> 0;
        };
        final TextColor red = color(0xFF0000 - (0x330000 * tier));
        this.displayName = tier > 0
            ? text(Unicode.SKULL.string + "Mobslayer " + roman(tier + 1), red)
            : text(Unicode.SKULL.string + "Mobslayer", red);
        this.prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                meta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 3, true);
                meta.addEnchant(Enchantment.DAMAGE_ALL, 3 + tier, true);
                meta.addEnchant(Enchantment.DAMAGE_UNDEAD, 3 + tier, true);
                if (tier >= 1) {
                    meta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 3, true);
                    meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
                }
                if (tier >= 2) {
                    meta.addEnchant(Enchantment.SWEEPING_EDGE, 3, true);
                    meta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
                }
                Items.text(meta, List.of(displayName,
                                         empty(),
                                         text(tiny("Neither living nor dead"), red),
                                         text(tiny("may escape the wrath"), red),
                                         text(tiny("of this blade."), red)));
                meta.setUnbreakable(true);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }
}

