package com.alexchrp.tablegenerator.aligns;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HorizontalAlignTest {

    @org.junit.jupiter.api.Test
    void leftAlignTest() {
        String testString = "testString";
        int stringSize = 15;
        String leftAlign = HorizontalAlign.LEFT.apply(testString, stringSize);
        assertEquals(stringSize, leftAlign.length());
        assertEquals("     ", leftAlign.substring(testString.length()));
    }

    @org.junit.jupiter.api.Test
    void centerAlignTest() {
        String testString = "testString";
        int stringSize = 15;
        String centerAlign = HorizontalAlign.CENTER.apply(testString, stringSize);
        assertEquals(stringSize, centerAlign.length());
        assertEquals("  ", centerAlign.substring(0, (stringSize - testString.length()) / 2));
        assertEquals("   ",
                centerAlign.substring((stringSize - testString.length()) / 2 + testString.length()));
    }

    @org.junit.jupiter.api.Test
    void rightAlignTest() {
        String testString = "testString";
        int stringSize = 15;
        String rightAlign = HorizontalAlign.RIGHT.apply(testString, stringSize);
        assertEquals(stringSize, rightAlign.length());
        assertEquals("     ", rightAlign.substring(0, stringSize - testString.length()));
    }
}