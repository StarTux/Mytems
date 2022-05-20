package com.cavetale.mytems.item.photo;

import java.util.UUID;

public final record PhotoData(UUID owner, int mapId, int color, String name) {
    public static final PhotoData ZERO = new PhotoData(null, 0, Photo.SEPIA, "Photo");
}
