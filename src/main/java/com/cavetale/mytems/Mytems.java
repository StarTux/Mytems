package com.cavetale.mytems;

import com.cavetale.mytems.item.ArmorPart;
import com.cavetale.mytems.item.ChristmasToken;
import com.cavetale.mytems.item.DiceItem;
import com.cavetale.mytems.item.DummyMytem;
import com.cavetale.mytems.item.Enderball;
import com.cavetale.mytems.item.Ingredient;
import com.cavetale.mytems.item.KittyCoin;
import com.cavetale.mytems.item.MagicCape;
import com.cavetale.mytems.item.MagicMap;
import com.cavetale.mytems.item.Paintbrush;
import com.cavetale.mytems.item.Toilet;
import com.cavetale.mytems.item.UnicornHorn;
import com.cavetale.mytems.item.WardrobeItem;
import com.cavetale.mytems.item.WeddingRing;
import com.cavetale.mytems.item.acula.BatMask;
import com.cavetale.mytems.item.acula.DrAculaStaff;
import com.cavetale.mytems.item.acula.FlameShield;
import com.cavetale.mytems.item.acula.GhastBow;
import com.cavetale.mytems.item.acula.Stompers;
import com.cavetale.mytems.item.captain.Blunderbuss;
import com.cavetale.mytems.item.captain.CaptainsCutlass;
import com.cavetale.mytems.item.dune.DuneItem;
import com.cavetale.mytems.item.dwarven.DwarvenItem;
import com.cavetale.mytems.item.easter.EasterEgg;
import com.cavetale.mytems.item.easter.EasterGear;
import com.cavetale.mytems.item.easter.EasterToken;
import com.cavetale.mytems.item.font.GlyphItem;
import com.cavetale.mytems.item.halloween.HalloweenCandy;
import com.cavetale.mytems.item.halloween.HalloweenToken2;
import com.cavetale.mytems.item.halloween.HalloweenToken;
import com.cavetale.mytems.item.medieval.GoldenScythe;
import com.cavetale.mytems.item.medieval.WitchBroom;
import com.cavetale.mytems.item.music.HyruleInstrument;
import com.cavetale.mytems.item.music.MusicalInstrument;
import com.cavetale.mytems.item.pocketmob.MobCatcher;
import com.cavetale.mytems.item.pocketmob.PocketMob;
import com.cavetale.mytems.item.santa.SantaBoots;
import com.cavetale.mytems.item.santa.SantaHat;
import com.cavetale.mytems.item.santa.SantaJacket;
import com.cavetale.mytems.item.santa.SantaPants;
import com.cavetale.mytems.item.scarlet.ScarletItem;
import com.cavetale.mytems.item.swampy.SwampyItem;
import com.cavetale.mytems.item.vote.VoteCandy;
import com.cavetale.mytems.item.vote.VoteFirework;
import com.cavetale.mytems.util.Items;
import com.cavetale.worldmarker.item.ItemMarker;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * List of all known Mytems.
 * Unicode characters start at 0xE200.
 */
