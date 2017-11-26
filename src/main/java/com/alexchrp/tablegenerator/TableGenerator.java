package com.alexchrp.tablegenerator;

import com.alexchrp.tablegenerator.aligns.HorizontalAlign;
import com.alexchrp.tablegenerator.aligns.VerticalAlign;
import com.alexchrp.tablegenerator.styles.TableStyle;
import com.alexchrp.tablegenerator.styles.TableStyles;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.alexchrp.tablegenerator.utils.TextUtilities.getEmptyStrings;

/**
 * Builder class for generating tables, takes table content and table settings and creates
 * string representation of table
 */
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

    /**
     * Takes objects, creates row from them and adds it to table
     *
     * @param cells objects in row, if object is instance of {@link Cell} it simply will be
     *              added to row, otherwise will be created new {@link Cell} instance with passed
     *              object's toString() method result as a cell's content
     * @return instance on which method was called
     */
    public TableGenerator addRow(Object... cells) {
        addRow(Arrays.asList(cells));
        return this;
    }

    /**
     * Adds passed row to table
     * @param row new row to add
     * @return instance on which method was called
     */
    public TableGenerator addRow(Row row) {
        rows.add(row);
        if (row.getSize() > columnsCount) {
            columnsCount = row.getSize();
        }
        return this;
    }

    /**
     * Takes list of objects, creates row from them and adds it to table
     *
     * @param cells objects in row, if objects in passed list is instance of {@link Cell} they
     *              simply will be added to row, otherwise will be created new {@link Cell}
     *              instance with passed object's toString() method result as a cell's content
     * @return instance on which method was called
     */
    public TableGenerator addRow(List<Object> cells) {
        List<Cell> cellsList = cells.stream()
                .map(obj -> obj instanceof Cell
                        ? (Cell) obj : Cell.of(Objects.toString(obj, "")))
                .collect(Collectors.toList());
        addRow(new Row(cellsList));
        return this;
    }

    /**
     * Takes two-dimensional array, creates rows from it and adds them to table
     *
     * @param rows new rows to add
     * @return instance on which method was called
     */
    public TableGenerator addRows(String[][] rows) {
        Arrays.stream(rows).forEach(this::addRow);
        return this;
    }

    /**
     * Adds columns to table
     *
     * @param columns new columns to add, if objects are instances of {@link Column} it simply
     *                will be added to table, otherwise will be created new {@link Column}
     *                instance with  passed object's toString() method result as a column's header
     * @return instance on which method was called
     */
    public TableGenerator addColumns(Object... columns) {
        addColumns(Arrays.asList(columns));
        return this;

    }

    /**
     * Adds columns to table
     *
     * @param columns new columns to add, if objects are instances of {@link Column} it simply will
     *                be added to table, otherwise will be created new {@link Column} instance
     *                with passed object's toString() method result as a column's header
     * @return instance on which method was called
     */
    public TableGenerator addColumns(List<Object> columns) {
        this.columns.addAll(columns.stream()
                .map(obj -> obj instanceof Column
                        ? (Column) obj : new Column(Objects.toString(obj, "")))
                .collect(Collectors.toList()));
        return this;
    }

    /**
     * Sets columns to table
     *
     * @param columns new columns, if objects are instances of {@link Column} it simply will be
 *                    added to table, otherwise will be created new {@link Column} instance with
     *                passed object's toString() method result as a column's header
     * @return instance on which method was called
     */
    public TableGenerator setColumns(List<Object> columns) {
        this.columns = columns.stream()
                .map(obj -> obj instanceof Column
                        ? (Column) obj : new Column(Objects.toString(obj, "")))
                .collect(Collectors.toList());
        return this;
    }

    /**
     * Sets columns to table
     *
     * @param columns new columns, if objects are instances of {@link Column} it simply will be
     *                    added to table, otherwise will be created new {@link Column} instance with
     *                passed object's toString() method result as a column's header
     * @return instance on which method was called
     */
    public TableGenerator setColumns(Object... columns) {
        setColumns(Arrays.asList(columns));
        return this;
    }

    /**
     * @return horizontal alignment of table
     */
    public HorizontalAlign getHorizontalAlign() {
        return horizontalAlign;
    }

    /**
     * Sets the horizontal alignment of table. Table's alignment has the lowest priority, if cell
     * has it's own alignment it will be applied, otherwise column's alignment will be used
     * and only if column's alignment isn't setted, table's alignment will be used
     * @param horizontalAlign the horizontal alignment of table
     * @return instance on which method was called
     */
    public TableGenerator setHorizontalAlign(HorizontalAlign horizontalAlign) {
        this.horizontalAlign = horizontalAlign;
        return this;
    }


    /**
     * @return vertical alignment of table
     */
    public VerticalAlign getVerticalAlign() {
        return verticalAlign;
    }

    /**
     * Sets the vertical alignment of table. Table's alignment has the lowest priority, if cell
     * has it's own alignment it will be applied, otherwise column's alignment will be used
     * and only if column's alignment isn't setted, table's alignment will be used
     * @param verticalAlign the horizontal alignment of table
     * @return instance on which method was called
     */
    public TableGenerator setVerticalAlign(VerticalAlign verticalAlign) {
        this.verticalAlign = verticalAlign;
        return this;
    }

    /**
     * @return style of table
     */
    public TableStyle getTableStyle() {
        return tableStyle;
    }

    /**
     * Sets the new style of table
     * @param tableStyle new style of table
     * @return instance on which method was called
     */
    public TableGenerator setTableStyle(TableStyle tableStyle) {
        this.tableStyle = tableStyle;
        return this;
    }

    /**
     * @return whether bounds of table will be painted or not
     */
    public boolean isPaintBounds() {
        return paintBounds;
    }

    /**
     * Sets the parameter defining whether bounds of table will be painted or not
     *
     * @return instance on which method was called
     */
    public TableGenerator setPaintBounds(boolean paintBounds) {
        this.paintBounds = paintBounds;
        return this;
    }

    /**
     * @return whether column separators of table will be painted or not
     */
    public boolean isPaintColumnsSeparators() {
        return paintColumnsSeparators;
    }

    /**
     * Sets the parameter defining whether column separators of table will be painted or not
     *
     * @return instance on which method was called
     */
    public TableGenerator setPaintColumnsSeparators(boolean paintColumnsSeparators) {
        this.paintColumnsSeparators = paintColumnsSeparators;
        return this;
    }

    /**
     * @return whether rows separators of table will be painted or not
     */
    public boolean isPaintRowsSeparators() {
        return paintRowsSeparators;
    }

    /**
     * Sets the parameter defining whether rows separators of table will be painted or not
     *
     * @return instance on which method was called
     */
    public TableGenerator setPaintRowsSeparators(boolean paintRowsSeparators) {
        this.paintRowsSeparators = paintRowsSeparators;
        return this;
    }

    /**
     * Creates string representation of table
     * @return table as a string
     */
    @Override
    public String toString() {
        equalizeRowsSizes();
        StringBuilder sb = new StringBuilder();
        List<List<List<String>>> preparedLines = getPreparedTable();
        int[] colWidths = evalColWidths(preparedLines);
        if (paintBounds) {
            sb.append(createTopBorder(colWidths));
        }
        final String rowSeparator = createRowSeparator(colWidths);
        if (!columns.isEmpty()) {
            List<List<String>> header = preparedLines.get(0);
            String headerRow = generateRowText(createHeaderRow(), header, colWidths);
            sb.append(headerRow);
            sb.append(rowSeparator);
            preparedLines.remove(0);
        }
        for (int i = 0; i < rows.size(); i++) {
            Row curRow = rows.get(i);
            List<List<String>> partedRow = preparedLines.get(i);
            String row = generateRowText(curRow, partedRow, colWidths);
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

    /**
     * Creates bottom border string
     *
     * @param colWidths array containing width for each column of table, defines the space between
     *                  column separators
     * @return bottom border string
     */
    private String createBottomBorder(int[] colWidths) {
        return createHorizontalSeparator(colWidths, tableStyle.getLeftBottomCorner(),
                tableStyle.getHorizontalLine(),
                paintColumnsSeparators ? tableStyle.getBottomIntersection() : "",
                tableStyle.getRightBottomCorner());
    }

    /**
     * Creates top border string
     *
     * @param colWidths array containing width for each column of table, defines the space between
     *                  column separators
     * @return top border string
     */
    private String createTopBorder(int[] colWidths) {
        return createHorizontalSeparator(colWidths, tableStyle.getLeftTopCorner(),
                tableStyle.getHorizontalLine(),
                paintColumnsSeparators ? tableStyle.getTopIntersection() : "",
                tableStyle.getRightTopCorner());
    }

    /**
     * Creates rows separator string
     *
     * @param colWidths array containing width for each column of table, defines the space between
     *                  column separators
     * @return rows separator string
     */
    private String createRowSeparator(int[] colWidths) {
        return createHorizontalSeparator(colWidths,
                paintBounds ? tableStyle.getLeftIntersection() : "",
                tableStyle.getHorizontalLine(),
                paintColumnsSeparators ? tableStyle.getCenterIntersection() : "",
                paintBounds ? tableStyle.getRightIntersection() : "");
    }

    /**
     * Creates separator string from passed parts
     *
     * @param colWidths array containing width for each column of table, defines the space between
     *      *           column separators
     * @param left left part of separator
     * @param horLine part between vertical separators
     * @param center central vertical separator
     * @param right right part of separator
     * @return separator string from passed parts
     */
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


    /**
     * Creates row using columns' headers as a cells
     *
     * @return row with columns' headers as a cells
     */
    private Row createHeaderRow() {
        return new Row(columns.stream()
                .map(column -> Cell.of(column.getHeader(), column.getHorizontalAlign(),
                        column.getVerticalAlign()))
                .collect(Collectors.toList()));
    }

    /**
     * Generates full row of table
     *
     * @param row row to generate string from
     * @param partedRow row parted into lines
     * @param colWidths array containing width for each column of table, defines the space between
     *                  column separators
     * @return full row of table
     */
    private String generateRowText(Row row, List<List<String>> partedRow, int[] colWidths) {
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

    /**
     * Finds horizontal align for specific cell in row. Table's alignment has the lowest priority,
     * if cell has it's own alignment it will be applied, otherwise column's alignment will be used
     * and only if column's alignment isn't setted, table's alignment will be used
     *
     * @param row row to find align from
     * @param cellNum cell number to look for
     * @return horizontal align for cell
     */
    private HorizontalAlign getHorizontalAlign(Row row, int cellNum) {
        HorizontalAlign cellAlign = row.getCells().get(cellNum).getHorizontalAlign();
        if (cellAlign != null) {
            return cellAlign;
        } else if (columns.size() > cellNum && columns.get(cellNum) != null
                && columns.get(cellNum).getHorizontalAlign() != null) {
            return columns.get(cellNum).getHorizontalAlign();
        } else {
            return horizontalAlign;
        }
    }

    /**
     * Adds empty cells to table rows if needed to make all rows the same size
     *
     */
    private void equalizeRowsSizes() {
        rows.stream()
                .filter(row -> row.getCells().size() < columnsCount)
                .forEach(row -> row.addCells(
                        getEmptyStrings(columnsCount - row.getCells().size())));
    }

    /**
     * Repeats passed string to make new string with desired size. If the repeated string
     * doesn't fit into the desired length, the last repetition is truncated to the required length
     *
     * @param string repeated string
     * @param newStringLength length of returned string
     * @return string of passed size
     */
    private String repeatString(String string, int newStringLength) {
        if (string.isEmpty()) {
            return "";
        }
        int repeatsCount = newStringLength / string.length();
        int addSymbolsCount = newStringLength % string.length();
        return Stream.generate(() -> string).limit(repeatsCount).collect(Collectors.joining())
                + string.substring(0, addSymbolsCount);
    }

    /**
     * Finds the maximum width of each column
     *
     * @param table table content. Rows -> cells -> lines in cell
     * @return array with maximum widths of each column
     */
    private int[] evalColWidths(List<List<List<String>>> table) {
        int[] widths = new int[columnsCount];
        table.forEach(row -> {
            for (int colNum = 0; colNum < row.size(); colNum++) {
                final int cellWidth = row.get(colNum).stream()
                        .mapToInt(String::length)
                        .max().orElse(0);
                widths[colNum] = Math.max(widths[colNum], cellWidth);
            }
        });
        return widths;
    }

    /**
     * Prepares table content, part cell into lines, applies vertical alignments,
     * adds prefixes and postfixes
     *
     * @return table content. Rows -> cells -> lines in cell
     */
    private List<List<List<String>>> getPreparedTable() {
        List<List<List<String>>> table = new ArrayList<>();
        if (!columns.isEmpty()) {
            Row headerRow = createHeaderRow();
            table.add(partRowAndApplyVertAlign(headerRow));
        }
        rows.stream()
                .map(this::partRowAndApplyVertAlign)
                .peek(this::addPrefixesAndPostfixes)
                .forEach(table::add);
        return table;
    }

    /**
     * Adds columns prefix and postfix to each non-empty line in each cell
     *
     * @param cells row with cells as list of strings
     */
    private void addPrefixesAndPostfixes(List<List<String>> cells) {
        for (int i = 0; i < cells.size(); i++) {
            List<String> cell = cells.get(i);
            if (columns.size() > i) {
                final Column currentColumn = columns.get(i);
                final String cellPrefix = currentColumn.getPrefix();
                final String cellPostfix = currentColumn.getPostfix();
                for (int j = 0; j < cell.size(); j++) {
                    cell.set(j, addPrefixAndPostfixIfNotEmpty(cellPrefix, cellPostfix,
                            cell.get(j)));
                }
            }
        }
    }

    /**
     * Adds prefix and postfix to string if string is not empty
     *
     * @param prefix text to add to beginning of string
     * @param postfix text to add to end of string
     * @param text text to which the prefix and postfix should be added
     * @return text with prefix and postfix or empty string
     */
    private String addPrefixAndPostfixIfNotEmpty(String prefix, String postfix,
                                                 String text) {
        if (!text.trim().isEmpty()) {
            return prefix + text + postfix;
        }
        return text;
    }

    /**
     * Parts cells of row into lines, applies vertical alignments
     *
     * @param row the row to part
     * @return row parted into lines
     */
    private List<List<String>> partRowAndApplyVertAlign(Row row) {
        int rowHeight = findRowHeight(row);
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

    /**
     * Finds vertical align for specific cell in row. Table's alignment has the lowest priority,
     * if cell has it's own alignment it will be applied, otherwise column's alignment will be used
     * and only if column's alignment isn't setted, table's alignment will be used
     *
     * @param cell cell to find align from
     * @param columnNumber column number to look for
     * @return vertical alignment for cell
     */
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

    /**
     * Finds the height of row
     *
     * @param row the row to find height of
     * @return the height of row
     */
    private int findRowHeight(Row row) {
        return row.getCells().stream()
                .map(Optional::ofNullable)
                .mapToInt(o
                        -> o.map(Cell::getText)
                        .map(this::getStringHeight)
                        .orElse(0))
                .max()
                .orElse(0);
    }

    /**
     * Finds the height of string
     *
     * @param string the row to find height of
     * @return the height of string
     */
    private Integer getStringHeight(String string) {
        return string.split(System.lineSeparator()).length;
    }
}
