package com.cavetale.mytems.item.furniture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class FurnitureTypeFixedBlock implements FurnitureTypeImpl {
    private final FurnitureType type;

    @Override
    public FurnitureOrientation getOrientation() {
        return FurnitureOrientation.NONE;
    }
}
