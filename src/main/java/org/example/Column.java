package org.example;

public class Column {
    private final String name;
    private final String type;

    public Column(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
