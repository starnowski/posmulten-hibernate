package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables;

import com.github.starnowski.posmulten.hibernate.core.TenantTable;
import org.hibernate.boot.Metadata;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;

import java.util.HashMap;
import java.util.Map;

public class TenantTablePropertiesResolver {

    public TenantTableProperties resolve(PersistentClass persistentClass, Table table, Metadata metadata) {
        TenantTable tenantTable = (TenantTable) persistentClass.getMappedClass().getAnnotation(TenantTable.class);
        if (tenantTable == null) {
            return null;
        }
        TenantTableProperties result = new TenantTableProperties();
        result.setTable(table.getName());
        result.setSchema(table.getSchema());
        result.setTenantColumnName(tenantTable.tenantIdColumn() == null || tenantTable.tenantIdColumn().trim().isEmpty() ? null : tenantTable.tenantIdColumn());
        Map<String, String> primaryKeysColumnAndTypeMap = new HashMap<>();
        table.getPrimaryKey().getColumnIterator().forEachRemaining(column -> {
                    String sqlType = column.getSqlType();
                    primaryKeysColumnAndTypeMap.put(column.getName(), sqlType == null ? column.getSqlType(metadata.getDatabase().getDialect(), metadata) : sqlType);
                }
        );
        result.setPrimaryKeysColumnAndTypeMap(primaryKeysColumnAndTypeMap);
        return result;
    }
}
