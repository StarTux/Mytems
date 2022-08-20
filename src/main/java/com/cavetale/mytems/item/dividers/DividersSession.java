package com.cavetale.mytems.item.dividers;

import com.cavetale.core.struct.Vec3i;
import java.util.List;

public final class DividersSession {
    protected boolean drawing;
    protected String world;
    protected Vec3i point1;
    protected Vec3i point2;
    protected List<Vec3i> blocks;

    protected void reset() {
        world = null;
        point1 = null;
        point2 = null;
        blocks = null;
    }
}
