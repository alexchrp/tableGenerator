package com.alexchrp.tablegenerator.aligns;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HorizontalAlignTest {

    @org.junit.jupiter.api.Test
    void leftAlignTest() {
        String testString = "testString";
        final int stringSize = 15;
        String leftAlign = HorizontalAlign.LEFT.apply(testString, stringSize);
        assertEquals(stringSize, leftAlign.length());
        final int spacesBeginIndex = testString.length();
        assertEquals("     ", leftAlign.substring(spacesBeginIndex));
    }

    @org.junit.jupiter.api.Test
    void centerAlignTest() {
        String testString = "testString";
        final int stringSize = 15;
        String centerAlign = HorizontalAlign.CENTER.apply(testString, stringSize);
        assertEquals(stringSize, centerAlign.length());

        final int spaceEndIndex = (stringSize - testString.length()) / 2;
        final int spacesBeginIndex = 0;
        assertEquals("  ", centerAlign.substring(spacesBeginIndex, spaceEndIndex));

        final int spacesBeginIndex2 = spaceEndIndex + testString.length();
        assertEquals("   ", centerAlign.substring(spacesBeginIndex2));
    }

    @org.junit.jupiter.api.Test
    void rightAlignTest() {
        String testString = "testString";
        final int stringSize = 15;
        String rightAlign = HorizontalAlign.RIGHT.apply(testString, stringSize);
        assertEquals(stringSize, rightAlign.length());
        final int spacesEndIndex = stringSize - testString.length();
        assertEquals("     ", rightAlign.substring(0, spacesEndIndex));
    }
}