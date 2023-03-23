package com.cavetale.mytems.item.deflector;

import com.cavetale.mytems.Mytem;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Text;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import static com.cavetale.core.font.Unicode.tiny;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;
import static net.kyori.adventure.text.format.NamedTextColor.*;

@Getter
public final class DeflectorShield implements Mytem {
    private final Mytems key;
    private final DeflectorType type;
    private ItemStack prototype;
    private Component displayName;
    private static final NamespacedKey DEFLECTED = new NamespacedKey("mytems", "deflected");

    public DeflectorShield(final Mytems key) {
        this.key = key;
        this.type = DeflectorType.of(key);
    }

    @Override
    public void enable() {
        displayName = type.displayName;
        prototype = new ItemStack(key.material);
        prototype.editMeta(meta -> {
                List<Component> txt = new ArrayList<>();
                txt.add(displayName);
                txt.addAll(Text.wrapLore(type.description, c -> c.color(GRAY)));
                txt.add(empty());
                txt.add(textOfChildren(text(tiny("cooldown"), GRAY), text(" " + type.cooldown.toSeconds() + "s", WHITE)));
                Items.text(meta, txt);
                meta.addItemFlags(ItemFlag.values());
                ((Repairable) meta).setUnbreakable(true);
                key.markItemMeta(meta);
            });
    }

    @Override
    public ItemStack createItemStack() {
        return prototype.clone();
    }

    @Override
    public void onProjectileHitPlayer(ProjectileHitEvent event, Player player, ItemStack item, EquipmentSlot slot) {
        if (slot != EquipmentSlot.HAND && slot != EquipmentSlot.OFF_HAND) return;
        if (!player.isBlocking()) return;
        if (slot != player.getHandRaised()) return;
        if (player.getCooldown(Material.SHIELD) > 0) return;
        Projectile proj = event.getEntity();
        if (player.equals(proj.getShooter())) return;
        PersistentDataContainer tag = proj.getPersistentDataContainer();
        if (tag.has(DEFLECTED)) return;
        tag.set(DEFLECTED, PersistentDataType.BYTE, (byte) 1);
        event.setCancelled(true);
        final boolean isReturn = type.ordinal() >= DeflectorType.RETURN.ordinal();
        final boolean isVengeance = type.ordinal() >= DeflectorType.VENGEANCE.ordinal();
        final double factor = isVengeance ? 1.5 : 1.0;
        if (isReturn && proj.getShooter() instanceof Mob shooter && shooter.getWorld().equals(proj.getWorld())) {
            final double length = proj.getVelocity().length();
            Vector dir = shooter.getEyeLocation().toVector().subtract(proj.getLocation().toVector());
            dir.normalize().multiply(length * factor);
            proj.setVelocity(dir);
            if (proj instanceof Fireball fireball) {
                fireball.setDirection(dir);
            }
        } else {
            proj.setVelocity(proj.getVelocity().multiply(-1 * factor));
            if (proj instanceof Fireball fireball) {
                fireball.setDirection(fireball.getDirection().multiply(-1 * factor));
            }
        }
        proj.setShooter(player);
        if (proj instanceof AbstractArrow arrow) {
            arrow.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
        }
        player.setCooldown(Material.SHIELD, (int) (type.cooldown.toSeconds() * 20L));
        player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BLOCK, 1.0f, 1.0f);
    }
}
