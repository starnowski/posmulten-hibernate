package com.github.starnowski.posmulten.hibernate.hibernate5.context;

import com.github.starnowski.posmulten.hibernate.common.context.HibernateContext;
import com.github.starnowski.posmulten.hibernate.common.context.PropertiesHibernateContextFactory;
import org.hibernate.boot.registry.StandardServiceInitiator;
import org.hibernate.service.spi.ServiceRegistryImplementor;

import java.util.Map;

public class Hibernate5ContextSupplierInitiator implements StandardServiceInitiator<Hibernate5ContextSupplier> {

    private final HibernateContext hibernateContext;
    private final PropertiesHibernateContextFactory propertiesHibernateContextFactory;

    public Hibernate5ContextSupplierInitiator(HibernateContext hibernateContext, PropertiesHibernateContextFactory propertiesHibernateContextFactory) {
        this.hibernateContext = hibernateContext;
        this.propertiesHibernateContextFactory = propertiesHibernateContextFactory;
    }

    public Hibernate5ContextSupplierInitiator(HibernateContext hibernateContext) {
        this(hibernateContext, new PropertiesHibernateContextFactory());
    }

    public Hibernate5ContextSupplierInitiator() {
        this(null);
    }

    @Override
    public Hibernate5ContextSupplier initiateService(Map map, ServiceRegistryImplementor serviceRegistryImplementor) {
        return new Hibernate5ContextSupplier(hibernateContext == null ? propertiesHibernateContextFactory.build(map) : hibernateContext);
    }

    @Override
    public Class<Hibernate5ContextSupplier> getServiceInitiated() {
        return Hibernate5ContextSupplier.class;
    }
}
