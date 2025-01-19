package org.example;

import java.util.ArrayList;
import java.util.List;

public class TableService {
    private final List<Table> tables = new ArrayList<>();

    public void addTable(Table table){
        this.tables.add(table);
    }

    public List<Table> getTables(){
        return this.tables;
    }
}
