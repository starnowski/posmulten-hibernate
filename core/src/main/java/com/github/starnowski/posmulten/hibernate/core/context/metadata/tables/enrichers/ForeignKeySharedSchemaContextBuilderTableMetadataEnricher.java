package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers;

import com.github.starnowski.posmulten.hibernate.core.TenantTable;
import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderTableMetadataEnricher;
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.ForeignKey;
import org.hibernate.mapping.Table;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ForeignKeySharedSchemaContextBuilderTableMetadataEnricher implements IDefaultSharedSchemaContextBuilderTableMetadataEnricher {

    private boolean initialized = false;

    @Override
    public DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata, Table table) {
        // foreign keys
        final Iterator fkItr = table.getForeignKeyIterator();
        while (fkItr.hasNext()) {
            final ForeignKey foreignKey = (ForeignKey) fkItr.next();
            try {
                TenantTable tenantTable = (Class.forName(foreignKey.getReferencedEntityName())).getAnnotation(TenantTable.class);
                if (foreignKey.getReferencedEntityName() != null && tenantTable != null) {
                    enrichBuilder(builder, foreignKey);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return builder;
    }

    private void enrichBuilder(DefaultSharedSchemaContextBuilder builder, ForeignKey foreignKey) {
//        String functionName = NameGenerator.generate(64, "rls_fk_con_", foreignKey.getTable().getName(), foreignKey.getTable().getSchema(), foreignKey.getReferencedTable().getName(), foreignKey.getReferencedTable().getSchema());
        String functionName = "rls_fk_con_" + foreignKey.getTable();
        List<Column> referenceColumns = foreignKey.getReferencedTable().getPrimaryKey().getColumns();
        List<Column> columns = foreignKey.getColumns();
        Map<String, String> foreignKeyToPrimaryKeyMap = new HashMap<>();
        for (int i = 0; i < columns.size(); i++) {
            foreignKeyToPrimaryKeyMap.put(columns.get(i).getName(), referenceColumns.get(i).getName());
        }
        builder.createSameTenantConstraintForForeignKey(foreignKey.getTable().getName(), foreignKey.getReferencedTable().getName(), foreignKeyToPrimaryKeyMap, functionName);
    }

    @Override
    public void init(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        initialized = true;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }
}
