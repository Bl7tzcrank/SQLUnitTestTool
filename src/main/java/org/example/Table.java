package org.example;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private final String name;
    private List<Column> columns = new ArrayList<>();

    private final List<Object[]> rows = new ArrayList<>();

    public Table(String name) {
        this.name = name;
    }

    //ToDo Null Werte für rows einsetzen, falls column nachträglich dazu
    public void addColumn(String columnName, String columnType){
        this.columns.add(new Column(columnName, columnType));
    }

    public String getName() {
        return name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<Object[]> getRows() {
        return rows;
    }

}
