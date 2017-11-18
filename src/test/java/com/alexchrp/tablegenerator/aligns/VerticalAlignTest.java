package com.alexchrp.tablegenerator.aligns;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VerticalAlignTest {

    @Test
    void applyTopAlign() {
        final String line1 = "test";
        final String line2 = "String";
        String testString = line1 + System.lineSeparator() + line2;
        int stringHeight = 5;
        List<String> alignTop = VerticalAlign.TOP.apply(testString, stringHeight);
        assertEquals(stringHeight, alignTop.size());
        assertEquals(line1, alignTop.get(0));
        assertEquals(line2, alignTop.get(1));
        assertEquals("", alignTop.get(2));
        assertEquals("", alignTop.get(3));
        assertEquals("", alignTop.get(4));
    }

    @Test
    void applyCenterAlign() {
        final String line1 = "test";
        final String line2 = "String";
        String testString = line1 + System.lineSeparator() + line2;
        int stringHeight = 5;
        List<String> alignCenter = VerticalAlign.CENTER.apply(testString, stringHeight);
        assertEquals(stringHeight, alignCenter.size());
        assertEquals(line1, alignCenter.get(1));
        assertEquals(line2, alignCenter.get(2));
        assertEquals("", alignCenter.get(0));
        assertEquals("", alignCenter.get(3));
        assertEquals("", alignCenter.get(4));
    }

    @Test
    void applyBottomAlign() {
        final String line1 = "test";
        final String line2 = "String";
        String testString = line1 + System.lineSeparator() + line2;
        int stringHeight = 5;
        List<String> alignBottom = VerticalAlign.BOTTOM.apply(testString, stringHeight);
        assertEquals(stringHeight, alignBottom.size());
        assertEquals(line1, alignBottom.get(3));
        assertEquals(line2, alignBottom.get(4));
        assertEquals("", alignBottom.get(0));
        assertEquals("", alignBottom.get(1));
        assertEquals("", alignBottom.get(2));
    }
}