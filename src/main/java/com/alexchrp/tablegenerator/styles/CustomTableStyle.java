package com.alexchrp.tablegenerator.styles;

/**
 * Serves for defining custom table styles
 */

public class CustomTableStyle implements TableStyle {

    private String verticalLine;

    private String horizontalLine;

    private String rightTopCorner;

    private String leftTopCorner;

    private String leftBottomCorner;

    private String rightBottomCorner;

    private String leftIntersection;

    private String rightIntersection;

    private String topIntersection;

    private String bottomIntersection;

    private String centerIntersection;

    CustomTableStyle(String verticalLine, String horizontalLine, String rightTopCorner,
                            String leftTopCorner, String leftBottomCorner, String rightBottomCorner,
                            String leftIntersection, String rightIntersection,
                            String topIntersection, String bottomIntersection,
                            String centerIntersection) {
        this.verticalLine = verticalLine;
        this.horizontalLine = horizontalLine;
        this.rightTopCorner = rightTopCorner;
        this.leftTopCorner = leftTopCorner;
        this.leftBottomCorner = leftBottomCorner;
        this.rightBottomCorner = rightBottomCorner;
        this.leftIntersection = leftIntersection;
        this.rightIntersection = rightIntersection;
        this.topIntersection = topIntersection;
        this.bottomIntersection = bottomIntersection;
        this.centerIntersection = centerIntersection;
    }

    @Override
    public String getVerticalLine() {
        return verticalLine;
    }

    @Override
    public String getHorizontalLine() {
        return horizontalLine;
    }

    @Override
    public String getRightTopCorner() {
        return rightTopCorner;
    }

    @Override
    public String getLeftTopCorner() {
        return leftTopCorner;
    }

    @Override
    public String getLeftBottomCorner() {
        return leftBottomCorner;
    }

    @Override
    public String getRightBottomCorner() {
        return rightBottomCorner;
    }

    @Override
    public String getLeftIntersection() {
        return leftIntersection;
    }

    @Override
    public String getRightIntersection() {
        return rightIntersection;
    }

    @Override
    public String getTopIntersection() {
        return topIntersection;
    }

    @Override
    public String getBottomIntersection() {
        return bottomIntersection;
    }

    @Override
    public String getCenterIntersection() {
        return centerIntersection;
    }
}
