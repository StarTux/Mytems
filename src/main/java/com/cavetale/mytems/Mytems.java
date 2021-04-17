package com.cavetale.mytems;

import com.cavetale.mytems.item.*;
import com.cavetale.mytems.item.dune.DuneItem;
import com.cavetale.mytems.item.dwarven.DwarvenItem;
import com.cavetale.mytems.item.easter.EasterEgg;
import com.cavetale.mytems.item.easter.EasterGear;
import com.cavetale.mytems.item.easter.EasterToken;
import com.cavetale.mytems.item.santa.SantaBoots;
import com.cavetale.mytems.item.santa.SantaHat;
import com.cavetale.mytems.item.santa.SantaJacket;
import com.cavetale.mytems.item.santa.SantaPants;
import com.cavetale.mytems.item.swampy.SwampyItem;
import com.cavetale.mytems.item.vote.VoteCandy;
import com.cavetale.mytems.item.vote.VoteFirework;
import com.cavetale.worldmarker.item.ItemMarker;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Mytems {
    // Halloween 2020
    DR_ACULA_STAFF(DrAculaStaff::new, Material.NETHERITE_SWORD, 741302),
    FLAME_SHIELD(FlameShield::new),
    STOMPERS(Stompers::new),
    GHAST_BOW(GhastBow::new),
    BAT_MASK(BatMask::new),
    // Cloud City
    UNICORN_HORN(UnicornHorn::new, Material.END_ROD, 7413003),
    MAGIC_CAPE(MagicCape::new, Material.ELYTRA, 7413006),
    // Generic
    KITTY_COIN(KittyCoin::new, Material.PLAYER_HEAD, 7413001),
    // Christmas 2020
    CHRISTMAS_TOKEN(ChristmasToken::new),
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
    SWAMPY_TRIDENT(SwampyItem.Weapon::new, Material.TRIDENT, 7413305),
    // Swampy set
    DWARVEN_HELMET(DwarvenItem.Helmet::new, Material.PLAYER_HEAD, 7413401),
    DWARVEN_CHESTPLATE(DwarvenItem.Chestplate::new, Material.IRON_CHESTPLATE, 7413402),
    DWARVEN_LEGGINGS(DwarvenItem.Leggings::new, Material.IRON_LEGGINGS, 7413403),
    DWARVEN_BOOTS(DwarvenItem.Boots::new, Material.IRON_BOOTS, 7413404),
    DWARVEN_AXE(DwarvenItem.Weapon::new, Material.IRON_AXE, 7413405),
    // Easter 2021
    EASTER_TOKEN(EasterToken::new, Material.PLAYER_HEAD, 345700),
    BLUE_EASTER_EGG(EasterEgg::new, Material.PLAYER_HEAD, 345701),
    GREEN_EASTER_EGG(EasterEgg::new, Material.PLAYER_HEAD, 345702),
    ORANGE_EASTER_EGG(EasterEgg::new, Material.PLAYER_HEAD, 345703),
    PINK_EASTER_EGG(EasterEgg::new, Material.PLAYER_HEAD, 345704),
    PURPLE_EASTER_EGG(EasterEgg::new, Material.PLAYER_HEAD, 345705),
    YELLOW_EASTER_EGG(EasterEgg::new, Material.PLAYER_HEAD, 345706),
    EASTER_HELMET(EasterGear.Helmet::new, Material.PLAYER_HEAD, 345711),
    EASTER_CHESTPLATE(EasterGear.Chestplate::new, Material.LEATHER_CHESTPLATE, 345712),
    EASTER_LEGGINGS(EasterGear.Leggings::new, Material.LEATHER_LEGGINGS, 345713),
    EASTER_BOOTS(EasterGear.Boots::new, Material.LEATHER_BOOTS, 345714),
    //
    TOILET(Toilet::new, Material.CAULDRON, 498101), // APRIL
    WEDDING_RING(WeddingRing::new, Material.PLAYER_HEAD, 7413002),
    MAGIC_MAP(MagicMap::new, Material.FILLED_MAP, 7413005),
    BOSS_CHEST(DummyMytem::new, Material.CHEST, 7413004),
    // Wardrobe
    WHITE_BUNNY_EARS(DummyMytem::new, Material.IRON_BOOTS, 3919001), // EPIC
    // Vote
    VOTE_CANDY(VoteCandy::new, Material.COOKIE, 9073001), // VOTE
    VOTE_FIREWORK(VoteFirework::new, Material.FIREWORK_ROCKET, 9073002),
    // Maypole
    LUCID_LILY(DummyMytem::new, Material.AZURE_BLUET, 849001, '\uE201'),
    PINE_CONE(DummyMytem::new, Material.SPRUCE_SAPLING, 849002, '\uE202'),
    ORANGE_ONION(DummyMytem::new, Material.ORANGE_TULIP, 849003, '\uE203'),
    MISTY_MOREL(DummyMytem::new, Material.WARPED_FUNGUS, 849004, '\uE204'),
    RED_ROSE(DummyMytem::new, Material.POPPY, 849005, '\uE205'),
    FROST_FLOWER(DummyMytem::new, Material.BLUE_ORCHID, 849006, '\uE206'),
    HEAT_ROOT(DummyMytem::new, Material.DEAD_BUSH, 849007, '\uE207'),
    CACTUS_BLOSSOM(DummyMytem::new, Material.CACTUS, 849008, '\uE208'),
    PIPE_WEED(DummyMytem::new, Material.FERN, 849009, '\uE209'),
    KINGS_PUMPKIN(DummyMytem::new, Material.CARVED_PUMPKIN, 849010, '\uE20A'),
    SPARK_SEED(DummyMytem::new, Material.BEETROOT_SEEDS, 849011, '\uE20B'),
    OASIS_WATER(DummyMytem::new, Material.LIGHT_BLUE_DYE, 849012, '\uE20C'),
    CLAMSHELL(DummyMytem::new, Material.NAUTILUS_SHELL, 849013, '\uE20D'),
    FROZEN_AMBER(DummyMytem::new, Material.EMERALD, 849014, '\uE20E'),
    CLUMP_OF_MOSS(DummyMytem::new, Material.VINE, 849015, '\uE20F'),
    FIRE_AMANITA(DummyMytem::new, Material.CRIMSON_FUNGUS, 849016, '\uE210'),
    //
    ENDERBALL(Enderball::new);

    private static final Map<String, Mytems> ID_MAP = new HashMap<>();
    public final String id;
    public final Function<Mytems, Mytem> ctor;
    public final Material material;
    public final Integer customModelData;
    public final char character;
    public final Component component;

    static {
        for (Mytems it : Mytems.values()) {
            ID_MAP.put(it.id, it);
            if (!it.id.contains(":")) {
                ID_MAP.put("mytems:" + it.id, it);
            }
        }
        ID_MAP.put("dwarf_axe", DWARVEN_AXE); // legacy
        ID_MAP.put("mytems:dwarf_axe", DWARVEN_AXE); // legacy
        ID_MAP.put("vote:candy", VOTE_CANDY); // legacy
        ID_MAP.put("vote:firework", VOTE_FIREWORK); // legacy
    }

    Mytems(final Function<Mytems, Mytem> ctor) {
        this.ctor = ctor;
        this.id = name().toLowerCase();
        this.material = null;
        this.customModelData = null;
        this.character = (char) 0;
        this.component = Component.empty();
    }

    Mytems(final Function<Mytems, Mytem> ctor, final Material material, final Integer customModelData) {
        this.ctor = ctor;
        this.id = name().toLowerCase();
        this.material = material;
        this.customModelData = customModelData;
        this.character = (char) 0;
        this.component = Component.empty();
    }

    Mytems(final Function<Mytems, Mytem> ctor, final Material material, final Integer customModelData, final char character) {
        this.ctor = ctor;
        this.id = name().toLowerCase();
        this.material = material;
        this.customModelData = customModelData;
        this.character = character;
        this.component = Component.text(character)
            .style(Style.style().font(Key.key("cavetale:default")).color(TextColor.color(0xFFFFFF)));
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
            : mytems.getMytem().createItemStack();
    }

    public static ItemStack deserializeItem(String serialized, Player player) {
        int index = serialized.indexOf("{");
        String id = index >= 0 ? serialized.substring(0, index) : serialized;
        Mytems mytems = forId(id);
        if (mytems == null) return null;
        String tag = index >= 0 ? serialized.substring(index) : null;
        return tag != null
            ? mytems.getMytem().deserializeTag(tag, player)
            : mytems.getMytem().createItemStack();
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

    public ItemStack createItemStack() {
        return getMytem().createItemStack();
    }

    public ItemStack createItemStack(Player player) {
        return getMytem().createItemStack(player);
    }

    public void giveItemStack(Player player, int amount) {
        ItemStack itemStack = createItemStack(player);
        itemStack.setAmount(amount);
        for (ItemStack drop : player.getInventory().addItem(itemStack).values()) {
            player.getWorld().dropItem(player.getEyeLocation(), itemStack).setPickupDelay(0);
        }
    }
}
