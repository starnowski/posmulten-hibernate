package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables;

import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;

import java.util.Iterator;

public class TableUtils {

    public boolean hasColumnWithName(Table table, String columnName) {
        Iterator<Column> it = table.getColumnIterator();
        while (it.hasNext()) {
            Column column = it.next();
            if (column.getName().equals(columnName)) {
                return true;
            }
        }
        return false;
    }
}
