package com.github.starnowski.posmulten.hibernate.core.context.metadata;

import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.PersistentClassResolver;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TenantTablePropertiesResolver;
import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public class PosmultenUtilContextInitiator implements StandardServiceInitiator<PosmultenUtilContext> {
    @Override
    public PosmultenUtilContext initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        PosmultenUtilContext result = new PosmultenUtilContext();
        result.setTenantTablePropertiesResolver(new TenantTablePropertiesResolver());
        result.setPersistentClassResolver(new PersistentClassResolver());
        return result;
    }

    @Override
    public Class<PosmultenUtilContext> getServiceInitiated() {
        return PosmultenUtilContext.class;
    }
}
