package com.alexchrp.tablegenerator;

import com.alexchrp.tablegenerator.aligns.HorizontalAlign;

public class Cell {

    public static Cell of(String text) {
        return new Cell(text);
    }

    public static Cell of(String text, HorizontalAlign horizontalAlign) {
        return new Cell(text, horizontalAlign);
    }

    private final String text;

    private final HorizontalAlign horizontalAlign;

    private Cell(String text) {
        this.text = text;
        this.horizontalAlign = null;
    }

    private Cell(String text, HorizontalAlign horizontalAlign) {
        this.text = text;
        this.horizontalAlign = horizontalAlign;
    }

    public HorizontalAlign getHorizontalAlign() {
        return horizontalAlign;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "text='" + text + '\'' +
                ", horizontalAlign=" + horizontalAlign +
                '}';
    }
}
