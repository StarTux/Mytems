package com.cavetale.mytems;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum MytemsTag {
    ACULA(Mytems.Category.ACULA),
    BLOCK(Mytems.Category.BLOCK),
    CLOUD_CITY(Mytems.Category.CLOUD_CITY),
    CURRENCY(Mytems.Category.CURRENCY),
    DUNE(Mytems.Category.DUNE),
    DWARVEN(Mytems.Category.DWARVEN),
    EASTER(Mytems.Category.EASTER),
    EASTER_EGG(Mytems.Category.EASTER_EGG),
    ENDERBALL(Mytems.Category.UTILITY),
    ENEMY(Mytems.Category.ENEMY),
    FRIENDS(Mytems.Category.FRIENDS),
    HALLOWEEN(Mytems.Category.HALLOWEEN),
    MAYPOLE(Mytems.Category.MAYPOLE),
    MOB_CATCHER(Mytems.Category.MOB_CATCHER),
    MUSIC(Mytems.Category.MUSIC),
    PICTURE(Mytems.Category.PICTURE),
    PIRATE(Mytems.Category.PIRATE),
    POCKET_MOB(Mytems.Category.POCKET_MOB),
    REACTION(Mytems.Category.REACTION),
    RESOURCE(Mytems.Category.RESOURCE, Mytems.Category.MAYPOLE),
    SANTA(Mytems.Category.SANTA),
    SWAMPY(Mytems.Category.SWAMPY),
    UI(Mytems.Category.UI),
    UTILITY(Mytems.Category.UTILITY),
    VOTE(Mytems.Category.VOTE),
    WARDROBE(Mytems.Category.WARDROBE),
    ITEM_SETS(MytemsTag.ACULA, MytemsTag.SANTA, MytemsTag.DUNE, MytemsTag.SWAMPY,
              MytemsTag.DWARVEN, MytemsTag.EASTER),
    EQUIPMENT(MytemsTag.ITEM_SETS, MytemsTag.PIRATE);

    private final EnumSet<Mytems> set;

    MytemsTag(final Enum... enums) {
        EnumSet<Mytems> theSet = EnumSet.noneOf(Mytems.class);
        for (Enum e : enums) {
            if (e instanceof Mytems) {
                theSet.add((Mytems) e);
            } else if (e instanceof MytemsTag) {
                theSet.addAll(((MytemsTag) e).set);
            } else if (e instanceof Mytems.Category) {
                Mytems.Category category = (Mytems.Category) e;
                for (Mytems mytems : Mytems.values()) {
                    if (mytems.category == category) {
                        theSet.add(mytems);
                    }
                }
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
