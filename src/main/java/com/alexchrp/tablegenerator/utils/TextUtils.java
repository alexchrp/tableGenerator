package com.alexchrp.tablegenerator.utils;

public final class TextUtils {

    private TextUtils() {
    }

    public static Integer getStringHeight(String string) {
        return string.split(String.format("%n")).length;
    }

}
