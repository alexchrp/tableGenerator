package com.alexchrp.tablegenerator.aligns;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HorizontalAlignTest {
    
    private static final String TEST_STRING = "testString";

    private static final int STRING_SIZE = 15;
    
    private static String leftAlign = HorizontalAlign.LEFT.apply(TEST_STRING, STRING_SIZE);
    private static String centerAlign = HorizontalAlign.CENTER.apply(TEST_STRING, STRING_SIZE);
    private static String rightAlign = HorizontalAlign.RIGHT.apply(TEST_STRING, STRING_SIZE);
    
    @Test
    void leftAlignLengthTest() {
        assertEquals(STRING_SIZE, leftAlign.length());
    }

    @Test
    void leftAlignSpacesCountTest() {
        final int spacesBeginIndex = TEST_STRING.length();
        assertEquals("     ", leftAlign.substring(spacesBeginIndex));
    }
    
    @Test
    void centerAlignLengthTest() {
        assertEquals(STRING_SIZE, centerAlign.length());
    }

    @Test
    void centerAlignStartSpacesCountTest() {
        final int spaceEndIndex = (STRING_SIZE - TEST_STRING.length()) / 2;
        final int spacesBeginIndex = spaceEndIndex + TEST_STRING.length();
        assertEquals("   ", centerAlign.substring(spacesBeginIndex));
    }

    @Test
    void centerAlignEndSpacesCountTest() {
        final int spaceEndIndex = (STRING_SIZE - TEST_STRING.length()) / 2;
        final int spacesBeginIndex = 0;
        assertEquals("  ", centerAlign.substring(spacesBeginIndex, spaceEndIndex));
    }

    @Test
    void rightAlignLengthTest() {
        assertEquals(STRING_SIZE, rightAlign.length());
    }

    @Test
    void centerAlignSpacesCountTest() {
        final int spacesEndIndex = STRING_SIZE - TEST_STRING.length();
        assertEquals("     ", rightAlign.substring(0, spacesEndIndex));
    }

    @Test
    void leftAlignEmptyStringTest() {
        final String leftAlign = HorizontalAlign.LEFT.apply("", STRING_SIZE);
        assertEquals(15, leftAlign.length());
    }

    @Test
    void centerAlignEmptyStringTest() {
        final String centerAlign = HorizontalAlign.CENTER.apply("", STRING_SIZE);
        assertEquals(15, centerAlign.length());
    }

    @Test
    void rightAlignEmptyStringTest() {
        final String rightAlign = HorizontalAlign.RIGHT.apply("", STRING_SIZE);
        assertEquals(15, rightAlign.length());
    }

    @Test
    void leftAlignStringLongerThanAlignTest() {
        final String testString = "testStringTestString";
        final String leftAlign = HorizontalAlign.LEFT.apply(testString, STRING_SIZE);
        assertEquals(testString, leftAlign);
    }

    @Test
    void centerAlignStringLongerThanAlignTest() {
        final String testString = "testStringTestString";
        final String centerAlign = HorizontalAlign.CENTER.apply(testString, STRING_SIZE);
        assertEquals(testString, centerAlign);
    }

    @Test
    void rightAlignStringLongerThanAlignTest() {
        final String testString = "testStringTestString";
        final String rightAlign = HorizontalAlign.RIGHT.apply(testString, STRING_SIZE);
        assertEquals(testString, rightAlign);
    }

    @Test
    void leftAlignEmptyStringZeroLengthTest() {
        final String leftAlign = HorizontalAlign.LEFT.apply("", 0);
        assertEquals(0, leftAlign.length());
    }

    @Test
    void centerAlignEmptyStringZeroLengthTest() {
        final String centerAlign = HorizontalAlign.CENTER.apply("", 0);
        assertEquals(0, centerAlign.length());
    }

    @Test
    void rightAlignEmptyStringZeroLengthTest() {
        final String rightAlign = HorizontalAlign.RIGHT.apply("", 0);
        assertEquals(0, rightAlign.length());
    }
}