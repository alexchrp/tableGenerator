package com.alexchrp.tablegenerator;

import com.alexchrp.tablegenerator.aligns.HorizontalAlign;
import com.alexchrp.tablegenerator.aligns.VerticalAlign;
import com.alexchrp.tablegenerator.borders.Borders;
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
    
    private Borders borders = Borders.NONE;

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

    public TableGenerator addRows(String[][] rows) {
        Arrays.stream(rows).forEach(this::addRow);
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



    private List<List<List<String>>> getPreparedLines() {
        List<List<List<String>>> newRows = new ArrayList<>();
        if (!columns.isEmpty()) {
            Row headerRow = createHeaderRow();
            newRows.add(partRow(headerRow));
        }
        rows.stream()
                .map(this::partRow)
                .peek(this::addPrefixesAndPostfixes)
                .forEach(newRows::add);
        return newRows;
    }

    private Row createHeaderRow() {
        return new Row(columns.stream()
                .map(column -> Cell.of(column.getTitle(), column.getHorizontalAlign(),
                        column.getVerticalAlign()))
                .collect(Collectors.toList()));
    }

    private void addPrefixesAndPostfixes(List<List<String>> cells) {
        for (int i = 0; i < cells.size(); i++) {
            List<String> cell = cells.get(i);
            if (columns.size() > i) {
                final Column currentColumn = columns.get(i);
                final String cellPrefix = currentColumn.getPrefix();
                final String cellPostfix = currentColumn.getPostfix();
                for (int j = 0; j < cell.size(); j++) {
                    cell.set(j, addPrefixAndPostfixIfNotEmpty(cellPrefix, cellPostfix, cell.get(j)));
                }
            }
        }
    }

    private List<List<String>> partRow(Row row) {
        int rowHeight = evalRowHeight(row);
        List<Cell> cells = row.getCells();
        List<List<String>> newRow = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            if (cell != null) {
                VerticalAlign cellVerticalAlign = cell.getVerticalAlign();
                VerticalAlign verAlign;
                if (cellVerticalAlign != null) {
                    verAlign = cellVerticalAlign;
                } else if (columns.size() > i && columns.get(i) != null
                        && columns.get(i).getVerticalAlign() != null) {
                    verAlign = columns.get(i).getVerticalAlign();
                } else {
                    verAlign = verticalAlign;

                }
                List<String> strings = verAlign.apply(cell.getText(), rowHeight);
                newRow.add(strings);
            }
        }
        return newRow;
    }

    private String addPrefixAndPostfixIfNotEmpty(String cellPrefix, String cellPostfix,
                                                 String cellText) {
        if (!cellText.trim().isEmpty()) {
            return cellPrefix + cellText + cellPostfix;
        }
        return cellText;
    }

    @Override
    public String toString() {
        int maxCellsCount = getMaxCellsCount();
        fillEmptyCells(maxCellsCount);
        StringBuilder sb = new StringBuilder();
        List<List<List<String>>> partedRows = getPreparedLines();
        int[] colWidths = evalColWidths(partedRows);
        int colsCount = colWidths.length;
        String rowSeparatingString = createRowSeparator(colWidths, colsCount, rowsSeparator);
        String horBorder = createRowSeparator(colWidths, colsCount, borders.getHorizontalBorder());
        if (!columns.isEmpty()) {
            String headerSeparatingString = createRowSeparator(colWidths, colsCount,
                    headerSeparator);
            if (borders != Borders.NONE) {
                sb.append(horBorder);
            }
            List<List<String>> header = partedRows.get(0);
            String headerRow = createRow(maxCellsCount, colWidths, createHeaderRow(), header);
            sb.append(headerRow);
            sb.append(headerSeparatingString);
            partedRows.remove(0);
        } else if (borders != Borders.NONE) {
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
        if (borders != Borders.NONE) {
            sb.append(horBorder);
        }

        return sb.toString();
    }

    private String createRow(int maxCellsCount, int[] colWidths, Row curRow,
                             List<List<String>> partedRow) {
        int colsCount;
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < partedRow.get(0).size(); j++) {
            colsCount = partedRow.size();
            for (int k = 0; k < colsCount; k++) {
                if (borders != Borders.NONE && k == 0) {
                    stringBuilder.append(borders.getVerticalBorder());
                }
                HorizontalAlign cellAlign = curRow.getCells().get(k).getHorizontalAlign();
                HorizontalAlign horAlign;
                if (cellAlign != null) {
                    horAlign = cellAlign;
                } else if (columns.size() > k && columns.get(k) != null
                        && columns.get(k).getHorizontalAlign() != null) {
                    horAlign = columns.get(k).getHorizontalAlign();
                } else {
                    horAlign = horizontalAlign;
                }
                final String cellText = partedRow.get(k).get(j);
                final String textWithAlign = horAlign.apply(cellText, colWidths[k]);
                stringBuilder.append(textWithAlign);
                if (k < maxCellsCount - 1) {
                    stringBuilder.append(columnsSeparator);
                } else if (borders != Borders.NONE) {
                    stringBuilder.append(borders.getVerticalBorder());
                }
            }
            stringBuilder.append(String.format("%n"));
        }
        return stringBuilder.toString();
    }

    private String createRowSeparator(int[] colWidths, int colsCount, String rowsSeparator) {
        StringBuilder sb = new StringBuilder();
        if (!rowsSeparator.isEmpty()) {
            if (borders != Borders.NONE) {
                sb.append("+");
            }
            for (int k = 0; k < colsCount; k++) {
                sb.append(repeatRowsSeparator(colWidths[k], rowsSeparator));
                if (k < colsCount - 1 || borders != Borders.NONE) {
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

    public Borders getBorders() {
        return borders;
    }

    public TableGenerator setBorders(Borders borders) {
        this.borders = borders;
        return this;
    }
}
