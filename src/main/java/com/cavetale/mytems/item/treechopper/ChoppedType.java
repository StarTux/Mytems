package com.cavetale.mytems.item.treechopper;

import com.destroystokyo.paper.MaterialSetTag;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;

public enum ChoppedType {
    ACACIA(Tag.ACACIA_LOGS, Material.ACACIA_LEAVES, Material.ACACIA_SAPLING),
    BIRCH(Tag.BIRCH_LOGS, Material.BIRCH_LEAVES, Material.BIRCH_SAPLING),
    DARK_OAK(Tag.DARK_OAK_LOGS, Material.DARK_OAK_LEAVES, Material.DARK_OAK_SAPLING),
    JUNGLE(Tag.JUNGLE_LOGS, Material.JUNGLE_LEAVES, Material.JUNGLE_SAPLING),
    OAK(Tag.OAK_LOGS, Material.OAK_LEAVES, Material.OAK_SAPLING),
    SPRUCE(Tag.SPRUCE_LOGS, Material.SPRUCE_LEAVES, Material.SPRUCE_SAPLING),
    AZALEA(new MaterialSetTag(null, List.of()),
           new MaterialSetTag(null, List.of(Material.AZALEA_LEAVES, Material.FLOWERING_AZALEA_LEAVES)),
           new MaterialSetTag(null, List.of(Material.AZALEA, Material.FLOWERING_AZALEA)));

    public final Tag<Material> logs;
    public final Tag<Material> leaves;
    public final Tag<Material> saplings;
    public static final Map<Material, ChoppedType> MATERIAL_MAP = new EnumMap<>(Material.class);

    ChoppedType(final Tag<Material> logs, final Tag<Material> leaves, final Tag<Material> saplings) {
        this.logs = logs;
        this.leaves = leaves;
        this.saplings = saplings;
    }

    ChoppedType(final Tag<Material> logs, final Material leaves, final Material sapling) {
        this.logs = logs;
        this.leaves = new MaterialSetTag(null, List.of(leaves));
        this.saplings = new MaterialSetTag(null, List.of(sapling));
    }

    static {
        for (ChoppedType type : ChoppedType.values()) {
            for (Material mat : type.logs.getValues()) {
                MATERIAL_MAP.put(mat, type);
            }
            for (Material mat : type.leaves.getValues()) {
                MATERIAL_MAP.put(mat, type);
            }
            for (Material mat : type.saplings.getValues()) {
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
}