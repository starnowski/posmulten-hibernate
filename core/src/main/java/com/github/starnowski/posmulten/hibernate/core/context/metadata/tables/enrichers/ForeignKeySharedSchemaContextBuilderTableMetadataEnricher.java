package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers;

import com.github.starnowski.posmulten.hibernate.core.TenantTable;
import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderTableMetadataEnricher;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.PosmultenUtilContext;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.ForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.NameGenerator;
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.mapping.ForeignKey;
import org.hibernate.mapping.Table;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Iterator;
import java.util.Map;

public class ForeignKeySharedSchemaContextBuilderTableMetadataEnricher implements IDefaultSharedSchemaContextBuilderTableMetadataEnricher {

    private boolean initialized = false;
    private PosmultenUtilContext posmultenUtilContext;

    @Override
    public DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata, Table table) {
        NameGenerator nameGenerator = posmultenUtilContext.getNameGenerator();
        ForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper helper = posmultenUtilContext.getForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper();
        // foreign keys
        final Iterator fkItr = table.getForeignKeyIterator();
        while (fkItr.hasNext()) {
            final ForeignKey foreignKey = (ForeignKey) fkItr.next();
            if (foreignKey.getReferencedEntityName() != null) {
                try {
                    TenantTable tenantTable = Class.forName(foreignKey.getReferencedEntityName()).getAnnotation(TenantTable.class);
                    if (tenantTable != null) {
                        helper.enrichBuilder(builder, foreignKey, nameGenerator);
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return builder;
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
