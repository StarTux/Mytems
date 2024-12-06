package com.cavetale.mytems.item.gem;

import com.cavetale.mytems.Mytems;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GemType {
    SQUARE_SHAPED_SAPPHIRE(Mytems.SQUARE_SHAPED_SAPPHIRE, GemShape.SQUARE, GemColor.SAPPHIRE),
    TRIANGLE_SHAPED_SAPPHIRE(Mytems.TRIANGLE_SHAPED_SAPPHIRE, GemShape.TRIANGLE, GemColor.SAPPHIRE),
    STAR_SHAPED_SAPPHIRE(Mytems.STAR_SHAPED_SAPPHIRE, GemShape.STAR, GemColor.SAPPHIRE),
    MOON_SHAPED_SAPPHIRE(Mytems.MOON_SHAPED_SAPPHIRE, GemShape.MOON, GemColor.SAPPHIRE),
    CIRCLE_SHAPED_SAPPHIRE(Mytems.CIRCLE_SHAPED_SAPPHIRE, GemShape.CIRCLE, GemColor.SAPPHIRE),
    LIGHTNING_SHAPED_SAPPHIRE(Mytems.LIGHTNING_SHAPED_SAPPHIRE, GemShape.LIGHTNING, GemColor.SAPPHIRE),
    HEART_SHAPED_SAPPHIRE(Mytems.HEART_SHAPED_SAPPHIRE, GemShape.HEART, GemColor.SAPPHIRE),
    TEAR_SHAPED_SAPPHIRE(Mytems.TEAR_SHAPED_SAPPHIRE, GemShape.TEAR, GemColor.SAPPHIRE),
    SQUARE_SHAPED_OPAL(Mytems.SQUARE_SHAPED_OPAL, GemShape.SQUARE, GemColor.OPAL),
    TRIANGLE_SHAPED_OPAL(Mytems.TRIANGLE_SHAPED_OPAL, GemShape.TRIANGLE, GemColor.OPAL),
    STAR_SHAPED_OPAL(Mytems.STAR_SHAPED_OPAL, GemShape.STAR, GemColor.OPAL),
    MOON_SHAPED_OPAL(Mytems.MOON_SHAPED_OPAL, GemShape.MOON, GemColor.OPAL),
    CIRCLE_SHAPED_OPAL(Mytems.CIRCLE_SHAPED_OPAL, GemShape.CIRCLE, GemColor.OPAL),
    LIGHTNING_SHAPED_OPAL(Mytems.LIGHTNING_SHAPED_OPAL, GemShape.LIGHTNING, GemColor.OPAL),
    HEART_SHAPED_OPAL(Mytems.HEART_SHAPED_OPAL, GemShape.HEART, GemColor.OPAL),
    TEAR_SHAPED_OPAL(Mytems.TEAR_SHAPED_OPAL, GemShape.TEAR, GemColor.OPAL),
    SQUARE_SHAPED_TOPAZ(Mytems.SQUARE_SHAPED_TOPAZ, GemShape.SQUARE, GemColor.TOPAZ),
    TRIANGLE_SHAPED_TOPAZ(Mytems.TRIANGLE_SHAPED_TOPAZ, GemShape.TRIANGLE, GemColor.TOPAZ),
    STAR_SHAPED_TOPAZ(Mytems.STAR_SHAPED_TOPAZ, GemShape.STAR, GemColor.TOPAZ),
    MOON_SHAPED_TOPAZ(Mytems.MOON_SHAPED_TOPAZ, GemShape.MOON, GemColor.TOPAZ),
    CIRCLE_SHAPED_TOPAZ(Mytems.CIRCLE_SHAPED_TOPAZ, GemShape.CIRCLE, GemColor.TOPAZ),
    LIGHTNING_SHAPED_TOPAZ(Mytems.LIGHTNING_SHAPED_TOPAZ, GemShape.LIGHTNING, GemColor.TOPAZ),
    HEART_SHAPED_TOPAZ(Mytems.HEART_SHAPED_TOPAZ, GemShape.HEART, GemColor.TOPAZ),
    TEAR_SHAPED_TOPAZ(Mytems.TEAR_SHAPED_TOPAZ, GemShape.TEAR, GemColor.TOPAZ),
    SQUARE_SHAPED_JADE(Mytems.SQUARE_SHAPED_JADE, GemShape.SQUARE, GemColor.JADE),
    TRIANGLE_SHAPED_JADE(Mytems.TRIANGLE_SHAPED_JADE, GemShape.TRIANGLE, GemColor.JADE),
    STAR_SHAPED_JADE(Mytems.STAR_SHAPED_JADE, GemShape.STAR, GemColor.JADE),
    MOON_SHAPED_JADE(Mytems.MOON_SHAPED_JADE, GemShape.MOON, GemColor.JADE),
    CIRCLE_SHAPED_JADE(Mytems.CIRCLE_SHAPED_JADE, GemShape.CIRCLE, GemColor.JADE),
    LIGHTNING_SHAPED_JADE(Mytems.LIGHTNING_SHAPED_JADE, GemShape.LIGHTNING, GemColor.JADE),
    HEART_SHAPED_JADE(Mytems.HEART_SHAPED_JADE, GemShape.HEART, GemColor.JADE),
    TEAR_SHAPED_JADE(Mytems.TEAR_SHAPED_JADE, GemShape.TEAR, GemColor.JADE),
    SQUARE_SHAPED_RUBY(Mytems.SQUARE_SHAPED_RUBY, GemShape.SQUARE, GemColor.RUBY),
    TRIANGLE_SHAPED_RUBY(Mytems.TRIANGLE_SHAPED_RUBY, GemShape.TRIANGLE, GemColor.RUBY),
    STAR_SHAPED_RUBY(Mytems.STAR_SHAPED_RUBY, GemShape.STAR, GemColor.RUBY),
    MOON_SHAPED_RUBY(Mytems.MOON_SHAPED_RUBY, GemShape.MOON, GemColor.RUBY),
    CIRCLE_SHAPED_RUBY(Mytems.CIRCLE_SHAPED_RUBY, GemShape.CIRCLE, GemColor.RUBY),
    LIGHTNING_SHAPED_RUBY(Mytems.LIGHTNING_SHAPED_RUBY, GemShape.LIGHTNING, GemColor.RUBY),
    HEART_SHAPED_RUBY(Mytems.HEART_SHAPED_RUBY, GemShape.HEART, GemColor.RUBY),
    TEAR_SHAPED_RUBY(Mytems.TEAR_SHAPED_RUBY, GemShape.TEAR, GemColor.RUBY),
    ;

    private final Mytems mytems;
    private final GemShape shape;
    private final GemColor color;

    public static GemType of(Mytems mytems) {
        for (var it : values()) {
            if (it.mytems == mytems) return it;
        }
        return null;
    }

    public static GemType of(GemShape shape, GemColor color) {
        for (var it : values()) {
            if (it.shape == shape && it.color == color) return it;
        }
        return null;
    }
}
