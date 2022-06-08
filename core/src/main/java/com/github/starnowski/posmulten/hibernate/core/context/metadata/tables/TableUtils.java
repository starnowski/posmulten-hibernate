package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables;

import org.hibernate.boot.Metadata;
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

    public boolean isAnyCollectionComponentIsTenantTable(Collection collection, TenantTablePropertiesResolver tenantTablePropertiesResolver, Table table, Metadata metadata) {
        //TODO Add tests and implementation
        Class clazz = collection.getOwner().getMappedClass();
        TenantTableProperties tenantProperties = tenantTablePropertiesResolver.resolve(clazz, table, metadata);
        Value value = collection.getElement();
        if (ToOne.class.isAssignableFrom(value.getClass())) {
            ToOne toOne = (ToOne) value;
            try {
                clazz = Class.forName(toOne.getReferencedEntityName());
                tenantProperties = tenantTablePropertiesResolver.resolve(clazz, table, metadata);
            } catch (ClassNotFoundException e) {
                return false;
            }

        }
        return false;
    }
}
