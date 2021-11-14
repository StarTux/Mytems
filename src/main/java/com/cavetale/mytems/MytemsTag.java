package com.cavetale.mytems;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public enum MytemsTag {
    ACULA,
    CAT_EARS,
    CLOUD_CITY,
    COIN,
    CURRENCY,
    DUNE,
    DWARVEN,
    EASTER,
    EASTER_EGG,
    ENEMY,
    FONT(Mytems.Category.LETTER, Mytems.Category.NUMBER, Mytems.Category.MUSICAL),
    FRIENDS,
    FURNITURE,
    HALLOWEEN,
    KEY,
    KEYHOLE,
    LETTER,
    MAYPOLE,
    MOB_CATCHER,
    MUSIC,
    MUSICAL,
    MUSIC_ALL(Mytems.Category.MUSIC, Mytems.Category.MUSIC_HYRULE),
    MUSIC_HYRULE,
    NUMBER,
    PAINTBRUSH,
    PICTURE,
    PIRATE,
    POCKET_MOB,
    PUNCTUATION,
    REACTION,
    RESOURCE(Mytems.Category.MAYPOLE),
    SANTA,
    SCARLET,
    SUNGLASSES,
    SWAMPY,
    TREASURE,
    UI,
    UTILITY,
    VOTE,
    WARDROBE_HANDHELD,
    WITCH_HAT,
    WARDROBE_HAT(Mytems.Category.WITCH_HAT, Mytems.Category.SUNGLASSES),
    WARDROBE(Mytems.Category.WARDROBE_HAT, Mytems.Category.WARDROBE_HANDHELD,
             Mytems.Category.CAT_EARS, Mytems.Category.SUNGLASSES),
    ITEM_SETS(Mytems.Category.ACULA, Mytems.Category.SANTA,
              Mytems.Category.DUNE, Mytems.Category.SWAMPY,
              Mytems.Category.DWARVEN, Mytems.Category.EASTER),
    EQUIPMENT(MytemsTag.ITEM_SETS, MytemsTag.PIRATE),
    NEW;

    private final EnumSet<Mytems> set;

    MytemsTag(final Enum... enums) {
        EnumSet<Mytems> theSet = EnumSet.noneOf(Mytems.class);
        try {
            Mytems.Category category = Mytems.Category.valueOf(name());
            for (Mytems mytems : Mytems.values()) {
                if (mytems.category == category) {
                    theSet.add(mytems);
                }
            }
        } catch (IllegalArgumentException iae) { }
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
