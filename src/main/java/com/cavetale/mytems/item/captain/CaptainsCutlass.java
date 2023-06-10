package com.cavetale.mytems.item.captain;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Text;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.util.Vector;

@RequiredArgsConstructor @Getter
public final class CaptainsCutlass implements Mytem {
    private final Mytems key;
    private ItemStack prototype;
    private Component displayName;
    private final String description = ""
        + "The cap'n knows how t' keep an enemy at distance."
        + " No landlubber shall manage t' get too close while this mighty cutlass be drawn."
        + "\n\n"
        + "Strike fear into yer opponent's hearts."
        + " Hit 'em wit' this weapon at full force t' knock 'em way back.";
    private final TextColor titleColor = TextColor.color(0xddd417);
    private final TextColor loreColor = TextColor.color(0x760a0a);

    @Override
    public void enable() {
        displayName = Component.text("Captain's Cutlass", titleColor);
        prototype = new ItemStack(key.material);
        ItemMeta meta = prototype.getItemMeta();
        meta.displayName(displayName.decoration(TextDecoration.ITALIC, false));
        meta.lore(Text.wrapLore(description, c -> {
                    return c.color(loreColor).decoration(TextDecoration.ITALIC, false);
                }));
        meta.addItemFlags(ItemFlag.values());
        if (meta instanceof Repairable) {
            Repairable repairable = (Repairable) meta;
            repairable.setRepairCost(9999);
        }
        meta.setUnbreakable(true);
        key.markItemMeta(meta);
        prototype.setItemMeta(meta);
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onDamageEntity(EntityDamageByEntityEvent event, Player player, ItemStack item) {
        if (event.isCancelled()) return;
        event.setDamage(0);
        if (player.getAttackCooldown() < 1.0f) return;
        if (!(event.getEntity() instanceof LivingEntity)) return;
        LivingEntity target = (LivingEntity) event.getEntity();
        Vector velo = target.getLocation().subtract(player.getLocation()).toVector();
        Vector horizontal = new Vector(velo.getX(), 0.0, velo.getZ());
        if (horizontal.length() < 0.1) return;
        horizontal = horizontal.normalize();
        Vector vertical = new Vector(0.0, 2.0f, 0.0);
        Vector hit = horizontal.multiply(player.isSprinting() ? 4.0 : 3.0).add(vertical);
        target.setVelocity(target.getVelocity().add(hit));
        target.getWorld().playSound(target.getLocation(), Sound.ENTITY_ARMOR_STAND_BREAK, SoundCategory.PLAYERS, 1.0f, 0.75f);
        target.getWorld().spawnParticle(Particle.BLOCK_DUST, target.getLocation(), 24, .25, .25, .25, 0,
                                        Material.OAK_PLANKS.createBlockData());
    }
}
