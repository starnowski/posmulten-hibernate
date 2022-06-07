package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables;

import org.hibernate.mapping.*;

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

    public boolean isAnyCollectionComponentIsTenantTable(Collection collection, TenantTablePropertiesResolver tenantTablePropertiesResolver) {
        //TODO Add tests and implementation
        Class clazz = collection.getOwner().getMappedClass();
        Value value = collection.getElement();
        if (value.getClass().isAssignableFrom(ToOne.class)) {
            ToOne toOne = (ToOne) value;
            try {
                clazz = Class.forName(toOne.getReferencedEntityName());

            } catch (ClassNotFoundException e) {
                return false;
            }

        }
        return false;
    }
}
