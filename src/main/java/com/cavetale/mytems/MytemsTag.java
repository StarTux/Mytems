package com.cavetale.mytems;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Mark Mytems with Tags.  Most of these instances won't be named here
 * because they are genereated from the MytemsCategory.
 * Use of() to get an instance by name or via Category.
 */
public final class MytemsTag {
    public static final MytemsTag EMPTY = new MytemsTag("empty");
    public static final MytemsTag DICE = new MytemsTag("dice", MytemsCategory.DIE);
    public static final MytemsTag FONT = new MytemsTag("font", new MytemsCategory[] {
            MytemsCategory.LETTER, MytemsCategory.NUMBER, MytemsCategory.MUSICAL
        });
    public static final MytemsTag MUSIC_ALL = new MytemsTag("music_all", new MytemsCategory[] {
            MytemsCategory.MUSIC, MytemsCategory.MUSIC_HYRULE,
        });
    public static final MytemsTag RESOURCE = new MytemsTag("resource",
                                                           MytemsCategory.MAYPOLE);
    public static final MytemsTag WARDROBE_HAT = new MytemsTag("wardtobe_hat", new MytemsCategory[] {
            MytemsCategory.WITCH_HAT, MytemsCategory.SUNGLASSES,
        });
    public static final MytemsTag WARDROBE = new MytemsTag("wardrobe", new MytemsCategory[] {
            MytemsCategory.WARDROBE_HAT,
            MytemsCategory.WARDROBE_HANDHELD,
            MytemsCategory.CAT_EARS,
            MytemsCategory.SUNGLASSES,
        });
    public static final MytemsTag ITEM_SETS = new MytemsTag("item_sets", new MytemsCategory[] {
            MytemsCategory.ACULA,
            MytemsCategory.SANTA,
            MytemsCategory.DUNE,
            MytemsCategory.SWAMPY,
            MytemsCategory.DWARVEN,
            MytemsCategory.EASTER,
            MytemsCategory.SCARLET,
        });
    public static final MytemsTag EQUIPMENT = new MytemsTag("equipment", MytemsTag.ITEM_SETS, MytemsCategory.PIRATE);
    public static final MytemsTag NEW = new MytemsTag("new");

    public final String name;
    private final EnumSet<Mytems> set;
    private static final List<MytemsTag> VALUES = new ArrayList<>();
    private static final Map<String, MytemsTag> NAME_MAP = new HashMap<>();
    private static final Map<MytemsCategory, MytemsTag> CATEGORY_MAP = new EnumMap<>(MytemsCategory.class);
    private static List<MytemsTag> tmpList;

    static {
        for (MytemsTag it : tmpList) {
            VALUES.add(it);
            NAME_MAP.put(it.name, it);
        }
        tmpList = null;
        for (MytemsCategory it : MytemsCategory.values()) {
            MytemsTag tag = new MytemsTag(it.name().toLowerCase(), it);
            VALUES.add(tag);
            NAME_MAP.put(tag.name, tag);
            CATEGORY_MAP.put(it, tag);
        }
    }

    MytemsTag(final String name, final Enum... enums) {
        this(name, null, enums);
    }

    MytemsTag(final String name, final MytemsTag parent, final Enum... enums) {
        this.name = name;
        EnumSet<Mytems> theSet = EnumSet.noneOf(Mytems.class);
        if (parent != null) {
            theSet.addAll(parent.set);
        }
        for (Enum e : enums) {
            if (e instanceof Mytems) {
                theSet.add((Mytems) e);
            } else if (e instanceof MytemsCategory) {
                MytemsCategory category = (MytemsCategory) e;
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
        if (tmpList == null) tmpList = new ArrayList<>();
        tmpList.add(this);
    }

    public boolean isTagged(Mytems mytems) {
        return set.contains(mytems);
    }

    public static MytemsTag of(String in) {
        return NAME_MAP.getOrDefault(in.toLowerCase(), EMPTY);
    }

    public static MytemsTag of(MytemsCategory cat) {
        return CATEGORY_MAP.get(cat);
    }

    public List<Mytems> getMytems() {
        return new ArrayList<>(set);
    }

    public String name() {
        return name;
    }

    public Set<Mytems> getValues() {
        return set;
    }

    public static MytemsTag[] values() {
        return VALUES.toArray(new MytemsTag[0]);
    }
}
