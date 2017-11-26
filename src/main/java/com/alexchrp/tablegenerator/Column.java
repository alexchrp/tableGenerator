package com.alexchrp.tablegenerator;

import com.alexchrp.tablegenerator.aligns.HorizontalAlign;
import com.alexchrp.tablegenerator.aligns.VerticalAlign;

/**
 * Represents column of table, defines preferences which can be applied to a column,
 * doesn't content actual content of table
 */
public class Column {

    private String header;

    private HorizontalAlign horizontalAlign;

    private VerticalAlign verticalAlign;

    // prefix which will be added to every line in column
    private String prefix = "";

    // postfix which will be added to every line in column
    private String postfix = "";

    public Column(String header) {
        this.header = header;
    }

    private Column(String header, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign, String prefix, String postfix) {
        this.header = header;
        this.horizontalAlign = horizontalAlign;
        this.verticalAlign = verticalAlign;
        this.prefix = prefix;
        this.postfix = postfix;
    }

    public String getHeader() {
        return header;
    }

    public Column setHeader(String header) {
        this.header = header;
        return this;
    }

    public HorizontalAlign getHorizontalAlign() {
        return horizontalAlign;
    }

    public Column setHorizontalAlign(HorizontalAlign horizontalAlign) {
        this.horizontalAlign = horizontalAlign;
        return this;
    }

    public VerticalAlign getVerticalAlign() {
        return verticalAlign;
    }

    public Column setVerticalAlign(VerticalAlign verticalAlign) {
        this.verticalAlign = verticalAlign;
        return this;
    }

    public String getPrefix() {
        return prefix;
    }

    public Column setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public String getPostfix() {
        return postfix;
    }

    public Column setPostfix(String postfix) {
        this.postfix = postfix;
        return this;
    }
}
