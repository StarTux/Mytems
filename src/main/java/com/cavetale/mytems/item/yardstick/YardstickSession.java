package com.cavetale.mytems.item.yardstick;

import com.cavetale.core.struct.Vec3i;
import com.cavetale.mytems.session.Session;
import java.util.List;
import org.bukkit.entity.Player;

public final class YardstickSession {
    protected boolean drawing;
    protected String world;
    protected Vec3i point1;
    protected Vec3i point2;
    protected int horizontalLength;
    protected int length;
    protected List<Vec3i> blocks;

    protected void reset() {
        world = null;
        point1 = null;
        point2 = null;
        blocks = null;
    }

    public static YardstickSession of(Player player) {
        return Session.of(player).getFavorites().getOrSet(YardstickSession.class, YardstickSession::new);
    }
}
