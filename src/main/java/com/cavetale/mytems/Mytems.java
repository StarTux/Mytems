package com.cavetale.mytems;

import com.cavetale.core.item.ItemKind;
import com.cavetale.core.util.Json;
import com.cavetale.mytems.item.ArmorPart;
import com.cavetale.mytems.item.ArmorStandEditor;
import com.cavetale.mytems.item.ChristmasToken;
import com.cavetale.mytems.item.DiceItem;
import com.cavetale.mytems.item.DummyMytem;
import com.cavetale.mytems.item.Enderball;
import com.cavetale.mytems.item.ForbiddenMytem;
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
import com.cavetale.mytems.item.beestick.Beestick;
import com.cavetale.mytems.item.captain.Blunderbuss;
import com.cavetale.mytems.item.captain.CaptainsCutlass;
import com.cavetale.mytems.item.coin.Coin;
import com.cavetale.mytems.item.dividers.Dividers;
import com.cavetale.mytems.item.dune.DuneItem;
import com.cavetale.mytems.item.dwarven.DwarvenItem;
import com.cavetale.mytems.item.easter.EasterBasket;
import com.cavetale.mytems.item.easter.EasterEgg;
import com.cavetale.mytems.item.easter.EasterGear;
import com.cavetale.mytems.item.easter.EasterToken;
import com.cavetale.mytems.item.equipment.EquipmentItem;
import com.cavetale.mytems.item.farawaymap.FarawayMap;
import com.cavetale.mytems.item.fertilizer.Fertilizer;
import com.cavetale.mytems.item.font.GlyphItem;
import com.cavetale.mytems.item.garden.Scissors;
import com.cavetale.mytems.item.garden.Scythe;
import com.cavetale.mytems.item.halloween.HalloweenCandy;
import com.cavetale.mytems.item.halloween.HalloweenToken2;
import com.cavetale.mytems.item.halloween.HalloweenToken;
import com.cavetale.mytems.item.magnifier.Magnifier;
import com.cavetale.mytems.item.medieval.WitchBroom;
import com.cavetale.mytems.item.mobcostume.ChickenCostume;
import com.cavetale.mytems.item.mobcostume.CreeperCostume;
import com.cavetale.mytems.item.mobcostume.EndermanCostume;
import com.cavetale.mytems.item.mobcostume.SkeletonCostume;
import com.cavetale.mytems.item.mobcostume.SpiderCostume;
import com.cavetale.mytems.item.mobslayer.Mobslayer;
import com.cavetale.mytems.item.music.HyruleInstrument;
import com.cavetale.mytems.item.music.MusicalInstrument;
import com.cavetale.mytems.item.photo.Photo;
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
import com.cavetale.mytems.item.trophy.Trophy;
import com.cavetale.mytems.item.util.BlindEye;
import com.cavetale.mytems.item.util.SlimeFinder;
import com.cavetale.mytems.item.util.Sneakers;
import com.cavetale.mytems.item.vote.VoteCandy;
import com.cavetale.mytems.item.vote.VoteFirework;
import com.cavetale.mytems.item.wateringcan.EmptyWateringCan;
import com.cavetale.mytems.item.wateringcan.WateringCan;
import com.cavetale.mytems.item.wrench.MonkeyWrench;
import com.cavetale.mytems.util.Items;
import com.cavetale.mytems.util.Skull;
import com.cavetale.worldmarker.item.ItemMarker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import static com.cavetale.mytems.Animation.SPINNING_COIN;
import static com.cavetale.mytems.MytemsCategory.*;
import static net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer.plainText;
import static org.bukkit.Material.*;

/**
 * List of all known Mytems.
 * Unicode characters start at 0xE200.
 */
