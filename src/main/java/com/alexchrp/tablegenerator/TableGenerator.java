package com.alexchrp.tablegenerator;

import com.alexchrp.tablegenerator.aligns.HorizontalAlign;
import com.alexchrp.tablegenerator.aligns.VerticalAlign;
import com.alexchrp.tablegenerator.utils.TextUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TableGenerator {

    private List<Row> rows = new ArrayList<>();

    private String columnsSeparator = " ";

    private String rowsSeparator = "";

    private String headerSeparator = "";

    private HorizontalAlign horizontalAlign = HorizontalAlign.LEFT;

    private VerticalAlign verticalAlign = VerticalAlign.TOP;

    private List<Column> columns = new ArrayList<>();
    
    private boolean borders = false;

    public TableGenerator addRow(Object... cells) {
        addRow(Arrays.asList(cells));
        return this;
    }

    public TableGenerator addRow(Row row) {
        rows.add(row);
        return this;
    }

    public TableGenerator addRow(List<Object> cells) {
        List<Cell> cellsList = cells.stream()
                .map(obj -> obj instanceof Cell
                        ? (Cell) obj : Cell.of(Objects.toString(obj, "")))
                .collect(Collectors.toList());
        rows.add(new Row(cellsList));
        return this;
    }

    public TableGenerator addColumns(Object... columns) {
        addColumns(Arrays.asList(columns));
        return this;

    }

    public TableGenerator addColumns(List<Object> columns) {
        this.columns.addAll(columns.stream()
                .map(obj -> obj instanceof Column
                        ? (Column) obj : new Column(Objects.toString(obj, "")))
                .collect(Collectors.toList()));
        return this;
    }

    public TableGenerator setColumns(List<Object> columns) {
        this.columns = columns.stream()
                .map(obj -> obj instanceof Column
                        ? (Column) obj : new Column(Objects.toString(obj, "")))
                .collect(Collectors.toList());
        return this;
    }

    public TableGenerator setColumns(Object... columns) {
        setColumns(Arrays.asList(columns));
        return this;
    }

    private int[] evalColWidths(List<List<List<String>>> rows) {
        int cols = rows.stream().mapToInt(List::size).max().orElse(0);
        int[] widths = new int[cols];
        rows.forEach(row -> {
            for (int colNum = 0; colNum < row.size(); colNum++) {
                widths[colNum] = Math.max(widths[colNum], row.get(colNum).stream()
                        .mapToInt(String::length)
                        .max().orElse(0));
            }
        });

        return widths;
    }

    private int evalRowHeight(Row row) {
        return row.getCells().stream()
                .map(Optional::ofNullable)
                .mapToInt(o
                        -> o.map(Cell::getText)
                        .map(TextUtils::getStringHeight)
                        .orElse(0))
                .max()
                .orElse(0);
    }

    private List<List<List<String>>> applyVerticalAligns() {
        List<List<List<String>>> newRows = new ArrayList<>();
        if (!columns.isEmpty()) {
            Row headerRow = createHeaderRow();
            newRows.add(partRow(headerRow));
        }
        rows.stream()
                .map(this::partRow)
                .forEach(newRows::add);
        return newRows;
    }

    private Row createHeaderRow() {
        return new Row(columns.stream()
                .map(column -> Cell.of(column.getTitle(), column.getHorizontalAlign(), column.getVerticalAlign()))
                .collect(Collectors.toList()));
    }

    private List<List<String>> partRow(Row row) {
        int rowHeight = evalRowHeight(row);
        List<Cell> cells = row.getCells();
        List<List<String>> newRow = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            if (cell != null) {
                VerticalAlign cellVerticalAlign = cell.getVerticalAlign();
                VerticalAlign columnVerticalAlign = columns.get(i).getVerticalAlign();
                VerticalAlign verAlign;
                if (cellVerticalAlign != null) {
                    verAlign = cellVerticalAlign;
                } else if (columnVerticalAlign != null) {
                    verAlign = columnVerticalAlign;
                } else {
                    verAlign = verticalAlign;
                }
                List<String> strings = verAlign.apply(cell.getText(), rowHeight);
                newRow.add(strings);
            }
        }
        return newRow;
    }

    @Override
    public String toString() {
        int maxCellsCount = getMaxCellsCount();
        fillEmptyCells(maxCellsCount);
        StringBuilder sb = new StringBuilder();
        List<List<List<String>>> partedRows = applyVerticalAligns();
        int[] colWidths = evalColWidths(partedRows);
        int colsCount = colWidths.length;
        String rowSeparatingString = createRowSeparator(colWidths, colsCount, rowsSeparator);
        String horBorder = createRowSeparator(colWidths, colsCount, rowsSeparator);
        if (!columns.isEmpty()) {
            String headerSeparatingString = createRowSeparator(colWidths, colsCount, headerSeparator);
            if (borders) {
                sb.append(horBorder);
            }
            List<List<String>> header = partedRows.get(0);
            String headerRow = createRow(maxCellsCount, colWidths, createHeaderRow(), header);
            sb.append(headerRow);
            sb.append(headerSeparatingString);
            partedRows.remove(0);
        } else if (borders) {
            sb.append(horBorder);
        }
        for (int i = 0; i < this.rows.size(); i++) {
            Row curRow = this.rows.get(i);
            List<List<String>> partedRow = partedRows.get(i);
            String row = createRow(maxCellsCount, colWidths, curRow, partedRow);
            sb.append(row);
            if (i < partedRows.size() - 1 && !rowsSeparator.isEmpty()) {
                sb.append(rowSeparatingString);
            }
        }
        if (borders) {
            sb.append(horBorder);
        }

        return sb.toString();
    }

    private String createRow(int maxCellsCount, int[] colWidths, Row curRow, List<List<String>> partedRow) {
        int colsCount;
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < partedRow.get(0).size(); j++) {
            colsCount = partedRow.size();
            for (int k = 0; k < colsCount; k++) {
                if (borders && k == 0) {
                    stringBuilder.append(columnsSeparator);
                }
                HorizontalAlign columnAlign = columns.get(k).getHorizontalAlign();
                HorizontalAlign cellAlign = curRow.getCells().get(k).getHorizontalAlign();
                HorizontalAlign horAlign;
                if (cellAlign != null) {
                    horAlign = cellAlign;
                } else if (columnAlign != null) {
                    horAlign = columnAlign;
                } else {
                    horAlign = horizontalAlign;
                }
                String string = partedRow.get(k).get(j);
                stringBuilder.append(horAlign.apply(string, colWidths[k]));
                if (k < maxCellsCount - 1) {
                    stringBuilder.append(columnsSeparator);
                } else if (borders) {
                    stringBuilder.append(columnsSeparator);
                }
            }
            stringBuilder.append(String.format("%n"));
        }
        return stringBuilder.toString();
    }

    private String createRowSeparator(int[] colWidths, int colsCount, String rowsSeparator) {
        StringBuilder sb = new StringBuilder();
        if (!rowsSeparator.isEmpty()) {
            if (borders) {
                sb.append("+");
            }
            for (int k = 0; k < colsCount; k++) {
                sb.append(repeatRowsSeparator(colWidths[k], rowsSeparator));
                if (k < colsCount - 1 || borders) {
                    sb.append("+");
                }
            }
            sb.append(String.format("%n"));
        }
        return sb.toString();
    }

    private void fillEmptyCells(int maxCellsCount) {
        rows.stream()
                .filter(row -> row.getCells().size() < maxCellsCount)
                .forEach(row -> IntStream.range(0, maxCellsCount - row.getCells().size())
                        .forEach(i -> row.addCell("")));
    }

    private int getMaxCellsCount() {
        return rows.stream()
                .mapToInt(row -> row.getCells().size())
                .max().orElse(0);
    }

    private String repeatRowsSeparator(int sumColsWidth, String rowsSeparator) {
        if (rowsSeparator.isEmpty()) {
            return "";
        }
        int count = sumColsWidth / rowsSeparator.length();
        int addSymbsCount = sumColsWidth % rowsSeparator.length();
        return String.join("", Collections.nCopies(count, rowsSeparator))
                + rowsSeparator.substring(0, addSymbsCount);
    }

    public String getColumnsSeparator() {
        return columnsSeparator;
    }

    public TableGenerator setColumnsSeparator(String columnsSeparator) {
        this.columnsSeparator = columnsSeparator;
        return this;
    }

    public String getRowsSeparator() {
        return rowsSeparator;
    }

    public TableGenerator setRowsSeparator(String rowsSeparator) {
        this.rowsSeparator = rowsSeparator;
        return this;
    }

    public String getHeaderSeparator() {
        return headerSeparator;
    }

    public TableGenerator setHeaderSeparator(String headerSeparator) {
        this.headerSeparator = headerSeparator;
        return this;
    }

    public HorizontalAlign getHorizontalAlign() {
        return horizontalAlign;
    }

    public TableGenerator setHorizontalAlign(HorizontalAlign horizontalAlign) {
        this.horizontalAlign = horizontalAlign;
        return this;
    }

    public VerticalAlign getVerticalAlign() {
        return verticalAlign;
    }

    public TableGenerator setVerticalAlign(VerticalAlign verticalAlign) {
        this.verticalAlign = verticalAlign;
        return this;
    }

    public boolean isBorders() {
        return borders;
    }

    public TableGenerator setBorders(boolean borders) {
        this.borders = borders;
        return this;
    }
}
