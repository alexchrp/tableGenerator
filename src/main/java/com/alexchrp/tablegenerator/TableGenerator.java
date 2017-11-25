package com.alexchrp.tablegenerator;

import com.alexchrp.tablegenerator.aligns.HorizontalAlign;
import com.alexchrp.tablegenerator.aligns.VerticalAlign;
import com.alexchrp.tablegenerator.styles.TableStyle;
import com.alexchrp.tablegenerator.styles.TableStyles;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.alexchrp.tablegenerator.utils.TextUtilities.getEmptyStrings;

public class TableGenerator {

    private List<Row> rows = new ArrayList<>();

    private HorizontalAlign horizontalAlign = HorizontalAlign.LEFT;

    private VerticalAlign verticalAlign = VerticalAlign.TOP;

    private List<Column> columns = new ArrayList<>();

    private TableStyle tableStyle = TableStyles.NONE;

    private boolean paintBounds = true;

    private boolean paintColumnsSeparators = true;

    private boolean paintRowsSeparators = true;

    private int columnsCount = 0;

    public TableGenerator addRow(Object... cells) {
        addRow(Arrays.asList(cells));
        return this;
    }

    public TableGenerator addRow(Row row) {
        rows.add(row);
        if (row.getSize() > columnsCount) {
            columnsCount = row.getSize();
        }
        return this;
    }

