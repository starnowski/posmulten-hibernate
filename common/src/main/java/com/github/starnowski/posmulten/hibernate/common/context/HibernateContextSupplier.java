package com.github.starnowski.posmulten.hibernate.common.context;

import java.util.function.Supplier;

public abstract class HibernateContextSupplier implements Supplier<HibernateContext> {

    private final HibernateContext hibernateContext;


    public HibernateContextSupplier(HibernateContext hibernateContext) {
        this.hibernateContext = hibernateContext;
    }

    @Override
    public HibernateContext get() {
        return hibernateContext;
    }
}