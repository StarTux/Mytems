package com.cavetale.mytems.item.photo;

import java.util.UUID;

public final record PhotoData(UUID owner, int mapId, int color, String name) { }
