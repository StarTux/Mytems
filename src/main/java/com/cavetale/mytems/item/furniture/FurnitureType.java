package com.cavetale.mytems.item.furniture;

import com.cavetale.mytems.Mytems;
import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import static com.cavetale.core.util.CamelCase.toCamelCase;
import static net.kyori.adventure.text.Component.text;

@Getter
@RequiredArgsConstructor
public enum FurnitureType {
    TOILET(Mytems.TOILET, FurnitureTypeToilet::new),
    BOSS_CHEST(Mytems.BOSS_CHEST, FurnitureTypeFixedBlock::new),
    OAK_CHAIR(Mytems.OAK_CHAIR, FurnitureTypeChair::new),
    SPRUCE_CHAIR(Mytems.SPRUCE_CHAIR, FurnitureTypeChair::new),
    // Armchairs
    WHITE_WOOL_ARMCHAIR(Mytems.WHITE_WOOL_ARMCHAIR, FurnitureTypeChair::new),
    LIGHT_GRAY_WOOL_ARMCHAIR(Mytems.LIGHT_GRAY_WOOL_ARMCHAIR, FurnitureTypeChair::new),
    GRAY_WOOL_ARMCHAIR(Mytems.GRAY_WOOL_ARMCHAIR, FurnitureTypeChair::new),
    BLACK_WOOL_ARMCHAIR(Mytems.BLACK_WOOL_ARMCHAIR, FurnitureTypeChair::new),
    BROWN_WOOL_ARMCHAIR(Mytems.BROWN_WOOL_ARMCHAIR, FurnitureTypeChair::new),
    RED_WOOL_ARMCHAIR(Mytems.RED_WOOL_ARMCHAIR, FurnitureTypeChair::new),
    ORANGE_WOOL_ARMCHAIR(Mytems.ORANGE_WOOL_ARMCHAIR, FurnitureTypeChair::new),
    YELLOW_WOOL_ARMCHAIR(Mytems.YELLOW_WOOL_ARMCHAIR, FurnitureTypeChair::new),
    LIME_WOOL_ARMCHAIR(Mytems.LIME_WOOL_ARMCHAIR, FurnitureTypeChair::new),
    GREEN_WOOL_ARMCHAIR(Mytems.GREEN_WOOL_ARMCHAIR, FurnitureTypeChair::new),
    CYAN_WOOL_ARMCHAIR(Mytems.CYAN_WOOL_ARMCHAIR, FurnitureTypeChair::new),
    LIGHT_BLUE_WOOL_ARMCHAIR(Mytems.LIGHT_BLUE_WOOL_ARMCHAIR, FurnitureTypeChair::new),
    BLUE_WOOL_ARMCHAIR(Mytems.BLUE_WOOL_ARMCHAIR, FurnitureTypeChair::new),
    PURPLE_WOOL_ARMCHAIR(Mytems.PURPLE_WOOL_ARMCHAIR, FurnitureTypeChair::new),
    MAGENTA_WOOL_ARMCHAIR(Mytems.MAGENTA_WOOL_ARMCHAIR, FurnitureTypeChair::new),
    PINK_WOOL_ARMCHAIR(Mytems.PINK_WOOL_ARMCHAIR, FurnitureTypeChair::new),
    // Sofas
    WHITE_WOOL_SOFA(Mytems.WHITE_WOOL_SOFA, FurnitureTypeSofa::new),
    LIGHT_GRAY_WOOL_SOFA(Mytems.LIGHT_GRAY_WOOL_SOFA, FurnitureTypeSofa::new),
    GRAY_WOOL_SOFA(Mytems.GRAY_WOOL_SOFA, FurnitureTypeSofa::new),
    BLACK_WOOL_SOFA(Mytems.BLACK_WOOL_SOFA, FurnitureTypeSofa::new),
    BROWN_WOOL_SOFA(Mytems.BROWN_WOOL_SOFA, FurnitureTypeSofa::new),
    RED_WOOL_SOFA(Mytems.RED_WOOL_SOFA, FurnitureTypeSofa::new),
    ORANGE_WOOL_SOFA(Mytems.ORANGE_WOOL_SOFA, FurnitureTypeSofa::new),
    YELLOW_WOOL_SOFA(Mytems.YELLOW_WOOL_SOFA, FurnitureTypeSofa::new),
    LIME_WOOL_SOFA(Mytems.LIME_WOOL_SOFA, FurnitureTypeSofa::new),
    GREEN_WOOL_SOFA(Mytems.GREEN_WOOL_SOFA, FurnitureTypeSofa::new),
    CYAN_WOOL_SOFA(Mytems.CYAN_WOOL_SOFA, FurnitureTypeSofa::new),
    LIGHT_BLUE_WOOL_SOFA(Mytems.LIGHT_BLUE_WOOL_SOFA, FurnitureTypeSofa::new),
    BLUE_WOOL_SOFA(Mytems.BLUE_WOOL_SOFA, FurnitureTypeSofa::new),
    PURPLE_WOOL_SOFA(Mytems.PURPLE_WOOL_SOFA, FurnitureTypeSofa::new),
    MAGENTA_WOOL_SOFA(Mytems.MAGENTA_WOOL_SOFA, FurnitureTypeSofa::new),
    PINK_WOOL_SOFA(Mytems.PINK_WOOL_SOFA, FurnitureTypeSofa::new),
    ;

    private final Mytems mytems;
    private final Function<FurnitureType, FurnitureTypeImpl> constructor;
    private FurnitureTypeImpl implementation;

    protected void enable() {
        implementation = constructor.apply(this);
    }

    public static FurnitureType of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }

    public Component getDisplayName() {
        return text(toCamelCase(" ", this));
    }
}
