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
    public static final MytemsTag WARDROBE_HAT = new MytemsTag("wardtobe_hat", new MytemsCategory[] {
            MytemsCategory.WITCH_HAT, MytemsCategory.SUNGLASSES,
        });
    public static final MytemsTag WARDROBE = new MytemsTag("wardrobe", new MytemsCategory[] {
            MytemsCategory.CAT_EARS,
            MytemsCategory.SUNGLASSES,
            MytemsCategory.WARDROBE_HANDHELD,
            MytemsCategory.WARDROBE_HAT,
            MytemsCategory.WARDROBE_MOUNT,
            MytemsCategory.WARDROBE_OFFHAND,
            MytemsCategory.WITCH_HAT,
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
    public static final MytemsTag HOLIDAYS = new MytemsTag("holidays", new MytemsCategory[] {
            MytemsCategory.CHRISTMAS,
            MytemsCategory.EASTER_EGGS,
            MytemsCategory.EASTER_TOKENS,
            MytemsCategory.HALLOWEEN,
            MytemsCategory.MAY,
            MytemsCategory.VALENTINE_TOKENS,
        });
    public static final MytemsTag COSTUME = new MytemsTag("costume", new MytemsCategory[] {
            MytemsCategory.BEE_COSTUME,
            MytemsCategory.CACTUS_COSTUME,
            MytemsCategory.CHICKEN_COSTUME,
            MytemsCategory.CREEPER_COSTUME,
            MytemsCategory.ENDERMAN_COSTUME,
            MytemsCategory.FOX_COSTUME,
            MytemsCategory.SHEEP_COSTUME,
            MytemsCategory.SKELETON_COSTUME,
            MytemsCategory.SPIDER_COSTUME,
        });
    public static final MytemsTag UTILITY = new MytemsTag("utility", new MytemsCategory[] {
            MytemsCategory.UTILITY,
            MytemsCategory.BINGO_BUKKITS,
        });
    public static final MytemsTag WEAPON = new MytemsTag("weapon", new MytemsCategory[] {
            MytemsCategory.MOBSLAYERS,
        });
    public static final MytemsTag COLLECTIBLES = new MytemsTag("collectibles", new MytemsCategory[] {
            MytemsCategory.COLLECTIBLES,
            MytemsCategory.CAVEBOY,
            MytemsCategory.HOURGLASS,
            MytemsCategory.FINDER,
        });
    public static final MytemsTag EQUIPMENT = new MytemsTag("equipment", new MytemsCategory[] {
            MytemsCategory.EQUIP_AXE,
            MytemsCategory.EQUIP_BASIC_IRON_SET,
            MytemsCategory.EQUIP_BLUE_CLOTH_SET,
            MytemsCategory.EQUIP_BLUE_JESTER_SET,
            MytemsCategory.EQUIP_BOW,
            MytemsCategory.EQUIP_BROADSWORD,
            MytemsCategory.EQUIP_CANE,
            MytemsCategory.EQUIP_CLOAK,
            MytemsCategory.EQUIP_CUTLASS,
            MytemsCategory.EQUIP_DAGGER,
            MytemsCategory.EQUIP_FINE_IRON_SET,
            MytemsCategory.EQUIP_FINE_LEATHER_SET,
            MytemsCategory.EQUIP_GILDED_KING_SET,
            MytemsCategory.EQUIP_GILDED_LORD_SET,
            MytemsCategory.EQUIP_GREEN_FEATHER_SET,
            MytemsCategory.EQUIP_HAMMER,
            MytemsCategory.EQUIP_HELMET,
            MytemsCategory.EQUIP_IRON_KNIGHT_SET,
            MytemsCategory.EQUIP_MACE,
            MytemsCategory.EQUIP_NECKLACE,
            MytemsCategory.EQUIP_ORANGE_CLOTH_SET,
            MytemsCategory.EQUIP_PEACH_CLOTH_SET,
            MytemsCategory.EQUIP_PINK_WINGED_CLOTH_SET,
            MytemsCategory.EQUIP_POLEARM,
            MytemsCategory.EQUIP_QUIVER,
            MytemsCategory.EQUIP_RED_JESTER_SET,
            MytemsCategory.EQUIP_RING,
            MytemsCategory.EQUIP_SCEPTER,
            MytemsCategory.EQUIP_SCYTHE,
            MytemsCategory.EQUIP_SHIELD,
            MytemsCategory.EQUIP_SIMPLE_LEATHER_SET,
            MytemsCategory.EQUIP_SPEAR,
            MytemsCategory.EQUIP_STAFF,
            MytemsCategory.EQUIP_STUDDED_LEATHER_SET,
            MytemsCategory.EQUIP_SWORD,
            MytemsCategory.EQUIP_TURQUOISE_CLOTH_SET,
            MytemsCategory.EQUIP_WAND,
            MytemsCategory.EQUIP_WHIP,
            MytemsCategory.EQUIP_YELLOW_WINGED_CLOTH_SET,
        });

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
        this.set = theSet;
        if (set.isEmpty() && !name.equals("empty")) {
            MytemsPlugin.getInstance().getLogger().warning("Empty tag: " + name);
        }
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
