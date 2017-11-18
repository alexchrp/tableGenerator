package com.alexchrp.tablegenerator;

import com.alexchrp.tablegenerator.aligns.HorizontalAlign;
import com.alexchrp.tablegenerator.aligns.VerticalAlign;
import com.alexchrp.tablegenerator.borders.Borders;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableGeneratorTest {

    @Test
    void toStringTest() {
        TableGenerator tb = new TableGenerator();
        String table
                = tb
                .setColumns("Header 1", new Column("Header 2").setPostfix(" p."))
                .addColumns(new Column("Header 3").setHorizontalAlign(HorizontalAlign.RIGHT)
                        .setVerticalAlign(VerticalAlign.BOTTOM).setPrefix("$ "))
                .addRow("Test1\n\nTest1", "Test1", "Test1")
                .addRow("Test2 Test2", "Test2\nTest2", Cell.of("Test2", VerticalAlign.TOP))
                .addRow("Test2 Test2", "Test2\nTest2")
                .addRow("", "Test3  Test3", "Test3Test3\n\n\n\nTest3")
                .addRow("Test3 Test3", "Test3Test3\nTest3")
                .addRow("Test4", Cell.of("Test4", HorizontalAlign.RIGHT), Cell.of("Test4", HorizontalAlign.LEFT))
                .setColumnsSeparator("|")
                .setRowsSeparator("-")
                .setHeaderSeparator("=")
                .setBorders(Borders.NONE)
                .setHorizontalAlign(HorizontalAlign.LEFT)
                .setVerticalAlign(VerticalAlign.CENTER)
                .toString();
        System.out.println(table);
        
    }
}