public enum Mytems implements ComponentLike, Keyed, ItemKind {
    // Halloween 2020
    DR_ACULA_STAFF(DrAculaStaff::new, NETHERITE_SWORD, 741302, (char) 0xE220, ACULA),
    FLAME_SHIELD(FlameShield::new, SHIELD, 741303, (char) 0xE234, ACULA),
    STOMPERS(Stompers::new, NETHERITE_BOOTS, 741304, (char) 0xE235, ACULA),
    GHAST_BOW(GhastBow::new, BOW, 741305, (char) 0xE236, ACULA),
    BAT_MASK(BatMask::new, PLAYER_HEAD, 741306, (char) 0xE237, ACULA),
    // Cloud City
    UNICORN_HORN(UnicornHorn::new, END_ROD, 7413003, (char) 0, UTILITY),
    MAGIC_CAPE(MagicCape::new, ELYTRA, 7413006, (char) 0xE238, chrarr(0xE238, 654, 655, 656, 657), UTILITY, Animation.MAGIC_CAPE),
    SNEAKERS(Sneakers::new, LEATHER_BOOTS, 333, UTILITY),
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
    KNITTED_BEANIE(ForbiddenMytem::new, LEATHER_HELMET, 222, CHRISTMAS),
    // Dune set
    DUNE_HELMET(DuneItem.Helmet::new, PLAYER_HEAD, 7413201, (char) 0xE225, DUNE),
    DUNE_CHESTPLATE(DuneItem.Chestplate::new, GOLDEN_CHESTPLATE, 7413202, (char) 0xE226, DUNE),
    DUNE_LEGGINGS(DuneItem.Leggings::new, GOLDEN_LEGGINGS, 7413203, (char) 0xE227, DUNE),
    DUNE_BOOTS(DuneItem.Boots::new, GOLDEN_BOOTS, 7413204, (char) 0xE228, DUNE),
    DUNE_DIGGER(DuneItem.Weapon::new, GOLDEN_SHOVEL, 7413205, (char) 0xE229, DUNE, Animation.DUNE_DIGGER),
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
    // Easter
    EASTER_TOKEN(EasterToken::new, PLAYER_HEAD, 345700, (char) 0xE211, EASTER_TOKENS),
    // Easter Eggs
    BLUE_EASTER_EGG(EasterEgg::new, PLAYER_HEAD, 345701, (char) 0xE212, EASTER_EGGS),
    GREEN_EASTER_EGG(EasterEgg::new, PLAYER_HEAD, 345702, (char) 0xE213, EASTER_EGGS),
    ORANGE_EASTER_EGG(EasterEgg::new, PLAYER_HEAD, 345703, (char) 0xE214, EASTER_EGGS),
    PINK_EASTER_EGG(EasterEgg::new, PLAYER_HEAD, 345704, (char) 0xE215, EASTER_EGGS),
    PURPLE_EASTER_EGG(EasterEgg::new, PLAYER_HEAD, 345705, (char) 0xE216, EASTER_EGGS),
    YELLOW_EASTER_EGG(EasterEgg::new, PLAYER_HEAD, 345706, (char) 0xE217, EASTER_EGGS),
    // Easter Baskets
    BLUE_EASTER_BASKET(EasterBasket::new, PLAYER_HEAD, 286, (char) 0, EASTER_BASKETS),
    GREEN_EASTER_BASKET(EasterBasket::new, PLAYER_HEAD, 287, (char) 0, EASTER_BASKETS),
    ORANGE_EASTER_BASKET(EasterBasket::new, PLAYER_HEAD, 288, (char) 0, EASTER_BASKETS),
    PINK_EASTER_BASKET(EasterBasket::new, PLAYER_HEAD, 289, (char) 0, EASTER_BASKETS),
    PURPLE_EASTER_BASKET(EasterBasket::new, PLAYER_HEAD, 290, (char) 0, EASTER_BASKETS),
    YELLOW_EASTER_BASKET(EasterBasket::new, PLAYER_HEAD, 291, (char) 0, EASTER_BASKETS),
    // Easter Gear
    EASTER_HELMET(EasterGear.Helmet::new, PLAYER_HEAD, 345711, (char) 0xE218, EASTER),
    EASTER_CHESTPLATE(EasterGear.Chestplate::new, LEATHER_CHESTPLATE, 345712, (char) 0xE219, EASTER),
    EASTER_LEGGINGS(EasterGear.Leggings::new, LEATHER_LEGGINGS, 345713, (char) 0xE21A, EASTER),
    EASTER_BOOTS(EasterGear.Boots::new, LEATHER_BOOTS, 345714, (char) 0xE21B, EASTER),
    // Furniture
    TOILET(Toilet::new, CAULDRON, 498101, (char) 0, FURNITURE), // APRIL
    BOSS_CHEST(ForbiddenMytem::new, CHEST, 7413004, (char) 0, FURNITURE),
    OAK_CHAIR(ForbiddenMytem::new, OAK_PLANKS, 135, (char) 0, FURNITURE),
    SPRUCE_CHAIR(ForbiddenMytem::new, SPRUCE_PLANKS, 136, (char) 0, FURNITURE),
    WHITE_ARMCHAIR(ForbiddenMytem::new, WHITE_WOOL, 137, (char) 0, FURNITURE),
    WHITE_SOFA_LEFT(ForbiddenMytem::new, WHITE_WOOL, 138, (char) 0, FURNITURE),
    WHITE_SOFA_RIGHT(ForbiddenMytem::new, WHITE_WOOL, 139, (char) 0, FURNITURE),
    RED_ARMCHAIR(ForbiddenMytem::new, RED_WOOL, 140, (char) 0, FURNITURE),
    RED_SOFA_LEFT(ForbiddenMytem::new, RED_WOOL, 141, (char) 0, FURNITURE),
    RED_SOFA_RIGHT(ForbiddenMytem::new, RED_WOOL, 142, (char) 0, FURNITURE),
    BLACK_ARMCHAIR(ForbiddenMytem::new, RED_WOOL, 143, (char) 0, FURNITURE),
    BLACK_SOFA_LEFT(ForbiddenMytem::new, RED_WOOL, 144, (char) 0, FURNITURE),
    BLACK_SOFA_RIGHT(ForbiddenMytem::new, RED_WOOL, 145, (char) 0, FURNITURE),
    // Utility
    WEDDING_RING(WeddingRing::new, PLAYER_HEAD, 7413002, (char) 0xE21C, FRIENDS),
    MAGIC_MAP(MagicMap::new, FILLED_MAP, 7413005, (char) 0xE21D,
              chrarr(0xE21D, 658, 659, 660, 661, 662, 663, 664, 665, 666, 667, 668, 669, 670, 671, 672), UTILITY, Animation.MAGIC_MAP),
    SNOW_SHOVEL(SnowShovel::new, IRON_SHOVEL, 220, UTILITY),
    HASTY_PICKAXE(ForbiddenMytem::new, GOLDEN_PICKAXE, 223, UTILITY),
    TREE_CHOPPER(TreeChopper::new, GOLDEN_AXE, 242, UTILITY),
    ARMOR_STAND_EDITOR(ArmorStandEditor::new, FLINT, 241, UTILITY),
    FERTILIZER(Fertilizer::new, BONE_MEAL, 285, UTILITY),
    WATERING_CAN(WateringCan::new, STONE_HOE, 297, GARDENING),
    EMPTY_WATERING_CAN(EmptyWateringCan::new, STONE_HOE, 334, GARDENING),
    GOLDEN_WATERING_CAN(WateringCan::new, GOLDEN_HOE, 307, GARDENING),
    EMPTY_GOLDEN_WATERING_CAN(EmptyWateringCan::new, STONE_HOE, 335, GARDENING),
    MONKEY_WRENCH(MonkeyWrench::new, STONE_HOE, 79, UTILITY),
    DIVIDERS(Dividers::new, WOODEN_HOE, 593, UTILITY),
    SLIME_FINDER(SlimeFinder::new, SLIME_BALL, 594, UTILITY),
    BLIND_EYE(BlindEye::new, CARROT, 596, UTILITY),
    SCISSORS(Scissors::new, SHEARS, 652, GARDENING),
    MAGNIFYING_GLASS(Magnifier::new, AMETHYST_SHARD, 653, UTILITY),
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
    FIREFIGHTER_HELMET(WardrobeItem::new, RED_CONCRETE, 149, (char) 0, WARDROBE_HAT),
    PLAGUE_DOCTOR(WardrobeItem::new, BLACK_CONCRETE, 150, (char) 0, WARDROBE_HAT),
    PLAGUE_DOCTOR_2(WardrobeItem::new, BLACK_CONCRETE, 151, (char) 0, WARDROBE_HAT),
    PUMPKIN_STUB(WardrobeItem::new, SEA_PICKLE, 152, (char) 0, WARDROBE_HAT),
    STOCKING_CAP(WardrobeItem::new, RED_WOOL, 153, (char) 0, WARDROBE_HAT), // Santa Hat
    STRAW_HAT(WardrobeItem::new, HAY_BLOCK, 154, (char) 0, WARDROBE_HAT),
    // Wardrobe Christmas 2022
    CHRISTMAS_HAT(WardrobeItem::new, RED_CONCRETE, 632, (char) 0, WARDROBE_HAT),
    SANTA_SLED(WardrobeItem::new, RED_CONCRETE, 649, (char) 0, WARDROBE_MOUNT),
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
    // Maypole Ingredients
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
    // May
    BOOK_OF_MAY(DummyMytem::new, BOOK, 304, MAY),
    BEESTICK(Beestick::new, BLAZE_ROD, 305, UTILITY),
    // Enderball
    ENDERBALL(Enderball::new, DRAGON_EGG, (Integer) null, (char) 0, UTILITY),
    // PocketMob
    POCKET_ALLAY(PocketMob::new, ALLAY_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_AXOLOTL(PocketMob::new, AXOLOTL_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_BAT(PocketMob::new, BAT_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_BEE(PocketMob::new, BEE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_BLAZE(PocketMob::new, BLAZE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_CAMEL(PocketMob::new, CAMEL_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
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
    POCKET_ENDER_DRAGON(PocketMob::new, ENDER_DRAGON_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_EVOKER(PocketMob::new, EVOKER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_FOX(PocketMob::new, FOX_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_FROG(PocketMob::new, FROG_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_GHAST(PocketMob::new, GHAST_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_GIANT(PocketMob::new, ZOMBIE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_GLOW_SQUID(PocketMob::new, GLOW_SQUID_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_GOAT(PocketMob::new, GOAT_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_GUARDIAN(PocketMob::new, GUARDIAN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_HOGLIN(PocketMob::new, HOGLIN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_HORSE(PocketMob::new, HORSE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_HUSK(PocketMob::new, HUSK_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ILLUSIONER(PocketMob::new, VINDICATOR_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_IRON_GOLEM(PocketMob::new, IRON_GOLEM_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
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
    POCKET_TADPOLE(PocketMob::new, TADPOLE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_TRADER_LLAMA(PocketMob::new, TRADER_LLAMA_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_TROPICAL_FISH(PocketMob::new, TROPICAL_FISH_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_TURTLE(PocketMob::new, TURTLE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_VEX(PocketMob::new, VEX_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_VILLAGER(PocketMob::new, VILLAGER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_VINDICATOR(PocketMob::new, VINDICATOR_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_WANDERING_TRADER(PocketMob::new, WANDERING_TRADER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_WARDEN(PocketMob::new, WARDEN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_WITCH(PocketMob::new, WITCH_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_WITHER(PocketMob::new, WITHER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
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
    CAPTAINS_CUTLASS(CaptainsCutlass::new, WOODEN_SWORD, 2, (char) 0xE239, UTILITY),
    BLUNDERBUSS(Blunderbuss::new, IRON_INGOT, 3, (char) 0xE23A, UTILITY),
    // Medieval
    IRON_SCYTHE(Scythe::new, IRON_HOE, 650, GARDENING),
    GOLDEN_SCYTHE(Scythe::new, GOLDEN_HOE, 4, (char) 0xE23B, GARDENING),
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
    KOBOLD_HEAD(WardrobeItem::new, GREEN_CONCRETE, 1, (char) 0, WARDROBE_HAT),
    // Random
    RUBY(DummyMytem::new, EMERALD, 6, (char) 0xE23E, CURRENCY),
    RUBY_KITTY(DummyMytem::new, EMERALD, 651, CURRENCY),
    // UI
    OK(ForbiddenMytem::new, BLUE_CONCRETE, 7, (char) 0xE23F, UI),
    NO(ForbiddenMytem::new, RED_CONCRETE, 8, (char) 0xE240, UI),
    ON(ForbiddenMytem::new, ENDER_EYE, 210, UI),
    OFF(ForbiddenMytem::new, ENDER_PEARL, 211, UI),
    REDO(ForbiddenMytem::new, EGG, 212, UI),
    HALF_HEART(ForbiddenMytem::new, NAUTILUS_SHELL, 10, (char) 0xE242, UI),
    EMPTY_HEART(ForbiddenMytem::new, NAUTILUS_SHELL, 13, (char) 0xE246, UI),
    ARROW_RIGHT(ForbiddenMytem::new, ARROW, 11, (char) 0xE244, ARROWS),
    ARROW_LEFT(ForbiddenMytem::new, ARROW, 12, (char) 0xE245, ARROWS),
    ARROW_UP(ForbiddenMytem::new, ARROW, 37, (char) 0xE265, ARROWS),
    ARROW_DOWN(ForbiddenMytem::new, ARROW, 38, (char) 0xE266, ARROWS),
    TURN_LEFT(ForbiddenMytem::new, ARROW, 283, ARROWS),
    TURN_RIGHT(ForbiddenMytem::new, ARROW, 284, ARROWS),
    CHECKBOX(ForbiddenMytem::new, WHITE_CONCRETE, 14, (char) 0xE247, UI),
    CHECKED_CHECKBOX(ForbiddenMytem::new, GREEN_CONCRETE, 15, (char) 0xE248, UI),
    CROSSED_CHECKBOX(ForbiddenMytem::new, BARRIER, 16, (char) 0xE249, UI),
    EAGLE(ForbiddenMytem::new, FEATHER, 19, (char) 0xE24C, UI),
    EARTH(ForbiddenMytem::new, ENDER_PEARL, 5, (char) 0xE23D, UI),
    EASTER_EGG(DummyMytem::new, EGG, 345715, (char) 0xE23C, COLLECTIBLES),
    TRAFFIC_LIGHT(ForbiddenMytem::new, YELLOW_DYE, 57, (char) 0xE279, UI),
    INVISIBLE_ITEM(ForbiddenMytem::new, LIGHT_GRAY_STAINED_GLASS_PANE, 65, (char) 0xE281, UI),
    PAINT_PALETTE(ForbiddenMytem::new, STICK, 202, UI),
    PLUS_BUTTON(ForbiddenMytem::new, EGG, 266, UI),
    MINUS_BUTTON(ForbiddenMytem::new, SNOWBALL, 267, UI),
    FLOPPY_DISK(ForbiddenMytem::new, MUSIC_DISC_CAT, 268, UI),
    FOLDER(ForbiddenMytem::new, CHEST, 269, UI),
    MAGNET(ForbiddenMytem::new, IRON_NUGGET, 270, UI),
    DATA_INTEGER(ForbiddenMytem::new, REPEATER, 271, UI),
    DATA_STRING(ForbiddenMytem::new, CHAIN, 272, UI),
    DATA_FLOAT(ForbiddenMytem::new, REPEATER, 273, UI),
    BOMB(ForbiddenMytem::new, CHAIN, 274, UI),
    MOUSE(ForbiddenMytem::new, WHITE_CONCRETE, 336, UI),
    MOUSE_LEFT(ForbiddenMytem::new, LIGHT_BLUE_CONCRETE, 337, UI),
    MOUSE_RIGHT(ForbiddenMytem::new, RED_CONCRETE, 338, UI),
    SHIFT_KEY(ForbiddenMytem::new, LIGHT_GRAY_CONCRETE, 339, UI),
    THUMBS_UP(ForbiddenMytem::new, GREEN_CONCRETE, 340, UI),
    EYES(ForbiddenMytem::new, ENDER_EYE, 595, UI),
    CAVETALE_DUNGEON(ForbiddenMytem::new, SPAWNER, 706, UI),
    // Animated Shines
    RAINBOW_BUTTERFLY(ForbiddenMytem::new, FEATHER, 633, (char) 633, chrarr(633, 634, 635, 636, 637, 638, 639, 640), UI, Animation.frametime(8)),
    SNOWFLAKE(ForbiddenMytem::new, SNOWBALL, 673, (char) 673,
              chrarr(673, 674, 675, 676, 677, 678, 679, 680, 681, 682, 683, 684, 685, 686, 687, 688), UI, Animation.SNOWFLAKE),
    // Collectibles
    HEART(DummyMytem::new, HEART_OF_THE_SEA, 9, (char) 0xE241, COLLECTIBLES),
    STAR(DummyMytem::new, NETHER_STAR, 18, (char) 0xE24B, COLLECTIBLES),
    MOON(DummyMytem::new, YELLOW_DYE, 204, COLLECTIBLES),
    GREEN_MOON(DummyMytem::new, LIME_DYE, 592, COLLECTIBLES),
    LIGHTNING(DummyMytem::new, LIGHTNING_ROD, 250, COLLECTIBLES),
    // Dice
    DICE(DiceItem::new, PLAYER_HEAD, 213, DIE),
    DICE4(DiceItem::new, PRISMARINE_SHARD, 243, DIE),
    DICE8(DiceItem::new, PRISMARINE_SHARD, 244, DIE),
    DICE10(DiceItem::new, PRISMARINE_SHARD, 245, DIE),
    DICE12(DiceItem::new, PRISMARINE_SHARD, 246, DIE),
    DICE20(DiceItem::new, PRISMARINE_SHARD, 247, DIE),
    DICE100(DiceItem::new, PRISMARINE_SHARD, 248, DIE),
    DICE_1(ForbiddenMytem::new, QUARTZ_BLOCK, 707, UI),
    DICE_2(ForbiddenMytem::new, QUARTZ_BLOCK, 708, UI),
    DICE_3(ForbiddenMytem::new, QUARTZ_BLOCK, 709, UI),
    DICE_4(ForbiddenMytem::new, QUARTZ_BLOCK, 710, UI),
    DICE_5(ForbiddenMytem::new, QUARTZ_BLOCK, 711, UI),
    DICE_6(ForbiddenMytem::new, QUARTZ_BLOCK, 712, UI),
    DICE_ROLL(ForbiddenMytem::new, QUARTZ_BLOCK, 713, (char) 713, chrarr(713, 714, 715, 716, 717, 718), UI, Animation.frametime(2)),
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
    SURPRISED(ForbiddenMytem::new, SLIME_BALL, 21, (char) 0xE255, REACTION),
    HAPPY(ForbiddenMytem::new, SLIME_BALL, 22, (char) 0xE256, REACTION),
    SOB(ForbiddenMytem::new, SLIME_BALL, 23, (char) 0xE257, REACTION),
    CRY(ForbiddenMytem::new, SLIME_BALL, 24, (char) 0xE258, REACTION),
    COOL(ForbiddenMytem::new, SLIME_BALL, 25, (char) 0xE259, REACTION),
    SMILE(ForbiddenMytem::new, SLIME_BALL, 26, (char) 0xE25A, REACTION),
    SMILE_UPSIDE_DOWN(ForbiddenMytem::new, SLIME_BALL, 27, (char) 0xE25B, REACTION),
    FROWN(ForbiddenMytem::new, SLIME_BALL, 28, (char) 0xE25C, REACTION),
    SILLY(ForbiddenMytem::new, SLIME_BALL, 29, (char) 0xE25D, REACTION),
    CLOWN(ForbiddenMytem::new, SLIME_BALL, 30, (char) 0xE25E, REACTION),
    STARSTRUCK(ForbiddenMytem::new, SLIME_BALL, 31, (char) 0xE25F, REACTION),
    LOVE_EYES(ForbiddenMytem::new, SLIME_BALL, 32, (char) 0xE260, REACTION),
    WINK_SMILE(ForbiddenMytem::new, SLIME_BALL, 33, (char) 0xE261, REACTION),
    MIND_BLOWN(ForbiddenMytem::new, SLIME_BALL, 34, (char) 0xE262, REACTION),
    WINK(ForbiddenMytem::new, SLIME_BALL, 35, (char) 0xE263, REACTION),
    STEVE_FACE(ForbiddenMytem::new, SLIME_BALL, 234, REACTION),
    ALEX_FACE(ForbiddenMytem::new, SLIME_BALL, 235, REACTION),
    // Mob Faces
    COW_FACE(ForbiddenMytem::new, SLIME_BALL, 224, MOB_FACE),
    CREEPER_FACE(ForbiddenMytem::new, SLIME_BALL, 225, MOB_FACE),
    ENDERMAN_FACE(ForbiddenMytem::new, SLIME_BALL, 226, MOB_FACE),
    GHAST_FACE(ForbiddenMytem::new, SLIME_BALL, 227, MOB_FACE),
    PIG_FACE(ForbiddenMytem::new, SLIME_BALL, 228, MOB_FACE),
    SHEEP_FACE(ForbiddenMytem::new, SLIME_BALL, 229, MOB_FACE),
    SKELETON_FACE(ForbiddenMytem::new, SLIME_BALL, 230, MOB_FACE),
    SLIME_FACE(ForbiddenMytem::new, SLIME_BALL, 231, MOB_FACE),
    SPIDER_FACE(ForbiddenMytem::new, SLIME_BALL, 232, MOB_FACE),
    SQUID_FACE(ForbiddenMytem::new, SLIME_BALL, 233, MOB_FACE),
    VILLAGER_FACE(ForbiddenMytem::new, SLIME_BALL, 236, MOB_FACE),
    PILLAGER_FACE(ForbiddenMytem::new, SLIME_BALL, 237, MOB_FACE),
    WITHER_FACE(ForbiddenMytem::new, SLIME_BALL, 238, MOB_FACE),
    ZOMBIE_FACE(ForbiddenMytem::new, SLIME_BALL, 239, MOB_FACE),
    WITCH_FACE(ForbiddenMytem::new, SLIME_BALL, 240, MOB_FACE),
    BLAZE_FACE(ForbiddenMytem::new, SLIME_BALL, 689, MOB_FACE),
    PIGLIN_FACE(ForbiddenMytem::new, SLIME_BALL, 690, MOB_FACE),
    WARDEN_FACE(ForbiddenMytem::new, SLIME_BALL, 691, MOB_FACE),
    HOGLIN_FACE(ForbiddenMytem::new, SLIME_BALL, 692, MOB_FACE),
    HUSK_FACE(ForbiddenMytem::new, SLIME_BALL, 693, MOB_FACE),
    VEX_FACE(ForbiddenMytem::new, SLIME_BALL, 694, MOB_FACE),
    GUARDIAN_FACE(ForbiddenMytem::new, SLIME_BALL, 695, MOB_FACE),
    DROWNED_FACE(ForbiddenMytem::new, SLIME_BALL, 696, MOB_FACE),
    PHANTOM_FACE(ForbiddenMytem::new, SLIME_BALL, 697, MOB_FACE),
    MAGMA_CUBE_FACE(ForbiddenMytem::new, SLIME_BALL, 698, MOB_FACE),
    RAVAGER_FACE(ForbiddenMytem::new, SLIME_BALL, 699, MOB_FACE),
    SILVERFISH_FACE(ForbiddenMytem::new, SLIME_BALL, 700, MOB_FACE),
    ZOGLIN_FACE(ForbiddenMytem::new, SLIME_BALL, 701, MOB_FACE),
    ZOMBIE_VILLAGER_FACE(ForbiddenMytem::new, SLIME_BALL, 702, MOB_FACE),
    WITHER_SKELETON_FACE(ForbiddenMytem::new, SLIME_BALL, 703, MOB_FACE),
    ENDER_DRAGON_FACE(ForbiddenMytem::new, SLIME_BALL, 704, MOB_FACE),
    STRAY_FACE(ForbiddenMytem::new, SLIME_BALL, 705, MOB_FACE),
    // Pic
    PIC_WOLF(ForbiddenMytem::new, BONE, 39, (char) 0xE267, PICTURE),
    PIC_CAT(ForbiddenMytem::new, STRING, 40, (char) 0xE268, PICTURE),
    // Halloween
    CANDY_CORN(HalloweenCandy::new, CARROT, 42, (char) 0xE26A, HALLOWEEN),
    CHOCOLATE_BAR(HalloweenCandy::new, PUMPKIN_PIE, 52, (char) 0xE274, HALLOWEEN),
    LOLLIPOP(HalloweenCandy::new, COOKIE, 53, (char) 0xE275, HALLOWEEN),
    ORANGE_CANDY(HalloweenCandy::new, COOKIE, 54, (char) 0xE276, HALLOWEEN),
    HALLOWEEN_TOKEN(HalloweenToken::new, PUMPKIN, 109, (char) 0xE2AC, HALLOWEEN),
    HALLOWEEN_TOKEN_2(HalloweenToken2::new, JACK_O_LANTERN, 110, (char) 0xE2AD, HALLOWEEN),
    // Halloween 2022
    CREEPER_CUSTOME_HELMET(CreeperCostume.CreeperHelmet::new, CREEPER_HEAD, null, (char) 0, CREEPER_COSTUME),
    CREEPER_CUSTOME_CHESTPLATE(CreeperCostume.CreeperChestplate::new, LEATHER_CHESTPLATE, null, (char) 0, CREEPER_COSTUME),
    CREEPER_CUSTOME_LEGGINGS(CreeperCostume.CreeperLeggings::new, LEATHER_LEGGINGS, null, (char) 0, CREEPER_COSTUME),
    CREEPER_CUSTOME_BOOTS(CreeperCostume.CreeperBoots::new, LEATHER_BOOTS, null, (char) 0, CREEPER_COSTUME),
    SPIDER_CUSTOME_HELMET(SpiderCostume.SpiderHelmet::new, PLAYER_HEAD, null, (char) 0, SPIDER_COSTUME),
    SPIDER_CUSTOME_CHESTPLATE(SpiderCostume.SpiderChestplate::new, LEATHER_CHESTPLATE, null, (char) 0, SPIDER_COSTUME),
    SPIDER_CUSTOME_LEGGINGS(SpiderCostume.SpiderLeggings::new, LEATHER_LEGGINGS, null, (char) 0, SPIDER_COSTUME),
    SPIDER_CUSTOME_BOOTS(SpiderCostume.SpiderBoots::new, LEATHER_BOOTS, null, (char) 0, SPIDER_COSTUME),
    ENDERMAN_CUSTOME_HELMET(EndermanCostume.EndermanHelmet::new, PLAYER_HEAD, null, (char) 0, ENDERMAN_COSTUME),
    ENDERMAN_CUSTOME_CHESTPLATE(EndermanCostume.EndermanChestplate::new, LEATHER_CHESTPLATE, null, (char) 0, ENDERMAN_COSTUME),
    ENDERMAN_CUSTOME_LEGGINGS(EndermanCostume.EndermanLeggings::new, LEATHER_LEGGINGS, null, (char) 0, ENDERMAN_COSTUME),
    ENDERMAN_CUSTOME_BOOTS(EndermanCostume.EndermanBoots::new, LEATHER_BOOTS, null, (char) 0, ENDERMAN_COSTUME),
    SKELETON_CUSTOME_HELMET(SkeletonCostume.SkeletonHelmet::new, SKELETON_SKULL, null, (char) 0, SKELETON_COSTUME),
    SKELETON_CUSTOME_CHESTPLATE(SkeletonCostume.SkeletonChestplate::new, LEATHER_CHESTPLATE, null, (char) 0, SKELETON_COSTUME),
    SKELETON_CUSTOME_LEGGINGS(SkeletonCostume.SkeletonLeggings::new, LEATHER_LEGGINGS, null, (char) 0, SKELETON_COSTUME),
    SKELETON_CUSTOME_BOOTS(SkeletonCostume.SkeletonBoots::new, LEATHER_BOOTS, null, (char) 0, SKELETON_COSTUME),
    CHICKEN_CUSTOME_HELMET(ChickenCostume.ChickenHelmet::new, PLAYER_HEAD, null, (char) 0, CHICKEN_COSTUME),
    CHICKEN_CUSTOME_CHESTPLATE(ChickenCostume.ChickenChestplate::new, LEATHER_CHESTPLATE, null, (char) 0, CHICKEN_COSTUME),
    CHICKEN_CUSTOME_LEGGINGS(ChickenCostume.ChickenLeggings::new, LEATHER_LEGGINGS, null, (char) 0, CHICKEN_COSTUME),
    CHICKEN_CUSTOME_BOOTS(ChickenCostume.ChickenBoots::new, LEATHER_BOOTS, null, (char) 0, CHICKEN_COSTUME),
    // Scarlet
    SCARLET_HELMET(ScarletItem.Helmet::new, PLAYER_HEAD, 156, SCARLET),
    SCARLET_CHESTPLATE(ScarletItem.Chestplate::new, LEATHER_CHESTPLATE, 157, SCARLET),
    SCARLET_LEGGINGS(ScarletItem.Leggings::new, LEATHER_LEGGINGS, 158, SCARLET),
    SCARLET_BOOTS(ScarletItem.Boots::new, LEATHER_BOOTS, 159, SCARLET),
    SCARLET_SWORD(ScarletItem.Sword::new, NETHERITE_SWORD, 160, SCARLET, Animation.frametime(4)),
    SCARLET_SHIELD(ScarletItem.Shield::new, SHIELD, 161, SCARLET),
    // Keys
    COPPER_KEY(ForbiddenMytem::new, COPPER_INGOT, 177, KEY),
    SILVER_KEY(ForbiddenMytem::new, IRON_INGOT, 178, KEY),
    GOLDEN_KEY(ForbiddenMytem::new, GOLD_INGOT, 179, KEY),
    COPPER_KEYHOLE(ForbiddenMytem::new, COPPER_BLOCK, 180, KEYHOLE),
    SILVER_KEYHOLE(ForbiddenMytem::new, IRON_BLOCK, 181, KEYHOLE),
    GOLDEN_KEYHOLE(ForbiddenMytem::new, GOLD_BLOCK, 182, KEYHOLE),
    // Coins
    COPPER_COIN(Coin::new, COPPER_INGOT, 183, (char) 183, chrarr(604, 605, 183, 606, 607, 608, 609, 610), COIN, SPINNING_COIN),
    SILVER_COIN(Coin::new, IRON_INGOT, 184, (char) 184, chrarr(611, 612, 184, 613, 614, 615, 616, 617), COIN, SPINNING_COIN),
    GOLDEN_COIN(Coin::new, GOLD_INGOT, 185, (char) 185, chrarr(597, 598, 185, 599, 600, 601, 602, 603), COIN, SPINNING_COIN),
    DIAMOND_COIN(Coin::new, DIAMOND, 275, (char) 275, chrarr(618, 619, 275, 620, 621, 622, 623, 624), COIN, SPINNING_COIN),
    RUBY_COIN(Coin::new, EMERALD, 316, (char) 316, chrarr(625, 626, 316, 627, 628, 629, 630, 631), COIN, SPINNING_COIN),
    GOLDEN_HOOP(Coin::new, GOLD_INGOT, 641, (char) 641, chrarr(641, 642, 643, 644, 645, 646, 647, 648), COIN, SPINNING_COIN),
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
    FANCY_OAK_SEED(TreeSeed::new, BEETROOT_SEEDS, 263, TREE_SEED, Animation.frametime(6)),
    FANCY_BIRCH_SEED(TreeSeed::new, BEETROOT_SEEDS, 264, TREE_SEED, Animation.frametime(6)),
    FANCY_SPRUCE_CONE(TreeSeed::new, BEETROOT_SEEDS, 265, TREE_SEED, Animation.frametime(6)),
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
    // Cup Trophy
    GOLDEN_CUP(Trophy::new, GOLD_INGOT, 108, (char) 0xE2AB, TROPHY),
    SILVER_CUP(Trophy::new, IRON_INGOT, 292, TROPHY),
    BRONZE_CUP(Trophy::new, COPPER_INGOT, 293, TROPHY),
    PARTICIPATION_CUP(Trophy::new, BLUE_CONCRETE, 298, TROPHY),
    // Medal Trophy
    GOLD_MEDAL(Trophy::new, GOLD_NUGGET, 294, TROPHY),
    SILVER_MEDAL(Trophy::new, IRON_NUGGET, 295, TROPHY),
    BRONZE_MEDAL(Trophy::new, COPPER_INGOT, 296, TROPHY),
    PARTICIPATION_MEDAL(Trophy::new, BLUE_WOOL, 299, TROPHY),
    // Easter Trophy
    GOLD_EASTER_TROPHY(Trophy::new, EGG, 300, TROPHY),
    SILVER_EASTER_TROPHY(Trophy::new, EGG, 301, TROPHY),
    BRONZE_EASTER_TROPHY(Trophy::new, EGG, 302, TROPHY),
    PARTICIPATION_EASTER_TROPHY(Trophy::new, EGG, 303, TROPHY),
    // Vertigo Trophy
    GOLD_VERTIGO_TROPHY(Trophy::new, LAVA_BUCKET, 308, TROPHY),
    SILVER_VERTIGO_TROPHY(Trophy::new, BUCKET, 309, TROPHY),
    BRONZE_VERTIGO_TROPHY(Trophy::new, WATER_BUCKET, 310, TROPHY),
    PARTICIPATION_VERTIGO_TROPHY(Trophy::new, BUCKET, 311, TROPHY),
    // Ladder Trophy
    GOLD_LADDER_TROPHY(Trophy::new, LADDER, 312, TROPHY),
    SILVER_LADDER_TROPHY(Trophy::new, LADDER, 313, TROPHY),
    BRONZE_LADDER_TROPHY(Trophy::new, LADDER, 314, TROPHY),
    PARTICIPATION_LADDER_TROPHY(Trophy::new, LADDER, 315, TROPHY),
    // Cavepaint Trophy
    GOLD_CAVEPAINT_TROPHY(Trophy::new, PAINTING, 317, TROPHY),
    SILVER_CAVEPAINT_TROPHY(Trophy::new, PAINTING, 318, TROPHY),
    BRONZE_CAVEPAINT_TROPHY(Trophy::new, PAINTING, 319, TROPHY),
    PARTICIPATION_CAVEPAINT_TROPHY(Trophy::new, PAINTING, 320, TROPHY),
    // Red Light Green Light Trophy
    GOLD_RED_GREEN_LIGHT_TROPHY(Trophy::new, GOLD_INGOT, 321, TROPHY),
    SILVER_RED_GREEN_LIGHT_TROPHY(Trophy::new, IRON_INGOT, 322, TROPHY),
    BRONZE_RED_GREEN_LIGHT_TROPHY(Trophy::new, COPPER_INGOT, 323, TROPHY),
    PART_RED_GREEN_LIGHT_TROPHY(Trophy::new, BLUE_CONCRETE, 324, TROPHY),
    // Hide and Seek Trophy
    GOLD_HIDE_AND_SEEK_TROPHY(Trophy::new, GOLD_INGOT, 325, TROPHY),
    SILVER_HIDE_AND_SEEK_TROPHY(Trophy::new, IRON_INGOT, 326, TROPHY),
    BRONZE_HIDE_AND_SEEK_TROPHY(Trophy::new, COPPER_INGOT, 327, TROPHY),
    PART_HIDE_AND_SEEK_TROPHY(Trophy::new, BLUE_CONCRETE, 328, TROPHY),
    // Vote Trophy
    GOLD_VOTE_TROPHY(Trophy::new, GOLD_INGOT, 329, TROPHY),
    SILVER_VOTE_TROPHY(Trophy::new, IRON_INGOT, 330, TROPHY),
    BRONZE_VOTE_TROPHY(Trophy::new, COPPER_INGOT, 331, TROPHY),
    PART_VOTE_TROPHY(Trophy::new, BLUE_CONCRETE, 332, TROPHY),
    // Spleef Trophy
    GOLDEN_SPLEEF_SHOVEL(Trophy::new, GOLDEN_SHOVEL, 341, TROPHY),
    SILVER_SPLEEF_SHOVEL(Trophy::new, IRON_SHOVEL, 342, TROPHY),
    BRONZE_SPLEEF_SHOVEL(Trophy::new, STONE_SHOVEL, 343, TROPHY),
    BLUE_SPLEEF_SHOVEL(Trophy::new, WOODEN_SHOVEL, 344, TROPHY),
    // End Fight Trophy
    GOLDEN_END_FIGHT_TROPHY(Trophy::new, GOLDEN_SWORD, 588, TROPHY),
    SILVER_END_FIGHT_TROPHY(Trophy::new, GOLDEN_SWORD, 589, TROPHY),
    BRONZE_END_FIGHT_TROPHY(Trophy::new, GOLDEN_SWORD, 590, TROPHY),
    BLUE_END_FIGHT_TROPHY(Trophy::new, GOLDEN_SWORD, 591, TROPHY),
    // Photo
    PHOTO(Photo::new, FILLED_MAP, 306, PHOTOS),
    DEBUG(DummyMytem::new, DIAMOND, 20, UTILITY),
    // Mob Arena
    MOBSLAYER(Mobslayer::new, NETHERITE_SWORD, 719, MOBSLAYERS),
    MOBSLAYER2(Mobslayer::new, NETHERITE_SWORD, 720, MOBSLAYERS),
    MOBSLAYER3(Mobslayer::new, NETHERITE_SWORD, 721, MOBSLAYERS),
    // Equipment
    GOLDEN_QUIVER(EquipmentItem::new, STICK, 345, EQUIP_QUIVER),
    STEEL_QUIVER(EquipmentItem::new, STICK, 346, EQUIP_QUIVER),
    WOODEN_QUIVER(EquipmentItem::new, STICK, 347, EQUIP_QUIVER),
    EARTH_BROADSWORD(EquipmentItem::new, IRON_SWORD, 348, EQUIP_BROADSWORD),
    ICE_BROADSWORD(EquipmentItem::new, IRON_SWORD, 349, EQUIP_BROADSWORD),
    STEEL_BROADSWORD(EquipmentItem::new, IRON_SWORD, 350, EQUIP_BROADSWORD),
    SERRATED_BROADSWORD(EquipmentItem::new, IRON_SWORD, 351, EQUIP_BROADSWORD),
    GOLDEN_BROADSWORD(EquipmentItem::new, GOLDEN_SWORD, 352, EQUIP_BROADSWORD),
    GILDED_STEEL_HAMMER(EquipmentItem::new, IRON_AXE, 353, EQUIP_HAMMER),
    STEEL_HAMMER(EquipmentItem::new, IRON_AXE, 354, EQUIP_HAMMER),
    WOODEN_MALLET(EquipmentItem::new, WOODEN_AXE, 355, EQUIP_HAMMER),
    GILDED_STEEL_MALLET(EquipmentItem::new, IRON_AXE, 356, EQUIP_HAMMER),
    STEEL_MALLET(EquipmentItem::new, IRON_AXE, 357, EQUIP_HAMMER),
    WOODEN_HAMMER(EquipmentItem::new, WOODEN_AXE, 358, EQUIP_HAMMER),
    GILDED_STEEL_CUTLASS(EquipmentItem::new, IRON_SWORD, 359, EQUIP_CUTLASS),
    FIRE_CUTLASS(EquipmentItem::new, IRON_SWORD, 360, EQUIP_CUTLASS),
    COPPER_CUTLASS(EquipmentItem::new, IRON_SWORD, 361, EQUIP_CUTLASS),
    EARTH_CUTLASS(EquipmentItem::new, IRON_SWORD, 362, EQUIP_CUTLASS),
    WATER_CUTLASS(EquipmentItem::new, IRON_SWORD, 363, EQUIP_CUTLASS),
    SERRATED_STEEL_CUTLASS(EquipmentItem::new, IRON_SWORD, 364, EQUIP_CUTLASS),
    GILDED_STEEL_SWORD(EquipmentItem::new, IRON_SWORD, 365, EQUIP_SWORD),
    STEEL_KATANA(EquipmentItem::new, IRON_SWORD, 366, EQUIP_SWORD),
    ICE_SWORD(EquipmentItem::new, IRON_SWORD, 367, EQUIP_SWORD),
    SERRATED_STEEL_SWORD(EquipmentItem::new, IRON_SWORD, 368, EQUIP_SWORD),
    FIRE_SWORD(EquipmentItem::new, IRON_SWORD, 369, EQUIP_SWORD),
    STEEL_SWORD(EquipmentItem::new, IRON_SWORD, 370, EQUIP_SWORD),
    COPPER_SWORD(EquipmentItem::new, IRON_SWORD, 371, EQUIP_SWORD),
    GILDED_WOODEN_BOW(EquipmentItem::new, BOW, 372, EQUIP_BOW),
    WOODEN_BOW(EquipmentItem::new, BOW, 373, EQUIP_BOW),
    WOODEN_COMPOSITE_BOW(EquipmentItem::new, BOW, 374, EQUIP_BOW),
    GOLDEN_COMPOSITE_BOW(EquipmentItem::new, BOW, 375, EQUIP_BOW),
    STEEL_COMPOSITE_BOW(EquipmentItem::new, BOW, 376, EQUIP_BOW),
    WOODEN_CROSSBOW(EquipmentItem::new, BOW, 377, EQUIP_BOW),
    VIOLET_BATTLE_SCYTHE(EquipmentItem::new, IRON_AXE, 378, EQUIP_SCYTHE),
    GOLDEN_BATTLE_SCYTHE(EquipmentItem::new, GOLDEN_AXE, 379, EQUIP_SCYTHE),
    IRON_BATTLE_SCYTHE(EquipmentItem::new, IRON_AXE, 380, EQUIP_SCYTHE),
    IRON_GRAND_BATTLE_SCYTHE(EquipmentItem::new, IRON_AXE, 381, EQUIP_SCYTHE),
    STEEL_BATTLE_SCYTHE(EquipmentItem::new, IRON_AXE, 382, EQUIP_SCYTHE),
    TURQUOISE_BATTLE_SCYTHE(EquipmentItem::new, IRON_AXE, 383, EQUIP_SCYTHE),
    RUSTY_DAGGER(EquipmentItem::new, IRON_SWORD, 384, EQUIP_DAGGER),
    SERRATED_STEEL_DAGGER(EquipmentItem::new, IRON_SWORD, 385, EQUIP_DAGGER),
    COPPER_DAGGER(EquipmentItem::new, IRON_SWORD, 386, EQUIP_DAGGER),
    STEEL_NINJA_SAIS(EquipmentItem::new, IRON_SWORD, 387, EQUIP_DAGGER),
    GILDED_STEEL_DAGGER(EquipmentItem::new, IRON_SWORD, 388, EQUIP_DAGGER),
    STEEL_DAGGER(EquipmentItem::new, IRON_SWORD, 389, EQUIP_DAGGER),
    ICE_WHIP(EquipmentItem::new, LEAD, 390, EQUIP_WHIP),
    GREEN_FLAIL(EquipmentItem::new, IRON_AXE, 391, EQUIP_WHIP),
    FIRE_WHIP(EquipmentItem::new, LEAD, 392, EQUIP_WHIP),
    CHAIN_WHIP(EquipmentItem::new, LEAD, 393, EQUIP_WHIP),
    LEATHER_WHIP(EquipmentItem::new, LEAD, 394, EQUIP_WHIP),
    EARTH_WHIP(EquipmentItem::new, LEAD, 395, EQUIP_WHIP),
    SPIKED_STEEL_ROUND_SHIELD(EquipmentItem::new, SHIELD, 396, EQUIP_SHIELD),
    CLOVER_ROUND_SHIELD(EquipmentItem::new, SHIELD, 397, EQUIP_SHIELD),
    FIRE_KITE_SHIELD(EquipmentItem::new, SHIELD, 398, EQUIP_SHIELD),
    WATER_WANKEL_SHIELD(EquipmentItem::new, SHIELD, 399, EQUIP_SHIELD),
    FIRE_WANKEL_SHIELD(EquipmentItem::new, SHIELD, 400, EQUIP_SHIELD),
    ICE_WANKEL_SHIELD(EquipmentItem::new, SHIELD, 401, EQUIP_SHIELD),
    COPPER_WANKEL_SHIELD(EquipmentItem::new, SHIELD, 402, EQUIP_SHIELD),
    CLOVER_WANKEL_SHIELD(EquipmentItem::new, SHIELD, 403, EQUIP_SHIELD),
    COPPER_ROUND_SHIELD(EquipmentItem::new, SHIELD, 404, EQUIP_SHIELD),
    COATED_KITE_SHIELD(EquipmentItem::new, SHIELD, 405, EQUIP_SHIELD),
    STEEL_ROUND_SHIELD(EquipmentItem::new, SHIELD, 406, EQUIP_SHIELD),
    EARTH_WANKEL_SHIELD(EquipmentItem::new, SHIELD, 407, EQUIP_SHIELD),
    STEEL_KITE_SHIELD(EquipmentItem::new, SHIELD, 408, EQUIP_SHIELD),
    COPPER_KITE_SHIELD(EquipmentItem::new, SHIELD, 409, EQUIP_SHIELD),
    GILDED_IRON_BATTLE_AXE(EquipmentItem::new, IRON_AXE, 410, EQUIP_AXE),
    SMALL_IRON_HATCHET(EquipmentItem::new, IRON_HELMET, 411, EQUIP_AXE),
    IRON_HATCHET(EquipmentItem::new, IRON_HELMET, 412, EQUIP_AXE),
    IRON_BROAD_AXE(EquipmentItem::new, IRON_AXE, 413, EQUIP_AXE),
    GILDED_IRON_MAUL(EquipmentItem::new, IRON_AXE, 414, EQUIP_AXE),
    STEEL_TRIDENT(EquipmentItem::new, TRIDENT, 415, EQUIP_SPEAR),
    GOLDEN_TRIDENT(EquipmentItem::new, TRIDENT, 416, EQUIP_SPEAR),
    WOODEN_JAVELIN(EquipmentItem::new, TRIDENT, 417, EQUIP_SPEAR),
    STEEL_SPEAR(EquipmentItem::new, TRIDENT, 418, EQUIP_SPEAR),
    STEEL_JAVELIN(EquipmentItem::new, TRIDENT, 419, EQUIP_SPEAR),
    SPIKED_WOODEN_CLUB(EquipmentItem::new, WOODEN_AXE, 420, EQUIP_MACE),
    IRON_MACE(EquipmentItem::new, IRON_AXE, 421, EQUIP_MACE),
    SPIKED_IRON_MACE(EquipmentItem::new, IRON_AXE, 422, EQUIP_MACE),
    GILDED_BAT(EquipmentItem::new, GOLDEN_AXE, 423, EQUIP_MACE),
    WOODEN_MACE(EquipmentItem::new, WOODEN_AXE, 424, EQUIP_MACE),
    WOODEN_BAT(EquipmentItem::new, WOODEN_AXE, 425, EQUIP_MACE),
    VIOLET_DOUBLE_POLEARM(EquipmentItem::new, IRON_SWORD, 426, EQUIP_POLEARM),
    STEEL_POLEARM(EquipmentItem::new, IRON_SWORD, 427, EQUIP_POLEARM),
    GOLDEN_NECKLACE_AMETHYSTS(EquipmentItem::new, GOLD_INGOT, 428, EQUIP_NECKLACE),
    GOLDEN_NECKLACE_DIAMONDS(EquipmentItem::new, GOLD_INGOT, 429, EQUIP_NECKLACE),
    GOLDEN_NECKLACE_JADE(EquipmentItem::new, GOLD_INGOT, 430, EQUIP_NECKLACE),
    LEATHER_COLLAR_GOLD_CROSS(EquipmentItem::new, IRON_INGOT, 431, EQUIP_NECKLACE),
    GOLDEN_COLLAR(EquipmentItem::new, GOLD_INGOT, 432, EQUIP_NECKLACE),
    SILVER_NECKLACE_PEARL(EquipmentItem::new, IRON_INGOT, 433, EQUIP_NECKLACE),
    LEATHER_COLLAR(EquipmentItem::new, IRON_INGOT, 434, EQUIP_NECKLACE),
    SILVER_NECKLACE_HEART(EquipmentItem::new, IRON_INGOT, 435, EQUIP_NECKLACE),
    LEATHER_COLLAR_DIAMOND_SHARD(EquipmentItem::new, IRON_INGOT, 436, EQUIP_NECKLACE),
    GREEN_DECORATED_IRON_HELMET(EquipmentItem::new, IRON_HELMET, 437, EQUIP_HELMET),
    DECORATED_GILDED_KING_HELMET(EquipmentItem::new, GOLDEN_HELMET, 438, EQUIP_HELMET),
    BLUE_DECORATED_IRON_HELMET(EquipmentItem::new, IRON_HELMET, 439, EQUIP_HELMET),
    RED_DECORATED_IRON_HELMET(EquipmentItem::new, IRON_HELMET, 440, EQUIP_HELMET),
    GOLDEN_HUGE_DIAMOND_RING(EquipmentItem::new, GOLD_NUGGET, 441, EQUIP_RING),
    SILVER_LINCHPIN_RING(EquipmentItem::new, IRON_NUGGET, 442, EQUIP_RING),
    GOLDEN_HEART_RING(EquipmentItem::new, GOLD_NUGGET, 443, EQUIP_RING),
    SILVER_BERYL_RING(EquipmentItem::new, IRON_NUGGET, 444, EQUIP_RING),
    GOLDEN_RING(EquipmentItem::new, GOLD_NUGGET, 445, EQUIP_RING),
    SILVER_RUBY_RING(EquipmentItem::new, IRON_NUGGET, 446, EQUIP_RING),
    SILVER_OPAL_RING(EquipmentItem::new, IRON_NUGGET, 447, EQUIP_RING),
    GOLDEN_AMETHYST_RING(EquipmentItem::new, GOLD_NUGGET, 448, EQUIP_RING),
    GOLDEN_BIG_PEARL_RING(EquipmentItem::new, GOLD_NUGGET, 449, EQUIP_RING),
    GOLDEN_DIAMOND_RING(EquipmentItem::new, GOLD_NUGGET, 450, EQUIP_RING),
    GOLDEN_BIG_HEART_RING(EquipmentItem::new, GOLD_NUGGET, 451, EQUIP_RING),
    SILVER_DIAMOND_RING(EquipmentItem::new, IRON_NUGGET, 452, EQUIP_RING),
    GOLDEN_PEARL_RING(EquipmentItem::new, GOLD_NUGGET, 453, EQUIP_RING),
    SILVER_AND_GOLD_RINGS(EquipmentItem::new, IRON_NUGGET, 454, EQUIP_RING),
    SILVER_RING_MISSING_GEM(EquipmentItem::new, IRON_NUGGET, 455, EQUIP_RING),
    GOLDEN_EMERALD_RING(EquipmentItem::new, GOLD_NUGGET, 456, EQUIP_RING),
    SILVER_SHIELD_RING(EquipmentItem::new, SHIELD, 457, EQUIP_RING),
    SILVER_BIG_SHIELD_RING(EquipmentItem::new, SHIELD, 458, EQUIP_RING),
    SILVER_RING(EquipmentItem::new, IRON_NUGGET, 459, EQUIP_RING),
    GOLDEN_RUBY_RING(EquipmentItem::new, GOLD_NUGGET, 460, EQUIP_RING),
    FIRE_WAND(EquipmentItem::new, STICK, 461, EQUIP_WAND),
    RED_WAND(EquipmentItem::new, STICK, 462, EQUIP_WAND),
    ICE_WAND(EquipmentItem::new, STICK, 463, EQUIP_WAND),
    BLUE_WAND(EquipmentItem::new, STICK, 464, EQUIP_WAND),
    BLUE_SPARK_WAND(EquipmentItem::new, STICK, 465, EQUIP_WAND),
    PURPLE_SPARK_WAND(EquipmentItem::new, STICK, 466, EQUIP_WAND),
    EARTH_WAND(EquipmentItem::new, STICK, 467, EQUIP_WAND),
    WATER_WAND(EquipmentItem::new, STICK, 468, EQUIP_WAND),
    PURPLE_WAND(EquipmentItem::new, STICK, 469, EQUIP_WAND),
    RED_SPARK_WAND(EquipmentItem::new, STICK, 470, EQUIP_WAND),
    SMALL_RED_CLOAK(EquipmentItem::new, IRON_CHESTPLATE, 471, EQUIP_CLOAK),
    RED_CLOAK(EquipmentItem::new, IRON_CHESTPLATE, 472, EQUIP_CLOAK),
    BLUE_CLOAK(EquipmentItem::new, IRON_CHESTPLATE, 473, EQUIP_CLOAK),
    ORANGE_HOODED_CLOAK(EquipmentItem::new, IRON_HELMET, 474, EQUIP_CLOAK),
    SMALL_EARTH_CLOAK(EquipmentItem::new, IRON_CHESTPLATE, 475, EQUIP_CLOAK),
    RED_HOODED_CLOAK(EquipmentItem::new, IRON_HELMET, 476, EQUIP_CLOAK),
    FIRE_CLOAK(EquipmentItem::new, IRON_CHESTPLATE, 477, EQUIP_CLOAK),
    BLUE_FUR_COLLAR_CLOAK(EquipmentItem::new, IRON_CHESTPLATE, 478, EQUIP_CLOAK),
    EARTH_CLOAK(EquipmentItem::new, IRON_CHESTPLATE, 479, EQUIP_CLOAK),
    WATER_CLOAK(EquipmentItem::new, IRON_CHESTPLATE, 480, EQUIP_CLOAK),
    SMALL_ICE_CLOAK(EquipmentItem::new, IRON_CHESTPLATE, 481, EQUIP_CLOAK),
    ORANGE_FUR_COLOR_CLOAK(EquipmentItem::new, IRON_CHESTPLATE, 482, EQUIP_CLOAK),
    SMALL_ORANGE_CLOAK(EquipmentItem::new, IRON_CHESTPLATE, 483, EQUIP_CLOAK),
    SMALL_WATER_CLOAK(EquipmentItem::new, IRON_CHESTPLATE, 484, EQUIP_CLOAK),
    RED_FUR_COLLAR_CLOAK(EquipmentItem::new, IRON_CHESTPLATE, 485, EQUIP_CLOAK),
    BLUE_HOODED_CLOAK(EquipmentItem::new, IRON_HELMET, 486, EQUIP_CLOAK),
    ORANGE_CLOAK(EquipmentItem::new, IRON_CHESTPLATE, 487, EQUIP_CLOAK),
    ICE_CLOAK(EquipmentItem::new, IRON_CHESTPLATE, 488, EQUIP_CLOAK),
    SMALL_FIRE_CLOAK(EquipmentItem::new, IRON_CHESTPLATE, 489, EQUIP_CLOAK),
    SMALL_BLUE_CLOAK(EquipmentItem::new, IRON_CHESTPLATE, 490, EQUIP_CLOAK),
    WATER_SCEPTER(EquipmentItem::new, STICK, 491, EQUIP_SCEPTER),
    EARTH_SCEPTER(EquipmentItem::new, STICK, 492, EQUIP_SCEPTER),
    FIRE_SCEPTER(EquipmentItem::new, STICK, 493, EQUIP_SCEPTER),
    ICE_SCEPTER(EquipmentItem::new, STICK, 494, EQUIP_SCEPTER),
    EARTH_STAFF(EquipmentItem::new, STICK, 495, EQUIP_STAFF),
    ICE_STAFF(EquipmentItem::new, STICK, 496, EQUIP_STAFF),
    FIRE_STAFF(EquipmentItem::new, STICK, 497, EQUIP_STAFF),
    WATER_STAFF(EquipmentItem::new, STICK, 498, EQUIP_STAFF),
    WATER_CANE(EquipmentItem::new, STICK, 499, EQUIP_CANE),
    FIRE_CANE(EquipmentItem::new, STICK, 500, EQUIP_CANE),
    ICE_CANE(EquipmentItem::new, STICK, 501, EQUIP_CANE),
    EARTH_CANE(EquipmentItem::new, STICK, 502, EQUIP_CANE),
    GILDED_LORD_GAUNTLETS(EquipmentItem::new, GOLDEN_BOOTS, 503, EQUIP_GILDED_LORD_SET),
    GILDED_LORD_CHESTPLATE(EquipmentItem::new, GOLDEN_CHESTPLATE, 504, EQUIP_GILDED_LORD_SET),
    GILDED_LORD_HELMET(EquipmentItem::new, GOLDEN_HELMET, 505, EQUIP_GILDED_LORD_SET),
    GILDED_LORD_GREAVES(EquipmentItem::new, GOLDEN_LEGGINGS, 506, EQUIP_GILDED_LORD_SET),
    GILDED_LORD_SABATONS(EquipmentItem::new, GOLDEN_BOOTS, 507, EQUIP_GILDED_LORD_SET),
    TURQUOISE_CLOTH_GLOVES(EquipmentItem::new, LEATHER_BOOTS, 508, EQUIP_TURQUOISE_CLOTH_SET),
    TURQUOISE_CLOTH_TUNIC(EquipmentItem::new, LEATHER_CHESTPLATE, 509, EQUIP_TURQUOISE_CLOTH_SET),
    TURQUOISE_CLOTH_HAT(EquipmentItem::new, LEATHER_HELMET, 510, EQUIP_TURQUOISE_CLOTH_SET),
    TURQUOISE_CLOTH_SNEAKERS(EquipmentItem::new, LEATHER_BOOTS, 511, EQUIP_TURQUOISE_CLOTH_SET),
    TURQUOISE_CLOTH_PANTS(EquipmentItem::new, LEATHER_LEGGINGS, 512, EQUIP_TURQUOISE_CLOTH_SET),
    YELLOW_WINGED_CLOTH_CAP(EquipmentItem::new, LEATHER_HELMET, 513, EQUIP_YELLOW_WINGED_CLOTH_SET),
    YELLOW_WINGED_CLOTH_GLOVES(EquipmentItem::new, LEATHER_BOOTS, 514, EQUIP_YELLOW_WINGED_CLOTH_SET),
    YELLOW_WINGED_CLOTH_PANTS(EquipmentItem::new, LEATHER_LEGGINGS, 515, EQUIP_YELLOW_WINGED_CLOTH_SET),
    YELLOW_WINGED_CLOTH_TUNIC(EquipmentItem::new, LEATHER_CHESTPLATE, 516, EQUIP_YELLOW_WINGED_CLOTH_SET),
    YELLOW_WINGED_CLOTH_SNEAKERS(EquipmentItem::new, LEATHER_BOOTS, 517, EQUIP_YELLOW_WINGED_CLOTH_SET),
    STUDDED_LEATHER_GLOVES(EquipmentItem::new, LEATHER_BOOTS, 518, EQUIP_STUDDED_LEATHER_SET),
    STUDDED_LEATHER_BOOTS(EquipmentItem::new, LEATHER_BOOTS, 519, EQUIP_STUDDED_LEATHER_SET),
    STUDDED_LEATHER_HELMET(EquipmentItem::new, LEATHER_HELMET, 520, EQUIP_STUDDED_LEATHER_SET),
    STUDDED_LEATHER_PANTS(EquipmentItem::new, LEATHER_LEGGINGS, 521, EQUIP_STUDDED_LEATHER_SET),
    STUDDED_LEATHER_CHESTPLATE(EquipmentItem::new, LEATHER_CHESTPLATE, 522, EQUIP_STUDDED_LEATHER_SET),
    ORANGE_CLOTH_GLOVES(EquipmentItem::new, LEATHER_BOOTS, 523, EQUIP_ORANGE_CLOTH_SET),
    ORANGE_CLOTH_PANTS(EquipmentItem::new, LEATHER_LEGGINGS, 524, EQUIP_ORANGE_CLOTH_SET),
    ORANGE_CLOTH_HOOD(EquipmentItem::new, LEATHER_HELMET, 525, EQUIP_ORANGE_CLOTH_SET),
    ORANGE_CLOTH_SNEAKERS(EquipmentItem::new, LEATHER_BOOTS, 526, EQUIP_ORANGE_CLOTH_SET),
    ORANGE_CLOTH_TUNIC(EquipmentItem::new, LEATHER_CHESTPLATE, 527, EQUIP_ORANGE_CLOTH_SET),
    PEACH_CLOTH_TUNIC(EquipmentItem::new, LEATHER_CHESTPLATE, 528, EQUIP_PEACH_CLOTH_SET),
    PEACH_CLOTH_SNEAKERS(EquipmentItem::new, LEATHER_BOOTS, 529, EQUIP_PEACH_CLOTH_SET),
    PEACH_CLOTH_HOOD(EquipmentItem::new, LEATHER_HELMET, 530, EQUIP_PEACH_CLOTH_SET),
    PEACH_CLOTH_GLOVES(EquipmentItem::new, LEATHER_BOOTS, 531, EQUIP_PEACH_CLOTH_SET),
    PEACH_CLOTH_PANTS(EquipmentItem::new, LEATHER_LEGGINGS, 532, EQUIP_PEACH_CLOTH_SET),
    IRON_KNIGHT_HELMET(EquipmentItem::new, IRON_HELMET, 533, EQUIP_IRON_KNIGHT_SET),
    IRON_KNIGHT_GREAVES(EquipmentItem::new, IRON_LEGGINGS, 534, EQUIP_IRON_KNIGHT_SET),
    IRON_KNIGHT_SABATONS(EquipmentItem::new, IRON_BOOTS, 535, EQUIP_IRON_KNIGHT_SET),
    IRON_KNIGHT_GAUNTLETS(EquipmentItem::new, IRON_BOOTS, 536, EQUIP_IRON_KNIGHT_SET),
    IRON_KNIGHT_CHESTPLATE(EquipmentItem::new, IRON_CHESTPLATE, 537, EQUIP_IRON_KNIGHT_SET),
    PINK_WINGED_CLOTH_HAT(EquipmentItem::new, LEATHER_HELMET, 538, EQUIP_PINK_WINGED_CLOTH_SET),
    PINK_WINGED_CLOTH_PANTS(EquipmentItem::new, LEATHER_LEGGINGS, 539, EQUIP_PINK_WINGED_CLOTH_SET),
    PINK_WINGED_CLOTH_GLOVES(EquipmentItem::new, LEATHER_BOOTS, 540, EQUIP_PINK_WINGED_CLOTH_SET),
    PINK_WINGED_CLOTH_SNEAKERS(EquipmentItem::new, LEATHER_BOOTS, 541, EQUIP_PINK_WINGED_CLOTH_SET),
    PINK_WINGED_CLOTH_TUNIC(EquipmentItem::new, LEATHER_CHESTPLATE, 542, EQUIP_PINK_WINGED_CLOTH_SET),
    GREEN_FEATHER_CLOTH_TUNIC(EquipmentItem::new, LEATHER_CHESTPLATE, 543, EQUIP_GREEN_FEATHER_SET),
    GREEN_FEATHER_CLOTH_CAP(EquipmentItem::new, LEATHER_HELMET, 544, EQUIP_GREEN_FEATHER_SET),
    GREEN_FEATHER_CLOTH_GLOVES(EquipmentItem::new, LEATHER_BOOTS, 545, EQUIP_GREEN_FEATHER_SET),
    GREEN_FEATHER_CLOTH_SNEAKERS(EquipmentItem::new, LEATHER_BOOTS, 546, EQUIP_GREEN_FEATHER_SET),
    GREEN_FEATHER_CLOTH_PANTS(EquipmentItem::new, LEATHER_LEGGINGS, 547, EQUIP_GREEN_FEATHER_SET),
    FINE_IRON_GAUNTLETS(EquipmentItem::new, IRON_BOOTS, 548, EQUIP_FINE_IRON_SET),
    FINE_IRON_CHESTPLATE(EquipmentItem::new, IRON_CHESTPLATE, 549, EQUIP_FINE_IRON_SET),
    FINE_IRON_SABATONS(EquipmentItem::new, IRON_BOOTS, 550, EQUIP_FINE_IRON_SET),
    FINE_IRON_GREAVES(EquipmentItem::new, IRON_LEGGINGS, 551, EQUIP_FINE_IRON_SET),
    FINE_IRON_HELMET(EquipmentItem::new, IRON_HELMET, 552, EQUIP_FINE_IRON_SET),
    GILDED_KING_SABATONS(EquipmentItem::new, GOLDEN_BOOTS, 553, EQUIP_GILDED_KING_SET),
    GILDED_KING_GREAVES(EquipmentItem::new, GOLDEN_LEGGINGS, 554, EQUIP_GILDED_KING_SET),
    GILDED_KING_GAUNTLETS(EquipmentItem::new, GOLDEN_BOOTS, 555, EQUIP_GILDED_KING_SET),
    GILDED_KING_CHESTPLATE(EquipmentItem::new, GOLDEN_CHESTPLATE, 556, EQUIP_GILDED_KING_SET),
    GILDED_KING_HELMET(EquipmentItem::new, GOLDEN_HELMET, 557, EQUIP_GILDED_KING_SET),
    SIMPLE_LEATHER_PANTS(EquipmentItem::new, LEATHER_LEGGINGS, 558, EQUIP_SIMPLE_LEATHER_SET),
    SIMPLE_LEATHER_VEST(EquipmentItem::new, LEATHER_CHESTPLATE, 559, EQUIP_SIMPLE_LEATHER_SET),
    SIMPLE_LEATHER_GLOVES(EquipmentItem::new, LEATHER_BOOTS, 560, EQUIP_SIMPLE_LEATHER_SET),
    SIMPLE_LEATHER_BOOTS(EquipmentItem::new, LEATHER_BOOTS, 561, EQUIP_SIMPLE_LEATHER_SET),
    SIMPLE_LEATHER_CAP(EquipmentItem::new, LEATHER_HELMET, 562, EQUIP_SIMPLE_LEATHER_SET),
    BASIC_IRON_GREAVES(EquipmentItem::new, IRON_LEGGINGS, 563, EQUIP_BASIC_IRON_SET),
    BASIC_IRON_CHESTPLATE(EquipmentItem::new, IRON_CHESTPLATE, 564, EQUIP_BASIC_IRON_SET),
    BASIC_IRON_HELMET(EquipmentItem::new, IRON_HELMET, 565, EQUIP_BASIC_IRON_SET),
    BASIC_IRON_SABATONS(EquipmentItem::new, IRON_BOOTS, 566, EQUIP_BASIC_IRON_SET),
    BASIC_IRON_GAUNTLETS(EquipmentItem::new, IRON_BOOTS, 567, EQUIP_BASIC_IRON_SET),
    BLUE_CLOTH_TUNIC(EquipmentItem::new, LEATHER_CHESTPLATE, 568, EQUIP_BLUE_CLOTH_SET),
    BLUE_CLOTH_PANTS(EquipmentItem::new, LEATHER_LEGGINGS, 569, EQUIP_BLUE_CLOTH_SET),
    BLUE_CLOTH_HOOD(EquipmentItem::new, LEATHER_HELMET, 570, EQUIP_BLUE_CLOTH_SET),
    BLUE_CLOTH_SNEAKERS(EquipmentItem::new, LEATHER_BOOTS, 571, EQUIP_BLUE_CLOTH_SET),
    BLUE_CLOTH_GLOVES(EquipmentItem::new, LEATHER_BOOTS, 572, EQUIP_BLUE_CLOTH_SET),
    FINE_LEATHER_PANTS(EquipmentItem::new, LEATHER_LEGGINGS, 573, EQUIP_FINE_LEATHER_SET),
    FINE_LEATHER_BOOTS(EquipmentItem::new, LEATHER_BOOTS, 574, EQUIP_FINE_LEATHER_SET),
    FINE_LEATHER_VEST(EquipmentItem::new, LEATHER_CHESTPLATE, 575, EQUIP_FINE_LEATHER_SET),
    FINE_LEATHER_GLOVES(EquipmentItem::new, LEATHER_BOOTS, 576, EQUIP_FINE_LEATHER_SET),
    FINE_LEATHER_HELMET(EquipmentItem::new, LEATHER_HELMET, 577, EQUIP_FINE_LEATHER_SET),
    BLUE_JESTER_PANTS(EquipmentItem::new, LEATHER_LEGGINGS, 578, EQUIP_BLUE_JESTER_SET),
    BLUE_JESTER_GLOVES(EquipmentItem::new, LEATHER_BOOTS, 579, EQUIP_BLUE_JESTER_SET),
    BLUE_JESTER_HAT(EquipmentItem::new, LEATHER_HELMET, 580, EQUIP_BLUE_JESTER_SET),
    BLUE_JESTER_SHOES(EquipmentItem::new, LEATHER_BOOTS, 581, EQUIP_BLUE_JESTER_SET),
    BLUE_JESTER_TUNIC(EquipmentItem::new, LEATHER_CHESTPLATE, 582, EQUIP_BLUE_JESTER_SET),
    RED_JESTER_GLOVES(EquipmentItem::new, LEATHER_BOOTS, 583, EQUIP_RED_JESTER_SET),
    RED_JESTER_HAT(EquipmentItem::new, LEATHER_HELMET, 584, EQUIP_RED_JESTER_SET),
    RED_JESTER_TUNIC(EquipmentItem::new, LEATHER_CHESTPLATE, 585, EQUIP_RED_JESTER_SET),
    RED_JESTER_SHOES(EquipmentItem::new, LEATHER_BOOTS, 586, EQUIP_RED_JESTER_SET),
    RED_JESTER_PANTS(EquipmentItem::new, LEATHER_LEGGINGS, 587, EQUIP_RED_JESTER_SET),
    ;
    // (Deprecated) Next High Unicode Character: \uE2AE

    private static final Map<String, Mytems> ID_MAP = new HashMap<>();
    public final String id;
    public final Function<Mytems, Mytem> ctor;
    public final Material material;
    public final Integer customModelData;
    public final char character;
    public final char[] characters; // Contains `character'
    public final Component component;
    public final MytemsCategory category;
    public final Animation animation;
    private char[] animationFrames;

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
        ID_MAP.put("fireman_helmet", FIREFIGHTER_HELMET);
    }

    Mytems(final Function<Mytems, Mytem> ctor, final Material material,
           final Integer customModelData,
           final char character, final char[] characters,
           final MytemsCategory category,
           final Animation animation) {
        this.ctor = ctor;
        this.id = name().toLowerCase();
        this.material = material;
        this.customModelData = customModelData;
        this.character = character;
        this.characters = characters;
        this.component = character > 0
            ? (Component.text(character)
               .style(Style.style()
                      .font(Key.key("cavetale:default"))
                      .color(NamedTextColor.WHITE)))
            : Component.empty();
        this.category = category;
        this.animation = animation;
    }

    Mytems(final Function<Mytems, Mytem> ctor, final Material material,
           final Integer customModelData,
           final char character, final char[] characters,
           final MytemsCategory category) {
        this(ctor, material, customModelData, character, characters, category, null);
    }

    Mytems(final Function<Mytems, Mytem> ctor, final Material material,
           final Integer customModelData,
           final char character,
           final MytemsCategory category) {
        this(ctor, material, customModelData, character, new char[] {character}, category);
    }

    Mytems(final Function<Mytems, Mytem> ctor, final Material material,
           final Integer customModelData,
           final char character,
           final MytemsCategory category,
           final Animation animation) {
        this(ctor, material, customModelData, character, new char[] {character}, category, animation);
    }

    Mytems(final Function<Mytems, Mytem> ctor, final Material material,
           final int customModelData, final MytemsCategory category) {
        this(ctor, material, customModelData, (char) customModelData, chrarr(customModelData), category);
    }

    Mytems(final Function<Mytems, Mytem> ctor, final Material material,
           final int customModelData, final MytemsCategory category, final Animation animation) {
        this(ctor, material, customModelData, (char) customModelData, chrarr(customModelData), category, animation);
    }

    private static char[] chrarr(int... values) {
        char[] result = new char[values.length];
        for (int i = 0; i < values.length; i += 1) {
            result[i] = (char) values[i];
        }
        return result;
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
            item = Skull.of(createItemStack()).create();
        } else if (material == LEATHER_HELMET
                   || material == LEATHER_CHESTPLATE
                   || material == LEATHER_LEGGINGS
                   || material == LEATHER_BOOTS) {
            item = createItemStack();
            item.editMeta(meta -> {
                    meta.displayName(Component.empty());
                    meta.lore(List.of());
                });
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

    public void setItem(ItemStack item) {
        item.setType(material);
        item.setItemMeta(createItemStack().getItemMeta());
    }

    @Override
    public Component asComponent() {
        return component;
    }

    @Override
    public NamespacedKey getKey() {
        return new NamespacedKey(MytemsPlugin.getInstance(), id);
    }

    @Override
    public String name(ItemStack item) {
        return plainText().serialize(getMytem().getDisplayName(item));
    }

    @Override
    public Component displayName(ItemStack item) {
        return getMytem().getDisplayName(item);
    }

    @Override
    public Component icon(ItemStack item) {
        return component;
    }

    @Override
    public ItemStack create(String tag) {
        return getMytem().deserializeTag(tag);
    }

    @Override
    public int getMaxStackSize(ItemStack item) {
        return getMytem().getMaxStackSize();
    }

    @Override
    public boolean isSimilar(ItemStack a, ItemStack b) {
        if (forItem(a) != this || forItem(b) != this) return false;
        MytemTag tagA = getMytem().serializeTag(a);
        MytemTag tagB = getMytem().serializeTag(b);
        if (tagA != null && tagA.isDismissable()) tagA = null;
        if (tagB != null && tagB.isDismissable()) tagB = null;
        if (tagA == null && tagB == null) return true;
        return tagA != null
            ? tagA.isSimilar(tagB)
            : tagB.isSimilar(tagA);
    }

    public boolean isAnimated() {
        return animationFrames.length > 1;
    }

    public int getAnimationFrameCount() {
        return animationFrames.length;
    }

    public Component getAnimationFrame(int index) {
        char chr = animationFrames[index];
        if ((int) chr == 0) {
            throw new IllegalArgumentException(this + ": Animation frame " + index + " is 0!");
        }
        return Component.text(chr)
            .style(Style.style()
                   .font(Key.key("cavetale:default"))
                   .color(NamedTextColor.WHITE));
    }

    public List<Component> getAnimationFrames() {
        List<Component> list = new ArrayList<>(characters.length);
        for (int i = 0; i < animationFrames.length; i += 1) {
            if (characters[i] == (char) 0) continue;
            list.add(getAnimationFrame(i));
        }
        return list;
    }

    /**
     * Internal use only.
     */
    public void preprocessAnimationFrames() {
        if (animation == null || characters.length == 1) {
            this.animationFrames = characters;
            return;
        }
        List<Character> list = new ArrayList<>();
        if (animation.frames != null) {
            for (Animation.Frame frame : animation.frames) {
                final int time = frame.time != 0 ? frame.time : animation.frametime;
                char theCharacter = characters[frame.index];
                for (int i = 0; i < time; i += 1) {
                    list.add(theCharacter);
                }
            }
        } else {
            for (char theCharacter : characters) {
                for (int i = 0; i < animation.frametime; i += 1) {
                    list.add(theCharacter);
                }
            }
        }
        this.animationFrames = new char[list.size()];
        for (int i = 0; i < list.size(); i += 1) {
            animationFrames[i] = list.get(i);
        }
    }
}
