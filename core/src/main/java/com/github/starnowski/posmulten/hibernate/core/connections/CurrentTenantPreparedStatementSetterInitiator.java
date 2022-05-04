package com.github.starnowski.posmulten.hibernate.core.connections;

import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public class CurrentTenantPreparedStatementSetterInitiator implements StandardServiceInitiator<ICurrentTenantPreparedStatementSetter> {
    @Override
    public ICurrentTenantPreparedStatementSetter initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        return null;
    }

    @Override
    public Class<ICurrentTenantPreparedStatementSetter> getServiceInitiated() {
        return ICurrentTenantPreparedStatementSetter.class;
    }
}
