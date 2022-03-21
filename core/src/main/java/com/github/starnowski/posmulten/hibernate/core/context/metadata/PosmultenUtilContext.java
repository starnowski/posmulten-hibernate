package com.github.starnowski.posmulten.hibernate.core.context.metadata;

import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.CollectionResolver;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.NameGenerator;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.PersistentClassResolver;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TenantTablePropertiesResolver;
import org.hibernate.service.Service;

/**
 * TODO
 */
public class PosmultenUtilContext implements Service {

    private TenantTablePropertiesResolver tenantTablePropertiesResolver;
    private PersistentClassResolver persistentClassResolver;
    private NameGenerator nameGenerator;
    private CollectionResolver collectionResolver;

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
}
