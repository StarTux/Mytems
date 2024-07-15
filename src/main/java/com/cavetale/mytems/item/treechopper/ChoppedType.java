package com.cavetale.mytems.item.treechopper;

import com.cavetale.core.font.VanillaItems;
import com.destroystokyo.paper.MaterialSetTag;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;

public enum ChoppedType {
    ACACIA(Tag.ACACIA_LOGS, Material.ACACIA_LEAVES, Placeable.SAPLING, Material.ACACIA_SAPLING, VanillaItems.ACACIA_SAPLING),
    BIRCH(Tag.BIRCH_LOGS, Material.BIRCH_LEAVES, Placeable.SAPLING, Material.BIRCH_SAPLING, VanillaItems.BIRCH_SAPLING),
    DARK_OAK(Tag.DARK_OAK_LOGS, Material.DARK_OAK_LEAVES, Placeable.SAPLING, Material.DARK_OAK_SAPLING, VanillaItems.DARK_OAK_SAPLING),
    JUNGLE(Tag.JUNGLE_LOGS, Material.JUNGLE_LEAVES, Placeable.SAPLING, Material.JUNGLE_SAPLING, VanillaItems.JUNGLE_SAPLING),
    OAK(Tag.OAK_LOGS, Material.OAK_LEAVES, Placeable.SAPLING, Material.OAK_SAPLING, VanillaItems.OAK_SAPLING),
    SPRUCE(Tag.SPRUCE_LOGS, Material.SPRUCE_LEAVES, Placeable.SAPLING, Material.SPRUCE_SAPLING, VanillaItems.SPRUCE_SAPLING),
    CHERRY(Tag.CHERRY_LOGS, Material.CHERRY_LEAVES, Placeable.SAPLING, Material.CHERRY_SAPLING, VanillaItems.CHERRY_SAPLING),
    AZALEA(Tag.OAK_LOGS,
           new MaterialSetTag(null, List.of(Material.AZALEA_LEAVES, Material.FLOWERING_AZALEA_LEAVES, Material.OAK_LEAVES)),
           Placeable.SAPLING,
           new MaterialSetTag(null, List.of(Material.AZALEA, Material.FLOWERING_AZALEA)),
           VanillaItems.AZALEA),
    // MANGROVE(Tag.MANGROVE_LOGS, Material.MANGROVE_LEAVES, Material.MANGROVE_PROPAGULE),
    RED_MUSHROOM(new MaterialSetTag(null, List.of(Material.MUSHROOM_STEM)),
                 new MaterialSetTag(null, List.of(Material.RED_MUSHROOM_BLOCK)),
                 Placeable.SAPLING,
                 new MaterialSetTag(null, List.of(Material.RED_MUSHROOM)),
                 VanillaItems.RED_MUSHROOM),
    BROWN_MUSHROOM(new MaterialSetTag(null, List.of(Material.MUSHROOM_STEM)),
                   new MaterialSetTag(null, List.of(Material.BROWN_MUSHROOM_BLOCK)),
                   Placeable.SAPLING,
                   new MaterialSetTag(null, List.of(Material.BROWN_MUSHROOM)),
                   VanillaItems.BROWN_MUSHROOM),
    CRIMSON(Tag.CRIMSON_STEMS, Material.NETHER_WART_BLOCK, Placeable.FUNGUS, Material.CRIMSON_FUNGUS, VanillaItems.CRIMSON_FUNGUS),
    WARPED(Tag.WARPED_STEMS, Material.WARPED_WART_BLOCK, Placeable.FUNGUS, Material.WARPED_FUNGUS, VanillaItems.WARPED_FUNGUS),
    ;

    public final Tag<Material> logs;
    public final Tag<Material> leaves;
    public final Tag<Material> placeableOn;
    public final Tag<Material> saplings;
    public final VanillaItems chatIcon;
    public static final Map<Material, ChoppedType> MATERIAL_MAP = new HashMap<>();
    public static final Set<Material> ALL_LOGS = new HashSet<>();

    ChoppedType(final Tag<Material> logs, final Tag<Material> leaves, final Tag<Material> placeableOn, final Tag<Material> saplings, final VanillaItems chatIcon) {
        this.logs = logs;
        this.leaves = leaves;
        this.placeableOn = placeableOn;
        this.saplings = saplings;
        this.chatIcon = chatIcon;
    }

    ChoppedType(final Tag<Material> logs, final Material leaves, final Tag<Material> placeableOn, final Material sapling, final VanillaItems chatIcon) {
        this.logs = logs;
        this.leaves = new MaterialSetTag(null, List.of(leaves));
        this.placeableOn = placeableOn;
        this.saplings = new MaterialSetTag(null, List.of(sapling));
        this.chatIcon = chatIcon;
    }

    public boolean isJustAGuess() {
        return logs.getValues().isEmpty();
    }

    static {
        // OAK comes before AZALEA
        for (ChoppedType type : ChoppedType.values()) {
            for (Material mat : type.logs.getValues()) {
                ALL_LOGS.add(mat);
                if (MATERIAL_MAP.containsKey(mat)) continue;
                MATERIAL_MAP.put(mat, type);
            }
            for (Material mat : type.leaves.getValues()) {
                if (MATERIAL_MAP.containsKey(mat)) continue;
                MATERIAL_MAP.put(mat, type);
            }
            for (Material mat : type.saplings.getValues()) {
                if (MATERIAL_MAP.containsKey(mat)) continue;
                MATERIAL_MAP.put(mat, type);
            }
        }
    }

    public static ChoppedType of(Block block) {
        return MATERIAL_MAP.get(block.getType());
    }

    public static ChoppedType of(Material material) {
        return MATERIAL_MAP.get(material);
    }

    public boolean isAzaleaLeaf(Material material) {
        return material == Material.AZALEA_LEAVES || material == Material.FLOWERING_AZALEA_LEAVES;
    }
}

final class Placeable {
    public static final Tag<Material> SAPLING = new MaterialSetTag(null, List.of(new Material[] {
                Material.DIRT,
                Material.COARSE_DIRT,
                Material.ROOTED_DIRT,
                Material.MOSS_BLOCK,
                Material.GRASS_BLOCK,
                Material.PODZOL,
                Material.FARMLAND,
                Material.MYCELIUM,
                // World Gen
                Material.GRAVEL,
                Material.SAND,
            }));

    public static final Tag<Material> FUNGUS = new MaterialSetTag(null, List.of(new Material[] {
                Material.GRASS_BLOCK,
                Material.DIRT,
                Material.COARSE_DIRT,
                Material.PODZOL,
                Material.FARMLAND,
                Material.ROOTED_DIRT,
                Material.MOSS_BLOCK,
                Material.CRIMSON_NYLIUM,
                Material.WARPED_NYLIUM,
                Material.MYCELIUM,
                Material.SOUL_SOIL,
                Material.MUD,
                Material.MUDDY_MANGROVE_ROOTS,
                // Not technically placeable, but some blocks will
                // turn into
                Material.NETHERRACK,
            }));

    private Placeable() { }
}
