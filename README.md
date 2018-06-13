# TableGenerator
A library for easy creation of plain text tables with flexible style settings. 
## Installation
#### JAR 
You can download the latest .jar file from [GitHub](https://github.com/alexchrp/tableGenerator/raw/master/TableGenerator.jar).
## Examples
#### Simple table
```java
String table = new TableGenerator()
        .setColumns("Header 1", "Header 2", "Header 3")
        .addRow("Test1\n\nTest1", "Test1", "Test1")
        .addRow("Test2 Test2", "Test2\nTest2", "Test3")
        .addRow("Test2 Test2", "Test2\nTest2")
        .addRow("", "Test3  Test3", "Test3\n\nTest3")
        .toString();
System.out.println(table);
```
![Simple table result](https://github.com/alexchrp/tableGenerator/raw/master/media/SimpleTableResult.png)
#### Table with vertical and horizontal alignments 
```java
String table = new TableGenerator()
        .setColumns("Header 1", "Header 2", "Header 3")
        .addRow("Test1\n\nTest1", "Test1", "Test1")
        .addRow("Test2 Test2", "Test2\nTest2", "Test3")
        .addRow("Test2 Test2", "Test2\nTest2")
        .addRow("", "Test3  Test3", "Test3\n\nTest3")
        .setTableStyle(TableStyles.SOLID)
        .setHorizontalAlign(HorizontalAlign.CENTER)
        .setVerticalAlign(VerticalAlign.BOTTOM)
        .toString();
System.out.println(table);
```
![Table with alignments](https://github.com/alexchrp/tableGenerator/raw/master/media/TableWithAligns.png)
#### Table with vertical and horizontal alignments for specific column
```java
Column column2 = new Column("Header 2");
column2.setVerticalAlign(VerticalAlign.TOP);
column2.setHorizontalAlign(HorizontalAlign.RIGHT);
String table = new TableGenerator()
        .setColumns("Header 1", column2, "Header 3")
        .addRow("Test1\n\nTest1", "Test1", "Test1")
        .addRow("Test2 Test2", "Test2\nTest2", "Test3")
        .addRow("Test2 Test2", "Test2\nTest2")
        .addRow("", "Test3  Test3", "Test3\n\nTest3")
        .setTableStyle(TableStyles.SOLID)
        .setHorizontalAlign(HorizontalAlign.CENTER)
        .setVerticalAlign(VerticalAlign.BOTTOM)
        .toString();
System.out.println(table);
```
![Table with alignments for column](https://github.com/alexchrp/tableGenerator/raw/master/media/TableWithAlignsColumn.png)
#### Table with vertical and horizontal alignments for specific cell
```java
Column column2 = new Column("Header 2");
column2.setVerticalAlign(VerticalAlign.TOP);
column2.setHorizontalAlign(HorizontalAlign.RIGHT);
String table = new TableGenerator()
        .setColumns("Header 1", column2, "Header 3")
        .addRow("Test1\n\nTest1", Cell.of("Test1", HorizontalAlign.LEFT, VerticalAlign.CENTER), "Test1")
        .addRow("Test2 Test2", "Test2\nTest2", "Test2")
        .addRow("Test2 Test2", "Test2\nTest2")
        .addRow("", "Test3  Test3", "Test3\n\nTest3")
        .setTableStyle(TableStyles.SOLID)
        .setHorizontalAlign(HorizontalAlign.CENTER)
        .setVerticalAlign(VerticalAlign.BOTTOM)
        .toString();
System.out.println(table);
```
![Table with alignments for cell](https://github.com/alexchrp/tableGenerator/raw/master/media/TableWithAlignsCell.png)
#### Table from two-dimensional array with no outer bounds
```java
String[][] rows = {{"Test1", "Test1", "Test1", "Test1"},
        {"Test2", "Test2", "Test2"},
        {null, "Test3", "Test3", "Test3"},
        {"Test4", "Test4", "Test4", "Test4"}};
String table = new TableGenerator()
        .addRows(rows)
        .setTableStyle(TableStyles.DOUBLE)
        .setPaintBounds(false)
        .toString();
System.out.println(table);
```
![Table from array](https://github.com/alexchrp/tableGenerator/raw/master/media/TableFromArray.png)
#### Table from two-dimensional array with custom style
```java
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
```
![Table from array with custom style](https://github.com/alexchrp/tableGenerator/raw/master/media/TableFromArrayCustomStyle.png)
