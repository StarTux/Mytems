package com.cavetale.mytems;

import com.cavetale.mytems.item.*;
import com.cavetale.mytems.item.dune.DuneItem;
import com.cavetale.mytems.item.santa.*;
import com.cavetale.worldmarker.ItemMarker;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.bukkit.inventory.ItemStack;

public enum Mytems {
    // Halloween 2020
    DR_ACULA_STAFF(DrAculaStaff::new),
    FLAME_SHIELD(FlameShield::new),
    STOMPERS(Stompers::new),
    GHAST_BOW(GhastBow::new),
    BAT_MASK(BatMask::new),
    // Cloud City
    UNICORN_HORN(UnicornHorn::new),
    MAGIC_CAPE(MagicCape::new),
    // Generic
    KITTY_COIN(KittyCoin::new, "kitty_coin"),
    // Christmas 2020
    CHRISTMAS_TOKEN(ChristmasToken::new, "christmas_token"),
    SANTA_HAT(SantaHat::new),
    SANTA_JACKET(SantaJacket::new),
    SANTA_PANTS(SantaPants::new),
    SANTA_BOOTS(SantaBoots::new),
    // Dune set
    DUNE_HELMET(DuneItem.Helmet::new),
    DUNE_CHESTPLATE(DuneItem.Chestplate::new),
    DUNE_LEGGINGS(DuneItem.Leggings::new),
    DUNE_BOOTS(DuneItem.Boots::new),
    DUNE_DIGGER(DuneItem.Weapon::new);

    private static final Map<String, Mytems> ID_MAP = new HashMap<>();
    public final String id; // optionally qualified, e.g. mytems:dr_acula_staff
    public final Function<MytemsPlugin, Mytem> ctor;

    static {
        for (Mytems it : Mytems.values()) {
            ID_MAP.put(it.id, it);
            ID_MAP.put(it.id.split(":")[0], it);
        }
    }

    Mytems(final Function<MytemsPlugin, Mytem> ctor) {
        this.ctor = ctor;
        this.id = "mytems:" + name().toLowerCase();
    }

    Mytems(final Function<MytemsPlugin, Mytem> ctor, final String id) {
        this.ctor = ctor;
        this.id = id;
    }

    public static Mytems forId(String in) {
        return ID_MAP.get(in);
    }

    public static Mytems forItem(ItemStack item) {
        if (item == null) return null;
        String id = ItemMarker.getId(item);
        if (id == null) return null;
        return forId(id);
    }

    public Mytem getMytem() {
        return MytemsPlugin.getInstance().getMytem(this);
    }
}
