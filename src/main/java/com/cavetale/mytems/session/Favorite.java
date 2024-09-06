package com.cavetale.mytems.session;

/**
 * Objects stored with Favorites can implement this interface to
 * receive a call when the session is disabled.
 */
public interface Favorite {
    void onDisable();
}
