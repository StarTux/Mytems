package com.cavetale.mytems.item.halloween;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import io.papermc.paper.datacomponent.DataComponentTypes;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.tiny;
import static com.cavetale.mytems.util.Items.tooltip;
import static com.cavetale.mytems.util.Text.wrapLore2;
import static io.papermc.paper.datacomponent.item.ItemEnchantments.itemEnchantments;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
@RequiredArgsConstructor
public final class SmashingPumpkin implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private final String lore = "This pumpkin will smash you.";

    @Override
    public void enable() {
        displayName = text("Smashing Pumpkin", GOLD);
        prototype = new ItemStack(key.material);
        prototype.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false);
        prototype.setData(DataComponentTypes.ENCHANTMENTS, itemEnchantments()
                          .add(Enchantment.DENSITY, 5)
                          .add(Enchantment.BREACH, 4)
                          .add(Enchantment.WIND_BURST, 2));
        final List<Component> tooltip = new ArrayList<>();
        tooltip.add(displayName);
        tooltip.addAll(wrapLore2(lore, txt -> text(tiny(txt), GOLD)));
        tooltip(prototype, tooltip);
        key.markItemStack(prototype);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onDamageEntity(EntityDamageByEntityEvent event, Player player, ItemStack item) {
        if (event.isCancelled()) return;
        if (event.getCause() != DamageCause.ENTITY_ATTACK) return;
        final Location location = event.getEntity().getLocation().add(0.0, event.getEntity().getHeight() * 0.5, 0.0);
        final double d = 0.35;
        location.getWorld().spawnParticle(Particle.DUST, location, 16, d, d, d, 0.0, new Particle.DustOptions(Color.YELLOW, 2f));
        location.getWorld().spawnParticle(Particle.DUST, location, 16, d, d, d, 0.0, new Particle.DustOptions(Color.ORANGE, 2f));
        location.getWorld().playSound(location, Sound.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, 0.5f, 0.75f);
    }
}
