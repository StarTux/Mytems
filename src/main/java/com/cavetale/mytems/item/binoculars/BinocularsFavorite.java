package com.cavetale.mytems.item.binoculars;

import com.cavetale.mytems.session.Favorite;
import com.cavetale.mytems.session.Session;
import lombok.Data;

@Data
public final class BinocularsFavorite implements Favorite {
    private String worldName;
    private int x;
    private int z;

    @Override
    public void onSessionDisable(Session session) {
        session.getPlayer().setViewDistance(session.getPlayer().getWorld().getViewDistance());
    }
}
