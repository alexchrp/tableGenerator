package com.alexchrp.tablegenerator;

import com.alexchrp.tablegenerator.aligns.HorizontalAlign;
import com.alexchrp.tablegenerator.aligns.VerticalAlign;

/**
 * Represents one cell with text in table which may contain multiple lines of text
 */
public class Cell {

    public static Cell of(String text) {
        return new Cell(text);
    }

    public static Cell of(String text, HorizontalAlign horizontalAlign) {
        return new Cell(text, horizontalAlign);
    }

    public static Cell of(String text, VerticalAlign verticalAlign) {
        return new Cell(text, verticalAlign);
    }

    public static Cell of(String text, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign) {
        return new Cell(text, horizontalAlign, verticalAlign);
    }

    private final String text;

    private final HorizontalAlign horizontalAlign;

    private final VerticalAlign verticalAlign;

    private Cell(String text) {
        this.text = text;
        this.horizontalAlign = null;
        this.verticalAlign = null;
    }

    private Cell(String text, HorizontalAlign horizontalAlign) {
        this.text = text;
        this.horizontalAlign = horizontalAlign;
        this.verticalAlign = null;
    }

    private Cell(String text, VerticalAlign verticalAlign) {
        this.text = text;
        this.verticalAlign = verticalAlign;
        this.horizontalAlign = null;
    }

    private Cell(String text, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign) {
        this.text = text;
        this.horizontalAlign = horizontalAlign;
        this.verticalAlign = verticalAlign;
    }

    public HorizontalAlign getHorizontalAlign() {
        return horizontalAlign;
    }

    public VerticalAlign getVerticalAlign() {
        return verticalAlign;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "text='" + text + '\'' +
                ", horizontalAlign=" + horizontalAlign +
                ", verticalAlign=" + verticalAlign +
                '}';
    }
}
