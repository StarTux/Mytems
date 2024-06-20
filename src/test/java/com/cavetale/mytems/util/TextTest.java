package com.cavetale.mytems.util;

import org.junit.Assert;
import org.junit.Test;

public final class TextTest {
    @Test
    public void testRoman() {
        Assert.assertEquals("MCCCLVII", Text.roman(1357));
        Assert.assertEquals("XII", Text.roman(12));
        Assert.assertEquals("DCCLIX", Text.roman(759));
    }
}
