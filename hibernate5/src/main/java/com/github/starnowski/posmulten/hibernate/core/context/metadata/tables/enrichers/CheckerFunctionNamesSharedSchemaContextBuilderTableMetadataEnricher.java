package com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.enrichers;

import com.github.starnowski.posmulten.hibernate.common.context.metadata.tables.TenantTableProperties;
import com.github.starnowski.posmulten.hibernate.core.context.IDefaultSharedSchemaContextBuilderTableMetadataEnricher;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.PosmultenUtilContext;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.NameGenerator;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.PersistentClassResolver;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TenantTablePropertiesResolver;
import com.github.starnowski.posmulten.postgresql.core.context.DefaultSharedSchemaContextBuilder;
import com.github.starnowski.posmulten.postgresql.core.context.TableKey;
import org.hibernate.boot.Metadata;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public class CheckerFunctionNamesSharedSchemaContextBuilderTableMetadataEnricher implements IDefaultSharedSchemaContextBuilderTableMetadataEnricher {

    private boolean initialized = false;
    private PosmultenUtilContext posmultenUtilContext;

    @Override
    public DefaultSharedSchemaContextBuilder enrich(DefaultSharedSchemaContextBuilder builder, Metadata metadata, Table table) {
        PersistentClassResolver persistentClassResolver = this.posmultenUtilContext.getPersistentClassResolver();
        PersistentClass persistentClass = persistentClassResolver.resolve(metadata, table);
        if (persistentClass == null) {
            return builder;
        }
        TenantTablePropertiesResolver tenantTablePropertiesResolver = posmultenUtilContext.getTenantTablePropertiesResolver();
        TenantTableProperties tenantTableProperties = tenantTablePropertiesResolver.resolve(persistentClass, table, metadata);
        if (tenantTableProperties != null) {
            NameGenerator nameGenerator = posmultenUtilContext.getNameGenerator();
            builder.setNameForFunctionThatChecksIfRecordExistsInTable(new TableKey(tenantTableProperties.getTable(), tenantTableProperties.getSchema()), nameGenerator.generate("is_rls_record_exists_in_", table));
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
