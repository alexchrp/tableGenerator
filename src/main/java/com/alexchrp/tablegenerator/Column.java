package com.alexchrp.tablegenerator;

import com.alexchrp.tablegenerator.aligns.HorizontalAlign;
import com.alexchrp.tablegenerator.aligns.VerticalAlign;

public class Column {

    private String title;

    private HorizontalAlign horizontalAlign;

    private VerticalAlign verticalAlign;

    private String prefix = "";

    private String postfix = "";

    public Column(String title) {
        this.title = title;
    }

    private Column(String title, HorizontalAlign horizontalAlign, VerticalAlign verticalAlign, String prefix, String postfix) {
        this.title = title;
        this.horizontalAlign = horizontalAlign;
        this.verticalAlign = verticalAlign;
        this.prefix = prefix;
        this.postfix = postfix;
    }

    public String getTitle() {
        return title;
    }

    public Column setTitle(String title) {
        this.title = title;
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

    public Column createColumn() {
        return new Column(title, horizontalAlign, verticalAlign, prefix, postfix);
    }
}
