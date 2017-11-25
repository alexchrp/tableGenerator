package com.alexchrp.tablegenerator;

import com.alexchrp.tablegenerator.aligns.VerticalAlign;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public Row addCells(Collection<String> newCells) {
        cells.addAll(newCells.stream()
                .map(Cell::of)
                .collect(Collectors.toList()));
        return this;
    }

    public List<Cell> getCells() {
        return Collections.unmodifiableList(cells);
    }

    public int getSize() {
        return cells.size();
    }

    @Override
    public String toString() {
        return "Row{" +
                "cells=" + cells +
                '}';
    }
}
