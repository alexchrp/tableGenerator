package com.alexchrp.tablegenerator.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class containing common methods for working with text
 */
public final class TextUtilities {

    private TextUtilities() {
    }

    /**
     * Creates list of passed size containing only empty strings
     *
     * @param numOfNewLines number of empty strings
     * @return modifiable list of empty strings
     */
    public static List<String> getEmptyStrings(int numOfNewLines) {
        if (numOfNewLines < 1) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Collections.nCopies(numOfNewLines, ""));
    }
}
