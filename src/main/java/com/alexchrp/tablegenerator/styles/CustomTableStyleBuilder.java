package com.alexchrp.tablegenerator.styles;

public class CustomTableStyleBuilder {
    private String verticalLine = "";
    private String horizontalLine = "";
    private String rightTopCorner = "";
    private String leftTopCorner = "";
    private String leftBottomCorner = "";
    private String rightBottomCorner = "";
    private String leftIntersection = "";
    private String rightIntersection = "";
    private String topIntersection = "";
    private String bottomIntersection = "";
    private String centerIntersection = "";

    public CustomTableStyleBuilder() {
    }

    public CustomTableStyleBuilder setVerticalLine(String verticalLine) {
        this.verticalLine = verticalLine;
        return this;
    }

    public CustomTableStyleBuilder setHorizontalLine(String horizontalLine) {
        this.horizontalLine = horizontalLine;
        return this;
    }

    public CustomTableStyleBuilder setRightTopCorner(String rightTopCorner) {
        this.rightTopCorner = rightTopCorner;
        return this;
    }

    public CustomTableStyleBuilder setLeftTopCorner(String leftTopCorner) {
        this.leftTopCorner = leftTopCorner;
        return this;
    }

    public CustomTableStyleBuilder setLeftBottomCorner(String leftBottomCorner) {
        this.leftBottomCorner = leftBottomCorner;
        return this;
    }

    public CustomTableStyleBuilder setRightBottomCorner(String rightBottomCorner) {
        this.rightBottomCorner = rightBottomCorner;
        return this;
    }

    public CustomTableStyleBuilder setLeftIntersection(String leftIntersection) {
        this.leftIntersection = leftIntersection;
        return this;
    }

    public CustomTableStyleBuilder setRightIntersection(String rightIntersection) {
        this.rightIntersection = rightIntersection;
        return this;
    }

    public CustomTableStyleBuilder setTopIntersection(String topIntersection) {
        this.topIntersection = topIntersection;
        return this;
    }

    public CustomTableStyleBuilder setBottomIntersection(String bottomIntersection) {
        this.bottomIntersection = bottomIntersection;
        return this;
    }

    public CustomTableStyleBuilder setCenterIntersection(String centerIntersection) {
        this.centerIntersection = centerIntersection;
        return this;
    }

    public CustomTableStyle createCustomTableStyle() {
        return new CustomTableStyle(verticalLine, horizontalLine, rightTopCorner, leftTopCorner,
                leftBottomCorner, rightBottomCorner, leftIntersection, rightIntersection,
                topIntersection, bottomIntersection, centerIntersection);
    }
}