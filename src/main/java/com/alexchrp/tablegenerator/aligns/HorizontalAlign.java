package com.alexchrp.tablegenerator.aligns;

import java.util.function.BiFunction;

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

    public String apply(String string, Integer stringSize) {
        return applyAlign.apply(string, stringSize);
    }
}
