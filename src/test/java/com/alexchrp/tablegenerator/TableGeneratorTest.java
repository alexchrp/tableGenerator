package com.alexchrp.tablegenerator;

import com.alexchrp.tablegenerator.aligns.HorizontalAlign;
import com.alexchrp.tablegenerator.aligns.VerticalAlign;
import com.alexchrp.tablegenerator.styles.CustomTableStyleBuilder;
import com.alexchrp.tablegenerator.styles.TableStyle;
import com.alexchrp.tablegenerator.styles.TableStyles;
import org.junit.jupiter.api.Test;

class TableGeneratorTest {

    @Test
    void toStringTest() {
        TableGenerator tb = new TableGenerator();
        final Column column3 = new Column("Header 3").setHorizontalAlign(HorizontalAlign.RIGHT)
                .setVerticalAlign(VerticalAlign.BOTTOM).setPrefix("$");
        final Column column2 = new Column("Header 2").setPostfix(" p.");
        String table = tb
                .setColumns("Header 1", column2, column3)
                .addRow("Test1\n\nTest1", "Test1", "Test1")
                .addRow("Test2 Test2", "Test2\nTest2", Cell.of("Test2", VerticalAlign.TOP))
                .addRow("Test2 Test2", "Test2\nTest2")
                .addRow("", "Test3  Test3", "Test3Test3\n\n\n\nTest3")
                .addRow("Test3 Test3", "Test3Test3\nTest3")
                .addRow("Test4", Cell.of("Test4", HorizontalAlign.RIGHT),
                        Cell.of("Test4", HorizontalAlign.LEFT))
                .setTableStyle(TableStyles.DOUBLE)
                .setHorizontalAlign(HorizontalAlign.LEFT)
                .setVerticalAlign(VerticalAlign.CENTER)
                .toString();
        System.out.println(table);
    }

    @Test
    void twoDimensionalArrayTest() {
        String[][] rows = {{"Test1", "Test1", "Test1", "Test1"},
                {"Test2", "Test2", "Test2"},
                {null, "Test3", "Test3", "Test3"},
                {"Test4", "Test4", "Test4", "Test4"}};
        TableStyle customStyle = new CustomTableStyleBuilder()
                .setVerticalLine("   ")
                .setHorizontalLine(" ")
                .createCustomTableStyle();
        String table = new TableGenerator()
                .addRows(rows)
                .setTableStyle(customStyle)
                .setPaintBounds(false)
                .toString();
        System.out.println(table);

    }
}