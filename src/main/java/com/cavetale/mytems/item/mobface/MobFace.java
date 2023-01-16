package com.cavetale.mytems.item.mobface;

import com.cavetale.mytems.Mytems;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.EntityType;
import static net.kyori.adventure.text.Component.empty;

/**
 * Mob Face dictionary.
 */
@RequiredArgsConstructor
public enum MobFace {
    COW(Mytems.COW_FACE, EntityType.COW),
    CREEPER(Mytems.CREEPER_FACE, EntityType.CREEPER),
    ENDERMAN(Mytems.ENDERMAN_FACE, EntityType.ENDERMAN),
    GHAST(Mytems.GHAST_FACE, EntityType.GHAST),
    PIG(Mytems.PIG_FACE, EntityType.PIG),
    SHEEP(Mytems.SHEEP_FACE, EntityType.SHEEP),
    SKELETON(Mytems.SKELETON_FACE, EntityType.SKELETON),
    SLIME(Mytems.SLIME_FACE, EntityType.SLIME),
    SPIDER(Mytems.SPIDER_FACE, EntityType.SPIDER),
    SQUID(Mytems.SQUID_FACE, EntityType.SQUID),
    VILLAGER(Mytems.VILLAGER_FACE, EntityType.VILLAGER),
    PILLAGER(Mytems.PILLAGER_FACE, EntityType.PILLAGER),
    WITHER(Mytems.WITHER_FACE, EntityType.WITHER),
    ZOMBIE(Mytems.ZOMBIE_FACE, EntityType.ZOMBIE),
    WITCH(Mytems.WITCH_FACE, EntityType.WITCH),
    BLAZE(Mytems.BLAZE_FACE, EntityType.BLAZE),
    PIGLIN(Mytems.PIGLIN_FACE, EntityType.PIGLIN),
    WARDEN(Mytems.WARDEN_FACE, EntityType.WARDEN),
    HOGLIN(Mytems.HOGLIN_FACE, EntityType.HOGLIN),
    HUSK(Mytems.HUSK_FACE, EntityType.HUSK),
    VEX(Mytems.VEX_FACE, EntityType.VEX),
    GUARDIAN(Mytems.GUARDIAN_FACE, EntityType.GUARDIAN),
    DROWNED(Mytems.DROWNED_FACE, EntityType.DROWNED),
    PHANTOM(Mytems.PHANTOM_FACE, EntityType.PHANTOM),
    MAGMA_CUBE(Mytems.MAGMA_CUBE_FACE, EntityType.MAGMA_CUBE),
    RAVAGER(Mytems.RAVAGER_FACE, EntityType.RAVAGER),
    SILVERFISH(Mytems.SILVERFISH_FACE, EntityType.SILVERFISH),
    ZOGLIN(Mytems.ZOGLIN_FACE, EntityType.ZOGLIN),
    ZOMBIE_VILLAGER(Mytems.ZOMBIE_VILLAGER_FACE, EntityType.ZOMBIE_VILLAGER),
    WITHER_SKELETON(Mytems.WITHER_SKELETON_FACE, EntityType.WITHER_SKELETON),
    ENDER_DRAGON(Mytems.ENDER_DRAGON_FACE, EntityType.ENDER_DRAGON),
    STRAY(Mytems.STRAY_FACE, EntityType.STRAY),
    ;

    public final Mytems mytems;
    public final EntityType entityType;

    public static MobFace of(EntityType entityType) {
        for (var it : values()) {
            if (it.entityType == entityType) return it;
        }
        return null;
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
