package com.github.starnowski.posmulten.hibernate.core.connections;

import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

import static com.github.starnowski.posmulten.hibernate.core.Properties.TENANT_COLUMN_JAVA_TYPE;
import static com.github.starnowski.posmulten.hibernate.core.Properties.TENANT_COLUMN_JAVA_TYPE_CUSTOM_RESOLVER;

public class CurrentTenantPreparedStatementSetterInitiator implements StandardServiceInitiator<ICurrentTenantPreparedStatementSetter> {
    @Override
    public ICurrentTenantPreparedStatementSetter initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        if (map.containsKey(TENANT_COLUMN_JAVA_TYPE)) {
            //TODO https://github.com/starnowski/posmulten/issues/251
            switch ((String) map.get(TENANT_COLUMN_JAVA_TYPE)) {
                case "long":
                    return new CurrentTenantPreparedStatementSetterForLong();
                case "custom":
                    if (map.containsKey(TENANT_COLUMN_JAVA_TYPE_CUSTOM_RESOLVER)) {
                        try {
                            return (ICurrentTenantPreparedStatementSetter) Class.forName((String) map.get(TENANT_COLUMN_JAVA_TYPE_CUSTOM_RESOLVER)).newInstance();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        throw new RuntimeException("The property 'hibernate.posmulten.tenant.column.java.type' has value 'custom' but the 'hibernate.posmulten.tenant.column.java.type.custom.resolver' property is not set");
                    }
                case "string":
            }
        }
        return new CurrentTenantPreparedStatementSetterForString();
    }

    @Override
    public Class<ICurrentTenantPreparedStatementSetter> getServiceInitiated() {
        return ICurrentTenantPreparedStatementSetter.class;
    }
}
