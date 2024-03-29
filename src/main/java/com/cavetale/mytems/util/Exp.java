package com.cavetale.mytems.util;

import org.bukkit.entity.Player;

public final class Exp {
    public static boolean has(Player player, float amount) {
        final int levels = (int) Math.floor(amount);
        if (player.getLevel() < levels) return false;
        final float remainder = amount - (float) levels;
        return player.getLevel() > 0
            || player.getExp() >= remainder;
    }

    public static void take(Player player, float amount) {
        if (amount >= 1f) {
            final int levels = (int) Math.floor(amount);
            player.setLevel(Math.max(0, player.getLevel() - levels));
            amount = amount - (float) levels;
        }
        if (player.getExp() > amount) {
            player.setExp(player.getExp() - amount);
        } else if (player.getLevel() > 0) {
            player.setLevel(player.getLevel() - 1);
            player.setExp(player.getExp() - amount + 1f);
        } else {
            player.setLevel(0);
            player.setExp(0f);
        }
    }

    public static boolean tryToTake(Player player, float amount) {
        if (!has(player, amount)) {
            return false;
        } else {
            take(player, amount);
            return true;
        }
    }

    private Exp() { }
}
