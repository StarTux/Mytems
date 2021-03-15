package com.cavetale.mytems;

import com.cavetale.mytems.item.*;
import com.cavetale.mytems.item.dune.DuneItem;
import com.cavetale.mytems.item.dwarven.DwarvenItem;
import com.cavetale.mytems.item.santa.*;
import com.cavetale.mytems.item.swampy.SwampyItem;
import com.cavetale.worldmarker.item.ItemMarker;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Mytems {
    // Halloween 2020
    DR_ACULA_STAFF(DrAculaStaff::new, "dr_acula_staff", Material.NETHERITE_SWORD, 741302),
    FLAME_SHIELD(FlameShield::new),
    STOMPERS(Stompers::new),
    GHAST_BOW(GhastBow::new),
    BAT_MASK(BatMask::new),
    // Cloud City
    UNICORN_HORN(UnicornHorn::new, Material.END_ROD, 7413003),
    MAGIC_CAPE(MagicCape::new),
    // Generic
    KITTY_COIN(KittyCoin::new, "kitty_coin", Material.PLAYER_HEAD, 7413001),
    // Christmas 2020
    CHRISTMAS_TOKEN(ChristmasToken::new, "christmas_token"),
    SANTA_HAT(SantaHat::new, Material.PLAYER_HEAD, 7413101),
    SANTA_JACKET(SantaJacket::new, Material.LEATHER_CHESTPLATE, 4713102),
    SANTA_PANTS(SantaPants::new, Material.LEATHER_LEGGINGS, 4713103),
    SANTA_BOOTS(SantaBoots::new, Material.LEATHER_BOOTS, 4713104),
    // Dune set
    DUNE_HELMET(DuneItem.Helmet::new, Material.PLAYER_HEAD, 7413201),
    DUNE_CHESTPLATE(DuneItem.Chestplate::new, Material.GOLDEN_CHESTPLATE, 7413202),
    DUNE_LEGGINGS(DuneItem.Leggings::new, Material.GOLDEN_LEGGINGS, 7413203),
    DUNE_BOOTS(DuneItem.Boots::new, Material.GOLDEN_BOOTS, 7413204),
    DUNE_DIGGER(DuneItem.Weapon::new, Material.GOLDEN_SHOVEL, 7413205),
    // Swampy set
    SWAMPY_HELMET(SwampyItem.Helmet::new, Material.PLAYER_HEAD, 7413301),
    SWAMPY_CHESTPLATE(SwampyItem.Chestplate::new, Material.LEATHER_CHESTPLATE, 7413302),
    SWAMPY_LEGGINGS(SwampyItem.Leggings::new, Material.LEATHER_LEGGINGS, 7413303),
    SWAMPY_BOOTS(SwampyItem.Boots::new, Material.LEATHER_BOOTS, 7413304),
    SWAMPY_TRIDENT(SwampyItem.Weapon::new, "swampy_trident", Material.TRIDENT, 7413305),
    // Swampy set
    DWARVEN_HELMET(DwarvenItem.Helmet::new, "dwarven_helmet", Material.PLAYER_HEAD, 7413401),
    DWARVEN_CHESTPLATE(DwarvenItem.Chestplate::new, "dwarven_chestplate", Material.IRON_CHESTPLATE, 7413402),
    DWARVEN_LEGGINGS(DwarvenItem.Leggings::new, "dwarven_leggings", Material.IRON_LEGGINGS, 7413403),
    DWARVEN_BOOTS(DwarvenItem.Boots::new, "dwarven_boots", Material.IRON_BOOTS, 7413404),
    DWARVEN_AXE(DwarvenItem.Weapon::new, "dwarven_axe", Material.IRON_AXE, 7413405),
    //
    WEDDING_RING(WeddingRing::new, "wedding_ring", Material.PLAYER_HEAD, 7413002),
    MAGIC_MAP(MagicMap::new, "magic_map");

    private static final Map<String, Mytems> ID_MAP = new HashMap<>();
    public final String id;
    public final Function<MytemsPlugin, Mytem> ctor;
    public final Material material;
    public final Integer customModelData;

    static {
        for (Mytems it : Mytems.values()) {
            ID_MAP.put(it.id, it);
            ID_MAP.put("mytems:" + it.id, it);
        }
        ID_MAP.put("dwarf_axe", DWARVEN_AXE);
        ID_MAP.put("mytems:dwarf_axe", DWARVEN_AXE);
    }

    Mytems(final Function<MytemsPlugin, Mytem> ctor) {
        this.ctor = ctor;
        this.id = name().toLowerCase();
        this.material = null;
        this.customModelData = null;
    }

    Mytems(final Function<MytemsPlugin, Mytem> ctor, final String id) {
        this(ctor, id, null, null);
    }

    Mytems(final Function<MytemsPlugin, Mytem> ctor, final Material material, final Integer customModelData) {
        this.ctor = ctor;
        this.id = name().toLowerCase();
        this.material = material;
        this.customModelData = customModelData;
    }

    Mytems(final Function<MytemsPlugin, Mytem> ctor, final String id, final Material material, final Integer customModelData) {
        this.ctor = ctor;
        this.id = id;
        this.material = material;
        this.customModelData = customModelData;
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

    /**
     * Return the mytems id, optionally by the serialized tag if it
     * exists.
     */
    public String serializeItem(ItemStack itemStack) {
        String tag = getMytem().serializeTag(itemStack);
        return tag != null
            ? id + tag
            : id;
    }

    public static ItemStack deserializeItem(String serialized) {
        int index = serialized.indexOf("{");
        String id = index >= 0 ? serialized.substring(0, index) : serialized;
        Mytems mytems = forId(id);
        if (mytems == null) return null;
        String tag = index >= 0 ? serialized.substring(index) : null;
        return tag != null
            ? mytems.getMytem().deserializeTag(tag)
            : mytems.getMytem().getItem();
    }

    public void markItemMeta(ItemMeta meta) {
        ItemMarker.setId(meta, id);
        if (customModelData != null) {
            meta.setCustomModelData(customModelData);
        }
    }

    public void markItemStack(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        markItemMeta(meta);
        itemStack.setItemMeta(meta);
    }
}
