package com.github.starnowski.posmulten.hibernate.hibernate5.context;

import com.github.starnowski.posmulten.hibernate.common.context.HibernateContext;
import com.github.starnowski.posmulten.hibernate.common.context.HibernateContextSupplier;
import org.hibernate.service.Service;

public class Hibernate5ContextSupplier extends HibernateContextSupplier implements Service {

    public Hibernate5ContextSupplier(HibernateContext hibernateContext) {
        super(hibernateContext);
    }
}
