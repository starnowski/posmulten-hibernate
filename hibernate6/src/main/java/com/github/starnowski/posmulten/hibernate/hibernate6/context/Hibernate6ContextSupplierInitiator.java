package com.github.starnowski.posmulten.hibernate.hibernate6.context;

import com.github.starnowski.posmulten.hibernate.common.context.HibernateContext;
import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public class Hibernate6ContextSupplierInitiator implements StandardServiceInitiator<Hibernate6ContextSupplier> {

    private final HibernateContext hibernateContext;

    public Hibernate6ContextSupplierInitiator(HibernateContext hibernateContext) {
        this.hibernateContext = hibernateContext;
    }

    @Override
    public Hibernate6ContextSupplier initiateService(Map<String, Object> map, ServiceRegistryImplementor serviceRegistryImplementor) {
        return new Hibernate6ContextSupplier(hibernateContext);
    }

    @Override
    public Class<Hibernate6ContextSupplier> getServiceInitiated() {
        return Hibernate6ContextSupplier.class;
    }
}
