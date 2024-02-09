package com.cavetale.mytems.item.finder;

import com.cavetale.mytems.Mytems;
import java.util.List;
import java.util.function.Supplier;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.util.CamelCase.toCamelCase;

public enum FoundType {
    // Disabled
    FOSSILS(null, List.of("minecraft:nether_fossil"), () -> new ItemStack(Material.BONE_BLOCK)),
    // Normal
    END_CITY(FinderType.STRUCTURE, List.of("minecraft:end_city"), () -> new ItemStack(Material.PURPUR_BLOCK)),
    IGLOO(FinderType.STRUCTURE, List.of("minecraft:igloo"), () -> new ItemStack(Material.SNOW_BLOCK)),
    JUNGLE_TEMPLE(FinderType.STRUCTURE, List.of("minecraft:jungle_pyramid"), () -> new ItemStack(Material.MOSSY_COBBLESTONE)),
    MINESHAFT(FinderType.STRUCTURE, List.of("minecraft:mineshaft", "minecraft:mineshaft_mesa"), () -> new ItemStack(Material.CHEST_MINECART)),
    NETHER_FORTRESS(FinderType.STRUCTURE, List.of("minecraft:fortress"), Mytems.BLAZE_FACE::createIcon),
    PILLAGER_OUTPOST(FinderType.STRUCTURE, List.of("minecraft:pillager_outpost"), Mytems.PILLAGER_FACE::createIcon),
    PYRAMID(FinderType.STRUCTURE, List.of("minecraft:desert_pyramid"), () -> new ItemStack(Material.SANDSTONE_STAIRS)),
    RUINED_PORTAL(FinderType.STRUCTURE, List.of("minecraft:ruined_portal", "minecraft:ruined_portal_"), () -> new ItemStack(Material.OBSIDIAN)),
    VILLAGE(FinderType.STRUCTURE, List.of("minecraft:village_"), () -> new ItemStack(Material.EMERALD)),
    WITCH_HUT(FinderType.STRUCTURE, List.of("minecraft:swamp_hut"), Mytems.WITCH_FACE::createIcon),
    // Secret
    BASTION_REMNANT(FinderType.SECRET, List.of("minecraft:bastion_remnant"), () -> new ItemStack(Material.GILDED_BLACKSTONE)),
    BURIED_TREASURE(FinderType.SECRET, List.of("minecraft:buried_treasure"), () -> new ItemStack(Material.CHEST)),
    MONUMENT(FinderType.SECRET, List.of("minecraft:monument"), Mytems.GUARDIAN_FACE::createIcon),
    SHIPWRECK(FinderType.SECRET, List.of("minecraft:shipwreck", "minecraft:shipwreck_beached"), () -> new ItemStack(Material.STRIPPED_OAK_LOG)),
    UNDERWATER_RUINS(FinderType.SECRET, List.of("minecraft:ocean_ruin_cold", "minecraft:ocean_ruin_warm"), () -> new ItemStack(Material.MOSSY_STONE_BRICKS)),
    // Mystic
    ANCIENT_CITY(FinderType.MYSTIC, List.of("minecraft:ancient_city"), Mytems.WARDEN_FACE::createIcon),
    CAVETALE_DUNGEON(FinderType.MYSTIC, List.of("dungeons:dungeon"), Mytems.CAVETALE_DUNGEON::createIcon),
    STRONGHOLD(FinderType.MYSTIC, List.of("minecraft:stronghold"), () -> new ItemStack(Material.ENDER_EYE)),
    WOODLAND_MANSION(FinderType.MYSTIC, List.of("minecraft:mansion"), Mytems.VEX_FACE::createIcon),
    TRAIL_RUINS(FinderType.MYSTIC, List.of("minecraft:trail_ruins"), () -> new ItemStack(Material.BRUSH)),
    ;

    public final FinderType type;
    public final List<String> keys;
    protected final Supplier<ItemStack> iconSupplier;
    public final String displayName;

    FoundType(final FinderType type, final List<String> keys, final Supplier<ItemStack> iconSupplier) {
        this.type = type;
        this.keys = keys;
        this.iconSupplier = iconSupplier;
        this.displayName = toCamelCase(" ", this);
    }

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

    public boolean isDisabled() {
        return type == null;
    }
}
