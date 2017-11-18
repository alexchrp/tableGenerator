package com.alexchrp.tablegenerator.borders;

public enum Borders {

    NONE("", ""), SIMPLE("-", "|"), BOLD("━", "┃"), DOUBLE("=", "||"), SOLID("─", "│");

    Borders(String horizontalBorder, String verticalBorder) {
        this.horizontalBorder = horizontalBorder;
        this.verticalBorder = verticalBorder;
    }

    private String verticalBorder;

    private String horizontalBorder;

    public String getVerticalBorder() {
        return verticalBorder;
    }

    public String getHorizontalBorder() {
        return horizontalBorder;
    }

}
