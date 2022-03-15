package com.github.starnowski.posmulten.hibernate.core.context.metadata;

import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.PersistentClassResolver;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TenantTablePropertiesResolver;
import org.hibernate.service.Service;

/**
 * TODO
 */
public class PosmultenUtilContext implements Service {

    private TenantTablePropertiesResolver tenantTablePropertiesResolver;
    private PersistentClassResolver persistentClassResolver;

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
