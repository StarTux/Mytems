package com.cavetale.mytems.item.finder;

import com.cavetale.mytems.Mytems;
import java.util.List;
import java.util.function.Supplier;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.util.CamelCase.toCamelCase;

public enum FoundType {
    CAVETALE_DUNGEON(FinderType.NORMAL, List.of("dungeons:dungeon"), Mytems.CAVETALE_DUNGEON::createIcon),
    DESERT_PYRAMID(FinderType.NORMAL, List.of("minecraft:desert_pyramid"), () -> new ItemStack(Material.SANDSTONE_STAIRS)),
    END_CITY(FinderType.NORMAL, List.of("minecraft:end_city"), () -> new ItemStack(Material.PURPUR_BLOCK)),
    IGLOO(FinderType.NORMAL, List.of("minecraft:igloo"), () -> new ItemStack(Material.SNOW_BLOCK)),
    JUNGLE_PYRAMID(FinderType.NORMAL, List.of("minecraft:jungle_pyramid"), () -> new ItemStack(Material.MOSSY_COBBLESTONE)),
    MINESHAFT(FinderType.NORMAL, List.of("minecraft:mineshaft", "minecraft:mineshaft_mesa"), () -> new ItemStack(Material.CHEST_MINECART)),
    NETHER_FORTRESS(FinderType.NORMAL, List.of("minecraft:fortress"), Mytems.BLAZE_FACE::createIcon),
    NETHER_FOSSIL(FinderType.NORMAL, List.of("minecraft:nether_fossil"), () -> new ItemStack(Material.BONE_BLOCK)),
    OCEAN_RUINS(FinderType.NORMAL, List.of("minecraft:ocean_ruin_cold", "minecraft:ocean_ruin_warm"), () -> new ItemStack(Material.MOSSY_STONE_BRICKS)),
    PILLAGER_OUTPOST(FinderType.NORMAL, List.of("minecraft:pillager_outpost"), Mytems.PILLAGER_FACE::createIcon),
    RUINED_PORTAL(FinderType.NORMAL, List.of("minecraft:ruined_portal", "minecraft:ruined_portal_"), () -> new ItemStack(Material.OBSIDIAN)),
    SHIPWRECK(FinderType.NORMAL, List.of("minecraft:shipwreck", "minecraft:shipwreck_beached"), () -> new ItemStack(Material.STRIPPED_OAK_LOG)),
    VILLAGE(FinderType.NORMAL, List.of("minecraft:village_"), () -> new ItemStack(Material.EMERALD)),
    WITCH_HUT(FinderType.NORMAL, List.of("minecraft:witch_hut"), Mytems.WITCH_FACE::createIcon),
    // Secret
    BASTION_REMNANT(FinderType.SECRET, List.of("minecraft:bastion_remnant"), () -> new ItemStack(Material.GILDED_BLACKSTONE)),
    BURIED_TREASURE(FinderType.SECRET, List.of("minecraft:buried_treasure"), () -> new ItemStack(Material.CHEST)),
    MONUMENT(FinderType.SECRET, List.of("minecraft:monument"), Mytems.GUARDIAN_FACE::createIcon),
    STRONGHOLD(FinderType.SECRET, List.of("minecraft:stronghold"), () -> new ItemStack(Material.ENDER_EYE)),
    WOODLAND_MANSION(FinderType.SECRET, List.of("minecraft:mansion"), Mytems.VEX_FACE::createIcon),
    // Master
    ANCIENT_CITY(FinderType.MASTER, List.of("minecraft:ancient_city"), Mytems.WARDEN_FACE::createIcon),
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
}
