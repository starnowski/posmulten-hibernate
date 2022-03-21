package com.github.starnowski.posmulten.hibernate.core.util;

import org.hibernate.mapping.Table;

import static java.util.Collections.emptyList;
import static org.hibernate.mapping.Constraint.generateName;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NameUtils {

    public static String generateNameForPrefixAndTableName(String prefix, String tableName){
        Table table = mock(Table.class);
        when(table.getName()).thenReturn(tableName);
        return generateName(prefix, table, emptyList());
    }
}
