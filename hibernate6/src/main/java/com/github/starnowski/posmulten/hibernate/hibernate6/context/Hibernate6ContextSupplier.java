package com.github.starnowski.posmulten.hibernate.hibernate6.context;

import com.github.starnowski.posmulten.hibernate.common.context.HibernateContext;
import com.github.starnowski.posmulten.hibernate.common.context.HibernateContextSupplier;
import org.hibernate.service.Service;

public class Hibernate6ContextSupplier extends HibernateContextSupplier implements Service {
    public Hibernate6ContextSupplier(HibernateContext hibernateContext) {
        super(hibernateContext);
    }
}
