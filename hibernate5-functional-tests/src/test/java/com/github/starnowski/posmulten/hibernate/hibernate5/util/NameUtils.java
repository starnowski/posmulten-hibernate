package com.github.starnowski.posmulten.hibernate.hibernate5.util;

import org.hibernate.mapping.Column;
import org.hibernate.mapping.Table;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.hibernate.mapping.Constraint.generateName;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NameUtils {

    public static String generateNameForPrefixAndTableName(String prefix, String tableName) {
        return generateNameForPrefixAndTableNameAndColumnsNames(prefix, tableName);
    }

    public static String generateNameForPrefixAndTableNameAndColumnsNames(String prefix, String tableName, String... columnsNames) {
        Table table = mock(Table.class);
        when(table.getName()).thenReturn(tableName);
        List<Column> columns = Stream.of(ofNullable(columnsNames).orElse(new String[0])).map(name -> {
            Column column = mock(Column.class);
            when(column.getName()).thenReturn(name);
            return column;
        }).collect(toList());
        return generateName(prefix, table, columns);
    }
}
