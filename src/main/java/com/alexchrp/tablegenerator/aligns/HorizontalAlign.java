package com.alexchrp.tablegenerator.aligns;

import java.util.function.BiFunction;

/**
 *  The enum to represent horizontal align of text
 */

public enum HorizontalAlign {
    LEFT((text, size) -> String.format("%1$-" + size + "s", text)),
    CENTER((text, size) -> {
        int padSize = size - text.length();
        int padStart = text.length() + padSize / 2;
        text = String.format("%" + padStart + "s", text);
        return String.format("%-" + size + "s", text);
    }),
    RIGHT((text, size) -> String.format("%1$" + size + "s", text));

    private BiFunction<String, Integer, String> applyAlign;

    HorizontalAlign(BiFunction<String, Integer, String> applyAlign) {
        this.applyAlign = applyAlign;
    }

    /**
     * Applies align to a string, if string longer than desired size returns the same string
     *
     * @param string the string to which apply the alignment
     * @param stringLength length of aligned string
     * @return aligned string of passed or original length
     */
    public String apply(String string, Integer stringLength) {
        return applyAlign.apply(string, stringLength);
    }
}
