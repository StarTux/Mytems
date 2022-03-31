package com.cavetale.mytems;

import com.cavetale.core.util.Json;
import com.cavetale.mytems.item.ArmorPart;
import com.cavetale.mytems.item.ArmorStandEditor;
import com.cavetale.mytems.item.ChristmasToken;
import com.cavetale.mytems.item.DiceItem;
import com.cavetale.mytems.item.DummyMytem;
import com.cavetale.mytems.item.Enderball;
import com.cavetale.mytems.item.Ingredient;
import com.cavetale.mytems.item.KittyCoin;
import com.cavetale.mytems.item.MagicCape;
import com.cavetale.mytems.item.MagicMap;
import com.cavetale.mytems.item.Paintbrush;
import com.cavetale.mytems.item.SnowShovel;
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
import com.cavetale.mytems.item.coin.Coin;
import com.cavetale.mytems.item.dune.DuneItem;
import com.cavetale.mytems.item.dwarven.DwarvenItem;
import com.cavetale.mytems.item.easter.EasterEgg;
import com.cavetale.mytems.item.easter.EasterGear;
import com.cavetale.mytems.item.easter.EasterToken;
import com.cavetale.mytems.item.farawaymap.FarawayMap;
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
import com.cavetale.mytems.item.potion.PotionFlask;
import com.cavetale.mytems.item.santa.SantaBoots;
import com.cavetale.mytems.item.santa.SantaHat;
import com.cavetale.mytems.item.santa.SantaJacket;
import com.cavetale.mytems.item.santa.SantaPants;
import com.cavetale.mytems.item.scarlet.ScarletItem;
import com.cavetale.mytems.item.swampy.SwampyItem;
import com.cavetale.mytems.item.tree.TreeSeed;
import com.cavetale.mytems.item.treechopper.TreeChopper;
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
import static com.cavetale.mytems.MytemsCategory.*;
import static org.bukkit.Material.*;

/**
 * List of all known Mytems.
 * Unicode characters start at 0xE200.
 */
