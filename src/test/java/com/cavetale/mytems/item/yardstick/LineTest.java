package com.cavetale.mytems.item.yardstick;

import com.cavetale.core.struct.Vec2i;
import com.cavetale.core.struct.Vec3i;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public final class LineTest {
    @Test
    public void test() {
        testLine2d();
        testLine3d();
    }

    private void testLine2d() {
        assertEquals(List.of(Vec2i.of(10, 10), Vec2i.of(11, 11), Vec2i.of(12, 12)),
                     LineTool.line2d(Vec2i.of(10, 10), Vec2i.of(12, 12)));
        assertEquals(List.of(Vec2i.of(0, 0), Vec2i.of(-1, 0), Vec2i.of(-2, 1), Vec2i.of(-3, 1), Vec2i.of(-4, 2), Vec2i.of(-5, 2)),
                     LineTool.line2d(Vec2i.of(0, 0), Vec2i.of(-5, 2)));
        assertEquals(List.of(Vec2i.of(0, 0), Vec2i.of(1, 1), Vec2i.of(2, 1)),
                     LineTool.line2d(Vec2i.of(0, 0), Vec2i.of(2, 1)));
        assertEquals(List.of(Vec2i.of(0, 0), Vec2i.of(1, -1), Vec2i.of(1, -2)),
                     LineTool.line2d(Vec2i.of(0, 0), Vec2i.of(1, -2)));
    }

    private void testLine3d() {
        assertEquals(List.of(Vec3i.of(10, -10, 10), Vec3i.of(10, -11, 10), Vec3i.of(11, -12, 10), Vec3i.of(11, -13, 10)),
                     LineTool.line3d(Vec3i.of(10, -10, 10), Vec3i.of(11, -13, 10)));
    }
}
