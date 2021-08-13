package com.cavetale.mytems;

import com.cavetale.mytems.item.ChristmasToken;
import com.cavetale.mytems.item.DummyMytem;
import com.cavetale.mytems.item.Enderball;
import com.cavetale.mytems.item.Ingredient;
import com.cavetale.mytems.item.KittyCoin;
import com.cavetale.mytems.item.MagicCape;
import com.cavetale.mytems.item.MagicMap;
import com.cavetale.mytems.item.Ocarina;
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
import com.cavetale.mytems.item.medieval.GoldenScythe;
import com.cavetale.mytems.item.pocketmob.MobCatcher;
import com.cavetale.mytems.item.pocketmob.PocketMob;
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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * List of all known Mytems.
 * Unicode characters start at 0xE200.
 */
public enum Mytems {
    // Halloween 2020
    DR_ACULA_STAFF(DrAculaStaff::new, Material.NETHERITE_SWORD, 741302, '\uE220'),
    FLAME_SHIELD(FlameShield::new, Material.SHIELD, 741303, '\uE234'),
    STOMPERS(Stompers::new, Material.NETHERITE_BOOTS, 741304, '\uE235'),
    GHAST_BOW(GhastBow::new, Material.BOW, 741305, '\uE236'),
    BAT_MASK(BatMask::new, Material.PLAYER_HEAD, 741306, '\uE237'),
    // Cloud City
    UNICORN_HORN(UnicornHorn::new, Material.END_ROD, 7413003),
    MAGIC_CAPE(MagicCape::new, Material.ELYTRA, 7413006, '\uE238'),
    // Generic
    KITTY_COIN(KittyCoin::new, Material.PLAYER_HEAD, 7413001, '\uE200'),
    RAINBOW_KITTY_COIN(KittyCoin::new, Material.PLAYER_HEAD, 7413007, '\uE243'),
    // Christmas 2020
    CHRISTMAS_TOKEN(ChristmasToken::new),
    SANTA_HAT(SantaHat::new, Material.PLAYER_HEAD, 7413101, '\uE221'),
    SANTA_JACKET(SantaJacket::new, Material.LEATHER_CHESTPLATE, 4713102, '\uE222'),
    SANTA_PANTS(SantaPants::new, Material.LEATHER_LEGGINGS, 4713103, '\uE223'),
    SANTA_BOOTS(SantaBoots::new, Material.LEATHER_BOOTS, 4713104, '\uE224'),
    // Dune set
    DUNE_HELMET(DuneItem.Helmet::new, Material.PLAYER_HEAD, 7413201, '\uE225'),
    DUNE_CHESTPLATE(DuneItem.Chestplate::new, Material.GOLDEN_CHESTPLATE, 7413202, '\uE226'),
    DUNE_LEGGINGS(DuneItem.Leggings::new, Material.GOLDEN_LEGGINGS, 7413203, '\uE227'),
    DUNE_BOOTS(DuneItem.Boots::new, Material.GOLDEN_BOOTS, 7413204, '\uE228'),
    DUNE_DIGGER(DuneItem.Weapon::new, Material.GOLDEN_SHOVEL, 7413205, '\uE229'),
    // Swampy set
    SWAMPY_HELMET(SwampyItem.Helmet::new, Material.PLAYER_HEAD, 7413301, '\uE22A'),
    SWAMPY_CHESTPLATE(SwampyItem.Chestplate::new, Material.LEATHER_CHESTPLATE, 7413302, '\uE22B'),
    SWAMPY_LEGGINGS(SwampyItem.Leggings::new, Material.LEATHER_LEGGINGS, 7413303, '\uE22C'),
    SWAMPY_BOOTS(SwampyItem.Boots::new, Material.LEATHER_BOOTS, 7413304, '\uE22D'),
    SWAMPY_TRIDENT(SwampyItem.Weapon::new, Material.TRIDENT, 7413305, '\uE22E'),
    // Swampy set
    DWARVEN_HELMET(DwarvenItem.Helmet::new, Material.PLAYER_HEAD, 7413401, '\uE22F'),
    DWARVEN_CHESTPLATE(DwarvenItem.Chestplate::new, Material.IRON_CHESTPLATE, 7413402, '\uE230'),
    DWARVEN_LEGGINGS(DwarvenItem.Leggings::new, Material.IRON_LEGGINGS, 7413403, '\uE231'),
    DWARVEN_BOOTS(DwarvenItem.Boots::new, Material.IRON_BOOTS, 7413404, '\uE232'),
    DWARVEN_AXE(DwarvenItem.Weapon::new, Material.IRON_AXE, 7413405, '\uE233'),
    // Easter 2021
    EASTER_TOKEN(EasterToken::new, Material.PLAYER_HEAD, 345700, '\uE211'),
    EASTER_EGG(DummyMytem::new, Material.EGG, 345715, '\uE23C'),
    BLUE_EASTER_EGG(EasterEgg::new, Material.PLAYER_HEAD, 345701, '\uE212'),
    GREEN_EASTER_EGG(EasterEgg::new, Material.PLAYER_HEAD, 345702, '\uE213'),
    ORANGE_EASTER_EGG(EasterEgg::new, Material.PLAYER_HEAD, 345703, '\uE214'),
    PINK_EASTER_EGG(EasterEgg::new, Material.PLAYER_HEAD, 345704, '\uE215'),
    PURPLE_EASTER_EGG(EasterEgg::new, Material.PLAYER_HEAD, 345705, '\uE216'),
    YELLOW_EASTER_EGG(EasterEgg::new, Material.PLAYER_HEAD, 345706, '\uE217'),
    EASTER_HELMET(EasterGear.Helmet::new, Material.PLAYER_HEAD, 345711, '\uE218'),
    EASTER_CHESTPLATE(EasterGear.Chestplate::new, Material.LEATHER_CHESTPLATE, 345712, '\uE219'),
    EASTER_LEGGINGS(EasterGear.Leggings::new, Material.LEATHER_LEGGINGS, 345713, '\uE21A'),
    EASTER_BOOTS(EasterGear.Boots::new, Material.LEATHER_BOOTS, 345714, '\uE21B'),
    //
    TOILET(Toilet::new, Material.CAULDRON, 498101), // APRIL
    WEDDING_RING(WeddingRing::new, Material.PLAYER_HEAD, 7413002, '\uE21C'),
    MAGIC_MAP(MagicMap::new, Material.FILLED_MAP, 7413005, '\uE21D'),
    BOSS_CHEST(DummyMytem::new, Material.CHEST, 7413004),
    // Wardrobe
    WHITE_BUNNY_EARS(WardrobeItem::new, Material.IRON_BOOTS, 3919001), // EPIC
    RED_LIGHTSABER(WardrobeItem::new, Material.END_ROD, 3919002),
    BLUE_LIGHTSABER(WardrobeItem::new, Material.END_ROD, 3919003),
    PIRATE_HAT(WardrobeItem::new, Material.BLACK_DYE, 3919004),
    COWBOY_HAT(WardrobeItem::new, Material.BROWN_DYE, 3919005),
    // Vote
    VOTE_CANDY(VoteCandy::new, Material.COOKIE, 9073001, '\uE21E'), // VOTE
    VOTE_FIREWORK(VoteFirework::new, Material.FIREWORK_ROCKET, 9073002, '\uE21F'),
    // Maypole
    LUCID_LILY(Ingredient::new, Material.AZURE_BLUET, 849001, '\uE201'),
    PINE_CONE(Ingredient::new, Material.SPRUCE_SAPLING, 849002, '\uE202'),
    ORANGE_ONION(Ingredient::new, Material.ORANGE_TULIP, 849003, '\uE203'),
    MISTY_MOREL(Ingredient::new, Material.WARPED_FUNGUS, 849004, '\uE204'),
    RED_ROSE(Ingredient::new, Material.POPPY, 849005, '\uE205'),
    FROST_FLOWER(Ingredient::new, Material.BLUE_ORCHID, 849006, '\uE206'),
    HEAT_ROOT(Ingredient::new, Material.DEAD_BUSH, 849007, '\uE207'),
    CACTUS_BLOSSOM(Ingredient::new, Material.CACTUS, 849008, '\uE208'),
    PIPE_WEED(Ingredient::new, Material.FERN, 849009, '\uE209'),
    KINGS_PUMPKIN(Ingredient::new, Material.CARVED_PUMPKIN, 849010, '\uE20A'),
    SPARK_SEED(Ingredient::new, Material.BEETROOT_SEEDS, 849011, '\uE20B'),
    OASIS_WATER(Ingredient::new, Material.LIGHT_BLUE_DYE, 849012, '\uE20C'),
    CLAMSHELL(Ingredient::new, Material.NAUTILUS_SHELL, 849013, '\uE20D'),
    FROZEN_AMBER(Ingredient::new, Material.EMERALD, 849014, '\uE20E'),
    CLUMP_OF_MOSS(Ingredient::new, Material.VINE, 849015, '\uE20F'),
    FIRE_AMANITA(Ingredient::new, Material.CRIMSON_FUNGUS, 849016, '\uE210'),
    // Enderball
    ENDERBALL(Enderball::new),
    // PocketMob
    POCKET_BAT(mytems -> new PocketMob(mytems, EntityType.BAT), Material.BAT_SPAWN_EGG, 908301),
    POCKET_BEE(mytems -> new PocketMob(mytems, EntityType.BEE), Material.BEE_SPAWN_EGG, 908301),
    POCKET_BLAZE(mytems -> new PocketMob(mytems, EntityType.BLAZE), Material.BLAZE_SPAWN_EGG, 908301),
    POCKET_CAT(mytems -> new PocketMob(mytems, EntityType.CAT), Material.CAT_SPAWN_EGG, 908301),
    POCKET_CAVE_SPIDER(mytems -> new PocketMob(mytems, EntityType.CAVE_SPIDER), Material.CAVE_SPIDER_SPAWN_EGG, 908301),
    POCKET_CHICKEN(mytems -> new PocketMob(mytems, EntityType.CHICKEN), Material.CHICKEN_SPAWN_EGG, 908301),
    POCKET_COD(mytems -> new PocketMob(mytems, EntityType.COD), Material.COD_SPAWN_EGG, 908301),
    POCKET_COW(mytems -> new PocketMob(mytems, EntityType.COW), Material.COW_SPAWN_EGG, 908301),
    POCKET_CREEPER(mytems -> new PocketMob(mytems, EntityType.CREEPER), Material.CREEPER_SPAWN_EGG, 908301),
    POCKET_DOLPHIN(mytems -> new PocketMob(mytems, EntityType.DOLPHIN), Material.DOLPHIN_SPAWN_EGG, 908301),
    POCKET_DONKEY(mytems -> new PocketMob(mytems, EntityType.DONKEY), Material.DONKEY_SPAWN_EGG, 908301),
    POCKET_DROWNED(mytems -> new PocketMob(mytems, EntityType.DROWNED), Material.DROWNED_SPAWN_EGG, 908301),
    POCKET_ELDER_GUARDIAN(mytems -> new PocketMob(mytems, EntityType.ELDER_GUARDIAN), Material.ELDER_GUARDIAN_SPAWN_EGG, 908301),
    POCKET_ENDERMAN(mytems -> new PocketMob(mytems, EntityType.ENDERMAN), Material.ENDERMAN_SPAWN_EGG, 908301),
    POCKET_ENDERMITE(mytems -> new PocketMob(mytems, EntityType.ENDERMITE), Material.ENDERMITE_SPAWN_EGG, 908301),
    POCKET_ENDER_DRAGON(mytems -> new PocketMob(mytems, EntityType.ENDER_DRAGON), Material.ENDERMAN_SPAWN_EGG, 908301),
    POCKET_EVOKER(mytems -> new PocketMob(mytems, EntityType.EVOKER), Material.EVOKER_SPAWN_EGG, 908301),
    POCKET_FOX(mytems -> new PocketMob(mytems, EntityType.FOX), Material.FOX_SPAWN_EGG, 908301),
    POCKET_GHAST(mytems -> new PocketMob(mytems, EntityType.GHAST), Material.GHAST_SPAWN_EGG, 908301),
    POCKET_GIANT(mytems -> new PocketMob(mytems, EntityType.GIANT), Material.ZOMBIE_SPAWN_EGG, 908301),
    POCKET_GUARDIAN(mytems -> new PocketMob(mytems, EntityType.GUARDIAN), Material.GUARDIAN_SPAWN_EGG, 908301),
    POCKET_HOGLIN(mytems -> new PocketMob(mytems, EntityType.HOGLIN), Material.HOGLIN_SPAWN_EGG, 908301),
    POCKET_HORSE(mytems -> new PocketMob(mytems, EntityType.HORSE), Material.HORSE_SPAWN_EGG, 908301),
    POCKET_HUSK(mytems -> new PocketMob(mytems, EntityType.HUSK), Material.HUSK_SPAWN_EGG, 908301),
    POCKET_ILLUSIONER(mytems -> new PocketMob(mytems, EntityType.ILLUSIONER), Material.VINDICATOR_SPAWN_EGG, 908301),
    POCKET_IRON_GOLEM(mytems -> new PocketMob(mytems, EntityType.IRON_GOLEM), Material.WOLF_SPAWN_EGG, 908301),
    POCKET_LLAMA(mytems -> new PocketMob(mytems, EntityType.LLAMA), Material.LLAMA_SPAWN_EGG, 908301),
    POCKET_MAGMA_CUBE(mytems -> new PocketMob(mytems, EntityType.MAGMA_CUBE), Material.MAGMA_CUBE_SPAWN_EGG, 908301),
    POCKET_MULE(mytems -> new PocketMob(mytems, EntityType.MULE), Material.MULE_SPAWN_EGG, 908301),
    POCKET_MUSHROOM_COW(mytems -> new PocketMob(mytems, EntityType.MUSHROOM_COW), Material.MOOSHROOM_SPAWN_EGG, 908301),
    POCKET_OCELOT(mytems -> new PocketMob(mytems, EntityType.OCELOT), Material.OCELOT_SPAWN_EGG, 908301),
    POCKET_PANDA(mytems -> new PocketMob(mytems, EntityType.PANDA), Material.PANDA_SPAWN_EGG, 908301),
    POCKET_PARROT(mytems -> new PocketMob(mytems, EntityType.PARROT), Material.PARROT_SPAWN_EGG, 908301),
    POCKET_PHANTOM(mytems -> new PocketMob(mytems, EntityType.PHANTOM), Material.PHANTOM_SPAWN_EGG, 908301),
    POCKET_PIG(mytems -> new PocketMob(mytems, EntityType.PIG), Material.PIG_SPAWN_EGG, 908301),
    POCKET_PIGLIN(mytems -> new PocketMob(mytems, EntityType.PIGLIN), Material.PIGLIN_SPAWN_EGG, 908301),
    POCKET_PIGLIN_BRUTE(mytems -> new PocketMob(mytems, EntityType.PIGLIN_BRUTE), Material.PIGLIN_BRUTE_SPAWN_EGG, 908301),
    POCKET_PILLAGER(mytems -> new PocketMob(mytems, EntityType.PILLAGER), Material.PILLAGER_SPAWN_EGG, 908301),
    POCKET_POLAR_BEAR(mytems -> new PocketMob(mytems, EntityType.POLAR_BEAR), Material.POLAR_BEAR_SPAWN_EGG, 908301),
    POCKET_PUFFERFISH(mytems -> new PocketMob(mytems, EntityType.PUFFERFISH), Material.PUFFERFISH_SPAWN_EGG, 908301),
    POCKET_RABBIT(mytems -> new PocketMob(mytems, EntityType.RABBIT), Material.RABBIT_SPAWN_EGG, 908301),
    POCKET_RAVAGER(mytems -> new PocketMob(mytems, EntityType.RAVAGER), Material.RAVAGER_SPAWN_EGG, 908301),
    POCKET_SALMON(mytems -> new PocketMob(mytems, EntityType.SALMON), Material.SALMON_SPAWN_EGG, 908301),
    POCKET_SHEEP(mytems -> new PocketMob(mytems, EntityType.SHEEP), Material.SHEEP_SPAWN_EGG, 908301),
    POCKET_SHULKER(mytems -> new PocketMob(mytems, EntityType.SHULKER), Material.SHULKER_SPAWN_EGG, 908301),
    POCKET_SILVERFISH(mytems -> new PocketMob(mytems, EntityType.SILVERFISH), Material.SILVERFISH_SPAWN_EGG, 908301),
    POCKET_SKELETON(mytems -> new PocketMob(mytems, EntityType.SKELETON), Material.SKELETON_SPAWN_EGG, 908301),
    POCKET_SKELETON_HORSE(mytems -> new PocketMob(mytems, EntityType.SKELETON_HORSE), Material.SKELETON_HORSE_SPAWN_EGG, 908301),
    POCKET_SLIME(mytems -> new PocketMob(mytems, EntityType.SLIME), Material.SLIME_SPAWN_EGG, 908301),
    POCKET_SNOWMAN(mytems -> new PocketMob(mytems, EntityType.SNOWMAN), Material.POLAR_BEAR_SPAWN_EGG, 908301),
    POCKET_SPIDER(mytems -> new PocketMob(mytems, EntityType.SPIDER), Material.SPIDER_SPAWN_EGG, 908301),
    POCKET_SQUID(mytems -> new PocketMob(mytems, EntityType.SQUID), Material.SQUID_SPAWN_EGG, 908301),
    POCKET_STRAY(mytems -> new PocketMob(mytems, EntityType.STRAY), Material.STRAY_SPAWN_EGG, 908301),
    POCKET_STRIDER(mytems -> new PocketMob(mytems, EntityType.STRIDER), Material.STRIDER_SPAWN_EGG, 908301),
    POCKET_TRADER_LLAMA(mytems -> new PocketMob(mytems, EntityType.TRADER_LLAMA), Material.TRADER_LLAMA_SPAWN_EGG, 908301),
    POCKET_TROPICAL_FISH(mytems -> new PocketMob(mytems, EntityType.TROPICAL_FISH), Material.TROPICAL_FISH_SPAWN_EGG, 908301),
    POCKET_TURTLE(mytems -> new PocketMob(mytems, EntityType.TURTLE), Material.TURTLE_SPAWN_EGG, 908301),
    POCKET_VEX(mytems -> new PocketMob(mytems, EntityType.VEX), Material.VEX_SPAWN_EGG, 908301),
    POCKET_VILLAGER(mytems -> new PocketMob(mytems, EntityType.VILLAGER), Material.VILLAGER_SPAWN_EGG, 908301),
    POCKET_VINDICATOR(mytems -> new PocketMob(mytems, EntityType.VINDICATOR), Material.VINDICATOR_SPAWN_EGG, 908301),
    POCKET_WANDERING_TRADER(mytems -> new PocketMob(mytems, EntityType.WANDERING_TRADER), Material.WANDERING_TRADER_SPAWN_EGG, 908301),
    POCKET_WITCH(mytems -> new PocketMob(mytems, EntityType.WITCH), Material.WITCH_SPAWN_EGG, 908301),
    POCKET_WITHER(mytems -> new PocketMob(mytems, EntityType.WITHER), Material.WITHER_SKELETON_SPAWN_EGG, 908301),
    POCKET_WITHER_SKELETON(mytems -> new PocketMob(mytems, EntityType.WITHER_SKELETON), Material.WITHER_SKELETON_SPAWN_EGG, 908301),
    POCKET_WOLF(mytems -> new PocketMob(mytems, EntityType.WOLF), Material.WOLF_SPAWN_EGG, 908301),
    POCKET_ZOGLIN(mytems -> new PocketMob(mytems, EntityType.ZOGLIN), Material.ZOGLIN_SPAWN_EGG, 908301),
    POCKET_ZOMBIE(mytems -> new PocketMob(mytems, EntityType.ZOMBIE), Material.ZOMBIE_SPAWN_EGG, 908301),
    POCKET_ZOMBIE_HORSE(mytems -> new PocketMob(mytems, EntityType.ZOMBIE_HORSE), Material.ZOMBIE_HORSE_SPAWN_EGG, 908301),
    POCKET_ZOMBIE_VILLAGER(mytems -> new PocketMob(mytems, EntityType.ZOMBIE_VILLAGER), Material.ZOMBIE_VILLAGER_SPAWN_EGG, 908301),
    POCKET_ZOMBIFIED_PIGLIN(mytems -> new PocketMob(mytems, EntityType.ZOMBIFIED_PIGLIN), Material.ZOMBIFIED_PIGLIN_SPAWN_EGG, 908301),
    POCKET_GOAT(mytems -> new PocketMob(mytems, EntityType.GOAT), Material.GOAT_SPAWN_EGG, 908301),
    POCKET_AXOLOTL(mytems -> new PocketMob(mytems, EntityType.AXOLOTL), Material.AXOLOTL_SPAWN_EGG, 908301),
    POCKET_GLOW_SQUID(mytems -> new PocketMob(mytems, EntityType.GLOW_SQUID), Material.GLOW_SQUID_SPAWN_EGG, 908301),
    // Mob Catcher
    MOB_CATCHER(MobCatcher::new, Material.EGG, 908302, '\uE24E'),
    MONSTER_CATCHER(MobCatcher::new, Material.EGG, 908303, '\uE24F'),
    ANIMAL_CATCHER(MobCatcher::new, Material.EGG, 908304, '\uE250'),
    VILLAGER_CATCHER(MobCatcher::new, Material.EGG, 908305, '\uE251'),
    FISH_CATCHER(MobCatcher::new, Material.EGG, 908306, '\uE252'),
    PET_CATCHER(MobCatcher::new, Material.EGG, 908307, '\uE253'),
    CAPTAINS_CUTLASS(CaptainsCutlass::new, Material.WOODEN_SWORD, 2, '\uE239'),
    BLUNDERBUSS(Blunderbuss::new, Material.IRON_INGOT, 3, '\uE23A'),
    GOLDEN_SCYTHE(GoldenScythe::new, Material.GOLDEN_HOE, 4, '\uE23B'),
    OCARINA_OF_CHIME(Ocarina::new, Material.NAUTILUS_SHELL, 36, '\uE264'),
    // Enemy
    KOBOLD_HEAD(DummyMytem::new, Material.GREEN_CONCRETE, 1),
    // Random
    EARTH(DummyMytem::new, Material.ENDER_PEARL, 5, '\uE23D'),
    RUBY(DummyMytem::new, Material.EMERALD, 6, '\uE23E'),
    // UI
    OK(DummyMytem::new, Material.BLUE_CONCRETE, 7, '\uE23F'),
    NO(DummyMytem::new, Material.RED_CONCRETE, 8, '\uE240'),
    HEART(DummyMytem::new, Material.APPLE, 9, '\uE241'),
    HALF_HEART(DummyMytem::new, Material.APPLE, 10, '\uE242'),
    ARROW_RIGHT(DummyMytem::new, Material.ARROW, 11, '\uE244'),
    ARROW_LEFT(DummyMytem::new, Material.ARROW, 12, '\uE245'),
    ARROW_UP(DummyMytem::new, Material.ARROW, 37, '\uE265'),
    ARROW_DOWN(DummyMytem::new, Material.ARROW, 38, '\uE266'),
    EMPTY_HEART(DummyMytem::new, Material.APPLE, 13, '\uE246'),
    CHECKBOX(DummyMytem::new, Material.WHITE_CONCRETE, 14, '\uE247'),
    CHECKED_CHECKBOX(DummyMytem::new, Material.GREEN_CONCRETE, 15, '\uE248'),
    CROSSED_CHECKBOX(DummyMytem::new, Material.BARRIER, 16, '\uE249'),
    QUESTION_MARK(DummyMytem::new, Material.BARRIER, 17, '\uE24A'),
    STAR(DummyMytem::new, Material.NETHER_STAR, 18, '\uE24B'),
    EAGLE(DummyMytem::new, Material.FEATHER, 19, '\uE24C'),
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
    WINK(DummyMytem::new, Material.SLIME_BALL, 35, '\uE263', Category.REACTION);
    // Next Unicode Character: 0xE267
    // Next CustomModelData: 39

