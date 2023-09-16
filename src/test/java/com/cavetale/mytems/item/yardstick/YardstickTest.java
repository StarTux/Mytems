package com.cavetale.mytems.item.yardstick;

import com.cavetale.core.struct.Vec2i;
import java.util.List;
import org.junit.Test;

public final class YardstickTest {
    @Test
    public void test() {
        // testLines();
    }

    private void testLines() {
        testLine(Vec2i.of(0, 0), Vec2i.of(3, 3));
        testLine(Vec2i.of(0, 0), Vec2i.of(-3, 0));
        testLine(Vec2i.of(5, -5), Vec2i.of(-5, 5));
    }

    private void testLine(Vec2i a, Vec2i b) {
        List<Vec2i> line = Yardstick.makeLine(a, b);
        System.out.println("Line (" + a + ")-(" + b + ") " + line);
        assert a.equals(line.get(0));
        assert b.equals(line.get(line.size() - 1));
    }
}
