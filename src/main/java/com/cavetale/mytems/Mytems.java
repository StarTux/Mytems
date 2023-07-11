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
import com.cavetale.mytems.item.craft.NetheriteParityTable;
import com.cavetale.mytems.item.deflector.DeflectorShield;
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
import com.cavetale.mytems.item.finder.Finder;
import com.cavetale.mytems.item.font.GlyphItem;
import com.cavetale.mytems.item.garden.Scissors;
import com.cavetale.mytems.item.garden.Scythe;
import com.cavetale.mytems.item.halloween.HalloweenCandy;
import com.cavetale.mytems.item.halloween.HalloweenToken2;
import com.cavetale.mytems.item.halloween.HalloweenToken;
import com.cavetale.mytems.item.hourglass.Hourglass;
import com.cavetale.mytems.item.luminator.Luminator;
import com.cavetale.mytems.item.magnifier.Magnifier;
import com.cavetale.mytems.item.medieval.WitchBroom;
import com.cavetale.mytems.item.mobcostume.BeeCostume;
import com.cavetale.mytems.item.mobcostume.CactusCostume;
import com.cavetale.mytems.item.mobcostume.ChickenCostume;
import com.cavetale.mytems.item.mobcostume.CreeperCostume;
import com.cavetale.mytems.item.mobcostume.EndermanCostume;
import com.cavetale.mytems.item.mobcostume.FoxCostume;
import com.cavetale.mytems.item.mobcostume.SheepCostume;
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
import com.cavetale.mytems.item.spleef.SpleefShovel;
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
    MAGIC_CAPE(MagicCape.class, ELYTRA, 7413006, (char) 0xE238, chrarr(0xE238, 0xF001, 0xF002, 0xF003, 0xF004), UTILITY, Animation.MAGIC_CAPE),
    SNEAKERS(Sneakers.class, LEATHER_BOOTS, 333, (char) 0xF005, UTILITY),
    KITTY_COIN(KittyCoin.class, PLAYER_HEAD, 7413001, (char) 0xE200, CURRENCY),
    RAINBOW_KITTY_COIN(KittyCoin.class, PLAYER_HEAD, 7413007, (char) 0xE243, CURRENCY),
    CHRISTMAS_TOKEN(ChristmasToken.class, PLAYER_HEAD, 221, (char) 0xF006, CHRISTMAS),
    SANTA_HAT(SantaHat.class, PLAYER_HEAD, 7413101, (char) 0xE221, SANTA),
    SANTA_JACKET(SantaJacket.class, LEATHER_CHESTPLATE, 4713102, (char) 0xE222, SANTA),
    SANTA_PANTS(SantaPants.class, LEATHER_LEGGINGS, 4713103, (char) 0xE223, SANTA),
    SANTA_BOOTS(SantaBoots.class, LEATHER_BOOTS, 4713104, (char) 0xE224, SANTA),
    BLUE_CHRISTMAS_BALL(DummyMytem.class, BLUE_STAINED_GLASS, 214, (char) 0xF007, CHRISTMAS),
    GREEN_CHRISTMAS_BALL(DummyMytem.class, GREEN_STAINED_GLASS, 215, (char) 0xF008, CHRISTMAS),
    ORANGE_CHRISTMAS_BALL(DummyMytem.class, ORANGE_STAINED_GLASS, 216, (char) 0xF009, CHRISTMAS),
    PINK_CHRISTMAS_BALL(DummyMytem.class, PINK_STAINED_GLASS, 217, (char) 0xF00A, CHRISTMAS),
    PURPLE_CHRISTMAS_BALL(DummyMytem.class, PURPLE_STAINED_GLASS, 218, (char) 0xF00B, CHRISTMAS),
    YELLOW_CHRISTMAS_BALL(DummyMytem.class, YELLOW_STAINED_GLASS, 219, (char) 0xF00C, CHRISTMAS),
    KNITTED_BEANIE(ForbiddenMytem.class, LEATHER_HELMET, 222, (char) 0xF00D, CHRISTMAS),
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
    LOVE_LETTER(DummyMytem.class, PAPER, 0xF24D, VALENTINE_TOKENS),
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
    MAGIC_MAP(MagicMap.class, FILLED_MAP, 7413005, (char) 0xE21D, chrarr(0xE21D, 0xF00E, 0xF00F, 0xF010, 0xF011, 0xF012, 0xF013, 0xF014, 0xF015, 0xF016, 0xF017, 0xF018, 0xF019, 0xF01A, 0xF01B, 0xF01C), UTILITY, Animation.MAGIC_MAP),
    SNOW_SHOVEL(SnowShovel.class, IRON_SHOVEL, 220, (char) 0xF01D, UTILITY),
    HASTY_PICKAXE(ForbiddenMytem.class, GOLDEN_PICKAXE, 223, (char) 0xF01E, UTILITY),
    TREE_CHOPPER(TreeChopper.class, GOLDEN_AXE, 242, (char) 0xF01F, UTILITY),
    ARMOR_STAND_EDITOR(ArmorStandEditor.class, FLINT, 241, (char) 0xF020, UTILITY),
    FERTILIZER(Fertilizer.class, BONE_MEAL, 285, (char) 0xF021, UTILITY),
    WATERING_CAN(WateringCan.class, STONE_HOE, 297, (char) 0xF022, GARDENING),
    EMPTY_WATERING_CAN(EmptyWateringCan.class, STONE_HOE, 334, (char) 0xF023, GARDENING),
    GOLDEN_WATERING_CAN(WateringCan.class, GOLDEN_HOE, 307, (char) 0xF024, GARDENING),
    EMPTY_GOLDEN_WATERING_CAN(EmptyWateringCan.class, STONE_HOE, 335, (char) 0xF025, GARDENING),
    MONKEY_WRENCH(MonkeyWrench.class, STONE_HOE, 79, (char) 0xF026, UTILITY),
    DIVIDERS(Dividers.class, WOODEN_HOE, 593, (char) 0xF027, UTILITY),
    SLIME_FINDER(SlimeFinder.class, SLIME_BALL, 594, (char) 0xF028, UTILITY),
    BLIND_EYE(BlindEye.class, CARROT, 596, (char) 0xF029, UTILITY),
    SCISSORS(Scissors.class, SHEARS, 652, (char) 0xF02A, GARDENING),
    MAGNIFYING_GLASS(Magnifier.class, AMETHYST_SHARD, 653, (char) 0xF02B, UTILITY),
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
    CUPID_WINGS(WardrobeItem.class, FEATHER, 0xF258, WARDROBE_OFFHAND),
    BUTTERFLY_WINGS(WardrobeItem.class, FEATHER, 0xF269, WARDROBE_OFFHAND, Animation.BUTTERFLY_WINGS),
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
    BOOK_OF_MAY(DummyMytem.class, BOOK, 304, (char) 0xF02C, MAY),
    BEESTICK(Beestick.class, BLAZE_ROD, 305, (char) 0xF02D, UTILITY),
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
    POCKET_SNIFFER(PocketMob.class, SNIFFER_SPAWN_EGG, 908301, (char) 0, POCKET_MOB),
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
    BLUNDERBUSS(Blunderbuss.class, IRON_PICKAXE, 3, (char) 0xE23A, UTILITY),
    IRON_SCYTHE(Scythe.class, IRON_HOE, 650, (char) 0xF02E, GARDENING),
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
    RUBY_KITTY(DummyMytem.class, EMERALD, 651, (char) 0xF02F, CURRENCY),
    OK(ForbiddenMytem.class, BLUE_CONCRETE, 7, (char) 0xE23F, UI),
    NO(ForbiddenMytem.class, RED_CONCRETE, 8, (char) 0xE240, UI),
    ON(ForbiddenMytem.class, ENDER_EYE, 210, (char) 0xF030, UI),
    OFF(ForbiddenMytem.class, ENDER_PEARL, 211, (char) 0xF031, UI),
    REDO(ForbiddenMytem.class, EGG, 212, (char) 0xF032, UI),
    HALF_HEART(ForbiddenMytem.class, NAUTILUS_SHELL, 10, (char) 0xE242, UI),
    EMPTY_HEART(ForbiddenMytem.class, NAUTILUS_SHELL, 13, (char) 0xE246, UI),
    ARROW_RIGHT(ForbiddenMytem.class, ARROW, 11, (char) 0xE244, ARROWS),
    ARROW_LEFT(ForbiddenMytem.class, ARROW, 12, (char) 0xE245, ARROWS),
    ARROW_UP(ForbiddenMytem.class, ARROW, 37, (char) 0xE265, ARROWS),
    ARROW_DOWN(ForbiddenMytem.class, ARROW, 38, (char) 0xE266, ARROWS),
    TURN_LEFT(ForbiddenMytem.class, ARROW, 283, (char) 0xF033, ARROWS),
    TURN_RIGHT(ForbiddenMytem.class, ARROW, 284, (char) 0xF034, ARROWS),
    CHECKBOX(ForbiddenMytem.class, WHITE_CONCRETE, 14, (char) 0xE247, UI),
    CHECKED_CHECKBOX(ForbiddenMytem.class, GREEN_CONCRETE, 15, (char) 0xE248, chrarr(0xE248, 0xF035, 0xF036, 0xF037), UI, Animation.CHECKBOX),
    CROSSED_CHECKBOX(ForbiddenMytem.class, BARRIER, 16, (char) 0xE249, chrarr(0xE249, 0xF038, 0xF039, 0xF03A), UI, Animation.frametime(20)),
    EAGLE(ForbiddenMytem.class, FEATHER, 19, (char) 0xE24C, UI),
    EARTH(ForbiddenMytem.class, ENDER_PEARL, 5, (char) 0xE23D, UI),
    EASTER_EGG(DummyMytem.class, EGG, 345715, (char) 0xE23C, COLLECTIBLES),
    TRAFFIC_LIGHT(ForbiddenMytem.class, YELLOW_DYE, 57, (char) 0xE279, UI),
    INVISIBLE_ITEM(ForbiddenMytem.class, LIGHT_GRAY_STAINED_GLASS_PANE, 65, (char) 0xE281, UI),
    PAINT_PALETTE(ForbiddenMytem.class, STICK, 202, (char) 0xF03B, UI),
    PLUS_BUTTON(ForbiddenMytem.class, EGG, 266, (char) 0xF03C, UI),
    MINUS_BUTTON(ForbiddenMytem.class, SNOWBALL, 267, (char) 0xF03D, UI),
    FLOPPY_DISK(ForbiddenMytem.class, MUSIC_DISC_CAT, 268, (char) 0xF03E, UI),
    FOLDER(ForbiddenMytem.class, CHEST, 269, (char) 0xF03F, UI),
    MAGNET(ForbiddenMytem.class, IRON_NUGGET, 270, (char) 0xF040, UI),
    DATA_INTEGER(ForbiddenMytem.class, REPEATER, 271, (char) 0xF041, UI),
    DATA_STRING(ForbiddenMytem.class, CHAIN, 272, (char) 0xF042, UI),
    DATA_FLOAT(ForbiddenMytem.class, REPEATER, 273, (char) 0xF043, UI),
    BOMB(ForbiddenMytem.class, CHAIN, 274, (char) 0xF044, UI),
    MOUSE(ForbiddenMytem.class, WHITE_CONCRETE, 336, (char) 0xF045, UI),
    MOUSE_LEFT(ForbiddenMytem.class, LIGHT_BLUE_CONCRETE, 337, (char) 0xF046, UI),
    MOUSE_RIGHT(ForbiddenMytem.class, RED_CONCRETE, 338, (char) 0xF047, UI),
    SHIFT_KEY(ForbiddenMytem.class, LIGHT_GRAY_CONCRETE, 339, (char) 0xF048, UI),
    THUMBS_UP(ForbiddenMytem.class, GREEN_CONCRETE, 340, (char) 0xF049, UI),
    EYES(ForbiddenMytem.class, ENDER_EYE, 595, (char) 0xF04A, UI),
    CAVETALE_DUNGEON(ForbiddenMytem.class, SPAWNER, 706, (char) 0xF04B, UI),
    RAINBOW_BUTTERFLY(ForbiddenMytem.class, FEATHER, 633, (char) 0xF04C, chrarr(0xF04C, 0xF04D, 0xF04E, 0xF04F, 0xF050, 0xF051, 0xF052, 0xF053), UI, Animation.frametime(8)),
    SNOWFLAKE(ForbiddenMytem.class, SNOWBALL, 673, (char) 0xF054, chrarr(0xF054, 0xF055, 0xF056, 0xF057, 0xF058, 0xF059, 0xF05A, 0xF05B, 0xF05C, 0xF05D, 0xF05E, 0xF05F, 0xF060, 0xF061, 0xF062, 0xF063), UI, Animation.SNOWFLAKE),
    HEART(DummyMytem.class, HEART_OF_THE_SEA, 9, (char) 0xE241, chrarr(0xE241, 0xF252, 0xF253), COLLECTIBLES, Animation.HEART),
    STAR(DummyMytem.class, NETHER_STAR, 18, (char) 0xE24B, COLLECTIBLES),
    MOON(DummyMytem.class, YELLOW_DYE, 204, (char) 0xF064, COLLECTIBLES),
    GREEN_MOON(DummyMytem.class, LIME_DYE, 592, (char) 0xF065, COLLECTIBLES),
    LIGHTNING(DummyMytem.class, LIGHTNING_ROD, 250, (char) 0xF066, chrarr(0xF066, 0xF067, 0xF068, 0xF069, 0xF06A, 0xF06B, 0xF06C, 0xF06D, 0xF06E, 0xF06F, 0xF070, 0xF071, 0xF072, 0xF073, 0xF074, 0xF075, 0xF076, 0xF077), COLLECTIBLES, Animation.LIGHTNING),
    DICE(DiceItem.class, PLAYER_HEAD, 213, (char) 0xF078, DIE),
    DICE4(DiceItem.class, PRISMARINE_SHARD, 243, (char) 0xF079, DIE),
    DICE8(DiceItem.class, PRISMARINE_SHARD, 244, (char) 0xF07A, DIE),
    DICE10(DiceItem.class, PRISMARINE_SHARD, 245, (char) 0xF07B, DIE),
    DICE12(DiceItem.class, PRISMARINE_SHARD, 246, (char) 0xF07C, DIE),
    DICE20(DiceItem.class, PRISMARINE_SHARD, 247, (char) 0xF07D, DIE),
    DICE100(DiceItem.class, PRISMARINE_SHARD, 248, (char) 0xF07E, DIE),
    DICE_1(ForbiddenMytem.class, QUARTZ_BLOCK, 707, (char) 0xF07F, UI),
    DICE_2(ForbiddenMytem.class, QUARTZ_BLOCK, 708, (char) 0xF080, UI),
    DICE_3(ForbiddenMytem.class, QUARTZ_BLOCK, 709, (char) 0xF081, UI),
    DICE_4(ForbiddenMytem.class, QUARTZ_BLOCK, 710, (char) 0xF082, UI),
    DICE_5(ForbiddenMytem.class, QUARTZ_BLOCK, 711, (char) 0xF083, UI),
    DICE_6(ForbiddenMytem.class, QUARTZ_BLOCK, 712, (char) 0xF084, UI),
    DICE_ROLL(ForbiddenMytem.class, QUARTZ_BLOCK, 713, (char) 0xF085, chrarr(0xF085, 0xF086, 0xF087, 0xF088, 0xF089, 0xF08A), UI, Animation.frametime(3)),
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
    STEVE_FACE(ForbiddenMytem.class, SLIME_BALL, 234, (char) 0xF08B, REACTION),
    ALEX_FACE(ForbiddenMytem.class, SLIME_BALL, 235, (char) 0xF08C, REACTION),
    COW_FACE(ForbiddenMytem.class, SLIME_BALL, 224, (char) 0xF08D, MOB_FACE),
    CREEPER_FACE(ForbiddenMytem.class, SLIME_BALL, 225, (char) 0xF08E, MOB_FACE),
    ENDERMAN_FACE(ForbiddenMytem.class, SLIME_BALL, 226, (char) 0xF08F, MOB_FACE),
    GHAST_FACE(ForbiddenMytem.class, SLIME_BALL, 227, (char) 0xF090, MOB_FACE),
    PIG_FACE(ForbiddenMytem.class, SLIME_BALL, 228, (char) 0xF091, MOB_FACE),
    SHEEP_FACE(ForbiddenMytem.class, SLIME_BALL, 229, (char) 0xF092, MOB_FACE),
    SKELETON_FACE(ForbiddenMytem.class, SLIME_BALL, 230, (char) 0xF093, MOB_FACE),
    SLIME_FACE(ForbiddenMytem.class, SLIME_BALL, 231, (char) 0xF094, MOB_FACE),
    SPIDER_FACE(ForbiddenMytem.class, SLIME_BALL, 232, (char) 0xF095, MOB_FACE),
    SQUID_FACE(ForbiddenMytem.class, SLIME_BALL, 233, (char) 0xF096, MOB_FACE),
    VILLAGER_FACE(ForbiddenMytem.class, SLIME_BALL, 236, (char) 0xF097, MOB_FACE),
    PILLAGER_FACE(ForbiddenMytem.class, SLIME_BALL, 237, (char) 0xF098, MOB_FACE),
    WITHER_FACE(ForbiddenMytem.class, SLIME_BALL, 238, (char) 0xF099, MOB_FACE),
    ZOMBIE_FACE(ForbiddenMytem.class, SLIME_BALL, 239, (char) 0xF09A, MOB_FACE),
    WITCH_FACE(ForbiddenMytem.class, SLIME_BALL, 240, (char) 0xF09B, MOB_FACE),
    BLAZE_FACE(ForbiddenMytem.class, SLIME_BALL, 689, (char) 0xF09C, MOB_FACE),
    PIGLIN_FACE(ForbiddenMytem.class, SLIME_BALL, 690, (char) 0xF09D, MOB_FACE),
    WARDEN_FACE(ForbiddenMytem.class, SLIME_BALL, 691, (char) 0xF09E, MOB_FACE),
    HOGLIN_FACE(ForbiddenMytem.class, SLIME_BALL, 692, (char) 0xF09F, MOB_FACE),
    HUSK_FACE(ForbiddenMytem.class, SLIME_BALL, 693, (char) 0xF0A0, MOB_FACE),
    VEX_FACE(ForbiddenMytem.class, SLIME_BALL, 694, (char) 0xF0A1, MOB_FACE),
    GUARDIAN_FACE(ForbiddenMytem.class, SLIME_BALL, 695, (char) 0xF0A2, MOB_FACE),
    DROWNED_FACE(ForbiddenMytem.class, SLIME_BALL, 696, (char) 0xF0A3, MOB_FACE),
    PHANTOM_FACE(ForbiddenMytem.class, SLIME_BALL, 697, (char) 0xF0A4, MOB_FACE),
    MAGMA_CUBE_FACE(ForbiddenMytem.class, SLIME_BALL, 698, (char) 0xF0A5, MOB_FACE),
    RAVAGER_FACE(ForbiddenMytem.class, SLIME_BALL, 699, (char) 0xF0A6, MOB_FACE),
    SILVERFISH_FACE(ForbiddenMytem.class, SLIME_BALL, 700, (char) 0xF0A7, MOB_FACE),
    ZOGLIN_FACE(ForbiddenMytem.class, SLIME_BALL, 701, (char) 0xF0A8, MOB_FACE),
    ZOMBIE_VILLAGER_FACE(ForbiddenMytem.class, SLIME_BALL, 702, (char) 0xF0A9, MOB_FACE),
    WITHER_SKELETON_FACE(ForbiddenMytem.class, SLIME_BALL, 703, (char) 0xF0AA, MOB_FACE),
    ENDER_DRAGON_FACE(ForbiddenMytem.class, SLIME_BALL, 704, (char) 0xF0AB, MOB_FACE),
    STRAY_FACE(ForbiddenMytem.class, SLIME_BALL, 705, (char) 0xF0AC, MOB_FACE),
    BEE_FACE(ForbiddenMytem.class, SLIME_BALL, 731, (char) 0xF0AD, MOB_FACE),
    TEMPERATE_FROG_FACE(ForbiddenMytem.class, SLIME_BALL, 732, (char) 0xF0AE, MOB_FACE),
    COLD_FROG_FACE(ForbiddenMytem.class, SLIME_BALL, 733, (char) 0xF0AF, MOB_FACE),
    WARM_FROG_FACE(ForbiddenMytem.class, SLIME_BALL, 734, (char) 0xF0B0, MOB_FACE),
    GOAT_FACE(ForbiddenMytem.class, SLIME_BALL, 735, (char) 0xF0B1, MOB_FACE),
    CHICKEN_FACE(ForbiddenMytem.class, SLIME_BALL, 736, (char) 0xF0B2, MOB_FACE),
    BROWN_RABBIT_FACE(ForbiddenMytem.class, SLIME_BALL, 737, (char) 0xF0B3, MOB_FACE),
    CREAMY_HORSE_FACE(ForbiddenMytem.class, SLIME_BALL, 738, (char) 0xF0B4, MOB_FACE),
    WOLF_FACE(ForbiddenMytem.class, SLIME_BALL, 739, (char) 0xF0B5, MOB_FACE),
    OCELOT_FACE(ForbiddenMytem.class, SLIME_BALL, 740, (char) 0xF0B6, MOB_FACE),
    CYAN_AXOLOTL_FACE(ForbiddenMytem.class, SLIME_BALL, 741, (char) 0xF0B7, MOB_FACE),
    CREAMY_LLAMA_FACE(ForbiddenMytem.class, SLIME_BALL, 742, (char) 0xF0B8, MOB_FACE),
    PANDA_FACE(ForbiddenMytem.class, SLIME_BALL, 743, (char) 0xF0B9, MOB_FACE),
    FOX_FACE(ForbiddenMytem.class, SLIME_BALL, 744, (char) 0xF0BA, MOB_FACE),
    STRIDER_FACE(ForbiddenMytem.class, SLIME_BALL, 745, (char) 0xF0BB, MOB_FACE),
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
    BEE_CUSTOME_HELMET(BeeCostume.BeeHelmet.class, PLAYER_HEAD, null, (char) 0, BEE_COSTUME),
    BEE_CUSTOME_CHESTPLATE(BeeCostume.BeeChestplate.class, LEATHER_CHESTPLATE, null, (char) 0, BEE_COSTUME),
    BEE_CUSTOME_LEGGINGS(BeeCostume.BeeLeggings.class, LEATHER_LEGGINGS, null, (char) 0, BEE_COSTUME),
    BEE_CUSTOME_BOOTS(BeeCostume.BeeBoots.class, LEATHER_BOOTS, null, (char) 0, BEE_COSTUME),
    CACTUS_CUSTOME_HELMET(CactusCostume.CactusHelmet.class, PLAYER_HEAD, null, (char) 0, CACTUS_COSTUME),
    CACTUS_CUSTOME_CHESTPLATE(CactusCostume.CactusChestplate.class, LEATHER_CHESTPLATE, null, (char) 0, CACTUS_COSTUME),
    CACTUS_CUSTOME_LEGGINGS(CactusCostume.CactusLeggings.class, LEATHER_LEGGINGS, null, (char) 0, CACTUS_COSTUME),
    CACTUS_CUSTOME_BOOTS(CactusCostume.CactusBoots.class, LEATHER_BOOTS, null, (char) 0, CACTUS_COSTUME),
    SHEEP_CUSTOME_HELMET(SheepCostume.SheepHelmet.class, PLAYER_HEAD, null, (char) 0, SHEEP_COSTUME),
    SHEEP_CUSTOME_CHESTPLATE(SheepCostume.SheepChestplate.class, LEATHER_CHESTPLATE, null, (char) 0, SHEEP_COSTUME),
    SHEEP_CUSTOME_LEGGINGS(SheepCostume.SheepLeggings.class, LEATHER_LEGGINGS, null, (char) 0, SHEEP_COSTUME),
    SHEEP_CUSTOME_BOOTS(SheepCostume.SheepBoots.class, LEATHER_BOOTS, null, (char) 0, SHEEP_COSTUME),
    FOX_CUSTOME_HELMET(FoxCostume.FoxHelmet.class, PLAYER_HEAD, null, (char) 0, FOX_COSTUME),
    FOX_CUSTOME_CHESTPLATE(FoxCostume.FoxChestplate.class, LEATHER_CHESTPLATE, null, (char) 0, FOX_COSTUME),
    FOX_CUSTOME_LEGGINGS(FoxCostume.FoxLeggings.class, LEATHER_LEGGINGS, null, (char) 0, FOX_COSTUME),
    FOX_CUSTOME_BOOTS(FoxCostume.FoxBoots.class, LEATHER_BOOTS, null, (char) 0, FOX_COSTUME),
    SCARLET_HELMET(ScarletItem.Helmet.class, PLAYER_HEAD, 156, (char) 0xF0BC, SCARLET),
    SCARLET_CHESTPLATE(ScarletItem.Chestplate.class, LEATHER_CHESTPLATE, 157, (char) 0xF0BD, SCARLET),
    SCARLET_LEGGINGS(ScarletItem.Leggings.class, LEATHER_LEGGINGS, 158, (char) 0xF0BE, SCARLET),
    SCARLET_BOOTS(ScarletItem.Boots.class, LEATHER_BOOTS, 159, (char) 0xF0BF, SCARLET),
    SCARLET_SWORD(ScarletItem.Sword.class, NETHERITE_SWORD, 160, (char) 0xF0C0, SCARLET, Animation.frametime(4)),
    SCARLET_SHIELD(ScarletItem.Shield.class, SHIELD, 161, (char) 0xF0C1, SCARLET),
    COPPER_KEY(ForbiddenMytem.class, COPPER_INGOT, 177, (char) 0xF0C2, KEY),
    SILVER_KEY(ForbiddenMytem.class, IRON_INGOT, 178, (char) 0xF0C3, KEY),
    GOLDEN_KEY(ForbiddenMytem.class, GOLD_INGOT, 179, (char) 0xF0C4, KEY),
    COPPER_KEYHOLE(ForbiddenMytem.class, COPPER_BLOCK, 180, (char) 0xF0C5, KEYHOLE),
    SILVER_KEYHOLE(ForbiddenMytem.class, IRON_BLOCK, 181, (char) 0xF0C6, KEYHOLE),
    GOLDEN_KEYHOLE(ForbiddenMytem.class, GOLD_BLOCK, 182, (char) 0xF0C7, KEYHOLE),
    COPPER_COIN(Coin.class, COPPER_INGOT, 183, (char) 0xF0CA, chrarr(0xF0C8, 0xF0C9, 0xF0CA, 0xF0CB, 0xF0CC, 0xF0CD, 0xF0CE, 0xF0CF), COIN, Animation.SPINNING_COIN),
    SILVER_COIN(Coin.class, IRON_INGOT, 184, (char) 0xF0D2, chrarr(0xF0D0, 0xF0D1, 0xF0D2, 0xF0D3, 0xF0D4, 0xF0D5, 0xF0D6, 0xF0D7), COIN, Animation.SPINNING_COIN),
    GOLDEN_COIN(Coin.class, GOLD_INGOT, 185, (char) 0xF0DA, chrarr(0xF0D8, 0xF0D9, 0xF0DA, 0xF0DB, 0xF0DC, 0xF0DD, 0xF0DE, 0xF0DF), COIN, Animation.SPINNING_COIN),
    DIAMOND_COIN(Coin.class, DIAMOND, 275, (char) 0xF0E2, chrarr(0xF0E0, 0xF0E1, 0xF0E2, 0xF0E3, 0xF0E4, 0xF0E5, 0xF0E6, 0xF0E7), COIN, Animation.SPINNING_COIN),
    RUBY_COIN(Coin.class, EMERALD, 316, (char) 0xF0EA, chrarr(0xF0E8, 0xF0E9, 0xF0EA, 0xF0EB, 0xF0EC, 0xF0ED, 0xF0EE, 0xF0EF), COIN, Animation.SPINNING_COIN),
    GOLDEN_HOOP(Coin.class, GOLD_INGOT, 641, (char) 0xF0F0, chrarr(0xF0F0, 0xF0F1, 0xF0F2, 0xF0F3, 0xF0F4, 0xF0F5, 0xF0F6, 0xF0F7), COIN, Animation.SPINNING_COIN),
    COPPER_COIN_SHINE(ForbiddenMytem.class, COPPER_INGOT, 598, (char) 0, COIN_SHINE, Animation.SPINNING_COIN),
    SILVER_COIN_SHINE(ForbiddenMytem.class, IRON_INGOT, 599, (char) 0, COIN_SHINE, Animation.SPINNING_COIN),
    GOLDEN_COIN_SHINE(ForbiddenMytem.class, GOLD_INGOT, 600, (char) 0, COIN_SHINE, Animation.SPINNING_COIN),
    DIAMOND_COIN_SHINE(ForbiddenMytem.class, DIAMOND, 601, (char) 0, COIN_SHINE, Animation.SPINNING_COIN),
    RUBY_COIN_SHINE(ForbiddenMytem.class, EMERALD, 602, (char) 0, COIN_SHINE, Animation.SPINNING_COIN),
    GOLDEN_HOOP_SHINE(ForbiddenMytem.class, GOLD_INGOT, 603, (char) 0, COIN_SHINE, Animation.SPINNING_COIN),
    BLACK_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 186, (char) 0xF0F8, PAINTBRUSH),
    RED_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 187, (char) 0xF0F9, PAINTBRUSH),
    GREEN_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 188, (char) 0xF0FA, PAINTBRUSH),
    BROWN_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 189, (char) 0xF0FB, PAINTBRUSH),
    BLUE_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 190, (char) 0xF0FC, PAINTBRUSH),
    PURPLE_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 191, (char) 0xF0FD, PAINTBRUSH),
    CYAN_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 192, (char) 0xF0FE, PAINTBRUSH),
    LIGHT_GRAY_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 193, (char) 0xF0FF, PAINTBRUSH),
    GRAY_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 194, (char) 0xF100, PAINTBRUSH),
    PINK_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 195, (char) 0xF101, PAINTBRUSH),
    LIME_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 196, (char) 0xF102, PAINTBRUSH),
    YELLOW_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 197, (char) 0xF103, PAINTBRUSH),
    LIGHT_BLUE_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 198, (char) 0xF104, PAINTBRUSH),
    MAGENTA_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 199, (char) 0xF105, PAINTBRUSH),
    ORANGE_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 200, (char) 0xF106, PAINTBRUSH),
    WHITE_PAINTBRUSH(Paintbrush.class, WOODEN_SHOVEL, 201, (char) 0xF107, PAINTBRUSH),
    RUSTY_BUCKET(ArmorPart.class, BUCKET, 205, (char) 0xF108, ARMOR_PART),
    OLD_OVEN_LID(ArmorPart.class, NETHERITE_SCRAP, 207, (char) 0xF109, ARMOR_PART),
    SOOTY_STOVE_PIPE(ArmorPart.class, BARREL, 206, (char) 0xF10A, ARMOR_PART),
    FLOTSAM_CAN(ArmorPart.class, FLOWER_POT, 208, (char) 0xF10B, ARMOR_PART),
    BENT_PITCHFORK(ArmorPart.class, LIGHTNING_ROD, 209, (char) 0xF10C, ARMOR_PART),
    TRASH_CAN_LID(ArmorPart.class, NETHERITE_SCRAP, 259, (char) 0xF10D, ARMOR_PART),
    FARAWAY_MAP(FarawayMap.class, PAPER, 249, (char) 0xF10E, TECHNICAL),
    OAKNUT(TreeSeed.class, BEETROOT_SEEDS, 251, (char) 0xF10F, TREE_SEED),
    BIRCH_SEED(TreeSeed.class, BEETROOT_SEEDS, 252, (char) 0xF110, TREE_SEED),
    SPRUCE_CONE(TreeSeed.class, BEETROOT_SEEDS, 253, (char) 0xF111, TREE_SEED),
    JUNGLE_SEED(TreeSeed.class, BEETROOT_SEEDS, 254, (char) 0xF112, TREE_SEED),
    ACACIA_SEED(TreeSeed.class, BEETROOT_SEEDS, 255, (char) 0xF113, TREE_SEED),
    DARK_OAK_SEED(TreeSeed.class, BEETROOT_SEEDS, 256, (char) 0xF114, TREE_SEED),
    AZALEA_SEED(TreeSeed.class, BEETROOT_SEEDS, 260, (char) 0xF115, TREE_SEED),
    SCOTCH_PINE_CONE(TreeSeed.class, BEETROOT_SEEDS, 261, (char) 0xF116, TREE_SEED),
    FIR_CONE(TreeSeed.class, BEETROOT_SEEDS, 262, (char) 0xF117, TREE_SEED),
    FANCY_OAK_SEED(TreeSeed.class, BEETROOT_SEEDS, 263, (char) 0xF118, TREE_SEED, Animation.frametime(6)),
    FANCY_BIRCH_SEED(TreeSeed.class, BEETROOT_SEEDS, 264, (char) 0xF119, TREE_SEED, Animation.frametime(6)),
    FANCY_SPRUCE_CONE(TreeSeed.class, BEETROOT_SEEDS, 265, (char) 0xF11A, TREE_SEED, Animation.frametime(6)),
    EMPTY_FLASK(DummyMytem.class, GLASS_BOTTLE, 257, (char) 0xF11B, POTIONS),
    POTION_FLASK(PotionFlask.class, POTION, 258, (char) 0xF11C, POTIONS),
    TETRIS_I(DummyMytem.class, SAND, 276, (char) 0xF11D, TETRIS),
    TETRIS_O(DummyMytem.class, SAND, 277, (char) 0xF11E, TETRIS),
    TETRIS_T(DummyMytem.class, SAND, 278, (char) 0xF11F, TETRIS),
    TETRIS_L(DummyMytem.class, SAND, 279, (char) 0xF120, TETRIS),
    TETRIS_J(DummyMytem.class, SAND, 280, (char) 0xF121, TETRIS),
    TETRIS_S(DummyMytem.class, SAND, 281, (char) 0xF122, TETRIS),
    TETRIS_Z(DummyMytem.class, SAND, 282, (char) 0xF123, TETRIS),
    GOLDEN_CUP(Trophy.class, GOLD_INGOT, 108, (char) 0xE2AB, TROPHY),
    SILVER_CUP(Trophy.class, IRON_INGOT, 292, (char) 0xF124, TROPHY),
    BRONZE_CUP(Trophy.class, COPPER_INGOT, 293, (char) 0xF125, TROPHY),
    PARTICIPATION_CUP(Trophy.class, BLUE_CONCRETE, 298, (char) 0xF126, TROPHY),
    GOLD_MEDAL(Trophy.class, GOLD_NUGGET, 294, (char) 0xF127, TROPHY),
    SILVER_MEDAL(Trophy.class, IRON_NUGGET, 295, (char) 0xF128, TROPHY),
    BRONZE_MEDAL(Trophy.class, COPPER_INGOT, 296, (char) 0xF129, TROPHY),
    PARTICIPATION_MEDAL(Trophy.class, BLUE_WOOL, 299, (char) 0xF12A, TROPHY),
    GOLD_EASTER_TROPHY(Trophy.class, EGG, 300, (char) 0xF12B, TROPHY),
    SILVER_EASTER_TROPHY(Trophy.class, EGG, 301, (char) 0xF12C, TROPHY),
    BRONZE_EASTER_TROPHY(Trophy.class, EGG, 302, (char) 0xF12D, TROPHY),
    PARTICIPATION_EASTER_TROPHY(Trophy.class, EGG, 303, (char) 0xF12E, TROPHY),
    GOLD_VERTIGO_TROPHY(Trophy.class, LAVA_BUCKET, 308, (char) 0xF12F, TROPHY),
    SILVER_VERTIGO_TROPHY(Trophy.class, BUCKET, 309, (char) 0xF130, TROPHY),
    BRONZE_VERTIGO_TROPHY(Trophy.class, WATER_BUCKET, 310, (char) 0xF131, TROPHY),
    PARTICIPATION_VERTIGO_TROPHY(Trophy.class, BUCKET, 311, (char) 0xF132, TROPHY),
    GOLD_LADDER_TROPHY(Trophy.class, LADDER, 312, (char) 0xF133, TROPHY),
    SILVER_LADDER_TROPHY(Trophy.class, LADDER, 313, (char) 0xF134, TROPHY),
    BRONZE_LADDER_TROPHY(Trophy.class, LADDER, 314, (char) 0xF135, TROPHY),
    PARTICIPATION_LADDER_TROPHY(Trophy.class, LADDER, 315, (char) 0xF136, TROPHY),
    GOLD_CAVEPAINT_TROPHY(Trophy.class, PAINTING, 317, (char) 0xF137, TROPHY),
    SILVER_CAVEPAINT_TROPHY(Trophy.class, PAINTING, 318, (char) 0xF138, TROPHY),
    BRONZE_CAVEPAINT_TROPHY(Trophy.class, PAINTING, 319, (char) 0xF139, TROPHY),
    PARTICIPATION_CAVEPAINT_TROPHY(Trophy.class, PAINTING, 320, (char) 0xF13A, TROPHY),
    GOLD_RED_GREEN_LIGHT_TROPHY(Trophy.class, GOLD_INGOT, 321, (char) 0xF13B, TROPHY),
    SILVER_RED_GREEN_LIGHT_TROPHY(Trophy.class, IRON_INGOT, 322, (char) 0xF13C, TROPHY),
    BRONZE_RED_GREEN_LIGHT_TROPHY(Trophy.class, COPPER_INGOT, 323, (char) 0xF13D, TROPHY),
    PART_RED_GREEN_LIGHT_TROPHY(Trophy.class, BLUE_CONCRETE, 324, (char) 0xF13E, TROPHY),
    GOLD_HIDE_AND_SEEK_TROPHY(Trophy.class, GOLD_INGOT, 325, (char) 0xF13F, TROPHY),
    SILVER_HIDE_AND_SEEK_TROPHY(Trophy.class, IRON_INGOT, 326, (char) 0xF140, TROPHY),
    BRONZE_HIDE_AND_SEEK_TROPHY(Trophy.class, COPPER_INGOT, 327, (char) 0xF141, TROPHY),
    PART_HIDE_AND_SEEK_TROPHY(Trophy.class, BLUE_CONCRETE, 328, (char) 0xF142, TROPHY),
    GOLD_VOTE_TROPHY(Trophy.class, GOLD_INGOT, 329, (char) 0xF143, TROPHY),
    SILVER_VOTE_TROPHY(Trophy.class, IRON_INGOT, 330, (char) 0xF144, TROPHY),
    BRONZE_VOTE_TROPHY(Trophy.class, COPPER_INGOT, 331, (char) 0xF145, TROPHY),
    PART_VOTE_TROPHY(Trophy.class, BLUE_CONCRETE, 332, (char) 0xF146, TROPHY),
    GOLDEN_SPLEEF_TROPHY(Trophy.class, GOLDEN_SHOVEL, 341, (char) 0xF147, TROPHY),
    SILVER_SPLEEF_TROPHY(Trophy.class, IRON_SHOVEL, 342, (char) 0xF148, TROPHY),
    BRONZE_SPLEEF_TROPHY(Trophy.class, STONE_SHOVEL, 343, (char) 0xF149, TROPHY),
    BLUE_SPLEEF_TROPHY(Trophy.class, WOODEN_SHOVEL, 344, (char) 0xF14A, TROPHY),
    GOLDEN_END_FIGHT_TROPHY(Trophy.class, GOLDEN_SWORD, 588, (char) 0xF14B, TROPHY),
    SILVER_END_FIGHT_TROPHY(Trophy.class, GOLDEN_SWORD, 589, (char) 0xF14C, TROPHY),
    BRONZE_END_FIGHT_TROPHY(Trophy.class, GOLDEN_SWORD, 590, (char) 0xF14D, TROPHY),
    BLUE_END_FIGHT_TROPHY(Trophy.class, GOLDEN_SWORD, 591, (char) 0xF14E, TROPHY),
    PHOTO(Photo.class, FILLED_MAP, 306, (char) 0xF14F, PHOTOS),
    DEBUG(DummyMytem.class, DIAMOND, 20, (char) 0xF150, UTILITY),
    MOBSLAYER(Mobslayer.class, NETHERITE_SWORD, 719, (char) 0xF151, MOBSLAYERS),
    MOBSLAYER2(Mobslayer.class, NETHERITE_SWORD, 720, (char) 0xF152, MOBSLAYERS),
    MOBSLAYER3(Mobslayer.class, NETHERITE_SWORD, 721, (char) 0xF153, MOBSLAYERS),
    BINGO_BUKKIT(BingoBukkit.class, WOODEN_PICKAXE, 722, (char) 0xF154, BINGO_BUKKITS),
    GOLD_BINGO_BUKKIT(BingoBukkit.class, WOODEN_PICKAXE, 723, (char) 0xF155, BINGO_BUKKITS),
    DIAMOND_BINGO_BUKKIT(BingoBukkit.class, WOODEN_PICKAXE, 724, (char) 0xF156, BINGO_BUKKITS),
    SEALED_CAVEBOY(SealedCaveboy.class, WOODEN_SHOVEL, 748, (char) 0xF157, CAVEBOY),
    CAVEBOY_TICTACTOE(Caveboy.class, IRON_SHOVEL, 746, (char) 0xF158, CAVEBOY),
    CAVEBOY_TNTSWEEP(Caveboy.class, IRON_SHOVEL, 747, (char) 0xF159, CAVEBOY),
    CAVEBOY_MEMORY(Caveboy.class, IRON_SHOVEL, 0xF254, CAVEBOY),
    CAVEBOY_GEMS(Caveboy.class, IRON_SHOVEL, 0xF26A, CAVEBOY),
    COLORFALL_HOURGLASS(Hourglass.class, CLOCK, 0xF24E, (char) 0xF24E, chrarr(0xF24E, 0xF25B, 0xF25C, 0xF25D, 0xF25E, 0xF25F, 0xF260, 0xF261, 0xF262, 0xF263, 0xF264, 0xF265), HOURGLASS, Animation.HOURGLASS),
    MOONLIGHT_HOURGLASS(Hourglass.class, CLOCK, 0xF24F, HOURGLASS, Animation.HOURGLASS),
    ATMOSPHERE_HOURGLASS(Hourglass.class, CLOCK, 0xF250, HOURGLASS, Animation.HOURGLASS),
    CLIMATE_HOURGLASS(Hourglass.class, CLOCK, 0xF251, HOURGLASS, Animation.HOURGLASS),
    STRUCTURE_FINDER(Finder.class, COMPASS, 0xF255, FINDER),
    SECRET_FINDER(Finder.class, COMPASS, 0xF256, FINDER),
    MYSTIC_FINDER(Finder.class, COMPASS, 0xF257, FINDER),
    EMPTY_LUMINATOR(Luminator.class, IRON_PICKAXE, 0xF259, UTILITY),
    LUMINATOR(Luminator.class, IRON_PICKAXE, 0xF25A, UTILITY, Animation.frametime(2)),
    DEFLECTOR_SHIELD(DeflectorShield.class, SHIELD, 0xF266, DEFLECTOR),
    RETURN_SHIELD(DeflectorShield.class, SHIELD, 0xF267, DEFLECTOR),
    VENGEANCE_SHIELD(DeflectorShield.class, SHIELD, 0xF268, DEFLECTOR),
    GOLDEN_QUIVER(EquipmentItem.class, STICK, 345, (char) 0xF15A, EQUIP_QUIVER),
    STEEL_QUIVER(EquipmentItem.class, STICK, 346, (char) 0xF15B, EQUIP_QUIVER),
    WOODEN_QUIVER(EquipmentItem.class, STICK, 347, (char) 0xF15C, EQUIP_QUIVER),
    EARTH_BROADSWORD(EquipmentItem.class, IRON_SWORD, 348, (char) 0xF15D, EQUIP_BROADSWORD),
    ICE_BROADSWORD(EquipmentItem.class, IRON_SWORD, 349, (char) 0xF15E, EQUIP_BROADSWORD),
    STEEL_BROADSWORD(EquipmentItem.class, IRON_SWORD, 350, (char) 0xF15F, EQUIP_BROADSWORD),
    SERRATED_BROADSWORD(EquipmentItem.class, IRON_SWORD, 351, (char) 0xF160, EQUIP_BROADSWORD),
    GOLDEN_BROADSWORD(EquipmentItem.class, GOLDEN_SWORD, 352, (char) 0xF161, EQUIP_BROADSWORD),
    GILDED_STEEL_HAMMER(EquipmentItem.class, IRON_AXE, 353, (char) 0xF162, EQUIP_HAMMER),
    STEEL_HAMMER(EquipmentItem.class, IRON_AXE, 354, (char) 0xF163, EQUIP_HAMMER),
    WOODEN_MALLET(EquipmentItem.class, WOODEN_AXE, 355, (char) 0xF164, EQUIP_HAMMER),
    GILDED_STEEL_MALLET(EquipmentItem.class, IRON_AXE, 356, (char) 0xF165, EQUIP_HAMMER),
    STEEL_MALLET(EquipmentItem.class, IRON_AXE, 357, (char) 0xF166, EQUIP_HAMMER),
    WOODEN_HAMMER(EquipmentItem.class, WOODEN_AXE, 358, (char) 0xF167, EQUIP_HAMMER),
    GILDED_STEEL_CUTLASS(EquipmentItem.class, IRON_SWORD, 359, (char) 0xF168, EQUIP_CUTLASS),
    FIRE_CUTLASS(EquipmentItem.class, IRON_SWORD, 360, (char) 0xF169, EQUIP_CUTLASS),
    COPPER_CUTLASS(EquipmentItem.class, IRON_SWORD, 361, (char) 0xF16A, EQUIP_CUTLASS),
    EARTH_CUTLASS(EquipmentItem.class, IRON_SWORD, 362, (char) 0xF16B, EQUIP_CUTLASS),
    WATER_CUTLASS(EquipmentItem.class, IRON_SWORD, 363, (char) 0xF16C, EQUIP_CUTLASS),
    SERRATED_STEEL_CUTLASS(EquipmentItem.class, IRON_SWORD, 364, (char) 0xF16D, EQUIP_CUTLASS),
    GILDED_STEEL_SWORD(EquipmentItem.class, IRON_SWORD, 365, (char) 0xF16E, EQUIP_SWORD),
    STEEL_KATANA(EquipmentItem.class, IRON_SWORD, 366, (char) 0xF16F, EQUIP_SWORD),
    ICE_SWORD(EquipmentItem.class, IRON_SWORD, 367, (char) 0xF170, EQUIP_SWORD),
    SERRATED_STEEL_SWORD(EquipmentItem.class, IRON_SWORD, 368, (char) 0xF171, EQUIP_SWORD),
    FIRE_SWORD(EquipmentItem.class, IRON_SWORD, 369, (char) 0xF172, EQUIP_SWORD),
    STEEL_SWORD(EquipmentItem.class, IRON_SWORD, 370, (char) 0xF173, EQUIP_SWORD),
    COPPER_SWORD(EquipmentItem.class, IRON_SWORD, 371, (char) 0xF174, EQUIP_SWORD),
    GILDED_WOODEN_BOW(EquipmentItem.class, BOW, 372, (char) 0xF175, EQUIP_BOW),
    WOODEN_BOW(EquipmentItem.class, BOW, 373, (char) 0xF176, EQUIP_BOW),
    WOODEN_COMPOSITE_BOW(EquipmentItem.class, BOW, 374, (char) 0xF177, EQUIP_BOW),
    GOLDEN_COMPOSITE_BOW(EquipmentItem.class, BOW, 375, (char) 0xF178, EQUIP_BOW),
    STEEL_COMPOSITE_BOW(EquipmentItem.class, BOW, 376, (char) 0xF179, EQUIP_BOW),
    WOODEN_CROSSBOW(EquipmentItem.class, BOW, 377, (char) 0xF17A, EQUIP_BOW),
    VIOLET_BATTLE_SCYTHE(EquipmentItem.class, IRON_AXE, 378, (char) 0xF17B, EQUIP_SCYTHE),
    GOLDEN_BATTLE_SCYTHE(EquipmentItem.class, GOLDEN_AXE, 379, (char) 0xF17C, EQUIP_SCYTHE),
    IRON_BATTLE_SCYTHE(EquipmentItem.class, IRON_AXE, 380, (char) 0xF17D, EQUIP_SCYTHE),
    IRON_GRAND_BATTLE_SCYTHE(EquipmentItem.class, IRON_AXE, 381, (char) 0xF17E, EQUIP_SCYTHE),
    STEEL_BATTLE_SCYTHE(EquipmentItem.class, IRON_AXE, 382, (char) 0xF17F, EQUIP_SCYTHE),
    TURQUOISE_BATTLE_SCYTHE(EquipmentItem.class, IRON_AXE, 383, (char) 0xF180, EQUIP_SCYTHE),
    RUSTY_DAGGER(EquipmentItem.class, IRON_SWORD, 384, (char) 0xF181, EQUIP_DAGGER),
    SERRATED_STEEL_DAGGER(EquipmentItem.class, IRON_SWORD, 385, (char) 0xF182, EQUIP_DAGGER),
    COPPER_DAGGER(EquipmentItem.class, IRON_SWORD, 386, (char) 0xF183, EQUIP_DAGGER),
    STEEL_NINJA_SAIS(EquipmentItem.class, IRON_SWORD, 387, (char) 0xF184, EQUIP_DAGGER),
    GILDED_STEEL_DAGGER(EquipmentItem.class, IRON_SWORD, 388, (char) 0xF185, EQUIP_DAGGER),
    STEEL_DAGGER(EquipmentItem.class, IRON_SWORD, 389, (char) 0xF186, EQUIP_DAGGER),
    ICE_WHIP(EquipmentItem.class, LEAD, 390, (char) 0xF187, EQUIP_WHIP),
    GREEN_FLAIL(EquipmentItem.class, IRON_AXE, 391, (char) 0xF188, EQUIP_WHIP),
    FIRE_WHIP(EquipmentItem.class, LEAD, 392, (char) 0xF189, EQUIP_WHIP),
    CHAIN_WHIP(EquipmentItem.class, LEAD, 393, (char) 0xF18A, EQUIP_WHIP),
    LEATHER_WHIP(EquipmentItem.class, LEAD, 394, (char) 0xF18B, EQUIP_WHIP),
    EARTH_WHIP(EquipmentItem.class, LEAD, 395, (char) 0xF18C, EQUIP_WHIP),
    SPIKED_STEEL_ROUND_SHIELD(EquipmentItem.class, SHIELD, 396, (char) 0xF18D, EQUIP_SHIELD),
    CLOVER_ROUND_SHIELD(EquipmentItem.class, SHIELD, 397, (char) 0xF18E, EQUIP_SHIELD),
    FIRE_KITE_SHIELD(EquipmentItem.class, SHIELD, 398, (char) 0xF18F, EQUIP_SHIELD),
    WATER_WANKEL_SHIELD(EquipmentItem.class, SHIELD, 399, (char) 0xF190, EQUIP_SHIELD),
    FIRE_WANKEL_SHIELD(EquipmentItem.class, SHIELD, 400, (char) 0xF191, EQUIP_SHIELD),
    ICE_WANKEL_SHIELD(EquipmentItem.class, SHIELD, 401, (char) 0xF192, EQUIP_SHIELD),
    COPPER_WANKEL_SHIELD(EquipmentItem.class, SHIELD, 402, (char) 0xF193, EQUIP_SHIELD),
    CLOVER_WANKEL_SHIELD(EquipmentItem.class, SHIELD, 403, (char) 0xF194, EQUIP_SHIELD),
    COPPER_ROUND_SHIELD(EquipmentItem.class, SHIELD, 404, (char) 0xF195, EQUIP_SHIELD),
    COATED_KITE_SHIELD(EquipmentItem.class, SHIELD, 405, (char) 0xF196, EQUIP_SHIELD),
    STEEL_ROUND_SHIELD(EquipmentItem.class, SHIELD, 406, (char) 0xF197, EQUIP_SHIELD),
    EARTH_WANKEL_SHIELD(EquipmentItem.class, SHIELD, 407, (char) 0xF198, EQUIP_SHIELD),
    STEEL_KITE_SHIELD(EquipmentItem.class, SHIELD, 408, (char) 0xF199, EQUIP_SHIELD),
    COPPER_KITE_SHIELD(EquipmentItem.class, SHIELD, 409, (char) 0xF19A, EQUIP_SHIELD),
    GILDED_IRON_BATTLE_AXE(EquipmentItem.class, IRON_AXE, 410, (char) 0xF19B, EQUIP_AXE),
    SMALL_IRON_HATCHET(EquipmentItem.class, IRON_HELMET, 411, (char) 0xF19C, EQUIP_AXE),
    IRON_HATCHET(EquipmentItem.class, IRON_HELMET, 412, (char) 0xF19D, EQUIP_AXE),
    IRON_BROAD_AXE(EquipmentItem.class, IRON_AXE, 413, (char) 0xF19E, EQUIP_AXE),
    GILDED_IRON_MAUL(EquipmentItem.class, IRON_AXE, 414, (char) 0xF19F, EQUIP_AXE),
    STEEL_TRIDENT(EquipmentItem.class, TRIDENT, 415, (char) 0xF1A0, EQUIP_SPEAR),
    GOLDEN_TRIDENT(EquipmentItem.class, TRIDENT, 416, (char) 0xF1A1, EQUIP_SPEAR),
    WOODEN_JAVELIN(EquipmentItem.class, TRIDENT, 417, (char) 0xF1A2, EQUIP_SPEAR),
    STEEL_SPEAR(EquipmentItem.class, TRIDENT, 418, (char) 0xF1A3, EQUIP_SPEAR),
    STEEL_JAVELIN(EquipmentItem.class, TRIDENT, 419, (char) 0xF1A4, EQUIP_SPEAR),
    SPIKED_WOODEN_CLUB(EquipmentItem.class, WOODEN_AXE, 420, (char) 0xF1A5, EQUIP_MACE),
    IRON_MACE(EquipmentItem.class, IRON_AXE, 421, (char) 0xF1A6, EQUIP_MACE),
    SPIKED_IRON_MACE(EquipmentItem.class, IRON_AXE, 422, (char) 0xF1A7, EQUIP_MACE),
    GILDED_BAT(EquipmentItem.class, GOLDEN_AXE, 423, (char) 0xF1A8, EQUIP_MACE),
    WOODEN_MACE(EquipmentItem.class, WOODEN_AXE, 424, (char) 0xF1A9, EQUIP_MACE),
    WOODEN_BAT(EquipmentItem.class, WOODEN_AXE, 425, (char) 0xF1AA, EQUIP_MACE),
    VIOLET_DOUBLE_POLEARM(EquipmentItem.class, IRON_SWORD, 426, (char) 0xF1AB, EQUIP_POLEARM),
    STEEL_POLEARM(EquipmentItem.class, IRON_SWORD, 427, (char) 0xF1AC, EQUIP_POLEARM),
    GOLDEN_NECKLACE_AMETHYSTS(EquipmentItem.class, GOLD_INGOT, 428, (char) 0xF1AD, EQUIP_NECKLACE),
    GOLDEN_NECKLACE_DIAMONDS(EquipmentItem.class, GOLD_INGOT, 429, (char) 0xF1AE, EQUIP_NECKLACE),
    GOLDEN_NECKLACE_JADE(EquipmentItem.class, GOLD_INGOT, 430, (char) 0xF1AF, EQUIP_NECKLACE),
    LEATHER_COLLAR_GOLD_CROSS(EquipmentItem.class, IRON_INGOT, 431, (char) 0xF1B0, EQUIP_NECKLACE),
    GOLDEN_COLLAR(EquipmentItem.class, GOLD_INGOT, 432, (char) 0xF1B1, EQUIP_NECKLACE),
    SILVER_NECKLACE_PEARL(EquipmentItem.class, IRON_INGOT, 433, (char) 0xF1B2, EQUIP_NECKLACE),
    LEATHER_COLLAR(EquipmentItem.class, IRON_INGOT, 434, (char) 0xF1B3, EQUIP_NECKLACE),
    SILVER_NECKLACE_HEART(EquipmentItem.class, IRON_INGOT, 435, (char) 0xF1B4, EQUIP_NECKLACE),
    LEATHER_COLLAR_DIAMOND_SHARD(EquipmentItem.class, IRON_INGOT, 436, (char) 0xF1B5, EQUIP_NECKLACE),
    GREEN_DECORATED_IRON_HELMET(EquipmentItem.class, IRON_HELMET, 437, (char) 0xF1B6, EQUIP_HELMET),
    DECORATED_GILDED_KING_HELMET(EquipmentItem.class, GOLDEN_HELMET, 438, (char) 0xF1B7, EQUIP_HELMET),
    BLUE_DECORATED_IRON_HELMET(EquipmentItem.class, IRON_HELMET, 439, (char) 0xF1B8, EQUIP_HELMET),
    RED_DECORATED_IRON_HELMET(EquipmentItem.class, IRON_HELMET, 440, (char) 0xF1B9, EQUIP_HELMET),
    GOLDEN_HUGE_DIAMOND_RING(EquipmentItem.class, GOLD_NUGGET, 441, (char) 0xF1BA, EQUIP_RING),
    SILVER_LINCHPIN_RING(EquipmentItem.class, IRON_NUGGET, 442, (char) 0xF1BB, EQUIP_RING),
    GOLDEN_HEART_RING(EquipmentItem.class, GOLD_NUGGET, 443, (char) 0xF1BC, EQUIP_RING),
    SILVER_BERYL_RING(EquipmentItem.class, IRON_NUGGET, 444, (char) 0xF1BD, EQUIP_RING),
    GOLDEN_RING(EquipmentItem.class, GOLD_NUGGET, 445, (char) 0xF1BE, EQUIP_RING),
    SILVER_RUBY_RING(EquipmentItem.class, IRON_NUGGET, 446, (char) 0xF1BF, EQUIP_RING),
    SILVER_OPAL_RING(EquipmentItem.class, IRON_NUGGET, 447, (char) 0xF1C0, EQUIP_RING),
    GOLDEN_AMETHYST_RING(EquipmentItem.class, GOLD_NUGGET, 448, (char) 0xF1C1, EQUIP_RING),
    GOLDEN_BIG_PEARL_RING(EquipmentItem.class, GOLD_NUGGET, 449, (char) 0xF1C2, EQUIP_RING),
    GOLDEN_DIAMOND_RING(EquipmentItem.class, GOLD_NUGGET, 450, (char) 0xF1C3, EQUIP_RING),
    GOLDEN_BIG_HEART_RING(EquipmentItem.class, GOLD_NUGGET, 451, (char) 0xF1C4, EQUIP_RING),
    SILVER_DIAMOND_RING(EquipmentItem.class, IRON_NUGGET, 452, (char) 0xF1C5, EQUIP_RING),
    GOLDEN_PEARL_RING(EquipmentItem.class, GOLD_NUGGET, 453, (char) 0xF1C6, EQUIP_RING),
    SILVER_AND_GOLD_RINGS(EquipmentItem.class, IRON_NUGGET, 454, (char) 0xF1C7, EQUIP_RING),
    SILVER_RING_MISSING_GEM(EquipmentItem.class, IRON_NUGGET, 455, (char) 0xF1C8, EQUIP_RING),
    GOLDEN_EMERALD_RING(EquipmentItem.class, GOLD_NUGGET, 456, (char) 0xF1C9, EQUIP_RING),
    SILVER_SHIELD_RING(EquipmentItem.class, SHIELD, 457, (char) 0xF1CA, EQUIP_RING),
    SILVER_BIG_SHIELD_RING(EquipmentItem.class, SHIELD, 458, (char) 0xF1CB, EQUIP_RING),
    SILVER_RING(EquipmentItem.class, IRON_NUGGET, 459, (char) 0xF1CC, EQUIP_RING),
    GOLDEN_RUBY_RING(EquipmentItem.class, GOLD_NUGGET, 460, (char) 0xF1CD, EQUIP_RING),
    FIRE_WAND(EquipmentItem.class, STICK, 461, (char) 0xF1CE, EQUIP_WAND),
    RED_WAND(EquipmentItem.class, STICK, 462, (char) 0xF1CF, EQUIP_WAND),
    ICE_WAND(EquipmentItem.class, STICK, 463, (char) 0xF1D0, EQUIP_WAND),
    BLUE_WAND(EquipmentItem.class, STICK, 464, (char) 0xF1D1, EQUIP_WAND),
    BLUE_SPARK_WAND(EquipmentItem.class, STICK, 465, (char) 0xF1D2, EQUIP_WAND),
    PURPLE_SPARK_WAND(EquipmentItem.class, STICK, 466, (char) 0xF1D3, EQUIP_WAND),
    EARTH_WAND(EquipmentItem.class, STICK, 467, (char) 0xF1D4, EQUIP_WAND),
    WATER_WAND(EquipmentItem.class, STICK, 468, (char) 0xF1D5, EQUIP_WAND),
    PURPLE_WAND(EquipmentItem.class, STICK, 469, (char) 0xF1D6, EQUIP_WAND),
    RED_SPARK_WAND(EquipmentItem.class, STICK, 470, (char) 0xF1D7, EQUIP_WAND),
    SMALL_RED_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 471, (char) 0xF1D8, EQUIP_CLOAK),
    RED_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 472, (char) 0xF1D9, EQUIP_CLOAK),
    BLUE_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 473, (char) 0xF1DA, EQUIP_CLOAK),
    ORANGE_HOODED_CLOAK(EquipmentItem.class, IRON_HELMET, 474, (char) 0xF1DB, EQUIP_CLOAK),
    SMALL_EARTH_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 475, (char) 0xF1DC, EQUIP_CLOAK),
    RED_HOODED_CLOAK(EquipmentItem.class, IRON_HELMET, 476, (char) 0xF1DD, EQUIP_CLOAK),
    FIRE_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 477, (char) 0xF1DE, EQUIP_CLOAK),
    BLUE_FUR_COLLAR_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 478, (char) 0xF1DF, EQUIP_CLOAK),
    EARTH_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 479, (char) 0xF1E0, EQUIP_CLOAK),
    WATER_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 480, (char) 0xF1E1, EQUIP_CLOAK),
    SMALL_ICE_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 481, (char) 0xF1E2, EQUIP_CLOAK),
    ORANGE_FUR_COLOR_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 482, (char) 0xF1E3, EQUIP_CLOAK),
    SMALL_ORANGE_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 483, (char) 0xF1E4, EQUIP_CLOAK),
    SMALL_WATER_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 484, (char) 0xF1E5, EQUIP_CLOAK),
    RED_FUR_COLLAR_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 485, (char) 0xF1E6, EQUIP_CLOAK),
    BLUE_HOODED_CLOAK(EquipmentItem.class, IRON_HELMET, 486, (char) 0xF1E7, EQUIP_CLOAK),
    ORANGE_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 487, (char) 0xF1E8, EQUIP_CLOAK),
    ICE_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 488, (char) 0xF1E9, EQUIP_CLOAK),
    SMALL_FIRE_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 489, (char) 0xF1EA, EQUIP_CLOAK),
    SMALL_BLUE_CLOAK(EquipmentItem.class, IRON_CHESTPLATE, 490, (char) 0xF1EB, EQUIP_CLOAK),
    WATER_SCEPTER(EquipmentItem.class, STICK, 491, (char) 0xF1EC, EQUIP_SCEPTER),
    EARTH_SCEPTER(EquipmentItem.class, STICK, 492, (char) 0xF1ED, EQUIP_SCEPTER),
    FIRE_SCEPTER(EquipmentItem.class, STICK, 493, (char) 0xF1EE, EQUIP_SCEPTER),
    ICE_SCEPTER(EquipmentItem.class, STICK, 494, (char) 0xF1EF, EQUIP_SCEPTER),
    EARTH_STAFF(EquipmentItem.class, STICK, 495, (char) 0xF1F0, EQUIP_STAFF),
    ICE_STAFF(EquipmentItem.class, STICK, 496, (char) 0xF1F1, EQUIP_STAFF),
    FIRE_STAFF(EquipmentItem.class, STICK, 497, (char) 0xF1F2, EQUIP_STAFF),
    WATER_STAFF(EquipmentItem.class, STICK, 498, (char) 0xF1F3, EQUIP_STAFF),
    WATER_CANE(EquipmentItem.class, STICK, 499, (char) 0xF1F4, EQUIP_CANE),
    FIRE_CANE(EquipmentItem.class, STICK, 500, (char) 0xF1F5, EQUIP_CANE),
    ICE_CANE(EquipmentItem.class, STICK, 501, (char) 0xF1F6, EQUIP_CANE),
    EARTH_CANE(EquipmentItem.class, STICK, 502, (char) 0xF1F7, EQUIP_CANE),
    GILDED_LORD_GAUNTLETS(EquipmentItem.class, GOLDEN_BOOTS, 503, (char) 0xF1F8, EQUIP_GILDED_LORD_SET),
    GILDED_LORD_CHESTPLATE(EquipmentItem.class, GOLDEN_CHESTPLATE, 504, (char) 0xF1F9, EQUIP_GILDED_LORD_SET),
    GILDED_LORD_HELMET(EquipmentItem.class, GOLDEN_HELMET, 505, (char) 0xF1FA, EQUIP_GILDED_LORD_SET),
    GILDED_LORD_GREAVES(EquipmentItem.class, GOLDEN_LEGGINGS, 506, (char) 0xF1FB, EQUIP_GILDED_LORD_SET),
    GILDED_LORD_SABATONS(EquipmentItem.class, GOLDEN_BOOTS, 507, (char) 0xF1FC, EQUIP_GILDED_LORD_SET),
    TURQUOISE_CLOTH_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 508, (char) 0xF1FD, EQUIP_TURQUOISE_CLOTH_SET),
    TURQUOISE_CLOTH_TUNIC(EquipmentItem.class, LEATHER_CHESTPLATE, 509, (char) 0xF1FE, EQUIP_TURQUOISE_CLOTH_SET),
    TURQUOISE_CLOTH_HAT(EquipmentItem.class, LEATHER_HELMET, 510, (char) 0xF1FF, EQUIP_TURQUOISE_CLOTH_SET),
    TURQUOISE_CLOTH_SNEAKERS(EquipmentItem.class, LEATHER_BOOTS, 511, (char) 0xF200, EQUIP_TURQUOISE_CLOTH_SET),
    TURQUOISE_CLOTH_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 512, (char) 0xF201, EQUIP_TURQUOISE_CLOTH_SET),
    YELLOW_WINGED_CLOTH_CAP(EquipmentItem.class, LEATHER_HELMET, 513, (char) 0xF202, EQUIP_YELLOW_WINGED_CLOTH_SET),
    YELLOW_WINGED_CLOTH_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 514, (char) 0xF203, EQUIP_YELLOW_WINGED_CLOTH_SET),
    YELLOW_WINGED_CLOTH_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 515, (char) 0xF204, EQUIP_YELLOW_WINGED_CLOTH_SET),
    YELLOW_WINGED_CLOTH_TUNIC(EquipmentItem.class, LEATHER_CHESTPLATE, 516, (char) 0xF205, EQUIP_YELLOW_WINGED_CLOTH_SET),
    YELLOW_WINGED_CLOTH_SNEAKERS(EquipmentItem.class, LEATHER_BOOTS, 517, (char) 0xF206, EQUIP_YELLOW_WINGED_CLOTH_SET),
    STUDDED_LEATHER_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 518, (char) 0xF207, EQUIP_STUDDED_LEATHER_SET),
    STUDDED_LEATHER_BOOTS(EquipmentItem.class, LEATHER_BOOTS, 519, (char) 0xF208, EQUIP_STUDDED_LEATHER_SET),
    STUDDED_LEATHER_HELMET(EquipmentItem.class, LEATHER_HELMET, 520, (char) 0xF209, EQUIP_STUDDED_LEATHER_SET),
    STUDDED_LEATHER_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 521, (char) 0xF20A, EQUIP_STUDDED_LEATHER_SET),
    STUDDED_LEATHER_CHESTPLATE(EquipmentItem.class, LEATHER_CHESTPLATE, 522, (char) 0xF20B, EQUIP_STUDDED_LEATHER_SET),
    ORANGE_CLOTH_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 523, (char) 0xF20C, EQUIP_ORANGE_CLOTH_SET),
    ORANGE_CLOTH_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 524, (char) 0xF20D, EQUIP_ORANGE_CLOTH_SET),
    ORANGE_CLOTH_HOOD(EquipmentItem.class, LEATHER_HELMET, 525, (char) 0xF20E, EQUIP_ORANGE_CLOTH_SET),
    ORANGE_CLOTH_SNEAKERS(EquipmentItem.class, LEATHER_BOOTS, 526, (char) 0xF20F, EQUIP_ORANGE_CLOTH_SET),
    ORANGE_CLOTH_TUNIC(EquipmentItem.class, LEATHER_CHESTPLATE, 527, (char) 0xF210, EQUIP_ORANGE_CLOTH_SET),
    PEACH_CLOTH_TUNIC(EquipmentItem.class, LEATHER_CHESTPLATE, 528, (char) 0xF211, EQUIP_PEACH_CLOTH_SET),
    PEACH_CLOTH_SNEAKERS(EquipmentItem.class, LEATHER_BOOTS, 529, (char) 0xF212, EQUIP_PEACH_CLOTH_SET),
    PEACH_CLOTH_HOOD(EquipmentItem.class, LEATHER_HELMET, 530, (char) 0xF213, EQUIP_PEACH_CLOTH_SET),
    PEACH_CLOTH_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 531, (char) 0xF214, EQUIP_PEACH_CLOTH_SET),
    PEACH_CLOTH_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 532, (char) 0xF215, EQUIP_PEACH_CLOTH_SET),
    IRON_KNIGHT_HELMET(EquipmentItem.class, IRON_HELMET, 533, (char) 0xF216, EQUIP_IRON_KNIGHT_SET),
    IRON_KNIGHT_GREAVES(EquipmentItem.class, IRON_LEGGINGS, 534, (char) 0xF217, EQUIP_IRON_KNIGHT_SET),
    IRON_KNIGHT_SABATONS(EquipmentItem.class, IRON_BOOTS, 535, (char) 0xF218, EQUIP_IRON_KNIGHT_SET),
    IRON_KNIGHT_GAUNTLETS(EquipmentItem.class, IRON_BOOTS, 536, (char) 0xF219, EQUIP_IRON_KNIGHT_SET),
    IRON_KNIGHT_CHESTPLATE(EquipmentItem.class, IRON_CHESTPLATE, 537, (char) 0xF21A, EQUIP_IRON_KNIGHT_SET),
    PINK_WINGED_CLOTH_HAT(EquipmentItem.class, LEATHER_HELMET, 538, (char) 0xF21B, EQUIP_PINK_WINGED_CLOTH_SET),
    PINK_WINGED_CLOTH_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 539, (char) 0xF21C, EQUIP_PINK_WINGED_CLOTH_SET),
    PINK_WINGED_CLOTH_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 540, (char) 0xF21D, EQUIP_PINK_WINGED_CLOTH_SET),
    PINK_WINGED_CLOTH_SNEAKERS(EquipmentItem.class, LEATHER_BOOTS, 541, (char) 0xF21E, EQUIP_PINK_WINGED_CLOTH_SET),
    PINK_WINGED_CLOTH_TUNIC(EquipmentItem.class, LEATHER_CHESTPLATE, 542, (char) 0xF21F, EQUIP_PINK_WINGED_CLOTH_SET),
    GREEN_FEATHER_CLOTH_TUNIC(EquipmentItem.class, LEATHER_CHESTPLATE, 543, (char) 0xF220, EQUIP_GREEN_FEATHER_SET),
    GREEN_FEATHER_CLOTH_CAP(EquipmentItem.class, LEATHER_HELMET, 544, (char) 0xF221, EQUIP_GREEN_FEATHER_SET),
    GREEN_FEATHER_CLOTH_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 545, (char) 0xF222, EQUIP_GREEN_FEATHER_SET),
    GREEN_FEATHER_CLOTH_SNEAKERS(EquipmentItem.class, LEATHER_BOOTS, 546, (char) 0xF223, EQUIP_GREEN_FEATHER_SET),
    GREEN_FEATHER_CLOTH_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 547, (char) 0xF224, EQUIP_GREEN_FEATHER_SET),
    FINE_IRON_GAUNTLETS(EquipmentItem.class, IRON_BOOTS, 548, (char) 0xF225, EQUIP_FINE_IRON_SET),
    FINE_IRON_CHESTPLATE(EquipmentItem.class, IRON_CHESTPLATE, 549, (char) 0xF226, EQUIP_FINE_IRON_SET),
    FINE_IRON_SABATONS(EquipmentItem.class, IRON_BOOTS, 550, (char) 0xF227, EQUIP_FINE_IRON_SET),
    FINE_IRON_GREAVES(EquipmentItem.class, IRON_LEGGINGS, 551, (char) 0xF228, EQUIP_FINE_IRON_SET),
    FINE_IRON_HELMET(EquipmentItem.class, IRON_HELMET, 552, (char) 0xF229, EQUIP_FINE_IRON_SET),
    GILDED_KING_SABATONS(EquipmentItem.class, GOLDEN_BOOTS, 553, (char) 0xF22A, EQUIP_GILDED_KING_SET),
    GILDED_KING_GREAVES(EquipmentItem.class, GOLDEN_LEGGINGS, 554, (char) 0xF22B, EQUIP_GILDED_KING_SET),
    GILDED_KING_GAUNTLETS(EquipmentItem.class, GOLDEN_BOOTS, 555, (char) 0xF22C, EQUIP_GILDED_KING_SET),
    GILDED_KING_CHESTPLATE(EquipmentItem.class, GOLDEN_CHESTPLATE, 556, (char) 0xF22D, EQUIP_GILDED_KING_SET),
    GILDED_KING_HELMET(EquipmentItem.class, GOLDEN_HELMET, 557, (char) 0xF22E, EQUIP_GILDED_KING_SET),
    SIMPLE_LEATHER_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 558, (char) 0xF22F, EQUIP_SIMPLE_LEATHER_SET),
    SIMPLE_LEATHER_VEST(EquipmentItem.class, LEATHER_CHESTPLATE, 559, (char) 0xF230, EQUIP_SIMPLE_LEATHER_SET),
    SIMPLE_LEATHER_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 560, (char) 0xF231, EQUIP_SIMPLE_LEATHER_SET),
    SIMPLE_LEATHER_BOOTS(EquipmentItem.class, LEATHER_BOOTS, 561, (char) 0xF232, EQUIP_SIMPLE_LEATHER_SET),
    SIMPLE_LEATHER_CAP(EquipmentItem.class, LEATHER_HELMET, 562, (char) 0xF233, EQUIP_SIMPLE_LEATHER_SET),
    BASIC_IRON_GREAVES(EquipmentItem.class, IRON_LEGGINGS, 563, (char) 0xF234, EQUIP_BASIC_IRON_SET),
    BASIC_IRON_CHESTPLATE(EquipmentItem.class, IRON_CHESTPLATE, 564, (char) 0xF235, EQUIP_BASIC_IRON_SET),
    BASIC_IRON_HELMET(EquipmentItem.class, IRON_HELMET, 565, (char) 0xF236, EQUIP_BASIC_IRON_SET),
    BASIC_IRON_SABATONS(EquipmentItem.class, IRON_BOOTS, 566, (char) 0xF237, EQUIP_BASIC_IRON_SET),
    BASIC_IRON_GAUNTLETS(EquipmentItem.class, IRON_BOOTS, 567, (char) 0xF238, EQUIP_BASIC_IRON_SET),
    BLUE_CLOTH_TUNIC(EquipmentItem.class, LEATHER_CHESTPLATE, 568, (char) 0xF239, EQUIP_BLUE_CLOTH_SET),
    BLUE_CLOTH_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 569, (char) 0xF23A, EQUIP_BLUE_CLOTH_SET),
    BLUE_CLOTH_HOOD(EquipmentItem.class, LEATHER_HELMET, 570, (char) 0xF23B, EQUIP_BLUE_CLOTH_SET),
    BLUE_CLOTH_SNEAKERS(EquipmentItem.class, LEATHER_BOOTS, 571, (char) 0xF23C, EQUIP_BLUE_CLOTH_SET),
    BLUE_CLOTH_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 572, (char) 0xF23D, EQUIP_BLUE_CLOTH_SET),
    FINE_LEATHER_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 573, (char) 0xF23E, EQUIP_FINE_LEATHER_SET),
    FINE_LEATHER_BOOTS(EquipmentItem.class, LEATHER_BOOTS, 574, (char) 0xF23F, EQUIP_FINE_LEATHER_SET),
    FINE_LEATHER_VEST(EquipmentItem.class, LEATHER_CHESTPLATE, 575, (char) 0xF240, EQUIP_FINE_LEATHER_SET),
    FINE_LEATHER_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 576, (char) 0xF241, EQUIP_FINE_LEATHER_SET),
    FINE_LEATHER_HELMET(EquipmentItem.class, LEATHER_HELMET, 577, (char) 0xF242, EQUIP_FINE_LEATHER_SET),
    BLUE_JESTER_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 578, (char) 0xF243, EQUIP_BLUE_JESTER_SET),
    BLUE_JESTER_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 579, (char) 0xF244, EQUIP_BLUE_JESTER_SET),
    BLUE_JESTER_HAT(EquipmentItem.class, LEATHER_HELMET, 580, (char) 0xF245, EQUIP_BLUE_JESTER_SET),
    BLUE_JESTER_SHOES(EquipmentItem.class, LEATHER_BOOTS, 581, (char) 0xF246, EQUIP_BLUE_JESTER_SET),
    BLUE_JESTER_TUNIC(EquipmentItem.class, LEATHER_CHESTPLATE, 582, (char) 0xF247, EQUIP_BLUE_JESTER_SET),
    RED_JESTER_GLOVES(EquipmentItem.class, LEATHER_BOOTS, 583, (char) 0xF248, EQUIP_RED_JESTER_SET),
    RED_JESTER_HAT(EquipmentItem.class, LEATHER_HELMET, 584, (char) 0xF249, EQUIP_RED_JESTER_SET),
    RED_JESTER_TUNIC(EquipmentItem.class, LEATHER_CHESTPLATE, 585, (char) 0xF24A, EQUIP_RED_JESTER_SET),
    RED_JESTER_SHOES(EquipmentItem.class, LEATHER_BOOTS, 586, (char) 0xF24B, EQUIP_RED_JESTER_SET),
    RED_JESTER_PANTS(EquipmentItem.class, LEATHER_LEGGINGS, 587, (char) 0xF24C, EQUIP_RED_JESTER_SET),
    // Flags, ids transferred from Core
    ARGENTINA(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE1A8, COUNTRY_FLAG, 32),
    AUSTRALIA(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE164, COUNTRY_FLAG, 32),
    AUSTRIA(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE10B, COUNTRY_FLAG, 32),
    BELGIUM(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE10C, COUNTRY_FLAG, 32),
    BRITAIN(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE106, COUNTRY_FLAG, 32),
    CANADA(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE163, COUNTRY_FLAG, 32),
    DENMARK(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE10D, COUNTRY_FLAG, 32),
    ENGLAND(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE11B, COUNTRY_FLAG, 32),
    EUROPE(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE10E, COUNTRY_FLAG, 32),
    FINLAND(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE1A6, COUNTRY_FLAG, 32),
    FRANCE(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE10F, COUNTRY_FLAG, 32),
    GERMANY(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE110, COUNTRY_FLAG, 32),
    IRELAND(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE111, COUNTRY_FLAG, 32),
    ITALY(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE112, COUNTRY_FLAG, 32),
    JAPAN(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE1A7, COUNTRY_FLAG, 32),
    MEXICO(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE108, COUNTRY_FLAG, 32),
    NETHERLANDS(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE165, COUNTRY_FLAG, 32),
    NORWAY(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE113, COUNTRY_FLAG, 32),
    POLAND(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE114, COUNTRY_FLAG, 32),
    SPAIN(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE107, COUNTRY_FLAG, 32),
    SWEDEN(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE115, COUNTRY_FLAG, 32),
    SWITZERLAND(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE116, COUNTRY_FLAG, 16),
    UKRAINE(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE166, COUNTRY_FLAG, 32),
    USA(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE109, COUNTRY_FLAG, 32),
    // Pride Flags
    PRIDE_FLAG(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE119, PRIDE_FLAGS, 32),
    BI_FLAG(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE1A5, PRIDE_FLAGS, 32),
    TRANS_PRIDE_FLAG(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xE11A, PRIDE_FLAGS, 32),
    AGENDER_PRIDE(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xF299, PRIDE_FLAGS, 32),
    AROMANTIC_PRIDE(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xF300, PRIDE_FLAGS, 32),
    ASEXUAL_PRIDE(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xF301, PRIDE_FLAGS, 32),
    GAY_MALE_PRIDE(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xF302, PRIDE_FLAGS, 32),
    GENDERFLUID_PRIDE(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xF303, PRIDE_FLAGS, 32),
    INTERSEX_PRIDE(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xF304, PRIDE_FLAGS, 32),
    NONBINARY_PRIDE(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xF305, PRIDE_FLAGS, 32),
    LESBIAN_PRIDE(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xF306, PRIDE_FLAGS, 32),
    PANSEXUAL_PRIDE(WardrobeItem.class, MOJANG_BANNER_PATTERN, 0xF307, PRIDE_FLAGS, 32),
    // April
    APRIL_FOOLS(ForbiddenMytem.class, CARROT, 0xF26B, JOKE),
    // Spleef
    COPPER_SPLEEF_SHOVEL(SpleefShovel.class, IRON_SHOVEL, 0xF26C, SPLEEF_SHOVEL),
    IRON_SPLEEF_SHOVEL(SpleefShovel.class, IRON_SHOVEL, 0xF26D, SPLEEF_SHOVEL),
    GOLDEN_SPLEEF_SHOVEL(SpleefShovel.class, IRON_SHOVEL, 0xF26E, SPLEEF_SHOVEL),
    // Butterfly
    BLUE_BUTTERFLY(WardrobeItem.class, FEATHER, 0xF26F, (char) 0xF26F, chrarr(0xF26F, 0xF270, 0xF271, 0xF272, 0xF273, 0xF274), BUTTERFLY, Animation.BUTTERFLY),
    CYAN_BUTTERFLY(WardrobeItem.class, FEATHER, 0xF275, (char) 0xF275, chrarr(0xF275, 0xF276, 0xF277, 0xF278, 0xF279, 0xF27A), BUTTERFLY, Animation.BUTTERFLY),
    GREEN_BUTTERFLY(WardrobeItem.class, FEATHER, 0xF27B, (char) 0xF27B, chrarr(0xF27B, 0xF27C, 0xF27D, 0xF27E, 0xF27F, 0xF280), BUTTERFLY, Animation.BUTTERFLY),
    ORANGE_BUTTERFLY(WardrobeItem.class, FEATHER, 0xF281, (char) 0xF281, chrarr(0xF281, 0xF282, 0xF283, 0xF284, 0xF285, 0xF286), BUTTERFLY, Animation.BUTTERFLY),
    PINK_BUTTERFLY(WardrobeItem.class, FEATHER, 0xF287, (char) 0xF287, chrarr(0xF287, 0xF288, 0xF289, 0xF28A, 0xF28B, 0xF28C), BUTTERFLY, Animation.BUTTERFLY),
    PURPLE_BUTTERFLY(WardrobeItem.class, FEATHER, 0xF28D, (char) 0xF28D, chrarr(0xF28D, 0xF28E, 0xF28F, 0xF290, 0xF291, 0xF292), BUTTERFLY, Animation.BUTTERFLY),
    YELLOW_BUTTERFLY(WardrobeItem.class, FEATHER, 0xF293, (char) 0xF293, chrarr(0xF293, 0xF294, 0xF295, 0xF296, 0xF297, 0xF298), BUTTERFLY, Animation.BUTTERFLY),
    // Block
    NETHERITE_PARITY_TABLE(NetheriteParityTable.class, NETHERITE_INGOT, 0xF308, BLOCKS),
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
    public final int pixelWidth;

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

    /**
     * General purpose constructor.
     */
    Mytems(final Class<? extends Mytem> mytemClass, final Material material,
           final Integer customModelData, final char character, final char[] characters,
           final MytemsCategory category,
           final Animation animation,
           final int pixelWidth) {
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
        this.pixelWidth = pixelWidth;
    }

    /**
     * Flag constructor.
     * Model data are indentical.  No animation, but a pixel width.
     */
    Mytems(final Class<? extends Mytem> mytemClass, final Material material, final int model, final MytemsCategory category, final int pixelWidth) {
        this(mytemClass, material, model, (char) model, chrarr(model), category, Animation.NONE, pixelWidth);
    }

    Mytems(final Class<? extends Mytem> mytemClass, final Material material,
           final Integer customModelData, final char character, final char[] characters,
           final MytemsCategory category, final Animation animation) {
        this(mytemClass, material, customModelData, character, characters, category, animation, 16);
    }

    Mytems(final Class<? extends Mytem> mytemClass, final Material material,
           final Integer customModelData,
           final char character, final char[] characters,
           final MytemsCategory category) {
        this(mytemClass, material, customModelData, character, characters, category, Animation.NONE);
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

    public boolean isEssential() {
        switch (this) {
        case EASTER_EGG:
        case EASTER_TOKEN:
        case KITTY_COIN:
        case EARTH:
        case BRITAIN:
        case USA:
        case SPAIN:
        case MEXICO:
        case AUSTRIA:
        case BELGIUM:
        case DENMARK:
        case EUROPE:
        case FRANCE:
        case GERMANY:
        case IRELAND:
        case ITALY:
        case NORWAY:
        case POLAND:
        case SWEDEN:
        case SWITZERLAND:
        case PRIDE_FLAG:
        case TRANS_PRIDE_FLAG:
        case EAGLE:
        case ENGLAND:
        case STAR:
        case HEART:
        case TRAFFIC_LIGHT:
        case HALLOWEEN_TOKEN:
        case MOON:
        case CANADA:
        case AUSTRALIA:
        case NETHERLANDS:
        case UKRAINE:
        case GREEN_MOON:
        case BI_FLAG:
        case FINLAND:
        case GOLDEN_COIN:
        case JAPAN:
        case COPPER_COIN:
        case SILVER_COIN:
        case DIAMOND_COIN:
        case RUBY_COIN:
        case GOLDEN_HOOP:
        case RAINBOW_BUTTERFLY:
        case ARGENTINA:
        case SNOWFLAKE:
        case MAGIC_CAPE:
        case MAGIC_MAP:
        case DICE_ROLL:
        case LIGHTNING:
        case TETRIS_L:
        case TETRIS_Z:
        case TETRIS_T:
        case TETRIS_I:
        case TETRIS_J:
        case TETRIS_S:
        case TETRIS_O:
        case PINK_BUTTERFLY:
        case BLUE_BUTTERFLY:
        case CYAN_BUTTERFLY:
        case GREEN_BUTTERFLY:
        case ORANGE_BUTTERFLY:
        case PURPLE_BUTTERFLY:
        case YELLOW_BUTTERFLY:
        case AGENDER_PRIDE:
        case AROMANTIC_PRIDE:
        case ASEXUAL_PRIDE:
        case GAY_MALE_PRIDE:
        case GENDERFLUID_PRIDE:
        case INTERSEX_PRIDE:
        case NONBINARY_PRIDE:
        case LESBIAN_PRIDE:
        case PANSEXUAL_PRIDE:
        case NO:
            return true;
        default: return false;
        }
    }
}
