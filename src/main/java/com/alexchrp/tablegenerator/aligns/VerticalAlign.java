package com.alexchrp.tablegenerator.aligns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import static com.alexchrp.tablegenerator.utils.TextUtilities.getEmptyStrings;

/**
 *  The enum to represent vertical align of text
 */
public enum VerticalAlign {

    TOP(VerticalAlign::splitAndAddEmptyLinesToBottom),
    CENTER(VerticalAlign::splitAndAddEmptyLinesToTopAndBottom),
    BOTTOM(VerticalAlign::splitAndAddEmptyLinesToTop);

    private BiFunction<String, Integer, List<String>> applyAlign;

    VerticalAlign(BiFunction<String, Integer, List<String>> applyAlign) {
        this.applyAlign = applyAlign;
    }

    /**
     * Splits the string on lines and adds additional empty lines if needed
     * align defines the position where empty lines will be added
     *
     * @param string string to apply align
     * @param linesHeight height of aligned string
     * @return list of lines in passed strings and additional empty stirngs
     */

    public List<String> apply(String string, Integer linesHeight) {
        return applyAlign.apply(string, linesHeight);
    }

    private static List<String> splitAndAddEmptyLinesToTop(String string, int size) {
        List<String> strings = splitString(string);
        List<String> newLines = getEmptyStrings(size - strings.size());
        newLines.addAll(strings);
        return newLines;
    }

    private static List<String> splitAndAddEmptyLinesToBottom(String string, int size) {
        List<String> strings = splitString(string);
        List<String> newLines = getEmptyStrings(size - strings.size());
        strings.addAll(newLines);
        return strings;
    }

    private static List<String> splitAndAddEmptyLinesToTopAndBottom(String text, Integer size) {
        final List<String> splitString = splitString(text);
        int linesNum = size - splitString.size();
        List<String> strings = getEmptyStrings(linesNum / 2);
        strings.addAll(splitString);
        strings.addAll(getEmptyStrings(linesNum / 2 + linesNum % 2));
        return strings;
    }

    private static List<String> splitString(String string) {
        return new ArrayList<>(Arrays.asList(string.split(System.lineSeparator())));
    }
    
}