public enum Mytems {
    // Halloween 2020
    DR_ACULA_STAFF(DrAculaStaff::new, Material.NETHERITE_SWORD, 741302, '\uE220', Category.ACULA),
    FLAME_SHIELD(FlameShield::new, Material.SHIELD, 741303, '\uE234', Category.ACULA),
    STOMPERS(Stompers::new, Material.NETHERITE_BOOTS, 741304, '\uE235', Category.ACULA),
    GHAST_BOW(GhastBow::new, Material.BOW, 741305, '\uE236', Category.ACULA),
    BAT_MASK(BatMask::new, Material.PLAYER_HEAD, 741306, '\uE237', Category.ACULA),
    // Cloud City
    UNICORN_HORN(UnicornHorn::new, Material.END_ROD, 7413003, (char) 0, Category.CLOUD_CITY),
    MAGIC_CAPE(MagicCape::new, Material.ELYTRA, 7413006, '\uE238', Category.CLOUD_CITY),
    // Generic
    KITTY_COIN(KittyCoin::new, Material.PLAYER_HEAD, 7413001, '\uE200', Category.CURRENCY),
    RAINBOW_KITTY_COIN(KittyCoin::new, Material.PLAYER_HEAD, 7413007, '\uE243', Category.CURRENCY),
    // Christmas 2020
    CHRISTMAS_TOKEN(ChristmasToken::new, Category.CHRISTMAS),
    SANTA_HAT(SantaHat::new, Material.PLAYER_HEAD, 7413101, '\uE221', Category.SANTA),
    SANTA_JACKET(SantaJacket::new, Material.LEATHER_CHESTPLATE, 4713102, '\uE222', Category.SANTA),
    SANTA_PANTS(SantaPants::new, Material.LEATHER_LEGGINGS, 4713103, '\uE223', Category.SANTA),
    SANTA_BOOTS(SantaBoots::new, Material.LEATHER_BOOTS, 4713104, '\uE224', Category.SANTA),
    // Dune set
    DUNE_HELMET(DuneItem.Helmet::new, Material.PLAYER_HEAD, 7413201, '\uE225', Category.DUNE),
    DUNE_CHESTPLATE(DuneItem.Chestplate::new, Material.GOLDEN_CHESTPLATE, 7413202, '\uE226', Category.DUNE),
    DUNE_LEGGINGS(DuneItem.Leggings::new, Material.GOLDEN_LEGGINGS, 7413203, '\uE227', Category.DUNE),
    DUNE_BOOTS(DuneItem.Boots::new, Material.GOLDEN_BOOTS, 7413204, '\uE228', Category.DUNE),
    DUNE_DIGGER(DuneItem.Weapon::new, Material.GOLDEN_SHOVEL, 7413205, '\uE229', Category.DUNE),
    // Swampy set
    SWAMPY_HELMET(SwampyItem.Helmet::new, Material.PLAYER_HEAD, 7413301, '\uE22A', Category.SWAMPY),
    SWAMPY_CHESTPLATE(SwampyItem.Chestplate::new, Material.LEATHER_CHESTPLATE, 7413302, '\uE22B', Category.SWAMPY),
    SWAMPY_LEGGINGS(SwampyItem.Leggings::new, Material.LEATHER_LEGGINGS, 7413303, '\uE22C', Category.SWAMPY),
    SWAMPY_BOOTS(SwampyItem.Boots::new, Material.LEATHER_BOOTS, 7413304, '\uE22D', Category.SWAMPY),
    SWAMPY_TRIDENT(SwampyItem.Weapon::new, Material.TRIDENT, 7413305, '\uE22E', Category.SWAMPY),
    // Swampy set
    DWARVEN_HELMET(DwarvenItem.Helmet::new, Material.PLAYER_HEAD, 7413401, '\uE22F', Category.DWARVEN),
    DWARVEN_CHESTPLATE(DwarvenItem.Chestplate::new, Material.IRON_CHESTPLATE, 7413402, '\uE230', Category.DWARVEN),
    DWARVEN_LEGGINGS(DwarvenItem.Leggings::new, Material.IRON_LEGGINGS, 7413403, '\uE231', Category.DWARVEN),
    DWARVEN_BOOTS(DwarvenItem.Boots::new, Material.IRON_BOOTS, 7413404, '\uE232', Category.DWARVEN),
    DWARVEN_AXE(DwarvenItem.Weapon::new, Material.IRON_AXE, 7413405, '\uE233', Category.DWARVEN),
    // Easter 2021
    EASTER_TOKEN(EasterToken::new, Material.PLAYER_HEAD, 345700, '\uE211', Category.CURRENCY),
    BLUE_EASTER_EGG(EasterEgg::new, Material.PLAYER_HEAD, 345701, '\uE212', Category.EASTER_EGG),
    GREEN_EASTER_EGG(EasterEgg::new, Material.PLAYER_HEAD, 345702, '\uE213', Category.EASTER_EGG),
    ORANGE_EASTER_EGG(EasterEgg::new, Material.PLAYER_HEAD, 345703, '\uE214', Category.EASTER_EGG),
    PINK_EASTER_EGG(EasterEgg::new, Material.PLAYER_HEAD, 345704, '\uE215', Category.EASTER_EGG),
    PURPLE_EASTER_EGG(EasterEgg::new, Material.PLAYER_HEAD, 345705, '\uE216', Category.EASTER_EGG),
    YELLOW_EASTER_EGG(EasterEgg::new, Material.PLAYER_HEAD, 345706, '\uE217', Category.EASTER_EGG),
    EASTER_HELMET(EasterGear.Helmet::new, Material.PLAYER_HEAD, 345711, '\uE218', Category.EASTER),
    EASTER_CHESTPLATE(EasterGear.Chestplate::new, Material.LEATHER_CHESTPLATE, 345712, '\uE219', Category.EASTER),
    EASTER_LEGGINGS(EasterGear.Leggings::new, Material.LEATHER_LEGGINGS, 345713, '\uE21A', Category.EASTER),
    EASTER_BOOTS(EasterGear.Boots::new, Material.LEATHER_BOOTS, 345714, '\uE21B', Category.EASTER),
    // Furniture
    TOILET(Toilet::new, Material.CAULDRON, 498101, (char) 0, Category.FURNITURE), // APRIL
    BOSS_CHEST(DummyMytem::new, Material.CHEST, 7413004, (char) 0, Category.FURNITURE),
    OAK_CHAIR(DummyMytem::new, Material.OAK_PLANKS, 135, (char) 0, Category.FURNITURE),
    SPRUCE_CHAIR(DummyMytem::new, Material.SPRUCE_PLANKS, 136, (char) 0, Category.FURNITURE),
    WHITE_ARMCHAIR(DummyMytem::new, Material.WHITE_WOOL, 137, (char) 0, Category.FURNITURE),
    WHITE_SOFA_LEFT(DummyMytem::new, Material.WHITE_WOOL, 138, (char) 0, Category.FURNITURE),
    WHITE_SOFA_RIGHT(DummyMytem::new, Material.WHITE_WOOL, 139, (char) 0, Category.FURNITURE),
    RED_ARMCHAIR(DummyMytem::new, Material.RED_WOOL, 140, (char) 0, Category.FURNITURE),
    RED_SOFA_LEFT(DummyMytem::new, Material.RED_WOOL, 141, (char) 0, Category.FURNITURE),
    RED_SOFA_RIGHT(DummyMytem::new, Material.RED_WOOL, 142, (char) 0, Category.FURNITURE),
    BLACK_ARMCHAIR(DummyMytem::new, Material.RED_WOOL, 143, (char) 0, Category.FURNITURE),
    BLACK_SOFA_LEFT(DummyMytem::new, Material.RED_WOOL, 144, (char) 0, Category.FURNITURE),
    BLACK_SOFA_RIGHT(DummyMytem::new, Material.RED_WOOL, 145, (char) 0, Category.FURNITURE),
    // Utility
    WEDDING_RING(WeddingRing::new, Material.PLAYER_HEAD, 7413002, '\uE21C', Category.FRIENDS),
    MAGIC_MAP(MagicMap::new, Material.FILLED_MAP, 7413005, '\uE21D', Category.UTILITY),
    // Wardrobe
    WHITE_BUNNY_EARS(WardrobeItem::new, Material.IRON_BOOTS, 3919001, (char) 0, Category.WARDROBE_HAT), // EPIC
    RED_LIGHTSABER(WardrobeItem::new, Material.END_ROD, 3919002, (char) 0, Category.WARDROBE_HANDHELD),
    BLUE_LIGHTSABER(WardrobeItem::new, Material.END_ROD, 3919003, (char) 0, Category.WARDROBE_HANDHELD),
    PIRATE_HAT(WardrobeItem::new, Material.BLACK_DYE, 3919004, (char) 0, Category.WARDROBE_HAT),
    COWBOY_HAT(WardrobeItem::new, Material.BROWN_DYE, 3919005, (char) 0, Category.WARDROBE_HAT),
    ANGEL_HALO(WardrobeItem::new, Material.LIGHT_WEIGHTED_PRESSURE_PLATE, 111, (char) 0, Category.WARDROBE_HAT),
    TOP_HAT(WardrobeItem::new, Material.BLACK_WOOL, 203, (char) 0, Category.WARDROBE_HAT),
    // Cat Ears
    BLACK_CAT_EARS(WardrobeItem::new, Material.BLACK_CARPET, 112, (char) 0, Category.CAT_EARS),
    CYAN_CAT_EARS(WardrobeItem::new, Material.CYAN_CARPET, 113, (char) 0, Category.CAT_EARS),
    LIGHT_BLUE_CAT_EARS(WardrobeItem::new, Material.LIGHT_BLUE_CARPET, 114, (char) 0, Category.CAT_EARS),
    LIME_CAT_EARS(WardrobeItem::new, Material.LIME_CARPET, 115, (char) 0, Category.CAT_EARS),
    ORANGE_CAT_EARS(WardrobeItem::new, Material.ORANGE_CARPET, 116, (char) 0, Category.CAT_EARS),
    PINK_CAT_EARS(WardrobeItem::new, Material.PINK_CARPET, 117, (char) 0, Category.CAT_EARS),
    RED_CAT_EARS(WardrobeItem::new, Material.RED_CARPET, 118, (char) 0, Category.CAT_EARS),
    WHITE_CAT_EARS(WardrobeItem::new, Material.WHITE_CARPET, 119, (char) 0, Category.CAT_EARS),
    // Sunglasses
    BLACK_SUNGLASSES(WardrobeItem::new, Material.ANVIL, 120, (char) 0, Category.SUNGLASSES),
    BLUE_SUNGLASSES(WardrobeItem::new, Material.ANVIL, 121, (char) 0, Category.SUNGLASSES),
    CYAN_SUNGLASSES(WardrobeItem::new, Material.ANVIL, 122, (char) 0, Category.SUNGLASSES),
    GRAY_SUNGLASSES(WardrobeItem::new, Material.ANVIL, 123, (char) 0, Category.SUNGLASSES),
    GREEN_SUNGLASSES(WardrobeItem::new, Material.ANVIL, 124, (char) 0, Category.SUNGLASSES),
    LIGHT_BLUE_SUNGLASSES(WardrobeItem::new, Material.ANVIL, 125, (char) 0, Category.SUNGLASSES),
    LIGHT_GRAY_SUNGLASSES(WardrobeItem::new, Material.ANVIL, 126, (char) 0, Category.SUNGLASSES),
    LIME_SUNGLASSES(WardrobeItem::new, Material.ANVIL, 127, (char) 0, Category.SUNGLASSES),
    MAGENTA_SUNGLASSES(WardrobeItem::new, Material.ANVIL, 128, (char) 0, Category.SUNGLASSES),
    PURPLE_SUNGLASSES(WardrobeItem::new, Material.ANVIL, 129, (char) 0, Category.SUNGLASSES),
    ORANGE_SUNGLASSES(WardrobeItem::new, Material.ANVIL, 130, (char) 0, Category.SUNGLASSES),
    PINK_SUNGLASSES(WardrobeItem::new, Material.ANVIL, 131, (char) 0, Category.SUNGLASSES),
    RED_SUNGLASSES(WardrobeItem::new, Material.ANVIL, 132, (char) 0, Category.SUNGLASSES),
    WHITE_SUNGLASSES(WardrobeItem::new, Material.ANVIL, 133, (char) 0, Category.SUNGLASSES),
    YELLOW_SUNGLASSES(WardrobeItem::new, Material.ANVIL, 134, (char) 0, Category.SUNGLASSES),
    // November 2021
    GOLDEN_CROWN(WardrobeItem::new, Material.GOLD_INGOT, 146, (char) 0, Category.WARDROBE_HAT),
    DEVIL_HORNS(WardrobeItem::new, Material.NETHERITE_LEGGINGS, 147, (char) 0, Category.WARDROBE_HAT),
    ELF_HAT(WardrobeItem::new, Material.RED_WOOL, 148, (char) 0, Category.WARDROBE_HAT),
    FIREMAN_HELMET(WardrobeItem::new, Material.RED_CONCRETE, 149, (char) 0, Category.WARDROBE_HAT),
    PLAGUE_DOCTOR(WardrobeItem::new, Material.BLACK_CONCRETE, 150, (char) 0, Category.WARDROBE_HAT),
    PLAGUE_DOCTOR_2(WardrobeItem::new, Material.BLACK_CONCRETE, 151, (char) 0, Category.WARDROBE_HAT),
    PUMPKIN_STUB(WardrobeItem::new, Material.SEA_PICKLE, 152, (char) 0, Category.WARDROBE_HAT),
    STOCKING_CAP(WardrobeItem::new, Material.RED_WOOL, 153, (char) 0, Category.WARDROBE_HAT),
    STRAW_HAT(WardrobeItem::new, Material.HAY_BLOCK, 154, (char) 0, Category.WARDROBE_HAT),
    // Witch Hats
    WHITE_WITCH_HAT(WardrobeItem::new, Material.WHITE_SHULKER_BOX, 155, (char) 0, Category.WITCH_HAT),
    ORANGE_WITCH_HAT(WardrobeItem::new, Material.ORANGE_SHULKER_BOX, 162, (char) 0, Category.WITCH_HAT),
    MAGENTA_WITCH_HAT(WardrobeItem::new, Material.MAGENTA_SHULKER_BOX, 163, (char) 0, Category.WITCH_HAT),
    LIGHT_BLUE_WITCH_HAT(WardrobeItem::new, Material.LIGHT_BLUE_SHULKER_BOX, 164, (char) 0, Category.WITCH_HAT),
    YELLOW_WITCH_HAT(WardrobeItem::new, Material.YELLOW_SHULKER_BOX, 165, (char) 0, Category.WITCH_HAT),
    LIME_WITCH_HAT(WardrobeItem::new, Material.LIME_SHULKER_BOX, 166, (char) 0, Category.WITCH_HAT),
    PINK_WITCH_HAT(WardrobeItem::new, Material.PINK_SHULKER_BOX, 167, (char) 0, Category.WITCH_HAT),
    GRAY_WITCH_HAT(WardrobeItem::new, Material.GRAY_SHULKER_BOX, 168, (char) 0, Category.WITCH_HAT),
    LIGHT_GRAY_WITCH_HAT(WardrobeItem::new, Material.LIGHT_GRAY_SHULKER_BOX, 169, (char) 0, Category.WITCH_HAT),
    CYAN_WITCH_HAT(WardrobeItem::new, Material.CYAN_SHULKER_BOX, 170, (char) 0, Category.WITCH_HAT),
    PURPLE_WITCH_HAT(WardrobeItem::new, Material.PURPLE_SHULKER_BOX, 171, (char) 0, Category.WITCH_HAT),
    BLUE_WITCH_HAT(WardrobeItem::new, Material.BLUE_SHULKER_BOX, 172, (char) 0, Category.WITCH_HAT),
    BROWN_WITCH_HAT(WardrobeItem::new, Material.BROWN_SHULKER_BOX, 173, (char) 0, Category.WITCH_HAT),
    GREEN_WITCH_HAT(WardrobeItem::new, Material.GREEN_SHULKER_BOX, 174, (char) 0, Category.WITCH_HAT),
    RED_WITCH_HAT(WardrobeItem::new, Material.RED_SHULKER_BOX, 175, (char) 0, Category.WITCH_HAT),
    BLACK_WITCH_HAT(WardrobeItem::new, Material.BLACK_SHULKER_BOX, 176, (char) 0, Category.WITCH_HAT),
    // Vote
    VOTE_CANDY(VoteCandy::new, Material.COOKIE, 9073001, '\uE21E', Category.VOTE), // VOTE
    VOTE_FIREWORK(VoteFirework::new, Material.FIREWORK_ROCKET, 9073002, '\uE21F', Category.VOTE),
    // Maypole
    LUCID_LILY(Ingredient::new, Material.AZURE_BLUET, 849001, '\uE201', Category.MAYPOLE),
    PINE_CONE(Ingredient::new, Material.SPRUCE_SAPLING, 849002, '\uE202', Category.MAYPOLE),
    ORANGE_ONION(Ingredient::new, Material.ORANGE_TULIP, 849003, '\uE203', Category.MAYPOLE),
    MISTY_MOREL(Ingredient::new, Material.WARPED_FUNGUS, 849004, '\uE204', Category.MAYPOLE),
    RED_ROSE(Ingredient::new, Material.POPPY, 849005, '\uE205', Category.MAYPOLE),
    FROST_FLOWER(Ingredient::new, Material.BLUE_ORCHID, 849006, '\uE206', Category.MAYPOLE),
    HEAT_ROOT(Ingredient::new, Material.DEAD_BUSH, 849007, '\uE207', Category.MAYPOLE),
    CACTUS_BLOSSOM(Ingredient::new, Material.CACTUS, 849008, '\uE208', Category.MAYPOLE),
    PIPE_WEED(Ingredient::new, Material.FERN, 849009, '\uE209', Category.MAYPOLE),
    KINGS_PUMPKIN(Ingredient::new, Material.CARVED_PUMPKIN, 849010, '\uE20A', Category.MAYPOLE),
    SPARK_SEED(Ingredient::new, Material.BEETROOT_SEEDS, 849011, '\uE20B', Category.MAYPOLE),
    OASIS_WATER(Ingredient::new, Material.LIGHT_BLUE_DYE, 849012, '\uE20C', Category.MAYPOLE),
    CLAMSHELL(Ingredient::new, Material.NAUTILUS_SHELL, 849013, '\uE20D', Category.MAYPOLE),
    FROZEN_AMBER(Ingredient::new, Material.EMERALD, 849014, '\uE20E', Category.MAYPOLE),
    CLUMP_OF_MOSS(Ingredient::new, Material.VINE, 849015, '\uE20F', Category.MAYPOLE),
    FIRE_AMANITA(Ingredient::new, Material.CRIMSON_FUNGUS, 849016, '\uE210', Category.MAYPOLE),
    // Enderball
    ENDERBALL(Enderball::new, Material.DRAGON_EGG, (Integer) null, (char) 0, Category.UTILITY),
    // PocketMob
    POCKET_AXOLOTL(PocketMob::new, Material.AXOLOTL_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_BAT(PocketMob::new, Material.BAT_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_BEE(PocketMob::new, Material.BEE_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_BLAZE(PocketMob::new, Material.BLAZE_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_CAT(PocketMob::new, Material.CAT_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_CAVE_SPIDER(PocketMob::new, Material.CAVE_SPIDER_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_CHICKEN(PocketMob::new, Material.CHICKEN_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_COD(PocketMob::new, Material.COD_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_COW(PocketMob::new, Material.COW_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_CREEPER(PocketMob::new, Material.CREEPER_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_DOLPHIN(PocketMob::new, Material.DOLPHIN_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_DONKEY(PocketMob::new, Material.DONKEY_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_DROWNED(PocketMob::new, Material.DROWNED_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_ELDER_GUARDIAN(PocketMob::new, Material.ELDER_GUARDIAN_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_ENDERMAN(PocketMob::new, Material.ENDERMAN_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_ENDERMITE(PocketMob::new, Material.ENDERMITE_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_ENDER_DRAGON(PocketMob::new, Material.ENDERMAN_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_EVOKER(PocketMob::new, Material.EVOKER_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_FOX(PocketMob::new, Material.FOX_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_GHAST(PocketMob::new, Material.GHAST_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_GIANT(PocketMob::new, Material.ZOMBIE_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_GLOW_SQUID(PocketMob::new, Material.GLOW_SQUID_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_GOAT(PocketMob::new, Material.GOAT_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_GUARDIAN(PocketMob::new, Material.GUARDIAN_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_HOGLIN(PocketMob::new, Material.HOGLIN_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_HORSE(PocketMob::new, Material.HORSE_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_HUSK(PocketMob::new, Material.HUSK_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_ILLUSIONER(PocketMob::new, Material.VINDICATOR_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_IRON_GOLEM(PocketMob::new, Material.WOLF_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_LLAMA(PocketMob::new, Material.LLAMA_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_MAGMA_CUBE(PocketMob::new, Material.MAGMA_CUBE_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_MULE(PocketMob::new, Material.MULE_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_MUSHROOM_COW(PocketMob::new, Material.MOOSHROOM_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_OCELOT(PocketMob::new, Material.OCELOT_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_PANDA(PocketMob::new, Material.PANDA_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_PARROT(PocketMob::new, Material.PARROT_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_PHANTOM(PocketMob::new, Material.PHANTOM_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_PIG(PocketMob::new, Material.PIG_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_PIGLIN(PocketMob::new, Material.PIGLIN_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_PIGLIN_BRUTE(PocketMob::new, Material.PIGLIN_BRUTE_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_PILLAGER(PocketMob::new, Material.PILLAGER_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_POLAR_BEAR(PocketMob::new, Material.POLAR_BEAR_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_PUFFERFISH(PocketMob::new, Material.PUFFERFISH_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_RABBIT(PocketMob::new, Material.RABBIT_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_RAVAGER(PocketMob::new, Material.RAVAGER_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_SALMON(PocketMob::new, Material.SALMON_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_SHEEP(PocketMob::new, Material.SHEEP_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_SHULKER(PocketMob::new, Material.SHULKER_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_SILVERFISH(PocketMob::new, Material.SILVERFISH_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_SKELETON(PocketMob::new, Material.SKELETON_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_SKELETON_HORSE(PocketMob::new, Material.SKELETON_HORSE_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_SLIME(PocketMob::new, Material.SLIME_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_SNOWMAN(PocketMob::new, Material.POLAR_BEAR_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_SPIDER(PocketMob::new, Material.SPIDER_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_SQUID(PocketMob::new, Material.SQUID_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_STRAY(PocketMob::new, Material.STRAY_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_STRIDER(PocketMob::new, Material.STRIDER_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_TRADER_LLAMA(PocketMob::new, Material.TRADER_LLAMA_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_TROPICAL_FISH(PocketMob::new, Material.TROPICAL_FISH_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_TURTLE(PocketMob::new, Material.TURTLE_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_VEX(PocketMob::new, Material.VEX_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_VILLAGER(PocketMob::new, Material.VILLAGER_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_VINDICATOR(PocketMob::new, Material.VINDICATOR_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_WANDERING_TRADER(PocketMob::new, Material.WANDERING_TRADER_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_WITCH(PocketMob::new, Material.WITCH_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_WITHER(PocketMob::new, Material.WITHER_SKELETON_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_WITHER_SKELETON(PocketMob::new, Material.WITHER_SKELETON_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_WOLF(PocketMob::new, Material.WOLF_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_ZOGLIN(PocketMob::new, Material.ZOGLIN_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_ZOMBIE(PocketMob::new, Material.ZOMBIE_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_ZOMBIE_HORSE(PocketMob::new, Material.ZOMBIE_HORSE_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_ZOMBIE_VILLAGER(PocketMob::new, Material.ZOMBIE_VILLAGER_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    POCKET_ZOMBIFIED_PIGLIN(PocketMob::new, Material.ZOMBIFIED_PIGLIN_SPAWN_EGG, 908301, (char) 0, Category.POCKET_MOB),
    // Mob Catcher
    MOB_CATCHER(MobCatcher::new, Material.EGG, 908302, '\uE24E', Category.MOB_CATCHER),
    MONSTER_CATCHER(MobCatcher::new, Material.EGG, 908303, '\uE24F', Category.MOB_CATCHER),
    ANIMAL_CATCHER(MobCatcher::new, Material.EGG, 908304, '\uE250', Category.MOB_CATCHER),
    VILLAGER_CATCHER(MobCatcher::new, Material.EGG, 908305, '\uE251', Category.MOB_CATCHER),
    FISH_CATCHER(MobCatcher::new, Material.EGG, 908306, '\uE252', Category.MOB_CATCHER),
    PET_CATCHER(MobCatcher::new, Material.EGG, 908307, '\uE253', Category.MOB_CATCHER),
    // Pirate
    CAPTAINS_CUTLASS(CaptainsCutlass::new, Material.WOODEN_SWORD, 2, '\uE239', Category.PIRATE),
    BLUNDERBUSS(Blunderbuss::new, Material.IRON_INGOT, 3, '\uE23A', Category.PIRATE),
    GOLDEN_SCYTHE(GoldenScythe::new, Material.GOLDEN_HOE, 4, '\uE23B', Category.UTILITY),
    WITCH_BROOM(WitchBroom::new, Material.WOODEN_SHOVEL, 51, '\uE273', Category.UTILITY),
    // Musical Instruments
    OCARINA_OF_CHIME(HyruleInstrument::new, Material.NAUTILUS_SHELL, 36, '\uE264', Category.MUSIC_HYRULE),
    GOLDEN_BANJO(HyruleInstrument::new, Material.WOODEN_SHOVEL, 41, '\uE269', Category.MUSIC_HYRULE),
    PAN_FLUTE(MusicalInstrument::new, Material.STICK, 43, '\uE26B', Category.MUSIC), // Flute
    TRIANGLE(MusicalInstrument::new, Material.STICK, 44, '\uE26C', Category.MUSIC), // Chime
    WOODEN_DRUM(MusicalInstrument::new, Material.STICK, 45, '\uE26D', Category.MUSIC), // Bass Drum
    WOODEN_LUTE(MusicalInstrument::new, Material.STICK, 46, '\uE26E', Category.MUSIC), // Guitar
    WOODEN_OCARINA(MusicalInstrument::new, Material.STICK, 47, '\uE26F', Category.MUSIC), // Flute
    BANJO(MusicalInstrument::new, Material.STICK, 48, '\uE270', Category.MUSIC), // Banjo
    BIT_BOY(MusicalInstrument::new, Material.STICK, 49, '\uE271', Category.MUSIC), // Bit
    GUITAR(MusicalInstrument::new, Material.STICK, 50, '\uE272', Category.MUSIC), // Guitar
    WOODEN_HORN(MusicalInstrument::new, Material.STICK, 55, '\uE277', Category.MUSIC), // Didgeridoo
    MUSICAL_BELL(MusicalInstrument::new, Material.STICK, 56, '\uE278', Category.MUSIC), // Bell
    COW_BELL(MusicalInstrument::new, Material.STICK, 58, '\uE27A', Category.MUSIC), // Cow Bell
    RAINBOW_XYLOPHONE(MusicalInstrument::new, Material.STICK, 59, '\uE27B', Category.MUSIC), // Xylophone
    ELECTRIC_GUITAR(MusicalInstrument::new, Material.STICK, 60, '\uE27C', Category.MUSIC), // Bass Guitar
    POCKET_PIANO(MusicalInstrument::new, Material.STICK, 61, '\uE27D', Category.MUSIC), // Piano
    ELECTRIC_PIANO(MusicalInstrument::new, Material.STICK, 62, '\uE27E', Category.MUSIC), // Pling
    SNARE_DRUM(MusicalInstrument::new, Material.STICK, 63, '\uE27F', Category.MUSIC), // Snare Drums
    IRON_XYLOPHONE(MusicalInstrument::new, Material.STICK, 64, '\uE280', Category.MUSIC),
    CLICKS_AND_STICKS(MusicalInstrument::new, Material.STICK, 66, '\uE282', Category.MUSIC), // Sticks
    ANGELIC_HARP(MusicalInstrument::new, Material.STICK, 67, '\uE283', Category.MUSIC), // Piano
    // Enemy
    KOBOLD_HEAD(DummyMytem::new, Material.GREEN_CONCRETE, 1, (char) 0, Category.ENEMY),
    // Random
    RUBY(DummyMytem::new, Material.EMERALD, 6, '\uE23E', Category.RESOURCE),
    // UI
    OK(DummyMytem::new, Material.BLUE_CONCRETE, 7, '\uE23F', Category.UI),
    NO(DummyMytem::new, Material.RED_CONCRETE, 8, '\uE240', Category.UI),
    ON(DummyMytem::new, Material.ENDER_EYE, 210, (char) 210, Category.UI),
    OFF(DummyMytem::new, Material.ENDER_PEARL, 211, (char) 211, Category.UI),
    REDO(DummyMytem::new, Material.EGG, 212, (char) 212, Category.UI),
    DICE(DiceItem::new, Material.PLAYER_HEAD, 213, (char) 213, Category.UTILITY),
    HEART(DummyMytem::new, Material.HEART_OF_THE_SEA, 9, '\uE241', Category.UI),
    HALF_HEART(DummyMytem::new, Material.NAUTILUS_SHELL, 10, '\uE242', Category.UI),
    EMPTY_HEART(DummyMytem::new, Material.NAUTILUS_SHELL, 13, '\uE246', Category.UI),
    ARROW_RIGHT(DummyMytem::new, Material.ARROW, 11, '\uE244', Category.UI),
    ARROW_LEFT(DummyMytem::new, Material.ARROW, 12, '\uE245', Category.UI),
    ARROW_UP(DummyMytem::new, Material.ARROW, 37, '\uE265', Category.UI),
    ARROW_DOWN(DummyMytem::new, Material.ARROW, 38, '\uE266', Category.UI),
    CHECKBOX(DummyMytem::new, Material.WHITE_CONCRETE, 14, '\uE247', Category.UI),
    CHECKED_CHECKBOX(DummyMytem::new, Material.GREEN_CONCRETE, 15, '\uE248', Category.UI),
    CROSSED_CHECKBOX(DummyMytem::new, Material.BARRIER, 16, '\uE249', Category.UI),
    STAR(DummyMytem::new, Material.NETHER_STAR, 18, '\uE24B', Category.UI),
    EAGLE(DummyMytem::new, Material.FEATHER, 19, '\uE24C', Category.UI),
    EARTH(DummyMytem::new, Material.ENDER_PEARL, 5, '\uE23D', Category.UI),
    EASTER_EGG(DummyMytem::new, Material.EGG, 345715, '\uE23C', Category.UI),
    TRAFFIC_LIGHT(DummyMytem::new, Material.YELLOW_DYE, 57, '\uE279', Category.UI),
    INVISIBLE_ITEM(DummyMytem::new, Material.LIGHT_GRAY_STAINED_GLASS_PANE, 65, '\uE281', Category.UI),
    PAINT_PALETTE(DummyMytem::new, Material.STICK, 202, (char) 202, Category.UI),
    MOON(DummyMytem::new, Material.YELLOW_DYE, 204, (char) 204, Category.UI),
    // Leters
    LETTER_A(GlyphItem::new, Material.PLAYER_HEAD, 68, '\uE284', Category.LETTER),
    LETTER_B(GlyphItem::new, Material.PLAYER_HEAD, 69, '\uE285', Category.LETTER),
    LETTER_C(GlyphItem::new, Material.PLAYER_HEAD, 70, '\uE286', Category.LETTER),
    LETTER_D(GlyphItem::new, Material.PLAYER_HEAD, 71, '\uE287', Category.LETTER),
    LETTER_E(GlyphItem::new, Material.PLAYER_HEAD, 72, '\uE288', Category.LETTER),
    LETTER_F(GlyphItem::new, Material.PLAYER_HEAD, 73, '\uE289', Category.LETTER),
    LETTER_G(GlyphItem::new, Material.PLAYER_HEAD, 74, '\uE28A', Category.LETTER),
    LETTER_H(GlyphItem::new, Material.PLAYER_HEAD, 75, '\uE28B', Category.LETTER),
    LETTER_I(GlyphItem::new, Material.PLAYER_HEAD, 76, '\uE28C', Category.LETTER),
    LETTER_J(GlyphItem::new, Material.PLAYER_HEAD, 77, '\uE28D', Category.LETTER),
    LETTER_K(GlyphItem::new, Material.PLAYER_HEAD, 78, '\uE28E', Category.LETTER),
    LETTER_L(GlyphItem::new, Material.PLAYER_HEAD, 80, '\uE28F', Category.LETTER),
    LETTER_M(GlyphItem::new, Material.PLAYER_HEAD, 81, '\uE290', Category.LETTER),
    LETTER_N(GlyphItem::new, Material.PLAYER_HEAD, 82, '\uE291', Category.LETTER),
    LETTER_O(GlyphItem::new, Material.PLAYER_HEAD, 83, '\uE292', Category.LETTER),
    LETTER_P(GlyphItem::new, Material.PLAYER_HEAD, 84, '\uE293', Category.LETTER),
    LETTER_Q(GlyphItem::new, Material.PLAYER_HEAD, 85, '\uE294', Category.LETTER),
    LETTER_R(GlyphItem::new, Material.PLAYER_HEAD, 86, '\uE295', Category.LETTER),
    LETTER_S(GlyphItem::new, Material.PLAYER_HEAD, 87, '\uE296', Category.LETTER),
    LETTER_T(GlyphItem::new, Material.PLAYER_HEAD, 88, '\uE297', Category.LETTER),
    LETTER_U(GlyphItem::new, Material.PLAYER_HEAD, 89, '\uE298', Category.LETTER),
    LETTER_V(GlyphItem::new, Material.PLAYER_HEAD, 90, '\uE299', Category.LETTER),
    LETTER_W(GlyphItem::new, Material.PLAYER_HEAD, 91, '\uE29A', Category.LETTER),
    LETTER_X(GlyphItem::new, Material.PLAYER_HEAD, 92, '\uE29B', Category.LETTER),
    LETTER_Y(GlyphItem::new, Material.PLAYER_HEAD, 93, '\uE29C', Category.LETTER),
    LETTER_Z(GlyphItem::new, Material.PLAYER_HEAD, 94, '\uE29D', Category.LETTER),
    NUMBER_0(GlyphItem::new, Material.PLAYER_HEAD, 95, '\uE29E', Category.NUMBER),
    NUMBER_1(GlyphItem::new, Material.PLAYER_HEAD, 96, '\uE29F', Category.NUMBER),
    NUMBER_2(GlyphItem::new, Material.PLAYER_HEAD, 97, '\uE2A0', Category.NUMBER),
    NUMBER_3(GlyphItem::new, Material.PLAYER_HEAD, 98, '\uE2A1', Category.NUMBER),
    NUMBER_4(GlyphItem::new, Material.PLAYER_HEAD, 99, '\uE2A2', Category.NUMBER),
    NUMBER_5(GlyphItem::new, Material.PLAYER_HEAD, 100, '\uE2A3', Category.NUMBER),
    NUMBER_6(GlyphItem::new, Material.PLAYER_HEAD, 101, '\uE2A4', Category.NUMBER),
    NUMBER_7(GlyphItem::new, Material.PLAYER_HEAD, 102, '\uE2A5', Category.NUMBER),
    NUMBER_8(GlyphItem::new, Material.PLAYER_HEAD, 103, '\uE2A6', Category.NUMBER),
    NUMBER_9(GlyphItem::new, Material.PLAYER_HEAD, 104, '\uE2A7', Category.NUMBER),
    MUSICAL_SHARP(GlyphItem::new, Material.PLAYER_HEAD, 105, '\uE2A8', Category.MUSICAL),
    MUSICAL_FLAT(GlyphItem::new, Material.PLAYER_HEAD, 106, '\uE2A9', Category.MUSICAL),
    EXCLAMATION_MARK(GlyphItem::new, Material.PLAYER_HEAD, 107, '\uE2AA', Category.PUNCTUATION),
    QUESTION_MARK(GlyphItem::new, Material.PLAYER_HEAD, 17, '\uE24A', Category.PUNCTUATION),
    // Reactions
    SURPRISED(DummyMytem::new, Material.SLIME_BALL, 21, '\uE255', Category.REACTION),
    HAPPY(DummyMytem::new, Material.SLIME_BALL, 22, '\uE256', Category.REACTION),
    SOB(DummyMytem::new, Material.SLIME_BALL, 23, '\uE257', Category.REACTION),
    CRY(DummyMytem::new, Material.SLIME_BALL, 24, '\uE258', Category.REACTION),
    COOL(DummyMytem::new, Material.SLIME_BALL, 25, '\uE259', Category.REACTION),
    SMILE(DummyMytem::new, Material.SLIME_BALL, 26, '\uE25A', Category.REACTION),
    SMILE_UPSIDE_DOWN(DummyMytem::new, Material.SLIME_BALL, 27, '\uE25B', Category.REACTION),
    FROWN(DummyMytem::new, Material.SLIME_BALL, 28, '\uE25C', Category.REACTION),
    SILLY(DummyMytem::new, Material.SLIME_BALL, 29, '\uE25D', Category.REACTION),
    CLOWN(DummyMytem::new, Material.SLIME_BALL, 30, '\uE25E', Category.REACTION),
    STARSTRUCK(DummyMytem::new, Material.SLIME_BALL, 31, '\uE25F', Category.REACTION),
    LOVE_EYES(DummyMytem::new, Material.SLIME_BALL, 32, '\uE260', Category.REACTION),
    WINK_SMILE(DummyMytem::new, Material.SLIME_BALL, 33, '\uE261', Category.REACTION),
    MIND_BLOWN(DummyMytem::new, Material.SLIME_BALL, 34, '\uE262', Category.REACTION),
    WINK(DummyMytem::new, Material.SLIME_BALL, 35, '\uE263', Category.REACTION),
    // Pic
    PIC_WOLF(DummyMytem::new, Material.BONE, 39, '\uE267', Category.PICTURE),
    PIC_CAT(DummyMytem::new, Material.STRING, 40, '\uE268', Category.PICTURE),
    // Halloween
    CANDY_CORN(HalloweenCandy::new, Material.CARROT, 42, '\uE26A', Category.HALLOWEEN),
    CHOCOLATE_BAR(HalloweenCandy::new, Material.PUMPKIN_PIE, 52, '\uE274', Category.HALLOWEEN),
    LOLLIPOP(HalloweenCandy::new, Material.COOKIE, 53, '\uE275', Category.HALLOWEEN),
    ORANGE_CANDY(HalloweenCandy::new, Material.COOKIE, 54, '\uE276', Category.HALLOWEEN),
    GOLDEN_CUP(DummyMytem::new, Material.GOLD_NUGGET, 108, '\uE2AB', Category.TREASURE),
    HALLOWEEN_TOKEN(HalloweenToken::new, Material.PUMPKIN, 109, '\uE2AC', Category.HALLOWEEN),
    HALLOWEEN_TOKEN_2(HalloweenToken2::new, Material.JACK_O_LANTERN, 110, '\uE2AD', Category.HALLOWEEN),
    // Scarlet
    SCARLET_HELMET(ScarletItem.Helmet::new, Material.PLAYER_HEAD, 156, (char) 156, Category.SCARLET),
    SCARLET_CHESTPLATE(ScarletItem.Chestplate::new, Material.LEATHER_CHESTPLATE, 157, (char) 157, Category.SCARLET),
    SCARLET_LEGGINGS(ScarletItem.Leggings::new, Material.LEATHER_LEGGINGS, 158, (char) 158, Category.SCARLET),
    SCARLET_BOOTS(ScarletItem.Boots::new, Material.LEATHER_BOOTS, 159, (char) 159, Category.SCARLET),
    SCARLET_SWORD(ScarletItem.Sword::new, Material.NETHERITE_SWORD, 160, (char) 160, Category.SCARLET),
    SCARLET_SHIELD(ScarletItem.Shield::new, Material.SHIELD, 161, (char) 161, Category.SCARLET),
    // Keys
    COPPER_KEY(DummyMytem::new, Material.COPPER_INGOT, 177, (char) 177, Category.KEY),
    SILVER_KEY(DummyMytem::new, Material.IRON_INGOT, 178, (char) 178, Category.KEY),
    GOLDEN_KEY(DummyMytem::new, Material.GOLD_INGOT, 179, (char) 179, Category.KEY),
    COPPER_KEYHOLE(DummyMytem::new, Material.COPPER_BLOCK, 180, (char) 180, Category.KEYHOLE),
    SILVER_KEYHOLE(DummyMytem::new, Material.IRON_BLOCK, 181, (char) 181, Category.KEYHOLE),
    GOLDEN_KEYHOLE(DummyMytem::new, Material.GOLD_BLOCK, 182, (char) 182, Category.KEYHOLE),
    // Coins
    COPPER_COIN(DummyMytem::new, Material.COPPER_INGOT, 183, (char) 183, Category.COIN),
    SILVER_COIN(DummyMytem::new, Material.IRON_INGOT, 184, (char) 184, Category.COIN),
    GOLDEN_COIN(DummyMytem::new, Material.GOLD_INGOT, 185, (char) 185, Category.COIN),
    // Paintbrush
    BLACK_PAINTBRUSH(Paintbrush::new, Material.WOODEN_SHOVEL, 186, (char) 186, Category.PAINTBRUSH),
    RED_PAINTBRUSH(Paintbrush::new, Material.WOODEN_SHOVEL, 187, (char) 187, Category.PAINTBRUSH),
    GREEN_PAINTBRUSH(Paintbrush::new, Material.WOODEN_SHOVEL, 188, (char) 188, Category.PAINTBRUSH),
    BROWN_PAINTBRUSH(Paintbrush::new, Material.WOODEN_SHOVEL, 189, (char) 189, Category.PAINTBRUSH),
    BLUE_PAINTBRUSH(Paintbrush::new, Material.WOODEN_SHOVEL, 190, (char) 190, Category.PAINTBRUSH),
    PURPLE_PAINTBRUSH(Paintbrush::new, Material.WOODEN_SHOVEL, 191, (char) 191, Category.PAINTBRUSH),
    CYAN_PAINTBRUSH(Paintbrush::new, Material.WOODEN_SHOVEL, 192, (char) 192, Category.PAINTBRUSH),
    LIGHT_GRAY_PAINTBRUSH(Paintbrush::new, Material.WOODEN_SHOVEL, 193, (char) 193, Category.PAINTBRUSH),
    GRAY_PAINTBRUSH(Paintbrush::new, Material.WOODEN_SHOVEL, 194, (char) 194, Category.PAINTBRUSH),
    PINK_PAINTBRUSH(Paintbrush::new, Material.WOODEN_SHOVEL, 195, (char) 195, Category.PAINTBRUSH),
    LIME_PAINTBRUSH(Paintbrush::new, Material.WOODEN_SHOVEL, 196, (char) 196, Category.PAINTBRUSH),
    YELLOW_PAINTBRUSH(Paintbrush::new, Material.WOODEN_SHOVEL, 197, (char) 197, Category.PAINTBRUSH),
    LIGHT_BLUE_PAINTBRUSH(Paintbrush::new, Material.WOODEN_SHOVEL, 198, (char) 198, Category.PAINTBRUSH),
    MAGENTA_PAINTBRUSH(Paintbrush::new, Material.WOODEN_SHOVEL, 199, (char) 199, Category.PAINTBRUSH),
    ORANGE_PAINTBRUSH(Paintbrush::new, Material.WOODEN_SHOVEL, 200, (char) 200, Category.PAINTBRUSH),
    WHITE_PAINTBRUSH(Paintbrush::new, Material.WOODEN_SHOVEL, 201, (char) 201, Category.PAINTBRUSH),
    // Armor Parts
    RUSTY_BUCKET(ArmorPart::new, Material.BUCKET, 205, (char) 205, Category.ARMOR_PART),
    OLD_OVEN_LID(ArmorPart::new, Material.NETHERITE_SCRAP, 207, (char) 207, Category.ARMOR_PART),
    BRITTLE_BARREL(ArmorPart::new, Material.BARREL, 206, (char) 206, Category.ARMOR_PART),
    FLOTSAM_CAN(ArmorPart::new, Material.FLOWER_POT, 208, (char) 208, Category.ARMOR_PART),
    BENT_PITCHFORK(ArmorPart::new, Material.LIGHTNING_ROD, 209, (char) 209, Category.ARMOR_PART);
    // Next CustomModelData: 214
    // Next High Unicode Character: \uE2AE

    private static final Map<String, Mytems> ID_MAP = new HashMap<>();
    public final String id;
    public final Function<Mytems, Mytem> ctor;
    public final Material material;
    public final Integer customModelData;
    public final char character;
    public final Component component;
    public final Category category;

    public enum Category {
        ACULA,
        ARMOR_PART,
        CAT_EARS,
        CHRISTMAS,
        CLOUD_CITY,
        COIN,
        CURRENCY,
        DUNE,
        DWARVEN,
        EASTER,
        EASTER_EGG,
        ENEMY,
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
        MUSIC_HYRULE,
        NUMBER,
        PAINTBRUSH,
        PICTURE,
        PIRATE,
        POCKET_MOB,
        PUNCTUATION,
        REACTION,
        RESOURCE,
        SANTA,
        SCARLET,
        SUNGLASSES,
        SWAMPY,
        TREASURE,
        UI,
        UTILITY,
        VOTE,
        WARDROBE,
        WARDROBE_HANDHELD,
        WARDROBE_HAT,
        WITCH_HAT,
        NEW,
        UNKNOWN;
    }

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
        ID_MAP.put("corroded_drum", OLD_OVEN_LID);
    }

    Mytems(final Function<Mytems, Mytem> ctor, final Material material, final Integer customModelData, final char character, final Category category) {
        this.ctor = ctor;
        this.id = name().toLowerCase();
        this.material = material;
        this.customModelData = customModelData;
        this.character = character;
        this.component = character > 0
            ? (Component.text(character)
               .style(Style.style()
                      .font(Key.key("cavetale:default"))
                      .color(NamedTextColor.WHITE)))
            : Component.empty();
        this.category = category;
    }

    Mytems(final Function<Mytems, Mytem> ctor, final Material material, final Integer customModelData, final char character) {
        this(ctor, material, customModelData, character, Category.UNKNOWN);
    }

    Mytems(final Function<Mytems, Mytem> ctor, final Material material, final Integer customModelData) {
        this(ctor, material, customModelData, (char) 0, Category.UNKNOWN);
    }

    Mytems(final Function<Mytems, Mytem> ctor, final Category category) {
        this(ctor, (Material) null, (Integer) null, (char) 0, category);
    }

    Mytems(final Function<Mytems, Mytem> ctor) {
        this(ctor, (Material) null, (Integer) null, (char) 0, Category.UNKNOWN);
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

    public static String serializeMytem(ItemStack item) {
        Mytems mytems = Mytems.forItem(item);
        return mytems != null
            ? mytems.serializeItem(item)
            : null;
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

    @Deprecated
    public static ItemStack deserializeItem(String serialized, Player player) {
        return deserializeItem(serialized);
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

    public ItemStack createItemStack(int amount) {
        ItemStack itemStack = getMytem().createItemStack();
        itemStack.setAmount(amount);
        return itemStack;
    }

    @Deprecated
    public ItemStack createItemStack(Player player) {
        return getMytem().createItemStack();
    }

    public void giveItemStack(Player player, int amount) {
        ItemStack itemStack = createItemStack(amount);
        for (ItemStack drop : player.getInventory().addItem(itemStack).values()) {
            player.getWorld().dropItem(player.getEyeLocation(), itemStack).setPickupDelay(0);
        }
    }

    /**
     * Create an item with the looks of the Mytem, but none of the
     * stats, id, or lore attached.
     */
    public ItemStack createIcon() {
        final ItemStack item;
        if (material == Material.PLAYER_HEAD) {
            item = Items.text(createItemStack(), List.of());
        } else {
            item = new ItemStack(material);
        }
        item.editMeta(meta -> meta.setCustomModelData(customModelData));
        return item;
    }

    public ItemStack createIcon(List<Component> text) {
        return Items.text(createIcon(), text);
    }
}