    public TableGenerator addRow(List<Object> cells) {
        List<Cell> cellsList = cells.stream()
                .map(obj -> obj instanceof Cell
                        ? (Cell) obj : Cell.of(Objects.toString(obj, "")))
                .collect(Collectors.toList());
        addRow(new Row(cellsList));
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

    public TableStyle getTableStyle() {
        return tableStyle;
    }

    public TableGenerator setTableStyle(TableStyle tableStyle) {
        this.tableStyle = tableStyle;
        return this;
    }

    public boolean isPaintBounds() {
        return paintBounds;
    }

    public TableGenerator setPaintBounds(boolean paintBounds) {
        this.paintBounds = paintBounds;
        return this;
    }

    public boolean isPaintColumnsSeparators() {
        return paintColumnsSeparators;
    }

    public TableGenerator setPaintColumnsSeparators(boolean paintColumnsSeparators) {
        this.paintColumnsSeparators = paintColumnsSeparators;
        return this;
    }

    public boolean isPaintRowsSeparators() {
        return paintRowsSeparators;
    }

    public TableGenerator setPaintRowsSeparators(boolean paintRowsSeparators) {
        this.paintRowsSeparators = paintRowsSeparators;
        return this;
    }

    @Override
    public String toString() {
        equalizeRowsSizes();
        StringBuilder sb = new StringBuilder();
        List<List<List<String>>> preparedLines = getPreparedLines();
        int[] colWidths = evalColWidths(preparedLines);
        if (paintBounds) {
            sb.append(createTopBorder(colWidths));
        }
        final String rowSeparator = createRowSeparator(colWidths);
        if (!columns.isEmpty()) {
            List<List<String>> header = preparedLines.get(0);
            String headerRow = getRowText(createHeaderRow(), header, colWidths);
            sb.append(headerRow);
            sb.append(rowSeparator);
            preparedLines.remove(0);
        }
        for (int i = 0; i < rows.size(); i++) {
            Row curRow = rows.get(i);
            List<List<String>> partedRow = preparedLines.get(i);
            String row = getRowText(curRow, partedRow, colWidths);
            sb.append(row);
            if (i < preparedLines.size() - 1 && paintRowsSeparators) {
                sb.append(rowSeparator);
            }
        }
        if (paintBounds) {
            sb.append(createBottomBorder(colWidths));
        }

        return sb.toString();
    }

    private String createBottomBorder(int[] colWidths) {
        return createHorizontalSeparator(colWidths, tableStyle.getLeftBottomCorner(),
                tableStyle.getHorizontalLine(),
                paintColumnsSeparators ? tableStyle.getBottomIntersection() : "",
                tableStyle.getRightBottomCorner());
    }

    private String createTopBorder(int[] colWidths) {
        return createHorizontalSeparator(colWidths, tableStyle.getLeftTopCorner(),
                tableStyle.getHorizontalLine(),
                paintColumnsSeparators ? tableStyle.getTopIntersection() : "",
                tableStyle.getRightTopCorner());
    }

    private String createRowSeparator(int[] colWidths) {
        return createHorizontalSeparator(colWidths,
                paintBounds ? tableStyle.getLeftIntersection() : "",
                tableStyle.getHorizontalLine(),
                paintColumnsSeparators ? tableStyle.getCenterIntersection() : "",
                paintBounds ? tableStyle.getRightIntersection() : "");
    }

    private String createHorizontalSeparator(int[] colWidths, String left, String horLine,
                                             String center, String right) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(left);
        for (int i = 0; i < colWidths.length; i++) {
            int colWidth = colWidths[i];
            stringBuilder.append(repeatString(horLine, colWidth));
            if (i < colWidths.length - 1) {
                stringBuilder.append(center);
            }
        }
        stringBuilder.append(right);
        if (stringBuilder.length() > 0) {
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    private Row createHeaderRow() {
        return new Row(columns.stream()
                .map(column -> Cell.of(column.getTitle(), column.getHorizontalAlign(),
                        column.getVerticalAlign()))
                .collect(Collectors.toList()));
    }

    private String getRowText(Row row, List<List<String>> partedRow, int[] colWidths) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int j = 0; j < partedRow.get(0).size(); j++) {
            int colsCount = partedRow.size();
            for (int k = 0; k < colsCount; k++) {
                if (k == 0 && paintBounds) {
                    stringBuilder.append(tableStyle.getVerticalLine());
                }
                HorizontalAlign horAlign = getHorizontalAlign(row, k);
                final String cellText = partedRow.get(k).get(j);
                final String textWithAlign = horAlign.apply(cellText, colWidths[k]);
                stringBuilder.append(textWithAlign);
                if (k < colWidths.length - 1) {
                    if (paintColumnsSeparators) {
                        stringBuilder.append(tableStyle.getVerticalLine());
                    }
                } else if (paintBounds) {
                    stringBuilder.append(tableStyle.getVerticalLine());
                }
            }
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    private HorizontalAlign getHorizontalAlign(Row row, int colNum) {
        HorizontalAlign cellAlign = row.getCells().get(colNum).getHorizontalAlign();
        if (cellAlign != null) {
            return cellAlign;
        } else if (columns.size() > colNum && columns.get(colNum) != null
                && columns.get(colNum).getHorizontalAlign() != null) {
            return columns.get(colNum).getHorizontalAlign();
        } else {
            return horizontalAlign;
        }
    }

    private void equalizeRowsSizes() {
        rows.stream()
                .filter(row -> row.getCells().size() < columnsCount)
                .forEach(row -> row.addCells(
                        getEmptyStrings(columnsCount - row.getCells().size())));
    }

    private String repeatString(String string, int newStringLength) {
        if (string.isEmpty()) {
            return "";
        }
        int repeatsCount = newStringLength / string.length();
        int addSymbolsCount = newStringLength % string.length();
        return Stream.generate(() -> string).limit(repeatsCount).collect(Collectors.joining())
                + string.substring(0, addSymbolsCount);
    }

    private int[] evalColWidths(List<List<List<String>>> rows) {
        int[] widths = new int[columnsCount];
        rows.forEach(row -> {
            for (int colNum = 0; colNum < row.size(); colNum++) {
                final int cellWidth = row.get(colNum).stream()
                        .mapToInt(String::length)
                        .max().orElse(0);
                widths[colNum] = Math.max(widths[colNum], cellWidth);
            }
        });
        return widths;
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

    private String addPrefixAndPostfixIfNotEmpty(String prefix, String postfix,
                                                 String text) {
        if (!text.trim().isEmpty()) {
            return prefix + text + postfix;
        }
        return text;
    }

    private List<List<String>> partRow(Row row) {
        int rowHeight = evalRowHeight(row);
        List<Cell> cells = row.getCells();
        List<List<String>> newRow = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            if (cell != null) {
                VerticalAlign verAlign = getVerticalAlign(cell, i);
                List<String> strings = verAlign.apply(cell.getText(), rowHeight);
                newRow.add(strings);
            }
        }
        return newRow;
    }

    private VerticalAlign getVerticalAlign(Cell cell, int columnNumber) {
        VerticalAlign cellVerticalAlign = cell.getVerticalAlign();
        VerticalAlign verAlign;
        if (cellVerticalAlign != null) {
            verAlign = cellVerticalAlign;
        } else if (columns.size() > columnNumber && columns.get(columnNumber) != null
                && columns.get(columnNumber).getVerticalAlign() != null) {
            verAlign = columns.get(columnNumber).getVerticalAlign();
        } else {
            verAlign = verticalAlign;
        }
        return verAlign;
    }

    private int evalRowHeight(Row row) {
        return row.getCells().stream()
                .map(Optional::ofNullable)
                .mapToInt(o
                        -> o.map(Cell::getText)
                        .map(this::getStringHeight)
                        .orElse(0))
                .max()
                .orElse(0);
    }

    private Integer getStringHeight(String string) {
        return string.split(System.lineSeparator()).length;
    }
}
