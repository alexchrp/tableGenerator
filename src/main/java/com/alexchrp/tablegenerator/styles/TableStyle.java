package com.alexchrp.tablegenerator.styles;

/**
 * Defines elements which will be used for painting a table
 */
public interface TableStyle {

    public String getVerticalLine();

    public String getLeftTopCorner();

    public String getRightTopCorner();

    public String getLeftBottomCorner();

    public String getRightBottomCorner();

    public String getHorizontalLine();

    public String getLeftIntersection();

    public String getRightIntersection();

    public String getTopIntersection();

    public String getBottomIntersection();

    public String getCenterIntersection();

}
