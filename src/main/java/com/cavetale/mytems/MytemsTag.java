package com.cavetale.mytems;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum MytemsTag {
    EASTER_EGG(Mytems.BLUE_EASTER_EGG, Mytems.GREEN_EASTER_EGG, Mytems.ORANGE_EASTER_EGG,
               Mytems.PINK_EASTER_EGG, Mytems.PURPLE_EASTER_EGG, Mytems.YELLOW_EASTER_EGG),
    VOTE(Mytems.VOTE_CANDY, Mytems.VOTE_FIREWORK),
    MAYPOLE(Mytems.MISTY_MOREL, Mytems.LUCID_LILY, Mytems.PINE_CONE, Mytems.ORANGE_ONION,
            Mytems.MISTY_MOREL, Mytems.RED_ROSE, Mytems.FROST_FLOWER, Mytems.HEAT_ROOT,
            Mytems.CACTUS_BLOSSOM, Mytems.PIPE_WEED, Mytems.KINGS_PUMPKIN, Mytems.SPARK_SEED,
            Mytems.OASIS_WATER, Mytems.CLAMSHELL, Mytems.FROZEN_AMBER, Mytems.CLUMP_OF_MOSS,
            Mytems.FIRE_AMANITA);

    private final EnumSet<Mytems> set;

    MytemsTag(final Mytems mytems, final Mytems... mytems2) {
        set = EnumSet.of(mytems, mytems2);
    }

    public boolean isTagged(Mytems mytems) {
        return set.contains(mytems);
    }

    public static MytemsTag of(String in) {
        try {
            return valueOf(in.toUpperCase());
        } catch (IllegalArgumentException iae) {
            return null;
        }
    }

    public List<Mytems> toList() {
        return new ArrayList<>(set);
    }
}
