package com.cavetale.mytems.item.mobface;

import com.cavetale.mytems.Mytems;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.EntityType;
import static net.kyori.adventure.text.Component.empty;

/**
 * Mob Face dictionary.
 */
@RequiredArgsConstructor
public enum MobFace {
    BEE(Mytems.BEE_FACE, EntityType.BEE),
    BLAZE(Mytems.BLAZE_FACE, EntityType.BLAZE),
    BROWN_RABBIT(Mytems.BROWN_RABBIT_FACE, EntityType.RABBIT),
    CHICKEN(Mytems.CHICKEN_FACE, EntityType.CHICKEN),
    COLD_FROG(Mytems.COLD_FROG_FACE, EntityType.FROG),
    COW(Mytems.COW_FACE, EntityType.COW),
    CREAMY_HORSE(Mytems.CREAMY_HORSE_FACE, EntityType.HORSE),
    CREAMY_LLAMA(Mytems.CREAMY_LLAMA_FACE, EntityType.LLAMA),
    CREEPER(Mytems.CREEPER_FACE, EntityType.CREEPER),
    CYAN_AXOLOTL(Mytems.CYAN_AXOLOTL_FACE, EntityType.AXOLOTL),
    DROWNED(Mytems.DROWNED_FACE, EntityType.DROWNED),
    ENDERMAN(Mytems.ENDERMAN_FACE, EntityType.ENDERMAN),
    ENDER_DRAGON(Mytems.ENDER_DRAGON_FACE, EntityType.ENDER_DRAGON),
    FOX(Mytems.FOX_FACE, EntityType.FOX),
    GHAST(Mytems.GHAST_FACE, EntityType.GHAST),
    GOAT(Mytems.GOAT_FACE, EntityType.GOAT),
    GUARDIAN(Mytems.GUARDIAN_FACE, EntityType.GUARDIAN),
    HOGLIN(Mytems.HOGLIN_FACE, EntityType.HOGLIN),
    HUSK(Mytems.HUSK_FACE, EntityType.HUSK),
    MAGMA_CUBE(Mytems.MAGMA_CUBE_FACE, EntityType.MAGMA_CUBE),
    OCELOT(Mytems.OCELOT_FACE, EntityType.OCELOT),
    PANDA(Mytems.PANDA_FACE, EntityType.PANDA),
    PHANTOM(Mytems.PHANTOM_FACE, EntityType.PHANTOM),
    PIG(Mytems.PIG_FACE, EntityType.PIG),
    PIGLIN(Mytems.PIGLIN_FACE, EntityType.PIGLIN),
    PILLAGER(Mytems.PILLAGER_FACE, EntityType.PILLAGER),
    RAVAGER(Mytems.RAVAGER_FACE, EntityType.RAVAGER),
    SHEEP(Mytems.SHEEP_FACE, EntityType.SHEEP),
    SILVERFISH(Mytems.SILVERFISH_FACE, EntityType.SILVERFISH),
    SKELETON(Mytems.SKELETON_FACE, EntityType.SKELETON),
    SLIME(Mytems.SLIME_FACE, EntityType.SLIME),
    SPIDER(Mytems.SPIDER_FACE, EntityType.SPIDER),
    SQUID(Mytems.SQUID_FACE, EntityType.SQUID),
    STRAY(Mytems.STRAY_FACE, EntityType.STRAY),
    STRIDER(Mytems.STRIDER_FACE, EntityType.STRIDER),
    TEMPERATE_FROG(Mytems.TEMPERATE_FROG_FACE, EntityType.FROG),
    VEX(Mytems.VEX_FACE, EntityType.VEX),
    VILLAGER(Mytems.VILLAGER_FACE, EntityType.VILLAGER),
    WARDEN(Mytems.WARDEN_FACE, EntityType.WARDEN),
    WARM_FROG(Mytems.WARM_FROG_FACE, EntityType.FROG),
    WITCH(Mytems.WITCH_FACE, EntityType.WITCH),
    WITHER(Mytems.WITHER_FACE, EntityType.WITHER),
    WITHER_SKELETON(Mytems.WITHER_SKELETON_FACE, EntityType.WITHER_SKELETON),
    WOLF(Mytems.WOLF_FACE, EntityType.WOLF),
    ZOGLIN(Mytems.ZOGLIN_FACE, EntityType.ZOGLIN),
    ZOMBIE(Mytems.ZOMBIE_FACE, EntityType.ZOMBIE),
    ZOMBIE_VILLAGER(Mytems.ZOMBIE_VILLAGER_FACE, EntityType.ZOMBIE_VILLAGER),
    ;

    public final Mytems mytems;
    public final EntityType entityType;

    public static MobFace of(EntityType entityType) {
        for (var it : values()) {
            if (it.entityType == entityType) return it;
        }
        return null;
    }

    public static List<MobFace> allOf(EntityType entityType) {
        List<MobFace> result = new ArrayList<>();
        for (var it : values()) {
            if (it.entityType == entityType) result.add(it);
        }
        return result;
    }

    public static MobFace of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }

    public static ComponentLike toComponent(EntityType entityType) {
        MobFace mobFace = of(entityType);
        return mobFace != null
            ? mobFace.mytems
            : empty();
    }
}
