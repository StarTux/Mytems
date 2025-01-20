package com.cavetale.mytems.item.pocketmob;

import com.cavetale.mytems.Mytems;
import java.util.EnumMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.EntityType;

@RequiredArgsConstructor
public enum PocketMobType {
    ALLAY(Mytems.POCKET_ALLAY, EntityType.ALLAY, 0xdaff, 0xadff),
    ARMADILLO(Mytems.POCKET_ARMADILLO, EntityType.ARMADILLO, 0xad716d, 0x824848),
    AXOLOTL(Mytems.POCKET_AXOLOTL, EntityType.AXOLOTL, 0xfbc1e3, 0xa62d74),
    BAT(Mytems.POCKET_BAT, EntityType.BAT, 0x4c3e30, 0xf0f0f),
    BEE(Mytems.POCKET_BEE, EntityType.BEE, 0xedc343, 0x43241b),
    BLAZE(Mytems.POCKET_BLAZE, EntityType.BLAZE, 0xf6b201, 0xfff87e),
    BOGGED(Mytems.POCKET_BOGGED, EntityType.BOGGED, 0x8a9c72, 0x314d1b),
    BREEZE(Mytems.POCKET_BREEZE, EntityType.BREEZE, 0xaf94df, 0x9166df),
    CAMEL(Mytems.POCKET_CAMEL, EntityType.CAMEL, 0xfcc369, 0xcb9337),
    CAT(Mytems.POCKET_CAT, EntityType.CAT, 0xefc88e, 0x957256),
    CAVE_SPIDER(Mytems.POCKET_CAVE_SPIDER, EntityType.CAVE_SPIDER, 0xc424e, 0xa80e0e),
    CHICKEN(Mytems.POCKET_CHICKEN, EntityType.CHICKEN, 0xa1a1a1, 0xff0000),
    COD(Mytems.POCKET_COD, EntityType.COD, 0xc1a76a, 0xe5c48b),
    COW(Mytems.POCKET_COW, EntityType.COW, 0x443626, 0xa1a1a1),
    CREAKING(Mytems.POCKET_CREAKING, EntityType.CREAKING, 0xffffff, 0x0),
    CREEPER(Mytems.POCKET_CREEPER, EntityType.CREEPER, 0xda70b, 0x0),
    DOLPHIN(Mytems.POCKET_DOLPHIN, EntityType.DOLPHIN, 0x223b4d, 0xf9f9f9),
    DONKEY(Mytems.POCKET_DONKEY, EntityType.DONKEY, 0x534539, 0x867566),
    DROWNED(Mytems.POCKET_DROWNED, EntityType.DROWNED, 0x8ff1d7, 0x799c65),
    ELDER_GUARDIAN(Mytems.POCKET_ELDER_GUARDIAN, EntityType.ELDER_GUARDIAN, 0xceccba, 0x747693),
    ENDERMAN(Mytems.POCKET_ENDERMAN, EntityType.ENDERMAN, 0x161616, 0x0),
    ENDERMITE(Mytems.POCKET_ENDERMITE, EntityType.ENDERMITE, 0x161616, 0x6e6e6e),
    ENDER_DRAGON(Mytems.POCKET_ENDER_DRAGON, EntityType.ENDER_DRAGON, 0x1c1c1c, 0xe079fa),
    EVOKER(Mytems.POCKET_EVOKER, EntityType.EVOKER, 0x959b9b, 0x1e1c1a),
    FOX(Mytems.POCKET_FOX, EntityType.FOX, 0xd5b69f, 0xcc6920),
    FROG(Mytems.POCKET_FROG, EntityType.FROG, 0xd07444, 0xffc77c),
    GHAST(Mytems.POCKET_GHAST, EntityType.GHAST, 0xf9f9f9, 0xbcbcbc),
    GIANT(Mytems.POCKET_GIANT, EntityType.GIANT, 0xafaf, 0x799c65),
    GLOW_SQUID(Mytems.POCKET_GLOW_SQUID, EntityType.GLOW_SQUID, 0x95656, 0x85f1bc),
    GOAT(Mytems.POCKET_GOAT, EntityType.GOAT, 0xa5947c, 0x55493e),
    GUARDIAN(Mytems.POCKET_GUARDIAN, EntityType.GUARDIAN, 0x5a8272, 0xf17d30),
    HOGLIN(Mytems.POCKET_HOGLIN, EntityType.HOGLIN, 0xc66e55, 0x5f6464),
    HORSE(Mytems.POCKET_HORSE, EntityType.HORSE, 0xc09e7d, 0xeee500),
    HUSK(Mytems.POCKET_HUSK, EntityType.HUSK, 0x797061, 0xe6cc94),
    ILLUSIONER(Mytems.POCKET_ILLUSIONER, EntityType.ILLUSIONER, 0x532f36, 0x959b9b),
    IRON_GOLEM(Mytems.POCKET_IRON_GOLEM, EntityType.IRON_GOLEM, 0xdbcdc2, 0x74a332),
    LLAMA(Mytems.POCKET_LLAMA, EntityType.LLAMA, 0xc09e7d, 0x995f40),
    MAGMA_CUBE(Mytems.POCKET_MAGMA_CUBE, EntityType.MAGMA_CUBE, 0x340000, 0xfcfc00),
    MOOSHROOM(Mytems.POCKET_MOOSHROOM, EntityType.MOOSHROOM, 0xa00f10, 0xb7b7b7),
    MULE(Mytems.POCKET_MULE, EntityType.MULE, 0x1b0200, 0x51331d),
    OCELOT(Mytems.POCKET_OCELOT, EntityType.OCELOT, 0xefde7d, 0x564434),
    PANDA(Mytems.POCKET_PANDA, EntityType.PANDA, 0xe7e7e7, 0x1b1b22),
    PARROT(Mytems.POCKET_PARROT, EntityType.PARROT, 0xda70b, 0xff0000),
    PHANTOM(Mytems.POCKET_PHANTOM, EntityType.PHANTOM, 0x43518a, 0x88ff00),
    PIG(Mytems.POCKET_PIG, EntityType.PIG, 0xf0a5a2, 0xdb635f),
    PIGLIN(Mytems.POCKET_PIGLIN, EntityType.PIGLIN, 0x995f40, 0xf9f3a4),
    PIGLIN_BRUTE(Mytems.POCKET_PIGLIN_BRUTE, EntityType.PIGLIN_BRUTE, 0x592a10, 0xf9f3a4),
    PILLAGER(Mytems.POCKET_PILLAGER, EntityType.PILLAGER, 0x532f36, 0x959b9b),
    POLAR_BEAR(Mytems.POCKET_POLAR_BEAR, EntityType.POLAR_BEAR, 0xeeeede, 0xd5d6cd),
    PUFFERFISH(Mytems.POCKET_PUFFERFISH, EntityType.PUFFERFISH, 0xf6b201, 0x37c3f2),
    RABBIT(Mytems.POCKET_RABBIT, EntityType.RABBIT, 0x995f40, 0x734831),
    RAVAGER(Mytems.POCKET_RAVAGER, EntityType.RAVAGER, 0x757470, 0x5b5049),
    SALMON(Mytems.POCKET_SALMON, EntityType.SALMON, 0xa00f10, 0xe8474),
    SHEEP(Mytems.POCKET_SHEEP, EntityType.SHEEP, 0xe7e7e7, 0xffb5b5),
    SHULKER(Mytems.POCKET_SHULKER, EntityType.SHULKER, 0x946794, 0x4d3852),
    SILVERFISH(Mytems.POCKET_SILVERFISH, EntityType.SILVERFISH, 0x6e6e6e, 0x303030),
    SKELETON(Mytems.POCKET_SKELETON, EntityType.SKELETON, 0xc1c1c1, 0x494949),
    SKELETON_HORSE(Mytems.POCKET_SKELETON_HORSE, EntityType.SKELETON_HORSE, 0x68684f, 0xe5e5d8),
    SLIME(Mytems.POCKET_SLIME, EntityType.SLIME, 0x51a03e, 0x7ebf6e),
    SNIFFER(Mytems.POCKET_SNIFFER, EntityType.SNIFFER, 0x871e09, 0x25ab70),
    SNOW_GOLEM(Mytems.POCKET_SNOW_GOLEM, EntityType.SNOW_GOLEM, 0xd9f2f2, 0x81a4a4),
    SPIDER(Mytems.POCKET_SPIDER, EntityType.SPIDER, 0x342d27, 0xa80e0e),
    SQUID(Mytems.POCKET_SQUID, EntityType.SQUID, 0x223b4d, 0x708899),
    STRAY(Mytems.POCKET_STRAY, EntityType.STRAY, 0x617677, 0xddeaea),
    STRIDER(Mytems.POCKET_STRIDER, EntityType.STRIDER, 0x9c3436, 0x4d494d),
    TADPOLE(Mytems.POCKET_TADPOLE, EntityType.TADPOLE, 0x6d533d, 0x160a00),
    TRADER_LLAMA(Mytems.POCKET_TRADER_LLAMA, EntityType.TRADER_LLAMA, 0xeaa430, 0x456296),
    TROPICAL_FISH(Mytems.POCKET_TROPICAL_FISH, EntityType.TROPICAL_FISH, 0xef6915, 0xfff9ef),
    TURTLE(Mytems.POCKET_TURTLE, EntityType.TURTLE, 0xe7e7e7, 0xafaf),
    VEX(Mytems.POCKET_VEX, EntityType.VEX, 0x7a90a4, 0xe8edf1),
    VILLAGER(Mytems.POCKET_VILLAGER, EntityType.VILLAGER, 0x563c33, 0xbd8b72),
    VINDICATOR(Mytems.POCKET_VINDICATOR, EntityType.VINDICATOR, 0x959b9b, 0x275e61),
    WANDERING_TRADER(Mytems.POCKET_WANDERING_TRADER, EntityType.WANDERING_TRADER, 0x456296, 0xeaa430),
    WARDEN(Mytems.POCKET_WARDEN, EntityType.WARDEN, 0xf4649, 0x39d6e0),
    WITCH(Mytems.POCKET_WITCH, EntityType.WITCH, 0x340000, 0x51a03e),
    WITHER(Mytems.POCKET_WITHER, EntityType.WITHER, 0x141414, 0x4d72a0),
    WITHER_SKELETON(Mytems.POCKET_WITHER_SKELETON, EntityType.WITHER_SKELETON, 0x141414, 0x474d4d),
    WOLF(Mytems.POCKET_WOLF, EntityType.WOLF, 0xd7d3d3, 0xceaf96),
    ZOGLIN(Mytems.POCKET_ZOGLIN, EntityType.ZOGLIN, 0xc66e55, 0xe6e6e6),
    ZOMBIE(Mytems.POCKET_ZOMBIE, EntityType.ZOMBIE, 0xafaf, 0x799c65),
    ZOMBIE_HORSE(Mytems.POCKET_ZOMBIE_HORSE, EntityType.ZOMBIE_HORSE, 0x315234, 0x97c284),
    ZOMBIE_VILLAGER(Mytems.POCKET_ZOMBIE_VILLAGER, EntityType.ZOMBIE_VILLAGER, 0x563c33, 0x799c65),
    ZOMBIFIED_PIGLIN(Mytems.POCKET_ZOMBIFIED_PIGLIN, EntityType.ZOMBIFIED_PIGLIN, 0xea9393, 0x4c7129),
    ;

    protected static final Map<Mytems, PocketMobType> MYTEMS_MAP = new EnumMap<>(Mytems.class);

    public final Mytems mytems;
    public final EntityType entityType;
    public final int layer0;
    public final int layer1;

    static {
        for (PocketMobType it : PocketMobType.values()) {
            MYTEMS_MAP.put(it.mytems, it);
        }
    }

    public static PocketMobType of(Mytems mytems) {
        return MYTEMS_MAP.get(mytems);
    }

    public static PocketMobType of(EntityType entityType) {
        for (var it : values()) {
            if (it.entityType == entityType) return it;
        }
        return null;
    }
}
