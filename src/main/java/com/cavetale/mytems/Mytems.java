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
import com.cavetale.mytems.item.arcade.Caveboy;
import com.cavetale.mytems.item.arcade.SealedCaveboy;
import com.cavetale.mytems.item.beestick.Beestick;
import com.cavetale.mytems.item.bingo.BingoBukkit;
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
import static com.cavetale.mytems.MytemsCategory.*;
import static net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer.plainText;
import static org.bukkit.Material.*;

@SuppressWarnings("LineLength")
/**
 * List of all known Mytems.
 * Unicode characters start at 0xE200.
 */
public enum Mytems implements ComponentLike, Keyed, ItemKind {
    DR_ACULA_STAFF(DrAculaStaff.class, NETHERITE_SWORD, 741302, (char) 0xE220, ACULA),
    FLAME_SHIELD(FlameShield.class, SHIELD, 741303, (char) 0xE234, ACULA),
    STOMPERS(Stompers.class, NETHERITE_BOOTS, 741304, (char) 0xE235, ACULA),
    GHAST_BOW(GhastBow.class, BOW, 741305, (char) 0xE236, ACULA),
    BAT_MASK(BatMask.class, PLAYER_HEAD, 741306, (char) 0xE237, ACULA),
    UNICORN_HORN(UnicornHorn.class, END_ROD, 7413003, (char) 0, UTILITY),
    MAGIC_CAPE(MagicCape.class, ELYTRA, 7413006, (char) 0xE238, chrarr(0xE238, 0xE2AE, 0xE2AF, 0xE2B0, 0xE2B1), UTILITY, Animation.MAGIC_CAPE),
    SNEAKERS(Sneakers.class, LEATHER_BOOTS, 333, (char) 0xE2B2, UTILITY),
    KITTY_COIN(KittyCoin.class, PLAYER_HEAD, 7413001, (char) 0xE200, CURRENCY),
    RAINBOW_KITTY_COIN(KittyCoin.class, PLAYER_HEAD, 7413007, (char) 0xE243, CURRENCY),
    CHRISTMAS_TOKEN(ChristmasToken.class, PLAYER_HEAD, 221, (char) 0xE2B3, CHRISTMAS),
    SANTA_HAT(SantaHat.class, PLAYER_HEAD, 7413101, (char) 0xE221, SANTA),
    SANTA_JACKET(SantaJacket.class, LEATHER_CHESTPLATE, 4713102, (char) 0xE222, SANTA),
    SANTA_PANTS(SantaPants.class, LEATHER_LEGGINGS, 4713103, (char) 0xE223, SANTA),
    SANTA_BOOTS(SantaBoots.class, LEATHER_BOOTS, 4713104, (char) 0xE224, SANTA),
    BLUE_CHRISTMAS_BALL(DummyMytem.class, BLUE_STAINED_GLASS, 214, (char) 0xE2B4, CHRISTMAS),
    GREEN_CHRISTMAS_BALL(DummyMytem.class, GREEN_STAINED_GLASS, 215, (char) 0xE2B5, CHRISTMAS),
    ORANGE_CHRISTMAS_BALL(DummyMytem.class, ORANGE_STAINED_GLASS, 216, (char) 0xE2B6, CHRISTMAS),
    PINK_CHRISTMAS_BALL(DummyMytem.class, PINK_STAINED_GLASS, 217, (char) 0xE2B7, CHRISTMAS),
    PURPLE_CHRISTMAS_BALL(DummyMytem.class, PURPLE_STAINED_GLASS, 218, (char) 0xE2B8, CHRISTMAS),
    YELLOW_CHRISTMAS_BALL(DummyMytem.class, YELLOW_STAINED_GLASS, 219, (char) 0xE2B9, CHRISTMAS),
    KNITTED_BEANIE(ForbiddenMytem.class, LEATHER_HELMET, 222, (char) 0xE2BA, CHRISTMAS),
    DUNE_HELMET(DuneItem.Helmet.class, PLAYER_HEAD, 7413201, (char) 0xE225, DUNE),
    DUNE_CHESTPLATE(DuneItem.Chestplate.class, GOLDEN_CHESTPLATE, 7413202, (char) 0xE226, DUNE),
    DUNE_LEGGINGS(DuneItem.Leggings.class, GOLDEN_LEGGINGS, 7413203, (char) 0xE227, DUNE),
    DUNE_BOOTS(DuneItem.Boots.class, GOLDEN_BOOTS, 7413204, (char) 0xE228, DUNE),
    DUNE_DIGGER(DuneItem.Weapon.class, GOLDEN_SHOVEL, 7413205, (char) 0xE229, DUNE, Animation.DUNE_DIGGER),
    SWAMPY_HELMET(SwampyItem.Helmet.class, PLAYER_HEAD, 7413301, (char) 0xE22A, SWAMPY),
    SWAMPY_CHESTPLATE(SwampyItem.Chestplate.class, LEATHER_CHESTPLATE, 7413302, (char) 0xE22B, SWAMPY),
    SWAMPY_LEGGINGS(SwampyItem.Leggings.class, LEATHER_LEGGINGS, 7413303, (char) 0xE22C, SWAMPY),
    SWAMPY_BOOTS(SwampyItem.Boots.class, LEATHER_BOOTS, 7413304, (char) 0xE22D, SWAMPY),
    SWAMPY_TRIDENT(SwampyItem.Weapon.class, TRIDENT, 7413305, (char) 0xE22E, SWAMPY),
    DWARVEN_HELMET(DwarvenItem.Helmet.class, PLAYER_HEAD, 7413401, (char) 0xE22F, DWARVEN),
    DWARVEN_CHESTPLATE(DwarvenItem.Chestplate.class, IRON_CHESTPLATE, 7413402, (char) 0xE230, DWARVEN),
    DWARVEN_LEGGINGS(DwarvenItem.Leggings.class, IRON_LEGGINGS, 7413403, (char) 0xE231, DWARVEN),
    DWARVEN_BOOTS(DwarvenItem.Boots.class, IRON_BOOTS, 7413404, (char) 0xE232, DWARVEN),
    DWARVEN_AXE(DwarvenItem.Weapon.class, IRON_AXE, 7413405, (char) 0xE233, DWARVEN),
    EASTER_TOKEN(EasterToken.class, PLAYER_HEAD, 345700, (char) 0xE211, EASTER_TOKENS),
    BLUE_EASTER_EGG(EasterEgg.class, PLAYER_HEAD, 345701, (char) 0xE212, EASTER_EGGS),
    GREEN_EASTER_EGG(EasterEgg.class, PLAYER_HEAD, 345702, (char) 0xE213, EASTER_EGGS),
    ORANGE_EASTER_EGG(EasterEgg.class, PLAYER_HEAD, 345703, (char) 0xE214, EASTER_EGGS),
    PINK_EASTER_EGG(EasterEgg.class, PLAYER_HEAD, 345704, (char) 0xE215, EASTER_EGGS),
    PURPLE_EASTER_EGG(EasterEgg.class, PLAYER_HEAD, 345705, (char) 0xE216, EASTER_EGGS),
    YELLOW_EASTER_EGG(EasterEgg.class, PLAYER_HEAD, 345706, (char) 0xE217, EASTER_EGGS),
    BLUE_EASTER_BASKET(EasterBasket.class, PLAYER_HEAD, 286, (char) 0, EASTER_BASKETS),
    GREEN_EASTER_BASKET(EasterBasket.class, PLAYER_HEAD, 287, (char) 0, EASTER_BASKETS),
    ORANGE_EASTER_BASKET(EasterBasket.class, PLAYER_HEAD, 288, (char) 0, EASTER_BASKETS),
    PINK_EASTER_BASKET(EasterBasket.class, PLAYER_HEAD, 289, (char) 0, EASTER_BASKETS),
    PURPLE_EASTER_BASKET(EasterBasket.class, PLAYER_HEAD, 290, (char) 0, EASTER_BASKETS),
    YELLOW_EASTER_BASKET(EasterBasket.class, PLAYER_HEAD, 291, (char) 0, EASTER_BASKETS),
    EASTER_HELMET(EasterGear.Helmet.class, PLAYER_HEAD, 345711, (char) 0xE218, EASTER),
    EASTER_CHESTPLATE(EasterGear.Chestplate.class, LEATHER_CHESTPLATE, 345712, (char) 0xE219, EASTER),
    EASTER_LEGGINGS(EasterGear.Leggings.class, LEATHER_LEGGINGS, 345713, (char) 0xE21A, EASTER),
    EASTER_BOOTS(EasterGear.Boots.class, LEATHER_BOOTS, 345714, (char) 0xE21B, EASTER),
    TOILET(Toilet.class, CAULDRON, 498101, (char) 0, FURNITURE),
    BOSS_CHEST(ForbiddenMytem.class, CHEST, 7413004, (char) 0, FURNITURE),
    OAK_CHAIR(ForbiddenMytem.class, OAK_PLANKS, 135, (char) 0, FURNITURE),
    SPRUCE_CHAIR(ForbiddenMytem.class, SPRUCE_PLANKS, 136, (char) 0, FURNITURE),
    WHITE_ARMCHAIR(ForbiddenMytem.class, WHITE_WOOL, 137, (char) 0, FURNITURE),
    WHITE_SOFA_LEFT(ForbiddenMytem.class, WHITE_WOOL, 138, (char) 0, FURNITURE),
    WHITE_SOFA_RIGHT(ForbiddenMytem.class, WHITE_WOOL, 139, (char) 0, FURNITURE),
    RED_ARMCHAIR(ForbiddenMytem.class, RED_WOOL, 140, (char) 0, FURNITURE),
    RED_SOFA_LEFT(ForbiddenMytem.class, RED_WOOL, 141, (char) 0, FURNITURE),
    RED_SOFA_RIGHT(ForbiddenMytem.class, RED_WOOL, 142, (char) 0, FURNITURE),
    BLACK_ARMCHAIR(ForbiddenMytem.class, RED_WOOL, 143, (char) 0, FURNITURE),
    BLACK_SOFA_LEFT(ForbiddenMytem.class, RED_WOOL, 144, (char) 0, FURNITURE),
    BLACK_SOFA_RIGHT(ForbiddenMytem.class, RED_WOOL, 145, (char) 0, FURNITURE),
    WEDDING_RING(WeddingRing.class, PLAYER_HEAD, 7413002, (char) 0xE21C, FRIENDS),
    MAGIC_MAP(MagicMap.class, FILLED_MAP, 7413005, (char) 0xE21D, chrarr(0xE21D, 0xE2BB, 0xE2BC, 0xE2BD, 0xE2BE, 0xE2BF, 0xE2C0, 0xE2C1, 0xE2C2, 0xE2C3, 0xE2C4, 0xE2C5, 0xE2C6, 0xE2C7, 0xE2C8, 0xE2C9), UTILITY, Animation.MAGIC_MAP),
    SNOW_SHOVEL(SnowShovel.class, IRON_SHOVEL, 220, (char) 0xE2CA, UTILITY),
    HASTY_PICKAXE(ForbiddenMytem.class, GOLDEN_PICKAXE, 223, (char) 0xE2CB, UTILITY),
    TREE_CHOPPER(TreeChopper.class, GOLDEN_AXE, 242, (char) 0xE2CC, UTILITY),
    ARMOR_STAND_EDITOR(ArmorStandEditor.class, FLINT, 241, (char) 0xE2CD, UTILITY),
    FERTILIZER(Fertilizer.class, BONE_MEAL, 285, (char) 0xE2CE, UTILITY),
    WATERING_CAN(WateringCan.class, STONE_HOE, 297, (char) 0xE2CF, GARDENING),
    EMPTY_WATERING_CAN(EmptyWateringCan.class, STONE_HOE, 334, (char) 0xE2D0, GARDENING),
    GOLDEN_WATERING_CAN(WateringCan.class, GOLDEN_HOE, 307, (char) 0xE2D1, GARDENING),
    EMPTY_GOLDEN_WATERING_CAN(EmptyWateringCan.class, STONE_HOE, 335, (char) 0xE2D2, GARDENING),
    MONKEY_WRENCH(MonkeyWrench.class, STONE_HOE, 79, (char) 0xE2D3, UTILITY),
    DIVIDERS(Dividers.class, WOODEN_HOE, 593, (char) 0xE2D4, UTILITY),
    SLIME_FINDER(SlimeFinder.class, SLIME_BALL, 594, (char) 0xE2D5, UTILITY),
    BLIND_EYE(BlindEye.class, CARROT, 596, (char) 0xE2D6, UTILITY),
    SCISSORS(Scissors.class, SHEARS, 652, (char) 0xE2D7, GARDENING),
    MAGNIFYING_GLASS(Magnifier.class, AMETHYST_SHARD, 653, (char) 0xE2D8, UTILITY),
    WHITE_BUNNY_EARS(WardrobeItem.class, IRON_BOOTS, 3919001, (char) 0, WARDROBE_HAT),
    RED_LIGHTSABER(WardrobeItem.class, END_ROD, 3919002, (char) 0, WARDROBE_HANDHELD),
    BLUE_LIGHTSABER(WardrobeItem.class, END_ROD, 3919003, (char) 0, WARDROBE_HANDHELD),
    PIRATE_HAT(WardrobeItem.class, BLACK_DYE, 3919004, (char) 0, WARDROBE_HAT),
    COWBOY_HAT(WardrobeItem.class, BROWN_DYE, 3919005, (char) 0, WARDROBE_HAT),
    ANGEL_HALO(WardrobeItem.class, LIGHT_WEIGHTED_PRESSURE_PLATE, 111, (char) 0, WARDROBE_HAT),
    TOP_HAT(WardrobeItem.class, BLACK_WOOL, 203, (char) 0, WARDROBE_HAT),
    BLACK_CAT_EARS(WardrobeItem.class, BLACK_CARPET, 112, (char) 0, CAT_EARS),
    CYAN_CAT_EARS(WardrobeItem.class, CYAN_CARPET, 113, (char) 0, CAT_EARS),
    LIGHT_BLUE_CAT_EARS(WardrobeItem.class, LIGHT_BLUE_CARPET, 114, (char) 0, CAT_EARS),
    LIME_CAT_EARS(WardrobeItem.class, LIME_CARPET, 115, (char) 0, CAT_EARS),
    ORANGE_CAT_EARS(WardrobeItem.class, ORANGE_CARPET, 116, (char) 0, CAT_EARS),
    PINK_CAT_EARS(WardrobeItem.class, PINK_CARPET, 117, (char) 0, CAT_EARS),
    RED_CAT_EARS(WardrobeItem.class, RED_CARPET, 118, (char) 0, CAT_EARS),
    WHITE_CAT_EARS(WardrobeItem.class, WHITE_CARPET, 119, (char) 0, CAT_EARS),
    BLACK_SUNGLASSES(WardrobeItem.class, ANVIL, 120, (char) 0, SUNGLASSES),
    BLUE_SUNGLASSES(WardrobeItem.class, ANVIL, 121, (char) 0, SUNGLASSES),
    CYAN_SUNGLASSES(WardrobeItem.class, ANVIL, 122, (char) 0, SUNGLASSES),
    GRAY_SUNGLASSES(WardrobeItem.class, ANVIL, 123, (char) 0, SUNGLASSES),
    GREEN_SUNGLASSES(WardrobeItem.class, ANVIL, 124, (char) 0, SUNGLASSES),
    LIGHT_BLUE_SUNGLASSES(WardrobeItem.class, ANVIL, 125, (char) 0, SUNGLASSES),
    LIGHT_GRAY_SUNGLASSES(WardrobeItem.class, ANVIL, 126, (char) 0, SUNGLASSES),
    LIME_SUNGLASSES(WardrobeItem.class, ANVIL, 127, (char) 0, SUNGLASSES),
    MAGENTA_SUNGLASSES(WardrobeItem.class, ANVIL, 128, (char) 0, SUNGLASSES),
    PURPLE_SUNGLASSES(WardrobeItem.class, ANVIL, 129, (char) 0, SUNGLASSES),
    ORANGE_SUNGLASSES(WardrobeItem.class, ANVIL, 130, (char) 0, SUNGLASSES),
    PINK_SUNGLASSES(WardrobeItem.class, ANVIL, 131, (char) 0, SUNGLASSES),
    RED_SUNGLASSES(WardrobeItem.class, ANVIL, 132, (char) 0, SUNGLASSES),
    WHITE_SUNGLASSES(WardrobeItem.class, ANVIL, 133, (char) 0, SUNGLASSES),
    YELLOW_SUNGLASSES(WardrobeItem.class, ANVIL, 134, (char) 0, SUNGLASSES),
    GOLDEN_CROWN(WardrobeItem.class, GOLD_INGOT, 146, (char) 0, WARDROBE_HAT),
    DEVIL_HORNS(WardrobeItem.class, NETHERITE_LEGGINGS, 147, (char) 0, WARDROBE_HAT),
    ELF_HAT(WardrobeItem.class, RED_WOOL, 148, (char) 0, WARDROBE_HAT),
    FIREFIGHTER_HELMET(WardrobeItem.class, RED_CONCRETE, 149, (char) 0, WARDROBE_HAT),
    PLAGUE_DOCTOR(WardrobeItem.class, BLACK_CONCRETE, 150, (char) 0, WARDROBE_HAT),
    PLAGUE_DOCTOR_2(WardrobeItem.class, BLACK_CONCRETE, 151, (char) 0, WARDROBE_HAT),
    PUMPKIN_STUB(WardrobeItem.class, SEA_PICKLE, 152, (char) 0, WARDROBE_HAT),
    STOCKING_CAP(WardrobeItem.class, RED_WOOL, 153, (char) 0, WARDROBE_HAT),
    STRAW_HAT(WardrobeItem.class, HAY_BLOCK, 154, (char) 0, WARDROBE_HAT),
    CHRISTMAS_HAT(WardrobeItem.class, RED_CONCRETE, 632, (char) 0, WARDROBE_HAT),
    SANTA_SLED(WardrobeItem.class, RED_CONCRETE, 649, (char) 0, WARDROBE_MOUNT),
    WHITE_WITCH_HAT(WardrobeItem.class, WHITE_SHULKER_BOX, 155, (char) 0, WITCH_HAT),
    ORANGE_WITCH_HAT(WardrobeItem.class, ORANGE_SHULKER_BOX, 162, (char) 0, WITCH_HAT),
    MAGENTA_WITCH_HAT(WardrobeItem.class, MAGENTA_SHULKER_BOX, 163, (char) 0, WITCH_HAT),
    LIGHT_BLUE_WITCH_HAT(WardrobeItem.class, LIGHT_BLUE_SHULKER_BOX, 164, (char) 0, WITCH_HAT),
    YELLOW_WITCH_HAT(WardrobeItem.class, YELLOW_SHULKER_BOX, 165, (char) 0, WITCH_HAT),
    LIME_WITCH_HAT(WardrobeItem.class, LIME_SHULKER_BOX, 166, (char) 0, WITCH_HAT),
    PINK_WITCH_HAT(WardrobeItem.class, PINK_SHULKER_BOX, 167, (char) 0, WITCH_HAT),
    GRAY_WITCH_HAT(WardrobeItem.class, GRAY_SHULKER_BOX, 168, (char) 0, WITCH_HAT),
    LIGHT_GRAY_WITCH_HAT(WardrobeItem.class, LIGHT_GRAY_SHULKER_BOX, 169, (char) 0, WITCH_HAT),
    CYAN_WITCH_HAT(WardrobeItem.class, CYAN_SHULKER_BOX, 170, (char) 0, WITCH_HAT),
    PURPLE_WITCH_HAT(WardrobeItem.class, PURPLE_SHULKER_BOX, 171, (char) 0, WITCH_HAT),
    BLUE_WITCH_HAT(WardrobeItem.class, BLUE_SHULKER_BOX, 172, (char) 0, WITCH_HAT),
    BROWN_WITCH_HAT(WardrobeItem.class, BROWN_SHULKER_BOX, 173, (char) 0, WITCH_HAT),
    GREEN_WITCH_HAT(WardrobeItem.class, GREEN_SHULKER_BOX, 174, (char) 0, WITCH_HAT),
    RED_WITCH_HAT(WardrobeItem.class, RED_SHULKER_BOX, 175, (char) 0, WITCH_HAT),
    BLACK_WITCH_HAT(WardrobeItem.class, BLACK_SHULKER_BOX, 176, (char) 0, WITCH_HAT),
    VOTE_CANDY(VoteCandy.class, COOKIE, 9073001, (char) 0xE21E, VOTE),
    VOTE_FIREWORK(VoteFirework.class, FIREWORK_ROCKET, 9073002, (char) 0xE21F, VOTE),
    LUCID_LILY(Ingredient.class, AZURE_BLUET, 849001, (char) 0xE201, MAYPOLE),
    PINE_CONE(Ingredient.class, SPRUCE_SAPLING, 849002, (char) 0xE202, MAYPOLE),
    ORANGE_ONION(Ingredient.class, ORANGE_TULIP, 849003, (char) 0xE203, MAYPOLE),
    MISTY_MOREL(Ingredient.class, WARPED_FUNGUS, 849004, (char) 0xE204, MAYPOLE),
    RED_ROSE(Ingredient.class, POPPY, 849005, (char) 0xE205, MAYPOLE),
    FROST_FLOWER(Ingredient.class, BLUE_ORCHID, 849006, (char) 0xE206, MAYPOLE),
    HEAT_ROOT(Ingredient.class, DEAD_BUSH, 849007, (char) 0xE207, MAYPOLE),
    CACTUS_BLOSSOM(Ingredient.class, CACTUS, 849008, (char) 0xE208, MAYPOLE),
    PIPE_WEED(Ingredient.class, FERN, 849009, (char) 0xE209, MAYPOLE),
    KINGS_PUMPKIN(Ingredient.class, CARVED_PUMPKIN, 849010, (char) 0xE20A, MAYPOLE),
    SPARK_SEED(Ingredient.class, BEETROOT_SEEDS, 849011, (char) 0xE20B, MAYPOLE),
    OASIS_WATER(Ingredient.class, LIGHT_BLUE_DYE, 849012, (char) 0xE20C, MAYPOLE),
    CLAMSHELL(Ingredient.class, NAUTILUS_SHELL, 849013, (char) 0xE20D, MAYPOLE),
    FROZEN_AMBER(Ingredient.class, EMERALD, 849014, (char) 0xE20E, MAYPOLE),
    CLUMP_OF_MOSS(Ingredient.class, VINE, 849015, (char) 0xE20F, MAYPOLE),
    FIRE_AMANITA(Ingredient.class, CRIMSON_FUNGUS, 849016, (char) 0xE210, MAYPOLE),
    BOOK_OF_MAY(DummyMytem.class, BOOK, 304, (char) 0xE2D9, MAY),
    BEESTICK(Beestick.class, BLAZE_ROD, 305, (char) 0xE2DA, UTILITY),
    ENDERBALL(Enderball.class, DRAGON_EGG, null, (char) 0, UTILITY),
    POCKET_ALLAY(PocketMob.class, ALLAY_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_AXOLOTL(PocketMob.class, AXOLOTL_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_BAT(PocketMob.class, BAT_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_BEE(PocketMob.class, BEE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_BLAZE(PocketMob.class, BLAZE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_CAMEL(PocketMob.class, CAMEL_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_CAT(PocketMob.class, CAT_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_CAVE_SPIDER(PocketMob.class, CAVE_SPIDER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_CHICKEN(PocketMob.class, CHICKEN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_COD(PocketMob.class, COD_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_COW(PocketMob.class, COW_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_CREEPER(PocketMob.class, CREEPER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_DOLPHIN(PocketMob.class, DOLPHIN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_DONKEY(PocketMob.class, DONKEY_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_DROWNED(PocketMob.class, DROWNED_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ELDER_GUARDIAN(PocketMob.class, ELDER_GUARDIAN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ENDERMAN(PocketMob.class, ENDERMAN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ENDERMITE(PocketMob.class, ENDERMITE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ENDER_DRAGON(PocketMob.class, ENDER_DRAGON_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_EVOKER(PocketMob.class, EVOKER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_FOX(PocketMob.class, FOX_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_FROG(PocketMob.class, FROG_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_GHAST(PocketMob.class, GHAST_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_GIANT(PocketMob.class, ZOMBIE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_GLOW_SQUID(PocketMob.class, GLOW_SQUID_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_GOAT(PocketMob.class, GOAT_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_GUARDIAN(PocketMob.class, GUARDIAN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_HOGLIN(PocketMob.class, HOGLIN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_HORSE(PocketMob.class, HORSE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_HUSK(PocketMob.class, HUSK_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ILLUSIONER(PocketMob.class, VINDICATOR_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_IRON_GOLEM(PocketMob.class, IRON_GOLEM_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_LLAMA(PocketMob.class, LLAMA_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_MAGMA_CUBE(PocketMob.class, MAGMA_CUBE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_MULE(PocketMob.class, MULE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_MUSHROOM_COW(PocketMob.class, MOOSHROOM_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_OCELOT(PocketMob.class, OCELOT_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_PANDA(PocketMob.class, PANDA_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_PARROT(PocketMob.class, PARROT_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_PHANTOM(PocketMob.class, PHANTOM_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_PIG(PocketMob.class, PIG_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_PIGLIN(PocketMob.class, PIGLIN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_PIGLIN_BRUTE(PocketMob.class, PIGLIN_BRUTE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_PILLAGER(PocketMob.class, PILLAGER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_POLAR_BEAR(PocketMob.class, POLAR_BEAR_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_PUFFERFISH(PocketMob.class, PUFFERFISH_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_RABBIT(PocketMob.class, RABBIT_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_RAVAGER(PocketMob.class, RAVAGER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SALMON(PocketMob.class, SALMON_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SHEEP(PocketMob.class, SHEEP_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SHULKER(PocketMob.class, SHULKER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SILVERFISH(PocketMob.class, SILVERFISH_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SKELETON(PocketMob.class, SKELETON_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SKELETON_HORSE(PocketMob.class, SKELETON_HORSE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SLIME(PocketMob.class, SLIME_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SNOWMAN(PocketMob.class, POLAR_BEAR_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SPIDER(PocketMob.class, SPIDER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_SQUID(PocketMob.class, SQUID_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_STRAY(PocketMob.class, STRAY_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_STRIDER(PocketMob.class, STRIDER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_TADPOLE(PocketMob.class, TADPOLE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_TRADER_LLAMA(PocketMob.class, TRADER_LLAMA_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_TROPICAL_FISH(PocketMob.class, TROPICAL_FISH_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_TURTLE(PocketMob.class, TURTLE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_VEX(PocketMob.class, VEX_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_VILLAGER(PocketMob.class, VILLAGER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_VINDICATOR(PocketMob.class, VINDICATOR_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_WANDERING_TRADER(PocketMob.class, WANDERING_TRADER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_WARDEN(PocketMob.class, WARDEN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_WITCH(PocketMob.class, WITCH_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_WITHER(PocketMob.class, WITHER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_WITHER_SKELETON(PocketMob.class, WITHER_SKELETON_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_WOLF(PocketMob.class, WOLF_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ZOGLIN(PocketMob.class, ZOGLIN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ZOMBIE(PocketMob.class, ZOMBIE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ZOMBIE_HORSE(PocketMob.class, ZOMBIE_HORSE_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ZOMBIE_VILLAGER(PocketMob.class, ZOMBIE_VILLAGER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    POCKET_ZOMBIFIED_PIGLIN(PocketMob.class, ZOMBIFIED_PIGLIN_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
    MOB_CATCHER(MobCatcher.class, EGG, 908302, (char) 0xE24E, MOB_CATCHERS),
    MONSTER_CATCHER(MobCatcher.class, EGG, 908303, (char) 0xE24F, MOB_CATCHERS),
    ANIMAL_CATCHER(MobCatcher.class, EGG, 908304, (char) 0xE250, MOB_CATCHERS),
    VILLAGER_CATCHER(MobCatcher.class, EGG, 908305, (char) 0xE251, MOB_CATCHERS),
    FISH_CATCHER(MobCatcher.class, EGG, 908306, (char) 0xE252, MOB_CATCHERS),
    PET_CATCHER(MobCatcher.class, EGG, 908307, (char) 0xE253, MOB_CATCHERS),
    CAPTAINS_CUTLASS(CaptainsCutlass.class, WOODEN_SWORD, 2, (char) 0xE239, UTILITY),
    BLUNDERBUSS(Blunderbuss.class, IRON_INGOT, 3, (char) 0xE23A, UTILITY),
    IRON_SCYTHE(Scythe.class, IRON_HOE, 650, (char) 0xE2DB, GARDENING),
    GOLDEN_SCYTHE(Scythe.class, GOLDEN_HOE, 4, (char) 0xE23B, GARDENING),
    WITCH_BROOM(WitchBroom.class, WOODEN_SHOVEL, 51, (char) 0xE273, UTILITY),
    OCARINA_OF_CHIME(HyruleInstrument.class, NAUTILUS_SHELL, 36, (char) 0xE264, MUSIC_HYRULE),
    GOLDEN_BANJO(HyruleInstrument.class, WOODEN_SHOVEL, 41, (char) 0xE269, MUSIC_HYRULE),
    PAN_FLUTE(MusicalInstrument.class, STICK, 43, (char) 0xE26B, MUSIC),
    TRIANGLE(MusicalInstrument.class, STICK, 44, (char) 0xE26C, MUSIC),
    WOODEN_DRUM(MusicalInstrument.class, STICK, 45, (char) 0xE26D, MUSIC),
    WOODEN_LUTE(MusicalInstrument.class, STICK, 46, (char) 0xE26E, MUSIC),
    WOODEN_OCARINA(MusicalInstrument.class, STICK, 47, (char) 0xE26F, MUSIC),
    BANJO(MusicalInstrument.class, STICK, 48, (char) 0xE270, MUSIC),
    BIT_BOY(MusicalInstrument.class, STICK, 49, (char) 0xE271, MUSIC),
    GUITAR(MusicalInstrument.class, STICK, 50, (char) 0xE272, MUSIC),
    WOODEN_HORN(MusicalInstrument.class, STICK, 55, (char) 0xE277, MUSIC),
    MUSICAL_BELL(MusicalInstrument.class, STICK, 56, (char) 0xE278, MUSIC),
    COW_BELL(MusicalInstrument.class, STICK, 58, (char) 0xE27A, MUSIC),
    RAINBOW_XYLOPHONE(MusicalInstrument.class, STICK, 59, (char) 0xE27B, MUSIC),
    ELECTRIC_GUITAR(MusicalInstrument.class, STICK, 60, (char) 0xE27C, MUSIC),
    POCKET_PIANO(MusicalInstrument.class, STICK, 61, (char) 0xE27D, MUSIC),
    ELECTRIC_PIANO(MusicalInstrument.class, STICK, 62, (char) 0xE27E, MUSIC),
    SNARE_DRUM(MusicalInstrument.class, STICK, 63, (char) 0xE27F, MUSIC),
    IRON_XYLOPHONE(MusicalInstrument.class, STICK, 64, (char) 0xE280, MUSIC),
    CLICKS_AND_STICKS(MusicalInstrument.class, STICK, 66, (char) 0xE282, MUSIC),
    ANGELIC_HARP(MusicalInstrument.class, STICK, 67, (char) 0xE283, MUSIC),
    KOBOLD_HEAD(WardrobeItem.class, GREEN_CONCRETE, 1, (char) 0, WARDROBE_HAT),
    RUBY(DummyMytem.class, EMERALD, 6, (char) 0xE23E, CURRENCY),
    RUBY_KITTY(DummyMytem.class, EMERALD, 651, (char) 0xE2DC, CURRENCY),
    OK(ForbiddenMytem.class, BLUE_CONCRETE, 7, (char) 0xE23F, UI),
    NO(ForbiddenMytem.class, RED_CONCRETE, 8, (char) 0xE240, UI),
    ON(ForbiddenMytem.class, ENDER_EYE, 210, (char) 0xE2DD, UI),
    OFF(ForbiddenMytem.class, ENDER_PEARL, 211, (char) 0xE2DE, UI),
    REDO(ForbiddenMytem.class, EGG, 212, (char) 0xE2DF, UI),
    HALF_HEART(ForbiddenMytem.class, NAUTILUS_SHELL, 10, (char) 0xE242, UI),
    EMPTY_HEART(ForbiddenMytem.class, NAUTILUS_SHELL, 13, (char) 0xE246, UI),
    ARROW_RIGHT(ForbiddenMytem.class, ARROW, 11, (char) 0xE244, ARROWS),
    ARROW_LEFT(ForbiddenMytem.class, ARROW, 12, (char) 0xE245, ARROWS),
    ARROW_UP(ForbiddenMytem.class, ARROW, 37, (char) 0xE265, ARROWS),
    ARROW_DOWN(ForbiddenMytem.class, ARROW, 38, (char) 0xE266, ARROWS),
    TURN_LEFT(ForbiddenMytem.class, ARROW, 283, (char) 0xE2E0, ARROWS),
    TURN_RIGHT(ForbiddenMytem.class, ARROW, 284, (char) 0xE2E1, ARROWS),
    CHECKBOX(ForbiddenMytem.class, WHITE_CONCRETE, 14, (char) 0xE247, UI),
    CHECKED_CHECKBOX(ForbiddenMytem.class, GREEN_CONCRETE, 15, (char) 0xE248, chrarr(0xE248, 0xE2E2, 0xE2E3, 0xE2E4), UI, Animation.CHECKBOX),
    CROSSED_CHECKBOX(ForbiddenMytem.class, BARRIER, 16, (char) 0xE249, chrarr(0xE249, 0xE2E5, 0xE2E6, 0xE2E7), UI, Animation.frametime(20)),
    EAGLE(ForbiddenMytem.class, FEATHER, 19, (char) 0xE24C, UI),
    EARTH(ForbiddenMytem.class, ENDER_PEARL, 5, (char) 0xE23D, UI),
    EASTER_EGG(DummyMytem.class, EGG, 345715, (char) 0xE23C, COLLECTIBLES),
    TRAFFIC_LIGHT(ForbiddenMytem.class, YELLOW_DYE, 57, (char) 0xE279, UI),
    INVISIBLE_ITEM(ForbiddenMytem.class, LIGHT_GRAY_STAINED_GLASS_PANE, 65, (char) 0xE281, UI),
    PAINT_PALETTE(ForbiddenMytem.class, STICK, 202, (char) 0xE2E8, UI),
    PLUS_BUTTON(ForbiddenMytem.class, EGG, 266, (char) 0xE2E9, UI),
    MINUS_BUTTON(ForbiddenMytem.class, SNOWBALL, 267, (char) 0xE2EA, UI),
    FLOPPY_DISK(ForbiddenMytem.class, MUSIC_DISC_CAT, 268, (char) 0xE2EB, UI),
    FOLDER(ForbiddenMytem.class, CHEST, 269, (char) 0xE2EC, UI),
    MAGNET(ForbiddenMytem.class, IRON_NUGGET, 270, (char) 0xE2ED, UI),
    DATA_INTEGER(ForbiddenMytem.class, REPEATER, 271, (char) 0xE2EE, UI),
    DATA_STRING(ForbiddenMytem.class, CHAIN, 272, (char) 0xE2EF, UI),
    DATA_FLOAT(ForbiddenMytem.class, REPEATER, 273, (char) 0xE2F0, UI),
    BOMB(ForbiddenMytem.class, CHAIN, 274, (char) 0xE2F1, UI),
    MOUSE(ForbiddenMytem.class, WHITE_CONCRETE, 336, (char) 0xE2F2, UI),
    MOUSE_LEFT(ForbiddenMytem.class, LIGHT_BLUE_CONCRETE, 337, (char) 0xE2F3, UI),
    MOUSE_RIGHT(ForbiddenMytem.class, RED_CONCRETE, 338, (char) 0xE2F4, UI),
    SHIFT_KEY(ForbiddenMytem.class, LIGHT_GRAY_CONCRETE, 339, (char) 0xE2F5, UI),
    THUMBS_UP(ForbiddenMytem.class, GREEN_CONCRETE, 340, (char) 0xE2F6, UI),
    EYES(ForbiddenMytem.class, ENDER_EYE, 595, (char) 0xE2F7, UI),
    CAVETALE_DUNGEON(ForbiddenMytem.class, SPAWNER, 706, (char) 0xE2F8, UI),
    RAINBOW_BUTTERFLY(ForbiddenMytem.class, FEATHER, 633, (char) 0xE2F9, chrarr(0xE2F9, 0xE2FA, 0xE2FB, 0xE2FC, 0xE2FD, 0xE2FE, 0xE2FF, 0xE300), UI, Animation.frametime(8)),
    SNOWFLAKE(ForbiddenMytem.class, SNOWBALL, 673, (char) 0xE301, chrarr(0xE301, 0xE302, 0xE303, 0xE304, 0xE305, 0xE306, 0xE307, 0xE308, 0xE309, 0xE30A, 0xE30B, 0xE30C, 0xE30D, 0xE30E, 0xE30F, 0xE310), UI, Animation.SNOWFLAKE),
    HEART(DummyMytem.class, HEART_OF_THE_SEA, 9, (char) 0xE241, COLLECTIBLES),
    STAR(DummyMytem.class, NETHER_STAR, 18, (char) 0xE24B, COLLECTIBLES),
    MOON(DummyMytem.class, YELLOW_DYE, 204, (char) 0xE311, COLLECTIBLES),
    GREEN_MOON(DummyMytem.class, LIME_DYE, 592, (char) 0xE312, COLLECTIBLES),
    LIGHTNING(DummyMytem.class, LIGHTNING_ROD, 250, (char) 0xE313, chrarr(0xE313, 0xE314, 0xE315, 0xE316, 0xE317, 0xE318, 0xE319, 0xE31A, 0xE31B, 0xE31C, 0xE31D, 0xE31E, 0xE31F, 0xE320, 0xE321, 0xE322, 0xE323, 0xE324), COLLECTIBLES, Animation.LIGHTNING),
    DICE(DiceItem.class, PLAYER_HEAD, 213, (char) 0xE325, DIE),
    DICE4(DiceItem.class, PRISMARINE_SHARD, 243, (char) 0xE326, DIE),
    DICE8(DiceItem.class, PRISMARINE_SHARD, 244, (char) 0xE327, DIE),
    DICE10(DiceItem.class, PRISMARINE_SHARD, 245, (char) 0xE328, DIE),
    DICE12(DiceItem.class, PRISMARINE_SHARD, 246, (char) 0xE329, DIE),
    DICE20(DiceItem.class, PRISMARINE_SHARD, 247, (char) 0xE32A, DIE),
    DICE100(DiceItem.class, PRISMARINE_SHARD, 248, (char) 0xE32B, DIE),
    DICE_1(ForbiddenMytem.class, QUARTZ_BLOCK, 707, (char) 0xE32C, UI),
    DICE_2(ForbiddenMytem.class, QUARTZ_BLOCK, 708, (char) 0xE32D, UI),
    DICE_3(ForbiddenMytem.class, QUARTZ_BLOCK, 709, (char) 0xE32E, UI),
    DICE_4(ForbiddenMytem.class, QUARTZ_BLOCK, 710, (char) 0xE32F, UI),
    DICE_5(ForbiddenMytem.class, QUARTZ_BLOCK, 711, (char) 0xE330, UI),
    DICE_6(ForbiddenMytem.class, QUARTZ_BLOCK, 712, (char) 0xE331, UI),
    DICE_ROLL(ForbiddenMytem.class, QUARTZ_BLOCK, 713, (char) 0xE332, chrarr(0xE332, 0xE333, 0xE334, 0xE335, 0xE336, 0xE337), UI, Animation.frametime(2)),
    LETTER_A(GlyphItem.class, PLAYER_HEAD, 68, (char) 0xE284, LETTER),
    LETTER_B(GlyphItem.class, PLAYER_HEAD, 69, (char) 0xE285, LETTER),
    LETTER_C(GlyphItem.class, PLAYER_HEAD, 70, (char) 0xE286, LETTER),
    LETTER_D(GlyphItem.class, PLAYER_HEAD, 71, (char) 0xE287, LETTER),
    LETTER_E(GlyphItem.class, PLAYER_HEAD, 72, (char) 0xE288, LETTER),
    LETTER_F(GlyphItem.class, PLAYER_HEAD, 73, (char) 0xE289, LETTER),
    LETTER_G(GlyphItem.class, PLAYER_HEAD, 74, (char) 0xE28A, LETTER),
    LETTER_H(GlyphItem.class, PLAYER_HEAD, 75, (char) 0xE28B, LETTER),
    LETTER_I(GlyphItem.class, PLAYER_HEAD, 76, (char) 0xE28C, LETTER),
    LETTER_J(GlyphItem.class, PLAYER_HEAD, 77, (char) 0xE28D, LETTER),
    LETTER_K(GlyphItem.class, PLAYER_HEAD, 78, (char) 0xE28E, LETTER),
    LETTER_L(GlyphItem.class, PLAYER_HEAD, 80, (char) 0xE28F, LETTER),
    LETTER_M(GlyphItem.class, PLAYER_HEAD, 81, (char) 0xE290, LETTER),
    LETTER_N(GlyphItem.class, PLAYER_HEAD, 82, (char) 0xE291, LETTER),
    LETTER_O(GlyphItem.class, PLAYER_HEAD, 83, (char) 0xE292, LETTER),
    LETTER_P(GlyphItem.class, PLAYER_HEAD, 84, (char) 0xE293, LETTER),
    LETTER_Q(GlyphItem.class, PLAYER_HEAD, 85, (char) 0xE294, LETTER),
    LETTER_R(GlyphItem.class, PLAYER_HEAD, 86, (char) 0xE295, LETTER),
    LETTER_S(GlyphItem.class, PLAYER_HEAD, 87, (char) 0xE296, LETTER),
    LETTER_T(GlyphItem.class, PLAYER_HEAD, 88, (char) 0xE297, LETTER),
    LETTER_U(GlyphItem.class, PLAYER_HEAD, 89, (char) 0xE298, LETTER),
    LETTER_V(GlyphItem.class, PLAYER_HEAD, 90, (char) 0xE299, LETTER),
    LETTER_W(GlyphItem.class, PLAYER_HEAD, 91, (char) 0xE29A, LETTER),
    LETTER_X(GlyphItem.class, PLAYER_HEAD, 92, (char) 0xE29B, LETTER),
    LETTER_Y(GlyphItem.class, PLAYER_HEAD, 93, (char) 0xE29C, LETTER),
    LETTER_Z(GlyphItem.class, PLAYER_HEAD, 94, (char) 0xE29D, LETTER),
    NUMBER_0(GlyphItem.class, PLAYER_HEAD, 95, (char) 0xE29E, NUMBER),
    NUMBER_1(GlyphItem.class, PLAYER_HEAD, 96, (char) 0xE29F, NUMBER),
    NUMBER_2(GlyphItem.class, PLAYER_HEAD, 97, (char) 0xE2A0, NUMBER),
    NUMBER_3(GlyphItem.class, PLAYER_HEAD, 98, (char) 0xE2A1, NUMBER),
    NUMBER_4(GlyphItem.class, PLAYER_HEAD, 99, (char) 0xE2A2, NUMBER),
    NUMBER_5(GlyphItem.class, PLAYER_HEAD, 100, (char) 0xE2A3, NUMBER),
    NUMBER_6(GlyphItem.class, PLAYER_HEAD, 101, (char) 0xE2A4, NUMBER),
    NUMBER_7(GlyphItem.class, PLAYER_HEAD, 102, (char) 0xE2A5, NUMBER),
    NUMBER_8(GlyphItem.class, PLAYER_HEAD, 103, (char) 0xE2A6, NUMBER),
    NUMBER_9(GlyphItem.class, PLAYER_HEAD, 104, (char) 0xE2A7, NUMBER),
    MUSICAL_SHARP(GlyphItem.class, PLAYER_HEAD, 105, (char) 0xE2A8, MUSICAL),
    MUSICAL_FLAT(GlyphItem.class, PLAYER_HEAD, 106, (char) 0xE2A9, MUSICAL),
    EXCLAMATION_MARK(GlyphItem.class, PLAYER_HEAD, 107, (char) 0xE2AA, PUNCTUATION),
    QUESTION_MARK(GlyphItem.class, PLAYER_HEAD, 17, (char) 0xE24A, PUNCTUATION),
    SURPRISED(ForbiddenMytem.class, SLIME_BALL, 21, (char) 0xE255, REACTION),
    HAPPY(ForbiddenMytem.class, SLIME_BALL, 22, (char) 0xE256, REACTION),
    SOB(ForbiddenMytem.class, SLIME_BALL, 23, (char) 0xE257, REACTION),
    CRY(ForbiddenMytem.class, SLIME_BALL, 24, (char) 0xE258, REACTION),
    COOL(ForbiddenMytem.class, SLIME_BALL, 25, (char) 0xE259, REACTION),
    SMILE(ForbiddenMytem.class, SLIME_BALL, 26, (char) 0xE25A, REACTION),
    SMILE_UPSIDE_DOWN(ForbiddenMytem.class, SLIME_BALL, 27, (char) 0xE25B, REACTION),
    FROWN(ForbiddenMytem.class, SLIME_BALL, 28, (char) 0xE25C, REACTION),
    SILLY(ForbiddenMytem.class, SLIME_BALL, 29, (char) 0xE25D, REACTION),
    CLOWN(ForbiddenMytem.class, SLIME_BALL, 30, (char) 0xE25E, REACTION),
    STARSTRUCK(ForbiddenMytem.class, SLIME_BALL, 31, (char) 0xE25F, REACTION),
    LOVE_EYES(ForbiddenMytem.class, SLIME_BALL, 32, (char) 0xE260, REACTION),
    WINK_SMILE(ForbiddenMytem.class, SLIME_BALL, 33, (char) 0xE261, REACTION),
    MIND_BLOWN(ForbiddenMytem.class, SLIME_BALL, 34, (char) 0xE262, REACTION),
    WINK(ForbiddenMytem.class, SLIME_BALL, 35, (char) 0xE263, REACTION),
    STEVE_FACE(ForbiddenMytem.class, SLIME_BALL, 234, (char) 0xE338, REACTION),
    ALEX_FACE(ForbiddenMytem.class, SLIME_BALL, 235, (char) 0xE339, REACTION),
    COW_FACE(ForbiddenMytem.class, SLIME_BALL, 224, (char) 0xE33A, MOB_FACE),
    CREEPER_FACE(ForbiddenMytem.class, SLIME_BALL, 225, (char) 0xE33B, MOB_FACE),
    ENDERMAN_FACE(ForbiddenMytem.class, SLIME_BALL, 226, (char) 0xE33C, MOB_FACE),
    GHAST_FACE(ForbiddenMytem.class, SLIME_BALL, 227, (char) 0xE33D, MOB_FACE),
    PIG_FACE(ForbiddenMytem.class, SLIME_BALL, 228, (char) 0xE33E, MOB_FACE),
    SHEEP_FACE(ForbiddenMytem.class, SLIME_BALL, 229, (char) 0xE33F, MOB_FACE),
    SKELETON_FACE(ForbiddenMytem.class, SLIME_BALL, 230, (char) 0xE340, MOB_FACE),
    SLIME_FACE(ForbiddenMytem.class, SLIME_BALL, 231, (char) 0xE341, MOB_FACE),
    SPIDER_FACE(ForbiddenMytem.class, SLIME_BALL, 232, (char) 0xE342, MOB_FACE),
    SQUID_FACE(ForbiddenMytem.class, SLIME_BALL, 233, (char) 0xE343, MOB_FACE),
    VILLAGER_FACE(ForbiddenMytem.class, SLIME_BALL, 236, (char) 0xE344, MOB_FACE),
    PILLAGER_FACE(ForbiddenMytem.class, SLIME_BALL, 237, (char) 0xE345, MOB_FACE),
    WITHER_FACE(ForbiddenMytem.class, SLIME_BALL, 238, (char) 0xE346, MOB_FACE),
    ZOMBIE_FACE(ForbiddenMytem.class, SLIME_BALL, 239, (char) 0xE347, MOB_FACE),
    WITCH_FACE(ForbiddenMytem.class, SLIME_BALL, 240, (char) 0xE348, MOB_FACE),
    BLAZE_FACE(ForbiddenMytem.class, SLIME_BALL, 689, (char) 0xE349, MOB_FACE),
    PIGLIN_FACE(ForbiddenMytem.class, SLIME_BALL, 690, (char) 0xE34A, MOB_FACE),
    WARDEN_FACE(ForbiddenMytem.class, SLIME_BALL, 691, (char) 0xE34B, MOB_FACE),
    HOGLIN_FACE(ForbiddenMytem.class, SLIME_BALL, 692, (char) 0xE34C, MOB_FACE),
    HUSK_FACE(ForbiddenMytem.class, SLIME_BALL, 693, (char) 0xE34D, MOB_FACE),
    VEX_FACE(ForbiddenMytem.class, SLIME_BALL, 694, (char) 0xE34E, MOB_FACE),
    GUARDIAN_FACE(ForbiddenMytem.class, SLIME_BALL, 695, (char) 0xE34F, MOB_FACE),
    DROWNED_FACE(ForbiddenMytem.class, SLIME_BALL, 696, (char) 0xE350, MOB_FACE),
    PHANTOM_FACE(ForbiddenMytem.class, SLIME_BALL, 697, (char) 0xE351, MOB_FACE),
    MAGMA_CUBE_FACE(ForbiddenMytem.class, SLIME_BALL, 698, (char) 0xE352, MOB_FACE),
    RAVAGER_FACE(ForbiddenMytem.class, SLIME_BALL, 699, (char) 0xE353, MOB_FACE),
    SILVERFISH_FACE(ForbiddenMytem.class, SLIME_BALL, 700, (char) 0xE354, MOB_FACE),
    ZOGLIN_FACE(ForbiddenMytem.class, SLIME_BALL, 701, (char) 0xE355, MOB_FACE),
    ZOMBIE_VILLAGER_FACE(ForbiddenMytem.class, SLIME_BALL, 702, (char) 0xE356, MOB_FACE),
    WITHER_SKELETON_FACE(ForbiddenMytem.class, SLIME_BALL, 703, (char) 0xE357, MOB_FACE),
    ENDER_DRAGON_FACE(ForbiddenMytem.class, SLIME_BALL, 704, (char) 0xE358, MOB_FACE),
    STRAY_FACE(ForbiddenMytem.class, SLIME_BALL, 705, (char) 0xE359, MOB_FACE),
    BEE_FACE(ForbiddenMytem.class, SLIME_BALL, 731, (char) 0xE35A, MOB_FACE),
    TEMPERATE_FROG_FACE(ForbiddenMytem.class, SLIME_BALL, 732, (char) 0xE35B, MOB_FACE),
    COLD_FROG_FACE(ForbiddenMytem.class, SLIME_BALL, 733, (char) 0xE35C, MOB_FACE),
    WARM_FROG_FACE(ForbiddenMytem.class, SLIME_BALL, 734, (char) 0xE35D, MOB_FACE),
    GOAT_FACE(ForbiddenMytem.class, SLIME_BALL, 735, (char) 0xE35E, MOB_FACE),
    CHICKEN_FACE(ForbiddenMytem.class, SLIME_BALL, 736, (char) 0xE35F, MOB_FACE),
    BROWN_RABBIT_FACE(ForbiddenMytem.class, SLIME_BALL, 737, (char) 0xE360, MOB_FACE),
    CREAMY_HORSE_FACE(ForbiddenMytem.class, SLIME_BALL, 738, (char) 0xE361, MOB_FACE),
    WOLF_FACE(ForbiddenMytem.class, SLIME_BALL, 739, (char) 0xE362, MOB_FACE),
    OCELOT_FACE(ForbiddenMytem.class, SLIME_BALL, 740, (char) 0xE363, MOB_FACE),
    CYAN_AXOLOTL_FACE(ForbiddenMytem.class, SLIME_BALL, 741, (char) 0xE364, MOB_FACE),
    CREAMY_LLAMA_FACE(ForbiddenMytem.class, SLIME_BALL, 742, (char) 0xE365, MOB_FACE),
    PANDA_FACE(ForbiddenMytem.class, SLIME_BALL, 743, (char) 0xE366, MOB_FACE),
    FOX_FACE(ForbiddenMytem.class, SLIME_BALL, 744, (char) 0xE367, MOB_FACE),
    STRIDER_FACE(ForbiddenMytem.class, SLIME_BALL, 745, (char) 0xE368, MOB_FACE),
    PIC_WOLF(ForbiddenMytem.class, BONE, 39, (char) 0xE267, PICTURE),
    PIC_CAT(ForbiddenMytem.class, STRING, 40, (char) 0xE268, PICTURE),
    CANDY_CORN(HalloweenCandy.class, CARROT, 42, (char) 0xE26A, HALLOWEEN),
    CHOCOLATE_BAR(HalloweenCandy.class, PUMPKIN_PIE, 52, (char) 0xE274, HALLOWEEN),
    LOLLIPOP(HalloweenCandy.class, COOKIE, 53, (char) 0xE275, HALLOWEEN),
    ORANGE_CANDY(HalloweenCandy.class, COOKIE, 54, (char) 0xE276, HALLOWEEN),
    HALLOWEEN_TOKEN(HalloweenToken.class, PUMPKIN, 109, (char) 0xE2AC, HALLOWEEN),
    HALLOWEEN_TOKEN_2(HalloweenToken2.class, JACK_O_LANTERN, 110, (char) 0xE2AD, HALLOWEEN),
    CREEPER_CUSTOME_HELMET(CreeperCostume.CreeperHelmet.class, CREEPER_HEAD, null, (char) 0, CREEPER_COSTUME),
    CREEPER_CUSTOME_CHESTPLATE(CreeperCostume.CreeperChestplate.class, LEATHER_CHESTPLATE, null, (char) 0, CREEPER_COSTUME),
    CREEPER_CUSTOME_LEGGINGS(CreeperCostume.CreeperLeggings.class, LEATHER_LEGGINGS, null, (char) 0, CREEPER_COSTUME),
    CREEPER_CUSTOME_BOOTS(CreeperCostume.CreeperBoots.class, LEATHER_BOOTS, null, (char) 0, CREEPER_COSTUME),
    SPIDER_CUSTOME_HELMET(SpiderCostume.SpiderHelmet.class, PLAYER_HEAD, null, (char) 0, SPIDER_COSTUME),
    SPIDER_CUSTOME_CHESTPLATE(SpiderCostume.SpiderChestplate.class, LEATHER_CHESTPLATE, null, (char) 0, SPIDER_COSTUME),
    SPIDER_CUSTOME_LEGGINGS(SpiderCostume.SpiderLeggings.class, LEATHER_LEGGINGS, null, (char) 0, SPIDER_COSTUME),
    SPIDER_CUSTOME_BOOTS(SpiderCostume.SpiderBoots.class, LEATHER_BOOTS, null, (char) 0, SPIDER_COSTUME),
    ENDERMAN_CUSTOME_HELMET(EndermanCostume.EndermanHelmet.class, PLAYER_HEAD, null, (char) 0, ENDERMAN_COSTUME),
    ENDERMAN_CUSTOME_CHESTPLATE(EndermanCostume.EndermanChestplate.class, LEATHER_CHESTPLATE, null, (char) 0, ENDERMAN_COSTUME),
    ENDERMAN_CUSTOME_LEGGINGS(EndermanCostume.EndermanLeggings.class, LEATHER_LEGGINGS, null, (char) 0, ENDERMAN_COSTUME),
    ENDERMAN_CUSTOME_BOOTS(EndermanCostume.EndermanBoots.class, LEATHER_BOOTS, null, (char) 0, ENDERMAN_COSTUME),
    SKELETON_CUSTOME_HELMET(SkeletonCostume.SkeletonHelmet.class, SKELETON_SKULL, null, (char) 0, SKELETON_COSTUME),
    SKELETON_CUSTOME_CHESTPLATE(SkeletonCostume.SkeletonChestplate.class, LEATHER_CHESTPLATE, null, (char) 0, SKELETON_COSTUME),
    SKELETON_CUSTOME_LEGGINGS(SkeletonCostume.SkeletonLeggings.class, LEATHER_LEGGINGS, null, (char) 0, SKELETON_COSTUME),
    SKELETON_CUSTOME_BOOTS(SkeletonCostume.SkeletonBoots.class, LEATHER_BOOTS, null, (char) 0, SKELETON_COSTUME),
    CHICKEN_CUSTOME_HELMET(ChickenCostume.ChickenHelmet.class, PLAYER_HEAD, null, (char) 0, CHICKEN_COSTUME),
    CHICKEN_CUSTOME_CHESTPLATE(ChickenCostume.ChickenChestplate.class, LEATHER_CHESTPLATE, null, (char) 0, CHICKEN_COSTUME),
    CHICKEN_CUSTOME_LEGGINGS(ChickenCostume.ChickenLeggings.class, LEATHER_LEGGINGS, null, (char) 0, CHICKEN_COSTUME),
    CHICKEN_CUSTOME_BOOTS(ChickenCostume.ChickenBoots.class, LEATHER_BOOTS, null, (char) 0, CHICKEN_COSTUME),
    SCARLET_HELMET(ScarletItem.Helmet.class, PLAYER_HEAD, 156, (char) 0xE369, SCARLET),
    SCARLET_CHESTPLATE(ScarletItem.Chestplate.class, LEATHER_CHESTPLATE, 157, (char) 0xE36A, SCARLET),
    SCARLET_LEGGINGS(ScarletItem.Leggings.class, LEATHER_LEGGINGS, 158, (char) 0xE36B, SCARLET),
    SCARLET_BOOTS(ScarletItem.Boots.class, LEATHER_BOOTS, 159, (char) 0xE36C, SCARLET),
    SCARLET_SWORD(ScarletItem.Sword.class, NETHERITE_SWORD, 160, (char) 0xE36D, SCARLET, Animation.frametime(4)),
    SCARLET_SHIELD(ScarletItem.Shield.class, SHIELD, 161, (char) 0xE36E, SCARLET),
    COPPER_KEY(ForbiddenMytem.class, COPPER_INGOT, 177, (char) 0xE36F, KEY),
    SILVER_KEY(ForbiddenMytem.class, IRON_INGOT, 178, (char) 0xE370, KEY),
    GOLDEN_KEY(ForbiddenMytem.class, GOLD_INGOT, 179, (char) 0xE371, KEY),
    COPPER_KEYHOLE(ForbiddenMytem.class, COPPER_BLOCK, 180, (char) 0xE372, KEYHOLE),
    SILVER_KEYHOLE(ForbiddenMytem.class, IRON_BLOCK, 181, (char) 0xE373, KEYHOLE),
    GOLDEN_KEYHOLE(ForbiddenMytem.class, GOLD_BLOCK, 182, (char) 0xE374, KEYHOLE),
    COPPER_COIN(Coin.class, COPPER_INGOT, 183, (char) 0xE377, chrarr(0xE375, 0xE376, 0xE377, 0xE378, 0xE379, 0xE37A, 0xE37B, 0xE37C), COIN, Animation.SPINNING_COIN),
    SILVER_COIN(Coin.class, IRON_INGOT, 184, (char) 0xE37F, chrarr(0xE37D, 0xE37E, 0xE37F, 0xE380, 0xE381, 0xE382, 0xE383, 0xE384), COIN, Animation.SPINNING_COIN),
    GOLDEN_COIN(Coin.class, GOLD_INGOT, 185, (char) 0xE387, chrarr(0xE385, 0xE386, 0xE387, 0xE388, 0xE389, 0xE38A, 0xE38B, 0xE38C), COIN, Animation.SPINNING_COIN),
    DIAMOND_COIN(Coin.class, DIAMOND, 275, (char) 0xE38F, chrarr(0xE38D, 0xE38E, 0xE38F, 0xE390, 0xE391, 0xE392, 0xE393, 0xE394), COIN, Animation.SPINNING_COIN),
    RUBY_COIN(Coin.class, EMERALD, 316, (char) 0xE397, chrarr(0xE395, 0xE396, 0xE397, 0xE398, 0xE399, 0xE39A, 0xE39B, 0xE39C), COIN, Animation.SPINNING_COIN),
    GOLDEN_HOOP(Coin.class, GOLD_INGOT, 641, (char) 0xE39D, chrarr(0xE39D, 0xE39E, 0xE39F, 0xE3A0, 0xE3A1, 0xE3A2, 0xE3A3, 0xE3A4), COIN, Animation.SPINNING_COIN),
    BLACK_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 186, (char) 0xE3A5, PAINTBRUSH),
    RED_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 187, (char) 0xE3A6, PAINTBRUSH),
    GREEN_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 188, (char) 0xE3A7, PAINTBRUSH),
    BROWN_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 189, (char) 0xE3A8, PAINTBRUSH),
    BLUE_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 190, (char) 0xE3A9, PAINTBRUSH),
    PURPLE_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 191, (char) 0xE3AA, PAINTBRUSH),
    CYAN_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 192, (char) 0xE3AB, PAINTBRUSH),
    LIGHT_GRAY_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 193, (char) 0xE3AC, PAINTBRUSH),
    GRAY_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 194, (char) 0xE3AD, PAINTBRUSH),
    PINK_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 195, (char) 0xE3AE, PAINTBRUSH),
    LIME_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 196, (char) 0xE3AF, PAINTBRUSH),
    YELLOW_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 197, (char) 0xE3B0, PAINTBRUSH),
    LIGHT_BLUE_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 198, (char) 0xE3B1, PAINTBRUSH),
    MAGENTA_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 199, (char) 0xE3B2, PAINTBRUSH),
    ORANGE_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 200, (char) 0xE3B3, PAINTBRUSH),
    WHITE_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 201, (char) 0xE3B4, PAINTBRUSH),
    RUSTY_BUCKET(ArmorPart.class, BUCKET, 205, (char) 0xE3B5, ARMOR_PART),
    OLD_OVEN_LID(ArmorPart.class, NETHERITE_SCRAP, 207, (char) 0xE3B6, ARMOR_PART),
    SOOTY_STOVE_PIPE(ArmorPart.class, BARREL, 206, (char) 0xE3B7, ARMOR_PART),
    FLOTSAM_CAN(ArmorPart.class, FLOWER_POT, 208, (char) 0xE3B8, ARMOR_PART),
    BENT_PITCHFORK(ArmorPart.class, LIGHTNING_ROD, 209, (char) 0xE3B9, ARMOR_PART),
    TRASH_CAN_LID(ArmorPart.class, NETHERITE_SCRAP, 259, (char) 0xE3BA, ARMOR_PART),
    FARAWAY_MAP(FarawayMap.class, PAPER, 249, (char) 0xE3BB, TECHNICAL),
    OAKNUT(TreeSeed.class, BEETROOT_SEEDS, 251, (char) 0xE3BC, TREE_SEED),
    BIRCH_SEED(TreeSeed.class, BEETROOT_SEEDS, 252, (char) 0xE3BD, TREE_SEED),
    SPRUCE_CONE(TreeSeed.class, BEETROOT_SEEDS, 253, (char) 0xE3BE, TREE_SEED),
    JUNGLE_SEED(TreeSeed.class, BEETROOT_SEEDS, 254, (char) 0xE3BF, TREE_SEED),
    ACACIA_SEED(TreeSeed.class, BEETROOT_SEEDS, 255, (char) 0xE3C0, TREE_SEED),
    DARK_OAK_SEED(TreeSeed.class, BEETROOT_SEEDS, 256, (char) 0xE3C1, TREE_SEED),
    AZALEA_SEED(TreeSeed.class, BEETROOT_SEEDS, 260, (char) 0xE3C2, TREE_SEED),
    SCOTCH_PINE_CONE(TreeSeed.class, BEETROOT_SEEDS, 261, (char) 0xE3C3, TREE_SEED),
    FIR_CONE(TreeSeed.class, BEETROOT_SEEDS, 262, (char) 0xE3C4, TREE_SEED),
    FANCY_OAK_SEED(TreeSeed.class, BEETROOT_SEEDS, 263, (char) 0xE3C5, TREE_SEED, Animation.frametime(6)),
    FANCY_BIRCH_SEED(TreeSeed.class, BEETROOT_SEEDS, 264, (char) 0xE3C6, TREE_SEED, Animation.frametime(6)),
    FANCY_SPRUCE_CONE(TreeSeed.class, BEETROOT_SEEDS, 265, (char) 0xE3C7, TREE_SEED, Animation.frametime(6)),
    EMPTY_FLASK(DummyMytem.class, GLASS_BOTTLE, 257, (char) 0xE3C8, POTIONS),
    POTION_FLASK(PotionFlask.class, POTION, 258, (char) 0xE3C9, POTIONS),
    TETRIS_I(DummyMytem.class, SAND, 276, (char) 0xE3CA, TETRIS),
    TETRIS_O(DummyMytem.class, SAND, 277, (char) 0xE3CB, TETRIS),
    TETRIS_T(DummyMytem.class, SAND, 278, (char) 0xE3CC, TETRIS),
    TETRIS_L(DummyMytem.class, SAND, 279, (char) 0xE3CD, TETRIS),
    TETRIS_J(DummyMytem.class, SAND, 280, (char) 0xE3CE, TETRIS),
    TETRIS_S(DummyMytem.class, SAND, 281, (char) 0xE3CF, TETRIS),
    TETRIS_Z(DummyMytem.class, SAND, 282, (char) 0xE3D0, TETRIS),
    GOLDEN_CUP(Trophy.class, GOLD_INGOT, 108, (char) 0xE2AB, TROPHY),
    SILVER_CUP(Trophy.class, IRON_INGOT, 292, (char) 0xE3D1, TROPHY),
    BRONZE_CUP(Trophy.class, COPPER_INGOT, 293, (char) 0xE3D2, TROPHY),
    PARTICIPATION_CUP(Trophy.class, BLUE_CONCRETE, 298, (char) 0xE3D3, TROPHY),
    GOLD_MEDAL(Trophy.class, GOLD_NUGGET, 294, (char) 0xE3D4, TROPHY),
    SILVER_MEDAL(Trophy.class, IRON_NUGGET, 295, (char) 0xE3D5, TROPHY),
    BRONZE_MEDAL(Trophy.class, COPPER_INGOT, 296, (char) 0xE3D6, TROPHY),
    PARTICIPATION_MEDAL(Trophy.class, BLUE_WOOL, 299, (char) 0xE3D7, TROPHY),
    GOLD_EASTER_TROPHY(Trophy.class, EGG, 300, (char) 0xE3D8, TROPHY),
    SILVER_EASTER_TROPHY(Trophy.class, EGG, 301, (char) 0xE3D9, TROPHY),
    BRONZE_EASTER_TROPHY(Trophy.class, EGG, 302, (char) 0xE3DA, TROPHY),
    PARTICIPATION_EASTER_TROPHY(Trophy.class, EGG, 303, (char) 0xE3DB, TROPHY),
    GOLD_VERTIGO_TROPHY(Trophy.class, LAVA_BUCKET, 308, (char) 0xE3DC, TROPHY),
    SILVER_VERTIGO_TROPHY(Trophy.class, BUCKET, 309, (char) 0xE3DD, TROPHY),
    BRONZE_VERTIGO_TROPHY(Trophy.class, WATER_BUCKET, 310, (char) 0xE3DE, TROPHY),
    PARTICIPATION_VERTIGO_TROPHY(Trophy.class, BUCKET, 311, (char) 0xE3DF, TROPHY),
    GOLD_LADDER_TROPHY(Trophy.class, LADDER, 312, (char) 0xE3E0, TROPHY),
    SILVER_LADDER_TROPHY(Trophy.class, LADDER, 313, (char) 0xE3E1, TROPHY),
    BRONZE_LADDER_TROPHY(Trophy.class, LADDER, 314, (char) 0xE3E2, TROPHY),
    PARTICIPATION_LADDER_TROPHY(Trophy.class, LADDER, 315, (char) 0xE3E3, TROPHY),
    GOLD_CAVEPAINT_TROPHY(Trophy.class, PAINTING, 317, (char) 0xE3E4, TROPHY),
    SILVER_CAVEPAINT_TROPHY(Trophy.class, PAINTING, 318, (char) 0xE3E5, TROPHY),
    BRONZE_CAVEPAINT_TROPHY(Trophy.class, PAINTING, 319, (char) 0xE3E6, TROPHY),
    PARTICIPATION_CAVEPAINT_TROPHY(Trophy.class, PAINTING, 320, (char) 0xE3E7, TROPHY),
    GOLD_RED_GREEN_LIGHT_TROPHY(Trophy.class, GOLD_INGOT, 321, (char) 0xE3E8, TROPHY),
    SILVER_RED_GREEN_LIGHT_TROPHY(Trophy.class, IRON_INGOT, 322, (char) 0xE3E9, TROPHY),
    BRONZE_RED_GREEN_LIGHT_TROPHY(Trophy.class, COPPER_INGOT, 323, (char) 0xE3EA, TROPHY),
    PART_RED_GREEN_LIGHT_TROPHY(Trophy.class, BLUE_CONCRETE, 324, (char) 0xE3EB, TROPHY),
    GOLD_HIDE_AND_SEEK_TROPHY(Trophy.class, GOLD_INGOT, 325, (char) 0xE3EC, TROPHY),
    SILVER_HIDE_AND_SEEK_TROPHY(Trophy.class, IRON_INGOT, 326, (char) 0xE3ED, TROPHY),
    BRONZE_HIDE_AND_SEEK_TROPHY(Trophy.class, COPPER_INGOT, 327, (char) 0xE3EE, TROPHY),
    PART_HIDE_AND_SEEK_TROPHY(Trophy.class, BLUE_CONCRETE, 328, (char) 0xE3EF, TROPHY),
    GOLD_VOTE_TROPHY(Trophy.class, GOLD_INGOT, 329, (char) 0xE3F0, TROPHY),
    SILVER_VOTE_TROPHY(Trophy.class, IRON_INGOT, 330, (char) 0xE3F1, TROPHY),
    BRONZE_VOTE_TROPHY(Trophy.class, COPPER_INGOT, 331, (char) 0xE3F2, TROPHY),
    PART_VOTE_TROPHY(Trophy.class, BLUE_CONCRETE, 332, (char) 0xE3F3, TROPHY),
    GOLDEN_SPLEEF_SHOVEL(Trophy.class, GOLDEN_SHOVEL, 341, (char) 0xE3F4, TROPHY),
    SILVER_SPLEEF_SHOVEL(Trophy.class, IRON_SHOVEL, 342, (char) 0xE3F5, TROPHY),
    BRONZE_SPLEEF_SHOVEL(Trophy.class, STONE_SHOVEL, 343, (char) 0xE3F6, TROPHY),
    BLUE_SPLEEF_SHOVEL(Trophy.class, WOODEN_SHOVEL, 344, (char) 0xE3F7, TROPHY),
    GOLDEN_END_FIGHT_TROPHY(Trophy.class, GOLDEN_SWORD, 588, (char) 0xE3F8, TROPHY),
    SILVER_END_FIGHT_TROPHY(Trophy.class, GOLDEN_SWORD, 589, (char) 0xE3F9, TROPHY),
    BRONZE_END_FIGHT_TROPHY(Trophy.class, GOLDEN_SWORD, 590, (char) 0xE3FA, TROPHY),
    BLUE_END_FIGHT_TROPHY(Trophy.class, GOLDEN_SWORD, 591, (char) 0xE3FB, TROPHY),
    PHOTO(Photo.class, FILLED_MAP, 306, (char) 0xE3FC, PHOTOS),
    DEBUG(DummyMytem.class, DIAMOND, 20, (char) 0xE3FD, UTILITY),
    MOBSLAYER(Mobslayer.class, NETHERITE_SWORD, 719, (char) 0xE3FE, MOBSLAYERS),
    MOBSLAYER2(Mobslayer.class, NETHERITE_SWORD, 720, (char) 0xE3FF, MOBSLAYERS),
    MOBSLAYER3(Mobslayer.class, NETHERITE_SWORD, 721, (char) 0xE400, MOBSLAYERS),
    BINGO_BUKKIT(BingoBukkit.class, WOODEN_PICKAXE, 722, (char) 0xE401, BINGO_BUKKITS),
    GOLD_BINGO_BUKKIT(BingoBukkit.class, WOODEN_PICKAXE, 723, (char) 0xE402, BINGO_BUKKITS),
    DIAMOND_BINGO_BUKKIT(BingoBukkit.class, WOODEN_PICKAXE, 724, (char) 0xE403, BINGO_BUKKITS),
    SEALED_CAVEBOY(SealedCaveboy.class, WOODEN_SHOVEL, 748, (char) 0xE404, CAVEBOY),
    CAVEBOY_TICTACTOE(Caveboy.class, IRON_SHOVEL, 746, (char) 0xE405, CAVEBOY),
    CAVEBOY_TNTSWEEP(Caveboy.class, IRON_SHOVEL, 747, (char) 0xE406, CAVEBOY),
    GOLDEN_QUIVER(EquipmentItem.class, STICK, 345, (char) 0xE407, EQUIP_QUIVER),
    STEEL_QUIVER(EquipmentItem.class, STICK, 346, (char) 0xE408, EQUIP_QUIVER),
    WOODEN_QUIVER(EquipmentItem.class, STICK, 347, (char) 0xE409, EQUIP_QUIVER),
    EARTH_BROADSWORD(EquipmentItem.class, IRON_SWORD, 348, (char) 0xE40A, EQUIP_BROADSWORD),
    ICE_BROADSWORD(EquipmentItem.class, IRON_SWORD, 349, (char) 0xE40B, EQUIP_BROADSWORD),
    STEEL_BROADSWORD(EquipmentItem.class, IRON_SWORD, 350, (char) 0xE40C, EQUIP_BROADSWORD),
    SERRATED_BROADSWORD(EquipmentItem.class, IRON_SWORD, 351, (char) 0xE40D, EQUIP_BROADSWORD),
    GOLDEN_BROADSWORD(EquipmentItem.class, GOLDEN_SWORD, 352, (char) 0xE40E, EQUIP_BROADSWORD),
    GILDED_STEEL_HAMMER(EquipmentItem.class, IRON_AXE, 353, (char) 0xE40F, EQUIP_HAMMER),
    STEEL_HAMMER(EquipmentItem.class, IRON_AXE, 354, (char) 0xE410, EQUIP_HAMMER),
    WOODEN_MALLET(EquipmentItem.class, WOODEN_AXE, 355, (char) 0xE411, EQUIP_HAMMER),
    GILDED_STEEL_MALLET(EquipmentItem.class, IRON_AXE, 356, (char) 0xE412, EQUIP_HAMMER),
    STEEL_MALLET(EquipmentItem.class, IRON_AXE, 357, (char) 0xE413, EQUIP_HAMMER),
    WOODEN_HAMMER(EquipmentItem.class, WOODEN_AXE, 358, (char) 0xE414, EQUIP_HAMMER),
    GILDED_STEEL_CUTLASS(EquipmentItem.class, IRON_SWORD, 359, (char) 0xE415, EQUIP_CUTLASS),
    FIRE_CUTLASS(EquipmentItem.class, IRON_SWORD, 360, (char) 0xE416, EQUIP_CUTLASS),
    COPPER_CUTLASS(EquipmentItem.class, IRON_SWORD, 361, (char) 0xE417, EQUIP_CUTLASS),
    EARTH_CUTLASS(EquipmentItem.class, IRON_SWORD, 362, (char) 0xE418, EQUIP_CUTLASS),
    WATER_CUTLASS(EquipmentItem.class, IRON_SWORD, 363, (char) 0xE419, EQUIP_CUTLASS),
    SERRATED_STEEL_CUTLASS(EquipmentItem.class, IRON_SWORD, 364, (char) 0xE41A, EQUIP_CUTLASS),
    GILDED_STEEL_SWORD(EquipmentItem.class, IRON_SWORD, 365, (char) 0xE41B, EQUIP_SWORD),
    STEEL_KATANA(EquipmentItem.class, IRON_SWORD, 366, (char) 0xE41C, EQUIP_SWORD),
    ICE_SWORD(EquipmentItem.class, IRON_SWORD, 367, (char) 0xE41D, EQUIP_SWORD),
    SERRATED_STEEL_SWORD(EquipmentItem.class, IRON_SWORD, 368, (char) 0xE41E, EQUIP_SWORD),
    FIRE_SWORD(EquipmentItem.class, IRON_SWORD, 369, (char) 0xE41F, EQUIP_SWORD),
    STEEL_SWORD(EquipmentItem.class, IRON_SWORD, 370, (char) 0xE420, EQUIP_SWORD),
    COPPER_SWORD(EquipmentItem.class, IRON_SWORD, 371, (char) 0xE421, EQUIP_SWORD),
    GILDED_WOODEN_BOW(EquipmentItem.class, BOW, 372, (char) 0xE422, EQUIP_BOW),
    WOODEN_BOW(EquipmentItem.class, BOW, 373, (char) 0xE423, EQUIP_BOW),
    WOODEN_COMPOSITE_BOW(EquipmentItem.class, BOW, 374, (char) 0xE424, EQUIP_BOW),
    GOLDEN_COMPOSITE_BOW(EquipmentItem.class, BOW, 375, (char) 0xE425, EQUIP_BOW),
    STEEL_COMPOSITE_BOW(EquipmentItem.class, BOW, 376, (char) 0xE426, EQUIP_BOW),
    WOODEN_CROSSBOW(EquipmentItem.class, BOW, 377, (char) 0xE427, EQUIP_BOW),
    VIOLET_BATTLE_SCYTHE(EquipmentItem.class, IRON_AXE, 378, (char) 0xE428, EQUIP_SCYTHE),
    GOLDEN_BATTLE_SCYTHE(EquipmentItem.class, GOLDEN_AXE, 379, (char) 0xE429, EQUIP_SCYTHE),
    IRON_BATTLE_SCYTHE(EquipmentItem.class, IRON_AXE, 380, (char) 0xE42A, EQUIP_SCYTHE),
    IRON_GRAND_BATTLE_SCYTHE(EquipmentItem.class, IRON_AXE, 381, (char) 0xE42B, EQUIP_SCYTHE),
    STEEL_BATTLE_SCYTHE(EquipmentItem.class, IRON_AXE, 382, (char) 0xE42C, EQUIP_SCYTHE),
    TURQUOISE_BATTLE_SCYTHE(EquipmentItem.class, IRON_AXE, 383, (char) 0xE42D, EQUIP_SCYTHE),
    RUSTY_DAGGER(EquipmentItem.class, IRON_SWORD, 384, (char) 0xE42E, EQUIP_DAGGER),
    SERRATED_STEEL_DAGGER(EquipmentItem.class, IRON_SWORD, 385, (char) 0xE42F, EQUIP_DAGGER),
    COPPER_DAGGER(EquipmentItem.class, IRON_SWORD, 386, (char) 0xE430, EQUIP_DAGGER),
    STEEL_NINJA_SAIS(EquipmentItem.class, IRON_SWORD, 387, (char) 0xE431, EQUIP_DAGGER),
    GILDED_STEEL_DAGGER(EquipmentItem.class, IRON_SWORD, 388, (char) 0xE432, EQUIP_DAGGER),
    STEEL_DAGGER(EquipmentItem.class, IRON_SWORD, 389, (char) 0xE433, EQUIP_DAGGER),
    ICE_WHIP(EquipmentItem.class, LEAD, 390, (char) 0xE434, EQUIP_WHIP),
    GREEN_FLAIL(EquipmentItem.class, IRON_AXE, 391, (char) 0xE435, EQUIP_WHIP),
    FIRE_WHIP(EquipmentItem.class, LEAD, 392, (char) 0xE436, EQUIP_WHIP),
    CHAIN_WHIP(EquipmentItem.class, LEAD, 393, (char) 0xE437, EQUIP_WHIP),
    LEATHER_WHIP(EquipmentItem.class, LEAD, 394, (char) 0xE438, EQUIP_WHIP),
    EARTH_WHIP(EquipmentItem.class, LEAD, 395, (char) 0xE439, EQUIP_WHIP),
    SPIKED_STEEL_ROUND_SHIELD(EquipmentItem.class, SHIELD, 396, (char) 0xE43A, EQUIP_SHIELD),
    CLOVER_ROUND_SHIELD(EquipmentItem.class, SHIELD, 397, (char) 0xE43B, EQUIP_SHIELD),
    FIRE_KITE_SHIELD(EquipmentItem.class, SHIELD, 398, (char) 0xE43C, EQUIP_SHIELD),
    WATER_WANKEL_SHIELD(EquipmentItem.class, SHIELD, 399, (char) 0xE43D, EQUIP_SHIELD),
    FIRE_WANKEL_SHIELD(EquipmentItem.class, SHIELD, 400, (char) 0xE43E, EQUIP_SHIELD),
    ICE_WANKEL_SHIELD(EquipmentItem.class, SHIELD, 401, (char) 0xE43F, EQUIP_SHIELD),
    COPPER_WANKEL_SHIELD(EquipmentItem.class, SHIELD, 402, (char) 0xE440, EQUIP_SHIELD),
    CLOVER_WANKEL_SHIELD(EquipmentItem.class, SHIELD, 403, (char) 0xE441, EQUIP_SHIELD),
    COPPER_ROUND_SHIELD(EquipmentItem.class, SHIELD, 404, (char) 0xE442, EQUIP_SHIELD),
    COATED_KITE_SHIELD(EquipmentItem.class, SHIELD, 405, (char) 0xE443, EQUIP_SHIELD),
    STEEL_ROUND_SHIELD(EquipmentItem.class, SHIELD, 406, (char) 0xE444, EQUIP_SHIELD),
    EARTH_WANKEL_SHIELD(EquipmentItem.class, SHIELD, 407, (char) 0xE445, EQUIP_SHIELD),
    STEEL_KITE_SHIELD(EquipmentItem.class, SHIELD, 408, (char) 0xE446, EQUIP_SHIELD),
    COPPER_KITE_SHIELD(EquipmentItem.class, SHIELD, 409, (char) 0xE447, EQUIP_SHIELD),
    GILDED_IRON_BATTLE_AXE(EquipmentItem.class, IRON_AXE, 410, (char) 0xE448, EQUIP_AXE),
    SMALL_IRON_HATCHET(EquipmentItem.class, IRON_HELMET, 411, (char) 0xE449, EQUIP_AXE),
    IRON_HATCHET(EquipmentItem.class, IRON_HELMET, 412, (char) 0xE44A, EQUIP_AXE),
    IRON_BROAD_AXE(EquipmentItem.class, IRON_AXE, 413, (char) 0xE44B, EQUIP_AXE),
    GILDED_IRON_MAUL(EquipmentItem.class, IRON_AXE, 414, (char) 0xE44C, EQUIP_AXE),
    STEEL_TRIDENT(EquipmentItem.class, TRIDENT, 415, (char) 0xE44D, EQUIP_SPEAR),
    GOLDEN_TRIDENT(EquipmentItem.class, TRIDENT, 416, (char) 0xE44E, EQUIP_SPEAR),
    WOODEN_JAVELIN(EquipmentItem.class, TRIDENT, 417, (char) 0xE44F, EQUIP_SPEAR),
    STEEL_SPEAR(EquipmentItem.class, TRIDENT, 418, (char) 0xE450, EQUIP_SPEAR),
    STEEL_JAVELIN(EquipmentItem.class, TRIDENT, 419, (char) 0xE451, EQUIP_SPEAR),
    SPIKED_WOODEN_CLUB(EquipmentItem.class, WOODEN_AXE, 420, (char) 0xE452, EQUIP_MACE),
    IRON_MACE(EquipmentItem.class, IRON_AXE, 421, (char) 0xE453, EQUIP_MACE),
    SPIKED_IRON_MACE(EquipmentItem.class, IRON_AXE, 422, (char) 0xE454, EQUIP_MACE),
    GILDED_BAT(EquipmentItem.class, GOLDEN_AXE, 423, (char) 0xE455, EQUIP_MACE),
    WOODEN_MACE(EquipmentItem.class, WOODEN_AXE, 424, (char) 0xE456, EQUIP_MACE),
    WOODEN_BAT(EquipmentItem.class, WOODEN_AXE, 425, (char) 0xE457, EQUIP_MACE),
    VIOLET_DOUBLE_POLEARM(EquipmentItem.class, IRON_SWORD, 426, (char) 0xE458, EQUIP_POLEARM),
    STEEL_POLEARM(EquipmentItem.class, IRON_SWORD, 427, (char) 0xE459, EQUIP_POLEARM),
    GOLDEN_NECKLACE_AMETHYSTS(EquipmentItem.class, GOLD_INGOT, 428, (char) 0xE45A, EQUIP_NECKLACE),
    GOLDEN_NECKLACE_DIAMONDS(EquipmentItem.class, GOLD_INGOT, 429, (char) 0xE45B, EQUIP_NECKLACE),
    GOLDEN_NECKLACE_JADE(EquipmentItem.class, GOLD_INGOT, 430, (char) 0xE45C, EQUIP_NECKLACE),
    LEATHER_COLLAR_GOLD_CROSS(EquipmentItem.class, IRON_INGOT, 431, (char) 0xE45D, EQUIP_NECKLACE),
    GOLDEN_COLLAR(EquipmentItem.class, GOLD_INGOT, 432, (char) 0xE45E, EQUIP_NECKLACE),
    SILVER_NECKLACE_PEARL(EquipmentItem.class, IRON_INGOT, 433, (char) 0xE45F, EQUIP_NECKLACE),
    LEATHER_COLLAR(EquipmentItem.class, IRON_INGOT, 434, (char) 0xE460, EQUIP_NECKLACE),
    SILVER_NECKLACE_HEART(EquipmentItem.class, IRON_INGOT, 435, (char) 0xE461, EQUIP_NECKLACE),
    LEATHER_COLLAR_DIAMOND_SHARD(EquipmentItem.class, IRON_INGOT, 436, (char) 0xE462, EQUIP_NECKLACE),
    GREEN_DECORATED_IRON_HELMET(EquipmentItem.class, IRON_HELMET, 437, (char) 0xE463, EQUIP_HELMET),
    DECORATED_GILDED_KING_HELMET(EquipmentItem.class, GOLDEN_HELMET, 438, (char) 0xE464, EQUIP_HELMET),
    BLUE_DECORATED_IRON_HELMET(EquipmentItem.class, IRON_HELMET, 439, (char) 0xE465, EQUIP_HELMET),
    RED_DECORATED_IRON_HELMET(EquipmentItem.class, IRON_HELMET, 440, (char) 0xE466, EQUIP_HELMET),
    GOLDEN_HUGE_DIAMOND_RING(EquipmentItem.class, GOLD_NUGGET, 441, (char) 0xE467, EQUIP_RING),
    SILVER_LINCHPIN_RING(EquipmentItem.class, IRON_NUGGET, 442, (char) 0xE468, EQUIP_RING),
    GOLDEN_HEART_RING(EquipmentItem.class, GOLD_NUGGET, 443, (char) 0xE469, EQUIP_RING),
    SILVER_BERYL_RING(EquipmentItem.class, IRON_NUGGET, 444, (char) 0xE46A, EQUIP_RING),
    GOLDEN_RING(EquipmentItem.class, GOLD_NUGGET, 445, (char) 0xE46B, EQUIP_RING),
    SILVER_RUBY_RING(EquipmentItem.class, IRON_NUGGET, 446, (char) 0xE46C, EQUIP_RING),
    SILVER_OPAL_RING(EquipmentItem.class, IRON_NUGGET, 447, (char) 0xE46D, EQUIP_RING),
    GOLDEN_AMETHYST_RING(EquipmentItem.class, GOLD_NUGGET, 448, (char) 0xE46E, EQUIP_RING),
    GOLDEN_BIG_PEARL_RING(EquipmentItem.class, GOLD_NUGGET, 449, (char) 0xE46F, EQUIP_RING),
    GOLDEN_DIAMOND_RING(EquipmentItem.class, GOLD_NUGGET, 450, (char) 0xE470, EQUIP_RING),
    GOLDEN_BIG_HEART_RING(EquipmentItem.class, GOLD_NUGGET, 451, (char) 0xE471, EQUIP_RING),
    SILVER_DIAMOND_RING(EquipmentItem.class, IRON_NUGGET, 452, (char) 0xE472, EQUIP_RING),
    GOLDEN_PEARL_RING(EquipmentItem.class, GOLD_NUGGET, 453, (char) 0xE473, EQUIP_RING),
    SILVER_AND_GOLD_RINGS(EquipmentItem.class, IRON_NUGGET, 454, (char) 0xE474, EQUIP_RING),
    SILVER_RING_MISSING_GEM(EquipmentItem.class, IRON_NUGGET, 455, (char) 0xE475, EQUIP_RING),
    GOLDEN_EMERALD_RING(EquipmentItem.class, GOLD_NUGGET, 456, (char) 0xE476, EQUIP_RING),
    SILVER_SHIELD_RING(EquipmentItem.class, SHIELD, 457, (char) 0xE477, EQUIP_RING),
    SILVER_BIG_SHIELD_RING(EquipmentItem.class, SHIELD, 458, (char) 0xE478, EQUIP_RING),
    SILVER_RING(EquipmentItem.class, IRON_NUGGET, 459, (char) 0xE479, EQUIP_RING),
    GOLDEN_RUBY_RING(EquipmentItem.class, GOLD_NUGGET, 460, (char) 0xE47A, EQUIP_RING),
    FIRE_WAND(EquipmentItem.class, STICK, 461, (char) 0xE47B, EQUIP_WAND),
    RED_WAND(EquipmentItem.class, STICK, 462, (char) 0xE47C, EQUIP_WAND),
    ICE_WAND(EquipmentItem.class, STICK, 463, (char) 0xE47D, EQUIP_WAND),
    BLUE_WAND(EquipmentItem.class, STICK, 464, (char) 0xE47E, EQUIP_WAND),
    BLUE_SPARK_WAND(EquipmentItem.class, STICK, 465, (char) 0xE47F, EQUIP_WAND),
    PURPLE_SPARK_WAND(EquipmentItem.class, STICK, 466, (char) 0xE480, EQUIP_WAND),
    EARTH_WAND(EquipmentItem.class, STICK, 467, (char) 0xE481, EQUIP_WAND),
    WATER_WAND(EquipmentItem.class, STICK, 468, (char) 0xE482, EQUIP_WAND),
    PURPLE_WAND(EquipmentItem.class, STICK, 469, (char) 0xE483, EQUIP_WAND),
    RED_SPARK_WAND(EquipmentItem.class, STICK, 470, (char) 0xE484, EQUIP_WAND),
    SMALL_RED_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 471, (char) 0xE485, EQUIP_CLOAK),
    RED_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 472, (char) 0xE486, EQUIP_CLOAK),
    BLUE_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 473, (char) 0xE487, EQUIP_CLOAK),
    ORANGE_HOODED_CLOAK(EquipmentItem.class, IRON_HELMET, 474, (char) 0xE488, EQUIP_CLOAK),
    SMALL_EARTH_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 475, (char) 0xE489, EQUIP_CLOAK),
    RED_HOODED_CLOAK(EquipmentItem.class, IRON_HELMET, 476, (char) 0xE48A, EQUIP_CLOAK),
    FIRE_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 477, (char) 0xE48B, EQUIP_CLOAK),
    BLUE_FUR_COLLAR_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 478, (char) 0xE48C, EQUIP_CLOAK),
    EARTH_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 479, (char) 0xE48D, EQUIP_CLOAK),
    WATER_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 480, (char) 0xE48E, EQUIP_CLOAK),
    SMALL_ICE_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 481, (char) 0xE48F, EQUIP_CLOAK),
    ORANGE_FUR_COLOR_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 482, (char) 0xE490, EQUIP_CLOAK),
    SMALL_ORANGE_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 483, (char) 0xE491, EQUIP_CLOAK),
    SMALL_WATER_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 484, (char) 0xE492, EQUIP_CLOAK),
    RED_FUR_COLLAR_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 485, (char) 0xE493, EQUIP_CLOAK),
    BLUE_HOODED_CLOAK(EquipmentItem.class, IRON_HELMET, 486, (char) 0xE494, EQUIP_CLOAK),
    ORANGE_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 487, (char) 0xE495, EQUIP_CLOAK),
    ICE_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 488, (char) 0xE496, EQUIP_CLOAK),
    SMALL_FIRE_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 489, (char) 0xE497, EQUIP_CLOAK),
    SMALL_BLUE_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 490, (char) 0xE498, EQUIP_CLOAK),
    WATER_SCEPTER(EquipmentItem.class, STICK, 491, (char) 0xE499, EQUIP_SCEPTER),
    EARTH_SCEPTER(EquipmentItem.class, STICK, 492, (char) 0xE49A, EQUIP_SCEPTER),
    FIRE_SCEPTER(EquipmentItem.class, STICK, 493, (char) 0xE49B, EQUIP_SCEPTER),
    ICE_SCEPTER(EquipmentItem.class, STICK, 494, (char) 0xE49C, EQUIP_SCEPTER),
    EARTH_STAFF(EquipmentItem.class, STICK, 495, (char) 0xE49D, EQUIP_STAFF),
    ICE_STAFF(EquipmentItem.class, STICK, 496, (char) 0xE49E, EQUIP_STAFF),
    FIRE_STAFF(EquipmentItem.class, STICK, 497, (char) 0xE49F, EQUIP_STAFF),
    WATER_STAFF(EquipmentItem.class, STICK, 498, (char) 0xE4A0, EQUIP_STAFF),
    WATER_CANE(EquipmentItem.class, STICK, 499, (char) 0xE4A1, EQUIP_CANE),
    FIRE_CANE(EquipmentItem.class, STICK, 500, (char) 0xE4A2, EQUIP_CANE),
    ICE_CANE(EquipmentItem.class, STICK, 501, (char) 0xE4A3, EQUIP_CANE),
    EARTH_CANE(EquipmentItem.class, STICK, 502, (char) 0xE4A4, EQUIP_CANE),
    GILDED_LORD_GAUNTLETS(EquipmentItem.class, GOLDEN_BOOTS, 503, (char) 0xE4A5, EQUIP_GILDED_LORD_SET),
    GILDED_LORD_CHESTPLATE(EquipmentItem.class, GOLDEN_CHESTPLATE, 504, (char) 0xE4A6, EQUIP_GILDED_LORD_SET),
    GILDED_LORD_HELMET(EquipmentItem.class, GOLDEN_HELMET, 505, (char) 0xE4A7, EQUIP_GILDED_LORD_SET),
    GILDED_LORD_GREAVES(EquipmentItem.class, GOLDEN_LEGGINGS, 506, (char) 0xE4A8, EQUIP_GILDED_LORD_SET),
    GILDED_LORD_SABATONS(EquipmentItem.class, GOLDEN_BOOTS, 507, (char) 0xE4A9, EQUIP_GILDED_LORD_SET),
    TURQUOISE_CLOTH_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 508, (char) 0xE4AA, EQUIP_TURQUOISE_CLOTH_SET),
    TURQUOISE_CLOTH_TUNIC(EquipmentItem.class, LEATHER_CHESTPLATE, 509, (char) 0xE4AB, EQUIP_TURQUOISE_CLOTH_SET),
    TURQUOISE_CLOTH_HAT(EquipmentItem.class, LEATHER_HELMET, 510, (char) 0xE4AC, EQUIP_TURQUOISE_CLOTH_SET),
    TURQUOISE_CLOTH_SNEAKERS(EquipmentItem.class, LEATHER_BOOTS, 511, (char) 0xE4AD, EQUIP_TURQUOISE_CLOTH_SET),
    TURQUOISE_CLOTH_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 512, (char) 0xE4AE, EQUIP_TURQUOISE_CLOTH_SET),
    YELLOW_WINGED_CLOTH_CAP(EquipmentItem.class, LEATHER_HELMET, 513, (char) 0xE4AF, EQUIP_YELLOW_WINGED_CLOTH_SET),
    YELLOW_WINGED_CLOTH_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 514, (char) 0xE4B0, EQUIP_YELLOW_WINGED_CLOTH_SET),
    YELLOW_WINGED_CLOTH_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 515, (char) 0xE4B1, EQUIP_YELLOW_WINGED_CLOTH_SET),
    YELLOW_WINGED_CLOTH_TUNIC(EquipmentItem.class, LEATHER_CHESTPLATE, 516, (char) 0xE4B2, EQUIP_YELLOW_WINGED_CLOTH_SET),
    YELLOW_WINGED_CLOTH_SNEAKERS(EquipmentItem.class, LEATHER_BOOTS, 517, (char) 0xE4B3, EQUIP_YELLOW_WINGED_CLOTH_SET),
    STUDDED_LEATHER_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 518, (char) 0xE4B4, EQUIP_STUDDED_LEATHER_SET),
    STUDDED_LEATHER_BOOTS(EquipmentItem.class, LEATHER_BOOTS, 519, (char) 0xE4B5, EQUIP_STUDDED_LEATHER_SET),
    STUDDED_LEATHER_HELMET(EquipmentItem.class, LEATHER_HELMET, 520, (char) 0xE4B6, EQUIP_STUDDED_LEATHER_SET),
    STUDDED_LEATHER_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 521, (char) 0xE4B7, EQUIP_STUDDED_LEATHER_SET),
    STUDDED_LEATHER_CHESTPLATE(EquipmentItem.class, LEATHER_CHESTPLATE, 522, (char) 0xE4B8, EQUIP_STUDDED_LEATHER_SET),
    ORANGE_CLOTH_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 523, (char) 0xE4B9, EQUIP_ORANGE_CLOTH_SET),
    ORANGE_CLOTH_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 524, (char) 0xE4BA, EQUIP_ORANGE_CLOTH_SET),
    ORANGE_CLOTH_HOOD(EquipmentItem.class, LEATHER_HELMET, 525, (char) 0xE4BB, EQUIP_ORANGE_CLOTH_SET),
    ORANGE_CLOTH_SNEAKERS(EquipmentItem.class, LEATHER_BOOTS, 526, (char) 0xE4BC, EQUIP_ORANGE_CLOTH_SET),
    ORANGE_CLOTH_TUNIC(EquipmentItem.class, LEATHER_CHESTPLATE, 527, (char) 0xE4BD, EQUIP_ORANGE_CLOTH_SET),
    PEACH_CLOTH_TUNIC(EquipmentItem.class, LEATHER_CHESTPLATE, 528, (char) 0xE4BE, EQUIP_PEACH_CLOTH_SET),
    PEACH_CLOTH_SNEAKERS(EquipmentItem.class, LEATHER_BOOTS, 529, (char) 0xE4BF, EQUIP_PEACH_CLOTH_SET),
    PEACH_CLOTH_HOOD(EquipmentItem.class, LEATHER_HELMET, 530, (char) 0xE4C0, EQUIP_PEACH_CLOTH_SET),
    PEACH_CLOTH_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 531, (char) 0xE4C1, EQUIP_PEACH_CLOTH_SET),
    PEACH_CLOTH_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 532, (char) 0xE4C2, EQUIP_PEACH_CLOTH_SET),
    IRON_KNIGHT_HELMET(EquipmentItem.class, IRON_HELMET, 533, (char) 0xE4C3, EQUIP_IRON_KNIGHT_SET),
    IRON_KNIGHT_GREAVES(EquipmentItem.class, IRON_LEGGINGS, 534, (char) 0xE4C4, EQUIP_IRON_KNIGHT_SET),
    IRON_KNIGHT_SABATONS(EquipmentItem.class, IRON_BOOTS, 535, (char) 0xE4C5, EQUIP_IRON_KNIGHT_SET),
    IRON_KNIGHT_GAUNTLETS(EquipmentItem.class, IRON_BOOTS, 536, (char) 0xE4C6, EQUIP_IRON_KNIGHT_SET),
    IRON_KNIGHT_CHESTPLATE(EquipmentItem.class, IRON_CHESTPLATE, 537, (char) 0xE4C7, EQUIP_IRON_KNIGHT_SET),
    PINK_WINGED_CLOTH_HAT(EquipmentItem.class, LEATHER_HELMET, 538, (char) 0xE4C8, EQUIP_PINK_WINGED_CLOTH_SET),
    PINK_WINGED_CLOTH_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 539, (char) 0xE4C9, EQUIP_PINK_WINGED_CLOTH_SET),
    PINK_WINGED_CLOTH_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 540, (char) 0xE4CA, EQUIP_PINK_WINGED_CLOTH_SET),
    PINK_WINGED_CLOTH_SNEAKERS(EquipmentItem.class, LEATHER_BOOTS, 541, (char) 0xE4CB, EQUIP_PINK_WINGED_CLOTH_SET),
    PINK_WINGED_CLOTH_TUNIC(EquipmentItem.class, LEATHER_CHESTPLATE, 542, (char) 0xE4CC, EQUIP_PINK_WINGED_CLOTH_SET),
    GREEN_FEATHER_CLOTH_TUNIC(EquipmentItem.class, LEATHER_CHESTPLATE, 543, (char) 0xE4CD, EQUIP_GREEN_FEATHER_SET),
    GREEN_FEATHER_CLOTH_CAP(EquipmentItem.class, LEATHER_HELMET, 544, (char) 0xE4CE, EQUIP_GREEN_FEATHER_SET),
    GREEN_FEATHER_CLOTH_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 545, (char) 0xE4CF, EQUIP_GREEN_FEATHER_SET),
    GREEN_FEATHER_CLOTH_SNEAKERS(EquipmentItem.class, LEATHER_BOOTS, 546, (char) 0xE4D0, EQUIP_GREEN_FEATHER_SET),
    GREEN_FEATHER_CLOTH_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 547, (char) 0xE4D1, EQUIP_GREEN_FEATHER_SET),
    FINE_IRON_GAUNTLETS(EquipmentItem.class, IRON_BOOTS, 548, (char) 0xE4D2, EQUIP_FINE_IRON_SET),
    FINE_IRON_CHESTPLATE(EquipmentItem.class, IRON_CHESTPLATE, 549, (char) 0xE4D3, EQUIP_FINE_IRON_SET),
    FINE_IRON_SABATONS(EquipmentItem.class, IRON_BOOTS, 550, (char) 0xE4D4, EQUIP_FINE_IRON_SET),
    FINE_IRON_GREAVES(EquipmentItem.class, IRON_LEGGINGS, 551, (char) 0xE4D5, EQUIP_FINE_IRON_SET),
    FINE_IRON_HELMET(EquipmentItem.class, IRON_HELMET, 552, (char) 0xE4D6, EQUIP_FINE_IRON_SET),
    GILDED_KING_SABATONS(EquipmentItem.class, GOLDEN_BOOTS, 553, (char) 0xE4D7, EQUIP_GILDED_KING_SET),
    GILDED_KING_GREAVES(EquipmentItem.class, GOLDEN_LEGGINGS, 554, (char) 0xE4D8, EQUIP_GILDED_KING_SET),
    GILDED_KING_GAUNTLETS(EquipmentItem.class, GOLDEN_BOOTS, 555, (char) 0xE4D9, EQUIP_GILDED_KING_SET),
    GILDED_KING_CHESTPLATE(EquipmentItem.class, GOLDEN_CHESTPLATE, 556, (char) 0xE4DA, EQUIP_GILDED_KING_SET),
    GILDED_KING_HELMET(EquipmentItem.class, GOLDEN_HELMET, 557, (char) 0xE4DB, EQUIP_GILDED_KING_SET),
    SIMPLE_LEATHER_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 558, (char) 0xE4DC, EQUIP_SIMPLE_LEATHER_SET),
    SIMPLE_LEATHER_VEST(EquipmentItem.class, LEATHER_CHESTPLATE, 559, (char) 0xE4DD, EQUIP_SIMPLE_LEATHER_SET),
    SIMPLE_LEATHER_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 560, (char) 0xE4DE, EQUIP_SIMPLE_LEATHER_SET),
    SIMPLE_LEATHER_BOOTS(EquipmentItem.class, LEATHER_BOOTS, 561, (char) 0xE4DF, EQUIP_SIMPLE_LEATHER_SET),
    SIMPLE_LEATHER_CAP(EquipmentItem.class, LEATHER_HELMET, 562, (char) 0xE4E0, EQUIP_SIMPLE_LEATHER_SET),
    BASIC_IRON_GREAVES(EquipmentItem.class, IRON_LEGGINGS, 563, (char) 0xE4E1, EQUIP_BASIC_IRON_SET),
    BASIC_IRON_CHESTPLATE(EquipmentItem.class, IRON_CHESTPLATE, 564, (char) 0xE4E2, EQUIP_BASIC_IRON_SET),
    BASIC_IRON_HELMET(EquipmentItem.class, IRON_HELMET, 565, (char) 0xE4E3, EQUIP_BASIC_IRON_SET),
    BASIC_IRON_SABATONS(EquipmentItem.class, IRON_BOOTS, 566, (char) 0xE4E4, EQUIP_BASIC_IRON_SET),
    BASIC_IRON_GAUNTLETS(EquipmentItem.class, IRON_BOOTS, 567, (char) 0xE4E5, EQUIP_BASIC_IRON_SET),
    BLUE_CLOTH_TUNIC(EquipmentItem.class, LEATHER_CHESTPLATE, 568, (char) 0xE4E6, EQUIP_BLUE_CLOTH_SET),
    BLUE_CLOTH_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 569, (char) 0xE4E7, EQUIP_BLUE_CLOTH_SET),
    BLUE_CLOTH_HOOD(EquipmentItem.class, LEATHER_HELMET, 570, (char) 0xE4E8, EQUIP_BLUE_CLOTH_SET),
    BLUE_CLOTH_SNEAKERS(EquipmentItem.class, LEATHER_BOOTS, 571, (char) 0xE4E9, EQUIP_BLUE_CLOTH_SET),
    BLUE_CLOTH_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 572, (char) 0xE4EA, EQUIP_BLUE_CLOTH_SET),
    FINE_LEATHER_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 573, (char) 0xE4EB, EQUIP_FINE_LEATHER_SET),
    FINE_LEATHER_BOOTS(EquipmentItem.class, LEATHER_BOOTS, 574, (char) 0xE4EC, EQUIP_FINE_LEATHER_SET),
    FINE_LEATHER_VEST(EquipmentItem.class, LEATHER_CHESTPLATE, 575, (char) 0xE4ED, EQUIP_FINE_LEATHER_SET),
    FINE_LEATHER_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 576, (char) 0xE4EE, EQUIP_FINE_LEATHER_SET),
    FINE_LEATHER_HELMET(EquipmentItem.class, LEATHER_HELMET, 577, (char) 0xE4EF, EQUIP_FINE_LEATHER_SET),
    BLUE_JESTER_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 578, (char) 0xE4F0, EQUIP_BLUE_JESTER_SET),
    BLUE_JESTER_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 579, (char) 0xE4F1, EQUIP_BLUE_JESTER_SET),
    BLUE_JESTER_HAT(EquipmentItem.class, LEATHER_HELMET, 580, (char) 0xE4F2, EQUIP_BLUE_JESTER_SET),
    BLUE_JESTER_SHOES(EquipmentItem.class, LEATHER_BOOTS, 581, (char) 0xE4F3, EQUIP_BLUE_JESTER_SET),
    BLUE_JESTER_TUNIC(EquipmentItem.class, LEATHER_CHESTPLATE, 582, (char) 0xE4F4, EQUIP_BLUE_JESTER_SET),
    RED_JESTER_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 583, (char) 0xE4F5, EQUIP_RED_JESTER_SET),
    RED_JESTER_HAT(EquipmentItem.class, LEATHER_HELMET, 584, (char) 0xE4F6, EQUIP_RED_JESTER_SET),
    RED_JESTER_TUNIC(EquipmentItem.class, LEATHER_CHESTPLATE, 585, (char) 0xE4F7, EQUIP_RED_JESTER_SET),
    RED_JESTER_SHOES(EquipmentItem.class, LEATHER_BOOTS, 586, (char) 0xE4F8, EQUIP_RED_JESTER_SET),
    RED_JESTER_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 587, (char) 0xE4F9, EQUIP_RED_JESTER_SET),
    ;

    private static final Map<String, Mytems> ID_MAP = new HashMap<>();
    public final String id;
    public final Class<? extends Mytem> mytemClass;
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

    Mytems(final Class<? extends Mytem> mytemClass, final Material material,
           final Integer customModelData,
           final char character, final char[] characters,
           final MytemsCategory category,
           final Animation animation) {
        this.mytemClass = mytemClass;
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

    Mytems(final Class<? extends Mytem> mytemClass, final Material material,
           final Integer customModelData,
           final char character, final char[] characters,
           final MytemsCategory category) {
        this(mytemClass, material, customModelData, character, characters, category, null);
    }

    Mytems(final Class<? extends Mytem> mytemClass, final Material material,
           final Integer customModelData,
           final char character,
           final MytemsCategory category) {
        this(mytemClass, material, customModelData, character, new char[] {character}, category);
    }

    Mytems(final Class<? extends Mytem> mytemClass, final Material material,
           final Integer customModelData,
           final char character,
           final MytemsCategory category,
           final Animation animation) {
        this(mytemClass, material, customModelData, character, new char[] {character}, category, animation);
    }

    Mytems(final Class<? extends Mytem> mytemClass, final Material material,
           final int customModelData, final MytemsCategory category) {
        this(mytemClass, material, customModelData, (char) customModelData, chrarr(customModelData), category);
    }

    Mytems(final Class<? extends Mytem> mytemClass, final Material material,
           final int customModelData, final MytemsCategory category, final Animation animation) {
        this(mytemClass, material, customModelData, (char) customModelData, chrarr(customModelData), category, animation);
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
    public ItemStack createIcon(int amount) {
        final ItemStack item;
        if (material == PLAYER_HEAD) {
            item = Skull.of(createItemStack()).create(amount);
        } else if (material == LEATHER_HELMET
                   || material == LEATHER_CHESTPLATE
                   || material == LEATHER_LEGGINGS
                   || material == LEATHER_BOOTS) {
            item = createItemStack(1);
            item.editMeta(meta -> {
                    meta.displayName(Component.empty());
                    meta.lore(List.of());
                });
        } else {
            item = new ItemStack(material, amount);
        }
        if (customModelData != null) {
            item.editMeta(meta -> meta.setCustomModelData(customModelData));
        }
        return item;
    }

    public ItemStack createIcon() {
        return createIcon(1);
    }

    public ItemStack createIcon(List<Component> text) {
        return Items.text(createIcon(1), text);
    }

    public ItemStack createIcon(int amount, List<Component> text) {
        return Items.text(createIcon(amount), text);
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

    public Component getCurrentAnimationFrame() {
        if (!isAnimated()) return component;
        long tick = System.currentTimeMillis() / 50L;
        int frame = (int) (tick % (long) getAnimationFrameCount());
        return getAnimationFrame(frame);
    }
}
