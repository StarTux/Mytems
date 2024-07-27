package com.cavetale.mytems.item.finder;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.core.structure.Structure;

record Found(Structure structure, FoundType type, Vec3i vector, int distance) { }
