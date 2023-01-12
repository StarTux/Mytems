package com.cavetale.mytems.util;

import com.cavetale.mytems.MytemsPlugin;
import java.util.EnumMap;
import java.util.Map;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;

/**
 * Utiltiy for block colors.
 */
public enum BlockColor {
    BLACK(0x1D1D21),
    RED(0xB02E26),
    GREEN(0x5E7C16),
    BROWN(0x835432),
    BLUE(0x3C44AA),
    PURPLE(0x8932B8),
    CYAN(0x169C9C),
    LIGHT_GRAY(0x9D9D97),
    GRAY(0x474F52),
    PINK(0xF38BAA),
    LIME(0x80C71F),
    YELLOW(0xFED83D),
    LIGHT_BLUE(0x3AB3DA),
    MAGENTA(0xC74EBD),
    ORANGE(0xF9801D),
    WHITE(0xF9FFFE);

    public enum Suffix {
        BANNER,
        BED,
        CANDLE,
        CANDLE_CAKE,
        CARPET,
        CONCRETE,
        CONCRETE_POWDER,
        DYE,
        GLAZED_TERRACOTTA,
        SHULKER_BOX,
        STAINED_GLASS,
        STAINED_GLASS_PANE,
        TERRACOTTA,
        WALL_BANNER,
        WOOL;

        public Material getMaterial(BlockColor blockColor) {
            return blockColor.suffixMap.get(this);
        }
    }

    public final String niceName;
    public final TextColor textColor;
    public final org.bukkit.Color bukkitColor;
    public final DyeColor dyeColor;
    private static final Map<Material, BlockColor> MATERIAL_MAP = new EnumMap<>(Material.class);
    private static final Map<DyeColor, BlockColor> DYE_COLOR_MAP = new EnumMap<>(DyeColor.class);
    private final Map<Suffix, Material> suffixMap = new EnumMap<>(Suffix.class);
    private final Map<Material, Suffix> materialSuffixMap = new EnumMap<>(Material.class);

    BlockColor(final int hex) {
        this.niceName = Text.toCamelCase(this, " ");
        this.textColor = TextColor.color(hex);
        this.bukkitColor = org.bukkit.Color.fromRGB(hex);
        this.dyeColor = DyeColor.valueOf(name());
    }

    static {
        for (BlockColor blockColor : BlockColor.values()) {
            for (Suffix suffix : Suffix.values()) {
                String key = blockColor.name() + "_" + suffix.name();
                try {
                    Material material = Material.valueOf(key);
                    MATERIAL_MAP.put(material, blockColor);
                    blockColor.suffixMap.put(suffix, material);
                    blockColor.materialSuffixMap.put(material, suffix);
                    DYE_COLOR_MAP.put(blockColor.dyeColor, blockColor);
                } catch (IllegalArgumentException iae) {
                    MytemsPlugin.getInstance().getLogger().warning("BlockColor: Material not found: " + key);
                }
            }
        }
    }

    public static BlockColor of(Material material) {
        return MATERIAL_MAP.get(material);
    }

    public static BlockColor of(DyeColor dyeColor) {
        return DYE_COLOR_MAP.get(dyeColor);
    }

    public Material getMaterial(Suffix suffix) {
        return suffixMap.get(suffix);
    }

    public Suffix suffixOf(Material material) {
        return materialSuffixMap.get(material);
    }
}
