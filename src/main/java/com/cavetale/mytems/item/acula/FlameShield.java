package com.cavetale.mytems.item.acula;

import com.cavetale.mytems.Mytems;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import static org.bukkit.DyeColor.*;
import static org.bukkit.attribute.Attribute.*;
import static org.bukkit.attribute.AttributeModifier.Operation.*;
import static org.bukkit.block.banner.PatternType.*;
import static org.bukkit.inventory.EquipmentSlot.*;

@Getter
public final class FlameShield extends AculaItem {
    private final String rawDisplayName = "Flame Shield";
    private final String description = ""
        + ChatColor.RED + "After the castle was completely burned down to the ground, this item remained intact."
        + " For years it was kept in a secret vault, until one day, when it vanished.";

    public FlameShield(final Mytems key) {
        super(key);
    }

    @Override @SuppressWarnings("LineLength")
    protected ItemStack getRawItemStack() {
        ItemStack itemStack = new ItemStack(Material.SHIELD);
        itemStack.editMeta(meta -> {
                BlockStateMeta blockStateMeta = (BlockStateMeta) meta;
                Banner banner = (Banner) blockStateMeta.getBlockState();
                banner.setBaseColor(BLACK);
                banner.setPatterns(List.of(new Pattern(ORANGE, MOJANG),
                                           new Pattern(ORANGE, TRIANGLE_BOTTOM),
                                           new Pattern(BLACK, RHOMBUS_MIDDLE),
                                           new Pattern(RED, GRADIENT_UP)));
                blockStateMeta.setBlockState(banner);
                meta.addAttributeModifier(GENERIC_ARMOR,
                                          new AttributeModifier(UUID.randomUUID(), key.id, 4, ADD_NUMBER, HAND));
                meta.addAttributeModifier(GENERIC_ARMOR_TOUGHNESS,
                                          new AttributeModifier(UUID.randomUUID(), key.id, 1, ADD_NUMBER, HAND));
                meta.addAttributeModifier(GENERIC_KNOCKBACK_RESISTANCE,
                                          new AttributeModifier(UUID.randomUUID(),
                                                                key.id, 0.1, ADD_NUMBER, HAND));
                meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS); // hides banner patterns
            });
        return itemStack;
    }
}
