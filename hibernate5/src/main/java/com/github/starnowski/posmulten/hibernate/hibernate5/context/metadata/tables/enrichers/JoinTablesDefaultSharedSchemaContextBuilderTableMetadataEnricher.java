package com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables.enrichers;

import com.github.starnowski.posmulten.hibernate.common.context.metadata.tables.TenantTableProperties;
import com.github.starnowski.posmulten.hibernate.hibernate5.context.IDefaultSharedSchemaContextBuilderTableMetadataEnricher;
import com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.PosmultenUtilContext;
import com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables.CollectionResolver;
import com.github.starnowski.posmulten.hibernate.hibernate5.context.metadata.tables.PersistentClassResolver;
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import org.hibernate.boot.Metadata;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.HashMap;
import java.util.Map;

public class JoinTablesDefaultSharedSchemaContextBuilderTableMetadataEnricher implements IDefaultSharedSchemaContextBuilderTableMetadataEnricher {

    private boolean initialized = false;
    private PosmultenUtilContext posmultenUtilContext;

    @Override
    public DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata, Table table) {
        PersistentClassResolver persistentClassResolver = this.posmultenUtilContext.getPersistentClassResolver();
        CollectionResolver collectionResolver = this.posmultenUtilContext.getCollectionResolver();
        PersistentClass persistentClass = persistentClassResolver.resolve(metadata, table);
        Collection pCollection = collectionResolver.resolve(metadata, table);
        if (pCollection == null || persistentClass != null) {
            return builder;
        }
        boolean collectionContainsAtLeastOneTenantTable = this.posmultenUtilContext.getTableUtils().isAnyCollectionComponentIsTenantTable(pCollection, this.posmultenUtilContext.getTenantTablePropertiesResolver(), table, metadata);
        if (collectionContainsAtLeastOneTenantTable) {
            TenantTableProperties tenantTableProperties = new TenantTableProperties();
            tenantTableProperties.setTable(table.getName());
            tenantTableProperties.setSchema(table.getSchema());
            tenantTableProperties.setPrimaryKeysColumnAndTypeMap(new HashMap<>());
            tenantTableProperties.setTenantColumnName(null);
            this.posmultenUtilContext.getRlsPolicyTableHelper().enrichBuilderWithTableRLSPolicy(builder, table, tenantTableProperties, posmultenUtilContext);
        }
        return builder;
    }

    @Override
    public void init(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        this.posmultenUtilContext = serviceRegistryImplementor.getService(PosmultenUtilContext.class);
        this.initialized = true;
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }
}