    private static final Map<String, Mytems> ID_MAP = new HashMap<>();
    public final String id;
    public final Function<Mytems, Mytem> ctor;
    public final Material material;
    public final Integer customModelData;
    public final char character;
    public final Component component;
    public final Category category;

    public enum Category {
        REACTION;
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
    }

    Mytems(final Function<Mytems, Mytem> ctor) {
        this.ctor = ctor;
        this.id = name().toLowerCase();
        this.material = null;
        this.customModelData = null;
        this.character = (char) 0;
        this.component = Component.empty();
        this.category = null;
    }

    Mytems(final Function<Mytems, Mytem> ctor, final Material material, final Integer customModelData) {
        this.ctor = ctor;
        this.id = name().toLowerCase();
        this.material = material;
        this.customModelData = customModelData;
        this.character = (char) 0;
        this.component = Component.empty();
        this.category = null;
    }

    Mytems(final Function<Mytems, Mytem> ctor, final Material material, final Integer customModelData, final char character) {
        this.ctor = ctor;
        this.id = name().toLowerCase();
        this.material = material;
        this.customModelData = customModelData;
        this.character = character;
        this.component = Component.text(character)
            .style(Style.style().font(Key.key("cavetale:default")).color(TextColor.color(0xFFFFFF)));
        this.category = null;
    }

    Mytems(final Function<Mytems, Mytem> ctor, final Material material, final Integer customModelData, final char character, final Category category) {
        this.ctor = ctor;
        this.id = name().toLowerCase();
        this.material = material;
        this.customModelData = customModelData;
        this.character = character;
        this.component = Component.text(character)
            .style(Style.style().font(Key.key("cavetale:default")).color(TextColor.color(0xFFFFFF)));
        this.category = category;
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
