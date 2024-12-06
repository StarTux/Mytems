package com.cavetale.mytems.util;

import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.entity.Player;

public final class Hunger {
    private Hunger() { }

    /**
     * Create hunger by first attempting to take some saturation, then
     * take away one food level point according to the given chance.
     *
     * Side effects: The player may lose some food level or
     * saturation.

     * @param player the player
     * @param saturation the saturation to take away if available
     * @param foodLevelChance the chance by which the food level will decrease
     * @result true if they had either some sturation or food level to
     *   use up, false otherwise.
     */
    public static boolean createHunger(Player player, float saturation, double foodLevelChance) {
        if (player.getSaturation() >= 0.01) {
            player.setSaturation(Math.max(0.0f, player.getSaturation() - saturation));
            return true;
        } else if (player.getFoodLevel() > 0) {
            if (ThreadLocalRandom.current().nextDouble() < foodLevelChance) {
                player.setFoodLevel(Math.max(0, player.getFoodLevel() - 1));
            }
            return true;
        } else {
            return false;
        }
    }
}
