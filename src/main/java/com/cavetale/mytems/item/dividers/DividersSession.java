package com.cavetale.mytems.item.dividers;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.mytems.item.axis.CuboidOutline;
import com.cavetale.mytems.session.Favorite;
import com.cavetale.mytems.session.Session;
import java.util.Map;
import org.bukkit.Axis;
import org.bukkit.entity.Player;

public final class DividersSession implements Favorite {
    protected String world;
    protected Vec3i point1;
    protected Vec3i point2;
    protected Axis axis;
    protected int radius;
    protected Map<Vec3i, CuboidOutline> blocks;

    @Override
    public void onSessionDisable(Session session) {
        clearBlocks();
        reset();
    }

    protected void reset() {
        world = null;
        point1 = null;
        point2 = null;
        blocks = null;
    }

    protected void clearBlocks() {
        if (blocks == null) return;
        for (CuboidOutline outline : blocks.values()) {
            outline.remove();
        }
        blocks.clear();
    }

    public static DividersSession of(Player player) {
        return Session.of(player).getFavorites().getOrSet(DividersSession.class, DividersSession::new);
    }
}
