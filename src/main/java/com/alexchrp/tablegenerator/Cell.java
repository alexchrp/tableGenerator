package com.alexchrp.tablegenerator;

public class Cell {

    public static Cell of(String text) {
        return new Cell(text);
    }
    
    private final String text;

    private Cell(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "text='" + text + '\'' +
                '}';
    }
}
