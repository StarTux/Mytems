package com.cavetale.mytems.item.mobslayer;

import com.cavetale.core.struct.Vec2i;
import com.cavetale.mytems.Mytems;
import com.cavetale.mytems.item.upgradable.BaseAttributeStatLevel;
import com.cavetale.mytems.item.upgradable.EnchantmentStatLevel;
import com.cavetale.mytems.item.upgradable.UpgradableStat;
import com.cavetale.mytems.item.upgradable.UpgradableStatLevel;
import java.util.List;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import static net.kyori.adventure.text.Component.text;

@Getter
@RequiredArgsConstructor
public enum MobslayerStat implements UpgradableStat {
    SHARP(Vec2i.of(4, 0), text("Sharpness"), () -> new ItemStack(Material.GOLDEN_SWORD),
          List.of(new EnchantmentStatLevel(1, MobslayerTier.TIER_1, () -> new ItemStack(Material.GOLDEN_SWORD, 1), Enchantment.SHARPNESS),
                  new EnchantmentStatLevel(2, MobslayerTier.TIER_1, () -> new ItemStack(Material.GOLDEN_SWORD, 2), Enchantment.SHARPNESS),
                  new EnchantmentStatLevel(3, MobslayerTier.TIER_1, () -> new ItemStack(Material.GOLDEN_SWORD, 3), Enchantment.SHARPNESS),
                  new EnchantmentStatLevel(4, MobslayerTier.TIER_1, () -> new ItemStack(Material.GOLDEN_SWORD, 4), Enchantment.SHARPNESS),
                  new EnchantmentStatLevel(5, MobslayerTier.TIER_1, () -> new ItemStack(Material.GOLDEN_SWORD, 5), Enchantment.SHARPNESS)),
          List.of(), List.of()),
    BANE(Vec2i.of(2, 0), text("Bane of Arthropods"), Mytems.SPIDER_FACE::createIcon,
         List.of(new EnchantmentStatLevel(1, MobslayerTier.TIER_1, () -> Mytems.SPIDER_FACE.createIcon(1), Enchantment.BANE_OF_ARTHROPODS),
                 new EnchantmentStatLevel(2, MobslayerTier.TIER_1, () -> Mytems.SPIDER_FACE.createIcon(2), Enchantment.BANE_OF_ARTHROPODS),
                 new EnchantmentStatLevel(3, MobslayerTier.TIER_1, () -> Mytems.SPIDER_FACE.createIcon(3), Enchantment.BANE_OF_ARTHROPODS)),
         List.of(SHARP), List.of()),
    SMITE(Vec2i.of(6, 0), text("Smite"), Mytems.ZOMBIE_FACE::createIcon,
          List.of(new EnchantmentStatLevel(1, MobslayerTier.TIER_1, () -> Mytems.ZOMBIE_FACE.createIcon(1), Enchantment.SMITE),
                  new EnchantmentStatLevel(2, MobslayerTier.TIER_1, () -> Mytems.ZOMBIE_FACE.createIcon(2), Enchantment.SMITE),
                  new EnchantmentStatLevel(3, MobslayerTier.TIER_1, () -> Mytems.ZOMBIE_FACE.createIcon(3), Enchantment.SMITE),
                  new EnchantmentStatLevel(4, MobslayerTier.TIER_1, () -> Mytems.ZOMBIE_FACE.createIcon(4), Enchantment.SMITE),
                  new EnchantmentStatLevel(5, MobslayerTier.TIER_1, () -> Mytems.ZOMBIE_FACE.createIcon(5), Enchantment.SMITE)),
          List.of(SHARP), List.of()),
    KNOCK(Vec2i.of(4, 2), text("Knockback"), () -> new ItemStack(Material.SLIME_BLOCK),
          List.of(new EnchantmentStatLevel(1, MobslayerTier.TIER_2, () -> new ItemStack(Material.SLIME_BLOCK, 1), Enchantment.KNOCKBACK),
                  new EnchantmentStatLevel(2, MobslayerTier.TIER_2, () -> new ItemStack(Material.SLIME_BLOCK, 2), Enchantment.KNOCKBACK)),
          List.of(SHARP), List.of()),
    LOOT(Vec2i.of(6, 2), text("Looting"), () -> new ItemStack(Material.CHEST),
         List.of(new EnchantmentStatLevel(1, MobslayerTier.TIER_2, () -> new ItemStack(Material.CHEST, 1), Enchantment.LOOTING),
                 new EnchantmentStatLevel(2, MobslayerTier.TIER_2, () -> new ItemStack(Material.CHEST, 2), Enchantment.LOOTING),
                 new EnchantmentStatLevel(3, MobslayerTier.TIER_2, () -> new ItemStack(Material.CHEST, 2), Enchantment.LOOTING)),
         List.of(KNOCK), List.of()),
    SWEEP(Vec2i.of(2, 2), text("Sweeping Edge"), Mytems.STEEL_BROADSWORD::createIcon,
          List.of(new EnchantmentStatLevel(1, MobslayerTier.TIER_3, () -> Mytems.STEEL_BROADSWORD.createIcon(1), Enchantment.SWEEPING_EDGE),
                  new EnchantmentStatLevel(2, MobslayerTier.TIER_3, () -> Mytems.STEEL_BROADSWORD.createIcon(2), Enchantment.SWEEPING_EDGE),
                  new EnchantmentStatLevel(3, MobslayerTier.TIER_3, () -> Mytems.STEEL_BROADSWORD.createIcon(3), Enchantment.SWEEPING_EDGE)),
          List.of(KNOCK), List.of()),
    FIRE(Vec2i.of(0, 2), text("Fire Aspect"), () -> new ItemStack(Material.FIRE_CHARGE),
         List.of(new EnchantmentStatLevel(1, MobslayerTier.TIER_3, () -> new ItemStack(Material.FIRE_CHARGE, 1), Enchantment.FIRE_ASPECT),
                 new EnchantmentStatLevel(2, MobslayerTier.TIER_3, () -> new ItemStack(Material.FIRE_CHARGE, 2), Enchantment.FIRE_ASPECT)),
         List.of(SWEEP), List.of()),
    // TESTING
    ATTR(Vec2i.of(2, 4), text("Attack Damage"), () -> new ItemStack(Material.DIRT),
         List.of(new BaseAttributeStatLevel(1, MobslayerTier.TIER_1, () -> new ItemStack(Material.STONE, 1), Attribute.ATTACK_DAMAGE, 1.0),
                 new BaseAttributeStatLevel(2, MobslayerTier.TIER_2, () -> new ItemStack(Material.STONE, 2), Attribute.ATTACK_DAMAGE, 2.0),
                 new BaseAttributeStatLevel(3, MobslayerTier.TIER_3, () -> new ItemStack(Material.STONE, 3), Attribute.ATTACK_DAMAGE, 3.0)),
         List.of(SWEEP), List.of()),
    ;

    private final String key = name().toLowerCase();
    private final Vec2i guiSlot;
    private final Component title;
    private final Supplier<ItemStack> iconSupplier;
    private final List<? extends UpgradableStatLevel> levels;
    private final List<MobslayerStat> dependencies;
    private final List<MobslayerStat> completeDependencies;

    @Override
    public ItemStack getIcon() {
        return iconSupplier.get();
    }

    @Override
    public List<MobslayerStat> getConflicts() {
        return List.of();
    }
}