public enum Mytems {
    // Halloween 2020
    DR_ACULA_STAFF(DrAculaStaff::new, NETHERITE_SWORD, 741302, (char) 0xE220, ACULA),
    FLAME_SHIELD(FlameShield::new, SHIELD, 741303, (char) 0xE234, ACULA),
    STOMPERS(Stompers::new, NETHERITE_BOOTS, 741304, (char) 0xE235, ACULA),
    GHAST_BOW(GhastBow::new, BOW, 741305, (char) 0xE236, ACULA),
    BAT_MASK(BatMask::new, PLAYER_HEAD, 741306, (char) 0xE237, ACULA),
    // Cloud City
    UNICORN_HORN(UnicornHorn::new, END_ROD, 7413003, (char) 0, CLOUD_CITY),
    MAGIC_CAPE(MagicCape::new, ELYTRA, 7413006, (char) 0xE238, CLOUD_CITY),
    // Generic
    KITTY_COIN(KittyCoin::new, PLAYER_HEAD, 7413001, (char) 0xE200, CURRENCY),
    RAINBOW_KITTY_COIN(KittyCoin::new, PLAYER_HEAD, 7413007, (char) 0xE243, CURRENCY),
    // Christmas
    CHRISTMAS_TOKEN(ChristmasToken::new, PLAYER_HEAD, 221, CHRISTMAS),
    SANTA_HAT(SantaHat::new, PLAYER_HEAD, 7413101, (char) 0xE221, SANTA),
    SANTA_JACKET(SantaJacket::new, LEATHER_CHESTPLATE, 4713102, (char) 0xE222, SANTA),
    SANTA_PANTS(SantaPants::new, LEATHER_LEGGINGS, 4713103, (char) 0xE223, SANTA),
    SANTA_BOOTS(SantaBoots::new, LEATHER_BOOTS, 4713104, (char) 0xE224, SANTA),
    BLUE_CHRISTMAS_BALL(DummyMytem::new, BLUE_STAINED_GLASS, 214, CHRISTMAS),
    GREEN_CHRISTMAS_BALL(DummyMytem::new, GREEN_STAINED_GLASS, 215, CHRISTMAS),
    ORANGE_CHRISTMAS_BALL(DummyMytem::new, ORANGE_STAINED_GLASS, 216, CHRISTMAS),
    PINK_CHRISTMAS_BALL(DummyMytem::new, PINK_STAINED_GLASS, 217, CHRISTMAS),
    PURPLE_CHRISTMAS_BALL(DummyMytem::new, PURPLE_STAINED_GLASS, 218, CHRISTMAS),
    YELLOW_CHRISTMAS_BALL(DummyMytem::new, YELLOW_STAINED_GLASS, 219, CHRISTMAS),
    KNITTED_BEANIE(DummyMytem::new, LEATHER_HELMET, 222, CHRISTMAS),
    // Dune set
    DUNE_HELMET(DuneItem.Helmet::new, PLAYER_HEAD, 7413201, (char) 0xE225, DUNE),
    DUNE_CHESTPLATE(DuneItem.Chestplate::new, GOLDEN_CHESTPLATE, 7413202, (char) 0xE226, DUNE),
    DUNE_LEGGINGS(DuneItem.Leggings::new, GOLDEN_LEGGINGS, 7413203, (char) 0xE227, DUNE),
    DUNE_BOOTS(DuneItem.Boots::new, GOLDEN_BOOTS, 7413204, (char) 0xE228, DUNE),
    DUNE_DIGGER(DuneItem.Weapon::new, GOLDEN_SHOVEL, 7413205, (char) 0xE229, DUNE),
    // Swampy set
    SWAMPY_HELMET(SwampyItem.Helmet::new, PLAYER_HEAD, 7413301, (char) 0xE22A, SWAMPY),
    SWAMPY_CHESTPLATE(SwampyItem.Chestplate::new, LEATHER_CHESTPLATE, 7413302, (char) 0xE22B, SWAMPY),
    SWAMPY_LEGGINGS(SwampyItem.Leggings::new, LEATHER_LEGGINGS, 7413303, (char) 0xE22C, SWAMPY),
    SWAMPY_BOOTS(SwampyItem.Boots::new, LEATHER_BOOTS, 7413304, (char) 0xE22D, SWAMPY),
    SWAMPY_TRIDENT(SwampyItem.Weapon::new, TRIDENT, 7413305, (char) 0xE22E, SWAMPY),
    // Swampy set
    DWARVEN_HELMET(DwarvenItem.Helmet::new, PLAYER_HEAD, 7413401, (char) 0xE22F, DWARVEN),
    DWARVEN_CHESTPLATE(DwarvenItem.Chestplate::new, IRON_CHESTPLATE, 7413402, (char) 0xE230, DWARVEN),
    DWARVEN_LEGGINGS(DwarvenItem.Leggings::new, IRON_LEGGINGS, 7413403, (char) 0xE231, DWARVEN),
    DWARVEN_BOOTS(DwarvenItem.Boots::new, IRON_BOOTS, 7413404, (char) 0xE232, DWARVEN),
    DWARVEN_AXE(DwarvenItem.Weapon::new, IRON_AXE, 7413405, (char) 0xE233, DWARVEN),
    // Easter 2021
    EASTER_TOKEN(EasterToken::new, PLAYER_HEAD, 345700, (char) 0xE211, CURRENCY),
    BLUE_EASTER_EGG(EasterEgg::new, PLAYER_HEAD, 345701, (char) 0xE212, EASTER_EGGS),
    GREEN_EASTER_EGG(EasterEgg::new, PLAYER_HEAD, 345702, (char) 0xE213, EASTER_EGGS),
    ORANGE_EASTER_EGG(EasterEgg::new, PLAYER_HEAD, 345703, (char) 0xE214, EASTER_EGGS),
    PINK_EASTER_EGG(EasterEgg::new, PLAYER_HEAD, 345704, (char) 0xE215, EASTER_EGGS),
    PURPLE_EASTER_EGG(EasterEgg::new, PLAYER_HEAD, 345705, (char) 0xE216, EASTER_EGGS),
    YELLOW_EASTER_EGG(EasterEgg::new, PLAYER_HEAD, 345706, (char) 0xE217, EASTER_EGGS),
    EASTER_HELMET(EasterGear.Helmet::new, PLAYER_HEAD, 345711, (char) 0xE218, EASTER),
    EASTER_CHESTPLATE(EasterGear.Chestplate::new, LEATHER_CHESTPLATE, 345712, (char) 0xE219, EASTER),
    EASTER_LEGGINGS(EasterGear.Leggings::new, LEATHER_LEGGINGS, 345713, (char) 0xE21A, EASTER),
    EASTER_BOOTS(EasterGear.Boots::new, LEATHER_BOOTS, 345714, (char) 0xE21B, EASTER),
    // Furniture
    TOILET(Toilet::new, CAULDRON, 498101, (char) 0, FURNITURE), // APRIL
    BOSS_CHEST(DummyMytem::new, CHEST, 7413004, (char) 0, FURNITURE),
    OAK_CHAIR(DummyMytem::new, OAK_PLANKS, 135, (char) 0, FURNITURE),
    SPRUCE_CHAIR(DummyMytem::new, SPRUCE_PLANKS, 136, (char) 0, FURNITURE),
    WHITE_ARMCHAIR(DummyMytem::new, WHITE_WOOL, 137, (char) 0, FURNITURE),
    WHITE_SOFA_LEFT(DummyMytem::new, WHITE_WOOL, 138, (char) 0, FURNITURE),
    WHITE_SOFA_RIGHT(DummyMytem::new, WHITE_WOOL, 139, (char) 0, FURNITURE),
    RED_ARMCHAIR(DummyMytem::new, RED_WOOL, 140, (char) 0, FURNITURE),
    RED_SOFA_LEFT(DummyMytem::new, RED_WOOL, 141, (char) 0, FURNITURE),
    RED_SOFA_RIGHT(DummyMytem::new, RED_WOOL, 142, (char) 0, FURNITURE),
    BLACK_ARMCHAIR(DummyMytem::new, RED_WOOL, 143, (char) 0, FURNITURE),
    BLACK_SOFA_LEFT(DummyMytem::new, RED_WOOL, 144, (char) 0, FURNITURE),
    BLACK_SOFA_RIGHT(DummyMytem::new, RED_WOOL, 145, (char) 0, FURNITURE),
    // Utility
    WEDDING_RING(WeddingRing::new, PLAYER_HEAD, 7413002, (char) 0xE21C, FRIENDS),
    MAGIC_MAP(MagicMap::new, FILLED_MAP, 7413005, (char) 0xE21D, UTILITY),
    SNOW_SHOVEL(SnowShovel::new, IRON_SHOVEL, 220, UTILITY),
    HASTY_PICKAXE(DummyMytem::new, GOLDEN_PICKAXE, 223, UTILITY),
    TREE_CHOPPER(TreeChopper::new, GOLDEN_AXE, 242, UTILITY),
    ARMOR_STAND_EDITOR(ArmorStandEditor::new, FLINT, 241, UTILITY),
    // Wardrobe
    WHITE_BUNNY_EARS(WardrobeItem::new, IRON_BOOTS, 3919001, (char) 0, WARDROBE_HAT), // EPIC
    RED_LIGHTSABER(WardrobeItem::new, END_ROD, 3919002, (char) 0, WARDROBE_HANDHELD),
    BLUE_LIGHTSABER(WardrobeItem::new, END_ROD, 3919003, (char) 0, WARDROBE_HANDHELD),
    PIRATE_HAT(WardrobeItem::new, BLACK_DYE, 3919004, (char) 0, WARDROBE_HAT),
    COWBOY_HAT(WardrobeItem::new, BROWN_DYE, 3919005, (char) 0, WARDROBE_HAT),
    ANGEL_HALO(WardrobeItem::new, LIGHT_WEIGHTED_PRESSURE_PLATE, 111, (char) 0, WARDROBE_HAT),
    TOP_HAT(WardrobeItem::new, BLACK_WOOL, 203, (char) 0, WARDROBE_HAT),
    // Cat Ears
    BLACK_CAT_EARS(WardrobeItem::new, BLACK_CARPET, 112, (char) 0, CAT_EARS),
    CYAN_CAT_EARS(WardrobeItem::new, CYAN_CARPET, 113, (char) 0, CAT_EARS),
    LIGHT_BLUE_CAT_EARS(WardrobeItem::new, LIGHT_BLUE_CARPET, 114, (char) 0, CAT_EARS),
    LIME_CAT_EARS(WardrobeItem::new, LIME_CARPET, 115, (char) 0, CAT_EARS),
    ORANGE_CAT_EARS(WardrobeItem::new, ORANGE_CARPET, 116, (char) 0, CAT_EARS),
    PINK_CAT_EARS(WardrobeItem::new, PINK_CARPET, 117, (char) 0, CAT_EARS),
    RED_CAT_EARS(WardrobeItem::new, RED_CARPET, 118, (char) 0, CAT_EARS),
    WHITE_CAT_EARS(WardrobeItem::new, WHITE_CARPET, 119, (char) 0, CAT_EARS),
    // Sunglasses
    BLACK_SUNGLASSES(WardrobeItem::new, ANVIL, 120, (char) 0, SUNGLASSES),
    BLUE_SUNGLASSES(WardrobeItem::new, ANVIL, 121, (char) 0, SUNGLASSES),
    CYAN_SUNGLASSES(WardrobeItem::new, ANVIL, 122, (char) 0, SUNGLASSES),
    GRAY_SUNGLASSES(WardrobeItem::new, ANVIL, 123, (char) 0, SUNGLASSES),
    GREEN_SUNGLASSES(WardrobeItem::new, ANVIL, 124, (char) 0, SUNGLASSES),
    LIGHT_BLUE_SUNGLASSES(WardrobeItem::new, ANVIL, 125, (char) 0, SUNGLASSES),
    LIGHT_GRAY_SUNGLASSES(WardrobeItem::new, ANVIL, 126, (char) 0, SUNGLASSES),
    LIME_SUNGLASSES(WardrobeItem::new, ANVIL, 127, (char) 0, SUNGLASSES),
    MAGENTA_SUNGLASSES(WardrobeItem::new, ANVIL, 128, (char) 0, SUNGLASSES),
    PURPLE_SUNGLASSES(WardrobeItem::new, ANVIL, 129, (char) 0, SUNGLASSES),
    ORANGE_SUNGLASSES(WardrobeItem::new, ANVIL, 130, (char) 0, SUNGLASSES),
    PINK_SUNGLASSES(WardrobeItem::new, ANVIL, 131, (char) 0, SUNGLASSES),
    RED_SUNGLASSES(WardrobeItem::new, ANVIL, 132, (char) 0, SUNGLASSES),
    WHITE_SUNGLASSES(WardrobeItem::new, ANVIL, 133, (char) 0, SUNGLASSES),
    YELLOW_SUNGLASSES(WardrobeItem::new, ANVIL, 134, (char) 0, SUNGLASSES),
    // November 2021
    GOLDEN_CROWN(WardrobeItem::new, GOLD_INGOT, 146, (char) 0, WARDROBE_HAT),
    DEVIL_HORNS(WardrobeItem::new, NETHERITE_LEGGINGS, 147, (char) 0, WARDROBE_HAT),
    ELF_HAT(WardrobeItem::new, RED_WOOL, 148, (char) 0, WARDROBE_HAT),
    FIREMAN_HELMET(WardrobeItem::new, RED_CONCRETE, 149, (char) 0, WARDROBE_HAT),
    PLAGUE_DOCTOR(WardrobeItem::new, BLACK_CONCRETE, 150, (char) 0, WARDROBE_HAT),
    PLAGUE_DOCTOR_2(WardrobeItem::new, BLACK_CONCRETE, 151, (char) 0, WARDROBE_HAT),
    PUMPKIN_STUB(WardrobeItem::new, SEA_PICKLE, 152, (char) 0, WARDROBE_HAT),
    STOCKING_CAP(WardrobeItem::new, RED_WOOL, 153, (char) 0, WARDROBE_HAT),
    STRAW_HAT(WardrobeItem::new, HAY_BLOCK, 154, (char) 0, WARDROBE_HAT),
    // Witch Hats
    WHITE_WITCH_HAT(WardrobeItem::new, WHITE_SHULKER_BOX, 155, (char) 0, WITCH_HAT),
    ORANGE_WITCH_HAT(WardrobeItem::new, ORANGE_SHULKER_BOX, 162, (char) 0, WITCH_HAT),
    MAGENTA_WITCH_HAT(WardrobeItem::new, MAGENTA_SHULKER_BOX, 163, (char) 0, WITCH_HAT),
    LIGHT_BLUE_WITCH_HAT(WardrobeItem::new, LIGHT_BLUE_SHULKER_BOX, 164, (char) 0, WITCH_HAT),
    YELLOW_WITCH_HAT(WardrobeItem::new, YELLOW_SHULKER_BOX, 165, (char) 0, WITCH_HAT),
    LIME_WITCH_HAT(WardrobeItem::new, LIME_SHULKER_BOX, 166, (char) 0, WITCH_HAT),
    PINK_WITCH_HAT(WardrobeItem::new, PINK_SHULKER_BOX, 167, (char) 0, WITCH_HAT),
    GRAY_WITCH_HAT(WardrobeItem::new, GRAY_SHULKER_BOX, 168, (char) 0, WITCH_HAT),
    LIGHT_GRAY_WITCH_HAT(WardrobeItem::new, LIGHT_GRAY_SHULKER_BOX, 169, (char) 0, WITCH_HAT),
    CYAN_WITCH_HAT(WardrobeItem::new, CYAN_SHULKER_BOX, 170, (char) 0, WITCH_HAT),
    PURPLE_WITCH_HAT(WardrobeItem::new, PURPLE_SHULKER_BOX, 171, (char) 0, WITCH_HAT),
    BLUE_WITCH_HAT(WardrobeItem::new, BLUE_SHULKER_BOX, 172, (char) 0, WITCH_HAT),
    BROWN_WITCH_HAT(WardrobeItem::new, BROWN_SHULKER_BOX, 173, (char) 0, WITCH_HAT),
    GREEN_WITCH_HAT(WardrobeItem::new, GREEN_SHULKER_BOX, 174, (char) 0, WITCH_HAT),
    RED_WITCH_HAT(WardrobeItem::new, RED_SHULKER_BOX, 175, (char) 0, WITCH_HAT),
    BLACK_WITCH_HAT(WardrobeItem::new, BLACK_SHULKER_BOX, 176, (char) 0, WITCH_HAT),
    // Vote
    VOTE_CANDY(VoteCandy::new, COOKIE, 9073001, (char) 0xE21E, VOTE), // VOTE
    VOTE_FIREWORK(VoteFirework::new, FIREWORK_ROCKET, 9073002, (char) 0xE21F, VOTE),
    // Maypole
    LUCID_LILY(Ingredient::new, AZURE_BLUET, 849001, (char) 0xE201, MAYPOLE),
    PINE_CONE(Ingredient::new, SPRUCE_SAPLING, 849002, (char) 0xE202, MAYPOLE),
    ORANGE_ONION(Ingredient::new, ORANGE_TULIP, 849003, (char) 0xE203, MAYPOLE),
    MISTY_MOREL(Ingredient::new, WARPED_FUNGUS, 849004, (char) 0xE204, MAYPOLE),
    RED_ROSE(Ingredient::new, POPPY, 849005, (char) 0xE205, MAYPOLE),
    FROST_FLOWER(Ingredient::new, BLUE_ORCHID, 849006, (char) 0xE206, MAYPOLE),
    HEAT_ROOT(Ingredient::new, DEAD_BUSH, 849007, (char) 0xE207, MAYPOLE),
    CACTUS_BLOSSOM(Ingredient::new, CACTUS, 849008, (char) 0xE208, MAYPOLE),
    PIPE_WEED(Ingredient::new, FERN, 849009, (char) 0xE209, MAYPOLE),
    KINGS_PUMPKIN(Ingredient::new, CARVED_PUMPKIN, 849010, (char) 0xE20A, MAYPOLE),
    SPARK_SEED(Ingredient::new, BEETROOT_SEEDS, 849011, (char) 0xE20B, MAYPOLE),
    OASIS_WATER(Ingredient::new, LIGHT_BLUE_DYE, 849012, (char) 0xE20C, MAYPOLE),
    CLAMSHELL(Ingredient::new, NAUTILUS_SHELL, 849013, (char) 0xE20D, MAYPOLE),
    FROZEN_AMBER(Ingredient::new, EMERALD, 849014, (char) 0xE20E, MAYPOLE),
    CLUMP_OF_MOSS(Ingredient::new, VINE, 849015, (char) 0xE20F, MAYPOLE),
    FIRE_AMANITA(Ingredient::new, CRIMSON_FUNGUS, 849016, (char) 0xE210, MAYPOLE),
    // Enderball
    ENDERBALL(Enderball::new, DRAGON_EGG, (Integer) null, (char) 0, UTILITY),
    // PocketMob
    POCKET_AXOLOTL(PocketMob::new, AXOLOTL_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_BAT(PocketMob::new, BAT_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_BEE(PocketMob::new, BEE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_BLAZE(PocketMob::new, BLAZE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_CAT(PocketMob::new, CAT_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_CAVE_SPIDER(PocketMob::new, CAVE_SPIDER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_CHICKEN(PocketMob::new, CHICKEN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_COD(PocketMob::new, COD_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_COW(PocketMob::new, COW_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_CREEPER(PocketMob::new, CREEPER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_DOLPHIN(PocketMob::new, DOLPHIN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_DONKEY(PocketMob::new, DONKEY_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_DROWNED(PocketMob::new, DROWNED_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ELDER_GUARDIAN(PocketMob::new, ELDER_GUARDIAN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ENDERMAN(PocketMob::new, ENDERMAN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ENDERMITE(PocketMob::new, ENDERMITE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ENDER_DRAGON(PocketMob::new, ENDERMAN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_EVOKER(PocketMob::new, EVOKER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_FOX(PocketMob::new, FOX_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_GHAST(PocketMob::new, GHAST_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_GIANT(PocketMob::new, ZOMBIE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_GLOW_SQUID(PocketMob::new, GLOW_SQUID_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_GOAT(PocketMob::new, GOAT_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_GUARDIAN(PocketMob::new, GUARDIAN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_HOGLIN(PocketMob::new, HOGLIN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_HORSE(PocketMob::new, HORSE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_HUSK(PocketMob::new, HUSK_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ILLUSIONER(PocketMob::new, VINDICATOR_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_IRON_GOLEM(PocketMob::new, WOLF_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_LLAMA(PocketMob::new, LLAMA_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_MAGMA_CUBE(PocketMob::new, MAGMA_CUBE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_MULE(PocketMob::new, MULE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_MUSHROOM_COW(PocketMob::new, MOOSHROOM_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_OCELOT(PocketMob::new, OCELOT_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_PANDA(PocketMob::new, PANDA_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_PARROT(PocketMob::new, PARROT_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_PHANTOM(PocketMob::new, PHANTOM_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_PIG(PocketMob::new, PIG_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_PIGLIN(PocketMob::new, PIGLIN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_PIGLIN_BRUTE(PocketMob::new, PIGLIN_BRUTE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_PILLAGER(PocketMob::new, PILLAGER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_POLAR_BEAR(PocketMob::new, POLAR_BEAR_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_PUFFERFISH(PocketMob::new, PUFFERFISH_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_RABBIT(PocketMob::new, RABBIT_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_RAVAGER(PocketMob::new, RAVAGER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SALMON(PocketMob::new, SALMON_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SHEEP(PocketMob::new, SHEEP_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SHULKER(PocketMob::new, SHULKER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SILVERFISH(PocketMob::new, SILVERFISH_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SKELETON(PocketMob::new, SKELETON_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SKELETON_HORSE(PocketMob::new, SKELETON_HORSE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SLIME(PocketMob::new, SLIME_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SNOWMAN(PocketMob::new, POLAR_BEAR_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SPIDER(PocketMob::new, SPIDER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SQUID(PocketMob::new, SQUID_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_STRAY(PocketMob::new, STRAY_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_STRIDER(PocketMob::new, STRIDER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_TRADER_LLAMA(PocketMob::new, TRADER_LLAMA_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_TROPICAL_FISH(PocketMob::new, TROPICAL_FISH_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_TURTLE(PocketMob::new, TURTLE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_VEX(PocketMob::new, VEX_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_VILLAGER(PocketMob::new, VILLAGER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_VINDICATOR(PocketMob::new, VINDICATOR_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_WANDERING_TRADER(PocketMob::new, WANDERING_TRADER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_WITCH(PocketMob::new, WITCH_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_WITHER(PocketMob::new, WITHER_SKELETON_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_WITHER_SKELETON(PocketMob::new, WITHER_SKELETON_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_WOLF(PocketMob::new, WOLF_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ZOGLIN(PocketMob::new, ZOGLIN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ZOMBIE(PocketMob::new, ZOMBIE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ZOMBIE_HORSE(PocketMob::new, ZOMBIE_HORSE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ZOMBIE_VILLAGER(PocketMob::new, ZOMBIE_VILLAGER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ZOMBIFIED_PIGLIN(PocketMob::new, ZOMBIFIED_PIGLIN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    // Mob Catcher
    MOB_CATCHER(MobCatcher::new, EGG, 908302, (char) 0xE24E, MOB_CATCHERS),
    MONSTER_CATCHER(MobCatcher::new, EGG, 908303, (char) 0xE24F, MOB_CATCHERS),
    ANIMAL_CATCHER(MobCatcher::new, EGG, 908304, (char) 0xE250, MOB_CATCHERS),
    VILLAGER_CATCHER(MobCatcher::new, EGG, 908305, (char) 0xE251, MOB_CATCHERS),
    FISH_CATCHER(MobCatcher::new, EGG, 908306, (char) 0xE252, MOB_CATCHERS),
    PET_CATCHER(MobCatcher::new, EGG, 908307, (char) 0xE253, MOB_CATCHERS),
    // Pirate
    CAPTAINS_CUTLASS(CaptainsCutlass::new, WOODEN_SWORD, 2, (char) 0xE239, PIRATE),
    BLUNDERBUSS(Blunderbuss::new, IRON_INGOT, 3, (char) 0xE23A, PIRATE),
    GOLDEN_SCYTHE(GoldenScythe::new, GOLDEN_HOE, 4, (char) 0xE23B, UTILITY),
    WITCH_BROOM(WitchBroom::new, WOODEN_SHOVEL, 51, (char) 0xE273, UTILITY),
    // Musical Instruments
    OCARINA_OF_CHIME(HyruleInstrument::new, NAUTILUS_SHELL, 36, (char) 0xE264, MUSIC_HYRULE),
    GOLDEN_BANJO(HyruleInstrument::new, WOODEN_SHOVEL, 41, (char) 0xE269, MUSIC_HYRULE),
    PAN_FLUTE(MusicalInstrument::new, STICK, 43, (char) 0xE26B, MUSIC), // Flute
    TRIANGLE(MusicalInstrument::new, STICK, 44, (char) 0xE26C, MUSIC), // Chime
    WOODEN_DRUM(MusicalInstrument::new, STICK, 45, (char) 0xE26D, MUSIC), // Bass Drum
    WOODEN_LUTE(MusicalInstrument::new, STICK, 46, (char) 0xE26E, MUSIC), // Guitar
    WOODEN_OCARINA(MusicalInstrument::new, STICK, 47, (char) 0xE26F, MUSIC), // Flute
    BANJO(MusicalInstrument::new, STICK, 48, (char) 0xE270, MUSIC), // Banjo
    BIT_BOY(MusicalInstrument::new, STICK, 49, (char) 0xE271, MUSIC), // Bit
    GUITAR(MusicalInstrument::new, STICK, 50, (char) 0xE272, MUSIC), // Guitar
    WOODEN_HORN(MusicalInstrument::new, STICK, 55, (char) 0xE277, MUSIC), // Didgeridoo
    MUSICAL_BELL(MusicalInstrument::new, STICK, 56, (char) 0xE278, MUSIC), // Bell
    COW_BELL(MusicalInstrument::new, STICK, 58, (char) 0xE27A, MUSIC), // Cow Bell
    RAINBOW_XYLOPHONE(MusicalInstrument::new, STICK, 59, (char) 0xE27B, MUSIC), // Xylophone
    ELECTRIC_GUITAR(MusicalInstrument::new, STICK, 60, (char) 0xE27C, MUSIC), // Bass Guitar
    POCKET_PIANO(MusicalInstrument::new, STICK, 61, (char) 0xE27D, MUSIC), // Piano
    ELECTRIC_PIANO(MusicalInstrument::new, STICK, 62, (char) 0xE27E, MUSIC), // Pling
    SNARE_DRUM(MusicalInstrument::new, STICK, 63, (char) 0xE27F, MUSIC), // Snare Drums
    IRON_XYLOPHONE(MusicalInstrument::new, STICK, 64, (char) 0xE280, MUSIC),
    CLICKS_AND_STICKS(MusicalInstrument::new, STICK, 66, (char) 0xE282, MUSIC), // Sticks
    ANGELIC_HARP(MusicalInstrument::new, STICK, 67, (char) 0xE283, MUSIC), // Piano
    // Enemy
    KOBOLD_HEAD(DummyMytem::new, GREEN_CONCRETE, 1, (char) 0, ENEMY),
    // Random
    RUBY(DummyMytem::new, EMERALD, 6, (char) 0xE23E, RESOURCE),
    // UI
    OK(DummyMytem::new, BLUE_CONCRETE, 7, (char) 0xE23F, UI),
    NO(DummyMytem::new, RED_CONCRETE, 8, (char) 0xE240, UI),
    ON(DummyMytem::new, ENDER_EYE, 210, UI),
    OFF(DummyMytem::new, ENDER_PEARL, 211, UI),
    REDO(DummyMytem::new, EGG, 212, UI),
    DICE(DiceItem::new, PLAYER_HEAD, 213, DIE),
    DICE4(DiceItem::new, PRISMARINE_SHARD, 243, DIE),
    DICE8(DiceItem::new, PRISMARINE_SHARD, 244, DIE),
    DICE10(DiceItem::new, PRISMARINE_SHARD, 245, DIE),
    DICE12(DiceItem::new, PRISMARINE_SHARD, 246, DIE),
    DICE20(DiceItem::new, PRISMARINE_SHARD, 247, DIE),
    DICE100(DiceItem::new, PRISMARINE_SHARD, 248, DIE),
    HEART(DummyMytem::new, HEART_OF_THE_SEA, 9, (char) 0xE241, UI),
    HALF_HEART(DummyMytem::new, NAUTILUS_SHELL, 10, (char) 0xE242, UI),
    EMPTY_HEART(DummyMytem::new, NAUTILUS_SHELL, 13, (char) 0xE246, UI),
    ARROW_RIGHT(DummyMytem::new, ARROW, 11, (char) 0xE244, ARROWS),
    ARROW_LEFT(DummyMytem::new, ARROW, 12, (char) 0xE245, ARROWS),
    ARROW_UP(DummyMytem::new, ARROW, 37, (char) 0xE265, ARROWS),
    ARROW_DOWN(DummyMytem::new, ARROW, 38, (char) 0xE266, ARROWS),
    TURN_LEFT(DummyMytem::new, ARROW, 283, ARROWS),
    TURN_RIGHT(DummyMytem::new, ARROW, 284, ARROWS),
    CHECKBOX(DummyMytem::new, WHITE_CONCRETE, 14, (char) 0xE247, UI),
    CHECKED_CHECKBOX(DummyMytem::new, GREEN_CONCRETE, 15, (char) 0xE248, UI),
    CROSSED_CHECKBOX(DummyMytem::new, BARRIER, 16, (char) 0xE249, UI),
    STAR(DummyMytem::new, NETHER_STAR, 18, (char) 0xE24B, UI),
    EAGLE(DummyMytem::new, FEATHER, 19, (char) 0xE24C, UI),
    EARTH(DummyMytem::new, ENDER_PEARL, 5, (char) 0xE23D, UI),
    EASTER_EGG(DummyMytem::new, EGG, 345715, (char) 0xE23C, UI),
    TRAFFIC_LIGHT(DummyMytem::new, YELLOW_DYE, 57, (char) 0xE279, UI),
    INVISIBLE_ITEM(DummyMytem::new, LIGHT_GRAY_STAINED_GLASS_PANE, 65, (char) 0xE281, UI),
    PAINT_PALETTE(DummyMytem::new, STICK, 202, UI),
    MOON(DummyMytem::new, YELLOW_DYE, 204, UI),
    LIGHTNING(DummyMytem::new, LIGHTNING_ROD, 250, UI),
    PLUS_BUTTON(DummyMytem::new, EGG, 266, UI),
    MINUS_BUTTON(DummyMytem::new, SNOWBALL, 267, UI),
    FLOPPY_DISK(DummyMytem::new, MUSIC_DISC_CAT, 268, UI),
    FOLDER(DummyMytem::new, CHEST, 269, UI),
    MAGNET(DummyMytem::new, IRON_NUGGET, 270, UI),
    DATA_INTEGER(DummyMytem::new, REPEATER, 271, UI),
    DATA_STRING(DummyMytem::new, CHAIN, 272, UI),
    DATA_FLOAT(DummyMytem::new, REPEATER, 273, UI),
    BOMB(DummyMytem::new, CHAIN, 274, UI),
    // Leters
    LETTER_A(GlyphItem::new, PLAYER_HEAD, 68, (char) 0xE284, LETTER),
    LETTER_B(GlyphItem::new, PLAYER_HEAD, 69, (char) 0xE285, LETTER),
    LETTER_C(GlyphItem::new, PLAYER_HEAD, 70, (char) 0xE286, LETTER),
    LETTER_D(GlyphItem::new, PLAYER_HEAD, 71, (char) 0xE287, LETTER),
    LETTER_E(GlyphItem::new, PLAYER_HEAD, 72, (char) 0xE288, LETTER),
    LETTER_F(GlyphItem::new, PLAYER_HEAD, 73, (char) 0xE289, LETTER),
    LETTER_G(GlyphItem::new, PLAYER_HEAD, 74, (char) 0xE28A, LETTER),
    LETTER_H(GlyphItem::new, PLAYER_HEAD, 75, (char) 0xE28B, LETTER),
    LETTER_I(GlyphItem::new, PLAYER_HEAD, 76, (char) 0xE28C, LETTER),
    LETTER_J(GlyphItem::new, PLAYER_HEAD, 77, (char) 0xE28D, LETTER),
    LETTER_K(GlyphItem::new, PLAYER_HEAD, 78, (char) 0xE28E, LETTER),
    LETTER_L(GlyphItem::new, PLAYER_HEAD, 80, (char) 0xE28F, LETTER),
    LETTER_M(GlyphItem::new, PLAYER_HEAD, 81, (char) 0xE290, LETTER),
    LETTER_N(GlyphItem::new, PLAYER_HEAD, 82, (char) 0xE291, LETTER),
    LETTER_O(GlyphItem::new, PLAYER_HEAD, 83, (char) 0xE292, LETTER),
    LETTER_P(GlyphItem::new, PLAYER_HEAD, 84, (char) 0xE293, LETTER),
    LETTER_Q(GlyphItem::new, PLAYER_HEAD, 85, (char) 0xE294, LETTER),
    LETTER_R(GlyphItem::new, PLAYER_HEAD, 86, (char) 0xE295, LETTER),
    LETTER_S(GlyphItem::new, PLAYER_HEAD, 87, (char) 0xE296, LETTER),
    LETTER_T(GlyphItem::new, PLAYER_HEAD, 88, (char) 0xE297, LETTER),
    LETTER_U(GlyphItem::new, PLAYER_HEAD, 89, (char) 0xE298, LETTER),
    LETTER_V(GlyphItem::new, PLAYER_HEAD, 90, (char) 0xE299, LETTER),
    LETTER_W(GlyphItem::new, PLAYER_HEAD, 91, (char) 0xE29A, LETTER),
    LETTER_X(GlyphItem::new, PLAYER_HEAD, 92, (char) 0xE29B, LETTER),
    LETTER_Y(GlyphItem::new, PLAYER_HEAD, 93, (char) 0xE29C, LETTER),
    LETTER_Z(GlyphItem::new, PLAYER_HEAD, 94, (char) 0xE29D, LETTER),
    NUMBER_0(GlyphItem::new, PLAYER_HEAD, 95, (char) 0xE29E, NUMBER),
    NUMBER_1(GlyphItem::new, PLAYER_HEAD, 96, (char) 0xE29F, NUMBER),
    NUMBER_2(GlyphItem::new, PLAYER_HEAD, 97, (char) 0xE2A0, NUMBER),
    NUMBER_3(GlyphItem::new, PLAYER_HEAD, 98, (char) 0xE2A1, NUMBER),
    NUMBER_4(GlyphItem::new, PLAYER_HEAD, 99, (char) 0xE2A2, NUMBER),
    NUMBER_5(GlyphItem::new, PLAYER_HEAD, 100, (char) 0xE2A3, NUMBER),
    NUMBER_6(GlyphItem::new, PLAYER_HEAD, 101, (char) 0xE2A4, NUMBER),
    NUMBER_7(GlyphItem::new, PLAYER_HEAD, 102, (char) 0xE2A5, NUMBER),
    NUMBER_8(GlyphItem::new, PLAYER_HEAD, 103, (char) 0xE2A6, NUMBER),
    NUMBER_9(GlyphItem::new, PLAYER_HEAD, 104, (char) 0xE2A7, NUMBER),
    MUSICAL_SHARP(GlyphItem::new, PLAYER_HEAD, 105, (char) 0xE2A8, MUSICAL),
    MUSICAL_FLAT(GlyphItem::new, PLAYER_HEAD, 106, (char) 0xE2A9, MUSICAL),
    EXCLAMATION_MARK(GlyphItem::new, PLAYER_HEAD, 107, (char) 0xE2AA, PUNCTUATION),
    QUESTION_MARK(GlyphItem::new, PLAYER_HEAD, 17, (char) 0xE24A, PUNCTUATION),
    // Reactions
    SURPRISED(DummyMytem::new, SLIME_BALL, 21, (char) 0xE255, REACTION),
    HAPPY(DummyMytem::new, SLIME_BALL, 22, (char) 0xE256, REACTION),
    SOB(DummyMytem::new, SLIME_BALL, 23, (char) 0xE257, REACTION),
    CRY(DummyMytem::new, SLIME_BALL, 24, (char) 0xE258, REACTION),
    COOL(DummyMytem::new, SLIME_BALL, 25, (char) 0xE259, REACTION),
    SMILE(DummyMytem::new, SLIME_BALL, 26, (char) 0xE25A, REACTION),
    SMILE_UPSIDE_DOWN(DummyMytem::new, SLIME_BALL, 27, (char) 0xE25B, REACTION),
    FROWN(DummyMytem::new, SLIME_BALL, 28, (char) 0xE25C, REACTION),
    SILLY(DummyMytem::new, SLIME_BALL, 29, (char) 0xE25D, REACTION),
    CLOWN(DummyMytem::new, SLIME_BALL, 30, (char) 0xE25E, REACTION),
    STARSTRUCK(DummyMytem::new, SLIME_BALL, 31, (char) 0xE25F, REACTION),
    LOVE_EYES(DummyMytem::new, SLIME_BALL, 32, (char) 0xE260, REACTION),
    WINK_SMILE(DummyMytem::new, SLIME_BALL, 33, (char) 0xE261, REACTION),
    MIND_BLOWN(DummyMytem::new, SLIME_BALL, 34, (char) 0xE262, REACTION),
    WINK(DummyMytem::new, SLIME_BALL, 35, (char) 0xE263, REACTION),
    COW_FACE(DummyMytem::new, SLIME_BALL, 224, REACTION),
    CREEPER_FACE(DummyMytem::new, SLIME_BALL, 225, REACTION),
    ENDERMAN_FACE(DummyMytem::new, SLIME_BALL, 226, REACTION),
    GHAST_FACE(DummyMytem::new, SLIME_BALL, 227, REACTION),
    PIG_FACE(DummyMytem::new, SLIME_BALL, 228, REACTION),
    SHEEP_FACE(DummyMytem::new, SLIME_BALL, 229, REACTION),
    SKELETON_FACE(DummyMytem::new, SLIME_BALL, 230, REACTION),
    SLIME_FACE(DummyMytem::new, SLIME_BALL, 231, REACTION),
    SPIDER_FACE(DummyMytem::new, SLIME_BALL, 232, REACTION),
    SQUID_FACE(DummyMytem::new, SLIME_BALL, 233, REACTION),
    STEVE_FACE(DummyMytem::new, SLIME_BALL, 234, REACTION),
    ALEX_FACE(DummyMytem::new, SLIME_BALL, 235, REACTION),
    VILLAGER_FACE(DummyMytem::new, SLIME_BALL, 236, REACTION),
    PILLAGER_FACE(DummyMytem::new, SLIME_BALL, 237, REACTION),
    WITHER_FACE(DummyMytem::new, SLIME_BALL, 238, REACTION),
    ZOMBIE_FACE(DummyMytem::new, SLIME_BALL, 239, REACTION),
    WITCH_FACE(DummyMytem::new, SLIME_BALL, 240, REACTION),
    // Pic
    PIC_WOLF(DummyMytem::new, BONE, 39, (char) 0xE267, PICTURE),
    PIC_CAT(DummyMytem::new, STRING, 40, (char) 0xE268, PICTURE),
    // Halloween
    CANDY_CORN(HalloweenCandy::new, CARROT, 42, (char) 0xE26A, HALLOWEEN),
    CHOCOLATE_BAR(HalloweenCandy::new, PUMPKIN_PIE, 52, (char) 0xE274, HALLOWEEN),
    LOLLIPOP(HalloweenCandy::new, COOKIE, 53, (char) 0xE275, HALLOWEEN),
    ORANGE_CANDY(HalloweenCandy::new, COOKIE, 54, (char) 0xE276, HALLOWEEN),
    GOLDEN_CUP(DummyMytem::new, GOLD_NUGGET, 108, (char) 0xE2AB, TREASURE),
    HALLOWEEN_TOKEN(HalloweenToken::new, PUMPKIN, 109, (char) 0xE2AC, HALLOWEEN),
    HALLOWEEN_TOKEN_2(HalloweenToken2::new, JACK_O_LANTERN, 110, (char) 0xE2AD, HALLOWEEN),
    // Scarlet
    SCARLET_HELMET(ScarletItem.Helmet::new, PLAYER_HEAD, 156, SCARLET),
    SCARLET_CHESTPLATE(ScarletItem.Chestplate::new, LEATHER_CHESTPLATE, 157, SCARLET),
    SCARLET_LEGGINGS(ScarletItem.Leggings::new, LEATHER_LEGGINGS, 158, SCARLET),
    SCARLET_BOOTS(ScarletItem.Boots::new, LEATHER_BOOTS, 159, SCARLET),
    SCARLET_SWORD(ScarletItem.Sword::new, NETHERITE_SWORD, 160, SCARLET),
    SCARLET_SHIELD(ScarletItem.Shield::new, SHIELD, 161, SCARLET),
    // Keys
    COPPER_KEY(DummyMytem::new, COPPER_INGOT, 177, KEY),
    SILVER_KEY(DummyMytem::new, IRON_INGOT, 178, KEY),
    GOLDEN_KEY(DummyMytem::new, GOLD_INGOT, 179, KEY),
    COPPER_KEYHOLE(DummyMytem::new, COPPER_BLOCK, 180, KEYHOLE),
    SILVER_KEYHOLE(DummyMytem::new, IRON_BLOCK, 181, KEYHOLE),
    GOLDEN_KEYHOLE(DummyMytem::new, GOLD_BLOCK, 182, KEYHOLE),
    // Coins
    COPPER_COIN(Coin::new, COPPER_INGOT, 183, COIN),
    SILVER_COIN(Coin::new, IRON_INGOT, 184, COIN),
    GOLDEN_COIN(Coin::new, GOLD_INGOT, 185, COIN),
    // Paintbrush
    BLACK_PAINTBRUSH(Paintbrush::new, WOODEN_SHOVEL, 186, PAINTBRUSH),
    RED_PAINTBRUSH(Paintbrush::new, WOODEN_SHOVEL, 187, PAINTBRUSH),
    GREEN_PAINTBRUSH(Paintbrush::new, WOODEN_SHOVEL, 188, PAINTBRUSH),
    BROWN_PAINTBRUSH(Paintbrush::new, WOODEN_SHOVEL, 189, PAINTBRUSH),
    BLUE_PAINTBRUSH(Paintbrush::new, WOODEN_SHOVEL, 190, PAINTBRUSH),
    PURPLE_PAINTBRUSH(Paintbrush::new, WOODEN_SHOVEL, 191, PAINTBRUSH),
    CYAN_PAINTBRUSH(Paintbrush::new, WOODEN_SHOVEL, 192, PAINTBRUSH),
    LIGHT_GRAY_PAINTBRUSH(Paintbrush::new, WOODEN_SHOVEL, 193, PAINTBRUSH),
    GRAY_PAINTBRUSH(Paintbrush::new, WOODEN_SHOVEL, 194, PAINTBRUSH),
    PINK_PAINTBRUSH(Paintbrush::new, WOODEN_SHOVEL, 195, PAINTBRUSH),
    LIME_PAINTBRUSH(Paintbrush::new, WOODEN_SHOVEL, 196, PAINTBRUSH),
    YELLOW_PAINTBRUSH(Paintbrush::new, WOODEN_SHOVEL, 197, PAINTBRUSH),
    LIGHT_BLUE_PAINTBRUSH(Paintbrush::new, WOODEN_SHOVEL, 198, PAINTBRUSH),
    MAGENTA_PAINTBRUSH(Paintbrush::new, WOODEN_SHOVEL, 199, PAINTBRUSH),
    ORANGE_PAINTBRUSH(Paintbrush::new, WOODEN_SHOVEL, 200, PAINTBRUSH),
    WHITE_PAINTBRUSH(Paintbrush::new, WOODEN_SHOVEL, 201, PAINTBRUSH),
    // Armor Parts
    RUSTY_BUCKET(ArmorPart::new, BUCKET, 205, ARMOR_PART),
    OLD_OVEN_LID(ArmorPart::new, NETHERITE_SCRAP, 207, ARMOR_PART),
    SOOTY_STOVE_PIPE(ArmorPart::new, BARREL, 206, ARMOR_PART),
    FLOTSAM_CAN(ArmorPart::new, FLOWER_POT, 208, ARMOR_PART),
    BENT_PITCHFORK(ArmorPart::new, LIGHTNING_ROD, 209, ARMOR_PART),
    TRASH_CAN_LID(ArmorPart::new, NETHERITE_SCRAP, 259, ARMOR_PART),
    // Technical
    FARAWAY_MAP(FarawayMap::new, PAPER, 249, TECHNICAL),
    // Tree
    OAKNUT(TreeSeed::new, BEETROOT_SEEDS, 251, TREE_SEED),
    BIRCH_SEED(TreeSeed::new, BEETROOT_SEEDS, 252, TREE_SEED),
    SPRUCE_CONE(TreeSeed::new, BEETROOT_SEEDS, 253, TREE_SEED),
    JUNGLE_SEED(TreeSeed::new, BEETROOT_SEEDS, 254, TREE_SEED),
    ACACIA_SEED(TreeSeed::new, BEETROOT_SEEDS, 255, TREE_SEED),
    DARK_OAK_SEED(TreeSeed::new, BEETROOT_SEEDS, 256, TREE_SEED),
    AZALEA_SEED(TreeSeed::new, BEETROOT_SEEDS, 260, TREE_SEED),
    SCOTCH_PINE_CONE(TreeSeed::new, BEETROOT_SEEDS, 261, TREE_SEED),
    FIR_CONE(TreeSeed::new, BEETROOT_SEEDS, 262, TREE_SEED),
    FANCY_OAK_SEED(TreeSeed::new, BEETROOT_SEEDS, 263, TREE_SEED),
    FANCY_BIRCH_SEED(TreeSeed::new, BEETROOT_SEEDS, 264, TREE_SEED),
    FANCY_SPRUCE_CONE(TreeSeed::new, BEETROOT_SEEDS, 265, TREE_SEED),
    // Potions
    EMPTY_FLASK(DummyMytem::new, GLASS_BOTTLE, 257, POTIONS),
    POTION_FLASK(PotionFlask::new, POTION, 258, POTIONS),
    // Tetris
    TETRIS_I(DummyMytem::new, SAND, 276, TETRIS),
    TETRIS_O(DummyMytem::new, SAND, 277, TETRIS),
    TETRIS_T(DummyMytem::new, SAND, 278, TETRIS),
    TETRIS_L(DummyMytem::new, SAND, 279, TETRIS),
    TETRIS_J(DummyMytem::new, SAND, 280, TETRIS),
    TETRIS_S(DummyMytem::new, SAND, 281, TETRIS),
    TETRIS_Z(DummyMytem::new, SAND, 282, TETRIS),
    ;
    // Next CustomModelData: 285
    // (Deprecated) Next High Unicode Character: \uE2AE

    private static final Map<String, Mytems> ID_MAP = new HashMap<>();
    public final String id;
    public final Function<Mytems, Mytem> ctor;
    public final Material material;
    public final Integer customModelData;
    public final char character;
    public final Component component;
    public final MytemsCategory category;

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
        ID_MAP.put("brittle_barrel", SOOTY_STOVE_PIPE);
    }

    Mytems(final Function<Mytems, Mytem> ctor, final Material material, final Integer customModelData, final char character, final MytemsCategory category) {
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

    Mytems(final Function<Mytems, Mytem> ctor, final Material material, final int customModelData, final MytemsCategory category) {
        this(ctor, material, customModelData, (char) customModelData, category);
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
        return serializeWithTag(getMytem().serializeTag(itemStack));
    }

    public static String serializeMytem(ItemStack item) {
        Mytems mytems = Mytems.forItem(item);
        return mytems != null
            ? mytems.serializeItem(item)
            : null;
    }

    public String serializeSingleItem(ItemStack itemStack) {
        MytemTag tag = getMytem().serializeTag(itemStack);
        if (tag != null) tag.setAmount(null);
        return serializeWithTag(tag);
    }

    public static String serializeSingleMytem(ItemStack item) {
        Mytems mytems = Mytems.forItem(item);
        return mytems != null
            ? mytems.serializeSingleItem(item)
            : null;
    }

    public String serializeWithTag(MytemTag tag) {
        return tag != null && !tag.isDismissable()
            ? id + Json.serialize(tag)
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
        if (material == PLAYER_HEAD) {
            item = Items.text(createItemStack(), List.of());
            ItemMarker.resetId(item);
        } else {
            item = new ItemStack(material);
        }
        if (customModelData != null) {
            item.editMeta(meta -> meta.setCustomModelData(customModelData));
        }
        return item;
    }

    public ItemStack createIcon(List<Component> text) {
        return Items.text(createIcon(), text);
    }

    public boolean isItem(ItemStack item) {
        return forItem(item) == this;
    }

    public static boolean isIn(ItemStack item, Mytems... mytemsList) {
        Mytems mytems = forItem(item);
        if (mytems == null) return false;
        for (Mytems it : mytemsList) {
            if (mytems == it) return true;
        }
        return false;
    }
}
