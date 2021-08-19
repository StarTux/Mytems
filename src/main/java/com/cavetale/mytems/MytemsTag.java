package com.cavetale.mytems;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum MytemsTag {
    ACULA(Mytems.Category.ACULA),
    EASTER_EGG(Mytems.Category.EASTER_EGG),
    CLOUD_CITY(Mytems.Category.CLOUD_CITY),
    CURRENCY(Mytems.Category.CURRENCY),
    SANTA(Mytems.Category.SANTA),
    DUNE(Mytems.Category.DUNE),
    SWAMPY(Mytems.Category.SWAMPY),
    DWARVEN(Mytems.Category.DWARVEN),
    EASTER(Mytems.Category.EASTER),
    WARDROBE(Mytems.Category.WARDROBE),
    VOTE(Mytems.Category.VOTE),
    MAYPOLE(Mytems.Category.MAYPOLE),
    PIRATE(Mytems.Category.PIRATE),
    ENDERBALL(Mytems.Category.UTILITY),
    POCKET_MOB(Mytems.Category.POCKET_MOB),
    MOB_CATCHER(Mytems.Category.MOB_CATCHER),
    ITEM_SETS(MytemsTag.ACULA, MytemsTag.SANTA, MytemsTag.DUNE, MytemsTag.SWAMPY,
              MytemsTag.DWARVEN, MytemsTag.EASTER),
    EQUIPMENT(MytemsTag.ITEM_SETS, MytemsTag.PIRATE),
    ENEMY(Mytems.Category.ENEMY),
    BLOCK(Mytems.Category.BLOCK),
    REACTION(Mytems.Category.REACTION),
    UI(Mytems.Category.UI),
    FRIENDS(Mytems.Category.FRIENDS),
    RESOURCE(Mytems.Category.RESOURCE, Mytems.Category.MAYPOLE),
    PICTURE(Mytems.Category.PICTURE),
    UTILITY(Mytems.Category.UTILITY);

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
