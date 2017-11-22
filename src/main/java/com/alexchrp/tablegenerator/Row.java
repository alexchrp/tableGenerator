package com.alexchrp.tablegenerator;

import com.alexchrp.tablegenerator.aligns.VerticalAlign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Row {

    private final List<Cell> cells;

    public Row(List<Cell> cells) {
        this.cells = cells;
    }

    public Row addCell(String text) {
        cells.add(Cell.of(text));
        return this;
    }

    public Row addCell(Cell cell) {
        cells.add(cell);
        return this;
    }

    public List<Cell> getCells() {
        return Collections.unmodifiableList(cells);
    }

    @Override
    public String toString() {
        return "Row{" +
                "cells=" + cells +
                '}';
    }
}
