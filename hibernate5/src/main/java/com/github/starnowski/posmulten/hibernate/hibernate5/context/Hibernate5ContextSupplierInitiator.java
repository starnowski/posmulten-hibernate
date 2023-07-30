package com.github.starnowski.posmulten.hibernate.hibernate5.context;

import com.github.starnowski.posmulten.hibernate.common.context.HibernateContext;
import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public class Hibernate5ContextSupplierInitiator implements StandardServiceInitiator<Hibernate5ContextSupplier> {

    private final HibernateContext hibernateContext;

    public Hibernate5ContextSupplierInitiator(HibernateContext hibernateContext) {
        this.hibernateContext = hibernateContext;
    }

    @Override
    public Hibernate5ContextSupplier initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        return new Hibernate5ContextSupplier(hibernateContext);
    }

    @Override
    public Class<Hibernate5ContextSupplier> getServiceInitiated() {
        return Hibernate5ContextSupplier.class;
    }
}
