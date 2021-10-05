package com.cavetale.mytems;

import com.cavetale.mytems.item.ChristmasToken;
import com.cavetale.mytems.item.DummyMytem;
import com.cavetale.mytems.item.Enderball;
import com.cavetale.mytems.item.Ingredient;
import com.cavetale.mytems.item.KittyCoin;
import com.cavetale.mytems.item.MagicCape;
import com.cavetale.mytems.item.MagicMap;
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
import com.cavetale.mytems.item.medieval.WitchBroom;
import com.cavetale.mytems.item.music.HyruleInstrument;
import com.cavetale.mytems.item.music.RegularInstrument;
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
    CHRISTMAS_TOKEN(ChristmasToken::new, Category.CURRENCY),
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
    //
    TOILET(Toilet::new, Material.CAULDRON, 498101, (char) 0, Category.BLOCK), // APRIL
    WEDDING_RING(WeddingRing::new, Material.PLAYER_HEAD, 7413002, '\uE21C', Category.FRIENDS),
    MAGIC_MAP(MagicMap::new, Material.FILLED_MAP, 7413005, '\uE21D', Category.UTILITY),
    BOSS_CHEST(DummyMytem::new, Material.CHEST, 7413004, (char) 0, Category.BLOCK),
    // Wardrobe
    WHITE_BUNNY_EARS(WardrobeItem::new, Material.IRON_BOOTS, 3919001, (char) 0, Category.WARDROBE), // EPIC
    RED_LIGHTSABER(WardrobeItem::new, Material.END_ROD, 3919002, (char) 0, Category.WARDROBE),
    BLUE_LIGHTSABER(WardrobeItem::new, Material.END_ROD, 3919003, (char) 0, Category.WARDROBE),
    PIRATE_HAT(WardrobeItem::new, Material.BLACK_DYE, 3919004, (char) 0, Category.WARDROBE),
    COWBOY_HAT(WardrobeItem::new, Material.BROWN_DYE, 3919005, (char) 0, Category.WARDROBE),
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
    PAN_FLUTE(RegularInstrument::new, Material.STICK, 43, '\uE26B', Category.MUSIC), // Flute
    TRIANGLE(RegularInstrument::new, Material.STICK, 44, '\uE26C', Category.MUSIC), // Chime
    WOODEN_DRUM(RegularInstrument::new, Material.STICK, 45, '\uE26D', Category.MUSIC), // Bass Drum
    WOODEN_LUTE(RegularInstrument::new, Material.STICK, 46, '\uE26E', Category.MUSIC), // Guitar
    WOODEN_OCARINA(RegularInstrument::new, Material.STICK, 47, '\uE26F', Category.MUSIC), // Flute
    BANJO(RegularInstrument::new, Material.STICK, 48, '\uE270', Category.MUSIC), // Banjo
    BIT_BOY(RegularInstrument::new, Material.STICK, 49, '\uE271', Category.MUSIC), // Bit
    GUITAR(RegularInstrument::new, Material.STICK, 50, '\uE272', Category.MUSIC), // Guitar
    WOODEN_HORN(RegularInstrument::new, Material.STICK, 55, '\uE277', Category.MUSIC), // Didgeridoo
    MUSICAL_BELL(RegularInstrument::new, Material.STICK, 56, '\uE278', Category.MUSIC), // Bell
    COW_BELL(RegularInstrument::new, Material.STICK, 58, '\uE27A', Category.MUSIC), // Cow Bell
    RAINBOW_XYLOPHONE(RegularInstrument::new, Material.STICK, 59, '\uE27B', Category.MUSIC), // Xylophone
    ELECTRIC_GUITAR(RegularInstrument::new, Material.STICK, 60, '\uE27C', Category.MUSIC), // Bass Guitar
    POCKET_PIANO(RegularInstrument::new, Material.STICK, 61, '\uE27D', Category.MUSIC), // Piano
    ELECTRIC_PIANO(RegularInstrument::new, Material.STICK, 62, '\uE27E', Category.MUSIC), // Pling
    SNARE_DRUM(RegularInstrument::new, Material.STICK, 63, '\uE27F', Category.MUSIC), // Snare Drums
    // Enemy
    KOBOLD_HEAD(DummyMytem::new, Material.GREEN_CONCRETE, 1, (char) 0, Category.ENEMY),
    // Random
    RUBY(DummyMytem::new, Material.EMERALD, 6, '\uE23E', Category.RESOURCE),
    // UI
    OK(DummyMytem::new, Material.BLUE_CONCRETE, 7, '\uE23F', Category.UI),
    NO(DummyMytem::new, Material.RED_CONCRETE, 8, '\uE240', Category.UI),
    HEART(DummyMytem::new, Material.APPLE, 9, '\uE241', Category.UI),
    HALF_HEART(DummyMytem::new, Material.APPLE, 10, '\uE242', Category.UI),
    ARROW_RIGHT(DummyMytem::new, Material.ARROW, 11, '\uE244', Category.UI),
    ARROW_LEFT(DummyMytem::new, Material.ARROW, 12, '\uE245', Category.UI),
    ARROW_UP(DummyMytem::new, Material.ARROW, 37, '\uE265', Category.UI),
    ARROW_DOWN(DummyMytem::new, Material.ARROW, 38, '\uE266', Category.UI),
    EMPTY_HEART(DummyMytem::new, Material.APPLE, 13, '\uE246', Category.UI),
    CHECKBOX(DummyMytem::new, Material.WHITE_CONCRETE, 14, '\uE247', Category.UI),
    CHECKED_CHECKBOX(DummyMytem::new, Material.GREEN_CONCRETE, 15, '\uE248', Category.UI),
    CROSSED_CHECKBOX(DummyMytem::new, Material.BARRIER, 16, '\uE249', Category.UI),
    QUESTION_MARK(DummyMytem::new, Material.BARRIER, 17, '\uE24A', Category.UI),
    STAR(DummyMytem::new, Material.NETHER_STAR, 18, '\uE24B', Category.UI),
    EAGLE(DummyMytem::new, Material.FEATHER, 19, '\uE24C', Category.UI),
    EARTH(DummyMytem::new, Material.ENDER_PEARL, 5, '\uE23D', Category.UI),
    EASTER_EGG(DummyMytem::new, Material.EGG, 345715, '\uE23C', Category.UI),
    TRAFFIC_LIGHT(DummyMytem::new, Material.YELLOW_DYE, 57, '\uE279', Category.UI),
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
    CANDY_CORN(DummyMytem::new, Material.CARROT, 42, '\uE26A', Category.HALLOWEEN),
    CHOCOLATE_BAR(DummyMytem::new, Material.PUMPKIN_PIE, 52, '\uE274', Category.HALLOWEEN),
    LOLLIPOP(DummyMytem::new, Material.COOKIE, 53, '\uE275', Category.HALLOWEEN),
    ORANGE_CANDY(DummyMytem::new, Material.COOKIE, 54, '\uE276', Category.HALLOWEEN);
    // Next Unicode Character: \uE280
    // Next CustomModelData: 64

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
        BLOCK,
        CLOUD_CITY,
        CURRENCY,
        DUNE,
        DWARVEN,
        EASTER,
        EASTER_EGG,
        ENEMY,
        FRIENDS,
        HALLOWEEN,
        MAYPOLE,
        MOB_CATCHER,
        MUSIC,
        MUSIC_HYRULE,
        PICTURE,
        PIRATE,
        POCKET_MOB,
        REACTION,
        RESOURCE,
        SANTA,
        SWAMPY,
        UI,
        UTILITY,
        VOTE,
        WARDROBE,
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
        this(ctor, (Material) null, (Integer) null, (char) 0, Category.UNKNOWN);
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

    /**
     * Create an item with the looks of the Mytem, but none of the
     * stats, id, or lore attached.
     */
    public ItemStack createIcon() {
        ItemStack item = new ItemStack(material);
        item.editMeta(meta -> meta.setCustomModelData(customModelData));
        return item;
    }
}
