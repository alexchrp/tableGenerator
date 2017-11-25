package com.alexchrp.tablegenerator.aligns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

import static com.alexchrp.tablegenerator.utils.TextUtilities.getEmptyStrings;

public enum VerticalAlign {

    TOP(VerticalAlign::addNewLinesToBottom),
    CENTER(VerticalAlign::addNewLinesToTopAndBottom),
    BOTTOM(VerticalAlign::addNewLinesToTop);

    private BiFunction<String, Integer, List<String>> applyAlign;

    VerticalAlign(BiFunction<String, Integer, List<String>> applyAlign) {
        this.applyAlign = applyAlign;
    }

    public List<String> apply(String string, Integer stringSize) {
        return applyAlign.apply(string, stringSize);
    }

    private static List<String> addNewLinesToTop(String string, int size) {
        List<String> strings = splitString(string);
        List<String> newLines = getEmptyStrings(size - strings.size());
        newLines.addAll(strings);
        return newLines;
    }

    private static List<String> addNewLinesToBottom(String string, int size) {
        List<String> strings = splitString(string);
        List<String> newLines = getEmptyStrings(size - strings.size());
        strings.addAll(newLines);
        return strings;
    }

    private static List<String> addNewLinesToTopAndBottom(String text, Integer size) {
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
