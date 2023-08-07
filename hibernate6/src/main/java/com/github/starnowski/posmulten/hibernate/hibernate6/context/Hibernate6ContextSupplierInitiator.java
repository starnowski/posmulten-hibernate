package com.github.starnowski.posmulten.hibernate.hibernate6.context;

import com.github.starnowski.posmulten.hibernate.common.context.HibernateContext;
import com.github.starnowski.posmulten.hibernate.common.context.PropertiesHibernateContextFactory;
import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public class Hibernate6ContextSupplierInitiator implements StandardServiceInitiator<Hibernate6ContextSupplier> {

    private final HibernateContext hibernateContext;
    private final PropertiesHibernateContextFactory propertiesHibernateContextFactory;

    public Hibernate6ContextSupplierInitiator(HibernateContext hibernateContext, PropertiesHibernateContextFactory propertiesHibernateContextFactory) {
        this.hibernateContext = hibernateContext;
        this.propertiesHibernateContextFactory = propertiesHibernateContextFactory;
    }

    public Hibernate6ContextSupplierInitiator(HibernateContext hibernateContext) {
        this(hibernateContext, new PropertiesHibernateContextFactory());
    }

    public Hibernate6ContextSupplierInitiator() {
        this(null);
    }

    @Override
    public Hibernate6ContextSupplier initiateService(Map<String, Object> map, ServiceRegistryImplementor serviceRegistryImplementor) {
        return new Hibernate6ContextSupplier(hibernateContext == null ? propertiesHibernateContextFactory.build(map) : hibernateContext);
    }

    @Override
    public Class<Hibernate6ContextSupplier> getServiceInitiated() {
        return Hibernate6ContextSupplier.class;
    }
}
