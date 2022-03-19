package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables;

import com.github.starnowski.posmulten.hibernate.core.TenantTable;
import org.hibernate.boot.Metadata;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;

public class TenantTablePropertiesResolver {

    public TenantTableProperties resolve(PersistentClass persistentClass, Table table, Metadata metadata) {
        TenantTable tenantTable = (TenantTable) persistentClass.getMappedClass().getAnnotation(TenantTable.class);
        if (tenantTable == null) {
            return null;
        }
        TenantTableProperties result = new TenantTableProperties();
        result.setTable(table.getName());
        result.setTenantColumnName(tenantTable.tenantIdColumn() == null || tenantTable.tenantIdColumn().trim().isEmpty() ? null : tenantTable.tenantIdColumn());
        table.getPrimaryKey().getColumnIterator().forEachRemaining(column ->
                result.getPrimaryKeysColumnAndTypeMap().put(column.getName(), column.getSqlType())
        );
        return result;
    }
}
