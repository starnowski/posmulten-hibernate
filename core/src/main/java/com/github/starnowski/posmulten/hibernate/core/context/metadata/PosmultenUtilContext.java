package com.github.starnowski.posmulten.hibernate.core.context.metadata;

import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.*;
import org.hibernate.service.Service;

/**
 * TODO
 */
public class PosmultenUtilContext implements Service {

    private TenantTablePropertiesResolver tenantTablePropertiesResolver;
    private PersistentClassResolver persistentClassResolver;
    private NameGenerator nameGenerator;
    private CollectionResolver collectionResolver;
    private ForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper foreignKeySharedSchemaContextBuilderTableMetadataEnricherHelper;
    private TableUtils tableUtils;

    public ForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper getForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper() {
        return foreignKeySharedSchemaContextBuilderTableMetadataEnricherHelper;
    }

    public void setForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper(ForeignKeySharedSchemaContextBuilderTableMetadataEnricherHelper foreignKeySharedSchemaContextBuilderTableMetadataEnricherHelper) {
        this.foreignKeySharedSchemaContextBuilderTableMetadataEnricherHelper = foreignKeySharedSchemaContextBuilderTableMetadataEnricherHelper;
    }

    public CollectionResolver getCollectionResolver() {
        return collectionResolver;
    }

    public void setCollectionResolver(CollectionResolver collectionResolver) {
        this.collectionResolver = collectionResolver;
    }

    public NameGenerator getNameGenerator() {
        return nameGenerator;
    }

    public void setNameGenerator(NameGenerator nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    public PersistentClassResolver getPersistentClassResolver() {
        return persistentClassResolver;
    }

    public void setPersistentClassResolver(PersistentClassResolver persistentClassResolver) {
        this.persistentClassResolver = persistentClassResolver;
    }

    public TenantTablePropertiesResolver getTenantTablePropertiesResolver() {
        return tenantTablePropertiesResolver;
    }

    public void setTenantTablePropertiesResolver(TenantTablePropertiesResolver tenantTablePropertiesResolver) {
        this.tenantTablePropertiesResolver = tenantTablePropertiesResolver;
    }

    public TableUtils getTableUtils() {
        return tableUtils;
    }

    public void setTableUtils(TableUtils tableUtils) {
        this.tableUtils = tableUtils;
    }
}
