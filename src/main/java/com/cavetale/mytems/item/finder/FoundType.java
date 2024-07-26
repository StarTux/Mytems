package com.cavetale.mytems.item.finder;

import com.cavetale.core.item.ItemKinds;
import com.cavetale.mytems.Mytems;
import java.util.List;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.util.CamelCase.toCamelCase;

@Getter
@RequiredArgsConstructor
public enum FoundType {
    // Disabled
    FOSSILS(0, FinderStat.NONE, List.of("minecraft:nether_fossil"), () -> new ItemStack(Material.BONE_BLOCK)),
    // Always Unlocked
    IGLOO(1, FinderStat.NONE, List.of("minecraft:igloo"), () -> new ItemStack(Material.SNOW_BLOCK)),
    MINESHAFT(10, FinderStat.NONE, List.of("minecraft:mineshaft", "minecraft:mineshaft_mesa"), () -> new ItemStack(Material.CHEST_MINECART)),
    VILLAGE(10, FinderStat.NONE, List.of("minecraft:village_"), () -> new ItemStack(Material.EMERALD)),
    RUINED_PORTAL(10, FinderStat.NONE, List.of("minecraft:ruined_portal", "minecraft:ruined_portal_"), () -> new ItemStack(Material.OBSIDIAN)),
    // Treasure
    BURIED_TREASURE(15, FinderStat.TREASURE, List.of("minecraft:buried_treasure"), () -> new ItemStack(Material.CHEST)),
    SHIPWRECK(15, FinderStat.TREASURE, List.of("minecraft:shipwreck", "minecraft:shipwreck_beached"), () -> new ItemStack(Material.STRIPPED_OAK_LOG)),
    // Pyramids
    JUNGLE_TEMPLE(20, FinderStat.PYRAMID, List.of("minecraft:jungle_pyramid"), () -> new ItemStack(Material.MOSSY_COBBLESTONE)),
    PYRAMID(20, FinderStat.PYRAMID, List.of("minecraft:desert_pyramid"), () -> new ItemStack(Material.SANDSTONE_STAIRS)),
    // Archaeology
    TRAIL_RUINS(25, FinderStat.ARCHAEOLOGY, List.of("minecraft:trail_ruins"), () -> new ItemStack(Material.BRUSH)),
    UNDERWATER_RUINS(25, FinderStat.ARCHAEOLOGY, List.of("minecraft:ocean_ruin_cold", "minecraft:ocean_ruin_warm"), () -> new ItemStack(Material.MOSSY_STONE_BRICKS)),
    // Villain
    PILLAGER_OUTPOST(15, FinderStat.VILLAIN, List.of("minecraft:pillager_outpost"), Mytems.PILLAGER_FACE::createIcon),
    WITCH_HUT(15, FinderStat.VILLAIN, List.of("minecraft:swamp_hut"), Mytems.WITCH_FACE::createIcon),
    END_CITY(15, FinderStat.VILLAIN, List.of("minecraft:end_city"), () -> new ItemStack(Material.PURPUR_BLOCK)),
    // Castle
    NETHER_FORTRESS(20, FinderStat.CASTLE, List.of("minecraft:fortress"), Mytems.BLAZE_FACE::createIcon),
    BASTION_REMNANT(20, FinderStat.CASTLE, List.of("minecraft:bastion_remnant"), () -> new ItemStack(Material.GILDED_BLACKSTONE)),
    STRONGHOLD(20, FinderStat.CASTLE, List.of("minecraft:stronghold"), () -> new ItemStack(Material.ENDER_EYE)),
    // Hidden
    MONUMENT(25, FinderStat.HIDDEN, List.of("minecraft:monument"), Mytems.GUARDIAN_FACE::createIcon),
    CAVETALE_DUNGEON(25, FinderStat.HIDDEN, List.of("dungeons:dungeon"), Mytems.CAVETALE_DUNGEON::createIcon),
    ANCIENT_CITY(25, FinderStat.HIDDEN, List.of("minecraft:ancient_city"), Mytems.WARDEN_FACE::createIcon),
    WOODLAND_MANSION(25, FinderStat.HIDDEN, List.of("minecraft:mansion"), Mytems.VEX_FACE::createIcon),
    // Boss Areas
    TRIAL_CHAMBERS(30, FinderStat.TRIAL_CHAMBER, List.of("minecraft:trial_chambers"), () -> new ItemStack(Material.TRIAL_KEY)),
    ;

    private final int xp;
    private final FinderStat requiredStat;
    private final List<String> keys;
    private final Supplier<ItemStack> iconSupplier;

    public String getDisplayName() {
        return toCamelCase(" ", this);
    }

    /**
     * Get the FoundType for a structure.
     *
     * @param nkey the key of the structure
     * @return the corresponding FoundType or null
     */
    public static FoundType of(NamespacedKey nkey) {
        final String str = nkey.toString();
        for (var it : values()) {
            for (String key : it.keys) {
                if (str.equals(key)) return it;
                if (key.endsWith("_") && str.startsWith(key)) return it;
            }
        }
        return null;
    }

    public ItemStack getIcon() {
        return iconSupplier.get();
    }

    public boolean isDisabled() {
        return this == FOSSILS;
    }

    public FinderTier getRequiredTier() {
        return requiredStat != null
            ? (FinderTier) requiredStat.getFirstLevel().getRequiredTier()
            : FinderTier.STRUCTURE;
    }

    public Component getChatIcon() {
        return ItemKinds.icon(getIcon());
    }
}
