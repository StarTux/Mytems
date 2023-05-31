package com.cavetale.mytems;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Mimic the data structure of an mcmeta file in the resource pack.
 *
 * This will be used to synthesize the mcmeta file for the animated
 * texture by CavetaleResourcePack.
 *
 * This also helps to create the animation in chat in a manner
 * consistent with the resource pack.  This can only be done if the
 * Mytems is registered with sufficient characters.
 */
@Value
public final class Animation {
    public final String name;
    public final boolean interpolate;
    public final int width;
    public final int height;
    public final int frametime;
    public final List<Frame> frames;

    @Value @RequiredArgsConstructor
    public static final class Frame {
        public final int index;
        public final int time;

        public Frame(final int index) {
            this(index, 0);
        }
    }

    public static Animation frametime(int frametime) {
        return new Animation("frametime(" + frametime + ")", false, 0, 0, frametime, null);
    }

    public static final Animation NONE = null;

    public static final Animation MAGIC_MAP = new Animation("MAGIC_MAP",
                                                            false, 0, 0, 6, List.of(new Frame(0, 60),
                                                                                    new Frame(1),
                                                                                    new Frame(2),
                                                                                    new Frame(3),
                                                                                    new Frame(4),
                                                                                    new Frame(5),
                                                                                    new Frame(6),
                                                                                    new Frame(7),
                                                                                    new Frame(8),
                                                                                    new Frame(9),
                                                                                    new Frame(10),
                                                                                    new Frame(11),
                                                                                    new Frame(12),
                                                                                    new Frame(13),
                                                                                    new Frame(14),
                                                                                    new Frame(15, 40),
                                                                                    new Frame(14),
                                                                                    new Frame(13),
                                                                                    new Frame(12),
                                                                                    new Frame(11),
                                                                                    new Frame(10),
                                                                                    new Frame(9),
                                                                                    new Frame(8),
                                                                                    new Frame(7),
                                                                                    new Frame(6),
                                                                                    new Frame(5),
                                                                                    new Frame(4),
                                                                                    new Frame(3),
                                                                                    new Frame(2),
                                                                                    new Frame(1)));

    public static final Animation SPINNING_COIN = new Animation("SPINNING_COIN",
                                                                false, 0, 0, 3, List.of(new Frame(0, 4),
                                                                                        new Frame(1),
                                                                                        new Frame(2),
                                                                                        new Frame(3),
                                                                                        new Frame(4),
                                                                                        new Frame(5),
                                                                                        new Frame(6),
                                                                                        new Frame(7)));

    public static final Animation MAGIC_CAPE = new Animation("MAGIC_CAPE",
                                                             false, 0, 0, 0, List.of(new Frame(0, 8),
                                                                                     new Frame(1, 3),
                                                                                     new Frame(2, 2),
                                                                                     new Frame(3, 3),
                                                                                     new Frame(4, 4)));

    public static final Animation DUNE_DIGGER = new Animation("DUNE_DIGGER",
                                                              false, 0, 0, 0, List.of(new Frame(0, 55),
                                                                                      new Frame(1),
                                                                                      new Frame(2),
                                                                                      new Frame(3)));

    public static final Animation SNOWFLAKE = new Animation("SNOWFLAKE",
                                                            false, 0, 0, 1, List.of(new Frame[] {
                // Pause
                new Frame(0, 20),
                new Frame(1, 20),
                new Frame(0, 20),
                new Frame(1, 20),
                // Down
                new Frame(2, 6), new Frame(3, 4), new Frame(4, 2), new Frame(5), new Frame(6, 2), new Frame(7, 4),
                new Frame(8, 10), new Frame(7, 4), new Frame(6, 2), new Frame(5), new Frame(4), new Frame(3), new Frame(2),
                // Pause
                new Frame(0),
                // Up
                new Frame(15), new Frame(14), new Frame(13), new Frame(12), new Frame(11, 2), new Frame(10, 4),
                new Frame(9, 10), new Frame(10, 4), new Frame(11, 2), new Frame(12), new Frame(13, 2), new Frame(14, 4), new Frame(15, 6),
                // Pause
                new Frame(0, 20),
                new Frame(1, 20),
            }));

    public static final Animation CHECKBOX = new Animation("CHECKBOX", false, 0, 0, 4, List.of(new Frame[] {
                new Frame(0),
                new Frame(1),
                new Frame(0),
                new Frame(1),
                new Frame(0),
                new Frame(1),
                new Frame(0),
                new Frame(1),
                new Frame(2),
                new Frame(3),
                new Frame(2),
                new Frame(3),
                new Frame(2),
                new Frame(3),
                new Frame(2),
                new Frame(3),
            }));

    public static final Animation LIGHTNING = new Animation("LIGHTNING", false, 0, 0, 2, List.of(new Frame[] {
                new Frame(0, 100),
                new Frame(1),
                new Frame(2),
                new Frame(3),
                new Frame(4),
                new Frame(5),
                new Frame(6),
                new Frame(7),
                new Frame(8),
                new Frame(9),
                new Frame(10, 1),
                new Frame(11, 1),
                new Frame(12, 1),
                new Frame(13, 1),
                new Frame(14, 1),
                new Frame(15, 1),
                new Frame(16, 1),
                new Frame(17, 1),
            }));

    public static final Animation HOURGLASS = new Animation("HOURGLASS", false, 0, 0, 12, List.of(new Frame[] {
                new Frame(0),
                new Frame(1),
                new Frame(2),
                new Frame(3),
                new Frame(4),
                new Frame(5),
                new Frame(6, 20),
                new Frame(7, 2),
                new Frame(8, 2),
                new Frame(9, 2),
                new Frame(10, 2),
                new Frame(11, 2),
            }));

    public static final Animation HEART = new Animation("HEART", false, 0, 0, 2, List.of(new Frame[] {
                new Frame(0, 80),
                new Frame(1),
                new Frame(0, 1),
                new Frame(2, 4),
                new Frame(0, 8),
                new Frame(1),
                new Frame(0, 1),
                new Frame(2, 4),
            }));

    public static final Animation BUTTERFLY_WINGS = new Animation("BUTTERFLY_WINGS", false, 0, 0, 1, List.of(new Frame[] {
                new Frame(0, 20),
                new Frame(1),
                new Frame(0),
                new Frame(2),
                new Frame(3),
                new Frame(4),
                new Frame(5),
                new Frame(6),
                new Frame(7),
                new Frame(6),
                new Frame(5),
                new Frame(4),
                new Frame(3),
                new Frame(2),
                new Frame(0),
                new Frame(1),
            }));

    public static final Animation BUTTERFLY = new Animation("BUTTERFLY", false, 0, 0, 1, List.of(new Frame[] {
                new Frame(0, 3),
                new Frame(1),
                new Frame(2),
                new Frame(3),
                new Frame(4),
                new Frame(5, 3),
                new Frame(4),
                new Frame(3),
                new Frame(2),
                new Frame(1),
            }));
}
