package com.cavetale.mytems.custom;

import com.destroystokyo.paper.MaterialTags;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import static com.cavetale.mytems.MytemsPlugin.namespacedKey;

public final class NetheriteParity {
    public static String getAttributeNamePrefix() {
        return "netherite_parity_";
    }

    public static NamespacedKey getItemTagKey() {
        return namespacedKey("netherite_parity");
    }

    public static int getItemTagValue() {
        return 1;
    }

    public static boolean isUpgraded(ItemStack item) {
        if (!item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(getItemTagKey(), PersistentDataType.INTEGER);
    }

    public static List<Material> getUpgradableMaterials() {
        return List.of(// Stone Tools
                       Material.STONE_SWORD,
                       Material.STONE_AXE,
                       // Leather
                       Material.LEATHER_HELMET,
                       Material.LEATHER_CHESTPLATE,
                       Material.LEATHER_LEGGINGS,
                       Material.LEATHER_BOOTS,
                       // Iron
                       Material.IRON_HELMET,
                       Material.IRON_CHESTPLATE,
                       Material.IRON_LEGGINGS,
                       Material.IRON_BOOTS,
                       // Iron Tools
                       Material.IRON_SWORD,
                       Material.IRON_AXE,
                       // Gold
                       Material.GOLDEN_HELMET,
                       Material.GOLDEN_CHESTPLATE,
                       Material.GOLDEN_LEGGINGS,
                       Material.GOLDEN_BOOTS,
                       // Gold Tools
                       Material.GOLDEN_SWORD,
                       Material.GOLDEN_AXE,
                       // Chainmail
                       Material.CHAINMAIL_HELMET,
                       Material.CHAINMAIL_CHESTPLATE,
                       Material.CHAINMAIL_LEGGINGS,
                       Material.CHAINMAIL_BOOTS,
                       // Diamond
                       Material.DIAMOND_HELMET,
                       Material.DIAMOND_CHESTPLATE,
                       Material.DIAMOND_LEGGINGS,
                       Material.DIAMOND_BOOTS,
                       // Diamond Tools
                       Material.DIAMOND_SWORD,
                       Material.DIAMOND_AXE,
                       // Elytra
                       Material.ELYTRA);
    }

    public static Material getNetheriteCounterpart(Material mat) {
        EquipmentSlot slot = mat.getEquipmentSlot();
        if (slot == null) return null;
        return switch (slot) {
        case HEAD -> Material.NETHERITE_HELMET;
        case CHEST -> Material.NETHERITE_CHESTPLATE;
        case LEGS -> Material.NETHERITE_LEGGINGS;
        case FEET -> Material.NETHERITE_BOOTS;
        case HAND -> {
            if (MaterialTags.SWORDS.isTagged(mat)) yield Material.NETHERITE_SWORD;
            if (MaterialTags.AXES.isTagged(mat)) yield Material.NETHERITE_AXE;
            if (MaterialTags.PICKAXES.isTagged(mat)) yield Material.NETHERITE_PICKAXE;
            if (MaterialTags.SHOVELS.isTagged(mat)) yield Material.NETHERITE_SHOVEL;
            if (MaterialTags.HOES.isTagged(mat)) yield Material.NETHERITE_HOE;
            yield null;
        }
        default -> null;
        };
    }

    public static boolean upgradeItem(ItemStack item) {
        if (item == null || isUpgraded(item)) return false;
        if (!getUpgradableMaterials().contains(item.getType())) return false;
        Material netherite = getNetheriteCounterpart(item.getType());
        if (netherite == null) return false;
        EquipmentSlot slot = netherite.getEquipmentSlot();
        item.editMeta(meta -> {
                for (Map.Entry<Attribute, Collection<AttributeModifier>> entry : netherite.getDefaultAttributeModifiers(slot).asMap().entrySet()) {
                    Attribute attr = entry.getKey();
                    for (AttributeModifier mod : entry.getValue()) {
                        final UUID uuid = UUID.randomUUID();
                        final String name = getAttributeNamePrefix() + attr.name().toLowerCase();
                        meta.addAttributeModifier(attr, new AttributeModifier(uuid, name, mod.getAmount(), mod.getOperation(), mod.getSlot()));
                    }
                }
                meta.getPersistentDataContainer().set(getItemTagKey(), PersistentDataType.INTEGER, getItemTagValue());
            });
        return true;
    }

    private NetheriteParity() { }
}
