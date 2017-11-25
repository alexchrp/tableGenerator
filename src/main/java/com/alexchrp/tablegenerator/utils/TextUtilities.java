package com.alexchrp.tablegenerator.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TextUtilities {

    private TextUtilities() {
    }

    public static List<String> getEmptyStrings(int numOfNewLines) {
        return new ArrayList<>(Collections.nCopies(numOfNewLines, ""));
    }
}
