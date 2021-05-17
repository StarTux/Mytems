package com.cavetale.mytems;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum MytemsTag {
    ACULA(Mytems.DR_ACULA_STAFF, Mytems.FLAME_SHIELD, Mytems.STOMPERS,
          Mytems.GHAST_BOW, Mytems.BAT_MASK),
    EASTER_EGG(Mytems.BLUE_EASTER_EGG, Mytems.GREEN_EASTER_EGG, Mytems.ORANGE_EASTER_EGG,
               Mytems.PINK_EASTER_EGG, Mytems.PURPLE_EASTER_EGG, Mytems.YELLOW_EASTER_EGG),
    CLOUD_CITY(Mytems.UNICORN_HORN, Mytems.MAGIC_CAPE),
    CURRENCY(Mytems.KITTY_COIN, Mytems.CHRISTMAS_TOKEN, Mytems.EASTER_TOKEN),
    SANTA(Mytems.SANTA_HAT, Mytems.SANTA_JACKET, Mytems.SANTA_PANTS, Mytems.SANTA_BOOTS),
    DUNE(Mytems.DUNE_HELMET, Mytems.DUNE_CHESTPLATE, Mytems.DUNE_LEGGINGS,
         Mytems.DUNE_BOOTS, Mytems.DUNE_DIGGER),
    SWAMPY(Mytems.SWAMPY_HELMET, Mytems.SWAMPY_CHESTPLATE, Mytems.SWAMPY_LEGGINGS,
           Mytems.SWAMPY_BOOTS, Mytems.SWAMPY_TRIDENT),
    DWARVEN(Mytems.DWARVEN_HELMET, Mytems.DWARVEN_CHESTPLATE, Mytems.DWARVEN_LEGGINGS,
            Mytems.DWARVEN_BOOTS, Mytems.DWARVEN_AXE),
    EASTER(Mytems.EASTER_HELMET, Mytems.EASTER_CHESTPLATE, Mytems.EASTER_LEGGINGS, Mytems.EASTER_BOOTS),
    WARDROBE(Mytems.WHITE_BUNNY_EARS, Mytems.RED_LIGHTSABER, Mytems.BLUE_LIGHTSABER),
    VOTE(Mytems.VOTE_CANDY, Mytems.VOTE_FIREWORK),
    MAYPOLE(Mytems.MISTY_MOREL, Mytems.LUCID_LILY, Mytems.PINE_CONE, Mytems.ORANGE_ONION,
            Mytems.MISTY_MOREL, Mytems.RED_ROSE, Mytems.FROST_FLOWER, Mytems.HEAT_ROOT,
            Mytems.CACTUS_BLOSSOM, Mytems.PIPE_WEED, Mytems.KINGS_PUMPKIN, Mytems.SPARK_SEED,
            Mytems.OASIS_WATER, Mytems.CLAMSHELL, Mytems.FROZEN_AMBER, Mytems.CLUMP_OF_MOSS,
            Mytems.FIRE_AMANITA),
    ENDERBALL(Mytems.ENDERBALL),
    POCKET_MOB(Mytems.POCKET_BAT, Mytems.POCKET_BEE, Mytems.POCKET_BLAZE, Mytems.POCKET_CAT,
               Mytems.POCKET_CAVE_SPIDER, Mytems.POCKET_CHICKEN, Mytems.POCKET_COD,
               Mytems.POCKET_COW, Mytems.POCKET_CREEPER, Mytems.POCKET_DOLPHIN,
               Mytems.POCKET_DONKEY, Mytems.POCKET_DROWNED, Mytems.POCKET_ELDER_GUARDIAN,
               Mytems.POCKET_ENDERMAN, Mytems.POCKET_ENDERMITE, Mytems.POCKET_ENDER_DRAGON,
               Mytems.POCKET_EVOKER, Mytems.POCKET_FOX, Mytems.POCKET_GHAST,
               Mytems.POCKET_GIANT, Mytems.POCKET_GUARDIAN, Mytems.POCKET_HOGLIN,
               Mytems.POCKET_HORSE, Mytems.POCKET_HUSK, Mytems.POCKET_ILLUSIONER,
               Mytems.POCKET_IRON_GOLEM, Mytems.POCKET_LLAMA, Mytems.POCKET_MAGMA_CUBE,
               Mytems.POCKET_MULE, Mytems.POCKET_MUSHROOM_COW, Mytems.POCKET_OCELOT,
               Mytems.POCKET_PANDA, Mytems.POCKET_PARROT, Mytems.POCKET_PHANTOM,
               Mytems.POCKET_PIG, Mytems.POCKET_PIGLIN, Mytems.POCKET_PIGLIN_BRUTE,
               Mytems.POCKET_PILLAGER, Mytems.POCKET_POLAR_BEAR, Mytems.POCKET_PUFFERFISH,
               Mytems.POCKET_RABBIT, Mytems.POCKET_RAVAGER, Mytems.POCKET_SALMON,
               Mytems.POCKET_SHEEP, Mytems.POCKET_SHULKER, Mytems.POCKET_SILVERFISH,
               Mytems.POCKET_SKELETON, Mytems.POCKET_SKELETON_HORSE, Mytems.POCKET_SLIME,
               Mytems.POCKET_SNOWMAN, Mytems.POCKET_SPIDER, Mytems.POCKET_SQUID,
               Mytems.POCKET_STRAY, Mytems.POCKET_STRIDER, Mytems.POCKET_TRADER_LLAMA,
               Mytems.POCKET_TROPICAL_FISH, Mytems.POCKET_TURTLE, Mytems.POCKET_VEX,
               Mytems.POCKET_VILLAGER, Mytems.POCKET_VINDICATOR, Mytems.POCKET_WANDERING_TRADER,
               Mytems.POCKET_WITCH, Mytems.POCKET_WITHER, Mytems.POCKET_WITHER_SKELETON,
               Mytems.POCKET_WOLF, Mytems.POCKET_ZOGLIN, Mytems.POCKET_ZOMBIE,
               Mytems.POCKET_ZOMBIE_HORSE, Mytems.POCKET_ZOMBIE_VILLAGER,
               Mytems.POCKET_ZOMBIFIED_PIGLIN),
    MOB_CATCHER(Mytems.MOB_CATCHER, Mytems.MONSTER_CATCHER, Mytems.ANIMAL_CATCHER,
                Mytems.VILLAGER_CATCHER, Mytems.FISH_CATCHER, Mytems.PET_CATCHER),
    ITEM_SETS(MytemsTag.ACULA, MytemsTag.SANTA, MytemsTag.DUNE, MytemsTag.SWAMPY,
              MytemsTag.DWARVEN, MytemsTag.EASTER),
    EQUIPMENT(MytemsTag.ITEM_SETS, Mytems.CAPTAINS_CUTLASS),
    ENEMY(Mytems.KOBOLD_HEAD);

    private final EnumSet<Mytems> set;

    MytemsTag(final Enum... enums) {
        EnumSet<Mytems> theSet = EnumSet.noneOf(Mytems.class);
        for (Enum e : enums) {
            if (e instanceof Mytems) {
                theSet.add((Mytems) e);
            } else if (e instanceof MytemsTag) {
                theSet.addAll(((MytemsTag) e).set);
            } else {
                throw new IllegalArgumentException("Invlid enum: " + e.getClass().getName() + "." + e.name());
            }
        }
        set = theSet;
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
