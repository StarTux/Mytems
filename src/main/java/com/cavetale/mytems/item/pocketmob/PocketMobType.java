package com.cavetale.mytems.item.pocketmob;

import com.cavetale.mytems.Mytems;
import java.util.EnumMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.EntityType;

@RequiredArgsConstructor
public enum PocketMobType {
    ALLAY(Mytems.POCKET_ALLAY, EntityType.ALLAY),
    AXOLOTL(Mytems.POCKET_AXOLOTL, EntityType.AXOLOTL),
    BAT(Mytems.POCKET_BAT, EntityType.BAT),
    BEE(Mytems.POCKET_BEE, EntityType.BEE),
    BLAZE(Mytems.POCKET_BLAZE, EntityType.BLAZE),
    CAT(Mytems.POCKET_CAT, EntityType.CAT),
    CAVE_SPIDER(Mytems.POCKET_CAVE_SPIDER, EntityType.CAVE_SPIDER),
    CHICKEN(Mytems.POCKET_CHICKEN, EntityType.CHICKEN),
    COD(Mytems.POCKET_COD, EntityType.COD),
    COW(Mytems.POCKET_COW, EntityType.COW),
    CREEPER(Mytems.POCKET_CREEPER, EntityType.CREEPER),
    DOLPHIN(Mytems.POCKET_DOLPHIN, EntityType.DOLPHIN),
    DONKEY(Mytems.POCKET_DONKEY, EntityType.DONKEY),
    DROWNED(Mytems.POCKET_DROWNED, EntityType.DROWNED),
    ELDER_GUARDIAN(Mytems.POCKET_ELDER_GUARDIAN, EntityType.ELDER_GUARDIAN),
    ENDERMAN(Mytems.POCKET_ENDERMAN, EntityType.ENDERMAN),
    ENDERMITE(Mytems.POCKET_ENDERMITE, EntityType.ENDERMITE),
    ENDER_DRAGON(Mytems.POCKET_ENDER_DRAGON, EntityType.ENDER_DRAGON),
    EVOKER(Mytems.POCKET_EVOKER, EntityType.EVOKER),
    FOX(Mytems.POCKET_FOX, EntityType.FOX),
    FROG(Mytems.POCKET_FROG, EntityType.FROG),
    GHAST(Mytems.POCKET_GHAST, EntityType.GHAST),
    GIANT(Mytems.POCKET_GIANT, EntityType.GIANT),
    GLOW_SQUID(Mytems.POCKET_GLOW_SQUID, EntityType.GLOW_SQUID),
    GOAT(Mytems.POCKET_GOAT, EntityType.GOAT),
    GUARDIAN(Mytems.POCKET_GUARDIAN, EntityType.GUARDIAN),
    HOGLIN(Mytems.POCKET_HOGLIN, EntityType.HOGLIN),
    HORSE(Mytems.POCKET_HORSE, EntityType.HORSE),
    HUSK(Mytems.POCKET_HUSK, EntityType.HUSK),
    ILLUSIONER(Mytems.POCKET_ILLUSIONER, EntityType.ILLUSIONER),
    IRON_GOLEM(Mytems.POCKET_IRON_GOLEM, EntityType.IRON_GOLEM),
    LLAMA(Mytems.POCKET_LLAMA, EntityType.LLAMA),
    MAGMA_CUBE(Mytems.POCKET_MAGMA_CUBE, EntityType.MAGMA_CUBE),
    MULE(Mytems.POCKET_MULE, EntityType.MULE),
    MUSHROOM_COW(Mytems.POCKET_MUSHROOM_COW, EntityType.MUSHROOM_COW),
    OCELOT(Mytems.POCKET_OCELOT, EntityType.OCELOT),
    PANDA(Mytems.POCKET_PANDA, EntityType.PANDA),
    PARROT(Mytems.POCKET_PARROT, EntityType.PARROT),
    PHANTOM(Mytems.POCKET_PHANTOM, EntityType.PHANTOM),
    PIG(Mytems.POCKET_PIG, EntityType.PIG),
    PIGLIN(Mytems.POCKET_PIGLIN, EntityType.PIGLIN),
    PIGLIN_BRUTE(Mytems.POCKET_PIGLIN_BRUTE, EntityType.PIGLIN_BRUTE),
    PILLAGER(Mytems.POCKET_PILLAGER, EntityType.PILLAGER),
    POLAR_BEAR(Mytems.POCKET_POLAR_BEAR, EntityType.POLAR_BEAR),
    PUFFERFISH(Mytems.POCKET_PUFFERFISH, EntityType.PUFFERFISH),
    RABBIT(Mytems.POCKET_RABBIT, EntityType.RABBIT),
    RAVAGER(Mytems.POCKET_RAVAGER, EntityType.RAVAGER),
    SALMON(Mytems.POCKET_SALMON, EntityType.SALMON),
    SHEEP(Mytems.POCKET_SHEEP, EntityType.SHEEP),
    SHULKER(Mytems.POCKET_SHULKER, EntityType.SHULKER),
    SILVERFISH(Mytems.POCKET_SILVERFISH, EntityType.SILVERFISH),
    SKELETON(Mytems.POCKET_SKELETON, EntityType.SKELETON),
    SKELETON_HORSE(Mytems.POCKET_SKELETON_HORSE, EntityType.SKELETON_HORSE),
    SLIME(Mytems.POCKET_SLIME, EntityType.SLIME),
    SNOWMAN(Mytems.POCKET_SNOWMAN, EntityType.SNOWMAN),
    SPIDER(Mytems.POCKET_SPIDER, EntityType.SPIDER),
    SQUID(Mytems.POCKET_SQUID, EntityType.SQUID),
    STRAY(Mytems.POCKET_STRAY, EntityType.STRAY),
    STRIDER(Mytems.POCKET_STRIDER, EntityType.STRIDER),
    TADPOLE(Mytems.POCKET_TADPOLE, EntityType.TADPOLE),
    TRADER_LLAMA(Mytems.POCKET_TRADER_LLAMA, EntityType.TRADER_LLAMA),
    TROPICAL_FISH(Mytems.POCKET_TROPICAL_FISH, EntityType.TROPICAL_FISH),
    TURTLE(Mytems.POCKET_TURTLE, EntityType.TURTLE),
    VEX(Mytems.POCKET_VEX, EntityType.VEX),
    VILLAGER(Mytems.POCKET_VILLAGER, EntityType.VILLAGER),
    VINDICATOR(Mytems.POCKET_VINDICATOR, EntityType.VINDICATOR),
    WANDERING_TRADER(Mytems.POCKET_WANDERING_TRADER, EntityType.WANDERING_TRADER),
    WARDEN(Mytems.POCKET_WARDEN, EntityType.WARDEN),
    WITCH(Mytems.POCKET_WITCH, EntityType.WITCH),
    WITHER(Mytems.POCKET_WITHER, EntityType.WITHER),
    WITHER_SKELETON(Mytems.POCKET_WITHER_SKELETON, EntityType.WITHER_SKELETON),
    WOLF(Mytems.POCKET_WOLF, EntityType.WOLF),
    ZOGLIN(Mytems.POCKET_ZOGLIN, EntityType.ZOGLIN),
    ZOMBIE(Mytems.POCKET_ZOMBIE, EntityType.ZOMBIE),
    ZOMBIE_HORSE(Mytems.POCKET_ZOMBIE_HORSE, EntityType.ZOMBIE_HORSE),
    ZOMBIE_VILLAGER(Mytems.POCKET_ZOMBIE_VILLAGER, EntityType.ZOMBIE_VILLAGER),
    ZOMBIFIED_PIGLIN(Mytems.POCKET_ZOMBIFIED_PIGLIN, EntityType.ZOMBIFIED_PIGLIN);

    protected static final Map<Mytems, PocketMobType> MYTEMS_MAP = new EnumMap<>(Mytems.class);

    public final Mytems mytems;
    public final EntityType entityType;

    static {
        for (PocketMobType it : PocketMobType.values()) {
            MYTEMS_MAP.put(it.mytems, it);
        }
    }

    public static PocketMobType of(Mytems mytems) {
        return MYTEMS_MAP.get(mytems);
    }
}
