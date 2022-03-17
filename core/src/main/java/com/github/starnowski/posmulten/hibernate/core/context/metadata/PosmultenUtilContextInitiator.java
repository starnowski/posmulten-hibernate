package com.github.starnowski.posmulten.hibernate.core.context.metadata;

import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.NameGenerator;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.PersistentClassResolver;
import com.github.starnowski.posmulten.hibernate.core.context.metadata.tables.TenantTablePropertiesResolver;
import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

import static com.github.starnowski.posmulten.hibernate.core.Properties.MAXIMUM_IDENTIFIER_LENGTH;

public class PosmultenUtilContextInitiator implements StandardServiceInitiator<PosmultenUtilContext> {
    @Override
    public PosmultenUtilContext initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        PosmultenUtilContext result = new PosmultenUtilContext();
        result.setTenantTablePropertiesResolver(new TenantTablePropertiesResolver());
        result.setPersistentClassResolver(new PersistentClassResolver());
        result.setNameGenerator(new NameGenerator(MAXIMUM_IDENTIFIER_LENGTH));
        return result;
    }

    @Override
    public Class<PosmultenUtilContext> getServiceInitiated() {
        return PosmultenUtilContext.class;
    }
}
