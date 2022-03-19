package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers;

import com.github.starnowski.posmulten.hibernate.core.TenantTable;
import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderTableMetadataEnricher;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.PosmultenUtilContext;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.NameGenerator;
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
    private PosmultenUtilContext posmultenUtilContext;

    @Override
    public DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata, Table table) {
        // foreign keys
        final Iterator fkItr = table.getForeignKeyIterator();
        while (fkItr.hasNext()) {
            final ForeignKey foreignKey = (ForeignKey) fkItr.next();
            try {
                TenantTable tenantTable = Class.forName(foreignKey.getReferencedEntityName()).getAnnotation(TenantTable.class);
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
        NameGenerator nameGenerator = posmultenUtilContext.getNameGenerator();
        List<Column> referenceColumns = foreignKey.getReferencedTable().getPrimaryKey().getColumns();
        List<Column> columns = foreignKey.getColumns();
        Map<String, String> foreignKeyToPrimaryKeyMap = new HashMap<>();
        for (int i = 0; i < columns.size(); i++) {
            foreignKeyToPrimaryKeyMap.put(columns.get(i).getName(), referenceColumns.get(i).getName());
        }
        //TODO Pass schema and table name https://github.com/starnowski/posmulten/issues/239
        builder.createSameTenantConstraintForForeignKey(foreignKey.getTable().getName(), foreignKey.getReferencedTable().getName(), foreignKeyToPrimaryKeyMap, nameGenerator.generate("rls_fk_con_", foreignKey.getTable(), columns));
    }

    @Override
    public void init(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        this.posmultenUtilContext = serviceRegistryImplementor.getService(PosmultenUtilContext.class);
        initialized = true;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }
}
