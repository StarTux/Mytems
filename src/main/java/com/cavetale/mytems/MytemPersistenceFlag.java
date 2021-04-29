package com.cavetale.mytems;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Tell the framework which aspects of an ItemStack need to be saved
 * when it is serialized. Some Mytems may come with their own
 * enchantments so they need not be saved. Others can be enchanted by
 * players, so they should return the ENCHANTMENTS flag.
 *
 * The method Mytem::getMytemPersistenceFlags returns a set of these
 * flags.
 *
 * The default method Mytem::serializeItem and serializeItem respect
 * these flags.
 *
 * Any Mytem wishing to save anything beyond these flags must override
 * Mytem::serializeItem and deserializeItem.
 */
public enum MytemPersistenceFlag {
    ENCHANTMENTS, // (de)serialise enchantments
    DURABILITY,
    OWNER,
    DISPLAY_NAME;

    public static final Set<MytemPersistenceFlag> VANILLA = EnumSet.of(ENCHANTMENTS, DURABILITY);
    public static final Set<MytemPersistenceFlag> NONE = Collections.emptySet();

    public Set<MytemPersistenceFlag> toSet() {
        return EnumSet.of(this);
    }
}
